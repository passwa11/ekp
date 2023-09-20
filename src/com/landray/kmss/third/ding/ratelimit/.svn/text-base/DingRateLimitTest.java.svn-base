package com.landray.kmss.third.ding.ratelimit;

import java.util.Random;

import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;

public class DingRateLimitTest {


	public static void test() throws InterruptedException {
		Random ra = new Random();
		for (int i = 0; i < 3; i++) {
			final long current = System.currentTimeMillis();
			for (int j = 0; j < 100; j++) {
				new Thread(new DingThread(i, current), "DingThread-" + i + j)
						.start();
			}
			int sleep = ra.nextInt(10);
			Thread.sleep(sleep * 10);
		}
	}

	public static class DingThread implements Runnable {
		private int i;
		private long current;

		public DingThread(int i, long current) {
			this.i = i;
			this.current = current;
		}

		@Override
		public void run() {
			try {
				ThirdDingTalkClient client = new ThirdDingTalkClient(
						"https://oapi.dingtalk.com/user/get");
				OapiUserGetRequest req = new OapiUserGetRequest();
				req.setUserid("16d141393ed37c06eb3f39c4e88b2f03");
				req.setHttpMethod("GET");
				OapiUserGetResponse rsp = client.execute(req,
						DingUtils.getDingApiService().getAccessToken());
				System.out.println(Thread.currentThread().getName()
						+ "耗时：" + (System.currentTimeMillis() - current) + "---"
						+ ("0".equals(rsp.getErrorCode()) ? "成功" : "失败"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
