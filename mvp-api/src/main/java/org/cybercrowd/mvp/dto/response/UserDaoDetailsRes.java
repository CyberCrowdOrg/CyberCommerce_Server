package org.cybercrowd.mvp.dto.response;

import org.cybercrowd.mvp.dto.BaseDaoNftAssetsDto;
import org.cybercrowd.mvp.dto.DaoViewRootDto;
import org.cybercrowd.mvp.dto.BaseDaoDto;
import org.cybercrowd.mvp.dto.DaoProposalDto;

import java.io.Serializable;
import java.util.List;

public class UserDaoDetailsRes implements Serializable {

    private BaseDaoDto userDaoDto;

    private String grouponRules;

    private String distributionRules;

    private DaoViewRootDto daoViewRootDto;

    private List<DaoProposalDto> daoProposalList;

    private List<BaseDaoNftAssetsDto> daoNftAssetsDtoList;

    private int viewLayers;

    private String nftAssetsLink;


    public String getGrouponRules() {
        return grouponRules;
    }

    public void setGrouponRules(String grouponRules) {
        this.grouponRules = grouponRules;
    }

    public String getDistributionRules() {
        return distributionRules;
    }

    public void setDistributionRules(String distributionRules) {
        this.distributionRules = distributionRules;
    }

    public DaoViewRootDto getDaoViewRootDto() {
        return daoViewRootDto;
    }

    public void setDaoViewRootDto(DaoViewRootDto daoViewRootDto) {
        this.daoViewRootDto = daoViewRootDto;
    }

    public List<DaoProposalDto> getDaoProposalList() {
        return daoProposalList;
    }

    public void setDaoProposalList(List<DaoProposalDto> daoProposalList) {
        this.daoProposalList = daoProposalList;
    }

    public int getViewLayers() {
        return viewLayers;
    }

    public void setViewLayers(int viewLayers) {
        this.viewLayers = viewLayers;
    }

    public List<BaseDaoNftAssetsDto> getDaoNftAssetsDtoList() {
        return daoNftAssetsDtoList;
    }

    public void setDaoNftAssetsDtoList(List<BaseDaoNftAssetsDto> daoNftAssetsDtoList) {
        this.daoNftAssetsDtoList = daoNftAssetsDtoList;
    }

    public String getNftAssetsLink() {
        return nftAssetsLink;
    }

    public void setNftAssetsLink(String nftAssetsLink) {
        this.nftAssetsLink = nftAssetsLink;
    }

    public BaseDaoDto getUserDaoDto() {
        return userDaoDto;
    }

    public void setUserDaoDto(BaseDaoDto userDaoDto) {
        this.userDaoDto = userDaoDto;
    }
}
