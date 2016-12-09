/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementine;

import java.io.IOException; 
import java.text.DecimalFormat; 
import com.pi4j.component.sensor.DistanceSensorChangeEvent; 
import com.pi4j.component.sensor.DistanceSensorListener; 
import com.pi4j.component.sensor.impl.DistanceSensorComponent; 
import com.pi4j.gpio.extension.ads.ADS1115GpioProvider; 
import com.pi4j.gpio.extension.ads.ADS1115Pin; 
import com.pi4j.gpio.extension.ads.ADS1x15GpioProvider.ProgrammableGainAmplifierValue; 
import com.pi4j.io.gpio.GpioController; 
import com.pi4j.io.gpio.GpioFactory; 
import com.pi4j.io.gpio.GpioPinAnalogInput; 
import com.pi4j.io.i2c.I2CBus; 
import com.pi4j.io.i2c.I2CFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Waleed
 */
public class SensorReader extends BotController{
    public DecimalFormat df;
    ADS1115GpioProvider gpioProvider;
    public DecimalFormat pdf;
    public GpioController gpio;
    public GpioPinAnalogInput distanceSensorPin;
    public DistanceSensorComponent distanceSensor;
    private int channel;
    

    
    public SensorReader(int num) throws I2CFactory.UnsupportedBusNumberException, IOException, InterruptedException {
        // keep program running for 1 minutes  
        channel = num;
        for (int count = 0; count < 600; count++) {
            reads();
            Thread.sleep(1000);
        } 

        // stop all GPIO activity/threads by shutting down the GPIO controller 
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks) 
        //gpio.shutdown(); 
        //System.out.print(""); 
    }
    
    
    public void reads() throws I2CFactory.UnsupportedBusNumberException, IOException{
    
        System.out.println("<--Pi4J--> ADS1115 Distance Sensor Example ... started."); 
 
        // number formatters 
        final DecimalFormat df = new DecimalFormat("#.##"); 
        final DecimalFormat pdf = new DecimalFormat("###.#"); 
         
        // create gpio controller 
        final GpioController gpio = GpioFactory.getInstance(); 
         
        // create custom ADS1115 GPIO provider 
        final ADS1115GpioProvider gpioProvider = new ADS1115GpioProvider(I2CBus.BUS_1, ADS1115GpioProvider.ADS1115_ADDRESS_0x48); 
         
        // provision gpio analog input pins from ADS1115 
        final GpioPinAnalogInput distanceSensorPin = gpio.provisionAnalogInputPin(gpioProvider, ADS1115Pin.INPUT_A0, "DistanceSensor-A0"); 
         
        // ATTENTION !!           
        // It is important to set the PGA (Programmable Gain Amplifier) for all analog input pins.  
        // (You can optionally set each input to a different value)     
        // You measured input voltage should never exceed this value! 
        // 
        // In my testing, I am using a Sharp IR Distance Sensor (GP2Y0A21YK0F) whose voltage never exceeds 3.3 VDC 
        // (http://www.adafruit.com/products/164) 
        // 
        // PGA value PGA_4_096V is a 1:1 scaled input,  
        // so the output values are in direct proportion to the detected voltage on the input pins 
        gpioProvider.setProgrammableGainAmplifier(ProgrammableGainAmplifierValue.PGA_4_096V, ADS1115Pin.ALL); 
                 
         
        // Define a threshold value for each pin for analog value change events to be raised. 
        // It is important to set this threshold high enough so that you don't overwhelm your program with change events for insignificant changes 
        gpioProvider.setEventThreshold(150, ADS1115Pin.ALL); 
 
         
        // Define the monitoring thread refresh interval (in milliseconds). 
        // This governs the rate at which the monitoring thread will read input values from the ADC chip 
        // (a value less than 50 ms is not permitted) 
        gpioProvider.setMonitorInterval(100); 
         
        // create a distance sensor based on an analog input pin 
        DistanceSensorComponent distanceSensor = new DistanceSensorComponent(distanceSensorPin); 
         
        /* build a distance coordinates mapping (estimated distance at raw values) 
        distanceSensor.addCalibrationCoordinate(21600, 13); 
        distanceSensor.addCalibrationCoordinate(21500, 14); 
        distanceSensor.addCalibrationCoordinate(21400, 15); 
        distanceSensor.addCalibrationCoordinate(21200, 16); 
        distanceSensor.addCalibrationCoordinate(21050, 17); 
        distanceSensor.addCalibrationCoordinate(20900, 18);  
        distanceSensor.addCalibrationCoordinate(20500, 19); 
        distanceSensor.addCalibrationCoordinate(20000, 20);  
        distanceSensor.addCalibrationCoordinate(15000, 30);   
        distanceSensor.addCalibrationCoordinate(12000, 40);  
        distanceSensor.addCalibrationCoordinate(9200,  50);  
        distanceSensor.addCalibrationCoordinate(8200,  60);  
        distanceSensor.addCalibrationCoordinate(6200,  70);  
        distanceSensor.addCalibrationCoordinate(4200,  80);  
*/
        distanceSensor.addCalibrationCoordinate(15150, 13); 
        distanceSensor.addCalibrationCoordinate(14020, 14); 
        distanceSensor.addCalibrationCoordinate(13291, 15); 
        distanceSensor.addCalibrationCoordinate(12569, 16); 
        distanceSensor.addCalibrationCoordinate(11788, 17); 
        distanceSensor.addCalibrationCoordinate(11369, 18);  
        distanceSensor.addCalibrationCoordinate(10999, 19); 
        distanceSensor.addCalibrationCoordinate(10586, 20);  
        distanceSensor.addCalibrationCoordinate(8190, 30);   
        distanceSensor.addCalibrationCoordinate(7628, 40);  
        distanceSensor.addCalibrationCoordinate(7500,  50);  
        distanceSensor.addCalibrationCoordinate(6500,  60);  
        distanceSensor.addCalibrationCoordinate(6200,  70);  
        distanceSensor.addCalibrationCoordinate(4200,  80); 
 
        distanceSensor.addListener(new DistanceSensorListener() 
        { 
            @Override 
            public void onDistanceChange(DistanceSensorChangeEvent event) 
            { 
                // RAW value 
                double value = event.getRawValue(); 
 
                // Estimated distance 
                double distance =  event.getDistance(); 
                 
                // percentage 
                double percent =  ((value * 100) / ADS1115GpioProvider.ADS1115_RANGE_MAX_VALUE); 
                 
                // approximate voltage ( *scaled based on PGA setting ) 
                double voltage = gpioProvider.getProgrammableGainAmplifier(distanceSensorPin).getVoltage() * (percent/100); 
                
                // display output 
                System.out.print("\r DISTANCE=" + df.format(distance)  + "cm : VOLTS=" + df.format(voltage) + "  | PERCENT=" + pdf.format(percent) + "% | RAW=" + value + "       "); 
                if(distance < 15){
                    moveReverse();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SensorReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    brakingStop();
                }
            } 
        }); 
         
        // keep program running for 1 minutes  
        for (int count = 0; count < 6000; count++) { 
            try { 
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(SensorReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        // stop all GPIO activity/threads by shutting down the GPIO controller 
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks) 
        gpio.shutdown(); 
        System.out.print(""); 
    } 
} 

