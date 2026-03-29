package com.kssjw.minecartbraking.manager;

import net.minecraft.resources.Identifier;

import com.kssjw.minecartbraking.MinecartBraking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;

public class NetworkManager {

    public record BrakePayload() implements CustomPacketPayload {
        public static final Type<BrakePayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(MinecartBraking.MOD_ID, "brake")
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static final StreamCodec<FriendlyByteBuf, BrakePayload> CODEC = StreamCodec.unit(new BrakePayload());
    }
}