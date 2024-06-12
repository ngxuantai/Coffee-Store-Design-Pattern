package com.example.coffestoreapp.DTO;

interface Builder<T> {
    Builder<T> setDrinkID(int drinkID);
    Builder<T> setCategoryID(int categoryID);
    Builder<T> setDrinkName(String drinkName);
    Builder<T> setPrice(String price);
    Builder<T> setStatus(String status);
    Builder<T> setImage(byte[] image);
    T build();
}

public class DrinkDTO {
    private final int drinkID, categoryID;
    private final String drinkName, price, status;
    private final byte[] image;

    private DrinkDTO(DrinkBuilder builder) {
        this.drinkID = builder.drinkID;
        this.categoryID = builder.categoryID;
        this.drinkName = builder.drinkName;
        this.price = builder.price;
        this.status = builder.status;
        this.image = builder.image;
    }

    public static class DrinkBuilder implements Builder<DrinkDTO>{
        private int drinkID, categoryID;
        private String drinkName, price, status;
        private byte[] image;

        @Override
        public DrinkBuilder setDrinkID(int drinkID) {
            this.drinkID = drinkID;
            return this;
        }

        @Override
        public DrinkBuilder setCategoryID(int categoryID) {
            this.categoryID = categoryID;
            return this;
        }

        @Override
        public DrinkBuilder setDrinkName(String drinkName) {
            this.drinkName = drinkName;
            return this;
        }

        @Override
        public DrinkBuilder setPrice(String price) {
            this.price = price;
            return this;
        }

        @Override
        public DrinkBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        @Override
        public DrinkBuilder setImage(byte[] image) {
            this.image = image;
            return this;
        }

        @Override
        public DrinkDTO build() {
            return new DrinkDTO(this);
        }
    }

    public int getDrinkID() {
        return drinkID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public byte[] getImage() {
        return image;
    }

}
