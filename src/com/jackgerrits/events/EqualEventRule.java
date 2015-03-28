package com.jackgerrits.events;

import com.jackgerrits.Options;
import com.jackgerrits.Sensor;
import com.jackgerrits.SensorController;
import com.phidgets.PhidgetException;
import com.phidgets.event.InputChangeEvent;
import com.phidgets.event.SensorChangeEvent;

import java.util.NoSuchElementException;

/**
 * Created by Jack on 28/03/2015.
 */
public class EqualEventRule extends EventRule {

    private String name;
    private String description;
    private String sensorName;
    private int val;
    private SensorController sensorController;

    public EqualEventRule(String name, String description, String sensorName, int val, SensorController sensorController, Options ops){
        super(ops);
        this.name = name;
        this.description = description;
        this.sensorName = sensorName;
        this.val = val;
        this.sensorController = sensorController;
    }

    @Override
    public Event test(InputChangeEvent ie, boolean override) throws PhidgetException {
        Sensor eventSensor;
        try {
            eventSensor = sensorController.getSensor(ie.getIndex(), Sensor.sensorType.DIGITAL);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            System.out.println("Port: " + ie.getIndex() + ", DIGITAL");
            System.out.println("State: " + ie.getState());
            System.out.println("--------------");
            return null;
        }

        if(eventSensor.getName().equals(sensorName)){
            if(sensorController.getVal(eventSensor) == val){
                if(canFire() || override){
                    return new Event(name, description);
                }
            }
        }
        return null;
    }

    @Override
    public Event test(SensorChangeEvent se, boolean override) throws PhidgetException {
        Sensor eventSensor;

        try {
            eventSensor = sensorController.getSensor(se.getIndex(), Sensor.sensorType.ANALOG);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            System.out.println("Port: " + se.getIndex() + ", DIGITAL");
            System.out.println("Value: " + se.getValue());
            System.out.println("--------------");
            return null;
        }

        if(eventSensor.getName().equals(sensorName)){
            if(sensorController.getVal(eventSensor) == val){
                if(canFire() || override){
                    return new Event(name, description);
                }
            }
        }
        return null;
    }

    public Event test() throws PhidgetException {
        if(sensorController.getVal(sensorName) == val){
            return new Event(name, description);
        }
        return null;
    }

    @Override
    public type getType() {
        return type.EQUAL;
    }

}
