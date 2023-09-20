define([ "dojo/_base/declare", "dojo/_base/array", "dojo/ready", "dojo/dom-style", "dijit/_WidgetBase", 
         "sys/xform/mobile/controls/xformUtil", "dijit/registry", "dojo/request", "mui/util", "mui/dialog/Tip",
         "sys/xform/mobile/controls/EventDataDialog",  "dojo/query","sys/xform/mobile/controls/RelationEventBase","dojo/on","dojo/touch",
         "dojo/dom-class"], 
         function(declare, array, ready, domStyle, WidgetBase, xUtil, 
        		 registry, request, util, Tip, EventDataDialog, query, relationEventBase,on,touch,domClass) {
	var claz = declare("sys.xform.mobile.controls.RelationEvent", [WidgetBase, relationEventBase], {
		
		//绑定事件对象
		bindDom:null,
		
		//绑定事件
		bindEvent:null,
		
		
		buildRendering : function() {
			this.inherited(arguments);
			domStyle.set(this.domNode,"display","none");
			if(this.bindDom && this.bindDom.indexOf(".")>-1){
				this._bindDom = this.bindDom;
				this.bindDom = this._bindDom.substr(this._bindDom.lastIndexOf(".")+1);
				this._inDetailTable = true;
			}
			if(this.name.indexOf(".")>-1){
				this.identy = xUtil.parseXformName(this);
			}
		},
		
		postCreate: function() {
			this.inherited(arguments);
			if (this.status === "edit" || 'click' != this.bindEvent){
				if("change" == this.bindEvent){//监听表单事件变更
					var self = this;
					//#155371 数据填充控件，高级地址本如果设置了初始值，传出参数在移动端无法自动带出，手动选择的传入值正常，pc端正常
					//原因新建流程时因为延时加载，目前如果是明细表添加延时
					if(this._inDetailTable){
						this.defer(function() {
							self.subscribe("/mui/form/valueChanged","_execChange");
						}, 500);
					}else{
						self.subscribe("/mui/form/valueChanged","_execChange");
					}
				}else if('click' == this.bindEvent){//点击事件处理
					if(this._inDetailTable!=true){	//绑定事件的控件在明细表内
						this._bindClick();
					}else{
						this.subscribe("/mui/form/valueChanged","addRowBind");
						//获取编辑页面,控件所在明细表的行
						var self = this;
						setTimeout(function(){
							if (self.identy && self.identy.indexOf(".") > -1) {
								var nodes = query(self.domNode).parents("tr[kmss_iscontentrow='1']");
								if (nodes.length>0) {
									var areaDom = nodes[0];
									self._bindClick(areaDom);
								}
							} else {
								self._bindClick();
							}
						},0);
					}
				}else if('relation_event_setvalue' == this.bindEvent){//自定义事件
					var self = this; 
					$(document).bind('relation_event_setvalue',function(event, param1){
						self._execSetValue(param1);
					});
				}
				this.subscribe("/sys/xform/event/selected","_fillDataInfo");
				this.subscribe("/sys/xform/event/nextpage","_getNextDataInfo");
				this.subscribe("/sys/xform/event/cancel","_clearDataInfo");
			}
		},
		
		startup:function(){
			this.inherited(arguments);
		},
		
		_execChange:function(srcObj, arguContext){
			if(srcObj){
				var evtObjName =  xUtil.parseXformName(srcObj);
				if(evtObjName!=null && evtObjName!='' && srcObj.showStatus != "view"){
					var bindDom = this.bindDom;
					if(bindDom){
						var domArray = bindDom.split(";");
						for(var i = 0;i < domArray.length;i++){
							var id = domArray[i];
							if(id != null && id != ""){
								// 如果srcObj是地址本，evtObjName为不带id和name的控件id
								// 处理地址本带id和name的情况
								if(/-fd(\w+)/g.test(id)){
									id = id.match(/(\S+)-/g)[0].replace("-","");
								}
								id = id.replace(/_text$/g,"");
								if(evtObjName.indexOf(id) > -1){
									this.execEvent(srcObj.get('name'));
									break;
								}
							}
						}
					}
				}
			}
		},
		
		addRowBind:function(srcObj, arguContext){	//新增行时增加事件绑定
			if(srcObj==null){
				if(arguContext && arguContext.row){
					if('click' == this.bindEvent)
						this._bindClick(arguContext.row);
				}
			}
		},
		
		_bindClick:function(areaDom){
			var self = this;
			if(this._inDetailTable==true){
				this._inDetailTableControl(areaDom,self);
			}else{
				if (self.identy && self.identy.indexOf(".") <= -1) {
					this.subscribe('parser/done', function () {
						var wdgs = xUtil.getXformWidgetsBlur(areaDom, self.bindDom);
						if (wdgs.length > 0) {
							array.forEach(wdgs, function (wgt) {
								self.connect(wgt.domNode, "click", function () {
									self._execClick(wgt);
								});
							});
						}
					});
				}else{
					this._inDetailTableControl(areaDom,self);
				}
			}
		},

		_inDetailTableControl:function(areaDom,self){  //控件在明细表中
			var wdgs = xUtil.getXformWidgetsBlur(areaDom, self.bindDom);
			if(wdgs.length>0){
				array.forEach(wdgs,function(wgt){
					var formElement = wgt.inputNode || wgt.domNode;
					self.connect(formElement,"click",function(evt){
						evt.stopPropagation();
						formElement.blur && formElement.blur();
						self._execClick(wgt);
					});
				});

			}
		},



		_execSetValue:function(param){//自定义事件
			if(this.name.indexOf(param)>-1)
				return;
			if(this.bindDom.indexOf(param)>-1 ){
				this.execEvent(this.bindDom);
			}
		}
		
	});
	return claz;
});
