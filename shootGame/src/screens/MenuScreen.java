package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.ShootGame;

public class MenuScreen implements Screen {
    private static  int EXIT_BUTTON_WIDTH = 75;
    private static  int EXIT_BUTTON_HEIGHT = 40;
    private static  int EXIT_BUTTON_Y = 50;
    private static  int WORLD_WIDTH = Gdx.graphics.getWidth();
    ShootGame game;
    BackGround backGround;
    Texture exitButtonActive;
    Texture exitButtonInActive;
    Texture Earth;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator1;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter1;
    private BitmapFont font1;
    private float alpha;
    private float Speed = 0.5f;
    private boolean fadingOut = true;
    //music
    private Music music;
	private OrthographicCamera camera;
	private StretchViewport viewport;
    public MenuScreen(ShootGame game) {
    	//
    	camera = new OrthographicCamera();
		viewport = new StretchViewport(620, 980, camera);
		
        this.game = game;
        backGround = new BackGround();
        backGround.SpeedFixed(true);
        backGround.setSpeed(BackGround.DEFAULT_SPEED);
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInActive = new Texture("exit_button_inactive.png");
        Earth = new Texture("Earth.png");
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("old_pixel-7.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 150;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = new Color(0.5f, 0.2f, 0.1f, 1);
        fontParameter.color = Color.GRAY;
        font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale(0.7f, 0.7f);
        font.setUseIntegerPositions(false);

        float alpha = 1.0f;
        fontGenerator1 = new FreeTypeFontGenerator(Gdx.files.internal("old_pixel-7.ttf"));
        fontParameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter1.size = 100;
        fontParameter1.borderWidth = 5;
        fontParameter1.borderColor = new Color(0, 0, 0, alpha);
        fontParameter1.color = Color.MAGENTA;
        font1 = fontGenerator1.generateFont(fontParameter1);
        font1.getData().setScale(0.45f, 0.45f);
        font1.setUseIntegerPositions(false);
        //music
        music = Gdx.audio.newMusic(Gdx.files.internal("MENU BGM.mp3"));
        music.setVolume(0.35f);
        music.setLooping(true);
        music.play();
        //
        EXIT_BUTTON_WIDTH = 75;
        EXIT_BUTTON_HEIGHT = 40;
        EXIT_BUTTON_Y = 50;
        WORLD_WIDTH = Gdx.graphics.getWidth();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(Earth, 0, 0, 620, Gdx.graphics.getHeight());
        //Title
        font.draw(game.batch, "ASTROSTRIKE", 0, Gdx.graphics.getHeight() - 150, WORLD_WIDTH, Align.center, false);
        //Click button
        Color fontColor = font1.getColor();
        font1.setColor(fontColor.r, fontColor.g, fontColor.b, alpha);
        font1.draw(game.batch, "Click Anywhere To Start", 0, 200, WORLD_WIDTH, Align.center, false);
        if (fadingOut) {
            alpha -= Speed * Gdx.graphics.getDeltaTime();
            if (alpha < 0) {
                alpha = 0.0f;
                fadingOut = false;
            }
        } else {
            alpha += Speed * Gdx.graphics.getDeltaTime();
            if (alpha > 1) {
                alpha = 1.0f;
                fadingOut = true;
            }
        }
        backGround.updateAndRender(delta, game.batch);
        int x1 = 620 / 2 - EXIT_BUTTON_WIDTH / 2;
        if (Gdx.input.getX() < x1 + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x1 && 980 - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && 980 - Gdx.input.getY() > EXIT_BUTTON_Y) {
        	game.batch.draw(exitButtonActive, x1, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        	if (Gdx.input.justTouched()) {
        		Gdx.app.exit();
        	}
        } 
        else if (Gdx.input.justTouched()) {
            this.dispose();
            
            game.setScreen(new GameScreen(game));
        }
        else {
            game.batch.draw(exitButtonInActive, x1, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
    	viewport.update(width, height, true);

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
    	music.dispose();
    }
}
