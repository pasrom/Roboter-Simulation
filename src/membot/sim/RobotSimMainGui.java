/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import javax.swing.SwingUtilities;
import membot.robot.DifferentialDrive;
import membot.robot.ICommandServer;
import membot.robot.IMotor;
import membot.robot.Robot;
import membot.robot.RobotGeometry;
import membot.robot.StarteTcpServer;
import membot.robot.test.CommandServerString;
import membot.robot.test.Motor;

/**
 *
 * @author rpa2306
 */
public class RobotSimMainGui {

    private final IMotor left = new Motor("LEFT ");
    private final IMotor right = new Motor("RIGHT ");
    private final RobotGeometry robotGeometry = new RobotGeometry(10, 250, 170);
    private final DifferentialDrive drive = new DifferentialDrive(left, right,
            robotGeometry.getWidth());
    private final ICommandServer cs = new CommandServerString();
    private final RobotTracker RobotTracking = new RobotTracker(left, right,
            robotGeometry);
    private final Robot robot = new Robot(cs, drive, RobotTracking,
            robotGeometry);
    private final RobotGUI myRobotGUI = new RobotGUI(RobotTracking, robot, cs,
            robotGeometry);
    private final GUIStart start = new GUIStart(myRobotGUI);

    public void startApp() {
        Thread t1 = new Thread(new StarteTcpServer(cs));
        t1.start();
        Thread t2 = new Thread(new RunThread(robot));
        t2.start();
        Thread t3 = new Thread(new RobotTrackingThread(RobotTracking));
        t3.start();
        start.startSimulation();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RobotSimMainGui RobotSimTest = new RobotSimMainGui();
                RobotSimTest.startApp();
            }
        });
    }
}
