define([
        "dojo/_base/declare",
        "mui/tabbar/TabBar",
        "mui/tabbar/TabBarButton",
        "mui/dialog/Dialog",
        "dijit/registry",
        "dojo/dom-construct",
        "dojo/dom-class",
    	"dojo/_base/array",
    	"dojo/_base/lang",
    	"dojo/topic",
    	"dojo/query",
    	"mui/i18n/i18n!sys-lbpmservice",
    	"dojo/NodeList-traverse"
	], function(declare, TabBar, TabBarButton, Dialog, registry, domConstruct, domClass, array, lang, topic, query, Msg) {
	//事务或操作切换按钮
	return declare("sys.lbpmservice.mobile.workitem.OperationSwitchButton", [TabBarButton], {
		
		methodSwitch:true,//操作切换
		
		options:[],
		
		tmpl:'<div data-dojo-type="mui/form/RadioGroup" ' + 
			'data-dojo-props="showStatus:\'edit\',name:\'_lbpm_switch_radio\',renderType:\'table\',mul:\'false\',store:{store},orient:\'vertical\'"></div>',
		
		buildRendering : function() {
			if(this.methodSwitch){
				if(!this.label){
					this.label = Msg["mui.workitem.operation.other"];
				}
			}else{
				if(!this.icon1){
					this.icon1 = "mui mui-attr";
				}
				if(!this.label){
					this.label = Msg["mui.workitem.operation.transaction"];
				}
			}
			if(this.options.length>0){
				this.label = this.label + (this.methodSwitch? "" : ("(" + this.options.length + ")"));
			}
			this.inherited(arguments);
			if(!this.methodSwitch)
				domClass.add(this.domNode,'lbpmSwitchButton muiSplitterButton');
		},
		
		startup : function() {
			if (this._started)
				return;
			this.inherited(arguments);
		},
		onClick : function(evt) {
			if(this.dialog){
				return false;
			}
			evt.preventDefault();
			evt.stopPropagation();
			
			//避免ios kk 双击
			var nowTime = new Date().getTime();
	        var clickTime = this.cbtime;
	        if (clickTime != "undefined" && nowTime - clickTime < 500) {
	           return false;
	        }
	        this.cbtime = nowTime;
			
			if(this.options.length>0){
				this.dialogDom = domConstruct.toDom(lang.replace(this.tmpl,
					{store:JSON.stringify(this.options).replace(/\"/g,"\'")}));
			}
			this.defer(function(){
				if(this.dialogDom){
						var self = this;
						this.dialog = Dialog.element({
							canClose : false,
							showClass : 'muiDialogElementShow muiFormSelect',
							element: this.dialogDom,
							position:'bottom',
							'scrollable' : false,
							'parseable' : true,
							onDrawed:function(){
								self.initOptionsEvent();
							},
							callback:function(){
								self.dialog=null;
							}
						});
				}
			},500);
		},
		
		initOptionsEvent:function(){
			var self = this;
			var redioNodeList = query(".muiRadioTableItem",this.dialogDom);
			if(!this.methodSwitch){
				var curTaskId = null;
				var curDom = null;
				array.forEach(this.options,function(optCfg){
					if(optCfg.checked){
						curTaskId = optCfg.value;	
					}
				});
				redioNodeList.forEach(function(domObj){
					if(query("input",domObj).val()==curTaskId){
						curDom = domObj;
					}
				});
				domClass.add(curDom,"lbpmSwitchOption");
			}
			self._curTime = 0;
			redioNodeList.on("click",function(evt){
				evt.preventDefault();
				evt.stopPropagation();
				
				var curTime = new Date();
				if(curTime - self._curTime<500){
					return;
				}
				self._curTime = curTime;
				var srcDom = evt.target;
				var fieldObj;
				if(srcDom.className!='' && srcDom.className.indexOf('muiRadioTableItem')>-1){
					fieldObj = srcDom;
				}else{
					fieldObj = query(evt.target).parents(".muiRadioTableItem")[0];
				}
				
				topic.publish("/lbpm/operation/switch",self,{
					methodSwitch: self.methodSwitch,
					value: query("input",fieldObj).val(),
					label: registry.byNode(query("input",fieldObj)[0]).text
				});
				self.dialog.hide();
				self.dialog=null;
			});
		},
	});
});