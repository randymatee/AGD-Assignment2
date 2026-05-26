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

        if (y < 0) {
            return true;
        }

        for (int row = (int)(y / TileType.TILE_SIZE);
             row < Math.ceil((y + height) / TileType.TILE_SIZE);
             row++) {

            for (int col = (int)(x / TileType.TILE_SIZE);
                 col < Math.ceil((x + width) / TileType.TILE_SIZE);
                 col++) {

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