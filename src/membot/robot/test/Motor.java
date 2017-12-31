/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.robot.test;

import membot.robot.IMotor;

/**
 *
 * @author rpa2306
 */
public class Motor implements IMotor {
    private double v;
    private final String name;
    public Motor(String name) {
        this.name = name;
    }
    @Override
    public void setSpeed(double v) {
        this.v = v;
    }
    @Override
    public double getSpeed() {
        return v;
    }
}
