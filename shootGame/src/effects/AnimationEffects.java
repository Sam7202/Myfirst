package effects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ships.PlayerShip;

public abstract class AnimationEffects {
	public float xPosition,yPosition,width,height;
	protected float totalAnimationTime = 0;
	protected Texture animationTexture;
	protected TextureRegion[][] textureRegion2D;
	protected TextureRegion[] textureRegion1D;
	protected Animation<TextureRegion> animation;
	protected float timer = 0;
	protected Sound animationSound;

	public AnimationEffects(float xPosition, float yPosition, float width, float height,
			float totalAnimationTime, Texture animationTexture, Sound animationSound) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.totalAnimationTime = totalAnimationTime;
		this.animationTexture = animationTexture;
		this.animationSound = animationSound;
		setAudio();
		splitTexture();
		creatAnimation();
	}

	//functions
	public void draw(Batch batch) {
		batch.draw(animation.getKeyFrame(timer), 
				xPosition, yPosition, width, height);
	
	}
	
	public boolean isFinished() {
		return animation.isAnimationFinished(timer);
	}
	
	public void update(float deltaTime) {
		timer += deltaTime;
	}
	
	//update with position
	public void update(PlayerShip playerShip, float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	protected abstract void setAudio();
	protected abstract void creatAnimation();
	protected abstract void splitTexture();

	
}
