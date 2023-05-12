package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ShootGame;

public class MenuScreen extends ShootGame implements Screen{
	Texture img;
	
	private SpriteBatch batch;

	Texture playButtonActive;
	Texture playButtonInActive;
	Texture exitButtonActive;
	Texture exitButtonInActive;

	private Camera camera;
	private Viewport viewport;
	
	public MenuScreen(){
//		this.game = game;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(220, 440, camera);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		img = new Texture("Amogus.png");
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.2f, 0.5f, 0.32f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		batch.draw(img, 0, 0, 100, 100);
		batch.end();
//		super.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height, true);

		batch.setProjectionMatrix(camera.combined);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
