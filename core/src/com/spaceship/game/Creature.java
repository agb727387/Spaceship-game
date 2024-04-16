package com.spaceship.game;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;

public class Creature {
    public Vector2 position;
    public Vector2 positionBullet;
    public Vector2 positionInitial;
    public Sprite sprite;
    public Sprite spriteBullet;
    public boolean isAlive = true;
    public float speedBullet = 2f;
    Random random = new Random(); // Random number generator . . .
    
    private float bulletTimer = 0; // Timer to track time since the last bullet was fired
    private float bulletDelay = 3; // Delay between each bullet firing (adjust as needed)
    private float firingProbability = .00001f; // Probability of firing a bullet on each frame
    
    public Creature(Vector2 pos, Texture img, Texture imgBullet)
    {
        position = pos;
        positionInitial = pos;
        sprite = new Sprite(img);
        spriteBullet = new Sprite(imgBullet);
        sprite.setScale(3);
        spriteBullet.setScale(1);
        positionBullet = new Vector2(50, 10000);
    }
    
    public void Update(float deltaTime)
    {
        // Update bullet timer
        bulletTimer += deltaTime;

        // Generate a random value between 0 and 1
        float randomValue = random.nextFloat();

        // Check if enough time has passed since the last bullet and if random value meets firing probability
        if (bulletTimer >= bulletDelay && randomValue < firingProbability)
        {
            // Fire a bullet
            positionBullet.set(position.x, position.y);
            bulletTimer = 0;
        }

        // Update bullet position
        positionBullet.y -= deltaTime * speedBullet;
    }
    public void Draw(SpriteBatch batch)
    {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);

        // Draw bullet if it's visible
        if (positionBullet.y > 0)
        {
            spriteBullet.setPosition(positionBullet.x, positionBullet.y);
            spriteBullet.draw(batch);
        }
    }
}
