package com.telegram.coinmarketbot.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CoinModel {
    private Long id;
    private String name;
    private String symbol;
    private BigDecimal price;
    private Date lastUpdated;

}