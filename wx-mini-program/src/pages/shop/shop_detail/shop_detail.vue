<template>
  <view class="fx-body">
    <view class="board-frame">
      <image class="board-image" mode="aspectFill" :src="shopDetail.logo"/>
      <view class="board-right">
        <text class="board-right-title">
          {{ shopDetail.name }}
        </text>

        <text class="board-right-intro">
          {{ shopDetail.intro }}
        </text>
      </view>
      <view @click="onEditClick"> <Edit /> </view
    ></view>

    <view class="row">
      <nut-row
        style=" line-height: 48px;  border-radius: 5%; padding: 10px; border: 1px solid #f5f6f7"
      >
        <nut-col>
          <nut-row
            style="line-height: 36px;"
            type="flex"
            align="center"
            justify="space-evenly"
          >
            <nut-col :span="6" style="text-align: left;align-self: flex-start;"
              >余额</nut-col
            >
            <nut-col :span="4">
              <nut-switch
                @change="onSwitch"
                v-model="switchStatus"
                :loading="switchLoading"
              />
            </nut-col>
            <nut-col :span="4"
              ><view @click="subscribeMsg('deposit')">
                <image :src="depositIcon" class="icon" />
              </view>
            </nut-col>
            <nut-col :span="4">
              <view @click="subscribeMsg('withdraw')">
                <image :src="withdrawIcon" class="icon" />
              </view>
            </nut-col>
          </nut-row>

          <nut-row
            style="line-height: 36px;"
            type="flex"
            align="center"
            justify="space-evenly"
          >
            <nut-col :span="6" style="text-align: center"
              >¥{{ shopDetail.balance.toFixed(1) }}</nut-col
            >
            <nut-col :span="4">
              <view><text>开关</text></view>
            </nut-col>
            <nut-col :span="4">
              <view @click="showDepositPopup = true"><text>充值</text></view>
            </nut-col>

            <nut-col :span="4">
              <view @click="showWithdrawPopup = true"><text>提现</text></view>
            </nut-col>
          </nut-row>
        </nut-col>
      </nut-row>
    </view>

    <view class="row">
      <nut-row
        style="
      line-height: 48px;
        text-align: center;
        border-radius: 5%;
        padding: 10px;
        border: 1px solid #f5f6f7;
      "
      >
        <nut-col>
          <nut-row
            style="line-height: 36px;"
            type="flex"
            align="center"
            justify="space-evenly"
          >
            <nut-col :span="8" style="text-align: left">已付 </nut-col>
            <nut-col :span="7.5" style="text-align: left">笔数</nut-col>
            <nut-col :span="4.5" style="text-align: left">
              <view @click="onQrCodeClick">
                <image :src="qrCodeIcon" class="icon" />
              </view>
            </nut-col>
          </nut-row>
          <nut-row
            style="line-height: 36px;"
            type="flex"
            align="center"
            justify="space-evenly"
          >
            <nut-col :span="8" style="text-align: center;margin-top: 10px"
              >¥{{ shopDetail.totalAmount.toFixed(1) }}
            </nut-col>
            <nut-col :span="7.5" style="text-align: center;margin-top: 10px;"
              >{{ shopDetail.orderCount }}&nbsp;笔</nut-col
            >
            <nut-col :span="4.5" style="text-align: center;margin-top: 10px"
              ><view @click="onDetailClick">
                <text style="color: blue;">详情</text>
              </view>
            </nut-col>
          </nut-row>
        </nut-col>
      </nut-row>
    </view>
    <nut-popup
      v-model:visible="showDepositPopup"
      position="center"
      closeable
      :round="true"
      :close-on-click-overlay="false"
      @click-close-icon="
        showDepositPopup = false;
        depositAmount = undefined;
      "
    >
      <template #default>
        <view class="input-amount-modal">
          <view class="input-amount-modal-tag">输入充值金额</view>
          <view class="input-amount-modal-input">
            <nut-input type="digit" input-align="right" v-model="depositAmount">
              <template #left>
                <text>¥</text>
              </template>
              <template #right>
                <text>元</text>
              </template>
            </nut-input>
          </view>
          <view class="input-amount-modal-button">
            <nut-button
              shape="round"
              type="primary"
              size="small"
              @click="confirmDeposit"
              >&nbsp;确&nbsp;定&nbsp;</nut-button
            >
          </view>
        </view>
      </template>
    </nut-popup>

    <nut-popup
      v-model:visible="showWithdrawPopup"
      position="center"
      closeable
      :round="true"
      :close-on-click-overlay="false"
      @click-close-icon="
        showWithdrawPopup = false;
        withdrawAmount = undefined;
      "
    >
      <template #default>
        <view class="input-amount-modal">
          <view class="input-amount-modal-tag">输入提现金额</view>
          <view class="input-amount-modal-input">
            <nut-input
              type="digit"
              input-align="right"
              v-model="withdrawAmount"
            >
              <template #left>
                <text>¥</text>
              </template>
              <template #right>
                <text>元</text>
              </template>
            </nut-input>
          </view>
          <view class="input-amount-modal-button">
            <nut-button
              shape="round"
              type="primary"
              size="small"
              @click="confirmWithdraw"
              >&nbsp;确&nbsp;定&nbsp;</nut-button
            >
          </view>
        </view>
      </template>
    </nut-popup>

    <nut-popup
      v-model:visible="showPaySecretPopup"
      position="center"
      closeable
      :round="true"
      :close-on-click-overlay="false"
      @click-close-icon="
        showPaySecretPopup = false;
        paySecret = undefined;
      "
    >
      <template #default>
        <view class="input-amount-modal">
          <view class="input-amount-modal-tag">输入支付密码</view>
          <view class="input-amount-modal-input">
            <nut-input type="password" v-model="paySecret" />
          </view>
          <view class="input-amount-modal-button">
            <nut-button
              shape="round"
              type="primary"
              size="small"
              @click="confirmPaySecret"
              >&nbsp;确&nbsp;定&nbsp;</nut-button
            >
          </view>
        </view>
      </template>
    </nut-popup>

    <nut-popup
      v-model:visible="showQrCodePopup"
      position="center"
      closeable
      :round="true"
      :close-on-click-overlay="false"
      @click-close-icon="
        showQrCodePopup = false;
        qrCodeSrc = '';
      "
    >
      <template #default>
        <view class="qr-code-modal">
          <view class="qr-code-modal-tag">分享小程序码</view>
          <view>
            <image
              :src="qrCodeSrc"
              :showMenuByLongpress="true"
              mode="aspectFit"
              class="qr-code-modal-image"
            />
          </view>
        </view>
      </template>
    </nut-popup>
  </view>
