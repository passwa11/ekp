<%@ page language="java" contentType="application/x-javascript; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	String loadMessage = (String)request.getAttribute("_loadMessageInfo");
	if(loadMessage==null) {
%>
 
 if(typeof Attachment_MessageInfo == "undefined")
	Attachment_MessageInfo = [];
 if(Attachment_MessageInfo.length==0) {
 	Attachment_MessageInfo["sysAttMain.table"]="<bean:message bundle="sys-attachment" key="table.sysAttMain" source="js" />";
 	
 	Attachment_MessageInfo["sysAttMain.fdFileName"]="<bean:message bundle="sys-attachment" key="sysAttMain.fdFileName" source="js" />";
 	Attachment_MessageInfo["sysAttMain.fdSize"]="<bean:message bundle="sys-attachment" key="sysAttMain.fdSize" source="js" />";
 	Attachment_MessageInfo["opt.return"]="<bean:message bundle="sys-attachment" key="sysAttMain.opt.return" source="js" />";
	Attachment_MessageInfo["button.saveDraft"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.saveDraft"  source="js" />";
	Attachment_MessageInfo["button.selectAll"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.selectAll" source="js" />";
	Attachment_MessageInfo["button.fullsize"]="<bean:message bundle="sys-attachment" key="JG.tools.fullsize" source="js" />";
	

	Attachment_MessageInfo["button.upload"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.upload" source="js" />";
	Attachment_MessageInfo["button.img.upload"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.img.upload" source="js" />";
	Attachment_MessageInfo["button.textinfo"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.textinfo" source="js" />";
	Attachment_MessageInfo["button.cancelAll"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.cancelAll" source="js" />";
	Attachment_MessageInfo["button.batchdown"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.batchdown" source="js" />";
	Attachment_MessageInfo["button.downall"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.downall" source="js" />";
	Attachment_MessageInfo["button.filesize"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.filesize" source="js" />";
	Attachment_MessageInfo["button.downtimes"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.downtimes" source="js" />";
	Attachment_MessageInfo["button.cancelupload"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.cancelupload" source="js" />";
	Attachment_MessageInfo["button.play"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.play" source="js" />";
	Attachment_MessageInfo["button.confimdelte"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.confimdelte" source="js" />";
	Attachment_MessageInfo["button.progress"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.progress" source="js" />";
	Attachment_MessageInfo["show.downloadCount"]="<bean:message bundle="sys-attachment" key="sysAttMain.show.downloadCount" source="js" />";
	Attachment_MessageInfo["show.downloadCountTail"]="<bean:message bundle="sys-attachment" key="sysAttMain.show.downloadCountTail" source="js" />";
	
	Attachment_MessageInfo["button.signature"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.signature" source="js" />";
  	Attachment_MessageInfo["button.create"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.create" source="js" />";
  	Attachment_MessageInfo["button.delete"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.delete"  source="js" />";
  	Attachment_MessageInfo["button.download"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.download"  source="js" />";
  	Attachment_MessageInfo["button.read"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.read"  source="js" />";
  	Attachment_MessageInfo["button.open"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.open"  source="js" />";
  	Attachment_MessageInfo["button.edit"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.edit"  source="js" />";
  	Attachment_MessageInfo["button.bookmark"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.bookmark"  source="js" />"; 
  	Attachment_MessageInfo["button.page"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.page"  source="js" />"; 
  	Attachment_MessageInfo["button.print"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.print"  source="js" />"; 
  	Attachment_MessageInfo["button.printPreview"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.printPreview"  source="js" />"; 
  	Attachment_MessageInfo["button.exitPrintPreview"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.exitPrintPreview"  source="js" />"; 
  	Attachment_MessageInfo["button.activate"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.activate"  source="js" />"; 
  	Attachment_MessageInfo["button.hideRevisions"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.hideRevisions"  source="js" />";   	  	  	 	  	  	  	
  	Attachment_MessageInfo["button.showRevisions"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.showRevisions"  source="js" />";
	Attachment_MessageInfo["button.office"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.office"  source="js" />";
	Attachment_MessageInfo["button.handWrite"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.handWrite"  source="js" />";
  	Attachment_MessageInfo["button.acceptRevisions"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.acceptRevisions"  source="js" />";   	  	  	 	  	  	  	
  	Attachment_MessageInfo["button.refuseRevisions"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.refuseRevisions"  source="js" />";
  	Attachment_MessageInfo["button.openLocal"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.openLocal"  source="js" />";
  	Attachment_MessageInfo["button.bigAttBtn"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.bigAttBtn"  source="js" />";
  	Attachment_MessageInfo["msg.downloadSucess"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.downloadSucess"  source="js" />";
  	Attachment_MessageInfo["msg.noChoice"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.noChoice"  source="js" />";   	
  	Attachment_MessageInfo["msg.deleteNoChoice"]="<kmss:message bundle="sys-attachment" key="sysAttMain.msg.deleteNoChoice" source="js" />"; 
  	Attachment_MessageInfo["msg.uploading"]="<kmss:message bundle="sys-attachment" key="sysAttMain.msg.uploading" source="js" />";
  	Attachment_MessageInfo["msg.uploadSucess"]="<kmss:message bundle="sys-attachment" key="sysAttMain.msg.uploadSucess" source="js" />";
  	Attachment_MessageInfo["msg.uploadFail"]="<kmss:message bundle="sys-attachment" key="sysAttMain.msg.uploadFail" source="js" />";
  	Attachment_MessageInfo["error.exceedMaxSize"]="<bean:message bundle="sys-attachment" key="sysAttMain.error.exceedMaxSize" source="js" />"; 
  	Attachment_MessageInfo["error.smallMaxSize"]="<bean:message bundle="sys-attachment" key="sysAttMain.error.smallMaxSize" source="js" />"; 
  	Attachment_MessageInfo["error.exceedSingleMaxSize"]="<kmss:message bundle="sys-attachment" key="sysAttMain.error.exceedSingleMaxSize" source="js" />";   	
  	Attachment_MessageInfo["error.enabledFileType"]="<kmss:message bundle="sys-attachment" key="sysAttMain.error.enabledFileType" source="js" />";
	Attachment_MessageInfo["error.zeroError"]="<kmss:message bundle="sys-attachment" key="sysAttMain.error.zeroError" source="js" />";
	Attachment_MessageInfo["error.other"]="<kmss:message bundle="sys-attachment" key="sysAttMain.error.other" source="js" />";
	Attachment_MessageInfo["error.jgsupport"]="<kmss:message bundle="sys-attachment" key="sysAttMain.error.jgsupport" source="js" />";
  	Attachment_MessageInfo["info.JG.lang"]="<kmss:message bundle="sys-attachment" key="JG.lang" source="js" />";
  	Attachment_MessageInfo["start.video"]="<kmss:message bundle="sys-attachment" key="start.video" source="js" />";
  	Attachment_MessageInfo["error.exceedImageMaxSize"]="<kmss:message bundle="sys-attachment" key="sysAttMain.error.exceedImageMaxSize" source="js" />";
  	Attachment_MessageInfo["msg.null"]="<kmss:message bundle="sys-attachment" key="sysAttMain.msg.null" source="js" />";
  	Attachment_MessageInfo["msg.singleNull"]="<kmss:message bundle="sys-attachment" key="sysAttMain.msg.singleNull" source="js" />";
  	Attachment_MessageInfo["msg.single"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.single"  source="js" />";
  	Attachment_MessageInfo["msg.disableFileType"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.disableFileType"  source="js" />";
   	Attachment_MessageInfo["msg.disableFileType2"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.disableFileType2"  source="js" />";
	 Attachment_MessageInfo["msg.fileNameMax"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.fileNameMax"  source="js" />";
  	Attachment_MessageInfo["msg.single.confirm"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.single.confirm"  source="js" />";
  	Attachment_MessageInfo["msg.fail.fileName"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.fail.fileName"  source="js" />";
  	Attachment_MessageInfo["error.exceedNumber"]="<bean:message bundle="sys-attachment" key="sysAttMain.error.exceedNumber" source="js" />"; 
  	Attachment_MessageInfo["error.fileType"]="<bean:message bundle="sys-attachment" key="sysAttMain.error.onlySupportFileTypes" source="js" />";
  	Attachment_MessageInfo["msg.fileName.confirm"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.fileName.confirm" source="js" />"; 
  	Attachment_MessageInfo["msg.fileExist.confirm"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.fileExist.confirm" source="js" />"; 
  	Attachment_MessageInfo["msg.fileSize.null"]="<bean:message bundle="sys-attachment" key="sysAttMain.error.null" source="js" />";
	Attachment_MessageInfo["msg.fileName.error"]="<bean:message bundle="sys-attachment" key="sysAttMain.illegal.fileName" source="js" />";
  	Attachment_MessageInfo["button.moveup"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.moveup" source="js" />";
  	Attachment_MessageInfo["button.movedown"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.movedown" source="js" />";
  	Attachment_MessageInfo["button.rename"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.rename" source="js" />";
  	Attachment_MessageInfo["button.md5"]="<bean:message bundle="sys-attachment" key="sysAttMain.md5.caculate" source="js" />";
  	Attachment_MessageInfo["button.confirmCancel"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.confirmCancel" source="js" />";  

 	Attachment_MessageInfo["layout.upload"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload" source="js" />";
 	Attachment_MessageInfo["layout.upload.note1"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note1" source="js" />";
 	Attachment_MessageInfo["layout.upload.note2"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note2" source="js" />";
 	Attachment_MessageInfo["layout.upload.note3"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note3" source="js" />";
 	Attachment_MessageInfo["layout.upload.note4"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note4" source="js" />";
 	Attachment_MessageInfo["layout.upload.note5"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note5" source="js" />";
 	Attachment_MessageInfo["layout.upload.note6"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note6" source="js" />";
 	Attachment_MessageInfo["layout.upload.note7"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note7" source="js" />";
 	Attachment_MessageInfo["layout.upload.note8"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note8" source="js" />";
 	Attachment_MessageInfo["layout.upload.note9"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note9" source="js" />";
 	Attachment_MessageInfo["layout.upload.note10"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note10" source="js" />";
 	Attachment_MessageInfo["layout.upload.note11"]="<bean:message bundle="sys-attachment" key="attachment.layout.upload.note11" source="js" />";
 	Attachment_MessageInfo["button.reupload"]="<bean:message bundle="sys-attachment" key="attachment.button.reupload" source="js" />";
 	Attachment_MessageInfo["button.replace"]="<bean:message bundle="sys-attachment" key="sysAttMain.button.replace" source="js" />";
 	
 	Attachment_MessageInfo["sysAttMain.material.upload.error"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.upload.error" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.limit1"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.limit1" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.limit2"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.limit2" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.select.pic"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.select.pic" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.click.pic.upload"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.click.pic.upload" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.pic.type.limit"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.pic.type.limit" source="js" />";
 	Attachment_MessageInfo["sysAttMain.preview"]="<bean:message bundle="sys-attachment" key="sysAttMain.preview" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.edit.pic"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.edit.pic" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.edit.icon"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.edit.icon" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.illegal.name"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.illegal.name" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.operate.success"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.operate.success" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.operate.fail"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.operate.fail" source="js" />";
 	Attachment_MessageInfo["sysAttMain.material.write.title"]="<bean:message bundle="sys-attachment" key="sysAttMain.material.write.title" source="js" />";
  	Attachment_MessageInfo["sysAttMain.view.history"]="<bean:message bundle="sys-attachment" key="sysAttMain.view.history" source="js" />";
  	Attachment_MessageInfo["sysAttMain.msg.recognitioning"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.recognitioning" source="js" />";
  	Attachment_MessageInfo["sysAttMain.msg.ocrnotnull"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.ocrnotnull" source="js" />";
  	Attachment_MessageInfo["sysAttMain.msg.plaseUpload"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.plaseUpload" source="js" />";
  	Attachment_MessageInfo["sysAttachment.ocr.reIdentify"]="<bean:message bundle="sys-attachment" key="sysAttachment.ocr.reIdentify" source="js" />";
  	Attachment_MessageInfo["sysAttachment.ocr.uploadPicturesOnly"]="<bean:message bundle="sys-attachment" key="sysAttachment.ocr.uploadPicturesOnly" source="js" />";
  	Attachment_MessageInfo["sysAttachment.ocr.recognitioning"]="<bean:message bundle="sys-attachment" key="sysAttachment.ocr.recognitioning" source="js" />";

  	Attachment_MessageInfo["sysAttMain.msg.slideUp"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.slideUp" source="js" />";
  	Attachment_MessageInfo["sysAttMain.msg.slideDown"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.slideDown" source="js" />";
  	Attachment_MessageInfo["sysAttMain.msg.total"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.total" source="js" />";
  	Attachment_MessageInfo["sysAttMain.msg.attachments"]="<bean:message bundle="sys-attachment" key="sysAttMain.msg.attachments" source="js" />";

 }
 
 <%
		request.setAttribute("_loadMessageInfo","1");
	}
 %>
