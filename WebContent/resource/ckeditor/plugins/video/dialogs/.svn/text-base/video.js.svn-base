(function() {
	function b(a, b, c) {
		var k = n[this.id];		
		if(!c){
			c = {};
		}		
		if (k)
			for (var f = this instanceof CKEDITOR.ui.dialog.checkbox, e = 0; e < k.length; e++) {
				var d = k[e];								
				if(!d || !d.type){
					continue;
				}
				switch (d.type) {
					case g :
						if (!a)
							continue;
						if (null !== a.getAttribute(d.name)) {
							a = a.getAttribute(d.name);
							f ? this.setValue("true" == a.toLowerCase()) : this
									.setValue(a);
							return
						}
						f && this.setValue(!!d["default"]);
						break;
					case o :
						if (!a)
							continue;
						if (d.name in c) {
							a = c[d.name];						
							f ? this.setValue("true" == a.toLowerCase()) : this
									.setValue(a);
							return
						}
						f && this.setValue(!!d["default"]);
						break;
					case i :
						if (!b)
							continue;
						if (b.getAttribute(d.name)) {
							a = b.getAttribute(d.name);
							f ? this.setValue("true" == a.toLowerCase()) : this
									.setValue(a);
							return
						}
						f && this.setValue(!!d["default"])
				}
			}
	}
	function c(a, b, c) {
		if(!c){
			c = {};
		}
		var k = n[this.id];
		if (k)
			for (var f = "" === this.getValue(), e = this instanceof CKEDITOR.ui.dialog.checkbox, d = 0; d < k.length; d++) {
				var h = k[d];				
				switch (h.type) {
					case g :
						if (!a || "data" == h.name && b
								&& !a.hasAttribute("data"))
							continue;
						var l = this.getValue();
						f || e && l === h["default"] ? a
								.removeAttribute(h.name) : a.setAttribute(
								h.name, l);
						break;
					case o :
						if (!a)
							continue;
						l = this.getValue();
						if (f || e && l === h["default"])
							h.name in c && c[h.name].remove();
						else if (h.name in c)
							c[h.name].setAttribute("value", l);
						break;
					case i :
						if (!b)
							continue;
						if('src' == h.name && ('flv' == CKEDITOR.dialog.getCurrent().extName || 'mp4' == CKEDITOR.dialog.getCurrent().extName ) )
							continue;
						l = this.getValue();
						f || e && l === h["default"] ? b
								.removeAttribute(h.name) : b.setAttribute(
								h.name, l)
				}
			}
	}
	
	for (var g = 1, o = 2, i = 4, n = {
		id : [{
					type : g,
					name : "id"
				}],
		classid : [{
					type : g,
					name : "classid"
				}],
		codebase : [{
					type : g,
					name : "codebase"
				}],
		pluginspage : [{
					type : i,
					name : "pluginspage"
				}],
		src : [{
					type : o,
					name : "movie"
				}, {
					type : i,
					name : "src"
				},{
					type : g,
					name : "src"
				}],
		name : [{
					type : i,
					name : "name"
				}],
		align : [{
					type : g,
					name : "align"
				}],
		"class" : [{
					type : g,
					name : "class"
				}, {
					type : i,
					name : "class"
				}],
		width : [{
					type : g,
					name : "width"
				}, {
					type : i,
					name : "width"
				}],
		height : [{
					type : g,
					name : "height"
				}, {
					type : i,
					name : "height"
				}],
		hSpace : [{
					type : g,
					name : "hSpace"
				}, {
					type : i,
					name : "hSpace"
				}],
		vSpace : [{
					type : g,
					name : "vSpace"
				}, {
					type : i,
					name : "vSpace"
				}],
		style : [{
					type : g,
					name : "style"
				}, {
					type : i,
					name : "style"
				}],
		type : [{
					type : i,
					name : "type"
				}]
	}, m = "play loop menu quality scale salign wmode bgcolor base flashvars allowScriptAccess allowFullScreen"
			.split(" "), j = 0; j < m.length; j++)
		n[m[j]] = [{
					type : i,
					name : m[j]
				}, {
					type : o,
					name : m[j]
				}];
	m = ["allowFullScreen", "play", "loop", "menu"];
	for (j = 0; j < m.length; j++)
		n[m[j]][0]["default"] = n[m[j]][1]["default"] = !0;
	
	
	  var trim = CKEDITOR.tools.trim;

	  var unbind = function(video$) {
	    video$.onloadedmetadata = video$.onerror = null;
	  };

	  var cache = {}, getMetadata = function(dialog, readyCallback) {
	    var video$ = dialog.video.$;	 
	    unbind(video$);
	    var url = trim(dialog.getValueOf('info', 'src'));
	    if (!url.length) {
	      return false;
	    }
	    if (cache[url] !== undefined) {
	      return cache[url];
	    }
	    video$.onloadedmetadata = video$.onerror = function() {
	      unbind(video$);
	      var w = video$.videoWidth;
	      var h = video$.videoHeight;
	      cache[url] = w && h ? {width: w, height: h} : false;
	      readyCallback();
	    };
	    video$.src = url;
	  };

	  /**
	   * 清除video预览
	   */
	  var clearPreview = function(dialog) {
	    var video = dialog.video.setStyle('display', 'none');
	    var video$ = video.$;
	    unbind(video$);
	    video$.src = '';
	  };
	  
	  /**
	   * 判断video是否允许预览
	   */
	 var canPreViewH5Video = function(){
		 return false;
		 if(!!document.createElement('video').canPlayType){
			 return true;
		 }
		 return false;
	 }
	
	 /**
	  * 更新video预览
	  */
	var updatePreview = function(dialog) {
		if(canPreViewH5Video()){
		    var metadata = getMetadata(dialog, function() {
		      updatePreview(dialog);
		    });
		    var video = dialog.video;
		    if (metadata) {
		      dialog.commitContent(video);
		      var ratio = (100 * metadata.height / metadata.width).toFixed(5) + '%';
		      video.setStyle('display', 'block');
		    }
		    else {
		      video.setStyle('display', 'none');
		    }
		}
	  };
	
	CKEDITOR.dialog.add("video", function(a) {
		a.canPreViewH5Video = canPreViewH5Video();	
		var config = a.config;
		config.getFilebrowserVideoUploadUrl(a);	
		return {
			title : a.lang.video.title,
			minWidth : 420,
			minHeight : 310,
			onLoad: function() {			
				if(a.canPreViewH5Video){					
					this.video = this.getContentElement('info', 'preview').getElement().getNext().getFirst();		      
			        // Ensure browser does not ignore the muted attribute.
			        this.video.$.addEventListener('loadedmetadata', function(e) {
			          var video$ = e.target;
			          video$.muted = video$.hasAttribute('muted');
			        }, false);
				}								
		      },
			onShow : function() {			
				var element = this.getSelectedElement();	
				this.fakeImage = null;
				this.videoNode = null;
				k = new CKEDITOR.dom.element("video", a.document);	
		        if (element && element.data('cke-real-element-type') == 'video') {
		          var realElement = a.restoreRealElement(element);
		          this.fakeImage = element;		
		          this.videoNode = realElement;
		          this.setupContent(realElement, realElement);
		          updatePreview(this);
		        }
			},
			onOk : function() {
				var dialog = this;
				var width =  dialog.getValueOf('info', 'width');
			    var height = dialog.getValueOf('info', 'height');	
			    var src = dialog.getValueOf('info', 'src');
			    var realElement = null;
			    if(this.fakeImage){
			    	realElement = this.videoNode;
			    	dialog.commitContent(realElement);		
			    }else{
			    	realElement = CKEDITOR.dom.element.createFromHtml('<cke:video></cke:video>', a.document);								
					realElement.setStyles({
						background:'black'
					});
					realElement.setAttributes({
						controls: "controls",
						src: src
					});
					dialog.commitContent(realElement);	
			    }										
				var e = a.createFakeElement( realElement , "cke-video", "video", !0);	
				e.setAttributes({
					width: width,
					height: height
				});
				e.setStyles({
					width: width,
					height: height,
				});						 
				this.fakeImage ? (e.replace(this.fakeImage), a.getSelection()
						.selectElement(e)) : a.insertElement(e)				
				dialog.hide();
				return;
			},
			onHide : function() {
				this.video && clearPreview(this);
			},
			contents : [{
						id : "Upload",
						hidden : !a.config.VideoBrowser,
						filebrowser : "uploadButton",
						label : a.lang.common.upload,
						elements : [
							{
					            id: 'uploadTip',					          
					            type: 'html',
					            html: "<span style='font-weight:bold;color:red;'>" + a.lang.video.medieTypeLimitInfo + "</span>"
							},
						
							{
								type : "file",
								id : "NewFile",
								label : a.lang.common.upload,
								size : 38,								
								setup: function(){									
									var iframe = $("#"+this._.frameId);
									if(iframe){
										if(iframe.contents()){											
											var file = iframe.contents().find("input[name='NewFile']");												
											if(file){
												this.getDialog()["_newFile"] = file;										
												file.attr("accept", "video/mp4");
											}											
										}										
									}									
								},
								onClick: function(){
									var iframe = $("#"+this._.frameId);										
									if(iframe){							
										if(iframe.contents()){	
											var file = iframe.contents().find("input[name='NewFile']");												
											if(file){
												this.getDialog()["_newFile"] = file;										
												file.attr("accept","video/mp4");
											}											
										}										
									}		
								}
							}, {
								type : "fileButton",
								id : "uploadButton",
								label : a.lang.common.uploadSubmit,
								filebrowser : "info:src",
								"for" : ["Upload", "NewFile"],
								onClick : CheckUpload
							}]
					}, 					
					{
						id : "info",
						label : a.lang.common.generalTab,
						accessKey : "I",
						elements : [
							{
								type : "vbox",
								padding : 0,
								children : [{
									type : "hbox",
									widths : ["280px", "110px"],
									align : "right",
									children : [
										{
											id : "src",
											type : "text",
											label : a.lang.common.url,
											required : !0,
											validate : CKEDITOR.dialog.validate
													.notEmpty(a.lang.video.invalidSrc),
											setup : function(video){												
												if(video){
													var src = video.getAttribute("src");												
													if(src){
														this.setValue(src);
													}else{
														var sources = video.getElementsByTag("source");
														if(sources && sources.$ && sources.$.length > 0){
															for(var i=0; i < sources.$.length; i++){
																var source = sources.$[i];
																src = src = source.src;	
																if(source.dataset){
																	console.log(source.dataset);
																	src = source.dataset.ckeSavedSrc;	
																}
																															
																if(src){
																	this.setValue(src);
																	break;
																}
															}
														}
													}
												}
											},
											commit : function(video){												
												var oldSrc = video.getAttribute("src");		
												if(oldSrc){															
													video.setAttribute("src",this.getValue());												
												}else{
													var sources = video.getElementsByTag("source");	
													if(sources && sources.$ && sources.$.length > 0){					
														for(var i=0; i < sources.$.length; i++){
															var source = sources.$[i];																							
															source.src = this.getValue();
															if(source.dataset){
																source.dataset.ckeSavedSrc = this.getValue();
															}													
															break;
														}
													}else{
														video.setAttribute("src",this.getValue());
													}
												}
											}
//											,
//											onLoad : function() {
//												var a = this.getDialog(), b = function(
//														b) {													
//													k.setAttribute("src", b);
//												};
//												this.on("change", function(a) {
//															a.data && a.data.value
//																	&& b(a.data.value)
//														});
//												this.getInputElement().on("change",
//														function() {
//															b(this.getValue())
//														}, this)
//											}
										}, 
										{
											type : "button",
											id : "browse",
											filebrowser : "info:src",
											hidden : !0,//!a.canPreViewH5Video,
											style : "display:inline-block;margin-top:20px;",
											label : a.lang.video.preview,
											onClick: function(){	
												 var dialog = this.getDialog();
												 updatePreview(dialog);
											},
										}
									]
								}]
						},
//				        {
//							type : "hbox",
//							widths : ["25%", "25%", "25%", "25%"],
//							children : [															
//								 {
//							          id: 'muted',
//							          type: 'checkbox',
//							          label: a.lang.video.muted,
//							          onChange: function() {
//							            var dialog = this.getDialog();					            
//							            updatePreview(dialog);
//							          },
//							          setup: function(element) {
//							            this.setValue(!!(element && element.hasAttribute('muted')));
//							          },
//							          commit: function(element) {
//							            if (this.getValue()) {
//							              element.setAttributes({muted: 'muted'});
//							            }
//							            else {
//							              element.removeAttributes(['muted']);
//							            }
//							          }
//							      },
//							      {
//							          id: 'looping',
//							          type: 'checkbox',
//							          label: a.lang.video.looping,
//							          onChange: function() {
//								            var dialog = this.getDialog();						            
//								            updatePreview(dialog);
//							          },
//							          setup: function(element) {
//							        	  	this.setValue(!!(element && element.hasAttribute('loop')));
//							          },
//							          commit: function(element) {
//								            if (this.getValue()) {
//								              element.setAttributes({loop: 'loop'});
//								            } else {
//								              element.removeAttributes(['loop']);
//								            }
//							          }
//								    }							    
//							]
//						}, 		
						{
							type : "hbox",
							widths : ["25%", "25%", "25%", "25%", "25%"],
							children : [
								{
									type : "text",
									id : "width",
									requiredContent : "video[width]",
									style : "width:95px",
									label : a.lang.common.width,
									validate : CKEDITOR.dialog.validate
											.htmlLength(a.lang.common.invalidHtmlLength
													.replace("%1",
															a.lang.common.width)),
									setup : b,
									commit : c
								},
								{
										type : "text",
										id : "height",
										requiredContent : "video[height]",
										style : "width:95px",
										label : a.lang.common.height,
										validate : CKEDITOR.dialog.validate
														.htmlLength(a.lang.common.invalidHtmlLength.replace("%1",a.lang.common.height)),
										setup : b,
										commit : c
								 }
						    ]
						}//, 					
//					    {
//					            id: 'preview',
//					            hidden: !a.canPreViewH5Video,
//					            type: 'html',
//					            html: '<label>' + a.lang.video.preview + '</label><div class="cke_dialog_ui_labeled_content" style="position:relative;background-color:#f8f8f8;border:1px solid #d1d1d1;"><video controls preload="metadata" style="width:100%;height:100%;max-height:250px;display:none;"></video></div>'
//					    }
				   ]
			}]
		}
	})
})();