</template>

<script setup lang="ts">
import "../../../app.css";
import depositIcon from "@/asset/deposit.png";
import withdrawIcon from "@/asset/refund.png";
import qrCodeIcon from "@/asset/qrcode.png";
import Taro from "@tarojs/taro";
import { ref } from "vue";
import shop from "@/service/shop";
import { GetShopResult } from "@/api/backend";
import "./shop_detail.scss";
import { Edit } from "@nutui/icons-vue-taro";
import payment from "@/service/payment";
import constants from "@/utils/constants";
import common from "@/service/common";

const shopId = ref(0);
const showDepositPopup = ref(false);
const showWithdrawPopup = ref(false);
const showPaySecretPopup = ref(false);
const depositAmount = ref<number | undefined>();
const withdrawAmount = ref<number | undefined>();
const paySecret = ref<string | undefined>();
const switchLoading = ref(false);
const switchStatus = ref(false);

const showQrCodePopup = ref(false);
const qrCodeSrc = ref("");

const shopDetail = ref({
  id: 0,
  name: "店铺详情",
  logo: "",
  intro: "",
  balance: 0.0,
  status: 0,
  totalAmount: 0.0,
  orderCount: 0
});
Taro.useLoad(options => {
  shopId.value = options.shopId;
});

Taro.useDidShow(async () => {
  const ret = await shop.getCompositeShop(shopId.value);
  if (ret) {
    shopDetail.value = buildShop(ret);
    switchStatus.value = Boolean(shopDetail.value.status);
  }
});

function subscribeMsg(src: "deposit" | "withdraw") {
  Taro.requestSubscribeMessage({
    tmplIds: [constants.BILL_MESSAGE_ID],
    entityIds: [],
    success: () => {
      common.msgSubscribe(constants.BILL_MESSAGE_ID);
      if (src == "deposit") {
        showDepositPopup.value = true;
      } else {
        showWithdrawPopup.value = true;
      }
    }
  });
}

async function onQrCodeClick() {
  const ret = await shop.getQrCode(shopId.value);
  if (ret) {
    showQrCodePopup.value = true;
    qrCodeSrc.value = "data:image/jpeg;base64," + ret;
  }
}

function onEditClick() {
  Taro.navigateTo({
    url: "/pages/shop/register/register?edit=true&shopId=" + shopId.value
  });
}

function onDetailClick() {
  Taro.navigateTo({
    url:
      "/pages/shop/order_list/order_list?shopId=" +
      shopId.value +
      "&status=" +
      constants.ORDER_STATUS.SUCCEED
  });
}

async function confirmDeposit() {
  if (depositAmount.value && depositAmount.value > 0) {
    const ret = await payment.deposit(depositAmount.value);
    if (ret) {
      showDepositPopup.value = false;
      depositAmount.value = undefined;
      Taro.showModal({
        content: "充值成功!",
        showCancel: false,
        success: () => Taro.getCurrentInstance().page?.onShow!()
      });
    }
  } else {
    Taro.showModal({
      content: "请输入正确的金额!",
      showCancel: false
    });
  }
}
function confirmWithdraw() {
  if (withdrawAmount.value && withdrawAmount.value > 0) {
    showPaySecretPopup.value = true;
  } else {
    Taro.showModal({
      content: "请输入正确的金额!",
      showCancel: false
    });
  }
}

async function confirmPaySecret() {
  if (paySecret.value) {
    const ret = await payment.withdraw(withdrawAmount.value!, paySecret.value!);
    if (ret) {
      showPaySecretPopup.value = false;
      showWithdrawPopup.value = false;
      paySecret.value = undefined;
      withdrawAmount.value = undefined;
      Taro.showModal({
        content: "提现成功!",
        showCancel: false,
        success: () => Taro.getCurrentInstance().page?.onShow!()
      });
    }
  } else {
    Taro.showModal({
      content: "密码不能为空!",
      showCancel: false
    });
  }
}

async function onSwitch(val: boolean) {
  switchLoading.value = true;
  const ret = await shop.switchFX(shopId.value, val ? 1 : 0);
  switchLoading.value = false;
  if (!ret) {
    switchStatus.value = false;
  }
}

function buildShop(ret: GetShopResult) {
  return {
    id: ret.shopId,
    name: ret.shopName,
    logo: ret.logo,
    intro: ret.intro,
    balance: ret.balance,
    status: ret.status,
    totalAmount: ret.orderTotalAmount,
    orderCount: ret.orderCount
  };
}
</script>
