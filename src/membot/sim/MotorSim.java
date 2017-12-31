/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import membot.robot.IMotor;

/**
 *
 * @author roman
 */
public class MotorSim implements IMotor {

    private double v;

    @Override
    public void setSpeed(double v) {
        this.v = v;
    }

    @Override
    public double getSpeed() {
        return v;
    }
}
