String.prototype.endWith = function(endStr){
	var d = this.length - endStr.length;      
	return (d >= 0 && this.lastIndexOf(endStr) == d);
}

function Attach_EditOnlineSubmit() {
	//判断父页面是否已经关闭
	var checkErrorFlag = false;
	if (window.opener && window.opener.closed) {
		checkErrorFlag = true;
	} else if (window.opener == null) {
		checkErrorFlag = true;
	}
	if (checkErrorFlag) {
 		seajs.use('lui/dialog', function(dialog) {
     		dialog.alert(clauseMsg.msg1,function() {
    			Com_Parameter.CloseInfo = null;
    			window.close();
     		});
     	});
 		return false;
	} else {
		//通知父页面
		if (window.opener.refreshKmAgreementApplyMain) {
			window.opener.refreshKmAgreementApplyMain();
		}
	}
	 
	return submitCluaseDoc();	
}

//提交表单，保存数据
function submitCluaseDoc() {
	//提交表单校验
	for(var i=0; i<Com_Parameter.event["submit"].length; i++){
		if(!Com_Parameter.event["submit"][i]()){
			return false;
		}
	}
	//提交表单消息确认
	for(var i=0; i<Com_Parameter.event["confirm"].length; i++){
		if(!Com_Parameter.event["confirm"][i]()){
			return false;
		}
	}
	window.opener.setSaveFlag = true;
	Com_Parameter.CloseInfo = null;
	window.close();
	return true;
}


//关闭窗口
function closeWin(){
	Com_Parameter.CloseInfo=clauseMsg.msg2;
	chromeHideJG(0);
	if (!confirm(Com_Parameter.CloseInfo)){
		chromeHideJG(1);
		return;
	}else{
		//清除当前在线编辑用户的信息
    	clearEdit(fdId,fdModelId,fdModelName,fdKey);
	 	window.close();
    }
}

//隐藏显示金格控件，调用common.js中方法不生效，这里重写一个
function chromeHideJG(value) {	
	try{
		if (navigator.userAgent.indexOf("Chrome") >= 0) {
			if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
				$("object[id*='JGWebOffice_']").each(function(i,_obj){
					_obj.HidePlugin(value);
				});	
			}
		}
	}catch(e){}
}

//显示条款库
function showClauseLib() {
	//点击后，进行显示隐藏的切换
	if ($("#jg_tab2").is(":visible")) {
		$("#jg_tab1").css("width","100%");
		$("#jg_tab2").hide();
	} else {
		$("#jg_tab1").css("width","74%");
		$("#jg_tab2").show();
	}
	
}

//设置或者恢复内容控件的背景颜色
function setContentControlBgColor(k) {
	//内容控件的默认值处理
	var _mydoc = jg_attachmentObject_editonline.ocxObj.WebObject;
	var _app = _mydoc.Application;
	var docContentCtrlArr = _app.ActiveDocument.ContentControls;
	for (var p = 1; p <= docContentCtrlArr.Count; p++) {
		var contentText = docContentCtrlArr.Item(p);
		if (contentText && contentText.Tag) {
			if (contentText.Tag.endWith('_1') || contentText.Tag.endWith('_2')) {
				//条款插入的内容控件
				if (k == 0) {
					contentText.Range.HighlightColorIndex = 0;
				} else {
					contentText.Range.HighlightColorIndex = 7;
				}
				
			}
		}
	}
}

/**
 * 重新绑定暂存按钮的事件
 * @returns
 */
function bindSaveDraftClick() {
	var operTmpBar = $("#S_OperationBar").find(".optInnerContainerTab").find("tr:eq(0)");
	for (var m = 0; m < operTmpBar.find("nobr").length;m ++) {
		var curBar = operTmpBar.find("nobr:eq("+m+")");
		if (Attachment_MessageInfo["button.saveDraft"] == curBar.text()) {
			curBar.parent().unbind('click');
			curBar.parent().bind("click", function(evt) {
				evt.stopPropagation();
				//判断父页面是否已经关闭
				var checkErrorFlag = false;
				if (window.opener && window.opener.closed) {
					checkErrorFlag = true;
				} else if (window.opener == null) {
					checkErrorFlag = true;
				}
				if (checkErrorFlag) {
			 		seajs.use('lui/dialog', function(dialog) {
			     		dialog.alert(clauseMsg.msg1,function() {
			    			Com_Parameter.CloseInfo = null;
			    			window.close();
			     		});
			     	});
			 		return false;
				}

				setContentControlBgColor(0);
				
				window.opener.setSaveFlag = true;
			});
			break;
		}
	}
}

