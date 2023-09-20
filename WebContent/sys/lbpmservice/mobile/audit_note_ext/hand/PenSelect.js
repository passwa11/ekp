define([ 'dojo/_base/declare', 'dijit/_WidgetBase', 'dojo/dom-construct','dojo/dom-class','dojo/topic','dojo/query' ],
		function(declare, WidgetBase, domConstruct, domClass, topic, query){
	
	return declare("sys.lbpmservice.mobile.audit_note_ext._PenBaseSelect", [WidgetBase], {
		
		label : '',
		
		name : '',
		
		store : [],
		
		value : null,
		
		postMixInProperties : function(){
			this.inherited(arguments);
		},
		
		buildRendering : function(){
			var self = this
			this.inherited(arguments);
			domClass.add(this.domNode,'muiPenSelect');
			this.valueNode = domConstruct.create('input',{type : 'hidden',value : this.value, name : this.name},this.domNode);
			domConstruct.create('div',{className : 'muiPenSelectLabel',innerHTML : this.label},this.domNode);
			var penSelectCircleContainer = domConstruct.create('div',{className:'muiPenSelectCircleContainer'},this.domNode)
			for(var i = 0; i < this.store.length; i++){
				var className = 'muiPenSelectCircle ';
				if(this.value === this.store[i].value){
					className += 'selected';
				}
				var touchNode = domConstruct.create('span', { className : className }, penSelectCircleContainer);
				// 外环
				var outerNode = domConstruct.create('div',{ className : 'muiPenSelectCircleOuter' },touchNode);
				// 内环
				var innerNode = domConstruct.create('div',{ className : 'muiPenSelectCircleInner' },outerNode);
				// 选中勾
				domConstruct.create('i',{ className : 'mui mui-pitchon' },innerNode);
				// 绑定修改事件
				this.connect(touchNode,'click',(function(index){
					return function(evt){
						self.valueNode.value = self.store[index].value;
						self.selectNode(index);
						topic.publish('sys/lbpmservice/select/pen',{ name : this.name })
					}
				})(i))
			}
		},
		
		selectNode : function(index){
			var muiPenSelectCircles = query('.muiPenSelectCircle',this.domNode);
			for(var i = 0; i < muiPenSelectCircles.length; i++){
				domClass.remove(muiPenSelectCircles[i],'selected');
			}
			domClass.add(muiPenSelectCircles[index],'selected');
		}
		
	});
	
})

