/* eslint-disable */
/* tslint:disable */
/*
 * ---------------------------------------------------------------
 * ## THIS FILE WAS GENERATED VIA SWAGGER-TYPESCRIPT-API        ##
 * ##                                                           ##
 * ## AUTHOR: acacode                                           ##
 * ## SOURCE: https://github.com/acacode/swagger-typescript-api ##
 * ---------------------------------------------------------------
 */

import auth from "@/service/user";
import utils from "@/utils";
import Taro from "@tarojs/taro";

export interface BaseRequest {
  head: MobileRequestHead;
}
export interface BaseResponse {
  status: ResponseStatus;
}

/** AccountPartnerQueryParam */
export interface AccountPartnerQueryParam {
  /** @format int64 */
  accountId: number;
}

/** AccountPartnerQueryResult */
export interface AccountPartnerQueryResult {
  /** @format int64 */
  accountId: number;
  balance: number;
  /** @format date-time */
  createdAt: string;
  /** @format int32 */
  status: number;
  /** @format date-time */
  updatedAt: string;
  /** @format int64 */
  userId: number;
}

/** AccountTransferParam */
export interface AccountTransferParam {
  amount: number;
  /** @format int64 */
  fromAccountId: number;
  secretKey: string;
  /** @format int64 */
  toAccountId: number;
}

/** AccountTransferResult */
export type AccountTransferResult = object;

/** AccountUserQueryResult */
export interface AccountUserQueryResult {
  /** @format int64 */
  accountId: number;
  balance: number;
  /** @format date-time */
  createdAt: string;
  /** @format int32 */
  status: number;
  /** @format date-time */
  updatedAt: string;
  /** @format int64 */
  userId: number;
}

/** BaseRequest */
export interface BaseRequest {
  head: MobileRequestHead;
}

/** DepositParam */
export interface DepositParam {
  amount: number;
}

/** DepositResult */
export interface DepositResult {
  appId: string;
  nonceStr: string;
  package: string;
  paySign: string;
  signType: string;
  timeStamp: string;
}

/** GetLinkParam */
export interface GetQrCodesParam {
  /** @format int32 */
  number: number;
  shopId: number;
}

/** GetLinkResult */
export interface GetLinkResult {
  qrCodes: string[];
}

/** MobileRequestHead */
export interface MobileRequestHead {
  deviceId?: string;
  ip?: string;
  latitude?: string;
  longitude?: string;
  mac?: string;
  platform?: string;
  brand?: string;
  model?: string;
  system?: string;
  sign?: string;
  token?: string;
  uid?: string;
}

