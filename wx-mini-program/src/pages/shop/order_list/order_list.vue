<template>
  <view class="fx-body">
    <view class="filter-button">
      <nut-button shape="square" :type="btTypes.btAll" @click="onAllClick"
        >全部</nut-button
      >
      <nut-button shape="square" :type="btTypes.bt24H" @click="onClick24H"
        >24H</nut-button
      >
      <nut-button shape="square" :type="btTypes.bt7D" @click="onClick7D"
        >7日</nut-button
      >
      <nut-button shape="square" :type="btTypes.btMore" @click="onClickMore"
        >更多</nut-button
      >
    </view>

    <view class="filter-button">
      <nut-button
        shape="square"
        :type="btStatusTypes.all"
        @click="onAllStatusClick"
        >全部</nut-button
      >
      <nut-button
        shape="square"
        :type="btStatusTypes.success"
        @click="onSuccessClick"
        >已返现</nut-button
      >
      <nut-button
        shape="square"
        :type="btStatusTypes.created"
        @click="onCreatedClick"
        >待处理</nut-button
      >
      <nut-button
        shape="square"
        :type="btStatusTypes.processing"
        @click="onProcessingClick"
        >处理中</nut-button
      >
      <nut-button
        shape="square"
        :type="btStatusTypes.fail"
        @click="onFailureClick"
        >失败</nut-button
      >
    </view>
    <view style="align-self: stretch">
      <order v-for="o in shopOrders" :key="o.id" :order="o" />

      <!--       <nut-backtop height="calc(100vh - 100px)">
        <template #content>
          <order v-for="o in shopOrders" :key="o.id" :order="o" />
        </template>
      </nut-backtop> -->
    </view>
    <nut-popup
      v-model:visible="showDatePicker"
      position="bottom"
      round
      pop-class="filter-modal"
    >
      <template #default>
        <view class="filter-modal-label">自定义筛选</view>
        <view class="filter-modal-datepicker">
          <nut-input
            v-model="searchFrom"
            placeholder="选择起始时间"
            :readonly="true"
            @click-input="clickInput('from')"
          />
          <nut-popup v-model:visible="showFrom" position="bottom">
            <nut-date-picker
              v-model="searchFromInit"
              :three-dimensional="false"
              @confirm="confirmFrom"
              @cancel="showFrom = false"
            ></nut-date-picker>
          </nut-popup>
          <text>至</text>
          <nut-input
            v-model="searchTo"
            placeholder="选择截止时间"
            :readonly="true"
            @click-input="clickInput('to')"
          />
          <nut-popup v-model:visible="showTo" position="bottom">
            <nut-date-picker
              v-model="searchToInit"
              :three-dimensional="false"
              @confirm="confirmTo"
              @cancel="showTo = false"
            ></nut-date-picker>
          </nut-popup>
        </view>
        <view class="filter-model-button">
          <nut-button shape="square" type="primary" @click="confirm"
            >&nbsp;确&nbsp;定&nbsp;</nut-button
          >
        </view>
      </template>
    </nut-popup>
  </view>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import utils from "@/utils";
import "./order_list.scss";
import Order from "./order.vue";
import { ShopOrder } from "./order.vue";
import Taro from "@tarojs/taro";
import order from "@/service/order";
import { ShopOrderDetail } from "@/api/backend";
import constants from "@/utils/constants";
import { ButtonType } from "@nutui/nutui-taro";

const shopOrders = ref<ShopOrder[]>([]);

const init = new Date();
const searchFromInit = computed(() =>
  searchFrom.value ? new Date(searchFrom.value) : init
);
const searchToInit = computed(() =>
  searchTo.value ? new Date(searchTo.value) : init
);

// search params
const shopId = ref<number>(0);
const pageNo = ref<number>(1);
const status = ref<number | undefined>(undefined);
const searchFrom = ref<string | undefined>(undefined);
const searchTo = ref<string | undefined>(undefined);

const btTypes = ref({
  btAll: "primary" as ButtonType,
  bt24H: "default" as ButtonType,
  bt7D: "default" as ButtonType,
  btMore: "default" as ButtonType,
});

function resetBtTypes() {
  btTypes.value = {
    btAll: "default",
    bt24H: "default",
    bt7D: "default",
    btMore: "default",
  };
}

const btStatusTypes = ref({
  all: "primary" as ButtonType,
  created: "default" as ButtonType,
  processing: "default" as ButtonType,
  success: "default" as ButtonType,
  fail: "default" as ButtonType,
});

function resetBtStatusPlains() {
  btStatusTypes.value = {
    all: "default",
    created: "default",
    processing: "default",
    success: "default",
    fail: "default",
  };
}

