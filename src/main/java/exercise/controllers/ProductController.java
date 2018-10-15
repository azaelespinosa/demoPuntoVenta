package exercise.controllers;


import exercise.dto.ProductDto;
import exercise.dto.ProductLst;
import exercise.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Clase controller para el manejo de las peticiones de los productos.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@Slf4j
@RequestMapping("product")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/register")
    public void registerProducts(@RequestBody ProductDto dto){
        productService.saveOneProduct(dto);
    }

    @PutMapping("/update")
    public void updateProducts(@RequestBody ProductDto dto){
        productService.updateProduct(dto);
    }

    @DeleteMapping("/delete/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProduct(@PathVariable("productId") Long productId) throws Exception {
        productService.deleteProduct(productId);
    }

    @GetMapping("/findall")
    public List<ProductDto> findAllProducts(){
       return productService.findAllProducts();
    }

    @GetMapping("/findby/{productId}")
    public ProductDto findProductById( @RequestParam(value = "productId", required = false) Long productId){
        return productService.findProductById(productId);
    }


}
