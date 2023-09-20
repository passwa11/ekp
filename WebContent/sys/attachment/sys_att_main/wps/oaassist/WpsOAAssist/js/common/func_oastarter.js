/**
 * 在这个js中，集中处理来自OA的传入参数
 * 
 */

/**
 * web页面调用WPS的方法入口
 *  * info参数结构
 * info:[
 *      {
 *       '方法名':'方法参数',需要执行的方法
 *     },
 *     ...
 *   ]
 * @param {*} info
 */
let testFuncs=null;
function dispatcher(info) {
    var funcs = info.funcs;
    //执行web页面传递的方法
    setTimeout(function(){
        for (var index = 0; index < funcs.length; index++) {
            testFuncs=funcs;
            var func = funcs[index];
            for (var key in func) {
            	//设置cookie值
            	if(func[key].ekpcookie){
            		var ekpcookie=func[key].ekpcookie;
            		document.cookie="JSESSIONID="+ekpcookie;
            		wps.PluginStorage.setItem("ekp_wps_cookie",ekpcookie);
            	}
            	if(func[key].ekpUserId){
            		wps.PluginStorage.setItem("ekp_wps_userId",func[key].ekpUserId);
            	}
            	if(func[key].ekpUserName){
            		wps.PluginStorage.setItem("ekp_wps_userName",func[key].ekpUserName);
            	}
            	if(func[key].ekpAttMainId){
            		wps.PluginStorage.setItem("ekp_wps_attMainId",func[key].ekpAttMainId);
            	}
            	if(func[key].ekpModelId){
            		wps.PluginStorage.setItem("ekp_wps_modelId",func[key].ekpModelId);
            	}
            	if(func[key].ekpModelName){
            		wps.PluginStorage.setItem("ekp_wps_modelName",func[key].ekpModelName);
            	}
            	if(func[key].ekpAttMainKey){
            		wps.PluginStorage.setItem("ekp_wps_attMainKey",func[key].ekpAttMainKey);
            	}
            	if(typeof func[key].ekpcanCopy != "undefined" ){
            		wps.PluginStorage.setItem("ekp_wps_canCopy",func[key].ekpcanCopy);
            	}
            	if(typeof func[key].ekpcanPrint != "undefined"){
            		wps.PluginStorage.setItem("ekp_wps_canPrint",func[key].ekpcanPrint);
            	}
            	if(typeof func[key].ekpdownload != "undefined"){
            		wps.PluginStorage.setItem("ekp_wps_download",func[key].ekpdownload);
            	}
            	if(typeof func[key].redheadOpenType != "undefined"){
            		wps.PluginStorage.setItem("ekp_wps_redheadOpenType",func[key].redheadOpenType);
            	}
				if(typeof func[key].newFlag != "undefined"){
                    wps.PluginStorage.setItem(constStrEnum.newFlag,func[key].newFlag);
                }
            	if(typeof func[key].wpsoaassistToken != "undefined"){
            		wps.PluginStorage.setItem("ekp_wps_oaassist_token",func[key].wpsoaassistToken);
            	}
            	if(typeof func[key].ekpServerPrefix != "undefined"){
            		wps.PluginStorage.setItem("ekp_server_prefix",func[key].ekpServerPrefix);
            	}
            	if(typeof func[key].fileOpenType != "undefined"){
            		wps.PluginStorage.setItem("ekp_file_open_type",func[key].fileOpenType);
            	}
            	// 新增：根据这个判断是从哪个模块过来的
                if(typeof func[key].wpsExtAppModel != "undefined"){
                    wps.PluginStorage.setItem(constStrEnum.WpsExtAppModel,func[key].wpsExtAppModel);
                }
                // 协同写作传过来的参数
                if(typeof func[key].kmsCowritingMainId != "undefined"){
                    wps.PluginStorage.setItem("kms_cowriting_mainid",func[key].kmsCowritingMainId);
                }
                if(typeof func[key].kmsCowritingMTaskId != "undefined"){
                    wps.PluginStorage.setItem("kms_cowriting_m_taskid",func[key].kmsCowritingMTaskId);
                }
                if(typeof func[key].kmsCowritingMTaskUserId != "undefined"){
                    wps.PluginStorage.setItem("kms_cowriting_m_task_userid",func[key].kmsCowritingMTaskUserId);
                }
                if(typeof func[key].kmsCowritingDocId != "undefined"){
                    wps.PluginStorage.setItem("kms_cowriting_docId",func[key].kmsCowritingDocId);
                }
                if(typeof func[key].kmsCowritingToolTabName != "undefined"){
                    wps.PluginStorage.setItem("kms_cowriting_tool_tabname",func[key].kmsCowritingToolTabName);
                }
                // 新增用于判断是否开启知识助手开关，wps页面依此来显示知识助手和智能推荐图标
                if(typeof func[key].kmsTabSwitch != "undefined"){
                    wps.PluginStorage.setItem("kms_tab_switch",func[key].kmsTabSwitch);
                }
                if(typeof func[key].kmsIsCowritingWSLoad != "undefined"){
                    wps.PluginStorage.setItem("kms_is_cowritingws_load",func[key].kmsIsCowritingWSLoad);
                }
                if(typeof func[key].kmsAssistUrl != "undefined"){
                    wps.PluginStorage.setItem("kms_assist_url",func[key].kmsAssistUrl);
                }
                if(typeof func[key].kmsRecommendUrl != "undefined"){
                    wps.PluginStorage.setItem("kms_recommend_url",func[key].kmsRecommendUrl);
                }
                if(typeof func[key].kmsOnlyCurrentTask != "undefined"){
                    wps.PluginStorage.setItem("kms_only_current_task",func[key].kmsOnlyCurrentTask);
                }
                if (typeof func[key].kmsCowritingTemplateName != "undefined") {
                    // 如果只是查看模板，需要模板名称与当前打开的文档进行比对
                    wps.PluginStorage.setItem("kms_cowriting_template_name", func[key].kmsCowritingTemplateName);
                }
                
                func[key].isOA=true
                if (key === "OpenDoc") { // OpenDoc 属于普通的打开文档的操作方式，文档落地操作
                	showLoading();//打开文档前调用
                    OpenDoc(func[key]); //进入打开文档处理函数
                } else if (key === "OnlineEditDoc") { //在线方式打开文档，属于文档不落地的方式打开
                	showLoading();//打开文档前调用
                    OnlineEditDoc(func[key]);
                } else if (key === "NewDoc") {
                	showLoading();//打开文档前调用
                    OpenDoc(func[key]);
                } else if (key === "UseTemplate") {
                	showLoading();//打开文档前调用
                    OpenDoc(func[key]);
                } else if (key === "InsertRedHead") {
                    InsertRedHead(func[key]);
                } else if (key === "taskPaneBookMark"){
                    taskPaneBookMark(func[key])
                } else if (key === "ExitWPS") {
                	hideLoading();
                    ExitWPS(func[key])
                } else if (key === "GetDocStatus") {
                    return GetDocStatus(func[key])
                } else if (key === "NewOfficialDocument"){
                	showLoading();//打开文档前调用
                    return OpenDoc(func[key])
                } else if (key === "checkWps") {
                    checkWps(func[key]);
                }
            }
        }

        // 保存配置项域名用于校验
        wps.PluginStorage.setItem("config_domain", document.domain);
    },50)
    return {message:"ok", app:wps.WpsApplication().Name}
}


