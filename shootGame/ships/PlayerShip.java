package ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import effects.Laser;
import effects.LowLifeEffect;
import screens.GameScreen;


public class PlayerShip extends Ship {

	public boolean lowLife = false;
	public int lives;
	//audio
	public static Sound SHIELD_DOWN_SOUND;
	//hurt
	Texture hurt;
	//low life
	LowLifeEffect lowLifeEffect;
	Texture lowLifeTexture;

	public PlayerShip(float movementSpeed, int shield,
			float xCenter, float yCenter,
			float width, float height,
			float laserWidth, float laserHeight, float laserMovementSpeed,
			float timeBetweenShots,
			TextureRegion shipTexureRegion, TextureRegion shieldTextureRegion,
			TextureRegion laserTextureRegion) {
		super(movementSpeed, shield,
				xCenter, yCenter,
				width, height,
				laserWidth, laserHeight, laserMovementSpeed,
				timeBetweenShots,
				shipTexureRegion, shieldTextureRegion,
				laserTextureRegion);
		lives = 3;
		SHIELD_DOWN_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx_shieldDown.ogg"));

		hurt = new Texture(Gdx.files.internal("hurt.jpg"));

		lowLifeTexture = new Texture(Gdx.files.internal("low life effect.png"));
		lowLifeEffect = new LowLifeEffect(lowLifeTexture);

	}



	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
	public void updateLowLife(float deltaTime,Batch batch) {
		if(lives == 1) {
//			lowLife = true;
			lowLifeEffect.play();
			lowLifeEffect.draw(batch);
		}
		else {
//			lowLife = false;
			lowLifeEffect.stop();
		}
	}
	@Override
	public void hit(Laser laser, Batch batch) {
		if(shield>0) {
			shield--;
			if(shield == 0) {
				SHIELD_DOWN_SOUND.play(0.4f);
			}
		}
		else {

			if(lives > 1) {
				lives--;
				//draw hurt
				batch.draw(hurt, 0, 0, GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT);
			}
			else {
				lives = 0;
				destroyed = true;
			}
		}

	}
	@Override
	public Laser[] fireLasers() {
		Laser[] laser = new Laser[1];
		laser[0] = new Laser(xPosition+ 0.5f*width,yPosition + height ,
				laserWidth, laserHeight, 
				laserMovementSpeed, laserTextureRegion);

		timeSinceLastShot = 0;

		return laser;
	}


}
