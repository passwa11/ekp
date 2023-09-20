define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase",
        "dojo/dom-construct","mui/util"],function(declare,lang,WidgetBase,domConstruct,util){
	
		return declare("mui.view.ViewBanner",[WidgetBase],{
			docIsIntroduced:'false',
			bgImg:'',
			
			buildRendering:function(){
				this.inherited(arguments);
				//该参数废弃
				if(this.bgImg){
					this.containerNode = domConstruct.toDom("<div style='background:url(" + this.bgImg + ") no-repeat left top;background-size:cover;' class='muiViewBanner'></div>");
					domConstruct.place(this.containerNode, this.domNode);
				}else{
					this.containerNode = domConstruct.create('div', {className : 'muiViewBanner'}, this.domNode);
				}
				this.buildInternalRender();
			},
			buildInternalRender:function(){
				this.leftInfo = domConstruct.create('div', {className : 'leftInfo'}, this.containerNode);
				if(this.icon){
					//用户头像
					var personBox = domConstruct.create("span",{className:"figure"},this.leftInfo);
					domConstruct.create("img", { className: "muiSummaryImg",src:this.icon}, personBox);
				}else{
					//自定义列表图标
					var viewIcon = this.viewIcon? this.viewIcon : "mui-bookLogo";
					var span = domConstruct.toDom("<span class='figure'><i class='mui '" + viewIcon + "></i></span>");
					domConstruct.place(span, this.leftInfo);
				}
				
				this.rightInfo = domConstruct.create('div', {className : 'rightInfo'}, this.containerNode);
				
				if(this.docIsIntroduced=='true'){
					domConstruct.create("span",{className:"title",innerHTML:"<span class='muiEssence muiProcessStatusBorder'>精</span>" +util.formatText(this.docSubject)},this.rightInfo);
				}else{
					domConstruct.create("span",{className:"title",innerHTML:util.formatText(this.docSubject)},this.rightInfo);
				}
				
				
				var ul = domConstruct.create("ul",{},this.rightInfo);
				if(this.docStatus>='30' && this.docPublishTime){
					var li = domConstruct.toDom("<li><i class='mui mui-todo_date'></i>" + this.docPublishTime + "</li>");
					domConstruct.place(li, ul);
				}
				if(this.created){
					var li = domConstruct.toDom("<li><i class='mui mui-todo_date'></i>" + this.created + "</li>");
					domConstruct.place(li, ul);
				}
				if(this.creator){
					var li = domConstruct.toDom("<li><i class='mui mui-todo_person'></i>" + this.creator + "</li>");
					domConstruct.place(li, ul);
				}
				if(this.docStatus>='30' && this.docReadCount){
					var li = domConstruct.toDom("<li><i class='mui mui-eyes'></i>" + this.docReadCount + "</li>");
					domConstruct.place(li, ul);
				}
			},
			startup:function(){
				if(this._started){ return; }
				this.inherited(arguments);
			}
		});
});