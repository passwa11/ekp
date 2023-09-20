define([
	"dojo/_base/declare",
	"dojo/_base/array",
	"dojo/topic",
	"dojo/on",
	"dojo/touch",
    "dojo/dom-construct",
	"dojo/dom-class",
	"dojo/query",
	"mui/form/_FormBase",
    "./_DialogCategoryBase",
	"dojo/_base/lang",
	"dojo/dom-style",
	"mui/i18n/i18n!sys-mobile",
	"dojo/dom-attr",
	"./tagUtil",
	"mui/util",
], function(declare, array, topic, on, touch, domConstruct, domClass,
	query, FormBase, CategoryBase, lang,domStyle, Msg, domAttr, tagUtil, util) {
	var _field = declare("mui.tag.DialogCategory", [ FormBase, CategoryBase ], {

		//id字段名
		idField : null,

		//姓名字段名
		nameField : null,


		placeholder : null,

		//自建字段
		isCreateField:true,

		opt : true,

		required: "",

		//对外事件
		EVENT_VALUE_CHANGE : '/mui/Category/valueChange',

		EVENT_ADD_VALUE : '/mui/tag/addValue',

		postMixInProperties:function(){
			this.inherited(arguments);
			if(this.nameField){
				var tmpFileds = query("[name='"+this.nameField+"']");
				if(tmpFileds.length>0) {
					this.isCreateField = false;
					this.opt = false;
				}
			}else if(!this.idDom){
				this.isCreateField = false;
				this.opt = false;
			}
		},

		buildRendering : function() {
			this.inherited(arguments);
			var _this = this;
			if(this.edit){
				this.connect(this.domNode, "click", function () {
					_this.defer(function(){
						_this._selectCate();
					},350);
				});
			}
			this._buildValue();
			this.subscribe(this.EVENT_ADD_VALUE, "addValue");
		},

		postCreate : function() {
			this.inherited(arguments);
			this.eventBind();

			// if(!this.isCreateField){
			// 	domStyle.set(this.domNode,"display","none");
			// }
		},

		//加载
		startup : function() {
			this.inherited(arguments);
			this.key = this.idField;
			this.set("value",this.curIds);
		},

		_buildValue:function(){
			if(this.edit){
				this.setInputValue();
			}
			if(!this.cateFieldShow){
				this.cateFieldShow = domConstruct.create("div" ,{className:'muiCateFiledShow',placeholder:Msg['mui.form.please.input'] + " " + this.subject},this.isCreateField?this.valueNode:this.nameDom.parentNode);
			} else if (lang.isString(this.cateFieldShow)) {
				this.cateFieldShow = query(this.cateFieldShow)[0];
			}

			if (this.cateFieldShow && this.edit && !this.cateFieldShow.getAttribute('data-del-listener-' + this.id)) {
				// 用touch.press
				this.connect(this.cateFieldShow, on.selector(".muiAddressOrg", "click"), function(evt) {
					if (evt.stopPropagation)
						evt.stopPropagation();
					if (evt.cancelBubble)
						evt.cancelBubble = true;
					if (evt.preventDefault)
						evt.preventDefault();
					if (evt.returnValue)
						evt.returnValue = false;
					var nodes = query(evt.target).closest(".muiAddressOrg");
					nodes.forEach(function(orgDom) {
						var id = orgDom.getAttribute("data-id");
						this.defer(function() { // 同时关注时，必须要异步处理
							this._delOneOrg(orgDom, id);
						}, 420);
					}, this);
				});
				// this.cateFieldShow.setAttribute('data-has-del-listener-' + this.id, 'true');
			}
			domConstruct.empty(this.cateFieldShow);
			this.buildValue(this.cateFieldShow);
		},

		setInputValue: function () {
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
					this.nameDom = domConstruct.create("input" ,{type:'hidden',name:this.nameField},this.valueNode);
				}
			}
			if(!this.idField&&!this.idDom&&!this.nameField&&!this.nameDom){
				var ele = this.domNode;
				while(ele!=null&&ele.tagName!="TD"){
					ele = ele.parentNode;
				}
				if(ele!=null){
					var inputField = ele.getElementsByTagName("INPUT");
					for(var i=0;i<inputField.length;i++){
						if(inputField[i].type=="hidden"){
							this.idDom = inputField[i];
						}else if(this.nameDom==null) this.nameDom = inputField[i];
					}
					if(this.idDom==null) this.idDom = inputField[1];
				}
			}
			if(this.idDom){
				if(!this.idField) this.idField = this.idDom.name;
				this.idDom.value = this.curIds==null?'':this.curIds;
			}
			if(this.nameDom){
				if(!this.nameField) this.nameField = this.nameDom.name;
				this.nameDom.value = this.curNames==null?'':this.curNames;
			}
			this.set("value",this.curIds);
		},

		buildValue:function(domContainer){
			if(this[this.primaryKey]!=null && this[this.primaryKey]!=''){
				var ids = this.curIds.split(this.splitStr);
				var names = this.curNames.split(this.splitStr);
				for ( var i = 0; i < names.length; i++) {
					this._buildOneOrg(domContainer, ids[i], names[i]);
					if(i < ids.length-1 && !this.edit){
						domConstruct.create("span",{innerHTML:this.splitStr},domContainer);
					}
				}
			}else{
				if(this.edit && this.placeholder!=null && this.placeholder!='')
					domConstruct.create("div",{className:'muiCatePlaceHold', innerHTML:this.placeholder},domContainer);
			}
		},

		addValue:function(obj, data, domContainer){
			if(data[this.primaryKey] != null && data[this.primaryKey] != ''){
				if(!domContainer) {
					if (lang.isString(this.cateFieldShow)) {
						this.cateFieldShow = query(this.cateFieldShow)[0];
					}
					domContainer = this.cateFieldShow;
				}
				if(!this.isMul) {
					if(this.curIds && this.curIds.split(this.splitStr).length > 0) {
						var curIds = this.curIds.split(this.splitStr);
						for(var i=0; i<curIds.length; i++) {
							var ele = query("[data-id='"+curIds[i]+"']")[0];
							if(ele) {
								this._delOneOrg(ele, curIds[i])
							}
						}
						this.curIds = "";
						this.curNames = "";
					}
					data.curIds = data.curIds.split(this.splitStr)[0];
					data.curNames = data.curNames.split(this.splitStr)[0];
				}
				var ids = data.curIds? data.curIds.split(this.splitStr) : [];
				var names = data.curNames.split(this.splitStr);
				for ( var i = names.length - 1; i >= 0; i--) {
					this.buildOneInpOrg(domContainer, ids[i], names[i], "first");
					if(i < ids.length - 1 && !this.edit){
						domConstruct.create("span",{ innerHTML: this.splitStr },domContainer);
					}
				}
				if(ids) {
					if(this.curIds) {
						this.curIds = ids.join(";") + ";" + this.curIds
					} else {
						this.curIds = ids.join(";");
					}
				}
				if(names) {
					if(this.curNames) {
						this.curNames = names.join(";") + ";" + this.curNames
					} else {
						this.curNames = names.join(";");
					}
				}
				this.setInputValue();
				if(this.validation) {
					this.validation.hideWarnHint(this.domNode);
				}
			}
		},

		buildOneInpOrg:function(domContainer, id, name, position){
			id = id? id : "";
			name = name? name : "";
			position = position? position : "last";
			var tmpOrgDom = domConstruct.create("div",{
				className:"muiAddressOrg",
				"data-id": id
			}, domContainer, position);
			domConstruct.create("div",{
				className:"name",
				innerHTML: util.formatText(name)
			},tmpOrgDom);
			if(this.edit){
				var deleteEle = domConstruct.create("div",{
					className:"del fontmuis muis-epid-close",
				},tmpOrgDom);

				this.connect(deleteEle, "click", function() {
					this._delOneOrg(tmpOrgDom, id);
				})
			}
		},

		_buildOneOrg:function(domContainer, id, name, position){
			id = id? id : tagUtil.GenerateId();
			name = name? name : "";
			position = position? position : "last";
			var tmpOrgDom = domConstruct.create("div",{
				className:"muiAddressOrg",
				"data-id": id
			}, domContainer, position);
			domConstruct.create("div",{
				className:"name",
				innerHTML: util.formatText(name)
			},tmpOrgDom);
			if(this.edit){
				domConstruct.create("div",{
					className:"del fontmuis muis-epid-close",
				},tmpOrgDom);
			}
		},

		_delOneOrg : function(orgDom, id){
			var ids = this.curIds.split(this.splitStr);
			var names = this.curNames.split(this.splitStr);
			var idx = array.indexOf(ids,id);
			if(idx > -1){
				ids.splice(idx,1);
				names.splice(idx,1);
				this.curIds = ids.join(this.splitStr);
				this.curNames = names.join(this.splitStr);
				if(this.idDom){
					this.idDom.value = this.curIds==null?'':this.curIds;
					this.set("value",this.curIds==null?'':this.curIds);
				}
				if(this.nameDom){
					this.nameDom.value = this.curNames==null?'':this.curNames;
				}
				if(this.curIds==null || this.curIds=='')
					this.buildValue(this.cateFieldShow);
				topic.publish(this.EVENT_VALUE_CHANGE,this,{curIds:this.curIds,curNames:this.curNames});
			}
			domConstruct.destroy(orgDom);
		},

		returnDialog:function(srcObj , evt){
			this.inherited(arguments);
			if(srcObj.key == this.idField){
				this.set("value",this.curIds);
				this._buildValue();
				topic.publish(this.EVENT_VALUE_CHANGE,this,{curIds:this.curIds,curNames:this.curNames});
			}
		},

		clearDialog:function(srcObj , evt){
			this.inherited(arguments);
			if(srcObj.key == this.idField){
				this.set("value",this.curIds);
				this._buildValue();
				topic.publish(this.EVENT_VALUE_CHANGE,this,{curIds:this.curIds,curNames:this.curNames});
			}
		}

	});
	return _field;
});