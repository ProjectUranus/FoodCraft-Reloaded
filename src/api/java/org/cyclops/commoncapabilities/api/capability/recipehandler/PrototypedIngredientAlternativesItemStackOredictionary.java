package org.cyclops.commoncapabilities.api.capability.recipehandler;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;
import org.cyclops.commoncapabilities.api.ingredient.IIngredientMatcher;
import org.cyclops.commoncapabilities.api.ingredient.IPrototypedIngredient;
import org.cyclops.commoncapabilities.api.ingredient.IngredientComponent;
import org.cyclops.commoncapabilities.api.ingredient.PrototypedIngredient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An oredictionary-based {@link IPrototypedIngredientAlternatives} implementation.
 * @author rubensworks
 */
public class PrototypedIngredientAlternativesItemStackOredictionary implements IPrototypedIngredientAlternatives<ItemStack, Integer> {

    public static final PrototypedIngredientAlternativesItemStackOredictionary.Serializer SERIALIZER = new PrototypedIngredientAlternativesItemStackOredictionary.Serializer();
    static {
        SERIALIZERS.put((byte) 1, SERIALIZER);
    }

    private static final LoadingCache<String, List<ItemStack>> CACHE_OREDICT = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String, List<ItemStack>>() {
                @Override
                public List<ItemStack> load(String key) throws Exception {
                    return OreDictionary.getOres(key);
                }
            });

    private final List<String> keys;
    private final Integer matchCondition;
    private final long quantity;

    public PrototypedIngredientAlternativesItemStackOredictionary(List<String> keys, Integer matchCondition, long quantity) {
        this.keys = keys;
        this.matchCondition = matchCondition;
        this.quantity = quantity;
    }

    /**
     * Get a list of variants from the given stack if its damage value is the wildcard value,
     * otherwise the list will only contain the given itemstack.
     * @param itemStack The itemstack
     * @return The list of variants.
     */
    public static List<ItemStack> getItemStackVariants(ItemStack itemStack) {
        NonNullList<ItemStack> output = NonNullList.create();
        if(itemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            itemStack.getItem().getSubItems(CreativeTabs.SEARCH, output);
        } else {
            output.add(itemStack);
        }
        return output;
    }

    public Collection<IPrototypedIngredient<ItemStack, Integer>> getAlternatives() {
        IIngredientMatcher<ItemStack, Integer> matcher = IngredientComponent.ITEMSTACK.getMatcher();
        return this.keys.stream().flatMap((key) -> {
            try {
                return CACHE_OREDICT.get(key).stream();
            } catch (ExecutionException e) {
                return Stream.empty();
            }
        }).flatMap(itemStack -> getItemStackVariants(itemStack).stream())
                .map(itemStack -> matcher.withQuantity(itemStack, getQuantity()))
                .map(itemStack -> new PrototypedIngredient<>(IngredientComponent.ITEMSTACK, itemStack, this.matchCondition))
                .collect(Collectors.toList());
    }

    @Override
    public ISerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IPrototypedIngredientAlternatives
                && this.getAlternatives().equals(((IPrototypedIngredientAlternatives) obj).getAlternatives());
    }

    @Override
    public int hashCode() {
        int inputsHash = 333;
        for (IPrototypedIngredient<ItemStack, Integer> value : getAlternatives()) {
            inputsHash |= value.hashCode();
        }
        return 1235 | inputsHash << 2;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "[PrototypedIngredientAlternativesList: " + getAlternatives().toString() + "]";
    }

    public static class Serializer implements IPrototypedIngredientAlternatives.ISerializer<PrototypedIngredientAlternativesItemStackOredictionary> {
        @Override
        public byte getId() {
            return 1;
        }

        @Override
        public <T, M> NBTBase serialize(IngredientComponent<T, M> ingredientComponent, PrototypedIngredientAlternativesItemStackOredictionary alternatives) {
            NBTTagCompound tag = new NBTTagCompound();
            NBTTagList keys = new NBTTagList();
            for (String key : alternatives.keys) {
                keys.appendTag(new NBTTagString(key));
            }
            tag.setTag("keys", keys);
            tag.setInteger("match", alternatives.matchCondition);
            tag.setLong("quantity", alternatives.quantity);
            return tag;
        }

        @Override
        public <T, M> PrototypedIngredientAlternativesItemStackOredictionary deserialize(IngredientComponent<T, M> ingredientComponent, NBTBase tag) {
            NBTTagCompound tagCompound = (NBTTagCompound) tag;
            if (!tagCompound.hasKey("keys")) {
                throw new IllegalArgumentException("A oredict prototyped alternatives did not contain valid keys");
            }
            if (!tagCompound.hasKey("match")) {
                throw new IllegalArgumentException("A oredict prototyped alternatives did not contain a valid match");
            }
            NBTTagList keysTag = tagCompound.getTagList("keys", Constants.NBT.TAG_STRING);
            List<String> keys = Lists.newArrayList();
            for (NBTBase key : keysTag) {
                keys.add(((NBTTagString) key).getString());
            }
            int matchCondition = tagCompound.getInteger("match");
            long quantity = tagCompound.hasKey("quantity") ? tagCompound.getLong("quantity") : 1; // TODO: remove check in 1.13
            return new PrototypedIngredientAlternativesItemStackOredictionary(keys, matchCondition, quantity);
        }
    }
}