/**
 * 获取活动文档的状态
 */
function GetDocStatus() {
    let l_doc = wps.WpsApplication().ActiveDocument
    if (l_doc && pCheckIfOADoc()) {//此方法还可根据需要进行扩展
        return{
            message: "GetDocStatus",
            docstatus:{
                words: l_doc.Words.Count,
                saved: l_doc.Saved,
                pages: l_doc.ActiveWindow.Panes.Item(1).Pages.Count
            }
        }
    }    
}

/**
 * 关闭WPS活动文档并退出WPS进程
 */
function ExitWPS(params) {
	var ekpSysAttMainId=params.ekpSysAttMainId;
	var Documents = wps.WpsApplication().Documents;
	var wdDoNotSaveChanges = 0;
	
	for (var i = 1; i < Documents.Count+1; i++) {
		var n_doc=Documents.Item(i);
		var ekpAttMainId = GetDocParamsValue(n_doc, "ekpAttMainId");
		if(ekpSysAttMainId==ekpAttMainId){
			console.log("close:"+ekpAttMainId);
			SetDocParamsValue(n_doc,"closeFileNoTip",params.closeFileNoTip);
			Documents.Item(i).Close(wdDoNotSaveChanges);
		}
	}
	
}

/**
 * 
 * @param {*} params  OA端传入的参数
 */
