package org.cyclops.commoncapabilities.api.capability.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Equivalent of {@link ICapabilityProvider} for blocks that do not have an internal state.
 * Register a provider at {@link BlockCapabilities}.
 * @author rubensworks
 */
public interface IBlockCapabilityProvider {

    /**
     * Determines if the given block has support for the capability in question at the given position.
     * The position is identified by the World, BlockPos and EnumFacing.
     * The return value of this MIGHT change during runtime if this block gains or looses support
     * for a capability.
     *
     * Example:
     *   A Pipe getting a cover placed on one side causing it loose the Inventory attachment function for that side.
     *
     * This is a light weight version of getCapability, intended for metadata uses.
     *
     * @param blockState The blockstate to retrieve the capability from
     * @param capability The capability to check
     * @param world The world in which the given block exists
     * @param pos The position at which the given block exists
     * @param facing The Side to check from:
     *   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return True if this object supports the capability.
     */
    boolean hasCapability(@Nonnull IBlockState blockState, @Nonnull Capability<?> capability,
                          @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing facing);

    /**
     * Retrieves the handler for the capability of the given block requested at the given position.
     * The position is identified by the World, BlockPos and EnumFacing.
     * The return value CAN be null if the block does not support the capability.
     * The return value CAN be the same for multiple faces.
     *
     * @param blockState The blockstate to retrieve the capability from
     * @param capability The capability to check
     * @param world The world in which the given block exists
     * @param pos The position at which the given block exists
     * @param facing The Side to check from:
     *   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @param <T> The capability type.
     * @return The requested capability. Returns null when
     *         {@link #hasCapability(IBlockState, Capability, IBlockAccess, BlockPos, EnumFacing)}} would return false.
     */
    @Nullable
    <T> T getCapability(@Nonnull IBlockState blockState, @Nonnull Capability<T> capability,
                        @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing facing);

}
