Com_RegisterFile("wps_linux_attachment.js");
Com_IncludeFile("common.js");
Com_IncludeFile("data.js");

var wpsLinuxPluginObj;
function WPSLinuxOffice_AttachmentObject(fdId, fdKey,fdModelId,fdModelName, fdMode,fdFileName) {
    this.fdId = fdId;
    this.fdKey = fdKey; // 附件标识
    this.fdModelName = fdModelName;
    this.fdModelId = fdModelId;
    this.optMode = Com_GetUrlParameter(location.href, "method");
    this.fdMode = fdMode;
    this.contentFlag = false;  //公文单子控制正文下载打印权限使用
    this.isTemplate = false; //是否是模板正文
    this.canPrint = '';
    this.canExport = '';

    this.fdFileName=fdFileName;//文件名称

    this.container = "wpsLinux_" + fdKey;
    this.wpsObj = null;
    this.wpsRevisionsObj = null;
    this.updateRelationUrl =  Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=uploadWps";
    this.updateTmpRelationUrl =  Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsTmp";

    this.hasLoad = false;
    this.isLoading = false;
    this.forceLoad = false;

    this.isSubmitByWps = true;

    //临时文件ID,其中editonline为正文，editonlinePrint为打印
    this.tmpFileAttmainId;
    this.isCurrent=true;
    this.loadCount=0;

    this.init = WPSLinuxOffice_Attachment_init;
    this.excelInit = WPSLinuxOffice_Attachment_excel_init;
    this.pptInit = WPSLinuxOffice_Attachment_ppt_init;
    this.load = WPSLinuxOffice_Attachment_Load;
    this.unLoad = WPSLinuxOffice_Attachment_UnLoad;
    this.submit = WPSLinuxOffice_Attachment_Submit;
    this.print = WPSLinuxOffice_Attachment_Print;
    this.accent = WPSLinuxOffice_Attachment_accent;
    this.reject = WPSLinuxOffice_Attachment_reject;
    this.handleRevision = WPSLinuxOffice_Attachment_handleRevision;

    this.showRevision = WPSLinuxOffice_Attachment_showRevision;

    this.setTmpFileByAttKey = WPSLinuxOffice_Attachment_setTmpFileByAttKey;

    //this.getTmpFileByAttKey = WPSLinuxOffice_Attachment_getTmpFileByAttKey;

}

/***********************************************
 初始化word
 ***********************************************/
function WPSLinuxOffice_Attachment_init() {
    var _self = this;
    var iframe;
    var obj;

    iframe = document.getElementById(_self.container);
    iframe.innerHTML = '';
    var codes = [];
    codes.push(
        "<object style='z-index:99999999' name='webwps' id='webwps_id' type='application/x-wps' width='100%' height='100%'> <param name='Enabled' value='1' />  </object>"
    );

    iframe.innerHTML = codes.join("");
    obj = document.getElementById("webwps_id");

    window.onbeforeunload = function () {
        //obj.Application.Saved=true;
        obj.Application.Quit();
    };

    window.onresize = function () {
        console.log("ondrag");
        obj.sltReleaseKeyboard();
    };
    //obj.Application.DisabledHotKeys = "(ctrl+s)";
    if(obj.Application){
        obj.Application.DisabledHotKeys = "(ctrl+s)";
    }

    return obj.Application;
}

/***********************************************
 初始化excel
 ***********************************************/
function WPSLinuxOffice_Attachment_excel_init() {
    var _self = this;
    var office = null;

    if (office != undefined)
        office.Application.Quit();
    var iframe = document.getElementById(_self.container);
    var codes = [];
    codes.push('<object name="rpcet" style="z-index:99999999" id="rpcet_id" type="application/x-et" wpsshieldbutton="false" width="100%" height="100%">');
    codes.push('<param name="quality" value="high" />');
    codes.push('<param name="bgcolor" value="#ffffff" />');
    codes.push('<param name="Enabled" value="1" />');
    codes.push('<param name="allowFullScreen" value="true" />');
    codes.push('</object>');
    iframe.innerHTML = codes.join("");
    office = document.getElementById("rpcet_id");
    window.onbeforeunload = function () {
        office.Application.Quit();
    };

    return office.Application;
}

/***********************************************
 初始化PPT
 ***********************************************/
