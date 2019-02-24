package exercise.service.impl;

import exercise.aspects.RequestLog;
import exercise.aspects.Time;
import exercise.common.exceptions.CustomException;
import exercise.common.services.BaseService;
import exercise.dto.ProductDto;
import exercise.model.DiscountEntity;
import exercise.model.ProductEntity;
import exercise.repository.ProductRepository;
import exercise.service.ProductService;
import lombok.extern.slf4j.Slf4j;
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
public class ProductServiceImpl extends BaseService<ProductRepository,ProductEntity > implements ProductService {

    /**
     * Metodo para guardar un producto via REST.
     * @param productDto
     * @return
     */
    @Time
    @RequestLog
    @Transactional
    public ProductDto saveOneProduct(ProductDto productDto){

        log.info("Method saveOneProduct iniciando.");

        if (Optional.ofNullable(productDto).map(ProductDto::getName).map(t -> t == null).orElse(true)) {
            throw new RuntimeException("Favor de enviar un producto.");
        }

        try {
            return create(productDto,ProductDto.class);

        }catch(Exception e){
            throw new CustomException("Ocurrio un error al guardar el producto.");
        }


    }

    /**
     * Metodo para guardar un producto via REST.
     * @param productDto
     * @return
     */
    @Time
    @RequestLog
    @Transactional
    public ProductDto updateProduct(ProductDto productDto){

        log.info("Method updateProduct iniciando.");

        if (Optional.ofNullable(productDto).map(ProductDto::getProductId).map(t -> t == null).orElse(true)) {
            throw new RuntimeException("Favor de enviar un producto.");
        }

        try {

            return update(productDto.getProductId(),productDto,ProductDto.class);

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
    public List<ProductDto> findAllProducts(){

        log.info("Method findAllProducts iniciando.");

        try {

            return findAll(ProductDto.class);

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
    public ProductDto findProductById(Long productId){

        log.info("Method findProductById iniciando.");

        try {

            return findById(productId,ProductDto.class);

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

        List<ProductEntity> lstProducts = xmlToEntity();

        try {
            //TODO validar lista vacia
            repository.saveAll(lstProducts);

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al guardar los productos.");
        }


    }

    /**
     * Metodo para cargar un archivo XML y crear una lista entidades de producto.
     * @return List<ProductEntity>
     */
    @Time
    @RequestLog
    private List<ProductEntity> xmlToEntity(){
        log.info("Method xmlToEntity iniciando.");
        List<ProductEntity> lst = new ArrayList<>();
        ProductEntity entity=null;
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
                    entity =ProductEntity.builder()
                            .productId(id)
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


}
