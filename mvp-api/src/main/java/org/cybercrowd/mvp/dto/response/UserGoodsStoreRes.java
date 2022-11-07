package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.GoodsStoreDto;

import java.io.Serializable;
import java.util.List;

public class UserGoodsStoreRes implements Serializable {

    List<GoodsStoreDto> goodsStoreDtos;

    private int pageNum;

    private int pageSize;

    private int totalPage;

    public List<GoodsStoreDto> getGoodsStoreDtos() {
        return goodsStoreDtos;
    }

    public void setGoodsStoreDtos(List<GoodsStoreDto> goodsStoreDtos) {
        this.goodsStoreDtos = goodsStoreDtos;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
