/**
 * 从OA调用传来的指令，打开本地新建文件
 * @param {*} fileUrl 文件url路径
 */
function NewFile(params) {
    //获取WPS Application 对象
    var wpsApp = wps.WpsApplication();
    wps.PluginStorage.setItem(constStrEnum.IsInCurrOADocOpen, true); //设置OA打开文档的临时状态
    //判断一下isOfficialDocument是否通过公文写作打开
    var doc;
    if (params.isOfficialDocument) {
        wps.Application.GetApplicationEx().NewOfficialDocument(); //新增使用公文写作打开的公文
        doc = wpsApp.ActiveDocument;
    } else {
        doc = wpsApp.Documents.Add(); //新增OA端文档
    }
    wps.PluginStorage.setItem(constStrEnum.IsInCurrOADocOpen, false);

    //检查系统临时文件目录是否能访问
    if (wps.Env && wps.Env.GetTempPath) {
        if (params.newFileName) {
            //按OA传入的文件名称保存
            doc.SaveAs2($FileName = wps.Env.GetTempPath() + "/" + params.newFileName, undefined, undefined, undefined, false);
        } else {
            //OA传入空文件名称，则保存成系统时间文件
            if (params.isOfficialDocument) {
                doc.SaveAs2($FileName = wps.Env.GetTempPath() + "/OA_" + currentTime(), 0, undefined, undefined, false);
            } else {
                doc.SaveAs2($FileName = wps.Env.GetTempPath() + "/OA_" + currentTime(), undefined, undefined, undefined, false);
            }
        }
    } else {
        alert("文档保存临时目录出错！不能保存新建文档！请联系系统开发商。");
    }

    var l_NofityURL = GetParamsValue(params, constStrEnum.notifyUrl);
    if (l_NofityURL) {
        NotifyToServer(l_NofityURL.replace("{?}", "1"));
    }

    //Office文件打开后，设置该文件属性：从服务端来的OA文件
    pSetOADocumentFlag(doc, params);
    //设置当前文档为 本地磁盘落地模式
    DoSetOADocLandMode(doc, EnumDocLandMode.DLM_LocalDoc);
    //强制执行一次Activate事件
  /*  OnWindowActivate();
    var app=wps.WpsApplication();
    var state=app.WindowState;
    app.WindowState=2
    app.WindowState=1
    if(state!=2){
    	app.WindowState=state;
    }
    wps.WpsApplication().Activate(); //把WPS对象置前
*/
    return doc; //返回新创建的Document对象
}

/**
 * 打开服务器上的文件
 * @param {*} fileUrl 文件url路径
 */
function OpenFile(params) {
    var l_strFileUrl = params.fileName; //来自OA网页端的OA文件下载路径
    var doc;
    var l_IsOnlineDoc = false; //默认打开的是不落地文档
    if (l_strFileUrl) {
        //下载文档之前，判断是否已下载该文件
        if (pCheckIsExistOpenOADoc(l_strFileUrl) == true) {
        	hideLoading();//在文件已经打开的情况下拉起，需要隐藏弹窗
            //如果找到相同OA地址文档，则给予提示
            wps.WpsApplication().Activate(); //把WPS对象置前
            //根据OA助手对是否允许再次打开相同文件的判断处理
            var l_AllowOADocReOpen = false;
            l_AllowOADocReOpen = wps.PluginStorage.getItem(constStrEnum.AllowOADocReOpen);
            if (l_AllowOADocReOpen == false) {
                alert("已打开相同的OA文件，请关闭之前的文件，再次打开。");
                wps.WpsApplication().Activate();
                return null;
            } else {
                //处理重复打开相同OA 文件的方法
                var nDocCount = wps.WpsApplication().Documents.Count;
                pReOpenOADoc(l_strFileUrl);
                //重复打开的文档采用不落地的方式打开
                // 不落地方式打开文档判断落地比较多，V1版本先暂时关闭
                l_IsOnlineDoc = true;
                var nDocCount_New = wps.WpsApplication().Documents.Count;
                if (nDocCount_New > nDocCount) {
                    doc = wps.WpsApplication().ActiveDocument;
                }
            }
        } else {
            //如果当前没有打开文档，则另存为本地文件，再打开
            if (l_strFileUrl.startWith("http")) { // 网络文档
                DownloadFile(l_strFileUrl, function(path) {
                    if (path == "") {
                        alert("从服务端下载路径：" + l_strFileUrl + "\n" + "获取文件下载失败！");
                        return null;
                    }

                    doc = pDoOpenOADocProcess(params, path);
                    pOpenFile(doc, params, l_IsOnlineDoc);
                    
                    hideLoading();
                });
                return;
            } else { //本地文档
                doc = pDoOpenOADocProcess(params, l_strFileUrl);
                if (doc)
                    doc.SaveAs2($FileName = wps.Env.GetTempPath() + "/" + doc.Name, undefined, undefined, undefined, false);
            }
        }
    } else {
        //fileURL 如果为空，则按新建OA本地文件处理    
        NewFile(params);
    }
    //如果打开pdf等其他非Office文档，则doc对象为空
    if (!doc) {
        return null;
    }

    pOpenFile(doc, params, l_IsOnlineDoc);
}

/**
 * 作用：文档打开后执行的动作集合
 * @param {*} doc 文档对象
 * @param {*} params 前端传递的参数集合
 * @param {*} isOnlineDoc 在线打开/落地打开
 */
function pOpenFile(doc, params, isOnlineDoc) {
    var l_IsOnlineDoc = isOnlineDoc
        //Office文件打开后，设置该文件属性：从服务端来的OA文件
    pSetOADocumentFlag(doc, params)
        //设置当前文档为 本地磁盘落地模式
    if (l_IsOnlineDoc == true) {
        DoSetOADocLandMode(doc, EnumDocLandMode.DLM_OnlineDoc);
    } else {
        DoSetOADocLandMode(doc, EnumDocLandMode.DLM_LocalDoc);
    }

    if (GetParamsValue(params, constStrEnum.templateDataUrl)) {
        GetServerTemplateData(doc, params[constStrEnum.templateDataUrl]);
    }

    if (GetParamsValue(params, constStrEnum.insertFileUrl)) {
        InsertRedHeadDoc(doc);
    }

    var l_NofityURL = GetParamsValue(params, constStrEnum.notifyUrl);
    if (l_NofityURL) {
        l_NofityURL = l_NofityURL.replace("{?}", "1"); //约定：参数为1则代码打开状态
        NotifyToServer(l_NofityURL);
    }
    //重新设置工具条按钮的显示状态
    pDoResetRibbonGroups();
    // 触发切换窗口事件
    OnWindowActivate();
    // 把WPS对象置前
    var app=wps.WpsApplication();
    var state=app.WindowState;
    app.WindowState=2
    app.WindowState=1
    if(state!=2){
    	app.WindowState=state;
    }
    wps.WpsApplication().Activate();
    
    oaInitProc(doc); //打开文档后，需要做一些额外处理
    return doc;
}

