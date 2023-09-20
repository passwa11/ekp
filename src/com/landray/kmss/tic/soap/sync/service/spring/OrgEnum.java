package com.landray.kmss.tic.soap.sync.service.spring;

/**
 *
 * EAS取组织机构详细信息(含成本中心、利润中心等)的数据库模板
 * 
 * Date:2019/1/20
 * 
 * @author 何建华
 * 
 */
public enum OrgEnum {
	
	fd_name(1,"fd_name","机构名称"),
	fd_description(2,"fd_description","描述"),
	fd_short_name(3,"fd_short_name","简称"),
	fd_yes(4,"fd_yes","是否为集团公司"),
	fd_effective_date(5,"fd_effective_date","生效日期"),
	fd_expiration_date(6,"fd_expiration_date","失效日期"),
	fd_is_freeze(7,"fd_is_freeze","是否冻结"),
	fd_financial_org(8,"fd_financial_org","是否是财务组织"),
	
	fd_administrative_org(9,"fd_administrative_org","是否是行政组织"),
	fd_sales_org(10,"fd_sales_org","是否是销售组织"),
	fd_purchasing_org(11,"fd_purchasing_org","是否是采购组织"),
	fd_inventory_org(12,"fd_inventory_org","是否是库存组织"),
	fd_profit_center(13,"fd_profit_center","是否是利润中心"),
	fd_cost_center(14,"fd_cost_center","是否是成本中心"),
	fd_cu(15,"fd_cu","是否是CU"),
	fd_merge_scope(16,"fd_merge_scope","是否合并范围"),
	
	fd_hr_org_unit(17,"fd_hr_org_unit","是否是HR组织单元"),
	fd_creator_username(18,"fd_creator_username","创建者用户名"),
	fd_creator_realname(19,"fd_creator_realname","创建者用户实名"),
	doc_create_time(20,"doc_create_time","创建时间");
	
	
	//防止字段值被修改，增加的字段也统一final表示常量
	private final int lineNumber;//接口字段行号（只能通过接口行号确定接口字段）
	private final String mappingValue;//数据库字段
	private final String description;//数据库字段描述

	private OrgEnum(int lineNumber,String mappingValue,String description){
		this.lineNumber = lineNumber;
		this.mappingValue = mappingValue;
		this.description=description;
	}
	//根据key获取枚举
	public static OrgEnum getEnumByLineNumber(int lineNumber){
		for(OrgEnum temp:OrgEnum.values()){
			if(temp.getLineNumber()==lineNumber){
				return temp;
			}
		}
		return null;
	}

	//根据key获取枚举的value
	public static String getEnumValueByKey(int lineNumber){
		for(OrgEnum temp:OrgEnum.values()){
			if(temp.getLineNumber()==lineNumber){
				return temp.getMappingValue();
			}
		}
		return null;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	public String getMappingValue() {
		return mappingValue;
	}
	public String getDescription() {
		return description;
	}

}
