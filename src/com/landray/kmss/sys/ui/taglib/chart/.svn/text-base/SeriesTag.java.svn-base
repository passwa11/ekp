package com.landray.kmss.sys.ui.taglib.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class SeriesTag extends BaseTag {
	private String name;
	private String type;
	private String stack;
	private String forY2;
	private String barWidth;
	//数据部分  支持(String,List<String>,list<Map>)
	public Object data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getForY2() {
		return forY2;
	}

	public void setForY2(String forY2) {
		this.forY2 = forY2;
	}
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String getBarWidth() {
		return barWidth;
	}

	public void setBarWidth(String barWidth) {
		this.barWidth = barWidth;
	}

	public JSONObject seriesdata = new JSONObject();
	public JSONObject bulidJsonDatas() {
		if(StringUtil.isNotNull(this.name)){
			seriesdata.element ("name", this.name);
		}
		if(StringUtil.isNotNull(this.type)){
			//区域图处理
			if("linearea".equals(this.type)){
				seriesdata.element ("type", "line");
				seriesdata.element ("smooth", true);
				JSONObject itemStyle = new JSONObject();
				JSONObject normal = new JSONObject();
				JSONObject areaStyle = new JSONObject();
				areaStyle.element("type", "default");
				normal.element("areaStyle", areaStyle);
				itemStyle.element("normal", normal);
				seriesdata.element ("itemStyle", itemStyle);
			}else{
				seriesdata.element ("type", this.type);

				if("gauge".equals(this.type)){
					JSONObject detail = new JSONObject();
					detail.element("formatter", "{value}%");
					seriesdata.element ("detail", detail);
				}
				if("pie".equals(this.type)){
					JSONObject tooltip = new JSONObject();
					tooltip.element("trigger", "item");
					tooltip.element("formatter", "{a} <br/>{b} : {c} ({d}%)");
					seriesdata.element ("tooltip", tooltip);
				}
			}
			if ("bar".equals(this.type)) {
				if (StringUtil.isNotNull(this.barWidth)) {
					seriesdata.element("barWidth", this.barWidth);
				}
			}
		}
		if(StringUtil.isNotNull(this.stack)){
			seriesdata.element ("stack", this.stack);
		}
		ChartDataTag parent = (ChartDataTag) findAncestorWithClass(this, ChartDataTag.class);
		if(!"pie".equals(this.type)){
			JSONArray yAxis = parent.option.getJSONArray("yAxis");
				if("true".equals(parent.reverseXY)){
					if(StringUtil.isNotNull(this.forY2)){
						// 设置了使用第二y轴且存在第二y轴
						if ("true".equals(this.forY2) && yAxis.size() > 1) {
							 seriesdata.element ("xAxisIndex", "1");
						}else{
							 seriesdata.element ("xAxisIndex", "0");
						}
					}else{
						seriesdata.element ("xAxisIndex", "0");
					}
				}else{
					if(StringUtil.isNotNull(this.forY2)){
						// 设置了使用第二y轴且存在第二y轴
						if ("true".equals(this.forY2) && yAxis.size() > 1) {
							 seriesdata.element ("yAxisIndex", "1");
						}else{
							 seriesdata.element ("yAxisIndex", "0");
						}
					}else{
						seriesdata.element ("yAxisIndex", "0");
					}
				}
		}
		 JSONArray dataobj = new JSONArray();
		 if(this.data!=null){
		     if(this.data instanceof String){
		    	 String[] dataArray =((String)this.data).split(",");
		    	 dataobj = JSONArray.fromObject(dataArray);
		    	 seriesdata.element("data", dataobj);
		     }
	         if(this.data instanceof List){
	        	 if ("pie".equals(this.type) || "gauge".equals(this.type)) {
					List list = (List) this.data;
					if (list != null && list.size() > 0) {
						Object obj = list.get(0);
						if (obj instanceof Map) {
							dataobj = JSONArray.fromObject(this.data);
							seriesdata.element("data", dataobj);
						} else {
							Object xDataObj = parent.option
									.getJSONArray("xAxis")
									.getJSONObject(0).get("data");
							if (xDataObj != null) {
								JSONArray xData = (JSONArray) xDataObj;
								List newData = new ArrayList();
								List dataList = (List) this.data;
								for (int i = 0; i < dataList.size(); i++) {
									Map map = new HashMap();
									map.put("value", dataList.get(i));
									map.put("name", xData.get(i));
									newData.add(map);
								}
								dataobj = JSONArray.fromObject(newData);
								seriesdata.element("data", dataobj);
							} else {
								List newData = new ArrayList();
								List dataList = (List) this.data;
								for (int i = 0; i < dataList.size(); i++) {
									Map map = new HashMap();
									map.put("value", dataList.get(i));
									map.put("name", dataList.get(i));
									newData.add(map);
								}
								dataobj = JSONArray.fromObject(newData);
								seriesdata.element("data", dataobj);
							}

						}
					} else {
						seriesdata.element("data", dataobj);
					}

				} else {
					dataobj = JSONArray.fromObject(this.data);
					seriesdata.element("data", dataobj);
				}
		     }	         	         
	     }
		return seriesdata;
	}
	
	
	@Override
	public int doStartTag() throws JspException {
		bulidJsonDatas();
		return EVAL_BODY_BUFFERED;
	}

	@Override
    public int doEndTag() throws JspException {
		try {
			Tag parent = findAncestorWithClass(this, ChartDataTag.class);
			if (parent instanceof ChartDataTag) {
				ChartDataTag parentTag = (ChartDataTag) parent;
				parentTag.addSerise(seriesdata);
				//饼图legend处理
				if (parentTag.option.get("legend") != null) {
				if("pie".equals(this.type)){
					JSONArray dataobj  = (JSONArray)seriesdata.get("data");
					
						JSONArray legendData = parentTag.option.getJSONObject(
								"legend").getJSONArray("data");
						for (int j = 0; j < dataobj.size(); j++) {
							String name = dataobj.getJSONObject(j).getString(
									"name");
							if (!legendData.contains(name)) {
								parentTag.addLedendData(name);
							}
						}
				}else{
					parentTag.addLedendData(this.name);
				}
			  }
			}
		} catch (Exception e) {
			logger.error(e.toString());
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}
	@Override
	public void release() {
		name = null;
		type = null;
		stack = null;
		data = null;
		forY2 = null;
		seriesdata.clear();
		super.release();
	}
}
