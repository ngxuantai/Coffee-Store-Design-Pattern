package com.example.coffestoreapp.Command;

public interface Command {
    boolean execute();
    void undo();
}