/** ModelAndView */
export interface ModelAndView {
  empty?: boolean;
  model?: object;
  modelMap?: Record<string, object>;
  reference?: boolean;
  status?:
    | "ACCEPTED"
    | "ALREADY_REPORTED"
    | "BAD_GATEWAY"
    | "BAD_REQUEST"
    | "BANDWIDTH_LIMIT_EXCEEDED"
    | "CHECKPOINT"
    | "CONFLICT"
    | "CONTINUE"
    | "CREATED"
    | "DESTINATION_LOCKED"
    | "EXPECTATION_FAILED"
    | "FAILED_DEPENDENCY"
    | "FORBIDDEN"
    | "FOUND"
    | "GATEWAY_TIMEOUT"
    | "GONE"
    | "HTTP_VERSION_NOT_SUPPORTED"
    | "IM_USED"
    | "INSUFFICIENT_SPACE_ON_RESOURCE"
    | "INSUFFICIENT_STORAGE"
    | "INTERNAL_SERVER_ERROR"
    | "I_AM_A_TEAPOT"
    | "LENGTH_REQUIRED"
    | "LOCKED"
    | "LOOP_DETECTED"
    | "METHOD_FAILURE"
    | "METHOD_NOT_ALLOWED"
    | "MOVED_PERMANENTLY"
    | "MOVED_TEMPORARILY"
    | "MULTIPLE_CHOICES"
    | "MULTI_STATUS"
    | "NETWORK_AUTHENTICATION_REQUIRED"
    | "NON_AUTHORITATIVE_INFORMATION"
    | "NOT_ACCEPTABLE"
    | "NOT_EXTENDED"
    | "NOT_FOUND"
    | "NOT_IMPLEMENTED"
    | "NOT_MODIFIED"
    | "NO_CONTENT"
    | "OK"
    | "PARTIAL_CONTENT"
    | "PAYLOAD_TOO_LARGE"
    | "PAYMENT_REQUIRED"
    | "PERMANENT_REDIRECT"
    | "PRECONDITION_FAILED"
    | "PRECONDITION_REQUIRED"
    | "PROCESSING"
    | "PROXY_AUTHENTICATION_REQUIRED"
    | "REQUESTED_RANGE_NOT_SATISFIABLE"
    | "REQUEST_ENTITY_TOO_LARGE"
    | "REQUEST_HEADER_FIELDS_TOO_LARGE"
    | "REQUEST_TIMEOUT"
    | "REQUEST_URI_TOO_LONG"
    | "RESET_CONTENT"
    | "SEE_OTHER"
    | "SERVICE_UNAVAILABLE"
    | "SWITCHING_PROTOCOLS"
    | "TEMPORARY_REDIRECT"
    | "TOO_EARLY"
    | "TOO_MANY_REQUESTS"
    | "UNAUTHORIZED"
    | "UNAVAILABLE_FOR_LEGAL_REASONS"
    | "UNPROCESSABLE_ENTITY"
    | "UNSUPPORTED_MEDIA_TYPE"
    | "UPGRADE_REQUIRED"
    | "URI_TOO_LONG"
    | "USE_PROXY"
    | "VARIANT_ALSO_NEGOTIATES";
  view?: View;
  viewName?: string;
}

/** MsgSubsParam */
export interface MsgSubsParam {
  templateId: string;
}

/** MsgSubsResult */
export type MsgSubsResult = object;

/** OrderCreateParam */
export interface OrderCreateParam {
  capture: string;
  serialNo: string;
  /** @format int64 */
  shopId: number;
}

/** OrderCreateResult */
export interface OrderCreateResult {
  /** @format int64 */
  orderId: number;
}

/** OrderDetail */
export interface OrderDetail {
  amount: number;
  capture: string;
  /** @format date-time */
  createdAt: string;
  failMsg?: string;
  /** @format date-time */
  fxTime?: string;
  /** @format int64 */
  id: number;
  /** @format int64 */
  shopId: number;
  /** @format int32 */
  status: number;
  /** @format date-time */
  updatedAt: string;
  /** @format int64 */
  userId: number;
}

export interface UserOrderDetail extends OrderDetail {
  shopName: string;
  shopLogo: string;
}

/** OrderDetail */
export interface ShopOrderDetail extends OrderDetail {
  nickName: string;
  avatar: string;
}

/** OrderPayParam */
export interface OrderPayParam {
  /** @format int64 */
  orderId: number;
}

/** OrderPayResult */
export type OrderPayResult = object;

/** ResponseStatus */
export interface ResponseStatus {
  errorCode?: string;
  errorMessage?: string;
  errors?: Record<string, string>;
}

/** ShopCreateParam */
export interface ShopCreateParam {
  address: string;
  intro: string;
  logo: string;
  name: string;
  paySecret: string;
  phone: string;
}

/** ShopCreateResult */
export interface ShopCreateResult {
  /** @format int64 */
  shopId: number;
}

/** ShopDetail */
export interface ShopDetail {
  address: string;
  /** @format date-time */
  createdAt: string;
  intro: string;
  logo: string;
  /** @format int64 */
  ownerUserId: number;
  phone: string;
  /** @format int64 */
  shopId: number;
  shopName: string;
  /** @format date-time */
  updatedAt: string;
  status: number;
}

/** ShopListResult */
export interface ShopListResult {
  shopList?: ShopDetail[];
}

