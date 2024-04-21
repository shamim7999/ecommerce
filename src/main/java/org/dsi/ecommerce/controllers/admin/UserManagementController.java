package org.dsi.ecommerce.controllers.admin;

import lombok.AllArgsConstructor;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class UserManagementController {

    private final UserService userService;
    private final DTOConverter dtoConverter;

    @GetMapping("/user-lists")
    public String goUserLists(Model model) {
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
