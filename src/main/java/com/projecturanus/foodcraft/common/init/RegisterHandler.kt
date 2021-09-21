package com.projecturanus.foodcraft.common.init

import com.google.gson.GsonBuilder
import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.FruitTypes.*
import com.projecturanus.foodcraft.common.VegetableTypes.*
import com.projecturanus.foodcraft.common.block.*
import com.projecturanus.foodcraft.common.item.*
import com.projecturanus.foodcraft.common.util.Colorable
import com.projecturanus.foodcraft.fluid.FluidCookingOil
import com.projecturanus.foodcraft.fluid.FluidMilk
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.init.MobEffects.*
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fluids.BlockFluidClassic
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE
import net.minecraftforge.oredict.OreDictionary.registerOre
import java.awt.Color
import kotlin.math.pow


val INGREDIENTS = arrayListOf<Item>()
val CROPS = arrayListOf<ItemCrop>()
val SNACKS = arrayListOf<FCRItemFood>()
val STAPLES = arrayListOf<FCRItemFood>()
val DRINKS = arrayListOf<ItemDrink>()
val ICECREAMS = arrayListOf<ItemIcecream>()
val LIQUEURS = arrayListOf<ItemLiqueur>()
val JAMS = arrayListOf<ItemJam>()

val plantVegetables = arrayOf(CORN, CUCUMBER, EGGPLANT, FACING_HEAVEN_PEPPER, GREEN_PEPPER, PEANUT, RICE, STICKY_RICE, SWEET_POTATO, TOMATO, WHITE_RADISH, CABBAGE)
val plantFruits = arrayOf(STRAWBERRY)
val saplingFruits = arrayOf(GRAPEFRUIT, CHERRY, COCONUT, BANANA, PEACH, PERSIMMON, POMEGRANATE, HAWTHORN, LOQUAT, LEMON, PAPAYA, LONGAN, MANGO, LITCHI, PEAR, ORANGE, DATE)

val PLANTS = arrayListOf<BlockCrop>()
val SAPLINGS = arrayListOf<BlockFruitSapling>()
val LEAVES = arrayListOf<BlockFruitLeaves>()
val KITCHEN_KNIFES = arrayListOf<ItemKitchenKnife>()

val DEFAULT_MODEL_ITEMS = arrayListOf<Item>()

@Mod.EventBusSubscriber(modid = MODID)
object RegisterHandler {
    val blockQueue = arrayListOf<Block>()
    val itemQueue = arrayListOf<Pair<Item, Array<String>>>()

    fun registerOres(name: String, vararg items: Item) {
        items.forEach { registerOre(name, it) }
    }

    fun mirrorOres(target: String, source: String) {
        OreDictionary.getOres(source).forEach { registerOre(target, it) }
    }

    fun registerVanillaOres() {
        registerOre("listAllwater", Items.WATER_BUCKET)
        registerOre("bread", Items.BREAD)

        registerOre("listAllmeatraw", Items.BEEF)
        registerOre("listAllmeatraw", Items.CHICKEN)
        registerOre("listAllmeatraw", Items.PORKCHOP)
        registerOre("listAllmeatraw", Items.MUTTON)
        registerOre("listAllmeatraw", Items.RABBIT)

        registerOre("listAllmilk", Items.MILK_BUCKET)
        registerOre("flourEqualswheat", Items.WHEAT)

        registerOre("bread", Items.BREAD)
        registerOre("foodBread", Items.BREAD)

        registerOre("cropCarrot", Items.CARROT)
        registerOre("cropPotato", Items.POTATO)
        registerOre("cropPumpkin", Blocks.PUMPKIN)
        registerOre("cropWheat", Items.WHEAT)
        registerOre("cropBeet",  Items.BEETROOT)
        registerOre("cropCarrot", Items.CARROT)
        registerOre("cropApple", Items.APPLE);

        registerOre("listAllgrain", Items.WHEAT)

        registerOre("listAllmeatcooked", Items.COOKED_BEEF)
        registerOre("listAllmeatcooked", Items.COOKED_CHICKEN)
        registerOre("listAllmeatcooked", Items.COOKED_PORKCHOP)
        registerOre("listAllmeatcooked", Items.COOKED_MUTTON)
        registerOre("listAllmeatcooked", Items.COOKED_RABBIT)

        registerOre("listAllfishraw", ItemStack(Items.FISH, 1, WILDCARD_VALUE))
        registerOre("listAllfishfresh", ItemStack(Items.FISH, 1, WILDCARD_VALUE))
        registerOre("listAllfishcooked", ItemStack(Items.COOKED_FISH, 1, WILDCARD_VALUE))

        registerOres("listAllveggie",
            Items.CARROT,
            Items.POTATO,
            Item.getItemFromBlock(Blocks.PUMPKIN),
            Items.BEETROOT
        )

        registerOre("listAllporkraw", Items.PORKCHOP)
        registerOre("listAllporkcooked", Items.COOKED_PORKCHOP)
        registerOre("listAllbeefraw", Items.BEEF)
        registerOre("listAllbeefcooked", Items.COOKED_BEEF)
        registerOre("listAllrabbitraw", Items.RABBIT)
        registerOre("listAllrabbitcooked", Items.COOKED_RABBIT)
        registerOre("listAllmuttonraw", Items.MUTTON)
        registerOre("listAllmuttoncooked", Items.COOKED_MUTTON)
        registerOre("listAllchickenraw", Items.CHICKEN)
        registerOre("listAllegg", Items.EGG)
        registerOre("listAllchickencooked", Items.COOKED_CHICKEN)
        registerOres("listAllsugar", Items.SUGAR)

        registerOre("listAllmushroom", Blocks.RED_MUSHROOM)
        registerOre("listAllmushroom", Blocks.BROWN_MUSHROOM)
    }

