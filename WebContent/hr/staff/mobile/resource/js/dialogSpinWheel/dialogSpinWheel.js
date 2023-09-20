define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
	"dojo/dom-construct",
	'dojo/query',
	'dojo/topic',
	'mui/dialog/Dialog',
	"dojo/on",
	"dojo/html",
	"dijit/registry",
	], function(declare,_WidgetBase,domConstruct,query,topic,Dialog,on,html,registry) {
	return declare("hr.staff.mobile.resource.js.dialogSpinWheel", [_WidgetBase], {
		data:[],
		value:'',
		fieldName:'',
		baseClass:'mui-spin-wheel',
		valueIndex:-1,
		buildRendering:function(){
			this.inherited(arguments);
			var initValue = this.value;
			var initLabel = this.value;
			var self = this;
			if(Array.isArray(this.data)){
				this.data.map(function(item,index){
					if(self.value==item['value']){
						initLabel = item['name']
					}
					
				})
			}
			this.valueNode = domConstruct.create("input",{name:this.fieldName,type:"hidden",value:initValue},this.domNode);
			this.labelNode = domConstruct.create("div",{className:'spinWheelNode',innerHTML:initLabel},this.domNode);
			this.iconNode = domConstruct.create("i",{className:'spin-wheel-icon'},this.domNode);
			
		},
		startup:function(){
			this.inherited(arguments);
			var _this = this;
			on(this.domNode,"click",function(){
				_this.showDialog();
			})
			var cssHref= dojoConfig.baseUrl+"hr/staff/mobile/resource/js/dialogSpinWheel/css/hrmobiledialogspinwheel.css";
			if(!window.hasSpinWheelCss){
				domConstruct.create("link",{rel:"stylesheet",href:cssHref},document.head,'last')
				window.hasSpinWheelCss = true;
			}
		
		},
		SpinWheelSlot:function(itemString,length,valueIndex){
			var scrollNode = domConstruct.create("div",{className:'hr-dialog-spinwheel-box'});
			var urlPrefix = dojoConfig.baseUrl+"hr/staff/mobile/resource/js/";
			var htmlString=`
				<div class="hr-spin-wheel" data-dojo-type="${urlPrefix}scroll/IscrollView.js" 
					data-dojo-mixins="${urlPrefix}dialogSpinWheel/IscrollViewMixin.js"
					data-dojo-props="size:${length},initIndex:${valueIndex}"
					>
					
					<div class="hr-spin-wheel-item-all">
						${itemString}
					</div>
				</div>
			`
			html.set(scrollNode,htmlString);
			return scrollNode
		},
		renderItemString:function(data){
			var domString = '';
			for(var i = 0;i<data.length;i++){
				domString+=`<div class="hr-spin-wheel-item">${typeof data[i]=='string'?data[i]:data[i]['name']}</div>`
			}
			return domString;
		},

		showDialog:function(){
			var data = [];
			var _this = this;
			try{
				if(Array.isArray(_this.data)){
					//json对象[{name:'',value}]
					data = _this.data;
					if(_this.value){
						data.map(function(item,index){
							if(item['value']==_this.value){
								_this.valueIndex = index;
							}
						})
					}
				}else{
					//字符串"aaa;bbb;ccc"
					data = _this.data.split(";");
					if(_this.value){
						_this.valueIndex = data.indexOf(_this.value);
					}
				}
			}catch(e){
				console.log(e)
			}
			var slot = this.SpinWheelSlot(this.renderItemString(data),data.length,this.valueIndex);
			var dialogContainerNode = domConstruct.create("div",{style:'height:100%'});
			domConstruct.place(slot,dialogContainerNode);
			var _this = this;
			var dialogObj = Dialog.element({
				canClose : false,
				element :dialogContainerNode,
				scrollable:false,
				buttons : [{
					title:'取消',
					fn:function(){
						dialogObj.hide();
					}
				},{
					title:'确定',
					fn:function(){
						var index = registry.byNode(slot.children[0]).curentItemIndex;
						_this.valueNode.value = data[index]['value']?data[index]['value']:data[index];
						_this.labelNode.innerHTML = data[index]['name']?data[index]['name']:data[index];
						_this.valueIndex = index;
						dialogObj.hide();
					}
				}],
				position:'bottom',
				'parseable' :true,
				showClass : 'muiFormSelect',			
			});
		}
	});
});