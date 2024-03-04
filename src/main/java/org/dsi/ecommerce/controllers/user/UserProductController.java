package org.dsi.ecommerce.controllers.user;

import org.dsi.ecommerce.helper.CategoryDto;
import org.dsi.ecommerce.helper.ProductDto;
import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.ProductService;
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
@RequestMapping("/user")
public class UserProductController {
    public final ProductService productService;
    public final CategoryService categoryService;
    public final UserService userService;
    public final DTOConverter dtoConverter;

    public UserProductController(ProductService productService,
                                CategoryService categoryService,
                                UserService userService,
                                DTOConverter dtoConverter) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.dtoConverter = dtoConverter;
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

    @GetMapping("/product-index")
    public String goProductIndex(@RequestParam("category") Optional<Integer> categoryId, Model model) {

        int id = categoryId.orElse(1);

        List<ProductDto> productDtos =
                dtoConverter.convertToListOfProductDTO(productService.findProductsByCategoryId(id));
        List<CategoryDto> categoryDtos =
                dtoConverter.convertToListOfCategoryDTO(categoryService.getAllCategories());
        model.addAttribute("productDtos", productDtos);
        model.addAttribute("categoryDtos", categoryDtos);
        return "user/product_index";
    }
}
