package com.spaceship.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;

public class SpaceshipGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        Texture imgBullet;
        Texture imgCreature;
        Ship ship;
        Creature[] creatures;
        BitmapFont font;
        
        int numWidthCreatures = 10;
        int numHeightCreatures = 5;
        int spacingCreatures = 41;
        
        // Variables to help move the aliens along the (x, y) axis . . .
        int minXCreatures;
        int minYCreatures;
        int maxXCreatures;
        int maxYCreatures;
        
        // Variables to position scoreboard along the (x, y) axis . . .
        int scoreBoardX = 7;
        int scoreBoardY = 30;
        
        int directionCreatures = 1;
        float speedCreatures = 20; // Change speed of creatures . . .
        int score = 0; // Score to keep track of the number of creatures killed . . .
        
        // Offset to move the creatures . . .
        Vector2 offsetCreatures;
        
	@Override
	public void create() {
                offsetCreatures = Vector2.Zero;
		batch = new SpriteBatch();
		img = new Texture("spaceship.png");
                imgBullet = new Texture("spacebullet.png");
                imgCreature = new Texture("spacecreature.png");
                ship = new Ship(img, imgBullet);
                creatures = new Creature[numWidthCreatures * numHeightCreatures];
                font = new BitmapFont();
                
                int index = 0;
                
                for (int y = 0; y < numHeightCreatures; y++)
                {
                    for (int x = 0; x < numWidthCreatures; x++)
                    {
                        Vector2 position = new Vector2(x * spacingCreatures, y * spacingCreatures);
                        position.x += Gdx.graphics.getWidth()/2;
                        position.y += Gdx.graphics.getHeight();
                        position.x -= (numWidthCreatures/2) * spacingCreatures;
                        position.y -= (numHeightCreatures) * spacingCreatures;
                        creatures[index] = new Creature(position, imgCreature);
                        index++;
                    }
                }
	}
        
        int amountAliveCreatures = 0;
        
	@Override
	public void render() {
                float deltaTime = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
                font.setColor(Color.ORANGE);
                font.getData().setScale(1.5f);
                font.draw(batch, "Score: " + score, scoreBoardX, scoreBoardY);
		ship.Draw(batch);
                
                // Loop with conditional statements that determine whether bullet intersects creature, killing it afterwards . . .
                for (int count = 0; count < creatures.length; count++)
                {
                    if (creatures[count].alive)
                    {
                        if (ship.spriteBullet.getBoundingRectangle().overlaps(creatures[count].sprite.getBoundingRectangle()))
                        {
                            ship.positionBullet.y = 10000;
                            creatures[count].alive = false;
                            score++;
                            break;
                        }
                    }
                }
                
                minXCreatures = 10000;
                minYCreatures = 0;
                maxXCreatures = 0;
                maxYCreatures = 0;
                
                amountAliveCreatures = 0;
                
                for (int count = 0; count < creatures.length; count++)
                {
                    if (creatures[count].alive)
                    {
                        int indexX = count % numWidthCreatures;
                        int indexY = count / numWidthCreatures;
                    
                        if (indexX > maxXCreatures)
                        {
                            maxXCreatures = indexX;
                        }
                    
                        if (indexX < minXCreatures)
                        {
                            minXCreatures = indexX;
                        }
                    
                        if (indexY > maxYCreatures)
                        {
                            maxYCreatures = indexY;
                        }
                    
                        if (indexY > minYCreatures)
                        {
                            minYCreatures = indexY;
                        }
                        
                        amountAliveCreatures++;
                    }
                    
                }
                
                // Conditional statement that regenerates creatures . . .
                if (amountAliveCreatures == 0)
                {
                    for (int count = 0; count < creatures.length; count++)
                    {
                        creatures[count].alive = true;
                    }
                    offsetCreatures = new Vector2(0, 0);
                    batch.end();
                    speedCreatures = 50;
                    return;
                }
                
                offsetCreatures.x += directionCreatures * deltaTime * speedCreatures;
                
                if (creatures[maxXCreatures].position.x >= Gdx.graphics.getWidth())
                {
                    directionCreatures = -1;
                    offsetCreatures.y -= creatures[0].sprite.getHeight() * creatures[0].sprite.getScaleY() * 0.3f;
                    speedCreatures += 10;
                }
                
                if (creatures[minXCreatures].position.x <= 0)
                {
                    directionCreatures = 1;
                    offsetCreatures.y -= creatures[0].sprite.getHeight() * creatures[0].sprite.getScaleY() * 0.3f;
                    speedCreatures += 10;
                }
                
                // Conditional statement that terminate program if creatures reach bottom of screen . . .
                if (creatures[minYCreatures].position.y <= 0)
                {
                    Gdx.app.exit();
                }
                
                // For loop that only draws the creatures if they are still alive, and moves them with offsets . . .
                for (int count = 0; count < creatures.length; count++)
                {
                    creatures[count].position = new Vector2(creatures[count].positionInitial.x + 
                            offsetCreatures.x, creatures[count].positionInitial.y + offsetCreatures.y);
                    
                    if (creatures[count].alive)
                    {
                        creatures[count].Draw(batch);
                        
                        // Conditional statement that terminates program if a creature collides with the ship . . .
                        if (creatures[count].sprite.getBoundingRectangle().overlaps(ship.sprite.getBoundingRectangle()))
                        {
                            Gdx.app.exit();
                        }
                    }
                }
                
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
                font.dispose();
		img.dispose();
	}
}
