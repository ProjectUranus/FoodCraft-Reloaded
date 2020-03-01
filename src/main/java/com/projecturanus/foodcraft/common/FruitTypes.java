package com.projecturanus.foodcraft.common;

import com.projecturanus.foodcraft.common.util.Colorable;
import net.minecraft.potion.PotionEffect;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public enum FruitTypes implements Colorable {
    /**
     * Grape
     * 葡萄
     */
    GRAPE(new Color(0x6f2da8)),
    /**
     * Pear
     * 梨
     */
    PEAR(new Color(0xe5db3b)),

    /**
     * Litchi
     * 荔枝
     */
    LITCHI(new Color(0xf6edd0)),

    /**
     * 龙眼
     * Longan
     */
    LONGAN(new Color(0xF6F3C9)),

    /**
     * Peach
     * 桃
     */
    PEACH(new Color(0xffafaf)),

    /**
     * Orange
     * 橘子
     */
    ORANGE(new Color(0xf6ae24)),

    /**
     * Mango
     * 芒果
     */
    MANGO(new Color(0xffd986)),

    /**
     * Lemon
     * 柠檬
     */
    LEMON(new Color(0xfcf393)),

    /**
     * Grapefruit
     * 柚子
     */
    GRAPEFRUIT(new Color(0xece382)),

    /**
     * Persimmon
     * 柿子
     */
    PERSIMMON(new Color(0xeb8c30)),

    /**
     * Papaya
     * 木瓜
     */
    PAPAYA(new Color(0xf18a25)),

    /**
     * 枇杷
     * Loquat
     */
    LOQUAT(new Color(0xCE7031)),

    /**
     * Hawthorn
     * 山楂
     */
    HAWTHORN(new Color(0xea7b0e)),

    /**
     * Pomegranate
     * 石榴
     */
    POMEGRANATE(new Color(0xf46c30)),

    /**
     * Date
     * 红枣
     */
    DATE(new Color(0xb57c63)),

    /**
     * Cherry
     * 樱桃
     */
    CHERRY(new Color(0xfd6d0d)),

    /**
     * Coconut
     * 椰子
     */
    COCONUT(new Color(0xfcf4d6)),

    /**
     * Strawberry
     * 草莓
     */
    STRAWBERRY(new Color(0xfc5a8d)),

    /**
     * Banana
     * 香蕉
     */
    BANANA(new Color(0xf7eb6a));

    private Color color;
    private List<PotionEffect> effects;

    FruitTypes(Color color) {
        this.color = color;
    }

    FruitTypes(Color color, PotionEffect... effects) {
        this(color);
        this.effects = Arrays.asList(effects);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }
}
