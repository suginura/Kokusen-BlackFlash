// 殴るたびに確立計算を行い、確率を引き当てればクリティカルパンチが出る.
// クリティカル時は、武器ダメージとエンチャントダメージを含めた最終ダメージを、倍率の分だけ乗算.
// また、SEとエフェクトが追加される.
// コマンドで数値類を操作可能.
// 確率やダメージ倍率は別ファイルへ移行予定.


package io.github.suginura.kokusenblackflash.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class KokusenAttackMixin {

    private boolean isKokusen = false;   // クリティカル判定.
    private float kokusenDmgMultiplier = 100.0F; // ダメージ倍率.

    // 確率計算処理を先頭にねじ込む.
    @Inject(method="attack", at=@At("HEAD"))
    private void onKokusenRoll(Entity target, CallbackInfo ci){
        this.isKokusen = Math.random() < 0.1;
    }

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
            return totalDamage * kokusenDmgMultiplier;
        }
        return totalDamage;
    }


    //

}
