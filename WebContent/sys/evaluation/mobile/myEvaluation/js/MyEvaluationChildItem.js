define(		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class", "dojo/dom-style",
				"mui/util", "dojo/html","dojo/dom", "dojo/on",
				"dojo/_base/array", "mui/i18n/i18n!sys.evaluation:mobile","dojox/mobile/_ItemBase",
				"dojo/request", "mui/dialog/Confirm", "./Loading",
				],
		function(declare, domConstruct, domClass, domStyle, util, html, dom, on, array, msg, ItemBase, 
				request, Confirm, Loading) {

	return declare("sys.evaluation.myEvaluation.list.childItem", [ ItemBase, Loading ], {
		
		fdId: "",
		
		//评论内容
		fdEvaluationContent: "", 
		
		//评论时间
		fdEvaluationTime: "",   
		
		fdEvaluationIntervalTime: "",
		
		//是否追加评论
		isAddition: false,
		
		index: "",
		
		isShow: false,
		
		showHr: false,
		
		baseClass: "muiMyEvaluationListChildItem",

		buildRendering : function() {
			this.inherited(arguments);		
			this.biludItem();
			if(!this.isShow){
				domStyle.set(this.domNode,{
					display: "none",
				})
			}
		},
		
		
		biludItem: function(){			
			var containerNode = domConstruct.create("div",{	
			},this.domNode);
			
			if(this.isAddition){
				domConstruct.create("div",{	
					className: "evalAddition",
					innerHTML: msg["mobile.msg.additionEval"]
				},containerNode);
			}
			
			domConstruct.create("div",{	
				className: "evalContent",
				innerHTML: this.fdEvaluationContent
			},containerNode);		
			
			var containerBottomNode = domConstruct.create("div",{	
				className: "evalBottom"
			},containerNode);
			
			domConstruct.create("div",{	
				className: "evalTime",
				innerHTML: msg["mobile.msg.publishedIn"] + this.fdEvaluationTime
			},containerBottomNode);
			
			var deleteImgUrl = "/sys/evaluation/mobile/myEvaluation/img/delete.svg";
			deleteImgUrl = util.formatUrl(deleteImgUrl,true);
			var deleteImgNode = domConstruct.create("div",{
				className: "deleteDiv",
			},containerBottomNode);
			var containerBottomNode = domConstruct.create("img",{
				className: "deleteImg",
				src: deleteImgUrl,
			},deleteImgNode);
			var self = this;
			on(deleteImgNode, "click", function(){
				if(self.isDoingClick){
					return;
				}
				self.isDoingClick = true;
				setTimeout(function(){
					self.isDoingClick = false;
				},50);
				self.onDeleteClick();
			});
			
			if(this.showHr){
				domConstruct.create("div",{	
					className: "hr"				
				},containerNode);
			}		
		},
		
		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		},
		
		show: function(index){
			if(index != undefined){
				this.index = index;
			}
			this.isShow = true;
			domStyle.set(this.domNode,{
				display: "block",
			})
		},
		
		hide: function(index){
			this.isShow = false;
			if(index != undefined){
				this.index = index;
			}
			domStyle.set(this.domNode,{
				display: "none",
			})
		},
		
		onDeleteClick: function(){
			var self = this;
			if(this.click_doing){
				return;
			}
			this.click_doing = true;
			new Confirm(msg["mobile.confirm.delete.content"], msg["mobile.confirm.delete.title"], function(status, dialog){
				if(status){
					self.startToDelete();
				}
			});
			setTimeout(function(){
				self.click_doing = false;
			},100)
			
			
		},
		
		//开始删除评论
		startToDelete: function(){
			var url = "/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=deleteByMobile&fdId=" + this.fdId;
			url = util.formatUrl(url);
			var self = this;
			self.showLoading();
			var promise = request.get(url,{
				data:{},
				handleAs:"json"
			});
			promise.response.then(function(response){
				var data = response.data;
				self.hideLoading();
				if(data.success){
					self.loading_success(msg["mobile.msg.deleteSuccess"]);
					if(self.reHandleByDelete){
						self.reHandleByDelete(self.index);
					}
				}else{
					self.loading_error(data.errMsg);
				}
			},function(error){
				self.hideLoading();
				self.loading_error(error);
				console.log(error);
			});
		},


	});
	

});
	