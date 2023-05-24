package screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ShootGame;

public class GameOverScreen extends InputAdapter implements Screen{
	ShootGame game;
	
	//"game over" font 
	FreeTypeFontGenerator gameovergenerator ;
	FreeTypeFontParameter gameoverparameter ;
	BitmapFont gameoverfont ;
	
	//"score" font
	FreeTypeFontGenerator scoregenerator ;
	FreeTypeFontParameter scoreparameter ;
	BitmapFont scorefont ;
	
	//"menu" font
	FreeTypeFontGenerator menugenerator ;
	FreeTypeFontParameter menuparameter ;
	BitmapFont menufont;

	//"restart" font
	FreeTypeFontGenerator restartgenerator ;
	FreeTypeFontParameter restartparameter ;
	BitmapFont restartfont ;
	
	Color activeColor = new Color(1,1,1,1);
	Color inactiveColor = new Color(1f,0.278f,0.24f,0.75f);
	int score = 0 ;

	//virtual screen
	final float WORLD_WIDTH = GameScreen.WORLD_WIDTH;
	final float WORLD_HEIGHT = GameScreen.WORLD_HEIGHT;

	//camera
	private Viewport viewport;
	private Camera camera;

	//audio
	private Music backgroundMusic;
	
	//background
	Texture background;
	float backdroundScrollingSpeed = WORLD_HEIGHT / 8;
	float backgroundOffset;
	
