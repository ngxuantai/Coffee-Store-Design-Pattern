package com.example.coffestoreapp.Command;

import com.example.coffestoreapp.DAO.DrinkDAO;
import com.example.coffestoreapp.DTO.DrinkDTO;

public class AddDrinkCommand implements Command {
    private DrinkDAO drinkDAO;
    private DrinkDTO drinkDTO;

    public AddDrinkCommand(DrinkDAO drinkDAO, DrinkDTO drinkDTO) {
        this.drinkDAO = drinkDAO;
        this.drinkDTO = drinkDTO;
    }

    @Override
    public void execute() {
        drinkDAO.addDrink(drinkDTO);
    }

    @Override
    public void undo() {
        drinkDAO.deleteDrink(drinkDTO.getDrinkID());
    }
}

