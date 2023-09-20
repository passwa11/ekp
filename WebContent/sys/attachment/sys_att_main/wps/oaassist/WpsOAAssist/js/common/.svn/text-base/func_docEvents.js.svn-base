// 打印前监听事件
function OnDocumentBeforePrint(doc) {
    return;
}

//切换窗口时触发的事件
function OnWindowActivate() {
    var l_doc = wps.WpsApplication().ActiveDocument;
    SetCurrDocEnvProp(l_doc); // 设置当前文档对应的用户名
    showOATab(); // 根据文件是否为OA文件来显示OA菜单再进行刷新按钮
    setTimeout(activeTab, 500); // 激活页面必须要页签显示出来，所以做1秒延迟
    return;
}

/**
 *  作用：判断OA文档是否被另存为了
 */
function CheckIfOADocSaveAs(doc) {
    if (!doc) {
        return;
    }
    // 获取OA文档的原始保存路径
    var l_Path = GetDocParamsValue(doc, constStrEnum.SourcePath);
    // 原路径和当前文件的路径对比
    return l_Path == doc.FullName;
}


// 当文件保存前触发的事件
function OnDocumentBeforeSave(doc) {
	if(!doc||!wps.WpsApplication().ActiveDocument){
		return ;
	}
	if(doc.DocID == wps.WpsApplication().ActiveDocument.DocID){
		console.log("doc.DocId"+doc.DocID);
		console.log("wps.WpsApplication().ActiveDocument.DocId"+wps.WpsApplication().ActiveDocument.DocID);
		if(!pCheckIfOADoc()){
			return ;
		}
		console.log(doc.DocId,wps.WpsApplication().ActiveDocument.DocId);
		//设置变量，判断是否当前用户按了自定义的OA文件保存按钮
	    var l_IsOADocButtonSave = false;
	    l_IsOADocButtonSave = wps.PluginStorage.getItem(constStrEnum.OADocUserSave);
	    
		var l_model = GetDocParamsValue(doc, "wpsExtAppModel");
		var l_attMainKey = GetDocParamsValue(doc, "ekpAttMainKey");
		if (l_model == "kmImissive") {
		    showOATab();
		    return;
		}
		if ((l_model == "kmAgreement" || l_model == "kmSample") && l_attMainKey == "mainOnline") {
		    showOATab();
		    return;
		}

    //根据传入参数判断当前文档是否能另存为，默认不能另存为
    if (pCheckCurrOADocCanSaveAs(doc) == false) { //先根据OA助手的默认设置判断是否允许OA文档另存为操作        
        //0.如果配置文件：OA文档不允许另存为，则再判断
        //1.先判断是否是在线文档且是通过WPS自身按钮或快捷键保存，则取消弹出另存到本地的弹出框
        if (pIsOnlineOADoc(doc) == true && l_IsOADocButtonSave == false) {
            alert("来自OA的不落地文档，禁止另存为本地文档！");
            //如果是OA文档，则禁止另存为
            wps.ApiEvent.Cancel = true;
        }
        //2.如果是落地打开的OA文档并且通过WPS自身按钮或者快捷键保存，则执行保存到本地临时目录，取消弹出对话框
        if (pIsOnlineOADoc(doc) == false && l_IsOADocButtonSave == false){
            //用户手动另存为操作时，在这里被屏蔽掉
            doc.Save();
            //如果是OA文档，则禁止另存为
            wps.ApiEvent.Cancel = true;
        }        
    }
    //保存文档后，也要刷新一下Ribbon按钮的状态
    showOATab();
	}else{
		wps.ApiEvent.Cancel=true;
	}
    
    return;
}

/**
 * 缺少网页页签关闭后，防止wps客户端被吊起的方法
 * @param doc
 * @returns
 */
