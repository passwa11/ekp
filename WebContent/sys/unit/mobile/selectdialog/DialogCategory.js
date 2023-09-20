define( [ "mui/form/_FormBase","dojo/_base/declare", "dojo/_base/array", "dojo/topic", "dojo/on", "dojo/touch",
          "dojo/dom-construct", "dojo/dom-class", "dojo/query","sys/unit/mobile/selectdialog/_DialogCategoryBase", "dojo/_base/lang","dojo/dom-style"],
		function(TabBarButton,declare, array, topic, on, touch, domConstruct, domClass,
				query, CategoryBase, lang,domStyle) {
			var _field = declare("km.carmng.DialogCategory", [TabBarButton,CategoryBase ], {
				
				subject : '分类选择',
				
				baseClass : "muiFormEleWrap popup  muiCategory",
				
				//id字段名
				idField : null,
				
				//姓名字段名
				nameField : null,
				
				placeholder : null,
				
				icon:'mui mui-org',
				
				opt : true,
				
				//对外事件
				EVENT_VALUE_CHANGE : '/mui/Category/valueChange',
				
				buildRendering : function() {
					this.inherited(arguments);
					this._buildValue();
				},
				
				postCreate : function() {
					this.inherited(arguments);
					this.eventBind();
					if(this.edit && (!this.curIds || this.isMul  )){
						if(this.muiCategoryAddNode){
							domStyle.set(this.muiCategoryAddNode,'display','inline-block');
						}
					}else{
						if(this.muiCategoryAddNode){
							domStyle.set(this.muiCategoryAddNode,'display','none');
						}
					}
					// 表单属性变更控件需要控制地址本的只读编辑状态，故把事件赋给一个属性以达到控制状态的效果  by zhugr 2017-08-26
					this.domNodeOnClickEvent = this.connect(this.domNode,'click', this.domNodeClick);
				},
				
				domNodeClick : function(){
					var evtNode = query(arguments[0].target).closest(".muiAddressOrg");
					if(evtNode.length > 0){
						return;
					}
					this.defer(function(){
						this._selectCate();
					},350);
				},
				
				//加载
				startup : function() {
					this.inherited(arguments);
					this.key = this.idField;
					this.set("value",this.curIds);
				},
				
				_buildValue:function(){
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
								this.nameDom = domConstruct.create("input" ,{type:'hidden',name:this.nameField},this.valueNode);
							}
						}
						if(this.idDom){
							this.idDom.value = this.curIds==null?'':this.curIds;
						}
						if(this.nameDom){
							this.nameDom.value = this.curNames==null?'':this.curNames;
						}
					}
					if(!this.cateFieldShow){
						this.cateFieldShow = domConstruct.create("div" ,{className:'muiCateFiledShow'},this.valueNode);
						this.contentNode=this.cateFieldShow;
					} else if (lang.isString(this.cateFieldShow)) {
						this.cateFieldShow = query(this.cateFieldShow)[0];
						this.externalCateFieldShow = true;
					}
					
					if (this.cateFieldShow && this.edit && !this.cateFieldShow.getAttribute('data-del-listener-' + this.id)) {
						// 用touch.press
						this.orgIconClickHandle = this.connect(this.cateFieldShow, on.selector(".del.fontmuis.muis-epid-close", "click"), this.orgIconClick);
						this.cateFieldShow.setAttribute('data-has-del-listener-' + this.id, 'true');
					}
					this.buildValue(this.cateFieldShow);
					
					topic.publish(this.EVENT_VALUE_CHANGE,this,{curIds:this.curIds,curNames:this.curNames});
				},

				orgIconClick:function(evt) {
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
				},
				
				buildValue:function(domContainer){
					domConstruct.empty(domContainer);
					if(this.curIds!=null && this.curIds!=''){
						var ids = this.curIds.split(this.splitStr);
						var names = this.curNames.split(this.splitStr);
						for ( var i = 0; i < ids.length; i++) {
							this._buildOneOrg(domContainer,ids[i],names[i]);
							if(i < ids.length-1 && !this.edit){
								//domConstruct.create("span",{innerHTML:this.splitStr},domContainer);
							}
						}
					}else{
						if(this.edit && this.placeholder!=null && this.placeholder!='')
							domAttr.set(this.cateFieldShow,"placeholder",this.placeholder);
					}
				},
				
				_buildOneOrg:function(domContainer, id, name){
					var tmpOrgDom = domConstruct.create("div",{className:"muiAddressOrg", "data-id":id},domContainer);
					domConstruct.create('div',{
						className:'name',
						innerHTML: name 
					},tmpOrgDom);
					//编辑状态添加删除按钮
					if(this.edit){
						domConstruct.create('div',{ className : 'del fontmuis muis-epid-close' },tmpOrgDom);
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
						//回调
						this.afterSelect({curIds:this.curIds,curNames:this.curNames});
					}
					domConstruct.destroy(orgDom);
				},
				
				buildOptIcon:function(optContainer){
					this.inherited(arguments);
					this.muiCategoryAddNode = optContainer;
				},
				
				returnDialog:function(srcObj , evt){
					if(srcObj.key == this.idField){
						this.curIds = evt.curIds;
						this.curNames = evt.curNames;
						this.closeDialog(srcObj);
						if(this.afterSelect){
							this.afterSelect(evt);
						}
						this.set("value",this.curIds);
						this._setCurNamesAttr(this.curNames);
						this.buildValue(this.cateFieldShow);
						topic.publish(this.EVENT_VALUE_CHANGE,this,{curIds:this.curIds,curNames:this.curNames});
					}
				},
				
				_getNameAttr:function(){
					return this.idField;
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
					this.buildValue(this.cateFieldShow);
				}
			});
			return _field;
		});