package com.northerneyes.model;

import android.util.Log;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
public class NotesHolder implements IEntity {

    public static  int  NOTE_TYPE_COUNT = 6;

    private Random random; // Генератор случайных чисел

    public List<Note> particles; // Массив частичек (Catcher)

    /// <summary>
    /// Массив float-значений
    /// </summary>
    public ArrayList<Float> Accumulator;


    public NotesHolder() {
        this.particles = new ArrayList<Note>();

        random = new Random();

        Accumulator = new ArrayList<Float>();

        for (int i = 0; i < 128; i++)
        {
            Accumulator.add(1.0f);
        }
    }

    private Note GenerateNewParticle(float wave, float posY,  float amp)
    {
       // amp = 1f;
        if(amp > 1)
            Log.v("Note", "MAx");
        int ttl = 240; // Время жизни в 400 (400 актов рисования живет частица, т.е. 400 / 60 — 6 с лишним секунд.
        int type = 0; //— изначальный тип 0
        int viewType = random.nextInt(NOTE_TYPE_COUNT);

        Vector2 position = new Vector2(wave, posY); // Задаем позицию

        float size = 1+ (amp - 0.3f)*(Note.MAX_SIZE-1)/0.7f; // (0.5f + random.nextFloat()*2f); // размер

        ttl = findRealTTL(size, ttl);

        //change on type game
        Vector2 velocity = new Vector2((random.nextFloat() - 0.5f)*0.001f, 3.4f*amp*(15/(float)ttl)); // Случайное ускорение, 0.5f для X и 1f для Y  (amp * 10f)*0.04f

        float angle = 0; // Угол поворота = 0

        float angularVelocity = (float) (0.05f * (random.nextFloat() * 2 - 1)*180/Math.PI); // Случайная скорость вращения

        // Вероятность появления
        if (random.nextInt(10000) > 9900) // враг
            type = 1;
        else if (random.nextInt(10000) > 9900) // желтый
            type = 3;
        else if (random.nextInt(10000) > 9990) // пурпурный
            type = 2;
        else if (random.nextInt(10000) > 9995) // мигающий
            type = 4;

        return new Note(position, velocity, angle, angularVelocity, type, size, ttl, viewType, amp); // Создаем частичку и возвращаем её
    }

    private int findRealTTL(float InitialSize, int initialTTL)
    {
        for(int TTL = 0; TTL < initialTTL; TTL++)
        {
            float size = InitialSize - (Note.MAX_SIZE/(float)initialTTL)*TTL;
            if(size < 0)
                return TTL;
        }
        return initialTTL;
    }

    public void GenerateYellowExplossion(int x, int y, int radius)
    {
        int viewType = random.nextInt(NOTE_TYPE_COUNT);

        Vector2 direction = Vector2.Zero;
        float angle = (float)Math.PI * 2.0f * random.nextFloat(); //rad
      //  float length = radius;

        direction.x = MathUtils.cos(angle);
        direction.y = -MathUtils.sin(angle);

        Vector2 position = (new Vector2(x, y)).add(direction.mul(radius));

        Vector2 velocity = direction.mul(0.1f);

        float angularVelocity =  (float) (0.05f * (random.nextFloat() * 2 - 1)*180/Math.PI);

        float size = random.nextFloat() * .8f + .2f;
        int ttl = 400;

        int type = 3;
        particles.add(new Note(position, velocity, 0, angularVelocity, type, size, ttl, viewType, 0.5f));
    }


    public void beat(float wave, float posY, float amp)
    {
        particles.add(GenerateNewParticle(wave, posY, amp));
    }

    @Override
    public void update(float delta) {

        for (int particleIndex = 0; particleIndex < particles.size(); particleIndex++)
        {
            particles.get(particleIndex).update(delta);
            if (particles.get(particleIndex).Size <= 0 || particles.get(particleIndex).TTL <= 0)
            {
                // Если частичка дохлая или размер нуль или меньше, удаляем её
                particles.remove(particleIndex);
                particleIndex--;
            }
        }

        // Обновляем аккумулятор, если значения ячейки меньше 1f, то
        //добавляем значение, указанное в статическом классе
        //Constants — ACCUMULATE_SPEED
        for (int a = 0; a < 128; a++)
            if (Accumulator.get(a) < 1.0f)
                Accumulator.set(a, Accumulator.get(a) + Constants.ACCUMULATE_SPEED );
    }
}
