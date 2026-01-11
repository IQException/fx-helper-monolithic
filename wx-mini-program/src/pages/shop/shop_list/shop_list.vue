<template>
  <nut-navbar title="我的店铺" />
  <view class="fx-body">
    <shop-board
      v-for="shop in shops"
      :key="shop.shopId"
      :shop="shop"
      :clickable="true"
    />
  </view>
  <tabbar />
</template>

<script setup lang="ts">
import "../../../app.css";
import ShopBoard, { ShopInfo } from "@/components/shop_border/shop_board.vue";
import { ref } from "vue";
import shop from "@/service/shop";
import tabbar from "@/components/tabbar/tabbar.vue";
import Taro from "@tarojs/taro";

const shops = ref<ShopInfo[]>([]);

Taro.useDidShow(async () => {
  const ret = await shop.list();
  if (ret && ret.shopList) {
    shops.value = ret.shopList.map(e => {
      return {
        shopId: e.shopId,
        name: e.shopName,
        logo: e.logo,
        intro: e.intro
      };
    });
  }
});
</script>

<style scoped></style>