/** ShopOrderListParam */
export interface ShopOrderListParam {
  /** @format date-time */
  from?: string;
  /**
   * @format int32
   * @max 20
   * @exclusiveMax false
   */
  limit: number;
  /** @format int32 */
  offset: number;
  /** @format int64 */
  shopId: number;
  /** @format int32 */
  status?: number;
  /** @format date-time */
  to?: string;
}

/** ShopOrderListResult */
export interface ShopOrderListResult {
  orderList?: ShopOrderDetail[];
}

/** ShopQueryParam */
export interface GetShopParam {
  /** @format int64 */
  shopId: number;
}

/** ShopQueryResult */
export interface GetShopResult {
  address: string;
  /** @format date-time */
  createdAt: string;
  intro: string;
  logo: string;
  /** @format int64 */
  ownerUserId: number;
  phone: string;
  /** @format int64 */
  shopId: number;
  shopName: string;
  /** @format date-time */
  updatedAt: string;
  status: number;
  balance: number;
  orderCount: number;
  orderTotalAmount: number;
}

export interface ShopQueryResult {
  address: string;
  /** @format date-time */
  createdAt: string;
  intro: string;
  logo: string;
  /** @format int64 */
  ownerUserId: number;
  phone: string;
  /** @format int64 */
  shopId: number;
  shopName: string;
}

export interface GetPublicShopInfoResult {
  address: string;
  intro: string;
  logo: string;
  shopName: string;
}

/** ShopUpdateParam */
export interface ShopUpdateParam {
  address: string;
  intro: string;
  logo: string;
  name: string;
  phone: string;
  /** @format int64 */
  shopId: number;
}

/** ShopUpdateResult */
export type ShopUpdateResult = object;

/** SignUploadUrlResult */
export interface SignUploadUrlResult {
  signedUrl: string;
}

/** SwitchParam */
export interface SwitchParam {
  /** @format int64 */
  shopId: number;
  /**
   * @format int32
   * @min 0
   * @exclusiveMin false
   * @max 1
   * @exclusiveMax false
   */
  switchValue: number;
}

/** SwitchResult */
export type SwitchResult = object;

/** UserLoginParam */
export interface UserLoginParam {
  code: string;
}

/** UserLoginResult */
export interface UserLoginResult {
  /** @format int64 */
  userId: number;
  newUser: boolean;
}

/** UserOrderListParam */
export interface UserOrderListParam {
  /** @format date-time */
  from?: string;
  /**
   * @format int32
   * @max 20
   * @exclusiveMax false
   */
  limit: number;
  /** @format int32 */
  offset: number;
  /** @format int32 */
  status?: number;
  /** @format date-time */
  to?: string;
}

/** UserOrderListResult */
export interface UserOrderListResult {
  orderList?: OrderDetail[];
}

/** View */
export interface View {
  contentType?: string;
}

/** WithdrawParam */
export interface WithdrawParam {
  amount: number;
}

/** WithdrawResult */
export type WithdrawResult = object;

/** Request«AccountPartnerQueryParam» */
export interface RequestAccountPartnerQueryParam {
  head: MobileRequestHead;
  param: AccountPartnerQueryParam;
}

/** Request«AccountTransferParam» */
export interface RequestAccountTransferParam {
  head: MobileRequestHead;
  param: AccountTransferParam;
}

/** Request«DepositParam» */
export interface RequestDepositParam {
  head: MobileRequestHead;
  param: DepositParam;
}

/** Request«GetLinkParam» */
export interface RequestGetQrCodesParam {
  head: MobileRequestHead;
  param: GetQrCodesParam;
}

/** Request«MsgSubsParam» */
export interface RequestMsgSubsParam {
  head: MobileRequestHead;
  param: MsgSubsParam;
}

/** Request«OrderCreateParam» */
export interface RequestOrderCreateParam {
  head: MobileRequestHead;
  param: OrderCreateParam;
}

