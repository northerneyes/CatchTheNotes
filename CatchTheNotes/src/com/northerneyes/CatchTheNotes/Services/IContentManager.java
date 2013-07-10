package com.northerneyes.CatchTheNotes.Services;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.07.13
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public interface IContentManager {

    public String getString(String key);

    public String[] getStringArray(String key);

    public float getDimension(String key);

    public void setDimensionCoeff(float coeff);
}
