package org.cyclops.commoncapabilities.api.ingredient.capability;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.cyclops.commoncapabilities.api.ingredient.IngredientComponent;

/**
 * Event for when an {@link IngredientComponent} is being constructed.
 * @param <T> The instance type.
 * @param <M> The matching condition parameter.
 * @author rubensworks
 */
public class AttachCapabilitiesEventIngredientComponent<T, M> extends AttachCapabilitiesEvent<IngredientComponent<T, M>> {

    public AttachCapabilitiesEventIngredientComponent(IngredientComponent<T, M> ingredientComponent) {
        super((Class<IngredientComponent<T, M>>) (Class) IngredientComponent.class, ingredientComponent);
    }

    public IngredientComponent<T, M> getIngredientComponent() {
        return getObject();
    }
}
