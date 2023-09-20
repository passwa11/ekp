define( [ 
        "dojo/_base/declare",
        "dojo/query", 
        "dojo/dom-class",
		"dojo/dom-construct",
		"dojo/dom-style" ,
		"dojo/on",
		"mui/form/_FormBase",
		"dijit/_WidgetBase",
		"dojo/_base/event",
		"dojo/touch",
		"dojo/_base/array"
		], function(declare, query, domClass, domCtr, domStyle, on, FormBase, WidgetBase, event, touch, array) {
		var claz = declare("km.imeeting.InputMixins", [WidgetBase], {
			placeholder: null,
			nameArray: [],
			nameArr:['docReporterId','docReporterName','docReporterTime','attachmentName','docResponsId','attachmentSubmitTime'],
			data: [{}],
			buildRendering: function(){
				this.inherited(arguments);	
				var ctx = this;
				ctx.UlNode = domCtr.create("ul",{className:'inputList'},this.domNode);
				ctx.addBtns = domCtr.create("div",{className:"addListBtn",innerHTML:ctx.btnName},this.domNode);
				ctx.beforeInit();
				ctx.initNode();
			},
			startup: function() {
				this.inherited(arguments);
			},
			createLiNode: function(data,index){
				var ctx = this;
				var oLi = domCtr.create("li",{},ctx.UlNode);
				oInput = domCtr.create("input",{className:"inputNodeClass",placeholder:ctx.placeholder,name:'kmImeetingAgendaForms['+ctx.nameArray.length+'].docSubject'},oLi);
				//若存在值则赋值
				data && data.docSubject && (oInput.value = data.docSubject);
				for(var i = 0;i<this.nameArr.length;i++){
					var temp = domCtr.create("input",{type:"hidden",name:'kmImeetingAgendaForms['+ctx.nameArray.length+'].'+this.nameArr[i]},oLi);
					data && data[this.nameArr[i]] && (temp.value = data[this.nameArr[i]]);
					temp.className = this.nameArr[i];
				}
				
				//删除li
				ctx.nameArray.push(1);
				oRemoveNode = domCtr.create("span",{className:"mui mui-cancel inputIcon"},oLi);
				oRequiredNode = domCtr.create("span",{className:"RequiredIcon",innerHTML:"*"},oLi);
				on(oRemoveNode,touch.press,function(e){
					ctx.UlNode.removeChild(oLi);
					//ctx.nameArray.splice(0,1);
					ctx.nameArray.shift();
					ctx.NameRange();
				});
			},
			
			//初始化内容
			beforeInit: function() {
				var self = this;
				array.forEach(self.data || [], function(d) {
					self.createLiNode(d);
				});
			},
			
			initNode:function(){
				var ctx = this;
//				if(!ctx.data || ctx.data.length < 1) {
//					ctx.createLiNode();
//				}
				on(ctx.addBtns,touch.press,function(e){
					ctx.createLiNode();
				});
			},
			
			NameRange:function(){
				var liNodes = this.UlNode.getElementsByTagName('li');
				
				for(var i = 0;i<liNodes.length;i++){
					for(var j = 0;j<this.nameArr.length;j++){
						liNodes[i].querySelector("."+this.nameArr[j]).name = "kmImeetingAgendaForms["+i+"]."+this.nameArr[j];
					}
				}
			}
	
	
		});

		return claz;
});