import { BaseResponse, ResponseStatus } from "@/api/backend";
import Taro, { TabBarItem } from "@tarojs/taro";
import moment from "moment";
import common from "@/service/common";

const navBack = () => Taro.navigateBack();

const success = (ret?: BaseResponse): boolean => ret?.status.errorCode == "0";
const ok = (ret: Taro.login.SuccessCallbackResult) =>
  ret.errMsg.endsWith(":ok");

const formatTime = (time: string | number) =>
  moment(time).format("YYYY-MM-DD HH:mm:ss");

const formatHHmm = (time: string | number) =>
  moment(time).format("YYYY-MM-DD HH:mm");

const formatDate = (time: string | number) => moment(time).format("YYYY-MM-DD");

const minus24H = (time: Date) => new Date(time.getTime() - 24 * 3600 * 1000);

const minus7D = (time: Date) => new Date(time.getTime() - 7 * 24 * 3600 * 1000);

const failureToast = (status?: ResponseStatus) => {
  Taro.showToast({
    title: status && status.errorMessage ? status.errorMessage : "服务请求异常",
    icon: "error",
    duration: 2000,
  });
};
const validatePhone = (phone: string): boolean => {
  const phoneRegex = /^1[3-9]\d{9}$/;
  return phoneRegex.test(phone);
};

const switchTab = (tabbar: TabBarItem[], index: number) => {
  Taro.reLaunch({ url: tabbar[index].pagePath });
};

const getQueryParams = (url) => {
  const sUrl = url.split("?");
  // 取最后一位，兼容全链接有？和纯参数无？
  const sParams = sUrl[sUrl.length - 1];
  const arr = sParams.split("&"); // ['a=1', 'b=2']
  const result = {};
  arr.forEach((item) => {
    const keyVal = item.split("=");
    // key值
    const key = keyVal.shift();
    // value值，兼容参数没encode时有=，例如'a=b=1' => [a, b, 1] => key: a，value: b=1
    const value = decodeURIComponent(keyVal.join("="));
    result[key] = value;
  });
  return result;
};
const handleOptions = (options = {}, key) => {
  let params = JSON.parse(JSON.stringify(options));
  if (params.q || params.scene) {
    params = {
      ...params,
      ...getQueryParams(decodeURIComponent(params.q || params.scene)),
    };
  }
  if (key) {
    return params[key];
  } else {
    return params;
  }
};

const beforeXhrUpload = async (taroUploadFile, options) => {
  let uploadForm = await common.getUploadFormData();
  options.formData = uploadForm;
  //taroUploadFile  是 Taro.uploadFile ， 你也可以自定义设置其它函数
  const uploadTask = taroUploadFile({
    url: uploadForm?.host,
    filePath: options.taroFilePath,
    fileType: options.fileType,
    header: {
      "Content-Type": "multipart/form-data",
      ...options.headers,
    }, //
    formData: options.formData,
    name: options.name,
    success: (response: { errMsg; statusCode; data }) => {
      if (options.xhrState == response.statusCode) {
        options.onSuccess?.(response, options);
      } else {
        options.onFailure?.(response, options);
      }
    },
    fail: (e) => {
      options.onFailure?.(e, options);
    },
  });
  options.onStart?.(options);
  uploadTask.progress((res) => {
    options.onProgress?.(res, options);
    // console.log('上传进度', res.progress);
    // console.log('已经上传的数据长度', res.totalBytesSent);
    // console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend);
  });
  // uploadTask.abort(); // 取消上传任务
};
export default {
  navBack,
  success,
  ok,
  getQueryParams,
  handleOptions,
  formatHHmm,
  formatTime,
  formatDate,
  minus24H,
  minus7D,
  failureToast,
  switchTab,
  beforeXhrUpload,
  validatePhone,
};
