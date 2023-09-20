define([ "dojo/_base/declare", "dojo/dom-construct",
		"dojo/dom-style", "dojox/mobile/_ItemBase",
		"mui/util","mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile" , 
		"mui/openProxyMixin"], function(declare, domConstruct, domStyle, ItemBase, util, Msg, openProxyMixin) {
	var item = declare("sys.circulation.CirculationItemNewVersionMixin", [ ItemBase ,openProxyMixin], {
		tag : "li",
		// 传阅内容
		content : "",
		// 备注
		remark : "",
		// 创建时间
		created : "",
		
		href:"",
		
		// 标题
		label : "",
		// 创建者图像
		icon : "",
		// 传阅荐人名单
		receivedCirCulatorNames : "",
		
		buildRendering : function() {
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
							className : 'muiCirculationItem'
						});
				
				if(this.href){
					this.href = util.setUrlParameter(this.href, "isNewVersion", true);
					this.proxyClick(this.domNode, this.href, '_blank');
				}
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
					innerHTML : this.label
				}, circulationDiv);
				if(this.created){
					domConstruct.create("span", {
						className : "muiCirculationLabel muiCreated",
						innerHTML : this.created
					}, circulationDiv);
				}
			}
			this.contentNode = domConstruct.create('div', {
				className : 'muiCirculationContent',
				innerHTML : this.content
			}, this.domNode, 'last');

			this.infoNode = domConstruct.create('div', {
				className : 'muiCirculationInfo'
			}, this.contentNode);

			if (this.remark) {
				this.remarkNode = domConstruct.create("p", {
					className : "muiCirculationRemark",
					innerHTML : this.remark.replace(/\r\n/g,'<br>').replace(/\n/g,'<br>')
				}, this.infoNode);
			}
			var title = Msg['sysCirculationMain.mobile.circulors']+":";
			if (this.receivedCirCulatorNames) {
				title += this.receivedCirCulatorNames;
			}
			// 传阅对象
			this.targetNode = domConstruct.create('div', {
				className : 'muiCirculationTarget'
			}, this.contentNode, 'last');
			// 传阅对象
			this.titleNode = domConstruct.create('div', {
				className : 'muiCirculationTargetTitle',
				innerHTML : title
			}, this.targetNode, 'last');
			this.closeNode = domConstruct.create('span', {
				style:{
					'display': 'none',
					'margin-left': '1rem',
					'color': '#4285F4'
				},
				innerHTML : Msg['sysCirculationMain.mobile.close']
			}, this.titleNode, 'last');
			this.openNode = domConstruct.create('div', {
				style:{
					'display': 'none',
				    'color': '#4285F4'
				},
				innerHTML : Msg['sysCirculationMain.mobile.open']
			}, this.targetNode, 'last');
			var self = this;
			this.defer(function(){
				if(self.titleNode.offsetHeight>20){
					domStyle.set(self.titleNode,{
						"width":"90%",
						"white-space":"nowrap"
					})
					domStyle.set(self.openNode,{
						'display': ''
					})
					self.connect(self.openNode, 'onclick', self.openDetail);
					self.connect(self.closeNode, 'onclick', self.closeDetail);
				}
			},0);
		},

		openDetail:function(event){
			revent = event || window.event;
			event.cancelBubble = true;
			if (event.stopPropagation) {event.stopPropagation();}
			domStyle.set(this.titleNode,{
				"width":"100%",
				"white-space":"normal"
			})
			domStyle.set(this.openNode,{
				'display': 'none'
			})
			domStyle.set(this.closeNode,{
				'display': ''
			})
		},
		
		closeDetail:function(event){
			revent = event || window.event;
			event.cancelBubble = true;
			if (event.stopPropagation) {event.stopPropagation();}
			domStyle.set(this.titleNode,{
				"width":"90%",
				"white-space":"nowrap"
			})
			domStyle.set(this.openNode,{
				'display': ''
			})
			domStyle.set(this.closeNode,{
				'display': 'none'
			})
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