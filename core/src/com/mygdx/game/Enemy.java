package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;

public class Enemy extends Entity implements ApplicationListener {
    private float speed;


    private Player player;

    @Override
    public void create() {
        super.create();

        setTextureSheet(new Texture(Gdx.files.internal("Roomba.png")));

        setTextureRows(1);
        setTextureCols(5);

        setAnimationFrames(new TextureRegion[1 * 5]);

        parseSprite(getTextureSheet(), getAnimationFrames(), 5, 1);

        setAnimation(new Animation<TextureRegion>(0.15f, getAnimationFrames()));
        setAnimationStateTime(0.0f);
        setStartingPositon(new Vector2(500, 300));
        setPosition(getStartingPositon());

        speed = 450;

        setSpriteHeight(200);
        setSpriteWidth(200);
    }

//    @Override
//    public void update(){
//
//    }





}
