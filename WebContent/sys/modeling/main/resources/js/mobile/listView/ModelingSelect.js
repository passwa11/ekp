define("sys/modeling/main/resources/js/mobile/listView/ModelingSelect",["dojo/_base/declare",
        "dojo/dom-construct",
        "dijit/_WidgetBase",
        "dojo/on",
        "dojo/touch",
        "dojo/dom-class",
        "dojo/query","dojo/store/Memory","dojo/topic","dojo/request",'dojo/dom-attr',"dojo/dom", "dojo/_base/window","dojo/dom-style"],
		function(declare, domConstruct, WidgetBase,on,touch,domClass,query,Memory,topic,request,domAttr,dom,win,domStyle) {
		var select = declare("sys.modeling.main.resources.js.mobile.listView.ModelingSelect", [WidgetBase], {
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
			emptyValue:"项目分类",
			//选中样式类名
			selectedClass:'board-select-selected-active',
			//name值
			fieldName:'',
			//是否有重置项，如：不限
			noReset:false,
			//是否展示选中值
			showLabel:true,
			buildRendering:function(){
				this.inherited(arguments);
				if(this.data.length == 0){
					return;
				}
				var select = domConstruct.create("div",{className:'board-select'},this.domNode);
				this.content = domConstruct.create("div",{className:'board-select-content'},select);
				this.contentValueNode = domConstruct.create("span",{innerHTML:this.docSubject},this.content);
				this.contentIconNode = domConstruct.create("span",{className:'board-select-content-down'},select);
				var viewContent = dom.byId("content");
				this.dropdownContent= domConstruct.create("div",{className:'board-select-dropdown trigger-height-hidden'},viewContent);
				if(typeof this.data=='string'){
					var dataArr= this.data.split(";").map(function(item){
						return {text:item,value:item};
					});
					this.data= dataArr;
					this.renderDropItem(dataArr,this.noReset);
				}else{
					this.renderDropItem(this.data,this.noReset);
				}
				
				this.bindSelected();
				this.renderValue();
				this.triggerDropwDown();
				var _this = this;
				on(win.doc,"click",function() {
					_this.isclose = true;
					_this.closeIcon();
				})
			},
			startup:function(){
				this.inherited(arguments);
				var _this = this;
				topic.subscribe("board/group/closed",function(id){
					if(id!=_this.id){
						_this.isclose = true;
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
				this.contentValueNode.innerHTML=this.value?this.label:this.docSubject;
				//topic.publish(this.onChangeSelect,this.value);
			},
			triggerDropwDown:function(){
				on(this.content,"click",this.changeIcon.bind(this))
			},
			changeIcon:function(e){
				e.stopPropagation();
				var offsetSelect = this.offset(e.target.parentNode.offsetParent);
				var topSelect = offsetSelect.top + e.target.parentNode.offsetParent.offsetHeight;
				if(domClass.contains(this.dropdownContent,'trigger-height-hidden')){
					domClass.remove(this.dropdownContent,'trigger-height-hidden');
					domClass.remove(this.contentIconNode,'board-select-content-down');
					domClass.add(this.contentIconNode,'board-select-content-up');
					domStyle.set(this.dropdownContent,"top",topSelect+"px");
					topic.publish("board/group/closed",this.id);
				}else{
					this.closeIcon();
				}
			},
			getValue:function(){
				topic.publish("/modeling/board/group/select",{id:this.id,text:this.fieldName,value:this.value});
				return this.value;
			},
			//选择事件
			bindSelected:function(){
				var _this = this;
				on(this.dropdownContent,"click",function(e){
					e.stopPropagation();
					var selectNode = e.target;
					var value =domAttr.get(selectNode,"data-value");
					var label = domAttr.get(selectNode,"data-text");
					if(value){
						query(".board-select-dropdown-item").map(function(item){
							domClass.remove(item,_this.selectedClass);
						})
						if(value!=_this.emptyValue){
							_this.value=value;
							_this.label = label;
						}
						if(!selectNode.classList.contains("board-select-dropdown-item")){
							domClass.add(selectNode.parentNode,_this.selectedClass);
						}else{
							domClass.add(selectNode,_this.selectedClass);
						}
						_this.renderValue();
						_this.getValue();
					}
					_this.isclose = true;
					_this.closeIcon();
				})
				
			},
			//关闭
			closeIcon:function(){
				domClass.add(this.dropdownContent,'trigger-height-hidden');
				domClass.remove(this.contentIconNode,'board-select-content-up');
				domClass.add(this.contentIconNode,'board-select-content-down');
				domClass.remove(this.dropdownContent,'more-button-dropdown');
				this.contentValueNode.style.color="#50617A";
			},
			//
			renderDropItem:function(data,reset){
				var _this = this;
				_this.dropdownContent.innerHTML='';
				if(data.length>0){
					data.map(function(item,index){
						var active = false;
						if(_this.value == item.value){
							_this.label = item.text;
							active = true;
						}
						 domConstruct.place(_this.createItem(item.text,item.value,active),_this.dropdownContent,'last');
					})
					var selectMark = domConstruct.create("div",{className:'board-select-mark'});
					domConstruct.place(selectMark,_this.dropdownContent,'last');
				}
				// if(!reset){
				// 	domConstruct.place(_this.createItem(_this.emptyValue,_this.emptyValue,true),_this.dropdownContent,'first')
				// }
			},
			createItem:function(text,value,active){
				var itemNode = domConstruct.create("div",{className:"board-select-dropdown-item"});
				var itemSelected = domConstruct.create("div",{className:'board-select-selected'},itemNode);
				var itemSelectedDiv = domConstruct.create("div",{className:''},itemSelected);
				var itemContentNode = domConstruct.create("div",{className:'board-select-item',innerHTML:text},itemNode);
				if(active){
					itemNode.className+=" "+this.selectedClass;
				}
				domAttr.set(itemContentNode,"data-text",text);
				domAttr.set(itemContentNode,"data-value",value);
				domAttr.set(itemNode,"data-text",text);
				domAttr.set(itemNode,"data-value",value);
				return itemNode;
			},
			_getValue:function(){
				return this.value;
			},

			offset : function(curEle){
				var totalLeft = null,totalTop = null,par = curEle.offsetParent;
				//首先加自己本身的左偏移和上偏移
				totalLeft+=curEle.offsetLeft;
				totalTop+=curEle.offsetTop
				//只要没有找到body，就把父级参照物的边框和偏移也进行累加
				while(par){
					if(navigator.userAgent.indexOf("MSIE 8.0")===-1){
						//累加父级参照物的边框
						totalLeft+=par.clientLeft;
						totalTop+=par.clientTop
					}

					//累加父级参照物本身的偏移
					totalLeft+=par.offsetLeft;
					totalTop+=par.offsetTop
					par = par.offsetParent;
				}

				return{
					left:totalLeft,
					top:totalTop
				}
			}
		});
		return select;
});