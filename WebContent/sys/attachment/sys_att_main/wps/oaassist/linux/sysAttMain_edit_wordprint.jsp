<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<!-- 引用编辑页面，但用查看打开wps，类型：write(编辑)，read(只读) -->
<c:set var="isWrite" value="${param.fdMode}" />
<c:if test="${empty isWrite}">
	<c:set var="isWrite" value="write" />
</c:if>



<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("wps_linux_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/oaassist/linux/js/","js",true);</script>
<div style="width: 100%;height: 100%">
	<div id="wpsLinux_${param.fdKey}" style="width: 100%;height: 700px;">
	</div>
</div>

<script>
	var wpsLinux_${param.fdKey};

	$(document).ready(function(){
		var url ="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=findAttMains";
		$.ajax({
			type:"post",
			url:url,
			data:{fdModelName:"${HtmlParam.modelName}",fdKey:"${param.fdKey}",fdModelId:"${param.fdModelId}"},
			dataType:"json",
			async:false,
			success:function(data){
				var resultJson = checkValue(${resultJson});
				var ekpAttMainId=data.attMainId;

				wps_linux_${param.fdKey}  = new WPSLinuxOffice_AttachmentObject(data.attMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","${isWrite}",data.fdFileName);
				if("${param.contentFlag}" != "" && "${param.contentFlag}" == "true" && "${_docStatus}" == "30"){
					wps_linux_${param.fdKey}.contentFlag = true;
				}

				if("${param.load}" != 'false'){
					wps_linux_${param.fdKey}.load();
					setTimeout(function(){
						var doc = wps_linux_${param.fdKey}.wpsObj.ActiveDocument;
						if("read"=="${isWrite}") {
							doc.Unprotect("");
						}
						var bookMarks = doc.Bookmarks;


						var auditShowArr = [];
						var url = Com_Parameter.serverPrefix + "/sys/print/word/file";

						//书签配置审批意见
						for(var i = 1 ;i <= bookMarks.Count;i++)
						{
							var bookmarkRecord = {};
							var bookmarkObj = bookMarks.Item(i);
							var markName = bookmarkObj.Name;
							if(markName.indexOf('_auditShow') > -1)
							{
								bookmarkRecord.name = markName;
								bookmarkRecord.bookmarkObj = bookmarkObj;
								auditShowArr.push(bookmarkRecord);
							}
						}

						$.each(resultJson, function(name) {
							if (bookMarks.Exists(name)){

								try{
									var book = bookMarks.Item(name);
									if(name=='docQRCode'){
										book.Range.InlineShapes.AddPicture(this.toString());
									}else{
										book.Range.Text=this.replace(/\\\\n/g,"\r\n");
									}

								}catch(e){
									console.log(e);
								}
							}
						});

						//审批意见书签填充
						if(auditShowArr.length > 0 ){
							var printMarkContent=resultJson;
							for(var idx in auditShowArr)
							{
								var name = auditShowArr[idx].name;
								var fdId = name.substring(0,name.indexOf('_auditShow'));
								var queryUrl = url +  "/" + fdId + "/" + printMarkContent.fdId + "/" + printMarkContent.extendFilePath;
								var book = bookMarks.Item(name);
								book.Range.InsertFile(queryUrl);
								book.Range.Select();
								var count=wps_linux_${param.fdKey}.wpsObj.ActiveWindow.Selection.Cells.Item(1).Range.Paragraphs.Count;
								wps_linux_${param.fdKey}.wpsObj.ActiveDocument.Range(
										wps_linux_${param.fdKey}.wpsObj.ActiveWindow.Selection.Cells.Item(1).Range.Paragraphs.Item(count-1).
												Range.End-1,wps_linux_${param.fdKey}.wpsObj.ActiveWindow.Selection.Cells.Item(1).
										Range.Paragraphs.Item(count-1).Range.End).Text="";
							}
						}

						if("read"=="${isWrite}"){
							doc.Protect(3,"",ekpAttMainId);
						}
					},500);
				}
			}
		});



	});
	
	function wpsoaassistEmbedClearRevisions(){
		wps_linux_${param.fdKey}.accent();
	}

	function checkValue(value)
	{
		if(value == '')
		{
			return '';
		}else
		{
			return value;
		}
	}
</script>