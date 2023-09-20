package com.landray.kmss.fssc.budget.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.landray.kmss.fssc.budget.util.HttpClientUtil;
import com.landray.kmss.util.FileUtil;


public class MoneyDataServer {

	public static void getToken() {
		Properties proer = null;
		try {
			proer = FileUtil.getProperties("zxgl-config.properties");
			String url=proer.getProperty("tokenUrl");
			 String account=proer.getProperty("account");
			 String password=proer.getProperty("password");
			 Map<String, String> param=new HashMap<>();
			 param.put("account", account);
			 param.put("password", password);
			String data=HttpClientUtil.doGet(url,param);
			System.out.println(data);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	public static String getCangKuData() {
		Properties proer = null;
		String result="";
		try {
			proer = FileUtil.getProperties("zxgl-config.properties");
			String url=proer.getProperty("cangkuUrl");
			String token=proer.getProperty("token");
			 Map<String, String> param=new HashMap<>();
			 param.put("token", token);
			 result=HttpClientUtil.doPost(url,param);
			System.out.println(result);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 耗材扣除数量
	 * @return
	 */
	public static String cutNumByHc() {
		Properties proer = null;
		String result="";
		try {
			proer = FileUtil.getProperties("zxgl-config.properties");
			String token=proer.getProperty("token");
			//耗材主表调用
			String cutNumByHc_MainUrl=proer.getProperty("cutNumByHc_MainUrl");
			cutHcMain(cutNumByHc_MainUrl, token);
			//耗材明细调用
			String cutNumByHc_DetailUrl=proer.getProperty("cutNumByHc_DetailUrl");
			cutHcDetail(cutNumByHc_DetailUrl, token);
			System.out.println(result);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
	
	
	private static String cutHcMain(String url,String token){
		String result="";
		String cgbh="";//skt32.skf466:采购编号
		String cgmc="";//skt32.skf467:采购名称
		String cgrq="";//skt32.skf468:采购日期
		String sqrxm="";//skt32.skf4701:申请人姓名
		String cgje="";//skt32.skf477:采购金额
		String gysmc="";//skt32.skf4703:供应商名称
		String sjje="";//skt32.skf699:实际金额
		String cgrxm="";//skt32.skf4702:请购人姓名
		 Map<String, String> mainParam=new HashMap<>();
		 mainParam.put("token", token);
		 mainParam.put("skt32.skf466", cgbh);
		 mainParam.put("skt32.skf467", cgmc);
		 mainParam.put("skt32.skf468", cgrq);
		 mainParam.put("skt32.skf4701", sqrxm);
		 mainParam.put("skt32.skf477", cgje);
		 mainParam.put("skt32.skf4703", gysmc);
		 mainParam.put("skt32.skf699", sjje);
		 mainParam.put("skt32.skf4702", cgrxm);
		 result=HttpClientUtil.doPost(url,mainParam);
		return result;
	}
	
	private static String cutHcDetail(String url,String token){
		String result="";
		String cgbh="";//skt20.skf276:采购单号
		String cgmc="";//skt20.skf4693:备件code
		String cgrq="";//skt32.skf468:采购日期
		String sqrxm="";//skt32.skf4701:申请人姓名
		String cgje="";//skt32.skf477:采购金额
		String gysmc="";//skt32.skf4703:供应商名称
		String sjje="";//skt32.skf699:实际金额
		String cgrxm="";//skt32.skf4702:请购人姓名
		 Map<String, String> detailParam=new HashMap<>();
		 detailParam.put("token", token);
		 detailParam.put("skt20.skf276:采购单号", cgbh);
		 detailParam.put("skt20.skf4693:备件code", cgmc);
		 detailParam.put("skt20.skf277:备件名称", cgrq);
		 detailParam.put("skt20.skf281:备件规格型号", sqrxm);
		 detailParam.put("skt20.skf4694:仓库名称", cgje);
		 detailParam.put("skt20.skf4704:单位名称", gysmc);
		 detailParam.put("skt20.skf285:数量", sjje);
		 detailParam.put("skt20.skf286:单件", cgrxm);
		 detailParam.put("skt20.skf680:金额", cgrxm);
		 result=HttpClientUtil.doPost(url,detailParam);
		return result;
	}
	
	public static void main(String[] args) {
//		getToken();
		getCangKuData();
	}
}
