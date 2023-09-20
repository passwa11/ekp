package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2020年1月3日
*@Description                   单点跳转到业务分类
*/

public enum ElecChannelSsoTargeEnum {
	
	
	home ,//第三方首页
	
	signing,//合同签署页
	wapSigning, //wap合同签署页
	
	doc ,//合同管理页面
	docPrepare, //创建合同页面
	docDetail, //合同详情页
	docView, //合同预览页
	docSignView, //带签章的合同预览页；
	
	templates, //模板管理页面
	template, //模板编辑页面（建议同时传参customTemplateId）
	templatePreview, //模版生成文件预览页, 
	
	entAuth, //企业实名页
	personAuth, //个人实名页
	
	seal, //印章管理页
	
	signature, //签名管理页

	customize //自定义跳转页面

	;

}
