<template>
  <!--   <nut-navbar title="首页">
    <template #right>
      <ShareN color="red" size="1.2rem"></ShareN>
    </template>
  </nut-navbar> -->
  <view class="fx-body">
    <view class="fx-index-header">
      <image :src="indexGif" class="fx-index-img" />
      <text class="fx-index-text">PS好评骗返现？</text>
      <text class="fx-index-text">用户参与度低？</text>
      <text class="fx-index-text">员工监守自盗？</text>
      <text class="fx-index-text">没有统一管理？</text>
    </view>
    <view class="fx-index-slogan">
      <text> 让每一分钱， </text>
      <text> 都花在刀刃上！ </text>
    </view>
    <nut-button
      block
      type="primary"
      class="fx-index-button-reg"
      @click="register"
      >立即注册</nut-button
    >
  </view>
  <tabbar />
</template>

<script setup lang="ts">
import indexGif from "../../asset/index.gif";
import "./index.scss";
import Taro, { TabBarItem } from "@tarojs/taro";
import auth from "@/service/user";
import { useUserStore } from "@/store/user";
import shop from "@/service/shop";
import order from "@/service/order";
import { useTabStore } from "@/store/tab";
import tabbar from "@/components/tabbar/tabbar.vue";

const userStore = useUserStore();
const tabStore = useTabStore();

const register = () => {
  Taro.navigateTo({
    url: "/pages/shop/register/register"
  });
};

Taro.useDidShow(async () => {
  Taro.showShareMenu({ withShareTicket: true });
  const ret = await auth.login();
  if (ret) {
    userStore.setUserId(ret.userId);
    if (!ret.newUser) {
      await initUserStore();
      initTabbar();
    }
  }
});

async function initUserStore() {
  const shops = await shop.list();
  const userOrders = await order.listUserOrders();
  if (shops && shops.shopList) {
    userStore.setShopCount(shops.shopList.length);
  }
  if (userOrders && userOrders.orderList) {
    userStore.setOrderCount(userOrders.orderList.length);
  }
}

function initTabbar() {
  const indexTab = {
    pagePath: "/pages/index/index",
    selectedIconPath: "/images/tabbar_home_on.png",
    iconPath: "/images/tabbar_home.png",
    text: "首页"
  };
  const shopTab = {
    pagePath: "/pages/shop/shop_list/shop_list",
    selectedIconPath: "/images/tabbar_cate_on.png",
    iconPath: "/images/tabbar_cate.png",
    text: "店铺"
  };
  const ordersTab = {
    pagePath: "/pages/consumer/order_list/order_list?showTabbar=true",
    selectedIconPath: "/images/tabbar_my_on.png",
    iconPath: "/images/tabbar_my.png",
    text: "返现"
  };
  var tabbar: TabBarItem[] = [indexTab];

  if (userStore.shopCount == 0) {
    if (userStore.orderCount > 0) {
      tabbar.push();
    } else {
      tabbar = [];
    }
  } else {
    tabbar.push(shopTab);

    if (userStore.orderCount > 0) {
      tabbar.push(ordersTab);
    }
  }

  tabStore.setTabbar(tabbar);
  tabStore.setSelected(0);
}
/* Taro.useDidShow(() => {
  const pageObj = Taro.getCurrentInstance().page;
  const tabbar = Taro.getTabBar(pageObj);
}); */
</script>
