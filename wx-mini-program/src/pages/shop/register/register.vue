<template>
  <nut-navbar :title="title" />

  <view class="fx-body">
    <nut-form label-position="top" star-position="right">
      <nut-form-item label="店铺名称" required>
        <nut-input
          v-model="formData.name"
          placeholder="与您其他平台上的店铺名称保持一致"
          type="text"
          max-length="20"
          clearable
        />
      </nut-form-item>
      <nut-form-item label="店铺地址" required>
        <nut-input
          v-model="formData.addr"
          placeholder="点击选择地址"
          max-length="30"
          clearable
          type="text"
          @click-input="onAddrClick"
        >
          <template #right>
            <Locationg3 color="red" size="1.5rem" @click="onAddrClick" />
          </template>
        </nut-input>
      </nut-form-item>
      <nut-form-item label="联系电话" required>
        <nut-input
          v-model="formData.tel"
          placeholder="平台与您的业务联络方式"
          type="tel"
          max-length="11"
          clearable
        />
      </nut-form-item>
      <nut-form-item v-if="!edit" label="支付密码" required>
        <nut-input
          v-model="formData.secret"
          type="password"
          clearable
          max-length="18"
        />
      </nut-form-item>
      <nut-form-item label="店铺LOGO" required>
        <nut-row type="flex" justify="start" style="{line-height:100px;}">
          <image
            v-if="edit"
            mode="aspectFill"
            class="logo"
            :src="formData.logo"
          />

          <nut-uploader
            class="logo"
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
        </nut-row>
      </nut-form-item>
      <nut-form-item label="店铺简介" label-position="left">
        <nut-textarea
          v-model="formData.intro"
          placeholder="简要介绍下您的店铺，帮助营销您的产品。默认：欢迎光临，很高兴为您服务！"
          limit-show
          max-length="100"
          :autosize="{ minHeight: 100, maxHeight: 150 }"
        />
      </nut-form-item>
    </nut-form>
    <nut-button style="margin-top: 10px" block type="primary" @click="submit">{{
      submitButton
    }}</nut-button>
  </view>
</template>

<script setup lang="ts">
import Taro from "@tarojs/taro";
import { Locationg3, ShareN } from "@nutui/icons-vue-taro";
import "./register.css";
import { computed, ref } from "vue";
import "../../../app.css";
import { requirePlugin } from "@tarojs/taro";
import { Location } from "@/api/wx";
import shop from "@/service/shop";
import utils from "@/utils";

const edit = ref(false);
const submitButton = computed(() => (edit.value ? "保存" : "注册"));
const title = computed(() => (edit.value ? "店铺信息" : "店铺注册"));
//const category = "生活服务,娱乐休闲";
const formData = ref({
  shopId: 0,
  name: "",
  tel: "",
  addr: "",
  logo: "",
  secret: "",
  intro: "",
});
const fileList = ref<any[]>([]);

const locationPlugin = requirePlugin("chooseLocation");

Taro.useLoad(async (options) => {
  if (options.edit) {
    edit.value = true;
    const ret = await shop.getShop(options.shopId);
    if (ret) {
      formData.value = {
        shopId: ret.shopId,
        name: ret.shopName,
        logo: ret.logo,
        tel: ret.phone,
        addr: ret.address,
        intro: ret.intro,
        secret: "",
      };
    }
  }
});

Taro.useDidShow(() => {
  const location: Location = locationPlugin.getLocation();
  if (location) {
    formData.value.addr = location.address;
    loc = JSON.stringify({
      latitude: location.latitude,
      longitude: location.longitude,
    });
  }
});

Taro.useUnload(() => {
  locationPlugin.setLocation(null);
});

const submit = async () => {
  var ret;
  if (edit.value) {
    ret = await shop.update(buildShop());
    if (ret) {
      Taro.navigateBack();
    }
  } else {
    if (formData.value.intro.trim() == "") {
      formData.value.intro = "欢迎光临，很高兴为您服务！";
    }
    ret = await shop.create(buildShop());
    if (ret) {
      Taro.redirectTo({
        url: "/pages/shop/shop_detail/shop_detail?shopId=" + ret.shopId,
      });
    }
  }
};

var loc = "";

const onAddrClick = () => {
  const key = "LCVBZ-XFWH5-RI3IE-IIPNB-ZSD4F-DHBMO"; //使用在腾讯位置服务申请的key
  const referer = "返奖小助手"; //调用插件的app的名称
  Taro.navigateTo({
    url:
      "plugin://chooseLocation/index?key=" +
      key +
      "&referer=" +
      referer +
      "&location=" +
      loc,
    /*       "&category=" +
      category, */
  });
};
const onUploadSuccess = ({ option }) => {
  formData.value.logo = option.formData.host + "/" + option.formData.key;
};
const onDeleteUpload = () => {
  formData.value.logo = "";
};

const buildShop = () => {
  return {
    address: formData.value.addr,
    intro: formData.value.intro,
    logo: formData.value.logo,
    name: formData.value.name,
    paySecret: formData.value.secret,
    phone: formData.value.tel,
    shopId: formData.value.shopId,
  };
};
</script>

<style scoped></style>
