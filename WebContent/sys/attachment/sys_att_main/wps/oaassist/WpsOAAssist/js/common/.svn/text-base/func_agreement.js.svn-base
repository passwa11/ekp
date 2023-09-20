/**
 * 合同模块的初始处理
 * 默认只读文档可以通过取消保护来恢复为可以编辑，因此需要额外处理 
 * @param doc
 * @returns
 */
function kmAgreementInitProc(doc) {
	var ekpAttMainKey = GetDocParamsValue(doc, "ekpAttMainKey");
	var ekpModelName = GetDocParamsValue(doc, "ekpModelName");
	var ekpFdIsSample = GetDocParamsValue(doc, "ekpFdIsSample");
	var ekpFdMapping = GetDocParamsValue(doc, "ekpFdMapping");
	var ekpFdAttmainStatus = GetDocParamsValue(doc, "ekpFdAttmainStatus");
	var formDataStr = GetDocParamsValue(doc, "formDataStr");
	var ekpProtectType = GetDocParamsValue(doc, "ekpProtectType");
	var ekpPassword = GetDocParamsValue(doc, "ekpPassword");
	var ekpFdModelStatus = GetDocParamsValue(doc, "ekpFdModelStatus");
	
	var numberProtectType = -1;
	if (ekpProtectType) {
		numberProtectType = parseInt(ekpProtectType);
	}
	
	if (ekpAttMainKey == "mainOnline") {
		if (ekpModelName == "com.landray.kmss.km.agreement.model.KmAgreementApply"
			|| ekpModelName == "com.landray.kmss.km.agreement.model.KmAgreementChange") {
			//格式合同，文档为只读状态，并且进行表单映射正文处理
			if (ekpFdIsSample && ekpFdIsSample == "true") {
				if (ekpFdMapping == "formTomainOnline") {
					//表单映射正文
					var ekpOldPassword = GetDocParamsValue(doc, "ekpOrgPassword");
					//复制合同后进行映射，当前文档处于包含状态，需要先解除保护
					if (doc.ProtectionType != -1) {
						if (ekpOldPassword) {
							//先用原合同ID
							doc.Unprotect(ekpOldPassword);
						}
						if (doc.ProtectionType != -1) {
							doc.Unprotect(ekpPassword);
						}
						if (doc.ProtectionType != -1) {
							wps.alert('当前文档被保护，无法写入表单内容');
							return;
						}
					}
					formDataStr = formDataStr.replace(/>/g,'"');
					if (formDataStr) {
						var formDataObj = eval("("+formDataStr+")");
						kmAgreement_formToDoc(formDataObj);
					}
				}
				//设置文档只读
			    if (numberProtectType > -1){
			    	SetAgreementDocProtect(doc, numberProtectType, ekpPassword);
			    } 
			} else {
				//非格式合同，根据传入状态确认是否只读，默认需要打开修订模式
				if (ekpFdAttmainStatus == "edit" && ekpFdModelStatus != '10') {
					//编辑状态打开修订模式
					//开启修订模式
					OnOpenRevisions();
				} else if (ekpFdAttmainStatus == "read") {
					//阅读状态设置文档只读
					//设置文档只读
				    if (numberProtectType > -1){
				    	SetAgreementDocProtect(doc, numberProtectType, ekpPassword);
				    }
				}
			}
		} else if (ekpModelName == "com.landray.kmss.km.agreement.model.KmAgreementModel"
			|| ekpModelName == "com.landray.kmss.km.sample.model.KmSampleModel"
			|| ekpModelName == "com.landray.kmss.km.sample.model.KmSampleOutline") {
			//范本处理
			if (ekpFdAttmainStatus == "edit") {
				//编辑状态打开修订模式
			} else if (ekpFdAttmainStatus == "read") {
				//阅读状态设置文档只读
				//设置文档只读
			    if (numberProtectType > -1){
			    	SetAgreementDocProtect(doc, numberProtectType, ekpPassword);
			    }
			}
		}
	}
}

/**
 * 文档上传完毕后通知合同模块，执行js异步刷新
 * @param params
 * @returns
 */
function notifykmAgreement(params) {
	console.log(params);
	//测试用，看能否调用到业务模块的js
}

/**
 * 表单映射正文处理
 * @param formDataObj
 * @returns
 */
