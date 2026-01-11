import { TabBarItem } from "@tarojs/taro";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useTabStore = defineStore("tab", () => {
  const selected = ref(0);
  const tabbar = ref<TabBarItem[]>([]);

  function setSelected(index: number) {
    selected.value = index;
  }

  function setTabbar(val: TabBarItem[]) {
    tabbar.value = val;
  }

  return { selected, setSelected, tabbar, setTabbar };
});
