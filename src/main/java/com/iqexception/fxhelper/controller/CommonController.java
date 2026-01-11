package com.iqexception.fxhelper.controller;

import com.iqexception.fxhelper.controller.dto.BaseRequest;
import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.common.*;
import com.iqexception.fxhelper.service.CommonService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class CommonController {
    private final CommonService commonService;

    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    @PostMapping("/get_signed_upload_url")
    public Response<SignUploadUrlResult> getSignedUploadUrl(@RequestBody @Valid BaseRequest request) {
        return commonService.getSignedUploadUrl(request);
    }

    @PostMapping("/msg_subscribe")
    public BaseResponse msgSubscribe(@RequestBody @Valid Request<MsgSubsParam> request) {
        return commonService.msgSubscribe(request);
    }

    @PostMapping("/get_upload_policy")
    public Response<GetUploadPolicyResult> getUploadPolicy(@RequestBody @Valid BaseRequest request){
        return commonService.getUploadPolicy(request);
    }




}
