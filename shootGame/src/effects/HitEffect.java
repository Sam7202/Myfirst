package effects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class HitEffect {
	float xPosition;
	float yPosition;
	float hitEffectWidth =5;
	float hitEffectHeight =5;
	Sound hitSound;
	Texture hitTexture;
	public HitEffect(float xPosition, float yPosition, float hitEffectWidth, float hitEffectHeight,
			Texture hitTexture, Sound hitSound) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.hitEffectWidth = hitEffectWidth;
		this.hitEffectHeight = hitEffectHeight;
		this.hitTexture = hitTexture;
		this.hitSound = hitSound;
		setAudio();
	}
	public void draw(Batch batch) {
		batch.draw(hitTexture, xPosition- hitEffectWidth/2 , yPosition, hitEffectWidth, hitEffectHeight);
	}
	public void setAudio() {
		hitSound.play(0.3f);
	}
	
}
