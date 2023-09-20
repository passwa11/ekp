package com.landray.kmss.sys.oms.temp;

/**
* 类名
* @author yuLiang
* @version 1.0 创建时间：2019年12月11日
*/
public enum OmsTempSynFailType {

	DATA_ERR_TYPE_DEPT_ID("部门ID为空","dpetIdIsNull"),
	
	DATA_ERR_TYPE_POST_ID("岗位ID为空","postIdIsNull"),

	DATA_ERR_TYPE_PERSON_ID("人员ID为空","personIdIsNull"),

	DATA_ERR_TYPE_NAME("名称为空","nameIsNull"),

	DATA_ERR_TYPE_IS_AVAILABLE("是否有效为空","availableIsNull"),

	DATA_ERR_TYPE_PARENT_ID_NOT_FOUND("父部门ID找不到","parentIdNotFound"),

	DATA_ERR_TYPE_POST_ID_NOT_FOUND("岗位ID找不到","postIdNotFound"),

	DATA_ERR_TYPE_DEPT_ID_NOT_FOUND("部门ID找不到","deptIdNotFound"),

	DATA_ERR_TYPE_PERSON_ID_NOT_FOUND("人员ID找不到","personIdNotFound"),

	DATA_ERR_TYPE_DEPT_ID_DUPLICATE("部门ID重复","deptIdDuplicate"),

	DATA_ERR_TYPE_POST_ID_DUPLICATE("岗位ID重复","postIdDuplicate"),

	DATA_ERR_TYPE_PERSON_ID_DUPLICATE("人员ID重复","personIdDuplicate"),
	
	DATA_ERR_TYPE_SYN_FAIL("同步失败","othorErr"),
	
	DATA_ERR_TYPE_PERSON_LOGIN_NAME("人员登录名为空","loginNameIsNull");
	
	private String desc;
	private String value;
	 
    private OmsTempSynFailType(String desc,String value){
        this.value = value;
        this.desc = desc;
    }

	public String getDesc() {
		return desc;
	}

	public String getValue() {
		return value;
	} 
	
	/**
	 * 根据type获取错误类型
	 * @param type
	 * @return
	 */
	public static OmsTempSynFailType getEnumByValue(String value){
		if(value == null){
			return null;
		}
		for(OmsTempSynFailType temp:OmsTempSynFailType.values()){
			if(temp.getValue().equals(value)){
				return temp;
			}
		}
		return null;
	}
    
} 
