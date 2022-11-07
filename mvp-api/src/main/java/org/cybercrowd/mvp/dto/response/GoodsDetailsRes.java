package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.ProgressGroupDto;
import org.cybercrowd.mvp.dto.request.DistributionRulesDto;
import org.cybercrowd.mvp.dto.request.GrouponRulesDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GoodsDetailsRes implements Serializable {

    private String taskId;

    //用户是否商品或者任务的发布者
    private boolean isOwner;

    private String ownerAddress;

    private String goodsNftAddress;
    //商品详细介绍
    private String goodsDetailsIntro;
    //商品简介
    private String goodsIntro;
    //商品标题
    private String goodsTitle;
    //商品销售价格
    private BigDecimal goodsSalePrice;
    //商品图片JSON
    private  String goodsImageJson;
    //DAO NO
    private String daoNo;
    //分销规则
    private DistributionRulesDto distributionRulesDto;
    //拼团规则
    private GrouponRulesDto grouponRulesDto;
    //进行中的团
    private List<ProgressGroupDto> progressGroupList;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getGoodsNftAddress() {
        return goodsNftAddress;
    }

    public void setGoodsNftAddress(String goodsNftAddress) {
        this.goodsNftAddress = goodsNftAddress;
    }

    public String getGoodsDetailsIntro() {
        return goodsDetailsIntro;
    }

    public void setGoodsDetailsIntro(String goodsDetailsIntro) {
        this.goodsDetailsIntro = goodsDetailsIntro;
    }

    public DistributionRulesDto getDistributionRulesDto() {
        return distributionRulesDto;
    }

    public void setDistributionRulesDto(DistributionRulesDto distributionRulesDto) {
        this.distributionRulesDto = distributionRulesDto;
    }

    public GrouponRulesDto getGrouponRulesDto() {
        return grouponRulesDto;
    }

    public void setGrouponRulesDto(GrouponRulesDto grouponRulesDto) {
        this.grouponRulesDto = grouponRulesDto;
    }

    public List<ProgressGroupDto> getProgressGroupList() {
        return progressGroupList;
    }

    public void setProgressGroupList(List<ProgressGroupDto> progressGroupList) {
        this.progressGroupList = progressGroupList;
    }

    public String getGoodsImageJson() {
        return goodsImageJson;
    }

    public void setGoodsImageJson(String goodsImageJson) {
        this.goodsImageJson = goodsImageJson;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public BigDecimal getGoodsSalePrice() {
        return goodsSalePrice;
    }

    public void setGoodsSalePrice(BigDecimal goodsSalePrice) {
        this.goodsSalePrice = goodsSalePrice;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public String getDaoNo() {
        return daoNo;
    }

    public void setDaoNo(String daoNo) {
        this.daoNo = daoNo;
    }
}
