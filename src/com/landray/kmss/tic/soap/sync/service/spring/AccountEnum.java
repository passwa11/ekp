package com.landray.kmss.tic.soap.sync.service.spring;

/**
 * EAS会计科目的数据库模板
 * 
 * Date:2019/1/20
 * 
 * @author 何建华
 * 
 */
public enum AccountEnum {
	
	fdNumber(1,"fd_number","会计科目编码"),
	fdName(2,"fd_name","会计科目名称"),
	fdNumber1(3,"fd_number1","科目表I编码"),
	fdName1(4,"fd_name1","科目表I名称"),
	fdTypeNumber(5,"fd_type_number","科目类型编码"),
	fdTypeName(6,"fd_type_name","科目类型名称"),
	longName(7,"long_name","长名称"),
	parent_number(8,"parent_number","父科目编码"),
	
	parent_name(9,"parent_name","父科目名称 "),
	fd_dongjie(10,"fd_dongjie","是否集团冻结"),
	fd_top_number(11,"fd_top_number","上级辅助核算编码"),
	fd_top_name(12,"fd_top_name","上级辅助核算名称"),
	fd_is_company_dj(13,"fd_is_company_dj","是否公司冻结"),
	fd_helpercoder(14,"fd_helpercoder","助记码"),
	fd_company_code(15,"fd_company_code","公司编码"),
	fd_company_name(16,"parent_number","公司名称"),
	
	
	fd_currency_number(17,"fd_currency_number","币种编码 "),
	fd_currency_name(18,"fd_currency_name","币种名称"),
	fd_subject_balance_direction(19,"fd_subject_balance_direction","科目余额方向"),
	fd_terminal_remittance(20,"fd_terminal_remittance","是否期末调汇"),
	fd_cash_equivalents(21,"fd_cash_equivalents","现金等价物"),
	fd_account_interest(22,"fd_account_interest","科目计息"),
	fd_interest_rate(23,"fd_interest_rate","日利率"),
	fd_qty_accounting(24,"fd_qty_accounting","数量核算"),
	
	
	fd_unitgroup_number(25,"fd_unitgroup_number","计量单位组编码 "),
	fd_unitgroup_name(26,"fd_unitgroup_name","计量单位组名称 "),
	fd_unit_number(27,"fd_unit_number","计量单位编码"),
	fd_unit_name(28,"fd_unit_name","计量单位名称"),
	fd_current_account(29,"fd_current_account","往来核算"),
	fd_profit_category(30,"fd_profit_category","损益类别"),
	fd_control_attribute(31,"fd_control_attribute","控制属性"),
	fd_aux_accounting_code(32,"fd_aux_accounting_code","公司辅助核算编码"),
	
	fd_aux_accounting_name(33,"fd_aux_accounting_name","公司辅助核算名称");

	//防止字段值被修改，增加的字段也统一final表示常量
	private final int lineNumber;//接口字段行号（只能通过接口行号确定接口字段）
	private final String mappingValue;//数据库字段
	private final String description;//数据库字段描述

	private AccountEnum(int lineNumber,String mappingValue,String description){
		this.lineNumber = lineNumber;
		this.mappingValue = mappingValue;
		this.description=description;
	}
	//根据key获取枚举
	public static AccountEnum getEnumByLineNumber(int lineNumber){
		for(AccountEnum temp:AccountEnum.values()){
			if(temp.getLineNumber()==lineNumber){
				return temp;
			}
		}
		return null;
	}

	//根据key获取枚举的value
	public static String getEnumValueByKey(int lineNumber){
		for(AccountEnum temp:AccountEnum.values()){
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
