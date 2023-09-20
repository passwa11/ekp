/*压缩类型：标准*/
Com_IncludeFile("tag.css",Com_Parameter.ContextPath+"sys/tag/resource/css/","css",true);
function IntellOpt(modelName,modelId,url,systemId,params){
	this.modelName = modelName;
	this.modelId = modelId;
	this.url = url;
	this.systemId = systemId;
	this.params = params;
	var self = this;
	this.onload = function(params){
		var intellTagUi = $("div#intell_tag_area");
		intellTagUi.hide();
		var container = $("div.intell_tag_view");
		container.empty();
		$.ajax({
					type : "post",
					url : url+ '/data/labc-biz-smarttag/contenttag/getContentTag',
					data : JSON.stringify({
						"kmId" : modelId,
						"kmModule" : modelName,
						"kmPlatFrom" : systemId
					}),
					xhrFields: {
			            withCredentials: true
			        },
					 contentType: "application/json; charset=utf-8",
					dataType : "json",
					success : function(data) {
						var tags ;
						if(data.data!=null && data.data.tabInfoList!=null){
							tags = data.data.tabInfoList;
						}						
						var rtn = [];
					
						if(tags !=null && tags.length >0){
							for (var i = 0; i < tags.length; i++) {
								var tag = tags[i].tabName;
								//处理转义字符
								var queryTag = tag.replace(/&lt;/g,"<")
								.replace(/&gt;/g,">")
								.replace(/&#034;/g,"'")
								.replace(/&amp;/g,"&")
								.replace(/&quot;/g,"\"")
								;
								rtn
										.push({
											text : tag
										})
							}
							
						}
						// 阅读页面标签自定义展现
						if (self.params['render']) {
							new Function('rtn', self.params['render']
									+ '(rtn)')(rtn);
							intellTagUi.hide();
							return;
						}
						
						
						for (var i = 0; i < rtn.length; i++) {
							if (rtn[i] != "") {
								var tagDom = $("<div/>");
								tagDom.addClass("tag_tagSign");
								if (rtn[i]['isSpecial'] == 1) {
									tagDom.addClass("tag_tagSignSpecial");
								}
								tagDom.html(rtn[i].text);
								container.append(tagDom);
							}
						}
						intellTagUi.show();
					}
				});
			//标签选择
			$("#intell_selectItem").click(function(params){
				try {					  				
					var intell_instance = CKEDITOR.instances["docContent"];
					if(intell_instance){
						var intell_getRtf =  intell_instance.getData();
						var intell_tag_content_div = parent.document.getElementById("intell_tag_content_id");
						if(intell_tag_content_div != null){
							intell_tag_content_div.innerHTML = intell_getRtf;
						}					
					}
				}
				catch(err){
					console.log(err);
				}
				var url = "/third/intell/ui/tag_frame.jsp";				
				var m = $("input[name='fdCatelogList[1]']")
				seajs.use(['lui/dialog'], function(dialog) {
					var dialogObj = dialog.build({
						config:{
							width: 900,
							height: 600,
							lock: true,
							cache: false,
							title : " ",
							content : {
								id : 'dialog_iframe',
								scroll : false,
								type : "iframe",
								url : url
							}
						}
					});
					dialogObj.content.on("layoutDone",function(){
						var iframe = dialogObj.content.iframeObj;
						if(iframe !=null && iframe.length>0){
							iframe[0].scrolling="no";
						}
						iframe.bind('load',function(){
							if (iframe[0].contentWindow.init){
								iframe[0].contentWindow.init(setting);	
							}
						});
					});
					dialogObj.show();
				});
/*				seajs.use(['lui/dialog','lui/jquery','lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog,$,env,lang,ui_lang) {
				dialog.iframe(url,
						lang["sysTag.choiceTag"], null, {
					content : {
						scroll : false
					},
					width : 900,
					height : 550
				});
				});*/
			});
			
		}
	}
