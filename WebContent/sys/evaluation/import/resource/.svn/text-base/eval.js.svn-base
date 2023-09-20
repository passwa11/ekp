/*压缩类型：标准*/
Com_IncludeFile("jquery.js|calendar.js|security.js");
Com_IncludeFile("swf_attachment.js?mode=edit", Com_Parameter.ContextPath + "sys/attachment/js/","js",true);

if(!String.prototype.hasOwnProperty("replaceAll")){
	/**
	 * 替换所有匹配的字符
	 * @param matchStr
	 * @param replaceStr
	 * @param isReplaceReg（是否转译正则符号）
	 * @return {*}
	 */
	String.prototype.replaceAll = function (matchStr,replaceStr, isReplaceReg){
		if(isReplaceReg){
			//转译匹配字符中的正则符号
			matchStr = matchStr.replace(/\[/gm,"\\[")
				.replace(/\]/gm,"\\]")
				.replace(/\{/gm,"\\{")
				.replace(/\}/gm,"\\}")
				.replace(/\(/gm,"\\(")
				.replace(/\)/gm,"\\)")
				.replace(/\^/gm,"\\^")
				.replace(/\$/gm,"\\$")
				.replace(/\|/gm,"\\|")
				.replace(/\?/gm,"\\?")
				.replace(/\*/gm,"\\*")
				.replace(/\+/gm,"\\+")
				.replace(/\./gm,"\\.")
			console.log("matchStr",matchStr);
		}
		return this.replace(new RegExp(matchStr,"gm"),replaceStr);
	}
}
function EvaluationOpt(modelName , modelId , key, langCfg, fdIsCommented, attModelId ,fdKey){
	
	this.attModelName = "com.landray.kmss.sys.evaluation.model.SysEvaluationMain";
	this.attModelId = attModelId || "";
	this.attKeyPrefix = "eval_";
	this.langCfg = langCfg;
	this.modelName = modelName;
	this.modelId = modelId;
	this.key = key;
	this.fdKey=fdKey;
	this.fdIsCommented = fdIsCommented;
	this.S_EditAreaId = "eval_EditMain"; 
	this.S_SaveUrl = Com_Parameter.ContextPath + "sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=save";
	this.S_Eval_DeleteUrl = Com_Parameter.ContextPath +"sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=delete&fdId=!{fdId}&fdModelName="
						+ this.modelName + "&fdModelId=" + this.modelId;
	this.S_GetScoreUrl = Com_Parameter.ContextPath + "sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=score&fdModelName="
						+ this.modelName + "&fdModelId=" + this.modelId;
	this.S_Notes_DeleteUrl = Com_Parameter.ContextPath +"sys/evaluation/sys_evaluation_main/sysEvaluationNotes.do?method=delete&fdId=!{fdId}&fdModelName="
						+ this.modelName + "&fdModelId=" + this.modelId;
	this.EvalBqActiveClass = 'eval_bq_active';
	this.EvalBqCfg = {
				"face":{
					"activeClass":'eval_bq_active',
					"tabId":"eval_bq_system",
					"bqName":"系统表情",
					"width":24,
					"height":24,
					"container":"eval_bg_system_container",
					"row":5,
					"col":17,
					"start":0,
					"max":79,
					"type":"face",
					"suffix":".gif",	
					"path":Com_Parameter.ContextPath+"sys/evaluation/import/resource/images/bq/"
				},"dnyling":{
					"tabId":"eval_bq_dny_ling",
					"bqName":"动态凌凌",
					"width":60,
					"height":60,
					"container":"eval_bg_dny_ling_container",
					"isHide":true,
					"row":5,
					"col":8,
					"start":1,
					"max":32,
					"type":"dnyling",
					"suffix":".gif",
					"path": Com_Parameter.ContextPath+"sys/ui/resource/images/phiz/dnyling/"
				},"lingling":{
					"tabId":"eval_bq_ling_ling",
					"bqName":"凌凌表情",
					"width":60,
					"height":60,
					"container":"eval_bg_ling_ling_container",
					"isHide":true,
					"row":5,
					"col":8,
					"start":1,
					"max":33,
					"type":"lingling",
					"suffix":".png",
					"path": Com_Parameter.ContextPath+"sys/ui/resource/images/phiz/lingling/"
				}
			};
	
	 this.initEvalBqContentParseConfig = function(EvalBqCfg){
		for(var key in EvalBqCfg){
			var faceCfg = EvalBqCfg[key];
			faceCfg["configs"]=[];
			for(var i= faceCfg.start;i<=faceCfg.max;i++){
				faceCfg['configs'][i] ="["+faceCfg.type+i+"]";
			}
			faceCfg.replace = function(content){
				if(!content || content.length == 0){
					return '';
				}
				for(var i = this.start; i<=this.max;i++){
					content = content.replaceAll(this["configs"][i], "<img width='"+this.width+"' src='"+this.path+i+this.suffix +"' type='"+this.type+"' />",true);
				}
				content = content.replace(/&amp;nbsp;/g, ''); 
				content = content.replace(/&lt;div&gt;/g, '<br>');
				content = content.replace(/&lt;\/div&gt;/g, '');
				return content;
			}
		}
	}(this.EvalBqCfg);
	
	var self = this;
	/*************************以下是外部调用函数********************************************/
	//content显示时，编辑界面加载
	this.onload = function(){
		var editFlag = $('#' + self.S_EditAreaId);
		if(editFlag.length>0){
			//评价等级初始化
			var liArr = editFlag.find('.eval_star li');
			liArr.click(function(){
				var liObj = $(this);
				var val = liObj.attr("star");
				self._eval_starChange(parseInt(val, 10),true);
			});
			
			liArr.mouseover(function(){
				var liObj = $(this);
				var val = liObj.attr("star");
				self._eval_starChange(parseInt(val, 10),false);
			});
	
			liArr.mouseout(function(){
				var val = $('select[name="fdEvaluationScore"]').val();
				self._eval_starChange(parseInt(val, 10),false);
			});
			
		
			//提交按钮事件初始化
			$("#eval_button").click(function(){
				self._eval_submitData();
			});
			self._eval_enabledBtn(true);
		}
		//移动位置，修复没有点评权限的情况下不能进行点评删除的问题
		//删除点评按钮事件初始化
		$("#eval_ViewMain").delegate('span.eval_delopt','click',function(){
			var thisObj = $(this); 
			seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
				dialog.confirm(eval_lang['eval_prompt_del_confirm'],function(value){
					if(value==true){
						self.loading = dialog.loading(null,$("#" + self.S_EditAreaId).parent());
						var id = thisObj.attr('eval_id_flag');
						var modelName = thisObj.attr("eval-view-modelname");
						var evalMain = "com.landray.kmss.sys.evaluation.model.SysEvaluationMain";
						var evalNotes = "com.landray.kmss.sys.evaluation.model.SysEvaluationNotes";
						if(id!="" && id !=null){
							var url;
							if(modelName == evalMain){
								var eval_parent_id =  thisObj.attr("eval_parent_id");
								var  eval_evaluator_id = thisObj.attr("eval_evaluator_id");
								var isDelMyTop = !eval_parent_id && (Com_Parameter.CurrentUserId === eval_evaluator_id);
								var multiTop = false;//旧数据
								if(window.LUI && window.LUI("my_view")) {
									var datas = window.LUI("my_view")._data.datas;
									if(datas && datas.length > 1) {
										multiTop = true;
									}
								}
								url = self.S_Eval_DeleteUrl.replace(/!{fdId}/gi,id);//删除Action要求必须使用get方式请求
								$.get(url,{},
										function(data) {
											var numChange = false;
											if(isDelMyTop && !multiTop) {
												self._setIsCommented(false);
											} 
											if(isDelMyTop) {
												numChange = true;
											}
											self._eval_afterDelSubmit(data, numChange);
										}
									);
							}else if(modelName == evalNotes){
								url = self.S_Notes_DeleteUrl.replace(/!{fdId}/gi,id);//删除Action要求必须使用get方式请求
								$.get(url,{},self._notes_afterDelSubmit);
								//更新文档内容，除去img-dom
								self.deleteNotesIcon.func(id);
							}
						}
						thisObj.hide();
					}
				});
			});
		});
		//删除连接显示
		$("#eval_ViewMain").delegate('.eval_record_msg','mouseover mouseout', function(event) {
				  if (event.type == 'mouseover') {
					  $(this).find(".eval_delopt").show();
				  } else {
					  $(this).find(".eval_delopt").hide();
				  }
		});
		
		//点评回复
		$("#eval_ViewMain").delegate('.eval_reply','click',function(){
				var replyId= $(this).attr("eval_reply_id");
				var showReplyInfo = $("input[name='showReplyInfo']")[0].value;
				if(showReplyInfo!='false'){
					var obj = $("#replys_" +replyId);
					if(obj.is(":hidden")){
						obj.slideDown('slow');
						//将回复信息向页面顶部展开
						var replyTop = $(this).offset().top;
						$('html,body').animate( {
							scrollTop : replyTop - 90
						}, 200);
					}else{
						obj.slideUp('slow');
					}
				}else{
					self.listReply(replyId);
				}
			}
		);
		
		$("#eval_ViewMain").delegate('.inputText','focus',function(evt){
			var _evalMsg = $(this).parents('.eval_reply_infos');
			if(_evalMsg.length>0){
				var replyId = $(this).parents('.eval_reply_infos')[0].id;
				$("#reply_content").each(function(){
					$(this).parent().html("<input class='inputText' type='text' value='" +
											eval_lang['eval_prompt_reply_tips']+"'></div>");
				});
				
			 	$(this).parent().empty();
				self.appendReplyBox(replyId);
			}
		});
		//删除回复
		$("#eval_ViewMain").delegate('.eval_re_del','click',function(){
			var replyRecord = $(this).parents(".reply_li");
			var evalId = replyRecord.attr('eval_id');//点评id
			var replyId = replyRecord.attr('reply_id');//回复id
			var modelName = $("#eval_txt_"+evalId+" .eval_praise_reply").attr("eval-view-modelname");
			seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
				dialog.confirm(eval_lang['eval_prompt_del_confirm'],function(value){
					if(value==true){
						$.ajax({
							url : Com_Parameter.ContextPath + "sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=delete",
							type : 'post',
							cache : false,
							data: "fdModelName=" +  eval_opt.modelName 
							   + "&fdModelId=" + eval_opt.modelId //fdModelName和fdModelId是权限校验需要
							   + "&evalModelName=" + modelName
						       + "&replyId=" + replyId
						       +  "&evalId=" + evalId
						       + "&isDelReply=true",
							success : function(data) { 
									self.getReplyInfo(evalId);//刷新回复记录
									dialog.success(eval_lang['eval_prompt_sucess_del']);
								},
							error: function() {
								dialog.failure(eval_lang['eval_prompt_fail_del']);
							}
						});
					}
				});
			});
		});
		
		//查看对话
		$("#eval_ViewMain").delegate('.eval_re_dialogue','click',function(){
				$("body").attr("style","overFlow-y:hidden;");//父窗口禁掉滚动条
				var replyId = $(this).attr("re_dialog_id");
				var evalId = $(this).closest("li").attr("eval_id");
				var evalModelName = $("#eval_txt_"+evalId+" .eval_praise_reply").attr("eval-view-modelname");//全文点评或段落点评modelName
				seajs.use(['lui/dialog','lui/topic'],function(dialog, topic) {
					dialog.iframe("/sys/evaluation/sys_evaluation_main/sysEvaluationReply.do?method=getReplyDoalogs&evalId=" + 
									evalId + "&replyId=" + replyId + "&evalModelName=" + evalModelName, 
									eval_lang['eval_prompt_read_dialog2'],
									function(){
										$("body").attr("style","overFlow-y:auto;");
									}, 
									 {	
										width:700,
										height:500
									}
					);
				});
				
		});
		
		//回复特定人的回复
		$("#eval_ViewMain").delegate('.eval_re_reply','click',function(){
			var replyObj = $(this).parents("#reply_li");
			var replyBox = replyObj.find("div[class='reply_cont2']");
			if(replyBox.length>0){
				if(replyBox.is(":hidden")){
					replyBox.slideDown('slow');
				}else{
					replyBox.slideUp('slow');
				}
			}else{
				$(".reply_cont2").remove();
				var evalId = replyObj.attr('eval_id');//点评id
				var replyId = replyObj.attr('reply_id');//回复ideval_mark
				
				replyObj.append("<div class='reply_cont2' id='reply_content'>" +
												"<iframe name='eval_textarea' frameborder='0' class='eval_textarea2' id='subIfr_"+evalId+"' src='" + 
												Com_Parameter.ContextPath + "sys/evaluation/import/sysEvaluationMain_content_area.jsp?iframeType=reply&iframeId=subIfr_"+evalId+"'/>"+
												"<p class='eval_reply_submit'></p><span class='eval_icons' data-iframe-uid='subIfr_"+evalId+"'>"+eval_lang['eval_prompt_icon_smile'] +
												"</span><div class='eval_biaoqing eval_biaoqing_"+evalId+"'></div><div class='eval_btn_submit' id='eBtn_"+evalId+"' eval_mark='1' eval_id='"+ evalId +
												"'></div><span class='eval_reply_tip'>" + eval_lang['eval_prompt_words_alert1'] +
												"<font style='font-family: Constantia, Georgia; font-size: 20px;'>1000</font>" + 
												eval_lang['eval_prompt_words_alert3']+"</span></div>");
				replyObj.find("div[class='reply_cont2']").slideDown('slow');
				//回复按钮
				seajs.use(['lui/toolbar'],function(tool){
					var _cfg = {
							text: eval_lang['eval_prompt_eval_reply'],
							id: 'replyBtn_'+evalId,
							click: "eval_opt._evalSubmit(this)"
						}; 
					var replyBtn = tool.buildButton(_cfg);
					var t1 = $("#eBtn_" + evalId).html(); 
					t1 = t1.replace(/[\r\n]/g,"").replace(/[ ]/g,"");
					replyBtn.element.attr("data-eval-eid","eval-eid");
					if(t1== ''){
						$("#eBtn_" + evalId).append(replyBtn.element.css({
							width : '70px',
							float : 'right'
						}));
					}
					replyBtn.draw();
				});
				
				var evalAuthor = $("li[reply_id='"+replyId+"'] #fist_auth").text();
				var replyTip = eval_lang['eval_prompt_eval_reply']+evalAuthor+":";
				$(".eval_textarea2").unbind();//取消文本框事件绑定
				$(".eval_textarea2").val(replyTip);
				$(".eval_textarea2").css({'color':'#8a8a8a'});
				//文本框绑定事件
				$(".eval_textarea2").bind({
					"focus":function(){
						var replyContent = $.trim($(this).val());
						if(replyContent == replyTip){
							$(this).val('');
							$(this).css({'color':''});
						}
					},
					"blur":function(){
						var replyContent = $.trim($(this).val());
						if(replyContent == ''){
							$(this).val(replyTip);
							$(this).css({'color':'#8a8a8a'});
						}
					}
				});
			}
		});
		
		//表情
		$("#eval_ViewMain").parent().delegate('.eval_icons','click',function(){
			$(this).parents(".lui_tabpage_float_content").css({
				"position":"relative",
				"z-index":1
			});
			self.openBqBox($(this));
		});
		
		//表情选择
		$("#eval_ViewMain").parent().delegate('#bqIcon_select','click',function(){
			var iframeId = $(this).attr("lui-eval-iframe-id");
			var iframeObj = $("#"+iframeId);
			self.selectIcon(this,iframeObj);
		});
		//表情切换页签
		$("#eval_ViewMain").parent().delegate('.eval_bq_face_tab','click', function(){
			self.openBqTab(this);
		});
		
		//关闭表情框
		$("#eval_ViewMain").parent().on("click",".eval_bq_close", function (evt) {
			$(this).parents('.eval_biaoqing').hide();
		});
		
		var isEva = $("#attachment_eva").length > 0;
		if(isEva) {
			seajs.use("sys/evaluation/import/resource/sysAttMainSwf.js", function(sysAttMainSwf) {
				var attObj = new  sysAttMainSwf.SysAttMainWidget({
					element : "#attachment_eva",
					modelName : self.attModelName,
					modelId : self.attModelId,
					fdKey : self.attKeyPrefix + self.attModelId
				});
				
				attObj.startup();
				attObj.draw();
				
				//初始化附件
				self.attObj = attObj;
			});
		}
		
		
		
	};

	this.openBqTab = function(evt){
		var parentObj = $(evt).parent();
		var type = $(evt).attr('type');
		var textId =$(evt).attr('data-text-id');
		if(!type) return ;
		parentObj.find('.eval_bq').hide();
		parentObj.find('.eval_bq_face_tab').removeClass(self.EvalBqActiveClass);
		for(var key in self.EvalBqCfg){
			faceCfg = self.EvalBqCfg[key];
			var containerId = faceCfg.container;
			var reTabId = faceCfg.tabId;
			if(textId){
				reTabId = self.genIdByTextId(reTabId, textId);
				containerId = self.genIdByTextId(containerId, textId);
			}

			if(type == key){
				parentObj.find("#"+containerId).show();
				parentObj.find("#"+reTabId).addClass(self.EvalBqActiveClass);
			}
		}
	}
	//表情列表框
	this.openBqBox = function(evt){
		var textId = evt.attr("data-iframe-uid");//文本框id
		var reTextId = '';
		var iframeId = 'mainIframe';
		if(textId){
			var arrs = textId.split("_");
			if(arrs && arrs.length == 2){
				reTextId = arrs[1];
			}else if(arrs && arrs.length == 3){
				reTextId = arrs[2];
			}
			iframeId = textId;
		}
		var bqHtml = evt.next("div")[0].innerHTML;
		var showBq = true;
		if(bqHtml != ''){
			showBq = false;
		}
		
		var bodyHeight = document.body.clientHeight;
		var setHeight = evt.offset().top;
		var bottomHegiht = bodyHeight - setHeight;
		
		var findMatch = "div.eval_biaoqing";
		if(reTextId){
			findMatch = "div.eval_biaoqing_"+reTextId;
		}
		var biaoqing = evt.parent().find(findMatch);//关闭所有表情框
		
		if(!showBq)
			biaoqing.show();
		else {
			var bqHTML = "";
			for(var key in this.EvalBqCfg){
				var faceCfg = this.EvalBqCfg[key];
				bqHTML += this.crateFace(reTextId,faceCfg, iframeId);
			}
			var bqDialog = "<div class='eval_biao_qing' id='eval_biao_qing'>";
			for(var key in this.EvalBqCfg){
				var faceCfg = this.EvalBqCfg[key];
				var reTabId = faceCfg.tabId;
				if(reTextId){
					reTabId = self.genIdByTextId(reTabId,reTextId);
				}
				bqDialog+="<div data-text-id='"+reTextId+"' type='"+faceCfg.type+"' " +
					"class='"+faceCfg.tabId+" eval_bq_face_tab "+(faceCfg.activeClass != undefined ?faceCfg.activeClass:'')
					+"'" +" id='"+reTabId+"'>"+faceCfg.bqName+"</div>";
			}
			bqDialog+="<div class='eval_bq_close' id='eval_close_bq'></div>"+ bqHTML+"</div>";
			evt.parent().parent().css("position","relative");
			evt.parent().find(findMatch).html(bqDialog);
			evt.parent().find("div.eval_biaoqing").css({
				"position": "absolute",
				"width": "550px"
			});
			if(bottomHegiht < 260){
				evt.parent().find("div.eval_biaoqing").css("top","-60px");
			}
			var contentIframe = evt.parents("body").find("iframe[id='"+textId+"']");
			var bqParentWidth = contentIframe.width(); 
			var bqBox = evt.parent().find("div.eval_biaoqing");
			var bqBoxWidth = bqBox.width();
			var textAreaBody = contentIframe[0].contentWindow.document.body;
			var evalTxt = textAreaBody.innerHTML;
			evalTxt = $.trim(evalTxt);
			var isChangeColor = false;
			for(var i=0;i<5;i++){
				if(evalTxt == self.langCfg['eval_star_'+i] || evalTxt==''){
					textAreaBody.innerHTML = '';//当点评框中有提示语时，清除提示语
					textAreaBody.focus();
					break;
				}
			}
			//if (!!window.ActiveXObject || "ActiveXObject" in window) {//IE浏览器
			if(!!window.ActiveXObject || "ActiveXObject" in window){
				if(__range__ == null){
					var selection = contentIframe[0].contentWindow.document.selection;
					if(selection){
						__range__ = contentIframe[0].contentWindow.document.selection.createRange();
					}
					
				}
			}
		}
	}
	//创建表情
	this.crateFace = function(textId,faceCfg, iframeId){
		if(!faceCfg) return ;
		var isHide = faceCfg.isHide != undefined && faceCfg.isHide==true? true :false;
		var containerId = self.genIdByTextId(faceCfg.container, textId);
		var faceHTML = "<div class='eval_bq' id='"+containerId+"' style='"+(isHide?'display:none':'')+"'>"
		var num = faceCfg.start;
		for(var i=0; i < faceCfg.row;i++){
			faceHTML+="<ul>";
			
			for(var j=0; j < faceCfg.col;j++){
				if(faceCfg.max != -1 && num > faceCfg.max){
					break;
				}
				faceHTML += '<li id="'+faceCfg.type+'_" class="lui_eval_biaoqing" style="padding:0px 0px !important;">' +
				'<a id="bqIcon_select" lui-eval-iframe-id="'+iframeId+'" type="'+faceCfg.type+'">'+
				'<img src="' +faceCfg.path + num+faceCfg.suffix+ 
				'" width="'+faceCfg.width+'" style="margin: 4px;" type="'+faceCfg.type+'"></img></a></li>';
				num++;
			}
			faceHTML+="</ul>";
		}
		if("face" == faceCfg.type){
			faceHTML+="<div class='eval_bq_arrow'></div>";
		}
		return faceHTML + "</div>";
	}

	this.genIdByTextId = function(orig, textId){
		if(textId && textId.trim().length > 0){
			return orig+"_"+textId;
		}
		return orig;
	}
	
	//展开回复
	this.listReply = function(evalId){
			var thisObj = $("#"+evalId);
			var replyObject = $("#replys_"+evalId);
			if(replyObject.length>0){
				if(replyObject.is(":hidden")){
					replyObject.slideDown('slow');
				}else{
					replyObject.slideUp("slow");
				}
			}else{
				var showReplyInfo = $("input[name='showReplyInfo']")[0].value;
				self.showReply(evalId);
				if(showReplyInfo=='false'&&$("#replys_"+evalId).length>0){
					$("#replys_"+evalId).slideDown('slow');
					}
			}
	};
	
	this.appendReplyBox = function(fdEvaluationId){
		var replyCont = $("#replys_"+fdEvaluationId).find("div[class='reply_content']");
		if(replyCont[0] && replyCont[0].innerHTML == ""){
			var _replyHtml = "<div id='reply_content' class='replyIframe'><span class='trig'></span>" +
							"<iframe name='eval_textarea' frameborder='0' class='eval_textarea' id='ctn_"+fdEvaluationId+"' src='" + 
							Com_Parameter.ContextPath + "sys/evaluation/import/sysEvaluationMain_content_area.jsp?iframeType=reply&iframeId=ctn_"+fdEvaluationId+"'/>"+
							"<p class='eval_reply_submit'></p><span class='eval_icons' data-iframe-uid='ctn_"+fdEvaluationId+"'>" + eval_lang['eval_prompt_icon_smile'] + 
							"</span><div class='eval_biaoqing eval_biaoqing_"+fdEvaluationId+"'></div><div class='eval_btn_submit' id='rBtn_" + fdEvaluationId +
							"' eval_mark='0' eval_id='"+ fdEvaluationId +
							"'></div><span class='eval_reply_tip'>"+eval_lang['eval_prompt_words_alert1'] + 
							"<font style='font-family: Constantia, Georgia; font-size: 20px;'>1000</font>" + 
							eval_lang['eval_prompt_words_alert3'] + "</span></div>";
			
			replyCont.append(_replyHtml);
			
			//评论按钮
			seajs.use(['lui/toolbar'],function(tool){
				var _cfg = {
						text: eval_lang['eval_prompt_eval_reply'],
						id: 'evalBtn_'+fdEvaluationId,
						click: "eval_opt._evalSubmit(this)"
					};
				var replyBtn = tool.buildButton(_cfg);
				var t1 = $("#rBtn_" + fdEvaluationId).html(); 
				t1 = t1.replace(/[\r\n]/g,"").replace(/[ ]/g,"");
				replyBtn.element.attr("data-eval-eid","eval-eid");
				if(t1== ''){
					$("#rBtn_" + fdEvaluationId).append(replyBtn.element.css({
						width : '70px',
						float : 'right'
					}));
				}
				replyBtn.draw();
			});
		}
	}
	
	
	//点评回复信息
	this.showReply = function(fdEvaluationId){
		//是否有点评回复权限
		var hasReplyRight = $("input[name='hasReplyRight']").val();
		var replyHtml = "<div id='replys_" + fdEvaluationId +"' class='eval_replys'>";
		
		if(typeof(hasReplyRight) != "undefined" && hasReplyRight  == "true"){
			var _reply = "<div class='reply_content' id='reply_li' eval_id='" + fdEvaluationId 
						+ "'><input class='inputText' type='text' value='" 
						+ eval_lang['eval_prompt_reply_tips'] + "'></div>";
			replyHtml += _reply;
		}
		replyHtml += "<div id='record_"+fdEvaluationId+"'></div></div>";
		$("#"+fdEvaluationId+" #reply_cont_box").html(replyHtml);
		self.getReplyInfo(fdEvaluationId);
	};
	
	this.getReplyInfo = function(fdEvaluationId) {
		$.ajax({
			url : Com_Parameter.ContextPath + "sys/evaluation/sys_evaluation_main/sysEvaluationReply.do",
			data : {"method": "listReplyInfo","fdEvaluationId":fdEvaluationId,"orderby":"fdReplyTime"},
			type : 'post',
			cache : false,
			success : function(data) {
					var replyInfo = data['replyInfo'];
					var recordHtml;
					if(replyInfo!=null && replyInfo.length>0){
						recordHtml = "<span class='trig'></span><div class='eval_reply_info'>";
						for(var i=0;i<replyInfo.length;i++){
							recordHtml += "<ul><li id='reply_li' class='reply_li' eval_id='"+fdEvaluationId+"' reply_id='"+replyInfo[i].replyId+"'><img class='rtnPerson' src='" + 
											replyInfo[i].imgUrl+"'>" + "<div class='eval_re_cont'><span style='float:left;' class='eval_reply_author'><a class='com_author' href='" + 
											Com_Parameter.ContextPath + "sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" + 
											replyInfo[i].replyerId + "' target='_blank'>" + replyInfo[i].replyerName + "</a>";
							if(replyInfo[i].parentReplayerName != ""){
								recordHtml += "<span class='eval_reply_p'>"+eval_lang['eval_prompt_eval_reply']+"</span><a class='com_author' href='" + 
											Com_Parameter.ContextPath + "sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" + 
											replyInfo[i].parentReplyerId + "' target='_blank'>" + replyInfo[i].parentReplyerName + "</a>";
							}
							var SEP = "__$@$__";
							var replyCont = replyInfo[i].replyContent;
							var base64Patt = /(<img src="data:image[\S]+"\/>)/g;
							var matchResult = replyCont.match(base64Patt);
							if(matchResult && matchResult.length > 0){
								replyCont = replyCont.replace(base64Patt,SEP);
							}
							var fn = function(m, p1) {
								return  "\u4241\u5345\u3634{" + encodeURIComponent(p1) + "}";
							};
							replyCont = replyCont.replace(/(<img.*data-type.*>)/gi, fn);
							
							// 这里转义后移动端复制的RTF内容会显示源码，所以先不转义
							//replyCont = Com_HtmlEscape(replyCont);
							
							for(var key in  self.EvalBqCfg){
								var faceCfg = self.EvalBqCfg[key];
								if(faceCfg.replace){
									replyCont = faceCfg.replace(replyCont);
								}
							}
							
							//replyCont = replyCont.replace(/\[face/g,'<img src="' + 
											//Com_Parameter.ContextPath + 'sys/evaluation/import/resource/images/bq/')
											//.replace(/\]/g,'.gif" type="face">');
							//replyCont = replyCont.replace(/&amp;nbsp;/g, ' '); 
							//replyCont = replyCont.replace(/&lt;div&gt;/g, '<br>'); 
							//replyCont = replyCont.replace(/&lt;\/div&gt;/g, ''); 
							var fndes = function(m, p1) {
								return   decodeURIComponent(p1);
							};
							replyCont = replyCont.replace(/\u4241\u5345\u3634\{(.*)\}/gi, fndes);
							var strs = replyCont.split(SEP);
							
							if(null != matchResult){
								replyCont = "";
								for(var k = 0; k< matchResult.length;k++){
									replyCont+=strs[k]+matchResult[k];
								}
							}
						
							
							recordHtml += "<span class='replyers_dot'>：</span></span><div class='linefeed_desc'>" + replyCont +
										"</div></div><p class='eval_re_time'>"  + replyInfo[i].replyTime +"<span>";
							if(replyInfo[i].parentReplayerName != ""){
								recordHtml += "<a class='eval_re_dialogue' re_dialog_id='"+replyInfo[i].replyId+"'>"+eval_lang['eval_prompt_read_dialog']+"</a>";
							}
							//是否有删除回复权限
							var delReplyRight = $("input[name='delReplyRight']").val();
							if(delReplyRight && delReplyRight  == "true"){
								recordHtml += "<a class='eval_re_del' >"+eval_lang['eval_prompt_eval_delete']+"</a>";
							}
							
							//是否有点评回复权限
							var hasReplyRight = $("input[name='hasReplyRight']").val();
							var replyRight = typeof(hasReplyRight) != "undefined" && hasReplyRight  == "true";
							var currentUserId = replyInfo[i].currentUserId;//当前用户id
							//权限过滤，当前用户不能回复自己的回复
							if(currentUserId != replyInfo[i].replyerId && replyRight){
								recordHtml += "<a class='eval_re_reply' title='" + eval_lang['eval_prompt_eval_reply2'] 
								              + "'>"+eval_lang['eval_prompt_eval_reply2']+"</a>";
							}
							recordHtml += "</span></p></li></ul>";
						}
						recordHtml += "</div>";
						$("#record_"+fdEvaluationId).html(recordHtml);
						//下拉回复信息
						$("#replys_"+fdEvaluationId).slideDown('slow');
					}else{
						$("#record_"+fdEvaluationId).html("");
					}
					//更新回复数
					$("#reCount_"+fdEvaluationId).text(replyInfo.length);
					//更新按钮样式
					var $replyBtn = $("a[eval_reply_id='"+fdEvaluationId+"']");
					if(replyInfo.length==0){
						$replyBtn.attr("class","eval_reply reply_off");
					}else {
						$replyBtn.attr("class","eval_reply");
					}
			}
		});
	};
	
	//对应提交成功后事件处理
	this.refreshNum = function(info){
		var count = 0;
		if(info.data){
			if(info.data.recordCount!=null){
				count = info.data.recordCount;
			}
		}
		$("*[data-lui-mark='sys.evaluation.fdEvaluateCount']").text("("+count+")");
	};
	
	/****************************以下为内部函数*******************************************/
	//提交数据
	this._eval_submitData = function() {
		
		//过滤表情
		var evalIframeBody = $('iframe[id="mainIframe"]').contents().find("body");
		var fdEvaluationContent = self.filterExpress(evalIframeBody);
		//  敏感词的判断
		if(checkIsHasSenWords(fdEvaluationContent)!=false) {
			seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
				dialog.alert(checkIsHasSenWords(fdEvaluationContent));
			});
			return;
		}
		if(!validatorRule(fdEvaluationContent)) {
			//校验非法值
			seajs.use(["lui/dialog", "lang!sys-evaluation"], function(dialog, lang) {
				dialog.alert(lang["sysEvaluationMain.validatorRule.msg"]);
			});
			return;
		}
		
		seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
			self.loading = dialog.loading(null,$("#" + self.S_EditAreaId).parent());
		});
		
		//保存附件
		if(self.attObj) {
			self.attObj.updateInput();
		}
		
		var data = self._eval_getUploadData();
		data['fdEvaluationTime'] = (new Date()).format(Calendar_Lang.format.dataTime);
		data['fdId'] = self.attModelId;
		data['fdKey'] = self.fdKey;
		
		if(!self.attObj.validate()) {//校验附件是否在上传中
			return;
		}
		self._eval_resetEditStatus();
		
		$.post(self.S_SaveUrl,data,self._eval_afterAddSubmit,'json');
		return true;
	};
	//获取需要提交的数据
	this._eval_getUploadData = function(){
		var dataObj = {};
		$("#"+ self.S_EditAreaId).find(":input").each(function(){
			var domObj = $(this);
			var eName = domObj.attr("name");
			if(eName!=null && eName!="" ){
				dataObj[eName] = domObj.val();
			}
		});
		//过滤表情
		var evalIframeBody = $('iframe[id="mainIframe"]').contents().find("body");
		var fdEvaluationContent = self.filterExpress(evalIframeBody);
		dataObj['fdEvaluationContent'] = fdEvaluationContent;
		return dataObj;
	};
	//<img> --> [faceCfg.type]
	this.filterExpress = function(contentBody){
		contentBody.find("img").each(function(){
			var faceType = $(this).attr('type');
			var faceCfg = self.EvalBqCfg[faceType];
			var imgHtml = $(this)[0].outerHTML;
			var suffixIndex = imgHtml.indexOf(faceCfg.suffix);
			var imgToSuffix = imgHtml.substring(0,suffixIndex);
			var imgId = imgToSuffix.substring(imgToSuffix.lastIndexOf("/")+1);
			$(this)[0].outerHTML = "["+faceCfg.type+imgId+"]";
		});
		var evalContent = contentBody[0].innerText;
		if(typeof (evalContent) == "undefined")
			evalContent = contentBody[0].textContent;
		//contentBody[0].innerHTML = "";
		return evalContent;
	}
	
	//点评 非法值的判断
	function validatorRule(content) {
		var flag = true;
		var data = {fdEvaluationContent:content};
		//校验非法值
		LUI.$.ajax({
			url: Com_Parameter.ContextPath+'sys/ui/resources/user.jsp',
			type: 'POST',
			dataType: 'text',
			async: false,
			data: data,
			success: function(data, textStatus, xhr) {
				if(data){
					data = data.replace(/[\r\n]/g, "");
				}
				if(data.indexOf("invalid_value") == 0){
					flag = false;
				}
			}
		});
		return flag;
	}
	//点评  敏感词的判断
	function checkIsHasSenWords(content) {
		var result = false;
		var data = {formName:"sysEvaluationMainForm", content:encodeURIComponent(content)};
		LUI.$.ajax({
			url: Com_Parameter.ContextPath+"sys/profile/sysCommonSensitiveConfig.do?method=getIsHasSensitiveword",
			type: 'POST',
			dataType: 'json',
			async: false,
			data: data,
			success: function(data, textStatus, xhr) {
			    var json = eval(data);
			    var flag = json.flag;
			    if(flag){
			    	result = eval_lang['eval_prompt_word_warn']
			    	.replace("{0}", eval_lang['eval_prompt_word_content'])
			    	.replace("{1}",'<span style="color:#cc0000">'+json.senWords+'</span>');
			    }
			}
		});
		return result;
    };
    
    this._setIsCommented = function(commented){
    	var _commented = !!commented;
    	if(self.fdIsCommented == _commented) {
			return;
		}
    	if(_commented) {
    		self.fdIsCommented = true;
			$("#eval_EditMain_new_msg").hide();
			$("#eval_EditMain_add_msg").show();
    	} else {
    		self.fdIsCommented = false;
			$("#eval_EditMain_new_msg").show();
			$("#eval_EditMain_add_msg").hide();
    	}
    }
    
	//点评提交后
	this._eval_afterAddSubmit = function(data){
		if(data!=null && data.status==true){
			var numChange = true;
			if(self.fdIsCommented) {
				numChange = false;
			}
			if(data.newId) {
				//获取下个点评表单的id，附件机制需要用到
				self.attModelId = data.newId;
				self._att_reset();
			} 
			self._eval_evalRecordNumberChange("add",["eval_chl","eval_my"], numChange);
			self._setIsCommented(true);
		}else{
			seajs.use("lui/dialog", function(dialog) {
				dialog.alert(data.msg);
				self.loading.hide();
			});
		}
	};
	
	//重置附件控件
	this._att_reset = function() {
		if(this.attObj) {
			this.attObj.resetAttParam(this.attModelId,this.attModelName, this.attKeyPrefix + this.attModelId);
			this.attObj.refresh();
		}
	}
	
	//reset编辑操作
	this._eval_resetEditStatus = function() {
		self._eval_starChange(1,true,true);
	};
	//button事件处理
	this._eval_enabledBtn = function(enable){
		var buttonVar = $("#eval_button");
		if(enable==true){
			buttonVar.removeAttr("disabled");
			buttonVar.removeClass("eval_button_disable");
		}else{
			buttonVar.attr("disabled","disabled");
			buttonVar.addClass("eval_button_disable");
		}
	};
	//选择点评级别后
	this._eval_starChange = function(val, confirm , forceChg){
		var contentObj = $('iframe[id="mainIframe"]').contents().find("body");
		var contentVal = $.trim(contentObj[0].innerHTML); 
		//IE,包括IE11
		if(!!window.ActiveXObject || "ActiveXObject" in window){
			contentVal = contentVal.replace("<br>","");   
			contentVal = contentVal.replace(/<p>|<\/p>/ig,"");
			contentVal = contentVal.replace(/&nbsp;/ig,"");//过滤特殊字符
		}
		var chgVal = false;
		for(var i=0; i<5; i++){
			var className = 'lui_icon_s_starbad';
			if(i >= val){
				className = 'lui_icon_s_stargood';
			}else{
				className = 'lui_icon_s_starbad';
			}
			$("#" + self.S_EditAreaId + " #eval_star_" + i).attr('class',className);
			if(!chgVal)
				chgVal = contentVal=='' ||(contentVal == self.langCfg['eval_star_' + i]);
		}
		if(chgVal || forceChg){		
			contentObj[0].innerHTML = self.langCfg['eval_star_' + val];
			contentObj.css({'color':'#8a8a8a'});
			$(".eval_prompt").hide();
		}else{
			$(".eval_prompt").show();
		}
		if(confirm){
			$('select[name="fdEvaluationScore"]').val(val);
		}
		self._eval_prompInfo(contentObj,true);
		var level = $('#eval_level');
//		level.removeClass("lui_icon_s_recommend");
//		level.removeClass("lui_icon_s_poor");
//		if(val < 3){
//			level.addClass("lui_icon_s_recommend");
//		}else{
//			level.addClass("lui_icon_s_poor");
//		}
		level.html($('select[name="fdEvaluationScore"] option:nth-child('+(val+1)+')').text());
	};
	
	
	//剩余数字提示
	this._eval_prompt = function(thisObj){
		var propmtTxt = $(thisObj)[0].innerHTML;
		var isChangeColor = false;
		for(var i=0;i<5;i++){
			if(propmtTxt == self.langCfg['eval_star_'+i] || propmtTxt==''){
				isChangeColor = true;
				break;
			}
		}
		if(isChangeColor){
			$(thisObj).css({'color':''});
			$(thisObj)[0].innerHTML = '';
		}
		self._eval_prompInfo($(thisObj),true);
		$(".eval_prompt").show();
	};
	//提示语
	this._eval_prompInfo = function(propmtObj,changeBtn,evalType,replyIframePare,replyBtn){
		var propmtTxt = propmtObj[0].innerHTML;
		var angleReg = /<[^>]+>|<\/[^>]+>/g;
		propmtTxt = propmtTxt.replace(/<img[^>]*src[^>]*>/ig,"[face]");//过滤表情
		if(propmtTxt.indexOf("[face]")<0){
			propmtTxt = propmtObj[0].innerText;
			if(typeof(propmtTxt)=="undefined"){
				propmtTxt = propmtObj[0].textContent;
			}
		}else{
			propmtTxt = propmtTxt.replace(angleReg,"")
									.replace(/&nbsp;/ig,"")
									.replace(/&amp;/ig,"");//过滤特殊字符 
		}
		var l = 0;
		var propmtTxt=propmtTxt.replace(/\s+/g,"");
		var tmpArr = propmtTxt.split("");
		for (var i = 0; i < tmpArr.length; i++) {				
			if (tmpArr[i].charCodeAt(0) < 299) {
				l++;
			} else {
				l += 2;
			}
		}
		var promptVar;
		var __repltBtnId;
		if(evalType!=null && 'eval' != evalType){
			promptVar = replyIframePare.find("span[class='eval_reply_tip']");
		}else{
			promptVar = $(".eval_prompt");
		}
		var isReplyOpt = evalType!=null && 'eval' != evalType && replyBtn!=null;
		if(l<=2001){
			promptVar.html(self.langCfg['eval_prompt_1']+'<font style="font-family: Constantia, Georgia; font-size: 20px;">'
							+ Math.abs(parseInt((2000-l) / 2))+"</font>"+self.langCfg['eval_prompt_3']);
			promptVar.css({'color':''});
			
			if(isReplyOpt){
				replyBtn.setDisabled(l == 0||$.trim(propmtTxt)=='');
			}else if(changeBtn){
				self._eval_enabledBtn(!(l == 0||$.trim(propmtTxt)==''));
			}
		}else{
			promptVar.html(self.langCfg['eval_prompt_2']+'<font style="font-family: Constantia, Georgia; font-size: 20px;">'
							+ Math.abs(parseInt((l-2000) / 2))+"</font>"+self.langCfg['eval_prompt_3']);
			promptVar.css({'color':'red'});
			if(isReplyOpt){
				replyBtn.setDisabled(true);
			}else{
				if(changeBtn)
					self._eval_enabledBtn(false);
			}
		}
	};
	//提交成功发事件
	this._eval_evalRecordNumberChange = function(flag,cnl, numChange){
		seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
			if(numChange !== false) {//追加评论不更新点评数
				var count = self._eval_getEvalRecordNumber(flag);
				topic.publish("evaluation.submit.success",{"data":{"recordCount": count}});
			}
			self.loading.hide();
			dialog.success(self.langCfg['eval_prompt_sucess_' + flag ],$("#" + self.S_EditAreaId));
			
			if(typeof(cnl) == "string") {
				topic.channel(cnl).publish("list.refresh");
			} else {
				for(var i = 0 ; i < cnl.length; i ++) {
					topic.channel(cnl[i]).publish("list.refresh");
				}
			}
		});
	};
	//获取提交后的点评总数
	this._eval_getEvalRecordNumber = function(flag){
		var incNum = 1;
		if(flag=='del'){
			incNum = -1;
		}
		var count = 0;
		$('div[id="eval_label_title"]').each(function(){
			var scoreInfo = $(this).text();
			var numinfo = "0";
			if(scoreInfo.indexOf("(")>0){
				var prefix=scoreInfo.substring(0,scoreInfo.indexOf("("));
				numinfo = scoreInfo.substring(scoreInfo.indexOf("(") + 1 , scoreInfo.indexOf(")"));
				count = parseInt(numinfo) + incNum;
				$(this).text(prefix+"("+(parseInt(numinfo) + incNum)+")");
			}else{
				count = 1;
				$(this).text(scoreInfo + "(1)");
			}
		});
		$.get(self.S_GetScoreUrl,{},self._eval_refreshScroe,'json');
		return count;
	};
	
	//刷新点评平均分数
	this._eval_refreshScroe = function(data){
		var score = data.score;
		$(".eval_doc_score .eval_score_mark").text(score.indexOf('.')>-1?score:(score + ".0"));
		$(".eval_doc_score .eval_score_val").css({"width":(parseFloat(score)/5)*80 + "px"});
	};
	
	//全文点评删除成功处理
	this._eval_afterDelSubmit = function(data, numChange){
		self._eval_evalRecordNumberChange("del",["eval_chl","eval_my"], numChange);
	};
	//段落点评删除成功处理
	this._notes_afterDelSubmit = function(data){
		self._eval_evalRecordNumberChange("del","eval_listview");
	};
	
	//删除dom节点和段落信息
	this.deleteNotesIcon = {func: function(iconFdId){
			//移除img-dom和更新段落信息
			var $img = LUI.$('img[e_id="'+ iconFdId +'"]');
			var notesAreaName = $("input[name='notesAreaName']").val();//段落点评划圈区域
			var $contentDiv = $img.closest("div[name='rtf_"+notesAreaName+"']");
			
			//获取删除点评的段落id
			var notesModelId = $("input[name='notesModelId']").val();
			var notesModelName = $("input[name='notesModelName']").val();
			$img.remove();
			var  urlFlex = "http:\/\/" + location.hostname + ":" + location.port;
			var _docContent = encodeURIComponent($contentDiv[0].innerHTML.replace(urlFlex,''));
			//更新段落
			if (notesModelId) {
				LUI.$
					.post(
					Com_Parameter.ContextPath+'sys/evaluation/sys_evaluation_main/sysEvaluationNotes.do?method=updateContent', {
					docContent: _docContent,
					fdModelId: notesModelId,
					fdModelName: notesModelName
				}, function(data, textStatus, xhr) {
				});
			}
		}
	}
	//点评回复字数控制
	this.checkWordsNum = function(evt,iframeType,iframeId) {
		var replyIframeParent = $("#"+iframeId).parent();
		var __repltBtnId = replyIframeParent.find("div[class='eval_btn_submit']")
					.find("div[data-eval-eid='eval-eid']").attr("id");
		self._eval_prompInfo($(evt),true,iframeType,replyIframeParent,LUI(__repltBtnId));
	}
	
	 //点评回复中表情选择
	window.__range__ = null;
    this.selectIcon = function(obj,iframeObj){
		var imgSrc = $(obj).children(0).attr("src");//表情路径
		var type = $(obj).children(0).attr("type");//表情类型
		var width = this.EvalBqCfg[type]["width"];
		imgSrc += "."+type;
		if(!!window.ActiveXObject || "ActiveXObject" in window || 
				navigator.userAgent.indexOf("Edge") > -1){//IE浏览器
			var selection = iframeObj[0].contentWindow.document.selection;
			if(__range__!=null && selection){
				__range__.execCommand("insertimage",false,imgSrc);
				var r = iframeObj[0].contentWindow.document.body.createTextRange(); 
				r.collapse(false); 
				r.select(); 
				iframeObj[0].contentWindow.document.focus();
			}else{//IE11--不支持execCommand
				var __innerHtml = iframeObj[0].contentWindow.document.activeElement.innerHTML;
				var activeHtml = "";
				if(__innerHtml.lastIndexOf("<br></p>") > -1){
					activeHtml = __innerHtml.substring(0,__innerHtml.length-8) + '<img width="'+width+'" src="'+imgSrc+'"></p>';
				}else if(__innerHtml.lastIndexOf("</p>") > -1){
					activeHtml = __innerHtml.substring(0,__innerHtml.length-4) + '<img width="'+width+'"  src="'+imgSrc+'"></p>';
				}else if(__innerHtml.lastIndexOf("<br>") > -1){
					activeHtml = __innerHtml.substring(0,__innerHtml.length-4) + '<img  width="'+width+'" src="'+imgSrc+'">';
				}else{
					activeHtml = __innerHtml + '<img src="'+imgSrc+'">';
				}
				iframeObj[0].contentWindow.document.activeElement.innerHTML = activeHtml;
			}
			var iBody = iframeObj[0].contentWindow.document.body;
			self.checkEvalContent($(iBody));
		}else{//其他浏览器
			var ifrmaeBody = iframeObj[0].contentWindow.document;
			ifrmaeBody.execCommand("insertimage",false,imgSrc);
		} 
		$(obj).parents(".eval_biaoqing").hide();//关闭当前表情框
		var _mainIframe = $("iframe[id='mainIframe']").contents().find("body");
		self._eval_prompInfo(_mainIframe,true);
	}
    
    //提交回复
	this._evalSubmit = function(thisObj){
		var replyLi = $(thisObj).parents("#reply_li");
		var replyBox = $(thisObj).parents("#reply_content");
		var eval_id = replyLi.attr('eval_id');
		var reply_id = replyLi.attr('reply_id');
		//全文点评或段落点评modelName
		var modelName = $("#eval_txt_"+eval_id+" .eval_praise_reply").attr("eval-view-modelname");
		var evalMark = $(thisObj).parent().attr('eval_mark');//'0'：回复点评；'1'：回复特定回复
		var evalCtnBody = replyBox.find("iframe[name='eval_textarea']").contents().find("body");
		var replyContent = self.filterExpress(evalCtnBody);//过滤表情
		if(checkIsHasSenWords(replyContent)!=false) {
			seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
				dialog.alert(checkIsHasSenWords(replyContent));
			});
			return;
		}else{
			replyContent = base64Encode(replyContent);
			if($.trim(replyContent.replace(/&nbsp;/ig,"")) != ""){ 
				$.ajax({
					url : Com_Parameter.ContextPath + "sys/evaluation/sys_evaluation_main/sysEvaluationReply.do",
					data : {"method": "save","fdEvaluationId":eval_id,
					'replyContent':replyContent,'evalMark':evalMark,
					'modelName':modelName,'replyId':reply_id,
					'mainModelId':eval_opt.modelId,
					'mainModelName':eval_opt.modelName},
					type : 'post',
					cache : false,
					success : function(data) { 
						replyBox.find("iframe[name='eval_textarea']").contents()[0].activeElement.innerHTML = "";//清空文本框
						//恢复字数限制个数
						replyLi.find("span[class='eval_reply_tip']")
						.html(eval_lang['eval_prompt_words_alert1'] + 
								"<font style='font-family: Constantia, Georgia; font-size: 20px;'>1000</font>" + 
								eval_lang['eval_prompt_words_alert3']);
						eval_opt.getReplyInfo(eval_id);//刷新回复记录 
						//按钮恢复灰色
						var $lui = LUI('evalBtn_'+eval_id);
						if($lui){
							$lui.setDisabled(true);
						}
					}
				});
			}
		}
	}
    
    this.checkEvalContent = function(evt){
    	var ischeck = false;
    	$(evt).find("img").each(function(){
    		var imgSrc = $(this).attr('src');
    		if(!imgSrc || imgSrc.length == 0){
    			return ;
    		}
    		var typeIndex = imgSrc.lastIndexOf(".");
    		var faceType = imgSrc.substring(typeIndex+1, imgSrc.length);
    		var faceCfg = self.EvalBqCfg[faceType];
			if(!faceCfg){
				ischeck = true;
				return;
			}
			var cutedSrc = Com_Parameter.ContextPath + this.src.substring(this.src.indexOf("sys"));
			var lastSlash = cutedSrc.lastIndexOf("/");
			
			if(lastSlash>-1){
				var imgName = cutedSrc.substring(lastSlash+1,typeIndex);
				var originImg = $(this)[0].outerHTML;
				var imgSrc = cutedSrc.replace("."+faceCfg.type,"");
				$(this).attr("src",imgSrc);
				$(this).attr("width",faceCfg["width"]);
				$(this).attr("type",faceCfg.type);
			}
		}); 
    }
    
    
    this.endWith  = function(proStr,endStr){
    	 proStr = proStr.replace(/<p>|<\/p>/ig,"");
		 var d = proStr.length - endStr.length;
		 return (d>=0 && proStr.lastIndexOf(endStr)==d);
	}
    //过滤外部img
    this.filterOuterImg = function(evt){
    	var $this = $(evt);
    	$this.find("img[type='face']").each(function(){
			$(this)[0].outerHTML = $(this)[0].outerHTML.replace(/<img/ig,"#!img").replace(">","$!");
		});
    	//IE 解决IE下复制黏贴会多换一行的问题
    	if(!!window.ActiveXObject || "ActiveXObject" in window){
    		$this[0].innerHTML = $this[0].innerHTML.replace(/<p>/ig,"").replace(/<\/p>/ig,"<br>");
		}
		//火狐浏览器
    	var isFirefox = navigator.userAgent.indexOf("Firefox")>-1;
    	if(isFirefox){  
    		$this[0].innerHTML = $this[0].innerHTML.replace(/<br>/ig,"#!br$!");
    	}
    	if($this[0].innerText){
    		var _filHtml = $this[0].innerText
        	$this[0].innerText = _filHtml;
    	}else{
    		var _filHtml = $this[0].textContent
        	$this[0].textContent = _filHtml;
    	}
    	
    	$this[0].innerHTML = $this[0].innerHTML.replace(/\#\!img/ig,"<img").replace(/\$\!/g,">");
    	
    	if(isFirefox){  
    		$this[0].innerHTML = $this[0].innerHTML.replace(/\#\!br\$\!/ig,"<br>");
    	}
    }
    
    //解决IE8下光标在表情图标后，光标自动跳到最前的问题
    this.adjustCursor = function(evt){
    	var $this = $(evt);
		if (navigator.userAgent.indexOf("MSIE 8.0")>0) {
			var reg =/([.*])'gif">'$/g;
			var iframeCtn = $this[0].innerHTML;
			var endWidthGif = self.endWith(iframeCtn,'type="face">');
			if(endWidthGif){
				$this[0].innerHTML = iframeCtn+" ";
			}
		}
    }
    //解决IE8下按Backspace无法删除表情的问题
    this.delImgForIE = function(evt){
    	if (navigator.userAgent.indexOf("MSIE 8.0")>0) {
    		var $this = $(evt);
        	var html = $this[0].innerHTML;
    		var endWidthGif = self.endWith(html,'type="face">');
    		if(endWidthGif){
    			var childrenNum = $this.children().length;
    			$this.children()[childrenNum-1].outerHTML = "";
    		}
    	}
    }
    this.initEvalContentBox = function(){
    	var val =$('select[name="fdEvaluationScore"]').val();
		self._eval_starChange(parseInt(val, 10),false);
		//输入框事件初始化
    	$("iframe[id='mainIframe']").contents().find("body").bind({
    		"keyup":function(e){
    			var keyCode = e.keyCode;
				if(keyCode == 8){
					//解决IE8下按Backspace无法删除表情的问题
					self.delImgForIE(this);
				}
				
				self._eval_prompInfo($(this),true);	
			},
			"focus mouseup":function(){
						self._eval_prompt($(this));
						//解决IE8下光标在表情图标后，光标自动跳到最前的问题
						self.adjustCursor(this);
					},
			// 兼容右键粘贴字数限制
			"input":function(){
						self.checkEvalContent(this);
						self._eval_prompInfo($(this),true);	
					},
			//兼容ie浏览器右键粘贴
			"paste cut":function(){
						var $this = $(this);
						var _this = this;
						setTimeout(function(){
							self.checkEvalContent(_this);
							self.filterOuterImg(_this );
							self._eval_prompInfo($this,true);
						},2); 
						
					}
		});
    }
    
    
    this.superaddition = function(ids, obj, eval_validateAuth) {
    	
    	var $ele = obj, self = this;
    	
    	seajs.use(["lui/jquery","lui/util/env","lui/base","lui/view/render","lui/data/source"], 
    			function($, env,base, render, source) {
    		var  buildAddtion = function(data) {
    			if(data) {
    				var allWidgets = [], allDeffers = [];
	        		for(var key in data) {
	        			var dtd = $.Deferred();
	        			var sourceObj = new source.Static(
	            			{ datas:  data[key]}
	            		);
	        			var renderObj = new render.Template({
	        				"src":'/sys/evaluation/import/tmpl/sysEvaluationMain_addition_tmpl.jsp?eval_validateAuth=' + eval_validateAuth
	        			});
	        			var dataView = new base.DataView({
	        				element : $ele.find("[data-lui-mark='superaddition_" + key + "']")
	        			});
	        			
	        			dataView.addChild(renderObj);
	        			dataView.addChild(sourceObj);
	        			renderObj.setParent(dataView);
	        			sourceObj.setParent(dataView);
	        			
	        			dataView.on("load", function(dtd) {
	        				return function() {
	        					dtd.resolve(); 
	        				}
	        			}(dtd));
	        			allDeffers.push(dtd);
	        			allWidgets.push(dataView);
	        		}
	        		
	        		if(allDeffers.length > 0) {
		        		$.when.apply(this,allDeffers).done(function(ele) {
		        			return function() {
		        				var addListEle = ele, ids = [];
		        				addListEle.find("[data-eval-main-att]").each(function() {
		        					var $t = $(this),  id = $t.attr("data-eval-main-att");
		        					ids.push(id);
		        				});
		        				if(ids.length > 0) {
		        					self.listMainAtt(ids,"data-eval-main-att")
		        				}
		        			}
		        		}($ele));
	        		}
	        		if(allWidgets.length > 0) {
		        		for(var i = 0; i < allWidgets.length; i ++) {
		        			allWidgets[i].render.startup();
		        			allWidgets[i].source.startup();
		        			allWidgets[i].startup();
		        			allWidgets[i].draw();
		        		}
	        		}
    			}
        	};
    		
    		var d = {
    					"ids" : ids,
    					"fdModelName" : self.modelName,
    					"fdModelId" : self.modelId
    				};
    		$.ajax({
    			url : env.fn.formatUrl("/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=getEvaluationByParentIds"),
    			data : $.param(d, true),
    			type : "post",
    			dataType : "json",
    			success : function(data) {
    				buildAddtion(data);
    			},
    			error : function(){
    			}
    		});
    	});
    }
    
    //展现评论列表附件
    this.listMainAtt = function(ids, selector) {
    	var self = this;
    	seajs.use(["sys/evaluation/import/resource/sysAttMainSwf.js","lui/jquery","lui/util/env"], 
    			function(sysAttMainSwf, $, env ) {
    		var  buildAtt = function(data) {
    			if(data) {
            		for(var key in data) {
            			var attObj = new  sysAttMainSwf.SysAttMainWidget({
        					element : "["+ selector + "='" +  key + "']",
        					modelName : self.attModelName,
        					modelId : key,
        					editMode : "view",
        					fdKey : self.attKeyPrefix + key,
        					fileList : data[key]
        				});
        				
        				attObj.startup();
        				attObj.draw();
        				
            		}
    			}
        	};
        	
        	var d = {
    			"ids" : ids
    		};
        	
        	$.ajax({
    			url : env.fn.formatUrl("/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=getAttByIds"),
    			data : $.param(d, true),
    			type : "post",
    			dataType : "json",
    			success : function(data) {
    				buildAtt(data);
    			},
    			error : function(){
    			}
    		});
    	});
    }
}