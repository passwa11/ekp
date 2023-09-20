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
	
	
	return declare("sys.mportal.pictureFastLink.gridTile", [ WidgetBase, openProxyMixin ], {
		
	    // 图片数据源ID (以逗号间隔开的ID字符串)
		fdIds : '',
			
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
			// 将图片信息数组拆分为一行两列的数据结构数组
			dataList = this.structuredToRowsData(dataList);
			
			// 内容载体
			var containerNode = domConstruct.create('div', {
				className : 'muiPortalGridTileContainer',
			},this.domNode);
			
			// 构建图片DOM
			for(var i=0;i<dataList.length;i++){
				
				// 图片行DOM
				var rowNode = domConstruct.create('div', {
					className : 'muiPortalGridTileRow',
				},containerNode);
				
				var rowDataList = dataList[i];
				for(var k=0;k<rowDataList.length;k++){
					var pictrueInfo = rowDataList[k];
					var pictrueLinkNode = domConstruct.create('div', { 
						className:'muiPortalGridTilePictrue',
						style:{
							'background-image' : 'url(' + util.formatUrl(pictrueInfo.image) + ')',
							'height': this.pictureHeight+'px'	
						}
				    }, rowNode);
			        // 绑定图片点击事件
			        this.proxyClick(pictrueLinkNode, pictrueInfo.href, '_blank');					
				}
				

			}
			
		},
		
		/**
		* 将图片信息数组拆分为一行两列的数据结构数组
		* @param dataList 图片数据列表
		* @return 返回一行两列的数据结构数组
		*/		
		structuredToRowsData: function(dataList){
			var resultList = new Array();
			var rowList = new Array();
			var colNum = 2;
			for(var i=0;i<dataList.length;i++){
				rowList.push(dataList[i]);
				if((i+1)%colNum==0 || i==dataList.length-1){
					resultList.push(rowList);
				}
				if((i+1)%colNum==0 && i!=dataList.length-1){
					rowList = new Array();
				}
			}
			return resultList;
		}
		
		
	});
});