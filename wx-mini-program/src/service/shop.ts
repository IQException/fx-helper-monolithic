import api, {
  ShopCreateParam,
  ShopUpdateParam,
  GetShopParam
} from "@/api/backend";
import { useHeadStore } from "@/store/head";
import utils from "@/utils";

class Shop {
  private static instance: Shop;

  private headStore = useHeadStore();

  private constructor() {}

  static getInstance() {
    if (!Shop.instance) {
      Shop.instance = new Shop();
    }
    return Shop.instance;
  }

  async create(param: ShopCreateParam) {
    const ret = await api.createShop({
      head: this.headStore.head,
      param: param
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }

  async update(param: ShopUpdateParam) {
    const ret = await api.updateShop({
      head: this.headStore.head,
      param: param
    });
    return utils.success(ret.data);
  }

  async getCompositeShop(shopId: number) {
    const ret = await api.getShopDetail({
      head: this.headStore.head,
      param: { shopId: shopId }
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }

  async getShop(shopId: number) {
    const ret = await api.queryShop({
      head: this.headStore.head,
      param: { shopId: shopId }
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }

  async getPublicShopInfo(shopId: number) {
    const ret = await api.getPublicShopInfo({
      head: this.headStore.head,
      param: { shopId: shopId }
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }

  async list() {
    const ret = await api.listShops({
      head: this.headStore.head
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }

  async switchFX(shopId: number, val: 0 | 1) {
    const ret = await api.switchFx({
      head: this.headStore.head,
      param: {
        shopId: shopId,
        switchValue: val
      }
    });

    return utils.success(ret.data);
  }

  async getQrCodes(shopId: number, num: number = 1) {
    const ret = await api.getQrCodes({
      head: this.headStore.head,
      param: {
        shopId: shopId,
        number: num
      }
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }
  async getQrCode(shopId: number) {
    const ret = await this.getQrCodes(shopId);
    return ret?.qrCodes.at(0);
  }
}

export default Shop.getInstance();
