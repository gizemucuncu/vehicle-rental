package service;

import dao.CategoryDAO;
import exception.ExceptionMessagesConstants;
import exception.VehicleRentalException;
import model.Category;
import model.User;
import model.Vehicle;
import model.enums.Role;

import java.math.BigDecimal;
import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public List<Category> getAll() {
        return categoryDAO.findAll(5);
    }

    public void deleteById(long id) {
        categoryDAO.delete(id);
        System.out.println("Kategori Başarıyla Silindi!");
    }

    public Category getById(long categoryId) throws VehicleRentalException {
        Category foundCategory = categoryDAO.findById(categoryId);

        if (foundCategory == null) {
            throw new VehicleRentalException(ExceptionMessagesConstants.CATEGORY_NOT_FOUND);
        }
        System.out.println("Bulunan Kategori: " + foundCategory);
        return foundCategory;

    }

    public void save(Category category, User loginUser) throws VehicleRentalException {
        if (!Role.ADMIN.equals(loginUser.getRole())) {
            throw new VehicleRentalException(ExceptionMessagesConstants.USER_IS_NOT_ADMIN);
        }
        category.setCreatedUser(loginUser);
        category.setUpdatedUser(loginUser);

        categoryDAO.save(category);
        System.out.println("Kategori Başarıyla Kaydedildi!");
    }

}

