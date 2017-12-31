/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.sim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import membot.robot.Robot;
import javax.swing.JCheckBox;
import membot.robot.RobotGeometry;

/**
 *
 * @author roman
 */
public class RobotViewPanel extends JPanel {

    int barWidth;      
    int barHeight;     
    int wheelWidth = 100;    
    int wheelHeight = 30;    
    private int MAX_X;         
    private double umrandungX;
    private double umrandungY;
    RobotTracker robotView;
    private final Robot robot;
    private final RobotGeometry robotGeometry;
    int bufferXY[][];
    private JCheckBox labelC;
    private JCheckBox tracks;
    private Boolean robotStay;
    private WallSim wallSim;

    public RobotViewPanel(RobotTracker robotView, Robot robot,
            RobotGeometry robotGeometry, JCheckBox labelC, JCheckBox tracks,
            Boolean a) {
        this.robotView = robotView;
        this.robot = robot;
        this.robotGeometry = robotGeometry;
        MAX_X = (Math.abs(this.robotGeometry.getMaxX()) + Math.abs(
                this.robotGeometry.getMinX())) * 2;
        umrandungX = (Math.abs(this.robotGeometry.getMaxX()) + Math.abs(
                this.robotGeometry.getMinX())) * 2;
        umrandungY = (Math.abs(this.robotGeometry.getMaxY()) + Math.abs(
                this.robotGeometry.getMinY())) * 2;
        this.labelC = labelC;
        this.tracks = tracks;
        this.barWidth = this.robotGeometry.getBarWith();
        this.barHeight = this.robotGeometry.getBarHeight();
        this.wheelWidth = this.robotGeometry.getWheelWidth();
        this.wheelHeight = this.robotGeometry.getWheelHeight();
        this.robotStay = a;
        wallSim = robot.getWallSim();
    }

    public void drawRobot(Graphics2D g2, double x, double y,
            Boolean robotNoMoving, double scale, double umrX, double umrY) {
        
        int r = 50;
        int lineThickness = 15;
        int breite = barWidth;
        int hoehe = barHeight + 4 * wheelHeight;
        double translationInX = 0;
        double translationInY = 0;
        
        
        Ellipse2D.Double bar = new Ellipse2D.Double(x - breite / 2, y - hoehe
                / 2, breite, hoehe);
        RoundRectangle2D.Double barB = new RoundRectangle2D.Double(bar.
                getCenterX(), bar.getCenterY(), barWidth, barHeight, 100, 600);
        RoundRectangle2D.Double wheel = new RoundRectangle2D.Double(bar.
                getCenterX(), bar.getCenterY(), wheelWidth, wheelHeight, 10f,
                10f);
        Ellipse2D.Double point = new Ellipse2D.Double(bar.getCenterX(), bar.
                getCenterY(), r, r);

        AffineTransform at = new AffineTransform();
        AffineTransform at2 = new AffineTransform();
        at.setToTranslation(bar.getCenterX() + translationInX, bar.getCenterY()
                + translationInY);
        at.rotate(robotView.getPhi(), x - translationInX, y - translationInY);

        g2.setPaint(Color.gray);
        g2.setStroke(new BasicStroke(lineThickness));
        translationInX = -barWidth / 2;
        translationInY = -barHeight / 2;
        at.setToTranslation(bar.getCenterX() + translationInX, bar.getCenterY()
                + translationInY);
        at.rotate(robotView.getPhi(), x - translationInX, y - translationInY);
        g2.setPaint(Color.gray);
        g2.fill(at.createTransformedShape(barB));
        g2.setPaint(Color.black);
        g2.draw(at.createTransformedShape(barB));

        //draw translated and rotated robot wheels
        g2.setPaint(Color.gray);

        translationInX = -barWidth / 2 + wheelWidth;
        translationInY = +barHeight / 2 + lineThickness / 2;
        at.setToTranslation(bar.getCenterX() + translationInX, bar.getCenterY()
                + translationInY);
        at.rotate(robotView.getPhi(), x - translationInX, y - translationInY);
        g2.fill(at.createTransformedShape(wheel));

        translationInX = -barWidth / 2 + wheelWidth;
        translationInY = -barHeight / 2 - wheelHeight - lineThickness / 2;
        at.setToTranslation(bar.getCenterX() + translationInX, bar.getCenterY()
                + translationInY);
        at.rotate(robotView.getPhi(), x - translationInX, y - translationInY);
        g2.fill(at.createTransformedShape(wheel));

        translationInX = barWidth / 2 - r;
        translationInY = -r / 2;
        at.setToTranslation(bar.getCenterX() + translationInX, bar.getCenterY()
                + translationInY);
        at.rotate(robotView.getPhi(), x - translationInX, y - translationInY);
        g2.setPaint(Color.black);
        g2.fill(at.createTransformedShape(point));

        AffineTransform orig = g2.getTransform();
        Font f = new Font("Dialog", Font.PLAIN, 24);
        g2.setFont(f);

        translationInX = 2 * bar.getCenterX() + umrX / 2;
        translationInY = -2 * bar.getCenterY() + umrY / 2;
        at2.scale(scale, scale);
        at2.rotate(-robotView.getPhi(), translationInX, translationInY);
        g2.setColor(Color.BLACK);
        g2.setTransform(at2);

        translationInX = bar.getCenterX() + umrX / 2 - barWidth / 4 + wheelWidth
                / 2;
        translationInY = -bar.getCenterY() + umrY / 2 - barHeight / 2
                - wheelHeight / 2;
        g2.drawString(String.format("%.2f", robot.drive.right.getSpeed()),
                (float) (bar.getCenterX() + translationInX), (float) (-bar.
                getCenterY() + translationInY));
        translationInY = -bar.getCenterY() + umrY / 2 + barHeight / 2
                + wheelHeight / 2 + lineThickness;
        g2.drawString(String.format("%.2f", robot.drive.left.getSpeed()),
                (float) (bar.getCenterX() + translationInX), (float) (-bar.
                getCenterY() + translationInY));
        g2.setTransform(orig);

        if (labelC.isSelected() && !robotNoMoving) {
            double ergX = 4 * wallSim.getExtremeValueX();
            double ergY = 4 * wallSim.getExtremeValueY();
            Ellipse2D.Double kreisB = new Ellipse2D.Double(bar.getCenterX(),
                    bar.getCenterY(), 5, 5);

            g2.setPaint(Color.red);
            g2.setStroke(new BasicStroke(5));
            
            at.setToTranslation(bar.getCenterX() + ergX / 2 - 2.5, bar.
                    getCenterY() + ergY / 2 - 2.5);
            g2.draw(at.createTransformedShape(kreisB));

            at.setToTranslation(bar.getCenterX() - ergX / 2 - 2.5, bar.
                    getCenterY() - ergY / 2 - 2.5);
            g2.draw(at.createTransformedShape(kreisB));

            at.setToTranslation(bar.getCenterX() - ergX / 2, bar.getCenterY()
                    - ergY / 2);
            Rectangle2D.Double frame = new Rectangle2D.Double(x, y, ergX, ergY);
            g2.draw(at.createTransformedShape(frame));
        }
    }

