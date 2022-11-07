package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.BaseGoodsDto;

import java.io.Serializable;
import java.util.List;

public class GoodsListRes implements Serializable {

    private int pageNum;

    private int pageSize;

    private int totalPage;

    private List<BaseGoodsDto> baseGoodsDtos;

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

    public List<BaseGoodsDto> getBaseGoodsDtos() {
        return baseGoodsDtos;
    }

    public void setBaseGoodsDtos(List<BaseGoodsDto> baseGoodsDtos) {
        this.baseGoodsDtos = baseGoodsDtos;
    }
}
