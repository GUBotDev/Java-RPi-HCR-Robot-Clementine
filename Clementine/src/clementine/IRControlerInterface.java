/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementine;

/**
 *
 * @author JamesFox
 */
public interface IRControlerInterface {
    
    /* An array that holds the values of distance that each sensor is from an
    object*/
    public double[] readSensorArray();
    
    /* Calls a sensor and shows what the distance is from an object*/
    public int readDistance(int sensorID);
    
    /* Returns the shortest distance in cm */
    public double shortestDistance();
    /* Returns the int of the sensor with the shortest distance from an object
    */
    public int shortestSensnor();
    /* Returns the longest distance in cm */
    public double longestDistance();
    /* Returns the int of the sensor with the farthest distance from an object
    */
    public int longestSensor();
}
