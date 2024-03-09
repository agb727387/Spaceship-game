package com.spaceship.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author andrewgarcia
 */
public class BarricadeSection {
    public Vector2 position;
    public Sprite sprite;
    public boolean destroyed = true;
    
    public BarricadeSection(Vector2 pos, Texture img) 
    {
        position = pos;
        sprite = new Sprite(img);
        sprite.setScale(10);
    }
    
    public void Draw(SpriteBatch batch)
    {
        //Update(Gdx.graphics.getDeltaTime());
        //sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        //spriteBullet.setPosition(positionBullet.x, positionBullet.y);
        //spriteBullet.draw(batch);
    }
}
