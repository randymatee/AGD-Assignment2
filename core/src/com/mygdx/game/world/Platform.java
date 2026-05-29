package com.mygdx.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Platform extends GameMap {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public Platform() {
        map = new TmxMapLoader().load("Level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch) {
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {

        if (layer < 0 || layer >= getLayers()) return null;
        if (col < 0 || row < 0 || col >= getWidth() || row >= getHeight()) return null;

        Cell cell = ((TiledMapTileLayer) map.getLayers().get(layer)).getCell(col, row);

        if (cell != null) {

            TiledMapTile tile = cell.getTile();

            if (tile != null) {

                int id = tile.getId() & ~0xE0000000;
                return TileType.getTileTypeById(id);

            }
        }

        return null;
    }

    @Override
    public int getWidth() {
        return ((TiledMapTileLayer) map.getLayers().get(0)).getWidth();
    }

    @Override
    public int getHeight() {
        return ((TiledMapTileLayer) map.getLayers().get(0)).getHeight();
    }

    @Override
    public int getLayers() {
        return map.getLayers().getCount();
    }

    @Override
    public void dispose() {
        if (map != null) map.dispose();
        if (renderer != null) renderer.dispose();
    }
}
