package service;

import dao.CategoryDAO;
import dao.RentalDAO;
import exception.ExceptionMessagesConstants;
import exception.VehicleRentalException;
import model.Category;
import model.Customer;
import model.Rental;
import model.Vehicle;
import model.enums.CustomerType;
import model.enums.RentalType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalService {
    private final RentalDAO rentalDAO;

    public RentalService() {
        this.rentalDAO = new RentalDAO();
    }

    public void rent(Customer customer, Vehicle vehicle, RentalType rentalType, LocalDateTime startDate, LocalDateTime endDate) throws VehicleRentalException {
        if (customer.getCustomerType() == CustomerType.CORPORATE && rentalType != RentalType.MONTHLY) {
            throw new VehicleRentalException(ExceptionMessagesConstants.CORPORATE_USER_CAN_ONLY_RENT_MONTHLY);
        }

        if (vehicle.getPrice().compareTo(BigDecimal.valueOf(2_000_000)) > 0) {
            if(customer.getBirthDate() != null){
                int customerAge = Period.between(customer.getBirthDate(), LocalDate.now()).getYears();
                if (customerAge < 30) {
                    throw new VehicleRentalException(ExceptionMessagesConstants.USER_NOT_ELIGIBLE_FOR_EXPENSIVE_VEHICLE);
                }
            } else {
                throw new VehicleRentalException(ExceptionMessagesConstants.CUSTOMER_BIRTH_DATE_NULL);
            }
        }

        Category category=vehicle.getCategory();

        BigDecimal rate = switch (rentalType) {
            case HOURLY -> category.getRentalRatePerHour();
            case DAILY -> category.getRentalRatePerDay();
            case WEEKLY -> category.getRentalRatePerWeek();
            case MONTHLY -> category.getRentalRatePerMonth();
        };

        long duration = switch (rentalType) {
            case HOURLY -> ChronoUnit.HOURS.between(startDate, endDate);
            case DAILY -> ChronoUnit.DAYS.between(startDate, endDate);
            case WEEKLY -> ChronoUnit.WEEKS.between(startDate, endDate);
            case MONTHLY -> ChronoUnit.MONTHS.between(startDate, endDate);
        };

        BigDecimal total = rate.multiply(BigDecimal.valueOf(duration));
        BigDecimal deposit = vehicle.getPrice().compareTo(BigDecimal.valueOf(2_000_000)) > 0
                ? vehicle.getPrice().multiply(BigDecimal.valueOf(0.10))
                : BigDecimal.ZERO;

        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setVehicle(vehicle);
        rental.setRentalType(rentalType);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setTotalPrice(total);

        rentalDAO.save(rental);
        System.out.println("Kiralama tamamlandı: ₺" + total + " | Depozito: ₺" + deposit);
    }

    public void showRentHistory(Customer LOGINED_CUSTOMER){
        rentalDAO.findAllRentHistory(LOGINED_CUSTOMER.getId());

    }

    public List<Rental> getAllRentals() {
        return rentalDAO.findAllRentals();
    }
}
