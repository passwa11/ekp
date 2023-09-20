/*******************************************************************************
 * JS文件说明： 该文件提供了金格在线阅读PDF的操作函数
 ******************************************************************************/
Com_RegisterFile("jg_pdf2018Sinature.js");
Com_IncludeFile("common.js");
Com_IncludeFile("sysAttMain_MessageInfo.jsp?locale="+ Com_Parameter.__sysAttMainlocale__,Com_Parameter.ContextPath + "sys/attachment/sys_att_main/", 'js',true);
Com_IncludeFile("sysAttMain_jg_version.jsp?locale="+ Com_Parameter.__sysAttMainlocale__,Com_Parameter.ContextPath + "sys/attachment/sys_att_main/jg/", 'js',true);
(function(window) {
	function JG_PDF() {
		// 公用函数
		this.create = JG_PDF_Create;
		this.setOtherParamter = JG_SetOtherParamter;
		this.pdfUnLoad = JG_UnLoad;
		this.pdfLoad = JG_Load;
		this.statusMsg = JG_StatusMsg;
		this.pdfObject = null;
		this.fullSize = JG_FullSize;
		this.pdfSave = JG_Save;
		this.jgPdfSave = JGPDF_Save;
		this.canPrint = false;
		this.canCopy = false;
		this._toolsEnable = JG_ToolsEnable;
		this._showBtn = JG_ShowButton;
	}
	/********************************************
	 功能：获取当前DNS
	 ********************************************/
	function Com_GetCurDns(){
		var host = location.protocol.toLowerCase()+"//" + location.hostname;
		if(location.port!='' && location.port!='80'){
			host = host+ ":" + location.port;
		}
		return host;
	}
	/***************************************************************************
	 * 创建JG_PDF_Object
	 */
	function JG_PDF_Create(fdId, fdKey, fdModelName, fdModelId, fdAttType, divName, fileName) {
		if (divName == null) {
			divName = "JGWebPdf_" + fdKey;
		}
		this.fdModelName = fdModelName;
		this.fdId = fdId;
		this.fdKey = fdKey;
		this.fdAttType = fdAttType;
		var iWebPDF2018 = null;
		iWebPDF2018 = document.getElementById(divName);
		this.pdfObject = iWebPDF2018.iWebPDFFun;
		this.pdfObject.WebUrl = Com_GetCurDns() + Com_Parameter.ContextPath + "sys/attachment/sys_att_main/jg_service.jsp?jgBigVersion=2009";
		this.pdfObject.RecordID = fdId;
		this.pdfObject.FileName = fileName;
		//固定参数，与后端相对应，作为金格pdf签章的区分
		this.pdfObject.ExtParam = "pdf2018Signature";
		this.setOtherParamter(fdModelId, fdModelName, fdKey, fdId, fdAttType);
		this.pdfObject.Language = Attachment_MessageInfo["info.JG.lang"];// 语言
	}
	/***************************************************************************
	 * 设置其他参数
	 */
	function JG_SetOtherParamter(fdModelId, fdModelName, fdKey, fdId, fdAttType) {
		this.pdfObject.WebSetMsgByName("_fdModelName", fdModelName);
		this.pdfObject.WebSetMsgByName("_fdModelId", fdModelId);
		this.pdfObject.WebSetMsgByName("_fdKey", fdKey);
		this.pdfObject.WebSetMsgByName("_fdId", fdId);
		this.pdfObject.WebSetMsgByName("_attType", fdAttType);
	}
	/***************************************************************************
	 * 加载控件
	 */
	function JG_Load() {
		try {
			this.pdfObject.WebOpen(); // 打开该文档
			this.pdfObject.Zoom = -3; // 缩放比例
			this.pdfObject.CurPage = 1; // 当前显示的页码
			if (this.canCopy) {
				this._toolsEnable('文本工具', true);
			} else {
				this._toolsEnable('文本工具', false);
			}
			this._showBtn();
			JG_StatusMsg(this.pdfObject.Status);		
		} catch (e) {
			JG_StatusMsg(e.description); // 显示出错误信息
		}
	}
	
	/***************************************************************************
	 * 作用：保存iWebPDF
	 */
	function JG_Save(){
		var ret = this.pdfObject.WebSave();
		if (!ret) {
			this.pdfObject.Alert("文档保存失败！");
		}else{
			this.pdfUnLoad();
			window.close();
		}
	}
	
	/***************************************************************************
     * 作用：保存iWebPDF,单独给公文使用，返回一个true或者false2
     */
    function JGPDF_Save() {
        var ret = this.pdfObject.WebSave();
        if (!ret) {
            this.pdfObject.Alert("文档保存失败！");
        } else {
            this.pdfUnLoad();
            window.close();
        }
        return ret;
    }

	/***************************************************************************
	 * 作用：退出iWebPDF
	 */
	function JG_UnLoad() {
		try {
			if (!this.pdfObject.WebClose()) {
				JG_StatusMsg(this.pdfObject.Status);
			} else {
				JG_StatusMsg("关闭文档...");
			}
		} catch (e) {
			JG_StatusMsg(e.description);
		}
	}
	
	/***************************************************************************
	 * 作用：设置具体工具的有效性
	 */
	function JG_ToolsEnable(type, isEnable) {
		if (isEnable) {
			this.pdfObject.EnableTools(type, 1);
		} else {
			this.pdfObject.EnableTools(type, 2);
		}
	}
	/***************************************************************************
	 * 作用：满屏显示
	 */
	function JG_FullSize() {
		this.pdfObject.FullSize();
	}
	/***************************************************************************
	 * 作用：显示操作状态
	 */
	function JG_StatusMsg(mString) {
		window.status = mString;
	}
	/***************************************************************************
	 * 显示按钮
	 */
	function JG_ShowButton() {
		var obj = document.getElementById("optBarDiv");
		var attachmentObject = this;
		var _insertDomBefore = function(insertTo,insertObj){
			if(insertTo.firstChild!=null){
				insertTo.insertBefore(insertObj,insertTo.firstChild); 
			}else{
				insertTo.appendChild(insertObj);
			}
		};		
		
		if (this.canPrint && this.pdfObject.PrintStatus) {
			// 打印按钮
			var _printButton = document
					.createElement("input");
			_printButton.setAttribute("type","button");
			_printButton.value = Attachment_MessageInfo["button.print"];
			_printButton.name = this.fdKey + "_print";
			_printButton.onclick = function() {
				attachmentObject.pdfObject.WebPrint(-1, "", 0, 0, true);
			};
			_insertDomBefore(obj,_printButton);
		}
		if(window.jgLoadPdfSuccessCallback){
			window.jgLoadPdfSuccessCallback();
		}
		window.OptBar_Draw();
	}
	window.JG_PDFObject = new JG_PDF();
})(window);