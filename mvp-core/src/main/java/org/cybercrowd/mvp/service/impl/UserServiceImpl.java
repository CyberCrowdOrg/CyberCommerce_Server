package org.cybercrowd.mvp.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.constant.RedisKeyConstant;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.LoginBindDto;
import org.cybercrowd.mvp.dto.SocialAccountDto;
import org.cybercrowd.mvp.dto.request.UserLoginBindReq;
import org.cybercrowd.mvp.dto.request.UserLoginReq;
import org.cybercrowd.mvp.dto.response.UserLoginRes;
import org.cybercrowd.mvp.dto.response.UserNftAssetsRes;
import org.cybercrowd.mvp.dto.response.UserPersonalRes;
import org.cybercrowd.mvp.dto.response.UserWalletBalanceRes;
import org.cybercrowd.mvp.enums.*;
import org.cybercrowd.mvp.mapper.*;
import org.cybercrowd.mvp.model.*;
import org.cybercrowd.mvp.service.UserService;
import org.cybercrowd.mvp.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${wallet.file-path}")
    private String  walletFilePath;

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserWalletInfoMapper userWalletInfoMapper;
    @Autowired
    UserNftAssetsMapper userNftAssetsMapper;
    @Autowired
    BaseCoinMapper baseCoinMapper;
    @Autowired
    RegisterWalletInfoMapper registerWalletInfoMapper;
    @Autowired
    UserLoginBindMapper userLoginBindMapper;

    @Autowired
    CommonService commonService;

    @Override
    public BaseResponse userLogin(UserLoginReq userLoginReq) {
        BaseResponse baseResponse = new BaseResponse();
        SocialAccountDto socialAccountDto = null;
        try{
            //获取第三方登录账号
            baseResponse = getLoginAccount(userLoginReq.getLoginType(),userLoginReq.getUserOrigin(),
                    userLoginReq.getLoginKey(),userLoginReq.getRedirectUri());
            if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
                logger.info("用户登录业务,请求第三方登录授权用户信息失败");
                return baseResponse;
            }
            if(null != baseResponse.getData()){
                socialAccountDto = (SocialAccountDto)baseResponse.getData();
                userLoginReq.setAccount(socialAccountDto.getAccount());
            }

            UserInfo userInfo = null;

            //查询登录方式是否绑定了用户
            UserLoginBind userLoginBind = new UserLoginBind();
            userLoginBind.setLoginType(userLoginReq.getLoginType());
            userLoginBind.setUserOrigin(userLoginReq.getUserOrigin());
            userLoginBind.setLoginAccount(userLoginReq.getAccount());
            userLoginBind = userLoginBindMapper.selectUserLoginBind(userLoginBind);

            if(null != userLoginBind){
                //查询用户信息
                Long userId = userLoginBind.getUserId();
                userInfo = userInfoMapper.queryUserByUserId(userId);
            }

            //更新或者保存用户信息
            userInfo = updateOrSaveUser(userLoginReq, userInfo,socialAccountDto);

//            userLoginBind.setLastLoginTime(new Date());
//            userLoginBindMapper.updateByPrimaryKeySelective(userLoginBind);

            UserLoginRes userLoginRes = new UserLoginRes();
            userLoginRes.setNickName(userInfo.getUserNickName());
            userLoginRes.setUserId(userInfo.getUserId().toString());
            BeanUtils.copyProperties(userInfo, userLoginRes);

            //用户登录方式绑定状态
            List<LoginBindDto> loginBindDtos = userLoginBindMapper.userLoginBindList(userInfo.getUserId());

            userLoginRes.setLoginBindList(loginBindDtos);

            baseResponse.setData(userLoginRes);
        }catch (Exception e){
            logger.error("保存用户信息失败:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse userPersonal(Long userId) {
        BaseResponse baseResponse = new BaseResponse();
        UserPersonalRes userPersonalRes = new UserPersonalRes();
        try {
            //个人信息
            UserInfo userInfo = userInfoMapper.queryUserByUserId(userId);
            userPersonalRes.setUserId(userId.toString());
            userPersonalRes.setUserOrigin(userInfo.getUserOrigin());
            userPersonalRes.setUserAvatar(userInfo.getUserAvatar());
            userPersonalRes.setNickName(userInfo.getUserNickName());
            userPersonalRes.setCreditAmount(userInfo.getCreditAmount());
            userPersonalRes.setCreditScore(userInfo.getCreditScore());
            //用户钱包信息
            List<UserWalletBalanceRes> userWalletBalanceResList = userWalletInfoMapper.userWalletBalanceList(userId);
            userPersonalRes.setWalletBalanceResList(userWalletBalanceResList);
            //用户NFT持有信息
            List<UserNftAssetsRes> userNftAssetsResList = userNftAssetsMapper.userNftAssetsList(userId);
            userPersonalRes.setNftAssetsResList(userNftAssetsResList);

            //用户登录方式绑定状态
            List<LoginBindDto> loginBindDtos = userLoginBindMapper.userLoginBindList(userId);
            userPersonalRes.setLoginBindList(loginBindDtos);

            baseResponse.setData(userPersonalRes);
            logger.info("用户个人中心数据查询业务,响应结果:{}", JSON.toJSONString(baseResponse));
        }catch (Exception e){
            logger.error("用户个人中心数据查询业务,执行异常:{}",e.getMessage(),e);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_FAILED);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse userLoginBind(UserLoginBindReq userLoginBindReq) {
        BaseResponse baseResponse = new BaseResponse();
        SocialAccountDto socialAccountDto = null;
        Long userId = userLoginBindReq.getUserId();
        //获取第三方登录账号
        baseResponse = getLoginAccount(userLoginBindReq.getLoginType(),userLoginBindReq.getUserOrigin(),
                userLoginBindReq.getLoginKey(),userLoginBindReq.getRedirectUri());
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            logger.info("用户登录业务,请求第三方登录授权用户信息失败");
            return baseResponse;
        }
        if(null != baseResponse.getData()){
            socialAccountDto = (SocialAccountDto)baseResponse.getData();
            userLoginBindReq.setAccount(socialAccountDto.getAccount());
        }

        //检查用户同一登录方式是否已绑定了账号
        UserLoginBind userLoginBind2 = new UserLoginBind();
        userLoginBind2.setUserId(userId);
        userLoginBind2.setUserOrigin(userLoginBindReq.getUserOrigin());
        userLoginBind2.setBindStatus("1");
        userLoginBind2 = userLoginBindMapper.selectUserLoginBind(userLoginBind2);
        if(null != userLoginBind2){
            logger.info("用户登录账号绑定业务,当前账号:{} 绑定失败,当前登录方式已存在绑定账号",userLoginBindReq.getAccount());
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.USER_LGOIN_BIND_ERROR2);
            return baseResponse;
        }

        //查询登录账号是否被绑定
        UserLoginBind userLoginBind = new UserLoginBind();
        userLoginBind.setLoginType(userLoginBindReq.getLoginType());
        userLoginBind.setUserOrigin(userLoginBindReq.getUserOrigin());
        userLoginBind.setLoginAccount(userLoginBindReq.getAccount());
        userLoginBind = userLoginBindMapper.selectUserLoginBind(userLoginBind);

        if(null != userLoginBind
                && "1".equals(userLoginBind.getBindStatus())){
            //账号已被绑定
            Long bindUserId = userLoginBind.getUserId();
            if(null != bindUserId && bindUserId.intValue() != userId){
                //不是被自己绑定的，提示账号已被其他用户绑定
                logger.info("用户登录账号绑定业务,当前账号:{} 绑定失败,已被其他用户绑定",userLoginBindReq.getAccount());
                baseResponse.setReturnCodeEnum(ReturnCodeEnum.USER_LOGIN_BIND_ERROR);
                return baseResponse;
            }else{
                //自己绑定的,返回成功即可
                logger.info("用户登录账号绑定业务,当前账号:{} 自己已经绑定了,直接返回成功",userLoginBindReq.getAccount());
                List<LoginBindDto> loginBindDtos = userLoginBindMapper.userLoginBindList(userId);
                baseResponse.setData(loginBindDtos);
                return baseResponse;
            }
        }else {
            if(null == userLoginBind){
                userLoginBind = new UserLoginBind();
            }
            //未绑定，更新绑定数据
            userLoginBind.setUserId(userId);
            userLoginBind.setLoginAccount(userLoginBindReq.getAccount());
            userLoginBind.setLoginType(userLoginBindReq.getLoginType());
            userLoginBind.setUserOrigin(userLoginBindReq.getUserOrigin());
            userLoginBind.setUpdateTime(new Date());
            userLoginBind.setBindStatus("1");

            if(null != socialAccountDto && !StringUtils.isEmpty(socialAccountDto.getUserAvatar())){
                userLoginBind.setUserAvatar(socialAccountDto.getUserAvatar());
            }

            if(null != userLoginBind.getId()) {
                userLoginBindMapper.updateByPrimaryKeySelective(userLoginBind);
            }else {
                userLoginBindMapper.insertSelective(userLoginBind);
            }
            List<LoginBindDto> loginBindDtos = userLoginBindMapper.userLoginBindList(userId);
            baseResponse.setData(loginBindDtos);
        }
        return baseResponse;
    }

    private UserInfo updateOrSaveUser(UserLoginReq userLoginReq, UserInfo userInfo,SocialAccountDto socialAccountDto) throws Exception {
        if(null == userInfo){
            //创建用户
            userInfo = createUser(userLoginReq);
            if(UserLoginTypeEnum.WEB2.getCode().equals(userLoginReq.getLoginType())){
                if(!StringUtils.isEmpty(socialAccountDto.getNickName())){
                    userInfo.setUserNickName(socialAccountDto.getNickName());
                }
                if(!StringUtils.isEmpty(socialAccountDto.getUserAvatar())){
                    userInfo.setUserAvatar(socialAccountDto.getUserAvatar());
                }
                //注册钱包
                RegisterWalletInfo registerWalletInfo = createWallet();
                registerWalletInfo.setUserId(userInfo.getUserId());
                //保存钱包信息
                userInfo.setUserWalletAddr(registerWalletInfo.getWalletAddr());
                registerWalletInfoMapper.insertSelective(registerWalletInfo);
            }else{
                userInfo.setUserWalletAddr(userLoginReq.getAccount());
            }
            //创建用户钱包数据
            createUserWallet(userInfo);
            //保存用户信息
            userInfoMapper.insert(userInfo);
            //用户登录方式绑定数据
            bindUserLogin(userInfo.getUserId(),userInfo.getUserOrigin(),
                    userInfo.getLoginType(),null !=socialAccountDto ? socialAccountDto.getUserAvatar():"",userLoginReq.getAccount());
        }
        userInfo.setUserOrigin(userLoginReq.getUserOrigin());
        userInfo.setLoginType(userLoginReq.getLoginType());

        //更新loginToken和最后登录时间
        String token = RedisKeyConstant.USER_SESSION_ID + userInfo.getUserId() + ":" + IdUtil.fastSimpleUUID();
        userInfo.setLoginToken(token);
        userInfo.setLastLoginTime(new Date());
        userInfoMapper.updateByPrimaryKey(userInfo);
        return userInfo;
    }

    private void bindUserLogin(Long userId,String userOrigin,String loginType,String userAvatar,String loginAccount){
        UserLoginBind userLoginBind = new UserLoginBind();
        userLoginBind.setUserId(userId);
        userLoginBind.setUserAvatar(userAvatar);
        userLoginBind.setBindStatus("1");
        userLoginBind.setLoginType(loginType);
        userLoginBind.setUserOrigin(userOrigin);
        userLoginBind.setLoginAccount(loginAccount);
        userLoginBind.setLastLoginTime(new Date());
        userLoginBind.setCreateTime(new Date());
        userLoginBind.setUpdateTime(new Date());
        userLoginBindMapper.insertSelective(userLoginBind);
    }

    private List<LoginBindDto> loginBindHandle(List<LoginBindDto> loginBindDtos) {
        List<String> userOriginList = new ArrayList<>();
        List<LoginBindDto> loginBindDtoList = new ArrayList<>();
        userOriginList.add("facebook");
        userOriginList.add("twitter");
        userOriginList.add("google");

        userOriginList.add("metamask");
        userOriginList.add("coinbase");
        userOriginList.add("walletconnect");

        Map<String,LoginBindDto> loginBindDtoMap = new HashMap<>();

        for(LoginBindDto loginBindDto:loginBindDtos){
            loginBindDtoMap.put(loginBindDto.getUserOrigin(),loginBindDto);
        }

        for(String userOrigin: userOriginList){
            LoginBindDto loginBindDto = loginBindDtoMap.get(userOrigin);
            if(null != loginBindDto){
                loginBindDtoList.add(loginBindDto);
            }else {
                loginBindDto = new LoginBindDto();
                loginBindDto.setBindStatus("0");
                loginBindDto.setUserOrigin(userOrigin);

                if(userOrigin.equals("facebook") || userOrigin.equals("twitter") || userOrigin.equals("google")){
                    loginBindDto.setLoginType("WEB2");
                }else{
                    loginBindDto.setLoginType("WEB3");
                }
                loginBindDtoList.add(loginBindDto);
            }
        }
        return loginBindDtoList;
    }

    private void createUserWallet(UserInfo userInfo) {
        //查询基础币种信息
        List<BaseCoin> baseCoins = baseCoinMapper.selectDefaultWalletCoinList();
        for(BaseCoin baseCoin:baseCoins){
            UserWalletInfo userWalletInfo = new UserWalletInfo();
            userWalletInfo.setUserId(userInfo.getUserId());
            userWalletInfo.setWalletAddr(userInfo.getUserWalletAddr());
            userWalletInfo.setCoinId(baseCoin.getCoinId());
            userWalletInfo.setCoinName(baseCoin.getCoinName());
            userWalletInfo.setTotalInAmt(new BigDecimal(0));
            userWalletInfo.setTotalOutAmt(new BigDecimal(0));
            userWalletInfo.setTotalRechargeAmt(new BigDecimal(0));
            userWalletInfo.setAvailableAmt(new BigDecimal(0));
            userWalletInfo.setFrozenAmt(new BigDecimal(0));
            userWalletInfo.setTotalWithdrawalAmt(new BigDecimal(0));
            userWalletInfo.setAccountStatus(WalletAccountStatusEnum.NORMAL.getCode());
            userWalletInfo.setCreateTime(new Date());
            userWalletInfo.setUpdateTime(new Date());
            userWalletInfoMapper.insertSelective(userWalletInfo);
        }
    }

    private RegisterWalletInfo createWallet() throws Exception{
        RegisterWalletInfo registerWalletInfo = new RegisterWalletInfo();
        // 第一个参数是密码,第二个参数是钱包存放的路径
        Bip39Wallet wallet = WalletUtils.generateBip39Wallet("", new File(walletFilePath));
        // 生成12个单词的助记词
        String memorizingWords = wallet.getMnemonic();
        // 通过钱包密码与助记词获得钱包地址、公钥及私钥信息
        Credentials credentials222 = WalletUtils.loadBip39Credentials("", wallet.getMnemonic());
        String address = credentials222.getAddress();
        String publicKey = credentials222.getEcKeyPair().getPublicKey().toString(16);

        BigInteger privkeyBi = credentials222.getEcKeyPair().getPrivateKey();
        BigInteger bi = new BigInteger(privkeyBi.toString());
        String privKey = Numeric.toHexStringWithPrefix(bi);

        registerWalletInfo.setCreateTime(new Date());
        registerWalletInfo.setWalletAddr(address);
        registerWalletInfo.setPrivateKey(privKey);
        registerWalletInfo.setMnemonic(memorizingWords);
//        registerWalletInfo.setWalletType("");
        registerWalletInfo.setPublicKey(publicKey);
        registerWalletInfo.setUpdateTime(new Date());
        return registerWalletInfo;
    }

    private UserInfo createUser(UserLoginReq userLoginReq) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(IDUtils.getUserId());
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        userInfo.setUserAvatar("https://s3.ap-southeast-1.amazonaws.com/cybercrowd.bucket/mvp/useravatar/default.jpeg");
        userInfo.setUserNickName(userInfo.getUserId().toString());
        userInfo.setLoginType(userLoginReq.getLoginType());
        userInfo.setLoginAccount(userLoginReq.getAccount());
        userInfo.setUserOrigin(userLoginReq.getUserOrigin());
        userInfo.setCreditAmount(0l);
        userInfo.setCreditScore(0l);
        userInfo.setUserStatus(UserStatusEnum.NORMAL.getCode());
        return userInfo;
    }


    private BaseResponse getLoginAccount(String loginType,String userOrigin,String loginKey,String redirectUri){
        BaseResponse baseResponse = new BaseResponse();
        if(UserLoginTypeEnum.WEB2.getCode().equals(loginType)){
            //获取账号
            if(UserOriginEnum.TWITTER.getCode().equals(userOrigin)){
                //获取twitter 用户信息
                baseResponse = commonService.getTwitterAccount(loginKey,redirectUri);
            }else if(UserOriginEnum.GOOGLE.getCode().equals(userOrigin)){
                baseResponse = commonService.getGoogleAccount(loginKey,redirectUri);
            }else if(UserOriginEnum.FACEBOOK.getCode().equals(userOrigin)){
                baseResponse = commonService.getFaceBookAccount(loginKey,redirectUri);
            }
        }else {
        }
        return baseResponse;
    }

}
