package service;

import dao.CustomerDAO;
import exception.ExceptionMessagesConstants;
import exception.VehicleRentalException;
import model.Customer;
import model.enums.CustomerType;
import utils.PasswordUtil;

import java.time.LocalDate;

public class CustomerService {

    private CustomerDAO customerDAO;

    public CustomerService() {
        customerDAO = new CustomerDAO();
    }

    public void save(String name, String email, String password, CustomerType customerType, LocalDate birthDate) throws VehicleRentalException {

        boolean isExist = customerDAO.existByEmail(email);
        if (isExist) {
            throw new VehicleRentalException(ExceptionMessagesConstants.CUSTOMER_EMAIL_ALREADY_EXISTS);
        }

        Customer customer = new Customer(name, email, PasswordUtil.hash(password), customerType, birthDate);
        customerDAO.save(customer);
        System.out.println("Kayıt Başarılı");
    }

    public Customer login(String email, String password) throws VehicleRentalException {

        boolean isExist = customerDAO.existByEmail(email);

        if (!isExist) {
            throw new VehicleRentalException(ExceptionMessagesConstants.CUSTOMER_EMAIL_DOES_NOT_EXISTS);
        }

        String hashedPassword = PasswordUtil.hash(password);

        Customer foundCustomer = customerDAO.findByEmail(email);

        if (foundCustomer != null) {
            boolean passwordsEquals = foundCustomer.getPassword().equals(hashedPassword);
            if (!passwordsEquals) {
                throw new VehicleRentalException(ExceptionMessagesConstants.CUSTOMER_PASSWORD_DOES_NOT_MATCH);
            } else {
                System.out.println("Kullanıcı sisteme giriş yaptı!");
            }
        }
        return foundCustomer;
    }
}

