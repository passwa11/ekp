package com.landray.kmss.util.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工作表对象
 * @author 苏轶
 *
 */
public class Sheet {
	
	private int[] columHidenIndex; //索引列从0 
	private String password = null ;// 设置密码
	
	public int[] getColumHidenIndex() {
		return columHidenIndex;
	}

	public void setColumHidenIndex(int[] columHidenIndex) {
		this.columHidenIndex = columHidenIndex;
	}


	private int lockRow; // 锁定行
	private int lockColum; //锁定列
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLockRow() {
		return lockRow;
	}

	public void setLockRow(int lockRow) {
		this.lockRow = lockRow;
	}

	public int getLockColum() {
		return lockColum;
	}

	public void setLockColum(int lockColum) {
		this.lockColum = lockColum;
	}

	private String tip; //提示
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}


	private String title; // 工作表标题和名称
	private String titleKey; // 工作表标题和名称资源Key值
	private String bundle; // 工作表标题和名称资源bundle
	private List columnList = new ArrayList(); // 表格列的列表
	private List contentList; // 表格数据，每个元素通常是一个List或者Object[]
	private List headContentList;
	private Map<Integer, String[]> selectContentMap; // 存放的是excel的内容，例如某一列填值的方式需要修改成下拉选择。第一个参数是第几列，第二个参数是内容集合

	public void setSelectContentMap(Map<Integer, String[]> selectContentMap) {
		this.selectContentMap = selectContentMap;
	}

	public Map<Integer, String[]> getSelectContentMap() {
		return selectContentMap;
	}

	private boolean ifCreateSheetTitleLine = false; // 是否创建表格标题行（合并了所有列的那行，非列标题行）
	
	private boolean ifCreateSheetTipLine = false; // 是否创建表格提示行（合并了所有列的那行）
	
	public boolean isIfCreateSheetTipLine() {
		return ifCreateSheetTipLine;
	}

	public void setIfCreateSheetTipLine(boolean ifCreateSheetTipLine) {
		this.ifCreateSheetTipLine = ifCreateSheetTipLine;
	}

	public List getColumnList() {
		return columnList;
	}
	
	public void setColumnList(List columnList) {
		this.columnList = columnList;
	}
	
	public boolean addColumn(Column col) {
		return columnList.add(col);
	}
	
	public List getContentList() {
		return contentList;
	}
	
	public void setContentList(List contentList) {
		this.contentList = contentList;
	}
	
	public boolean addContent(List oneRow) {
		if (contentList == null) {
            contentList = new ArrayList();
        }
		return contentList.add(oneRow);
	}
	
	public boolean addContent(Object[] oneRow) {
		if (contentList == null) {
            contentList = new ArrayList();
        }
		return contentList.add(oneRow);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getBundle() {
		return bundle;
	}

	
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	
	public String getTitleKey() {
		return titleKey;
	}

	
	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}

	public boolean isIfCreateSheetTitleLine() {
		return ifCreateSheetTitleLine;
	}

	public void setIfCreateSheetTitleLine(boolean ifCreateSheetTitleLine) {
		this.ifCreateSheetTitleLine = ifCreateSheetTitleLine;
	}
	
	public List getHeadContentList() {
		return headContentList;
	}

	public void setHeadContentList(List headContentList) {
		this.headContentList = headContentList;
	}
}
