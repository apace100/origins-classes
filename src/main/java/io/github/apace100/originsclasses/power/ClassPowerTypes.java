package io.github.apace100.originsclasses.power;

import io.github.apace100.origins.power.*;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.originsclasses.OriginsClasses;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class ClassPowerTypes {

    // Rogue
    public static final PowerType<Power> SNEAKY = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "sneaky"));
    public static final PowerType<VariableIntPower> STEALTH = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "stealth"));//new PowerType<>((type, player) -> new VariableIntPower(type, player, 0, 0, 200));

    // Warrior
    public static final PowerType<Power> LESS_SHIELD_SLOWDOWN = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "less_shield_slowdown"));
    /*public static final PowerType<AttributePower> MORE_ATTACK_DAMAGE = new PowerType<>((type, player) -> {
        return new AttributePower(type, player, EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Warrior attack bonus", 1.0, EntityAttributeModifier.Operation.ADDITION));
    });*/

    // Ranger
    public static final PowerType<Power> LESS_BOW_SLOWDOWN = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "less_bow_slowdown"));
    public static final PowerType<Power> NO_PROJECTILE_DIVERGENCE = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "no_projectile_divergence"));

    // Beastmaster
    public static final PowerType<Power> TAMED_ANIMAL_BOOST = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "tamed_animal_boost"));
    public static final PowerType<Power> TAMED_POTION_DIFFUSAL = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "tamed_potion_diffusal"));

    // Cook
    public static final PowerType<Power> MORE_SMOKER_XP = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "more_smoker_xp"));
    public static final PowerType<Power> BETTER_CRAFTED_FOOD = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "better_crafted_food"));

    // Cleric
    public static final PowerType<Power> LONGER_POTIONS = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "longer_potions"));
    public static final PowerType<Power> BETTER_ENCHANTING = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "better_enchanting"));

    // Blacksmith
    public static final PowerType<Power> QUALITY_EQUIPMENT = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "quality_equipment"));
    public static final PowerType<Power> EFFICIENT_REPAIRS = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "efficient_repairs"));

    // Farmer
    public static final PowerType<Power> MORE_CROP_DROPS = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "more_crop_drops"));
    public static final PowerType<Power> BETTER_BONE_MEAL = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "better_bone_meal"));

    // Rancher
    public static final PowerType<Power> TWIN_BREEDING = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "twin_breeding"));
    public static final PowerType<Power> MORE_ANIMAL_LOOT = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "more_animal_loot"));

    // Merchant
    public static final PowerType<Power> TRADE_AVAILABILITY = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "trade_availability"));
    public static final PowerType<Power> RARE_WANDERING_LOOT = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "rare_wandering_loot"));

    // Miner
    /*public static final PowerType<MultiMinePower> ORE_VEIN_MINING = new PowerType<>((type, player) -> new MultiMinePower(type, player, (pl, bs, bp) -> {
        List<BlockPos> affected = new LinkedList<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(bp);
        while(!queue.isEmpty()) {
            BlockPos pos = queue.remove();
            for(Direction d : Direction.values()) {
                BlockPos newPos = pos.offset(d);
                if(pl.world.getBlockState(newPos).isOf(bs.getBlock()) && !affected.contains(newPos)) {
                    affected.add(newPos);
                    queue.add(newPos);
                    if(affected.size() >= 31) {
                        return affected;
                    }
                }
            }
        }
        return affected;
    }, state -> state.getBlock() instanceof OreBlock));*/
    //public static final PowerType<Power> MORE_STONE_BREAK_SPEED = new PowerType<>(Power::new);
    public static final PowerType<Power> NO_MINING_EXHAUSTION = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "no_mining_exhaustion"));

    //public static final PowerType<StartingEquipmentPower> EXPLORER_KIT = new PowerType<>((type, player) -> new StartingEquipmentPower(type, player).addStack(new ItemStack(Items.COMPASS)).addStack(new ItemStack(Items.CLOCK)).addStack(new ItemStack(Items.MAP, 9)));
    public static final PowerType<Power> NO_SPRINT_EXHAUSTION = new PowerTypeReference<>(new Identifier(OriginsClasses.MODID, "no_sprint_exhaustion"));

    // Lumberjack
    /*public static final PowerType<MultiMinePower> TREE_FELLING = new PowerType<>((type, player) -> (MultiMinePower)new MultiMinePower(type, player, (pl, bs, bp) -> {
        Set<BlockPos> affected = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(bp);
        boolean foundOneWithLeaves = false;
        BlockPos.Mutable pos = bp.mutableCopy();
        BlockPos.Mutable newPos = bp.mutableCopy();
        while(!queue.isEmpty()) {
            pos.set(queue.remove());
            for(int dx = -1; dx <= 1; dx++) {
                for(int dy = 0; dy <= 1; dy++) {
                    for(int dz = -1; dz <= 1; dz++) {
                        if(dx == 0 & dy == 0 && dz == 0) {
                            continue;
                        }
                        newPos.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                        BlockState state = pl.world.getBlockState(newPos);
                        if(state.isOf(bs.getBlock()) && !affected.contains(newPos)) {
                            BlockPos savedNewPos = newPos.toImmutable();
                            affected.add(savedNewPos);
                            queue.add(savedNewPos);
                            if(affected.size() > 127) {
                                if(!foundOneWithLeaves) {
                                    return new ArrayList<>();
                                }
                                return new ArrayList<>(affected);
                            }
                        } else
                        if(state.getBlock() instanceof LeavesBlock && !state.get(LeavesBlock.PERSISTENT)) {
                            foundOneWithLeaves = true;
                        }
                    }
                }
            }
        }
        if(!foundOneWithLeaves) {
            affected.clear();
        }
        return new ArrayList<>(affected);
    }, state -> state.getBlock().isIn(BlockTags.LOGS)).addCondition(p -> p.getMainHandStack().getItem() instanceof AxeItem));
    public static final PowerType<Power> MORE_PLANKS_FROM_LOGS = new PowerType<>(Power::new);*/
