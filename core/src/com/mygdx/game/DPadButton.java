package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DPadButton implements ApplicationListener {

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isPressed() {
        return isPressed;
    }

    private float posX;
    private float posY;
    private float width;
    private float height;

    private SpriteBatch spriteBatch;

    boolean isPressed;
    Texture texture;

    private String direction;


    public DPadButton(float posX, float posY, float height, float width, Texture texture, String direction) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.direction = direction;


    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(texture, posX, posY, width, height);
        spriteBatch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
