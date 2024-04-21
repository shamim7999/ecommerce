package org.dsi.ecommerce.controllers.user;

import lombok.AllArgsConstructor;
import org.dsi.ecommerce.helper.dtos.ProductDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.ProductService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserProductController {
    public final ProductService productService;
    public final CategoryService categoryService;
    public final UserService userService;
    public final DTOConverter dtoConverter;

    @GetMapping("/product-index")
    public String goProductIndex(@RequestParam("category") Optional<Integer> categoryId,
                                 @RequestParam("productPage") Optional<Integer> productPage,
                                 Model model) {

        int currentProductPage = productPage.orElse(1);
        int id = categoryId.orElse(1);

        Page<ProductDto> productDtos =
                dtoConverter.convertToPageOfProductDTO(
                        productService.findProductsByStatusSetToTrue(id, currentProductPage)
                );

        model.addAttribute("productDtos", productDtos);


        model.addAttribute("categoryId", id);
        model.addAttribute("productDtos", productDtos);
        model.addAttribute("currentProductPage", currentProductPage);
        model.addAttribute("totalProductPages", productDtos.getTotalPages());
        model.addAttribute("showQuery", false);

        return "user/product_index";
    }

    @GetMapping("product-index/queries")
    public String goProductIndexBySearch(@RequestParam(value = "query", required = false) Optional<String> query,
                                         @RequestParam("productPage") Optional<Integer> productPage,
                                         Model model) {

        int currentProductPage = productPage.orElse(1);
        String myQuery = query.orElse("").trim();

        Page<ProductDto> productDtos = dtoConverter.convertToPageOfProductDTO(
                productService.findProductsBySearchForUser(myQuery, currentProductPage)
        );

        model.addAttribute("productDtos", productDtos);
        model.addAttribute("showQuery", true);
        model.addAttribute("myQuery", myQuery);
        model.addAttribute("currentProductPage", currentProductPage);
        model.addAttribute("totalProductPages", productDtos.getTotalPages());

        return "user/product_index";
    }

    @GetMapping("/product-details")
    public String goProductDetails(@RequestParam("product") Optional<Integer> productId, Model model) throws Exception {
        int id = productId.orElse(1);

        model.addAttribute("productDto",
                dtoConverter.convertToProductDTO(productService.getProductById(id)));
        return "common/product_details";
    }
}
