import api, { OrderCreateParam } from "@/api/backend";
import { useHeadStore } from "@/store/head";
import utils from "@/utils";
import constants from "@/utils/constants";

class Order {
  private static instance: Order;

  private headStore = useHeadStore();

  private constructor() {}

  static getInstance() {
    if (!Order.instance) {
      Order.instance = new Order();
    }
    return Order.instance;
  }

  async create(param: OrderCreateParam) {
    const ret = await api.createOrder({
      head: this.headStore.head,
      param: param
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }

  async listShopOrders(
    shopId: number,
    pageNo: number = 1,
    status?: number,
    from?: Date,
    to?: Date
  ) {
    const ret = await api.listShopOrders({
      head: this.headStore.head,
      param: {
        shopId: shopId,
        offset: (pageNo - 1) * constants.PAGE_COUNT,
        limit: constants.PAGE_COUNT,
        status: status,
        from: from ? from.toISOString() : from,
        to: to ? to.toISOString() : to
      }
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }

  async listUserOrders(
    pageNo: number = 1,
    status?: number,
    from?: Date,
    to?: Date
  ) {
    const ret = await api.listUserOrders({
      head: this.headStore.head,
      param: {
        offset: (pageNo - 1) * constants.PAGE_COUNT,
        limit: constants.PAGE_COUNT,
        status: status,
        from: from ? from.toISOString() : from,
        to: to ? to.toISOString() : to
      }
    });
    if (utils.success(ret.data)) {
      return ret.data.result!;
    }
  }

  async payOrder(orderId: number) {
    const ret = await api.payOrder({
      head: this.headStore.head,
      param: {
        orderId: orderId
      }
    });
    return utils.success(ret.data);
  }
}

export default Order.getInstance();
