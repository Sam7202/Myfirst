package effects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerExplosion extends Explosion{

	public PlayerExplosion(Texture texture, float xPosition, float yPosition,
			float width, float height,
			float totalAnimationTime, Sound playerExplosionSound) {
		super(texture, xPosition-width/3, yPosition-height/3, width, height, totalAnimationTime, playerExplosionSound);

	}

	@Override
	protected void creatAnimation() {
		int index = 0;
		for(int i = 0 ; i<8 ; i++) {
			for(int j = 0; j<8 ; j++) {
				textureRegion1D[index] = textureRegion2D[i][j];
				index++;
			}
		}
		animation = new Animation<TextureRegion>(totalAnimationTime/64,
				textureRegion1D);

	}

	@Override
	protected void splitTexture() {
		textureRegion2D = TextureRegion.split(animationTexture, 512, 512);
		//covert to 1D array
		textureRegion1D = new TextureRegion[64];		
	}

	@Override
	protected void setAudio() {
		// TODO Auto-generated method stub
		long id = animationSound.play(0.2f);
		animationSound.setPitch(id, 0.5f);
		animationSound.setLooping(id, false);
	}



}
