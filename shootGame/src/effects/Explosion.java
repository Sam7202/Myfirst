package effects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public abstract class Explosion extends AnimationEffects{

	public Explosion(Texture texture, float xPosition, float yPosition,
			float width, float height,
			float totalAnimationTime, Sound explosionSound) {
		super(xPosition, yPosition, width, height, totalAnimationTime, texture, explosionSound);
	}

	
}
