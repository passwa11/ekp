package com.landray.kmss.sys.oms.temp;

/**
* 类名
* @author yuLiang
* @version 1.0 创建时间：2019年12月11日
*/
public enum OmsTempSynModel {
	
	///////////////////////////////////////////旧的JAVA API 模式//////////////////////////////////
	/**
	 * 同步部门、人员
	 */
	OMS_TEMP_SYN_MODEL_1("同步部门、人员",1),
	
	/**
	 * 1、同步部门、人员、部门人员关系：人员的部门属性表示主部门，
	 * 2、这种模式需要在该部门下新建一个名称为“成员”的岗位，并且将该人员放入该岗位中，
	 * 3、这种模式下部门人员关系即用来做部门人员关系，又做部门人员排序号
	 */
	OMS_TEMP_SYN_MODEL_20("同步部门、人员、部门人员关系",20),
	
	/**
	 * 1、同步部门、人员、部门人员关系：人员的部门属性无用，系统自动选择其中一个部门为用户主部门，
	 * 2、这种模式需要在该部门下新建一个名称为“成员”的岗位，并且将该人员放入该岗位中，
	 * 3、这种模式下部门人员关系即用来做部门人员关系，又做部门人员排序号
	 */
	OMS_TEMP_SYN_MODEL_21("同步部门、人员、部门人员关系",21),
	
	/**
	 * 同步部门、岗位、人员、岗位人员关系
	 */
	OMS_TEMP_SYN_MODEL_30("同步部门、岗位、人员、岗位人员关系",30),
	
	/**
	 * 同步部门、岗位、人员、岗位人员关系、部门人员关系：人员的部门属性表示主部门，这种模式部门人员关系只用来做排序号
	 */
	OMS_TEMP_SYN_MODEL_40("同步部门、岗位、人员、岗位人员关系、部门人员关系",40),
	
	/**
	 * 同步部门、岗位、人员、岗位人员关系、部门人员关系：人员的部门属性无用，暂不支持
	 */
	OMS_TEMP_SYN_MODEL_41("同步部门、岗位、人员、岗位人员关系、部门人员关系",41),
	
	
	
	///////////////////////////////////////////新的JAVA API 模式//////////////////////////////////
	/**
	 * 同步部门、人员
	 */
	OMS_TEMP_SYN_MODEL_100("同步部门、人员",100),
	
	/**
	 * 1、同步部门、人员、部门人员关系：人员的部门属性表示主部门，
	 * 2、这种模式需要在该部门下新建一个名称为“成员”的岗位，并且将该人员放入该岗位中，
	 * 3、这种模式下部门人员关系即用来做部门人员关系，又做部门人员排序号
	 */
	OMS_TEMP_SYN_MODEL_200("同步部门、人员、部门人员关系",200),
	
	/**
	 * 同步部门、岗位、人员、岗位人员关系
	 */
	OMS_TEMP_SYN_MODEL_300("同步部门、岗位、人员、岗位人员关系",300),
	
	/**
	 * 同步部门、岗位、人员、岗位人员关系、部门人员关系：人员的部门属性表示主部门，这种模式部门人员关系只用来做排序号
	 */
	OMS_TEMP_SYN_MODEL_400("同步部门、岗位、人员、岗位人员关系、部门人员关系",400);

	
	private String desc;
	private int value;
	 
    private OmsTempSynModel(String desc,int value){
        this.value = value;
        this.desc = desc;
    }

	public String getDesc() {
		return desc;
	}

	public int getValue() {
		return value;
	} 
	
	/**
	 * 根据type获取集成类型
	 * @param type
	 * @return
	 */
	public static OmsTempSynModel getEnumByValue(Integer value){
		if(value == null){
			return null;
		}
		for(OmsTempSynModel temp:OmsTempSynModel.values()){
			if(temp.getValue() == value){
				return temp;
			}
		}
		return null;
	}
    
} 