/**
 * 文档打开后根据业务需要做一些额外处理 
 * @param doc
 * @returns
 */
function oaInitProc(doc) {
    if (!doc) return;
    var moduleName = GetDocParamsValue(doc, "wpsExtAppModel");
    var wpsSysPrintApp = GetDocParamsValue(doc, "wpsSysPrintApp");
    var printMarkContent = GetDocParamsValue(doc, "printMarkContent");
    var wpsPrintEdit = 'false'; //是否允许打印时编辑
    //打印模板填充书签值
    if(wpsSysPrintApp && wpsSysPrintApp == 'SysPrintApp'
        && printMarkContent && printMarkContent != null) {
    	var bookMarks = doc.Bookmarks;
    	var contextPath  =  GetDocParamsValue(doc, "ekpServerPrefix");
    	var auditShowArr = [];
    	var url = contextPath + "/sys/print/word/file";
    	var app = wps.WpsApplication();
    	var ekpAttMainId = GetDocParamsValue(doc, "ekpAttMainId");
    	 wpsPrintEdit = GetDocParamsValue(doc, "wpsPrintEdit"); //是否允许打印时编辑
    	
        app.ActiveDocument.Unprotect(""); //允许编辑

    	     
    	//书签配置审批意见
	    for(var i = 1 ;i <= bookMarks.Count;i++) {
	    	var bookmarkRecord = {};
			var bookmarkObj = bookMarks.Item(i);
			var markName = bookmarkObj.Name;
			if(markName.indexOf('_auditShow') > -1) {
				bookmarkRecord.name = markName;
				bookmarkRecord.bookmarkObj = bookmarkObj;
				auditShowArr.push(bookmarkRecord);
			}
	    }
    	
    	$.each(printMarkContent, function(name) {
    		 if (bookMarks.Exists(name)) {
    			 try {
					 var book = bookMarks.Item(name);
    				 if(name=='docQRCode') {
    					 book.Range.InlineShapes.AddPicture(this.toString());
    				} else {
        	    		 book.Range.Text = this.replace(/\\\\n/g,"\r\n");
    				 }
    				
    			 }catch(e) {
    				 console.log(e);
    			 }
    			
    		}
    	});
    	
    	//审批意见书签填充
		 if(auditShowArr.length > 0 ) {
				for(var idx in auditShowArr) {
					var name = auditShowArr[idx].name;
					var fdId = name.substring(0,name.indexOf('_auditShow'));
					var queryUrl = url +  "/" + fdId + "/" + printMarkContent.fdId + "/" + printMarkContent.extendFilePath;
					var book = bookMarks.Item(name);
					book.Range.InsertFile(queryUrl);
                    // #143657 去掉默认的空行
                    try {
                        book.Range.Select();
                        var count=wps.WpsApplication().ActiveWindow.Selection.Cells.Item(1).Range.Paragraphs.Count;
                        wps.WpsApplication().ActiveDocument.Range(
                            wps.WpsApplication().ActiveWindow.Selection.Cells.Item(1).Range.Paragraphs.Item(count-1).
                                Range.End-1,wps.WpsApplication().ActiveWindow.Selection.Cells.Item(1).
                            Range.Paragraphs.Item(count-1).Range.End).Text="";
                    } catch (e) {
                        console.log("不含有表格审批，不进行去空处理...");
                    }

				}
			}
		 
		 if(wpsPrintEdit == 'false') {
			 app.ActiveDocument.Protect(3,"",ekpAttMainId); //关闭编辑
		 }
	        
    }
    
	if (moduleName == "kmAgreement" || moduleName == "kmSample") {
		kmAgreementInitProc(doc);
	}else if (moduleName == "kmImissive") {
		initRevision(doc);
	}
	
	//插入水印
	var fileOpenType = GetDocParamsValue(doc, "fileOpenType");

	if (fileOpenType == "read" && wpsPrintEdit == 'false') {
		insertWpsWaterMark(doc);
	}
	
	var startState = wps.WpsApplication().WindowState;
	//2是wps客户端最小化   1是最大化
	if(startState!=1){
		wps.WpsApplication().WindowState=1; 
	}
	if(moduleName == "kmSmissive") {
    	insertBookMark(doc);
    }
}
/**
 * 插入书签内容
 * @param doc
 * @returns
 */
