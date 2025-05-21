package dao;

import dao.constants.SqlScriptConstants;
import model.Category;
import model.Customer;
import model.Rental;
import model.Vehicle;
import model.enums.CustomerType;
import model.enums.RentalType;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO implements BaseDAO<Rental> {
    @Override
    public long save(Rental rental) {

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.RENTAL_INSERT)) {

            ps.setLong(1, rental.getCustomer().getId());
            ps.setLong(2, rental.getVehicle().getId());
            ps.setString(3, rental.getRentalType().name());
            ps.setTimestamp(4, Timestamp.valueOf(rental.getStartDate()));
            ps.setTimestamp(5, Timestamp.valueOf(rental.getEndDate()));
            ps.setBigDecimal(6, rental.getTotalPrice());
            ps.setBigDecimal(7, rental.getDeposit());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Rental findById(long id) {
        return null;
    }

    @Override
    public List<Rental> findAll(int page) {
        return List.of();
    }

    @Override
    public void update(Rental rental) {

    }

    @Override
    public void delete(long id) {

    }

    private Rental getRental(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setName(rs.getString("category_name"));

        Customer customer = new Customer();
        customer.setName(rs.getString("customer_name"));
        customer.setEmail(rs.getString("email"));
        customer.setCustomerType(CustomerType.valueOf(rs.getString("customer_type")));
        customer.setBirthDate(rs.getDate("birth_date").toLocalDate());

        Vehicle vehicle = new Vehicle();
        vehicle.setName(rs.getString("vehicle_name"));
        vehicle.setCategory(category);

        Rental rental = new Rental();
        rental.setVehicle(vehicle);
        rental.setRentalType(RentalType.valueOf(rs.getString("rental_type")));
        rental.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
        rental.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
        rental.setTotalPrice(rs.getBigDecimal("total_price"));
        rental.setDeposit(rs.getBigDecimal("deposit"));
        rental.setCustomer(customer);

        return rental;
    }


    public List<Rental> findAllRentHistory(long customerId) {
        List<Rental> rentals = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.GET_RENTAL_HISTORY)) {

            ps.setLong(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rentals.add(getRental(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(!rentals.isEmpty()) {
            System.out.println("Kiralama Geçmişiniz: ");
            for (Rental rental : rentals) {
                System.out.println(rental);
            }
        } else {
            System.out.println("Kiralama geçmişinde kayıt bulunamadı");
        }
        return rentals;
    }

    public List<Rental> findAllRentals() {
        List<Rental> rentals = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SqlScriptConstants.GET_ALL_RENTAL_HISTORY_TO_ADMIN)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rentals.add(getRental(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
}
