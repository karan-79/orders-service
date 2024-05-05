package com.fooddelivery.orders.api.web.v1;

import com.fooddelivery.orders.api.web.v1.model.APIBag;
import com.fooddelivery.orders.api.web.v1.model.APIBagItem;
import com.fooddelivery.orders.api.web.v1.model.APICheckoutDetails;
import com.fooddelivery.orders.api.web.v1.model.APIOrder;
import com.fooddelivery.orders.service.UserBagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/bag")
@RequiredArgsConstructor
public class UserBagController {

    private final UserBagService userBagService;

    @GetMapping
    public APIBag getItemsInBag(UUID userId) {
        return userBagService.getUserBag(userId);
    }

    @PostMapping
    public APIBagItem addItemInBag(@RequestBody APIBagItem item, UUID userId) {
       return userBagService.insertItem(userId, item);
    }

    @PutMapping
    public void updateItemInTheBag(APIBagItem item, UUID userId) {
        userBagService.updateItem(item, userId);
    }

    @GetMapping("/placeorder/")
    public List<APIOrder> checkout(UUID userId, APICheckoutDetails checkoutDetails){
        return userBagService.checkout(userId, checkoutDetails);
    }
}
