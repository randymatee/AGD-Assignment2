package com.mygdx.game.world;

import java.util.HashMap;

public enum TileType {

    COLLIDABLE(38, true, "Collidable");

    public static final int TILE_SIZE = 32;

    private int id;
    private boolean collidable;
    private String name;

    TileType(int id, boolean collidable, String name) {
        this.id = id;
        this.collidable = collidable;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public String getName() {
        return name;
    }

    private static HashMap<Integer, TileType> tileMap;

    static {
        tileMap = new HashMap<Integer, TileType>();

        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById(int id) {
        return tileMap.get(id);
    }
}