function OnDocumentBeforeClose3(doc) {
    console.log('OnDocumentBeforeClose');
    var l_fullName = doc.FullName;
    var l_bIsOADoc = false;
    l_bIsOADoc = CheckIfDocIsOADoc(doc); //判断是否OA文档要关闭
    if (l_bIsOADoc == false) { // 非OA文档不做处理
        return;
    }
    //判断是否只读的文档，或受保护的文档，对于只读的文档，不给予保存提示
    if (pISOADocReadOnly(doc) == false) {
        if (doc.Saved == false) { //如果OA文档关闭前，有未保存的数据
          var closeFileNoTip = GetDocParamsValue(doc, "closeFileNoTip")||false;
          if(!closeFileNoTip){
            if (wps.confirm("文档已修改，请确认是否保存到OA")) {
                    wps.ApiEvent.Cancel = true;
                    var l_uploadPath = GetDocParamsValue(doc, constStrEnum.uploadPath); // 文件上载路径
                    if (l_uploadPath == "") {
                        wps.alert("系统未传入文件上载路径，不能执行上传操作！");
                        return;
                    }

                    var l_showConfirm = wps.PluginStorage.getItem(constStrEnum.Save2OAShowConfirm)
                    if (l_showConfirm) {
                   /*  
                        if (!wps.confirm("先保存文档，并开始上传到系统后台，请确认？")) {
                            return;
                        }*/
                    }

                    var l_FieldName = GetDocParamsValue(doc, constStrEnum.uploadFieldName); //上载到后台的业务方自定义的字段名称
                    if (l_FieldName == "") {
                        l_FieldName = wps.PluginStorage.getItem(constStrEnum.DefaultUploadFieldName); // 默认为‘file’
                    }

                    var l_UploadName = GetDocParamsValue(doc, constStrEnum.uploadFileName); //设置OA传入的文件名称参数
                    if (l_UploadName == "") {
                        l_UploadName = doc.Name; //默认文件名称就是当前文件编辑名称
                    }

                    var l_DocPath = doc.FullName; // 文件所在路径
                    doc.SaveAsUrl(l_UploadName, l_uploadPath, l_FieldName, "", "OnUploadToServerFail");
                    return;
                }
          }
            
        }
    }
    var l_model = GetDocParamsValue(doc, "wpsExtAppModel");
    // 有未保存的数据，确认无需保存直接关闭
    doc.Close(wps.Enum.wdDoNotSaveChanges); // 不保存待定的更改。
    closeWpsIfNoDocument(l_model); // 判断WPS中的文件个数是否为0，若为0则关闭WPS函数
    wps.FileSystem.Remove(l_fullName);
}

//文档保存前关闭事件
/**解决了页签关闭后wps客户端调起的方法   但是套红头的时候会在关闭页签后掉漆两次
 * 作用：
 * @param {*} doc 
 */
