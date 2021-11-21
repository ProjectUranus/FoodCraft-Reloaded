package com.projecturanus.foodcraft.worldgen

import com.projecturanus.foodcraft.common.block.generateTree
import com.projecturanus.foodcraft.common.config.FcConfig
import com.projecturanus.foodcraft.common.init.SAPLINGS
import net.minecraft.util.ResourceLocation
import net.minecraft.world.biome.Biome
import net.minecraftforge.event.terraingen.DecorateBiomeEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.registries.ForgeRegistry

object FruitTreeWorldGenHandler {
    private var biomeIdsInternal = emptyList<Int>()

    var biomeIds
        get() = ""
        set(value: String) {
            biomeIdsInternal = value
                .split(";")
                .map { ResourceLocation(it) }
                .filter { ForgeRegistries.BIOMES.containsKey(it) }
                .map { (ForgeRegistries.BIOMES as ForgeRegistry<Biome>).getID(it) }
        }

    @SubscribeEvent
    fun genFruitTrees(event: DecorateBiomeEvent.Decorate) {
        if (event.type == DecorateBiomeEvent.Decorate.EventType.TREE) {
            if (event.rand.nextGaussian() > 1.0 - FcConfig.fruitTreeChance) {
                val sapling = SAPLINGS[event.rand.nextInt(SAPLINGS.size)]
                val pos = event.world.getHeight(event.chunkPos.getBlock(event.rand.nextInt(16) + 8, 0, event.rand.nextInt(16) + 8))
                if ((ForgeRegistries.BIOMES as ForgeRegistry<Biome>).getID(event.world.getBiome(pos)) in biomeIdsInternal)
                    generateTree(event.world, pos, sapling.defaultState, event.rand, sapling.leavesState, false)
            }
        }
    }
}
