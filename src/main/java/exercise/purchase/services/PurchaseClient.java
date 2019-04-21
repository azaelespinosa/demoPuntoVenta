package exercise.purchase.services;

import exercise.orders.dto.OrderDto;
import exercise.purchase.dto.PurchaseDto;

public interface PurchaseClient {

    PurchaseDto makePurchase(OrderDto dto);

    PurchaseDto findPurchaseByPurchaseId(String purchaseId);
}
