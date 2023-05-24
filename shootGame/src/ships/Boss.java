package ships;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lasers.BossLaser;
import lasers.Laser;

public class Boss extends EnemyShips{

	public static boolean IS_lIVING ;
	public static boolean HAD_COLLIDE_WITH_SUPERLASER;
	//animation
	TextureRegion[][] textureRegion2D;
	TextureRegion[] textureRegion1D;
	Animation<TextureRegion> bossAnimation;
	float totalAnimationTime = 1f;
	float timer = 0;
	
	private Texture healthBarBaseTexture;
	private Texture healthBarcontentTexture;
	private Texture healthBarfrontTexture;
	int originalLife;
	Texture bossTexture;
	
	public Music bossMusic;
	public Boss(int life, float movementSpeed, int shield, float xCenter, float yCenter, float width, float height,
			float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots,
			TextureRegion shipTexureRegion, TextureRegion shieldTextureRegion,
			TextureRegion laserTextureRegion, Sound laserSound, Music bossMusic) {
		super(life, movementSpeed, shield, xCenter, yCenter, width, height, laserWidth, laserHeight, laserMovementSpeed,
				timeBetweenShots, shipTexureRegion, shieldTextureRegion, laserTextureRegion, laserSound);
		bossTexture = new Texture(shipTexureRegion.getTexture().getTextureData());
		
		splitTexture();
		creatAnimation();
		
		IS_lIVING = false;
		totalAnimationTime = 1f;
		HAD_COLLIDE_WITH_SUPERLASER = false;
		//health bar
		healthBarBaseTexture = new Texture(Gdx.files.internal("effects/lifebar_back.png"));
		healthBarcontentTexture = new Texture(Gdx.files.internal("effects/lifebar_content.png"));
		healthBarfrontTexture= new Texture(Gdx.files.internal("effects/lifebar_front.png"));
		
		this.originalLife = life;
		this.bossMusic = bossMusic;
		this.bossMusic.play();
		this.bossMusic.setLooping(true);
		this.bossMusic.setVolume(0.1f);
	}

	@Override
	public Laser[] fireLasers() {
		
		Random r = new Random();
		ArrayList<BossLaser> lasers= new ArrayList<BossLaser>();
		//fire 1 to 3 laser at the same time
		for(int i = 0 ; i<1+r.nextInt(3) ; i++) {
			
			lasers.add(new BossLaser(xPosition+ 0.5f*width,yPosition -laserHeight/2f,
					laserWidth, laserHeight, 
					laserMovementSpeed, laserTextureRegion, laserSound));
		}
		BossLaser[] bossLaser = new BossLaser[lasers.size()];
		lasers.toArray(bossLaser);
		timeSinceLastShot = 0;
		
		return  bossLaser;
	}
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		timer += deltaTime;
		this.shipBoundingBox.set(xPosition, yPosition, width, height);
	}
	@Override
	public void draw(Batch batch) {
		batch.draw(bossAnimation.getKeyFrame(timer), 
				xPosition, yPosition, width, height);
		drawHealthBar(batch);
		if(shield > 0) {
			batch.draw(shieldTextureRegion, xPosition, yPosition, width, height);
		}
	}
	private void drawHealthBar(Batch batch) {
		float heaithBarWidth = 40;
		float heaithBarHeight = 9;
		
		if ((float)life/originalLife>0.6f)
			batch.setColor(Color.GREEN);
		else if ((float)life/originalLife>0.2f)
			batch.setColor(Color.ORANGE);
		else
			batch.setColor(Color.RED);
		batch.draw(healthBarcontentTexture,7,heaithBarHeight*0.21f,(heaithBarWidth*0.78f)*((float)life/originalLife),0.32f * heaithBarHeight);
		
		batch.setColor(Color.WHITE);
		batch.draw(healthBarBaseTexture, 0, 0, heaithBarWidth, heaithBarHeight);
		batch.draw(healthBarfrontTexture, 0, 0, heaithBarWidth, heaithBarHeight);
		
		batch.draw(bossAnimation.getKeyFrame(timer),
				1, 1, 7, 7);
		
	}

	public void creatAnimation() {
		int index = 0;
		for(int i = 0 ; i<4 ; i++) {
			textureRegion1D[index] = textureRegion2D[0][i];
			index++;
		}
		bossAnimation = new Animation<TextureRegion>(totalAnimationTime/4,
				textureRegion1D);
		bossAnimation.setPlayMode(PlayMode.LOOP);		
	}

	public void splitTexture() {
		textureRegion2D =TextureRegion.split(bossTexture, 90, 52);
		//covert to 1D array
		textureRegion1D = new TextureRegion[4];		
	}
	public boolean canDropBuff() {
		Random r = new Random();
		//10% to drop buff
		if(r.nextInt(10)+1 == 1) {
			return true;
		}
		return false;
		
	}

}
