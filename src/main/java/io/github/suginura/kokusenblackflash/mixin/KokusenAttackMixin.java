package io.github.suginura.kokusenblackflash.mixin;

import io.github.suginura.kokusenblackflash.config.KokusenConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;

@Mixin(Player.class)
public class KokusenAttackMixin {

    private boolean isKokusen = false;   // クリティカル判定.
    private Entity kokusenTarget = null; //相手のエンティティ.

    // 確率計算処理を先頭にねじ込む.
    @Inject(method="attack", at=@At("HEAD"))
    private void onKokusenRoll(Entity target, CallbackInfo ci){
        this.isKokusen = Math.random() * 100 < KokusenConfig.kokusenPer;
        this.kokusenTarget = target;
    }

    // ここでダメージ・ノックバック・SE・エフェクトを追加する.
    // totalDamageローカル変数に干渉.ModifyVariable使いたくなるけどfloatが多すぎて不安定.
    // Player.classの938行目をArgで狙う.
    @ModifyArg(
            method="attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            ),
            index = 1
    )
    private float modifyTotalDamage(float totalDamage){
        if(this.isKokusen){
            Player player = (Player)(Object)this;
            Level level = player.level();
            if(!level.isClientSide()){

                // SE.
                level.playSound(null, player.blockPosition(),
                        SoundEvents.GENERIC_EXPLODE.value(),SoundSource.PLAYERS, 1.2f, 2.0f);
                level.playSound(null, player.blockPosition(),
                        SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.4f, 1.0f);
                level.playSound(null, player.blockPosition(),
                        SoundEvents.PLAYER_ATTACK_CRIT,SoundSource.PLAYERS, 2.0f, 1.0f);

                // ノックバックSE.
                if(this.kokusenTarget instanceof LivingEntity livingTarget){
                    double dx = player.getX() - livingTarget.getX();
                    double dz = player.getZ() - livingTarget.getZ();

                    livingTarget.knockback(KokusenConfig.kokusenKnockBack, dx, dz);
                }
            }
            return totalDamage * KokusenConfig.kokusenDmgMultiplier;
        }
        return totalDamage;
    }

    //

}
