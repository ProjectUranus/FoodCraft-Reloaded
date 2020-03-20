package com.projecturanus.foodcraft.common.recipe;

import com.projecturanus.foodcraft.common.block.entity.TileEntityFluidRecipeMachine;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

import java.util.Objects;
import java.util.stream.Collectors;

public interface RecipeRegistryJavaFix {
    static void putFluids(IForgeRegistry<?> registry) {
        if (FluidRecipe.class.isAssignableFrom(registry.getRegistrySuperType()))
            TileEntityFluidRecipeMachine.Companion.getALLOW_FLUIDS_MAP().put(RegistryManager.ACTIVE.getName(registry),
                registry.getValuesCollection().stream()
                    .map(recipe -> ((FluidRecipe<?>) recipe).getFluidInput())
                    .filter(Objects::nonNull)
                    .map(FluidStack::getFluid)
                    .collect(Collectors.toSet()));
    }
}
