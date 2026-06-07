package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.world.Level;
import com.mygdx.game.world.Platform;
import com.mygdx.game.world.Constants;
import com.sun.java.accessibility.util.TopLevelWindowListener;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     main class that handles creating, updating, rendering and puts the game together
 */
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

	private Platform level1Platform;

	private Platform level2Platform;

	private List<Level> levels;

	private Level level1;
	private Level level2;

	private Level activeLevel;
	private DPadButton restartButton;

	SpriteBatch uiBatch;
	BitmapFont font;
	BitmapFont creditfont;
	Texture buttonTexture;
	Texture squareButtonTexture;

	//menu buttons
	Rectangle playButton;
	Rectangle exitButton;
	Rectangle tryAgainButton;
	Rectangle mainMenuButton;

	Rectangle nextLevelButton;

	private Sound pushSound;

	public Sound getPushSound() {
		return pushSound;
	}

	private Sound deathSound;
	private Sound menuEnter;
	private Sound menuExit;
	private Sound pause;
	private Sound levelTrans;

	private Sound levelComplete;

	private Sound levelStart;

	private Sound levelRestart;

	private boolean canPlayLevelSound = true;

	public Level getActiveLevel() {
		return activeLevel;
	}

	private void setupButtons() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		float bw = w * 0.25f;
		float bh = h * 0.1f;
		float cx = w / 2f - bw / 2f;

		playButton     = new Rectangle(cx, h * 0.55f, bw, bh);
		exitButton     = new Rectangle(cx, h * 0.42f, bw, bh);
		tryAgainButton = new Rectangle(cx, h * 0.48f, bw, bh);
		mainMenuButton = new Rectangle(cx, h * 0.35f, bw, bh);
		nextLevelButton = new Rectangle(cx, h * 0.2f, bw, bh);
	}

	/**
	 * <p>
	 *     load resources for each status in game and levels.
	 *     Loads objects, sounds and UI.
	 * </p>
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		font = new BitmapFont();
		creditfont = new BitmapFont();
		font.getData().setScale(5.0f);
		creditfont.getData().setScale(2f);
		buttonTexture = new Texture("Button/buttonSquare_blue.png");
		squareButtonTexture = new Texture("Button/buttonSquare_blue.png");
		setupButtons();


		leftButtonTexture = new Texture(Gdx.files.internal("Left_Key.png"));
		rightButtonTexture = new Texture(Gdx.files.internal("Right_Key.png"));
		upButtonTexture = new Texture(Gdx.files.internal("Up_Key.png"));
		downButtonTexture = new Texture(Gdx.files.internal("Down_Key.png"));
		restartButtonTexture = new Texture(Gdx.files.internal("restart_key.png"));

		pushSound = Gdx.audio.newSound(Gdx.files.internal("SFX/Bump.wav"));
		deathSound = Gdx.audio.newSound(Gdx.files.internal("SFX/Cancel.wav"));
		menuEnter = Gdx.audio.newSound(Gdx.files.internal("SFX/Menu_In.wav"));
		menuExit = Gdx.audio.newSound(Gdx.files.internal("SFX/Menu_Out.wav"));
		pause = Gdx.audio.newSound(Gdx.files.internal("SFX/Pause.wav"));
		levelTrans = Gdx.audio.newSound(Gdx.files.internal("SFX/Steps.wav"));
		levelComplete = Gdx.audio.newSound(Gdx.files.internal("SFX/Confirm.wav"));
		levelRestart = Gdx.audio.newSound(Gdx.files.internal("SFX/Trampoline.wav"));
		levelStart = Gdx.audio.newSound(Gdx.files.internal("SFX/Powerup.wav"));





		int trashHeight = 44;
		int trashWidth = 44;
		int padding = 4;

		player = new Player();
		player.create();


		int row1Height = 225;
		int row0Height = row1Height + trashHeight;
		int rowMinus1Height = row0Height + trashHeight;

		// LEVEL 1


		// Set positions of all trash in level, split up into 7 different rows

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



		int row2Height = row1Height - trashHeight;
		List<Vector2> level1Row2 = new ArrayList<Vector2>();
		level1Row2.add(new Vector2(250, row2Height));
		level1Row2.add(new Vector2(250 + trashWidth, row2Height));
		level1Row2.add(new Vector2(650, row2Height));
		level1Row2.add(new Vector2(650 + trashWidth, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth * 2, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth * 3, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth * 5, row2Height));
		level1Row2.add(new Vector2(1000 + trashWidth * 6, row2Height));


		level1Row2.add(new Vector2(1400 + trashWidth, row2Height));
		level1Row2.add(new Vector2(1400 + trashWidth * 2, row2Height));
		level1Row2.add(new Vector2(1400 + trashWidth * 3, row2Height));
		level1Row2.add(new Vector2(1400 + trashWidth * 6, row2Height));
		level1Row2.add(new Vector2(1400 + trashWidth * 7, row2Height));




		int row3Height = row2Height - trashHeight;

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
		level1Row3.add(new Vector2(1000 + trashWidth * 6, row3Height));


		level1Row3.add(new Vector2(1400, row3Height));
		level1Row3.add(new Vector2(1400 + trashWidth * 2, row3Height));
		//level1Row3.add(new Vector2(1400 + trashWidth * 6, row3Height));
		level1Row3.add(new Vector2(1400 + trashWidth * 7, row3Height));


		//level1Row3.add(new Vector2(1400 + trashWidth * 3, row3Height));


		int row4Height = row3Height - trashHeight;


		List<Vector2> level1Row4 = new ArrayList<Vector2>();
		level1Row4.add(new Vector2(250 + trashWidth, row4Height));
		level1Row4.add(new Vector2(500, row4Height));
		level1Row4.add(new Vector2(650, row4Height));
		level1Row4.add(new Vector2(650 + trashWidth, row4Height));
		level1Row4.add(new Vector2(1000 + trashWidth, row4Height));
		level1Row4.add(new Vector2(1000 + trashWidth * 5, row4Height));
		level1Row4.add(new Vector2(1000 + trashWidth * 6, row4Height));


		level1Row4.add(new Vector2(1400, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 2, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 3, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 6, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 7, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 8, row4Height));
		level1Row4.add(new Vector2(1400 + trashWidth * 9, row4Height));


		int row5Height = row4Height - trashHeight;
		List<Vector2> level1Row5 = new ArrayList<Vector2>();
		level1Row5.add(new Vector2(250, row5Height));
		level1Row5.add(new Vector2(250 + trashWidth, row5Height));
		level1Row5.add(new Vector2(650, row5Height));
		level1Row5.add(new Vector2(650 + trashWidth, row5Height));
		level1Row5.add(new Vector2(1000, row5Height));
		level1Row5.add(new Vector2(1000 + trashWidth, row5Height));
		level1Row5.add(new Vector2(1000 + trashWidth * 2, row5Height));
		level1Row5.add(new Vector2(1000 + trashWidth * 5, row5Height));
		level1Row5.add(new Vector2(1000 + trashWidth * 6, row5Height));

		level1Row5.add(new Vector2(1400, row5Height));
		level1Row5.add(new Vector2(1400 + trashWidth * 2, row5Height));

		List<List<Vector2>> level1TrashPos = new ArrayList<List<Vector2>>();

		level1TrashPos.add(level1RowMinusOne);

		level1TrashPos.add(level1Row0);
		level1TrashPos.add(level1Row1);
		level1TrashPos.add(level1Row2);
		level1TrashPos.add(level1Row3);
		level1TrashPos.add(level1Row4);
		level1TrashPos.add(level1Row5);


		// Set up enemy positions, direction and speeds to be passed into the level constructor.

		List<Vector2> level1EnemyPos = new ArrayList<Vector2>();
		List<PushDirection> level1EnemyDirs = new ArrayList<>();
		List<Float> level1EnemySpeeds = new ArrayList<>();
		float defaultEnemySpeed = 100;

		Vector2 level1EnemyPos1 = new Vector2(250 - trashWidth, row1Height + 15);
		level1EnemyDirs.add(PushDirection.DOWN);
		level1EnemySpeeds.add(defaultEnemySpeed);


		Vector2 level1EnemyPos2 = new Vector2(500 + trashWidth, row1Height + 15);
		level1EnemyDirs.add(PushDirection.DOWN);
		level1EnemySpeeds.add(defaultEnemySpeed);


		Vector2 level1EnemyPos3 = new Vector2(650 + trashWidth * 2, row3Height + 15);
		level1EnemyDirs.add(PushDirection.DOWN);
		level1EnemySpeeds.add(defaultEnemySpeed);


		Vector2 level1EnemyPos4 = new Vector2(1400 + trashWidth * 5, row5Height - 2);
		level1EnemyDirs.add(PushDirection.DOWN);
		level1EnemySpeeds.add(defaultEnemySpeed + 200);


		level1EnemyPos.add(level1EnemyPos1);
		level1EnemyPos.add(level1EnemyPos2);
		level1EnemyPos.add(level1EnemyPos3);
		level1EnemyPos.add(level1EnemyPos4);



		// LEVEL 2

		List<Vector2> level2Row0 = new ArrayList<Vector2>();
		level2Row0.add(new Vector2(650 + trashWidth * 7, row0Height));
		level2Row0.add(new Vector2(650 + trashWidth * 6, row0Height));





		List<Vector2> level2Row1 = new ArrayList<Vector2>();
		level2Row1.add(new Vector2(650 + trashWidth * 4, row1Height));
		level2Row1.add(new Vector2(650 + trashWidth * 2, row1Height));
		level2Row1.add(new Vector2(650 + trashWidth * 11, row1Height));
		level2Row1.add(new Vector2(650 + trashWidth * 6, row1Height));
		level2Row1.add(new Vector2(650 + trashWidth * 7, row1Height));



		List<Vector2> level2Row2 = new ArrayList<Vector2>();

		level2Row2.add(new Vector2(650, row2Height));
		level2Row2.add(new Vector2(650 + trashWidth * 3, row2Height));
		level2Row2.add(new Vector2(650 + trashWidth * 11, row2Height));


		List<Vector2> level2Row3 = new ArrayList<Vector2>();

		level2Row3.add(new Vector2(650, row3Height));
		level2Row3.add(new Vector2(650 + trashWidth, row3Height));
		level2Row3.add(new Vector2(650 + trashWidth * 2, row3Height));
		level2Row3.add(new Vector2(650 + trashWidth * 3, row3Height));
		level2Row3.add(new Vector2(650 + trashWidth * 11, row3Height));

		List<Vector2> level2Row4 = new ArrayList<Vector2>();
		level2Row4.add(new Vector2(650 + trashWidth * 11, row4Height));


		List<Vector2> level2Row5 = new ArrayList<Vector2>();
		level2Row5.add(new Vector2(650, row5Height));

		level2Row5.add(new Vector2(650 + trashWidth * 2, row5Height));
		level2Row5.add(new Vector2(650 + trashWidth * 7, row5Height));

		level2Row5.add(new Vector2(650 + trashWidth * 11, row5Height));


		List<List<Vector2>> level2TrashPos = new ArrayList<List<Vector2>>();

		level2TrashPos.add(level2Row0);
		level2TrashPos.add(level2Row1);
		level2TrashPos.add(level2Row2);
		level2TrashPos.add(level2Row3);
		level2TrashPos.add(level2Row4);
		level2TrashPos.add(level2Row5);


		List<Vector2> level2EnemyPos = new ArrayList<>();
		List<PushDirection> level2EnemyDir = new ArrayList<>();
		List<Float> level2EnemySpeeds = new ArrayList<>();


		Vector2 level2EnemyPos1 = new Vector2(150, row3Height);
		level2EnemyDir.add(PushDirection.DOWN);
		level2EnemySpeeds.add(defaultEnemySpeed - 5);


		Vector2 level2EnemyPos2 = new Vector2(150 + trashWidth, row3Height) ;
		level2EnemyDir.add(PushDirection.UP);
		level2EnemySpeeds.add(defaultEnemySpeed - 5);


		Vector2 level2EnemyPos3 = new Vector2(150 + trashWidth * 2, row3Height);
		level2EnemyDir.add(PushDirection.DOWN);
		level2EnemySpeeds.add(defaultEnemySpeed - 5);


		Vector2 level2EnemyPos4 = new Vector2(150 + trashWidth * 3, row3Height) ;
		level2EnemyDir.add(PushDirection.UP);
		level2EnemySpeeds.add(defaultEnemySpeed - 5);


		Vector2 level2EnemyPos5 = new Vector2(150 + trashWidth * 4, row3Height) ;
		level2EnemyDir.add(PushDirection.DOWN);
		level2EnemySpeeds.add(defaultEnemySpeed - 5);


		Vector2 level2EnemyPos6 = new Vector2(650 + trashWidth * 10, row0Height) ;
		level2EnemyDir.add(PushDirection.DOWN);
		level2EnemySpeeds.add(defaultEnemySpeed + 80);


		Vector2 level2EnemyPos7 = new Vector2(650 + trashWidth * 9, row0Height) ;
		level2EnemyDir.add(PushDirection.DOWN);
		level2EnemySpeeds.add(defaultEnemySpeed + 80);


		Vector2 level2EnemyPos8 = new Vector2(650 + trashWidth * 8, row0Height) ;
		level2EnemyDir.add(PushDirection.DOWN);
		level2EnemySpeeds.add(defaultEnemySpeed + 80);


		level2EnemyPos.add(level2EnemyPos1);
		level2EnemyPos.add(level2EnemyPos2);
		level2EnemyPos.add(level2EnemyPos3);
		level2EnemyPos.add(level2EnemyPos4);
		level2EnemyPos.add(level2EnemyPos5);
		level2EnemyPos.add(level2EnemyPos6);
		level2EnemyPos.add(level2EnemyPos7);
		level2EnemyPos.add(level2EnemyPos8);


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


		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		camera.update();

		player.setCamera(camera);

		level1Platform = new Platform("Level1.tmx");
		level2Platform = new Platform("Level2.tmx");


		newGame();

		gameState = GameState.MENU;

		levels = new ArrayList<>();

		level1 = new Level(level1TrashPos, level1EnemyPos, player.getStartingPositon(), player, level1Platform, this, level1EnemyDirs, level1EnemySpeeds, 1925);
		level2 = new Level(level2TrashPos, level2EnemyPos, player.getStartingPositon(), player, level2Platform, this, level2EnemyDir, level2EnemySpeeds, 1350);

		level1.create();

		levels.add(level1);
		levels.add(level2);
		activeLevel = level1;
		activeTrash = activeLevel.getTrash();
	}

	/**
	 * renders the frames of the game and updates camera positioning
	 */
	@Override
	public void render() {
		update();
		ScreenUtils.clear(0, 0, 0, 1);


		boolean isTouched = Gdx.input.isTouched();

		camera.position.x = player.getPosition().x + player.getSpriteWidth() / 2f;
		camera.position.y = Constants.WORLD_HEIGHT / 2f;
		camera.update();

		activeLevel.getPlatform().render(camera, batch);
		activeLevel.render();
		player.render();
		restartButton.render();


		// Render D-Pad only when playing
		if (gameState == GameState.PLAYING) {
			for (DPadButton button : dPadButtons) {
				button.render();
			}
		}

		// Draw the menu corresponding to the state that the game is in.
		uiBatch.begin();
		if (gameState == GameState.MENU) {
			font.setColor(Color.WHITE);
			font.draw(uiBatch, "BROOMBA", playButton.x + 40, playButton.y + playButton.height + 100);
			uiBatch.draw(buttonTexture, playButton.x, playButton.y, playButton.width, playButton.height);
			font.draw(uiBatch, "PLAY", playButton.x + playButton.width * 0.33f, playButton.y + playButton.height * 0.7f);
			uiBatch.draw(buttonTexture, exitButton.x, exitButton.y, exitButton.width, exitButton.height);
			font.draw(uiBatch, "EXIT", exitButton.x + exitButton.width * 0.33f, exitButton.y + exitButton.height * 0.7f);

			creditfont.draw(uiBatch, "CC BY SA 4.0: TileMaps made by https://tilation.itch.io/", playButton.x - 690, playButton.y + playButton.height - 500);
			creditfont.draw(uiBatch, "CC BY SA 4.0: Keys made by hyohnoo.itch.io/keyboard-controller-keys", playButton.x - 690, playButton.y + playButton.height - 550);
			creditfont.draw(uiBatch, "Sounds made by coffeevalenbat.itch.io/sweet-sounds-sfx-pack", playButton.x - 690, playButton.y + playButton.height - 600);


		} else if (gameState == GameState.FAIL) {
			font.setColor(Color.RED);
			font.draw(uiBatch, "GAME OVER", Gdx.graphics.getWidth() / 2f - 210, Gdx.graphics.getHeight() * 0.7f);
			uiBatch.draw(buttonTexture, tryAgainButton.x, tryAgainButton.y, tryAgainButton.width, tryAgainButton.height);
			font.setColor(Color.WHITE);
			font.draw(uiBatch, "TRY AGAIN", tryAgainButton.x + tryAgainButton.width * 0.12f, tryAgainButton.y + tryAgainButton.height * 0.7f);

		} else if (gameState == GameState.SUCCESS) {
			font.setColor(Color.GREEN);
			font.draw(uiBatch, "YOU WIN!", Gdx.graphics.getWidth() / 2f - 160, Gdx.graphics.getHeight() * 0.7f);
			uiBatch.draw(buttonTexture, tryAgainButton.x, tryAgainButton.y, tryAgainButton.width, tryAgainButton.height);
			font.setColor(Color.WHITE);
			font.draw(uiBatch, "PLAY AGAIN", tryAgainButton.x + tryAgainButton.width * 0.1f, tryAgainButton.y + tryAgainButton.height * 0.7f);
			uiBatch.draw(buttonTexture, mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height);
			font.draw(uiBatch, "MAIN MENU", mainMenuButton.x + mainMenuButton.width * 0.1f, mainMenuButton.y + mainMenuButton.height * 0.7f);

			if (activeLevel == level1) {
				uiBatch.draw(buttonTexture, nextLevelButton.x, nextLevelButton.y, nextLevelButton.width, nextLevelButton.height);
				font.draw(uiBatch, "NEXT LEVEL", nextLevelButton.x + nextLevelButton.width * 0.07f, nextLevelButton.y + nextLevelButton.height * 0.7f);
			}
		}
		uiBatch.end();
	}

	/**
	 * <p>
	 *     disposes resources to free up memory.
	 * </p>
	 */
	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		level1Platform.dispose();
	}

	/**
	 * <p>
	 *     updates logic of resources created.
	 *     updates logic for functions like player movement, buttons and collision for objects.
	 * </p>
	 */
	public void update() {

		if (gameState == GameState.MENU) {

			if (Gdx.input.justTouched()) {

				float touchX = Gdx.input.getX();
				float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

				if (playButton.contains(touchX, touchY)) {
					menuEnter.play(1.0f);
					gameState = GameState.PLAYING;
					RestartGame();
				}

				if (exitButton.contains(touchX, touchY)) {
					Gdx.app.exit();
				}
			}

			return;
		}

		// canPlayLevelSound is set to false here to ensure it is only played once.
		if (gameState == GameState.SUCCESS && canPlayLevelSound) {
			levelComplete.play(1.0f);
			canPlayLevelSound = false;

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


		float touchScreenX = Gdx.input.getX();
		float touchScreenY = Gdx.graphics.getHeight() - Gdx.input.getY();

		if (gameState == GameState.SUCCESS&& Gdx.input.justTouched()) {
			if (nextLevelButton.contains(touchScreenX, touchScreenY) && activeLevel == level1) {
				switchLevel(level2);
				gameState = GameState.PLAYING;

			} else if (tryAgainButton.contains(touchScreenX, touchScreenY)) {
				RestartGame();
				gameState = GameState.PLAYING;


			} else if (mainMenuButton.contains(touchScreenX, touchScreenY)) {
				gameState = GameState.MENU;
				menuExit.play(1.0f);
			}


		}

		if (gameState == GameState.FAIL) {
			if (Gdx.input.justTouched()) {


				if (tryAgainButton.contains(touchScreenX, touchScreenY)) {
					RestartGame();
				}
			}

			return;
		}

		boolean keyPressed = false;
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) ||
				Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
			keyPressed = true;
		}
		if (keyPressed || isTouched) {


			// Determines which button has been pressed by the user.
			if (isTouched) {
				for (DPadButton button : dPadButtons) {

					if ((touchX >= button.getPosX() && touchX <= button.getPosX() + button.getWidth()) &&
							(gameHeight - touchY >= button.getPosY() && gameHeight - touchY <= button.getPosY() + button.getHeight())) {

						touchedButton = button;
						break;
					}
				}
			}


			// Gets the currently active sprite to perform collision checks.
			Sprite playerSprite = new Sprite(player.getCurrentFrame(), (int)player.getPosition().x, (int)player.getPosition().y, player.getSpriteWidth(), player.getSpriteHeight());
			playerSprite.setPosition(player.getPosition().x, player.getPosition().y);
			Vector2 positionToMove = player.getPosition();
			Vector2 preMovePosition = new Vector2(player.getPosition());
			PushDirection pushDirection = null;

			playerSprite.setBounds(player.getPosition().x, player.getPosition().y, player.getHitboxDimensions().x, player.getHitboxDimensions().y);
			float newX = player.getPosition().x;
			float newY = player.getPosition().y;


			float speedDelta = player.getSpeed() * Gdx.graphics.getDeltaTime();


			if (touchedButton == null && !keyPressed) {
				return;

			}
			
			if (touchedButton == leftButton || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
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

			for (Trash trash: activeTrash) {
				Sprite trashSprite = new Sprite(trash.getCurrentFrame(), (int)trash.getPosition().x, (int)trash.getPosition().y, trash.getSpriteWidth(), trash.getSpriteHeight());
				trashSprite.setPosition(trash.getPosition().x, trash.getPosition().y);
				trashSprite.setBounds(trash.getPosition().x, trash.getPosition().y, trash.getHitboxDimensions().x, trash.getHitboxDimensions().y);



				if (playerSprite.getBoundingRectangle().overlaps(trashSprite.getBoundingRectangle())) {

					System.out.println(trashSprite.getBoundingRectangle().toString());

					trash.setDirectionOfMovement(pushDirection);
					trash.calculateRayCollisions();

					if (trash.isDirectionContainingPrevCollision(pushDirection)) {
						player.setPosition(preMovePosition);

					}

					if (trash.trashToCheckCollide.size() == 0 && !trash.getIsPushing()) {
						player.setPosition(preMovePosition);
					}

					// Helps prevent player from pushing trash if the player is not currently moving
					if (trash.isOverlapping() == true) {
						pushSound.play(1.0f);
						trash.setPushPosition(trash.push(pushDirection, speedDelta, player.isCanPush()));
						player.setCanPush(false);
						trash.setOverlapping(false);
						break;
					}

				// Allows player to push again when overlapping ends.
				} else if (!(playerSprite.getBoundingRectangle().overlaps(trashSprite.getBoundingRectangle())) && !trash.isOverlapping()) {
					trash.setOverlapping(true);
				}

			}

			boolean colliding = level1Platform.doesRectCollideWithMap(positionToMove.x, positionToMove.y,
							16,16);





			boolean playerMapColliding = activeLevel.getPlatform().doesRectCollideWithMap(
					positionToMove.x,
					positionToMove.y,
					player.getSpriteWidth(),
					player.getSpriteHeight()
			);

			if (playerMapColliding) {
				player.setPosition(preMovePosition);
				System.out.println("collision detected");
			}

		}
		Sprite playerSprite = new Sprite(
				player.getCurrentFrame(),
				(int) player.getPosition().x,
				(int) player.getPosition().y,
				player.getSpriteWidth(),
				player.getSpriteHeight()
		);
		playerSprite.setPosition(player.getPosition().x, player.getPosition().y);


		// Enemy collision
		for (Enemy enemy : activeLevel.getEnemies()) {
			Sprite enemySprite = new Sprite(
					enemy.getCurrentFrame(),
					(int) enemy.getPosition().x,
					(int) enemy.getPosition().y,
					enemy.getSpriteWidth(),
					enemy.getSpriteHeight()
			);
			enemySprite.setPosition(enemy.getPosition().x, enemy.getPosition().y);
			enemySprite.setBounds(enemy.getPosition().x, enemy.getPosition().y, enemy.getHitboxDimensions().x, enemy.getHitboxDimensions().y);

			if (playerSprite.getBoundingRectangle().overlaps(enemySprite.getBoundingRectangle())) {
				player.setDead(true);
				deathSound.play(1.0f);
				gameState = GameState.FAIL;
				System.out.println("Player died!");
			}
		}
	}
	// OBSOLETE
	public void newGame() {
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
			level1Platform.setActiveTrash(activeTrash);
			temp.setPlayer(this.player);
			temp.setGame(this);

		}



	}

	/**
	 *
	 *
	 *<p> Creates a ray to be used for trash collisions.</p>
	 * @param x The starting x position of the ray.
	 * @param y The starting y position of the ray
	 * @param pushDirection The direction that a trash has been pushed, used to
	 *                      set the directional componenet of the ray.
	 * @return The ray object pointing in the direction of travel.
	 */
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

	/**
	 *
	 * <p> Reset and dispose of all current level elements, recreate the level, and
	 * 		reset player variables.</p>
	 *
	 */
	public void RestartGame() {
		canPlayLevelSound = true;
		levelRestart.play(1.0f);
		activeLevel.dispose();

		player.setDead(false);
		player.setPosition(new Vector2(player.getStartingPositon()));

		activeLevel.create();

		activeTrash = activeLevel.getTrash();
		player.setCanPush(true);

		gameState = GameState.PLAYING;
	}

	/**
	 * <p> Switches between the current level and a new one.
	 * </p>
	 * @param level The level to switch to.
	 *
	 */
	public void switchLevel(Level level) {
		canPlayLevelSound = true;
		levelTrans.play(1.0f);

		activeLevel.dispose();

		player.setDead(false);
		player.setCanPush(true);
		player.setPosition(new Vector2(player.getStartingPositon()));


		activeLevel = level;
		activeTrash = level.getTrash();

		level.create();



	}

}