function insertBookMark(doc) {
	var bookMarks = doc.Bookmarks;
	var ekpAttMainId = GetDocParamsValue(doc, "ekpAttMainId");
	 var markContent =  GetDocParamsValue(doc, "bookMarks");
	 var app = wps.WpsApplication();
     app.ActiveDocument.Unprotect(""); //允许编辑
     var fileOpenType = GetDocParamsValue(doc, "fileOpenType");
     if(markContent != null && markContent != '') {
    	 var bookMarkContent = JSON.parse(markContent);
    		
    		$.each(bookMarkContent, function(name) {
    			 if (bookMarks.Exists(name)) {
    				 try {
    					 var book = bookMarks.Item(name);
    	    	       book.Range.Text = this.replace(/\\\\n/g,"\r\n");
    					
    				 } catch(e) {
    					 console.log(e);
    				 }
    				
    			}
    		});
     }
    
	if(fileOpenType == 'read') {
		app.ActiveDocument.Protect(3,"",ekpAttMainId); //关闭编辑
	}
}
function insertWpsWaterMark(doc) {
    try {
        var app, shapeRange;
        
        var waterText = GetDocParamsValue(doc, "waterText");
        var watermarkCfg = GetDocParamsValue(doc, "watermarkCfg");
        
        if(watermarkCfg.showWaterMark=="true"){
        	app = wps.WpsApplication();
            app.ActiveDocument.Unprotect("");
            var doc = app.ActiveDocument;
            var selection = doc.ActiveWindow.Selection;
            var pageCount = app.ActiveWindow.ActivePane.Pages.Count;
            for(var i=1;i<=pageCount;i++){
				selection.GoTo(1, 1, i);
				wps.WpsApplication().ActiveDocument.ActiveWindow.View.SeekView=10;
	            app.ActiveDocument.Sections.Item(1).Headers.Item(1).Shapes.AddTextEffect(0, waterText, watermarkCfg.markWordFontFamily, 18, false, false, 0, 0).Select();
	            shapeRange = app.Selection.ShapeRange;
	            shapeRange.TextEffect.NormalizedHeight = false;
	            shapeRange.Line.Visible = false;
	            shapeRange.Fill.Visible = true;
	            shapeRange.Fill.Solid();
	            shapeRange.Fill.ForeColor.RGB = 14737632;       /* WdColor枚举 wdColorGray25 代表颜色值 */
				try {            
					shapeRange.Fill.Transparency = parseInt(watermarkCfg.markOpacity);             /* 填充透明度，值为0.0~1.0 */
	            }catch (error1) {
				}
				shapeRange.LockAspectRatio = true;
	            shapeRange.Height = 4 * 28.346;
	            shapeRange.Width = 24 * 28.346;
	            shapeRange.Rotation = 300;                      /* 图形按照Z轴旋转度数，正值为顺时针旋转，负值为逆时针旋转 */
	            shapeRange.WrapFormat.AllowOverlap = true;
	            shapeRange.WrapFormat.Side = 3;                 /* WdWrapSideType枚举 wdWrapLargest 形状距离页边距最远的一侧 */
	            shapeRange.WrapFormat.Type = 3;                 /* WdWarpType枚举 wdWrapNone 形状放到文字前面 */
	            shapeRange.RelativeHorizontalPosition = 0;      /* WdRelativeHorizontalPosition枚举 wdRelativeHorizontalPositionMargin 相对于边距 */
	            shapeRange.RelativeVerticalPosition = 0;        /* WdRelativeVerticalPosition枚举 wdRelativeVerticalPositionMargin 相对于边距 */
	            try {
					shapeRange.Left = -999995;                    /* WdShapePosition枚举 wdShapeCenter 形状的位置在中央 */
	            }catch (error1) {
				}
				try {
					shapeRange.Top = -999995;  
				}catch (error1) {
				}
               
            }                  /* WdShapePosition枚举 wdShapeCenter 形状的位置在中央 */
            selection.GoTo(1, 1, 1);
            
            var ekpAttMainId = GetDocParamsValue(doc, "ekpAttMainId");
          //  app.ActiveDocument.Protect(3,"",ekpAttMainId);
            var moduleName = GetDocParamsValue(doc, "wpsExtAppModel");

            if(moduleName != "kmSmissive") //如果是简版公文，则关闭由简版公文处理
            {
            	 app.ActiveDocument.Protect(3,"",ekpAttMainId);
            }
            doc.Saved=true;
        }
        
    } catch (error) {
    	wps.WpsApplication().ActiveDocument.ActiveWindow.View.SeekView=0;
        wps.WpsApplication().ActiveDocument.Protect(3,"","")
    }
}


function insertNewWaterMark(doc) {
    try {
        var app, shapeRange;
        
        var waterText = GetDocParamsValue(doc, "waterText");
        var watermarkCfg = GetDocParamsValue(doc, "watermarkCfg");
        
        if(watermarkCfg.showWaterMark=="true"){
        	app = wps.WpsApplication();
            app.ActiveDocument.Unprotect("");
            var doc = app.ActiveDocument;
            var selection = doc.ActiveWindow.Selection;
            var pageCount = app.ActiveWindow.ActivePane.Pages.Count;
		    var markWordFontSize=(watermarkCfg.markWordFontSize/96)*72;//字体大小
		    var markRowSpace=(watermarkCfg.markRowSpace/96)*72;//纵向间距
		    var markColSpace=(watermarkCfg.markColSpace/96)*72;//横向间距
    		var wpsHeight=wps.WpsApplication().ActiveWindow.Selection.PageSetup.PageHeight;
    		var wpsWidth =wps.WpsApplication().ActiveWindow.Selection.PageSetup.PageWidth;
            for(var i=1;i<=pageCount;i++){
    	        
    	        for(var z=0;z<=wpsHeight/markRowSpace;z++){
    	        	var colheiht=markRowSpace*z+markWordFontSize*z;
	                for(var y=0;y<=wpsWidth/markColSpace;y++){
	    				var rowWidth=markColSpace*y+markWordFontSize*y;
				    	selection.GoTo(1, 1, i);
						wps.WpsApplication().ActiveDocument.ActiveWindow.View.SeekView=10;
				        app.ActiveDocument.Sections.Item(1).Headers.Item(1).Shapes.AddTextEffect(0, waterText, watermarkCfg.markWordFontFamily, markWordFontSize, false, false, rowWidth, colheiht).Select();
						shapeRange = app.Selection.ShapeRange;
			            shapeRange.TextEffect.NormalizedHeight = false;
			            shapeRange.Line.Visible = false;
			            shapeRange.Fill.Visible = true;
			            shapeRange.Fill.Solid();
			            shapeRange.Fill.ForeColor.RGB = 13056;       /* WdColor枚举 wdColorGray25 代表颜色值 */
			            shapeRange.Fill.Transparency = watermarkCfg.markOpacity;             /* 填充透明度，值为0.0~1.0 */
			            shapeRange.LockAspectRatio = true;
			            //shapeRange.Height = 4.58 * 28.346;
			            //shapeRange.Width = 28.07 * 28.346;
						if(watermarkCfg.markRotateType=="horizontal")
				            	shapeRange.Rotation = 0;                      /* 图形按照Z轴旋转度数，正值为顺时针旋转，负值为逆时针旋转 */
				        else
							shapeRange.Rotation =watermarkCfg.markRotateAngel;
						
						shapeRange.WrapFormat.AllowOverlap = true;
				            shapeRange.WrapFormat.Side = 3;                 /* WdWrapSideType枚举 wdWrapLargest 形状距离页边距最远的一侧 */
				            shapeRange.WrapFormat.Type = 5;                 /* WdWarpType枚举 wdWrapNone 形状放到文字前面 */
				            shapeRange.RelativeHorizontalPosition = 0;      /* WdRelativeHorizontalPosition枚举 wdRelativeHorizontalPositionMargin 相对于边距 */
				            shapeRange.RelativeVerticalPosition = 0;        /* WdRelativeVerticalPosition枚举 wdRelativeVerticalPositionMargin 相对于边距 */
				            //shapeRange.Left = '-999995';                    /* WdShapePosition枚举 wdShapeCenter 形状的位置在中央 */
				            //shapeRange.Top = '-999995';                     /* WdShapePosition枚举 wdShapeCenter 形状的位置在中央 */
	    			}
    	        }
               
            }                  /* WdShapePosition枚举 wdShapeCenter 形状的位置在中央 */
            selection.GoTo(1, 1, 1);
            
            var ekpAttMainId = GetDocParamsValue(doc, "ekpAttMainId");
            app.ActiveDocument.Protect(3,"",ekpAttMainId);
        }
        
    } catch (error) {
        alert(error.message);
        wps.WpsApplication().ActiveDocument.Protect(3,"","")
    }
}

