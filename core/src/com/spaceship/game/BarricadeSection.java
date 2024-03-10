package com.spaceship.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class BarricadeSection {
    public Sprite sprite;
    public boolean isDestroyed;
    
    public BarricadeSection(Texture texture, Vector2 position)
    {
        sprite = new Sprite(texture);
        sprite.setPosition(position.x, position.y);
        sprite.setScale(3);
        isDestroyed = false;
        // Comment . . .
        // Another comment . . .
        // A third comment . . .
    }
    
    public void Draw(SpriteBatch batch) 
    {
        if(!isDestroyed)
        {
            sprite.draw(batch);
        }
    }
    
    public Rectangle getBounds()
    {
        return sprite.getBoundingRectangle();
    }
}
