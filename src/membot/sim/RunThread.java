/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import java.util.logging.Level;
import java.util.logging.Logger;
import membot.robot.Robot;

/**
 *
 * @author roman
 */
public class RunThread implements Runnable {

    private final Robot robot;

    public RunThread(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void run() {
        while (true) {
            robot.run();
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {
                Logger.getLogger(RunThread.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }
}
