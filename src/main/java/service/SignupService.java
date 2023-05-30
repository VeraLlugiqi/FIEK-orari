package service;

import repository.UserRepository;

public class SignupService {
    private UserRepository userRepository;

    public SignupService() {
        this.userRepository = new UserRepository();
    }

    public boolean registerUser(String idNumber, String password, String confirmPassword) {
        return userRepository.registerUser(idNumber, password, confirmPassword);
    }
}
