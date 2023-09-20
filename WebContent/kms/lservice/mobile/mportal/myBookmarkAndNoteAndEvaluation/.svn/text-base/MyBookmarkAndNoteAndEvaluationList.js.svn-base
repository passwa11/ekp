define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/dom-style",
	"dojo/_base/lang",
	"dijit/_WidgetBase",
	"dijit/_Container",
	"dijit/_Contained",
	"dojo/_base/array",
	"dojo/request",
	"dojo/on",
	"mui/util",
	"./item/LayoutListNodeItem",
	"mui/i18n/i18n!kms-lservice:mobile"
	], function(declare, domConstruct, domClass, domStyle, lang, _WidgetBase, _Container, _Contained,  array, request, on, util, LayoutListNodeItem, msg) {
	
	return  declare("kms.lservice.myBookmarkAndNoteAndEvaluationList",[_WidgetBase, _Container, _Contained], {
		
		//展示收藏
		showBookmark: true,
	
		//展示笔记
		showNote: true,
		
		//展示点评
		showEvaluation: true,			
		
		baseClass: "muiKmsLseviceMyBookmarkAndNoteAndEvaluation",
		
		langInfo: {
			bookmark: msg["mobile.msg.bookmark"],
			note: msg["mobile.msg.note"],
			evaluation: msg["mobile.msg.evaluation"],
		},
			
		buildRendering : function() {
			this.inherited(arguments);	
			this.currentOrder = 0;
			if(this.showBookmark){
				this.buildBookmark();
			}
			if(this.showNote){
				this.buildNote();
			}
			if(this.showEvaluation){
				this.buildEvaluation();
			}
		},		
		
		

		
		//构建收藏
		buildBookmark: function(){	
			this.currentOrder++;
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/bookmark.png",true);	
			this.buildNode("bookmark", imgUrl, this.langInfo["bookmark"], "");
			
		},
		
		//构建笔记
		buildNote: function(){
			this.currentOrder++;
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/note.png",true);	
			this.buildNode("note", imgUrl, this.langInfo["note"], "");
		},
		
		
		//构建点评
		buildEvaluation: function(){
			this.currentOrder++;
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/evaluation.png",true);	
			this.buildNode("evaluation", imgUrl, this.langInfo["evaluation"], "");
		},
		
		buildNode: function(type, imgUrl, text, rightText){
			var showHr = false;
			//分割线
			if(this.currentOrder > 1){
				domConstruct.create("div",{
					className: "muiKmsLseviceMyBookmarkAndNoteAndEvaluation_hr"
				},this.domNode);
			}		
			var layoutListNodeItem = new LayoutListNodeItem({
				type: type,
				imgUrl: imgUrl,
				text: text,
				rightText: rightText,
				showHr: showHr,
			});
			layoutListNodeItem.placeAt(this.domNode);
		}
		
	});
});