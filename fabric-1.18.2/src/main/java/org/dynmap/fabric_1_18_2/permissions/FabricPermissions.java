package org.dynmap.fabric_1_18_2.permissions;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import org.dynmap.DynmapCore;
import org.dynmap.Log;
import org.dynmap.fabric_1_18_2.DynmapMod;
import org.dynmap.fabric_1_18_2.DynmapPlugin;

import java.util.Set;
import java.util.stream.Collectors;

public class FabricPermissions implements PermissionProvider {

    private String permissionKey(String perm) {
        return "dynmap." + perm;
    }

    @Override
    public Set<String> hasOfflinePermissions(String player, Set<String> perms) {
        Log.info("Requesting offline permissions: " + String.join(",", perms) + " for " + player);
        return perms.stream()
                .filter(perm -> hasOfflinePermission(player, perm))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasOfflinePermission(String player, String perm) {
        Log.info("Requesting offline permission: " + perm + " for " + player);
        return DynmapPlugin.plugin.isOp(player.toLowerCase());
    }

    @Override
    public boolean has(PlayerEntity player, String permission) {
        Log.info("Requesting privilege: " + permission);
        return player != null && Permissions.check(player, permissionKey(permission));
    }

    @Override
    public boolean hasPermissionNode(PlayerEntity player, String permission) {
        if (player != null) {
            String name = player.getName().getString().toLowerCase();
            Log.info("Requesting permission node: " + permission + " for " + name);
            return DynmapPlugin.plugin.isOp(name);
        }
        Log.info("Requesting permission node: " + permission);
        return false;
    }

}
