package com.landray.kmss.sys.ui.taglib.chart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class ChartDataTag extends BaseTag {

	// 主标题
	public String text;
	// 标题的展现位置 center/left/right,top/bottom|center
	public String textPosition;
	// 副标题
	public String subText;

	// 提示信息显示格式
	public String tooltipLabel;

	// 自适应宽度
	public boolean isAdapterSize;

	public boolean getIsAdapterSize() {
		return isAdapterSize;
	}

	public void setIsAdapterSize(boolean isAdapterSize) {
		this.isAdapterSize = isAdapterSize;
	}

	// x坐标轴名称
	public String xAxisName;

	// x坐标显示格式
	public String xAxisLabel;

	// x坐标轴数据 支持(String/List<String>)
	public Object xAxisData;

	public Object getxAxisData() {
		return xAxisData;
	}

	public void setxAxisData(Object xAxisData) {
		this.xAxisData = xAxisData;
	}

	// y坐标轴名称
	public String yAxisName;

	// y坐标显示格式
	public String yAxisLabel;
	// 第二y轴名称
	public String y2AxisName;

	// 第二y轴显示格式
	public String y2AxisLabel;

	// center/left/right,top/bottom|center
	public String legendPosition;
	// horizontal/vertical
	public String legendOrient;
	// 是否互换x轴和y轴
	public String reverseXY;
	// x轴数据多的时候，是否显示所有x轴数据，默认显示单数的数据
	public String xAxisShowAll;
	// x轴文字旋转的角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90 ~ 90
	public String xAxisRotate;

	public String dataZoom;

	public String getDataZoom() {
		return dataZoom;
	}

	public void setDataZoom(String dataZoom) {
		this.dataZoom = dataZoom;
	}

	public String getxAxisRotate() {
		return xAxisRotate;
	}

	public void setxAxisRotate(String axisRotate) {
		xAxisRotate = axisRotate;
	}

	public String getxAxisShowAll() {
		return xAxisShowAll;
	}

	public void setxAxisShowAll(String axisShowAll) {
		xAxisShowAll = axisShowAll;
	}

	public String getReverseXY() {
		return reverseXY;
	}

	public void setReverseXY(String reverseXY) {
		this.reverseXY = reverseXY;
	}

	public String zoomCount;

	public String getZoomCount() {
		return zoomCount;
	}

	public void setZoomCount(String zoomCount) {
		this.zoomCount = zoomCount;
	}

	public String gridMargin;

	public String getGridMargin() {
		return gridMargin;
	}

	public void setGridMargin(String gridMargin) {
		this.gridMargin = gridMargin;
	}

	public String getZoomAlign() {
		if (StringUtil.isNotNull(this.zoomAlign)) {
            return zoomAlign;
        } else {
            return "right";
        }
	}

	public void setZoomAlign(String zoomAlign) {
		this.zoomAlign = zoomAlign;
	}

	public String zoomAlign;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTextPosition() {
		return textPosition;
	}

	public void setTextPosition(String textPosition) {
		this.textPosition = textPosition;
	}

	public String getSubText() {
		return subText;
	}

	public void setSubText(String subText) {
		this.subText = subText;
	}

	public String getTooltipLabel() {
		return tooltipLabel;
	}

	public void setTooltipLabel(String tooltipLabel) {
		this.tooltipLabel = tooltipLabel;
	}

	public String getxAxisLabel() {
		return xAxisLabel;
	}

	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}

	public String getxAxisName() {
		return xAxisName;
	}

	public void setxAxisName(String xAxisName) {
		this.xAxisName = xAxisName;
	}

	public String getyAxisName() {
		return yAxisName;
	}

	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}

	public String getyAxisLabel() {
		return yAxisLabel;
	}

	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}

	public String gety2AxisName() {
		return y2AxisName;
	}

	public void sety2AxisName(String y2AxisName) {
		this.y2AxisName = y2AxisName;
	}

	public String gety2AxisLabel() {
		return y2AxisLabel;
	}

	public void sety2AxisLabel(String y2AxisLabel) {
		this.y2AxisLabel = y2AxisLabel;
	}

	public String getLegendPosition() {
		return legendPosition;
	}

	public void setLegendPosition(String legendPosition) {
		this.legendPosition = legendPosition;
	}

	public String getLegendOrient() {
		return legendOrient;
	}

	public void setLegendOrient(String legendOrient) {
		this.legendOrient = legendOrient;
	}

	public JSONArray ledendData = new JSONArray();

	public JSONArray getLedendData() {
		if (option.get("legend") != null) {
			return option.getJSONObject("legend").getJSONArray("data");
		} else {
			return ledendData;
		}
	}

	public void setLedendData(JSONArray ledendData) {
		this.ledendData = ledendData;
	}

	public void addLedendData(String datas) {
		getLedendData().add(datas);
	}

	public JSONArray serise = new JSONArray();

	public JSONArray getSerise() {
		return option.getJSONArray("series");
	}

	public void setSerise(JSONArray serise) {
		this.serise = serise;
	}

	public void addSerise(JSONObject datas) {
		getSerise().add(datas);
	}

	public void buildTitle(JSONObject option) {
		JSONObject title = new JSONObject();
		if (!"hidden".equals(this.textPosition)) {
			if (StringUtil.isNotNull(this.text)) {
				title.element("text", this.text);
			}
			if (StringUtil.isNotNull(this.subText)) {
				title.element("subtext", this.subText);
			}
			if (StringUtil.isNotNull(this.text)) {
				if (StringUtil.isNotNull(this.textPosition)) {
					String[] positionArray = this.textPosition.split("\\|");
					if (positionArray.length > 1) {
						title.element("x", positionArray[0]);
						title.element("y", positionArray[1]);
						title.element("left", positionArray[0]);
						title.element("top", positionArray[1]);
					} else {
						title.element("left", positionArray[0]);
					}
				} else {
					title.element("x", "center");
					title.element("y", "top");
					title.element("left", "center");
					title.element("top", "top");
				}
				JSONObject textStyle = new JSONObject();
				textStyle.element("fontFamily",
						"Microsoft YaHei, Geneva, sans-serif, SimSun");
				textStyle.element("fontSize", "18");
				// textStyle.element("fontWeight", "normal");

				title.element("textStyle", textStyle);
			}
			option.element("title", title);
		}
	}

	public void buildTooltip(JSONObject option) {
		JSONObject tooltip = new JSONObject();
		tooltip.element("trigger", "axis");
		tooltip.element("enterable", true);
		tooltip.element("confine", true);
		if (StringUtil.isNotNull(this.tooltipLabel)) {
			tooltip.element("formatter", this.tooltipLabel);
		}
		JSONObject textStyle = new JSONObject();
		textStyle.element("align", "left");
		tooltip.element("textStyle", textStyle);
		option.element("tooltip", tooltip);
	}

	public void buildLegend(JSONObject option) {
		JSONObject legend = new JSONObject();
		legend.element("type", "scroll");
		if (!"hidden".equals(this.legendPosition)) {
			if (StringUtil.isNotNull(this.legendPosition)) {
				String[] positionArray = this.legendPosition.split("\\|");
				if (positionArray.length > 1) {
					legend.element("x", positionArray[0]);
					legend.element("y", positionArray[1]);
					legend.element("left", positionArray[0]);
					legend.element("top", positionArray[1]);
				} else {
					legend.element("x", positionArray[0]);
					legend.element("left", positionArray[0]);
				}
			} else {
				// 默认上居中
				legend.element("x", "center");
				legend.element("y", "bottom");
				legend.element("left", "center");
				legend.element("top", "bottom");
			}
			if (StringUtil.isNotNull(this.legendOrient)) {
				legend.element("orient", this.legendOrient);
			} else {
				// 默认水平摆放
				legend.element("orient", "horizontal");
			}
			// legend.element("y", "bottom");
			legend.element("data", this.ledendData);
			option.element("legend", legend);
		}
	}

	public void buildToolbox(JSONObject option) {
		JSONObject toolbox = new JSONObject();
		toolbox.element("show", true);
		JSONObject feature = new JSONObject();
		JSONObject restore = new JSONObject();
		restore.element("show", true);
		feature.element("restore", restore);
		// JSONObject dataView = new JSONObject();
		// dataView.element("show", true);
		// dataView.element("readOnly", false);
		// feature.element("dataView", dataView);
		JSONObject saveAsImage = new JSONObject();
		saveAsImage.element("show", true);
		feature.element("saveAsImage", saveAsImage);
		JSONObject magicType = new JSONObject();
		magicType.element("show", true);
		JSONArray typex = new JSONArray();
		typex.add("line");
		typex.add("bar");
		magicType.element("type", typex);
		feature.put("magicType", magicType);
		toolbox.element("feature", feature);
		option.element("toolbox", toolbox);
	}

	public void buildDataZoom(JSONObject option) {
		JSONObject dataZoom = new JSONObject();
		if (StringUtil.isNotNull(this.dataZoom)) {
			dataZoom.element("show", true);
			dataZoom.element("realtime", false);
			dataZoom.element("height", "20");
			String[] dataZoomArray = this.dataZoom.split(" ");
			dataZoom.element("start",
					StringUtil.getIntFromString(dataZoomArray[0], 10));
			dataZoom.element("end",
					StringUtil.getIntFromString(dataZoomArray[1], 50));
		} else {
			JSONArray xdata = (JSONArray) (option.getJSONArray("xAxis")
					.getJSONObject(0).get("data"));
			// 如果zoomCount小于x轴数据长度才显示缩放条
			if (StringUtil.isNotNull(this.zoomCount)
					&& Integer.parseInt(this.zoomCount) > 0
					&& Integer.parseInt(this.zoomCount) < xdata.size()) {

				dataZoom.element("show", true);
				dataZoom.element("realtime", false);
				dataZoom.element("height", "20");
				if ("left".equals(getZoomAlign())) {
					dataZoom.element("start", 0);
					dataZoom.element("end", Integer.parseInt(this.zoomCount)
							* 100 / xdata.size());
				}
				if ("center".equals(getZoomAlign())) {
					dataZoom.element("start", (((xdata.size() - Integer
							.parseInt(this.zoomCount)) / 2))
							* 100 / xdata.size());
					dataZoom.element("end", (((xdata.size() + Integer
							.parseInt(this.zoomCount)) / 2))
							* 100 / xdata.size());
				}
				if ("right".equals(getZoomAlign())) {
					dataZoom.element("start", (xdata.size() - Integer
							.parseInt(this.zoomCount))
							* 100 / xdata.size());
					dataZoom.element("end", 100);
				}
			}
		}
		if (!dataZoom.isEmpty()) {
			JSONArray zoomArray = new JSONArray();
			zoomArray.add(dataZoom);
			JSONObject zoom = JSONObject.fromObject(dataZoom);
			zoom.put("type", "inside");
			zoomArray.add(zoom);
			option.element("dataZoom", zoomArray);
		}
	}

	public void buildYaxis(JSONObject option) {
		JSONArray yAxisArray = new JSONArray();
		// 第一y轴
		JSONObject yAxis = new JSONObject();
		if (StringUtil.isNotNull(this.yAxisName)) {
			yAxis.element("name", this.yAxisName);
			yAxis.element("type", "value");
		}
		if (StringUtil.isNotNull(this.yAxisLabel)) {
			JSONObject yAxisLabel = new JSONObject();
			yAxisLabel.element("formatter", this.yAxisLabel);
			yAxis.element("axisLabel", yAxisLabel);
		}
		if (!yAxis.isEmpty()) {
			yAxisArray.add(yAxis);
		}
		// 第二y轴
		JSONObject y2Axis = new JSONObject();
		if (StringUtil.isNotNull(this.y2AxisName)) {
			y2Axis.element("name", this.y2AxisName);
			y2Axis.element("type", "value");
		}

		if (StringUtil.isNotNull(this.y2AxisLabel)) {
			JSONObject y2AxisLabel = new JSONObject();
			y2AxisLabel.element("formatter", this.y2AxisLabel);
			y2Axis.element("axisLabel", y2AxisLabel);
		}
		if (!y2Axis.isEmpty()) {
			yAxisArray.add(y2Axis);
		}
		if (yAxisArray.isEmpty()) {
			// 如果第一y轴和第二y轴都没设置，则用默认y轴
			JSONObject defaultYAxis = new JSONObject();
			defaultYAxis.element("type", "value");
			yAxisArray.add(defaultYAxis);
		}
		option.element("yAxis", yAxisArray);
	}

	public void buildXaxis(JSONObject option) {
		JSONArray xAxisArray = new JSONArray();
		JSONObject xAxis = new JSONObject();
		if (StringUtil.isNotNull(this.xAxisName)) {
			xAxis.element("name", this.xAxisName);
		}
		xAxis.element("type", "category");
		JSONObject xAxisLabel = new JSONObject();
		if (StringUtil.isNotNull(this.xAxisLabel)) {
			xAxisLabel.element("formatter", this.xAxisLabel);
		}
		if (StringUtil.isNotNull(this.xAxisShowAll)) {
			if ("true".equals(this.xAxisShowAll)) {
				xAxisLabel.element("interval", "0");
			}
		}
		if (StringUtil.isNotNull(this.xAxisRotate)) {
			xAxisLabel.element("rotate", this.xAxisRotate);
		}
		if (!xAxisLabel.isEmpty()) {
			xAxis.element("axisLabel", xAxisLabel);
		}

		JSONArray xdata = new JSONArray();
		if (this.xAxisData != null) {
			if (this.xAxisData instanceof String) {
				String[] xdataArray = ((String) this.xAxisData).split(",");
				xdata = JSONArray.fromObject(xdataArray);
				xAxis.element("data", xdata);
			}
			if (this.xAxisData instanceof List) {
				xdata = JSONArray.fromObject(this.xAxisData);
				xAxis.element("data", xdata);
			}
		}
		xAxisArray.add(xAxis);
		option.element("xAxis", xAxisArray);
	}

	public void buildGridMargin(JSONObject option) {
		JSONObject grid = new JSONObject();
		if (StringUtil.isNotNull(this.gridMargin)) {
			String[] gridArray = this.gridMargin.split(" ");
			if (gridArray.length == 1) {
				grid.element("y", gridArray[0]);
				grid.element("x2", gridArray[0]);
				grid.element("y2", gridArray[0]);
				grid.element("x", gridArray[0]);
			}
			if (gridArray.length == 2) {
				grid.element("y", gridArray[0]);
				grid.element("x2", gridArray[1]);
				grid.element("y2", gridArray[0]);
				grid.element("x", gridArray[1]);
			}
			if (gridArray.length == 3) {
				grid.element("y", gridArray[0]);
				grid.element("x2", gridArray[1]);
				grid.element("y2", gridArray[2]);
				grid.element("x", gridArray[1]);
			}
			if (gridArray.length == 4) {
				grid.element("y", gridArray[0]);
				grid.element("x2", gridArray[1]);
				grid.element("y2", gridArray[2]);
				grid.element("x", gridArray[3]);
			}
		} else {
			// 默认全部为40px
			int x = 40;
			int x2 = 40;
			int y = 40;
			int y2 = 40;
			if (!"hidden".equals(this.textPosition)) {
				if (StringUtil.isNotNull(this.text)) {
					if (StringUtil.isNotNull(this.textPosition)) {
						String[] positionArray = this.textPosition.split("\\|");
						if (positionArray.length > 1) {
							String xPos = positionArray[0];
							if ("left".equals(xPos)) {
								x += 20;
							} else if ("right".equals(xPos)) {
								x2 += 20;
							}
							String yPos = positionArray[1];
							if ("bottom".equals(yPos)) {
								y2 += 20;
								if (StringUtil.isNotNull(this.subText)) {
									// 副标题不为空则y2再加20px
									y2 += 20;
								}
							} else if ("top".equals(yPos)) {
								if (StringUtil.isNotNull(this.subText)) {
									// 副标题不为空则y再加20px
									y += 20;
								}
							}
						} else {
							String xPos = positionArray[0];
							if ("left".equals(xPos)) {
								x += 20;
							} else if ("right".equals(xPos)) {
								x2 += 20;
							}
						}
					} else {
						if (StringUtil.isNotNull(this.subText)) {
							// 副标题不为空则再加20px
							y += 20;
						}
					}
				}
			}
			if (!"hidden".equals(this.legendPosition)) {
				if (StringUtil.isNotNull(this.legendPosition)) {
					String[] positionArray = this.legendPosition.split("\\|");
					if (positionArray.length > 1) {
						String xPos = positionArray[0];
						if ("left".equals(xPos)) {
							// 图例在左边，x加80px
							x += 60;
						} else if ("right".equals(xPos)) {
							// 图例在右边，x2加60px
							x2 += 60;
						}
						String yPos = positionArray[1];
						if ("bottom".equals(yPos)) {
							// 图例在下边，y2加20px
							y2 += 20;
						}
					} else {
						String xPos = positionArray[0];
						if ("left".equals(xPos)) {
							x += 60;
						} else if ("right".equals(xPos)) {
							x2 += 60;
						}
					}
				} else {
					// 默认图例在下边，y2加20px
					y2 += 20;
				}
			}
			if (StringUtil.isNotNull(this.zoomCount)
					|| StringUtil.isNotNull(this.dataZoom)) {
				y2 += 20;
			}
			grid.element("x", x);
			grid.element("y", y);
			grid.element("x2", x2);
			grid.element("y2", y2);
		}
		option.element("grid", grid);
	}

	public JSONObject option = new JSONObject();

	public JSONObject bulidJsonDatas() {
		// title
		buildTitle(option);
		// tooltip
		buildTooltip(option);
		// legend
		buildLegend(option);
		// toolbox
		buildToolbox(option);
		// calculable
		option.element("calculable", "true");
		// yAxis
		buildYaxis(option);
		// xAxis
		buildXaxis(option);
		buildGridMargin(option);
		// series
		option.element("series", this.serise);
		// option.element("isAdapterSize", this.isAdapterSize);
		if (this.isAdapterSize) {
			option.element("isAdapterSize", "true");
		} else {
			option.element("isAdapterSize", "false");
		}
		return option;
	}

	public void revertXY(JSONObject option) {
		if ("true".equals(this.reverseXY)) {
			// x和y轴是否反转，在标签结束处理，防止中间往x或y轴添加了数据
			JSONArray xAxis = option.getJSONArray("xAxis");
			JSONArray yAxis = option.getJSONArray("yAxis");
			option.put("xAxis", yAxis);
			option.put("yAxis", xAxis);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		bulidJsonDatas();
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();
			String jsonpcallback = request.getParameter("jsonpcallback");
			JSONArray datas = option.getJSONArray("series");
			if (datas.size() == 1) {
				JSONObject aaa = (JSONObject) datas.get(0);
				String type = (String) aaa.get("type");
				if (!("pie".equals(type) || "gauge".equals(type))) {
					buildDataZoom(option);
					revertXY(option);
				}
				if ("pie".equals(type) || "gauge".equals(type)) {
					try {
						// 需求要求：即使是饼图，在数据视图也要有标题
						option.accumulate("xAxisName",
								option.getJSONArray("xAxis")
										.getJSONObject(0).getString("name"));
					} catch (Exception e) {

					}
					// 如果数据只有一条，并且是饼图（仪表盘），则x轴部分的数据以及工具栏的线柱切换要清理掉
					option.discard("xAxis");
					option.discard("yAxis");
					option.discard("grid");
					JSONObject feature = option.getJSONObject("toolbox")
							.getJSONObject("feature");
					feature.discard("magicType");
				}
				if ("pie".equals(type)) {
					JSONObject tooltip = new JSONObject();
					tooltip.element("trigger", "item");
					tooltip.element("formatter", "{a} <br/>{b} : {c} ({d}%)");
					JSONObject textStyle = new JSONObject();
					textStyle.element("align", "left");
					tooltip.element("textStyle", textStyle);
					option.put("tooltip", tooltip);
				}
				if ("gauge".equals(type)) {
					JSONObject tooltip = new JSONObject();
					tooltip.element("formatter", "{a} <br/>{b} : {c} ({d}%)");
					JSONObject textStyle = new JSONObject();
					textStyle.element("align", "left");
					tooltip.element("textStyle", textStyle);
					option.put("tooltip", tooltip);
				}
			} else {
				buildDataZoom(option);
				revertXY(option);
			}
			String body = option.toString();
			if (StringUtil.isNotNull(jsonpcallback)) {
				pageContext.getOut().append(jsonpcallback + "(" + body + ")");
			} else {
				pageContext.getOut().append(body);
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
		text = null;
		subText = null;
		textPosition = null;
		xAxisName = null;
		xAxisLabel = null;
		xAxisData = null;
		xAxisShowAll = null;
		xAxisRotate = null;
		yAxisName = null;
		yAxisLabel = null;
		y2AxisName = null;
		y2AxisLabel = null;
		legendOrient = null;
		legendPosition = null;
		reverseXY = null;
		tooltipLabel = null;
		dataZoom = null;
		zoomAlign = null;
		zoomCount = null;
		gridMargin = null;
		serise.clear();
		ledendData.clear();
		option.clear();
		super.release();
	}
}
