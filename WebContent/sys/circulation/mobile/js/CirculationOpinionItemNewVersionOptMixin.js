define([ "dojo/_base/declare", "dojo/dom-construct",
		"dojo/dom-style", "dojox/mobile/_ItemBase",
		"mui/util","mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile" , 
		"mui/openProxyMixin"], function(declare, domConstruct, domStyle, ItemBase, util, Msg, openProxyMixin) {
	var item = declare("sys.circulation.CirculationItemNewVersionMixin", [ ItemBase ,openProxyMixin], {
		tag : "li",
		
		summary:"",
		
		//创建时间
		created:"",
		
		//创建者
		creator:"",
		
		//创建人图像
		icon:"",
		
		buildRendering : function() {
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
							className : 'muiCirculationItem'
						});
			}
			this.inherited(arguments);
			if (!this._templated)
				this.buildInternalRender();
		},
		buildInternalRender : function() {
			if (this.icon) {
				var imgDivNode = domConstruct.create("div", {
					className : "muiCirculationIcon"
				}, this.containerNode);
				this.imgNode = domConstruct.create("img", {
					className : "muiCirculationImg",
					src : util.formatUrl(this.icon)
				}, imgDivNode);
			}
			if (this.label) {
				var circulationDiv = domConstruct.create("div", {
					className : "muiCirculationDiv"
				}, this.domNode);
				domConstruct.create("span", {
					className : "muiCirculationLabel muiAuthor",
					innerHTML : this.creator
				}, circulationDiv);
				if(this.created){
					domConstruct.create("span", {
						className : "muiCirculationLabel muiCreated",
						innerHTML : this.created
					}, circulationDiv);
				}
				domConstruct.create("span", {
					className : "muiCirculationReply",
					innerHTML : Msg['sysCirculationMain.mobile.replyOpinion']
				}, circulationDiv);
			}
			this.contentNode = domConstruct.create('div', {
				className : 'muiCirculationContent'
			}, this.domNode, 'last');

			this.infoNode = domConstruct.create('div', {
				className : 'muiCirculationInfo'
			}, this.contentNode);

			if (this.summary) {
				this.remarkNode = domConstruct.create("p", {
					className : "muiCirculationTarget",
					innerHTML : this.summary
				}, this.infoNode);
			}
			// 传阅对象
			this.targetNode = domConstruct.create('div', {
				className : 'muiCirculationTarget muiFontSizeM'
			}, this.contentNode, 'last');
			// 传阅对象
			this.titleNode = domConstruct.create('div', {
				className : 'muiCirculationRemark',
				innerHTML : this.label
			}, this.targetNode, 'last');
		},

		startup : function() {
			if (this._started) {
				return;
			}
			this.inherited(arguments);
		},

		_setLabelAttr : function(text) {
			if (text)
				this._set("label", text);
		}
	});
	return item;
});