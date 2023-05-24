package effects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SuperLaserHitEffect extends HitEffect {
	float timer;
	float totalAnimationTime;
	TextureRegion[][] textureRegion2D;
	TextureRegion[] textureRegion1D;
	Animation<TextureRegion> animation;
	public SuperLaserHitEffect(float xPosition, float yPosition, float hitEffectWidth, float hitEffectHeight,
			Texture hittexture, Sound superLaserHitSound,float totalAnimationTime) {
		super(xPosition, yPosition, hitEffectWidth, hitEffectHeight, hittexture, superLaserHitSound);
		this.totalAnimationTime = totalAnimationTime;
		splitTexture();
		creatAnimation();
	}
	@Override
	public void draw(Batch batch) {
		batch.draw(animation.getKeyFrame(timer), xPosition , yPosition, hitEffectWidth, hitEffectHeight);
	}
	public void update(float deltaTime) {
		timer += deltaTime;
	}
	public void creatAnimation() {
		int index = 0;
		for(int i = 0 ; i<8 ; i++) {
			for(int j = 0 ; j<8 ; j++) {
				
				textureRegion1D[index] = textureRegion2D[i][j];
				index++;
			}
		}
		animation = new Animation<TextureRegion>(totalAnimationTime/64,
				textureRegion1D);		
	}

	public void splitTexture() {
		textureRegion2D =TextureRegion.split(hitTexture, 128, 128);
		//covert to 1D array
		textureRegion1D = new TextureRegion[64];		
	}

	public boolean isFinished() {
		return animation.isAnimationFinished(timer);
	}
}
