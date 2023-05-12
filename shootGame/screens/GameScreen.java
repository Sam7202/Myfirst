package screens;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ShootGame;

import ships.EnemyShips;
import effects.Explosion;
import effects.HitEffect;
import effects.Laser;
import effects.LowLifeEffect;
import ships.PlayerShip;
public class GameScreen  implements Screen {
	ShootGame game;
	//Screen
	private Camera camera;
	private Viewport viewport;


	//graphics
	//	private game.batch game.batch;
	private TextureRegion[] backgrounds ;
	private TextureAtlas textureAtlas;
	private final float PRESERVED_PIXEL = 0.5f;
	private TextureRegion playerShipTextureRegion, playerSheildTextureRegion,
	enemyShipTextureRegion, enemySheildTextureRegion,
	playerLaserTextureRegion, enemyLaserTextureRegion;
	private Texture enemyhitEffectTexture;
	private Texture playerhitEffectTexture;
	private Texture explosionTexture;
	private Texture boss;

	//timing
	private float[] backgroundOffsets = {0,0,0,0};
	private float backdroundMaxScrollingSpeed;
	private float timeBetweenEnemySpawn = 3f;
	private float timeSinceLastSpawn = 0;
	
	//gameover explosion times
	final int GAMEOVER_EXPLOSION_TIMES = 3;
	int TimeshasExploed = 0;
	
	//world parameters
	public static final float WORLD_WIDTH = 72;
	public static final float WORLD_HEIGHT = 128;
	private final float TOUCH_LIMIT = 0.5f;

	//game objects
	private PlayerShip playerShip;
	private LinkedList<EnemyShips> enemyShipList;
	private LinkedList<Laser> playerLaserList;
	private LinkedList<Laser> enemyLaserList;
	private LinkedList<HitEffect> playerHitEffectList;
	private LinkedList<HitEffect> enemyHitEffectList;
	private LinkedList<Explosion> explosionList;
	private LinkedList<Explosion> playerExplosionList;

	//audio
	Music backgroundMusic;

	//Heads-Up Display
	private int score = 0;
	BitmapFont font;
	float hudVerticalMargine, hudLeftx, hudRightx, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;


