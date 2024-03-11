package com.spaceship.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ship {
    public Vector2 position;
    public Vector2 positionBullet;
    
    public Sprite sprite;
    public Sprite spriteBullet;
    public float speed = 225;
    public float speedBullet = 300;
    
    public Ship(Texture img, Texture imgBullet)
    {
        sprite = new Sprite(img);
        spriteBullet = new Sprite(imgBullet);
        spriteBullet.setScale(1);
        sprite.setScale(3, 3);
        position = new Vector2(Gdx.graphics.getWidth()/2, sprite.getScaleY() * sprite.getHeight()/2);
        positionBullet = new Vector2(0, 10000);
    }
    
    public void Update(float deltaTime)
    {
        
        // Boolean variables set to true when the player presses the corresponding key . . .
        boolean isLeftPressed = Gdx.input.isKeyPressed(Keys.LEFT);
        boolean isRightPressed = Gdx.input.isKeyPressed(Keys.RIGHT);
        boolean isUpPressed = Gdx.input.isKeyPressed(Keys.UP);
        boolean isDownPressed = Gdx.input.isKeyPressed(Keys.DOWN);
        
        // Spacebar button logic for bullet firing . . .
        if (Gdx.input.isKeyPressed(Keys.SPACE) && positionBullet.y >= Gdx.graphics.getHeight())
        {
            positionBullet.x = position.x;
            positionBullet.y = position.y;
        }
        
        // Conditional statements that allow the player to move along the x or y axis if an arrow key is pressed . . .
        if (isLeftPressed)
        {
            position.x -= deltaTime * speed;
        }
        
        if (isRightPressed)
        {
            position.x += deltaTime * speed;
        }
        
        if (isUpPressed)
        {
            position.y += deltaTime * speed;
        }
        
        if (isDownPressed)
        {
            position.y -= deltaTime * speed;
        }
        
        if (position.y - (sprite.getWidth() * sprite.getScaleY()/2) <= 0)
        {
            position.y = sprite.getWidth() * sprite.getScaleY()/2;
        }
        
        if (position.y + (sprite.getWidth() * sprite.getScaleY()/2) >= Gdx.graphics.getHeight())
        {
            position.y = Gdx.graphics.getHeight() - sprite.getHeight() * (sprite.getScaleY()/2);
        }
        
        if (position.x - (sprite.getWidth() * sprite.getScaleX()/2) <= 0)
        {
            position.x = sprite.getWidth() * sprite.getScaleX()/2;
        }
        
        // Conditional statement that ensures player doesn't move outside of the right side of the window . . .
        if (position.x + (sprite.getWidth() * sprite.getScaleX()/2) >= Gdx.graphics.getWidth())
        {
            position.x = Gdx.graphics.getWidth() - sprite.getWidth() * (sprite.getScaleX()/2);
        }
        
        
        
        positionBullet.y += deltaTime * speedBullet;
    }
    
    public void Draw(SpriteBatch batch)
    {
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        spriteBullet.setPosition(positionBullet.x, positionBullet.y);
        spriteBullet.draw(batch);
    }
}
