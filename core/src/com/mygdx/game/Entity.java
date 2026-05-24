package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements ApplicationListener {

    private SpriteBatch spriteBatch;
    private Texture textureSheet;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private TextureRegion currentFrame;
    private int frameIndex;
    private float animationStateTime;
    private int textureCols;
    private int textureRows;

    private String baseSpritePath;

    private Vector2 position;

    private int spriteWidth;
    private int spriteHeight;


/*
    public Entity(
            SpriteBatch spriteBatch,
            Texture textureSheet,
            TextureRegion[] animationFrames,
            Animation animation,
            TextureRegion currentFrame,
            int frameIndex,
            float animationStateTime,
            int textureCols,
            int textureRows,
            String baseSpritePath) {

        this.spriteBatch = spriteBatch;
        this.textureSheet = textureSheet;
        this.animationFrames = animationFrames;
        this.animation = animation;
        this.currentFrame = currentFrame;
        this.frameIndex = frameIndex;
        this.animationStateTime = animationStateTime;
        this.textureCols = getTextureCols();
        this.textureRows = textureRows;


    }
    */

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

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

    public void parseSprite(Texture spriteSheet, TextureRegion[] frames, int cols, int rows) {
        TextureRegion[][] temp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / cols, spriteSheet.getHeight() / rows);

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = temp[i][j];
            }
        }
    }


    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Texture getTextureSheet() {
        return textureSheet;
    }

    public void setTextureSheet(Texture textureSheet) {
        this.textureSheet = textureSheet;
    }

    public TextureRegion[] getAnimationFrames() {
        return animationFrames;
    }

    public void setAnimationFrames(TextureRegion[] animationFrames) {
        this.animationFrames = animationFrames;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    public float getAnimationStateTime() {
        return animationStateTime;
    }

    public void setAnimationStateTime(float animationStateTime) {
        this.animationStateTime = animationStateTime;
    }

    public int getTextureCols() {
        return textureCols;
    }

    public void setTextureCols(int TEXTURE_COLS) {
        this.textureCols = textureCols;
    }

    public int getTextureRows() {
        return textureRows;
    }

    public void setTextureRows(int textureRows) {
        this.textureRows = textureRows;
    }

    public String getBaseSpritePath() {
        return baseSpritePath;
    }

    public void setBaseSpritePath(String baseSpritePath) {
        this.baseSpritePath = baseSpritePath;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }
}
