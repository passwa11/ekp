define(
		[ "dojo/_base/declare", "mui/property/filter/FilterAddress", "dojo/dom-construct",'mui/createUtils', "dojo/topic",
			"dojo/text!./address/tmpl.tmpl", "dojo/html","dojo/_base/lang", 'dojo/_base/array', "mui/form/Address", "mui/form/CheckBox"],
		function(declare, FilterAddress, domConstruct, createUtils, topic, tmpl, html, lang, array, Address, CheckBox) {

			var h = createUtils.createTemplate;
			
			var claz = declare("sys.modeling.main.resources.js.mobile.listView.FilterAddress", [ FilterAddress ],{
				
				CHECK_SET: 'mui/form/checkbox/change',
				
				isIncludeSubPre : "_prop_.",
				
				isIncludeSubVal : false,
				
				buildRendering : function() {
					this.inherited(arguments);
					
					this.subscribe(this.CHECK_SET, 'onCheckBoxChange');
				},
				
				render : function(ids, names, isIncludeSubVal) {
					var self = this;
					var isIncludeSubHtml = this.getIsIncludeSubHtml(isIncludeSubVal);
					// 设置模板内容
					var dhs = new html._ContentSetter({
						parseContent : true,
						cleanContent : true,
						node : this.contentNode,
						onBegin : function() {
							this.content = lang.replace(this.content, {
								type : self.type,
								idField : self.name,
								nameField : self.name,
								curIds : ids || '',
								curNames : names || '',
								isIncludeSubHtml : isIncludeSubHtml
							});
							this.inherited("onBegin", arguments);
						}
					});
					dhs.set(tmpl);
					dhs.parseDeferred.then(lang.hitch(this, function(wgtList) {
						array.forEach(wgtList, function(wgt){
							if(wgt instanceof CheckBox){
								self.isIncludeSubWgt = wgt;
								self.initIsIncludeSubWgt(wgt)
							}
							if(wgt instanceof Address){
								self.addressWidget = wgt;								
							}
						});
					}));
					dhs.tearDown();
				},
				
				// 获取显示子部门的HTML
				getIsIncludeSubHtml : function(isIncludeSubVal){
					var isIncludeSubHtml = "";
					if(this.isIncludeSub){
						var self = this;
						// 获取路由的值
						topic.publish(this.GET_EVENT , this, {
							name: this.isIncludeSubPre + this.name,
							cb: function(value){
								self.isIncludeSubVal = value === "true" ? true : false; 
							}
						});
						isIncludeSubHtml = h("div", {
							dojoType: 'mui/form/CheckBox',
							dojoProps: {
								text : "显示子部门",
								name : this.isIncludeSubPre + this.name,
								checked : this.isIncludeSubVal === true ? true : false
							},
							style : "display:none"
						});
					}
					return isIncludeSubHtml;
				},
				
				// 初始化子组件
				initIsIncludeSubWgt : function(wgt){
					// wgt.checkboxNode.style["padding-left"] = "6px";
				},
				
				// 显示子部门的值变更
				onCheckBoxChange : function(srcWgt, params){
					if(this.isIncludeSubWgt && srcWgt && srcWgt === this.isIncludeSubWgt){
						topic.publish(this.SET_EVENT, this, {
							name: this.isIncludeSubWgt.name,
							value: this.isIncludeSubWgt.checked + ""
						});
					}
				}
			});
			return claz;
		});