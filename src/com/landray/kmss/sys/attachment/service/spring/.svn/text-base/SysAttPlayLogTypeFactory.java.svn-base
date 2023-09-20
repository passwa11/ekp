package com.landray.kmss.sys.attachment.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.attachment.service.ISysAttPlayLogType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysAttPlayLogTypeFactory {

	private static final List<ISysAttPlayLogType> types = new ArrayList<>();

	private static final String VIDEO_TYPE = "video";
	private static final String AUDIO_TYPE = "audio";
	private static final String OFFICE_ASPOSE_TYPE = "office.aspose";

	static {
		types.add(new ISysAttPlayLogType() {
			@Override
			public String getType() {
				return VIDEO_TYPE;
			}
		});

		types.add(new ISysAttPlayLogType() {
			@Override
			public String getType() {
				return AUDIO_TYPE;
			}
		});

		types.add(new ISysAttPlayLogType() {
			@Override
			public String getType() {
				return OFFICE_ASPOSE_TYPE;
			}
		});
	}

	public static List<ISysAttPlayLogType> getTypes() {
		return types;
	}

	/**
	 * 获取播放类型对象
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static ISysAttPlayLogType getType(String type) throws Exception {

		for (ISysAttPlayLogType typeObj : types) {
			if (type.equals(typeObj.getType()) && typeObj.enable()) {
				return typeObj;
			}
		}

		return null;

	}

	/**
	 * 当前文件类型是否开启了续播功能
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Boolean isEnable(String type) throws Exception {

		for (ISysAttPlayLogType typeObj : types) {
			if (type.equals(typeObj.getType())) {
				return typeObj.enable();
			}
		}

		return false;
	}

	/**
	 * 获取所有支持播放记录的附件类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getTypeData() throws Exception {

		JSONArray array = new JSONArray();

		for (ISysAttPlayLogType typeObj : types) {

			if (!typeObj.enable()) {
				continue;
			}

			JSONObject obj = new JSONObject();
			String value = typeObj.getType();

			obj.element("value", value);
			obj.element("text", typeObj.getTitle());

			array.add(obj);
		}

		return array;

	}

}
