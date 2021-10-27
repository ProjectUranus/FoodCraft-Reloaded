package org.cyclops.commoncapabilities.api.capability.block;

import net.minecraft.block.Block;

import javax.annotation.Nullable;

/**
 * Constructor for block capability providers.
 * @author rubensworks
 */
public interface IBlockCapabilityConstructor {

    /**
     * @return The block the capability provider applies to,
     *         null if it applies to all blocks.
     *         Only use null if absolutely necessary, as this will reduce performance during lookup.
     */
    @Nullable
    public Block getBlock();

    /**
     * @return The capability provider
     */
    public IBlockCapabilityProvider createProvider();

}
