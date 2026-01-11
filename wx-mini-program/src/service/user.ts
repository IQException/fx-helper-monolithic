import utils from "@/utils";
import Taro from "@tarojs/taro";
import { useHeadStore } from "@/store/head";
import api from "@/api/backend";

class User {
  private static instance?: User;

  private headStore = useHeadStore();
  private constructor() {}

  static getInstance() {
    if (!User.instance) {
      User.instance = new User();
    }
    return User.instance;
  }
  async login() {
    const wxRes = await Taro.login();
    if (utils.ok(wxRes)) {
      const ret = await api.login({
        head: this.headStore.head,
        param: {
          code: wxRes.code
        }
      });
      if (utils.success(ret.data)) {
        ret.cookies;
        const { userId, newUser } = ret.data.result!;
        return { userId, newUser };
      }
    } else {
      Taro.showToast({
        title: wxRes.errMsg,
        icon: "error",
        duration: 2000
      });
    }
  }
}

export default User.getInstance();
