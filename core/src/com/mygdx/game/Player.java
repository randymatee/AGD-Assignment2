package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity implements ApplicationListener {

    public Player(SpriteBatch spriteBatch,
                  Texture textureSheet,
                  TextureRegion[] animationFrames,
                  Animation animation,
                  TextureRegion currentFrame,
                  int frameIndex,
                  float animationStateTime,
                  int textureCols,
                  int textureRows,
                  String baseSpritePath) {

        super(spriteBatch, textureSheet, animationFrames, animation,
                currentFrame, frameIndex, animationStateTime,
                textureCols, textureRows, baseSpritePath);
    }

    @Override
    public void create() {
        super.create();

        setTextureSheet(new Texture(Gdx.files.internal("assets/Broom.png")));
        setTextureRows(getTextureSheet().getWidth());
        setTextureCols(getTextureSheet().getHeight());
        setAnimationFrames(new TextureRegion[getTextureRows() * getTextureCols()]);

        parseSprite(getTextureSheet(), getAnimationFrames(), getTextureCols(), getTextureRows());

        setAnimation(new Animation(0.09f, getAnimationFrames()));
        setAnimationStateTime(0.0f);

    }

    @Override
    public void render() {
        super.render();

        getSpriteBatch().begin();
        getSpriteBatch().draw(getCurrentFrame(), 0,0);
        getSpriteBatch().end();

    }
}
