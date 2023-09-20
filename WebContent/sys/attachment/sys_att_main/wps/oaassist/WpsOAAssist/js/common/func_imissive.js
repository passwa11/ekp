/**
 * 作用：保存底稿
 * 返回值：无
 */
function saveRedAttTrac() {
	var doc = wps.WpsApplication().ActiveDocument;
	var trac = GetDocParamsValue(doc,"ekp_wps_atttrac");
	var fdModelName = GetDocParamsValue(doc, "ekpModelName");
	if(trac=="true" &&fdModelName!="com.landray.kmss.km.imissive.model.KmImissiveSignMain"){
	var nodevalue = GetDocParamsValue(doc, "nodevalue");
	var modelId = GetDocParamsValue(doc, "ekpModelId");
	var fdKey = GetDocParamsValue(doc, "ekpAttMainKey");
	var token = GetDocParamsValue(doc, "wpsoaassistToken");
	var prefix = GetDocParamsValue(doc, "ekpServerPrefix");
    var urlImssive = prefix+'/km/imissive/km_imissive_redhead_template/kmImissiveRedHeadTemplate.do?method=saveRedAttTrac&wpsOasisstToken='+token;
		$.ajax({
		    type:"post",
		    url:urlImssive,
		    data:{"modelId":modelId,"nodevalue":nodevalue,"fdKey":fdKey,"fdModelName":fdModelName},
		    dataType:"json",
		    async:false,
		    success:function(data){
				console.log(data.msg);
			}
		 });
	SetDocParamsValue(doc,"ekp_wps_atttrac","");
	}
}

/**
 * 作用：易企签署跳转
 */
function SignTrueShow(){
	var doc = wps.WpsApplication().ActiveDocument;
	var modelId = GetDocParamsValue(doc, "ekpModelId");
	var fdKey = GetDocParamsValue(doc, "ekpAttMainKey");
	var fdModelName = GetDocParamsValue(doc, "ekpModelName");
	var token = GetDocParamsValue(doc, "wpsoaassistToken");
	var prefix = GetDocParamsValue(doc, "ekpServerPrefix");
	var name=fdModelName.substring(fdModelName.lastIndexOf(".")+1);
	if("KmImissiveSendMain"==name){
		SignTrueSend(token,prefix,modelId,fdKey);
	}
	if("KmImissiveReceiveMain"==name){
		SignTrueReceive(token,prefix,modelId,fdKey);
	}
	if("KmImissiveSignMain"==name){
		SignTrueSign(token,prefix,modelId,fdKey);
	}
	
}

function SignTrueSend(token,prefix,modelId,key){
		 var fileTypeUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=validateFileType&signId="+modelId+"&wpsOasisstToken="+token;
		 var queryStatusUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=queryStatus&signId="+modelId+"&wpsOasisstToken="+token;
		 var validateOnceUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=validateOnce&signId="+modelId+"&wpsOasisstToken="+token;;
		 $.ajax({
				url : fileTypeUrl,
				type : 'post',
				data : {},
				dataType : 'text',
				async : false,     
				error : function(){
					alert('请求出错');
				} ,   
				success:function(data){
					if(data == "true"){
						 $.ajax({
								url : queryStatusUrl,
								type : 'post',
								data : {},
								dataType : 'text',
								async : true,     
								error : function(){
									alert('请求出错');
								} ,   
								success : function(data) {
									if(data == "false"){
										$.ajax({
											url : validateOnceUrl,
											type : 'post',
											data : {},
											dataType : 'text',
											async : false,     
											error : function(){
												alert('请求出错');
											} ,   
											success:function(data){
												if(data == "true"){
													alert("当前发文已经发送过易企签签署，整个签署事件未完成，请等待完成后在发起签署！！");
												}else{
													var obj_ = document.getElementById("JGWebOffice_"+key);
												    if(obj_){
														if(Attachment_ObjectInfo[key]){
															 if(Attachment_ObjectInfo[key].ocxObj){
																 var rFlag=Attachment_ObjectInfo[key].ocxObj.WebSave();
																 if(rFlag){
																	OnShowDialog("imissYqq/extendinfo_send.jsp?&wpsOasisstToken="+token+"&modelId="+modelId, "印章", 2300, 900);
																 }
															 }
														}
												    }else{
												    	OnShowDialog("imissYqq/extendinfo_send.jsp?&wpsOasisstToken="+token+"&modelId="+modelId, "印章", 2300, 900);
												    }
												}
											}
										});
									}else{
										OnShowDialog(data, "印章", 2300, 900,null,"true");
									}
								}
							}); 
					}else{
						dialog.alert("易企签不支持该文件类型("+data+")签署，请上传以下文件类型(.pdf;.doc;.xls;.ppt;.docx;.xlsx;.pptx;.jpg;.jpeg;.png;)");
					}
				}
			});
}

