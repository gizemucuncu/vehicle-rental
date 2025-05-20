package dao;

import dao.constants.SqlScriptConstants;
import model.Rental;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            e.printStackTrace(); // veya kendi log mekanizman
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
}
