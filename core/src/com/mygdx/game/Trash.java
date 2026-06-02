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

import java.util.ArrayList;
import java.util.List;

public class Trash extends Entity implements ApplicationListener {

    private boolean isPushing = false;

    public boolean getIsPushing() {
        return isPushing;
    }


    private PushDirection currentPushDirection;
    private float currentPushSpeed;

    private Player player;
    private List<Trash> activeTrash;

    private Trash collidedTrash;

    private PushDirection directionOfMovement;

    private MyGdxGame Game;
    List<Trash> trashToCheckCollide;
    List<Sprite> collideSprites;

    private boolean isOverlapping = true;

    private boolean hasPushBeenCalled = false;
    private Vector2 pushPosition;

    private Vector2 hitboxDimensions;


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
        setSpriteHeight(44);
        setSpriteWidth(44);
        activeTrash = null;
        player = null;
        collidedTrash = null;
        directionOfMovement = null;

        setCurrentFrame((TextureRegion) getAnimation().getKeyFrame(getAnimationStateTime(), true));

        trashToCheckCollide = new ArrayList<Trash>();
        collideSprites = new ArrayList<Sprite>();
        pushPosition = null;

        hitboxDimensions = new Vector2(getSpriteWidth() / 2, getSpriteHeight() / 2);


    }

    @Override
    public void render() {
        //super.render();

        calculateRayCollisions();

        /*
        if (activeTrash != null && isPushing) {
            Sprite ownSprite = new Sprite(this.getCurrentFrame(), (int)this.getPosition().x, (int)this.getPosition().y, this.getSpriteWidth(), this.getSpriteHeight());
            ownSprite.setPosition(this.getPosition().x, this.getPosition().y);

            List<Trash> trashToCheckCollide = new ArrayList<Trash>();
            List<Sprite> collideSprites = new ArrayList<Sprite>();
            Ray collisionRay = Game.createRay(this.getPosition().x + this.getSpriteWidth() / 2, this.getPosition().y + this.getSpriteHeight() / 2, directionOfMovement);

            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(Game.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.line(collisionRay.origin.x, collisionRay.origin.y, collisionRay.origin.x + 500, collisionRay.origin.y);
            shapeRenderer.end();

            for (Trash trash: activeTrash) {
                Sprite trashSprite = new Sprite(trash.getCurrentFrame(), (int)trash.getPosition().x, (int)trash.getPosition().y, trash.getSpriteWidth(), trash.getSpriteHeight());
                trashSprite.setPosition(trash.getPosition().x, trash.getPosition().y);


                BoundingBox spriteBox = new BoundingBox(new Vector3(trash.getPosition().x, trash.getPosition().y, -1),
                                                        new Vector3(trash.getPosition().x + trash.getSpriteWidth(), trash.getPosition().y + trash.getSpriteHeight(),1 ));

                if (Intersector.intersectRayBoundsFast(collisionRay, spriteBox)) {
                    trashToCheckCollide.add(trash);
                    collideSprites.add(trashSprite);
                }

         */


        // TODO: Turn off isPushing when colliding with a tilemap.

                /*
                if (ownSprite.getBoundingRectangle().overlaps(trashSprite.getBoundingRectangle()) && trash != this) {
                    collidedTrash = trash;



                    if(isPushing) {
                        isPushing = false;
                        player.setCanPush(true);
                    }
;

                }
                */

        Sprite ownSprite = new Sprite(this.getCurrentFrame(), (int) this.getPosition().x, (int) this.getPosition().y, this.getSpriteWidth(), this.getSpriteHeight());
        ownSprite.setPosition(this.getPosition().x, this.getPosition().y);
        //ownSprite.setBounds(this.getPosition().x, this.getPosition().y, this.getHitboxDimensions().x, getHitboxDimensions().y);

        for (int i = 0; i < trashToCheckCollide.size(); i++) {
            Trash trash = trashToCheckCollide.get(i);
            Sprite trashSprite = collideSprites.get(i);
            if (trash == null || trashSprite == null) {
                continue;
            }
            if (ownSprite.getBoundingRectangle().overlaps(trashSprite.getBoundingRectangle()) && trash != this) {
                collidedTrash = trash;

                if (isPushing) {
                    isPushing = false;
                    player.setCanPush(true);
                }
            }


        }

        if (isPushing) {
            this.setPushPosition(this.push(currentPushDirection, currentPushSpeed, true));
        }
        // Apply the new position calculated by push after collision has been checked.
        if (hasPushBeenCalled && this.pushPosition != null) {
            this.setPosition(pushPosition);
        }
        hasPushBeenCalled = false;
        pushPosition = null;

        super.render();
    }






    public Vector2 push(PushDirection direction, float deltaSpeed, boolean canBePushed) {
        if (!canBePushed) {
            return null;
        }

        hasPushBeenCalled = true;
        currentPushDirection = direction;
        currentPushSpeed = deltaSpeed;
        isPushing = true;

        switch(direction) {
            case LEFT:
                return new Vector2(getPosition().x - deltaSpeed, getPosition().y);

            case RIGHT:
                return new Vector2(getPosition().x + deltaSpeed, getPosition().y);


            case UP:
                return new Vector2(getPosition().x, getPosition().y + deltaSpeed);

            case DOWN:
                return new Vector2(getPosition().x, getPosition().y - deltaSpeed);
        }

        return null;
    }


    public boolean isDirectionContainingPrevCollision(PushDirection movDirection) {

        int distanceToCheck = 5;
        //float spriteHeightDivised = this.getSpriteHeight() / 2;
        //float collidedHeightDivised = collidedTrash.getSpriteHeight() / 2;

        //float spriteWidthDivised = this.getSpriteWidth() / 2;
        //float collidedWidthDivised = collidedTrash.getSpriteWidth() / 2;




        if (collidedTrash == null) {
            return false;
        }
        switch (movDirection) {

            case UP:
                if (this.getPosition().y + this.getSpriteHeight() + distanceToCheck > collidedTrash.getPosition().y
                && this.getPosition().y + this.getSpriteHeight() + distanceToCheck < collidedTrash.getPosition().y + collidedTrash.getSpriteHeight()) {
                    return true;
                }
                break;
            case DOWN:
                if (this.getPosition().y - distanceToCheck < collidedTrash.getPosition().y + collidedTrash.getSpriteHeight()
                        && this.getPosition().y - distanceToCheck > collidedTrash.getPosition().y) {
                    return true;


                }
                break;
            case LEFT:
                if (this.getPosition().x - distanceToCheck < collidedTrash.getPosition().x + collidedTrash.getSpriteWidth()
                        && this.getPosition().x - distanceToCheck > collidedTrash.getPosition().x) {
                    return true;
                }
                break;

            case RIGHT:
                if (this.getPosition().x + this.getSpriteWidth() + distanceToCheck > collidedTrash.getPosition().x
                        && this.getPosition().x + this.getSpriteWidth() + distanceToCheck < collidedTrash.getPosition().x + collidedTrash.getSpriteWidth()) {
                    return true;
                }
                break;


        }
        return false;


    }

    public void calculateRayCollisions() {
        if (activeTrash != null && isPushing) {

            trashToCheckCollide.clear();
            collideSprites.clear();

            Sprite ownSprite = new Sprite(this.getCurrentFrame(), (int) this.getPosition().x, (int) this.getPosition().y, this.getSpriteWidth(), this.getSpriteHeight());
            ownSprite.setPosition(this.getPosition().x, this.getPosition().y);
            //ownSprite.setBounds(this.getPosition().x, this.getPosition().y, this.getHitboxDimensions().x, getHitboxDimensions().y);


            Ray collisionRay = Game.createRay(this.getPosition().x + this.getSpriteWidth() / 2, this.getPosition().y + this.getSpriteHeight() / 2, directionOfMovement);

            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(Game.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.line(collisionRay.origin.x, collisionRay.origin.y, collisionRay.origin.x + 500, collisionRay.origin.y);
            shapeRenderer.end();

            for (Trash trash : activeTrash) {
                Sprite trashSprite = new Sprite(trash.getCurrentFrame(), (int) trash.getPosition().x, (int) trash.getPosition().y, trash.getSpriteWidth(), trash.getSpriteHeight());
                trashSprite.setPosition(trash.getPosition().x, trash.getPosition().y);
                //trashSprite.setBounds(trash.getPosition().x, trash.getPosition().y, trash.getHitboxDimensions().x, trash.getHitboxDimensions().y);



                BoundingBox spriteBox = new BoundingBox(new Vector3(trash.getPosition().x, trash.getPosition().y, -1),
                        new Vector3(trash.getPosition().x + trash.getSpriteWidth(), trash.getPosition().y + trash.getSpriteHeight(), 1));

                if (Intersector.intersectRayBounds(collisionRay, spriteBox, new Vector3(collisionRay.origin.x + 10, collisionRay.origin.y, 0))) {
                    trashToCheckCollide.add(trash);
                    collideSprites.add(trashSprite);
                }
            }
        }

    }


    public List<Trash> getActiveTrash() {
        return activeTrash;
    }

    public void setActiveTrash(List<Trash> activeTrash) {
        this.activeTrash = activeTrash;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PushDirection getDirectionOfMovement() {
        return directionOfMovement;
    }

    public void setDirectionOfMovement(PushDirection directionOfMovement) {
        this.directionOfMovement = directionOfMovement;
    }

    public void setGame(MyGdxGame game) {
        Game = game;
    }

    public boolean isOverlapping() {
        return isOverlapping;
    }

    public void setOverlapping(boolean overlapping) {
        isOverlapping = overlapping;
    }

    public void setPushPosition(Vector2 pushPosition) {
        this.pushPosition = pushPosition;
    }

    public Vector2 getHitboxDimensions() {
        return hitboxDimensions;
    }

    public void setHitboxDimensions(Vector2 hitboxDimensions) {
        this.hitboxDimensions = hitboxDimensions;
    }
}