//加载条款库相关按钮
var _addClauseBtn, clauseCnt = 0;
function loadClauseBtn() {
	var operTmpBar = $("#S_OperationBar").find(".optInnerContainerTab").find("tr:eq(0)");
	var existClauseBtn = operTmpBar.find("#_attClauseLibBtn_");
	if(!existClauseBtn || existClauseBtn.length == 0) {
		//增加条款库按钮
		var clauseBtnHtml = '<td class="innerTdBtn1"></td>';
		clauseBtnHtml = clauseBtnHtml + '<td class="innerTdBtn2"><a id="_attClauseLibBtn_" title="" href="javascript:showClauseLib();"><nobr>条款库</nobr></a></td>';
		clauseBtnHtml = clauseBtnHtml + '<td class="innerTdBtn3"></td>';
		clauseBtnHtml = clauseBtnHtml + '<td class="innerTdSpace"></td>';
		$("#S_OperationBar").find(".optInnerContainerTab").find("tr:eq(0)").prepend(clauseBtnHtml);
		
		bindSaveDraftClick();
	}
	
	//金格控件加载后会重置按钮，这里会定时再次添加条款库按钮
	if(clauseCnt >= 30) {
		clearTimeout(_addClauseBtn);
	} else {
		_addClauseBtn = setTimeout("loadClauseBtn()", 2500);
	}
	clauseCnt++;
	
	//按钮左对齐
	//$("#S_OperationBar").find(".optInnerContainerTab").css({"float":"left","margin-left":"10px"});

}

