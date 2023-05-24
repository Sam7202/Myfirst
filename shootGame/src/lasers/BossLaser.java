package lasers;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import screens.GameScreen;

public class BossLaser extends Laser{


	Random r = new Random();
    float totalAnimationTime;
    float xDirection;
    float yMovementSpeed;
    Texture animationTexture;
    TextureRegion[][] textureRegion2D;
    TextureRegion[] textureRegion1D;
    Animation<TextureRegion> animation;
    float timer = 0;

    public BossLaser(float xPosition, float yPosition, float width, float height, float movementspeed,
    		TextureRegion bossLasetextureRegion, Sound bossLaserSound) {
    	super(xPosition, yPosition, width, height, movementspeed, bossLasetextureRegion, bossLaserSound);
    	this.laserSound = bossLaserSound;
    	//animation
    	this.totalAnimationTime = 1f;
    	this.animationTexture = new Texture(bossLasetextureRegion.getTexture().getTextureData());
    	splitTexture();
    	createAnimation();
    	animation.setPlayMode(Animation.PlayMode.LOOP);
    	
    	//random the direction
    	double direciton = 0;
    	direciton = r.nextDouble()* 2*Math.PI;
    	xDirection = (float)Math.cos(direciton);
    	this.yMovementSpeed = movementspeed;
    	
    	this.attact = 2;
    	}
    @Override
    public Rectangle getBoundingBox() {
    	//adjust bounding box
    	float reserve = 3;

		laserBoungingBox.set(xPosition+reserve, yPosition+reserve, width-2*reserve, height-2*reserve);
		return laserBoungingBox;
	}
    @Override
    public void update(float deltaTime) {
        timer += deltaTime;
        if (xPosition < 0 || xPosition > GameScreen.WORLD_WIDTH - width) {
            movementspeed *= -1;
        }


            xPosition+= xDirection*movementspeed*deltaTime;
            yPosition+= yMovementSpeed*deltaTime;

    }
    @Override
    public void draw(Batch batch) {
        batch.draw(animation.getKeyFrame(timer), xPosition, yPosition, width, height);
    }

    public void createAnimation() {
        int index = 0;
        for (int i = 0; i < 4; i++) {
            textureRegion1D[index] = textureRegion2D[0][i];
            index++;
        }
        animation = new Animation<TextureRegion>(totalAnimationTime / 4,
                textureRegion1D);

    }
    @Override
    public void setAudio() {
		laserSound.play(0.5f);
	}

    public void splitTexture() {
        textureRegion2D = TextureRegion.split(animationTexture, 32, 32);
        //covert to 1D array
        textureRegion1D = new TextureRegion[4];
    }



}
