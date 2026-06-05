package com.mygdx.game.world;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enemy;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;
import com.mygdx.game.PushDirection;
import com.mygdx.game.Trash;

import java.util.ArrayList;
import java.util.List;

public class Level implements ApplicationListener {

    private List<Trash> trash;

    private List<Enemy> enemies;

    private final List<List<Vector2>> trashPositionsbyRow;

    private final List<Vector2> enemyPositions;

    private final Vector2 playerStartingPosition;

    private final Player player;

    private final Platform platform;

    private final MyGdxGame game;

    private List<PushDirection> enemyStartDirs;

    private List<Float> enemySpeeds;



    private final int endPosition;

    public Level(List<List<Vector2>> trashPositionsbyRow, List<Vector2> enemyPositions, Vector2 playerStartingPosition, Player player,
                 Platform platform, MyGdxGame game, List<PushDirection> enemyStartDirs, List<Float> enemySpeeds) {

        this.trashPositionsbyRow = trashPositionsbyRow;
        this.enemyPositions = enemyPositions;
        this.playerStartingPosition = playerStartingPosition;
        this.platform = platform;
        this.player = player;
        this.game = game;
        this.endPosition = 1925;
        this.enemyStartDirs = enemyStartDirs;
        this.enemySpeeds = enemySpeeds;


        trash = new ArrayList<Trash>();
        enemies = new ArrayList<>();

    }


    @Override
    public void create() {


        for (List<Vector2> list : trashPositionsbyRow) {
            for (Vector2 position : list) {

                Trash temp = new Trash();
                temp.create();
                temp.setCamera(game.getCamera());
                temp.setPosition(position);
                temp.setActiveTrash(trash);
                temp.setPlayer(player);
                platform.setActiveTrash(trash);
                temp.setGame(game);

                trash.add(temp);

            }


        }

        for (int i = 0; i < enemyPositions.size(); i++) {

            Enemy temp = new Enemy();
            temp.create();
            temp.setCamera(game.getCamera());
            temp.setPosition(enemyPositions.get(i));
            temp.setPlatform(platform);
            temp.setActiveTrash(trash);
            temp.setMoveDirection(enemyStartDirs.get(i));
            temp.setSpeed(enemySpeeds.get(i));
            enemies.add(temp);
        }


        /*
    }
        int trashCount = 5;

        for (int i = 0; i < trashCount - 1; i++) {

            Trash temp = new Trash();
            temp.create();
            activeTrash.add(temp);
            if (i == 0) {
                temp.setPosition(new Vector2(1000, 500));
            }

            if (i == 1) {
                temp.setPosition(new Vector2(1000, 700));
            }

            if (i == 2) {
                temp.setPosition(new Vector2(1000, 300));
            }


            temp.setActiveTrash(activeTrash);
            platform.setActiveTrash(activeTrash);
            temp.setPlayer(this.player);
            temp.setGame(this);

         */


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        for (Trash temp : trash) {
            temp.render();

        }
        for (Enemy enemy : enemies) {
            enemy.update();
            enemy.render();
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        for (Trash temp : trash) {
            temp.dispose();
        }
        trash.clear();

        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
        enemies.clear();
        //platform.dispose();

    }

    public Vector2 getPlayerStartingPosition() {
        return playerStartingPosition;
    }

    public List<Vector2> getEnemyPositions() {
        return enemyPositions;
    }


    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Trash> getTrash() {
        return trash;
    }

    public void setTrash(List<Trash> trash) {
        this.trash = trash;
    }

    public List<List<Vector2>> getTrashPositionsbyRow() {
        return trashPositionsbyRow;
    }

    public int getEndPosition() {
        return endPosition;
    }



    public Platform getPlatform() {
        return platform;
    }
}
