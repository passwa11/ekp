Com_IncludeFile("selectAnchor.js",Com_Parameter.ContextPath+"sys/evaluation/import/resource/","js",true); 
Com_IncludeFile("sysEvaluationMessageInfo.jsp",Com_Parameter.ContextPath+"sys/evaluation/import/","js",true);

	seajs.use(['lui/jquery'],function($) {
		$( function() {	
				//是否有段落点评权限
				var hasNotesRight = $("input[name='hasNotesRight']").val();
				//是否启用段落点评
				var notesEnable = $("input[name='notesEnable']").val();
				if(notesEnable == '1' && hasNotesRight && hasNotesRight  == "true"){
					var shareDiv  = document.getElementById('share_div');
					var notesAreaName = $("input[name='notesAreaName']").val();//段落点评划圈区域
					if(shareDiv)
						selectAnchor(shareDiv, $("div[name='rtf_"+notesAreaName+"']").toArray(),share);
				}
				if(notesEnable != '1' && hasNotesRight && hasNotesRight  == "true"){
					var shareDiv  = document.getElementById('share_div');
					var notesAreaName = $("input[name='notesAreaName']").val();//段落点评划圈区域
					if(shareDiv) {
						document.getElementById('evaluationShare').style.display = "none";;
						selectAnchor(shareDiv, $("div[name='rtf_"+notesAreaName+"']").toArray(),share);
					}
				}
				// 绑定段落点评事件
				bindEvaluationEvent();
			});
	});
	
	//段落点评
	var share = [{
		id: 'sinaShare',
		func: function() {
			var eleTitle = document.getElementsByTagName("title")[0];
			var txt = funGetSelectTxt(),
				title = (eleTitle && eleTitle.innerHTML) ? eleTitle.innerHTML : SysEval_MessageInfo["sysEvaluationNotes.unNamedPage"];
			if (txt) {
				//去掉弹出的点评分享框
				var shareDiv = document.getElementById('share_div');
				if (shareDiv.style.display == "block") {
					shareDiv.style.display = "none";
				}
				shareDialog(txt);
				// window.open('http://v.t.sina.com.cn/share/share.php?title=' + txt + SysEval_MessageInfo["sysEvaluationNotes.fromPage"] + title +SysEval_MessageInfo["sysEvaluationNotes.words"]+'&url=' + encodeURIComponent(window.location.href));
			}
		}
	}, {
		id: "evaluationShare",
		func: function() {
			var fdEvalId = '${param.fdId}';
			var _docSubject = funGetSelectTxt();
			_docSubject=_docSubject.replace(/</g,"&lt;").replace(/>/g,"&gt;");
			funGetSelect();
			if(_docSubject){
				//去掉弹出的点评分享框
				var shareDiv = document.getElementById('share_div');
				if (shareDiv.style.display == "block") {
					shareDiv.style.display = "none";
				}
				evaluationDialog(_docSubject); 
			}
		}
	  }
	];

    // 弹出分享框
    function shareDialog(txt) {
    	window.shareTxt = txt;
        seajs.use(['sys/ui/js/dialog'], function (dialog) {
            dialog.build({
                config: {
                    width: 710,
                    height: 570,
                    lock: true,
                    cache: false,
                    title : SysEval_MessageInfo["sysEvaluationNotes.Share"],
                    content: {
                        id: 'dialog_iframe',
                        scroll: true,
                        type: "iframe",
                        url: "/sys/evaluation/sys_evaluation_share/sysEvaluationShare_tab.jsp?fdModelId="+window.eval_opt.modelId+"&fdModelName="+window.eval_opt.modelName+"&fdUrl="+encodeURIComponent(window.location.href),
                        // 下面的代码路径有问题
                        // url: Com_Parameter.ContextPath+"sys/evaluation/sys_evaluation_share/sysEvaluationShare_tab.jsp?fdModelId="+window.eval_opt.modelId+"&fdModelName="+window.eval_opt.modelName,
                        // url|element|html:"",
                        iconType: "",
                        buttons : []
                    }
                },
                callback: function (value, dialog) {
                },
                actor: {
                    type: "default"
                },
                trigger: {
                    type: "default"
                    //timeout : 5
                }

            }).show();
        });
    }
    
	//绑定提示
	function bindEvaluationEvent() {
		seajs.use(['lui/jquery','sys/evaluation/import/resource/jquery.qtip'],function($, _qtip) {
			_qtip($);
			$('img[e_id]').each(function(index) {
				$(this).qtip({
					content: {
						url: Com_Parameter.ContextPath+"sys/evaluation/sys_evaluation_main/sysEvaluationNotes.do?method=view&e_id="+$(this).attr('e_id')
					},
						show: 'mouseover',
						hide: 'mouseout',
						style: {
							width : {
								min : 300,
								max : 500
							},
							border:"",
							background : "#fff"
						}
					});
				});
		}); 
	}
	
	//弹出段落点评
	function evaluationDialog(subject) {
			seajs.use( [ 'lui/dialog' ], function(dialog) {
				$("#lui_evalNote_subject p").html(subject);
				$("#eval_eva_content").val("");
				$("input[name='isNotify1']").prop("checked", true);
				$("input[name='isNotify2']").prop("checked", true);
				$("input[name='isNotify3']").attr("checked", false);
				window.dialogDiv = dialog.build( {
						id : 'evaluationDiv',
						config : {
							width:610,
							height:410,
							lock : true,
							cahce : false,
							title : SysEval_MessageInfo["table.sysEvaluationNotes"],
							content : {
							type : "Element",
							elem : $("#lui_evalNote_hidden"),
							    buttons : [ {
									name : SysEval_MessageInfo["button.ok"],
									value : true,
									focus : true,
									disabled:true,
									fn : function(value,_dialog) {
							    			var confirmBtnId = this.lui_id;
							    			var btnClass = $("#"+confirmBtnId).attr("class");
							    			if(btnClass.indexOf('lui_widget_btn_disabled') <= 0){
							    				var content = LUI.$('#eval_eva_content').val();
							    				confirmEva(subject,content,_dialog);
							    			}
										}
									}
								, {
									name : SysEval_MessageInfo["button.cancel"],
									value : false,
									styleClass: 'lui_toolbar_btn_gray',
									className : 'lui_toolbar_btn_cancel',
									fn : function(value, _dialog) {
										_dialog.hide();
									}
								} ]
							}
						},

						callback : function(value, dialog) {

						},
						actor : {
							type : "default"
						},
						trigger : {
							type : "default"
						}

					}).show();
		});
			dialogDiv.on("layoutDone",function () {
				LUI.$('#eval_eva_content').focus();
			});
	}
	//更新文档内容
	var updateDocContent = {func:function setContent(_data,fdModelId,fdModelName){
			var cons = document.createElement('img');
			cons.src =Com_Parameter.ContextPath + "sys/evaluation/import/resource/images/icon_add.png";
			cons.width = '10';
			cons.height = '10';
			cons.setAttribute('contenteditable','false');
			cons.setAttribute('e_id', _data);
			//点评的段落的img
			var editorDiv = funSetSelectTxt(cons);
			bindEvaluationEvent();
			//点评的段落
			var notesAreaName = $("input[name='notesAreaName']").val();//段落点评划圈区域
			var $contentDiv = LUI.$(editorDiv).closest("div[name='rtf_"+notesAreaName+"']");
			editorDiv = $contentDiv[0];
			var  urlFlex = location.protocol + "\/\/" + location.hostname + ":" + location.port;
			var _docContent = encodeURIComponent(editorDiv.innerHTML.replace(urlFlex,''));
			//更新段落
			if (fdModelId) {
				LUI.$
					.post(
					Com_Parameter.ContextPath+'sys/evaluation/sys_evaluation_main/sysEvaluationNotes.do?method=updateContent', {
					docContent: _docContent,
					fdModelId: fdModelId,
					fdModelName: fdModelName
				}, function(data, textStatus, xhr) {
						seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
							//点评成功
							if(data.flag && data.flag == true) {
								//更新点评总次数
								var count = eval_opt._eval_getEvalRecordNumber("add");
								topic.publish("evaluation.submit.success",{"data":{"recordCount": count}});
								topic.channel("eval_listview").publish("list.refresh");
								dialog.success(SysEval_MessageInfo['return.optSuccess']);
							}else{
								dialog.failure(SysEval_MessageInfo['return.optFailure']);
								//删除段落点评记录
								LUI.$.ajax({
									url: Com_Parameter.ContextPath + 'sys/evaluation/sys_evaluation_main/sysEvaluationNotes.do?method=delete',
									type: 'GET',
									dataType: 'json',
									async : false,
									data: "fdId="+_data,
									success: function(data, textStatus, xhr) {
									},
									error: function(xhr, textStatus, errorThrown) {
										
									}
								});
								
							}
						});
				}, "json");
			}
		}
	};
	//段落点评确认
	function confirmEva(_docSubject, _content,_dialog) {
		if(checkIsHasSenWords(_content)==true) { 
			return;
		}else{
			var fdModelId = $("input[name='notesModelId']").val();
			var fdModelName = $("input[name='notesModelName']").val();
			var isNotify1 = $("input[name='isNotify1']").val();
			var isNotify2 = $("input[name='isNotify2']").val();
			var isNotify3 = $("input[name='isNotify3']").val();
			var isNotify = isNotify1+";"+isNotify2+";"+isNotify3;
			var notifyType = $("input[name='fdNotifyType']").val();
			_dialog.hide();
			document.getElementById('share_div').style.display = 'none';
			var _data;
			//提交段落点评
			LUI.$.ajax({
				url: Com_Parameter.ContextPath + 'sys/evaluation/sys_evaluation_main/sysEvaluationNotes.do?method=saveEvalNotes',
				type: 'POST',
				dataType: 'json',
				async : false,
				data: "docSubject=" +  encodeURIComponent(_docSubject)
				+ "&fdEvaluationContent=" + encodeURIComponent(_content)
				+  "&fdModelId=" + fdModelId 
				+  "&fdModelName=" + fdModelName
				+  "&isNotify=" + isNotify
				+"&notifyType=" + notifyType,
				success: function(data, textStatus, xhr) {
				if (data && data['fdId']) {
					_data = data['fdId'];
				}
			},
			error: function(xhr, textStatus, errorThrown) {
				alert(errorThrown);
			}
			});
			if (_data) {
				updateDocContent.func(_data,fdModelId,fdModelName);
			}
		}
	}
	//段落点评  敏感词的判断
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
			    	var msg = eval_lang['eval_prompt_word_warn']
			    	.replace("{0}", eval_lang['eval_prompt_word_content'])
			    	.replace("{1}",'<span style="color:#cc0000">'+json.senWords+'</span>');
			    	
			    	seajs.use("lui/dialog", function(dialog) {
			    		dialog.alert(msg);
			    	});
			    	
			    	result = true;
			    }
			}
		});
		return result;
    };
	//段落点评字数控制
	function checkWordsCount(obj){
		if(!obj)obj= "#eval_eva_content";
		var textArea = $(obj).val();
		var l = 0;
		var tmpArr = textArea.split("");
		for (var i = 0; i < tmpArr.length; i++) {				
			if (tmpArr[i].charCodeAt(0) < 299) {
				l++;
			} else {
				l += 2;
			}
		}
		var promptVar = $(obj).parent()[0].nextSibling.nextSibling;
		var confirmBtnId = $(".lui_dialog_buttons_container").children()[0].id;
		if(l<=600){
			$(promptVar).html(SysEval_MessageInfo["sysEvaluationNotes.alert1"]+'<font style="font-family: Constantia, Georgia; font-size: 20px;">'
							+ Math.abs(parseInt((600-l) / 2))+"</font>"+SysEval_MessageInfo["sysEvaluationNotes.alert3"]);
			$(promptVar).css({'color':''});
			if(l == 0||textArea.trim()==''){
				LUI(confirmBtnId).setDisabled(true);
				return false;
			}else{
				LUI(confirmBtnId).setDisabled(false);
				return true;
			}
		}else{
			$(promptVar).html(SysEval_MessageInfo["sysEvaluationNotes.alert2"]+'<font style="font-family: Constantia, Georgia; font-size: 20px;">'
							+ Math.abs(parseInt((l-600) / 2))+"</font>"+SysEval_MessageInfo["sysEvaluationNotes.alert3"]);
			$(promptVar).css({'color':'red'});
			LUI(confirmBtnId).setDisabled(true);
			return false;
		}
	}
	
	