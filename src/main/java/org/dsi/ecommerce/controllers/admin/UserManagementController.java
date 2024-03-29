package org.dsi.ecommerce.controllers.admin;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserManagementController {

    private final UserService userService;
    private final DTOConverter dtoConverter;

    public UserManagementController(UserService userService, DTOConverter dtoConverter) {
        this.userService = userService;
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

    @GetMapping("/user-lists")
    public String goUserLists(Model model) {
        List<UserDto> userDtos = dtoConverter.convertToListOfUserDTO(userService.getAllUsers());
        model.addAttribute("userDtos", userDtos);
        return "admin/user_control_panel";
    }

    @GetMapping("/soft-delete-user")
    public String softDelete(@RequestParam("user") Optional<Integer> userId) throws Exception {
        int id = userId.orElseThrow(() -> new Exception("Null Id provided."));
        userService.softDelete(id);
        return "redirect:/admin/user-lists";
    }

    @GetMapping("/enable-user")
    public String enableUser(@RequestParam("user") Optional<Integer> userId) throws Exception {
        int id = userId.orElseThrow(() -> new Exception("Null Id provided."));
        userService.enableUser(id);
        return "redirect:/admin/user-lists";
    }


}
