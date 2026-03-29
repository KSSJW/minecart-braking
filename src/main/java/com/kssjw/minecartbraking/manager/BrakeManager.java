package com.kssjw.minecartbraking.manager;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.phys.Vec3;

public class BrakeManager {

    private static double coefficient = 0.1;

    public static void brake(ServerPlayer player) {
        if (
            player == null
            || player.getVehicle() == null
            || !(player.getVehicle() instanceof AbstractMinecart)
        ) {
            return;
        }

        AbstractMinecart cart = (AbstractMinecart)player.getVehicle();

        Vec3 vec = cart.getDeltaMovement();

        if (vec.lengthSqr() > coefficient * coefficient) {

            // 沿着速度方向减去固定大小
            cart.setDeltaMovement(
                vec.subtract(
                    vec.normalize().scale(coefficient)
                )
            );
        } else {
            cart.setDeltaMovement(Vec3.ZERO);   // 如果速度已经很小，直接归零
        }
    }
}