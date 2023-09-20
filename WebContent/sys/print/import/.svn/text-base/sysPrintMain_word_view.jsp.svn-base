<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil,com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<%
	pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
	pageContext.setAttribute("_isWpCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));
	pageContext.setAttribute("_isWpsoaassistEmbed", new Boolean(SysAttWpsoaassistUtil.isWPSOAassistEmbed(request)));
%>
<c:set var="isWrite" value="read" />
<c:if test="${sysPrintTemplateModel.fdPrintEdit=='true'}">
	<c:set var="isWrite" value="write" />
</c:if>
<style type="text/css">
	.attshowRevisions, .attbook {
		display: none;
	}
</style>
<%--打印按钮--%>
<div id="btnDiv" style="text-align: right; padding: 0px 10px 20px 0px;">
	<c:choose>
		<c:when test="${pageScope._isWpsCloudEnable == 'true' || pageScope._isWpCenterEnable == 'true' || pageScope._isWpsoaassistEmbed == 'true'}">
			<c:if test="${sysPrintTemplateModel.fdPrintEdit=='true' && pageScope._isWpsCloudEnable == 'true'}">
				<input class="lui_form_button" type="button"
					   value="${ lfn:message('sys-print:button.print.rmversions') }"
					   onclick="wpsclearRevisions();">
			</c:if>
			<c:if test="${sysPrintTemplateModel.fdPrintEdit=='true' && pageScope._isWpCenterEnable == 'true'}">
				<input class="lui_form_button" type="button"
					   value="${ lfn:message('sys-print:button.print.rmversions') }"
					   onclick="wpsCenterClearRevisions();">
			</c:if>
			<c:if test="${sysPrintTemplateModel.fdPrintEdit=='true' && pageScope._isWpsoaassistEmbed == 'true'}">
				<input class="lui_form_button" type="button"
					   value="${ lfn:message('sys-print:button.print.rmversions') }"
					   onclick="wpsoaassistEmbedClearRevisions();">
			</c:if>
			<c:if test="${isShowSwitchBtn=='true'}">
				<input class="lui_form_button" type="button"
					   value="${lfn:message('sys-print:sysPrintPage.switchBtn.old')}"
					   onclick="switchPrintPage();">
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${sysPrintTemplateModel.fdPrintEdit=='true'}">
				<input class="lui_form_button" type="button"
					   value="${ lfn:message('sys-print:button.print.rmversions') }"
					   onclick="Attachment_ObjectInfo['sysprint_editonline'].ocxObj.ClearRevisions();">
			</c:if>
			<input class="lui_form_button" type="button"
				   value="${ lfn:message('button.print') }"
				   onclick="Attachment_ObjectInfo['sysprint_editonline'].ocxObj.WebOpenPrint();">
			<c:if test="${isShowSwitchBtn=='true'}">
				<input class="lui_form_button" type="button"
					   value="${lfn:message('sys-print:sysPrintPage.switchBtn.old')}"
					   onclick="switchPrintPage();">
			</c:if>
		</c:otherwise>
	</c:choose>

	<input class="lui_form_button" type="button"
		   value="${lfn:message('button.close')}" onclick="Com_CloseWindow();">
</div>
<div id="wordEdit" style="height: 600px;">
	<c:choose>
		<c:when test="${pageScope._isWpsCloudEnable == 'true'}">
			<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit_wordprint.jsp" charEncoding="UTF-8">
				<c:param name="fdKey" value="sysprint_editonline" />
				<c:param name="fdAttType" value="office"/>
				<c:param name="load" value="true" />
				<c:param name="bindSubmit" value="false"/>
				<c:param name="fdModelId" value="${fdTemplateId}" />
				<c:param name="fdModelName" value="${HtmlParam.modelName}" />
				<c:param name="formBeanName" value="${HtmlParam.formName}" />
				<c:param name="fdTemplateModelId" value="${fdTemplateId}" />
				<c:param name="fdTemplateModelName" value="${HtmlParam.modelName}" />
				<c:param name="buttonDiv" value="printButtonDiv" />
				<c:param name="isTemplate" value="true"/>
				<c:param name="attHeight" value="550" />
				<c:param name="fdMode" value="${isWrite}" />
			</c:import>
		</c:when>
		<c:when test="${pageScope._isWpCenterEnable == 'true'}">
			<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit_wordprint.jsp" charEncoding="UTF-8">
				<c:param name="fdKey" value="sysprint_editonline" />
				<c:param name="fdAttType" value="office"/>
				<c:param name="load" value="true" />
				<c:param name="bindSubmit" value="false"/>
				<c:param name="fdModelId" value="${fdTemplateId}" />
				<c:param name="fdModelName" value="${HtmlParam.modelName}" />
				<c:param name="formBeanName" value="${HtmlParam.formName}" />
				<c:param name="fdTemplateModelId" value="${fdTemplateId}" />
				<c:param name="fdTemplateModelName" value="${HtmlParam.modelName}" />
				<c:param name="buttonDiv" value="printButtonDiv" />
				<c:param name="isTemplate" value="true"/>
				<c:param name="attHeight" value="550" />
				<c:param name="fdMode" value="${isWrite}" />
			</c:import>
		</c:when>
		<c:when test="${pageScope._isWpsoaassistEmbed == 'true'}">
			<c:import url="/sys/attachment/sys_att_main/wps/oaassist/linux/sysAttMain_edit_wordprint.jsp" charEncoding="UTF-8">
				<c:param name="fdKey" value="sysprint_editonline" />
				<c:param name="fdAttType" value="office"/>
				<c:param name="load" value="true" />
				<c:param name="bindSubmit" value="false"/>
				<c:param name="fdModelId" value="${fdTemplateId}" />
				<c:param name="fdModelName" value="${HtmlParam.modelName}" />
				<c:param name="formBeanName" value="${HtmlParam.formName}" />
				<c:param name="fdTemplateModelId" value="${fdTemplateId}" />
				<c:param name="fdTemplateModelName" value="${HtmlParam.modelName}" />
				<c:param name="buttonDiv" value="printButtonDiv" />
				<c:param name="fdMode" value="${isWrite}" />
			</c:import>
		</c:when>
		<c:otherwise>
			<div id="printButtonDiv" style="text-align: right; padding-bottom: 5px">&nbsp;</div>
			<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp"
					  charEncoding="UTF-8">
				<c:param name="fdKey" value="sysprint_editonline" />
				<c:param name="fdAttType" value="office" />
				<c:param name="fdModelId" value="${fdTemplateId}" />
				<c:param name="fdModelName" value="${HtmlParam.modelName }" />
				<c:param name="formBeanName" value="${HtmlParam.formName }" />
				<c:param name="buttonDiv" value="printButtonDiv" />
				<c:param name="showRevisions" value="true" />
				<c:param name="forceRevisions" value="true" />
				<c:param name="load" value="${not empty HtmlParam.load?HtmlParam.load:false }"></c:param>
				<c:param name="clearDownloadRevisions" value="true"></c:param>
				<c:param name="editMode" value="print" />
			</c:import>
		</c:otherwise>
	</c:choose>
</div>
<script type="text/javascript">
	var resultJson = ${resultJson};
	function init(){
		var h = $(document).height();
		$('#wordEdit').css('height',h);
	}

	function PrintSet() {
		/**
		 * 集合元素的容器，以对象来表示
		 * @type {Object}
		 */
		var items = {};/**
		          * 检测集合内是否有某个元素
		          * @param {Any} value  要检测的元素
		          * @return {Boolean}    如果有，返回true
		          */
		this.has = function (value) {
			// hasOwnProperty的问题在于
			// 它是一个方法，所以可能会被覆写
			return items.hasOwnProperty(value)
		};
		/**
		          * 给集合内添加某个元素
		          * @param {Any} value 要被添加的元素
		          * @return {Boolean}    添加成功返回True。
		          */
		this.add = function (value) {
			//先检测元素是否存在。
			if (!this.has(value)) {
				items[value] = value;
				return true;
			}
			//如果元素已存在则返回false
			return false;
		};
		/**
		          * 移除集合中某个元素
		          * @param {Any} value 要移除的元素
		          * @return {Boolean}    移除成功返回True。
		          */
		this.remove = function (value) {
			//先检测元素是否存在。
			if (this.has(value)) {
				delete items[value];
				return true;
			}
			//如果元素不存在，则删除失败返回false
			return false;
		};
		/**
		          * 清空集合
		          */
		this.clear = function () {
			this.items = {};
		};

		/**
		 * 返回集合长度，可用于所有浏览器
		 * @return {Number} 集合长度
		 */
		this.sizeLegacy = function () {
			var count = 0;
			for (var prop in items) {
				if (items.hasOwnProperty(prop)) {
					++count;
				}
			}
			return count;
		}

		/**
		 * 返回集合转换的数组，可用于所有浏览器
		 * @return {Array} 转换后的数组
		 */
		this.valuesLegacy = function () {
			var keys = [];
			for (var key in items) {
				keys.push(key)
			};
			return keys;
		};


	}

	function getBookmarNames(bookmark){
		var names = [];
		if(bookmark && bookmark.Count>0){
			for(var i = 1 ;i <= bookmark.Count;i++){
				var item = bookmark.Item(i);
				names.push(item.name);
			}
		}
		return names;
	}

	function loadWordData(){
		var office = Attachment_ObjectInfo['sysprint_editonline'];
		var _mydoc = office.ocxObj.WebObject; //得到Document对象
		var _app = _mydoc.Application; //得到Application对象
		var _sel = _app.Selection; //得到Selection对象
		var _bookmarks = _app.ActiveDocument.Bookmarks;
		var bookmarkNames = getBookmarNames(_bookmarks);

		$.each(resultJson, function(name) {
			debugger;
			if($.isArray(this)&& this.length>0){
				var items=new PrintSet();
				for(var r in this){
					keys=Object.keys(this[r]);
					for(var i in keys){
						items.add(keys[i]);
					}
				}
				var names =items.valuesLegacy();
				var bookmarkObj = null;
				var info =[];
				var cell = null;
				for(var x in names){
					try{
						var name=names[x];
						var tempBookmarkObj = _app.ActiveDocument.Bookmarks.Item(name);
						if(!tempBookmarkObj){
							continue;
						}else{
							bookmarkObj = tempBookmarkObj;
						}
						bookmarkObj.Select();
						cell = bookmarkObj.Range.Cells.Item(1);
					}catch(e){
					}
					//明细表数据必须在表格中
					if(!cell){
						bookmarkObj = null;
						continue;
					}
					var r = {};
					r.name=bookmarkObj.Name;
					r.colIndex=cell.ColumnIndex;
					info.push(r);
				}
				if(!bookmarkObj){
					return;
				}
				bookmarkObj.Select();
				_sel.SelectRow();
				var currentRow = _sel.Rows.Item(1);
				var currentRowIdx = currentRow.Index;
				var table = _sel.Tables.Item(1);
				//明细表序号
				for(var i = 0 ;i < this.length;i++){
					if(i!=0){
						//添加新行
						_sel.InsertRowsBelow(1);
					}
					//是否添加序号
					var numCell = table.Rows.Item(i+currentRowIdx).Cells.Item(1);
					if(isNumCell(info,numCell.ColumnIndex)){
						numCell.Range.text=i+1;
					}
				}
				//明细表数据
				for(var i = 0 ;i < this.length;i++){
					var _record = this[i];
					for(var index in _record){
						var _cell = getColInfo(info,index);
						if(_cell){
							var range = table.Rows.Item(i+currentRowIdx).Cells.Item(_cell.colIndex).Range;
							var value = range.text.replace("\r\u0007","");
							var newValue = _record[index];
							range.Text=$.trim(value)+newValue;
						}
					}
				}
			}else{
				//非明细表书签
				for(var i = 0;i < bookmarkNames.length;i++){
					var _bookmarkName = bookmarkNames[i];
					var _qrCodeName = name+"_qrCode";

					if(name=='docQRCode' && (_bookmarkName==name || _bookmarkName.indexOf('docQRCode_')>-1)){
						//文档二维码
						office.setBookmark(_bookmarkName,'');
						var bookmarkObj = _app.ActiveDocument.Bookmarks.Item(_bookmarkName);
						bookmarkObj.Range.InlineShapes.AddPicture(this.toString());//匿名
					}else if(_bookmarkName ==_qrCodeName && (_bookmarkName ==_qrCodeName || _bookmarkName.indexOf(_qrCodeName)>-1)){
						//表单二维码
						office.setBookmark(_bookmarkName,'');
						var bookmarkObj = _app.ActiveDocument.Bookmarks.Item(_bookmarkName);
						bookmarkObj.Range.InlineShapes.AddPicture(this.toString());
					}else if(_bookmarkName==name || _bookmarkName.indexOf(name + '_')>-1){
						if(_bookmarks.Exists(_bookmarkName)){
							office.setBookmark(_bookmarkName, this.replace(/\\\\n/g,"\r\n"));
						}
					}

				}

			}

		});

		//审批意见
		addAuditShowInfo(_bookmarks,resultJson.fdId,resultJson.extendFilePath);

		<c:if test="${sysPrintTemplateModel.fdPrintEdit!='true'}">
		setTimeout(function(){
			office.ocxObj.EditType="4,0";
			office.ocxObj.CopyType = "1";
		}, 1);
		</c:if>

	}

	function addAuditShowInfo(bookmarks,fdModelId,extendFilePath){
		var auditShowArr = [];
		var url = getHost() + Com_Parameter.ContextPath + "sys/print/word/file";
		for(var i = 1 ;i <= bookmarks.Count;i++){
			var bookmarkRecord = {};
			var bookmarkObj = bookmarks.Item(i);
			var markName = bookmarkObj.Name;
			if(markName.indexOf('_auditShow') > -1){
				bookmarkRecord.name = markName;
				bookmarkRecord.bookmarkObj = bookmarkObj;
				auditShowArr.push(bookmarkRecord);
			}
		}
		if(auditShowArr.length>0){
			for(var idx in auditShowArr){
				var name = auditShowArr[idx].name;
				var fdId = name.substring(0,name.indexOf('_auditShow'));
				var queryStr = "/" + fdId + "/" + fdModelId + "/" + extendFilePath;
				var bookmarkObj = auditShowArr[idx].bookmarkObj;
				bookmarkObj.Range.insertFile(url + queryStr);
			}
		}
	}


	function getColInfo(arr,name){
		for(var i = 0 ; i < arr.length;i++){
			if(arr[i].name==name){
				return arr[i];
			}
		}
		return null;
	}
	function isNumCell(arr,colIndex){
		for(var i = 0 ; i < arr.length;i++){
			if(arr[i].colIndex==colIndex){
				return false;
			}
		}
		return true;
	}
	function getHost() {
		var host = location.protocol.toLowerCase() + "//" + location.hostname;
		if (location.port != '' && location.port != '80') {
			host = host + ":" + location.port;
		}
		return host;
	}

	Com_AddEventListener(window, 'load', function() {
		var _iswpsload="${_isWpsCloudEnable}";
		var _isWpsCenterLoad="${_isWpCenterEnable}";
		var _isWpsoaassistEmbed="${_isWpsoaassistEmbed}";
		if(_iswpsload=="true" || _isWpsCenterLoad=="true" || _isWpsoaassistEmbed =="true"){
			return;
		}

		init();
		setTimeout(function(){
			if(Attachment_ObjectInfo['sysprint_editonline']){
				jg_attachmentObject_sysprint_editonline.load();
				jg_attachmentObject_sysprint_editonline.show();
				jg_attachmentObject_sysprint_editonline.ocxObj.Active(true);
				loadWordData();
			}
		},1000);
	});
</script>