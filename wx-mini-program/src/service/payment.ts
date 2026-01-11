import api from "@/api/backend";
import { useHeadStore } from "@/store/head";
import utils from "@/utils";
import constants from "@/utils/constants";

class Payment {
  private static instance: Payment;

  private headStore = useHeadStore();

  private constructor() {}

  static getInstance() {
    if (!Payment.instance) {
      Payment.instance = new Payment();
    }
    return Payment.instance;
  }

  async deposit(amount: number) {
    const ret = await api.deposit({
      head: this.headStore.head,
      param: {
        amount: amount
      }
    });
    return utils.success(ret.data);
  }

  async withdraw(amount: number, secret: string) {
    const ret = await api.withdraw(
      {
        head: this.headStore.head,
        param: {
          amount: amount
        }
      },
      {
        header: { [constants.HEADER_PAY_SECRET]: secret }
      }
    );
    return utils.success(ret.data);
  }
}

export default Payment.getInstance();
