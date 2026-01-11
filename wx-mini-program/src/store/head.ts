import { defineStore } from "pinia";
import { ref } from "vue";
import Taro from "@tarojs/taro";
import { MobileRequestHead } from "@/api/backend";

export const useHeadStore = defineStore("head", () => {
  const head = ref<MobileRequestHead>({});
  setHead(Taro.getDeviceInfo());
  function setHead(val: Taro.getDeviceInfo.Result) {
    head.value = {
      platform: val.platform,
      brand: val.brand,
      model: val.model,
      system: val.system
    };
  }

  return { head, setHead };
});
