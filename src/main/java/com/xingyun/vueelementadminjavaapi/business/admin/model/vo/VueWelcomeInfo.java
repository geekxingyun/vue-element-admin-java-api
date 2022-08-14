package com.xingyun.vueelementadminjavaapi.business.admin.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueWelcomeInfo implements Serializable {
    private String order_no;
    private Long timestamp;
    private String username;
    private double price;
    private String status;
}