/** Request«OrderPayParam» */
export interface RequestOrderPayParam {
  head: MobileRequestHead;
  param: OrderPayParam;
}

/** Request«ShopCreateParam» */
export interface RequestShopCreateParam {
  head: MobileRequestHead;
  param: ShopCreateParam;
}

/** Request«ShopOrderListParam» */
export interface RequestShopOrderListParam {
  head: MobileRequestHead;
  param: ShopOrderListParam;
}

/** Request«GetShopParam» */
export interface RequestGetShopDetailParam {
  head: MobileRequestHead;
  param: GetShopParam;
}

/** Request«ShopQueryParam» */
export interface RequestShopQueryParam {
  head: MobileRequestHead;
  param: ShopQueryParam;
}
/** Request«ShopQueryParam» */
export interface RequestGetPublicShopInfoParam {
  head: MobileRequestHead;
  param: GetPublicShopInfoParam;
}

export interface GetPublicShopInfoParam {
  /** @format int64 */
  shopId: number;
}

export interface ShopQueryParam {
  /** @format int64 */
  shopId: number;
}

/** Request«ShopUpdateParam» */
export interface RequestShopUpdateParam {
  head: MobileRequestHead;
  param: ShopUpdateParam;
}

/** Request«SwitchParam» */
export interface RequestSwitchParam {
  head: MobileRequestHead;
  param: SwitchParam;
}

/** Request«UserLoginParam» */
export interface RequestUserLoginParam {
  head: MobileRequestHead;
  param: UserLoginParam;
}

/** Request«UserOrderListParam» */
export interface RequestUserOrderListParam {
  head: MobileRequestHead;
  param: UserOrderListParam;
}

/** Request«WithdrawParam» */
export interface RequestWithdrawParam {
  head: MobileRequestHead;
  param: WithdrawParam;
}

/** Response«AccountPartnerQueryResult» */
export interface ResponseAccountPartnerQueryResult {
  result?: AccountPartnerQueryResult;
  status: ResponseStatus;
}

/** Response«AccountTransferResult» */
export interface ResponseAccountTransferResult {
  result?: AccountTransferResult;
  status: ResponseStatus;
}

/** Response«AccountUserQueryResult» */
export interface ResponseAccountUserQueryResult {
  result?: AccountUserQueryResult;
  status: ResponseStatus;
}

/** Response«DepositResult» */
export interface ResponseDepositResult {
  result?: DepositResult;
  status: ResponseStatus;
}

/** Response«GetLinkResult» */
export interface ResponseGetQrCodesResult {
  result?: GetLinkResult;
  status: ResponseStatus;
}

/** Response«OrderCreateResult» */
export interface ResponseOrderCreateResult {
  result?: OrderCreateResult;
  status: ResponseStatus;
}

/** Response«OrderPayResult» */
export interface ResponseOrderPayResult {
  result?: OrderPayResult;
  status: ResponseStatus;
}

/** Response«ShopCreateResult» */
export interface ResponseShopCreateResult {
  result?: ShopCreateResult;
  status: ResponseStatus;
}

/** Response«ShopListResult» */
export interface ResponseShopListResult {
  result?: ShopListResult;
  status: ResponseStatus;
}

/** Response«ShopOrderListResult» */
export interface ResponseShopOrderListResult {
  result?: ShopOrderListResult;
  status: ResponseStatus;
}

/** Response«ShopQueryResult» */
export interface ResponseGetShopDetailResult {
  result?: GetShopResult;
  status: ResponseStatus;
}

export interface ResponseShopQueryResult {
  result?: ShopQueryResult;
  status: ResponseStatus;
}

export interface ResponseGetPublicShopInfoResult {
  result?: GetPublicShopInfoResult;
  status: ResponseStatus;
}

/** Response«SignUploadUrlResult» */
export interface ResponseSignUploadUrlResult {
  result?: SignUploadUrlResult;
  status: ResponseStatus;
}

/** Response«GetUploadPolicyResult» */
export interface ResponseGetUploadPolicyResult {
  result?: GetUploadPolicyResult;
  status: ResponseStatus;
}

