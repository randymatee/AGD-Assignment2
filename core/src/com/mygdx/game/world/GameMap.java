package com.mygdx.game.world;

public abstract class GameMap {

    public GameMap() {
    }

    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(
                layer,
                (int)(x / TileType.TILE_SIZE),
                (int)(y / TileType.TILE_SIZE)
        );
    }

    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);

    public boolean doesRectCollideWithMap(float x, float y, int width, int height) {

        int leftTile = (int)(x / TileType.TILE_SIZE);
        int rightTile = (int)((x + width - 1) / TileType.TILE_SIZE);
        int bottomTile = (int)(y / TileType.TILE_SIZE);
        int topTile = (int)((y + height - 1) / TileType.TILE_SIZE);

        for (int row = bottomTile; row <= topTile; row++) {
            for (int col = leftTile; col <= rightTile; col++) {
                for (int layer = 0; layer < getLayers(); layer++) {

                    TileType type = getTileTypeByCoordinate(layer, col, row);

                    if (type != null && type.isCollidable()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public int getPixelWidth() {
        return getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight() {
        return getHeight() * TileType.TILE_SIZE;
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();

    public abstract void render(com.badlogic.gdx.graphics.OrthographicCamera camera, com.badlogic.gdx.graphics.g2d.SpriteBatch batch);

    public void dispose() {
    }
}