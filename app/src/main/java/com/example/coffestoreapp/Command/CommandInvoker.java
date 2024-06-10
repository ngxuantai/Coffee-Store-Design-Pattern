package com.example.coffestoreapp.Command;

import java.util.Stack;

public class CommandInvoker {
    private Stack<Command> commandStack = new Stack<>();

    public boolean executeCommand(Command command) {
        boolean result = command.execute();
        if (result) {
            commandStack.push(command);
        }
        return result;
    }

    public void undoLastCommand() {
        if (!commandStack.isEmpty()) {
            Command command = commandStack.pop();
            command.undo();
        }
    }

    public boolean hasCommands() {
        return !commandStack.isEmpty();
    }
}

