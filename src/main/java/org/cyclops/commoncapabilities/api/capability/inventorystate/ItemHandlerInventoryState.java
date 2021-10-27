package org.cyclops.commoncapabilities.api.capability.inventorystate;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.Objects;

/**
 * An inventory state implementation that wraps around an {@link IItemHandlerModifiable}.
 * @author rubensworks
 */
public class ItemHandlerInventoryState implements IInventoryState, IItemHandler {

    private final IItemHandler itemHandler;
    private int hash;

    public ItemHandlerInventoryState(IItemHandler itemHandler) {
        this.itemHandler = Objects.requireNonNull(itemHandler);
    }

    protected void setNewHash() {
        this.hash++;
    }

    @Override
    public int getHash() {
        return this.hash;
    }

    @Override
    public int getSlots() {
        return itemHandler.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!simulate) {
            setNewHash();
        }
        return itemHandler.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!simulate) {
            setNewHash();
        }
        return itemHandler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }
}
