package com.landray.kmss.third.ding.xform.controls.tablecontext;

//移动端行，每行只有两列，标题列和内容列
public class MobileTr {
	private Cell titleCell;

	private Cell contentCell;

	public Cell getTitleCell() {
		return titleCell;
	}

	public void setTitleCell(Cell titleCell) {
		this.titleCell = titleCell;
	}

	public Cell getContentCell() {
		return contentCell;
	}

	public void setContentCell(Cell contentCell) {
		this.contentCell = contentCell;
	}
}
