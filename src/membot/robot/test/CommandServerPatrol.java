/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.robot.test;

import membot.robot.ICommandServer;
import membot.robot.StarteTcpServer;

/**
 *
 * @author rpa2306
 */
public class CommandServerPatrol implements ICommandServer {

    protected long millis;
    protected int v;
    protected int w;
    protected String vString;

    public CommandServerPatrol() {
        millis = System.currentTimeMillis();
        v = 100;
        w = 4;
        vString = "w0v1";
    }

    @Override
    public String getCommand() {
        if (StarteTcpServer.newValueArrieved) {
            System.out.println("New Value arrived: "
                    + StarteTcpServer.newValueArrieved);
            vString = StarteTcpServer.inputLine;
            StarteTcpServer.newValueArrieved = false;
        }
        return vString;
    }

    @Override
    public void setCommandString(String commandstring) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
