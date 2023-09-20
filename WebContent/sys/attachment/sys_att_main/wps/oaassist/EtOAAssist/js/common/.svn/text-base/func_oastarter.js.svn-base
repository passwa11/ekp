/**
 * web页面调用WPS加载项的方法入口
 *  * info参数结构
 * info:[
 *      {
 *       '方法名':'方法参数',需要执行的方法
 *     },
 *     ...
 *   ]
 * @param {*} info
 */
function dispatcher(info) {
    var funcs = info.funcs;
    //NotifyToWeb();
    //alert(JSON.stringify(funcs));
    //执行web页面传递的方法
    for (var index = 0; index < funcs.length; index++) {
        var func = funcs[index];
        for (var key in func) {
        	func[key].isOA=true;
        	
            if (key === "OpenDoc") { // OpenDoc 属于普通的打开文档的操作方式，文档落地操作
            	showLoading();
                OpenDoc(func[key]); //进入打开文档处理函数
            } else if (key === "OnlineEditDoc") { //在线方式打开文档，属于文档不落地的方式打开
            	showLoading();
                OnlineEditDoc(func[key]);
            } else if (key === "NewDoc") {
            	showLoading();
                OpenDoc(func[key]);
            }
            else if (key === "ExitEt") {
            	ExitEt(func[key]);
            }
        }
    }
}

/**
 * 关闭WPS活动文档并退出WPS进程
 */
function ExitEt(params) {
	var ekpSysAttMainId=params.ekpSysAttMainId;
	var Documents = wps.EtApplication().Workbooks;
	var wdDoNotSaveChanges = 0;
	
	for (var i = 1; i < Documents.Count+1; i++) {
		var n_doc=Documents.Item(i);
		var ekpAttMainId = GetDocParamsValue(n_doc, "ekpAttMainId");
		if(ekpSysAttMainId==ekpAttMainId){
			SetEtParamsValue(n_doc,"closeFileNoTip",params.closeFileNoTip);
			Documents.Item(i).Close(wdDoNotSaveChanges);
		}
	}
	
}




/**
 * 
 * @param {*} params  OA端传入的参数
 */
function OnlineEditDoc(OaParams) {
    //如果
    if (OaParams.fileName == "") {
        NewFile(OaParams);
    } else {
        //OA传来下载文件的URL地址，调用openFile 方法打开
        OpenOnLineFile(OaParams);
    }
}

///打开来自OA端传递来的文档
function OpenDoc(OaParams) {
    //如果
    if (OaParams.fileName == "") {
        NewFile(OaParams);
    } else {
        //OA传来下载文件的URL地址，调用openFile 方法打开
        OpenFile(OaParams);
    }
}