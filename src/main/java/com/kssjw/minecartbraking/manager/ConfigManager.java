package com.kssjw.minecartbraking.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;

public class ConfigManager {
    private static final Path CONFIG_PATH = FabricLoader
        .getInstance()
            .getConfigDir()
                .resolve("minecart-braking.properties");

    private static Properties props = new Properties();

    public static void load() {

        try {
            
            if (Files.exists(CONFIG_PATH)) {

                try (InputStream in = Files.newInputStream(CONFIG_PATH)) {
                    props.load(in);

                    double force = getBrakeForce();

                    if (
                        force == -1
                        || force > 100
                        || force < 10
                    ) {
                        props.setProperty("brakeForce", "10");
                        save();
                    }
                }
            } else {

                // 文件不存在时生成默认配置
                props.setProperty("brakeForce", "10");
                save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (OutputStream out = Files.newOutputStream(CONFIG_PATH)) {
            String comments = "Minecart Braking Config\n\n";
            comments += "brakeForce -> Min: 10  Default: 10  Max: 100";
            comments += "\n";
            props.store(out, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setBrakeForce(int value) {
        props.setProperty("brakeForce", String.valueOf(value));
        save();
    }

    public static int getBrakeForce() {
        String value = props.getProperty("brakeForce", "10");

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1; // 标记非法
        }
    }
}