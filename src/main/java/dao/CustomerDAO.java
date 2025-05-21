package dao;

import dao.constants.SqlScriptConstants;
import model.Customer;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDAO implements BaseDAO<Customer>{


    public long save(Customer customer) {

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.CUSTOMER_SAVE)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getCustomerType().name());
            ps.setInt(5, customer.getAge());
            ps.setDate(6, java.sql.Date.valueOf(customer.getBirthDate()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Customer findById(long id) {
        Customer customer = null;
        try (Connection connection = DBUtil.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.CUSTOMER_FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setCreatedDate(new Timestamp(rs.getDate("created_date").getTime()).toLocalDateTime());
                customer.setUpdatedDate(new Timestamp(rs.getDate("updated_date").getTime()).toLocalDateTime());
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    public List<Customer> findAll(int page) {
        {
            List<Customer> customers = new ArrayList<>();

            try {
                Connection connection = DBUtil.getConnection();

                Statement stmt = connection.createStatement();

                ResultSet rs = stmt.executeQuery(SqlScriptConstants.CUSTOMER_FIND_ALL);

                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getLong("id"));
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setCreatedDate(new Timestamp(rs.getDate("created_date").getTime()).toLocalDateTime());
                    customer.setUpdatedDate(new Timestamp(rs.getDate("updated_date").getTime()).toLocalDateTime());
                    customer.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    customers.add(customer);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customers;
        }
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(long id) {

    }

    public boolean existByEmail(String email) {
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.CUSTOMER_EXIST_BY_EMAIL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Customer findByEmail(String email) {

        Customer customer = null;
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.CUSTOMER_EXIST_BY_EMAIL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setCreatedDate(new Timestamp(rs.getDate("created_date").getTime()).toLocalDateTime());
                customer.setUpdatedDate(new Timestamp(rs.getDate("updated_date").getTime()).toLocalDateTime());
                customer.setBirthDate(rs.getDate("birth_date").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}

