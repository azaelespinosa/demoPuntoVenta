package exercise.ui.controllers.catalogs;


import exercise.orders.dto.ItemDto;
import exercise.orders.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/ui/catalogs/product/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductUIController {

    private final ItemService itemService;


    @GetMapping
    public String index(Model model) {

        try {

            List<ItemDto> lstProducts = itemService.findAllProducts();

            model.addAttribute("products", lstProducts);

            return "/catalogs/product/index";

        } catch (Exception ex) {

            log.error("Method index - Error!!",ex);

            return "/error";
        }

    }

    @GetMapping(value = "/upload")
    public String upload(final ItemDto itemDto, Model model) {

        try {

            itemService.saveProduct();

            List<ItemDto> lstProducts = itemService.findAllProducts();

            model.addAttribute("products", lstProducts);

            return "/catalogs/product/index";

        } catch (Exception ex) {

            log.error("Method index - Error!!",ex);

            return "/error";
        }

    }




}