function OnDocumentBeforeClose(doc) {
    console.log('OnDocumentBeforeClose');
/*    if(wps.WpsApplication().Documents.Count==0){
    	wps.ApiEvent.Cancel=true;//关闭客户端之后不会调用OnDocumentBeforeClose
//    	doc.Close(-1); 会在关闭客户端之后再次调用OnDocumentBeforeClose方法
    	return ;
    }*/
	if (!pCheckIfOADoc(doc)) {
	/*	wps.ApiEvent.Cancel=true;
	    doc.Close()
	    closeWpsIfNoDocument();*/
	    return;
	 }
    var l_fullName = doc.FullName;
    var l_bIsOADoc = false;
    l_bIsOADoc = CheckIfDocIsOADoc(doc); //判断是否OA文档要关闭
    if (l_bIsOADoc == false) { // 非OA文档不做处理
        return;
    }
    //判断是否只读的文档，或受保护的文档，对于只读的文档，不给予保存提示
    if (pISOADocReadOnly(doc) == false) {
        if (doc.Saved == false) { //如果OA文档关闭前，有未保存的数据
        	var closeFileNoTip = GetDocParamsValue(doc, "closeFileNoTip")||false;
        	if(!closeFileNoTip){
        		if (wps.confirm("文档已修改，请确认是否保存到OA")) {
                    wps.ApiEvent.Cancel = true;
                    
                //return;
                    //定制功能  未保存提示点击后无论如何都执行保存到OA这一步
                   
                /*     * 参数定义：OAAsist.UploadFile(name, path, url, field,  "OnSuccess", "OnFail")
                     * 上传一个文件到远程服务器。
                     * name：为上传后的文件名称；
                     * path：是文件绝对路径；
                     * url：为上传地址；
                     * field：为请求中name的值；
                     * 最后两个参数为回调函数名称；
                     * 
                     **/
                   var l_uploadPath = GetDocParamsValue(doc, constStrEnum.uploadPath); // 文件上载路径
                    if (l_uploadPath == "") {
                        wps.alert("系统未传入文件上载路径，不能执行上传操作！");
                        return;
                    }

                    var l_showConfirm = wps.PluginStorage.getItem(constStrEnum.Save2OAShowConfirm)
                    if (l_showConfirm) {
                      /* 项目定制  去除提示直接保存 
                       * if (!wps.confirm("先保存文档，并开始上传到系统后台，请确认？")) {
                            return;
                        }*/
                    }

                    var l_FieldName = GetDocParamsValue(doc, constStrEnum.uploadFieldName); //上载到后台的业务方自定义的字段名称
                    if (l_FieldName == "") {
                        l_FieldName = wps.PluginStorage.getItem(constStrEnum.DefaultUploadFieldName); // 默认为‘file’
                    }

                    var l_UploadName = GetDocParamsValue(doc, constStrEnum.uploadFileName); //设置OA传入的文件名称参数
                    if (l_UploadName == "") {
                        l_UploadName = doc.Name; //默认文件名称就是当前文件编辑名称
                    }

                    var l_DocPath = doc.FullName; // 文件所在路径

                    var l_model = GetDocParamsValue(doc, "wpsExtAppModel");
                    var judgeFlag = false;
                    if ('kmAgreement' == l_model || 'kmSample' == l_model) {
                    	judgeFlag = true;
                    }
                    if (pIsOnlineOADoc(doc) == false && judgeFlag) {
                        //对于本地磁盘文件上传OA，先用Save方法保存后，再上传
                        //设置用户保存按钮标志，避免出现禁止OA文件保存的干扰信息
                        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.OADocSave);
                        doc.Save(); //执行一次保存方法
                        //设置用户保存按钮标志
                        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.NoneOADocSave);
                        //落地文档，调用UploadFile方法上传到OA后台
                        try {
                            //调用OA助手的上传方法
                            UploadFile(l_UploadName, l_DocPath, l_uploadPath, l_FieldName, null, OnUploadToServerFail);
                        } catch (err) {
                            alert("上传文件失败！请检查系统上传参数及网络环境！");
                        }
                    } else {
                        // 不落地的文档，调用 Document 对象的不落地上传方法   在保存前后调用文档保存方法，修改文档状态
                        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.OADocSave);
                    	doc.SaveAsUrl(l_UploadName, l_uploadPath, l_FieldName, "", "OnUploadToServerFail");
                        // 不落地的文档，调用 Document 对象的不落地上传方法   在保存前后调用文档保存方法，修改文档状态
                        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.OADocSave);
                        if(l_model == "kmImissive"){
                            let params={closeWps_gw:true};
                            wps.OAAssist.WebNotify(JSON.stringify(params),true);
                        }
                    }

                }
        	}
            
        }
    }
    var l_model = GetDocParamsValue(doc, "wpsExtAppModel");
    // 有未保存的数据，确认无需保存直接关闭
    doc.Close(wps.Enum.wdDoNotSaveChanges); // 不保存待定的更改。
    closeWpsIfNoDocument(l_model); // 判断WPS中的文件个数是否为0，若为0则关闭WPS函数
    wps.FileSystem.Remove(l_fullName);
    wpsClientCloseStatus=true;
}


