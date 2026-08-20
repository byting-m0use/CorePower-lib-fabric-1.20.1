package net.bytem0use.mixin.player;

import net.bytem0use.common.utils.PlayerFlightInterface;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin({PlayerEntityModel.class})
public abstract class PlayerFlyingModelMixin<T extends LivingEntity> extends BipedEntityModel<T> {

    public PlayerFlyingModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(
            method = {"setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V"},
            at = {@At("TAIL")}
    )
    private void onSetAngles(T player, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {

    }
}
