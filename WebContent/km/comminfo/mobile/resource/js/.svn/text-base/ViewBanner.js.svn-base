define(
		[ "dojo/_base/declare","dojo/dom-construct", "mui/view/ViewBanner","mui/util"],
		function(declare,domConstruct,ViewBanner,util) {
			return declare("km.comminfo.mobile.resource.js.ViewBanner",[ ViewBanner ], {
				//override
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
						domConstruct.create("span",{className:"title",innerHTML:"<span class='muiEssence muiProcessStatusBorder'>精</span>" +util.decodeHTML(this.docSubject)},this.rightInfo);
					}else{
						domConstruct.create("span",{className:"title",innerHTML:util.decodeHTML(this.docSubject)},this.rightInfo);
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
				}		
			});
			
		});