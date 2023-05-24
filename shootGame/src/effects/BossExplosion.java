package effects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BossExplosion extends Explosion {

	public BossExplosion(Texture texture, float xPosition, float yPosition, float width, float height,
			float totalAnimationTime, Sound explosionSound) {
		super(texture, xPosition, yPosition, width, height, totalAnimationTime, explosionSound);
	}
	@Override
	protected void creatAnimation() {
		int index = 0;
		for(int i = 0 ; i<4 ; i++) {
			for(int j = 0; j<8 ; j++) {
				textureRegion1D[index] = textureRegion2D[i][j];
				index++;
			}
		}
		animation = new Animation<TextureRegion>(totalAnimationTime/32,
				textureRegion1D);


	}

	@Override
	protected void splitTexture() {
		textureRegion2D = 
				TextureRegion.split(animationTexture, 64, 64);
		//covert to 1D array
		textureRegion1D = new TextureRegion[32];		
	}
	@Override
	protected void setAudio() {
		// TODO Auto-generated method stub
		animationSound.play(0.7f);
	}

}
