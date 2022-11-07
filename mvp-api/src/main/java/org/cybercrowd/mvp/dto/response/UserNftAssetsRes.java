package org.cybercrowd.mvp.dto.response;

import java.io.Serializable;

public class UserNftAssetsRes implements Serializable {

    private String contractAddr;

    private String nftNo;

    private Long nftNumber;

    private String nftName;

    public String getContractAddr() {
        return contractAddr;
    }

    public void setContractAddr(String contractAddr) {
        this.contractAddr = contractAddr;
    }

    public String getNftNo() {
        return nftNo;
    }

    public void setNftNo(String nftNo) {
        this.nftNo = nftNo;
    }

    public Long getNftNumber() {
        return nftNumber;
    }

    public void setNftNumber(Long nftNumber) {
        this.nftNumber = nftNumber;
    }

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }
}
