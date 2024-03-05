package org.dsi.ecommerce.controllers.home;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.models.User;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

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

    @ModelAttribute
    public UserDto sendUserDetails(Principal principal) {
        return userService.getUserDetails(principal);
    }

    @GetMapping("/test")
    public String sendHome() {
        return "common/test";
    }

//    @GetMapping({"/index", "/"})
//    public String sendIndex(Model model, Principal principal) {
//        if(principal == null)
//            return "redirect:/login";
//
//        UserDto userDto = userService.getUserDetails(principal);
//        model.addAttribute("userDto", userDto);
//
//        if(userDto.getRole().equals("ROLE_ADMIN") )
//            return "redirect:/admin/index";
//        return "redirect:/user/index";
//    }

    @GetMapping({"/index", "/"})
    public String sendIndex(@ModelAttribute UserDto userDto, Principal principal) {

        if(principal == null)
            return "redirect:/login";

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

    @GetMapping("/all-products")
    public String allProducts(@RequestParam("category") Optional<Integer> categoryId, @ModelAttribute UserDto userDto, Principal principal) {

        int id = categoryId.orElse(1);
        System.out.println("--------------------");
        System.out.println("CategoryID is: "+id);
        System.out.println("--------------------");

        if(principal == null)
            return "redirect:/login";

        if(userDto.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/product-index?category="+id;
        }
        return "redirect:/user/product-index?category="+id;
    }

    @GetMapping("/product-details")
    public String productDetails(@RequestParam("product") Optional<Integer> productId,
                                 @ModelAttribute UserDto userDto, Principal principal) {

        int id = productId.orElse(1);

        if(principal == null)
            return "redirect:/login";

        if(userDto.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/product-details?product="+id;
        }
        return "redirect:/user/product-details?product="+id;
    }
}
