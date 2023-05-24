package lasers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import effects.AnimationEffects;
import ships.PlayerShip;

public class SuperLaser extends AnimationEffects{
	Rectangle superLaserBondingBox;
	public int attact = 5;

	public SuperLaser(float xPosition, float yPosition, float width, float height,
			float totalAnimationTime, Texture superLaserTexture, Sound superLaserSound) {
		super(xPosition, yPosition, width, height,totalAnimationTime, superLaserTexture, superLaserSound);
		superLaserBondingBox = new Rectangle(xPosition, yPosition, width, height);
	}
	@Override
	public void update(PlayerShip playerShip , float deltaTime) {
		this.xPosition = playerShip.xPosition;
		this.yPosition = playerShip.yPosition+playerShip.height;
		superLaserBondingBox.set(xPosition, yPosition, width, height);
		timer +=  deltaTime;
	}
	@Override
	public void creatAnimation() {
		int index = 0;
		for(int i = 0 ; i<4 ; i++) {
				textureRegion1D[index] = textureRegion2D[i][0];
				index++;
		}
		for(int i = 0 ; i<4 ; i++) {
			textureRegion1D[index] = textureRegion2D[3-i][0];
			index++;
		}
		animation = new Animation<TextureRegion>(totalAnimationTime/8,
				 textureRegion1D);
				
	}
	@Override
	public void splitTexture() {
		textureRegion2D =TextureRegion.split(animationTexture, 61, 178);
		//covert to 1D array
		textureRegion1D = new TextureRegion[8];		
	}
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return superLaserBondingBox;
	}
	@Override
	protected void setAudio() {
		// TODO Auto-generated method stub
		animationSound.play(0.7f);
	}
}
