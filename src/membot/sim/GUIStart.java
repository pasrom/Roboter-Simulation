/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import membot.robot.Robot;
import membot.sim.RobotTracker;

/**
 *
 * @author roman
 */
public class GUIStart implements ActionListener {

    private Timer visuTimer;
    //private Timer robotTimer;
    private final RobotGUI myRobotGUI;

    public GUIStart(RobotGUI myRobotGUI) {
        this.myRobotGUI = myRobotGUI;
    }

    public void startSimulation() {
        myRobotGUI.showFrame();
        visuTimer = new Timer(20, this);
        visuTimer.start();      
        //robotTimer = new Timer(15, this);
        //robotTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == visuTimer) {
            myRobotGUI.repaint();
        }
    }
}
