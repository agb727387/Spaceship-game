package com.spaceship.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class BarricadeSection {
    public Sprite sprite;
    public boolean isNotDestroyed;
    
    public BarricadeSection(Texture img, Vector2 position)
    {
        sprite = new Sprite(img);
        sprite.setPosition(position.x, position.y);
        sprite.setScale(1);
        isNotDestroyed = true;
    }
    
    public void Draw(SpriteBatch batch) 
    {
        if(isNotDestroyed)
        {
            sprite.draw(batch);
        }
    }
}
