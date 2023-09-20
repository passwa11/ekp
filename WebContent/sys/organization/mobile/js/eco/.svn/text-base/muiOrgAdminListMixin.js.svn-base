define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin", "dojo/topic",
		"sys/organization/mobile/js/eco/muiOrgAdminListItemMixin", "mui/util" ],
	function(declare, _TemplateItemListMixin, topic, muiOrgListItemMixin, util) {

	return declare("sys.org.eco.admin.list.mixin", [ _TemplateItemListMixin ], {

		itemRenderer : muiOrgListItemMixin,

		buildRendering : function() {
			this.inherited(arguments);
			this.defaultUrl = util.formatUrl(this.url);
			this.subscribe("/mui/org/eco/admin/list/changeUrl", "changeUrl");
			this.subscribe("/mui/org/eco/admin/list/clickData", "clickData");
			this.subscribe("/mui/org/eco/admin/list/sendCardData", "sendCardData");
		},
		
		changeUrl : function(key, isCancel) {
			if(isCancel)
				this.url = this.defaultUrl;
			else
				this.url = this.defaultUrl + '&keyword=' + key;
		},
		
		// 先存起来点击对象的信息
		clickData : function(data) {
			this.objData = data;
		},
		
		// 等待muiOrgEcoCard加载完再提取数据
		sendCardData : function() {
			topic.publish("/sys/org/eco/card/reload", this.objData);
		}
		
	});
});