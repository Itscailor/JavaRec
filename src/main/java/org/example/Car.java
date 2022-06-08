package org.example;

public class Car implements Comparable<Car>{
    int id;
    String brand;
    Double price;

    public Car(int id, String brand, Double price) {
        this.id = id;
        this.brand = brand;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public Double getPrice() {
        return price;
    }

    public int compareTo(Car car) {
        return this.brand.compareTo(car.brand);
    }

}