function OnlineEditDoc(OaParams) {
    if (OaParams.fileName == "") {
        NewFile(OaParams);
    } else {
        //OA传来下载文件的URL地址，调用openFile 方法打开
        OpenOnLineFile(OaParams);
    }
}

///打开来自OA端传递来的文档
function OpenDoc(OaParams) {
    if (OaParams.fileName == "") {
        NewFile(OaParams);
    } else {
        //OA传来下载文件的URL地址，调用openFile 方法打开
        OpenFile(OaParams);
    }
}

function taskPaneBookMark(OaParams){
    let filePath = OaParams.fileName
    if (filePath == "")
        return
    OpenFile(OaParams);

    //创建taskpane，只创建一次
    let id = wps.PluginStorage.getItem(constStrEnum.taskpaneid)
    if (id){
        let tp = wps.GetTaskPane(id)
        tp.Width = 300
        tp.Visible = true
    }
    else{
        let url = getHtmlURL("taskpane.html");
        let tp =  wps.CreateTaskPane(url, "书签操作")
        if (tp){
            tp.DockPosition = WPS_Enum.msoCTPDockPositionRight  //这里可以设置taskapne是在左边还是右边
            tp.Width = 300
            tp.Visible = true
            wps.PluginStorage.setItem(constStrEnum.taskpaneid, tp.ID)
        }
    }
}

// 协同写作打开右侧窗口
function taskPaneCowriting(page, isReload) {
    var ekpServerPrefix = wps.PluginStorage.getItem("ekp_server_prefix");
    var token = wps.PluginStorage.getItem('ekp_wps_oaassist_token');

    let url = ekpServerPrefix + "/kms/cowriting/wpsplugin/" + page + ".jsp?wpsOasisstToken=" + token;
    // alert("url=" + url)

    //创建taskpane，只创建一次
    let id = wps.PluginStorage.getItem(constStrEnum.taskpaneid)
    if (id) {
        if(isReload){
            let tp = wps.GetTaskPane(id)
            tp.Width = 300
            tp.Visible = true
            tp.Navigate(url)
        }
    } else {
        let tp = wps.CreateTaskPane(url, "协同写作")
        if (tp) {
            tp.DockPosition = WPS_Enum.msoCTPDockPositionRight  //这里可以设置taskapne是在左边还是右边
            tp.Width = 300
            tp.Visible = true
            wps.PluginStorage.setItem(constStrEnum.taskpaneid, tp.ID)
        }
    }
}

function taskPaneCowritingWithUrl(url, isReload) {
    //创建taskpane，只创建一次
    let id = wps.PluginStorage.getItem(constStrEnum.taskpaneid)
    if (id) {
        if(isReload){
            let tp = wps.GetTaskPane(id)
            tp.Width = 300
            tp.Visible = true
            tp.Navigate(url)
        }
    } else {
        let tp = wps.CreateTaskPane(url, "协同写作")
        if (tp) {
            tp.DockPosition = WPS_Enum.msoCTPDockPositionRight  //这里可以设置taskapne是在左边还是右边
            tp.Width = 300
            tp.Visible = true
            wps.PluginStorage.setItem(constStrEnum.taskpaneid, tp.ID)
        }
    }
}

//检测wps
function checkWps() {
    console.log("检测wps")
}