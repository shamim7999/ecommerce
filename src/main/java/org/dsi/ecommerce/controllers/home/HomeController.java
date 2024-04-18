package org.dsi.ecommerce.controllers.home;

import jakarta.validation.Valid;
import org.dsi.ecommerce.helper.Message;
import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.models.ConfirmationToken;
import org.dsi.ecommerce.models.User;
import org.dsi.ecommerce.services.ConfirmationTokenService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    private final DTOConverter dtoConverter;

    public HomeController(UserService userService, ConfirmationTokenService confirmationTokenService,
                          DTOConverter dtoConverter) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.dtoConverter = dtoConverter;
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
        return "common/page";
    }


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
        model.addAttribute("user", new User());
        return "common/register";

    }

    @PostMapping("/processRegistration")
    public String processRegistration(@Valid @ModelAttribute User user, BindingResult result,
                                      RedirectAttributes redirectAttributes) throws Exception {

        try {
            if(result.hasErrors()) {
                redirectAttributes.addFlashAttribute("message",
                        new Message("Account creation failed", "alert-danger", Message.errorMessage(result)));
                return "common/register";
            }
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("message",
                    new Message("Account created Successfully", "alert-success", null));
            return "redirect:/register";
        } catch (DataAccessException ex) {
            result.rejectValue("email", "", "This email already exists.");
            redirectAttributes.addFlashAttribute("message",
                    new Message("Account creation failed", "alert-danger", Message.errorMessage(result)));
            return "redirect:/register";
        }
    }


    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userService.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userService.saveUpdatedUser(user);
            return "Account is verified.";
        }
        return "Invalid link, or the link is broken";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "common/login";
    }

    @GetMapping("/all-products")
    public String allProducts(@RequestParam("category") Optional<Integer> categoryId,
                              @RequestParam("productPage") Optional<Integer> productPage,
                              @ModelAttribute UserDto userDto, Principal principal) {

        int currentProductPage = productPage.orElse(1);
        int id = categoryId.orElse(1);

        if(principal == null)
            return "redirect:/login";

        if(userDto.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/product-index?category="+id+"&productPage="+currentProductPage;
        }
        return "redirect:/user/product-index?category="+id+"&productPage="+currentProductPage;
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

    @GetMapping("/customer-details")
    public String userDetails(@RequestParam("customer") Optional<Integer> customerId, Model model) throws Exception {
        int id = customerId.orElseThrow(() -> new Exception("Null Id provided."));
        UserDto userDto = dtoConverter.convertToUserDTO(userService.getUserById(id));
        model.addAttribute("customerDto", userDto);
        return "common/customer_details";
    }
}
