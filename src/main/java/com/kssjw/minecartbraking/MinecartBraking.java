package com.kssjw.minecartbraking;

import com.kssjw.minecartbraking.manager.BrakeManager;
import com.kssjw.minecartbraking.manager.NetworkManager;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class MinecartBraking implements ModInitializer {

    public final static String MOD_ID = "minecart-braking";
    
    @Override
    public void onInitialize() {
        PayloadTypeRegistry.serverboundPlay().register(NetworkManager.BrakePayload.TYPE, NetworkManager.BrakePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
            NetworkManager.BrakePayload.TYPE,
            (payload, context) -> {
                ServerPlayer player = context.player();
                BrakeManager.brake(player);
            }
        );
    }
}