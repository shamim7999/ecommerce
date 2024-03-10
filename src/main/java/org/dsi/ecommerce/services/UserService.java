package org.dsi.ecommerce.services;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.models.ConfirmationToken;
import org.dsi.ecommerce.models.User;
import org.dsi.ecommerce.repositories.ConfirmationTokenRepository;
import org.dsi.ecommerce.repositories.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    private final DTOConverter dtoConverter;

    private final EmailService emailService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder, DTOConverter dtoConverter,
                       EmailService emailService, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.dtoConverter = dtoConverter;
        this.emailService = emailService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public User getUserByUserName(String userEmail) {
        return userRepository.getUserByUserName(userEmail);
    }

    public void createUser(User user) throws Exception {
        user.setPic("default.png");
        user.setRole("ROLE_USER");
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("sarker.shamim1998@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://10.255.185.169:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailService.sendEmail(mailMessage);

    }

    public UserDto getUserDetails(Principal principal) {
        UserDto userDto = null;
        if(principal != null) {
            User user = userRepository.getUserByUserName(principal.getName());
            userDto = dtoConverter.convertToUserDTO(user);
        }
        return userDto;
    }

    public User getUserById(int userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(() -> new Exception("User not Found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void softDelete(int userId) throws Exception {
        userRepository.softDelete(userId);
    }

    public void enableUser(int userId) throws Exception {
        userRepository.enableUser(userId);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public void saveUpdatedUser(User user) {
        userRepository.save(user);
    }
}