/*
    public static void register() {
        register("sneaky", SNEAKY);
        register("stealth", STEALTH);
        register("stealth_descriptor", STEALTH_DESCRIPTOR);

        register("less_shield_slowdown", LESS_SHIELD_SLOWDOWN);
        register("more_attack_damage", MORE_ATTACK_DAMAGE);

        register("less_bow_slowdown", LESS_BOW_SLOWDOWN);
        register("no_projectile_divergence", NO_PROJECTILE_DIVERGENCE);

        register("tamed_animal_boost", TAMED_ANIMAL_BOOST);
        register("tamed_potion_diffusal", TAMED_POTION_DIFFUSAL);

        register("more_smoker_xp", MORE_SMOKER_XP);
        register("better_crafted_food", BETTER_CRAFTED_FOOD);

        register("longer_potions", LONGER_POTIONS);
        register("better_enchanting", BETTER_ENCHANTING);

        register("quality_equipment", QUALITY_EQUIPMENT);
        register("efficient_repairs", EFFICIENT_REPAIRS);

        register("more_crop_drops", MORE_CROP_DROPS);
        register("better_bone_meal", BETTER_BONE_MEAL);

        register("twin_breeding", TWIN_BREEDING);
        register("more_animal_loot", MORE_ANIMAL_LOOT);

        register("trade_availability", TRADE_AVAILABILITY);
        register("rare_wandering_loot", RARE_WANDERING_LOOT);

        register("ore_vein_mining", ORE_VEIN_MINING);
        register("more_stone_break_speed", MORE_STONE_BREAK_SPEED);
        register("no_mining_exhaustion", NO_MINING_EXHAUSTION);

        register("tree_felling", TREE_FELLING);
        register("more_planks_from_logs", MORE_PLANKS_FROM_LOGS);

        register("explorer_kit", EXPLORER_KIT);
        register("no_sprint_exhaustion", NO_SPRINT_EXHAUSTION);
    }

    private static void register(String path, PowerType<?> powerType) {
        Registry.register(ModRegistries.POWER_TYPE, new Identifier(OriginsClasses.MODID, path), powerType);
    }
*/
}
