package com.example.coffestoreapp.Command;

import com.example.coffestoreapp.DAO.DrinkDAO;
import com.example.coffestoreapp.DTO.DrinkDTO;

public class DeleteDrinkCommand implements Command {
    private DrinkDAO drinkDAO;
    private DrinkDTO drinkDTO;
    private int drinkId;

    public DeleteDrinkCommand(int drinkId) {
        this.drinkId = drinkId;
    }

    public void setDrinkDAO(DrinkDAO drinkDAO) {
        this.drinkDAO = drinkDAO;
        this.drinkDTO = drinkDAO.getDrinkById(drinkId);
    }

    @Override
    public boolean execute() {
        return drinkDAO.deleteDrink(drinkId);
    }

    @Override
    public void undo() {
        drinkDAO.addDrink(drinkDTO);
    }
}

