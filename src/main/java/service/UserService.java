
package service;

        import models.User;
        import repository.UserRepository;

public class UserService {
    public static String loggedInUserId;
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public boolean registerUser(String idNumber, String password, String confirmPassword) {
        return userRepository.registerUser(idNumber, password, confirmPassword);
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