	public void createCamera() {
		camera = new OrthographicCamera();
		viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
	}
	public GameOverScreen(ShootGame game,int score){
		this.game = game;
		this.score = score;
		
		//camera
		createCamera();
		
		//background
		generateBackground();
		
		//game over font
		generateGameoverFont();
		
		//score font 
		generateScoreFont();
		
		//operation font
		generateMenuFont();
		generateRestartFont();

		//set up audio
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("gameover/Better Call Saul Intro.mp3")); 
		backgroundMusic.setVolume(0.2f);
		backgroundMusic.play();
		backgroundMusic.setLooping(true);
	}
	private void generateBackground() {
		background = new Texture(Gdx.files.internal("gameover/gameover background.jpg"));
	}
	private void renderScrollingBackground(float deltaTime) {
			
			backgroundOffset += deltaTime * (backdroundScrollingSpeed );
			if(backgroundOffset <= WORLD_HEIGHT) {
				game.batch.draw(background, 0, -backgroundOffset, WORLD_WIDTH, WORLD_HEIGHT);
			}
			
	}
	private void generateRestartFont() {
		restartgenerator = new FreeTypeFontGenerator(Gdx.files.internal("NeonFuture20Demo-L3jp3.otf"));
		restartparameter = new FreeTypeFontParameter();
		restartparameter.size = 72;
		restartparameter.color = inactiveColor;
		restartparameter.borderColor = new Color(1f,0.91f,0.501f,0.75f);
		restartparameter.borderWidth = 3.5f;

		restartfont = restartgenerator.generateFont(restartparameter);

		restartfont.getData().setScale(0.1f);
		restartfont.setUseIntegerPositions(false);
	}
	private void generateMenuFont() {
		menugenerator = new FreeTypeFontGenerator(Gdx.files.internal("NeonFuture20Demo-L3jp3.otf"));
		menuparameter = new FreeTypeFontParameter();
		menuparameter.size = 72;
		menuparameter.color = inactiveColor;
		menuparameter.borderColor = new Color(1f,0.91f,0.501f,0.75f);
		menuparameter.borderWidth = 3.5f;

		menufont = menugenerator.generateFont(menuparameter);

		menufont.getData().setScale(0.1f);
		menufont.setUseIntegerPositions(false);
	}
	private void generateScoreFont() {
		scoregenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyPoster-3zRAp.otf"));
		scoreparameter = new FreeTypeFontParameter();
		scoreparameter.size = 72;
//		scoreparameter.color = new Color(0.52f,1,0.04f,1f);
		scoreparameter.color = new Color(1f,0.91f,0.501f,0.75f);
		scoreparameter.borderColor = new Color(0.5f,1,0.05f,0.2f);
		scoreparameter.borderWidth = 3.1f;

		scorefont = scoregenerator.generateFont(scoreparameter);
		scorefont.setUseIntegerPositions(false);
		scorefont.getData().setScale(0.1f);
	}
	private void generateGameoverFont() {
		gameovergenerator = new FreeTypeFontGenerator(Gdx.files.internal("AwakenningPersonalUse-DOLPD.ttf"));
		gameoverparameter = new FreeTypeFontParameter();
		gameoverparameter.size = 72;
//		gameoverparameter.color = new Color(0.52f,1,0.04f,1f);
		gameoverparameter.color = new Color(1f,0.278f,0.24f,0.75f);
		gameoverparameter.borderColor = new Color(1f,0.91f,0.501f,0.75f);
		gameoverparameter.borderWidth = 1.5f;
		
		gameoverfont = gameovergenerator.generateFont(gameoverparameter);
		gameoverfont.getData().setScale(0.15f);
		gameoverfont.setUseIntegerPositions(false);

	}
	@Override
	public void show() {
//		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		//steady background
		game.batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

		gameoverfont.draw(game.batch, "GAMEOVER", WORLD_WIDTH/4, 100, WORLD_WIDTH/2, Align.center, false);
		gameoverfont.draw(game.batch, "YOUR SCORE", WORLD_WIDTH/4, 80, WORLD_WIDTH/2, Align.center, false);

		//draw score
		scorefont.draw(game.batch, String.valueOf(score), WORLD_WIDTH/4, 60, WORLD_WIDTH/2, Align.center, false);

		detectMouse();
		
		///draw operation
		menufont.draw(game.batch, "MENU", WORLD_WIDTH/4, 40, WORLD_WIDTH/2, Align.center, false);
		restartfont.draw(game.batch, "RESTART", WORLD_WIDTH/4, 30, WORLD_WIDTH/2, Align.center, false);
		
		//background
		renderScrollingBackground(delta);
		game.batch.end();
	}

	private void detectMouse() {
		//menu position
		float menuButtonwidth = WORLD_WIDTH/3;
		float menuButtonheight = menufont.getCapHeight();
		float menuButtonX = WORLD_WIDTH/2 - menuButtonwidth/2 ;
		float menuButtonY = 34;

		//menu position
		float restartButtonwidth = WORLD_WIDTH/1.8f;
		float restartButtonheight = restartfont.getCapHeight();
		float restartButtonX = WORLD_WIDTH/2 - restartButtonwidth/2 ;
		float restartButtonY = 24;

		//mouse position
		Vector2 mousePosition = new Vector2(Gdx.input.getX() ,  Gdx.input.getY());
		mousePosition = viewport.unproject(mousePosition);
		float mouseX = mousePosition.x;
		float mouseY = mousePosition.y;

		// detect mouse on "menu" position
		if((mouseX<=menuButtonX + menuButtonwidth && mouseX>menuButtonX) && 
				(mouseY<=menuButtonY + menuButtonheight && mouseY>menuButtonY)) {
			menufont.setColor(activeColor);
			menufont.getData().setScale(0.12f);
			// detect mouse click
			if(Gdx.input.justTouched()) {
				game.setScreen(new MenuScreen(game));
				this.dispose();
			}
		}

		// detect mouse on "restart" position
		else if((mouseX<=restartButtonX + restartButtonwidth && mouseX>restartButtonX) && 
				(mouseY<=restartButtonY + restartButtonheight && mouseY>restartButtonY)) {
			restartfont.setColor(activeColor);
			restartfont.getData().setScale(0.12f);
			
			// detect mouse click
			if(Gdx.input.justTouched()) {
				game.setScreen(new GameScreen(game));
				this.dispose();
			}
		}
		else {
			menufont.getData().setScale(0.1f);
			menufont.setColor(inactiveColor);
			restartfont.getData().setScale(0.1f);
			restartfont.setColor(inactiveColor);
		}
	}
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height ,true);
		game.batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		gameoverfont.dispose();
		menufont.dispose();
		scorefont.dispose();
		restartfont.dispose();
		
		backgroundMusic.dispose();
		background.dispose();
	}

}
