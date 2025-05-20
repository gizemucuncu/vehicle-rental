package service;

import exception.ExceptionMessagesConstants;
import exception.VehicleRentalException;
import model.User;
import model.Vehicle;
import model.enums.CustomerType;
import model.enums.RentalType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentalService {
    public void rent(Customer customer, Vehicle vehicle, RentalType rentalType, LocalDateTime startDate, LocalDateTime endDate) throws VehicleRentalException {
        if (customer.getCustomerType() == CustomerType.CORPORATE && rentalType != RentalType.MONTHLY) {
            throw new VehicleRentalException(ExceptionMessagesConstants.CORPORATE_USER_CAN_ONLY_RENT_MONTHLY);
        }

        if (vehicle.getPrice().compareTo(BigDecimal.valueOf(2_000_000)) > 0) {
            if (customer.getAge() < 30) {
                throw new VehicleRentalException(ExceptionMessagesConstants.USER_NOT_ELIGIBLE_FOR_EXPENSIVE_VEHICLE);
            }
        }

        BigDecimal rate = switch (rentalType) {
            case HOURLY -> vehicle.getRentalRatePerHour();
            case DAILY -> vehicle.getRentalRatePerDay();
            case WEEKLY -> vehicle.getRentalRatePerWeek();
            case MONTHLY -> vehicle.getRentalRatePerMonth();
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
        rental.setDeposit(deposit);

        rentalDAO.save(rental);
        System.out.println("Kiralama tamamlandı: ₺" + total + " | Depozito: ₺" + deposit);
    }

}
