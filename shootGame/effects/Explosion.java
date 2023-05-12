package effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
	private Animation<TextureRegion> explosionAnimation;
	private float explosionTimer;
	private float xPosition,yPosition,width,height;
	public static Sound EXPLOSION_SOUND;
	
	public Explosion(Texture texture, float xPosition, float yPosition,
			float width, float height,
			float totalAnimationTime) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		//split texture
		
		TextureRegion[][] textureRegion2D = 
				TextureRegion.split(texture, 64, 64);
		//covert to 1D array
		TextureRegion[] textureRegion1D = new TextureRegion[16];
		
		int index = 0;
		for(int i = 0 ; i<4 ; i++) {
			for(int j = 0; j<4 ; j++) {
				textureRegion1D[index] = textureRegion2D[i][j];
				index++;
			}
		}
		explosionAnimation = new Animation<TextureRegion>(totalAnimationTime/16,
				 textureRegion1D);
		explosionTimer = 0;
		
		//sound effect
		EXPLOSION_SOUND = Gdx.audio.newSound(Gdx.files.internal("Explosion Sound.ogg"));
		long id = EXPLOSION_SOUND.play(0.2f);
		EXPLOSION_SOUND.setLooping(id, false);
		
	}
	public void update(float deltaTime) {
		explosionTimer += deltaTime;
	}
	public void draw(Batch batch) {
		batch.draw(explosionAnimation.getKeyFrame(explosionTimer), 
				xPosition, yPosition, width, height);
	}
	public boolean isFinished() {
		return explosionAnimation.isAnimationFinished(explosionTimer);
	}
}
