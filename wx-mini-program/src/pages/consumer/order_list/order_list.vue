<template>
  <nut-navbar title="返现详情🧧" />
  <view class="fx-body">
    <view style="align-self: stretch">
      <order v-for="o in userOrders" :key="o.id" :order="o" />
    </view>
    <tabbar v-if="showTabbar" />
  </view>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { UserOrder } from "./order.vue";
import Taro from "@tarojs/taro";
import order from "./order.vue";
import tabbar from "@/components/tabbar/tabbar.vue";
import orderService from "@/service/order";
import { UserOrderDetail } from "@/api/backend";
import "../../../app.css";

const userOrders = ref<UserOrder[]>([]);
const pageNo = ref(1);
const showTabbar = ref(false);

Taro.useLoad(options => {
  showTabbar.value = options.showTabbar ? true : false;
  refresh();
});

Taro.useReachBottom(() => {
  append();
});

// loader
const refresh = async () => {
  pageNo.value = 1;
  let _userOrders = await getUserOrders();
  userOrders.value = _userOrders ? _userOrders : [];
};
const append = async () => {
  pageNo.value = pageNo.value + 1;
  let _userOrders = await getUserOrders();
  if (_userOrders && _userOrders.length > 0) {
    userOrders.value.push(..._userOrders);
  } else {
    pageNo.value = pageNo.value - 1;
    Taro.showToast({
      title: "我也是有底线的！",
      icon: "error",
      duration: 2000
    });
  }
};

const getUserOrders = async () => {
  const _userOrders = await orderService.listUserOrders(
    pageNo.value,
    undefined,
    undefined,
    undefined
  );

  return _userOrders?.orderList?.map(buildOrder);
};

// builder
const buildOrder = (orderDetail: UserOrderDetail): UserOrder => {
  return {
    id: orderDetail.id,
    shopName: orderDetail.shopName,
    logo: orderDetail.shopLogo,
    amount: orderDetail.amount,
    payTime: orderDetail.fxTime,
    capture: orderDetail.capture,
    failMsg: orderDetail.failMsg,
    createTime: orderDetail.createdAt,
    status: orderDetail.status
  };
};
</script>
