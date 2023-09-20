define([
    "dojo/_base/declare",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/dom-prop",
    "dojo/query",
    "dojo/dom-construct",
    "dojo/request",
    "mui/util",
    "sys/mportal/mobile/OpenProxyMixin",
    "dijit/_WidgetBase"
	], function(declare, domStyle, domClass, domProp, query, domConstruct, request, util, openProxyMixin, WidgetBase) {
	
	
	return declare("sys.mportal.pictureFastLink.swipeLeftAndRight", [ WidgetBase, openProxyMixin ], {
		
	    // 图片数据源ID (以逗号间隔开的ID字符串)
		fdIds : '',
		
		// 图片宽度
		pictureWidth: '',
		
		// 图片高度
		pictureHeight: '',
	    
	    // 图片数据源请求URL
	    url: '/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=getImg&fdIds=!{fdIds}&orderby=docAlterTime&ordertype=down',
	    
	    
		startup : function() {
			this.inherited(arguments);
			var requestUrl = util.urlResolver(this.url,{"fdIds":this.fdIds});
			var self = this;
			request.get(util.formatUrl(requestUrl),{handleAs:'json'}).then(function(dataList) {
				if(dataList) {
					self.buildPictureFastLinkContent(dataList);
				}
			});
		},
		
		/**
		* 构建图片快捷方式内容
		* @param dataList 图片数据列表
		* @return
		*/		
		buildPictureFastLinkContent: function(dataList){

			// 滚动条载体（苹果IOS使用overflow会出现滚动条，添加一个DIV使用样式去强制隐藏IOS滚动条）
			var scollNodeDom = domConstruct.create('div', {
				className : 'muiPortalSwipeLeftAndRightContainerScoll',
				style:{'height': this.pictureHeight+'px'}
			},this.domNode);
			
			// 内容载体
			var containerNode = domConstruct.create('div', {
				className : 'muiPortalSwipeLeftAndRightContainer',
			},scollNodeDom);
			
			// 构建图片DOM
			for(var i=0;i<dataList.length;i++){
				var pictrueInfo = dataList[i];
				var pictrueLinkNode = domConstruct.create('div', { 
					className:'muiPortalSwipeLeftAndRightPictrue',
					style:{
						'background-image' : 'url(' + util.formatUrl(pictrueInfo.image) + ')',
						'width': this.pictureWidth+'px',
						'height': this.pictureHeight+'px'	
					}
			    }, containerNode);
		        // 绑定图片点击事件
		        this.proxyClick(pictrueLinkNode, pictrueInfo.href, '_blank');
			}
			
		}
		
		
	});
});