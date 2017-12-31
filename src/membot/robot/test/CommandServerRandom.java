/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.robot.test;

import java.util.Random;
import membot.robot.ICommandServer;

/**
 *
 * @author rpa2306
 */
public class CommandServerRandom implements ICommandServer {

    @Override
    public String getCommand() {
        String vw = "w";
        Random r = new Random();
        //Generate random double beetwenn -100 and 100
        double d = r.nextDouble() * 200 - 100;
        int vOrW = r.nextInt(2);
        System.out.println("Bool: " + vOrW);
        if (vOrW != 0) {
            vw = "v";
        }
        System.out.println("VW: " + vw);
        return vw + Double.toString(d);
    }

    @Override
    public void setCommandString(String commandstring) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
