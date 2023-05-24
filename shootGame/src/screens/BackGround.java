package screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackGround {
    public static final int DEFAULT_SPEED = 80;
    public static final int ACCELERATION = 500;
    public static final int GOAL_REACH_ACCELERATION = 200;
    Texture img1;
    float y1;
    float y2;
    int speed; //In pixel/second
    int goalSpeed;
    float imageScale;
    boolean speedFixed;

    public BackGround() {
        img1 = new Texture("stars_background.png");
        y1 = 0;
        y2 = img1.getHeight();
        speed = 0;
        goalSpeed = DEFAULT_SPEED;
        imageScale = 1;
        speedFixed = true;
    }

    public void updateAndRender(float deltaTime, SpriteBatch batch) {

        if (speed < goalSpeed) {
            speed += GOAL_REACH_ACCELERATION * deltaTime;
            if (speed > goalSpeed)
                speed = goalSpeed;
        } else if (speed > goalSpeed) {
            speed -= GOAL_REACH_ACCELERATION * deltaTime;
            if (speed < goalSpeed)
                speed = goalSpeed;
        }


        if (!speedFixed)
            speed += ACCELERATION * deltaTime;

        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;

        if (y1 + img1.getHeight() * imageScale <= 0)
            y1 = y2 + img1.getHeight() * imageScale;
        if (y2 + img1.getHeight() * imageScale <= 0)
            y2 = y1 + img1.getHeight() * imageScale;

        batch.draw(img1, 0, y1, Gdx.graphics.getWidth(), img1.getHeight() * imageScale);
        batch.draw(img1, 0, y2, Gdx.graphics.getWidth(), img1.getHeight() * imageScale);

    }

    public void SpeedFixed(boolean speedFixed) {
        this.speedFixed = speedFixed;
    }

    public void resize(int width, int height) {
        imageScale = width / img1.getWidth();
    }

    public void setSpeed(int goalSpeed) {
        this.goalSpeed = goalSpeed;
    }
}
