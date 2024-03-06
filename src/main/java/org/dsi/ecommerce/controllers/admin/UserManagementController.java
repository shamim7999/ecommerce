package org.dsi.ecommerce.controllers.admin;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public Principal sendPrincipal(Principal principal) {
        return principal;
    }

    @ModelAttribute
    public UserDto sendUserDetails(Principal principal) {
        return userService.getUserDetails(principal);
    }

    @GetMapping("/user-lists")
    public String goUserLists() {
        return "admin/user_control_panel";
    }
}