function WPSLinuxOffice_Attachment_ppt_init() {
    var _self = this;
    var office = null;

    if (office != undefined)
        office.Application.Quit();
    var iframe = document.getElementById(_self.container);
    var codes = [];
    codes.push('<object name="rpcwpp" style="z-index:99999999" id="rpcwpp_id" type="application/x-wpp" wpsshieldbutton="false" width="100%" height="100%">');
    codes.push('<param name="quality" value="high" />');
    codes.push('<param name="bgcolor" value="#ffffff" />');
    codes.push('<param name="Enabled" value="1" />');
    codes.push('<param name="allowFullScreen" value="true" />');
    codes.push('</object>');
    iframe.innerHTML = codes.join("");
    office = document.getElementById("rpcwpp_id");
    window.onbeforeunload = function () {
        office.Application.Quit();
    };

    return office.Application;
}

/***********************************************
 功能 打印
 ***********************************************/
function WPSLinuxOffice_Attachment_Print() {
    var _self = this;
    if(_self.wpsObj)
        _self.wpsObj.print();
}

/***********************************************
 功能 打开文档
 ***********************************************/
function WPSLinuxOffice_Attachment_Load() {
    if(checkEnvironment()){
        var _self = this;
        if(self.hasLoad && !self.isLoading){
            self.forceLoad = true;
        }
        if((!self.hasLoad && !self.isLoading) || self.forceLoad){
            _self.isLoading = true;
            if(isWordInWps(_self.fdFileName)) {
                _self.wpsObj = _self.init();
                wpsLinuxPluginObj=_self.wpsObj;
            }
            if(isExcelInWps(_self.fdFileName)){
                _self.wpsObj = _self.excelInit();
            }

            if(isPptInWps(_self.fdFileName)){
                _self.wpsObj = _self.pptInit();
            }

            var fdAttMainId=this.fdId;
            var tmpAttmainId=_self.tmpFileAttmainId;
            if(tmpAttmainId){
                fdAttMainId=tmpAttmainId;
            }
            var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl";
            $.ajax({
                type:"post",
                url:url,
                data:{fileId:fdAttMainId,contentFlag:this.contentFlag},
                async:false,    //用同步方式
                success:function(data){
                    if(isJSON(data)){
                        var results =  eval("("+data+")");
                        if(results['downUrl']&&results['downUrl']!=""){

                            var downloadUrl=results['downUrl'];
                            var userName=results['userName'];

                            if(_self.fdMode == "write"){
                                _self.isCurrent=true;
                                _self.updateRelationUrl=Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=uploadWps&useId="+results['userId']+"&fdId="+_self.fdId+"&wpsOasisstToken="+results['wpsoaassistToken'];
                                _self.updateTmpRelationUrl=Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsTmp&useId="+results['userId']+"&fdId="+results['attMainId']+"&wpsOasisstToken="+results['wpsoaassistToken'];

                                if(isWordInWps(_self.fdFileName)){
                                    _self.wpsObj.openDocumentRemote_s(downloadUrl, false);
                                    _self.wpsObj.setUserName(userName);
                                }

                                if(isExcelInWps(_self.fdFileName)){
                                    _self.wpsObj.openDocumentRemote_s(downloadUrl, false);
                                }
                                if(isPptInWps(_self.fdFileName)){
                                    _self.wpsObj.openDocumentRemote(downloadUrl, false);
                                }

                                _self.wpsObj.registerEvent("DIID_ApplicationEvents4","DocumentBeforeClose","OnDocumentBeforeCloseCallBack");
                                _self.wpsObj.unRegisterEvent("DIID_ApplicationEvents4","DocumentBeforeSave","DocumentBeforeSaveCallBack");

                                {
                                    var Bars = _self.wpsObj.get_CommandBars();
                                    var filebar = Bars.get_Item("file");
                                    var spControls = filebar.get_Controls();
                                    for (var i = 0; i < 40; i++) {
                                        try {
                                            var Controlpdf = spControls.get_Item(i);
                                            //console.log(Controlpdf.get_Caption(), i)
                                            if(i!=25&&i!=26&&i!=27&&i!=29&&i!=33&&i!=34){
                                                Controlpdf.put_Visible(false);
                                            }

                                        } catch (err) {
                                            //console.log(i)
                                        }
                                    }
                                }

                                {
                                    var Bars = _self.wpsObj.get_CommandBars();
                                    var filebar = Bars.get_Item("Standard");
                                    var spControls = filebar.get_Controls();
                                    for (var i = 0; i < 60; i++) {
                                        try {
                                            var Controlpdf = spControls.get_Item(i);
                                            if(i!=6&&i!=7&&i!=8&&i!=9&&i!=11&&i!=12&&i!=13&&i!=14&&i!=17&&i!=19&&i!=25&&i!=26&&i!=27&&i!=32&&i!=34&&i!=35&&i!=38&&i!=39&&i!=41&&i!=52){
                                                Controlpdf.put_Visible(false);
                                            }
                                            //console.log(Controlpdf.get_Caption(), i)

                                        } catch (err) {
                                            //console.log(i)
                                        }
                                    }
                                }
                                //如果是编辑状态，提交表单的时候提交附件内容
                                if(_self.fdMode == "write" && _self.loadCount==0){
                                    top.window.Com_Parameter.event["confirm"].unshift( function() {
                                        return _self.submit();
                                    });
                                }

                                _self.loadCount++;

                                if(_self.optMode != "add" && _self.fdMode == "write" && !_self.isTemplate) {
                                    setTimeout(function () {
                                        _self.handleRevision(true);
                                    }, 500);
                                }

                            }else{
                                if(isWordInWps(_self.fdFileName)) {
                                    _self.wpsObj.openDocumentRemote_s(downloadUrl, true);
                                }

                                if(isExcelInWps(_self.fdFileName)){
                                    _self.wpsObj.openDocumentRemote_s(downloadUrl, true);
                                }
                                if(isPptInWps(_self.fdFileName)){
                                    _self.wpsObj.openDocumentRemote(downloadUrl, true);
                                }

                                _self.wpsObj.registerEvent("DIID_ApplicationEvents4","DocumentBeforeClose","OnDocumentBeforeCloseCallBack");

                                var canCopy=results['canCopy'];
                                var canPrint=results['canPrint'];
                                var download=results['download'];

                                {
                                    var Bars = _self.wpsObj.get_CommandBars();
                                    var filebar = Bars.get_Item("file");
                                    var spControls = filebar.get_Controls();
                                    for (var i = 0; i < 40; i++) {
                                        try {
                                            var Controlpdf = spControls.get_Item(i);
                                            Controlpdf.put_Visible(false);
                                            //console.log(Controlpdf.get_Caption(), i)
                                            if(canPrint&&(i==16||i==17)){
                                                Controlpdf.put_Visible(true);
                                            }
                                            if(download&&(i==4||i==5||i==6||i==25||i==26||i==27)){
                                                Controlpdf.put_Visible(true);
                                            }

                                        } catch (err) {
                                            console.log(i)
                                        }
                                    }
                                }

                                {
                                    var Bars = _self.wpsObj.get_CommandBars();
                                    var filebar = Bars.get_Item("Standard");
                                    var spControls = filebar.get_Controls();
                                    for (var i = 0; i < 60; i++) {
                                        try {
                                            var Controlpdf = spControls.get_Item(i);
                                            Controlpdf.put_Visible(false);
                                            //console.log(Controlpdf.get_Caption(), i)
                                            if(canPrint&&(i==6||i==7)){
                                                Controlpdf.put_Visible(true);
                                            }
                                            if(download&&(i==52)){
                                                Controlpdf.put_Visible(true);
                                            }

                                        } catch (err) {
                                            console.log(i)
                                        }
                                    }
                                }
                                _self.wpsObj.enableCut(canCopy);
                                _self.wpsObj.enableCopy(canCopy);
                            }

                        }else{
                            if(results['error']){
                                seajs.use(["lui/dialog", "lui/jquery"], function(dialog, $) {
                                    dialog.alert(results['error']);
                                });
                            }
                        }
                        _self.hasLoad = true;
                        _self.isLoading = false;
                    }else{
                        $("#"+_self.container).html(data);
                    }

                    if (typeof(seajs) != 'undefined') {
                        seajs.use( ['lui/topic' ], function(topic) {
                            topic.publish('/sys/attachment/wpsOAassistLinux/loaded', _self.wpsObj, {});
                        });
                    }

                }
            });
        }
    }
}


