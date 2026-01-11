package com.iqexception.fxhelper.service.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.iqexception.fxhelper.config.OssConfig;
import com.iqexception.fxhelper.controller.dto.BaseRequest;
import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.common.GetUploadPolicyResult;
import com.iqexception.fxhelper.controller.dto.common.MsgSubsParam;
import com.iqexception.fxhelper.controller.dto.common.SignUploadUrlResult;
import com.iqexception.fxhelper.dal.ext.FxWxMsgSubsExtDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxWxMsgSubs;
import com.iqexception.fxhelper.service.CommonService;
import com.iqexception.fxhelper.service.builder.MsgSubsBuilder;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.support.TLVarManager;
import com.iqexception.fxhelper.util.CommonUtil;
import com.iqexception.fxhelper.wx.WxApi;
import org.jooq.DSLContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.iqexception.fxhelper.support.Constants.BUCKET_NAME;

@Service
public class CommonServiceImpl extends BaseService implements CommonService {

    private static final long EXPIRE_TIME = 60 * 1000L;
    private final OSS ossClient;

    private final OssConfig ossConfig;

    private FxWxMsgSubsExtDao wxMsgSubsExtDao;

    public CommonServiceImpl(MessageSource messageSource,
                             WxApi wxApi,
                             DSLContext jooqClient,
                             JsonMapper jsonMapper,
                             OssConfig ossConfig,
                             FxWxMsgSubsExtDao wxMsgSubsExtDao) {
        super(messageSource, wxApi, jooqClient, jsonMapper);
        this.ossConfig = ossConfig;
        this.ossClient = ossConfig.getOssClient();
        this.wxMsgSubsExtDao = wxMsgSubsExtDao;
    }

    @Override
    public Response<SignUploadUrlResult> getSignedUploadUrl(BaseRequest request) {

        GeneratePresignedUrlRequest signRequest = new GeneratePresignedUrlRequest(
                BUCKET_NAME,
                CommonUtil.getObjectName(TLVarManager.getCurrentUser().getUserId()),
                HttpMethod.PUT);
        Date expiration = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        signRequest.setExpiration(expiration);
//        signRequest.setContentType("image/png;");
        URL signedUrl = ossClient.generatePresignedUrl(signRequest);

        return new Response<>(new SignUploadUrlResult(signedUrl.toString()));

    }

    @Override
    public BaseResponse msgSubscribe(Request<MsgSubsParam> request) {

        FxWxMsgSubs wxMsgSubs = wxMsgSubsExtDao.fetchByKey(TLVarManager.getCurrentUser().getOpenId(),
                request.getParam().getTemplateId());
        if (wxMsgSubs == null) {
            wxMsgSubs = MsgSubsBuilder.build(TLVarManager.getCurrentUser().getOpenId(),
                    request.getParam().getTemplateId());
            wxMsgSubsExtDao.insert(wxMsgSubs);
        } else {
            wxMsgSubsExtDao.incrCount(wxMsgSubs.getId());
        }
        return new BaseResponse();
    }

    @Override
    public Response<GetUploadPolicyResult> getUploadPolicy(BaseRequest request) {

        long expireTime = System.currentTimeMillis() + 60 * 1000;

        PolicyConditions policyCons = new PolicyConditions();
        policyCons.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 8 * 1024 * 1024);
        String key = CommonUtil.getObjectName(TLVarManager.getCurrentUser().getUserId());
        policyCons.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, key);
        policyCons.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_CONTENT_TYPE, "image");


        String postPolicy = ossClient.generatePostPolicy(new Date(expireTime), policyCons);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        GetUploadPolicyResult result = new GetUploadPolicyResult();
        result.setAccessId(ossConfig.getAccessKeyId());
        result.setKey(key);
        result.setPolicy(encodedPolicy);
        result.setSignature(postSignature);
        result.setHost("https://" + BUCKET_NAME + "." + OssConfig.endpoint);
        result.setExpire(String.valueOf(expireTime / 1000));

        return new Response<>(result);


    }


}
