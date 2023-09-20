define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "mui/rating/Rating","mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile" ], function(declare, domConstruct,
		domClass, domStyle, domAttr, ItemBase, util, Praise,Msg) {
	var item = declare("sys.circulation.CirculationItemMixin", [ ItemBase ], {
		tag : "li",
		// 传阅内容
		content : "",
		// 备注
		remark : "",
		// 创建时间
		created : "",
		
		// 标题
		label : "",
		// 创建者图像
		icon : "",
		// 去除焦点虚线框
		tabIndex : "",
		// 传阅荐人名单
		receivedCirCulatorNames : "",
		
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
				domConstruct.create("span", {
					className : "muiCirculationLabel muiAuthor",
					innerHTML : this.label
				}, this.domNode);
			}

			// 半月遮
			domConstruct.create('div', {
				className : 'muiCirculationRound'
			}, this.domNode);

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
			

			if (this.created) {
				this.createdNode = domConstruct.create("div", {
					className : "muiCirculationCreated",
					innerHTML : '<i class="mui mui-time"></i>' + this.created
				}, this.infoNode);
			}

			// 传阅对象
			this.targetNode = domConstruct.create('div', {
				className : 'muiCirculationTarget',
				innerHTML : '<span class="muiCirculationTargetTitle">' + Msg['sysCirculationMain.mobile.circulors']+':</span>'
			}, this.contentNode, 'last');
			
			// 被传阅人员
			if (this.receivedCirCulatorNames) {
				this.circulationGoalNamesNode = domConstruct.create('span', {
					className : 'muiCirculationTargetName',
					innerHTML : this.receivedCirCulatorNames
				}, this.targetNode, 'last');
			}
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