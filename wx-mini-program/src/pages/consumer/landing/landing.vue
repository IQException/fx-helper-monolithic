<template>
  <nut-navbar title="评价返现金🧧" />

  <view class="fx-body">
    <ShopBoard :shop="shopInfo" />

    <nut-form label-position="top" star-position="right">
      <nut-form-item label="上传评价截图" required>
        <nut-uploader
          v-model:file-list="fileList"
          :maximize="8 * 1024 * 1024"
          maximum="1"
          accept="image/*"
          :source-type="['album']"
          :size-type="['compressed']"
          :media-type="['image']"
          :before-xhr-upload="utils.beforeXhrUpload"
          is-preview
          @success="onUploadSuccess"
          @delete="onDeleteUpload"
        >
        </nut-uploader>
      </nut-form-item>
    </nut-form>
    <nut-button
      style="margin-top: 100px"
      size="large"
      type="primary"
      v-on:click="onSubmit"
      >提交评价</nut-button
    >
  </view>
</template>

<script setup lang="ts">
import { ref } from "vue";
import "../../../app.css";
import Taro from "@tarojs/taro";
import ShopBoard, { ShopInfo } from "@/components/shop_border/shop_board.vue";
import utils from "@/utils";
import shop from "@/service/shop";
import order from "@/service/order";
import constants from "@/utils/constants";
import common from "@/service/common";

const fileList = ref<any[]>([]);

const formData = ref({
  shopId: 0,
  serialNo: "",
  capture: ""
});

const shopInfo = ref<ShopInfo>({
  shopId: 0,
  name: "",
  logo: "",
  intro: ""
});

Taro.useLoad(async query => {
  const scene = decodeScene(query.scene);
  if (scene) {
    formData.value.shopId = scene.shopId;
    formData.value.serialNo = scene.serialNo;
    const ret = await shop.getPublicShopInfo(scene.shopId);
    if (ret) {
      shopInfo.value = {
        shopId: scene.shopId,
        name: ret.shopName,
        logo: ret.logo,
        intro: ret.intro
      };
    }
  }
});

function decodeScene(scene: string) {
  if (scene) {
    const data = decodeURIComponent(scene).split("#");
    if (data.length == 2) {
      let shopId = Number(data[0]);
      let serialNo = data[1];
      return { shopId, serialNo };
    }
  }
}

const onUploadSuccess = ({ option }) => {
  formData.value.capture = option.formData.host + "/" + option.formData.key;
};

const onDeleteUpload = () => {
  formData.value.capture = "";
};

const onSubmit = async () => {
  Taro.requestSubscribeMessage({
    tmplIds: [constants.RECEIPT_MESSAGE_ID],
    entityIds: [],
    success: async () => {
      common.msgSubscribe(constants.RECEIPT_MESSAGE_ID);
      const ret = await order.create({
        shopId: formData.value.shopId,
        serialNo: formData.value.serialNo,
        capture: formData.value.capture
      });
      if (ret) {
        Taro.redirectTo({
          url: "/pages/consumer/order_list/order_list"
        });
      }
    }
  });
};
</script>

<style scoped></style>
