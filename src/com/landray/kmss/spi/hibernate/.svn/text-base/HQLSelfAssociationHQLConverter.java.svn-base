package com.landray.kmss.spi.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.hibernate.IHQLConverter;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

/**
 * 单独处理HQL字段,判断查询对象是否为自身关联，因为这些方法是涉及到业务对象
 * 
 * @author huangzz
 *
 */
public class HQLSelfAssociationHQLConverter implements IHQLConverter {

	private static final Logger log = LoggerFactory.getLogger(HQLSelfAssociationHQLConverter.class);
	private static String symbolDot = ".";
	private static String IS = "IS";
	private static String NULL = "NULL";
	private static String PARENT = "parent";
	private static String FDID = "fdId";

	private static boolean resetAssColumn(Column column, String key, String value) {
		SysDictModel sysDictModel = null;
		boolean isSelfAssociation = false;
		if (null == SysDataDict.getInstance().getModel(value)) {
			List<String> modelList = SysDataDict.getInstance().getModelInfoList();
			for (String dict : modelList) {
				String className = StringUtils.substringAfterLast(dict, symbolDot);
				if (className.toLowerCase().equals(key.toLowerCase())) {
					value = dict;
					sysDictModel = SysDataDict.getInstance().getModel(value);
					break;
				}
			}
		} else {
			sysDictModel = SysDataDict.getInstance().getModel(value);
		}

		if (null != sysDictModel) {
			SysDictModelProperty dictModelProperty = (SysDictModelProperty) sysDictModel.getPropertyMap()
					.get(column.getTable().getName());
			if (null != dictModelProperty) {
				String className = dictModelProperty.getType();
				String modelName = sysDictModel.getModelName();

				// 是自身关联表字段
				if (className.contentEquals(modelName)) {
					column.setColumnName(column.getTable().getName());
					column.setTable(new Table(key));
					isSelfAssociation = true;
				}
			}

		} else {
			// getDictModel(joinTables, null, value); 暂时不支持递归
			log.debug("HQL multilayer self-association Layer Greater than 4:{}", column.getFullyQualifiedName());
		}
		return isSelfAssociation;
	}

	/**
	 * 通过数据字段判断是否自身关联对象
	 * 
	 * @param joinTables
	 * @param column
	 * @param key
	 */
	private static boolean resetAssColumn(Map<String, Table> joinTables, Map<String, String> globalJoinTables,
			Column column, String key) {
		if (null != column) {
			key = column.getTable().getName();
		}

		String value = globalJoinTables.get(key);
		if (null == value) {
			if (null != joinTables.get(key)) {
				key = joinTables.get(key).getSchemaName();
			}

			if (null != key) {
				value = globalJoinTables.get(key);
			}

		}

		return resetAssColumn(column, key, value);
	}

	@Override
	public boolean isSelfAssociation(Map<String, Table> joinTables, Map<String, String> globalJoinTables,
			Column column) {
		if (FDID.equals(column.getASTNode().jjtGetLastToken().image)) {
			if (IS.equals(column.getASTNode().jjtGetLastToken().next.image.toUpperCase())) {
				if (NULL.equals(column.getASTNode().jjtGetLastToken().next.next.image.toUpperCase())) {
					String parentId = column.getTable().getName();
					if (null != parentId) {
						parentId = parentId.toLowerCase();
						if (parentId.indexOf(PARENT) >= 0) {
							return resetAssColumn(joinTables, globalJoinTables, column, null);
						}
					}
				}
			}

		}
		return false;
	}
}
