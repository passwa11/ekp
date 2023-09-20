package com.landray.kmss.util.excel;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface ExcelOutput {
	public void output(WorkBook workbook, OutputStream outputStream) throws Exception;

	public void output(WorkBook workbook, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 导出含有合并单元格和自定义样式的文件
	 * @param workbook
	 * @param response
	 * @param sheetMerge
	 * @param styleMap
	 * @throws Exception
	 */
	public void outputByMerge(WorkBook workbook, HttpServletResponse response, List<List<Integer []>> sheetMerge, Map<String, List<CellStyleEnum>> styleMap)
			throws Exception;
}
