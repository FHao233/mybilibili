package com.fhao.domain;

import java.util.List;

/**
 * author: FHao
 * create time: 2023-03-19 20:58
 * description:
 */
public class PageResult<T> {
    private Integer total;
    private List<T> list;

    public PageResult(Integer total,List<T> list){
        this.list = list;
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
