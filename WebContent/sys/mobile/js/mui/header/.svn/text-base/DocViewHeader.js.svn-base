/**
 *  〖 文档查看页面标准头部组件 〗
 *  固定展示项：
 *  文档标题、当前登录用户头像、当前登录用户姓名、文档状态
 *  动态扩展项:
 *  (支持通过传递动态数组数据展示例如：申请单编号、所属分类名称、创建人、所在部门、创建时间......等扩展字段)
 */
define("mui/header/DocViewHeader", [ 
       "dojo/_base/declare", 
       "dijit/_WidgetBase",
	   "dojo/dom-class",
	   "dojo/dom-style",
       "dojo/dom-construct",
       "dojo/dom-geometry",
       "dojo/on",
       "mui/util",
       "mui/i18n/i18n!sys-mobile:mui.mobile.process.status"
      ], function(declare, WidgetBase, domClass, domStyle, domConstruct, domGeometry, on, util, statusMsg) {
	
	var header = declare("mui.header.DocViewHeader", [ WidgetBase ], {

		baseClass : 'muiDocViewHeader',
		
		/* 文档标题   */
		subject: '',
		
		/* 文档创建人ID（用户头像是根据文档创建人ID动态获取） */
		userId: '',
		
		/* 文档创建人姓名 */
		userName: '',
		
	    /* 文档创建人头像请求链接   */
	    imgUrl: '/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=!{userId}',
	    
	    /* 文档状态常量值（00、20、30......）   */
	    docStatus: '', 
	    
	    /* 文档状态显示文本（此属性用于支持业务模块自定义需要展示的状态文本值）   */
	    docStatusText: '',
	    
	    /* 文档状态可选枚举项 (参数属性名解释： text：状态显示文本、iconClass：状态对应的图标CSS Class类名)  */
	    docStatusEnum:{
	        '00': { text:statusMsg["mui.mobile.process.status.discard"], iconClass:'processStatusDiscard' },   // 已废弃
	    	'20': { text:statusMsg["mui.mobile.process.status.examine"], iconClass:'processStatusExamine' },   // 审批中
	    	'30': { text:statusMsg["mui.mobile.process.status.done"], iconClass:'processStatusDone' }    // 已办结
	    },
	    
	    /*  是否默认展开明细项     */
	    expandItems: false,
	      
		/*
		 * 明细项数据数组
		 * 参数数据格式示例：
		 *   itemDatas: [
		 *   	{name: '申请单编号', value: 'LC2020010100001'},
		 *   	{name: '分类名称', value: '行政服务类/请假申请'},
		 *      {name: '创建人', value: '行政服务类/请假申请', click:function(){}},
		 *      {name: '创建时间', value: '2020-05-06 21:01'}
		 *   ]
		 * 参数属性名解释： name：字段名称、value：字段值、click：点击事件响应函数  
		 */
	    itemDatas: [],


		buildRendering : function() {
			this.inherited(arguments);

			var baseNode = domConstruct.create("div", {className: "muiDocViewHeaderBase"}, this.domNode);
			
			
			// 文档标题
			if(this.subject){
				domConstruct.create("div", {className: "muiDocViewHeaderSubject",innerHTML:this.subject}, baseNode);
			}
			
			
			if( this.userId || this.userName ){
				
				// 人员信息载体容器DOM（内容包括：创建人头像、创建人姓名）
				var personNode = domConstruct.create("div", {className: "muiDocViewHeaderPerson"}, baseNode);
				
				// 文档创建人头像
				if(this.userId){
			    	var headerIconNode = domConstruct.create("div", {className: "muiDocViewHeaderIcon"}, personNode);
			        domConstruct.create("img", {src: util.formatUrl(this.imgUrl.replace(/!{userId}/, this.userId))}, headerIconNode);
				}

				// 文档创建人姓名
				if(this.userName){
					domConstruct.create("div", {className: "muiDocViewHeaderUser",innerHTML:this.userName}, personNode);
				}

			}
			
			
			// 文档状态（图标加文字展示）
			if(this.docStatus && this.docStatusEnum[this.docStatus]){
				var statusData = this.docStatusEnum[this.docStatus];
				var iconClass = statusData.iconClass?statusData.iconClass:'';
				var statusNode = domConstruct.create("div", {className: "muiDocViewHeaderStatus "+iconClass});
				var iNode = domConstruct.create("i", {className: "fontmuis muis-watermark"}, statusNode);
				var spanNode = domConstruct.create("span", {}, statusNode);
				// 优先取自定义状态文本，无自定义状态文本则取默认文本 
				spanNode.innerText = this.docStatusText ? this.docStatusText : statusData.text;
				domConstruct.place(statusNode, baseNode, 'last');
			}

			
			// 详情明细项展示
			if(this.itemDatas && this.itemDatas.length>1){
				
				// 详情明细项信息载体容器DOM（内容为动态传递的详情字段信息）
				var detailWarp = domConstruct.create("div", {className: "muiDocViewHeaderDetailWarp"}, this.domNode);
				
				var detailContainer = domConstruct.create("div", {className: "muiDocViewHeaderDetailContainer"}, detailWarp);
				
				var detailNode = domConstruct.create("div", {className: "muiDocViewHeaderDetail"}, detailContainer);
				
				for(var i=0;i<this.itemDatas.length;i++){
					var data = this.itemDatas[i];
					// 详情明细行载体DOM
					var itemNode = domConstruct.create("div", {className: "muiDocViewHeaderDetailItem"}, detailNode);
					// 左侧标题
					var itemNodeL = domConstruct.create("div", {className: "muiDocViewHeaderDetailItemL"}, itemNode);
					var spanNodeL = domConstruct.create("span", {}, itemNodeL);
					if(data.name){
						spanNodeL.innerHTML = data.name;
					}
					// 右侧内容
					var itemNodeR = domConstruct.create("div", {className: "muiDocViewHeaderDetailItemR"}, itemNode);
					var spanNodeR = domConstruct.create("span", {}, itemNodeR);
					if(data.value){
						spanNodeR.innerHTML = data.value;
					}
					// 绑定click点击事件
					if(data.click){
				        on(spanNodeR, "click", data.click);
					}
				}
                
				// 明细项载体DOM Node设置最大高度样式（为了实现展开、收起的CSS动画）
				var detailWarpHeight = domGeometry.getContentBox(detailWarp).h;
				if(this.expandItems){
					domStyle.set( detailWarp, {"max-height":detailWarpHeight+"px","overflow":"inherit"} );				
				}else{
					domStyle.set( detailWarp, {"max-height":"0","overflow":"hidden"} );
				}
				
				// 构建“展开”更多按钮DOM
				var expandButton = domConstruct.create("div", { className: "muiDocViewHeaderExpandBtn", style:{"display":this.expandItems?"none":"block",}}, this.domNode);
				domConstruct.create("i", {className:"fontmuis muis-slide-down"}, expandButton);
				
				// 构建“收起”更多按钮DOM（默认隐藏）
				var collapseButton = domConstruct.create("div", { className: "muiDocViewHeaderCollapseBtn", style:{"display":this.expandItems?"block":"none"}}, this.domNode);
				domConstruct.create("i", {className:"fontmuis muis-slide-up"}, collapseButton);
				
				// 绑定“展开”更多按钮点击事件
			    this.connect(expandButton, "click", function(){
			    	 domClass.remove( expandButton, "muiDocViewHeaderBtnFadeIn" );
		    		 domStyle.set( expandButton, {"display":"none"} );
		    		 domClass.add( collapseButton, "muiDocViewHeaderBtnFadeIn" );
		    		 domStyle.set( collapseButton, {"display":"block"} );
		    		 domClass.remove( detailWarp, "muiDocViewHeaderWarpHidden" );
		    		 domClass.add( detailWarp, "muiDocViewHeaderWarpShow" );
		    		 domStyle.set( detailWarp, {"max-height":detailWarpHeight+"px","overflow":"inherit"} );
			    });
			    // 绑定“收起”更多按钮点击事件
			    this.connect(collapseButton, "click", function(){
			    	 domClass.remove( collapseButton, "muiDocViewHeaderBtnFadeIn" );
		    		 domStyle.set( collapseButton, {"display":"none"} );
		    		 domClass.add( expandButton, "muiDocViewHeaderBtnFadeIn" );
		    		 domStyle.set( expandButton, {"display":"block"} );
		    		 domClass.remove( detailWarp, "muiDocViewHeaderWarpShow" );
		    		 domClass.add( detailWarp, "muiDocViewHeaderWarpHidden" );
		    		 domStyle.set( detailWarp, {"max-height":"0","overflow":"hidden"} );
			    });
				
			}
			

		},

	});
	
	return header;
});