/**
 * 套用模板插入文字/图片/文档
 *  * params参数结构
 * params:{
 *     'docId': docId, //文档ID
 *     'templateURL':'',获取模板接口
 *     'fileName':'',获取文档接口
 *     'uploadPath':'',文档保存上传接口
 * }
 * 这个功能要求服务端传来的模板JSON数据字符串，支持三种类型定义：
 *  text 类型：直接插入对应的书签位置
 *  link 类型： 把对应的URL的文件插入到指定的书签位置
 *  pic 类型： 把对应的URL的图片文件插入到指定的书签位置
 * @param {*} params 
 */
function GetServerTemplateData(template, pTemplateDataUrl) {
    //获取文档内容
    $.ajax({
        url: pTemplateDataUrl,
        async: false,
        method: "get",
        dataType: 'json',
        success: function(res) {
            var data = res;
            let Bookmarks = template.Bookmarks;
            data.forEach(function(it) {

                var bookmark = Bookmarks.Item(it.name);
                let bookStart = bookmark.Range.Start;
                let bookEnd = bookmark.Range.End;
                let start = template.Range().End
                if (bookmark) {
                    if (!it.type || it.type === "text") {
                        bookmark.Range.Text = it.text;
                    } else if (it.type === "link") {
                        bookmark.Range.InsertFile(it.text);
                    } else if (it.type === "pic") {
                        bookmark.Range.InlineShapes.AddPicture(it.text);
                    }
                }
                let end = template.Range().End
                if (!Bookmarks.Exists(bookmark.Name))
                    Bookmarks.Add(bookmark.Name, bookmark.Range.SetRange(bookStart, bookEnd + (end - start)))
            })
        }
    });
}

/**
 * 打开服务端的文档（不落地）
 * @param {*} fileUrl 文件url路径
 */
function OpenOnLineFile(OAParams) {
    //OA参数如果为空的话退出
    if (!OAParams) return;

    //获取在线文档URL
    var l_OAFileUrl = OAParams.fileName;
    var l_doc;
  //判断是否有打开文件
    var currentAttMainId=OAParams.docId;
    var Documents = wps.WpsApplication().Documents;
	  for (var i = 1; i < Documents.Count+1; i++) {
	    var n_doc=Documents.Item(i);
	    var ekpAttMainId = GetDocParamsValue(n_doc, "ekpAttMainId");
	    if(ekpAttMainId==currentAttMainId){
	      var startState = wps.WpsApplication().WindowState;
	        //2是wps客户端最小化   1是最大化
	        if(startState!=1){
	           wps.WpsApplication().WindowState=1; 
	        }
	      n_doc.Activate();
	      hideLoading();
	      return;
	    }
	    
	  }
    if (l_OAFileUrl) {
        //下载文档不落地（16版WPS的925后支持）
        wps.WpsApplication().Documents.OpenFromUrl(l_OAFileUrl, "OnOpenOnLineDocSuccess", "OnOpenOnLineDocDownFail");
        l_doc = wps.WpsApplication().ActiveDocument;
    }
    //执行文档打开后的方法
    pOpenFile(l_doc, OAParams, true);
    return l_doc;
}


/**
 * 打开在线文档成功后触发事件
 * @param {*} resp 
 */
function OnOpenOnLineDocSuccess(resp) {

}


/**
 *  打开在线不落地文档出现失败时，给予错误提示
 */
function OnOpenOnLineDocDownFail() {
    alert("打开在线不落地文档失败！请尝试重新打开。");
    return;
}

/**
 * 参数：
 * doc : 当前OA文档的Document对象
 * DocLandMode ： 落地模式设置
 */
function DoSetOADocLandMode(doc, DocLandMode) {
    if (!doc) return;
    var l_Param = wps.PluginStorage.getItem(doc.DocID);
    var l_objParam = JSON.parse(l_Param);
    //增加属性，或设置
    l_objParam.OADocLandMode = DocLandMode; //设置OA文档的落地标志

    var l_p = JSON.stringify(l_objParam);
    //将OA文档落地模式标志存入系统变量对象保存

    wps.PluginStorage.setItem(doc.DocID, l_p);

}


/**
 * 作用：设置Ribbon工具条的按钮显示状态
 * @param {*} paramsGroups 
 */
function pDoResetRibbonGroups(paramsGroups) {

}

/**
 * 作用：打开文档处理的各种过程，包含：打开带密码的文档，保护方式打开文档，修订方式打开文档等种种情况
 * params	Object	OA Web端传来的请求JSON字符串，具体参数说明看下面数据
 * TempLocalFile : 字符串 先把文档从OA系统下载并保存在Temp临时目录，这个参数指已经下载下来的本地文档地址
 * ----------------------以下是OA参数的一些具体规范名称
 * docId	String	文档ID
 * uploadPath	String	保存文档接口
 * fileName	String	获取服务器文档接口（不传即为新建空文档）
 * suffix	String	".pdf|.uot"，可传多个，用“|”分割，保存时会按照所传的值转成对应的格式文档并上传
 * userName	String	用于更改显示修改人的用户名
 * strBookmarkDataPath	string	书签列表 (可不传，可以在OA助手config.js中配置)
 * templatePath	string	模板列表 (可不传，可以在OA助手config.js中配置)
 * buttonGroups	string	自定义按钮组 （可不传，不传显示所有按钮）
 * revisionCtrl	String	痕迹控制 ，不传正常打开
 *      bOpenRevision	String	true(打开)false(关闭)修订
 *      bShowRevision	String	true(显示)/false(关闭)痕迹
 * openType	String	文档打开方式 ，不传正常打开
 *      protectType	String	文档保护类型，-1：不启用保护模式，0：只允许对现有内容进行修订，1：只允许添加批注，2：只允许修改窗体域，3：只读
 *      password	String密码
 */
