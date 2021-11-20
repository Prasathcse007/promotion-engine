package com.ecom.controller;


import com.ecom.bo.ItemRequest;
import com.ecom.bo.OrderRequest;
import com.ecom.bo.OrderResponse;
import com.ecom.bo.PromotionRequest;
import com.ecom.service.PromotionEngineService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1")
public class PromotionEngineController {

    @Autowired
    private PromotionEngineService promotionEngineService;

    @GetMapping(value = "/")
    public String index() {
        return "Alive";
    }

    @PostMapping(value = "/order")
    @ApiOperation(value = "/order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public ResponseEntity<OrderResponse> order(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(promotionEngineService.order(orderRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/addPromotion")
    @ApiOperation(value = "/addPromotion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public ResponseEntity<String> addPromotion(@RequestBody PromotionRequest promotionRequest) {
        return new ResponseEntity<>(promotionEngineService.addPromotion(promotionRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/addItem")
    @ApiOperation(value = "/addItem")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public ResponseEntity<String> addItem(@RequestBody ItemRequest itemRequest) {
        return new ResponseEntity<>(promotionEngineService.addItem(itemRequest), HttpStatus.OK);
    }
}
