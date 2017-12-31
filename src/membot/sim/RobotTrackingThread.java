/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roman
 */
public class RobotTrackingThread implements Runnable {

    RobotTracker robotTracking;

    public RobotTrackingThread(RobotTracker a) {
        this.robotTracking = a;
    }

    @Override
    public void run() {
        while (true) {
            this.robotTracking.update(0.01);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(RobotTrackingThread.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }
}
