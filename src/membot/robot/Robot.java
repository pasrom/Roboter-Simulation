/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.robot;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import membot.sim.RobotTracker;
import membot.sim.WallSim;

/**
 *
 * @author rpa2306
 */
public class Robot {

    protected double v, oldV;
    protected double w, oldW;
    private double oldX, oldY, targetPos;
    public DifferentialDrive drive;
    protected ICommandServer cs;
    private final WallSim wallSim;
    private final RobotTracker RobotTracking;
    private final RobotGeometry robotGeometry;
    private int freiFahren = 0;
    private final String regexV = "(v-?\\d+\\.?\\d*)";
    private final String regexW = "(w-?\\d+\\.?\\d*)";
    private Boolean randomDrivesActive;
    private WallSim.HeavenlyDirections oldWallCollidated;
    private Boolean test = false;

    public Robot(ICommandServer cs, DifferentialDrive drive,
            RobotTracker RobotTracking, RobotGeometry robotGe) {
        this.drive = drive;
        this.cs = cs;
        this.RobotTracking = RobotTracking;
        this.robotGeometry = robotGe;
        this.wallSim = new WallSim(this.RobotTracking, robotGeometry);
        this.oldWallCollidated = WallSim.HeavenlyDirections.OK;
        this.oldV = 0;
        this.oldW = 0;
        randomDrivesActive = false;
    }

    public void run() {
        wallSim.WallCheck();
        wallSim.Direction();
        setVandW(wallSim.getWallCollidated());
        drive.move(v, w);
    }

    public void SetRandomDrivesActive(boolean randomDrivesActive) {
        this.randomDrivesActive = randomDrivesActive;
    }

    private double calculeteDifferenceToTarget(double old, double actual) {
        int a = this.robotGeometry.getBarHeight() + 4 * this.robotGeometry.
                getWheelHeight();
        int b = this.robotGeometry.getBarWith();
        targetPos = Math.abs(old) - Math.abs(a-b)/2;
        return Math.abs(actual) - targetPos;
    }

    private void setVandW(WallSim.HeavenlyDirections WallCollidated) {
        boolean go;
        go = !(WallCollidated == oldWallCollidated) && freiFahren != 0
                && !(WallCollidated == WallSim.HeavenlyDirections.OK);
        if ((!(WallCollidated == WallSim.HeavenlyDirections.OK) && freiFahren
                == 0) || go) {
            oldV = v;
            oldW = w;
            w = 0;
            oldX = RobotTracking.getX();
            oldY = RobotTracking.getY();
            oldWallCollidated = WallCollidated;
            freiFahren = 1;
            v = -v;
            cs.setCommandString("");
        }
        
        if (!(oldWallCollidated == WallSim.HeavenlyDirections.OK) && freiFahren
                != 0) {
            Random r = new Random();    //Objekt Zufallsgenerator erstellen
            if (((oldWallCollidated == WallSim.HeavenlyDirections.SOUTH)
                    || (oldWallCollidated == WallSim.HeavenlyDirections.NORTH))
                    && freiFahren == 1) {
                if (calculeteDifferenceToTarget(oldX, RobotTracking.getX()) <= 0) {
                    freiFahren = 2;
                }
            }
            if (((oldWallCollidated == WallSim.HeavenlyDirections.EAST)
                    || (oldWallCollidated == WallSim.HeavenlyDirections.WEST))
                    && freiFahren == 1) {
                if (calculeteDifferenceToTarget(oldY, RobotTracking.getY()) <= 0) {
                    freiFahren = 2;
                }
            }
            if (freiFahren == 2) {
                if (randomDrivesActive) {
                     v = -oldV;
                     w = r.nextDouble() * 4 - 2.0;
                     w = 10;
                } else {
                    v = 0;
                    w = 0;
                }
                oldWallCollidated = WallCollidated;
                WallCollidated = oldWallCollidated;
                RobotTracking.setRandomDrive();
                freiFahren = 0;
            }
        }

        if (freiFahren == 0 && WallCollidated == WallSim.HeavenlyDirections.OK) {
            if (randomDrivesActive) {
                parseCommand(cs.getCommand());
                if (RobotTracking.getRandomDrive() && !test) {
                    Random r = new Random();
                    w = r.nextDouble() * 6 - 3.0;
                    test = true;
                }
                if (!RobotTracking.getRandomDrive() && test) {
                    w = 0;
                    test = false;
                }

            } else {
                parseCommand(cs.getCommand());
            }
        }
    }

    public double zufallsZahl(double min, double max) {
        Random random = new Random();
        return random.nextDouble() * (max - min + 1) + min;
    }

    private String getString(String command, String pattern) {
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(command);
        if (matcher.find()) {
            return matcher.group().substring(1);
        } else {
            return "error";
        }
    }

    public void parseCommand(String command) {
        if (!"error".equals(getString(command, regexV))) {
            this.v = Double.parseDouble(getString(command, regexV));
        }
        if (!"error".equals(getString(command, regexW))) {
            this.w = Double.parseDouble(getString(command, regexW));
        }
    }

    public double getLinearVelocity() {
        return v;
    }

    public double getAngularVelocity() {
        return w;
    }
    
    public WallSim getWallSim() {
        return wallSim;
    }
}
