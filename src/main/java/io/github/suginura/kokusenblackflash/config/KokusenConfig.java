package io.github.suginura.kokusenblackflash.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.suginura.kokusenblackflash.KokusenBlackFlash;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class KokusenConfig {
    public static float kokusenPer= 0.11F; // 0.00f~100.00fを指定することで確率を指定する.
    public static float kokusenDmgMultiplier = 50.0F; // ダメージ倍率. 1.0f~100.0fの間.

    private static class ConfigData {
        float kokusenPer = KokusenConfig.kokusenPer;
        float kokusenDmgMultiplier = KokusenConfig.kokusenDmgMultiplier;
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir()
            .resolve("kokusen-blackflash.json");

    public static void save() {
        try{
            Files.writeString(CONFIG_PATH, GSON.toJson(new ConfigData()));
        } catch (IOException e){
            KokusenBlackFlash.LOGGER.error(e.getMessage());
        }
    }

    public static  void load() {
        if(!Files.exists(CONFIG_PATH)){
            save();
            return;
        } try {
            ConfigData data = GSON.fromJson(Files.readString(CONFIG_PATH), ConfigData.class);
            kokusenPer = data.kokusenPer;
            kokusenDmgMultiplier = data.kokusenDmgMultiplier;
        } catch (IOException e){
            KokusenBlackFlash.LOGGER.error(e.getMessage());
        }
    }

}
