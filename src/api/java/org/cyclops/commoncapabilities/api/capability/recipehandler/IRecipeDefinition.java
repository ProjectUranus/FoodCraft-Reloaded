package org.cyclops.commoncapabilities.api.capability.recipehandler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import org.cyclops.commoncapabilities.api.ingredient.IMixedIngredients;
import org.cyclops.commoncapabilities.api.ingredient.IngredientComponent;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Defines the inputs and outputs of a recipe.
 * Inputs are ingredient prototypes for ingredient component types.
 * Outputs are exact instances for ingredient component types.
 *
 * Implementing classes should properly implement the equals and hashCode methods.
 *
 * @author rubensworks
 */
public interface IRecipeDefinition extends Comparable<IRecipeDefinition> {

    /**
     * @return The input ingredient component types.
     */
    public Set<IngredientComponent<?, ?>> getInputComponents();

    /**
     * Get the input prototypes of a certain type.
     *
     * The first list contains a list of ingredients,
     * whereas the deeper second list contains different prototype-based alternatives for the ingredient at this position.
     *
     * @param ingredientComponent An ingredient component type.
     * @param <T> The instance type.
     * @param <M> The matching condition parameter, may be Void.
     * @return Input prototypes.
     */
    public <T, M> List<IPrototypedIngredientAlternatives<T, M>> getInputs(IngredientComponent<T, M> ingredientComponent);

    /**
     * @return The output ingredients.
     */
    public IMixedIngredients getOutput();

    /**
     * Deserialize a recipe to NBT.
     * @param recipe A recipe.
     * @return An NBT representation of the given recipe.
     */
    public static NBTTagCompound serialize(IRecipeDefinition recipe) {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound inputTag = new NBTTagCompound();
        for (IngredientComponent<?, ?> component : recipe.getInputComponents()) {
            NBTTagList instances = new NBTTagList();
            for (IPrototypedIngredientAlternatives ingredient : recipe.getInputs(component)) {
                NBTTagCompound subTag = new NBTTagCompound();
                IPrototypedIngredientAlternatives.ISerializer serializer = ingredient.getSerializer();
                subTag.setTag("val", serializer.serialize(component, ingredient));
                subTag.setByte("type", serializer.getId());
                instances.appendTag(subTag);
            }
            inputTag.setTag(component.getRegistryName().toString(), instances);
        }
        tag.setTag("input", inputTag);
        tag.setTag("output", IMixedIngredients.serialize(recipe.getOutput()));
        return tag;
    }

    /**
     * Deserialize a recipe from NBT
     * @param tag An NBT tag.
     * @return A new mixed recipe instance.
     * @throws IllegalArgumentException If the given tag is invalid or does not contain data on the given recipe.
     */
    public static RecipeDefinition deserialize(NBTTagCompound tag) throws IllegalArgumentException {
        Map<IngredientComponent<?, ?>, List<IPrototypedIngredientAlternatives<?, ?>>> inputs = Maps.newIdentityHashMap();
        if (!tag.hasKey("input")) {
            throw new IllegalArgumentException("A recipe tag did not contain a valid input tag");
        }
        if (!tag.hasKey("output")) {
            throw new IllegalArgumentException("A recipe tag did not contain a valid output tag");
        }
        NBTTagCompound inputTag = tag.getCompoundTag("input");
        for (String componentName : inputTag.getKeySet()) {
            IngredientComponent<?, ?> component = IngredientComponent.REGISTRY.getValue(new ResourceLocation(componentName));
            if (component == null) {
                throw new IllegalArgumentException("Could not find the ingredient component type " + componentName);
            }
            NBTBase subTag = inputTag.getTag(componentName);
            if (!(subTag instanceof NBTTagList)) {
                throw new IllegalArgumentException("The ingredient component type " + componentName + " did not contain a valid list of instances");
            }
            NBTTagList instancesTag = (NBTTagList) subTag;
            List<IPrototypedIngredientAlternatives<?, ?>> instances = Lists.newArrayList();
            for (NBTBase instanceTag : instancesTag) {
                IPrototypedIngredientAlternatives.ISerializer alternativeSerializer;
                NBTBase deserializeTag;
                if (instanceTag instanceof NBTTagList) {
                    // TODO: remove backwards compat in 1.13
                    alternativeSerializer = PrototypedIngredientAlternativesList.SERIALIZER;
                    deserializeTag = instanceTag;
                } else if (instanceTag instanceof NBTTagCompound) {
                    NBTTagCompound instanceTagCompound = (NBTTagCompound) instanceTag;
                    byte type = instanceTagCompound.getByte("type");
                    alternativeSerializer = IPrototypedIngredientAlternatives.SERIALIZERS.get(type);
                    if (alternativeSerializer == null) {
                        throw new IllegalArgumentException("Could not find a prototyped ingredient alternative serializer for id " + type);
                    }
                    deserializeTag = ((NBTTagCompound) instanceTag).getTag("val");
                } else {
                    throw new IllegalArgumentException("The ingredient component type " + componentName + " did not contain a valid reference to instances");
                }
                IPrototypedIngredientAlternatives alternatives = alternativeSerializer.deserialize(component, deserializeTag);
                instances.add(alternatives);
            }
            inputs.put(component, instances);
        }
        IMixedIngredients output = IMixedIngredients.deserialize(tag.getCompoundTag("output"));
        return new RecipeDefinition(inputs, output);
    }

}
