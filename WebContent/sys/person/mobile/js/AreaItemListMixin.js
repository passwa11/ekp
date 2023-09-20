define([
    'dojo/_base/declare',
    'dojo/query',
    'dojo/date/locale',
    'mui/i18n/i18n!sys-mobile',
    'mui/util',
	'mui/list/_TemplateItemListMixin',
	'./item/AreaItemMixin'
	], function(declare, query, locale, msg ,util,_TemplateItemMixin, AreaItemMixin) {
	
	return declare('sys.person.AreaItemListMixin', [_TemplateItemMixin], {
		
		itemRenderer : AreaItemMixin,
		
		dataUrl : '/sys/person/sys_person_switchArea/sysPersonSwitchArea.do?method=getDataList',
		
		//往下查看数据
		_cateChange:function(srcObj,evt){
			this.dataUrl = util.setUrlParameter(this.dataUrl,'id', evt.fdId);
			this.inherited(arguments);
		},
		
		resolveItems : function(items) {
			
			var t = this.inherited(arguments) || [];
			var res = [];
			for(var i = 0; i < t.length; i++) {
				res.push({
					fdId: t[i].value || t[i].id || '',
					fdName: t[i].text || t[i].name || '',
					child: t[i].child || '',
					isShowCheckBox: t[i].isShowCheckBox || ''
				});
			}
			
			return res;
		}
		
	});
});