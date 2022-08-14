package com.xingyun.vueelementadminjavaapi.framework.model.vo;

import com.xingyun.vueelementadminjavaapi.framework.util.SnowFlakeUtils;
import lombok.Data;

import java.io.Serializable;

/***
 * @author qingfeng.zhao
 * @date 2022/4/24
 * @apiNote
 */
@Data
public class VueElementAdminResponse implements Serializable {
    private Integer code;
    private String message;
    private Object data;
    private String trackId= String.valueOf(SnowFlakeUtils.nextId());
}