/***********************************************
 功能 关闭文件，释放资源
 ***********************************************/
function WPSLinuxOffice_Attachment_UnLoad() {

}

/***********************************************
 功能 提交保存文件
 ***********************************************/
function WPSLinuxOffice_Attachment_Submit() {
    if(!this.isSubmitByWps){
        return true;
    }
    var flag = false;
    var result;
    if(!this.isCurrent){
        $.ajax({
            type:"post",
            url:Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsByTmp",
            data:{editonlineFdId:this.fdId,editonlineTmpFdId:this.tmpFileAttmainId},
            async:false,    //用同步方式
            success:function(data){
                if (data){
                    var results =  eval("("+data+")");
                    if(results['flag']){
                        flag = true;
                    }
                }
            }
        });
        return flag;
    }

    if(isWordInWps(this.fdFileName)) {
        //result=this.wpsObj.saveURL_s(this.updateRelationUrl, 'default.docx');
        if(this.wpsObj){
            result=this.wpsObj.SaveDocumentToServer(this.updateRelationUrl, '{"fileName" : "default.docx"}');
        }

    }
    if(isExcelInWps(this.fdFileName)){
        result=this.wpsObj.saveURL_FormData(this.updateRelationUrl, 'default.xls');
    }

    if(isPptInWps(this.fdFileName)){
        result=this.wpsObj.saveURL(this.updateRelationUrl, 'default.ppt');
    }

    console.log("file save:"+result);
    if(isJSON(result)&&isWordInWps(this.fdFileName)){
        var data =  eval("("+result+")");
        if(data.result)
            flag = true;
        else
            console.log("file save error:"+data);
    }else if(result&&isExcelInWps(this.fdFileName)){
        flag = true;
    }else if(result&&isPptInWps(this.fdFileName)){
        flag = true;
    }else{
        console.log("file save error:"+result);
    }

    return flag;

}

