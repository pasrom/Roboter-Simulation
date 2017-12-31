/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.robot;

/**
 *
 * @author roman
 */
public class RobotGeometry {

    private final int width;
    private final int barWith;
    private final int barHeight;
    private final int maxX;
    private final int minX;
    private final int maxY;
    private final int minY;
    private final int wheelWidth;
    private final int wheelHeigth;

    public RobotGeometry(int width, int barWith, int barHight) {
        this.width = width;
        this.barWith = barWith;
        this.barHeight = barHight;
        //to to: move this to constructor parameters
        maxX = 500;
        minX = -maxX;
        maxY = 400;
        minY = -maxY;
        wheelWidth = 100;
        wheelHeigth = 30;
        System.out.println("maxX: " + maxX + " minX: " + minX + " maxY: " + maxY
                + " minY: " + minY);
        System.out.println("Geometry erstellt");
    }

    public int getWheelWidth() {
        return this.wheelWidth;
    }

    public int getWheelHeight() {
        return this.wheelHeigth;
    }

    public int getWidth() {
        return this.width;
    }

    public int getBarWith() {
        return this.barWith;
    }

    public int getBarHeight() {
        return this.barHeight;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMinX() {
        return this.minX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public int getMinY() {
        return this.minY;
    }
}