function SignTrueReceive(token,prefix,modelId,key){
		 var fileTypeUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=validateReceiveFileType&signId="+modelId+"&wpsOasisstToken="+token;
		 var queryStatusUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=queryStatus&signId="+modelId+"&wpsOasisstToken="+token;
		 var validateOnceUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=validateOnce&signId="+modelId+"&wpsOasisstToken="+token;;
		 $.ajax({
				url : fileTypeUrl,
				type : 'post',
				data : {},
				dataType : 'text',
				async : false,     
				error : function(){
					alert('请求出错');
				} ,   
				success:function(data){
					if(data == "true"){
						 $.ajax({
								url : queryStatusUrl,
								type : 'post',
								data : {},
								dataType : 'text',
								async : true,     
								error : function(){
									alert('请求出错');
								} ,   
								success : function(data) {
									if(data == "false"){
										$.ajax({
											url : validateOnceUrl,
											type : 'post',
											data : {},
											dataType : 'text',
											async : false,     
											error : function(){
												alert('请求出错');
											} ,   
											success:function(data){
												if(data == "true"){
													alert("当前收文已经发送过易企签签署，整个签署事件未完成，请等待完成后在发起签署！！");
												}else{
													var obj_ = document.getElementById("JGWebOffice_"+key);
												    if(obj_){
														if(Attachment_ObjectInfo[key]){
															 if(Attachment_ObjectInfo[key].ocxObj){
																 var rFlag=Attachment_ObjectInfo[key].ocxObj.WebSave();
																 if(rFlag){
																	OnShowDialog("imissYqq/extendinfo_receive.jsp?&wpsOasisstToken="+token+"&modelId="+modelId, "印章", 2300, 900);
																 }
															 }
														}
												    }else{
												    	OnShowDialog("imissYqq/extendinfo_receive.jsp?&wpsOasisstToken="+token+"&modelId="+modelId, "印章", 2300, 900);
												    }
												}
											}
										});
									}else{
										OnShowDialog(data, "印章", 2300, 900,null,"true");
									}
								}
							}); 
					}else{
						dialog.alert("易企签不支持该文件类型("+data+")签署，请上传以下文件类型(.pdf;.doc;.xls;.ppt;.docx;.xlsx;.pptx;.jpg;.jpeg;.png;)");
					}
				}
			});
}

function SignTrueSign(token,prefix,modelId,key){
		 var fileTypeUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=validateSignFileType&signId="+modelId+"&wpsOasisstToken="+token;
		 var queryStatusUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=queryStatus&signId="+modelId+"&wpsOasisstToken="+token;
		 var validateOnceUrl = prefix+"/km/imissive/km_imissive_out_sign/kmImissiveOutSign.do?method=validateOnce&signId="+modelId+"&wpsOasisstToken="+token;;
		 $.ajax({
				url : fileTypeUrl,
				type : 'post',
				data : {},
				dataType : 'text',
				async : false,     
				error : function(){
					alert('请求出错');
				} ,   
				success:function(data){
					if(data == "true"){
						 $.ajax({
								url : queryStatusUrl,
								type : 'post',
								data : {},
								dataType : 'text',
								async : true,     
								error : function(){
									alert('请求出错');
								} ,   
								success : function(data) {
									if(data == "false"){
										$.ajax({
											url : validateOnceUrl,
											type : 'post',
											data : {},
											dataType : 'text',
											async : false,     
											error : function(){
												alert('请求出错');
											} ,   
											success:function(data){
												if(data == "true"){
													alert("当前签报已经发送过易企签签署，整个签署事件未完成，请等待完成后在发起签署！！");
												}else{
													var obj_ = document.getElementById("JGWebOffice_"+key);
												    if(obj_){
														if(Attachment_ObjectInfo[key]){
															 if(Attachment_ObjectInfo[key].ocxObj){
																 var rFlag=Attachment_ObjectInfo[key].ocxObj.WebSave();
																 if(rFlag){
																	OnShowDialog("imissYqq/extendinfo_sign.jsp?&wpsOasisstToken="+token+"&modelId="+modelId, "印章", 2300, 900);
																 }
															 }
														}
												    }else{
												    	OnShowDialog("imissYqq/extendinfo_sign.jsp?&wpsOasisstToken="+token+"&modelId="+modelId, "印章", 2300, 900);
												    }
												}
											}
										});
									}else{
										OnShowDialog(data, "印章", 2300, 900,null,"true");
									}
								}
							}); 
					}else{
						dialog.alert("易企签不支持该文件类型("+data+")签署，请上传以下文件类型(.pdf;.doc;.xls;.ppt;.docx;.xlsx;.pptx;.jpg;.jpeg;.png;)");
					}
				}
			});
}
/**
 * 作用：初始化页面
 * 返回值：无
 */
