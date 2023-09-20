define([ "dojo/_base/declare", "dojox/mobile/_ItemBase", "dojo/topic",
         "dojo/dom-construct", "dojo/dom-construct", "./muiOrgEcoAddressMixin", "mui/util"], 
		function(declare, ItemBase, topic, domConstruct, domConstruct, muiOrgEcoAddressMixin, util) {

	var item = declare("sys.org.eco.admin.list.item.mixin", [ ItemBase, muiOrgEcoAddressMixin ], {

		buildRendering : function() {
			
			this.inherited(arguments);
			this.domNode = domConstruct.create('li');
			
			var sysOrgEcoSearchListItemProfile = domConstruct.create('div', {
				className : 'sysOrgEcoSearchListItemProfile',
			}, this.domNode);
			domConstruct.create('img', {
				src : util.formatUrl('/sys/mobile/css/themes/default/images/address-dept-external.png')
			}, sysOrgEcoSearchListItemProfile);
			
			var sysOrgEcoSearchListItemContent = domConstruct.create('div', {
				className : 'sysOrgEcoSearchListItemContent'
			}, this.domNode);
			domConstruct.create('p', {
				innerHTML : this.fdName
			}, sysOrgEcoSearchListItemContent);
			if(this.parentsName)
				domConstruct.create('span', {
					innerHTML : this.parentsName
				}, sysOrgEcoSearchListItemContent);
			domConstruct.create('i', {
				className : 'fontmuis muis-to-right'
			}, sysOrgEcoSearchListItemContent);
			
			this.connect(this.domNode, 'click', 'onClick');
			
		},
		
		onClick : function() {
			this._selectCate();
			var data = new Object();
			data.isShow = true; 
			data.fdId = this.fdId;
			data.label = this.fdName;
			data.fdNo = this.fdNo;
			data.fdAdmin = this.adminsName == ""? undefined : this.adminsName; 
			topic.publish("/mui/org/eco/admin/list/clickData", data);
		},

		_setLabelAttr : function() {
		}

	});

	return item;
});