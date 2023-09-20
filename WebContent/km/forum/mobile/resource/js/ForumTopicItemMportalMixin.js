define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
    "mui/util",
    "km/forum/mobile/resource/js/MixContentBItemMixin","mui/i18n/i18n!km-forum:kmForumTopic.status"
	], function(declare, domConstruct, domClass, util, MixContentItemMixin,Msg) {
	var item = declare("km.forum.mobile.resource.js.ForumTopicItemMixin", [MixContentItemMixin], {
		//回复数
		replay:0,
		
		//阅读数
		count:0,
		
		//是否为置顶帖
		isTop:"0",
		
		//点赞数
		supportCount:0,
		
		buildInternalRender:function(){
//			if(this.isTop=="1"){
//				this.buildTopicItemRender();
//			}else{
				this.inherited(arguments);
//			}
		},
		
		//绘制置顶帖子
		buildTopicItemRender:function(){
			domClass.add(this.domNode,"muiMixContentTopItem");
			var topArea = domConstruct.create("div",{className:"muiMixContentTopInfo"},this.contentNode);
			domConstruct.create("span",{className:"muiTopicHeadSign muiTopicHeadTop",innerHTML: Msg['kmForumTopic.status.top']},topArea);
			var cate = domConstruct.create("span",{className:"muiMixContentTopCate",innerHTML: '[' + this.category + ']'},topArea);
			this.connect(cate,'click','gotoCate');
			domConstruct.create("span",{className:"muiMixContentTopTitle", innerHTML:this.label},topArea);
		},
		
		gotoCate:function(evt){
			var url = "/km/forum/mobile/index.jsp";
			var dataUrl = '/km/forum/km_forum/kmForumTopicIndex.do?method=listChildren&q.categoryId=!{categoryId}&orderby=fdLastPostTime&ordertype=down';
			url = util.setUrlParameter(url,"moduleName",this.category);
			url = util.setUrlParameter(url,"filter","1");
			url = util.setUrlParameter(url,"queryStr",util.setUrlParameter(dataUrl,"q.categoryId",this.categoryId));
			location = util.formatUrl(url);
		},
		
		replayPost:function(evt){
			if(window.replayPost)
				window.replayPost(this);
		},
		
		buildBottomRender:function(bottom){
			if(this.created){
				domConstruct.create("div",{className:"muiMixContentCreated muiListSummary",
					innerHTML:"<span1 class='muiBottomTime'>" + this.created + "</span1>"},bottom);
			}
			domConstruct.create("div",{className:"muiMixContentNum",
					innerHTML:"<span class='muiBottomNumber'>"+(this.supportCount==null || this.supportCount==0?0:this.supportCount)+"</span><span class='muiBottom muiFontSizeS'>"+Msg['kmForumTopic.status.count']+"</span>"},bottom);
			//this.connect(parise,'click','parisePost');
			domConstruct.create("div",{className:"muiMixContentNum",
					innerHTML: "<span class='muiBottomNumber'>"+(this.replay==null || this.replay==0?0:this.replay)+"</span><span class='muiBottom muiFontSizeS'>"+Msg['kmForumTopic.status.replay']+"</span>"},bottom);
			//this.connect(replayDiv,'click','replayPost');
			domConstruct.create("div",{className:"muiMixContentNum",
					innerHTML: "<span class='muiBottomNumber'>"+(this.count==null || this.count==0?0:this.count)+"</span><span class='muiBottom muiFontSizeS'>"+Msg['kmForumTopic.status.view']+"</span>"},bottom,'last');
		}
	});
	return item;
});