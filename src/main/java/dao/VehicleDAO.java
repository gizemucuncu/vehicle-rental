package dao;

import constants.VehicleRentalConstants;
import dao.constants.SqlScriptConstants;
import model.Category;
import model.Vehicle;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO implements BaseDAO<Vehicle> {

    public long save(Vehicle vehicle) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.VEHICLE_SAVE)) {
            ps.setString(1, vehicle.getName());
            ps.setBigDecimal(2, vehicle.getPrice());
            ps.setInt(3, vehicle.getStock());
            ps.setLong(4, vehicle.getCategory().getId());
            ps.setLong(5, vehicle.getCreatedUser().getId());
            ps.setLong(6, vehicle.getUpdatedUser().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Vehicle findById(long id) {
        return null;
    }

    public List<Vehicle> findAll(int page) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.VEHICLE_FIND_ALL)) {
            int size = VehicleRentalConstants.PAGE_SIZE;
            int offset = (page - 1) * size;
            ps.setInt(1, size);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(getVehicle(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public void update(Vehicle vehicle) {

    }

    @Override
    public void delete(long id) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.VEHICLE_DELETE)
        ) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vehicle getVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(rs.getLong("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getInt("stock"),
                new Category(rs.getLong("category_id"), rs.getString("category_name")));
    }

    public int findTotalPage() {

        try (Connection connection = DBUtil.getConnection();
             Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(SqlScriptConstants.VEHICLE_TOTAL_PAGE_COUNT);

            if (rs.next()) {
                int totalRows = rs.getInt(1);
                return (int) Math.ceil((double) totalRows / VehicleRentalConstants.PAGE_SIZE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Vehicle> searchByName(String searchVehicleName) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.VEHICLE_SEARCH_BY_NAME)) {
            ps.setString(1, "%" + searchVehicleName + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vehicles.add(getVehicle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> findAllByCategoryName(String categoryName) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.VEHICLE_FIND_BY_CATEGORY_NAME)) {
            ps.setString(1, categoryName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vehicles.add(getVehicle(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public Vehicle findByName(String vehicleName) {
        Vehicle vehicle = null;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.VEHICLE_FIND_BY_NAME)) {
            ps.setString(1, vehicleName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setRentalRatePerHour(rs.getBigDecimal("rental_rate_per_hour"));
                category.setRentalRatePerDay(rs.getBigDecimal("rental_rate_per_day"));
                category.setRentalRatePerWeek(rs.getBigDecimal("rental_rate_per_week"));
                category.setRentalRatePerMonth(rs.getBigDecimal("rental_rate_per_month"));

                vehicle = new Vehicle(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock"),
                        category
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }
}
