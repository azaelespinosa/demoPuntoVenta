package exercise.orders.service;

import exercise.orders.dto.ItemDto;

import java.util.List;

/**
 * Interface para productos.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

public interface ItemService {

    void saveProduct();

    ItemDto saveOneProduct(ItemDto dto);

    List<ItemDto> findAllProducts();

    ItemDto findProductById(Long productId);

    void deleteProduct(Long productId);

    ItemDto updateProduct(ItemDto itemDto);

    String findProductNameById(Long productId);

    ItemDto jdbcFindProductById(Long productId);
}