function OnUploadCompleted(errorNumber, fdId, customMsg, extName) {
	errorNumber += "";
	switch (errorNumber) {
		case "0" : 
			alert('超出最大限制');
			return;
		case "1" : 
			break;
		case "2" : 
			alert('文件类型错误,只允许上传MP4格式视频');
			return;
		case "3" :
			alert(customMsg);
			return;
		case "4" :
			alert(customMsg);
			return;
	}
	var dialog = CKEDITOR.dialog.getCurrent();
	var src = dialog._.editor.config.downloadUrl + '?fdId='+fdId;
	SetUrl(src, extName);
}

function SetUrl(url, extName) {
	var dialog = CKEDITOR.dialog.getCurrent();
	dialog.setValueOf('info', 'width', '600');
	dialog.setValueOf('info', 'height', '450');
	var url = url ? url : '';
	dialog.src=url;
	dialog.extName = extName?extName.toLowerCase():'';
	dialog.setValueOf('info', 'src', url);
	dialog.getButton('ok').onClick({
				data : {
					dialog : dialog
				}
			});
}

function getFileSize(obj){  
    var objValue = obj.value;  
    if (objValue=="") return ;  
    var fileLenth=-1;  
    try {  
        //对于IE判断要上传的文件的大小  
        var fso = new ActiveXObject("Scripting.FileSystemObject");  
        fileLenth=parseInt(fso.getFile(objValue).size);  
    } catch (e){  
        try{  
            //对于非IE获得要上传文件的大小  
             fileLenth=parseInt(obj.files[0].size);  
        }catch (e) {  
            fileLenth=-1;   
        }    
    }  
    return fileLenth;  
}  

function CheckUpload(evt) {
	var dialog = CKEDITOR.dialog.getCurrent();
	var sFile = dialog.getValueOf('Upload', 'NewFile');
	var fileSize = -1;
//	if(dialog["_newFile"]){
//		var fileElement = dialog["_newFile"][0];								
//		fileSize = getFileSize(fileElement);
//	}
	if (sFile.length == 0) {
		alert('请选择需要上传的文件');
		return false;
	}
	var oUploadAllowedExtRegex = new RegExp(
			CKconfig.VideoUploadAllowedExtensions, 'i');
	var oUploadDeniedExtRegex = new RegExp(
			CKconfig.VideoUploadDeniedExtensions, 'i');

	if ((CKconfig.VideoUploadAllowedExtensions.length > 0 && !oUploadAllowedExtRegex
			.test(sFile))
			|| (CKconfig.VideoUploadDeniedExtensions.length > 0 && oUploadDeniedExtRegex
					.test(sFile))) {
		OnUploadCompleted("2");
		return false;
	}
	return true;
}