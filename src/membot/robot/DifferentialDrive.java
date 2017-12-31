/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.robot;

//import static mebot.robot.test.RobotTest.debuggen;
/**
 *
 * @author rpa2306
 */
public class DifferentialDrive {

    private static double width;
    public final IMotor left;
    public final IMotor right;

    public DifferentialDrive(IMotor left, IMotor right, double axisWidth) {
        this.left = left;
        this.right = right;
        this.width = axisWidth;
    }

    public void move(double v, double w) {
        double vLeft = v + w * width / 2;
        double vRight = v - w * width / 2;
        left.setSpeed(vLeft);
        right.setSpeed(vRight);
    }

    public double getAxisWidth() {
        return width;
    }

}