function kmAgreement_formToDoc(formDataObj) {
	console.log("进行表单映射正文处理");
	var formData = formDataObj['bookMarkValue'];
	var formFieldMapping = formData['wpsFieldName'];
	var l_Doc = wps.WpsApplication().ActiveDocument;

	if (l_Doc.TrackRevisions == false) {
		//开启修订模式
		OnOpenRevisions();
		//显示留痕
	}

	//清空所有修订
	var revisionCnt = l_Doc.Revisions.Count;
	for (var p = revisionCnt; p >= 1;p --) {
		var revisionRange = l_Doc.Revisions.Item(p).Range;
		if (revisionRange.Information(12)) {
			//书签在表格中，无须删除
		} else {
			revisionRange.Text = "";
		}
	}
	
	//先处理非表格书签，处理完毕后关闭修订模式，然后再处理表格
	var bookmarkCount = l_Doc.Bookmarks.Count;
	for(var cm = 1;cm <= bookmarkCount;cm ++){
		var cmBookMarkObj = l_Doc.Bookmarks.Item(cm);
		if (cmBookMarkObj) {
			var bookmarkname = cmBookMarkObj.Name;
			var bookVal = formData[bookmarkname];
			if (typeof bookVal != 'undefined' && bookVal != null && bookVal != '==请选择==') {
				//恢复回车换行
				if (typeof bookVal == 'string') {
					bookVal = bookVal.replace(/_ntko_##_@/g,'\n');
					//特殊符号恢复
					bookVal = bookVal.replace(/t_@_ctrl_##_p/g,"'");
				}
				//判断书签是否在表格中
				var initContainTable = cmBookMarkObj.Range.Tables.Count;
				if (initContainTable <= 0) {
					cmBookMarkObj.Range.Text = bookVal;
				}
			} else {
				//判断书签是否在表格中
				var initContainTable = cmBookMarkObj.Range.Tables.Count;
				if (initContainTable <= 0) {
					cmBookMarkObj.Range.Text = '';
				}
			}
		}

	}
	
	if (l_Doc.TrackRevisions == true) {
		//关闭修订模式
		OnCloseRevisions();
		//显示留痕
	}

	//判断表格是否存在编号列，如果存在，自动填充编号
	var procTableObj = {};
	//获取当前文档中的所有表格，将同一表格的书签归为一组
	var tableMapping = getBookMarksGroupByTable(l_Doc,formFieldMapping);
	
	for(var pIndex = 1;pIndex <= bookmarkCount;pIndex++){
		var bookMarkObj = l_Doc.Bookmarks.Item(pIndex);
		if (bookMarkObj) {
			var bookmarkname = bookMarkObj.Name;
			var bookVal = formData[bookmarkname];
			if (typeof bookVal != 'undefined' && bookVal != null && bookVal != '==请选择==') {
				//恢复回车换行
				if (typeof bookVal == 'string') {
					bookVal = bookVal.replace(/_ntko_##_@/g,'\n');
				}
				//判断书签是否在表格中
				var initContainTable = bookMarkObj.Range.Tables.Count;
				if (initContainTable > 0) {
					//默认存在编号列
					var numCellFlag = 1;
					var bookmarkCell = bookMarkObj.Range.Cells.Item(1);
					var table = bookmarkCell.Range.Tables.Item(1);
					var tableRow = bookVal.length;
					var tableRowIndex = bookmarkCell.RowIndex;
					var curColIndex = bookmarkCell.ColumnIndex;
					if (curColIndex == 1) {
						//不存在编号列
						numCellFlag = 0;
					}

					//获取书签对应的表格ID
					var tbId = '';
					if (tableMapping[bookmarkname] != null) {
						tbId = tableMapping[bookmarkname].split('.')[0];
						if (procTableObj[tbId] == null) {
							//添加新表格对象
							procTableObj[tbId] = {};
							procTableObj[tbId]['numCellFlag'] = numCellFlag;
							procTableObj[tbId]['tableObj'] = table;
							procTableObj[tbId]['tableRowIndex'] = tableRowIndex;
							procTableObj[tbId]['tableRow'] = tableRow;
						} else {
							if (procTableObj[tbId]['numCellFlag'] == 1) {
								procTableObj[tbId]['numCellFlag'] = numCellFlag;
							}
							
						}
					}
					//书签所在的行，数据从此行开始
					var curRowIndex = bookmarkCell.RowIndex;
					//表格行数
					var curTableRowCount = table.Rows.Count-curRowIndex+1;
					//数据行数
					var curDataCount = bookVal.length;
					if (curTableRowCount > curDataCount) {
						var endIndex = curRowIndex+curDataCount;
						if (curDataCount == 0) {
							endIndex = curRowIndex + 1;
							//清空书签行所在的数据
							var columnTotalNum = table.Rows.Item(curRowIndex).Cells.Count;
							//获取该行每个单元格中的书签
							var tmpColumnBookMarkArr = [];
							for (var v = 1; v <= table.Rows.Item(curRowIndex).Range.Bookmarks.Count; v ++) {
								var tmpColumnBookmarkObj = table.Rows.Item(curRowIndex).Range.Bookmarks.Item(v);
								var tmpColumnBookmarkName = tmpColumnBookmarkObj.Name;
								var tmpColumnBookmarkRange = tmpColumnBookmarkObj.Range;

								var tmpColumnJson = {};
								tmpColumnJson['name']=tmpColumnBookmarkName;
								tmpColumnJson['range']=tmpColumnBookmarkRange;
								tmpColumnBookMarkArr.push(tmpColumnJson);
							}
							for (var c1=1; c1 <= columnTotalNum; c1++) {
								table.Rows.Item(curRowIndex).Cells.Item(c1).Range.Text = '';
							}
							
							//表格行书签如果被删除，则重新添加
							for (var v1 = 0; v1 < tmpColumnBookMarkArr.length; v1 ++) {
								var deledBookJson = tmpColumnBookMarkArr[v1];
								//如果对应书签不存在，则重新添加书签
								if (!l_Doc.Bookmarks.Exists(deledBookJson['name'])) {
									l_Doc.Bookmarks.Add(deledBookJson['name'],deledBookJson['range']);
								}
							}
							
						}
						//删除行
						for (var t1=table.Rows.Count; t1 >= endIndex; t1--) {
							table.Rows.Item(t1).Delete();
						}
					} else if (curTableRowCount < curDataCount) {
						//增加行
						for (var t2=table.Rows.Count+1; t2 <= curRowIndex+curDataCount-1; t2++) {
							table.Rows.Add();
						}
					}
					
					for (var m = curRowIndex; m < curRowIndex + curDataCount; m ++) {
						var curText = '';
						if (bookVal[m-curRowIndex] != null) {
							curText = bookVal[m-curRowIndex]; 
						}
						var s1 = bookMarkObj.Range;
				
						//单元格赋值 
						table.Rows.Item(m).Cells.Item(curColIndex).Range.Text = curText;
						//如果对应书签不存在，则重新添加书签
						if (!l_Doc.Bookmarks.Exists(bookmarkname)) {
							l_Doc.Bookmarks.Add(bookmarkname,s1);
						}
					}
				}
			} else {
				//判断书签是否在表格中
				var initContainTable = bookMarkObj.Range.Tables.Count;
				if (initContainTable > 0) {
					var tbId = '';
					if (tableMapping[bookmarkname] != null) {
						tbId = tableMapping[bookmarkname].split('.')[0];
					}
					if (tbId != '' && procTableObj[tbId] == null) {
						//删除表格行
						var bookmarkCell = bookMarkObj.Range.Cells.Item(1);
						var table = bookmarkCell.Range.Tables.Item(1);
						var curRowIndex = bookmarkCell.RowIndex;
						//表格行数
						var curTableRowCount = table.Rows.Count-curRowIndex+1;
						//删除行
						for (var t1=table.Rows.Count; t1 > curRowIndex; t1--) {
							table.Rows.Item(t1).Delete();
						}
						//清空书签行所在的数据
						var columnTotalNum = table.Rows.Item(curRowIndex).Cells.Count;
						//获取该行每个单元格中的书签
						var tmpColumnBookMarkArr = [];
						for (var v = 1; v <= table.Rows.Item(curRowIndex).Range.Bookmarks.Count; v ++) {
							var tmpColumnBookmarkObj = table.Rows.Item(curRowIndex).Range.Bookmarks.Item(v);
							var tmpColumnBookmarkName = tmpColumnBookmarkObj.Name;
							var tmpColumnBookmarkRange = tmpColumnBookmarkObj.Range;
	
							var tmpColumnJson = {};
							tmpColumnJson['name']=tmpColumnBookmarkName;
							tmpColumnJson['range']=tmpColumnBookmarkRange;
							tmpColumnBookMarkArr.push(tmpColumnJson);
						}
						
						for (var c1=1; c1 <= columnTotalNum; c1++) {
							table.Rows.Item(curRowIndex).Cells.Item(c1).Range.Text = '';
						}
						
						//表格行书签如果被删除，则重新添加
						for (var v1 = 0; v1 < tmpColumnBookMarkArr.length; v1 ++) {
							var deledBookJson = tmpColumnBookMarkArr[v1];
							//如果对应书签不存在，则重新添加书签
							if (!l_Doc.Bookmarks.Exists(deledBookJson['name'])) {
								l_Doc.Bookmarks.Add(deledBookJson['name'],deledBookJson['range']);
							}
						}
					}
				}
			}
		}

	}
	//填充表格编号列
	for (var tbId in procTableObj) {
		if (procTableObj[tbId]['numCellFlag'] == 1) {
			var numBegin = procTableObj[tbId]['tableRowIndex'];
			for (var n = numBegin; n < procTableObj[tbId]['tableRow']+numBegin; n ++) {
				//procTableObj[tbId]['tableObj'].cell(n,1).Range.Text = n-numBegin+1;
				procTableObj[tbId]['tableObj'].Rows.Item(n).Cells.Item(1).Range.Text = n-numBegin+1;
			}
		}
	}
	
	if (l_Doc.TrackRevisions == false) {
		//开启修订模式
		OnOpenRevisions();
		//显示留痕
	}
	
	//隐藏留痕
	showHideRevisions(false);
	
}

/**
 * 获取当前文档中的所有表格，将同一表格的书签归为一组
 * @param l_Doc
 * @param mapping
 * @returns 表格书签数组
 */
function getBookMarksGroupByTable(l_Doc,mapping) {
	var tableCount = l_Doc.Tables.Count;
	var tableMapping = {};
	for (var m = 1; m <= tableCount; m ++) {
		var table = l_Doc.Tables.Item(m);
		var rows = table.Rows;
		//对表格中含有书签的行进行处理
		for (var n = 1; n <= table.Rows.Count; n ++) {
			if (rows.Item(n).Range.Bookmarks.Count > 0) {
				//获取该行每个单元格中的书签
				for (var v = 1; v <= rows.Item(n).Range.Bookmarks.Count; v ++) {
					var bookmarkObj = rows.Item(n).Range.Bookmarks.Item(v);
					var bookmarkName = bookmarkObj.Name;
					if (typeof mapping[bookmarkName] != 'undefined' && mapping[bookmarkName] != '') {
						//后台配置了书签映射
						tableMapping[bookmarkName] = m + '_' + mapping[bookmarkName];
					}
					
				}
			}
		}
	}
	return tableMapping;
}


/**
 * 新建空白文件
 * @returns
 */
function btnNewAgreementFile() {
	var wpsApp = wps.WpsApplication();
	var doc = wpsApp.ActiveDocument;
	if (doc) {
		var selection = wpsApp.ActiveWindow.Selection;
		selection.WholeStory();
		selection.Delete();
		//删除页眉页脚
		var sections = doc.Sections;
		for(var i = 1; i<= sections.Count; i++) {
			var curSelection = sections.Item(i);
			var myHeadRange = curSelection.Headers.Item(1).Range;
			myHeadRange.Delete();
			for (var k = 1; k < myHeadRange.ParagraphFormat.Borders.Count;k++){
				myHeadRange.ParagraphFormat.Borders.Item(k).LineStyle = 0;
			}
			
			var myFootRange = curSelection.Footers.Item(1).Range;
			myFootRange.Delete();
		}
	}

}

/**
 * 打开本地文件
 * @returns
 */
function btnOpenLocalgreementFile() {
    var wpsApp = wps.WpsApplication();
    wps.PluginStorage.setItem("openLocalFlag", "1");
    wpsApp.Dialogs.Item(80).Show();
}

/**
 * 暂存文件
 * @returns
 */
function btnTempSaveAgreementFile() {
	var l_doc = wps.WpsApplication().ActiveDocument;
	if (l_doc) {
		l_doc.Save();
	}
}

/**
 * 保存提交文件
 * @returns
 */
function btnSubmitAgreementFile() {
	var l_doc = wps.WpsApplication().ActiveDocument;
    if (!l_doc) {
        alert("空文档不能保存！");
        return;
    }
    //非OA文档，不能上传到OA
    if (pCheckIfOADoc() == false) {
        alert("非系统打开的文档，不能直接上传到系统！");
        return;
    }

    //如果是OA打开的文档，并且设置了保护的文档，则不能再上传到OA服务器
    if (pISOADocReadOnly(l_doc)) {
        wps.alert("系统设置了保护的文档，不能再提交到系统后台。");
        return;
    }
	
	//1，非格式合同审批状态时需要先备份当前文档，因为当前文档是有痕迹的
	//2，备份后对当前文档进行清稿操作，生成比对文件并保存到服务器
	//3，恢复备份的当前文档，进行提交操作，可参照kmAgreementLimitedArea_include.jsp
	var ekpAttMainKey = GetDocParamsValue(l_doc, "ekpAttMainKey");
	var ekpModelName = GetDocParamsValue(l_doc, "ekpModelName");
	var ekpFdIsSample = GetDocParamsValue(l_doc, "ekpFdIsSample");
	var ekpFdMapping = GetDocParamsValue(l_doc, "ekpFdMapping");
	var ekpFdAttmainStatus = GetDocParamsValue(l_doc, "ekpFdAttmainStatus");
	var ekpFdModelStatus = GetDocParamsValue(l_doc, "ekpFdModelStatus");
	var saveFlag = true;
	if (ekpAttMainKey == "mainOnline" && ekpModelName == "com.landray.kmss.km.agreement.model.KmAgreementApply") {
		if (ekpFdIsSample == "false" && ekpFdModelStatus != "10") {
			saveFlag = false;
			//检查系统临时文件目录是否能访问
		    if (wps.Env && wps.Env.GetTempPath) {
		    	var tempFileName = wps.Env.GetTempPath() + "/OA_Agreement_" + currentTime();
		    	l_doc.SaveAs2(tempFileName);
		    	
		    	wps.PluginStorage.setItem("agreementTempFileName", l_doc.FullName);
		    	
		        var l_Params = wps.PluginStorage.getItem(l_doc.DocID);
		        if (l_Params) {
		            //保存文档参数
		        	var l_objParams = JSON.parse(l_Params);
		        	wps.PluginStorage.setItem("agreementTempFileName_doc", JSON.stringify(l_objParams));
		        }
		    	
		        //接受所有修订
		        if (l_doc.Revisions.Count >= 1) {
		        	l_doc.AcceptAllRevisions();
		        }
		        //去除所有批注
		        if (l_doc.Comments.Count >= 1) {
		        	l_doc.RemoveDocumentInformation(wps.Enum.wdRDIComments);
		        }
		        //删除所有ink墨迹对象
		        pDeleteAllInkObj(l_doc);
		        l_doc.TrackRevisions = false; //关闭修订模式
		        
		        
		        var l_FieldName = GetDocParamsValue(l_doc, constStrEnum.uploadFieldName); //上载到后台的业务方自定义的字段名称
		        if (l_FieldName == "") {
		            l_FieldName = wps.PluginStorage.getItem(constStrEnum.DefaultUploadFieldName); // 默认为‘file’
		        }

		        var l_UploadName = GetDocParamsValue(l_doc, constStrEnum.uploadFileName); //设置OA传入的文件名称参数
		        if (l_UploadName == "") {
		            l_UploadName = l_doc.Name; //默认文件名称就是当前文件编辑名称
		        }
		        
		        var l_uploadPath = GetDocParamsValue(l_doc, constStrEnum.uploadPath); // 文件上载路径
		        if (l_uploadPath == "") {
		        	wps.alert("系统未传入文件上载路径，不能执行上传操作！");
		            return;
		        }
		        
		        //将上传路径中的uploadWpsOaAssit替换为clearRevisionWpsOaAssit
		        l_uploadPath = l_uploadPath.replace("uploadWpsOaAssit","clearRevisionWpsOaAssit");
		        
		        //当前文档进行了保存操作，因此设置为落地模式
		        DoSetOADocLandMode(l_doc, EnumDocLandMode.DLM_LocalDoc);
		        
		        //将清稿后的文档保存到服务器
		    	var tempClearFileName = wps.Env.GetTempPath() + "/OA_Agreement_Clear_" + currentTime();
		    	//保存清稿后的文件
		    	l_doc.SaveAs2(tempClearFileName);
		    	wps.PluginStorage.setItem("agreementClearFileName", l_doc.FullName);
	            //调用OA助手的上传方法
		        var l_DocPath = l_doc.FullName; // 文件所在路径
	            UploadFile(l_UploadName, l_DocPath, l_uploadPath, l_FieldName, OnAgreementSuccess, OnAgreementFail);
		    } else {
		    	alert("获取系统临时目录出错！不能保存文档！请联系系统开发商。");
		    }
		}
	}
	
	if (saveFlag) {
		OnBtnSaveToServer();
	}

}

/**
 * 调用文件上传到OA服务端时，
 * @param {*} resp 
 */
function OnAgreementSuccess(resp) {
    console.log("成功上传服务端后的回调：" + resp)
    console.log(resp)
    
    var resultResp=eval("("+resp+")");
    var bodyStr=resultResp.Body;
    var resultJson;
    if (bodyStr) {
    	var resultBase64=window.atob(bodyStr);
    	resultJson=eval("("+resultBase64+")");
    } else {
    	resultJson = resultResp;
    }

    if(resultJson.flag=="ok"){
    	var l_doc = wps.WpsApplication().ActiveDocument;
        if (l_doc) {
            l_doc.Close(-1); //保存文档后关闭
        }
        //加载备份文档，并且保存到OA
        var tempFileName = wps.PluginStorage.getItem("agreementTempFileName");
        var t_Params = wps.PluginStorage.getItem("agreementTempFileName_doc");
        if (tempFileName && t_Params) {
        	var bak_Doc = wps.WpsApplication().Documents.Open(tempFileName, false, false, false);
        	if (bak_Doc) {
        		var t_objParams = JSON.parse(t_Params);
        	    //把属性值整体再写回原来的文档ID中
        	    wps.PluginStorage.setItem(bak_Doc.DocID, JSON.stringify(t_objParams));
        		//激活当前文档
        		bak_Doc.Activate();
        		OnBtnSaveToServer();
        	}
        } else {
        	wps.alert("备份路径为空，无法加载原始文档！");
        }
    }else{
    	wps.alert("上传失败！");
    }
    
}

function OnAgreementFail(resp) {
    alert("文件上传失败！");
}

/**
 * 插入书签
 * @returns
 */
function btnAgreementCallBookMark() {
    var wpsApp = wps.WpsApplication();
    var l_doc = wpsApp.ActiveDocument;
    if (l_doc) {
    	wpsApp.Dialogs.Item(168).Show();
    }
}

/**
 * 插入批注
 * @returns
 */
function btnAgreementCallComments() {
    var wpsApp = wps.WpsApplication();
    var l_doc = wpsApp.ActiveDocument;
    if (l_doc) {
    	var selection = l_doc.ActiveWindow.Selection; // 活动窗口选定范围或插入点
    	//折叠所选区域 
    	selection.Collapse(1);
    	l_doc.Comments.Add(selection.Range);
    }
}

/**
 * 导航窗格
 * @returns
 */
function btnAgreementCallNavigationPane() {
	var l_doc = wps.WpsApplication().ActiveDocument;
	if (l_doc) {
		l_doc.ActiveWindow.DocumentMap = true;
	}
}

/**
 * 显示隐藏留痕
 * @returns
 */
function showHideRevisions(bShowRevision) {
	var l_doc = wps.WpsApplication().ActiveDocument;
	if (l_doc) {
		var l_v = l_doc.ActiveWindow.View;
	    l_v.ShowInsertionsAndDeletions = bShowRevision;
	    l_v.ShowRevisionsAndComments = bShowRevision; //如果为True，则 WPS 显示使用“修订”功能对文档所作的修订和批注
	    l_v.RevisionsBalloonShowConnectingLines = bShowRevision; //如果为 True，则 WPS 显示从文本到修订和批注气球之间的连接线
	    
	    if (bShowRevision) {
	    	wps.WpsApplication().CommandBars.ExecuteMso("KsoEx_RevisionCommentModify_Disable"); //去掉修改痕迹信息框中的接受修订和拒绝修订勾叉，使其不可用
	    	l_doc.ActiveWindow.ActivePane.View.RevisionsMode = 2; //2为不支持气泡显示。
	    }
	}
}


/**
 * protectType: '', 文档保护模式(   -1：不启用保护模式，
 *                                  0：只允许对现有内容进行修订，
 *                                  1：只允许添加批注，
 *                                  2：只允许修改窗体域，
 *                                  3：只读)
 * @param {*} protectType 
 * @param {*} doc 
 */
function SetAgreementDocProtect(doc, protectType, ProtectPassword) {
    if (!doc) return; //校验文档是否存在
    if ([0, 1, 2, 3].indexOf(protectType) !== -1) {
        // 保护文档如果之前有被保护，再次保护会出问题，需要先解除保护
    	if (ProtectPassword) {
    		doc.Unprotect(ProtectPassword);
    	} else {
    		doc.Unprotect();
    	}
        
        // ** 方法参数
        // ** Protect(Type As WdProtectionType, [NoReset], [Password], [UseIRM], [EnforceStyleLock])
        doc.Protect(protectType, false, ProtectPassword, false);
    }
    return;
}

/**
 * 切换留痕状态
 * @returns
 */
function btnAgreementCallRevisions() {
	var l_doc = wps.WpsApplication().ActiveDocument;
	if (l_doc) {
		var bShowRevision = false;
		var l_v = l_doc.ActiveWindow.View;
		if (l_v.ShowInsertionsAndDeletions == false) {
			bShowRevision = true;
		}

	    l_v.ShowInsertionsAndDeletions = bShowRevision;
	    l_v.ShowRevisionsAndComments = bShowRevision; //如果为True，则 WPS 显示使用“修订”功能对文档所作的修订和批注
	    l_v.RevisionsBalloonShowConnectingLines = bShowRevision; //如果为 True，则 WPS 显示从文本到修订和批注气球之间的连接线
	    
	    if (bShowRevision) {
	    	wps.WpsApplication().CommandBars.ExecuteMso("KsoEx_RevisionCommentModify_Disable"); //去掉修改痕迹信息框中的接受修订和拒绝修订勾叉，使其不可用
	    	l_doc.ActiveWindow.ActivePane.View.RevisionsMode = 2; //2为不支持气泡显示。
	    }
	}
}

/**
 * 审阅窗格
 * @returns
 */
function btnAgreementCallReviewPane() {
	var l_doc = wps.WpsApplication().ActiveDocument;
	if (l_doc) {
		l_doc.ActiveWindow.View.SplitSpecial = 18;
	}
}

/**
 * 页眉页脚
 * @returns
 */
function btnAgreementCallHeadFoot() {
	var l_doc = wps.WpsApplication().ActiveDocument;
	if (l_doc) {
		l_doc.ActiveWindow.ActivePane.View.SeekView = 9;
	}
}

/**
 * 打印设置
 * @returns
 */
function btnAgreementCallPrint() {
    var wpsApp = wps.WpsApplication();
    var doc = wpsApp.ActiveDocument;
    if (!doc) {
        return;
    }
    wpsApp.Dialogs.Item(wps.Enum.wdDialogFilePrint).Show();
}

/**
 * 二维码
 * @returns
 */
function btnAgreementCallQRCode() {
    var ekpServerPrefix = wps.PluginStorage.getItem("ekp_server_prefix");
    var token = wps.PluginStorage.getItem('ekp_wps_oaassist_token');

    let url = ekpServerPrefix + "/km/agreement/wps/oaassist/taskPane/qrCode/QRCode.jsp?wpsOasisstToken=" + token;
	OnShowDialog(url, "插入二维码", 550, 210, null, 'true');
}

/**
 * 文件来源
 * @returns
 */
function pGetAgreementFileSrc() {
    var l_Doc = wps.WpsApplication().ActiveDocument;
    if (!l_Doc) {
        return "";
    }

    var l_strLabel = "文件来源：合同管理"; //初始化
    return l_strLabel;
}

/**
 * 文档状态
 * @returns
 */
function pGetAgreementFileStatus() {
	return pGetOADocLabel();
}

/**
 * 操作者
 * @returns
 */
function pGetAgreementFileUser() {
    var l_doc = wps.WpsApplication().ActiveDocument;
    if (!l_doc) return "";

    var l_strUserName = "操作者：";
    if (pCheckIfOADoc() == true) { // OA文档，获取OA用户名
        var userName = GetDocParamsValue(l_doc, constStrEnum.userName);
        var tmp_UserName = userName == "" ? "未设置" : userName;
        l_strUserName = l_strUserName + tmp_UserName;
    } else {
        //非OA传来的文档，则按WPS安装后设置的用户名显示
        l_strUserName = l_strUserName + wps.PluginStorage.getItem(constStrEnum.WPSInitUserName);
    }

    return l_strUserName;
}

/**
 * 纯文本内容控件
 * @returns
 */
function btnAgreementCallTextCtrl() {
    var wpsApp = wps.WpsApplication();
    var l_doc = wpsApp.ActiveDocument;
    if (l_doc) {
    	var selection = l_doc.ActiveWindow.Selection; // 活动窗口选定范围或插入点
    	selection.Range.ContentControls.Add (1);
    }
}

/**
 * 范本框架
 * @returns
 */
function btnAgreementCallModelFrame() {
	//wps.alert("暂不可用");
}

/**
 * 条款库
 * @returns
 */
function btnAgreementCallClauseLib() {
	//判断是否存在条款库模块
	var l_doc = wps.WpsApplication().ActiveDocument;
	var hasClause = GetDocParamsValue(l_doc, "hasClause");
	if (hasClause && hasClause == 'true') {
		taskPaneAgreement("/clause/clauseIndex",true,"条款库",2);
	} else {
		wps.alert("条款库模块未加载");
	}
}

/**
 * 范本文档库
 * @returns
 */
function btnAgreementCallModelLib() {
	taskPaneAgreement("/model/modelIndex",true,"范本文档库",1);
}


//打开右侧窗口
function taskPaneAgreement(page, isReload, title, index) {
    var ekpServerPrefix = wps.PluginStorage.getItem("ekp_server_prefix");
    var token = wps.PluginStorage.getItem('ekp_wps_oaassist_token');

    let url = ekpServerPrefix + "/km/agreement/wps/oaassist/taskPane" + page + ".jsp?wpsOasisstToken=" + token;

    //创建taskpane，只创建一次
    let id = wps.PluginStorage.getItem(constStrEnum.taskpaneid+"_"+index);
    if (id) {
        if(isReload){
            let tp = wps.GetTaskPane(id);
            tp.Width = 350;
            tp.Visible = true;
            tp.Navigate(url);
        }
    } else {
        let tp = wps.CreateTaskPane(url, title);
        if (tp) {
            tp.DockPosition = WPS_Enum.msoCTPDockPositionRight;  //这里可以设置taskapne是在左边还是右边
            tp.Width = 350;
            tp.Visible = true;
            wps.PluginStorage.setItem(constStrEnum.taskpaneid+"_"+index, tp.ID);
        }
    }
}

/**
 * 合同模块保存的特殊处理
 * @param l_doc
 * @returns
 */
function saveAgreementProc(doc) {
	var ekpAttMainKey = GetDocParamsValue(doc, "ekpAttMainKey");
	var ekpModelName = GetDocParamsValue(doc, "ekpModelName");
	var ekpFdIsSample = GetDocParamsValue(doc, "ekpFdIsSample");
	var ekpFdMapping = GetDocParamsValue(doc, "ekpFdMapping");
	var ekpFdAttmainStatus = GetDocParamsValue(doc, "ekpFdAttmainStatus");
	var formDataStr = GetDocParamsValue(doc, "formDataStr");
	var ekpProtectType = GetDocParamsValue(doc, "ekpProtectType");
	var ekpPassword = GetDocParamsValue(doc, "ekpPassword");
	var numberProtectType = -1;
	if (ekpProtectType) {
		numberProtectType = parseInt(ekpProtectType);
	}
	
	
}
