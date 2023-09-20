package com.landray.kmss.third.ding.xform.controls.tablecontext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TemplateTr {
	private List<Cell> cells;

	public List<Cell> getCells() {
		if (cells == null) {
			cells = new ArrayList<Cell>();
		}
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	// 把模板配置的模板行转换为移动端展示的移动行
	public List<MobileTr> splitToMobileTr() {
		List<MobileTr> rs = new ArrayList<>();
		// 如果单元格全部为空则不添加
		if (cells.isEmpty() || isAllEmptyCell()) {
			cells.clear();
			return rs;
		}
		// 每两个单元格为一个MobileTr
		int count = 0;
		MobileTr tempMobileTr = null;
		for (Iterator<Cell> it = cells.iterator(); it.hasNext();) {
			Cell cell = it.next();
			if (count == 0) {
				tempMobileTr = new MobileTr();
				tempMobileTr.setTitleCell(cell);
				rs.add(tempMobileTr);
				count++;
			} else if (count == 1) {
				tempMobileTr.setContentCell(cell);
				count = 0;
			}
		}
		return rs;
	}

	private boolean isAllEmptyCell() {
		for (Cell cell : cells) {
			if (!cell.isEmpty()) {
				return false;
			}
		}
		return true;
	}
}