    private void drawCompass(Graphics2D g2, double scale) {
        Font f = new Font("Dialog", Font.PLAIN, 100);
        AffineTransform orig = g2.getTransform();
        g2.setFont(f);
        g2.scale(scale, -scale);
        Color farbeAn = Color.green;
        Color farbeAus = Color.LIGHT_GRAY;

        g2.setPaint(farbeAus);
        if (wallSim.getDirectionSin() == WallSim.DirectionView.UP) {
            g2.setPaint(farbeAn);
        }
        g2.drawString("E", 0, -240);

        g2.setPaint(farbeAus);
        if (wallSim.getDirectionSin() == WallSim.DirectionView.DOWN) {
            g2.setPaint(farbeAn);
        }
        g2.drawString("W", 0, 240);

        g2.setPaint(farbeAus);
        if (wallSim.getDirectionCos() == WallSim.DirectionView.RIGHT) {
            g2.setPaint(farbeAn);
        }
        g2.drawString("S", 240, 0);

        g2.setPaint(farbeAus);
        if (wallSim.getDirectionCos() == WallSim.DirectionView.LEFT) {
            g2.setPaint(farbeAn);
        }
        g2.drawString("N", -240, 0);
        g2.setTransform(orig);
    }

    @Override
    public void paintComponent(Graphics g) {
        // call paintComponent of superclass JPanel
        super.paintComponent(g); //Konstruktor
        setBackground(Color.white);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int panel_width = getWidth();
        double scale = 1 * (double) panel_width / (double) MAX_X;
        g2.setPaint(Color.DARK_GRAY);
        double x = 0;
        double y = 0;
        int lineThickness = 15;
        g2.setStroke(new BasicStroke(lineThickness));

        Rectangle2D.Double Umrandung = new Rectangle2D.Double(
                this.robotGeometry.getMinX() * 2, this.robotGeometry.getMinY()
                * 2, umrandungX, umrandungY);

        if (!robotStay) {
            g2.scale(scale, -scale);
            g2.translate(umrandungX / 2, -umrandungY / 2);
            g2.draw(Umrandung);
            x = robotView.getX();
            y = robotView.getY();

            bufferXY = robotView.getPointSaverBuffer();
            
            if (tracks.isSelected()) {
                for (int[] bufferXY1 : bufferXY) {
                    g2.fillOval((int) bufferXY1[0] * 2, (int) bufferXY1[1] * 2,
                            10, 10);
                }
            }
            drawRobot(g2, x, y, robotStay, scale, umrandungX, umrandungY);
            drawCompass(g2, scale);
        } else {
            x = 0;
            y = 0;
            g2.scale(scale * 7, -scale * 7);
            int MAX = 140;
            g2.translate(MAX, -MAX);
            drawRobot(g2, x, y, robotStay, scale * 7, MAX*2, MAX*2);
            drawCompass(g2, scale * 3.5);
        }
    }
}
