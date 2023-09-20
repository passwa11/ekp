define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require("lui/dialog");
	var dragdrop = require("lui/dragdrop");
	var JSON = require("./JSON");
	var msg = require("lang!sys-portal:desgin.msg");
	var showDialog = function(title,url,cb,w,h){
		w = w || 750;
		h = h || 500;
		return dialog.iframe(url,title,cb,{"width":w,"height":h});
	};

	/**
	 * 添加子级容器
	 * @param self  Editbox对象实例
	 * @return 返回 “添加子级容器” 菜单项配置信息（图标、显示文本、点击响应事件）
	 */
	var addVBox = function(self){
		return {
			"icon":"editMenuIconAddBoxChild",
			"text":""+msg['desgin.msg.addtable'],
			fn:function(){
				var dialogTitleText = this.text;
				var positionType = "inner"; // 容器创建的位置类型（inner:创建在当前容器内部）
				openVBoxConfigDialog(dialogTitleText,self,positionType);
			}
		};
	};

	/**
	 * 上方添加容器
	 * @param self  Editbox对象实例
	 * @return 返回 “上方添加同级容器” 菜单项配置信息（图标、显示文本、点击响应事件）
	 */
	var addBeforeVBox = function(self){
		return {
			"icon":"editMenuIconAddBoxAbove",
			"text":""+msg['desgin.msg.addTableAbove'],
			fn:function(){
				var dialogTitleText = this.text;
				var positionType = "above"; // 容器创建的位置类型（above:创建在当前容器上方）
				openVBoxConfigDialog(dialogTitleText,self,positionType);
			}
		};
	};

	/**
	 * 下方添加容器
	 * @param self  Editbox对象实例
	 * @return 返回 “下方添加同级容器” 菜单项配置信息（图标、显示文本、点击响应事件）
	 */
	var addAfterVBox = function(self){
		return {
			"icon":"editMenuIconAddBoxBelow",
			"text":""+msg['desgin.msg.addTableBelow'],
			fn:function(){
				var dialogTitleText = this.text;
				var positionType = "below"; // 容器创建的位置类型（below:创建在当前容器下方）
				openVBoxConfigDialog(dialogTitleText,self,positionType);
			}
		};
	};

	var openVBoxConfigDialog = function( dialogTitleText, self, positionType ){
		require.async(['./VBox','./Container','./Editable'],function(VBox,Container,Editable){
			// 获取容器配置弹出框内的需展示的样式初始化参数
			var dialogParameter = getVBoxDialogParameter(self,positionType,Editable);

			// 打开弹出框，定义回调函数(value为弹出窗口配置的JSON数据)
			showDialog(dialogTitleText,'/sys/portal/designer/jsp/addvbox.jsp',function(value){
				if(!value){
					return;
				}
				// 创建新的表格
				var xtable = $("<table width='100%' portal-key='"+self.uuid()+"' portal-type='./VBox'></table>");
				xtable.attr("data-config",escape(JSON.stringify({column:value.column,boxWidth:value.boxWidth,boxStyle:value.boxStyle})));
				xtable.attr("width",value.boxWidth);

				var xvbox = new VBox({"body":self.body ,"element":xtable});

				if(positionType=="inner"){ // 将新表格追加到当前容器内部
					self.element.append(xtable);
					xvbox.setParent(self);
					self.addChild(xvbox);
				}else if(positionType=="above"){ // 将新表格追加到当前容器同级上方
					self.element.closest("table[portal-type='./VBox']").before(xtable);
					xvbox.setParent(self.parent.parent);
					self.parent.parent.addChild(xvbox);
				}else if(positionType=="below"){ // 将新表格追加到当前容器同级下方
					self.element.closest("table[portal-type='./VBox']").next("div.widgetDock").after(xtable);
					xvbox.setParent(self.parent.parent);
					self.parent.parent.addChild(xvbox);
				}

				var xtr = $("<tr></tr>").appendTo(xtable);
				for ( var i = 0; i < value.cols.length; i++) {
					var col = value.cols[i];
					var xtd = $("<td valign='top' portal-key='"+self.uuid()+"' portal-type='./Container'></td>");
					xtd.attr("data-config",escape(JSON.stringify(col)));
					xtd.attr("width",col.columnWidth);
					var c = new Container({ "body":self.body, "element":xtd });
					c.setParent(xvbox);
					xvbox.addChild(c);
					if(i>0){
						var xtdSpacing = $("<td width='"+col.hSpacing+"' class='containerSpacing'></td>");
						xtdSpacing.attr("width",col.hSpacing);
						xtr.append(xtdSpacing);
						xtdSpacing.attr("style","min-width:"+col.hSpacing+"px;");
						c.spacingElement = xtdSpacing;
					}
					xtr.append(xtd);
					c.startup();
				}
				xvbox.startup();

			},750,500).dialogParameter=dialogParameter;
		});
	}

	/**
	 * 获取容器配置弹出框内的需展示的样式初始化参数
	 * @param self  Editbox对象实例
	 * @param positionType  添加新容器的位置类型（inner:创建在当前容器内部、above:创建在当前容器上方、below:创建在当前容器下方）
	 * @param Editable  Editable类对象
	 * @return 返回配置弹出框内的需展示的样式初始化参数
	 */
	var getVBoxDialogParameter = function( self, positionType, Editable ){
		var param = {};
		if(self instanceof Editable){
			var editableWidth = null;
			if($.trim(self.element.attr("data-config"))==""){
				editableWidth = self.element.outerWidth(true);
				self.element.attr("data-config",escape(JSON.stringify({"columnWidth":editableWidth,"vSpacing":self.config.vSpacing})));
			}else{
				var conf = toJSON(unescape(self.element.attr("data-config")));
				editableWidth = conf.columnWidth;
				if(editableWidth==null){
					editableWidth = self.element.outerWidth(true);
					conf.columnWidth = editableWidth;
					self.element.attr("data-config",escape(JSON.stringify(conf)));
				}
			}
			param.boxWidth = editableWidth;
			param.boxStyle = "";
		}else{
			// 如果新容器插入位置是在被选容器（TD单元格）的内部，则读取被选容器的的样式配置，如果插入位置在上方或下方，则读取被选容器（TD单元格）的父Table的样式配置(因为新容器最终是插入到Table的上方或下方)
			var dataConfigStr = positionType=="inner" ? self.element.attr("data-config") : self.element.closest("table[portal-type='./VBox']").attr("data-config");
			var conf = toJSON(unescape(dataConfigStr));
			param.boxWidth = positionType=="inner" ? conf.columnWidth :conf.boxWidth;
			param.boxStyle = conf.boxStyle;
		}
		return param;
	}

	/**
	 * 容器配置
	 * @param self  Editbox对象实例
	 * @return 返回 “容器配置” 菜单项配置信息（图标、显示文本、点击响应事件）
	 */
	var configVBox = function(self){
		return {
			"icon":"editMenuIconConfig",
			"text":""+msg['desgin.msg.configtable'],
			fn:function(){
				var xobj = this;
				require.async(['./VBox','./Container'],function(VBox,Container){
					var val = toJSON(unescape(self.parent.element.attr("data-config")));
					var param = {};
					param.boxWidth = val.boxWidth;
					param.column = val.column;
					param.boxStyle = val.boxStyle;
					param.cols=[];
					for(var i=0;i<param.column;i++){
						var obj = self.parent.children[i];
						var col = toJSON(unescape(obj.element.attr("data-config")));
						param.cols.push(col);
					}
					showDialog(xobj.text,'/sys/portal/designer/jsp/addvbox.jsp',function(value){
						if(!value){
							return;
						}
						self.parent.element.attr("data-config",escape(JSON.stringify({column:value.column,boxWidth:value.boxWidth,boxStyle:value.boxStyle})));
						for(var i=0;i<self.parent.children.length;i++){
							var obj = self.parent.children[i];
							if(obj.spacingElement != null){
								obj.spacingElement.attr("width",value.cols[i].hSpacing);
								obj.spacingElement.attr("style","min-width:"+value.cols[i].hSpacing+"px;");
							}
							obj.element.children(".widgetDock").css("height",value.cols[i].vSpacing);
							obj.element.attr("width",value.cols[i].columnWidth);
							obj.config.vSpacing = value.cols[i].vSpacing;
							obj.dropElement.attr("vSpacing",value.cols[i].vSpacing);
							obj.element.attr("data-config",escape(JSON.stringify(value.cols[i])));
						}
						if(value.cols.length > self.parent.children.length){
							for ( var i = self.parent.children.length; i < value.cols.length; i++) {
								var col = value.cols[i];
								var xtdSpacing = $("<td width='"+col.hSpacing+"' class='containerSpacing'></td>");
								xtdSpacing.attr("width",col.hSpacing);
								xtdSpacing.attr("style","min-width:"+col.hSpacing+"px;");
								var xtd = $("<td valign='top' portal-key='"+self.uuid()+"' portal-type='./Container'></td>");
								xtd.attr("data-config",escape(JSON.stringify(col)));
								xtd.attr("width",col.columnWidth);
								var obj = new Container({"body":self.body ,"element":xtd});
								obj.setParent(self.parent);
								self.parent.addChild(obj);
								self.parent.element.children("tbody").children("tr").append(xtdSpacing);
								obj.spacingElement = xtdSpacing;
								self.parent.element.children("tbody").children("tr").append(xtd);
								obj.startup();
							}
						}
						self.parent.alculateWidth();
					},750,500).dialogParameter = param;
				});
			}
		};
	};

	/**
	 * 添加部件
	 * @param self  Editbox对象实例
	 * @return 返回 “添加部件” 菜单项配置信息（图标、显示文本、点击响应事件）
	 */
	var addWidget = function(self){
		return {
			"icon":"editMenuIconAddWdiget",
			"text":""+msg['desgin.msg.addwidget'],
			fn:function(value){
				var xobj = this;
				require.async(['./Widget'],function(Widget){
					showDialog(xobj.text,'/sys/portal/designer/jsp/addwidget.jsp?scene='+self.getDesigner().scene,function(value){
						if(!value){
							return;
						}
						var temp = $("<div portal-key='"+self.uuid()+"' portal-type='./Widget'><script type='text/config'>"+JSON.stringify(value)+"</script></div>");
						if(value.panelType=="h" || value.panelType=="panel" || value.panelType=="none"){
							if($.trim(value.height) != ""){
								temp.css("height",value.height);
							}
						}else if(value.panelType=="v"){
							temp.css("height","");
						}
						var w = new Widget({"body":self.body ,"element":temp});
						w.setting = value;
						w.setParent(self);
						self.addChild(w);
						self.element.append(temp);
						w.startup();
					},750,510);
				});
			}
		};
	};

	/**
	 * 部件配置
	 * @param self  Editbox对象实例
	 * @return 返回 “部件配置” 菜单项配置信息（图标、显示文本、点击响应事件）
	 */
	var configWidget = function(self){
		return {
			"icon":"editMenuIconConfig",
			"text": msg['desgin.msg.configwidget'],
			fn:function(value){
				var xobj = this;
				require.async(['./Widget'],function(Widget){
					showDialog(xobj.text,'/sys/portal/designer/jsp/addwidget.jsp?scene='+self.getDesigner().scene,function(value){
						if(!value){
							return;
						}
						//debugger;
						self.element.children("script[type='text/config']").remove();
						self.element.prepend("<script type='text/config'>"+JSON.stringify(value)+"</script>");
						self.setting = value;
						if(value.panelType=="h" || value.panelType=="panel" || value.panelType=="none"){
							if($.trim(value.height) != ""){
								self.element.css("height",value.height);
							}else{
								self.element.css("height","");
							}
						}else if(value.panelType=="v"){
							self.element.css("height","");
						}
						self.preview.render();
					},750,510).dialogParameter = self.setting;
				});
			}
		};
	};
	var configNavWidget = function(self){
		return {
			"icon":"editMenuIconConfig",
			"text": '添加导航' ,
			"fn" : function(value){
				var xobj = this;
				require.async(['./NavWidget'],function(Widget){
					showDialog(xobj.text,'/sys/portal/varkind/selectAppNav.jsp',function(value){
						if(!value){ return; }
						var ___cfg = {
							panel : 'panel',
							panelType : 'none',
							layoutId : 'sys.ui.nonepanel.default',
							layoutOpt : {},
							heightExt : 'auto',
							portlet : [{
								title : '',
								format : 'sys.ui.sysnav',
								sourceId : 'sys.portal.sysnav.source',
								sourceOpt : {
									fdId : { fdId : value.fdId,  fdName : value.fdName }
								},
								renderId : 'sys.ui.sysnav.default'
							}]
						};
						var temp = $("<div portal-key='"+self.uuid()+"' portal-type='./NavWidget'><script type='text/config'>"+JSON.stringify(___cfg)+"</script></div>");
						var w = new Widget({"body":self.body ,"element":temp});
						w.setting = ___cfg;
						w.setParent(self);
						self.addChild(w);
						self.element.append(temp);
						w.startup();
					},750,500);
				});
			}
		};
	}

	var toJSON = function(str){
		try{
			return eval("("+str+")");
			//return JSON.parse(str);
			//return (new Function("return (" + str + ");"))();
		}catch(e){
			alert(str);
			alert(e);
		}
	};
	module.exports.showDialog 	= showDialog;
	module.exports.addBeforeVBox = addBeforeVBox;
	module.exports.addAfterVBox = addAfterVBox;
	module.exports.addVBox 		= addVBox;
	module.exports.configVBox 	= configVBox;
	module.exports.addWidget 	= addWidget;
	module.exports.configWidget = configWidget;
	module.exports.configNavWidget = configNavWidget;
	module.exports.toJSON 		= toJSON;
});