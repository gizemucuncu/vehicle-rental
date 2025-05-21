package service;

import dao.VehicleDAO;
import exception.ExceptionMessagesConstants;
import exception.VehicleRentalException;
import model.User;
import model.Vehicle;
import model.enums.Role;

import java.util.List;

public class VehicleService {
    private final VehicleDAO vehicleDAO;

    public VehicleService() {
        this.vehicleDAO = new VehicleDAO();
    }

    public void save(Vehicle vehicle, User user) throws VehicleRentalException {
        if (!Role.ADMIN.equals(user.getRole())) {
            throw new VehicleRentalException(ExceptionMessagesConstants.USER_IS_NOT_ADMIN);
        }
        vehicle.setCreatedUser(user);
        vehicle.setUpdatedUser(user);

        vehicleDAO.save(vehicle);
        System.out.println("Araç Başarıyla Kaydedildi!");
    }

    public int getTotalPage() {
        return vehicleDAO.findTotalPage();
    }

    public List<Vehicle> search(String searchVehicleName) {
        return  vehicleDAO.searchByName(searchVehicleName);
    }


    public List<Vehicle> getAll(int page) {
        return vehicleDAO.findAll(page);
    }

    public void deleteById(long id) {
        vehicleDAO.delete(id);
        System.out.println(id + " ID'li Araç sistemden başarıyla silindi!");
    }

    public List<Vehicle> getAllByCategoryName(String categoryName) {
        return vehicleDAO.findAllByCategoryName(categoryName);
    }

    public Vehicle getByName(String vehicleName) {
        return vehicleDAO.findByName(vehicleName);
    }

    public Vehicle getToRentByName(String vehicleName) {
        return vehicleDAO.findToRentByName(vehicleName);
    }


}
