package effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import screens.GameScreen;
import ships.PlayerShip;

public class ChagreEffects extends AnimationEffects{
	float chargeTime = 3f;
	float alpha = 0;
	float increasingSpeed = 1f;
	float lineThinerSpeed = 1f;
	Texture redLine;
	float redlineWidth = 2f;
	float redlineXposition = 0, redlineYposition = 0;
	public ChagreEffects(float xPosition, float yPosition, float width, float height, float totalAnimationTime,
			Texture animationTexture , Sound chargeSound) {
		super(xPosition, yPosition, width, height, totalAnimationTime, animationTexture, chargeSound);
		redLine = new Texture(Gdx.files.internal("effects/red line.png"));        

	}
	@Override
	public void update(PlayerShip playerShip, float deltaTime) {
		xPosition = playerShip.xPosition;
		yPosition = playerShip.yPosition;
		redlineXposition = playerShip.xPosition + playerShip.width/2 - redlineWidth/2;
		redlineYposition = playerShip.yPosition + playerShip.height;
		timer += deltaTime;
		//set batch alpha
		if(alpha>=0.3f)
			increasingSpeed *=(float)-1;
		else if(alpha<=0)
			increasingSpeed *=(float)-1;
		increasingSpeed += increasingSpeed*deltaTime;
		alpha += increasingSpeed*deltaTime;
		if(redlineWidth>=0.5f)
			redlineWidth -= lineThinerSpeed*deltaTime;
	}
	@Override
	public void draw(Batch batch) {
		batch.setColor(1, 1, 1, alpha);
		batch.draw(redLine,redlineXposition, redlineYposition, redlineWidth, GameScreen.WORLD_HEIGHT);

		batch.setColor(1, 1, 1, 1);

		batch.draw(animation.getKeyFrame(timer), 
				xPosition, yPosition, width, height);
	}
	@Override
	public void creatAnimation() {
		int index = 0;
		for(int i = 0 ; i<4 ; i++) {
			textureRegion1D[index] = textureRegion2D[0][i];
			index++;
		}
		animation = new Animation<TextureRegion>(totalAnimationTime/4,
				textureRegion1D);		
		animation.setPlayMode(PlayMode.LOOP);
	}

	@Override
	public void splitTexture() {
		textureRegion2D =TextureRegion.split(animationTexture, 32, 32);
		//covert to 1D array
		textureRegion1D = new TextureRegion[4];		
	}

	@Override
	public boolean isFinished() {
		if(timer <= chargeTime)
			return false;
		return true;
	}
	@Override
	protected void setAudio() {
		animationSound.play(0.7f);

	}

}
