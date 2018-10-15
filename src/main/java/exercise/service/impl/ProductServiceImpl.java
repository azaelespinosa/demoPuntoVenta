package exercise.service.impl;

import exercise.dto.DiscountDto;
import exercise.dto.ProductDto;
import exercise.model.DiscountEntity;
import exercise.model.ProductEntity;
import exercise.repository.ProductRepository;
import exercise.service.ProductService;
import exercise.util.ConvertUtil;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ConvertUtil convertUtil;


    /**
     * Metodo para guardar los productos a partir de un XML.
     * @param
     * @return
     */
    @Transactional
    public void saveProduct(){

        log.info("Method saveProduct iniciando.");

        List<ProductEntity> lstProducts = xmlToEntity();


        try {
            //TODO validar lista vacia
          productRepository.saveAll(lstProducts);

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al guardar los productos.");
        }


    }

    /**
     * Metodo para cargar un archivo XML y crear una lista entidades de producto.
     * @return List<ProductEntity>
     */
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



    /**
     * Metodo para construir la lista de entidades.
     * @param lstDto
     * @return List<ProductEntity>
     */
    private List<ProductEntity> buildProduct (List<ProductDto> lstDto){
        log.info("Method buildProduct iniciando.");
        List<ProductEntity> lstEntity = new ArrayList<>();

        try {
            //iteramos la lista
            lstDto.forEach(l -> {
                //Construimos la lista de entidades a persistir
                lstEntity.add(
                        buidlProductEntity(l)
                );
            });
        }catch (Exception e){
            throw new RuntimeException("Ocurrio un error al construir el objeto.");
        }

        return lstEntity;
    }

    /**
     * Metodo para guardar un producto via REST.
     * @param productDto
     * @return
     */
    @Transactional
    public void saveOneProduct(ProductDto productDto){

        log.info("Method saveOneProduct iniciando.");

        if (Optional.ofNullable(productDto).map(ProductDto::getName).map(t -> t == null).orElse(true)) {
            throw new RuntimeException("Favor de enviar un producto.");
        }

        try {

            productRepository.save(buidlProductEntity(productDto));

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al guardar el producto.");
        }


    }

    /**
     * Metodo para guardar un producto via REST.
     * @param productDto
     * @return
     */
    @Transactional
    public void updateProduct(ProductDto productDto){

        log.info("Method updateProduct iniciando.");

        if (Optional.ofNullable(productDto).map(ProductDto::getProductId).map(t -> t == null).orElse(true)) {
            throw new RuntimeException("Favor de enviar un producto.");
        }

        Optional<ProductEntity> opti = productRepository.findById(productDto.getProductId());

        //Valido que existe el resultado
        if(!opti.isPresent()){
            throw new RuntimeException("El producto "+productDto.getProductId()+" no existe .");
        }


        try {

            productRepository.save(buidlProductEntity(productDto));

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al guardar el producto.");
        }


    }

    /**
     * Metodo para busqueda de todos los productos.
     * @param
     * @return
     */

    public List<ProductDto> findAllProducts(){

        log.info("Method findAllProducts iniciando.");

        try {

            List<ProductEntity>  productLst = productRepository.findAll();

            //TODO validar lista vacia
            return enttityToDto(productLst);

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al buscar el producto.");
        }

    }

    /**
     * Metodo para busqueda de todos los productos.
     * @param
     * @return
     */

    public ProductDto findProductById(Long productId){

        log.info("Method findAll iniciando.");


            Optional<ProductEntity> opti = productRepository.findById(productId);

            //Valido que existe el resultado
            if(!opti.isPresent()){
                throw new RuntimeException("El producto "+productId+" no existe .");
            }

            //Construyo el objeto
            return buidlProductDto(opti.get());

    }

    /**
     * Metodo para busqueda de todos los productos.
     * @param
     * @return
     */

    public void deleteProduct(Long productId){

        log.info("Method deleteProduct iniciando.");

        Optional<ProductEntity> opti = productRepository.findById(productId);

        //Valido que existe el resultado
        if(!opti.isPresent()){
            throw new RuntimeException("El producto "+productId+" no existe");
        }

        try {

            productRepository.delete(opti.get());

        }catch(Exception e){
            throw new RuntimeException("Ocurrio un error al borrar el producto : "+ productId);
        }


    }

    /**
     * Metodo para mapeo de objetos complejos.
     * @param
     * @return
     */
    private List<ProductDto> enttityToDto(List<ProductEntity> productLst){
        return convertUtil.convert(productLst,ProductDto.class);
    }

    //TODO crear utilidades genericas de construccion de objetos.
    /**
     * Metodo para construir una entidad a partir de un DTO
     * @param productDto
     * @return ProductEntity
     */
    private ProductEntity buidlProductEntity(ProductDto productDto){
        return  ProductEntity.builder()
                .productId(productDto.getProductId())
                .price(productDto.getPrice())
                .name(productDto.getName())
                .upc(productDto.getUpc())

                .discount(
                        productDto.getDiscount()!=null?DiscountEntity.builder()
                                .discountId(productDto.getDiscount().getDiscountId())
                                .value(productDto.getDiscount().getValue())
                                .build():null
                )
                .build();
    }

    /**
     * Metodo para construir un DTO a partir de una Entidad
     * @param product
     * @return ProductDto
     */
    private ProductDto buidlProductDto(ProductEntity product){
        return  ProductDto.builder()
                .productId(product.getProductId())
                .price(product.getPrice())
                .name(product.getName())
                .upc(product.getUpc())

                .discount(
                        product.getDiscount()!=null?DiscountDto.builder()
                                .discountId(product.getDiscount().getDiscountId())
                                .value(product.getDiscount().getValue())
                                .build():null
                )
                .build();
    }

}
