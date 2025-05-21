package model;

import model.enums.CustomerType;
import model.enums.RentalType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Rental extends BaseModel {

    private Customer customer;
    private Vehicle vehicle;
    private RentalType rentalType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal totalPrice;
    private BigDecimal deposit;
    //private List<RentalVehicle> rentalVehicleList;


    public Rental() {
    }

    public Rental(Customer customer, Vehicle vehicle, RentalType rentalType, LocalDateTime startDate, LocalDateTime endDate, BigDecimal totalPrice) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.rentalType = rentalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Rental(Customer customer, Vehicle vehicle, RentalType rentalType, LocalDateTime startDate, LocalDateTime endDate, BigDecimal totalPrice, BigDecimal deposit) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.rentalType = rentalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.deposit = deposit;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }


    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return "Rental History: " + '\n' +
                "-> customer= " + customer + '\n' +
                "-> vehicle= " + vehicle + '\n' +
                "-> rental type= " + rentalType + '\n' +
                "-> start date= " + startDate + '\n' +
                "-> end date= " + endDate + '\n' +
                "-> total price= " + totalPrice + '\n' +
                "-> deposit= " + deposit + '\n';
    }
}
