package com.landray.kmss.sys.transport.service.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendElementProperty;
import com.landray.kmss.sys.transport.service.ISysTransportImportPropertyParse;
import com.landray.kmss.sys.transport.service.spring.ImportInDetailsCellContext;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.sys.transport.service.spring.SysTransportTableUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysTransportImportAddressParse
		implements ISysTransportImportPropertyParse {

	@Override
	public boolean parse(ImportInDetailsCellContext detailsCellContext)
			throws Exception {
		Locale locale = ResourceUtil.getLocaleByUser();
		// 对象属性
		SysDictCommonProperty property = detailsCellContext.getProperty();
		String propertyName = detailsCellContext.getPropertyName();
		Cell cell = detailsCellContext.getCell();
		KmssMessages contentMessage = detailsCellContext.getContentMessage();
		Map<String, String> temp = detailsCellContext.getTemp();
		// 名称属性，例如fd_name
		SysDictCommonProperty modelPro = detailsCellContext.getModelPro();
		int i = detailsCellContext.getIndex();
		String cellString = ImportUtil.getCellValue(cell);
		if (StringUtil.isNull(cellString)) {
			return false;
		}
		// 查找fdid
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(property.getType());
		String whereBlock = "";
		Boolean error = false;// 错误标志
		String propertyId = detailsCellContext.getDetailsContext()
				.getNameToIdMap()
				.get(propertyName);
		String errorMsg = "sys-transport:sysTransport.import.dataError.notFoundForeignKey";
		if (property instanceof SysDictModelProperty
				|| property instanceof SysDictExtendElementProperty) {
			// 只读不导进去
			if (property.isReadOnly()) {
				return false;
			}
			// 在表单有xformflag标签的之后，由于获取到的地址本字段名不再含有name和id了，所以此处做个兼容
			if (StringUtil.isNull(propertyId)) {
				propertyId = propertyName + ".id";
				propertyName += ".name";
			}
			// 地址本单选的时候，不能多个输入
			if (property instanceof SysDictExtendElementProperty) {
				String multiSplit = ((SysDictExtendElementProperty) property)
						.getMutiValueSplit();
				if (StringUtil.isNull(multiSplit)) {
					if (cellString.indexOf(";") > -1) {
						KmssMessage message = new KmssMessage(
								"sys-transport:sysTransport.import.dataError.notMulti",
								i + 1,
								SysTransportTableUtil
										.getSysSimpleOrExtendPropertyMessage(
												property, locale));
						contentMessage.addError(message);
						temp.put(propertyId, "");
						temp.put(propertyName, "");
						return false;
					}
				}
			}
			List cellList = new ArrayList();
			cellList = ImportUtil.getCellValueList(cell,
					modelPro, null, locale);
			if (cellList == null) {
				return false;
			}
			// 返回id和name，id用于页面的存储，name用于匹配哪个用户名有错误
			hqlInfo.setSelectBlock("fdId,fdName");
			// 返回解析过后的单元值('XXX','XXX','XXX')
			if (cellString.indexOf("/") > -1) {
				boolean isPerson = false;
				if (property.getType().indexOf(
						"com.landray.kmss.sys.organization.model.SysOrgPerson") > -1) {
					isPerson = true;
					hqlInfo.setModelName(property.getType());
				} else {
					hqlInfo.setModelName(
							"com.landray.kmss.sys.organization.model.SysOrgElement");
				}
				whereBlock = getAccurateHqlWhereBlock(
						hqlInfo, modelPro.getName(), cellList, isPerson);
			} else {
				whereBlock = getHqlWhereBlockByParseList(hqlInfo, modelPro,
						cellList);
			}

			hqlInfo.setWhereBlock(whereBlock);
			// 查询出来的id和name
			List datas = ((IBaseService) SpringBeanUtil
					.getBean("KmssBaseService")).findList(hqlInfo);
			if (datas.size() > 0) {
				// 把查询出来的list转换为map
				Map idAndNameMap = listToMap(datas);
				// 如果查询出来的name存在重名的情况，直接不再检验后面
				String doubleName = getDoubleName(idAndNameMap);
				if (StringUtil.isNotNull(doubleName)) {
					// 设置重名的msg
					errorMsg = "sys-transport:sysTransport.import.dataError.doubleName";
					error = true;
					cellString = doubleName;
				} else {
					// 校验是否有用户名无效或者找不到的
					// 当查出来的数据数量不为0且数量等于单元格分隔后的内容数量，才算正确
					StringBuffer idBuffer = new StringBuffer();
					StringBuffer nameBuffer = new StringBuffer();
					if (datas.size() == cellList.size()) {
						for (int j = 0; j < cellList.size(); j++) {
							String s = (String) cellList.get(j);
							s = getFirstStringInComplex(s);
							nameBuffer.append(s + ";");
							for (Object key : idAndNameMap.keySet()) {
								if (cellList.get(j)
										.equals(idAndNameMap.get(key))) {
									idBuffer.append(key + ";");
									break;
								}
								// 传入的是精准搜索时,兼容地址本其他格式
								if (s.equals(idAndNameMap.get(key))) {
									idBuffer.append(key + ";");
									break;
								}
							}
						}
						// 去除最后一个分号
						temp.put(propertyId, idBuffer.toString().substring(0,
								idBuffer.length() - 1));
						temp.put(propertyName,
								nameBuffer.toString().substring(0,
										nameBuffer.length() - 1));
					} else {
						// 如果长度不相等，即存在用户找不到的情况
						// excel单元的name集合
						String[] cellStringArray = cellString
								.split(";");
						// 返回name集合
						List valueList = new ArrayList();
						// 缺失name集合
						StringBuffer falseName = new StringBuffer();
						// 返回给页面的正确name集合
						StringBuffer trueName = new StringBuffer();
						for (Iterator iterator = datas
								.iterator(); iterator.hasNext();) {
							Object[] objectArray = (Object[]) iterator
									.next();
							valueList.add(objectArray[1]);
						}
						// 查找缺失的name
						for (int m = 0; m < cellStringArray.length; m++) {
							String s = cellStringArray[m];
							s = getFirstStringInComplex(s);
							if (!valueList.contains(s)) {
								falseName.append(s + ";");
								continue;
							}
							trueName.append(s + ";");
						}
						error = true;
						cellString = falseName.toString();
					}
				}
			} else {
				error = true;
			}
		} else if (property instanceof SysDictListProperty) {
			// 最好和上面的合并
			List cellList = new ArrayList();
			cellList = ImportUtil.getCellValueList(cell,
					modelPro, null, locale);
			if (cellList == null) {
				return false;
			}
			hqlInfo.setSelectBlock("fdId,fdName");
			whereBlock = getHqlWhereBlockByParseList(
					hqlInfo, modelPro, cellList);
			hqlInfo.setWhereBlock(whereBlock);
			List idAndName = ((IBaseService) SpringBeanUtil
					.getBean("KmssBaseService")).findValue(hqlInfo);
			Map idAndNameMap = listToMap(idAndName);
			// 当查出来的数据数量不为0且数量等于单元格分隔后的内容数量，才算正确
			StringBuffer idBuffer = new StringBuffer();
			StringBuffer nameBuffer = new StringBuffer();
			if (idAndName.size() > 0
					&& idAndName.size() == cellList.size()) {
				for (int j = 0; j < cellList.size(); j++) {
					String s = (String) cellList.get(j);
					s = getFirstStringInComplex(s);
					nameBuffer.append(s + ";");
					for (Object key : idAndName) {
						if (cellList.get(j)
								.equals(idAndNameMap.get(key))) {
							idBuffer.append(key + ";");
							break;
						}
					}
				}
				// 去除最后一个分号
				temp.put(propertyId, idBuffer.toString().substring(0,
						idBuffer.length() - 1));
				temp.put(propertyName,
						nameBuffer.toString().substring(0,
								nameBuffer.length() - 1));
			} else {
				// excel单元的name集合
				String[] cellStringArray = cellString
						.split(";");
				// 返回name集合
				List valueList = new ArrayList();
				// 缺失name集合
				StringBuffer falseName = new StringBuffer();
				// 返回给页面的name集合
				StringBuffer trueName = new StringBuffer();
				for (Iterator iterator = idAndName
						.iterator(); iterator
								.hasNext();) {
					Object[] objectArray = (Object[]) iterator
							.next();
					idBuffer.append(objectArray[0] + ";");
					valueList.add(objectArray[1]);
				}
				// 查找缺失的name
				for (int m = 0; m < cellStringArray.length; m++) {
					if (!valueList.contains(cellStringArray[m])) {
						falseName.append(cellStringArray[m]);
						continue;
					}
					trueName.append(cellStringArray[m] + ";");
				}
				temp.put(propertyId, idBuffer.toString());
				temp.put(propertyName, trueName.toString());
				error = true;
				cellString = falseName.toString();
			}
		}
		if (error) {
			KmssMessage message = new KmssMessage(
					errorMsg,
					cellString,
					i + 1,
					SysTransportTableUtil
							.getSysSimpleOrExtendPropertyMessage(
									property, locale));
			contentMessage.addError(message);
			temp.put(propertyId, "");
			temp.put(propertyName, "");
		}
		return false;
	}

	private String getFirstStringInComplex(String s) {
		if (s.indexOf("//") > -1) {
			s = s.split("//")[0];
		} else if (s.indexOf("/") > -1) {
			s = s.split("/")[0];
		}
		return s;
	}

	/**
	 * 精确搜索
	 * 
	 * 当重复人员姓名时：1、输入人员姓名/ID，这个时候会根据人员姓名和ID去确定唯一性
	 * 2、输入人员姓名//登录名，这个时候会根据人员姓名和登录名去确定唯一性
	 * 
	 * 当重复部门、岗位、机构等时： 1、输入名称/ID，这个时候会根据名称和ID去确定唯一性 2、输入名称//编号，这个时候会根据名称和编号去确定唯一性
	 * 
	 * demoSql:SELECT * FROM sys_org_element WHERE (fd_name='部门负责人' AND fd_id='1022') OR (fd_name='陈高' AND fd_id='11d75b1586c59abc42c83914379be4e5') OR (fd_name IN ('董明','李冬'))
	 * 
	 * @param hqlInfo
	 * @param modelPro
	 * @param cellList
	 * @return
	 */
	private String getAccurateHqlWhereBlock(HQLInfo hqlInfo,
			String fdName, List cellList, boolean isPerson) {

		String tableName = hqlInfo.getModelTable();
		StringBuffer whereBlock = new StringBuffer();
		StringBuffer onlyNames = new StringBuffer();
		for (int i = 0; i < cellList.size(); i++) {
			String cell = (String) cellList.get(i);
			if (cell.indexOf("//") > -1) {
				String[] a = cell.split("//");
				if (a.length > 1) {
					if (i != 0) {
						whereBlock.append("or ");
					}
					whereBlock.append("(" + tableName + "." + fdName
							+ "=\'" + a[0] + "\'");
					if (isPerson) {
						// 人员的用登录名匹配
						whereBlock.append(" and " + tableName + "."
								+ "fdLoginName=\'" + a[1] + "\'");
					} else {
						// 非人员的用编号匹配
						whereBlock.append(" and " + tableName + "."
								+ "fdNo=\'" + a[1] + "\'");
					}
					whereBlock.append(")");
				}

			} else if (cell.indexOf("/") > -1) {
				String[] a = cell.split("/");
				if (a.length > 1) {
					if (i != 0) {
						whereBlock.append("or ");
					}
					whereBlock.append("(");
					whereBlock
							.append(tableName + "." + "fdId=\'" + a[1] + "\'");
					whereBlock.append("and " + tableName + "." + fdName + "=\'"
							+ a[0] + "\'");
					whereBlock.append(")");
				}
			} else {
				onlyNames.append("'" + cell + "',");
			}
		}
		if (onlyNames.length() > 0) {
			whereBlock.append(" or " + fdName + " in ("
					+ onlyNames.substring(0, onlyNames.length() - 1) + ")");
		}
		return whereBlock.toString();
	}

	/**
	 * 解析单元格为“XXX;XXX;XX”格式的内容,转换为“'XXX','XXX','XXX'”
	 * 
	 * @param hqlInfo
	 * @param modelPro
	 * @param cellList
	 * @return
	 */
	private String getHqlWhereBlockByParseList(HQLInfo hqlInfo,
			SysDictCommonProperty modelPro, List cellList) {
		if (cellList == null) {
			return "";
		}
		// 去除/
		String whereBlock = cellList.toString();
		whereBlock = whereBlock.substring(1, whereBlock.length() - 1);
		if ("String".equals(modelPro.getType())
				|| "RTF".equals(modelPro.getType())) {
			whereBlock = StringUtil.replace(whereBlock, "'", "''");
			whereBlock = "('" + whereBlock.replaceAll("\\s*,\\s*", "','")
					+ "')";
		}
		whereBlock = hqlInfo.getModelTable() + "." + modelPro.getName() + " IN "
				+ whereBlock + " AND fdIsAvailable = 1";
		return whereBlock;
	}

	/**
	 * 把list转换为map
	 * 
	 * @param idAndName
	 * @return
	 */
	private Map listToMap(List idAndName) {
		Map resultMap = new HashMap();
		for (Iterator iterator = idAndName.iterator(); iterator.hasNext();) {
			Object[] objectArray = (Object[]) iterator.next();
			resultMap.put(objectArray[0], objectArray[1]);
		}
		return resultMap;
	}

	/**
	 * 查找map的value里面是否有重复
	 * 
	 * @param idAndNameMap
	 * @return
	 */
	private String getDoubleName(Map idAndNameMap) {
		// TODO 自动生成的方法存根
		String doubleName = "";
		// 临时存储的list，用于判断里面是否有重复值
		List templateList = new ArrayList();
		Iterator<String> names = idAndNameMap.values().iterator();
		while (names.hasNext()) {
			String name = names.next();
			// 只要有重复的，就跳出循环，不再校验后面的
			if (templateList.contains(name)) {
				doubleName = name;
				break;
			} else {
				// 没有即添加
				templateList.add(name);
			}
		}
		return doubleName;
	}

}
