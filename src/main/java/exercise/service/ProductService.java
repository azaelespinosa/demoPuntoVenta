package exercise.service;

import exercise.dto.ProductDto;

import java.util.List;

/**
 * Interface para productos.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

public interface ProductService {

    void saveProduct();

    void saveOneProduct(ProductDto dto);

    List<ProductDto> findAllProducts();

    ProductDto findProductById(Long productId);

    void deleteProduct(Long productId);

    void updateProduct(ProductDto productDto);
}
