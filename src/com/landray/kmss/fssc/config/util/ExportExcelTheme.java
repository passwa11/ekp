package com.landray.kmss.fssc.config.util;

import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.*;

/**
 * 定义导出excel的样式主题
 * @author ZhangFZ
 * @date 2020/5/7 18:01
 **/
public class ExportExcelTheme {
	 /**
     * 该数组有且只能有4个值
     * theme[0]: 标题栏背景色
     * theme[1]: 标题栏字体颜色
     * theme[2]: 数据隔行色，浅
     * theme[3]: 数据隔行色，深
     */
    public XSSFColor[] theme;
    
    public ExportExcelTheme() {
    	setTheme(getDEFAULT());
	}
    
    public XSSFColor[] getTheme() {
		return theme;
	}


	public void setTheme(XSSFColor[] theme) {
		this.theme = theme;
	}


	/**
     * 默认主题
     */
	public  XSSFColor[] getDEFAULT(){
	   return new XSSFColor[]{
	            new XSSFColor(new Color(165, 165, 165), new DefaultIndexedColorMap()),
	            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap()),
	            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap()),
	            new XSSFColor(new Color(250, 250, 250), new DefaultIndexedColorMap())
	    };
   }
}