//文档加载事件
Com_AddEventListener(window, "load", function() {
	setTimeout(function(){
        jg_attachmentObject_editonline.load(encodeURIComponent(unescape(clauseMsg.msg3)), "");
        jg_attachmentObject_editonline.show();
        if(jg_attachmentObject_editonline.ocxObj){
  		   //在线编辑打开，默认显示留痕
  		   jg_attachmentObject_editonline.ocxObj.Active(true);
			if(jg_attachmentObject_editonline.showRevisions == true){
				jg_attachmentObject_editonline.ocxObj.WebSetRevision(true,true,true,true);
			}
  			if(!jg_attachmentObject_editonline.isFirst && jg_attachmentObject_editonline.hasShowButton){
  	  			//非编辑状态下隐藏按钮栏
  				 $("#S_OperationBar").remove();
  			}

  			//默认关闭修订模式
  			var _mydoc = jg_attachmentObject_editonline.ocxObj.WebObject;
  			var _app = _mydoc.Application;
  			_app.ActiveDocument.TrackRevisions = false;
  			
  			setContentControlBgColor(1);
  			
  			//非格式合同的部分映射还需要进行文档限定操作
  			if (window.opener) {
  				var doc_fdIssample = window.opener.formInitData.fdIssample;
  				var doc_notSampleType = window.opener.formInitData.notSampleType;
  				if ('false' == doc_fdIssample && 'formTomainPart' == doc_notSampleType) {
  					var doc_ProtectKNames = window.opener.protectKNames;
  	  				var doc_applyId = window.opener.formInitData.applyId;
  	  				var doc_bookMappingJson = window.opener.bookMappingJson;
  	  				if(doc_ProtectKNames){
  	  					jg_attachmentObject_editonline.ocxObj.EditType="1,1";
  	  					if(_mydoc.ProtectionType != -1) {
  	  						var orgApplyId = window.opener.GetQueryString('fdApplyId');
  	  						if (orgApplyId) {
  	  							//复制合同后，因为文档处于被保护状态，需要使用原先的合同ID进行文档解保护
  	  							jg_attachmentObject_editonline.protectUnName(doc_ProtectKNames,orgApplyId);
  	  						} else {
  	  							//使用当前的合同ID进行文档解保护
  	  							jg_attachmentObject_editonline.protectUnName(doc_ProtectKNames,doc_applyId);
  	  						}
  	  					}
  	  					//修改未配置书签，光标改为范围书签
  	  					var doc_tableNameStr = window.opener.tableValueS.join(',');
  	  					var doc_notCofigBookMarkArr = doc_ProtectKNames.split(',');
	  	  				for(var i=0; i<doc_notCofigBookMarkArr.length; i++) {
	  	  					var notBookMarkName = doc_notCofigBookMarkArr[i];
	  	  					var notBookmarkObj = _app.ActiveDocument.Bookmarks.Item(notBookMarkName);
		  	  				if (notBookmarkObj && doc_tableNameStr.indexOf(notBookMarkName) < 0) {
			  	  				if (notBookmarkObj.Start == notBookmarkObj.End) {
			  						//锚点书签，修订为范围书签
			  						notBookmarkObj.End = notBookmarkObj.Start + 1;
			  					}
		  	  				}
	  	  				}
  	  					
  	  					//光标移动到第一个可编辑书签位置
  	  					var tmpBookmarkNames = doc_ProtectKNames.split(",");
  	  					_app.ActiveWindow.Selection.GoTo(-1,0,0,tmpBookmarkNames[0]);
  	  					jg_attachmentObject_editonline.protectKName(doc_ProtectKNames,doc_applyId,1,doc_bookMappingJson);
  	  					_app.ActiveDocument.ActiveWindow.ActivePane.View.Zoom.Percentage = 100;
  	  				}
  				}
  			}
        }
	},1000);
	
	setTimeout(function(){
		//合同编辑页面还有其他扩展点按钮，所以这里做延时处理
		loadClauseBtn();
		
		//文档提交事件
		Com_Parameter.event["submit"].push(function() {
			if(jg_attachmentObject_editonline.updateTimer){
				clearInterval(jg_attachmentObject_editonline.updateTimer);
				return true;
			}else{
				return true;
			}
		});
		
		//条款保存事件
		Com_Parameter.event["submit"].push(function() {
			var flag = false;
			//是否需要保存条款
			var needAddClause = false;
			var saveCaluseArr = [];
			
			//判断文档中是否引用了条款，且条款内容不为空
			var _mydoc = jg_attachmentObject_editonline.ocxObj.WebObject;
			var _app = _mydoc.Application;
			var docContentCtrlArr = _app.ActiveDocument.ContentControls;
			var oldTagNo = 1;
			var textCtrlNoObj = {};
			for (var p = 1; p <= docContentCtrlArr.Count; p++) {
				var contentText = docContentCtrlArr.Item(p);
				if (contentText && contentText.Tag) {
					if (contentText.Tag.endWith('_1') || contentText.Tag.endWith('_2')) {
						//条款插入的内容控件
						needAddClause = true;
						contentText.Range.HighlightColorIndex = 0;
						var curClauseJson = {};
						var inputStr = contentText.Range.Text;
						//条款内容
						var curContent = $.trim(inputStr);
						if ('单击此处输入文字。' == curContent) {
							curContent = '';
						}
						//条款编号
						var curTagNo = -1;
						//条款ID
						var curId;
						var noIndex = contentText.Tag.indexOf("*");
						if (noIndex > -1) {
							//新插入的条款，带有编号
							curTagNo = contentText.Tag.substring(0,noIndex);
							curId = contentText.Tag.substring(noIndex+1,contentText.Tag.length -2);
						} else {
							//旧条款，没有编号
							curId = contentText.Tag.substring(0,contentText.Tag.length -2);
						}

						//条款类型
						var curType;
						var curEnd;
						if (contentText.Tag.endWith('_1')) {
							curType = 'clause';
							curEnd = '1';
						} else {
							curType = 'clauses';
							curEnd = '2';
						}
						
						if (curTagNo == -1) {
							//给当前内容控件赋予编号
							var keyId = curId + "_" + curType;
							if (textCtrlNoObj[keyId]) {
								var tmpNo = parseInt(textCtrlNoObj[keyId]);
								curTagNo = tmpNo + 1;
							} else {
								curTagNo = 1;
							}
							textCtrlNoObj[keyId] = curTagNo;
							contentCtrl.Tag = curTagNo + "*" + curId + "_" + curEnd;
						}
						
						curContent = curContent.replace(//g,'<br>');
						curClauseJson['id'] = curId;
						curClauseJson['type'] = curType;
						curClauseJson['tagNo'] = curTagNo;
						curClauseJson['content'] = curContent;
						saveCaluseArr.push(curClauseJson);
					}
				}
			}
			
			if (needAddClause) {
				//保存条款
				try {
					var doc_curModelName = window.opener.curModelName;
					if (!doc_curModelName) {
						//只有合同申请或者审批页面才不会引用范本按钮
						doc_curModelName = 'apply';
					}
					var p_modelId;
					var p_modelName;
					var p_refTarget;
					var p_refHref;
					var p_subject = window.opener.formInitData.docSubject;
					if ('apply' == doc_curModelName) {
						p_modelId = window.opener.formInitData.applyId;
						p_modelName = 'com.landray.kmss.km.agreement.model.KmAgreementApply';
						p_refTarget = clauseMsg.msg5;
						p_refHref = '/km/agreement/km_agreement_apply/kmAgreementApply.do?method=view&fdId='+p_modelId;
					} else if ('model' == doc_curModelName){
						p_modelId = window.opener.formInitData.modelId;
						p_modelName = 'com.landray.kmss.km.agreement.model.KmAgreementModel';
						p_refTarget = clauseMsg.msg6;
						p_refHref = '/km/agreement/km_agreement_model/kmAgreementModel.do?method=view&fdId='+p_modelId;
					}
					//保存条款引用记录
					$.ajax({
						url: Com_Parameter.ContextPath + "km/clause/km_clause_other_ref/kmClauseOtherRef.do?method=saveRef",
						type: "POST",
						dataType: 'json',
						data : {
							refModelId:p_modelId,
							refTarget:p_refTarget,
							refTargetModelName:p_modelName,
							refHref:p_refHref,
							refSubject:p_subject,
							refCaluseJson:JSON.stringify(saveCaluseArr)
						},
						success: function(res) {
							if(window.console) {
								console.log('条款保存成功');
							}
						}
					});
					flag = true;
				} catch (e) {
					if(window.console) {
						console.error(e.message);
					}
				}
			} else {
				flag = true;
			}
			return flag;
		});
	},2500);
	
	var winHeight = 550;
	if (window.innerHeight)
		winHeight = window.innerHeight;
	else if ((document.body) && (document.body.clientHeight))
		winHeight = document.body.clientHeight;
	var obj1 = document.getElementById(clauseMsg.msg4);
	if(obj1){
		obj1.setAttribute("height", (winHeight-80)+"px");
	}
	$("#panelTd").css("height", (winHeight-80)+"px");
	var tabHeadHeight = parseInt($(".lui_agreement_tab").height());
	$(".lui_agreement_aside_list").css("height", (winHeight-80-tabHeadHeight)+"px");
	$("#listIframe").css("height", (winHeight-85-tabHeadHeight)+"px");
});


//WebClose方法放到unload事件里边执行会导致火狐和谷歌浏览器崩溃
Com_AddEventListener(window, "beforeunload", function() {
	if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0){ 
  			if (jg_attachmentObject_editonline.hasLoad) {		  			
  				try {	  						  				  					    
  					if (jg_attachmentObject_editonline.ocxObj && !jg_attachmentObject_editonline.ocxObj.WebClose()) {
  						jg_attachmentObject_editonline.setStatusMsg(jg_attachmentObject_editonline.ocxObj.Status);
  						alert(jg_attachmentObject_editonline.ocxObj.Status);
  					}
  				} catch (e) {
  					alert("jg_unLoad error: " + e.description);
  				}
  			}
		}
	})

