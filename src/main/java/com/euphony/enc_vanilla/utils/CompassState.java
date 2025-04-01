package com.euphony.enc_vanilla.utils;

public enum CompassState {
    INACTIVE(0), FOUND(1), NOT_FOUND(2);

    private final int id;

    CompassState(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public static CompassState fromID(int id) {
        for (CompassState state : values()) {
            if (state.getID() == id) {
                return state;
            }
        }

        return null;
    }

}
