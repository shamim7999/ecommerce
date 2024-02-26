package org.dsi.ecommerce.controllers.home;

import org.dsi.ecommerce.models.User;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String sendHome() {
        return "common/test";
    }

    @GetMapping("/index")
    public String sendIndex() {
        return "common/index";
    }

    @GetMapping("/register")
    public String registerHandler() {
        return "user/register";
    }

    @PostMapping("/processRegistration")
    @ResponseBody
    public String processRegistration(@ModelAttribute User user) throws Exception {
        userService.createUser(user);
        return "Success";
    }
}
