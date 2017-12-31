/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import static java.lang.Math.*;
import java.util.Random;
import membot.robot.IMotor;
import membot.robot.RobotGeometry;
//import mebot.robot.DifferentialDrive;

/**
 *
 * @author roman
 */
public class RobotTracker {

    private double x = 0;
    private double y = 0;
    private double phi;
    private double v1, v2, b;
    private double oldX, oldY, oldPhi;
    private final IMotor left;
    private final IMotor right;
    private int deltaX, deltaXB = 0;
    private int deltaY, deltaYB, deltaPhiB = 0;
    private final int bufferXY[][] = new int[500][2];
    private int i = 0;
    private final int threshold = 10;
    private int thresholdB = 100;
    private Boolean randomDrive = false;

    public RobotTracker(IMotor left, IMotor right, RobotGeometry a) {
        this.left = left;
        this.right = right;
        v1 = 0;
        v2 = 0;
        b = a.getWidth();
        oldX = 0;
        oldY = 0;
        oldPhi = 0;
        phi = 0;
    }

    public void update(double dT) {

        v1 = left.getSpeed();
        v2 = right.getSpeed();
        double deltaPhi = (v1 - v2) / b;
        double v = (v1 + v2) / 2;
        x = oldX + v * dT * cos((oldPhi + 1 / 2 * deltaPhi * dT));
        y = oldY + v * dT * sin((oldPhi + 1 / 2 * deltaPhi * dT));
        phi = (oldPhi - deltaPhi * dT);
        oldX = x;
        oldY = y;
        oldPhi = phi;
        pointSaver();
        randomDrive();
    }

    private void pointSaver() {
        if ((int) x > deltaX + threshold || (int) x < deltaX - threshold
                || (int) y > deltaY + threshold || (int) y < deltaY - threshold) {
            if (i >= bufferXY.length) {
                i = 0;
            }
            bufferXY[i][0] = (int) x;
            bufferXY[i][1] = (int) y;
            deltaX = (int) x;
            deltaY = (int) y;
            i = 1 + i;
        }
    }

    private void randomDrive() {
        if ((int) x > deltaXB + thresholdB || (int) x < deltaXB - thresholdB
                || (int) y > deltaYB + thresholdB || (int) y < deltaYB
                - thresholdB
                || (int) phi > deltaPhiB + 2 || (int) phi < deltaPhiB - 2) {
            randomDrive = !randomDrive;
            deltaXB = (int) x;
            deltaYB = (int) y;
            deltaPhiB = (int) phi;
            thresholdB = getRandomNumberInRange(50, 100);
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getPhi() {
        return phi;
    }

    public int[][] getPointSaverBuffer() {
        return bufferXY;
    }

    public Boolean getRandomDrive() {
        return randomDrive;
    }

    public void setRandomDrive() {
        deltaXB = 0;
        deltaYB = 0;
        deltaPhiB = 0;
    }
}
