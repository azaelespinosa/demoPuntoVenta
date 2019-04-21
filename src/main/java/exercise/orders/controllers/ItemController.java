package exercise.orders.controllers;


import exercise.orders.dto.ItemDto;
import exercise.orders.service.ItemService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase controller para el manejo de las peticiones de los productos.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@Slf4j
@RequestMapping("product")
@RestController
public class ItemController {

    @Autowired
    ItemService itemService;


    @PostMapping("/register")
    public void registerProducts(@RequestBody ItemDto dto){
        itemService.saveOneProduct(dto);
    }

    @PutMapping("/update")
    public void updateProducts(@RequestBody ItemDto dto){
        itemService.updateProduct(dto);
    }

    @DeleteMapping("/delete/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProduct(@PathVariable("productId") Long productId) throws Exception {
        itemService.deleteProduct(productId);
    }

    @GetMapping("/findall")
    public List<ItemDto> findAllProducts(){
       return itemService.findAllProducts();
    }

    @GetMapping("/findby/{productId}")
    public ItemDto findProductById(@RequestParam(value = "productId", required = false) Long productId){
        return itemService.findProductById(productId);
    }

    @GetMapping("/findby/jdbc/name/{productId}")
    @ApiOperation(value = "Busca un producto por id via jdbc con queryForObject")
    public String findProductNameById( @RequestParam(value = "productId", required = false) Long productId){
        return itemService.findProductNameById(productId);
    }

    @GetMapping("/findby/jdbc/{productId}")
    @ApiOperation(value = "Busca un producto por id via jdbc con queryForObject y BeanPropertyRowMapper")
    public ItemDto jdbcFindProductById(@RequestParam(value = "productId", required = false) Long productId){
        return itemService.jdbcFindProductById(productId);
    }

}
