package org.cyclops.commoncapabilities.api.ingredient.capability;

import net.minecraft.util.ResourceLocation;

/**
 * A base implementation of {@link IIngredientComponentCapabilityAttacher}.
 * @author rubensworks
 */
public abstract class IngredientComponentCapabilityAttacherAdapter<T, M> implements IIngredientComponentCapabilityAttacher<T, M> {

    private final ResourceLocation targetName;
    private final ResourceLocation capabilityProviderName;

    public IngredientComponentCapabilityAttacherAdapter(ResourceLocation targetName, ResourceLocation capabilityProviderName) {
        this.targetName = targetName;
        this.capabilityProviderName = capabilityProviderName;
    }

    @Override
    public ResourceLocation getTargetName() {
        return this.targetName;
    }

    @Override
    public ResourceLocation getCapabilityProviderName() {
        return this.capabilityProviderName;
    }

}
