package org.cyclops.commoncapabilities.api.ingredient;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

/**
 * An ingredient that is identified by a given instance and can be matched with other instances under a given condition.
 *
 * Implementing classes should properly implement the equals and hashCode methods.
 *
 * @param <T> The instance type.
 * @param <M> The matching condition parameter, may be Void.
 * @author rubensworks
 */
public interface IPrototypedIngredient<T, M> extends Comparable<IPrototypedIngredient<?, ?>> {

    /**
     * @return The type of ingredient component this prototype exists for.
     */
    public IngredientComponent<T, M> getComponent();

    /**
     * @return The prototype of this ingredient.
     */
    public T getPrototype();

    /**
     * @return The condition under which the prototype instance can be matched.
     */
    public M getCondition();

    /**
     * Deserialize an ingredient to NBT.
     * @param prototypedIngredient Ingredient.
     * @param <T> The instance type.
     * @param <M> The matching condition parameter, may be Void.
     * @return An NBT representation of the given ingredient.
     */
    public static <T, M> NBTTagCompound serialize(IPrototypedIngredient<T, M> prototypedIngredient) {
        NBTTagCompound tag = new NBTTagCompound();

        IngredientComponent<T, M> component = prototypedIngredient.getComponent();
        tag.setString("ingredientComponent", component.getName().toString());

        IIngredientSerializer<T, M> serializer = component.getSerializer();
        tag.setTag("prototype", serializer.serializeInstance(prototypedIngredient.getPrototype()));
        tag.setTag("condition", serializer.serializeCondition(prototypedIngredient.getCondition()));

        return tag;
    }

    /**
     * Deserialize an ingredient from NBT
     * @param tag An NBT tag.
     * @return A new ingredient instance.
     * @throws IllegalArgumentException If the given tag is invalid or does not contain data on the given ingredient.
     */
    public static PrototypedIngredient deserialize(NBTTagCompound tag) throws IllegalArgumentException {
        if (!tag.hasKey("ingredientComponent", Constants.NBT.TAG_STRING)) {
            throw new IllegalArgumentException("Could not find a ingredientComponent entry in the given tag");
        }
        if (!tag.hasKey("prototype")) {
            throw new IllegalArgumentException("Could not find a prototype entry in the given tag");
        }
        if (!tag.hasKey("condition")) {
            throw new IllegalArgumentException("Could not find a condition entry in the given tag");
        }

        String componentName = tag.getString("ingredientComponent");
        IngredientComponent<?, ?> component = IngredientComponent.REGISTRY.getValue(new ResourceLocation(componentName));
        if (component == null) {
            throw new IllegalArgumentException("Could not find the ingredient component type " + componentName);
        }

        IIngredientSerializer serializer = component.getSerializer();
        Object prototype = serializer.deserializeInstance(tag.getTag("prototype"));
        Object condition = serializer.deserializeCondition(tag.getTag("condition"));

        return new PrototypedIngredient(component, prototype, condition);
}

}