function initRevision(doc, insertbook) {
	//模板不需要走初始化
	var fdModelName = GetDocParamsValue(doc, "ekpModelName");
	if(fdModelName.indexOf("Template") >= 0){
		return;
	}
	var newFlag = GetDocParamsValue(doc, "newFlag");//是否是新建页面
	var forceRevisions = GetDocParamsValue(doc, "forceRevisions");//是否是强制不留痕
	
	var openType = GetDocParamsValue(doc, "openType");
	var l_ProtectType = openType['protectType'];
	//打开文档后，根据保护类型设置文档保护
    if (l_ProtectType > -1){
		// -1 :不设置文档保护 
        SetOADocProtectImissive(doc, l_ProtectType);
	} else{
		if(insertbook){
			
		} else {
			pInsertRInedHeadbook(doc);
		}
		
	}
	if("true" != newFlag && forceRevisions!="true"){
		//默认打开修订状态
		let bFlag = wps.PluginStorage.getItem(constStrEnum.RevisionEnableFlag);
	    wps.PluginStorage.setItem(constStrEnum.RevisionEnableFlag, !bFlag);
	    //通知wps刷新以下几个按钮的状态
		DoOADocOpenRevision(doc,true,true);
		wps.WpsApplication().ActiveDocument.Saved=true;
	}else{
		let bFlag = wps.PluginStorage.getItem(constStrEnum.RevisionEnableFlag)
        wps.PluginStorage.setItem(constStrEnum.RevisionEnableFlag, !bFlag)
        OnCloseRevisions();
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
function SetOADocProtectImissive(doc, protectType) {
    if (!doc) return; //校验文档是否存在
    if ([0, 1, 2, 3].indexOf(protectType) !== -1) {
        // 保护文档如果之前有被保护，再次保护会出问题，需要先解除保护
        doc.Unprotect();
		pInsertRInedHeadbook(doc);
        // ** 方法参数
        // ** Protect(Type As WdProtectionType, [NoReset], [Password], [UseIRM], [EnforceStyleLock])
        doc.Protect(protectType, false, "", false);
    }
    return;
}

/**
  *设置书签信息
 */
function pInsertRInedHeadbook(doc) {
	var modelId = GetDocParamsValue(doc, "ekpModelId");
	var fdKey = GetDocParamsValue(doc, "ekpAttMainKey");
	var fdModelName = GetDocParamsValue(doc, "ekpModelName");
	var token = GetDocParamsValue(doc, "wpsoaassistToken");
	var prefix = GetDocParamsValue(doc, "ekpServerPrefix");
	var bookMarks = GetDocParamsValue(doc, "bookMarks");
	var bookmark=[];
	var url = prefix+'/km/imissive/km_imissive_redhead_template/kmImissiveRedHeadTemplate.do?method=queryBooksMsg&wpsOasisstToken='+token;
    $.ajax({
        type:"post",
        url:url,
        data:{"modelId":modelId,"bookMarks":bookMarks,"fdKey":fdKey,"fdModelName":fdModelName},
        dataType:"json",
        async:false,
        success:function(data){
        	for ( var i in data) {
        		if (data[i].bookmark!=null && data[i].bookmark!=undefined && data[i].bookmark!="") {
        			bookmark.push(data[i].bookmark);
				}
			}
		}
     });
    var bookMarks = doc.Bookmarks;
    var wpsApp = wps.WpsApplication();
    var activeDoc = wps.WpsApplication().ActiveDocument;
    var selection = wpsApp.ActiveWindow.Selection;
    // 准备以非批注的模式插入红头文件(剪切/粘贴等操作会留有痕迹,故先关闭修订)
    activeDoc.TrackRevisions = false;
 	// 轮询插入书签
	var imssiveRed = GetDocParamsValue(doc, "bookMarks");
	imssiveRed = JSON.parse(imssiveRed);
	//bookmark 书签信息
	//bookmark[key] 书签名称
	//imssiveRed 书签value json
	for (var key in bookmark) {
		var bookVal = bookmark[key];
		//方式一：
		var book= bookMarks.Item(bookVal);
		if(book){
			let bookStart = book.Range.Start;
			let bookEnd = book.Range.End;
			let start = doc.Range().End;

			if(imssiveRed[bookVal]!=null && imssiveRed[bookVal]!=undefined){
				book.Range.Text=imssiveRed[bookVal];
			}
			let end = doc.Range().End;
			let range=book.Range;
			if (!bookMarks.Exists(bookVal)){
				//bookMarks.Add(bookVal, range.SetRange(bookStart, bookEnd + (end - start)))
				var book=bookMarks.Add(bookVal,doc.Range(0,0));
				book.Start=bookStart;
				book.End= bookEnd + (end - start);
			}
		}
		// }
	}

     //恢复修订模式(根据传入参数决定)
    var l_revisionCtrl = GetDocParamsValue(activeDoc, constStrEnum.revisionCtrl);
    activeDoc.TrackRevisions = l_revisionCtrl == "" ? false : l_revisionCtrl.bOpenRevision;
}

/**
 * 作用：痕迹稿
 * 返回值：无
 */
function saveClearDraftInit(doc) {
	var nodevalue = GetDocParamsValue(doc, "nodevalue");
	var modelId = GetDocParamsValue(doc, "ekpModelId");
	var fdKey = GetDocParamsValue(doc, "ekpAttMainKey");
	var fdModelName = GetDocParamsValue(doc, "ekpModelName");
	var token = GetDocParamsValue(doc, "wpsoaassistToken");
	var prefix = GetDocParamsValue(doc, "ekpServerPrefix");
	var urlImssive = prefix+'/km/imissive/km_imissive_redhead_template/kmImissiveRedHeadTemplate.do?method=saveClearDraftInit&wpsOasisstToken='+token;
	$.ajax({
		   type:"post",
		   url:urlImssive,
		   data:{"modelId":modelId,"nodevalue":nodevalue,"fdKey":fdKey,"fdModelName":fdModelName},
		   dataType:"json",
		   async:false,
		   success:function(data){
			if(data.msg == "true"){
				//接受所有修订
			    if (doc.Revisions.Count >= 1) {
			        doc.AcceptAllRevisions();
			    }
			    //去除所有批注
			    if (doc.Comments.Count >= 1) {
			        doc.RemoveDocumentInformation(wps.Enum.wdRDIComments);
			    }
			
			    //删除所有ink墨迹对象
			    pDeleteAllInkObj(doc);
				//保存
				OnSaveToServerQg().then(function(){
					setTimeout(function(){
						saveClearDraftAtt(doc);
					},100);
				});

			}
			console.log(data.msg);
		}
	});
}

/**
 * 作用：清稿
 * 返回值：无
 */
function saveClearDraftAtt(doc) {
		var nodevalue = GetDocParamsValue(doc, "nodevalue");
		var modelId = GetDocParamsValue(doc, "ekpModelId");
		var fdKey = GetDocParamsValue(doc, "ekpAttMainKey");
		var fdModelName = GetDocParamsValue(doc, "ekpModelName");
		var token = GetDocParamsValue(doc, "wpsoaassistToken");
		var prefix = GetDocParamsValue(doc, "ekpServerPrefix");
		var urlImssive = prefix+'/km/imissive/km_imissive_redhead_template/kmImissiveRedHeadTemplate.do?method=saveClearDraftAtt&wpsOasisstToken='+token;
		$.ajax({
		    type:"post",
		    url:urlImssive,
		    data:{"modelId":modelId,"nodevalue":nodevalue,"fdKey":fdKey,"fdModelName":fdModelName},
		    dataType:"json",
		    async:false,
		    success:function(data){
				console.log(data.msg);
			}
		 });
}

//保存到OA后台服务器
function OnSaveToServerQg() {
	var dtd = $.Deferred();
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

    /**
     * 参数定义：OAAsist.UploadFile(name, path, url, field,  "OnSuccess", "OnFail")
     * 上传一个文件到远程服务器。
     * name：为上传后的文件名称；
     * path：是文件绝对路径；
     * url：为上传地址；
     * field：为请求中name的值；
     * 最后两个参数为回调函数名称；
     */
    var l_uploadPath = GetDocParamsValue(l_doc, constStrEnum.uploadPath); // 文件上载路径
    if (l_uploadPath == "") {
        wps.alert("系统未传入文件上载路径，不能执行上传操作！");
        return;
    }

    /*var l_showConfirm = wps.PluginStorage.getItem(constStrEnum.Save2OAShowConfirm)
    if (l_showConfirm) {
        if (!wps.confirm("先保存文档，并开始上传到系统后台，请确认？")) {
            return;
        }
    }*/

    var l_FieldName = GetDocParamsValue(l_doc, constStrEnum.uploadFieldName); //上载到后台的业务方自定义的字段名称
    if (l_FieldName == "") {
        l_FieldName = wps.PluginStorage.getItem(constStrEnum.DefaultUploadFieldName); // 默认为‘file’
    }

    var l_UploadName = GetDocParamsValue(l_doc, constStrEnum.uploadFileName); //设置OA传入的文件名称参数
    if (l_UploadName == "") {
        l_UploadName = l_doc.Name; //默认文件名称就是当前文件编辑名称
    }

    var l_DocPath = l_doc.FullName; // 文件所在路径

    if (pIsOnlineOADoc(l_doc) == false) {
        //对于本地磁盘文件上传OA，先用Save方法保存后，再上传
        //设置用户保存按钮标志，避免出现禁止OA文件保存的干扰信息
        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.OADocSave);
        l_doc.Save(); //执行一次保存方法
        //设置用户保存按钮标志
        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.NoneOADocSave);
        //落地文档，调用UploadFile方法上传到OA后台
        try {
            //调用OA助手的上传方法
            UploadFile(l_UploadName, l_DocPath, l_uploadPath, l_FieldName, OnUploadToServerSuccess, OnUploadToServerFail);
        } catch (err) {
            alert("上传文件失败！请检查系统上传参数及网络环境！");
        }
    } else {
        // 不落地的文档，调用 Document 对象的不落地上传方法
        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.OADocSave);
        try {
			wps.ApiEvent.RemoveApiEventListener("DocumentBeforeClose", OnDocumentBeforeClose)
            //调用不落地上传方法
            l_doc.SaveAsUrl(l_UploadName, l_uploadPath, l_FieldName, "OnUploadToServerSuccess", "OnUploadToServerFail");
			wps.ApiEvent.AddApiEventListener("DocumentBeforeClose", OnDocumentBeforeClose);
        } catch (err) {
            alert("上传文件失败！请检查系统上传参数及网络环境，重新上传。");
        }
        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.NoneOADocSave);
    }

    //判断是否同时上传PDF等格式到OA后台
    var l_uploadWithAppendPath = GetDocParamsValue(l_doc, constStrEnum.uploadWithAppendPath); //标识是否同时上传suffix格式的文档
    if (l_uploadWithAppendPath == "1") {
        //调用转pdf格式函数，强制关闭转换修订痕迹，不弹出用户确认的对话框
        pDoChangeToOtherDocFormat(l_doc, l_suffix, false, false);
    }
	dtd.resolve();
    return dtd;
}

