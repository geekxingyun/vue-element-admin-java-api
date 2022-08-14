package com.xingyun.vueelementadminjavaapi.framework.model.vo;

import lombok.Data;

import java.util.List;

/***
 * 分页对象适配器 适配JPA返回的分页对象和Mybatis的分页对象
 * @author qingfeng.zhao
 * @date 2022/4/27
 * @apiNote
 */
@Data
public class BackPageAdapterVO<T>{
    /**
     * 总页数
     */
    private long totalPages;
    /**
     * 总记录数
     */
    private long totalElements;
    /**
     * 该方法直接返回每页的大小，与实际含有多少元素无关
     */
    private long size;
    /**
     * 该方法返回实际含有多少元素，最小为0，最大为每页最大的元素的数量。
     */
    private long numberOfElements;
    /**
     * 内容
     */
    private List<T> content;
}
