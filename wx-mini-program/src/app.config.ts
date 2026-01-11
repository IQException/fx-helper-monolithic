export default defineAppConfig({
  usingComponents: {},
  pages: [
    "pages/index/index",
    "pages/shop/register/register",
    "pages/consumer/landing/landing",
    "pages/shop/shop_list/shop_list",
    "pages/shop/shop_detail/shop_detail",
    "pages/shop/order_list/order_list",
    "pages/consumer/order_list/order_list"
  ],
  window: {
    backgroundTextStyle: "light",
    navigationBarBackgroundColor: "#fff",
    navigationBarTitleText: "WeChat",
    navigationBarTextStyle: "black"
  },
  plugins: {
    chooseLocation: {
      version: "1.1.1",
      provider: "wx76a9a06e5b4e693e"
    }
  },
  permission: {
    "scope.userLocation": {
      desc: "你的位置信息将用于小程序定位"
    }
  }
});
