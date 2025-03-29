package com.euphony.enc_vanilla.utils;

import net.neoforged.fml.ModList;

public class Utils {
    public static boolean isModLoaded(String modid){
        return ModList.get().isLoaded(modid);
    }
}
