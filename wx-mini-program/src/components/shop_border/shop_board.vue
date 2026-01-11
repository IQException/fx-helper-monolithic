<template>
  <view @click="onClick" class="board-frame">
    <image class="board-image" :src="shop.logo" mode="aspectFill" />
    <view class="board-right">
      <text class="board-right-title">
        {{ shop.name }}
      </text>

      <text maxLines="3" class="board-right-intro">
        {{ shop.intro }}
      </text>
    </view>
  </view>
</template>

<script setup lang="ts">
import "./shop_board.scss";
import Taro from "@tarojs/taro";

export interface ShopInfo {
  shopId: number;
  name: string;
  intro: string;
  logo: string;
}
const props = defineProps<{ shop: ShopInfo; clickable?: boolean }>();

function onClick() {
  if (props.clickable) {
    Taro.navigateTo({
      url: "/pages/shop/shop_detail/shop_detail?shopId=" + props.shop.shopId
    });
  }
}
</script>
