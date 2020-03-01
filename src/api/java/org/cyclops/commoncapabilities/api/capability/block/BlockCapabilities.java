package org.cyclops.commoncapabilities.api.capability.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * The general registry for capabilities of blocks.
 * This is used to register capabilities to blocks AND to lookup capabilities of blocks.
 * @author rubensworks
 */
public class BlockCapabilities implements IBlockCapabilityProvider {

    private List<IBlockCapabilityConstructor> capabilityConstructors = Lists.newLinkedList();

    private final Map<Block, IBlockCapabilityProvider[]> providers = Maps.newIdentityHashMap();

    private static final BlockCapabilities INSTANCE = new BlockCapabilities();

    private BlockCapabilities() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static BlockCapabilities getInstance() {
        return INSTANCE;
    }

    /**
     * Register a capability provider for the given block or for all blocks.
     * This MUST be called after the given block is registered, otherwise the block instance may be null.
     *
     * @param block The block the capability provider applies to,
     *              null if it applies to all blocks.
     *              Only use null if absolutely necessary, as this will reduce performance during lookup.
     * @param capabilityProvider The capability provider
     */
    protected void register(@Nullable Block block, @Nonnull IBlockCapabilityProvider capabilityProvider) {
        IBlockCapabilityProvider[] providers = this.providers.get(block);
        if (providers == null) {
            providers = new IBlockCapabilityProvider[0];
        }
        providers = ArrayUtils.add(providers, capabilityProvider);
        this.providers.put(block, providers);
    }

    /**
     * Register a block capability provider constructor.
     * This will make sure that the constructor is only called AFTER all blocks have been registered.
     * So this method can be called at any time.
     *
     * @param capabilityConstructor A constructor for a block capability provider.
     */
    public void register(@Nonnull IBlockCapabilityConstructor capabilityConstructor) {
        if (ForgeRegistries.BLOCKS.getKeys().isEmpty() && this.capabilityConstructors != null) {
            this.capabilityConstructors.add(capabilityConstructor);
        } else {
            register(capabilityConstructor.getBlock(), capabilityConstructor.createProvider());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void afterBlocksRegistered(RegistryEvent.Register<Block> event) {
        for (IBlockCapabilityConstructor capabilityConstructor : this.capabilityConstructors) {
            register(capabilityConstructor.getBlock(), capabilityConstructor.createProvider());
        }
        this.capabilityConstructors = null;
    }

    @Override
    public boolean hasCapability(@Nonnull IBlockState blockState, @Nonnull Capability<?> capability,
                                 @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing facing) {
        IBlockCapabilityProvider[] providers = this.providers.get(blockState.getBlock());
        if (providers != null) {
            for (IBlockCapabilityProvider provider : providers) {
                if (provider.hasCapability(blockState, capability, world, pos, facing)) {
                    return true;
                }
            }

        } else {
            providers = this.providers.get(null);
            if (providers != null) {
                for (IBlockCapabilityProvider provider : providers) {
                    if (provider.hasCapability(blockState, capability, world, pos, facing)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull IBlockState blockState, @Nonnull Capability<T> capability,
                               @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing facing) {
        IBlockCapabilityProvider[] providers = this.providers.get(blockState.getBlock());
        if (providers != null) {
            for (IBlockCapabilityProvider provider : providers) {
                if (provider.hasCapability(blockState, capability, world, pos, facing)) {
                    return provider.getCapability(blockState, capability, world, pos, facing);
                }
            }
        } else {
            providers = this.providers.get(null);
            if (providers != null) {
                for (IBlockCapabilityProvider provider : providers) {
                    if (provider.hasCapability(blockState, capability, world, pos, facing)) {
                        return provider.getCapability(blockState, capability, world, pos, facing);
                    }
                }

            }
        }
        return null;
    }
}
