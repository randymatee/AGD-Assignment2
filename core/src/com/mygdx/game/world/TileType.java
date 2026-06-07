package com.mygdx.game.world;

import java.util.HashMap;

/**
 * <p>
 *     represents the tile types for the game map
 * </p>
 */
public enum TileType {

    COLLIDABLE(38, true, "Collidable");

    public static final int TILE_SIZE = 32;

    private int id;
    private boolean collidable;
    private String name;

    /**
     * <p>
     *     used to create a tile instance and state their name, id from the tmx and whether they are collidable.
     * </p>
     * @param id id reference from the tmx
     * @param collidable whether the tile is collidable or not
     * @param name customisable name for the tiles
     */
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