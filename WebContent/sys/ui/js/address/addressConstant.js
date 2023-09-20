/**
 * 常量、API统一管理处
 */
define(function(require, exports, module) {
	
	module.exports = {
		
		event :{
			//添加人员事件
			ADDRESS_ELEMENT_SELECT : 'address.element.select',	
			
			//更新人员列表事件
			ADDRESS_ELEMENT_UPDATE : 'address.element.update',
			
			//移除人员事件
			ADDRESS_ELEMENT_REMOVE : 'address.element.remove',
			
			//移除所有人员事件,
			//TODO 考虑移除,可以用上面那个事件代替
			ADDRESS_ELEMENT_CLEAR : 'address.element.clear',
			
			//保存人员事件
			ADDRESS_ELEMENT_SAVE : 'address.element.save',
				
			//备选列表数据源发生变化事件
			ADDRESS_DATASOURCE_CHANGED : 'address.datasource.changed',
			
			//标签选中变化事件
			ADDRESS_TAB_SELECTED : 'address.tab.selected',
			
			//导航路径选中变化事件
			ADDRESS_NAV_SELECTED : 'address.nav.selected',
			
			//搜索数据事件
			ADDRESS_SEARCH_FINISH : 'address.search.finish',
			
			//取消搜索数据事件
			ADDRESS_SEARCH_CANCEL : 'address.search.cancel' ,
			
			//组织架构类型改变事件
			ADDRESS_ORGTYPEFILTER_CHANGE : 'address.orgTypeFilter.change'
		},
		
		image : {
			//机构图标
			1 : Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/address_orgType_org.png',
			
			//部门图标
			2 :  Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/address_orgType_dept.png',
			
			//岗位图标
			4 :  Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/address_orgType_post.png',
			
			//群组图标
			16 :  Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/address_orgType_group.png',
			
			//角色图标
			32 :  Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/address_orgType_sysrole.png',
			
			//外部机构图标
			101 : Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/address_orgType_externalOrg.png',
			
			//外部部门图标
			102 : Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/address_orgType_externalDept.png',
			
			//外部岗位图标
			104 : Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/address_orgType_externalPost.png'
		},
		
		dingImage : {
			//机构图标
			1 : Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/ding/address_orgType_org.png',
			
			//部门图标
			2 :  Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/ding/address_orgType_dept.png',
			
			//岗位图标
			4 :  Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/ding/address_orgType_post.png',
			
			//群组图标
			16 :  Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/ding/address_orgType_group.png',
			
			//角色图标
			32 :  Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/ding/address_orgType_sysrole.png',
			
			//外部机构图标
			101 : Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/ding/address_orgType_externalOrg.png',
			
			//外部部门图标
			102 : Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/ding/address_orgType_externalDept.png',
			
			//外部岗位图标
			104 : Com_Parameter.ContextPath+'sys/ui/extend/theme/default/images/address/ding/address_orgType_externalPost.png'
		},
		
		
		
			
	};
	
	
	
});