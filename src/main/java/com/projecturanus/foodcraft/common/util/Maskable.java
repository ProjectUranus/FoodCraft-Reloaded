package com.projecturanus.foodcraft.common.util;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public interface Maskable extends Colorable {
    ModelResourceLocation getModelLocation();
}
