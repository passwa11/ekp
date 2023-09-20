define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-style",
		"dojo/query", "dojo/_base/array", "dojo/_base/lang", "dojo/dom-attr",
		"mui/util", "dojo/on", "dojo/request" ], function(declare, domConstruct, domStyle, query, array,
		lang, domAttr, util, on, request) {

	return declare("mui.rtf._VideoResizeMixin", null, {

		formatContent : function(domNode) {

			this.inherited(arguments);
			var embeds = [];

			if (typeof (domNode) == "object")
				embeds = query('embed', domNode);
			else
				embeds = query(domNode + ' embed');

			if (embeds.length > 0) {

				var self = this;

				array.forEach(embeds, function(item, index) {
					self.resizeVideo(item);
				});

			}
			
			var h5Videos = [];
			if (typeof (domNode) == "object")
				h5Videos = query('video', domNode);
			else
				h5Videos = query(domNode + ' video');
			if (h5Videos.length > 0) {
				var self = this;
				array.forEach(h5Videos, function(item, index) {
					self.resizeH5Video(item);
				});
			}					
		},
		
		resizeH5Video: function(item){
			var src = domAttr.get(item, 'src');	
			var srcReg = /.*?\/resource\/fckeditor\/editor\/filemanager\/download.*?/;
			var self = this;
			domAttr.remove(item, 'src');
			domAttr.remove(item,"height");			
			domAttr.remove(item,"autoplay");			
			domAttr.set(item,"controls","controls");
			domAttr.set(item,"controlslist", "nodownload");
			domStyle.set(item,"max-width","100%");	
			if(src){				
				var fdId = util.getUrlParameter(src, "fdId");
				if(srcReg.test(src) && fdId){					
					var poster = domAttr.get(item, 'poster');		
					if(!poster){
						//视频封面图
						var posterUrl = '/sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&fdType=rtf&fdId=' + fdId;
						posterUrl =  util.urlResolver(util.formatUrl(posterUrl,true));
						domAttr.set(item, "poster", posterUrl);
					}
					var url = '/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttToken&fdType=rtf&fdId=' + fdId;
					url = util.urlResolver(util.formatUrl(url,true));				
					request(url, { 
						handleAs: 'json',
						method: "GET",
						data: {},
					}).then(function(data){							
						if (data && data.token) {
							 var newSrc = '/sys/attachment/mobile/viewer/play.jsp?fdType=rtf&token=' + data.token;
							 newSrc = util.urlResolver(util.formatUrl(newSrc,true));				  
							 self.buildVideoSource(item, newSrc, src);
						}else{
							self.buildVideoSource(item, src);
						}
					 });
				}else{
					self.buildVideoSource(item, src);
				}
			}else{
				var sources = query('source', item);
				if(sources && sources.length > 0){
					var isGetUsefulUrl = false;
					array.forEach(sources, function(source, index) {
						src = domAttr.get(source, 'src');	
						var fdId = util.getUrlParameter(src, "fdId");
						if(srcReg.test(src) && fdId){
							var poster = domAttr.get(item, 'poster');		
							if(!poster){
								//视频封面图
								var posterUrl = '/sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&fdType=rtf&fdId=' + fdId;
								posterUrl =  util.urlResolver(util.formatUrl(posterUrl,true));
								domAttr.set(item, "poster", posterUrl);
							}
							var url = '/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttToken&fdType=rtf&fdId=' + fdId;
							url = util.urlResolver(util.formatUrl(url,true));				
							request(url, { 
								handleAs: 'json',
								method: "GET",
								data: {},
							}).then(function(data){							
								if (data && data.token) {									
									 var newSrc = '/sys/attachment/mobile/viewer/play.jsp?fdType=rtf&token=' + data.token;
									 newSrc = util.urlResolver(util.formatUrl(newSrc,true));									
									 if(!isGetUsefulUrl){											
										 self.buildVideoSource(item, newSrc, src);									
										 isGetUsefulUrl = true;
									 }								 
								}
							 });
						}else{
							if(!isGetUsefulUrl && src){
								 self.buildVideoSource(item, src);									
								 isGetUsefulUrl = true;
							}
						}
					});
				}
			}			
		},
		
		buildVideoSource: function(item, src, oldSrc){	
			if(item.pause){
				item.pause();
			}
			var width = domAttr.get(item,"width");	
			var poster = domAttr.get(item, "poster");		
			var newVideo = domConstruct.create('video', {
				innerHTML : '<source src="' + src
						+ '" type="video/mp4"></source>',
				style: 'max-width:100%;background:black;',	
				width: width,
				controls : true
			}, item, 'before');
		
			if(poster){
				domAttr.set(newVideo, "poster", poster);	
			}		
			if(oldSrc){
				domConstruct.create('source', {
					src: oldSrc,
					type: "video/mp4"
				}, newVideo);
			}
			// 销毁旧有video节点
			domConstruct.destroy(item);
		},

		resizeVideo : function(item) {

			var url = domAttr.get(item, 'flashvars');

			if (!url)
				return;

			url = util.getUrlParameter(url, "vdo");

			domConstruct.create('video', {
				innerHTML : '<source src="' + url
						+ '" type="video/mp4"></source>',
				style : 'max-width:100%',
				controls : true
			}, item, 'before');

			// 销毁旧有flash节点
			domConstruct.destroy(item);

		}

	});

});