/***********************************************
 功能 接受所有修订
 ***********************************************/
function WPSLinuxOffice_Attachment_accent() {
    var _self = this;
    if(_self.wpsObj)
        _self.wpsObj.ActiveDocument.AcceptAllRevisions();
}

/***********************************************
 功能 拒绝所有修订
 ***********************************************/
function WPSLinuxOffice_Attachment_reject() {
    var _self = this;
    if(_self.wpsObj)
        _self.wpsObj.ActiveDocument.RejectAllRevisions();
}
/***********************************************
 功能 操作修订
 true:打开修订
 false:关闭修订
 ***********************************************/
function WPSLinuxOffice_Attachment_handleRevision(isRevision) {
    var _self = this;
    if(_self.wpsObj)
        _self.wpsObj.enableRevision(isRevision);
}
/***********************************************
 功能 显示修订
 0:显示标记的原始修订
 2:显示最终修订
 ***********************************************/
function WPSLinuxOffice_Attachment_showRevision(isRevision) {
    var _self = this;
    if(_self.wpsObj)
        _self.wpsObj.showRevision(isRevision);
}

/***********************************************
 功能 保存临时文件
 editonline:正文或者模板
 editonlinePrint:打印
 ***********************************************/
function WPSLinuxOffice_Attachment_setTmpFileByAttKey() {
    var _self = this;
    var result;
    var uuid=guid();

    var tmpAttmainId=_self.tmpFileAttmainId;
    if(tmpAttmainId){
        uuid=tmpAttmainId;
    }

    if(isWordInWps(this.fdFileName)) {
        //result=this.wpsObj.saveURL_s(this.updateRelationUrl, 'default.docx');
        if(this.wpsObj){
            result=this.wpsObj.SaveDocumentToServer(this.updateTmpRelationUrl+"&uuId="+uuid, '{"fileName" : "default.docx"}');
        }

        result=this.wpsObj.SaveDocumentToServer(this.updateTmpRelationUrl+"&uuId="+uuid, '{"fileName" : "default.docx"}');
    }

    console.log("file save:"+result);
    if(isJSON(result)&&isWordInWps(this.fdFileName)){
        var data =  eval("("+result+")");
        if(data.result){
            _self.tmpFileAttmainId=uuid;
        }else
            console.log("file save error:"+data);
    }else{
        console.log("file save error:"+result);
    }

}

/***********************************************
 功能 获取临时文件
 editonline:正文，
 editonlinePrint:打印
 ***********************************************/
