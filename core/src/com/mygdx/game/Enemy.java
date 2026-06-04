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
import com.mygdx.game.world.Platform;
import java.util.List;

public class Enemy extends Entity implements ApplicationListener {
    private float speed;

    private Player player;

    private PushDirection moveDirection = PushDirection.UP;
    private Platform platform;
    private List<Trash> activeTrash;

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
        setCurrentFrame((TextureRegion) getAnimation().getKeyFrame(getAnimationStateTime(), true));

        setStartingPositon(new Vector2(500, 300));
        setPosition(getStartingPositon());

        speed = 100;

        setSpriteHeight(40);
        setSpriteWidth(40);
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setActiveTrash(List<Trash> activeTrash) {
        this.activeTrash = activeTrash;
    }

    public void update() {
        float speedDelta = speed * Gdx.graphics.getDeltaTime();

        Vector2 nextPosition = new Vector2(getPosition());

        if (moveDirection == PushDirection.UP) {
            nextPosition.y += speedDelta;
        } else if (moveDirection == PushDirection.DOWN) {
            nextPosition.y -= speedDelta;
        }

        boolean hitWall = false;

        if (platform != null) {
            hitWall = platform.doesRectCollideWithMap(
                    nextPosition.x + 10,
                    nextPosition.y + 10,
                    20,
                    20
            );
        }

        boolean hitTrash = false;

        if (activeTrash != null) {
            Sprite enemySprite = new Sprite(
                    getCurrentFrame(),
                    (int) nextPosition.x,
                    (int) nextPosition.y,
                    getSpriteWidth(),
                    getSpriteHeight()
            );

            enemySprite.setPosition(nextPosition.x, nextPosition.y);

            for (Trash trash : activeTrash) {
                Sprite trashSprite = new Sprite(
                        trash.getCurrentFrame(),
                        (int) trash.getPosition().x,
                        (int) trash.getPosition().y,
                        trash.getSpriteWidth(),
                        trash.getSpriteHeight()
                );

                trashSprite.setPosition(trash.getPosition().x, trash.getPosition().y);

                if (enemySprite.getBoundingRectangle().overlaps(trashSprite.getBoundingRectangle())) {
                    hitTrash = true;
                    break;
                }
            }
        }

        if (hitWall || hitTrash) {
            if (moveDirection == PushDirection.UP) {
                moveDirection = PushDirection.DOWN;
            } else {
                moveDirection = PushDirection.UP;
            }

            return;
        }

        setPosition(nextPosition);
    }





}
