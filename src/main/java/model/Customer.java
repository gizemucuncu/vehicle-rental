package model;

import model.enums.CustomerType;

import java.time.LocalDate;

public class Customer extends BaseModel {

    private String name;
    private String email;
    private String password; // TODO hashlenmeli
    private CustomerType customerType;
    private int age;
    private LocalDate birthDate;

    public Customer() {
    }

    public Customer(String name, String email, String password, CustomerType customerType, int age, LocalDate birthDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.customerType= customerType;
        this.age=age;
        this.birthDate=birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
