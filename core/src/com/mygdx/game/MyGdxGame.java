package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.world.Level;
import com.mygdx.game.world.Platform;
import com.mygdx.game.world.Constants;
import java.awt.Button;
import java.security.UnresolvedPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyGdxGame extends ApplicationAdapter {

	public enum GameState { MENU, PLAYING, FAIL, SUCCESS }

	private GameState gameState;
	private SpriteBatch batch;
	private Texture img;
	private Player player;

	//private Enemy enemy;

	private DPadButton leftButton;
	private DPadButton rightButton;
	private DPadButton upButton;
	private DPadButton downButton;

	private DPadButton[] dPadButtons;

	private Texture leftButtonTexture;
	private Texture rightButtonTexture;
	private Texture upButtonTexture;
	private Texture downButtonTexture;
	private Texture restartButtonTexture;

	private List<Trash> activeTrash;

	//private boolean isOverlapping = true;


	private OrthographicCamera camera;

	public OrthographicCamera getCamera() {
		return camera;
	}

	private Platform platform;

	private List<Level> levels;

	private Level level1;

	private Level activeLevel;
	private DPadButton restartButton;






	@Override
	public void create() {
		batch = new SpriteBatch();

		img = new Texture("badlogic.jpg");
		leftButtonTexture = new Texture(Gdx.files.internal("Left_Key.png"));
		rightButtonTexture = new Texture(Gdx.files.internal("Right_Key.png"));
		upButtonTexture = new Texture(Gdx.files.internal("Up_Key.png"));
		downButtonTexture = new Texture(Gdx.files.internal("Down_Key.png"));
		restartButtonTexture = new Texture(Gdx.files.internal("restart_key.png"));


		int trashHeight = 44;
		int trashWidth = 44;
		int padding = 4;

		player = new Player();
		player.create();


		//create enemy -randy
		//enemy = new Enemy();
		//enemy.create();


		int row1Height = 225;
		int row0Height = row1Height + trashHeight + padding;
		int rowMinus1Height = row0Height + trashHeight + padding;

		List<Vector2> level1RowMinusOne = new ArrayList<Vector2>();
		level1RowMinusOne.add(new Vector2(1000 + trashWidth * 4, rowMinus1Height));
		level1RowMinusOne.add(new Vector2(1400 + trashWidth, rowMinus1Height));


		List<Vector2> level1Row0 = new ArrayList<Vector2>();
		level1Row0.add(new Vector2(1000 + trashWidth * 3, row0Height));
		level1Row0.add(new Vector2(1400, row0Height));



		List<Vector2> level1Row1 = new ArrayList<Vector2>();
		level1Row1.add(new Vector2(250, row1Height));
		level1Row1.add(new Vector2(250 + trashWidth, row1Height));
		level1Row1.add(new Vector2(650, row1Height));
		level1Row1.add(new Vector2(1000, row1Height));
		level1Row1.add(new Vector2(1000 + trashHeight * 3, row1Height));

		level1Row1.add(new Vector2(1400, row1Height));
		level1Row1.add(new Vector2(1400 + trashWidth * 6, row1Height));
		level1Row1.add(new Vector2(1400 + trashWidth * 7, row1Height));



		int row2Height = row1Height - trashHeight - padding ;
		List<Vector2> level1Row2 = new ArrayList<Vector2>();
		level1Row2.add(new Vector2(250, row2Height));
		level1Row2.add(new Vector2(250 + trashWidth, row2Height));
		level1Row2.add(new Vector2(650, row2Height));
		level1Row2.add(new Vector2(650 + trashWidth, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth * 2, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth * 3, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth * 5, row2Height));

		level1Row2.add(new Vector2(1400 + trashWidth, row2Height));
		level1Row2.add(new Vector2(1400 + trashWidth * 2, row2Height));
		level1Row2.add(new Vector2(1400 + trashWidth * 3, row2Height));
		level1Row2.add(new Vector2(1400 + trashWidth * 6, row2Height));
		level1Row2.add(new Vector2(1400 + trashWidth * 7, row2Height));




		int row3Height = row2Height - trashHeight - padding ;

		List<Vector2> level1Row3 = new ArrayList<Vector2>();
		level1Row3.add(new Vector2(250, row3Height));
		level1Row3.add(new Vector2(250 + trashWidth, row3Height));
		level1Row3.add(new Vector2(650, row3Height));
		level1Row3.add(new Vector2(650 + trashWidth, row3Height));

		level1Row3.add(new Vector2(1000, row3Height));
		level1Row3.add(new Vector2(1000 + trashWidth, row3Height));
		level1Row3.add(new Vector2(1000 + trashWidth * 5, row3Height));
		level1Row3.add(new Vector2(1000 + trashWidth * 2, row3Height));
		level1Row3.add(new Vector2(1000 + trashWidth * 3, row3Height));

		level1Row3.add(new Vector2(1400, row3Height));
		level1Row3.add(new Vector2(1400 + trashWidth * 2, row3Height));
		level1Row3.add(new Vector2(1400 + trashWidth * 6, row3Height));
		level1Row3.add(new Vector2(1400 + trashWidth * 7, row3Height));


		//level1Row3.add(new Vector2(1400 + trashWidth * 3, row3Height));


		int row4Height = row3Height - trashHeight - padding;


		List<Vector2> level1Row4 = new ArrayList<Vector2>();
		level1Row4.add(new Vector2(250 + trashWidth, row4Height));
		level1Row4.add(new Vector2(500, row4Height));
		level1Row4.add(new Vector2(650, row4Height));
		level1Row4.add(new Vector2(650 + trashWidth, row4Height));
		level1Row4.add(new Vector2(1000 + trashWidth, row4Height));
		level1Row4.add(new Vector2(1000 + trashWidth * 5, row4Height));

		level1Row4.add(new Vector2(1400, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 2, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 3, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 6, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 7, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 8, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 9, row4Height));



		//level1Row4.add(new Vector2(1000, row4Height));



		int row5Height = row4Height - trashHeight - padding ;
		List<Vector2> level1Row5 = new ArrayList<Vector2>();
		level1Row5.add(new Vector2(250, row5Height));
		level1Row5.add(new Vector2(250 + trashWidth, row5Height));
		level1Row5.add(new Vector2(650, row5Height));
		level1Row5.add(new Vector2(650 + trashWidth, row5Height));
		level1Row5.add(new Vector2(1000, row5Height));
		level1Row5.add(new Vector2(1000 + trashWidth, row5Height));
		level1Row5.add(new Vector2(1000 + trashWidth * 2, row5Height));
		//level1Row5.add(new Vector2(1000 + trashWidth * 3, row5Height));
		//level1Row5.add(new Vector2(1000 + trashWidth * 4, row5Height));
		level1Row5.add(new Vector2(1000 + trashWidth * 5, row5Height));

		level1Row5.add(new Vector2(1400, row5Height));
		level1Row5.add(new Vector2(1400 + trashWidth * 2, row5Height));

		//TODO: Temporary because no tile collison
		level1Row5.add(new Vector2(1400 + trashWidth, row5Height - trashHeight));


		List<List<Vector2>> level1TrashPos = new ArrayList<List<Vector2>>();

		level1TrashPos.add(level1RowMinusOne);

		level1TrashPos.add(level1Row0);
		level1TrashPos.add(level1Row1);
		level1TrashPos.add(level1Row2);
		level1TrashPos.add(level1Row3);
		level1TrashPos.add(level1Row4);
		level1TrashPos.add(level1Row5);


		List<Vector2> level1EnemyPos = new ArrayList<Vector2>();
		Vector2 level1EnemyPos1 = new Vector2(250 - trashWidth, row1Height + 15);
		Vector2 level1EnemyPos2 = new Vector2(500 + trashWidth, row1Height + 15);
		Vector2 level1EnemyPos3 = new Vector2(650 + trashWidth * 2, row3Height + 15);
		Vector2 level1EnemyPos4 = new Vector2(1400 + trashWidth * 5, row5Height - 2);


		level1EnemyPos.add(level1EnemyPos1);
		level1EnemyPos.add(level1EnemyPos2);
		level1EnemyPos.add(level1EnemyPos3);
		level1EnemyPos.add(level1EnemyPos4);


		float buttonSize = Gdx.graphics.getHeight() * 0.1f;
		leftButton = new DPadButton(20, 100, buttonSize, buttonSize, leftButtonTexture);
		rightButton = new DPadButton(280, 100, buttonSize, buttonSize, rightButtonTexture);
		downButton = new DPadButton(150, 20, buttonSize, buttonSize, downButtonTexture);
		upButton = new DPadButton(150, 180, buttonSize, buttonSize, upButtonTexture);

		restartButton = new DPadButton(1800, 950, buttonSize, buttonSize, restartButtonTexture);
		restartButton.create();


		dPadButtons = new DPadButton[]{leftButton, rightButton, upButton, downButton};

		for (DPadButton button : dPadButtons) {
			button.create();
		}

		activeTrash = new ArrayList<>();




		//leftButton = new


		//leftButton = new

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		camera.update();

		player.setCamera(camera);

		platform = new Platform();

		newGame();

		gameState = GameState.PLAYING;

		levels = new ArrayList<>();

		level1 = new Level(level1TrashPos, level1EnemyPos, player.getStartingPositon(), player, platform, this);
		level1.create();
		levels.add(level1);
		activeLevel = level1;
		activeTrash = activeLevel.getTrash();
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		if (gameState == GameState.PLAYING) {
			update();
		}



		camera.position.x = player.getPosition().x + player.getSpriteWidth() / 2f;
		camera.position.y = Constants.WORLD_HEIGHT / 2f;
		camera.update();
		platform.render(camera, batch);
		activeLevel.render();
		player.render();
		restartButton.render();








		//enemy.render();


		for (DPadButton button : dPadButtons) {
			button.render();
		}
		//if (activeTrash != null) {

			//for (Trash trash: activeTrash) {
				//trash.render();
			//}



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

		if (gameState == GameState.SUCCESS) {
			Gdx.app.exit();

		}

		if (player.getPosition().x > activeLevel.getEndPosition()) {
			gameState = GameState.SUCCESS;
		}

		boolean isTouched = Gdx.input.isTouched();

		int touchX = Gdx.input.getX();
		int touchY = Gdx.input.getY();
		DPadButton touchedButton = null;
		int gameHeight = Gdx.graphics.getHeight();


		if (isTouched) {
			if ((touchX >= restartButton.getPosX() && touchX <= restartButton.getPosX() + restartButton.getWidth()) &&
					(gameHeight - touchY >= restartButton.getPosY() && gameHeight - touchY <= restartButton.getPosY() + restartButton.getHeight())) {

				RestartGame();
				return;


			}

		}


		boolean keyPressed = false;
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
			keyPressed = true;
		}
		if (keyPressed || isTouched) {

			if (isTouched) {
				for (DPadButton button : dPadButtons) {

					if ((touchX >= button.getPosX() && touchX <= button.getPosX() + button.getWidth()) &&
							(gameHeight - touchY >= button.getPosY() && gameHeight - touchY <= button.getPosY() + button.getHeight())) {

						touchedButton = button;
						break;
					}
				}
			}
			// Gets the currently active sprite.
			Sprite playerSprite = new Sprite(player.getCurrentFrame(), (int)player.getPosition().x, (int)player.getPosition().y, player.getSpriteWidth(), player.getSpriteHeight());
			playerSprite.setPosition(player.getPosition().x, player.getPosition().y);
			Vector2 positionToMove = player.getPosition();
			Vector2 preMovePosition = new Vector2(player.getPosition());
			PushDirection pushDirection = null;

			playerSprite.setBounds(player.getPosition().x, player.getPosition().y, player.getHitboxDimensions().x, player.getHitboxDimensions().y);
			float newX = player.getPosition().x;
			float newY = player.getPosition().y;


			float speedDelta = player.getSpeed() * Gdx.graphics.getDeltaTime();


			if (touchedButton == null) {
				return;

			}
			else if (touchedButton == leftButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
				positionToMove = new Vector2(player.getPosition().x - speedDelta, player.getPosition().y);
				pushDirection = PushDirection.LEFT;
                newX -= player.getSpeed();


            }
			else if (touchedButton == rightButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
				positionToMove = new Vector2(player.getPosition().x + speedDelta, player.getPosition().y);
				pushDirection = PushDirection.RIGHT;
                newX += player.getSpeed();

            }

			else if (touchedButton == upButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
				positionToMove = new Vector2(player.getPosition().x, player.getPosition().y + speedDelta);
				pushDirection = PushDirection.UP;
                newY += player.getSpeed();

            }

			else if (touchedButton == downButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
				positionToMove = new Vector2(player.getPosition().x, player.getPosition().y - speedDelta);
				pushDirection = PushDirection.DOWN;
                newY -= player.getSpeed();

            }
			player.setPosition(positionToMove);
			// Ensure player has been moved for collision check
			//player.render();





			for (Trash trash: activeTrash) {
				Sprite trashSprite = new Sprite(trash.getCurrentFrame(), (int)trash.getPosition().x, (int)trash.getPosition().y, trash.getSpriteWidth(), trash.getSpriteHeight());
				trashSprite.setPosition(trash.getPosition().x, trash.getPosition().y);
				trashSprite.setBounds(trash.getPosition().x, trash.getPosition().y, trash.getHitboxDimensions().x, trash.getHitboxDimensions().y);



				if (playerSprite.getBoundingRectangle().overlaps(trashSprite.getBoundingRectangle())) {

					//System.out.println(playerSprite.getBoundingRectangle().toString());
					System.out.println(trashSprite.getBoundingRectangle().toString());

					trash.setDirectionOfMovement(pushDirection);
					trash.calculateRayCollisions();

					if (trash.isDirectionContainingPrevCollision(pushDirection)) {
						player.setPosition(preMovePosition);
						//player.render();



					}

					if (trash.trashToCheckCollide.size() == 0 && !trash.getIsPushing()) {
						player.setPosition(preMovePosition);
					}


					if (trash.isOverlapping() == true) {
						trash.setPushPosition(trash.push(pushDirection, speedDelta, player.isCanPush()));
						player.setCanPush(false);
						trash.setOverlapping(false);
						break;
					}


					//player.render();
					//break;
				} else if (!(playerSprite.getBoundingRectangle().overlaps(trashSprite.getBoundingRectangle())) && !trash.isOverlapping()) {
					trash.setOverlapping(true);
				}

			}

			boolean colliding = platform.doesRectCollideWithMap(positionToMove.x, positionToMove.y,
							16,16);



			for (Enemy enemy: activeLevel.getEnemies()) {
				Sprite enemySprite = new Sprite(
						enemy.getCurrentFrame(),
						(int) enemy.getPosition().x,
						(int) enemy.getPosition().y,
						enemy.getSpriteWidth(),
						enemy.getSpriteHeight()
				);
				enemySprite.setPosition(enemy.getPosition().x, enemy.getPosition().y);

				if (playerSprite.getBoundingRectangle().overlaps(enemySprite.getBoundingRectangle())) {
					player.setDead(true);
					gameState = GameState.FAIL;
					System.out.println("Player died!");
				}

			}



			/*
			if (colliding) {
				player.setPosition(preMovePosition);
				//player.render();
				System.out.println("collision detected");

			}

			 */


		}
	}

	public void newGame() {
		// TODO: Get variables stored in level class for stuff like amount of trash / orientation

		int trashCount = 5;

		for (int i = 0; i < trashCount - 1; i++) {

			Trash temp = new Trash();
			temp.create();
			activeTrash.add(temp);
			if (i == 0) {
				temp.setPosition(new Vector2(1000, 500));
			}

			if (i == 1) {
				temp.setPosition(new Vector2(1000, 700));
			}

			if (i == 2) {
				temp.setPosition(new Vector2(1000, 300));
			}


			temp.setActiveTrash(activeTrash);
			platform.setActiveTrash(activeTrash);
			temp.setPlayer(this.player);
			temp.setGame(this);

		}



	}

	public Ray createRay(float x, float y,PushDirection pushDirection) {
		Vector3 rayDirection = null;

		switch (pushDirection) {
			case UP:
				rayDirection = new Vector3(0, 1, 0);
				break;

			case DOWN:
				rayDirection = new Vector3(0, -1, 0);
				break;
			case LEFT:
				rayDirection = new Vector3(-1, 0, 0);
				break;

			case RIGHT:
				rayDirection = new Vector3(1, 0, 0);
				break;
		}

		Ray collisionRay = new Ray(new Vector3(x,y,0), new Vector3(rayDirection));
		return collisionRay;
	}


	public void RestartGame() {
		activeLevel.dispose();
		player.setPosition(player.getStartingPositon());
		activeLevel.create();

	}

}