function pDoOpenOADocProcess(params, TempLocalFile) {
    var l_ProtectType = -1; //默认文档保护类型 -1 为不启用保护
    var l_ProtectPassword = ""; //默认文档密码为空

    var l_strDocPassword = ""; //打开文档密码参数  
    var l_bOpenRevision = false; //初始化关闭修订模式
    var l_bShowRevision = false; //初始化不显示修订气泡样式

    for (var key = "" in params) {
        switch (key.toUpperCase()) //
        {
            case "userName".toUpperCase(): //修改当前文档用户名
                wps.WpsApplication().UserName = params[key];
                break;
            case "openType".toUpperCase():
                l_ProtectType = params[key].protectType; //获取OA传来的文档保护类型
                l_ProtectPassword = params[key].password; //获取OA传来的保护模式下的文档密码
                break;
            case "revisionCtrl".toUpperCase(): //限制修订状态
                l_bOpenRevision = params[key].bOpenRevision;
                l_bShowRevision = params[key].bShowRevision;
                break;
            case "buttonGroups".toUpperCase(): //按钮组合
                break;
            case "docPassword".toUpperCase(): //传入打开文件的密码
                l_strDocPassword = params[key].docPassword;
                break;
        }

    }

    var l_Doc;
    // Open方法的参数说明如下
    //Function Open(FileName, [ConfirmConversions], [ReadOnly], [AddToRecentFiles], 
    //  [PasswordDocument], [PasswordTemplate], [Revert], [WritePasswordDocument],
    //  [WritePasswordTemplate], [Format], [Encoding], [Visible], 
    //  [OpenAndRepair], [DocumentDirection], [NoEncodingDialog], [XMLTransform]) As Document
    l_Doc = wps.WpsApplication().Documents.Open(TempLocalFile, false, false, false, l_strDocPassword);
	if(params['wpsExtAppModel'] == "kmImissive"){
		l_bShowRevision = true;
	}
    //设置文档修订状态
    DoOADocOpenRevision(l_Doc, l_bOpenRevision, l_bShowRevision);

    //打开文档后，根据保护类型设置文档保护
    if (l_ProtectType > -1) // -1 :不设置文档保护 
        SetOADocProtect(l_Doc, l_ProtectType, l_ProtectPassword);
    return l_Doc;
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
function SetOADocProtect(doc, protectType, ProtectPassword) {
    if (!doc) return; //校验文档是否存在
    if ([0, 1, 2, 3].indexOf(protectType) !== -1) {
        // 保护文档如果之前有被保护，再次保护会出问题，需要先解除保护
        doc.Unprotect();
        // ** 方法参数
        // ** Protect(Type As WdProtectionType, [NoReset], [Password], [UseIRM], [EnforceStyleLock])
        doc.Protect(protectType, false, ProtectPassword, false);
    }
    return;
}




/**
 * 打开/关闭修订
 * @param {*} doc :传入文档对象
 * @param {*} bOpenRevision :  布尔值，是否允许打开修订模式，true:打开/false:关闭
 * @param {*} bOpenRevision ： 布尔值，是否显示修订痕迹状态
 * 返回值：无
 */
function DoOADocOpenRevision(doc, bOpenRevision, bShowRevision) {
    if (!doc) return;

    doc.TrackRevisions = bOpenRevision; //如果标记对指定文档的修改，则该属性值为True
    var l_v = doc.ActiveWindow.View;
    l_v.ShowRevisionsAndComments = bShowRevision; //如果为True，则 WPS 显示使用“修订”功能对文档所作的修订和批注
    l_v.RevisionsBalloonShowConnectingLines = bShowRevision; //如果为 True，则 WPS 显示从文本到修订和批注气球之间的连接线
    //wps.WpsApplication().CommandBars.ExecuteMso("KsoEx_RevisionCommentModify_Disable"); //去掉修改痕迹信息框中的接受修订和拒绝修订勾叉，使其不可用
    doc.Saved=true;

    //if (bShowRevision) {
        //doc.ActiveWindow.ActivePane.View.RevisionsMode = 2; //2为不支持气泡显示。
    //}

    //如果关闭修订,关闭显示痕迹并将按钮至灰
    wps.ribbonUI.InvalidateControl("btnOpenRevision");
    wps.ribbonUI.InvalidateControl("btnShowRevision");

    return;
}



/**
 *   描述：如何处理再次打开相同的OA文件
 *   返回值：打开的Document对象
 */
function pReOpenOADoc(OADocURL) {
    if (wps.confirm("当前环境已打开该文件，是否重新再打开一份？")) {
        //如果用户选择再次打开，则用在线方式打开
        wps.WpsApplication().Documents.OpenFromUrl(OADocURL, "", "");
    }
}

/**
 * 功能说明：判断是否已存在来自OA的已打开的文档
 * @param {字符串} FileURL 
 */
function pCheckIsExistOpenOADoc(FileURL) {
    var l_DocCount = wps.WpsApplication().Documents.Count;
    if (l_DocCount <= 0) return false;

    //轮询检查当前已打开的WPS文档中，是否存在OA相同的文件
    if (l_DocCount >= 1) {
        for (var l_index = 1; l_index <= l_DocCount; l_index++) {
            var l_objDoc = wps.WpsApplication().Documents.Item(l_index);

            var l_strParam = wps.PluginStorage.getItem(l_objDoc.DocID);
            if (l_strParam == null)
                continue;
            var l_objParam = JSON.parse(l_strParam)
            if (l_objParam.fileName == FileURL) {
                return true;
            }
        }
        return false;
    }
}

//Office文件打开后，设置该文件属性：从服务端来的OA文件
function pSetOADocumentFlag(doc, params) {
    if (!doc) {
        return; //
    }

    var l_Param = params;
    l_Param.isOA = EnumOAFlag.DocFromOA; //设置OA打开文档的标志

    l_Param.SourcePath = doc.FullName; //保存OA的原始文件路径，用于保存时分析，是否进行了另存为操作
    //console.log(l_Param.SourcePath);

    if (doc) {
        var l_p = JSON.stringify(l_Param);
        //将OA文档标志存入系统变量对象保存
        wps.PluginStorage.setItem(doc.DocID, l_p);
    }
}

/**
 * 作用：
 * @param {*} suffix  ：文档后缀明：.pdf 或 .uot 或 .uof
 * @param {*} doc 
 * @param {*} uploadPath 
 * @param {}    FieldName ： 上传到服务器端的字段名称，可由OA传入的参数设置
 * 
 * 返回值：是否执行了上传操作，布尔值
 */
function handleFileAndUpload(suffix, doc, uploadPath, FieldName) {
    var l_strChangeFileName = ""; // 转换格式后的文件名称
    var l_strPath = ""; // 转换格式后的文件路径
    var l_FieldName = FieldName;

    if (!doc) {
        return false;
    }
    if (!l_FieldName) {
        l_FieldName = "file"; //默认情况下，设置为 file 字段名称
    }

    var l_DocSourcePath = doc.FullName; //保留当前文档明，在SaveAs使用后再保存回原来的文件明

    //Sub ExportAsFixedFormat(OutputFileName As String, ExportFormat As WdExportFormat, 
    //                       [OpenAfterExport As Boolean = False],
    //                       [OptimizeFor As WdExportOptimizeFor = wdExportOptimizeForPrint], 
    //                       [Range As WdExportRange = wdExportAllDocument], [From As Long = 1], 
    //                       [To As Long = 1], [Item As WdExportItem = wdExportDocumentContent], 
    //                       [IncludeDocProps As Boolean = False], [KeepIRM As Boolean = True],
    //                       [CreateBookmarks As WdExportCreateBookmarks = wdExportCreateNoBookmarks],
    //                       [DocStructureTags As Boolean = True], [BitmapMissingFonts As Boolean = True], 
    //                       [UseISO19005_1 As Boolean = False], [FixedFormatExtClassPtr])

    //          Const wdExportFormatPDF = 17 (&H11)
    //          Const wdExportFormatXPS = 18 (&H12)
    //

    //根据传入的 后缀文件名称进行不同的转换文档操作
    switch (suffix.toLocaleLowerCase()) {
        case '.pdf':
            l_strPath = pGetValidDocTempPath(doc) + ".pdf"; //获取有效输出路径
            wps.FileSystem.Remove(l_strPath); //先删除之前可能存在的临时文件
            doc.ExportAsFixedFormat(l_strPath, wps.Enum.wdFormatPDF, true); //文档另存为PDF格式
            l_strChangeFileName = doc.Name.split(".")[0] + ".pdf";
            UploadFile(l_strChangeFileName, l_strPath, uploadPath, l_FieldName, OnChangeSuffixUploadSuccess, OnChangeSuffixUploadFail);
            break;
        case '.uof':
            l_strPath = pGetValidDocTempPath(doc) + suffix;
            wps.FileSystem.Remove(l_strPath); //先删除之前可能存在的临时文件
            doc.ExportAsFixedFormat(l_strPath, wps.Enum.wdFormatOpenDocumentText, true); //转换文件格式
            doc.SaveAs2(l_strPath);
            l_strChangeFileName = doc.Name.split(".")[0] + suffix;
            UploadFile(l_strChangeFileName, l_strPath, uploadPath, l_FieldName, OnChangeSuffixUploadSuccess, OnChangeSuffixUploadFail);
            doc.SaveAs2(l_DocSourcePath); //保存回原来的文档内容
            break;
        case '.uot':
            l_strPath = pGetValidDocTempPath(doc) + suffix;
            wps.FileSystem.Remove(l_strPath); //先删除之前可能存在的临时文件
            doc.ExportAsFixedFormat(l_strPath, wps.Enum.wdFormatOpenDocumentText, true);
            doc.SaveAs2(l_strPath);
            l_strChangeFileName = doc.Name.split(".")[0] + suffix;
            UploadFile(l_strChangeFileName, l_strPath, uploadPath, l_FieldName, OnChangeSuffixUploadSuccess, OnChangeSuffixUploadFail);
            doc.SaveAs2(l_DocSourcePath); //保存回原来的文档内容
            break;
        case '.ofd':
            l_strPath = pGetValidDocTempPath(doc) + suffix;
            wps.FileSystem.Remove(l_strPath); //先删除之前可能存在的临时文件
            doc.ExportAsFixedFormat(l_strPath, wps.Enum.wdFormatOpenDocumentText, true);
            doc.SaveAs2(l_strPath);
            l_strChangeFileName = doc.Name.split(".")[0] + suffix;
            UploadFile(l_strChangeFileName, l_strPath, uploadPath, l_FieldName, OnChangeSuffixUploadSuccess, OnChangeSuffixUploadFail);
            doc.SaveAs2(l_DocSourcePath); //保存回原来的文档内容
            break;
        default:
            l_strPath = pGetValidDocTempPath(doc) + suffix;
            wps.FileSystem.Remove(l_strPath); //先删除之前可能存在的临时文件
            doc.SaveAs2(l_strPath);
            l_strChangeFileName = doc.Name.split(".")[0] + suffix;
            UploadFile(l_strChangeFileName, l_strPath, uploadPath, l_FieldName, OnChangeSuffixUploadSuccess, OnChangeSuffixUploadFail);
            doc.SaveAs2(l_DocSourcePath); //保存回原来的文档内容
            break;
    }

    wps.FileSystem.Remove(l_strPath); //上载完成后，删除临时文件
    return true;
}

/**
 * 作用：获取一个有效的临时文档路径，用于保存转换格式后的文档
 * @param {*} doc 
 */
function pGetValidDocTempPath(doc) {
    if (!doc) {
        return;
    }
    if (doc.Path == "") { //对于不落地文档，文档路径为空
        return wps.Env.GetTempPath();
    } else {
        return doc.FullName.split(".")[0]
    }
}

/**
 * 作用：转格式保存上传成功后，触发这个事件的回调
 * @param {} response 
 */
function OnChangeSuffixUploadSuccess(response) {
    l_result = handleResultBody(response);
    alert("文件转格式保存成功！");
}

/**
 * 作用：转格式保存失败，触发失败事件回调
 * @param {*} response 
 */
function OnChangeSuffixUploadFail(response) {
    var l_result = "";
    l_result = handleResultBody(response);
    alert("保存失败" + "\n" + +"系统返回数据：" + +JSON.stringify(l_result));
}

/**
 * 解析返回response的参数
 * @param {*} resp 
 * @return {*} body
 */
function handleResultBody(resp) {
    var l_result = "";
    if (resp.Body) {
        //解析返回response的参数
    }
    return l_result;
}

/**
 * 把OA文件的当前编辑内容，自动提交到OA后台
 */
function pAutoUploadToServer(p_Doc) {
    if (!p_Doc) {
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

    var l_uploadPath = GetDocParamsValue(p_Doc, constStrEnum.uploadPath); // 文件上载路径
    if (l_uploadPath == "") {
        //wps.alert("系统未传入文件上载路径，不能执行上传操作！");
        return;
    }

    var l_FieldName = GetDocParamsValue(p_Doc, constStrEnum.uploadFieldName); //上载到后台的字段名称
    if (l_FieldName == "") {
        l_FieldName = wps.PluginStorage.getItem(constStrEnum.DefaultUploadFieldName); // 默认为‘file’
    }

    var l_UploadName = GetDocParamsValue(p_Doc, constStrEnum.uploadFileName); //设置OA传入的文件名称参数
    if (l_UploadName == "") {
        l_UploadName = p_Doc.Name; //默认文件名称就是当前文件编辑名称
    }

    var l_DocPath = p_Doc.FullName; // 文件所在路径

    if (pIsOnlineOADoc(p_Doc) == false) {
        console.log("落地文档自动保存");
        //对于本地磁盘文件上传OA，先用Save方法保存后，在上传
        //设置用户保存按钮标志，避免出现禁止OA文件保存的干扰信息
        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.OADocSave);
        p_Doc.Save(); //执行一次保存方法
        //设置用户保存按钮标志
        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.NoneOADocSave);
        //落地文档，调用UploadFile方法上传到OA后台
        try {
            //调用OA助手的上传方法
            UploadFile(l_UploadName, l_DocPath, l_uploadPath, l_FieldName, OnAutoUploadSuccess, OnAutoUploadFail);
        } catch (err) {
            alert("上传文件失败！请检查系统上传参数及网络环境！");
        }
    } else {
        console.log("不落地文档自动保存");
        // 不落地的文档，调用 Document 对象的不落地上传方法
        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.OADocSave);
        try {
            //调用不落地上传方法
            p_Doc.SaveAsUrl(l_UploadName, l_uploadPath, l_FieldName, "OnAutoUploadSuccess", "OnAutoUploadFail");
        } catch (err) {}
        wps.PluginStorage.setItem(constStrEnum.OADocUserSave, EnumDocSaveFlag.NoneOADocSave);
    }

}

