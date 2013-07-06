package com.northerneyes.CatchTheNotes.model;


import com.badlogic.gdx.math.Vector2;

public class Player implements IEntity {

    public enum PlayerState {
        NORMAL,
        HOVER;

        // Converts from an ordinal value to the ResponseCode
        public static PlayerState valueOf(int index) {
            PlayerState[] values = PlayerState.values();
            if (index < 0 || index >= values.length) {
                return NORMAL;
            }
            return values[index];
        }
    }

    public enum PulseType {
        NORMAL,
        BAD,
        GOOD,
        SUCTION,
        NONE;

        // Converts from an ordinal value to the ResponseCode
        public static PulseType valueOf(int index) {
            PulseType[] values = PulseType.values();
            if (index < 0 || index >= values.length) {
                return NONE;
            }
            return values[index];
        }
    }

    public Vector2 	Position = new Vector2();

    public float Size;
    private final float DefaultSize = 1.8f;
    private int power = 0;
    private int combo = 1;
    private int maxCombo = 0;


    public PulseType Type = PulseType.NONE;
    public PlayerState State = PlayerState.NORMAL;

   // public boolean ShowGameInfo = false;
    private float pulseCoef = 1;
    private boolean reverse = false;

    public Player(Vector2 position) {
		this.Position = position;
        Size = DefaultSize;
	}

    public void Clear()
    {
        power = 0;
        combo = 1;
        maxCombo = 0;

    }


    public float getPulseCoef() {
        return pulseCoef;
    }

	public void update(float delta) {

        if(Type != PulseType.NONE)
        {
            if(pulseCoef >= 1.5f )
                reverse = true;

            if(reverse)
                pulseCoef -=  0.08;
            else
                pulseCoef += 0.08;

            if(pulseCoef < 1f)
            {
                Type = PulseType.NONE;
                reverse = false;
                pulseCoef = 1f;
            }

        }
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

    public void addCombo() {
        combo++;
        if (combo > maxCombo)
        {
            maxCombo = combo;
        }
    }

    public int getCombo()
    {
        return combo;
    }

    public void resetCombo() {
        combo = (int) Math.ceil(combo / 2f);
    }


}