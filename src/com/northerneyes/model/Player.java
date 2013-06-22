package com.northerneyes.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements IEntity {


    public Vector2 	Position = new Vector2();

    public float Size;
    private final float DefaultSize = 1f;
    private int power = 0;
    private int combo = 0;
    private int maxCombo = 0;
    private int powerUpCount = 0;
    private int powerDownCount = 0;
    private int purplePowerCount = 0;
    private int yellowMadnessCount = 0;
    private int shapeCount = 0;

    public Player(Vector2 position) {
		
		this.Position = position;
        Size = 1f;
	}



    public void Clear()
    {
        power = 0;
        combo = 0;
        maxCombo = 0;
        powerUpCount = 0;
        powerDownCount = 0;
        purplePowerCount = 0;
        yellowMadnessCount = 0;
        shapeCount = 0;
    }

	

	public void update(float delta) {

	}

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setSize() {
        Size = DefaultSize + combo / 10f;

         if (Size > 4f)
        {
            Size = ((Size - 4f) / 4f) + 4f;
        }
    }

    public void addPowerUpCount() {
        powerUpCount++;
    }

    public void addCombo() {
        combo++;
        if (combo > maxCombo)
        {
            maxCombo = combo;
        }
    }
    public void resetCombo() {
        combo = (int) Math.ceil(combo / 2f);
    }

    public void addPowerDownCount() {
        powerDownCount++;
    }


    public void addPurplePowerCount() {
        purplePowerCount++;
    }

    public void addYellowMadnessCount() {
        yellowMadnessCount++;
    }

    public void addShapeCount() {
        shapeCount++;
    }


}