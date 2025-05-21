package dao;

import dao.constants.SqlScriptConstants;
import model.Category;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements BaseDAO<Category> {

    @Override
    public long save(Category category) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.CATEGORY_SAVE)) {

            ps.setString(1, category.getName());
            ps.setBigDecimal(2, category.getRentalRatePerHour());
            ps.setBigDecimal(3, category.getRentalRatePerDay());
            ps.setBigDecimal(4, category.getRentalRatePerWeek());
            ps.setBigDecimal(5, category.getRentalRatePerMonth());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Category findById(long id) {
        Category category = null;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.CATEGORY_FIND_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> findAll(int page) {
        List<Category> categoryList = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.CATEGORY_FIND_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoryList.add(
                        new Category(rs.getLong("id"),
                                rs.getString("name"),
                                rs.getBigDecimal("rental_rate_per_hour"),
                                rs.getBigDecimal("rental_rate_per_day"),
                                rs.getBigDecimal("rental_rate_per_week"),
                                rs.getBigDecimal("rental_rate_per_month")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public void update(Category category) {

    }

    @Override
    public void delete(long id) {
        int deletedRowCount = 0;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlScriptConstants.CATEGORY_DELETE)) {
            ps.setLong(1, id);
            deletedRowCount = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