// filter time
const onAllClick = () => {
  resetBtTypes();
  btTypes.value.btAll = "primary";
  searchFrom.value = undefined;
  searchTo.value = undefined;
  refresh();
};
const onClick24H = () => {
  resetBtTypes();
  btTypes.value.bt24H = "primary";
  searchFrom.value = utils.formatDate(utils.minus24H(new Date()).getTime());
  searchTo.value = undefined;
  refresh();
};
const onClick7D = () => {
  resetBtTypes();
  btTypes.value.bt7D = "primary";
  searchFrom.value = utils.formatDate(utils.minus7D(new Date()).getTime());
  searchTo.value = undefined;
  refresh();
};
const onClickMore = () => {
  resetBtTypes();
  btTypes.value.btMore = "primary";
  showDatePicker.value = true;
};

// filter status
const onAllStatusClick = () => {
  resetBtStatusPlains();
  btStatusTypes.value.all = "primary";
  status.value = undefined;
  refresh();
};
const onCreatedClick = () => {
  resetBtStatusPlains();
  btStatusTypes.value.created = "primary";
  status.value = constants.ORDER_STATUS.CREATED;
  refresh();
};
const onProcessingClick = () => {
  resetBtStatusPlains();
  btStatusTypes.value.processing = "primary";
  status.value = constants.ORDER_STATUS.PROCESSING;
  refresh();
};
const onSuccessClick = () => {
  resetBtStatusPlains();
  btStatusTypes.value.success = "primary";
  status.value = constants.ORDER_STATUS.SUCCEED;
  refresh();
};
const onFailureClick = () => {
  resetBtStatusPlains();
  btStatusTypes.value.fail = "primary";
  status.value = constants.ORDER_STATUS.FAILED;
  refresh();
};

// page life cycle
Taro.useLoad((options) => {
  shopId.value = options.shopId;

  if (options.status) {
    status.value = Number(options.status);
    resetBtStatusPlains();
  }

  switch (status.value) {
    case constants.ORDER_STATUS.CREATED: {
      btStatusTypes.value.created = "primary";
      break;
    }
    case constants.ORDER_STATUS.PROCESSING: {
      btStatusTypes.value.processing = "primary";
      break;
    }
    case constants.ORDER_STATUS.SUCCEED: {
      btStatusTypes.value.success = "primary";
      break;
    }
    case constants.ORDER_STATUS.FAILED: {
      btStatusTypes.value.fail = "primary";
      break;
    }
    default: {
      btStatusTypes.value.all = "primary";
      break;
    }
  }
  refresh();
});

Taro.useReachBottom(() => {
  append();
});

// loader
const refresh = async () => {
  pageNo.value = 1;
  let _shopOrders = await getShopOrders();
  shopOrders.value = _shopOrders ? _shopOrders : [];
};
const append = async () => {
  pageNo.value = pageNo.value + 1;
  let _shopOrders = await getShopOrders();
  if (_shopOrders && _shopOrders.length > 0) {
    shopOrders.value.push(..._shopOrders);
  } else {
    pageNo.value = pageNo.value - 1;
    Taro.showToast({
      title: "我也是有底线的！",
      icon: "error",
      duration: 2000,
    });
  }
};

const getShopOrders = async () => {
  const _shopOrders = await order.listShopOrders(
    shopId.value,
    pageNo.value,
    status.value,
    searchFrom.value ? new Date(searchFrom.value) : undefined,
    searchTo.value ? new Date(searchTo.value) : undefined
  );

  return _shopOrders?.orderList?.map(buildOrder);
};

// datepicker
const showDatePicker = ref(false);
const showFrom = ref(false);
const confirmFrom = ({ selectedValue }) => {
  searchFrom.value = selectedValue.join("-");
  showFrom.value = false;
};

const showTo = ref(false);
const confirmTo = ({ selectedValue }) => {
  searchTo.value = selectedValue.join("-");
  showTo.value = false;
};

const confirm = () => {
  showDatePicker.value = false;
  refresh();
};
const clickInput = (src: string) => {
  if (src === "from") showFrom.value = true;
  else if (src === "to") showTo.value = true;
};

// builder
const buildOrder = (orderDetail: ShopOrderDetail): ShopOrder => {
  return {
    id: orderDetail.id,
    nickName: orderDetail.nickName,
    avatar: orderDetail.avatar,
    amount: orderDetail.amount,
    payTime: orderDetail.fxTime,
    capture: orderDetail.capture,
    failMsg: orderDetail.failMsg,
    createTime: orderDetail.createdAt,
    status: orderDetail.status,
  };
};
</script>