/**
 * 作用：自动上传到OA后台成功后出发事件
 */
function OnAutoUploadSuccess(resp) {
    return;
}

/**
 * 作用：自动上传到OA后台成功后出发事件
 */
function OnAutoUploadFail(resp) {
    return;
}

/**
 * 按照定时器的时间，自动执行所有文档的自动保存事件
 */
function OnDocSaveByAutoTimer() {
    var l_Doc;

    var l_Count = 0
    var l_docCounts = wps.WpsApplication().Documents.Count;
    for (l_Count = 0; l_Count < l_docCounts; l_Count++) {
        l_Doc = wps.WpsApplication().Documents.Item(l_Count);
        if (l_Doc) {
            if (pCheckIfOADoc(l_Doc) == true) { // 是否为OA文件
                if (pISOADocReadOnly(l_Doc) == false) { // 是否为只读文档
                    //执行自动上传到OA服务器端的操作
                    pAutoUploadToServer(l_Doc);
                    //保存该文档对应的访问过程记录信息
                }
            }
        }
    }
}

/**
 * 实现一个定时器
 */
function OpenTimerRun(funcCallBack) {
    var l_mCount = 0; //设置一个计时器，按每分钟执行一次; 10分钟后重复执行
    var l_timeID = 0; //用于保存计时器ID值

    // 对间隔时间做处理
    var l_AutoSaveToServerTime = wps.PluginStorage.getItem(constStrEnum.AutoSaveToServerTime);
    if (l_AutoSaveToServerTime == 0) { // 设置为0则不启动定时器
        l_timeID = wps.PluginStorage.getItem(constStrEnum.TempTimerID);
        clearInterval(l_timeID);
        return;
    } else if (l_AutoSaveToServerTime < 3) {
        l_AutoSaveToServerTime = 3;
    }

    l_timeID = setInterval(function() {
        l_mCount = l_mCount + 1;
        if (l_mCount > l_AutoSaveToServerTime) { //l_AutoSaveToServerTime 值由系统配置时设定，见pInitParameters()函数
            l_mCount = 0;
            funcCallBack(); //每隔l_AutoSaveToServerTime 分钟（例如10分钟）执行一次回调函数
        }
    }, 60000); //60000 每隔1分钟，执行一次操作(1000*60)

    wps.PluginStorage.setItem(constStrEnum.TempTimerID, l_timeID); //保存计时器ID值
}

