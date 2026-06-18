// コマンド登録ファイル.
// registerをinitializeで呼び出し、registerではFabricイベントを使ってコマンド登録タイミングを検知.
// その後、コマンドをマイクラレジストリに埋め込んでからその処理内容を書く.
// ex) /kokusen per 50.
// ex) /kokusen dmgMultiplier 50.

package io.github.suginura.kokusenblackflash.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import io.github.suginura.kokusenblackflash.config.KokusenConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class KokusenCommand {

    // FabricEVENTを利用して、コマンド登録タイミングが来たら登録メソッドを動かす.
    // CommandRegistrationCallbackは、マイクラのコマンド登録時に発火する.
    public static void register(){
        CommandRegistrationCallback.EVENT.register(
                KokusenCommand::registerCommands
        );
    }

    private static void registerCommands(
            CommandDispatcher<CommandSourceStack> dispatcher,
            CommandBuildContext registryAccess,
            Commands.CommandSelection environment
    ){
        dispatcher.register(
                Commands.literal("kokusen")
                        .then(Commands.literal("per")
                            .then(Commands.argument("value", FloatArgumentType.floatArg(0.0f, 100.0f))
                                    .executes(KokusenCommand::executePer)
                            )
                        )
                        .then(Commands.literal("dmgMultiplier")
                                .then(Commands.argument("value", FloatArgumentType.floatArg(1.0f, 100.0f))
                                        .executes(KokusenCommand::executeDmgMultiplier)
                                )
                        )
        );
    }

    private static int executePer(
            CommandContext<CommandSourceStack> context
    ){
        KokusenConfig.kokusenPer = FloatArgumentType.getFloat(context, "value");
        KokusenConfig.save();
        context.getSource().sendSuccess(
                () -> Component.literal("kokusen発生確率を" + KokusenConfig.kokusenPer + "%に変更しました (kokusen chance set to " + KokusenConfig.kokusenPer + "%)"),
                true
        );
        return 1;
    }

    private  static int executeDmgMultiplier(
            CommandContext<CommandSourceStack> context
    ){
        KokusenConfig.kokusenDmgMultiplier = FloatArgumentType.getFloat(context, "value");
        KokusenConfig.save();
        context.getSource().sendSuccess(
                () -> Component.literal("kokusenダメージ倍率を" + KokusenConfig.kokusenDmgMultiplier + "倍に変更しました (kokusen damage multiplier set to " + KokusenConfig.kokusenDmgMultiplier + "x)"),
                true
        );
        return 1;
    }

}
