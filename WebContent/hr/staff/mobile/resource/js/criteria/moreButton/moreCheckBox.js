define(["dojo/_base/declare", 
        "dijit/_WidgetBase",
        "dojo/dom-construct",
        "dijit/_Contained",
        "dijit/_Container",
        "dojo/on",
        "dojo/touch",
        "dojo/dom-class",
        "dojo/query","dojo/topic"], 
		function(declare, WidgetBase,domConstruct,_Contained, _Container,on,touch,domClass,query,topic) {
		return declare("hr.criteria.moreCheckBox", [WidgetBase], {
			isRange:true,
			value:[],
			mult:false,
			index:-1,
			buildRendering:function(){
				this.inherited(arguments);
				if(this.isRange){
					
				}
				var parentDom = this.domNode.parentNode.parentNode;
				this.index = Array.prototype.slice.call(parentDom.children).indexOf(this.domNode.parentNode);
				var _this = this;
				domClass.add(this.domNode,"hr-check-box");
				topic.subscribe("hr/criteria/more/reset",function(data){
					query(".hr-check-box-node").map(function(item){
						domClass.remove(item,"hr-check-box-selected")
					})
					_this.value=[];
				});
			},
			renderCheckBoxGroup:function(data){
				var checkBoxGroupNode = domConstruct.create("div",{className:'hr-check-group'},this.domNode);
				var _this = this;
				Array.isArray(data)&&data.map(function(item,i){
					domConstruct.place(_this.renderCheckBox(item,i),checkBoxGroupNode)
					if(!this.mult){
						on(checkBoxGroupNode,on.selector(".hr-check-box-node-temp-"+i,touch.press),function(e){
							if(domClass.contains(this,"hr-check-box-selected")){
								domClass.remove(this,"hr-check-box-selected");
								topic.publish("hr/criteria/checkbox",{index:-1,value:-1})
							} else { 
								query(".hr-check-box-node").map(function(item2){
									domClass.remove(item2,"hr-check-box-selected")
								}); 
								domClass.add(this,"hr-check-box-selected"); 
								_this.value =item.value ;
								topic.publish("hr/criteria/checkbox",{index:_this.index,value:_this.value})
							} 
						})
					}
				})

			},
			renderCheckBox:function(item,i){ 
				var boxNodeClass ='hr-check-box-node hr-check-box-node-temp-'+i; 
				var checkBoxNode =  domConstruct.create("div",{className:boxNodeClass,innerHTML:item.label});
				var checkSelected = domConstruct.create("div",{className:'hr-check-box-active',style:"display:none;"},checkBoxNode);
				var checkSelectedIcon = domConstruct.create("div",{className:'hr-check-box-active-icon'},checkSelected);
				if(this.mult){
					on(checkBoxNode,touch.press,function(){
						if(checkSelected.style.display=="block"){
							checkSelected.style.display="none";
							checkBoxNode.style.background ="rgba(51,51,51,0.05)";
						}else{
							checkSelected.style.display="block";
							checkBoxNode.style.background="rgba(49,143,237,0.1)";
						}
					})					
				} 
				return checkBoxNode;
			},
			startup:function(){
				this.inherited(arguments);
				this.renderCheckBoxGroup(this.data);
			}
		});

});