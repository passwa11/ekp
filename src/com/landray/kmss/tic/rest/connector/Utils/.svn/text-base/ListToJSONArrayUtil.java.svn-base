package com.landray.kmss.tic.rest.connector.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.landray.kmss.sys.authentication.util.StringUtil;


/**
 * 
 * ekp普通列表页数据转JSONArray工具类
 * @author 何建华
 * @Date 2020/07/13
 * 
 */
public class ListToJSONArrayUtil {


	/**
	 * 将ekp通用列表数据转换成JSON数组
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static JSONArray listToJSONArrayList(JSONObject obj){
		JSONArray result=new JSONArray();
		try {
			if(obj!=null && obj.containsKey("datas")){
				JSONArray datas=obj.getJSONArray("datas");
				if(datas!=null && datas.size()>0){
					for(int i=0;i<datas.size();i++){
						JSONObject resultLine=new JSONObject(true);
						JSONArray dataLine=datas.getJSONArray(i);
						for(int j=0;j<dataLine.size();j++) {
							JSONObject propertyJson=dataLine.getJSONObject(j);
							String key=propertyJson.getString("col");
							Object value=propertyJson.get("value");
						    if(!"operations".equals(key)){
						    	resultLine.put(key.replace(".", "_"), handlerHtmlValue(value));
						    }
						}
						result.add(resultLine);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object handlerHtmlValue(Object value){
		if(value==null){
			return value;
		}
		if(value instanceof String){
			String newValue=value.toString();
			if(StringUtil.isNull(newValue)){
				return newValue;
			}
            if(newValue.indexOf("<span")>-1 && newValue.indexOf(">")>0){
            	try {
					Document doc = Jsoup.parse(newValue);
					Elements span = doc.getElementsByTag("span");
					newValue=span.text();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }else if(newValue.indexOf("<div")>-1 && newValue.indexOf(">")>0) {
            	try {
					Document doc = Jsoup.parse(newValue);
					Elements div = doc.getElementsByTag("div");
					newValue=div.text();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            return newValue;
		}else {
			return value;
		}
	}
	public static void main(String[] args) throws Exception{
		/*
		 * String
		 * str="{\\\"columns\\\":[{\\\"property\\\":\\\"fdId\\\"},{\\\"property\\\":\\\"index\\\"},{\\\"title\\\":\\\"数据源名称\\\",\\\"property\\\":\\\"fdName\\\"},{\\\"title\\\":\\\"格式\\\",\\\"property\\\":\\\"fdFormat.name\\\"},{\\\"property\\\":\\\"fdFormat.id\\\"},{\\\"title\\\":\\\"获取方式\\\",\\\"property\\\":\\\"fdSource.name\\\"},{\\\"property\\\":\\\"fdSource\\\"},{\\\"title\\\":\\\"环境名称\\\",\\\"property\\\":\\\"fdEnvName\\\"},{\\\"title\\\":\\\"场景名称\\\",\\\"property\\\":\\\"fdSceneName\\\"},{\\\"title\\\":\\\"操作\\\",\\\"property\\\":\\\"operations\\\",\\\"headerClass\\\":\\\"width:150\\\"},{}],\\\"datas\\\":[[{\\\"col\\\":\\\"fdId\\\",\\\"value\\\":\\\"173385a4dcbc759563ee1f34704a32e7\\\"},{\\\"col\\\":\\\"index\\\",\\\"value\\\":\\\"1\\\"},{\\\"col\\\":\\\"fdName\\\",\\\"value\\\":\\\"新闻数据源\\\"},{\\\"col\\\":\\\"fdFormat.name\\\",\\\"value\\\":\\\"资讯(蓝桥自定义组件)\\\"},{\\\"col\\\":\\\"fdFormat.id\\\",\\\"value\\\":\\\"dingpaas0000000information000000\\\"},{\\\"col\\\":\\\"fdSource.name\\\",\\\"value\\\":\\\"TIC\\\"},{\\\"col\\\":\\\"fdSource\\\",\\\"value\\\":\\\"tic_key\\\"},{\\\"col\\\":\\\"fdEnvName\\\",\\\"value\\\":\\\"蓝悦EKP自定义门户\\\"},{\\\"col\\\":\\\"fdSceneName\\\",\\\"value\\\":\\\"转换-蓝悦新闻\\\"},{\\\"col\\\":\\\"operations\\\",\\\"value\\\":\\\"<div class=\\\\\\\"conf_show_more_w\\\\\\\">\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t<div class=\\\\\\\"conf_btn_edit\\\\\\\">\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"javascript:void(0);\\\\\\\" onclick=\\\\\\\"openTestPage('tic_key','173385371eaf196104df5ec466e98c18','dingpaas0000000information000000')\\\\\\\"  style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">测试<\\\\/a>\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"/lding/common/lding_common_data_source/ldingCommonDataSource.do?method=edit&fdId=173385a4dcbc759563ee1f34704a32e7&from=edit\\\\\\\" style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">编辑<\\\\/a>\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"javascript:void(0);\\\\\\\" onclick=\\\\\\\"deleteById('173385a4dcbc759563ee1f34704a32e7')\\\\\\\" style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">删除<\\\\/a>\\\\r\\\\n\\\\t\\\\t       <\\\\/div>\\\\r\\\\n\\\\t\\\\t   <\\\\/div>\\\"}],[{\\\"col\\\":\\\"fdId\\\",\\\"value\\\":\\\"1733850eeda225b56a1134045568e97d\\\"},{\\\"col\\\":\\\"index\\\",\\\"value\\\":\\\"2\\\"},{\\\"col\\\":\\\"fdName\\\",\\\"value\\\":\\\"公告数据源\\\"},{\\\"col\\\":\\\"fdFormat.name\\\",\\\"value\\\":\\\"消息公告\\\"},{\\\"col\\\":\\\"fdFormat.id\\\",\\\"value\\\":\\\"dingpaas00000000noticeTips000000\\\"},{\\\"col\\\":\\\"fdSource.name\\\",\\\"value\\\":\\\"TIC\\\"},{\\\"col\\\":\\\"fdSource\\\",\\\"value\\\":\\\"tic_key\\\"},{\\\"col\\\":\\\"fdEnvName\\\",\\\"value\\\":\\\"蓝悦EKP自定义门户\\\"},{\\\"col\\\":\\\"fdSceneName\\\",\\\"value\\\":\\\"转换蓝悦公告\\\"},{\\\"col\\\":\\\"operations\\\",\\\"value\\\":\\\"<div class=\\\\\\\"conf_show_more_w\\\\\\\">\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t<div class=\\\\\\\"conf_btn_edit\\\\\\\">\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"javascript:void(0);\\\\\\\" onclick=\\\\\\\"openTestPage('tic_key','173384c6ea91552f36672a74bc58daff','dingpaas00000000noticeTips000000')\\\\\\\"  style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">测试<\\\\/a>\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"/lding/common/lding_common_data_source/ldingCommonDataSource.do?method=edit&fdId=1733850eeda225b56a1134045568e97d&from=edit\\\\\\\" style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">编辑<\\\\/a>\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"javascript:void(0);\\\\\\\" onclick=\\\\\\\"deleteById('1733850eeda225b56a1134045568e97d')\\\\\\\" style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">删除<\\\\/a>\\\\r\\\\n\\\\t\\\\t       <\\\\/div>\\\\r\\\\n\\\\t\\\\t   <\\\\/div>\\\"}],[{\\\"col\\\":\\\"fdId\\\",\\\"value\\\":\\\"173382e85f04daeddd51ad542c082002\\\"},{\\\"col\\\":\\\"index\\\",\\\"value\\\":\\\"3\\\"},{\\\"col\\\":\\\"fdName\\\",\\\"value\\\":\\\"轮播数据源\\\"},{\\\"col\\\":\\\"fdFormat.name\\\",\\\"value\\\":\\\"banner轮播\\\"},{\\\"col\\\":\\\"fdFormat.id\\\",\\\"value\\\":\\\"dingpaas00000000dingSwiper000000\\\"},{\\\"col\\\":\\\"fdSource.name\\\",\\\"value\\\":\\\"TIC\\\"},{\\\"col\\\":\\\"fdSource\\\",\\\"value\\\":\\\"tic_key\\\"},{\\\"col\\\":\\\"fdEnvName\\\",\\\"value\\\":\\\"蓝悦EKP自定义门户\\\"},{\\\"col\\\":\\\"fdSceneName\\\",\\\"value\\\":\\\"转换轮播新闻\\\"},{\\\"col\\\":\\\"operations\\\",\\\"value\\\":\\\"<div class=\\\\\\\"conf_show_more_w\\\\\\\">\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t<div class=\\\\\\\"conf_btn_edit\\\\\\\">\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"javascript:void(0);\\\\\\\" onclick=\\\\\\\"openTestPage('tic_key','1733825b3de560389c2c1cf4c87a4e4b','dingpaas00000000dingSwiper000000')\\\\\\\"  style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">测试<\\\\/a>\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"/lding/common/lding_common_data_source/ldingCommonDataSource.do?method=edit&fdId=173382e85f04daeddd51ad542c082002&from=edit\\\\\\\" style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">编辑<\\\\/a>\\\\r\\\\n\\\\t\\\\t\\\\t\\\\t\\\\t<a class=\\\\\\\"btn_txt\\\\\\\" href=\\\\\\\"javascript:void(0);\\\\\\\" onclick=\\\\\\\"deleteById('173382e85f04daeddd51ad542c082002')\\\\\\\" style=\\\\\\\"color:#4285f4;\\\\\\\" target=\\\\\\\"_self\\\\\\\">删除<\\\\/a>\\\\r\\\\n\\\\t\\\\t       <\\\\/div>\\\\r\\\\n\\\\t\\\\t   <\\\\/div>\\\"}]],\\\"page\\\":{\\\"currentPage\\\":1,\\\"pageSize\\\":15,\\\"totalSize\\\":3}}"
		 * ; System.out.println(
		 * listToJSONArrayList(JSONObject.fromObject(str)).toString());;
		 */
		/*
		 * String fdContent=
		 * "<span title=\"研讨会\">  \r\n\t\t             研讨会\r\n\t\t            </span>";
		 * Document doc = Jsoup.parse(fdContent); Elements span =
		 * doc.getElementsByTag("span"); System.out.println(span.attr("title"));
		 * System.out.println(span.text());
		 */
		
		String fdContent= "<div title=\"研讨会\">123</div>";
		Document doc = Jsoup.parse(fdContent);
    	Elements div = doc.getElementsByTag("div");
    	System.out.println(div.attr("title"));
    	System.out.println(div.text());
	}
}