	public GameScreen(ShootGame game){
		this.game = game;

		camera = new OrthographicCamera();
		viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

		//setting up the texture atlas
		textureAtlas = new TextureAtlas("images.atlas");

		//setting up the background
		backgrounds = new TextureRegion[4];
		backgrounds[0] = textureAtlas.findRegion("Starscape00");
		backgrounds[1] = textureAtlas.findRegion("Starscape01");
		backgrounds[2] = textureAtlas.findRegion("Starscape02");
		backgrounds[3] = textureAtlas.findRegion("Starscape03");

		backdroundMaxScrollingSpeed = (float)WORLD_HEIGHT / 4;

		//initialize texture regions && texture
		playerShipTextureRegion = textureAtlas.findRegion("playerShip1_red");
		playerSheildTextureRegion = textureAtlas.findRegion("shield1");
		playerLaserTextureRegion = textureAtlas.findRegion("laserBlue16");
		playerhitEffectTexture = 
				new Texture(Gdx.files.internal("laserBlue08.png"));

		enemyShipTextureRegion = textureAtlas.findRegion("enemyBlack2");
		enemySheildTextureRegion = textureAtlas.findRegion("shield2");
		enemyLaserTextureRegion = textureAtlas.findRegion("laserRed13");
		enemySheildTextureRegion.flip(false, true);
		enemyhitEffectTexture = 
				new Texture(Gdx.files.internal("laserRed11.png"));
		//		boss = new Texture("big boss.png");
		//		enemyShipTextureRegion = new TextureRegion(boss);

		//set up game object
		playerShip = new PlayerShip(50, 3, WORLD_WIDTH/2, WORLD_HEIGHT/4, 10, 10,
				0.8f, 4, 60, 0.3f,
				playerShipTextureRegion, playerSheildTextureRegion, playerLaserTextureRegion);
		playerLaserList = new LinkedList<>();
		playerHitEffectList = new LinkedList<>();

		enemyShipList = new LinkedList<>();
		enemyLaserList = new LinkedList<>();
		enemyHitEffectList = new LinkedList<>();

		explosionTexture = new Texture("explosion.png");
		explosionList = new LinkedList<>();
		playerExplosionList = new LinkedList<>();


		prepareHUD();
		
		//set up audio
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Among Us Trap Remix.wav")); 
		backgroundMusic.setVolume(0.2f);
		backgroundMusic.play();
		backgroundMusic.setLooping(true);

	}
	private void prepareHUD() {
		//create a BitmapFont from our font files
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(
				"SpaceArmor-vmlv4.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = 
				new FreeTypeFontGenerator.FreeTypeFontParameter();

		fontParameter.size = 78;
		fontParameter.borderWidth = 10.5f;
		fontParameter.color = new Color(1,1,1,0.35f); 
		fontParameter.borderColor = new Color(0, 0, 0, 0.3f);
		font = fontGenerator.generateFont(fontParameter);

		//scale the font to fit screen 
		font.setUseIntegerPositions(false);//important!!
		font.getData().setScale(0.035f,0.07f);

		//calculate HUD border, etc.
		hudVerticalMargine =font.getCapHeight()/3;
		hudLeftx = hudVerticalMargine;
		hudRightx = WORLD_WIDTH*2/3 - hudLeftx;
		hudCentreX = WORLD_WIDTH/3;
		hudRow1Y = WORLD_HEIGHT - hudVerticalMargine -1;
		hudRow2Y = hudRow1Y - hudVerticalMargine - font.getCapHeight();
		hudSectionWidth = WORLD_WIDTH/3;
	}
	@Override
	public void show() {
		//		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void render(float deltaTime) {
		game.batch.begin();
		//clean the screen
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//check end
		if(playerShip.destroyed) {
			backgroundMusic.stop();
			renderBackground(deltaTime);
			for(EnemyShips enemyShip : enemyShipList) {
				enemyShip.draw(game.batch);
				enemyShip.update(deltaTime);

			}
			//			playerShip.draw(game.batch);
			renderPlayerExplosions(deltaTime);

		}
		else {

			//scrolling background
			renderBackground(deltaTime);

			//enemy ships
			spawnEnemyShips(deltaTime);
			for(EnemyShips enemyShip : enemyShipList) {

				enemyShip.draw(game.batch);
				enemyShip.update(deltaTime);
			}

			//player ship
			playerShip.draw(game.batch);
			playerShip.update(deltaTime);

			//lasers
			renderLasers(deltaTime);

			//hit effects
			renderHitEffects(deltaTime);
			
			//low life effects
			renderlowLifeEffects(deltaTime);

			//explosions
			renderExplosions(deltaTime);

			//detect keyboard input
			keyBoardInput();

			//touch input(mouse)
			touchInput();

			//HUD rendering
			updateAndRenderHUD();

			//detect collisions between lasers and ships
			detectCollisions();

		}
		game.batch.end();
	}

	private void renderlowLifeEffects(float deltaTime) {
		if(playerShip.lowLife) {
			playerShip.updateLowLife(deltaTime, game.batch);
		}
	}
	private void renderHitEffects(float deltaTime) {
		Iterator<HitEffect> iterator = playerHitEffectList.iterator();
		while(iterator.hasNext()) {
			HitEffect hitEffect = iterator.next();
			hitEffect.draw(game.batch);
			iterator.remove();
		}
		iterator = enemyHitEffectList.iterator();
		while(iterator.hasNext()) {
			HitEffect hitEffect = iterator.next();
			hitEffect.draw(game.batch);
			iterator.remove();

		}

	}
	private void renderPlayerExplosions(float deltaTime) {

		if(playerExplosionList.isEmpty()) {
			game.setScreen(new GameOverScreen(game, score));
			this.dispose();
		}
		//		for(int i = 0;i<playerExplosionList.size();i++) {
		else {


			if(playerExplosionList.getFirst().isFinished()) {
				//				playerExplosionList.getFirst().playsound();
				playerExplosionList.removeFirst();
				if(TimeshasExploed< GAMEOVER_EXPLOSION_TIMES) {

					playerExplosionList.add( new Explosion(explosionTexture,
							playerShip.xPosition+5,
							playerShip.yPosition,
							playerShip.width,
							playerShip.height,
							0.3f));
					TimeshasExploed++;
				}
			}
			else {

				playerExplosionList.getFirst().draw(game.batch);
				playerExplosionList.getFirst().update(deltaTime);

			}
		}
		//		}
	}
	private void updateAndRenderHUD() {
		// render top row labels
		font.draw(game.batch, "Score", hudLeftx, hudRow1Y, hudSectionWidth, Align.left,false);
		font.draw(game.batch, "Sheild", hudCentreX, hudRow1Y, hudSectionWidth, Align.center,false);
		font.draw(game.batch, "Lives", hudRightx, hudRow1Y, hudSectionWidth, Align.right,false);

		//render second row values
		font.draw(game.batch, String.format(Locale.getDefault(), "%05d", score), hudLeftx, hudRow2Y, hudSectionWidth , Align.left, false);
		font.draw(game.batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
		font.draw(game.batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), hudRightx, hudRow2Y, hudSectionWidth, Align.right, false);
	}
	private void spawnEnemyShips(float deltaTime) {
		if(timeSinceLastSpawn > timeBetweenEnemySpawn) {
			enemyShipList.add(new EnemyShips(40, 3, WORLD_WIDTH/2, WORLD_HEIGHT*3/4, 10, 10,
					0.6f, 5, 30, 1.0f,
					enemyShipTextureRegion, enemySheildTextureRegion, enemyLaserTextureRegion));
			timeSinceLastSpawn = 0;
		}
		timeSinceLastSpawn += deltaTime;
	}
	private void touchInput() {
		if(Gdx.input.isTouched()) {
			//get touched position
			float xTouchPoint = Gdx.input.getX();
			float yTouchPoint = Gdx.input.getY();
			float movementSpeed = playerShip.movementSpeed;
			float deltaTime = Gdx.graphics.getDeltaTime();
			float xChange = 0;
			float yChange = 0;

			//convert to world coordinate	
			Vector2 touchPoint = new Vector2(xTouchPoint, yTouchPoint);
			touchPoint = viewport.unproject(touchPoint);

			Vector2 shipCentre = new Vector2(playerShip.xPosition + playerShip.width/2,
					playerShip.yPosition + playerShip.height/2);

			float touchDistance = touchPoint.dst(shipCentre);

			float xTouchDiffrence = touchPoint.x - shipCentre.x;
			float yTouchDiffrence = touchPoint.y - shipCentre.y;

			if(touchDistance >= TOUCH_LIMIT) {
				if(xTouchDiffrence < 0 && playerShip.xPosition>=PRESERVED_PIXEL 
						|| xTouchDiffrence > 0 && playerShip.xPosition + playerShip.width <= WORLD_WIDTH-PRESERVED_PIXEL)
					xChange = xTouchDiffrence/touchDistance * movementSpeed * deltaTime;
				if(yTouchDiffrence < 0 && playerShip.yPosition>=PRESERVED_PIXEL
						|| yTouchDiffrence > 0 && playerShip.yPosition + playerShip.height <= WORLD_HEIGHT/2)
					yChange = yTouchDiffrence/touchDistance * movementSpeed * deltaTime;

				 playerShip.move(xChange,yChange);
			}

		}

	}
	private void keyBoardInput() {
		float xChange = 0 ;
		float movementSpeed = playerShip.movementSpeed;
		float yChange = 0;
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Keys.LEFT) && playerShip.xPosition >= PRESERVED_PIXEL) {
			xChange = -movementSpeed * deltaTime ;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) && playerShip.xPosition + playerShip.width <= WORLD_WIDTH - PRESERVED_PIXEL) 
			xChange = movementSpeed * deltaTime;
		if(Gdx.input.isKeyPressed(Keys.UP) && playerShip.yPosition <= WORLD_HEIGHT/2 - playerShip.height) 
			yChange = movementSpeed * deltaTime;
		if(Gdx.input.isKeyPressed(Keys.DOWN) && playerShip.yPosition >= PRESERVED_PIXEL) 
			yChange = -movementSpeed * deltaTime;
		((PlayerShip) playerShip).move(xChange,yChange);
	}
	private void detectCollisions() {

		//for each player laser, check whether it intersect an enemy ship
		ListIterator<Laser> iterator = playerLaserList.listIterator();
		while(iterator.hasNext()) {
			Laser laser = iterator.next();
			for(int i = 0;i<enemyShipList.size();i++) {
				if(enemyShipList.get(i).intersects(laser.getBoundingBox())) {
					//contact with enemy ships
					iterator.remove();
					playerHitEffectList.add(new HitEffect(laser.xPosition +laser.width/2, laser.yPosition+laser.height,
							5, 5, playerhitEffectTexture));
					enemyShipList.get(i).hit(laser , game.batch );
					if(enemyShipList.get(i).destroyed == true) {
						explosionList.add(new Explosion(explosionTexture,
								enemyShipList.get(i).xPosition,
								enemyShipList.get(i).yPosition,
								enemyShipList.get(i).width,
								enemyShipList.get(i).height,
								0.5f));

						enemyShipList.remove(i);
						i--;
						//add score when enemy are destroyed
						score+=10;
					}
					//if a playerlaser collide with enemyship then check next laser
					break;	

				}
			}
		}
		//for each enemy laser, check whether it intersect an enemy ship
		iterator = enemyLaserList.listIterator();
		while(iterator.hasNext()) {
			Laser laser = iterator.next();
			if(playerShip.intersects(laser.getBoundingBox())) {
				//contact with player ship
				iterator.remove();
				playerShip.hit(laser ,game.batch);
				enemyHitEffectList.add(new HitEffect(laser.xPosition+laser.width/2, laser.yPosition
						, 5, 5, enemyhitEffectTexture)); 
				if(playerShip.destroyed == true) {
					//add five time explosion when player explode
					//					for(int i = 0; i < 5 ;i++) {

					playerExplosionList.add( new Explosion(explosionTexture,
							playerShip.xPosition,
							playerShip.yPosition,
							playerShip.width,
							playerShip.height,
							0.3f));
					//					}

				}
			}
		}

	}
	private void renderExplosions(float deltaTime) {
		ListIterator<Explosion> explosionIterator = explosionList.listIterator();

		while(explosionIterator.hasNext()) {
			Explosion explosion = explosionIterator.next();

			if(explosion.isFinished()) {
				explosionIterator.remove();
			}
			else {
				explosion.draw(game.batch);
				explosion.update(deltaTime);

			}

		}
	}
	private void renderLasers(float deltaTime) {
		//create news lasers

		//player lasers
		if(playerShip.canFireLaser()) {
			Laser[] lasers = playerShip.fireLasers();
			for(Laser laser : lasers) {
				playerLaserList.add(laser);
			}
		}
		//enemy lasers
		for(EnemyShips enemyShip : enemyShipList) {
			if(enemyShip.canFireLaser()) {
				Laser[] lasers = enemyShip.fireLasers();
				for(Laser laser : lasers) {
					enemyLaserList.add(laser);
				}
			}
		}
		//draw lasers
		ListIterator<Laser> iterator = playerLaserList.listIterator();
		while(iterator.hasNext()) {
			Laser laser = iterator.next();
			laser.yPosition += laser.movementspeed * deltaTime;
			laser.draw(game.batch);

			//remove old lasers
			if(laser.yPosition > WORLD_HEIGHT) {
				iterator.remove();
			}
		}
		iterator = enemyLaserList.listIterator();
		while(iterator.hasNext()) {
			Laser laser = iterator.next();
			laser.yPosition -= laser.movementspeed * deltaTime;
			laser.draw(game.batch);

			//remove old lasers
			if(laser.yPosition + laser.height < 0) {
				iterator.remove();
			}
		}
	}
	private void renderBackground(float deltaTime) {
		for(int i=0;i<backgroundOffsets.length ;i++) {
			backgroundOffsets[i] += deltaTime * (backdroundMaxScrollingSpeed / Math.pow(2, 3-i));
		}

		for(int i=0;i<backgroundOffsets.length ;i++) {
			if(backgroundOffsets[i] > WORLD_HEIGHT) {
				backgroundOffsets[i] = 0;
			}
			game.batch.draw(backgrounds[i], 0, -backgroundOffsets[i], WORLD_WIDTH, WORLD_HEIGHT);
			game.batch.draw(backgrounds[i], 0, -backgroundOffsets[i] + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);

		}
	}
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);

		game.batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void pause() {
		//		stop=true;
		System.out.println(" pause called");
	}

	@Override
	public void resume() {
		//		stop = false;
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		
		//dispose audio
		Laser.LASER_SOUND.dispose();
		LowLifeEffect.LOW_LIVES_ALARM.dispose();
		backgroundMusic.dispose();
		Explosion.EXPLOSION_SOUND.dispose();
		PlayerShip.SHIELD_DOWN_SOUND.dispose();
		HitEffect.HIT_SOUND.dispose();
		
		explosionTexture.dispose();
		font.dispose();
	}

}
