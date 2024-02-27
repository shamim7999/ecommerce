package org.dsi.ecommerce.services;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.models.User;
import org.dsi.ecommerce.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    public User getUserByUserName(String userEmail) {
        return userRepository.getUserByUserName(userEmail);
    }

    public void createUser(User user) throws Exception {
        user.setPic("default.png");
        user.setRole("ROLE_ADMIN");
        user.setEnabled(true);
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public UserDto getUserDetails(Principal principal) {
        UserDto userDto = new UserDto();
        if(principal != null) {
            User user = userRepository.getUserByUserName(principal.getName());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setAddress(user.getAddress());
            userDto.setEnabled(user.isEnabled());
            userDto.setPhone(user.getPhone());
            userDto.setId(user.getId());
            userDto.setPic(user.getPic());
            userDto.setRole(user.getRole());
        }
        return userDto;
    }
}
