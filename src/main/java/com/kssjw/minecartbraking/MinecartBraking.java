package com.kssjw.minecartbraking;

import com.kssjw.minecartbraking.manager.BrakeManager;
import com.kssjw.minecartbraking.manager.ConfigManager;
import com.kssjw.minecartbraking.manager.NetworkManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;

public class MinecartBraking implements ModInitializer {

    public final static String MOD_ID = "minecart-braking";
    
    @Override
    public void onInitialize() {
        ConfigManager.load();
        
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                Commands
                    .literal("setCartBrakeForce")
                    .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                    .then(
                        Commands
                            .argument("force", IntegerArgumentType.integer(10, 100))
                            .executes(context -> {
                                int value = IntegerArgumentType.getInteger(context, "force");
                                ConfigManager.setBrakeForce(value);
                                ConfigManager.load();
                                context.getSource().sendSystemMessage(Component.literal("Minecart brake force set to " + value));
                                
                                return Command.SINGLE_SUCCESS;
                            })
                    )
            );
        });

        
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