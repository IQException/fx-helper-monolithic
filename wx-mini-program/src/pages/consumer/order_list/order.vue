<template>
  <view class="item">
    <view class="field-center">
      <image
        :src="order.logo"
        style="width: 32px; height: 32px; border-radius: 10%"
      ></image>
      <text>{{ order.shopName.substring(0, 4) }}</text>
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
    <view class="field">
      <text>{{ utils.formatHHmm(order.createTime) }}</text>
      <text>{{ statusDesc }}</text>
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
import "../../shop/order_list/order_list.scss";
import constants from "@/utils/constants";
import utils from "@/utils";
import { ref } from "vue";

export interface UserOrder {
  id: number;
  shopName: string;
  logo: string;
  amount: number;
  createTime: string;
  payTime?: string;
  capture: string;
  failMsg?: string;
  status: number;
}
const props = defineProps<{ order: UserOrder }>();

const showPreview = ref(false);
const images = ref<{ src: string }[]>([]);

const onCaptureClick = async () => {
  images.value = [{ src: props.order.capture }];
  showPreview.value = true;
};

const statusDesc = computed(() => {
  if (props.order.status == constants.ORDER_STATUS.SUCCEED) {
    return utils.formatHHmm(props.order.payTime!);
  } else if (props.order.status == constants.ORDER_STATUS.CREATED) {
    return "待审核";
  } else if (props.order.status == constants.ORDER_STATUS.PROCESSING) {
    return "处理中";
  } else {
    return props.order.failMsg?.substring(0, 12);
  }
});
</script>