    fun registerPlants() {
        plantVegetables.forEach {
            val capitalized = it.toString().replace("_", "").capitalize()
            val blockCrop = BlockCrop().apply {
                setRegistryName(MODID, it.toString())
            }
            PLANTS += blockCrop
            blockQueue += blockCrop
            itemQueue += crop(null, blockCrop) {
                setRegistryName(MODID, it.toString())
                creativeTab = FcTabPlant
                translationKey = "$MODID.$it"
                CROPS += this@crop
                blockCrop.cropItem = this
            } to arrayOf("crop$capitalized", "food$capitalized", "seed$capitalized", "listAllveggie")
        }
        plantFruits.forEach {
                val capitalized = it.toString().replace("_", "").capitalize()
                val blockCrop = BlockCrop().apply {
                    setRegistryName(MODID, it.toString())
                }
                PLANTS += blockCrop
                blockQueue += blockCrop
                itemQueue += crop(null, blockCrop) {
                    setRegistryName(MODID, it.toString())
                    creativeTab = FcTabPlant
                    translationKey = "$MODID.$it"
                    CROPS += this@crop
                    blockCrop.cropItem = this
                } to arrayOf("crop$capitalized", "food$capitalized", "seed$capitalized", "listAllfruit")
        }
        saplingFruits.forEach {
            val fruit = food(name = it.toString(), healAmount = 1, saturation = 0.6f) {}
            val blockLeaves = BlockFruitLeaves(fruit).apply {
                setRegistryName(MODID, "${it}_leaves")
                baseTranslationKey = "item.$MODID.leaves.name"
                realTranslationKey = "item.$MODID.$it.name"
            }
            val blockSapling = BlockFruitSapling(blockLeaves.defaultState).apply {
                setRegistryName(MODID, "${it}_sapling")
                baseTranslationKey = "item.$MODID.sapling.name"
                realTranslationKey = "item.$MODID.$it.name"
            }
            itemQueue += fruit to arrayOf("crop${it.toString().capitalize()}", "listAllfruit")
            DEFAULT_MODEL_ITEMS += fruit
            blockQueue += blockLeaves
            LEAVES += blockLeaves
            blockQueue += blockSapling
            SAPLINGS += blockSapling
            itemQueue += ItemBlock(blockLeaves).apply {
                setRegistryName(MODID, "${it}_leaves")
            } to arrayOf("treeLeaves")
            itemQueue += ItemBlock(blockSapling).apply {
                setRegistryName(MODID, "${it}_sapling")
            } to arrayOf("treeSapling")
        }

        val greenOnion = BlockSugarLike().apply {
            setRegistryName(MODID, "green_onion")
            translationKey = "$MODID.green_onion"
        }
        blockQueue += greenOnion
        itemQueue += ItemSugarLike(greenOnion).apply {
            setRegistryName(MODID, "green_onion")
            translationKey = "$MODID.green_onion"
        } to arrayOf("foodGreenonion", "cropGreenonion", "listAllgreenveggie")

        DEFAULT_MODEL_ITEMS.addAll(itemQueue.map(Pair<Item, Array<String>>::first))
    }

