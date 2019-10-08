package com.niko.prokat.Controller.rest;

import com.niko.prokat.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;

    @PutMapping("/admin/status/{newStatus}/tool/{id}")
    public ResponseEntity changeOrderStatus(@PathVariable String newStatus,
                                            @PathVariable Long id){
        orderService.changeOrderStatus(id,newStatus);
        return new ResponseEntity<>("Статус заказа изменён", HttpStatus.OK);
    }
}
