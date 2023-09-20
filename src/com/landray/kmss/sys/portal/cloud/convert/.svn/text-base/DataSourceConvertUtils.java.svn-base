package com.landray.kmss.sys.portal.cloud.convert;

public class DataSourceConvertUtils {
	/**
	 * 将ekp的数据格式转换为cloud的数据格式
	 * <p>
	 * 未找到转换器不做转换
	 * </p>
	 * 
	 * @param datas
	 *            数据
	 * @param format
	 *            数据格式的code，例如：sys.ui.classic
	 * @return
	 */
	public static Object convert(Object datas, String format) {
		IDataSourceConverter converter = DataSourceConverterFactory
				.getConverter(format);
		if (converter != null) {
			return converter.convert(datas);
		}
		return datas;
	}
}
