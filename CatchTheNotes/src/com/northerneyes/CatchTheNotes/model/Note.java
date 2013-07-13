package com.northerneyes.CatchTheNotes.model;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.northerneyes.CatchTheNotes.controller.WorldController;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class Note implements IEntity {


    public enum NoteType {
        NORMAL,
        POWER_DOWN,
        POWER_UP,
        SUCTION,
        YELLOW_MADDNESS,
        COLLECTED,
        RECYCLE,
        ERROR;

        // Converts from an ordinal value to the ResponseCode
        public static NoteType valueOf(int index) {
            NoteType[] values = NoteType.values();
            if (index < 0 || index >= values.length) {
                return ERROR;
            }
            return values[index];
        }
    }

    private int InitialTTL;
    private final Vector2 initialVelocity;
    public Vector2 Position;
    public Vector2 Velocity;
    public float Angle;
    public float AngularVelocity;
    public Color Color;
    public float Size;
    public float InitialSize;
    public static float MAX_SIZE = 4f;
    public int TTL;
    private float a;
    private float RComponent;
    private  float GComponent;
    private float BComponent;

    public float Visibility = 1f;
    public NoteType Type;
    public int ViewType; //image
    private float amp;
    private Random random;
    public boolean Recycled = false;
    public Note(Vector2 position, Vector2 velocity, float angle, float angularVelocity, NoteType type, float size, int ttl, int viewType, float amp)
    {
        Position = position;
        Velocity = velocity;
        initialVelocity = velocity;
        Angle = angle;
        AngularVelocity = angularVelocity;
        Type = type;
        Size = size;
        InitialSize = size;
        TTL = ttl;
        InitialTTL = ttl;
        ViewType = viewType;
        this.amp = amp;

        a = 2*velocity.y/(float)(InitialTTL);
        SetType(type);  //Установка цвета под определённый тип
    }

    public Note(Vector2 position, NoteType type, float size, int viewType, boolean recycled)
    {
        Position = position;
        Velocity = new Vector2(0,0);
        initialVelocity = new Vector2(0,0);
        Angle = 0;
        AngularVelocity = 0;
        Type = type;
        TTL = 200;
        Size = size;
        InitialSize = size;
        ViewType = viewType;
        Recycled = recycled;
        SetType(type);  //Установка цвета под определённый тип
    }

    /// <summary>
    /// Добавление импульса (используется бонусом)
    /// </summary>
    /// <param name="vector"></param>
    public void ApplyImpulse(Vector2 vector)
    {
        Velocity.add(vector);
    }

    /// <summary>
    /// Обновление одной частички
    /// </summary>
    @Override
    public void update(float delta)
    {
        if(Recycled)
            return;

        if(Type == NoteType.COLLECTED)
        {
            Color =  new Color(RComponent, GComponent, BComponent, Visibility);
            Visibility = Visibility - 0.04f;
            return;
        }


        TTL--;
        Position.add(Velocity);
        Angle += AngularVelocity;

        if(Type != NoteType.ERROR)
        {
            Velocity = new Vector2(Velocity.x, initialVelocity.y - a*(InitialTTL - TTL)); // - 2*initialVelocity.y/InitialTTL

            Size = InitialSize - (InitialSize/InitialTTL)*(InitialTTL - TTL);
        }
        if(Type == NoteType.POWER_UP)
        {
            Color =  new Color(1f, 1f, 0f, 1f);
        }

        if(Type == NoteType.NORMAL)
        {
            GComponent -= 0.005f;
            BComponent += 0.005f;

            Color =  new Color(RComponent, GComponent, BComponent, Visibility);
        }
        else if (Type == NoteType.YELLOW_MADDNESS)
        {
            Color = new Color(random.nextFloat(),
                    random.nextFloat(), random.nextFloat(), 1);
        }
    }

    /// <summary>
    /// Установка цвета частички
    /// </summary>
    /// <param name="type"></param>
    public void SetType(NoteType type)
    {
        Type = type;
        Color startColor = new Color(1f, 1f, 1f, 1);

        switch(type)
        {
            case NORMAL: startColor = new Color(0f, 0.85f, 0.3f + 0.4f*(-amp), Visibility); break; // Обычная
            case POWER_DOWN: startColor = new Color(1f, 0f, 0f, Visibility); break; // Красная
            case SUCTION: startColor = new Color(1f, 0f, 1f, Visibility); break; // Пурпурная
            case POWER_UP: startColor = new Color(1f, 1f, 0f, Visibility); break; // Желтая
            case YELLOW_MADDNESS: random = new Random(); break; // Мигающая
        }

        RComponent = startColor.r;
        GComponent = startColor.g;
        BComponent = startColor.b;

        Color = new Color(RComponent, GComponent, BComponent, Visibility);

        if (type == NoteType.ERROR)
        {
            Color = new Color(1f, 1f, 1f, 0.1f);
        }
    }
}