//文档保存后关闭事件
function OnDocumentAfterClose(doc) {
    console.log("OnDocumentAfterClose");

    var l_NofityURL = GetDocParamsValue(doc, constStrEnum.notifyUrl);
    if (l_NofityURL) {
        l_NofityURL = l_NofityURL.replace("{?}", "3"); //约定：参数为3则文档关闭
        console.log("" + l_NofityURL);
        NotifyToServer(l_NofityURL);
    }

    pRemoveDocParam(doc); // 关闭文档时，移除PluginStorage对象的参数
    pSetWPSAppUserName(); // 判断文档关闭后，如果系统已经没有打开的文档了，则设置回初始用户名
}

//文档打开事件
function OnDocumentOpen(doc) {
	hideLoading();//调用文档打开后事件
	
	wps.WpsApplication().Options.UseLocalUserInfo=true;
	
	var openLocalFlag = wps.PluginStorage.getItem("openLocalFlag");
	var extModule = wps.PluginStorage.getItem("wpsExtAppModel");
	if (openLocalFlag && openLocalFlag == '1' && extModule && (extModule == 'kmAgreement' || extModule == 'kmSample')) {
		if (doc) {
			//将打开的本地文件的内容替换到当前文档，并关闭本地文档
			var openFilePath = doc.FullName;
			doc.Close(0);
			wps.PluginStorage.setItem("openLocalFlag", "0");
			var l_doc = wps.WpsApplication().ActiveDocument;
			if (l_doc) {
				if (l_doc.ProtectionType != -1) {
					wps.alert('当前文档被保护，无法进行文件替换');
					return;
				}
            	//将文件插入
            	var selection = wps.WpsApplication().ActiveWindow.Selection;
            	var doc = wps.WpsApplication().ActiveDocument;
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
            	selection.InsertFile(openFilePath);
			}
		}
		return;
	}
	
	
//	//设置当前新增文档是否来自OA的文档
//    if (wps.PluginStorage.getItem(constStrEnum.IsInCurrOADocOpen) == false) {
//        //如果是用户自己在WPS环境打开文档，则设置非OA文档标识
//        pSetNoneOADocFlag(doc);
//    }
    console.log(testFuncs);
    OnWindowActivate();
    ChangeOATabOnDocOpen(); //打开文档后，默认打开Tab页
}

//新建文档事件
function OnDocumentNew(doc) {
    //设置当前新增文档是否来自OA的文档
//    if (wps.PluginStorage.getItem(constStrEnum.IsInCurrOADocOpen) == false) {
//        //如果是用户自己在WPS环境打开文档，则设置非OA文档标识
//        pSetNoneOADocFlag(doc);
//    }
	hideLoading();//调用文档打开后事件
    ChangeOATabOnDocOpen(); // 打开OA助手Tab菜单页
    wps.ribbonUI.Invalidate(); // 刷新Ribbon按钮的状态
}

/**
 *  作用：判断当前文档是否是只读文档
 *  返回值：布尔
 */
function pISOADocReadOnly(doc) {
    if (!doc) {
        return false;
    }
    var l_openType = GetDocParamsValue(doc, constStrEnum.openType); // 获取OA传入的参数 openType
    if (l_openType == "") {
        return false;
    }
    try {
        if (l_openType.protectType != -1) { // -1 为未保护
            return true;
        }
    } catch (err) {
        return false;
    }
}


/**
 *  作用：根据当前活动文档的情况判断，当前文档适用的系统参数，例如：当前文档对应的用户名称等
 */
function SetCurrDocEnvProp(doc) {
    if (!doc) return;
    var l_bIsOADoc = false;
    l_bIsOADoc = pCheckIfOADoc(doc);

    //如果是OA文件，则按OA传来的用户名设置WPS   OA助手WPS用户名设置按钮冲突
    if (l_bIsOADoc == true) {
        var l_userName = GetDocParamsValue(doc, constStrEnum.userName);
        if (l_userName != "") {
            wps.WpsApplication().UserName = l_userName;
            return;
        }
    }
    //如果是非OA文件或者参数的值是空值，则按WPS安装默认用户名设置
    wps.WpsApplication().UserName = wps.PluginStorage.getItem(constStrEnum.WPSInitUserName);
}



