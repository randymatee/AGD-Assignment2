package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
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
                  int TEXTURE_COLS,
                  int TEXTURE_ROWS,
                  String baseSpritePath) {

        super(spriteBatch, textureSheet, animationFrames, animation,
                currentFrame, frameIndex, animationStateTime,
                TEXTURE_COLS, TEXTURE_ROWS, baseSpritePath);
    }

    @Override
    public void create() {
        super.create();







    }
}
