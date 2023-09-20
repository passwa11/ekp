define(		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class", "dojo/dom-style",
				"mui/util", "dojo/html","dojo/dom", "dojo/on",
				"dojo/_base/array", "mui/i18n/i18n!kms.common:mobile","dojox/mobile/_ItemBase"
				],
		function(declare, domConstruct, domClass, domStyle, util, html, dom, on, array, msg, ItemBase) {

	return declare("kms.common.myNoteCourse.list.item", [ ItemBase ], {
		
		fdId: "",
		
		fdModelName: "",
		
		//创建时间
		docCreateTime: "",
		
		//课件名称
		courseName: "",
		
		//是否删除
		isDelete: "",
		
		//笔记数量
		noteNum: "",
		
		baseClass: "muiMyNoteCourseItem",
		

		buildRendering : function() {
			this.inherited(arguments);
		
			this.biludItem();
			
			var self = this;
			on(this.domNode, "click", function(e){
				self.onClick(e);
			});
		},
		
		
		biludItem: function(){
			//课件名称
			domConstruct.create("div",{
				className:"courseName",
				innerHTML: this.courseName
			},this.domNode);
			
			
			var bottomContainer = domConstruct.create("div",{	
				className: "bottom",
			},this.domNode);
			
			//创建时间
			domConstruct.create("div",{	
				className: "time",
				innerHTML: this.docCreateTime
			},bottomContainer);
			
			
			var rightContainer = domConstruct.create("div",{
				className:"right"
			},bottomContainer);
			
			domConstruct.create("div",{	
				className: "num",
				innerHTML: this.noteNum + msg["mobile.myNote.msg.unit"]
			},rightContainer);
			
			domConstruct.create("span",{	
				className: "text",
				innerHTML: msg["mobile.myNote.msg.note"]
			},rightContainer);
			
			domConstruct.create("div",{	
				className: "hr",
			},this.domNode);

		},
		
		onClick: function(){
			var url = "/kms/common/mobile/myNote/courseNotes.jsp?";
			url += "fdModelId=" + this.fdId + "&fdModelName=" + this.fdModelName;
			url += "&courseName=" + encodeURIComponent(this.courseName);
			var url = util.formatUrl(url,true);
			window.open(url,"_self");
		},
		
		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		},


	});
	

});
	