// function WPSLinuxOffice_Attachment_getTmpFileByAttKey(attKey) {
//     var _self = this;
//     for(var key in _self.tmpFileJson){
//         if(attKey==key){
//             return _self.tmpFileJson[key];
//         }
//     }
// }

function isJSON(str) {
    if (typeof str == 'string') {
        try {
            JSON.parse(str);
            return true;
        } catch(e) {
            console.log(e);
            return false;
        }
    }
    return false;
}

/**
 * @desc 判断当前系统类型
 * @returns { bool } true代表windows系统，false代表linux系统
 */
function isWindowsPlatform() {
    var platform;

    platform = navigator.platform;
    if (platform == 'Win32' || platform == "Windows") {
        return true;
    } else {
        return false;
    }
}

/**
 * @desc 获取浏览器信息
 * @returns { object } 浏览器信息对象
 */
function getBrowserInfo() {
    var browserInfo = {},
        ua, s;

    ua = navigator.userAgent.toLowerCase();
    (s = ua.match(/msie ([\d.]+)/)) ? browserInfo.ie = s[1]:
        (s = ua.match(/firefox\/([\d.]+)/)) ? browserInfo.firefox = s[1] :
            (s = ua.match(/chrome\/([\d.]+)/)) ? browserInfo.chrome = s[1] :
                (s = ua.match(/opera.([\d.]+)/)) ? browserInfo.opera = s[1] :
                    (s = ua.match(/version\/([\d.]+).*safari/)) ? browserInfo.safari = s[1] : 0;

    return browserInfo;
}

/**
 * @desc 检查当前环境是否满足wps插件运行条件
 */
function checkEnvironment() {
    var haveWPSPlugin = false;

    var browserInfo = getBrowserInfo();
    if (isWindowsPlatform()) {
        try {
            var wpsPlugin = new ActiveXObject('WPSDocFrame.Frame');
            haveWPSPlugin = true;
        } catch (error) {
            haveWPSPlugin = false;
        }
    } else {
        var plugins = navigator.plugins;
        for (var i = 0; i < plugins.length; ++i) {
            if (plugins[i].name.indexOf('Kingsoft WPS Plugin') > -1) {
                haveWPSPlugin = true;
            }
        }
    }

    if (!haveWPSPlugin) {
        if (undefined != browserInfo.chrome && ua.indexOf('qihu') > -1) {
            alert('当前Chrome内核浏览器不支持NPAPI协议，请更换其他支持该协议的浏览器！');
        } else if (undefined != browserInfo.ie && parseInt(browserInfo.ie) < 8) {
            alert('需要安装IE8或者IE8以上版本浏览器，低版本浏览器不支持ActiveX插件！');
        } else if (undefined != browserInfo.firefox && parseInt(browserInfo.firefox) > 52) {
            alert('当前FireFox版本不支持NPAPI协议，请更换52及以下版本再次尝试！');
        } else {
            alert('当前系统未安装WPS专业版，请安装之后重启浏览器再次尝试！');
        }
    }

    return haveWPSPlugin;
}

function isWordInWps(fdFileName){
    var isword=false;
    var fileExt = fdFileName.substring(fdFileName.lastIndexOf("."));
    if(fileExt.toLowerCase()==".docx"||fileExt.toLowerCase()==".doc"||fileExt.toLowerCase()==".wps"){
        isword=true;
    }
    return isword;
}

function isExcelInWps(fdFileName){
    var isExcel=false;
    var fileExt = fdFileName.substring(fdFileName.lastIndexOf("."));
    if(fileExt.toLowerCase()==".xlsx"||fileExt.toLowerCase()==".xls"||fileExt.toLowerCase()==".et"){
        isExcel=true;
    }
    return isExcel;
}

function isPptInWps(fdFileName){
    var isPpt=false;
    var fileExt = fdFileName.substring(fdFileName.lastIndexOf("."));
    if(fileExt.toLowerCase()==".pptx"||fileExt.toLowerCase()==".ppt"||fileExt.toLowerCase()==".dps"){
        isPpt=true;
    }
    return isPpt;
}

function OnDocumentBeforeCloseCallBack(){
    wpsLinuxPluginObj.ActiveDocument.Saved=true;
}
function DocumentBeforeSaveCallBack(){
    wps.ApiEvent.Cancel = true;
}
function S4() {
    return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
    return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4());
}