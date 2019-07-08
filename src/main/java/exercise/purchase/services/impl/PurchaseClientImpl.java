package exercise.purchase.services.impl;

import exercise.aspects.RequestLog;
import exercise.aspects.Time;
import exercise.common.exceptions.CustomException;
import exercise.orders.dto.OrderDto;
import exercise.orders.service.OrderService;
import exercise.purchase.dto.PurchaseDetailDto;
import exercise.purchase.dto.PurchaseDto;
import exercise.purchase.services.PurchaseClient;
import exercise.purchase.services.client.PurchaseBaseClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Slf4j
@Service
public class PurchaseClientImpl extends PurchaseBaseClient implements PurchaseClient {

    @Autowired
    private RestTemplate purchaseRestTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private Environment env;


    @Time
    @RequestLog
    public PurchaseDto findPurchaseByPurchaseId(String purchaseId) {
        log.info("Method findPurchaseByPurchaseId - Sending request to Purchase API ");
        return purchaseRestTemplate.getForEntity(
                UriComponentsBuilder.fromPath("/purchase/find/{purchaseId}")
                        .buildAndExpand(purchaseId)
                        .toUriString(),
                PurchaseDto.class).getBody();
    }

    @Time
    @RequestLog
    public PurchaseDto makePurchase(OrderDto dto){
        Objects.requireNonNull(dto.getOrderId(),"El order id es requerido");
        PurchaseDto purchase = PurchaseDto.builder().orderId(dto.getOrderId()).build();
        if(generatePurchaseObject(purchase)){
            sendPurchase(purchase);
        }else{
            throw new RuntimeException("La venta no pudo ser generada.");
        }

        return purchase;
    }

    private boolean generatePurchaseObject(PurchaseDto dto){
        OrderDto orderDto = Optional.ofNullable(orderService.getOrderById(dto.getOrderId()))
                .orElseThrow(() -> new CustomException("El registro no existe"));

        try{
            dto.setPurchaseId(generatePurchaseId(dto.getOrderId()));
            dto.setUserId("Mostrador");
            dto.setTransNum(generateTransactionNumber());
            dto.setTotal(dto.getTotal());
            dto.setStatus(1);
            dto.setTransDate(new Date());
            dto.setClienteId(dto.getClienteId());
            dto.setPaymentForm("EFECTIVO");
            dto.setPaymentType("MXN");

            List<PurchaseDetailDto> detailLst = new ArrayList<>();
            orderDto.getOrderDetail().forEach(d -> {
                detailLst.add(PurchaseDetailDto.builder()
                        .purchaseItemId(generatePurchaseId(d.getOrderId()))
                        .purchaseId(dto.getPurchaseId())
                        .itemId(d.getItemId())
                        .price(d.getPrice())
                        .qty(d.getQty())
                        .build());
            });

            dto.setDetailItems(detailLst);


        }catch (Exception ex){
            return false;
        }

        return true;
    }



    private PurchaseDto sendPurchase(PurchaseDto dto) {

        HttpEntity httpEntity = createHttpEntity(dto,new Long(env.getProperty("api.key.config")));

        log.info("Method sendPurchase - Sending request to Purchase API ");
        return purchaseRestTemplate.exchange(UriComponentsBuilder.fromPath("/order/group/forceClose").toUriString()
                , HttpMethod.POST, httpEntity, PurchaseDto.class).getBody();

    }

    private String generateTransactionNumber() {
        return String.format("V%s%s"
                , RandomStringUtils.randomAlphanumeric(5).toUpperCase()
                , Calendar.getInstance().getTime().getTime()
        );
    }

    private String generatePurchaseId(long orderId) {

        return String.format("%s@%s"
                , Calendar.getInstance().getTime().getTime()
                , orderId
        );
    }

}