export interface GetUploadPolicyResult {
  accessid: string;
  policy: string;
  signature: string;
  dir?: string;
  host: string;
  expire: string;
  key: string;
}

/** Response«SwitchResult» */
export interface ResponseSwitchResult {
  result?: SwitchResult;
  status: ResponseStatus;
}

/** Response«UserLoginResult» */
export interface ResponseUserLoginResult {
  result?: UserLoginResult;
  status: ResponseStatus;
}

/** Response«UserOrderListResult» */
export interface ResponseUserOrderListResult {
  result?: UserOrderListResult;
  status: ResponseStatus;
}

/** Response«WithdrawResult» */
export interface ResponseWithdrawResult {
  result?: WithdrawResult;
  status: ResponseStatus;
}

export type RequestParams = Omit<
  Taro.request.Option,
  "url" | "method" | "data" | "success"
>;

function autoHandleError(
  target: any,
  key: string,
  descriptor: PropertyDescriptor
) {
  const originalMethod = descriptor.value;
  descriptor.value = async function(...args: any[]) {
    Taro.showLoading({
      title: "正在加载...",
    });
    let result: Taro.request.SuccessCallbackResult<BaseResponse> = await originalMethod.apply(
      this,
      args
    );
    console.log("request method: %s, result: %o", key, result);
    // 请求错误
    if (!result.data) {
      utils.failureToast();
      return result;
    }
    // 正常情况
    if (utils.success(result.data)) {
      Taro.hideLoading();
      return result;
    }
    // 未登录，重新登录后再请求
    if (result.data.status.errorCode == "100002") {
      await auth.login();
      result = await originalMethod.apply(this, args);
      Taro.hideLoading();
    } else {
      // 其他业务异常
      utils.failureToast(result.data.status);
    }
    return result;
  };
  return descriptor;
}

function enableCookie(
  target: any,
  key: string,
  descriptor: PropertyDescriptor
) {
  const originalMethod = descriptor.value;
  descriptor.value = async function(...args: any[]) {
    const cookie = Taro.getStorageSync("cookie");
    if (cookie) {
      console.log(cookie);
      if (args.length == 1) {
        args.push({});
      }
      const param = args[1];
      const header = param.header;
      if (header) {
        header.cookie = cookie;
      } else {
        param.header = {
          cookie: cookie,
        };
      }
    }
    const result: Taro.request.SuccessCallbackResult<BaseResponse> = await originalMethod.apply(
      this,
      args
    );

    const setCookie = result.header["Set-Cookie"];
    if (setCookie) {
      Taro.setStorageSync("cookie", setCookie);
    }

    return result;
  };
  return descriptor;
}

class Api {
  private static BASE_URL = "http://localhost:8080";

  // private static BASE_URL = "https://www.sgj.cool";

  private static instance: Api;

  private constructor() {}