    lateinit var mungBeanBlock: BlockCrop
    lateinit var soybeanBlock: BlockCrop
    lateinit var adzukiBeanBlock: BlockCrop

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        fun Block.itemBlock(): Block {
            val itemBlock = ItemBlock(this).setRegistryName(this.registryName)
            itemQueue += itemBlock to arrayOf()
            DEFAULT_MODEL_ITEMS += itemBlock
            return this
        }
        blockQueue.forEach(event.registry::register)
        event.registry.registerAll(
            BlockCrop().apply {
                setRegistryName(MODID, "mung_bean")
                mungBeanBlock = this
            },
            BlockCrop().apply {
                setRegistryName(MODID, "soybean")
                soybeanBlock = this
            },
            BlockCrop().apply {
                setRegistryName(MODID, "adzuki_bean")
                adzukiBeanBlock = this
            },
            BlockBeverageMaking().setRegistryName(MODID, "beverage_making").setTranslationKey("$MODID.beverage_making").itemBlock(),
            BlockBrewBarrel().also { it.setHarvestLevel("axe", 0) }.setRegistryName(MODID, "brew_barrel").setTranslationKey("$MODID.brew_barrel").itemBlock(),
            BlockChoppingBoard().also { it.setHarvestLevel("axe", 0) }.setRegistryName(MODID, "chopping_board").setTranslationKey("$MODID.chopping_board").itemBlock(),
            BlockFryingPan().setRegistryName(MODID, "frying_pan").setTranslationKey("$MODID.frying_pan").itemBlock(),
            BlockMill().setRegistryName(MODID, "mill").setTranslationKey("$MODID.mill").itemBlock(),
            BlockPan().setRegistryName(MODID, "pan").setTranslationKey("$MODID.pan").itemBlock(),
            BlockPot().setRegistryName(MODID, "pot").setTranslationKey("$MODID.pot").itemBlock(),
            BlockPressureCooker().setRegistryName(MODID, "pressure_cooker").setTranslationKey("$MODID.pressure_cooker").itemBlock(),
            BlockStove().setRegistryName(MODID, "stove").setTranslationKey("$MODID.stove").itemBlock(),
            Block(Material.ROCK).also { it.setHarvestLevel("pickaxe", 1) }.setHardness(4.0f).setRegistryName(MODID, "machine_casing").setTranslationKey("$MODID.machine_casing").itemBlock(),
            BlockFluidClassic(FluidMilk, Material.WATER).setRegistryName(MODID, "milk").setTranslationKey("$MODID.milk"),
            BlockFluidClassic(FluidCookingOil, Material.WATER).setRegistryName(MODID, "cooking_oil").setTranslationKey("$MODID.cooking_oil")
        )
    }

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        itemQueue.forEach { (item, oreDict) -> event.registry.register(item); oreDict.forEach { registerOre(it, item) }}

        val gson = GsonBuilder().setPrettyPrinting().create()
        fun item(name: String, vararg oreDictNames: String): Item {
            val item = Item()
            item.setRegistryName(MODID, name)
            item.translationKey = "$MODID.$name"
            item.creativeTab = FcTabIngredient
            event.registry.register(item)
            oreDictNames.asSequence().forEach { registerOre(it, item) }
            INGREDIENTS += item
            return item
        }

        val glassCup = item("glass_cup")

        fun staple(name: String, healAmount: Int, saturation: Float = 3f, vararg oreDictNames: String) {
            food(event.registry) {
                setRegistryName(MODID, name)
                translationKey = "$MODID.$name"
                staple()
                this.healAmount = healAmount
                this.saturation = saturation
            }.apply {
                oreDictNames.asSequence().forEach { registerOre(it, this) }
                STAPLES += this
            }
        }
        fun crop(name: String, blockCrop: BlockCrop, vararg oreDictNames: String) {
            val crop = ItemCrop(blockCrop)
            blockCrop.cropItem = crop
            crop.healAmount = 2
            crop.setRegistryName(MODID, name)
            crop.translationKey = "$MODID.$name"
            crop.creativeTab = FcTabPlant
            event.registry.register(crop)
            oreDictNames.asSequence().forEach { registerOre(it, crop) }
            CROPS += crop
        }
        fun snack(name: String, healAmount: Int, vararg oreDictNames: String): FCRItemFood {
            return food(event.registry, name) {
                snack()
                this.healAmount = healAmount
            }.apply {
                oreDictNames.asSequence().forEach { registerOre(it, this) }
                SNACKS += this
            }
        }
        fun drink(name: String, healAmount: Int, color: Color, effect: Int? = null, vararg oreDictNames: String): ItemDrink {
            val drink = ItemDrink(color)
            drink.healAmount = healAmount
            drink.saturation = 3f
            drink.setAlwaysEdible()
            drink.setRegistryName(MODID, name)
            drink.translationKey = "$MODID.$name"
            drink.containerItem = glassCup
            drink.creativeTab = FcTabDrink
            if (effect != null) {
                when (effect) {
                    0 -> drink.potions = lazy { listOf(PotionEffect(INSTANT_HEALTH, 9600, 4), PotionEffect(FIRE_RESISTANCE, 9600, 4), PotionEffect(STRENGTH, 9600, 4)) }
                    1 -> drink.potions = lazy { listOf(PotionEffect(RESISTANCE, 9600, 4), PotionEffect(REGENERATION, 9600, 4)) }
                }
            }
            event.registry.register(drink)
            oreDictNames.asSequence().forEach { registerOre(it, drink) }
            DRINKS += drink
            return drink
        }
        fun iceCream(name: String, color: Color, vararg oreDictNames: String): ItemIcecream {
            val icecream = ItemIcecream(color)
            icecream.healAmount = 9
            icecream.saturation = 3f
            icecream.setRegistryName(MODID, name)
            icecream.translationKey = "$MODID.$name"
            icecream.creativeTab = FcTabDrink
            event.registry.register(icecream)
            registerOre("listAllicecream", icecream)
            oreDictNames.asSequence().forEach { registerOre(it, icecream) }
            ICECREAMS += icecream
            return icecream
        }
        fun jam(name: String, color: Color, vararg oreDictNames: String): ItemJam {
            val jam = ItemJam(color)
            jam.healAmount = 9
            jam.saturation = 3f
            jam.setRegistryName(MODID, name)
            jam.translationKey = "$MODID.$name"
            jam.creativeTab = FcTabDrink
            event.registry.register(jam)
            registerOre("listAlljam", jam)
            oreDictNames.asSequence().forEach { registerOre(it, jam) }
            JAMS += jam
            return jam
        }
        fun liqueur(name: String, color: Color, vararg oreDictNames: String): ItemLiqueur {
            val drink = ItemLiqueur(color)
            drink.healAmount = 8
            drink.saturation = 3f
            drink.setAlwaysEdible()
            drink.setRegistryName(MODID, name)
            drink.translationKey = "$MODID.$name"
            drink.creativeTab = FcTabDrink
            event.registry.register(drink)
            oreDictNames.asSequence().forEach { registerOre(it, drink) }
            LIQUEURS += drink
            return drink
        }

        // Kitchen Knifes
        val knifes = listOf("wood" to Color(0x966f33),
            "stone" to Color(0x888c8d),
            "gold" to Color(0xCFB53B),
            "iron" to Color(0xffffff),
            "diamond" to Color(0xb9f2ff),
            "emerald" to Color(0x50c878))
        knifes.forEachIndexed { index, (name, color) ->
            val knife = ItemKitchenKnife((2.0).pow(index.toDouble() + 6.0).toInt(), color).apply {
                setRegistryName(MODID, "${name}_kitchen_knife")
                translationKey = "$MODID.${name}_kitchen_knife"
            }
            KITCHEN_KNIFES += knife
            event.registry.register(knife)
            registerOre("kitchenKnife", ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE))
        }
        // Tools
        event.registry.register(ItemWrench().setRegistryName(MODID, "wrench").setTranslationKey("$MODID.wrench").setCreativeTab(FcTabMachine).also { DEFAULT_MODEL_ITEMS += it })

        // Plants
        crop("mung_bean", mungBeanBlock, "foodBean", "cropBean", "seedBean")
        crop("soybean", soybeanBlock, "foodSoybean", "cropSoybean", "seedSoybean")
        crop("adzuki_bean", adzukiBeanBlock, "cropRedbean", "cropAdzukibean")

        // Ingredients
        item("chocolate_dust", "dustChocolate")
        item("iron_plate", "plateIron")
        item("purified_water", "listAllwater")
        item("milled_rice", "flourEqualswheat", "foodMilledrice")
        item("flour", "foodFlour")
        item("peanut_oil", "foodPeanutoil", "listAlloil")
        item("circuit", "circuitBasic")
        item("dough", "foodDough")
        item("dumpling_meat", "listAllmeatraw", "foodDumplingmeat")
        item("salt", "dustSalt", "itemSalt")
        item("soy_sauce", "foodSoysauce")
        item("vinegar", "foodVinegar")
        item("potato_sliced", "slicePotato")
        item("potato_shredded", "shredPotato")
        item("pixian_watercress")
        item("starches")
        item("zongye")
        item("curry", "foodCurry")
        item("sticky_rice_flour", "foodFloursticky", "foodFlour")
        item("sticky_rice_dough", "foodDough", "foodStickyricedough")
        item("peanut_tangyuan_stuffing", "listAlltangyuanstuffing")
        item("chicken_large", "listAllchickenraw", "listAllmeatraw")
        item("chicken_medium", "listAllmeatraw")
        item("chicken_small", "listAllmeatraw")
        item("tofu_shredded", "shredTofu")
        item("carrot_shredded", "shredCarrot")
        item("dough_shredded", "shredDough")
        item("lawei_flavor", "flavorLawei")
        item("white_radish_shredded", "shredWhiteradish")
        item("red_bean_paste", "foodPasteredbean", "listAlltangyuanstuffing")
        item("jinkela")
        item("overcooked_food", "dustAshes")

        // Crops

        // Staples
        staple("rice_porridge", 7)
        staple("egg_custard", 8)
        staple("mushroom_egg_soup", 7)
        staple("mushroom_chicken_soup", 9)
        staple("noodles", 7)
        staple("crossing_bridge_noodles", 7)
        staple("pasta", 7, oreDictNames = *arrayOf("foodPasta"))
        staple("preserved_egg_porridge", 7)
        staple("changfen", 7)
        staple("laba_porridge", 9)

        staple("tomato_fried_egg_rice", 18)
        staple("disanxian_rice", 18)
        staple("shredded_pork_rice", 18)
        staple("kung_pao_chicken_rice", 18)
        staple("fried_shredded_potato_rice", 18)
        staple("fish_head_rice", 18)
        staple("mapo_tofu_rice", 16)
        staple("pork_braised_in_brown_sauce_rice", 18)
        staple("twice_cooked_pork_slices_rice", 18)
        staple("orlean_chicken_wings_rice", 20)
        staple("sliced_cold_chicken", 18)
        staple("scallion_chicken", 18)
        staple("chicken_with_chili_sauce", 18)
        staple("peppery_chicken", 18)
        staple("steamed_fish", 18)
        staple("spicy_fish", 18)
        staple("boiled_fish_with_pickled_cabbage", 18)
        staple("coke_chicken_rice", 20)
        staple("curry_rice", 18)
        staple("boiled_beef", 18)
        staple("pork_fried_pasta", 18)
        staple("beef_fried_pasta", 18)
        staple("chicken_fried_pasta", 18)
        staple("japanese_tofu_rice", 18)


        // Snacks
        // 金葡萄
        snack("golden_grape", 20)
        // 米饭
        snack("cooked_rice", 3)
        // 鸡腿
        snack("chicken_leg", 3)
        // 鸡翅
        snack("chicken_wing", 4)
        // 煎鸡蛋
        snack("poached_egg", 5)
        // 烙饼
        snack("pancakes", 5, "foodPancake")
        // 饺子
        snack("dumpling", 8)
        // 煎饺
        snack("fried_dumpling", 10)
        // 巧克力
        snack("chocolate_bar", 4, "foodChocolatebar")
        // 豆腐
        snack("tofu", 4, "foodSilkentofu")
        // 豆腐干
        snack("dehydrated_tofu", 6, "foodFirmtofu")
        // 炒土豆片
        snack("fried_potato_chips", 7, "foodPotatochipscooked")
        // 粽子
        snack("zongzi", 8)
        // 汤圆
        snack("tangyuan", 8)
        // 炸鸡块
        snack("fried_chicken", 6, "foodFriedchicken")
        // 薯条
        snack("french_fries", 7, "foodFries")
        // 奥尔良鸡翅
        snack("orlean_chicken_wings", 8, "listAllchickencooked")
        // 鸡米花
        snack("popcorn_chicken", 4, "listAllchickencooked")
        // 原味鸡块
        snack("original_chicken", 8, "listAllchickencooked")
        // 麻花
        snack("dough_twist", 7)
        // 年糕
        snack("niangao", 8)
        // 春卷
        snack("chunjuan", 8)
        // 炸豆腐
        snack("fried_tofu", 6, "foodFirmtofu")
        // 炸年糕
        snack("fried_niangao", 10)
        // 炸土豆片
        snack("potato_chips", 7, "foodPotatochips")
        // 炸面包
        snack("fried_bread", 7, "foodBread")
        // 炸春卷
        snack("fried_chunjuan", 10)
        // 炸麻花
        snack("fried_dough_twist", 10)
        // 炸鸡腿
        snack("fried_chicken_leg", 5, "listAllchickencooked")
        // 月饼
        snack("mooncake", 8)
        // 馒头
        snack("steamed_buns", 4)
        // 酸菜饼
        snack("sauerkraut_cake", 7)
        // 油条
        snack("youtiao", 8)
        // 辣条
        snack("chili_tofu_strip", 4)
        // 核桃酥
        snack("walnut_shortbread", 8)
        // 艾糍
        snack("aici", 8, "foodAici")
        // 糍粑
        snack("ciba", 7, "foodCiba")
        // 香肠
        snack("sausage", 6, "foodSausage", "listAllmeatraw")
        // 热狗
        snack("hot_dog", 8)
        // 腊肠
        snack("bolongna", 8, "listAllmeatcooked")
        // 腊肉
        snack("bacon", 9, "listAllmeatcooked")
        // 烤香肠
        snack("fried_sausage", 8, "foodSausage", "listAllmeatcooked", "foodSausagefried")
        // 奶酪
        snack("cheese", 5, "foodCheese")
        // 披萨
        snack("pizza", 12, "foodPizza")
        // 汉堡
        snack("hamburger", 12)
        // 鱿鱼肉
        snack("squid_meat", 4, "listAllmeatraw", "foodCalamariraw")
        // 烤鱿鱼肉
        snack("cooked_squid_meat", 6, "listAllmeatcooked", "foodCalamaricooked")
        // 鱿鱼丝
        snack("squid_slice", 1)
        // 烤红薯
        snack("cooked_sweet_potato", 8)
        // 烤玉米
        snack("cooked_corn", 6)
        // 爆米花
        snack("popcorn", 6)
        // 曲奇饼
        snack("cookie", 6, "listAllcookie")
        // 笑脸曲奇饼
        snack("smiley_cookie", 7, "listAllcookie").apply {
            potions = lazy { listOf(PotionEffect(JUMP_BOOST, 1200, 1), PotionEffect(SPEED, 1200, 1), PotionEffect(HASTE, 1200, 1)) }
        }

        val drinkColorables = arrayOf<Colorable>(TOMATO, CUCUMBER, WHITE_RADISH, CABBAGE, GRAPE, LITCHI, PEACH, ORANGE, LOQUAT, MANGO, LEMON, GRAPEFRUIT, PERSIMMON, PAPAYA, HAWTHORN, POMEGRANATE, DATE, STRAWBERRY, COCONUT, BANANA)
        // Drinks
        drink("soymilk",4, Color(16777215), -1, "foodSoymilk", "listAllmilk")
        drink("hot_chocolate", 7, Color(0x956844), -1, "foodHotchocolate")
        drink("chocolate_milk", 7, Color(0xae927c), -1, "foodChocolatemilk", "listAllmilk")
        drink("carrot_juice", 7, Color(0xf4872f), -1, "listAlljuice", "foodCarrotjuice")
        drink("apple_juice", 7, Color(0xfa2059), -1, "listAlljuice", "foodApplejuice")
        drink("golden_grape_juice", 20, Color( 0xe0da42), 0, "listAlljuice", "foodGrapejuice", "foodGoldengrapejuice")
        drink("golden_apple_juice", 20, Color( 0xe7e480), 1, "listAlljuice", "foodGoldenapplejuice", "foodApplejuice")
        drink("soybean_milk", 7, Color(0xfafaf2), -1, "foodSoymilk", "listAllmilk")
        drink("cola", 7, Color(0x512008), -1, "foodCola")
        drink("sprite", 7, Color(0xededed), -1, "foodSprite")
        drink("melon_juice", 7, Color(0xe848b9), -1, "foodMelonjuice", "listAlljuice")
        drink("tea", 6, Color(0x98a285), -1, "foodTea")
        drink("milk_tea", 7, Color(0xc29833), -1, "foodMilktea")
        drink("coffee", 7, Color(0x50462d), -1, "foodCoffee")
        drink("sugar_with_water", 2, Color(16777215), -1)
        drinkColorables.forEach {
            drink("${it}_juice", 7, it.color, -1, "listAlljuice", "food${it.toString().capitalize()}juice").apply {
                realTranslationKey = "item.$MODID.$it.name"
                hasSuffix = true
                baseTranslationKey = "item.$MODID.juice.name"
            }
        }
        drink("coconut_juice_milk", 8, Color(0xfefdf4))

        // Ice-creams
        food(event.registry, "original_ice_cream", 5, 3f) { creativeTab = FcTabDrink; DEFAULT_MODEL_ITEMS += this }

        val iceCreamColorables = arrayOf<Colorable>(LITCHI, PEACH, PEAR, ORANGE, MANGO, LEMON, PAPAYA, PERSIMMON, BANANA, COCONUT, STRAWBERRY)
        iceCreamColorables.forEach {
            iceCream("${it}_ice_cream", it.color, "food${it.toString().capitalize()}icecream").apply {
                realTranslationKey = "item.$MODID.$it.name"
                hasSuffix = true
                baseTranslationKey = "item.$MODID.ice_cream.name"
            }
            jam("${it}_jam", it.color, "food${it.toString().capitalize()}jam").apply {
                realTranslationKey = "item.$MODID.$it.name"
                hasSuffix = true
                baseTranslationKey = "item.$MODID.jam.name"
            }
        }

        iceCream("carrot_ice_cream", Color(0xf4872f), "foodCarroticecream", "listAllicecream").apply {
            realTranslationKey = "item.carrots.name"
            hasSuffix = true
            baseTranslationKey = "item.$MODID.ice_cream.name"
        }
        iceCream("apple_ice_cream", Color(0xfa2059), "foodAppleicecream", "listAllicecream").apply {
            realTranslationKey = "item.apple.name"
            hasSuffix = true
            baseTranslationKey = "item.$MODID.ice_cream.name"
        }
        iceCream("melon_ice_cream", Color(0xe848b9), "foodMelonicecream", "listAllicecream").apply {
            realTranslationKey = "tile.melon.name"
            hasSuffix = true
            baseTranslationKey = "item.$MODID.ice_cream.name"
        }

        liqueur("wine", Color(0x722f37))
        liqueur("spirit", Color(0xffffff))
        val liqueurColorables = arrayOf(GRAPE, LITCHI, PEACH, MANGO, LEMON, POMEGRANATE)
        liqueurColorables.forEach {
            liqueur("${it}_liqueur", it.color, "food${it.toString().capitalize()}liqueur").apply {
                realTranslationKey = "item.$MODID.$it.name"
                hasSuffix = true
                baseTranslationKey = "item.$MODID.liqueur.name"
            }
        }
        liqueur("apple_liqueur", Color(0xFA853D))
        liqueur("golden_grape_liqueur", Color(0xe0da42)).apply {
            potions = lazy { listOf(PotionEffect(JUMP_BOOST, 9600, 4), PotionEffect(SPEED, 9600, 4), PotionEffect(HASTE, 9600, 4), PotionEffect(INSTANT_HEALTH, 9600, 4), PotionEffect(FIRE_RESISTANCE, 9600, 4), PotionEffect(STRENGTH, 9600, 4)) }
        }
        liqueur("golden_apple_liqueur", Color(0xe7e480)).apply {
            potions = lazy { listOf(PotionEffect(NIGHT_VISION, 9600, 4), PotionEffect(INVISIBILITY, 9600, 4), PotionEffect(WATER_BREATHING, 9600, 4), PotionEffect(RESISTANCE, 9600, 4), PotionEffect(REGENERATION, 9600, 4)) }
        }


        DEFAULT_MODEL_ITEMS.addAll(INGREDIENTS + CROPS + SNACKS + STAPLES)
        registerSeeds()
    }

    fun registerSeeds() {
        // TODO 1.13+ Loot Tables
        CROPS.forEach { MinecraftForge.addGrassSeed(ItemStack(it), 3) }
    }
}
