<template>
  <view class="item">
    <view class="field-center">
      <image
        :src="order.avatar"
        style="width: 32px; height: 32px; border-radius: 50%"
      ></image>
      <text>{{ order.nickName.substring(0, 3) }}</text>
    </view>
    <view class="field-large">
      <text>¥{{ order.amount.toFixed(1) }}</text>
    </view>
    <view class="field-center">
      <image
        :src="order.capture"
        mode="aspectFit"
        @tap="onCaptureClick"
        style="width: 64px; height: 64px"
      ></image>
    </view>
    <view class="field-center">
      <text>{{ utils.formatHHmm(order.createTime) }}</text>
      <view v-if="created">
        <nut-button
          shape="round"
          plain
          type="primary"
          size="small"
          @click="onPayClick"
          >确认支付</nut-button
        ></view
      >
      <text v-else>{{ statusDesc }}</text>
    </view>
  </view>
  <nut-image-preview
    :show="showPreview"
    :images="images"
    @close="showPreview = false"
  />
</template>

<script setup lang="ts">
import { computed } from "vue";
import "./order_list.scss";
import constants from "@/utils/constants";
import utils from "@/utils";
import orderService from "@/service/order";
import Taro from "@tarojs/taro";
import { ref } from "vue";

export interface ShopOrder {
  id: number;
  avatar: string;
  nickName: string;
  amount: number;
  createTime: string;
  payTime?: string;
  capture: string;
  failMsg?: string;
  status: number;
}
const props = defineProps<{ order: ShopOrder }>();
const showPreview = ref(false);
const images = ref<{ src: string }[]>([]);

const created = computed(
  () => props.order.status == constants.ORDER_STATUS.CREATED
);

const statusDesc = computed(() => {
  if (props.order.status == constants.ORDER_STATUS.SUCCEED) {
    return utils.formatHHmm(props.order.payTime!);
  } else {
    return props.order.failMsg?.substring(0, 12);
  }
});
const onCaptureClick = async () => {
  images.value = [{ src: props.order.capture }];
  showPreview.value = true;
};
const onPayClick = async () => {
  const ret = await orderService.payOrder(props.order.id);
  if (ret) {
    props.order.payTime = new Date();
    props.order.status = constants.ORDER_STATUS.SUCCEED;

    Taro.showToast({
      title: "支付成功",
      icon: "success",
      duration: 2000
    });
  }
};
</script>
