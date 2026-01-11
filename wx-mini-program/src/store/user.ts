import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("user", () => {
  const userId = ref<number>();
  // 非精确值，只是用来判断：0，无；1，只有一个；>1 多个
  const shopCount = ref(0);
  // 非精确值，只是用来判断：0，无；>0 有
  const orderCount = ref(0);

  function setUserId(val: number) {
    userId.value = val;
  }
  function setShopCount(val: number) {
    shopCount.value = val;
  }
  function setOrderCount(val: number) {
    orderCount.value = val;
  }

  return {
    userId,
    setUserId,
    shopCount,
    setShopCount,
    orderCount,
    setOrderCount
  };
});