/**
 * 从OA-web端点击套红头
 *  params : 需要存在以下参数
 *      'insertFileUrl':'',获取红头模板接口
 *      'bkInsertFile':'' ,正文书签
 */
function InsertRedHead(params) {
    var wpsApp = wps.WpsApplication();
    var activeDoc = wpsApp.ActiveDocument;
    if (!activeDoc) {
        alert('文档不存在，请先新建一个文档!');
        return;
    }

    var bookmark = GetParamsValue(params, constStrEnum.bkInsertFile);
    var strFile = GetParamsValue(params, constStrEnum.insertFileUrl);
    if (strFile == "") {
        alert("未获取到传入的红头模板URL路径，不能正常套红");
        return;
    }

    if (bookmark == "") {
        alert("获取到传入的正文书签，不能正常套红");
        return;
    }
    pInsertRInedHead(activeDoc, strFile, bookmark);
}

/**
 * 套红头
 *  doc ：套红头的文档
 *  strFile ：获取红头模板接口
 *  bookmark ：,正文书签
 */

function pInsertRInedHead(doc, strFile, bookmark) {
    var bookMarks = doc.Bookmarks;
    var wpsApp = wps.WpsApplication();
    var activeDoc = wpsApp.ActiveDocument;
    var selection = wpsApp.ActiveWindow.Selection;
    // 准备以非批注的模式插入红头文件(剪切/粘贴等操作会留有痕迹,故先关闭修订)
    activeDoc.TrackRevisions = false;
    selection.WholeStory(); //选取全文
    // selection.Cut();
    // activeDoc.Range(0,0).Select()
    // selection.InsertFile(strFile);
    InsertFile(strFile,bookmark,activeDoc,InsertRedHeadAfter)
    
}
function InsertRedHeadAfter(){
    // 轮询插入书签
    var doc=wps.WpsApplication().ActiveDocument;
    var elements = GetDocParamsValue(doc, constStrEnum.redFileElement);
    if (elements != "") {
        for (var key in elements) {
            console.log(key + "----" + elements[key])
            if (bookMarks.Exists(key)) {
                // 直接插入
                var eleBookmark = bookMarks.Item(key);
                eleBookmark.Range.Text = elements[key];
            }
        }
    }

    // 恢复修订模式(根据传入参数决定)
    var l_revisionCtrl = GetDocParamsValue(doc, constStrEnum.revisionCtrl);
    doc.TrackRevisions = l_revisionCtrl == "" ? false : l_revisionCtrl.bOpenRevision;
    //取消WPS关闭时的提示信息
    wps.WpsApplication().DisplayAlerts = wps.Enum&&wps.Enum.wdAlertsNone||0;
}
/*function pInsertRInedHead(doc, strFile, bookmark) {
    var bookMarks = doc.Bookmarks;

    var wpsApp = wps.WpsApplication();
    var activeDoc = wpsApp.ActiveDocument;
    var selection = wpsApp.ActiveWindow.Selection;
    // 准备以非批注的模式插入红头文件(剪切/粘贴等操作会留有痕迹,故先关闭修订)
    activeDoc.TrackRevisions = false;
    selection.WholeStory(); //选取全文
    selection.Cut();
    selection.InsertFile(strFile);
 	// 轮询插入书签
	if(bookmark!="redhead"){
		var imssiveRed = GetDocParamsValue(doc, "bookMarks");
		imssiveRed = JSON.parse(imssiveRed);
		for (var key in bookmark) {
			var bookVal = bookmark[key];
			if(bookVal=="redhead"){
				var book= bookMarks.Item(bookVal);
			    book.Range.Select(); //获取指定书签位置
			    var s = activeDoc.ActiveWindow.Selection;
			    s.Paste();
			}else{
				var book= bookMarks.Item(bookVal);
				let bookStart = book.Range.Start;
	            let bookEnd = book.Range.End;
	            let start = doc.Range().End
	
				if(imssiveRed[bookVal]!=null && imssiveRed[bookVal]!=undefined){
					book.Range.Text=imssiveRed[bookVal];
				}
				let end = doc.Range().End
	            let range=book.Range;
				if (!bookMarks.Exists(bookVal)){
					//bookMarks.Add(bookVal, range.SetRange(bookStart, bookEnd + (end - start)))
					var book=bookMarks.Add(bookVal,doc.Range(0,0));
					book.Start=bookStart;
					book.End= bookEnd + (end - start);
				}
			}
		}
	}else{
		var book= bookMarks.Item("redhead");
		book.Range.Select(); //获取指定书签位置
		var s = activeDoc.ActiveWindow.Selection;
		s.Paste();
	}

    // 恢复修订模式(根据传入参数决定)
    var l_revisionCtrl = GetDocParamsValue(activeDoc, constStrEnum.revisionCtrl);
    activeDoc.TrackRevisions = l_revisionCtrl == "" ? false : l_revisionCtrl.bOpenRevision;
	SetDocParamsValue(activeDoc,"ekp_wps_atttrac","true");
    //取消WPS关闭时的提示信息
	if(wps.Enum){
		wps.WpsApplication().DisplayAlerts = wps.Enum.wdAlertsNone;
	}
    //wps.WpsApplication().DisplayAlerts = wps.Enum.wdAlertsNone;
}*/
/**
 * 从OA-web端点击套红头
 *  doc : 需要存在以下属性
 *      'insertFileUrl':'',获取红头模板接口
 *      'bkInsertFile':'' ,正文书签
 */
