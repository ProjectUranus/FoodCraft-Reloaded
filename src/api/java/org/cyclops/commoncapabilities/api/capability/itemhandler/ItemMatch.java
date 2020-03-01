package org.cyclops.commoncapabilities.api.capability.itemhandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Comparator;

/**
 * Item matching flags to be used in {@link ISlotlessItemHandler}.
 * @author rubensworks
 */
public final class ItemMatch {

    /**
     * Convenience value matching any ItemStack.
     */
    public static final int ANY = 0;
    /**
     * Match ItemStack items.
     */
    public static final int ITEM = 1;
    /**
     * Match ItemStack damage values.
     */
    public static final int DAMAGE = 2;
    /**
     * Match ItemStack NBT tags.
     */
    public static final int NBT = 4;
    /**
     * Match ItemStack stacksizes.
     */
    public static final int STACKSIZE = 8;
    /**
     * Convenience value matching ItemStacks exactly by item, damage value, NBT tag and stacksize.
     */
    public static final int EXACT = ITEM | DAMAGE | NBT | STACKSIZE;

    /**
     * A comparator for NBT tags. (This is set in GeneralConfig)
     */
    public static Comparator<NBTBase> NBT_COMPARATOR;

    public static boolean areItemStacksEqual(ItemStack a, ItemStack b, int matchFlags) {
        if (matchFlags == ANY) {
            return true;
        }
        boolean item      = (matchFlags & ITEM     ) > 0;
        boolean damage    = (matchFlags & DAMAGE   ) > 0
                && !(a.getItemDamage() == OreDictionary.WILDCARD_VALUE
                  || b.getItemDamage() == OreDictionary.WILDCARD_VALUE);
        boolean nbt       = (matchFlags & NBT      ) > 0;
        boolean stackSize = (matchFlags & STACKSIZE) > 0;
        return a == b || a.isEmpty() && b.isEmpty() ||
                (!a.isEmpty() && !b.isEmpty()
                        && (!item || a.getItem() == b.getItem())
                        && (!damage || a.getItemDamage() == b.getItemDamage())
                        && (!stackSize || a.getCount() == b.getCount())
                        && (!nbt || areItemStackTagsEqual(a, b)));
    }

    public static boolean areItemStackTagsEqual(ItemStack a, ItemStack b) {
        if ((a.getTagCompound() == null && b.getTagCompound() != null)
                || a.getTagCompound() != null && b.getTagCompound() == null) {
            return false;
        } else {
            return (a.getTagCompound() == null || NBT_COMPARATOR.compare(a.getTagCompound(), b.getTagCompound()) == 0);
            // We don't include a.areCapsCompatible(b), because we expect that differing caps have different NBT tags.
        }
    }

}