//
/**
 * 作用：执行清稿按钮操作
 * 业务功能：清除所有修订痕迹和批注
 */
function OnImissiveClearRevDoc() {
    var doc = wps.WpsApplication().ActiveDocument;
    if (!doc) {
        alert("尚未打开文档，请先打开文档再进行清稿操作！");
    }

    //执行清稿操作前，给用户提示
    if (!wps.confirm("清稿操作将接受所有的修订内容，关闭修订显示。请确认执行清稿操作？")) {
        return;
    }

	var imFlag = false;
	//保存初始稿
	if (doc.Revisions.Count >= 1 || doc.Comments.Count >= 1) {
		wps.PluginStorage.setItem("imFlag",true);
		imFlag = true;
	}
	if(imFlag){
		SetDocParamsValue(doc, "ImissiveClearInitFlag","true");
		OnSaveToServerQg().then(function(){
			setTimeout(function(){
				saveClearDraftInit(doc);
			},100);
		});
	}
   /* //接受所有修订
    if (doc.Revisions.Count >= 1) {
        doc.AcceptAllRevisions();
    }
    //去除所有批注
    if (doc.Comments.Count >= 1) {
        doc.RemoveDocumentInformation(wps.Enum.wdRDIComments);
    }

    //删除所有ink墨迹对象
    pDeleteAllInkObj(doc);

    doc.TrackRevisions = false; //关闭修订模式
    wps.ribbonUI.InvalidateControl("btnOpenRevision");
	if(imFlag){
	//保存
	OnSaveToServerQg().then(function(){
		saveClearDraftAtt(doc);
	});
	}*/
	initRevision(doc, true);
    return;
}

