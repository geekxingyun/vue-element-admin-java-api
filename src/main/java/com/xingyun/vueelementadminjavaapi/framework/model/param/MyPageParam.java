package com.xingyun.vueelementadminjavaapi.framework.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class MyPageParam implements Serializable {
    private static final long serialVersionUID = -4878507903600498936L;
    /**
     * 第几页
     */
    private Integer page=1;
    /**
     * 每页多少条记录
     */
    private Integer limit=10;
    ////////////////////////////////////////
    /**
     * 数据库请求第几页开始
     */
    private Integer start;
    /**
     * 数据库请求每页多少行
     */
    private Integer length;

    /***
     * 排序字段
     */
    private String sort;

    /**
     * MyBatis 需要我们自己计算
     */
    public void initMyBatisPageParam(){
        if(null==this.limit){
            this.limit=10;
        }
        this.setLength(this.getLimit());
        if(null==this.getPage()||this.getPage()<=0){
            this.setStart(0);
        }else{
            this.setStart((this.getPage()-1)*this.getLimit());
        }
    }

    /**
     * Spring Data JPA 会自动帮我们计算
     */
    public void initJpaPageParam(){
        if(null==this.limit){
            this.limit=10;
        }
        this.setLength(this.getLimit());
        if(null==this.getPage()||this.getPage()<=0){
            this.setStart(0);
        }else{
            this.setStart((this.getPage()-1));
        }
    }
}
