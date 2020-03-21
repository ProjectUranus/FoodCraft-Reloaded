package com.projecturanus.foodcraft.worldgen

import com.projecturanus.foodcraft.common.block.generateTree
import com.projecturanus.foodcraft.common.config.FcConfig
import com.projecturanus.foodcraft.common.init.SAPLINGS
import net.minecraftforge.event.terraingen.DecorateBiomeEvent
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.random.Random

object FruitTreeWorldGen {
    @SubscribeEvent
    fun genFruitTrees(event: DecorateBiomeEvent.Decorate) {
        if (event.type == DecorateBiomeEvent.Decorate.EventType.TREE) {
            val xorRand = Random(event.world.seed)
            if (xorRand.nextDouble() > 1 - FcConfig.fruitTreeChance) {
                val sapling = SAPLINGS.random(xorRand)
                generateTree(event.world, event.world.getHeight(event.chunkPos.getBlock(xorRand.nextInt(16) + 8, 0, xorRand.nextInt(16) + 8)), sapling.defaultState, event.rand, sapling.leavesState, false)
                event.result = Event.Result.DENY
            }
        }
    }
}
