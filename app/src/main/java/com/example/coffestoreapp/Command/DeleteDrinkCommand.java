package com.example.coffestoreapp.Command;

import com.example.coffestoreapp.DAO.DrinkDAO;
import com.example.coffestoreapp.DTO.DrinkDTO;

public class DeleteDrinkCommand implements Command {
    private DrinkDAO drinkDAO;
    private DrinkDTO drinkDTO;

    public DeleteDrinkCommand(DrinkDAO drinkDAO, DrinkDTO drinkDTO) {
        this.drinkDAO = drinkDAO;
        this.drinkDTO = drinkDTO;
    }

    @Override
    public void execute() {
        drinkDAO.deleteDrink(drinkDTO.getDrinkID());
    }

    @Override
    public void undo() {
        drinkDAO.addDrink(drinkDTO);
    }
}
