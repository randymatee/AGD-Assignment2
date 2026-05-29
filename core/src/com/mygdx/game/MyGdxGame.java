package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.world.Platform;
import com.mygdx.game.world.Constants;
import java.awt.Button;
import java.security.UnresolvedPermission;

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

	private OrthographicCamera camera;
	private Platform platform;


	@Override
	public void create() {
		batch = new SpriteBatch();

		img = new Texture("badlogic.jpg");
		leftButtonTexture = new Texture(Gdx.files.internal("Left_Key.png"));
		rightButtonTexture = new Texture(Gdx.files.internal("Right_Key.png"));
		upButtonTexture = new Texture(Gdx.files.internal("Up_Key.png"));
		downButtonTexture = new Texture(Gdx.files.internal("Down_Key.png"));

		player = new Player();
		player.create();
		float buttonSize = Gdx.graphics.getHeight() * 0.1f;
		leftButton = new DPadButton(20, 100, buttonSize, buttonSize, leftButtonTexture, "left");
		rightButton = new DPadButton(280, 100, buttonSize, buttonSize, rightButtonTexture, "right");
		downButton = new DPadButton(150, 20, buttonSize, buttonSize, downButtonTexture, "down");
		upButton = new DPadButton(150, 180, buttonSize, buttonSize, upButtonTexture, "up");

		dPadButtons = new DPadButton[]{leftButton, rightButton, upButton, downButton};

		for (DPadButton button : dPadButtons) {
			button.create();
		}


		//leftButton = new


		//leftButton = new

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		camera.update();

		platform = new Platform();


	}

	@Override
	public void render() {
		update();
		ScreenUtils.clear(1, 0, 0, 1);

		camera.position.x = player.getPosition().x + player.getSpriteWidth() / 2f;
		camera.position.y = Constants.WORLD_HEIGHT / 2f;
		camera.update();

		platform.render(camera, batch);

		player.render();

		for (DPadButton button : dPadButtons) {
			button.render();
		}

		/*
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		 */

		/*
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		 */


	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		platform.dispose();
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
							(gameHeight - touchY >= button.getPosY() && gameHeight - touchY <= button.getPosY() + button.getHeight())) {

						touchedButton = button;
						break;
					}
				}
			}

			float newX = player.getPosition().x;
			float newY = player.getPosition().y;

			if (touchedButton == leftButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
				newX -= player.getSpeed();
			} else if (touchedButton == rightButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
				newX += player.getSpeed();
			} else if (touchedButton == upButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
				newY += player.getSpeed();
			} else if (touchedButton == downButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
				newY -= player.getSpeed();
			}

			boolean colliding = platform.doesRectCollideWithMap(
					newX,
					newY,
					16,
					16
			);

			if (!colliding) {
				player.setPosition(new Vector2(newX, newY));
				System.out.println("collision detected");


			}


		}
	}
}
