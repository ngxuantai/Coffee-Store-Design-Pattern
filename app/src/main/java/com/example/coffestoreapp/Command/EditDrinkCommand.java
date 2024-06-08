package com.example.coffestoreapp.Command;

import com.example.coffestoreapp.DAO.DrinkDAO;
import com.example.coffestoreapp.DTO.DrinkDTO;

public class EditDrinkCommand implements Command {
   private DrinkDAO drinkDAO;
   private DrinkDTO newDrinkDTO;
   private DrinkDTO oldDrinkDTO;
   private int drinkId;

   public EditDrinkCommand(DrinkDAO drinkDAO, DrinkDTO newDrinkDTO, int drinkId) {
       this.drinkDAO = drinkDAO;
       this.newDrinkDTO = newDrinkDTO;
       this.drinkId = drinkId;
       this.oldDrinkDTO = drinkDAO.getDrinkById(drinkId); // Lưu trữ thông tin cũ
   }

   @Override
   public boolean execute() {
       return drinkDAO.editDrink(newDrinkDTO, drinkId);
   }

   @Override
   public void undo() {
       drinkDAO.editDrink(oldDrinkDTO, drinkId);
   }
}