package com.landray.kmss.eop.basedata.imp.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.config.dict.SysDictModel;

public class EopBasedataImportContext {
	private Map<String,EopBasedataImportColumn> fdColumnMap;
	private String fdModelName;
	private String fdKeyColumn;
	private SysDictModel fdDict;
	private List<EopBasedataImportColumn> fdColumns;
	public String getFdModelName() {
		return fdModelName;
	}
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	public SysDictModel getFdDict() {
		return fdDict;
	}
	public void setFdDict(SysDictModel fdDict) {
		this.fdDict = fdDict;
	}
	public List<EopBasedataImportColumn> getFdColumns() {
		return fdColumns;
	}
	public void setFdColumns(List<EopBasedataImportColumn> fdColumns) {
		this.fdColumns = fdColumns;
	}
	public String getFdKeyColumn() {
		return fdKeyColumn;
	}
	public void setFdKeyColumn(String fdKeyColumn) {
		this.fdKeyColumn = fdKeyColumn;
	}
	public EopBasedataImportColumn getColumnByCol(Integer col){
		for(EopBasedataImportColumn column:fdColumns){
			if(column.getFdColumn().equals(col)){
				return column;
			}
		}
		return null;
	}
	public EopBasedataImportColumn getColumnByProperty(String property){
		if(fdColumnMap==null){
			fdColumnMap = new HashMap<String,EopBasedataImportColumn>();
			for(EopBasedataImportColumn column:fdColumns){
				fdColumnMap.put(column.getFdName(), column);
			}
		}
		return fdColumnMap.get(property);
	}
}
