package com.landray.kmss.sys.ui.model;

import java.util.List;

public class Pager {
	private List datas;
	private int pageNum;
	private int pageSize;

	public Pager(List datas, int pageSize) {
		this.datas = datas;
		this.pageSize = pageSize;
	}

	public List getPageList(int pageNum) {
		int fromindex = (pageNum - 1) * pageSize;
		int toindex = pageNum *pageSize;
		if (toindex > datas.size()) {
            toindex = datas.size();
        }
		return datas.subList(fromindex, toindex);
	}
}