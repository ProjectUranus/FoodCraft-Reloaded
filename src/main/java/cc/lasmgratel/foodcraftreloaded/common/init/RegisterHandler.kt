package cc.lasmgratel.foodcraftreloaded.common.init

import cc.lasmgratel.foodcraftreloaded.MODID
import cc.lasmgratel.foodcraftreloaded.common.item.FCRItemFood
import cc.lasmgratel.foodcraftreloaded.common.item.ItemCrop
import cc.lasmgratel.foodcraftreloaded.common.item.food
import com.google.gson.GsonBuilder
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.oredict.OreDictionary

val INGREDIENTS = arrayListOf<Item>()
val CROPS = arrayListOf<ItemCrop>()
val SNACKS = arrayListOf<FCRItemFood>()

@Mod.EventBusSubscriber(modid = MODID)
object RegisterHandler {
    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {

    }

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        fun item(name: String, vararg oreDictNames: String) {
            val item = Item()
            item.setRegistryName(MODID, name)
            item.translationKey = "$MODID.$name"
            item.creativeTab = FcTabIngredient
            event.registry.register(item)
            oreDictNames.asSequence().forEach { OreDictionary.registerOre(it, item) }
            INGREDIENTS += item
            /*
                            FileUtils.write(Paths.get("C:\\Users\\nanam\\IdeaProjects\\FoodCraft-Reloaded\\src\\main\\resources\\assets\\foodcraftreloaded\\models\\item", "$name.json").toFile(),
                gson.toJson(JsonObject().apply { addProperty("parent", "item/generated"); add("textures", JsonObject().apply { addProperty("layer0", "$MODID:items/$name") }) }), "UTF-8")
             */
        }
        fun crop(name: String, vararg oreDictNames: String) {
            val crop = ItemCrop()
            crop.setRegistryName(MODID, name)
            crop.translationKey = "$MODID.$name"
            event.registry.register(crop)
            oreDictNames.asSequence().forEach { OreDictionary.registerOre(it, crop) }
            CROPS += crop
        }
        fun snack(name: String, saturation: Float, vararg oreDictNames: String) {
            food(event.registry) {
                setRegistryName(MODID, name)
                translationKey = "$MODID.$name"
                snack()
                this.saturation = saturation
            }.apply {
                oreDictNames.asSequence().forEach { OreDictionary.registerOre(it, this) }
                SNACKS += this
            }
        }
        // Ingredients
        item("iron_plate", "plateIron")
        item("purified_water", "listAllwater")
        item("milled_rice", "flourEqualswheat")
        item("flour", "foodFlour")
        item("peanut_oil", "foodPeanutoil")
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
        item("sticky_rice_flour")
        item("sticky_rice_dough", "foodDough", "foodStickyricedough")
        item("peanut_tangyuan_stuffing")
        item("chicken_large", "listAllchickenraw", "listAllmeatraw")
        item("chicken_medium", "listAllchickenraw", "listAllmeatraw")
        item("chicken_small", "listAllchickenraw", "listAllmeatraw")
        item("tofu_shredded", "shredTofu")
        item("carrot_shredded", "shredCarrot")
        item("dough_shredded", "shredDough")
        item("lawei_flavor", "flavorLawei")
        item("white_radish_shredded", "shredWhiteradish")
        item("red_bean_paste", "foodPasteredbean")
        item("jinkela")
        item("overcooked_food", "dustAshes")

        // Crops


        // Snacks
        snack("cooked_rice", 3f)
        // 鸡腿
        snack("chicken_leg", 3f)
        // 鸡翅
        snack("chicken_wing", 4f)
        // 煎鸡蛋
        snack("poached_egg", 5f)
        // 烙饼
        snack("pancakes", 5f, "foodPancake")
        // 饺子
        snack("dumpling", 8f)
        // 煎饺
        snack("fried_dumpling", 10f)
        // 巧克力
        snack("chocolate_bar", 4f, "foodChocolatebar")
        // 豆腐
        snack("tofu", 4f, "foodSilkentofu")
        // 豆腐干
        snack("dehydrated_tofu", 6f, "foodFirmtofu")
        // 炒土豆片
        snack("fried_potato_chips", 7f, "foodPotatochips")
        // 粽子
        snack("zongzi", 8f)
        // 汤圆
        snack("tangyuan", 8f)
        // 炸鸡块
        snack("fried_chicken", 6f, "foodFriedchicken")
        // 薯条
        snack("french_fries", 7f, "foodFries")
        // 奥尔良鸡翅
        snack("orlean_chicken_wings", 8f,"listAllchickencooked")
        // 鸡米花
        snack("popcorn_chicken", 4f, "listAllchickencooked")
        // 原味鸡块
        snack("original_chicken", 8f, "listAllchickencooked")
        // 麻花
        snack("dough_twist", 7f)
        // 年糕
        snack("niangao", 8f)
        // 春卷
        snack("chunjuan", 8f)
        // 炸豆腐
        snack("fried_tofu", 6f, "foodFirmtofu")
        // 炸年糕
        snack("fried_niangao", 10f)
        // 炸土豆片
        snack("potato_chips", 7f, "foodPotatochips")
        // 炸面包
        snack("fried_bread", 7f, "foodBread")
        // 炸春卷
        snack("fried_chunjuan", 10f)
        // 炸麻花
        snack("fried_dough_twist", 10f)
        // 炸鸡腿
        snack("fried_chicken_leg", 5f, "listAllchickencooked")
        // 月饼
        snack("mooncake", 8f)
        // 馒头
        snack("steamed_buns", 4f)
        // 酸菜饼
        snack("sauerkraut_cake", 7f)
        // 油条
        snack("youtiao", 8f)
        // 辣条
        snack("chili_tofu_strip", 4f)
        // 核桃酥
        snack("walnut_shortbread", 8f)
        // 艾糍
        snack("aici", 8f)
        // 糍粑
        snack("ciba", 7f)
        // 香肠
        snack("sausage", 6f)
        // 热狗
        snack("hot_dog", 8f)
        // 腊肠
        snack("bolongna", 8f)
        // 腊肉
        snack("bacon", 9f)
        // 烤香肠
        snack("fried_sausage", 8f, "foodSausage")
        // 奶酪
        snack("cheese", 5f, "foodCheese")
        // 披萨
        snack("pizza", 12f, "foodPizza")
        // 汉堡
        snack("hamburger", 12f)
        // 鱿鱼肉
        snack("squid_meat", 8f, "listAllmeatraw")
        // 烤鱿鱼肉
        snack("cooked_squid_meat", 10f, "listAllmeatcooked")
        // 鱿鱼丝
        snack("squid_slice", 3f)
        // 烤红薯
        snack("cooked_sweet_potato", 8f)
        // 烤玉米
        snack("cooked_corn", 6f)
        // 爆米花
        snack("popcorn", 6f)
        // 曲奇饼
        snack("cookie", 6f, "listAllcookie")
        // 笑脸曲奇饼
        snack("smiley_cookie", 7f, "listAllcookie")
    }
}
