package org.dsi.ecommerce.controllers.home;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.models.User;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public Principal sendPrincipal(Principal principal) {
        return principal;
    }

    @GetMapping("/test")
    public String sendHome() {
        return "common/test";
    }

    @GetMapping({"/index", "/"})
    public String sendIndex(Model model, Principal principal) {
        if(principal == null)
            return "redirect:/login";

        UserDto userDto = userService.getUserDetails(principal);
        model.addAttribute("userDto", userDto);

        if(userDto.getRole().equals("ROLE_ADMIN") )
            return "redirect:/admin/index";
        return "redirect:/user/index";
    }

    @GetMapping("/register")
    public String registerHandler(Model model) {
        model.addAttribute("title", "Sign up");
        return "common/register";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(@ModelAttribute User user, RedirectAttributes redirectAttributes) throws Exception {
        userService.createUser(user);
        redirectAttributes.addFlashAttribute("message", "Account Created Successfully!!");
        redirectAttributes.addFlashAttribute("type", "alert-success");
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "common/login";
    }


}
