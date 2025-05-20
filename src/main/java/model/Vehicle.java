package model;

import java.math.BigDecimal;

public class Vehicle extends BaseModel{

    private String name;
    private BigDecimal rentPrice;
    private int stock;
    private Category category;

    public Vehicle() {
    }

    public Vehicle(String name) {
        this.name = name;
    }

    public Vehicle(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public Vehicle(Long id, String name, BigDecimal rentPrice) {
        this.setId(id);
        this.name = name;
        this.rentPrice = rentPrice;
    }

    public Vehicle(Long id, String name, BigDecimal rentPrice, int stock) {
        this.setId(id);
        this.name = name;
        this.rentPrice = rentPrice;
        this.stock = stock;
    }

    public Vehicle(Long id, String name, BigDecimal rentPrice, int stock, Category category) {
        this.setId(id);
        this.name = name;
        this.rentPrice = rentPrice;
        this.stock = stock;
        this.category = category;
    }

    public Vehicle(String name, BigDecimal rentPrice, int stock, Category category) {
        this.name = name;
        this.rentPrice = rentPrice;
        this.stock = stock;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