  static getInstance() {
    if (!Api.instance) {
      Api.instance = new Api();
    }
    return Api.instance;
  }
  /**
   * No description
   *
   * @tags user-controller
   * @name Login
   * @summary login
   * @request POST:/user/login
   */
  @enableCookie
  async login(request: RequestUserLoginParam, params: RequestParams = {}) {
    return Taro.request<ResponseUserLoginResult, RequestUserLoginParam>({
      url: Api.BASE_URL + `/user/login`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags account-controller
   * @name PartnerQuery
   * @summary partnerQuery
   * @request POST:/account/partner_query
   */
  async partnerQuery(
    request: RequestAccountPartnerQueryParam,
    params: RequestParams = {}
  ) {
    return Taro.request<
      ResponseAccountPartnerQueryResult,
      RequestAccountPartnerQueryParam
    >({
      url: Api.BASE_URL + `/account/partner_query`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags account-controller
   * @name UserQuery
   * @summary userQuery
   * @request POST:/account/user_query
   */
  @autoHandleError
  @enableCookie
  async userQuery(request: BaseRequest, params: RequestParams = {}) {
    return Taro.request<ResponseAccountUserQueryResult, BaseRequest>({
      url: Api.BASE_URL + `/account/user_query`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags account-controller
   * @name PartnerTransfer
   * @summary partnerTransfer
   * @request POST:/account/partner_transfer
   */
  async partnerTransfer(
    request: RequestAccountTransferParam,
    params: RequestParams = {}
  ) {
    return Taro.request<
      ResponseAccountTransferResult,
      RequestAccountTransferParam
    >({
      url: Api.BASE_URL + `/account/partner_transfer`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags common-controller
   * @name getSignedUploadUrl
   * @summary getSignedUploadUrl
   * @request POST:/get_signed_upload_url
   */
  @autoHandleError
  @enableCookie
  async getSignedUploadUrl(request: BaseRequest, params: RequestParams = {}) {
    return Taro.request<ResponseSignUploadUrlResult, BaseRequest>({
      url: Api.BASE_URL + `/get_signed_upload_url`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags common-controller
   * @name getUploadPolicy
   * @summary getUploadPolicy
   * @request POST:/get_upload_policy
   */
  @autoHandleError
  @enableCookie
  async getUploadPolicy(request: BaseRequest, params: RequestParams = {}) {
    return Taro.request<ResponseGetUploadPolicyResult, BaseRequest>({
      url: Api.BASE_URL + `/get_upload_policy`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags common-controller
   * @name MsgSubscribe
   * @summary msgSubscribe
   * @request POST:/msg_subscribe
   */
  @autoHandleError
  @enableCookie
  async msgSubscribe(request: RequestMsgSubsParam, params: RequestParams = {}) {
    return Taro.request<BaseResponse, RequestMsgSubsParam>({
      url: Api.BASE_URL + `/msg_subscribe`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags order-controller
   * @name createOrder
   * @summary createOrder
   * @request POST:/order/create
   */
  @autoHandleError
  @enableCookie
  async createOrder(
    request: RequestOrderCreateParam,
    params: RequestParams = {}
  ) {
    return Taro.request<ResponseOrderCreateResult, RequestOrderCreateParam>({
      url: Api.BASE_URL + `/order/create`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags order-controller
   * @name listShopOrders
   * @summary listShopOrders
   * @request POST:/composite/list_shop_orders
   */
  @autoHandleError
  @enableCookie
  async listShopOrders(
    request: RequestShopOrderListParam,
    params: RequestParams = {}
  ) {
    return Taro.request<ResponseShopOrderListResult, RequestShopOrderListParam>(
      {
        url: Api.BASE_URL + `/composite/list_shop_orders`,
        method: "POST",
        data: request,
        ...params,
      }
    );
  }

  /**
   * No description
   *
   * @tags order-controller
   * @name listUserOrders
   * @summary listUserOrders
   * @request POST:/composite/list_user_orders
   */
  @autoHandleError
  @enableCookie
  async listUserOrders(
    request: RequestUserOrderListParam,
    params: RequestParams = {}
  ) {
    return Taro.request<ResponseUserOrderListResult, RequestUserOrderListParam>(
      {
        url: Api.BASE_URL + `/composite/list_user_orders`,
        method: "POST",
        data: request,
        ...params,
      }
    );
  }

  /**
   * No description
   *
   * @tags order-controller
   * @name payOrder
   * @summary pay
   * @request POST:/order/pay
   */
  @autoHandleError
  @enableCookie
  async payOrder(request: RequestOrderPayParam, params: RequestParams = {}) {
    return Taro.request<ResponseOrderPayResult, RequestOrderPayParam>({
      url: Api.BASE_URL + `/order/pay`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags pay-controller
   * @name deposit
   * @summary deposit
   * @request POST:/pay/deposit
   */
  @autoHandleError
  @enableCookie
  async deposit(request: RequestDepositParam, params: RequestParams = {}) {
    return Taro.request<ResponseDepositResult, RequestDepositParam>({
      url: Api.BASE_URL + `/pay/deposit`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags pay-controller
   * @name withdraw
   * @summary withdraw
   * @request POST:/pay/withdraw
   */
  @autoHandleError
  @enableCookie
  async withdraw(request: RequestWithdrawParam, params: RequestParams = {}) {
    return Taro.request<ResponseWithdrawResult, RequestWithdrawParam>({
      url: Api.BASE_URL + `/pay/withdraw`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags shop-controller
   * @name createShop
   * @summary create
   * @request POST:/shop/create
   */
  @autoHandleError
  @enableCookie
  async createShop(
    request: RequestShopCreateParam,
    params: RequestParams = {}
  ) {
    return Taro.request<ResponseShopCreateResult, RequestShopCreateParam>({
      url: Api.BASE_URL + `/shop/create`,
      method: "POST",
      data: request,
      ...params,
    });
  }
  /**
   * No description
   *
   * @tags shop-controller
   * @name updateShop
   * @summary update
   * @request POST:/shop/update
   */
  @autoHandleError
  @enableCookie
  async updateShop(
    request: RequestShopUpdateParam,
    params: RequestParams = {}
  ) {
    return Taro.request<BaseResponse, RequestShopUpdateParam>({
      url: Api.BASE_URL + `/shop/update`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags shop-controller
   * @name queryShop
   * @summary query
   * @request POST:/shop/query
   */
  @autoHandleError
  @enableCookie
  async queryShop(request: RequestShopQueryParam, params: RequestParams = {}) {
    return Taro.request<ResponseShopQueryResult, RequestShopQueryParam>({
      url: Api.BASE_URL + `/shop/query`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /*
   * No description
   *
   * @tags shop-controller
   * @name getPublicShopInfo
   * @summary getPublicShopInfo
   * @request POST:/shop/get_public_info
   */
  @autoHandleError
  @enableCookie
  async getPublicShopInfo(
    request: RequestGetPublicShopInfoParam,
    params: RequestParams = {}
  ) {
    return Taro.request<
      ResponseGetPublicShopInfoResult,
      RequestGetPublicShopInfoParam
    >({
      url: Api.BASE_URL + `/shop/get_public_info`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  @autoHandleError
  @enableCookie
  async getShopDetail(
    request: RequestGetShopDetailParam,
    params: RequestParams = {}
  ) {
    return Taro.request<ResponseGetShopDetailResult, RequestGetShopDetailParam>(
      {
        url: Api.BASE_URL + `/composite/get_shop`,
        method: "POST",
        data: request,
        ...params,
      }
    );
  }

  /**
   * No description
   *
   * @tags shop-controller
   * @name listShops
   * @summary list
   * @request POST:/shop/list
   */
  @autoHandleError
  @enableCookie
  async listShops(request: BaseRequest, params: RequestParams = {}) {
    return Taro.request<ResponseShopListResult, BaseRequest>({
      url: Api.BASE_URL + `/shop/list`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags shop-controller
   * @name getQrCodes
   * @summary getQrCodes
   * @request POST:/shop/get_qr_codes
   */
  @autoHandleError
  @enableCookie
  async getQrCodes(
    request: RequestGetQrCodesParam,
    params: RequestParams = {}
  ) {
    return Taro.request<ResponseGetQrCodesResult, RequestGetQrCodesParam>({
      url: Api.BASE_URL + `/shop/get_qr_codes`,
      method: "POST",
      data: request,
      ...params,
    });
  }

  /**
   * No description
   *
   * @tags shop-controller
   * @name switchFx
   * @summary switchFx
   * @request POST:/shop/switch
   */
  @autoHandleError
  @enableCookie
  async switchFx(request: RequestSwitchParam, params: RequestParams = {}) {
    return Taro.request<ResponseSwitchResult, RequestSwitchParam>({
      url: Api.BASE_URL + `/shop/switch`,
      method: "POST",
      data: request,
      ...params,
    });
  }
}

export default Api.getInstance();