/**
	保存痕迹稿
 */
function btnImissiveSaveRevDoc(){
	var doc = wps.WpsApplication().ActiveDocument;
	if (!doc) {
        alert("尚未打开文档，请先打开文档再进行清稿操作！");
     }
	//执行保存痕迹稿操作前，给用户提示
     if (!wps.confirm("保存痕迹稿操作将接受所有的修订内容，请确认执行保存痕迹稿操作？")) {
        return;
     }
	var nodevalue = GetDocParamsValue(doc, "nodevalue");
	var modelId = GetDocParamsValue(doc, "ekpModelId");
	var fdKey = GetDocParamsValue(doc, "ekpAttMainKey");
	var fdModelName = GetDocParamsValue(doc, "ekpModelName");
	var token = GetDocParamsValue(doc, "wpsoaassistToken");
	var prefix = GetDocParamsValue(doc, "ekpServerPrefix");
	var urlImssive = prefix+'/km/imissive/km_imissive_redhead_template/kmImissiveRedHeadTemplate.do?method=saveClearDraftInit&wpsOasisstToken='+token;
	var msgFlag = false;
	$.ajax({
		    type:"post",
		    url:urlImssive,
		    data:{"modelId":modelId,"nodevalue":nodevalue,"fdKey":fdKey,"fdModelName":fdModelName},
		    dataType:"json",
		    async:false,
		    success:function(data){
				var msgFlag = data.msg;
				if(msgFlag!="true"){
					alert("操作失败！");
				}else{
					 //接受所有修订
				     if (doc.Revisions.Count >= 1) {
				        doc.AcceptAllRevisions();
				     }
				     //去除所有批注
				     if (doc.Comments.Count >= 1) {
				        doc.RemoveDocumentInformation(wps.Enum.wdRDIComments);
				     }
				
				     //删除所有ink墨迹对象
				     pDeleteAllInkObj(doc);
					 OnSaveToServerQg();
				}
			}
		 });
}

function clearOne(l_doc){
	var l_showConfirm = wps.PluginStorage.getItem(constStrEnum.Save2OAShowConfirm);
    if (l_showConfirm) {
		var ImissiveClearInitFlag = GetDocParamsValue(l_doc, "ImissiveClearInitFlag");
		if(ImissiveClearInitFlag !="true"){
			if (wps.confirm("文件上传成功！继续编辑请确认，取消关闭文档。") == false) {
                if (l_doc) {
					let params={closeWps_gw:true};
					wps.OAAssist.WebNotify(JSON.stringify(params),true);
					console.log("OnUploadToServerSuccess: before Close");
                    l_doc.Close(-1); //保存文档后关闭
                    console.log("OnUploadToServerSuccess: after Close");
                }
       		}
		}
		SetDocParamsValue(l_doc, "ImissiveClearInitFlag","");
        
    }
    var l_NofityURL = GetDocParamsValue(l_doc, constStrEnum.notifyUrl);
    if (l_NofityURL != "") {
        l_NofityURL = l_NofityURL.replace("{?}", "2"); //约定：参数为2则文档被成功上传
        NotifyToServer(l_NofityURL);
    }
}