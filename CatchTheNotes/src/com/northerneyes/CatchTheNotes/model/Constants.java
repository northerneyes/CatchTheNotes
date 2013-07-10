package com.northerneyes.CatchTheNotes.model;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static final boolean DEBUG = true;
    public static final boolean DEBUG_END_MENU = true;

    public static final float BEAT_COST = .4f; // "Стоимость" частички, отнимается у аккумулятора при генерации новой частички
    public static final float ACCUMULATE_SPEED = .01f; // Скорость аккумуляции
    public static final float BEAT_REACTION = .4f; // Значение реакции на "бит" в музыке
    public static final float ACCOMULATOR_REACTION = .5f; // Разрешает создавать новую частичку только тогда, когда значения больше реакции аккумулятора

}
