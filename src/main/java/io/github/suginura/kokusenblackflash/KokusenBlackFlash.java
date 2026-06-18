// 殴るたびに確立計算を行い、確率を引き当てればクリティカルパンチが出る.
// クリティカル時は、武器ダメージとエンチャントダメージを含めた最終ダメージを、倍率の分だけ乗算.
// また、SEとエフェクトが追加される.
// コマンドで数値類を操作可能.
// 確率やダメージ倍率は別ファイルへ移行予定.

package io.github.suginura.kokusenblackflash;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.suginura.kokusenblackflash.config.KokusenConfig;
import io.github.suginura.kokusenblackflash.command.KokusenCommand;

public class KokusenBlackFlash implements ModInitializer {
	public static final String MOD_ID = "kokusen-blackflash";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		// 各種数値保存jsonのロード.
		KokusenConfig.load();

		// コマンドの登録.
		KokusenCommand.register();

		LOGGER.info("!!! Kokusen BlackFlash Loaded !!!");
	}
}