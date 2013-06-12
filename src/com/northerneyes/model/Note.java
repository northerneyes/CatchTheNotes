package com.northerneyes.model;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class Note implements IEntity {

    private final int InitialTTL;
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

    public int Type;
    public int ViewType; //image
    private Random random;

    public Note(Vector2 position, Vector2 velocity, float angle, float angularVelocity, int type, float size, int ttl, int viewType)
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

        a = 2*velocity.y/(float)(InitialTTL);
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
        TTL--;
        Position.add(Velocity);
        Angle += AngularVelocity;

        if(Type != -1)
        {
            Velocity = new Vector2(Velocity.x, initialVelocity.y - a*(InitialTTL - TTL)); // - 2*initialVelocity.y/InitialTTL
           // Velocity = new Vector2(Velocity.x, Velocity.y - 0.002f);
            Size = InitialSize - (InitialSize/InitialTTL)*(InitialTTL - TTL);
          //  Size = (1 + Velocity.y)*1.33f;
          //  if (Size > 2f) Size = 2f;
        }

        if(Type == 0)
        {
            GComponent -= 0.005f;
            BComponent += 0.005f;

            Color =  new Color(RComponent, GComponent, BComponent, 1);
        }
        else if (Type == 4)
        {
            Color = new Color(random.nextFloat(),
                    random.nextFloat(), random.nextFloat(), 1);
        }
    }

    /// <summary>
    /// Установка цвета частички
    /// </summary>
    /// <param name="type"></param>
    public void SetType(int type)
    {
        Type = type;
        Color startColor = new Color(1f, 1f, 1f, 1);

        switch(type)
        {
            case 0: startColor = new Color(0f, 1f, 0f, 1); break; // Обычная
            case 1: startColor = new Color(1f, 0f, 0f, 1); break; // Красная
            case 2: startColor = new Color(1f, 0f, 1f, 1); break; // Пурпурная
            case 3: startColor = new Color(1f, 1f, 0f, 1); break; // Желтая
            case 4: random = new Random(); break; // Мигающая
        }

        RComponent = startColor.r;
        GComponent = startColor.g;
        BComponent = startColor.b;

        Color = new Color(RComponent, GComponent, BComponent, 1);

        if (type == -1)
        {
            Color = new Color(1f, 1f, 1f, 0.1f);
        }
    }
}