function InsertRedHeadDoc(doc) { //插入红头
    if (!doc) {
        alert('文档不存在!');
        return;
    }
    var bookmark = GetDocParamsValue(doc, constStrEnum.bkInsertFile);
    var strFile = GetDocParamsValue(doc, constStrEnum.insertFileUrl);
    if (strFile == "") {
        alert("未获取到系统传入的红头模板URL路径，不能正常套红");
        return;
    }
    if (bookmark == "") {
        alert("套红头失败，您选择的红头模板没有正文书签！");
        return;
    }

    pInsertRInedHead(doc, strFile, bookmark);
}

/**
 *  打开本地文档，并插入文档
 */
function OpenLocalFile() {
    var l_FileName = "";

    //msoFileDialogFilePicker = 3
    var l_FileDialog = wps.WpsApplication().FileDialog(3);
    if (l_FileDialog.Show()) {
        l_FileName = l_FileDialog.SelectedItems;
        if (l_FileName.Count > 0) {
            wps.WpsApplication().Selection.InsertFile(l_FileName.Item(1));
        }
    }
}



/**
 *  打开本地文档，并插入文档
 */
function OpenLocalImissive() {
    var l_FileName = "";

    //msoFileDialogFilePicker = 3
    var l_FileDialog = wps.WpsApplication().FileDialog(3);
    if (l_FileDialog.Show()) {
        l_FileName = l_FileDialog.SelectedItems;
        if (l_FileName.Count > 0) {
			var filePath = l_FileName.Item(1);
			//获取最后一个.的位置
			var index= filePath.lastIndexOf(".");
			//获取后缀
			var ext = filePath.substr(index+1).toLowerCase();
			
			if(ext == "doc" || ext == "docx" || ext == 'wps'){	
				btnNewImissiveFile();
				wps.WpsApplication().Selection.InsertFile(l_FileName.Item(1));
			}
        }
    }
}

/**
 * 删除内容
 * @returns
 */
function btnNewImissiveFile() {
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
 * 作用：OA的正文备份按钮操作功能，把OA文件备份一份到指定的OA后台copyUrl路径中
 */
function OnUploadOABackupClicked() {
    var doc = wps.WpsApplication().ActiveDocument;
    if (!doc) {
        alert("当前没有打开任何文档");
        return;
    }
    var l_copyUrl = GetDocParamsValue(doc, constStrEnum.copyUrl);
    var l_uploadFieldName = GetDocParamsValue(doc, constStrEnum.uploadFieldName);

    if (!l_copyUrl) {
        alert("系统未传入备份URL路径，不能执行备份！");
        return;
    }

    if (!l_uploadFieldName) {
        l_uploadFieldName = "file";
    }
    // 默认保存为新文档，走上传文档的接口
    UploadFile(doc.Name, doc.FullName, l_copyUrl, l_uploadFieldName, OnBackupSuccess, OnBackupFail);
}

/**
 * 备份成功后的回调
 */
function OnBackupSuccess() {
    wps.alert("备份上传成功");
}

/**
 * 备份失败后的回调
 */
function OnBackupFail() {
    wps.alert("备份失败");
}