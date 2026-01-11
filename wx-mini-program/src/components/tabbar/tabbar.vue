<template>
  <nut-tabbar
    v-show="show"
    :bottom="true"
    v-model="selected"
    @tab-switch="tabSwitch"
  >
    <nut-tabbar-item tab-title="首页">
      <template #icon>
        <Home></Home>
      </template>
    </nut-tabbar-item>
    <nut-tabbar-item v-if="shopCount > 0" tab-title="店铺">
      <template #icon>
        <Category></Category>
      </template>
    </nut-tabbar-item>
    <nut-tabbar-item v-if="orderCount > 0" tab-title="返现">
      <template #icon>
        <Order></Order>
      </template>
    </nut-tabbar-item>
  </nut-tabbar>
</template>

<script setup lang="ts">
import { useTabStore } from "@/store/tab";
import { Home, Category, Order } from "@nutui/icons-vue-taro";
import { storeToRefs } from "pinia";
import { computed } from "vue";
import utils from "@/utils";
import { useUserStore } from "@/store/user";

const tabStore = useTabStore();
const userStore = useUserStore();

const { orderCount, shopCount } = storeToRefs(userStore);

const { tabbar, selected } = storeToRefs(tabStore);
const setSelected = tabStore.setSelected;
const show = computed(() => tabbar.value.length > 0);

const tabSwitch = (item: Record<string, unknown>, index: number) => {
  setSelected(index);
  utils.switchTab(tabbar.value, index);
};
</script>
