/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import membot.robot.ICommandServer;
import membot.robot.Robot;
import membot.robot.RobotGeometry;
import membot.sim.RobotTracker;

/**
 *
 * @author roman
 */
public class RobotGUI extends JFrame {

    private final RobotTracker robotTracker;
    private final JLabel labelX = new JLabel("");
    private final JLabel labelY = new JLabel("");
    private final JLabel labelPhi = new JLabel("");
    private final JLabel labelCommand = new JLabel("");
    private final JTextField command = new JTextField();
    private final JCheckBox roboterBordersActive = new JCheckBox(
            "<html><p>Roboter Pane</p></html>");
    private final JCheckBox randomDrivesActive = new JCheckBox(
            "<html><p>Zufallsfahrten</p></html>");
    private final JCheckBox tracksActive = new JCheckBox(
            "<html><p>Spuren</p></html>");
    private final ICommandServer cs;
    private final RobotViewPanel drawRobot;
    private final RobotViewPanel drawRobotAtPos;
    private final Robot robot;
    private final RobotGeometry robotGeometry;
    JTextArea display = new JTextArea();
    JPanel buttonPanel;

    public RobotGUI(RobotTracker robotSim, Robot robot, ICommandServer cs,
            RobotGeometry robotGeometry) {
        this.robotTracker = robotSim;
        this.cs = cs;
        this.robot = robot;
        this.robotGeometry = robotGeometry;
        this.drawRobot = new RobotViewPanel(robotTracker, this.robot,
                this.robotGeometry, this.roboterBordersActive, this.tracksActive,
                false);
        this.drawRobotAtPos = new RobotViewPanel(robotTracker, this.robot,
                this.robotGeometry, this.roboterBordersActive, this.tracksActive,
                true);
    }

    public ICommandServer getCommandServer() {
        return this.cs;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        labelX.setText(String.format("%.2f", robotTracker.getX()));
        labelY.setText(String.format("%.2f", robotTracker.getY()));
        labelPhi.setText(String.format("%.2f", robotTracker.getPhi()));
        labelCommand.setText(this.cs.getCommand());
        display.setText(this.cs.getCommand()
                + "\nw: " + String.format("%.2f", robot.getAngularVelocity())
                + "\nv: " + String.format("%.2f", robot.getLinearVelocity()));
        robot.SetRandomDrivesActive(randomDrivesActive.isSelected());
    }

    public Boolean getRoboterBordersActive() {
        return roboterBordersActive.isSelected();
    }

    public Boolean getRandomDrivesActive() {
        return randomDrivesActive.isSelected();
    }

    public Boolean getTracksActive() {
        return tracksActive.isSelected();
    }

    public void showFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel middlePanel = new JPanel(new BorderLayout());
        JPanel pane = new JPanel(new GridBagLayout());
        JLabel label;        
        JLabel labelB = new JLabel("v oder w:");

        bottomPanel.add(labelB, BorderLayout.WEST);
        bottomPanel.add(command, BorderLayout.CENTER);
        middlePanel.add(drawRobot, BorderLayout.CENTER);

        addItem(pane, roboterBordersActive, 0, 1, 2, 1, GridBagConstraints.EAST, 0, 0);
        addItem(pane, randomDrivesActive, 0, 2, 2, 1, GridBagConstraints.EAST, 0, 0);
        addItem(pane, tracksActive, 0, 3, 2, 1, GridBagConstraints.EAST, 0, 0);
        label = new JLabel("x:");
        label.setHorizontalAlignment(JLabel.LEFT);
        addItem(pane, label, 0, 4, 1, 1, GridBagConstraints.EAST, 0, 0);
        label = new JLabel("y:");
        addItem(pane, label, 0, 5, 1, 1, GridBagConstraints.EAST, 0, 0);
        labelX.setHorizontalAlignment(JLabel.RIGHT);
        addItem(pane, labelX, 1, 4, 1, 1, GridBagConstraints.EAST, 0, 0);
        labelY.setHorizontalAlignment(JLabel.RIGHT);
        addItem(pane, labelY, 1, 5, 1, 1, GridBagConstraints.EAST, 0, 0);
        label = new JLabel("\u03A6:");
        addItem(pane, label, 0, 6, 1, 1, GridBagConstraints.EAST, 0, 0);
        labelPhi.setHorizontalAlignment(JLabel.RIGHT);
        addItem(pane, labelPhi, 1, 6, 1, 1, GridBagConstraints.EAST, 0, 0);
        label = new JLabel("Command: ");
        addItem(pane, label, 0, 9, 2, 1, GridBagConstraints.EAST, 0, 0);

        //Zeilenumbruch wird eingeschaltet
        display.setLineWrap(true);
        display.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(display);
        addItem(pane, scrollPane, 0, 10, 2, 4, GridBagConstraints.EAST, 100, 0);
        drawRobotAtPos.setPreferredSize(new Dimension(150, 150));
        addItem(pane, drawRobotAtPos, 0, 14, 4, 200, GridBagConstraints.CENTER,
                0, 0);

        getContentPane().add(middlePanel, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.EAST);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        
        setSize(new Dimension(
                Math.abs(this.robotGeometry.getMaxX()) + Math.abs(
                this.robotGeometry.getMinX()),
                Math.abs(this.robotGeometry.getMaxY()) + Math.abs(
                this.robotGeometry.getMinY())));
        setVisible(true);
        
        command.requestFocus(); //curser auf Eingabe bewegen, da ich faul bin
        randomDrivesActive.setSelected(true); //Zufallsfahrten sofort setzen, da ich ebenfalls faul bin
        
        command.addActionListener((ActionEvent e) -> {
            if (e.getSource() == command) {
                cs.setCommandString(command.getText());
                command.setText("");
            }
        });
    }

    private void addItem(JPanel p, JComponent c, int x, int y, int width,
            int height, int align, double weightX, double weightY) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.weightx = weightX;
        gc.weighty = weightY;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = align;
        gc.fill = GridBagConstraints.BOTH;
        p.add(c, gc);
    }
}
