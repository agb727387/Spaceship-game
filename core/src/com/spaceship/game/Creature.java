package com.spaceship.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Creature {
    public Vector2 position;
    public Vector2 positionInitial;
    public Sprite sprite;
    public boolean alive = true;
    
    public Creature(Vector2 pos, Texture img)
    {
        position = pos;
        positionInitial = pos;
        sprite = new Sprite(img);
        sprite.setScale(3);
    }
    
    public void Draw(SpriteBatch batch)
    {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
