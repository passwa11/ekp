package com.landray.kmss.common.model;

public interface IBaseCoreInnerModel extends IBaseModel {
	public String getFdModelName();

	public void setFdModelName(String fdModelName);

	public String getFdModelId();

	public void setFdModelId(String fdModelId);

	public String getFdKey();

	public void setFdKey(String fdKey);
	
	/**
	 * <pre>
	 * 获取机制名称的资源文件key，用于表示该Model是属于哪个机制的，默认是Model的简单类名，比如：
	 * 
	 * SysAttMain表示附件机制，在资源文件中应该存在一个mech.SysAttMain=附件机制的项
	 * </pre>
	 * @return
	 */
	public String getMechanismName(); 
	
}
