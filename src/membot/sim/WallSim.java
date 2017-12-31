/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import membot.robot.RobotGeometry;

/**
 *
 * @author roman
 */
public class WallSim {

    private final RobotTracker robotTracking;
    private final double a, b;

    public enum HeavenlyDirections {
        SOUTH, NORTH, WEST, EAST, OK
    };

    public enum DirectionView {
        UP, DOWN, LEFT, RIGHT
    };

    private final RobotGeometry robotGeometry;
    private HeavenlyDirections heavenlyDirections;
    private DirectionView directionViewCos;
    private DirectionView directionViewSin;

    public WallSim(RobotTracker robotTracking, RobotGeometry robotGeometry) {
        this.robotTracking = robotTracking;
        this.robotGeometry = robotGeometry;
        a = this.robotGeometry.getBarHeight() + 4 * this.robotGeometry.
                getWheelHeight();
        b = this.robotGeometry.getBarWith();
    }

    public void Direction() {
        if (cos(robotTracking.getPhi()) > 0) {
            directionViewCos = DirectionView.RIGHT;
        } else {
            directionViewCos = DirectionView.LEFT;
        }
        if (sin(robotTracking.getPhi()) > 0) {
            directionViewSin = DirectionView.UP;
        } else {
            directionViewSin = DirectionView.DOWN;
        }
    }

    // Code for the ExtremeValue
    // http://webcache.googleusercontent.com/search?q=cache:Svt7LOcNL8oJ:math.stackexchange.com/questions/91132/how-to-get-the-limits-of-rotated-ellipse+&cd=1&hl=de&ct=clnk&gl=at&client=safari
    public double getExtremeValueX() {
        return 0.25 * Math.sqrt(Math.pow(a, 2) * Math.pow(Math.sin(robotTracking.getPhi()), 2) + Math.pow(b, 2) * Math.pow(Math.
                cos(robotTracking.getPhi()), 2));
    }

    public double getExtremeValueY() {
        return 0.25 * Math.sqrt(Math.pow(a, 2) * Math.pow(Math.cos(robotTracking.getPhi()), 2) + Math.pow(b, 2) * Math.pow(Math.
                sin(robotTracking.getPhi()), 2));
    }

    public void WallCheck() {
        heavenlyDirections = HeavenlyDirections.OK;
        if (robotTracking.getX() + getExtremeValueX() >= robotGeometry.getMaxX()) {
            heavenlyDirections = HeavenlyDirections.NORTH;
        }
        if (robotTracking.getX() - getExtremeValueX() <= robotGeometry.getMinX()) {
            heavenlyDirections = HeavenlyDirections.SOUTH;
        }
        if (robotTracking.getY() + getExtremeValueY() >= robotGeometry.getMaxY()) {
            heavenlyDirections = HeavenlyDirections.EAST;
        }
        if (robotTracking.getY() - getExtremeValueY() <= robotGeometry.getMinY()) {
            heavenlyDirections = HeavenlyDirections.WEST;
        }
    }

    public HeavenlyDirections getWallCollidated() {
        return heavenlyDirections;
    }

    public DirectionView getDirectionCos() {
        return directionViewCos;
    }

    public DirectionView getDirectionSin() {
        return directionViewSin;
    }
}
