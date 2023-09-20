/*** 明细表桌面端自适应和单行编辑 ***/
define(["dojo/_base/declare", "dijit/_WidgetBase" , "dojo/dom-class" ,"dojo/query",
        "dojo/dom-attr","dojo/dom-construct",
        "dojo/dom-style","dojo/dom","dojo/topic","mui/util","mui/i18n/i18n!sys-mobile"],
		function(declare, WidgetBase, domClass,query,domAttr,domConstruct,domStyle,dom,topic,util,Msg) {
	
		var autoAdaptionMixin = declare("mui.form._AutoAdaptionMixin", [WidgetBase], {		
			//自适应事件
			EVENT_AUTO_ADAPTION : "/mui/form/detailTableAutoAdaption" ,
			
			//单行编辑初始化事件
			EVENT_SINGLE_ROW_VIEW : "mui/standDetailTable/singleRowView",
			
			//xformflag id 前缀
			XFORM_FLAG_PREFIX : "_xform_extendDataFormInfo.value(",
			
			//xformflag id 后缀
			XFORM_FLAG_SUFFIX : ")",
			
			//明细表桌面端自适应样式
			autoAdaptionClass : "muiDetailTableAutoAdaption",
			
			autoAdaptionViewClass : "autoAdaptionView",
			
			muiSingleRowViewClass : "muiInput_View",
			
			SWITCH_ID_PREFIX : "detailsTableAutoAdaption_TABLE_DL_",
			
			DETAIL_TABLE_ID_PREFIX : "_TABLE_DL_",
			
			RELATION_ATT_CHANGE : "/mui/relationRule/attrChange",
			
			SWITCH_ON : "on",
			
			SWITCH_OFF : "off",
			
			startup : function() {
				this.inherited(arguments);
				this.subscribe(this.EVENT_AUTO_ADAPTION, 'switchChange');
				this.subscribe(this.EVENT_SINGLE_ROW_VIEW,'singleRowView');
				this.subscribe(this.RELATION_ATT_CHANGE, 'relationAttrChange');
			},
			
			isNeedDestroy : function() {
				return true;
			},
			
			relationAttrChange : function(){
				if (this.singleRowEditMode) {
					this.buildSingleRowView();
				}
			},
			
			/**
			 * 自适应开关值改变事件，判断控件的跟开关是否在同个明细表里面
			 */
			switchChange : function(switchWgt){
				if (switchWgt && this._isAutoAdaption(switchWgt)){
					var val = switchWgt.value;
					if (val === this.SWITCH_ON) {
						this._autoAdaptionAction();
					}
					if (val === this.SWITCH_OFF) {
						this._reset();
					}
				}
			},
			
			// 判断控件是否在明细表里面，简单的控件可以直接通过name来判断，但是明细表统计行里面的不行
			isInStatisticTr : function(wgt){
				return this.hasParentClass(wgt,"detail_statistic");
			},
			
			// wgt 的父元素是否含有某className
			hasParentClass : function(wgt,className){
				var domNode = wgt.domNode;
				if(domNode){
					var parent = domNode.parentNode;
					while(parent){
						if(domClass.contains(parent,className)){
							return true;
						}
						parent = parent.parentNode;
					}
				}
				return false;
			},
			
			// 来自xformutil，本文件不应该依赖xform的文件
			parseName : function(wgt) {
				var valueField = wgt.get("name");
				if (valueField == null || valueField == "") {
					return null;
				}
				var sIndex = valueField.indexOf(".value(");
				if (sIndex > -1) {
					sIndex = sIndex + 7;
				} else {
					sIndex = 0;
				}
				var eIndex = valueField.lastIndexOf(')');
				if (eIndex > -1
						&& (eIndex + 1) == valueField.length) {
					eIndex = eIndex;
				} else {
					eIndex = valueField.length;
				}
				var tmpName = valueField.substring(sIndex, eIndex);
				tmpName = tmpName.replace(/\.id/gi, "");
				tmpName = tmpName.replace(/\.name/gi, "");
				return tmpName;
			},
			
			singleRowView : function(detailTableId) {
				var name = this.parseName(this);
				var controlDetailTableId;
				if(name && /\.(\d+)\./g.test(name)){
					var index = name.indexOf(".");
					controlDetailTableId = name.substring(0,index);
				}
				var isInStaticTr =  this.isInStatisticTr(this);
				if (isInStaticTr) {
					var tableObj = query(this.domNode).closest("table")[0];
					var tableId = tableObj.getAttribute("id");
					if (tableId && tableId.indexOf("TABLE_DL_") > -1) {
						controlDetailTableId = tableId.replace("TABLE_DL_","");
					}
				}
				//设置了单行编辑的明细表
				if (controlDetailTableId === detailTableId) {
					this.singleRowEditMode  = true;
					this.buildSingleRowView();
					topic.publish('/sys/xform/event/singleView/rebindNode', this);
				}
			},
			
			getDisplayVal : function(){
				//属性变更控件
				var xformFlag =  query(this.domNode).closest("xformflag")[0];
				var display;
				if (xformFlag) {
					display = domStyle.get(xformFlag,"display");
				}
				if (display !== "none") {
					display = domStyle.get(this.domNode,"display");
				}
				return display;
			},
			
			buildSingleRowView : function() {
				var display = this.getDisplayVal();
				//display:none,属性变更控件设置为隐藏
				if (this.showStatus != "hidden" && display !== "none") {
					if (!this.autoAdaptionViewNode) {
						this.autoAdaptionViewNode = this.createMarkNode(this.muiSingleRowViewClass);
					} else {
						domClass.replace(this.autoAdaptionViewNode,this.muiSingleRowViewClass,this.autoAdaptionClass);
					}
					if (this.autoAdaptionViewNode) {
						this._initViewText();
						domStyle.set(this.autoAdaptionViewNode,"display","");
//						domStyle.set(this.domNode,"display","none");
					}
				} else {
					if (this.autoAdaptionViewNode) {
						domStyle.set(this.autoAdaptionViewNode,"display","none");
					}
				}
			},
			
			createMarkNode : function(clazzName) {
				var name = this.parseName(this);
				var detailTableId;
				if(name && /\.(\d+)\./g.test(name)){
					var index = name.indexOf(".");
					detailTableId = name.substring(0,index);
				}
				var detailTableView = dom.byId(this.DETAIL_TABLE_ID_PREFIX + detailTableId);
				var xformflagId = this.XFORM_FLAG_PREFIX + name + this.XFORM_FLAG_SUFFIX;
				var xformflag = query("[id='" + xformflagId + "']",detailTableView)[0];
				var markNode;
				if (xformflag) {
					markNode = query("[mark='autoAdaptionView']",xformflag)[0];
					if (!markNode) {
						markNode = domConstruct.create('div', {
										mark : "autoAdaptionView",
										_name : this.get("name"),
										className : clazzName,
										val : this.get("value") || ""
										},xformflag,"last");
						//属性变更支持附件
						var viewInputDom = query("input[name='" + this.name + "']",xformflag)[0];
            			if (viewInputDom) {
            				var editInputDom = query("input[name='" + this.name + "']",this.domNode)[0];
            				if (editInputDom) {
            					if (viewInputDom.value) {
            						editInputDom.value = viewInputDom.value;
            					}
            				}
							//#154016 对于选择框，需要移除 查看视图中 xformflag标签 下所有的input，编辑视图已经存在这些input框
							if(this.declaredClass && this.declaredClass == "sys.xform.mobile.controls.RelationChoose"){
								var viewInputDoms = query("input",xformflag);
								if(viewInputDoms){
									for(var i=0; i < viewInputDoms.length; i++){
										domConstruct.destroy(viewInputDoms[i]);
									}
								}
							}else{
								domConstruct.destroy(viewInputDom);
							}
            			}
						xformflag.setAttribute("id","");
					}
				}
				return markNode;
			},
			
			_isAutoAdaption : function(switchWgt){
				var switchId = switchWgt.id;
				//自适应开关所在的明细表id
				var switchDetailTableId = switchId.substring(this.SWITCH_ID_PREFIX.length);
				var controlDetailTableId;
				var name = this.parseName(this);
				if(name && /\.(\d+)\./g.test(name)){
					var index = name.indexOf(".");
					controlDetailTableId = name.substring(0,index);
				}
				var isInStaticTr =  this.isInStatisticTr(this);
				if (isInStaticTr) {
					var tableObj = query(this.domNode).closest("table")[0];
					var tableId = tableObj.getAttribute("id");
					if (tableId && tableId.indexOf("TABLE_DL_") > -1) {
						controlDetailTableId = tableId.replace("TABLE_DL_","");
					}
				}
				if (controlDetailTableId === switchDetailTableId) {
					return true;
				}
				return false;
			},
			
			buildAutoAdaptionView : function() {
				//控件非隐藏状态
				//display:none,属性变更控件设置为隐藏
				var display = this.getDisplayVal();
				if (this.showStatus != "hidden" && display !== "none") {
					if (!this.autoAdaptionViewNode) {
						this.autoAdaptionViewNode = this.createMarkNode(this.autoAdaptionViewClass);
					} else {
						domClass.replace(this.autoAdaptionViewNode,this.autoAdaptionViewClass, this.muiSingleRowViewClass);
					}
					if (this.autoAdaptionViewNode) {
						this._initViewText();
						if (!domClass.contains(this.domNode, this.autoAdaptionViewClass)) {
							domClass.add(this.domNode,this.autoAdaptionViewClass);
					    }
						domStyle.set(this.autoAdaptionViewNode,"display","");
					}
				}  else {
					if (this.autoAdaptionViewNode) {
						domStyle.set(this.autoAdaptionViewNode,"display","none");
					}
				}
			},
			
			_initViewText : function() {
				var viewHtml = "";
				if (this.getText){
					viewHtml  = util.decodeHTML(this.getText());
				} else if (this.text) {
					viewHtml = util.decodeHTML(this.text);
				} else {
					var val = this.value;
					if (this.percentFormat) {
						val += "%";
					}
					viewHtml  = util.decodeHTML(val || "");
				}
				this.autoAdaptionViewNode.innerHTML = viewHtml;
			},
			
			_autoAdaptionAction : function() {
				this.buildAutoAdaptionView();
			},
			
			_reset : function() {
				if (domClass.contains(this.domNode, this.autoAdaptionClass)) {
					domClass.remove(this.domNode,this.autoAdaptionClass);
			    }
			},
		
		});
		return autoAdaptionMixin;
});