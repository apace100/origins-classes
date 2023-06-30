package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import io.github.apace100.originsclasses.util.ClassesTags;
import io.github.apace100.originsclasses.util.ItemUtil;
import io.github.apace100.originsclasses.util.TagUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.random.Random;
import java.util.Set;

@Mixin(MerchantEntity.class)
public abstract class MerchantEntityMixin extends PassiveEntity {

    @Shadow
    protected TradeOfferList offers;
    @Shadow
    private PlayerEntity customer;

    private int offerCountWithoutAdditional;
    private TradeOfferList additionalOffers;

    protected MerchantEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "trade", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/TradeOffer;use()V"))
    private void dontUseUpTrades(TradeOffer tradeOffer) {
        if(((Object)this instanceof WanderingTraderEntity) || !ClassPowerTypes.TRADE_AVAILABILITY.isActive(this.customer)) {
            tradeOffer.use();
        }
    }

    @Inject(method = "setCustomer", at = @At("HEAD"))
    private void addAdditionalOffers(PlayerEntity customer, CallbackInfo ci) {
        if((Object)this instanceof WanderingTraderEntity) {
            if (ClassPowerTypes.RARE_WANDERING_LOOT.isActive(customer)) {
                if(additionalOffers == null) {
                    offerCountWithoutAdditional = offers.size();
                    additionalOffers = buildAdditionalOffers();
                }
                this.offers.addAll(additionalOffers);
            } else if(additionalOffers != null) {
                while(this.offers.size() > offerCountWithoutAdditional) {
                    this.offers.remove(this.offers.size() - 1);
                }
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeAdditionalOffersToTag(NbtCompound tag, CallbackInfo ci) {
        if(additionalOffers != null) {
            tag.put("AdditionalOffers", additionalOffers.toNbt());
            tag.putInt("OfferCountNoAdditional", offerCountWithoutAdditional);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readAdditionalOffersFromTag(NbtCompound tag, CallbackInfo ci) {
        if(tag.contains("AdditionalOffers")) {
            additionalOffers = new TradeOfferList(tag.getCompound("AdditionalOffers"));
            offerCountWithoutAdditional = tag.getInt("OfferCountNoAdditional");
        }
    }

    private TradeOfferList buildAdditionalOffers() {
        TradeOfferList list = new TradeOfferList();
        Random random = getRandom();
        Set<Item> excludedItems = TagUtil.getAllEntries(Registries.ITEM, ClassesTags.MERCHANT_BLACKLIST);
        list.add(new TradeOffer(
            new ItemStack(Items.EMERALD, random.nextInt(12) + 6),
            ItemUtil.createMerchantItemStack(ItemUtil.getRandomObtainableItem(
                this.getWorld().getServer(),
                random,
                excludedItems), random),
            1,
            5,
            0.05F)
        );
        Item desiredItem = ItemUtil.getRandomObtainableItem(
            this.getWorld().getServer(),
            random,
            excludedItems);
        list.add(new TradeOffer(
            new ItemStack(desiredItem, 1 + random.nextInt(Math.min(16, desiredItem.getMaxCount()))),
            ItemUtil.createMerchantItemStack(ItemUtil.getRandomObtainableItem(
                this.getWorld().getServer(),
                random,
                excludedItems), random),
            1,
            5,
            0.05F)
        );
        return list;
    }
}
