package service;

import dao.UserDAO;
import exception.ExceptionMessagesConstants;
import exception.VehicleRentalException;
import model.User;
import model.enums.Role;
import utils.PasswordUtil;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public void save(String userName, String password, Role role, String email) throws VehicleRentalException {

        User foundUser = userDAO.findByUserEmail(email);
        System.out.println("User found: " + foundUser);

        if (foundUser != null) {
            throw new VehicleRentalException(ExceptionMessagesConstants.USER_EMAIL_ALREADY_EXIST);
        }

        userDAO.save(new User(userName, PasswordUtil.hash(password), role, email));
        System.out.println("Kayıt Başarılı!");
    }

    public User login(String email, String password) throws VehicleRentalException {

        User foundUser = userDAO.findByUserEmail(email);

        if (foundUser != null) {
            String hashedPassword = PasswordUtil.hash(password);
            if (!hashedPassword.equals(foundUser.getPassword())) {
                throw new VehicleRentalException(ExceptionMessagesConstants.USER_PASSWORDS_DOES_NOT_MATCH);
            }
        } else {
            throw new VehicleRentalException(ExceptionMessagesConstants.USER_PASSWORDS_DOES_NOT_MATCH);
        }

        System.out.println("Giriş Başarılı!");
        System.out.println("Hoş Geldin " + foundUser.getUsername());

        return foundUser;
    }
}

