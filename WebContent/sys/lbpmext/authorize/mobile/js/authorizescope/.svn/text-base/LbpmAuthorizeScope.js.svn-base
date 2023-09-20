//关联控件
define([ "dojo/_base/declare", "mui/form/_FormBase","mui/util", "dojo/dom-construct",
         "sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizeDialog", "dojo/query",
         "dojo/parser","dojo/ready","mui/i18n/i18n!sys-lbpmext-authorize"], 
		function(declare, _FormBase, util, domConstruct, LbpmAuthorizeDialog, query, parser, ready, Msg) {
	var claz = declare("sys.lbpmext.authorize.mobile.js.authorizescope.LbpmAuthorizeScope", [ _FormBase ], {

		curIds : null,
		
		curNames : null,
		
		idField : null,
		
		nameField : null,
		
		textareaTmpl:'<div data-dojo-type="mui/form/Textarea" data-dojo-props="name:\'!{name}\',value:\'!{value}\',showStatus:\'readOnly\'" class="oldMui muiFormEleWrap textarea muiFormStatusEdit showTitle"></div>',
		
		buildRendering : function() {
			this.inherited(arguments);
			this.curNames = util.decodeHTML(this.curNames);
			this._buildValue();
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/sys/lbpmext/authorize/mobile/js/authorizescope/setvalue","_setvalue");
		},
		
		_setvalue : function(srcObj , evt){
			if(evt && this.idField==srcObj.key){
				if(this.curIds!=evt.curIds){
					var fdScopeFormAuthorizeCateIdsObj = document.getElementsByName("fdScopeFormAuthorizeCateIds")[0];
					var fdScopeFormAuthorizeCateNamesObj = document.getElementsByName("fdScopeFormAuthorizeCateNames")[0];
					var fdScopeFormModelNamesObj = document.getElementsByName("fdScopeFormModelNames")[0];
					var fdScopeFormModuleNamesObj = document.getElementsByName("fdScopeFormModuleNames")[0];
					var fdScopeFormTemplateIdsObj = document.getElementsByName("fdScopeFormTemplateIds")[0];
					var fdScopeFormTemplateNamesObj = document.getElementsByName("fdScopeFormTemplateNames")[0];
					var fdScopeFormAuthorizeCateShowtextsObj = document.getElementsByName("fdScopeFormAuthorizeCateShowtexts")[0];
					var fdScopeFormAuthorizeCateIds = "";
					var fdScopeFormAuthorizeCateNames = "";
					var fdScopeFormModelNames = "";
					var fdScopeFormModuleNames = "";
					var fdScopeFormTemplateIds = "";
					var fdScopeFormTemplateNames = "";
					var fdScopeFormAuthorizeCateShowtexts = "";
					this.curIds = evt.curIds;
					this.curNames = evt.curNames;
					if(this.idDom){
						this.idDom.value = evt.curIds;
					}
					var curIds = this.curIds.split(";");
					var curNames = this.curNames.split(";");
					for(var i=0;i<curIds.length;i++){
						var showText = this.GetUrlParameter_Unescape(curIds[i], "showText");
						var categoryId = this.GetUrlParameter_Unescape(curIds[i], "categoryId");
						var categoryName = this.GetUrlParameter_Unescape(curIds[i], "categoryName");
						var modelName = this.GetUrlParameter_Unescape(curIds[i], "modelName");
						var moduleName = this.GetUrlParameter_Unescape(curIds[i], "moduleName");
						var templateId = this.GetUrlParameter_Unescape(curIds[i], "templateId");
						var templateName = this.GetUrlParameter_Unescape(curIds[i], "templateName");
						fdScopeFormAuthorizeCateIds += (categoryId == null?" ":categoryId) + ";";
						fdScopeFormAuthorizeCateNames += (categoryName == null?" ":categoryName) + ";";
						fdScopeFormModelNames += (modelName == null?" ":modelName) + ";";
						fdScopeFormModuleNames += (moduleName == null?" ":moduleName) + ";";
						fdScopeFormTemplateIds += (templateId == null?" ":templateId) + ";";
						fdScopeFormTemplateNames += (templateName == null?" ":templateName) + ";";
						fdScopeFormAuthorizeCateShowtexts += (showText == null?" ":showText) + ";";
					}
					fdScopeFormAuthorizeCateIdsObj.value = fdScopeFormAuthorizeCateIds;
					fdScopeFormAuthorizeCateNamesObj.value = fdScopeFormAuthorizeCateNames;
					fdScopeFormModelNamesObj.value = fdScopeFormModelNames;
					fdScopeFormModuleNamesObj.value = fdScopeFormModuleNames;
					fdScopeFormTemplateIdsObj.value = fdScopeFormTemplateIds;
					fdScopeFormTemplateNamesObj.value = fdScopeFormTemplateNames;
					fdScopeFormAuthorizeCateShowtextsObj.value = fdScopeFormAuthorizeCateShowtexts;
				}
			}
		},
		
		GetUrlParameter_Unescape : function(url, param){
			var re = new RegExp();
			re.compile("[\\?&]"+param+"=([^&]*)", "i");
			var arr = re.exec(url);
			if(arr==null) {
				return null;
			} else {
				return unescape(arr[1]);
			}
		},
		
		// 构建值区域
		_buildValue : function() {
			this.inherited(arguments);
			if(this.edit){
				if(this.idField && !this.idDom){
					var tmpFileds = query("[name='"+this.idField+"']");
					if(tmpFileds.length>0){
						this.idDom = tmpFileds[0];
					}else{
						this.idDom = domConstruct.create("input" ,{type:'hidden',name:this.idField},this.valueNode);
					}
				}
				if(this.nameField && !this.nameDom){
					var tmpFileds = query("[name='"+this.nameField+"']");
					if(tmpFileds.length>0){
						this.nameDom = tmpFileds[0];
					}else{
						domConstruct.create("span" ,{
							innerHTML:Msg["mui.authorize.scope"],
							style:{
								'margin-right': '3rem',
							    'color': '#999',
							    'font-size': '1.3rem'
							}},this.valueNode);
						var tmpl = this.textareaTmpl.replace('!{name}',this.nameField).replace('!{value}',this.curNames==null?'':this.curNames);
						this.nameDom = domConstruct.toDom(tmpl);
						domConstruct.place(this.nameDom,this.valueNode,"last");
						parser.parse(this.valueNode);
						ready(function(){
							query("[name='"+self.nameField+"']").attr("placeholder",Msg["mui.authorize.enterScope"])
						});
						var self = this;
						this.connect(this.nameDom,'click',function(evt){
							if(self.dialog==null){
								self.dialog = new LbpmAuthorizeDialog();
							}
							self.dialog.select({key:self.idField,curIds:self.curIds,curNames:self.curNames});
						});
					}
				}
				if(this.idDom){
					this.idDom.value = this.curIds==null?'':this.curIds;
				}
			}
		},
		
		_setValueAttr : function(val) {
			this.inherited(arguments);
			this.curIds = val;
			if(this.idDom){
				this.idDom.value = val;
			}
		},
		
		_setCurIdsAttr : function(val) {
			this.curIds = val;
			this.set("value",val);
		},
		
		_setCurNamesAttr : function(val) {
			this.curNames = val;
			if(this.nameDom){
				this.nameDom.value = val;
			}
			
		}
	});
	return claz;
});