/*
    入口参数：doc
    功能说明：判断当前文档是否能另存为本地文件
    返回值：布尔值true or false
*/
function pCheckCurrOADocCanSaveAs(doc) {
    //如果是非OA文档，则允许另存为
    if (CheckIfDocIsOADoc(doc) == false) return true;

    //对于来自OA系统的文档，则获取该文档对应的属性参数
    var l_CanSaveAs = GetDocParamsValue(doc, constStrEnum.CanSaveAs);

    //判断OA传入的参数
    if (typeof (l_CanSaveAs) == "boolean") {
        return l_CanSaveAs;
    }
    return false;
}

/**
 * 作用：判断文档关闭后，如果系统已经没有打开的文档了，则设置回初始用户名
 */
function pSetWPSAppUserName() {
    //文档全部关闭的情况下，把WPS初始启动的用户名设置回去
    if (wps.WpsApplication().Documents.Count == 1) {
        var l_strUserName = wps.PluginStorage.getItem(constStrEnum.WPSInitUserName);
        wps.WpsApplication().UserName = l_strUserName;
    }
}

/**
 * 作用：文档关闭后，删除对应的PluginStorage内的参数信息
 * 返回值：没有返回值
 * @param {*} doc 
 */
function pRemoveDocParam(doc) {
    if (!doc) return;
    wps.PluginStorage.removeItem(doc.DocID);
    return;
}

/**
 * 作用：判断当前文档是否从OA来的文档，如果非OA文档（就是本地新建或打开的文档，则设置EnumOAFlag 标识）
 * 作用：设置非OA文档的标识状态
 * @param {*} doc 
 * 返回值：无
 */
function pSetNoneOADocFlag(doc) {
    if (!doc) return;
    var l_param = wps.PluginStorage.getItem(doc.DocID); //定义JSON文档参数
    var l_objParams = new Object();
    if (l_param) {
        l_objParams = JSON.parse(l_param);
    }
    l_objParams.isOA = EnumOAFlag.DocFromNoOA; // 新增非OA打开文档属性
    wps.PluginStorage.setItem(doc.DocID, JSON.stringify(l_objParams)); // 存入内存中
}

/**
 * 作用：根据设置判断打开文件是否默认激活OA助手工具Tab菜单
 * 返回值：无
 */
function ChangeOATabOnDocOpen() {
    var l_ShowOATab = true; //默认打开
    l_ShowOATab = wps.PluginStorage.getItem(constStrEnum.ShowOATabDocActive);

    if (l_ShowOATab == true) {
    	var l_flag = false;
		var l_imissiveFlag = false;
		var l_smissiveFlag = false;
    	var l_doc = wps.WpsApplication().ActiveDocument;
    	if (l_doc) {
    		var l_model = GetDocParamsValue(l_doc, "wpsExtAppModel");
    		var l_attMainKey = GetDocParamsValue(l_doc, "ekpAttMainKey");
    		if ((l_model == "kmAgreement" || l_model == "kmSample") && l_attMainKey == "mainOnline") {
    			l_flag = true;
    		}else if(l_model == "kmImissive"){
				l_imissiveFlag = true;
			}
    		else if(l_model == "kmSmissive"){
    			l_smissiveFlag = true;
			}
    	}
		if(l_imissiveFlag){
			wps.ribbonUI.ActivateTab('WPSImissiveTab');
			return;
		}
		
		if(l_smissiveFlag){
			wps.ribbonUI.ActivateTab('WPSSmissiveTab');
			return;
		}
    	if (l_flag) {
    		wps.ribbonUI.ActivateTab('WPSAgreementTab');
    	} else {
    		wps.ribbonUI.ActivateTab('WPSWorkExtTab'); //新建文档时，自动切换到OA助手状态
    	}
    }
}