Com_AddEventListener(window, "unload", function() {
	//清除当前在线编辑用户的信息
	clearEdit(fdId,fdModelId,fdModelName,fdKey);
	jg_attachmentObject_editonline.unLoad();
});

//独立打开合同编辑页面窗口时，提交或者保存，对当前文档进行清稿
//并将清稿的文件保存为临时Key(tmpJgProcessDoc)，查看页提交的时候，使用这个Key生成版本文件
Com_Parameter.event["submit"].push(function() {
	if (window.opener) {
		var url = window.opener.location.href;
		if (url.indexOf("kmAgreementApply.do?method=view") > -1 && jg_attachmentObject_editonline.ocxObj.Modify) {
			__saveDoc();
		}
	}
	return true;
});

function __saveDoc() {
	// 记录原始数据
	var _WebUrl = jg_attachmentObject_editonline.ocxObj.WebUrl;
	var _AllowEmpty = jg_attachmentObject_editonline.ocxObj.AllowEmpty;
	var _fdId = jg_attachmentObject_editonline.ocxObj.WebGetMsgByName("_fdId");
	__log("保存过程文档-开始");
	try {
		var rtn;
		var tempPath;
		var fileName;
		if (window.userOpSysType.indexOf("Win") == -1
			&& navigator.userAgent.indexOf("Firefox") >= 0) {
			//国产化金格没有操作文件系统的接口，因此不做备份
		} else {
			// 备份原文档
			tempPath = jg_attachmentObject_editonline.activeObj.FileSystem.GetTempPath();
			var tempFileType = jg_attachmentObject_editonline.ocxObj.fileType;
			if (!tempFileType) {
				tempFileType = ".doc";
			}
			fileName = new Date().getTime() + tempFileType;
			__log("备份原文档：" + tempPath + fileName);
			rtn = jg_attachmentObject_editonline.ocxObj.WebSaveLocalFile(tempPath + fileName);
			__log("备份结果：" + rtn);
		}

		// 指定保存路径
		jg_attachmentObject_editonline.ocxObj.ClearRevisions();
		jg_attachmentObject_editonline.ocxObj.WebUrl = jg_attachmentObject_editonline.ocxObj.WebUrl+"?method=comparison&_addition=1";
		jg_attachmentObject_editonline.ocxObj.AllowEmpty = true;
		jg_attachmentObject_editonline.ocxObj.WebSetMsgByName("_fdId", new Date().getTime());
		jg_attachmentObject_editonline.ocxObj.WebSetMsgByName("_userId", clauseMsg.curUserId);
		// 保存文件
		jg_attachmentObject_editonline.ocxObj.WebSetMsgByName("_fdKey", "tmpJgProcessDoc");
		jg_attachmentObject_editonline.setFileName(clauseMsg.fileName);
		rtn = jg_attachmentObject_editonline.ocxObj.WebSave();
		__log("保存文件结果：" + rtn);

		if (window.userOpSysType.indexOf("Win") == -1
			&& navigator.userAgent.indexOf("Firefox") >= 0) {
			//国产化金格没有操作文件系统的接口，因此不做备份
		} else {
			// 加载备份文档
			rtn = jg_attachmentObject_editonline.ocxObj.WebOpenLocalFile(tempPath + fileName);
			__log("加载备份文档结果：" + rtn);
		}
		__log("保存过程文档-结束");
	} catch (e) {
		if(window.console) {
			console.error(e);
		}
	}

	// 还原数据
	jg_attachmentObject_editonline.ocxObj.WebUrl = _WebUrl;
	jg_attachmentObject_editonline.ocxObj.AllowEmpty = _AllowEmpty;
	jg_attachmentObject_editonline.ocxObj.WebSetMsgByName("_fdId", _fdId);
}

/**
 * 显示控制台日志
 * @param msg
 * @private
 */
function __log(msg) {
	if(window.console) {
		console.log(msg);
	}
}