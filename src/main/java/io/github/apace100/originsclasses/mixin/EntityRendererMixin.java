package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @ModifyVariable(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"), ordinal = 0)
    private boolean modifyUnsneakyState(boolean original, Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if(ClassPowerTypes.SNEAKY.isActive(entity)) {
            return false;
        }
        return original;
    }
}
