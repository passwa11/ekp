package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.model.BaseModel;

/**
 * License人员数量受限，凡是在此表中的人员将不能登录，登录时提示『您的License可用人员达到上限，此账号暂时无法登录』
 * <p>
 * 需要注意以下几个入口：
 * 1. 新增人员
 * 2. 第三方组织同步
 * 3. 高级导入
 * 4. 基础数据导入
 * <p>
 * 同时防止线下对数据库的操作，系统每次启动时需要做一个校验
 * 
 * @author 潘永辉 2020年1月14日
 *
 */
public class SysOrgPersonRestrict extends BaseModel {

	@Override
	public Class getFormClass() {
		return null;
	}

}
