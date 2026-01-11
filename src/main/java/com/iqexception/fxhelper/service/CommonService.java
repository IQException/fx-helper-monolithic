package com.iqexception.fxhelper.service;

import com.iqexception.fxhelper.controller.dto.BaseRequest;
import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.common.GetUploadPolicyResult;
import com.iqexception.fxhelper.controller.dto.common.MsgSubsParam;
import com.iqexception.fxhelper.controller.dto.common.SignUploadUrlResult;

public interface CommonService {
    Response<SignUploadUrlResult> getSignedUploadUrl(BaseRequest request);

    BaseResponse msgSubscribe(Request<MsgSubsParam> request);

    Response<GetUploadPolicyResult> getUploadPolicy(BaseRequest request);
}
