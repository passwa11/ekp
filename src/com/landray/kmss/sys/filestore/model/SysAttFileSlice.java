package com.landray.kmss.sys.filestore.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;

/**
 * 文件切片
 * 
 * @author 李衡
 * @version 1.0 2012-08-29
 */
public class SysAttFileSlice extends BaseModel {

	/**
	 * 开始点
	 */
	protected Long fdStartPoint;
	
	/**
	 * @return 开始点
	 */
	public Long getFdStartPoint() {
		return fdStartPoint;
	}
	
	/**
	 * @param fdStartPoint 开始点
	 */
	public void setFdStartPoint(Long fdStartPoint) {
		this.fdStartPoint = fdStartPoint;
	}
	
	/**
	 * 结束点
	 */
	protected Long fdEndPoint;
	
	/**
	 * @return 结束点
	 */
	public Long getFdEndPoint() {
		return fdEndPoint;
	}
	
	/**
	 * @param fdEndPoint 结束点
	 */
	public void setFdEndPoint(Long fdEndPoint) {
		this.fdEndPoint = fdEndPoint;
	}
	
	/**
	 * 状态
	 */
	protected Integer fdStatus;
	
	/**
	 * @return 状态
	 */
	public Integer getFdStatus() {
		return fdStatus;
	}
	
	/**
	 * @param fdStatus 状态
	 */
	public void setFdStatus(Integer fdStatus) {
		this.fdStatus = fdStatus;
	}
	
	/**
	 * 排序号
	 */
	protected Integer fdOrder;
	
	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}
	
	/**
	 * @param fdOrder 排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	/**
	 * 修改时间
	 */
	protected long fdModifyTime;
	
	/**
	 * @return 修改时间
	 */
	public long getFdModifyTime() {
		return fdModifyTime;
	}
	
	/**
	 * @param fdModifyTime 修改时间
	 */
	public void setFdModifyTime(long fdModifyTime) {
		this.fdModifyTime = fdModifyTime;
	}
	
	/**
	 * 所属文件
	 */
	protected SysAttFile fdFile;
	
	/**
	 * @return 所属文件
	 */
	public SysAttFile getFdFile() {
		return fdFile;
	}
	
	/**
	 * @param fdFile 所属文件
	 */
	public void setFdFile(SysAttFile fdFile) {
		this.fdFile = fdFile;
	}
	
	@Override
    public Class getFormClass() {
		return this.getClass();
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdFile.fdId", "fdFileId");
			toFormPropertyMap.put("fdFile.fdMd5", "fdFileName");
		}
		return toFormPropertyMap;
	}
}
