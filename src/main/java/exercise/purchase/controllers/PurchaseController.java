package exercise.purchase.controllers;

import exercise.orders.dto.OrderDto;
import exercise.purchase.dto.PurchaseDto;
import exercise.purchase.services.PurchaseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/purchase/remote")
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseClient purchaseClient;

    @PostMapping(value = "/makePurchase")
    public PurchaseDto makePurchase(@RequestBody OrderDto dto){
        return purchaseClient.makePurchase(dto);
    }

    @GetMapping("/find/{purchaseId}")
    public PurchaseDto findPurchaseByPurchaseId(@PathVariable String purchaseId ){
        return purchaseClient.findPurchaseByPurchaseId(purchaseId);
    }
}
