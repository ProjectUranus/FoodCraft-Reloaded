package org.cyclops.commoncapabilities.api.ingredient.storage;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.cyclops.commoncapabilities.api.ingredient.IngredientComponent;

import javax.annotation.Nullable;

/**
 * A handler for wrapping external storage interfaces into {@link IIngredientComponentStorage}
 * and the other way ({@link IIngredientComponentStorage} into external storage interfaces).
 *
 * @param <T> The instance type.
 * @param <M> The matching condition parameter, may be Void. Instances MUST properly implement the equals method.
 * @param <S> The external storage type.
 * @author rubensworks
 */
public interface IIngredientComponentStorageWrapperHandler<T, M, S> {

    /**
     * @return The ingredient component.
     */
    public IngredientComponent<T, M> getComponent();

    /**
     * Wrap the given storage.
     * @param storage The external storage to wrap.
     * @return A component storage.
     */
    public IIngredientComponentStorage<T, M> wrapComponentStorage(S storage);

    /**
     * Wrap the given storage.
     * @param componentStorage The component storage to wrap.
     * @return A component storage.
     */
    public S wrapStorage(IIngredientComponentStorage<T, M> componentStorage);

    /**
     * Get the storage within the given capability provider.
     * @param capabilityProvider A capability provider.
     * @param facing The side to get the storage from.
     * @return A storage, or null if it does not exist.
     */
    @Nullable
    public S getStorage(ICapabilityProvider capabilityProvider, @Nullable EnumFacing facing);

    /**
     * Get the ingredient storage within the given capability provider.
     * @param capabilityProvider A capability provider.
     * @param facing The side to get the storage from.
     * @return An ingredient storage, or null if it does not exist.
     */
    public default IIngredientComponentStorage<T, M> getComponentStorage(ICapabilityProvider capabilityProvider,
                                                                         @Nullable EnumFacing facing) {
        S storage = getStorage(capabilityProvider, facing);
        return storage == null ? new IngredientComponentStorageEmpty<>(getComponent()) : wrapComponentStorage(storage);
    }

}
