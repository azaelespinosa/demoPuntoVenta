package exercise.ui.controllers.catalogs;


import exercise.dto.ProductDto;
import exercise.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/ui/catalogs/product/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductUIController {

    private final ProductService productService;


    @GetMapping
    public String index(Model model) {

        try {

            List<ProductDto> lstProducts = productService.findAllProducts();

            model.addAttribute("products", lstProducts);

            return "/catalogs/product/index";

        } catch (Exception ex) {

            log.error("Method index - Error!!",ex);

            return "/error";
        }

    }

    @GetMapping(value = "/upload")
    public String upload(final ProductDto productDto, Model model) {

        try {

            productService.saveProduct();

            List<ProductDto> lstProducts = productService.findAllProducts();

            model.addAttribute("products", lstProducts);

            return "/catalogs/product/index";

        } catch (Exception ex) {

            log.error("Method index - Error!!",ex);

            return "/error";
        }

    }




}
