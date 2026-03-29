package com.kssjw.minecartbraking.client;

import com.kssjw.minecartbraking.client.manager.KeyManager;
import com.kssjw.minecartbraking.manager.NetworkManager;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class MinecartBrakingClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        KeyManager.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyManager.switchKey.isDown()) ClientPlayNetworking.send(new NetworkManager.BrakePayload());
        });
    }
}