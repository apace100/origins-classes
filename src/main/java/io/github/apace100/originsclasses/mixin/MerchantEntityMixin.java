package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MerchantEntity.class)
public class MerchantEntityMixin {

    @Shadow
    protected TradeOfferList offers;
    @Shadow
    private PlayerEntity customer;
    private int offerCountWithoutAdditional;
    private TradeOfferList additionalOffers;


    @Redirect(method = "trade", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/TradeOffer;use()V"))
    private void dontUseUpTrades(TradeOffer tradeOffer) {
        int rand_trade = random.nextInt(4);
        if(((Object)this instanceof WanderingTraderEntity) || !ClassPowerTypes.TRADE_AVAILABILITY.isActive(this.customer)) {
            tradeOffer.use();
        } else {
            switch(rand_trade) {
                case 0:
                tradeOffer.use();
                    break;
                default:
                    break;
            }
        }
    }

    @Inject(method = "setCurrentCustomer", at = @At("HEAD"))
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

    @Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
    private void writeAdditionalOffersToTag(CompoundTag tag, CallbackInfo ci) {
        if(additionalOffers != null) {
            tag.put("AdditionalOffers", additionalOffers.toTag());
            tag.putInt("OfferCountNoAdditional", offerCountWithoutAdditional);
        }
    }

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    private void readAdditionalOffersFromTag(CompoundTag tag, CallbackInfo ci) {
        if(tag.contains("AdditionalOffers")) {
            additionalOffers = new TradeOfferList(tag.getCompound("AdditionalOffers"));
            offerCountWithoutAdditional = tag.getInt("OfferCountNoAdditional");
        }
    }

    private static TradeOfferList buildAdditionalOffers() {
        TradeOfferList list = new TradeOfferList();
        Random random = new Random();
        int r = random.nextInt(9);
        switch(r) {
            case 0:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 33), new ItemStack(Items.DIAMOND_SWORD), 2, 5, 0.05F));
                break;
            case 1:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 21), new ItemStack(Items.DIAMOND, 3), 1, 5, 0.05F));
                break;
            case 2:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.EXPERIENCE_BOTTLE, 12), 1, 5, 0.05F));
                break;
            case 3:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 14), new ItemStack(Items.GOLDEN_APPLE), 2, 5, 0.05F));
                break;
            case 4:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.SPONGE), 3, 2, 0.05F));
                break;
            case 5:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.SPORE_BLOSSOM), 10, 5, 0.05F));
                break;
            case 6:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 16), new ItemStack(Items.BIG_DRIPLEAF), 1, 5, 0.05F));
                break;
            case 7:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 16), new ItemStack(Items.SMALL_DRIPLEAF), 1, 5, 0.05F));
                break;
            case 8:
                list.add(new TradeOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ItemTags.FLOWERS.getRandom(random)), 64, 5, 0.05F));
                break;
        }
        return list;
    }
}
