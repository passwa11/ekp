(function() {
	function b(a, b, c) {
		var k = n[this.id];
		if (k)
			for (var f = this instanceof CKEDITOR.ui.dialog.checkbox, e = 0; e < k.length; e++) {
				var d = k[e];
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
	CKEDITOR.dialog.add("flash", function(a) {
		var g = !a.config.flashEmbedTagOnly, i = a.config.flashAddEmbedTag
				|| a.config.flashEmbedTagOnly, k, f = '';
		return {
			title : a.lang.flash.title,
			minWidth : 420,
			minHeight : 310,
			onShow : function() {
				this.fakeImage = this.objectNode = this.embedNode = null;
				k = new CKEDITOR.dom.element("embed", a.document);
				var e = this.getSelectedElement();
				if (e && e.data("cke-real-element-type")
						&& "flash" == e.data("cke-real-element-type")) {
					this.fakeImage = e;
					var d = a.restoreRealElement(e), h = null, b = null, c = {};
					if ("cke:object" == d.getName()) {
						h = d;
						d = h.getElementsByTag("embed", "cke");
						0 < d.count() && (b = d.getItem(0));
						for (var d = h.getElementsByTag("param", "cke"), g = 0, i = d
								.count(); g < i; g++) {
							var f = d.getItem(g), j = f.getAttribute("name"), f = f
									.getAttribute("value");
							c[j] = f
						}
					} else
						"cke:embed" == d.getName() && (b = d);
					this.objectNode = h;
					this.embedNode = b;
					this.setupContent(h, b, c, e)
				}
			},
			onOk : function() {
				var e = null, d = null, b = null;
				if (this.fakeImage)
					e = this.embedNode;
				else if (g){
					var atts = {
							type : "application/x-shockwave-flash",
							pluginspage : "http://www.macromedia.com/go/getflashplayer"
					};
					if('flv' == this.extName || 'mp4' == this.extName){
						atts.flashvars = "?&autoplay=false&sound=70&buffer=2&vdo="+encodeURIComponent(this.src);
						CKEDITOR.dialog.getCurrent().setValueOf('info', 'src',  Com_Parameter.ContextPath+"sys/attachment/sys_att_main/video/gddflvplayer.swf");
					}
					e = CKEDITOR.dom.element.createFromHtml(
							"<cke:embed></cke:embed>", a.document); e
							.setAttributes(atts);
				}
				if (e)
					for (var b = {}, c = e.getElementsByTag("param", "cke"), f = 0, j = c
							.count(); f < j; f++)
						b[c.getItem(f).getAttribute("name")] = c.getItem(f);
				c = {};
				f = {};
				this.commitContent(e, d, b, c, f);
				e = a.createFakeElement(e || d, "cke_flash", "flash", !0);
				e.setAttributes(f);
				e.setStyles(c);
				this.fakeImage ? (e.replace(this.fakeImage), a.getSelection()
						.selectElement(e)) : a.insertElement(e)
			},
			onHide : function() {
				this.preview && this.preview.setHtml("")
			},
			contents : [{
						id : "Upload",
						hidden : !a.config.FlashBrowser,
						filebrowser : "uploadButton",
						label : a.lang.common.upload,
						elements : [{
									type : "file",
									id : "NewFile",
									label : a.lang.common.upload,
									size : 38
								}, {
									type : "fileButton",
									id : "uploadButton",
									label : a.lang.common.uploadSubmit,
									filebrowser : "info:src",
									"for" : ["Upload", "NewFile"],
									onClick : CheckUpload
								}]
					}, {
						id : "info",
						label : a.lang.common.generalTab,
						accessKey : "I",
						elements : [{
							type : "vbox",
							padding : 0,
							children : [{
								type : "hbox",
								widths : ["280px", "110px"],
								align : "right",
								children : [{
									id : "src",
									type : "text",
									label : a.lang.common.url,
									required : !0,
									validate : CKEDITOR.dialog.validate
											.notEmpty(a.lang.flash.validateSrc),
									setup : b,
									commit : c,
									onLoad : function() {
										var a = this.getDialog(), b = function(
												b) {
											k.setAttribute("src", b);
										};
										this.on("change", function(a) {
													a.data && a.data.value
															&& b(a.data.value)
												});
										this.getInputElement().on("change",
												function() {
													b(this.getValue())
												}, this)
									}
								}, {
									type : "button",
									id : "browse",
									filebrowser : "info:src",
									hidden : !0,
									style : "display:inline-block;margin-top:10px;",
									label : a.lang.common.browseServer
								}]
							}]
						}, {
							type : "hbox",
							widths : ["25%", "25%", "25%", "25%", "25%"],
							children : [{
								type : "text",
								id : "width",
								requiredContent : "embed[width]",
								style : "width:95px",
								label : a.lang.common.width,
								validate : CKEDITOR.dialog.validate
										.htmlLength(a.lang.common.invalidHtmlLength
												.replace("%1",
														a.lang.common.width)),
								setup : b,
								commit : c
							}, {
								type : "text",
								id : "height",
								requiredContent : "embed[height]",
								style : "width:95px",
								label : a.lang.common.height,
								validate : CKEDITOR.dialog.validate
										.htmlLength(a.lang.common.invalidHtmlLength
												.replace("%1",
														a.lang.common.height)),
								setup : b,
								commit : c
							}, {
								type : "text",
								id : "hSpace",
								requiredContent : "embed[hspace]",
								style : "width:95px",
								label : a.lang.flash.hSpace,
								validate : CKEDITOR.dialog.validate
										.integer(a.lang.flash.validateHSpace),
								setup : b,
								commit : c
							}, {
								type : "text",
								id : "vSpace",
								requiredContent : "embed[vspace]",
								style : "width:95px",
								label : a.lang.flash.vSpace,
								validate : CKEDITOR.dialog.validate
										.integer(a.lang.flash.validateVSpace),
								setup : b,
								commit : c
							}]
						}, {
							type : "vbox",
							children : [{
										type : "html",
										id : "preview",
										style : "width:95%;",
										html : f
									}]
						}]
					}]
		}
	})
})();

function OnUploadCompleted(errorNumber, fdId, customMsg, extName) {
	switch (errorNumber) {
		case "0" : 
			alert('超出最大限制');
			return;
		case "1" : 
			break;
		case "2" : 
			alert('文件类型错误');
			return;
		case "3" :
			alert(customMsg);
			return;
		case "4" :
			alert(customMsg);
			return;
	}
	var dialog = CKEDITOR.dialog.getCurrent();
	SetUrl(dialog._.editor.config.downloadUrl
			+ '?fdId='+fdId, extName);
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

function CheckUpload(evt) {
	var dialog = CKEDITOR.dialog.getCurrent();
	var sFile = dialog.getValueOf('Upload', 'NewFile');

	if (sFile.length == 0) {
		alert('请选择需要上传的文件');
		return false;
	}
	var oUploadAllowedExtRegex = new RegExp(
			CKconfig.FlashUploadAllowedExtensions, 'i');
	var oUploadDeniedExtRegex = new RegExp(
			CKconfig.FlashUploadDeniedExtensions, 'i');

	if ((CKconfig.FlashUploadAllowedExtensions.length > 0 && !oUploadAllowedExtRegex
			.test(sFile))
			|| (CKconfig.FlashUploadDeniedExtensions.length > 0 && oUploadDeniedExtRegex
					.test(sFile))) {
		OnUploadCompleted("2");
		return false;
	}
	return true;
}