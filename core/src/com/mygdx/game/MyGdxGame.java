package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Player player;

	private DPadButton leftButton;
	private DPadButton rightButton;
	private DPadButton upButton;
	private DPadButton downButton;

	private DPadButton[] dPadButtons;

	private Texture leftButtonTexture;
	private Texture rightButtonTexture;
	private Texture upButtonTexture;
	private Texture downButtonTexture;

	private List<Trash> activeTrash;






	@Override
	public void create () {
		batch = new SpriteBatch();

		img = new Texture("badlogic.jpg");
		leftButtonTexture = new Texture(Gdx.files.internal("Left_Key.png"));
		rightButtonTexture = new Texture(Gdx.files.internal("Right_Key.png"));
		upButtonTexture = new Texture(Gdx.files.internal("Up_Key.png"));
		downButtonTexture = new Texture(Gdx.files.internal("Down_Key.png"));

		player = new Player();
		player.create();
		float buttonSize = Gdx.graphics.getHeight() * 0.1f;
		leftButton = new DPadButton(20, 100, buttonSize, buttonSize, leftButtonTexture, "left" );
		rightButton = new DPadButton(280, 100, buttonSize, buttonSize, rightButtonTexture, "right");
		downButton = new DPadButton(150, 20, buttonSize,buttonSize, downButtonTexture, "down");
		upButton = new DPadButton(150, 180, buttonSize, buttonSize, upButtonTexture, "up");

		dPadButtons = new DPadButton[] {leftButton, rightButton, upButton, downButton};

		for (DPadButton button: dPadButtons) {
			button.create();
		}

		activeTrash = new ArrayList<>();
		newGame();


		//leftButton = new


	}

	@Override
	public void render () {
		update();
		ScreenUtils.clear(1, 0, 0, 1);
		player.render();

		for (DPadButton button: dPadButtons) {
			button.render();
		}
		if (activeTrash != null) {

			for (Trash trash: activeTrash) {
				trash.render();
			}
		}


		/*
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		 */
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void update() {
		boolean isTouched = Gdx.input.isTouched();

		int touchX = Gdx.input.getX();
		int touchY = Gdx.input.getY();
		DPadButton touchedButton = null;

		boolean keyPressed = false;
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
			keyPressed = true;
		}
		if (keyPressed || isTouched) {

			if (isTouched) {
				int gameHeight = Gdx.graphics.getHeight();
				for (DPadButton button : dPadButtons) {

					if ((touchX >= button.getPosX() && touchX <= button.getPosX() + button.getWidth()) &&
							(gameHeight - touchY >= button.getPosY() && gameHeight - touchY <= button.getPosY() + button.getHeight()))

					{

						touchedButton = button;
						break;
					}
				}
			}
			// Gets the currently active sprite.
			Sprite playerSprite = new Sprite(player.getCurrentFrame(), (int)player.getPosition().x, (int)player.getPosition().y, player.getSpriteWidth(), player.getSpriteHeight());
			playerSprite.setPosition(player.getPosition().x, player.getPosition().y);
			Vector2 positionToMove = null;
			Vector2 preMovePosition = player.getPosition();
			PushDirection pushDirection = null;


			float speedDelta = player.getSpeed() * Gdx.graphics.getDeltaTime();
			if (touchedButton == leftButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
				positionToMove = new Vector2(player.getPosition().x - speedDelta, player.getPosition().y);
				pushDirection = PushDirection.LEFT;

			}
			else if (touchedButton == rightButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
				positionToMove = new Vector2(player.getPosition().x + speedDelta, player.getPosition().y);
				pushDirection = PushDirection.RIGHT;
			}

			else if (touchedButton == upButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
				positionToMove = new Vector2(player.getPosition().x, player.getPosition().y + speedDelta);
				pushDirection = PushDirection.UP;
			}

			else if (touchedButton == downButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
				positionToMove = new Vector2(player.getPosition().x, player.getPosition().y - speedDelta);
				pushDirection = PushDirection.DOWN;
			}
			player.setPosition(positionToMove);
			// Ensure player has been moved for collision check
			player.render();

			for (Trash trash: activeTrash) {
				Sprite trashSprite = new Sprite(trash.getCurrentFrame(), (int)trash.getPosition().x, (int)trash.getPosition().y, trash.getSpriteWidth(), trash.getSpriteHeight());
				trashSprite.setPosition(trash.getPosition().x, trash.getPosition().y);


				if (playerSprite.getBoundingRectangle().overlaps(trashSprite.getBoundingRectangle())) {
					//System.out.println(playerSprite.getBoundingRectangle().toString());
					System.out.println(trashSprite.getBoundingRectangle().toString());
					trash.push(pushDirection, speedDelta, player.isCanPush());
					player.setCanPush(false);
					player.setPosition(preMovePosition);
					player.render();
					break;
				}

			}



		}
	}

	public void newGame() {
		// TODO: Get variables stored in level class for stuff like amount of trash / orientation

		int trashCount = 2;

		for (int i = 0; i < trashCount - 1; i++) {
			Trash temp = new Trash();
			temp.create();
			activeTrash.add(temp);
			temp.setActiveTrash(activeTrash);

		}



	}
}
