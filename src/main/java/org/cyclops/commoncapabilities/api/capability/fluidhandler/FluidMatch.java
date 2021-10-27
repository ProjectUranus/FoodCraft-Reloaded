package org.cyclops.commoncapabilities.api.capability.fluidhandler;

import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

/**
 * Fluid matching flags.
 * @author rubensworks
 */
public final class FluidMatch {

    /**
     * Convenience value matching any FluidStack.
     */
    public static final int ANY = 0;
    /**
     * Match FluidStack fluids.
     */
    public static final int FLUID = 1;
    /**
     * Match FluidStacks NBT tags.
     */
    public static final int NBT = 2;
    /**
     * Match FluidStacks amounts.
     */
    public static final int AMOUNT = 4;
    /**
     * Convenience value matching FluidStacks exactly by fluid, NBT tag and amount.
     */
    public static final int EXACT = FLUID | NBT | AMOUNT;

    public static boolean areFluidStacksEqual(@Nullable FluidStack a, @Nullable FluidStack b, int matchFlags) {
        if (matchFlags == ANY) {
            return true;
        }
        boolean fluid  = (matchFlags & FLUID ) > 0;
        boolean nbt    = (matchFlags & NBT   ) > 0;
        boolean amount = (matchFlags & AMOUNT) > 0;
        return a == b ||
                (a != null && b != null
                        && (!fluid || a.getFluid() == b.getFluid())
                        && (!amount || a.amount == b.amount)
                        && (!nbt || FluidStack.areFluidStackTagsEqual(a, b)));
    }

}
