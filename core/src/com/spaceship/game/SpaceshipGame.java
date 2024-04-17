package com.spaceship.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class SpaceshipGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        Texture imgBullet;
        Texture imgCreature;
        Texture imgBarricadeSection;
        Ship ship;
        
        BarricadeSection[][] barricadeSectionsOne;
        BarricadeSection[][] barricadeSectionsTwo;
        BarricadeSection[][] barricadeSectionsThree;
        
        Creature[] creatures;
        BitmapFont font;
        
        int numWidthCreatures = 9;
        int numHeightCreatures = 4;
        int spacingCreatures = 40;
        
        int numWidthBarricadeSections = 20;
        int numHeightBarricadeSections = 10;
        
        // Variables to help move the aliens along the (x, y) axis . . .
        int minXCreatures;
        int minYCreatures;
        int maxXCreatures;
        int maxYCreatures;
        
        // Variables to position scoreboard along the (x, y) axis . . .
        int scoreBoardX = 7;
        int scoreBoardY = 30;
        
        int directionCreatures = 1;
        float speedCreatures = 10; // Change speed of creatures . . .
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
                imgBarricadeSection = new Texture("spacebarricadesection.png");
                ship = new Ship(img, imgBullet);
                creatures = new Creature[numWidthCreatures * numHeightCreatures];
                
                // Create three barricades . . .
                barricadeSectionsOne = new BarricadeSection[numHeightBarricadeSections][numWidthBarricadeSections];
                barricadeSectionsTwo = new BarricadeSection[numHeightBarricadeSections][numWidthBarricadeSections];
                barricadeSectionsThree = new BarricadeSection[numHeightBarricadeSections][numWidthBarricadeSections];
                
                // Initialize barricade sections :)
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        Vector2 position = new Vector2((col + 6) * imgBarricadeSection.getWidth(), (row + 10) * imgBarricadeSection.getHeight());
                        barricadeSectionsOne[row][col] = new BarricadeSection(imgBarricadeSection, position);
                        
                    }
                }
                
                // Second barricade . . .
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        Vector2 position = new Vector2((col + 36) * imgBarricadeSection.getWidth(), (row + 10) * imgBarricadeSection.getHeight());
                        barricadeSectionsTwo[row][col] = new BarricadeSection(imgBarricadeSection, position);
                    }
                }
                
                // Third barricade . . .
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        Vector2 position = new Vector2((col + 66) * imgBarricadeSection.getWidth(), (row + 10) * imgBarricadeSection.getHeight());
                        barricadeSectionsThree[row][col] = new BarricadeSection(imgBarricadeSection, position);
                    }
                }
                
                font = new BitmapFont();
                
                
                int indexCreature = 0;
                
                for (int y = 0; y < numHeightCreatures; y++)
                {
                    for (int x = 0; x < numWidthCreatures; x++)
                    {
                        Vector2 position = new Vector2(x * spacingCreatures, y * spacingCreatures);
                        position.x += Gdx.graphics.getWidth()/2;
                        position.y += Gdx.graphics.getHeight();
                        position.x -= (numWidthCreatures/2) * spacingCreatures;
                        position.y -= (numHeightCreatures) * spacingCreatures;
                        creatures[indexCreature] = new Creature(position, imgCreature, imgBullet);
                        indexCreature++;
                    }
                }
	}
        
        int amountAliveCreatures = 0;
        
	@Override
	public void render() {
                float deltaTime = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
                
                // Pixel art font style code . . .
                FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PixelEmulator-xq08.ttf"));
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                
                parameter.size = 18; // Set font size . . .
                parameter.color = Color.WHITE; // Set font color . . .
                
                font = generator.generateFont(parameter); // Generate the BitmapFont . . .
                
                generator.dispose();
                
                font.draw(batch, "Point: " + score, scoreBoardX, scoreBoardY); // Draw score board on bottom left side of screen . . .
                
                // Recursive method: Check to see if section is destroyed. If it is, add 1 to barricade score, otherwise,
                // call function again to check other sections
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                         font.draw(batch, "Barricade Destroyed: " + calculateTotalScore(barricadeSectionsTwo, row, col), 340, 30);       
                    }
                }
                
		ship.Draw(batch);
                
                // Loop with conditional statements that determine whether bullet intersects creature, killing it afterwards . . .
                for (int count = 0; count < creatures.length; count++)
                {
                    if (creatures[count].isAlive)
                    {
                        if (ship.spriteBullet.getBoundingRectangle().overlaps(creatures[count].sprite.getBoundingRectangle()))
                        {
                            ship.positionBullet.y = 10000;
                            creatures[count].isAlive = false;
                            score++;
                            break;
                        }
                    }
                }
                
                // Render barricade sections . . .
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        barricadeSectionsOne[row][col].Draw(batch);
                    }
                }
                
                // Second barricade . . .
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        barricadeSectionsTwo[row][col].Draw(batch);
                    }
                }
                
                // Third barricade . . .
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        barricadeSectionsThree[row][col].Draw(batch);
                    }
                }
                
                // Loop that checks to see if bullet intersects with barricade section, destroying it afterwards if it does . . .
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        if (barricadeSectionsOne[row][col].isNotDestroyed)
                        {
                            if (ship.spriteBullet.getBoundingRectangle().overlaps(barricadeSectionsOne[row][col].sprite.getBoundingRectangle()))
                            {
                                ship.positionBullet.y = 10000;
                                barricadeSectionsOne[row][col].sprite.setScale(barricadeSectionsOne[row][col].sprite.getScaleX(), barricadeSectionsOne[row][col].sprite.getScaleY());
                                barricadeSectionsOne[row][col].isNotDestroyed = false;
                                break;
                            }
                        }
                    }
                }
                
                // Second barricade . . .
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        if (barricadeSectionsTwo[row][col].isNotDestroyed)
                        {
                            if (ship.spriteBullet.getBoundingRectangle().overlaps(barricadeSectionsTwo[row][col].sprite.getBoundingRectangle()))
                            {
                                ship.positionBullet.y = 10000;
                                barricadeSectionsTwo[row][col].isNotDestroyed = false;
                                break;
                            }
                        }
                    }
                }
                
                // Third barricade
                for (int row = numHeightBarricadeSections - 1; row >= 0; row--)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        if (barricadeSectionsThree[row][col].isNotDestroyed)
                        {
                            if (ship.spriteBullet.getBoundingRectangle().overlaps(barricadeSectionsThree[row][col].sprite.getBoundingRectangle()))
                            {
                                ship.positionBullet.y = 10000;
                                barricadeSectionsThree[row][col].isNotDestroyed = false;
                                break;
                            }
                        }
                    }
                }
                
                // Check to see if creatures bullets hit barricades . . .
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        if (barricadeSectionsOne[row][col].isNotDestroyed)
                        {
                            for (Creature creature : creatures)
                            {
                                // Check if the creature is alive and its bullet overlaps with the barricade section
                                if (creature.isAlive && creature.spriteBullet.getBoundingRectangle().overlaps(barricadeSectionsOne[row][col].sprite.getBoundingRectangle()))
                                {
                                    // Handle collision between the creature's bullet and the barricade section
                                    creature.positionBullet.y = 10000;
                                    barricadeSectionsOne[row][col].isNotDestroyed = false;
                                    break; // Exit the inner loop since the barricade is destroyed
                                }
                            }
                        }
                    }
                }


                
                // Second barricade
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        if (barricadeSectionsTwo[row][col].isNotDestroyed)
                        {
                            for (Creature creature : creatures)
                            {
                                // Check if the creature is alive and its bullet overlaps with the barricade section
                                if (creature.isAlive && creature.spriteBullet.getBoundingRectangle().overlaps(barricadeSectionsTwo[row][col].sprite.getBoundingRectangle()))
                                {
                                    // Handle collision between the creature's bullet and the barricade section
                                    creature.positionBullet.y = 10000;
                                    barricadeSectionsTwo[row][col].isNotDestroyed = false;
                                    break; // Exit the inner loop since the barricade is destroyed
                                }
                            }
                        }
                    }
                }
                
                // Third barricade
                for (int row = 0; row < numHeightBarricadeSections; row++)
                {
                    for (int col = 0; col < numWidthBarricadeSections; col++)
                    {
                        if (barricadeSectionsThree[row][col].isNotDestroyed)
                        {
                            for (Creature creature : creatures)
                            {
                                // Check if the creature is alive and its bullet overlaps with the barricade section
                                if (creature.isAlive && creature.spriteBullet.getBoundingRectangle().overlaps(barricadeSectionsThree[row][col].sprite.getBoundingRectangle()))
                                {
                                    // Handle collision between the creature's bullet and the barricade section
                                    creature.positionBullet.y = 10000;
                                    barricadeSectionsThree[row][col].isNotDestroyed = false;
                                    break; // Exit the inner loop since the barricade is destroyed
                                }
                            }
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
                    if (creatures[count].isAlive)
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
                        creatures[count].isAlive = true;
                    }
                    offsetCreatures = new Vector2(0, 0);
                    batch.end();
                    speedCreatures += 10;
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
                
                // Conditional statement that terminates program if creatures reach bottom of screen . . .
                if (creatures[minYCreatures].position.y <= 0)
                {
                    Gdx.app.exit();
                }
                
                // For loop that only draws the creatures if they are still alive, and moves them with offsets . . .
                for (int count = 0; count < creatures.length; count++)
                {
                    creatures[count].position = new Vector2(creatures[count].positionInitial.x + 
                            offsetCreatures.x, creatures[count].positionInitial.y + offsetCreatures.y);
                    
                    if (creatures[count].isAlive)
                    {
                        creatures[count].Draw(batch);
                        for (Creature creature : creatures)
                        {
                            creature.Update(Gdx.graphics.getDeltaTime()); // Pass delta time to update method
                        }
                        
                        // Conditional statement that terminates program if a creature collides with the ship . . .
                        if (creatures[count].sprite.getBoundingRectangle().overlaps(ship.sprite.getBoundingRectangle()))
                        {
                            Gdx.app.exit();
                        }
                        
                        // If creature bullet hits ship, then game ends . . .
                        if (creatures[count].spriteBullet.getBoundingRectangle().overlaps(ship.sprite.getBoundingRectangle()))
                        {
                            // Exception handling: If bullet doesn't successfully kill player, throw an exception
                            try
                            {
                                Gdx.app.exit();
                            }
                            catch (Exception e)
                            {
                                System.out.println("Error! Couldn't handle ship collision properly . . .");
                            }
                        }
                    }
                }
                
		batch.end();
	}
        
        

        private int calculateTotalScore(BarricadeSection[][] barricadeSections, int row, int col)
        {
            // Base case: If we've checked all sections, return 0
            if (row >= barricadeSections.length || col >= barricadeSections[0].length)
            {
                return 0;
            }

            // Check if this section is destroyed
            int score = barricadeSections[row][col].isNotDestroyed ? 0 : 1;

            // Move to the next section
            int nextRow = row;
            int nextCol = col + 1;
            if (nextCol >= barricadeSections[0].length)
            {
                nextRow++;
                nextCol = 0;
            }

            // Recursive call to calculate score of remaining sections
            return score + calculateTotalScore(barricadeSections, nextRow, nextCol);
        }
	
	@Override
	public void dispose() {
		batch.dispose();
                font.dispose();
		img.dispose();
	}
}
