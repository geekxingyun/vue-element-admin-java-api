package com.xingyun.vueelementadminjavaapi.framework.model.param;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @author qingfeng.zhao
 * @date 2022/4/29
 * @apiNote
 */
@NoArgsConstructor
@Data
public class PageInfo {
    //总记录数
    private int count;
    private int total ;   //总页数
    private int currentPage =1;   //当前页
    private int pageSize = 10;      //每页的数量
    private int offset = 0;

    public PageInfo(int count, Integer pageSize, Integer currentPage) {
        this.count = count;
        this.total = count % this.pageSize == 0 ? count / this.pageSize : count / this.pageSize + 1;
        if(pageSize != null && currentPage != null){
            this.count = count;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.total = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            this.offset = (currentPage-1)*pageSize;
        }
    }
}
