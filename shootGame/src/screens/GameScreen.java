package screens;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import Buffs.Buffs;
import Buffs.LiveUpBuff;
import Buffs.PowerUpBuff;
import Buffs.ShieldUpBuff;
import ships.Boss;
import ships.EnemyShips;
import effects.BossExplosion;
import effects.BossLaserHitEffect;
import effects.ChagreEffects;
import effects.EnemyExplosion;
import effects.Explosion;
import effects.HitEffect;
import lasers.BossLaser;
import lasers.Laser;
import effects.LowLifeEffect;
import effects.PlayerExplosion;
import lasers.SuperLaser;
import effects.SuperLaserHitEffect;
import ships.PlayerShip;
public class GameScreen  implements Screen {
	ShootGame game;
	//Screen
	private Camera camera;
	private Viewport viewport;
	

	//graphics
	private TextureRegion[] backgrounds ;
	private TextureAtlas textureAtlas;
	private final float PRESERVED_PIXEL = 0.5f;
	private TextureRegion playerShipTextureRegion, playerSheildTextureRegion,
	enemyShipTextureRegion, enemySheildTextureRegion,
	playerLaserTextureRegion, enemyLaserTextureRegion, bossTextureRegion;
	private Texture enemyhitEffectTexture;
	private Texture playerhitEffectTexture;
	private Texture explosionTexture;
	private Texture playerExplosionTexture;
	//boss
	private Texture bossTexture;
	private Texture bossExplosionTexture;
	private Texture bossLaserTexture;
	private Texture bossLaserHitEffectTexture;
	
	private TextureRegion bossLaserTextureRegion;
	
	//buffs Texture && parameters
	private Texture shieldUpTexture ;
	private Texture powerUpTexture;
	private Texture liveUpTexture;
	private Texture superLaserTexture;
	private Texture superLaserHitTexture;
	private Texture chargeTexture;

	//timing
	private float[] backgroundOffsets = {0,0,0,0};
	private float backdroundMaxScrollingSpeed;
	private float timeBetweenEnemySpawn = 3f;
	private float timeSinceLastSpawn = 0;

	//gameover explosion times
	final int GAMEOVER_EXPLOSION_TIMES = 2;
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
	private LinkedList<Buffs> buffList;
	private LinkedList<SuperLaser> superLaserList;
	private LinkedList<ChagreEffects> chargeEffecstList;


	//audio
	Music bossMusic;
	Music backgroundMusic;
	Sound playerExplosionSound2;
	Sound explosionSound;
	Sound bossExplosionSound;
	Sound superLaerSound;
	Sound chargeEffectsSound;
	Sound playerExplosionSound;
	Sound laserSound;
	Sound hitSound;
	Sound bossLaserHitSound;
	Sound bossLaserSound;
	

	//Heads-Up Display
	private int score = 0;
	BitmapFont font;
	float hudVerticalMargine, hudLeftx, hudRightx, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;
	private int subScore = 0;

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
		//boss
		bossTexture = new Texture("boss.png");
		bossTextureRegion = new TextureRegion(bossTexture);
		bossExplosionTexture = new Texture(Gdx.files.internal("effects/boss explosion.png"));
		bossLaserTexture = new Texture(Gdx.files.internal("Boss laser.png"));
		bossLaserTextureRegion = new TextureRegion(bossLaserTexture);
		bossLaserHitEffectTexture = new Texture(Gdx.files.internal("effects/boss laser hit effects.png"));
		//buffs
		shieldUpTexture = new Texture(Gdx.files.internal("buffs/shield_silver.png"));
		powerUpTexture = new Texture(Gdx.files.internal("buffs/bolt_gold.png"));
		liveUpTexture = new Texture(Gdx.files.internal("buffs/pill_red.png"));
		superLaserTexture = new Texture(Gdx.files.internal("effects/super laser1.png"));
		superLaserHitTexture = new Texture(Gdx.files.internal("effects/SuperLaser Hit Effect.png"));
		chargeTexture = new Texture(Gdx.files.internal("effects/ChargeEffect.png"));

