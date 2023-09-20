define([ 'dojo/_base/declare', 'dijit/_WidgetBase', 'sys/attachment/mobile/js/_AttachmentTypeMixin', 
         'dojo/dom-construct', 'dojo/dom-class', 'dojo/dom-style', 'dojo/dom-attr', 'dojo/_base/lang', 'dojo/on',
         'dojo/topic', 'dijit/registry', 'mui/util', 'mui/device/adapter', 'dojox/mobile/sniff', 'mui/dialog/Tip',
         'mui/device/device', 'dojo/request' ], 
         function(declare, _WidgetBase, _AttachmentTypeMixin, domCtr, domClass, domStyle, domAttr, lang, on, 
        		 topic, registry, util, adapter, has, Tip, device, request) {
	return declare('sys.attachment.mobile.zip.ZipViewerItem', [ _WidgetBase, _AttachmentTypeMixin ], {
		
		name: '',
		fdId: '',
		
		//文件类型：dir文件夹，file文件
		type: '',
		
		//标记文件是否转换成功
		success: true,
	
		imgViewHref: '/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId=!{fdId}',
		
		viewHref : '/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=!{fdId}&viewer=mobilehtmlviewer',

		viewPicHref : '/third/pda/attdownload.jsp?open=1',
		
		canDownload: true,
		canRead : true,
		
		postCreate: function() {
			
			var self = this;
			
			domClass.add(this.domNode, 'muiAttViewerZipItem');
			
			if(!this.success) {
				domAttr.set(this.domNode, 'data-success', 'false');
			}
			
			var fileType = this.getType();
			
			var leftIconNode = domCtr.create('div', {
				className: 'muiAttViewerLeftIcon muiAttViewerIcon ' + 'muiAttViewerIcon-' + fileType
			}, this.domNode);
			
			if(fileType == 'img') {
				leftIconNode.className = 'muiAttViewerLeftIcon';
				
				domCtr.create('img', {
					src: util.formatUrl(this.imgViewHref.replace(
							'!{fdId}', this.fdId), true)
				}, leftIconNode);
				
			} else if(this.type == 'dir') {
				leftIconNode.className = 'muiAttViewerLeftIcon muiAttViewerIcon muiAttViewerIcon-folder';
			} else {
			}
			
			var labelNode = domCtr.create('div', {
				className: 'muiAttViewerZipLabel'
			}, this.domNode);
			
			var topNode = domCtr.create('div', {
				className: 'muiAttViewerZipTop',
				innerHTML: this.name || ''
			}, labelNode);
			var maxWidth = window.innerWidth - 68;
			if(maxWidth > 0) {
				domStyle.set(topNode, 'max-width', (maxWidth / 10).toFixed(2) + 'rem');
			}
			
			var detailNode = domCtr.create('div', {
				className: 'muiAttViewerZipBottom'
			}, labelNode);
			
			if(this.type == 'dir') {
				/*
				domCtr.create('div', {
					className: 'mui mui-forward muiAttViewerRightIcon'
				}, this.domNode);
				 */
				
				domCtr.create('span', {
					innerHTML: '共' + ((this.list || []).length || 0) + '个文件'
				}, detailNode);
				
				on(this.domNode, 'click', function() {
					topic.publish('sys/attachment/mobile/zipViewer/pushLink', self.fdId);
				});
			} else {
				
				domCtr.create('span', {
					innerHTML: '__'
				}, detailNode);
				
				var size = this.size || 0;
				if(size < (1024 * 1024)) {
					domCtr.create('span', {
						innerHTML: (size / 1024).toFixed(2) + 'KB'
					}, detailNode);
				} else {
					domCtr.create('span', {
						innerHTML: (size / (1024 * 1024)).toFixed(2) + 'MB'
					}, detailNode);
				}
				
				on(this.domNode, 'click', function() {
					self.view();
				});
			}
			
			this.inherited(arguments);
		},
		
		view: function() {

			if(!this.type != 'dir' && (!this.fdId || !this.canView)) {
				Tip.fail({
					text: '附件转换失败'
				});
				return;
			}
			
			//附件不支持预览则提供下载功能
			if(!this.canViewOnline()) {
				this._downLoad();
				return;
			}
			
			var url = '';
			var fileType = this.getType();
			
			if (fileType == 'img') {
				var devType = device.getClientType();
				var attrNodeList = this.domNode.parentNode.children;
				var srcList = [];
				for(var i = 0;i < attrNodeList.length;i++){
					var attrObj = registry.byNode(attrNodeList[i]);
					try {
						if(attrObj && attrObj.getType() == 'img'){
							if (has('ios') || attrObj.canRead) {
								url = util.formatUrl( attrObj.viewPicHref + "&isPic=true" + '&fdId=' + attrObj.fdId,true);
								srcList.push(url);
							}
						}
					}catch(e){
						
					}
				}
				
				var curSrc = util.formatUrl(this.viewPicHref + "&isPic=true" + '&fdId=' + this.fdId,true);
				if(curSrc && srcList.length > 0) {
					adapter.imagePreview({
						curSrc : curSrc, 
						srcList : srcList,
						previewImgBgColor : ''
					});
				}
				
				return;
			} else if (fileType == 'video') {
				url = util
						.formatUrl('/sys/attachment/mobile/viewer/video/videoViewer.jsp?fdId='+ this.fdId,true);
			}
			else if (fileType == 'audio') {
				url = util
						.formatUrl('/sys/attachment/mobile/viewer/audio/audioViewer.jsp?fdId='+ this.fdId,true);
			}else if(fileType == 'text') {
				url = util
						.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId='+ this.fdId,true);
			}
			
			/*子压缩文件不允许预览
			else if(fileType == 'zip' || fileType == 'rar' || fileType == '7z') {
				url = util
						.formatUrl('/sys/attachment/mobile/viewer/zip/zipViewer.jsp?fdId='+ this.fdId,true);
			}
			*/
			
			else {
				url = util.formatUrl(this.viewHref.replace(
						'!{fdId}', this.fdId),true);
			}
			try {
				
				if (this.name) {
					// 去掉后缀，修复kk对于mp4结尾的链接进行直接播放的问题
					var name = this.name.substring(0, this.name
							.lastIndexOf('.'));
					url = util.setUrlParameter(url, 'title',
							name);
				}
				
				if(typeof(eval(adapter.open)) == 'function' && this.getType() != 'text'){
					adapter.open(url,'_blank');
				}else{
					location.href = url;
				}
			} catch (e) {
				location.href = url;
			}
			
		},

		canViewOnline : function() {
			return this.hasViewer || this.getType() == 'img' || (has('ios') && this.getType() == 'text');
		},
		
		_downLoad : function() {
			if(this.canDownload){
				// 记录下载日志
				var logUrl = "/sys/attachment/sys_att_download_log/sysAttDownloadLog.do?method=addDownlodLog&downloadType=manual&fdId="+this.fdId;
				request.post(util.formatUrl(logUrl,true));
				var downloadUrl = util.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=' + this.fdId, true);
				adapter.download({
					fdId : this.fdId,
					name : this.name,
					type : this.type,
					href : downloadUrl
				});
			}else{
				Tip.tip({
					icon : 'mui mui-warn',
					text : '无权限下载文件'
				});
			}
		},
		
		
	});
});