package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity implements ApplicationListener {

    private float speed;
/*
    public Player(SpriteBatch spriteBatch,
                  Texture textureSheet,
                  TextureRegion[] animationFrames,
ddwdadw                  Animation animation,
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
    */

    @Override
    public void create() {
        super.create();
        setTextureSheet(new Texture(Gdx.files.internal("Broom.png")));
        setTextureRows(1);
        setTextureCols(1);
        setAnimationFrames(new TextureRegion[1 * 1]);

        parseSprite(getTextureSheet(), getAnimationFrames(), 1, 1);

        setAnimation(new Animation(0.09f, getAnimationFrames()));
        setAnimationStateTime(0.0f);
        setPosition(new Vector2(50,50));
        speed = 5;

        setSpriteHeight(200);
        setSpriteWidth(200);

    }

    @Override
    public void render() {
        super.render();
        setAnimationStateTime(getAnimationStateTime() + Gdx.graphics.getDeltaTime());

        setCurrentFrame((TextureRegion) getAnimation().getKeyFrame(getAnimationStateTime(), true));


        getSpriteBatch().begin();
        getSpriteBatch().draw(getCurrentFrame(), getPosition().x, getPosition().y, getSpriteWidth(), getSpriteHeight());
        getSpriteBatch().end();

    }

    public void move(MoveDir direction) {

    }


    public float getSpeed() {
        return speed;
    }
}
