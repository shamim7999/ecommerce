package org.dsi.ecommerce.services;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.models.User;
import org.dsi.ecommerce.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    private final DTOConverter dtoConverter;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder,
                       DTOConverter dtoConverter) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.dtoConverter = dtoConverter;
    }

    public User getUserByUserName(String userEmail) {
        return userRepository.getUserByUserName(userEmail);
    }

    public void createUser(User user) throws Exception {
        user.setPic("default.png");
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public UserDto getUserDetails(Principal principal) {
        UserDto userDto = null;
        if(principal != null) {
            User user = userRepository.getUserByUserName(principal.getName());
            userDto = dtoConverter.convertToUserDTO(user);
        }
        return userDto;
    }

}
