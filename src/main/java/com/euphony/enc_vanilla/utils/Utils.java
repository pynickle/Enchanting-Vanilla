package com.euphony.enc_vanilla.utils;

import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;

public class Utils {
    public static boolean isModLoaded(String modid){
        return ModList.get().isLoaded(modid);
    }

    public static String getModDisplayName(String modid) {
        for (IModInfo info : ModList.get().getMods()) {
            if (info.getModId().equals(modid))
                return info.getDisplayName();
        }
        return null;
    }
}
