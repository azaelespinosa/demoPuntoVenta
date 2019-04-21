package exercise.orders.service.impl;

import exercise.aspects.RequestLog;
import exercise.aspects.Time;
import exercise.common.exceptions.CustomException;
import exercise.common.services.BaseService;
import exercise.orders.dto.ItemDto;
import exercise.orders.jdbcrepository.JdbcItemRepository;
import exercise.orders.model.DiscountEntity;
import exercise.orders.model.ItemEntity;
import exercise.orders.repository.ItemRepository;
import exercise.orders.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.w3c.dom.Element;

/**
 * Implementacion del servicio para productos.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@Slf4j
@Service
public class ItemServiceImpl extends BaseService<ItemRepository,ItemEntity> implements ItemService {

    @Autowired
    JdbcItemRepository jdbcItemRepository;

    /**
     * Metodo para guardar un producto via REST.
     * @param itemDto
     * @return
     */
    @Time
    @RequestLog
    @Transactional
    public ItemDto saveOneProduct(ItemDto itemDto){

        log.info("Method saveOneProduct iniciando.");

        if (Optional.ofNullable(itemDto).map(ItemDto::getName).map(t -> t == null).orElse(true)) {
            throw new RuntimeException("Favor de enviar un producto.");
        }

        try {
            return create(itemDto,ItemDto.class);

        }catch(Exception e){
            throw new CustomException("Ocurrio un error al guardar el producto.");
        }


    }

    /**
     * Metodo para guardar un producto via REST.
     * @param itemDto
     * @return
     */
    @Time
    @RequestLog
    @Transactional
    public ItemDto updateProduct(ItemDto itemDto){

        log.info("Method updateProduct iniciando.");

        if (Optional.ofNullable(itemDto).map(ItemDto::getItemId).map(t -> t == null).orElse(true)) {
            throw new RuntimeException("Favor de enviar un producto.");
        }

        try {

            return update(itemDto.getItemId(), itemDto,ItemDto.class);

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al guardar el producto.");
        }


    }

    /**
     * Metodo para busqueda de todos los productos.
     * @param
     * @return
     */
    @Time
    @RequestLog
    public List<ItemDto> findAllProducts(){

        log.info("Method findAllProducts iniciando.");

        try {

            return findAll(ItemDto.class);

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al buscar el producto.");
        }

    }

    /**
     * Metodo para busqueda de todos los productos.
     * @param
     * @return
     */
    @Time
    @RequestLog
    public ItemDto findProductById(Long productId){

        log.info("Method findProductById iniciando.");

        try {

            return findById(productId,ItemDto.class);

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al buscar el producto.");
        }

    }

    /**
     * Metodo para busqueda de todos los productos.
     * @param
     * @return
     */
    @Time
    @RequestLog
    public void deleteProduct(Long productId){

        log.info("Method deleteProduct iniciando.");

        try {

            delete(productId);

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al borrar el producto : "+ productId);
        }


    }


    /**
     * Metodo para guardar los productos a partir de un XML.
     * @param
     * @return
     */
    @Time
    @RequestLog
    @Transactional
    public void saveProduct(){

        log.info("Method saveProduct iniciando.");

        List<ItemEntity> lstProducts = xmlToEntity();

        try {
            //TODO validar lista vacia
            repository.saveAll(lstProducts);

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al guardar los productos.");
        }


    }

    /**
     * Metodo para cargar un archivo XML y crear una lista entidades de producto.
     * @return List<ItemEntity>
     */
    @Time
    @RequestLog
    private List<ItemEntity> xmlToEntity(){
        log.info("Method xmlToEntity iniciando.");
        List<ItemEntity> lst = new ArrayList<>();
        ItemEntity entity=null;
        try {

            File fXmlFile = new ClassPathResource("products.xml").getFile();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            log.info("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("product");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                log.info("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    log.info("id : " + eElement.getElementsByTagName("id").item(0).getTextContent());
                    log.info("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    log.info("Price : " + eElement.getElementsByTagName("price").item(0).getTextContent());
                    log.info("UPC : " + eElement.getElementsByTagName("upc").item(0).getTextContent());

                    //TODO validar tags vacios
                    Long id = Long.valueOf(eElement.getElementsByTagName("id").item(0).getTextContent());
                    String name =  eElement.getElementsByTagName("name").item(0).getTextContent();
                    Long price = Long.valueOf(eElement.getElementsByTagName("price").item(0).getTextContent());
                    Long upc = Long.valueOf(eElement.getElementsByTagName("upc").item(0).getTextContent());

                    NodeList nListChild = eElement.getElementsByTagName("discount");

                    Long discountId = null;
                    Long value = null;
                    for (int temp2 = 0; temp2 < nListChild.getLength(); temp2++) {
                        Node nNodeChild = nListChild.item(temp2);
                        if (nNodeChild.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElementChild = (Element) nNodeChild;
                            log.info("Discount ID : " + eElementChild.getElementsByTagName("discountId").item(0).getTextContent());
                            log.info("value : " + eElementChild.getElementsByTagName("value").item(0).getTextContent());

                            discountId = Long.valueOf(eElementChild.getElementsByTagName("discountId").item(0).getTextContent());
                            value = Long.valueOf(eElementChild.getElementsByTagName("value").item(0).getTextContent());
                        }
                    }

                    //TODO esta construccion se puede mejorar
                    entity =ItemEntity.builder()
                            .itemId(id)
                            .price(price)
                            .name(name)
                            .upc(upc)
                            .discount(
                                    discountId!=null?DiscountEntity.builder()
                                            .discountId(discountId)
                                            .value(value)
                                            .build():null
                            )
                            .build();

                    lst.add(entity);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al obtener el XML");
        }


        return lst;
    }

    public String findProductNameById(Long productId){
        return jdbcItemRepository.findProductNameById(productId);
    }

    public ItemDto jdbcFindProductById(Long productId){
        return jdbcItemRepository.findProductById(productId);
    }

}
