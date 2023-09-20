define(		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class", "dojo/dom-style",
				"mui/util", "dojo/html","dojo/dom", "dojo/on",
				"dojo/_base/array", "mui/i18n/i18n!sys.evaluation:mobile","dojox/mobile/_ItemBase",
				"./MyEvaluationChildItem"
				],
		function(declare, domConstruct, domClass, domStyle, util, html, dom, on, array, msg, ItemBase, MyEvaluationChildItem) {

	return declare("sys.evaluation.myEvaluation.list.item", [ ItemBase ], {
		
		fdId: "",
		
		fdModelId: "",
		
		fdModelName: "",
		
		//主model标题
		fdModelDocSubject: "",
		
		//主model模块名
		fdModelModuleName: "",		
		
		//评分
		fdEvaluationScore: "",		
		
		//评论列表
		evalList: [],
		// 单个评论内容
		//		{
		//			fdEvaluationContent: "", //评论内容
		//			fdEvaluationTime: "",   //评论时间
		//			fdEvaluationIntervalTime: "",
		//		}
		
		//最大评论展示数量（其余隐藏）
		showMaxEvalNum: 2,
				
		baseClass: "muiMyEvaluationListItem",
		

		buildRendering : function() {
			this.inherited(arguments);		
			this.biludItem();
		},
		
		
		biludItem: function(){
			//主model标题
			domConstruct.create("div",{
				className: "modelDocSubject",				
				innerHTML: this.fdModelDocSubject
			},this.domNode);
						
			var topBottomContainer = domConstruct.create("div",{
				className: "topBottomContainer",
			},this.domNode);
			
			//标签
			var bgUrl = "/sys/evaluation/mobile/myEvaluation/img/tag-background.png";
			bgUrl = util.formatUrl(bgUrl,true);
			domConstruct.create("div",{	
				className: "modelModuleName",
				innerHTML: this.fdModelModuleName
			},topBottomContainer);
			
			//评分
			this.buildEvalScoreNode(topBottomContainer);
			
			//评论容器结点
			var childContainerNode = domConstruct.create("div",{
				className: "evalContainer"
			},this.domNode);

			this.showMore = false;
			this.childItemNode = [];
			var totalSize = this.evalList.length;
			var self = this;
			//渲染评论
			for(var i = 0; i < this.evalList.length; i++){
				var item = this.evalList[i];
				item.reHandleByDelete = function(index){
					self.reHandleByDelete(index);
				};
				if(i < this.showMaxEvalNum){
					item.isShow = true;
				}
				if(i < (totalSize - 1)){
					item.showHr = true;
				}
				item.index = i;
				var node = new MyEvaluationChildItem(item);
				node.placeAt(childContainerNode);
				this.childItemNode.push(node);
			}
			
			//构建展示隐藏更多
			this.buildShowOrHideMoreNode(childContainerNode, totalSize);
		},
		
		/**
		 * 构建评分结点
		 */
		buildEvalScoreNode: function(containerNode){
			var score = 5 - this.fdEvaluationScore;
			var evalStar = {};
			for(var i=1; i <= score; i++){
				evalStar["evalStar"+i] = "muiRatingOn";
			}
			for(var i= score + 1; i <= 5; i++){
				evalStar["evalStar"+i] = "muiRatingOff";
			}
			
			var scoreHtml = "<div class='muiRating' style='width:auto !important;'><div class='muiRatingArea'>";
			
			for(var i=1; i <= 5; i++){
				scoreHtml += "<i class='mui mui-star-on mui-2 " + evalStar["evalStar"+i] + "'></i>";
			}
			scoreHtml += "</div></div>"	

			domConstruct.create("div",{	
				style:"margin-left:1rem;" ,
				innerHTML: scoreHtml
			},containerNode);
		},
		
		
		/**
		 * 构建展示或隐藏更多节点
		 */
		buildShowOrHideMoreNode: function(childContainerNode, totalSize){
			//显示隐藏更多按钮
			this.childShowHideButtonContainer = domConstruct.create("div",{	
			},childContainerNode);
			var arrowDownUrl = "/sys/evaluation/mobile/myEvaluation/img/arrow-down.svg";
			arrowDownUrl = util.formatUrl(arrowDownUrl,true);
			this.showMoreHTML = "<img class='arrrow-down' src='" + arrowDownUrl + "'/>" + msg["mobile.msg.moreEval"];
			this.hideMoreHTML = "<img class='arrrow-up' src='" + arrowDownUrl + "'/>" + msg["mobile.msg.packUpEval"];
			
			if(totalSize > this.showMaxEvalNum){		
				this.childShowHideButtonNode = domConstruct.create("div",{
					className: "showHideButton",
					innerHTML: this.showMoreHTML
				},this.childShowHideButtonContainer);
				var self = this;
				on(this.childShowHideButtonNode,"click", function(){
					if(self.doningClick){
						return;
					}
					self.doningClick = true;
					setTimeout(function(){
						self.doningClick = false;
					},50);
					self.showOrHideMoreChilds();
				})
			}
		},
		
		/**
		 * 隐藏和展示更多事件
		 */
		showOrHideMoreChilds: function(){
			if(!this.showMore){
				for(var i=0; i < this.childItemNode.length; i++){
					var node = this.childItemNode[i];
					node.show(i);
				}
				this.childShowHideButtonNode.innerHTML = this.hideMoreHTML;
				this.showMore = true;
			}else{
				for(var i=0; i < this.childItemNode.length; i++){
					var node = this.childItemNode[i];
					if(i < this.showMaxEvalNum){
						node.show(i);
					}else{
						node.hide(i);
					}
				}
				this.childShowHideButtonNode.innerHTML = this.showMoreHTML;
				this.showMore = false;	
			}
		},
		
		//删除评论后重新处理数据
		reHandleByDelete: function(childNode){
			var index = childNode.index;
			var newNodes = [];
			var deleteNode = null;
			//计算删除的结点
			for(var i=0; i < this.childItemNode.length; i++){
				var node = this.childItemNode[i];
				if(i != index){
					newNodes.push(node);
				}else{
					deleteNode = node;
				}
			}
			this.childItemNode = newNodes;
			if(deleteNode){
				deleteNode.destroyRecursive();
			}
			if(childNode.isAddition){
				//重新展示隐藏评论数据
				for(var i=0; i < this.childItemNode.length; i++){
					var node = this.childItemNode[i];
					if(i < this.showMaxEvalNum){
						node.show(i);
					}else{
						node.hide(i);
					}
				}			
				if(this.childItemNode.length <= this.showMaxEvalNum){
					domStyle.set(this.childShowHideButtonContainer,{
						display: "none"
					});
				}
			}
			
			if(this.childItemNode.length == 0 || !childNode.isAddition){
				var parentNode = this.getParent();
				var currentNodes = parentNode.getChildren();
				if(currentNodes.length <= 1){
					parentNode.reload();
				}else if(currentNodes.length <= 6 && !currentNodes._loadOver){
					parentNode.loadMore();
				}
				this.destroyRecursive();
			}
		},
		
	
		
		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		},


	});
	

});
	