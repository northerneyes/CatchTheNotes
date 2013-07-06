package com.northerneyes.CatchTheNotes.model;

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
    private ArrayList<Note> recycledParticles = new ArrayList<Note>();


    public NotesHolder() {
        this.particles = new ArrayList<Note>();

        random = new Random();

        Accumulator = new ArrayList<Float>();

        for (int i = 0; i < 128; i++)
        {
            Accumulator.add(1.0f);
        }
    }

    private Note GenerateNewParticle(float wave, float posY, float amp, Note.NoteType type)
    {
        int ttl = 240; // Время жизни в 400 (400 актов рисования живет частица, т.е. 400 / 60 — 6 с лишним секунд.

        int viewType = random.nextInt(NOTE_TYPE_COUNT);

        Vector2 position = new Vector2(wave, posY); // Задаем позицию

        float size = 1+ (Math.abs(amp) - 0.3f)*(Note.MAX_SIZE-1)/0.7f; // (0.5f + random.nextFloat()*2f); // размер

        ttl = findRealTTL(size, ttl);

        //change on type game
        Vector2 velocity = new Vector2((random.nextFloat() - 0.5f)*0.001f, 3.4f*amp*(15/(float)ttl)); // Случайное ускорение, 0.5f для X и 1f для Y  (amp * 10f)*0.04f

        float angle = 0; // Угол поворота = 0

        float angularVelocity = (float) (0.05f * (random.nextFloat() * 2 - 1)*180/Math.PI); // Случайная скорость вращения

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



    public void beat(float wave, float posY, float amp)
    {
        Note.NoteType type = Note.NoteType.NORMAL; //— изначальный тип 0
        // Вероятность появления
        int selector = (int) (Math.random() * 1200);
        if (selector < 20) // враг
            type = Note.NoteType.POWER_DOWN;
        else if (selector < 40) // желтый
            type = Note.NoteType.POWER_UP;
        else if (selector < 42) // пурпурный
            type = Note.NoteType.SUCTION;
        else if (selector == 42) // мигающий
            type = Note.NoteType.YELLOW_MADDNESS;
        particles.add(GenerateNewParticle(wave, posY, amp, type));
    }

    public void beat(float wave, float posY, float amp,  Note.NoteType noteType)
    {
        particles.add(GenerateNewParticle(wave, posY, amp, noteType));
    }

    @Override
    public void update(float delta) {
        if(recycledParticles.size() > 0)
        {
            particles.addAll(recycledParticles);
            recycledParticles.clear();
        }

        for (int particleIndex = 0; particleIndex < particles.size(); particleIndex++)
        {
            Note note =  particles.get(particleIndex);
            note.update(delta);
            if (note.Size <= 0 || note.TTL <= 0 || note.Visibility <= 0)
            {
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

    public void removeNote(Note note) {
        particles.remove(note);
    }

    public void addRecycledParticle(Note note) {
        recycledParticles.add(note);
    }
}
