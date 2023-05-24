package ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import effects.ChagreEffects;
import effects.LowLifeEffect;
import lasers.SuperLaser;
import lasers.Laser;
import screens.GameScreen;


public class PlayerShip extends Ship {

	public boolean lowLife = false;
	//audio
	public static Sound SHIELD_DOWN_SOUND;
	//hurt
	Texture hurt;
	//low life
	LowLifeEffect lowLifeEffect;
	Texture lowLifeTexture;
	//super laser
	Texture superlaserTexture;
	Sound superlaserSound;
	Texture chargeEffectsTexture;
	Sound chargeEffcetsSound;

	public PlayerShip(int life, float movementSpeed, int shield,
			float xCenter, float yCenter,
			float width, float height,
			float laserWidth, float laserHeight, float laserMovementSpeed,
			float timeBetweenShots,
			TextureRegion shipTexureRegion, TextureRegion shieldTextureRegion,
			TextureRegion laserTextureRegion, Texture superLaserTexture, Sound superLaserSound,
			 Texture chargeEffectsTexture, Sound chargeEffcetsSound,
			 Sound lasersound) {
		super(life, movementSpeed, shield,
				xCenter, yCenter,
				width, height,
				laserWidth, laserHeight, laserMovementSpeed,
				timeBetweenShots,
				shipTexureRegion, shieldTextureRegion,
				laserTextureRegion, lasersound);
		SHIELD_DOWN_SOUND = Gdx.audio.newSound(Gdx.files.internal("sfx_shieldDown.ogg"));

		hurt = new Texture(Gdx.files.internal("hurt.jpg"));

		lowLifeTexture = new Texture(Gdx.files.internal("effects/low life effect.png"));
		lowLifeEffect = new LowLifeEffect(lowLifeTexture);
		this.superlaserTexture = superLaserTexture;
		this.superlaserSound = superLaserSound;
		this.chargeEffectsTexture = chargeEffectsTexture;
		this.chargeEffcetsSound = chargeEffcetsSound;
	}



	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
	public void updateLowLife(float deltaTime,Batch batch) {
		if(life == 1) {
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
			shield-=laser.attact;
			if(shield <= 0) {
				shield = 0;
				SHIELD_DOWN_SOUND.play(0.4f);
			}
		}
		else {

			if(life > 1) {
				life-=laser.attact;
				//draw hurt
				batch.draw(hurt, 0, 0, GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT);
			}
			else {
				life = 0;
				destroyed = true;
			}
		}

	}
	@Override
	public Laser[] fireLasers() {
		Laser[] laser = new Laser[1];
		laser[0] = new Laser(xPosition+ 0.5f*width,yPosition + height ,
				laserWidth, laserHeight, 
				laserMovementSpeed, laserTextureRegion, laserSound);

		timeSinceLastShot = 0;

		return laser;
	}
	public SuperLaser fireSuperLaser() {
		SuperLaser superLaser = new SuperLaser(xPosition+ 0.5f*width,yPosition + height ,
				width, GameScreen.WORLD_HEIGHT, 0.3f, 
				superlaserTexture, superlaserSound );
		return superLaser;
	}
	public ChagreEffects charge() {
		ChagreEffects charge = new ChagreEffects(xPosition+ width/2, yPosition + height/2,
				width, height, 0.5f, 
				chargeEffectsTexture, chargeEffcetsSound);
		return charge;
	}


}
