define(		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class", "dojo/dom-style",
				"mui/util", "dojo/html","dojo/dom", "dojo/on", "dojo/_base/array", "dojo/request",
				"mui/dialog/Confirm",
				"mui/i18n/i18n!kms.common:mobile",
				"../Loading",
				"dojox/mobile/_ItemBase"
				],
		function(declare, domConstruct, domClass, domStyle, util, html, dom, on, array, request, Confirm, 
				msg, Loading, ItemBase) {

	return declare("kms.common.myNoteCourseNote.list.item", [ ItemBase, Loading ], {
		
		fdId: "",
		
		//创建时间
		docCreateTime: "",
		
		docCreateTime2: "",
		
		"docCreator.fdName": "",
		
		"docCreator.fdId": "",
		
		//点赞次数
		praiseCount: "",
		
		//点评次数
		evalCount: "",
		
		//课件名称
		fdCourse: "",
		
		//是否分享
		isShare: "false",
		
		//笔记内容
		fdNotesContent: "",
		
		baseClass: "muiMyNoteCourseNoteItem",
		

		buildRendering : function() {
			this.inherited(arguments);
			
			this.biludItem();
			
			var self = this;
			on(this.domNode, "click", function(e){
				self.onClick(e);
			});
		},
		
		
		biludItem: function(){
			
			var topContainer = domConstruct.create("div",{
				className: "top",
			},this.domNode);
				
				
			//笔记名称
			domConstruct.create("div",{
				className: "content",
				innerHTML: this.fdNotesContent
			},topContainer);
			
			
			var secondContainer = domConstruct.create("div",{	
				className: "second",
			},topContainer);
			
			var shareImgUrl = "/kms/common/mobile/myNote/image/share.png";
			if(!this.isShare || this.isShare=="false"){
				shareImgUrl = "/kms/common/mobile/myNote/image/private.png";
			}
			shareImgUrl = util.formatUrl(shareImgUrl,true);
			domConstruct.create("img",{	
				className: "shareImg",
				src: shareImgUrl
			},secondContainer);
			
			var timeImgUrl = "/kms/common/mobile/myNote/image/time.svg";
			timeImgUrl = util.formatUrl(timeImgUrl,true);
			
			domConstruct.create("img",{	
				className: "timeImg",
				src: timeImgUrl
			},secondContainer);
			
			//创建时间
			domConstruct.create("div",{	
				className: "time",
				innerHTML: this.docCreateTime2 || this.docCreateTime
			},secondContainer);
			
			//hr
			domConstruct.create("div",{	
				className: "hr",
			},topContainer);
			
			var bottomContainer = domConstruct.create("div",{	
				className: "bottom",
			},this.domNode);
			
			var bottomEvalContainer = domConstruct.create("div",{	
				className: "evalContainer",
			},bottomContainer);			
			var evalImgUrl = "/kms/common/mobile/myNote/image/eval.svg";
			evalImgUrl = util.formatUrl(evalImgUrl,true);
			domConstruct.create("img",{	
				className: "evalImg",				
				src: evalImgUrl
			},bottomEvalContainer);			
			domConstruct.create("div",{	
				className: "evalCount",
				innerHTML: this.evalCount
			},bottomEvalContainer);
			
			var bottomPraiseContainer = domConstruct.create("div",{	
				className: "praiseContainer"
			},bottomContainer);			
			var praiseImgUrl = "/kms/common/mobile/myNote/image/praise.svg";
			praiseImgUrl = util.formatUrl(praiseImgUrl,true);
			domConstruct.create("img",{	
				className: "praiseImg",
				src: praiseImgUrl
			},bottomPraiseContainer);		
			domConstruct.create("div",{	
				className: "praiseCount",
				innerHTML: this.praiseCount
			},bottomPraiseContainer);
			
			var bottomRightContainer = domConstruct.create("div",{	
				className: "right",
			},bottomContainer);
			
			var bottomDeleteContainer = domConstruct.create("div",{	
				className: "deleteContainer"
			},bottomRightContainer);
			var deleteImgUrl = "/kms/common/mobile/myNote/image/delete.svg";
			deleteImgUrl = util.formatUrl(deleteImgUrl,true);
			domConstruct.create("img",{	
				className: "deleteImg",
				src: deleteImgUrl
			},bottomDeleteContainer);
			this.bottomDeleteNode = bottomDeleteContainer;
			var self = this;
			on(this.bottomDeleteNode, "click", function(e){
				self.onDeleteClick(e);
			})
			on(bottomEvalContainer, "click", function(e){
				self.onEvalClick(e);
			})
		},
		
		
		
		onEvalClick: function(e){
			var url = "/sys/evaluation/mobile/index.jsp?modelName=com.landray.kmss.kms.common.model.KmsCourseNotes&modelId="
				+ this.fdId;
			url = util.formatUrl(url);
			window.open(url, "_self");
		},
		
		onDeleteClick: function(){
			var self = this;
			if(this.click_doing){
				return;
			}
			this.click_doing = true;
			new Confirm(msg["mobile.myNote.comfirm.delete.content"], msg["mobile.myNote.comfirm.delete.title"], function(status, dialog){
				if(status){
					self.startToDelete();
				}
			});
			setTimeout(function(){
				self.click_doing = false;
			},100)
			
			
		},
		
		//开始删除笔记
		startToDelete: function(){
			var url = "/kms/common/kms_notes/kmsCourseNotes.do?method=deleteByMobile&fdId=" + this.fdId;
			url = util.formatUrl(url);
			var self = this;
			self.showLoading();
			var promise = request.post(url,{
				data:{},
				handleAs:"json"
			});
			promise.response.then(function(response){
				var data = response.data;
				self.hideLoading();
				if(data.success){
					self.loading_success(msg["mobile.myNote.msg.deleteSuccess"]);
					var parentNode = self.getParent();
					var currentNodes = parentNode.getChildren();
					if(currentNodes.length <= 1){
						parentNode.reload();
					}
					self.destroyRecursive();		
				}else{
					self.loading_error(data.errMsg);
				}
			},function(error){
				self.loading_error(error);
				console.log(error);
			});
		},
		
		onClick: function(){
			//alert("跳转到笔记详情页");
		},
		
		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		},


	});
	

});
	