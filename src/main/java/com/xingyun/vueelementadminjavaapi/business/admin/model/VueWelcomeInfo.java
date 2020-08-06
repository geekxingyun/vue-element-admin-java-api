package com.xingyun.vueelementadminjavaapi.business.admin.model;

import lombok.Data;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueWelcomeInfo {
    private String order_no;
    private Long timestamp;
    private String username;
    private double price;
    private String status;
}