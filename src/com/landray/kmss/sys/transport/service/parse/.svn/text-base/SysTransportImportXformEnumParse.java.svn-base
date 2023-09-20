package com.landray.kmss.sys.transport.service.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.landray.kmss.sys.metadata.dict.SysDictExtendSimpleProperty;
import com.landray.kmss.sys.transport.service.ISysTransportImportPropertyParse;
import com.landray.kmss.sys.transport.service.spring.ImportInDetailsCellContext;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.sys.transport.service.spring.SysTransportTableUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;

public class SysTransportImportXformEnumParse
		implements ISysTransportImportPropertyParse {

	@Override
	public boolean parse(ImportInDetailsCellContext detailsCellContext)
			throws Exception {
		Locale locale = ResourceUtil.getLocaleByUser();
		SysDictExtendSimpleProperty extendProperty = (SysDictExtendSimpleProperty) detailsCellContext
				.getProperty();
		Map idToType = detailsCellContext.getDetailsContext().getIdToType();
		String propertyName = detailsCellContext.getPropertyName();
		Cell cell = detailsCellContext.getCell();
		KmssMessages contentMessage = detailsCellContext.getContentMessage();
		Map<String, String> temp = detailsCellContext.getTemp();
		int i = detailsCellContext.getIndex();
		String cellString = ImportUtil.getCellValue(cell);
		String value = "";
		Boolean isFalse = false; // 错误标志位
		String[] enumValues = extendProperty.getEnumValues()
				.split(";");
		String[] cellStringEnum = cellString.split(";");
		List cellEnumList = new ArrayList();
		Boolean isCheckBox = false;
		if (idToType.containsKey(
				extendProperty.getName())
				&& (
				        "xform_checkbox".equalsIgnoreCase(((String) idToType.get(extendProperty.getName())))
		           )

				||
				"xform_fSelect".equalsIgnoreCase(((String) idToType.get(extendProperty.getName())))) {
			isCheckBox = true;
		}
		// 如果EnumType为inputCheckBox，则为多选
		if (isCheckBox) {
			for (int v = 0; v < cellStringEnum.length; v++) {
				cellEnumList.add(cellStringEnum[v]);
			}
		} else {
			// 如果单元结尾有;，则清除分号
			if (cellString.endsWith(";")) {
				cellString = cellString.substring(0,
						cellString.length() - 2);
			}
			cellEnumList.add(cellString);
		}

		// 遍历单元格里面用；分开的文本
		for (int m = 0; m < cellEnumList.size(); m++) {
			String cellValue = (String) cellEnumList
					.get(m);
			// 遍历数据字典里面的枚举值
			for (int k = 0; k <= enumValues.length; k++) {
				// 当循环到最后一个都没有匹配值的话，证明该单元格有错误
				if (k == enumValues.length) {
					isFalse = true;
					break;
				}
				String enumValue = enumValues[k];
				// 把文本和值分割开来 例如：草稿状态|01，【0】为草稿状态，【1】为01
				String[] values = enumValue
						.split("\\|");
				// 判断单元格内容是否是文本
				if (values[0].equals(cellValue)) {
					// 当循环到最后一个的时候，不加“；”，以免单选值无法识别
					if (m == cellEnumList.size() - 1) {
						value += values[1];
					} else {
						value += values[1] + ";";
					}
					break;
				} else if (values[1].equals(cellValue)) { // 判断单元格内容是否是文本值，即使是文本值也是可以识别
					// 当循环到最后一个的时候，不加“；”，以免单选值无法识别
					if (m == cellEnumList.size() - 1) {
						value += values[1];
					} else {
						value += values[1] + ";";
					}
					break;
				}
			}

		}
		// 如果有错则输出错误信息
		if (isFalse) {
			KmssMessage message = new KmssMessage(
					"sys-transport:sysTransport.import.dataError.notEnum",
					i + 1,
					SysTransportTableUtil
							.getSysSimpleOrExtendPropertyMessage(
									extendProperty, locale));
			contentMessage.addError(message);
			temp.put(propertyName, "");
		} else {
			temp.put(propertyName, value);
		}
		return false;
	}

}
