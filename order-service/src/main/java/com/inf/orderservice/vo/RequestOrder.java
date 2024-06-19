package com.inf.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RequestOrder implements Serializable {

    private String productId;
    private Integer qty;
    private Integer unitPrice;

}
