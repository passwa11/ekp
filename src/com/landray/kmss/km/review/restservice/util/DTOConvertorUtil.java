package com.landray.kmss.km.review.restservice.util;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.review.restservice.dto.IdNameProperty;
import com.landray.kmss.km.review.restservice.dto.SysDictExtendPropertyDTO;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.interfaces.ISysDictLoader;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateModel;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DTOConvertorUtil {

	private static ISysDictLoader sysDictLoader;

	public static ISysDictLoader getSysDictLoader() {
		if (sysDictLoader == null) {
			sysDictLoader = (ISysDictLoader) SpringBeanUtil.getBean("sysDictLoader");
		}
		return sysDictLoader;
	}

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(DTOConvertorUtil.class);

	/**
	 * IBaseModel转IdNameProperty
	 *
	 * @param model           实体对象
	 * @param getNameProperty 获取名称的方法名，如:"getFdName"
	 */
	public static IdNameProperty convert2IdNameProperty(IBaseModel model, String getNameProperty) {
		if (model == null) {
			return new IdNameProperty();
		}
		String fdName = null;
		try {
			if (StringUtil.isNotNull(getNameProperty)) {
				Method method = model.getClass().getMethod(getNameProperty);
				Object result = method.invoke(model);
				if (result != null) {
					fdName = result.toString();
				}
			}
		} catch (Exception e) {
			log.error("获取名称失败:" + getNameProperty, e);
		}
		return new IdNameProperty(model.getFdId(), fdName);
	}

	/**
	 * List<IBaseModel>转List<IdNameProperty>
	 *
	 * @param modelList       实体对象列表
	 * @param getNameProperty 获取名称的方法名，如:"getFdName"
	 */
	public static List<IdNameProperty> convert2IdNamePropertyList(List<IBaseModel> modelList, String getNameProperty) {
		if (ArrayUtil.isEmpty(modelList)) {
			return new ArrayList<>();
		}
		List<IdNameProperty> modelDTO = new ArrayList<>();
		for (IBaseModel model : modelList) {
			IdNameProperty element = DTOConvertorUtil.convert2IdNameProperty(model, getNameProperty);
			modelDTO.add(element);
		}
		return modelDTO;
	}

	/**
	 * 表单字段数据字典转DTO
	 *
	 * @param property 表单字段数据字典
	 */
	public static SysDictExtendPropertyDTO convert2PropertyDTO(SysDictExtendProperty property) {
		if (property.getType() == null) {
			return null;
		}
		SysDictExtendPropertyDTO dto = new SysDictExtendPropertyDTO();
		dto.setBusinessType(property.getBusinessType());
		dto.setCanDisplay(property.isCanDisplay());
		dto.setColumn(property.getColumn());
		dto.setEnumType(property.getEnumType());
		dto.setEnumValues(property.getEnumValues());
		dto.setLabel(property.getLabel());
		dto.setName(property.getName());
		dto.setNotNull(property.isNotNull());
		dto.setReadOnly(property.isReadOnly());
		dto.setType(property.getType());
		return dto;
	}

	/**
	 * 将所有表单字段数据字典转List<DTO>
	 */
	public static List<SysDictExtendPropertyDTO> convert2PropertyDTOList(ISysFormTemplateModel fdTemplateModel, String key, String fdModelName) throws Exception {
		List<SysDictExtendPropertyDTO> getSysFormTemplateModels = new ArrayList<>();
		SysDictModel dictModel = loadDict(fdTemplateModel, key, fdModelName);
		List<SysDictCommonProperty> propertyList = dictModel.getPropertyList();
		if (!ArrayUtil.isEmpty(propertyList)) {
			for (SysDictCommonProperty property : propertyList) {
				if (property instanceof SysDictExtendProperty) {
					SysDictExtendPropertyDTO dto = DTOConvertorUtil.convert2PropertyDTO((SysDictExtendProperty) property);
					if (dto != null) {
                        getSysFormTemplateModels.add(dto);
                    }
				}
			}
		}
		return getSysFormTemplateModels;
	}

	/**
	 * 找到该模板Id对应的表单字段数据字典
	 */
	public static SysDictModel loadDict(ISysFormTemplateModel fdTemplateModel, String key, String fdModelName) throws Exception {
		if (fdTemplateModel != null && StringUtil.isNotNull(fdModelName)) {
			IExtendDataModel dataModel = (IExtendDataModel) com.landray.kmss.util.ClassUtils.forName(fdModelName).newInstance();
			dataModel.setExtendFilePath(XFormUtil.getFileName(fdTemplateModel, key));
			return getSysDictLoader().loadDict(dataModel);
		}
		return null;
	}
}
