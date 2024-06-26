package com.example.coffestoreapp.Command;

import com.example.coffestoreapp.DAO.DrinkDAO;
import com.example.coffestoreapp.DTO.DrinkDTO;

public class AddDrinkCommand implements Command {
    private final DrinkDAO drinkDAO;
    private final DrinkDTO drinkDTO;

    public AddDrinkCommand(DrinkDAO drinkDAO, DrinkDTO drinkDTO) {
        this.drinkDAO = drinkDAO;
        this.drinkDTO = drinkDTO;
    }

    @Override
    public boolean execute() {
        return drinkDAO.addDrink(drinkDTO);
    }

    @Override
    public void undo() {
        drinkDAO.deleteDrink(drinkDTO.getDrinkID());
    }
}

