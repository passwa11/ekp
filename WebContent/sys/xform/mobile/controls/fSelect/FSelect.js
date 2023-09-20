define([ "dojo/_base/declare", "mui/form/Select","mui/util", "dojo/dom-construct", "dojo/_base/json",
         "sys/xform/mobile/controls/fSelect/FSelectDialog", "mui/dialog/Confirm", "mui/form/validate/Validation",
         "dojo/_base/lang","mui/dialog/Dialog", "dojo/topic" ,"mui/i18n/i18n!sys-xform-base:mui"], 
		function(declare, Select, util, domConstruct, json, FSelectDialog, Confirm,Validation,lang,Dialog,topic,msg) {

			var _field = declare("sys.xform.mobile.controls.fSelect.FSelect", [ Select ], {
				
				type : 'fSelect',
				
				SELECTED_EVENT : "/sys/xform/fSelect/selected",

				//必填校验别名（默认校验的是required）
				requiredValidateAlias: "leastNItem",

				closeDialog : function(srcObj, evt) {
					if (!this.mul) {
						if (evt.name == (this.selectBoxPrefix + this.valueField)) {
							this.set('value', evt.value);
							this._closeDialog();
						}
					}else{
						if(srcObj.params.name== (this.selectBoxPrefix + this.valueField)){
							var type = srcObj.checked ? 'add' : 'remove';
							this[type+'Value'](srcObj.value);
						}
					}
				},
				
				_DoneClick : function() {
					this.set('value', this.value_s.join(';'));
					this._closeDialog();
				},
		
				_closeDialog : function(srcObj) {
					topic.publish(this.SELECT_CALLBACK, this);
					if(this.dialog){
						this.dialog.closeDialog(srcObj);
						this.dialog = null;
					}
				},
		
				_setTextAttr : function(text) {
					this.inputContent.innerHTML = text;
					this.text = text;
				},
		
				hiddenValueSet : function(value) {
					this.editValueSet(value);
				},
		
				readOnlyValueSet : function(value) {
					this.editValueSet(value);
				},
				
				addValue : function(value) {
					this.editValueSet(value);
				},
		
				removeValue : function(value) {
					this.editValueSet(value);
				},
		
				bindEvent : function() {
					//绑定点击事件，打开复选下拉列表框
					this._clickHandle =this.connect(this.domNode, 'click', function(evt){
						this.defer(function(){
							this._onClick(evt);
						},320);
					});
					this.subscribe(this.SELECTED_EVENT,"_fillDataInfo");
					if (this.required){
						this.validation = new Validation();
						var error = msg['mui.fSelect.validateMsg'].replace("{title}",this.subject).replace("{n}",this.leastNItem);
						this.validation.addValidator("leastNItem" , error , lang.hitch(this,'doValidate'));
						this.set("validate","leastNItem");
					}
				},
				
				
				doValidate : function(val,src){
					if (src != null && src.type === "fSelect"){
						var vals = (this.value != "" ? this.value.split(";") : []);
						if (vals.length >= this.leastNItem){
							return true;
						}else{
							var validator = this.validation.getValidator("leastNItem");
							validator.error =  msg['mui.fSelect.validateMsg'].replace("{title}",src.subject).replace("{n}",src.leastNItem);
							return false;
						}
					}
				},
				
				
				_fillDataInfo:function(srcObj,evt){
					if(evt){
						if(this.name==srcObj.key){
							var values = [];
							for(var i = 0;i < evt.rows.length; i++){
								var row = evt.rows[i];
								values.push(row.value);
							}
							this.set('value', values.join(';'));
							this._closeDialog(srcObj);
						}
					}
				},
				
				_readOnlyAction:function(value) {
					this.inherited(arguments);
					if(value){
						if(this._clickHandle){
							this.disconnect(this._clickHandle);
						}
						this._clickHandle = null;
					}else{
						this._clickHandle = this.connect(this.domNode, 'click', function(evt){
							this.defer(function(){
								this._onClick(evt);
							},320);
						});
					}
				},
				
				resizeTop:function(evt) {
					var wdgts =evt.htmlWdgts,y=0;
					var value_s= this.get("value").split(";");
					for(var i=0;i<wdgts.length;i++){
						if(value_s[0]==wdgts[i].value&&wdgts[i].checked){
							if(wdgts[i].checkboxNode.offsetTop<evt.containerNode.clientHeight){
								break;
							}else if(this.contentNode.clientHeight-wdgts[i].checkboxNode.offsetTop<evt.containerNode.clientHeight){
							   y=this.contentNode.clientHeight-evt.containerNode.clientHeight;
							}
							else{
							   y =wdgts[i].checkboxNode.offsetTop-(evt.containerNode.clientHeight-wdgts[i].checkboxNode.clientHeight)/2;  
							}
							evt.contentNode.scrollTop = y;
						}
					}
				},
		
				_onClick : function(evt) {
					if(this.dialog==null){
						this.dialog = new FSelectDialog();
					}
					this.dialog.select({key:this.name,data:this.values,curIds:this.value,
						isMul:true,required:this.required,leastNItem:this.leastNItem,subject:this.subject});
				},
			});
		return _field;
})
