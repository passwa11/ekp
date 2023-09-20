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
    	"sys/lbpmservice/mobile/common/workflow",
    	"mui/i18n/i18n!sys-lbpmservice"
	], function(declare, TabBar, TabBarButton, Dialog, registry, domConstruct, domClass, array, lang, topic, query, workflow, Msg) {
	//多表单切换按钮
	return declare("sys.lbpmservice.mobile.workitem.SubFormSwitchButton", [TabBarButton], {
		
		options:[],
		
		isInit : false,
		
		nowSubFormId : "default",

		tmpl:'<div data-dojo-type="mui/form/RadioGroup" ' + 
			'data-dojo-props="showStatus:\'edit\',name:\'_subform_switch_radio\',mul:\'false\',store:{store},orient:\'vertical\'"></div>',
		
		buildRendering : function() {
			if(!this.label){
				this.label = Msg["mui.subform.switchForm"];
			}
			this.inherited(arguments);
		},
		
		startup : function() {
			if (this._started)
				return;
			this.inherited(arguments);
		},
		onClick : function(evt) {
			evt.preventDefault();
			evt.stopPropagation();
			var subFormInfoObj = null;
			if (window.lbpm) {
				if (lbpm.subFormInfoObj) {
					subFormInfoObj = lbpm.subFormInfoObj;
				}
				if (lbpm.nowSubFormId) {
					this.nowSubFormId = lbpm.nowSubFormId;
				}
			}
			
			if (subFormInfoObj == null) {
				var subFormXMLObj = WorkFlow_LoadXMLData(query("input[name='sysWfBusinessForm.fdSubFormXML']")[0].value);
				if (subFormXMLObj && subFormXMLObj.subforms) {
					subFormInfoObj = $.extend(true, [], subFormXMLObj.subforms);
				}
			}
			
			if(subFormInfoObj && subFormInfoObj.length>0 && !this.isInit){
				var isExit = false;
				for(var i = 0;i<subFormInfoObj.length;i++){
					if(subFormInfoObj[i]["id"]=="default"){
						isExit = true;
					}
					this.isInit = true;
					var option = {};
					option.text = subFormInfoObj[i]["name"];
					if(subFormInfoObj[i]["id"]==this.nowSubFormId){
						option.text += "("+Msg["mui.subform.currForm"]+")";
					}else if (subFormInfoObj[i]["task"] == "1") {
						option.text += "("+Msg["mui.subform.transaction"]+")"
					}
					option.value = subFormInfoObj[i]["id"];
					this.options.push(option);
				}
				if(!isExit){
					if("default"==this.nowSubFormId){
						this.options.push({text:Msg["mui.subform.defaultCurrForm"],value:"default"});
					}else{
						this.options.push({text:Msg["mui.subform.defaultForm"],value:"default"});
					}
				}
			}
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
							}
						});
				}
			},500);
		},
		
		initOptionsEvent:function(){
			var self = this;
			this.defer(function(){
				var redioNodeList = query("label.muiRadioItem",this.dialogDom);
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
					if(srcDom.className!='' && srcDom.className.indexOf('muiRadioItem')>-1){
						fieldObj = srcDom;
					}else{
						fieldObj = query(evt.target).parents(".muiRadioItem")[0];
					}
					redioNodeList.forEach(function(domObj){
						domClass.remove(domObj,"lbpmSwitchOption");
					});
					domClass.add(fieldObj,"lbpmSwitchOption");
				
					var subFormId = query("input",fieldObj).val();
					if(self.nowSubFormId != subFormId){
						var url = window.location.href;
						url = Com_SetUrlParameter(url, "s_xform", subFormId);
						window.location.replace(url);
						//window.location.href = url;
					}
					self.dialog.hide();
				});
			},500);
		}
	});
});