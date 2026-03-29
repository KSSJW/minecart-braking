package com.kssjw.minecartbraking.client.manager;

import org.lwjgl.glfw.GLFW;

import com.kssjw.minecartbraking.MinecartBraking;
import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.resources.Identifier;

public class KeyManager {
    public static KeyMapping switchKey;
    
    public static void init() {
        Category customCategory = Category.register(Identifier.withDefaultNamespace(MinecartBraking.MOD_ID));

        switchKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.minecart-braking.brake",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT,
            customCategory
        ));
    }
}