package model;

import java.math.BigDecimal;

public class Category extends BaseModel {

    private String name;
    private BigDecimal rentalRatePerHour;
    private BigDecimal rentalRatePerDay;
    private BigDecimal rentalRatePerWeek;
    private BigDecimal rentalRatePerMonth;

    public Category() {
    }

    public Category(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, User createdUser, User updatedUser) {
        this.name = name;
        this.setCreatedUser(createdUser);
        this.setUpdatedUser(updatedUser);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRentalRatePerHour() {
        return rentalRatePerHour;
    }

    public void setRentalRatePerHour(BigDecimal rentalRatePerHour) {
        this.rentalRatePerHour = rentalRatePerHour;
    }

    public BigDecimal getRentalRatePerDay() {
        return rentalRatePerDay;
    }

    public void setRentalRatePerDay(BigDecimal rentalRatePerDay) {
        this.rentalRatePerDay = rentalRatePerDay;
    }

    public BigDecimal getRentalRatePerWeek() {
        return rentalRatePerWeek;
    }

    public void setRentalRatePerWeek(BigDecimal rentalRatePerWeek) {
        this.rentalRatePerWeek = rentalRatePerWeek;
    }

    public BigDecimal getRentalRatePerMonth() {
        return rentalRatePerMonth;
    }

    public void setRentalRatePerMonth(BigDecimal rentalRatePerMonth) {
        this.rentalRatePerMonth = rentalRatePerMonth;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id = '" + getId() + '\''+
                "name='" + name + '\'' +
                ", rentalRatePerHour=" + rentalRatePerHour +
                ", rentalRatePerDay=" + rentalRatePerDay +
                ", rentalRatePerWeek=" + rentalRatePerWeek +
                ", rentalRatePerMonth=" + rentalRatePerMonth +
                '}';
    }
}
