package service;

import models.User;
import repository.UserRepository;

public class LoginService {
    private UserRepository userRepository;

    public LoginService() {
        userRepository = new UserRepository();
    }

    public User loginUser(String idNumber, String password) {
        // Validate if any field is empty
        if (idNumber.isEmpty() || password.isEmpty()) {
            return null;
        }

        // Retrieve the user from the database
        User user = userRepository.loginUser(idNumber, password);

        return user;
    }
}
