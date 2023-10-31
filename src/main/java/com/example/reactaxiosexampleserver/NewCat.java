package com.example.reactaxiosexampleserver;

public record NewCat(
        String name,
        String breed,
        int age) {

    public Cat toCat(String id) {
        return new Cat(id, name, breed, age);
    }
}
