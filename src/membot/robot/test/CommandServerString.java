/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.robot.test;

import membot.robot.ICommandServer;

/**
 *
 * @author roman
 */
public class CommandServerString implements ICommandServer {

    private String commandString;
    
    public CommandServerString() {
        commandString = "v0w0";
    }

    @Override
    public String getCommand() {
        return commandString;
    }

    @Override
    public void setCommandString(String commandstring) {
        this.commandString = commandstring;
    }
}