		//set up audio
		prepareAudio();
		//set up game object
		playerShip = new PlayerShip(3,50, 3, WORLD_WIDTH/2, WORLD_HEIGHT/4, 10, 10,
				0.8f, 4, 60, 0.3f,
				playerShipTextureRegion, playerSheildTextureRegion, playerLaserTextureRegion,
				superLaserTexture, superLaerSound, chargeTexture, chargeEffectsSound, laserSound);
		explosionTexture = new Texture("explosion.png");
		playerExplosionTexture = new Texture("effects/player explosion.png");

		//set up list
		playerLaserList = new LinkedList<>();
		playerHitEffectList = new LinkedList<>();

		enemyShipList = new LinkedList<>();
		enemyLaserList = new LinkedList<>();
		enemyHitEffectList = new LinkedList<>();

		explosionList = new LinkedList<>();

		buffList = new LinkedList<>();
		superLaserList = new LinkedList<>();
		chargeEffecstList = new LinkedList<>();

		prepareHUD();
		//set last game boss "unspawn"
		Boss.IS_lIVING = false;

	}
	private void prepareAudio() {

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("game/Among Us Trap Remix.mp3")); 
		backgroundMusic.setVolume(0.2f);
		backgroundMusic.play();
		backgroundMusic.setLooping(true);

		laserSound = Gdx.audio.newSound(Gdx.files.internal("effects/LaserSound.ogg"));
		superLaerSound = Gdx.audio.newSound(Gdx.files.internal("effects/super laser sound.mp3"));
		chargeEffectsSound = Gdx.audio.newSound(Gdx.files.internal("effects/charge effect sound.mp3"));

		explosionSound = Gdx.audio.newSound(Gdx.files.internal("Explosion Sound.ogg"));
		playerExplosionSound = Gdx.audio.newSound(Gdx.files.internal("effects/player explosion sound.mp3"));
		playerExplosionSound2 = Gdx.audio.newSound(Gdx.files.internal("effects/playersound.mp3"));

		bossExplosionSound = Gdx.audio.newSound(Gdx.files.internal("effects/Boss Explosion Sond.mp3"));
		bossLaserHitSound = Gdx.audio.newSound(Gdx.files.internal("effects/boss laser hit sound.mp3"));
		bossLaserSound = Gdx.audio.newSound(Gdx.files.internal("effects/boss laser sound.mp3"));
		bossMusic = Gdx.audio.newMusic(Gdx.files.internal("game/bossMusic.mp3"));
		
		hitSound = Gdx.audio.newSound(Gdx.files.internal("effects/hit sound.mp3"));
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
		
		//scrolling background
		renderBackground(deltaTime);
		
		for(EnemyShips enemyShip : enemyShipList) {
			enemyShip.draw(game.batch);
			enemyShip.update(deltaTime);
			
		}
		//check end
		if(playerShip.destroyed) {
			LowLifeEffect.LOW_LIVES_ALARM.stop();
			backgroundMusic.stop();
			if(explosionList.isEmpty()) {
				game.setScreen(new GameOverScreen(game, score));
				this.dispose();
			}
		}
		else {


			//enemy ships
			spawnEnemyShips(deltaTime);

			//player ship
			playerShip.draw(game.batch);
			playerShip.update(deltaTime);

			//lasers
			renderLasers(deltaTime);
			renderSuperLaser(deltaTime);
			renderChargeEffect(deltaTime);
			
			//hit effects
			renderHitEffects(deltaTime);

			//low life effects
			renderlowLifeEffects(deltaTime);


			//detect keyboard input
			keyBoardInput();

			//touch input(mouse)
			touchInput();

			//HUD rendering
			updateAndRenderHUD();

			//detect collisions between lasers and ships
			detectCollisions();

			detectBuffs(deltaTime);

		}
		//explosions
		renderExplosions(deltaTime);
		
		game.batch.end();
	}

	private void renderChargeEffect(float deltaTime) {
		ListIterator<ChagreEffects> chargeEffectIterator = chargeEffecstList.listIterator();
		while(chargeEffectIterator.hasNext()) {
			ChagreEffects chargeEffect = chargeEffectIterator.next();
			if(chargeEffect.isFinished()) {
				chargeEffectIterator.remove();
				superLaserList.add(playerShip.fireSuperLaser());
			}
			else {
				chargeEffect.draw(game.batch);
				chargeEffect.update(playerShip, deltaTime);

			}
		}
	}
	private void renderSuperLaser(float deltaTime) {
		ListIterator<SuperLaser> superLaserIterator = superLaserList.listIterator();
		while(superLaserIterator.hasNext()) {
			SuperLaser superLaser = superLaserIterator.next();
			//see if super laser contact with enemy ships
			if(superLaser.isFinished()) {
				superLaserIterator.remove();
				//this super laser only cause a damage
				Boss.HAD_COLLIDE_WITH_SUPERLASER = false;
			}
			else {
				superLaser.draw(game.batch);
				superLaser.update(playerShip, deltaTime);
				//contact with enemy ships
				for(int i = 0;i<enemyShipList.size();i++) {
					if(enemyShipList.get(i).intersects(superLaser.getBoundingBox())) {
						//make sure a superLaser only cause damage once
						if(enemyShipList.get(i) instanceof Boss) {
							Boss boss = (Boss)enemyShipList.get(i);
							if(!Boss.HAD_COLLIDE_WITH_SUPERLASER) {
								enemyHitEffectList.add(new SuperLaserHitEffect(boss.xPosition, boss.yPosition,
										boss.width, boss.height, superLaserHitTexture, hitSound, 1.5f));
								boss.hit(superLaser, game.batch);
								Boss.HAD_COLLIDE_WITH_SUPERLASER = true;
							}
						}
						else {

							enemyShipList.get(i).hit(superLaser , game.batch );
						}

					}
					//check if enemy destroyed
					if(enemyShipList.get(i).destroyed == true) {

						//add score when enemy are destroyed
						if(enemyShipList.get(i) instanceof Boss) {
							Boss boss = (Boss)enemyShipList.get(i);
							boss.bossMusic.stop();
							explosionList.add(new BossExplosion(bossExplosionTexture,
									enemyShipList.get(i).xPosition,
									enemyShipList.get(i).yPosition,
									enemyShipList.get(i).width,
									enemyShipList.get(i).height,
									1f, bossExplosionSound));
							score+=100;
							//reset subScore
							subScore = 0;
							Boss.IS_lIVING = false;
						}
						else {
							//add explosion effects
							explosionList.add(new EnemyExplosion(explosionTexture,
									enemyShipList.get(i).xPosition,
									enemyShipList.get(i).yPosition,
									enemyShipList.get(i).width,
									enemyShipList.get(i).height,
									0.5f, explosionSound));

							score+=10;
							subScore += 10;
						}
						//add buffs
						addBuffs(enemyShipList.get(i));

						enemyShipList.remove(i);
						i--;
					}

				}
			}
		}
	}
	private void detectBuffs(float deltaTime) {
		Iterator<Buffs> iterator = buffList.iterator();
		while(iterator.hasNext()) {
			Buffs buff = iterator.next();
			if(buff.contactWithPlayerShip(playerShip.shipBoundingBox)) {
				//see which buff player gets
				if(buff instanceof ShieldUpBuff) {
					ShieldUpBuff a = (ShieldUpBuff) buff;
					a.buffUp(playerShip);
				}
				else if(buff instanceof LiveUpBuff) {
					LiveUpBuff a = (LiveUpBuff) buff;
					a.buffUp(playerShip);
				}
				else if(buff instanceof PowerUpBuff) {
					//					PowerUpBuff a = (PowerUpBuff) buff;
					chargeEffecstList.add(playerShip.charge());
					//					superLaserList.add(playerShip.fireSuperLaser());

				}
				iterator.remove();
			}
			buff.drawBuff(game.batch);
			buff.updateBuff(deltaTime);
			//delete old buffs
			if(buff.yPosition + buff.buffHeight <= -3)
				iterator.remove();

		}
	}
	private void renderlowLifeEffects(float deltaTime) {

		playerShip.updateLowLife(deltaTime, game.batch);
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
			//if hit effect is animation
			if(hitEffect instanceof SuperLaserHitEffect) {
				SuperLaserHitEffect superLaserHitEffect = (SuperLaserHitEffect)hitEffect;
				if(superLaserHitEffect.isFinished()) {
					iterator.remove();
				}
				else {
					
					superLaserHitEffect.draw(game.batch);
					superLaserHitEffect.update(deltaTime);
				}
				
			}
			else if(hitEffect instanceof BossLaserHitEffect) {
				BossLaserHitEffect bossLaserHitEffect = (BossLaserHitEffect)hitEffect;
				if(bossLaserHitEffect.isFinished()) {
					iterator.remove();
				}
				else {
					
					bossLaserHitEffect.draw(game.batch);
					bossLaserHitEffect.update(deltaTime);
				}
				
			}
			//if hit effect is not animation hit effect
			else {
				
				hitEffect.draw(game.batch);
				iterator.remove();
			}

		}

	}
	private void updateAndRenderHUD() {
		// render top row labels
		font.draw(game.batch, "Score", hudLeftx, hudRow1Y, hudSectionWidth, Align.left,false);
		font.draw(game.batch, "Sheild", hudCentreX, hudRow1Y, hudSectionWidth, Align.center,false);
		font.draw(game.batch, "Lives", hudRightx, hudRow1Y, hudSectionWidth, Align.right,false);

		//render second row values
		font.draw(game.batch, String.format(Locale.getDefault(), "%05d", score), hudLeftx, hudRow2Y, hudSectionWidth , Align.left, false);
		font.draw(game.batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
		font.draw(game.batch, String.format(Locale.getDefault(), "%02d", playerShip.life), hudRightx, hudRow2Y, hudSectionWidth, Align.right, false);
	}
	private void spawnEnemyShips(float deltaTime) {
		if(timeSinceLastSpawn > timeBetweenEnemySpawn && (Boss.IS_lIVING == false)) {
			enemyShipList.add(new EnemyShips(1,40, 3, WORLD_WIDTH/2, WORLD_HEIGHT*3/4, 10, 10,
					0.6f, 5, -30, 1.0f,
					enemyShipTextureRegion, enemySheildTextureRegion, enemyLaserTextureRegion, laserSound));
			timeSinceLastSpawn = 0;
		}
		timeSinceLastSpawn += deltaTime;
		if((float)subScore % 100 == 0 && subScore!=0 && Boss.IS_lIVING == false) {
			//spawn boss (strong along higher score)
			enemyShipList.add( new Boss(50 + (int)(score/100), 10, 3, WORLD_WIDTH/2, WORLD_HEIGHT*3/4, 30, 30,
					10, 10, -30, 2.0f,
					bossTextureRegion, enemySheildTextureRegion, bossLaserTextureRegion, bossLaserSound,
					bossMusic));
			Boss.IS_lIVING = true;

		}
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
							5, 5, playerhitEffectTexture, hitSound));
					enemyShipList.get(i).hit(laser , game.batch );
					if(enemyShipList.get(i) instanceof Boss) {
						Boss boss = (Boss)enemyShipList.get(i);
						if(boss.canDropBuff()) {
							addBuffs(boss);
						}
					}
					//if a playerlaser collide with enemyship then check next laser
					break;
				}
				if(enemyShipList.get(i).destroyed == true) {
					if(enemyShipList.get(i) instanceof Boss) {
						Boss boss = (Boss)enemyShipList.get(i);
						boss.bossMusic.stop();
						Boss.IS_lIVING = false;
						explosionList.add(new BossExplosion(bossExplosionTexture,
								enemyShipList.get(i).xPosition,
								enemyShipList.get(i).yPosition,
								enemyShipList.get(i).width,
								enemyShipList.get(i).height,
								1f, bossExplosionSound));

						score+=100;
						subScore = 0;

					}
					else {
						//add explosion effects
						explosionList.add(new EnemyExplosion(explosionTexture,
								enemyShipList.get(i).xPosition,
								enemyShipList.get(i).yPosition,
								enemyShipList.get(i).width,
								enemyShipList.get(i).height,
								0.5f, explosionSound));

						//add score when enemy are destroyed
						score+=10;
						subScore+=10;
					}
					//add buffs
					addBuffs(enemyShipList.get(i));
					enemyShipList.remove(i);
					i--;


				}
			}
		}
		//for each enemy laser, check whether it intersect an player ship
		iterator = enemyLaserList.listIterator();
		while(iterator.hasNext()) {
			Laser laser = iterator.next();
			if(playerShip.intersects(laser.getBoundingBox())) {
				//contact with player ship
				iterator.remove();
				playerShip.hit(laser ,game.batch);
				if(laser instanceof BossLaser) {
					enemyHitEffectList.add(new BossLaserHitEffect(laser.xPosition, laser.yPosition
							, 5, 5, bossLaserHitEffectTexture, bossLaserHitSound, 0.5f)); 
				}
				else {
					enemyHitEffectList.add(new HitEffect(laser.xPosition+laser.width/2, laser.yPosition
							, 5, 5, enemyhitEffectTexture, hitSound)); 
				}
				if(playerShip.destroyed == true) {

					playerExplosionSound2.play(0.4f);
					explosionList.add( new PlayerExplosion(playerExplosionTexture,
							playerShip.xPosition,
							playerShip.yPosition,
							playerShip.width*3,
							playerShip.height*3,
							1.5f,playerExplosionSound));

				}
			}
		}

	}
	private void addBuffs(EnemyShips enemyShip) {
		Random r = new Random();
		switch(r.nextInt(10)) {

		case 1:
			buffList.add(new ShieldUpBuff(30,enemyShip.xPosition, enemyShip.yPosition
					, 3.5f, 3.5f, shieldUpTexture));
			break;
		case 2:
			buffList.add(new LiveUpBuff(30,enemyShip.xPosition, enemyShip.yPosition
					, 3.5f, 3.5f , liveUpTexture));
			break;
		case 3: case 5:
			buffList.add(new PowerUpBuff(30,enemyShip.xPosition, enemyShip.yPosition
					, 3f, 4f, powerUpTexture));
			break;


		}

	}
	private void renderExplosions(float deltaTime) {
		ListIterator<Explosion> explosionIterator = explosionList.listIterator();

		while(explosionIterator.hasNext()) {
			Explosion explosion = explosionIterator.next();
			if(explosion.isFinished()) {
				explosionIterator.remove();
				if(explosion instanceof PlayerExplosion && TimeshasExploed< GAMEOVER_EXPLOSION_TIMES) {
					explosionIterator.add( new PlayerExplosion(playerExplosionTexture,
							playerShip.xPosition++,
							playerShip.yPosition,
							playerShip.width*3,
							playerShip.height*3,
							1f, playerExplosionSound));
					TimeshasExploed++;
				}

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
			laser.update( deltaTime);
			laser.draw(game.batch);

			//remove old lasers
			if(laser.yPosition > WORLD_HEIGHT) {
				iterator.remove();
			}
		}
		iterator = enemyLaserList.listIterator();
		while(iterator.hasNext()) {
			Laser laser = iterator.next();
			laser.update( deltaTime);
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
		laserSound.dispose();
		LowLifeEffect.LOW_LIVES_ALARM.dispose();
		PlayerShip.SHIELD_DOWN_SOUND.dispose();
		//		ShieldUpBuff.SHIELDUP_SOUND.dispose();
		//		LiveUpBuff.LIVEUP_SOUND.dispose();
		backgroundMusic.dispose();
		bossMusic.dispose();
		explosionSound.dispose();
		superLaerSound.dispose();
		playerExplosionSound2.dispose();

		textureAtlas.dispose();
		explosionTexture.dispose();
		font.dispose();
	}

}
