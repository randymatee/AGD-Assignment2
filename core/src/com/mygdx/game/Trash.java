package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Trash extends Entity implements ApplicationListener {

    private boolean isPushing;
    private PushDirection currentPushDirection;
    private float currentPushSpeed;

    private List<Trash> activeTrash;
    /*
    public Trash(SpriteBatch spriteBatch, Texture textureSheet,
                 TextureRegion[] animationFrames, Animation animation,
                 TextureRegion currentFrame, int frameIndex,
                 float animationStateTime, int TEXTURE_COLS,
                 int TEXTURE_ROWS, String baseSpritePath) {
        super(spriteBatch, textureSheet, animationFrames,
                animation, currentFrame, frameIndex, animationStateTime,
                TEXTURE_COLS, TEXTURE_ROWS, baseSpritePath);
    }

     */

    @Override
    public void create() {
        super.create();
        setTextureSheet(new Texture(Gdx.files.internal("Trash.png")));
        setTextureRows(1);
        setTextureCols(1);
        setAnimationFrames(new TextureRegion[1 * 1]);

        parseSprite(getTextureSheet(), getAnimationFrames(), 1, 1);

        setAnimation(new Animation(0.09f, getAnimationFrames()));
        setAnimationStateTime(0.0f);
        setPosition(new Vector2(500,500));
        setSpriteHeight(100);
        setSpriteWidth(100);
        activeTrash = null;
    }

    @Override
    public void render() {
        if (activeTrash != null) {
            for (Trash trash: activeTrash) {
                Sprite trashSprite = new Sprite(trash.getCurrentFrame(), (int)trash.getPosition().x, (int)trash.getPosition().y, trash.getSpriteWidth(), trash.getSpriteHeight());
                trashSprite.setPosition(trash.getPosition().x, trash.getPosition().y);
            }
        }



        if (isPushing) {
            push(currentPushDirection, currentPushSpeed, false);
        }
        super.render();
    }


    public void push(PushDirection direction, float deltaSpeed, boolean hasBeenPushed) {
        if (hasBeenPushed) {
            return;
        }
        currentPushDirection = direction;
        currentPushSpeed = deltaSpeed;
        isPushing = true;

        switch(direction) {
            case LEFT:
                setPosition(new Vector2(getPosition().x - deltaSpeed, getPosition().y));
                break;
            case RIGHT:
                setPosition(new Vector2(getPosition().x + deltaSpeed, getPosition().y));
                break;

            case UP:
                setPosition(new Vector2(getPosition().x, getPosition().y + deltaSpeed));
                break;
            case DOWN:
                setPosition(new Vector2(getPosition().x, getPosition().y - deltaSpeed));
        }

    }

    public List<Trash> getActiveTrash() {
        return activeTrash;
    }

    public void setActiveTrash(List<Trash> activeTrash) {
        this.activeTrash = activeTrash;
    }
}
