package org.dsi.ecommerce.services;

import org.dsi.ecommerce.models.User;
import org.dsi.ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) throws Exception {
        user.setPic("default.png");
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}
