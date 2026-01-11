import api from "@/api/backend";
import { useHeadStore } from "@/store/head";
import utils from "@/utils";

class Common {
  private static instance: Common;

  private headStore = useHeadStore();

  private constructor() {}

  static getInstance() {
    if (!Common.instance) {
      Common.instance = new Common();
    }
    return Common.instance;
  }

  async getSignedUploadUrl() {
    const ret = await api.getSignedUploadUrl({
      head: this.headStore.head,
    });
    if (utils.success(ret.data)) {
      return ret.data.result!.signedUrl!;
    }
  }

  async msgSubscribe(templateId: string) {
    const ret = await api.msgSubscribe({
      head: this.headStore.head,
      param: {
        templateId: templateId,
      },
    });
    return utils.success(ret.data);
  }

  async getUploadFormData(): Promise<UploadForm | undefined> {
    const ret = await api.getUploadPolicy({
      head: this.headStore.head,
    });
    if (utils.success(ret.data)) {
      const data = ret.data.result!;
      return {
        host: data.host,
        policy: data.policy,
        signature: data.signature,
        OSSAccessKeyId: data.accessid, // 确保键名一致
        key: data.key, // 文件名
        success_action_status: 200,
      };
    }
  }
}

export interface UploadForm {
  OSSAccessKeyId: string;
  policy: string;
  signature: string;
  host: string;
  key: string;
  success_action_status: 200;
}
export default Common.getInstance();
