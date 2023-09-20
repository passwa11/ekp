define(["dojo/_base/declare", 
        "dojo/dom-construct",
        "dijit/_WidgetBase",
        "dojo/on",
        "dojo/touch",
        "dojo/dom-class",
        "dojo/query","dojo/store/Memory","dojo/topic","dojo/request","mui/i18n/i18n!hr-staff:mobile.hr.staff.view"], 
		function(declare, domConstruct, WidgetBase,on,touch,domClass,query,Memory,topic,request,msg) {
		var select = declare("hr.criteria.select", [WidgetBase], {
			//默认值
			docSubject:'',
			//是否多选
			multi:false,
			//列表数据
			data:[],
			//值
			value:'',
			//label
			label:'',
			isclose:false,
			//改变事件
			onChangeSelect:'changeSelect',
			//重置label
			emptyValue:msg['mobile.hr.staff.view.8'],
			//选中样式类名
			selectedClass:'hr-select-selected-active',
			//name值
			fieldName:'',
			//是否有重置项，如：不限
			noReset:false,
			//是否展示选中值
			showLabel:true,
			buildRendering:function(){
				this.inherited(arguments);
				var select = domConstruct.create("div",{className:'hr-select'},this.domNode);
				this.content = domConstruct.create("div",{className:'hr-select-content'},select);
				this.contentValueNode = domConstruct.create("span",{innerHTML:this.docSubject},this.content);
				this.contentIconNode = domConstruct.create("span",{className:'hr-select-content-down'},this.content);
				this.dropdownContent= domConstruct.create("div",{className:'hr-select-dropdown trogger-height-hidden'},this.domNode);
				if(typeof this.data=='string'){
					var dataArr= this.data.split(";").map(function(item){
						return {name:item,value:item};
					});
					this.data= dataArr;
					this.renderDropItem(dataArr,this.noReset);
				}else{
					this.renderDropItem(this.data,this.noReset);
				}
				
				this.bindSelected();
				this.renderValue();
				this.troggleDropwDown();
			},
			startup:function(){
				this.inherited(arguments);
				var _this = this;
	/*			topic.subscribe("hr/criteria/closed",function(data){
					_this.isclose = false;
					_this.containsNode(_this.domNode,data.target);
					if(!_this.isclose){
						_this.closeIcon();
					}
				});*/
				topic.subscribe("hr/criteria/closed",function(id){
					if(id!=_this.id){
						_this.closeIcon();
					}
				});
			},
			containsNode:function(root,node,res){
				if(root==node){
					this.isclose = true;
				}
				for(var i = 0;i<root.children.length;i++){
					var res = this.containsNode(root.children[i],node);
				}					
			},
			renderValue:function(){
					if(!this.multi){
						console.log(this.data)
						this.contentValueNode.innerHTML=this.value?this.label:this.docSubject;
					}
					topic.publish(this.onChangeSelect,this.value);
			},
			troggleDropwDown:function(){
				on(this.content,touch.press,this.changeIcon.bind(this))
			},
			changeIcon:function(){
				if(domClass.contains(this.dropdownContent,'trogger-height-hidden')){
					domClass.remove(this.dropdownContent,'trogger-height-hidden');
					domClass.remove(this.contentIconNode,'hr-select-content-down');
					domClass.add(this.contentIconNode,'hr-select-content-up');
					topic.publish("hr/criteria/closed",this.id);
					topic.publish('criteria/mask',true);
				}else{
					topic.publish('criteria/mask',false);
					this.closeIcon();
				}
			},
			getValue:function(){
				topic.publish("criteria/select/changeValue",{id:this.id,name:this.fieldName,value:this.value});
				return this.value;
			},
			//选择事件
			bindSelected:function(){
				var _this = this;
				on(this.dropdownContent,touch.press,function(e){
					var selectNode = e.target;
					var isSelected = domClass.contains(selectNode,_this.selectedClass);
					var index =query(".hr-select-dropdown-item",_this.domNode).indexOf(e.srcElement) ;
					index= _this.noReset?index:index-1;
					var value =_this.data[index]&&_this.data[index].value;
					var label = _this.data[index]&&_this.data[index].name;
					if(isSelected){
						//取消选择
						domClass.remove(selectNode,_this.selectedClass)
						//_this.value.splice(index,1);
						var store = new Memory({data: _this.value});
						if(_this.multi){
							 store.remove(index);
							_this.value = store.data;
						}else{
							_this.value="";
							_this.label = "";
							//关闭面板
							_this.isclose = true;
							_this.closeIcon();
							topic.publish('criteria/mask',false);
						}
						
					}else{
							if(!_this.multi){
								query(".hr-select-dropdown-item",_this.domNode).map(function(item){
									domClass.remove(item,_this.selectedClass);
								})
								if(value!=_this.emptyValue){
									_this.value=value;
									_this.label = label;
								}
								_this.isclose = true;
								_this.closeIcon();
								topic.publish('criteria/mask',false);
							}else{
								if(value!=_this.emptyValue){
									if(Array.isArray(_this.value)){
										var store = new Memory({data: _this.value});
										store.put({id:index,value:value});
										_this.value=store.data;
		
									}else{
										_this.value=[];
										_this.value.push({id:index,value:value});
		
									}
								}
							}
						
						domClass.add(selectNode,_this.selectedClass)
					}
					_this.renderValue();
					_this.getValue();
					/*_this.closeIcon();*/
				})
				
			},
			//关闭
			closeIcon:function(){
				domClass.add(this.dropdownContent,'trogger-height-hidden');
				domClass.remove(this.contentIconNode,'hr-select-content-up');
				domClass.add(this.contentIconNode,'hr-select-content-down');
				domClass.remove(this.dropdownContent,'more-button-dropdown');
				this.contentValueNode.style.color="#50617A";
			},
			//
			renderDropItem:function(data,reset){
				var _this = this;
				_this.dropdownContent.innerHTML='';
				if(data.length>0){
					data.map(function(item,index){
						 domConstruct.place(_this.createItem(item.name),_this.dropdownContent,'last');
					})
				}
				if(!reset){
					domConstruct.place(_this.createItem(_this.emptyValue,true),_this.dropdownContent,'first')
				}
			},
			createItem:function(item,active){
				var itemNode = domConstruct.create("div",{className:"hr-select-dropdown-item"});
				var itemContentNode = domConstruct.create("div",{className:'hr-select-item',innerHTML:item},itemNode);
				var itemSelected = domConstruct.create("div",{className:'hr-select-selected'},itemNode)
				if(active){
					itemNode.className+=" "+this.selectedClass;
				}
				return itemNode;
			},
			_getValue:function(){
				return this.value;
			}
		});
		return select;
});