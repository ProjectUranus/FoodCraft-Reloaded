package org.cyclops.commoncapabilities.api.capability.wrench;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * An indicator for what is being targeted by a wrench.
 * @author rubensworks
 */
public class WrenchTarget {
    private final RayTraceResult.Type type;
    private final World world;
    private final BlockPos pos;
    private final EnumFacing side;
    private final Entity entity;

    protected WrenchTarget(RayTraceResult.Type type, World world, BlockPos pos, EnumFacing side, Entity entity) {
        this.type = type;
        this.world = world;
        this.pos = pos;
        this.side = side;
        this.entity = entity;
    }

    public static WrenchTarget forBlock(World world, BlockPos pos, EnumFacing side) {
        return new WrenchTarget(RayTraceResult.Type.BLOCK, world, pos, side, null);
    }

    public static WrenchTarget forEntity(Entity entity) {
        return new WrenchTarget(RayTraceResult.Type.ENTITY, null, null, null, entity);
    }

    public static WrenchTarget forNone() {
        return new WrenchTarget(RayTraceResult.Type.MISS, null, null, null, null);
    }

    public RayTraceResult.Type getType() {
        return type;
    }

    public @Nullable World getWorld() {
        return world;
    }

    public @Nullable BlockPos getPos() {
        return pos;
    }

    public @Nullable EnumFacing getSide() {
        return side;
    }

    public @Nullable Entity getEntity() {
        return entity;
    }
}
