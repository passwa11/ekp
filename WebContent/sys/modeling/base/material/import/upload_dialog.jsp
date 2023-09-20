<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService" %>
<%@ page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
<%
    ISysFileLocationDirectService directService =
            SysFileLocationUtil.getDirectService();
    request.setAttribute("methodName", directService.getMethodName());
    request.setAttribute("uploadUrl", directService.getUploadUrl(request.getHeader("User-Agent")));
    request.setAttribute("isSupportDirect", directService.isSupportDirect(request.getHeader("User-Agent")));
    request.setAttribute("fileVal", directService.getFileVal());
%>
<template:include ref="default.dialog">
    <template:replace name="head">
        <link rel="stylesheet" type="text/css"
            href="${LUI_ContextPath}/sys/modeling/base/material/source/material_main.css" />

        <script type="text/javascript">
            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("data.js|dialog.js|jquery.js");

            Com_IncludeFile("swf_attachment.js", "${LUI_ContextPath}/sys/attachment/js/", "js", true);
            Com_IncludeFile("swf_att_material.js", "${LUI_ContextPath}/sys/modeling/base/material/source/", "js", true);
            Com_IncludeFile("material_main.js", "${LUI_ContextPath}/sys/modeling/base/material/source/", 'js', true);
            
          
          
           
        </script>
    </template:replace>
    <template:replace name="content">
    		<script type="text/javascript">
                var attachmentConfig = {
                    // 上传路径
                    uploadurl: '${uploadUrl}',
                    // 上传方法名
                    methodName: '${methodName}',
                    // 是否支持直连模式
                    isSupportDirect: ${isSupportDirect},
                    // 文件key
                    fileVal: '${fileVal}'|| null,
                    //注册before-send-file事件
                    beforeSendFile:true
                }
    		  //系统配置的图片上传容量最大大小
    		  var _image_max_size = <%=ResourceUtil.getKmssConfigString("sys.att.imageMaxSize")%>?<%=ResourceUtil.getKmssConfigString("sys.att.imageMaxSize")%>:5;
    		 	//图标默认200k
    			var size = '${JsParam.type}'=='1'?_image_max_size:0.2	;
	     		var saveMaterialAsyncURL = Com_Parameter.ContextPath + "sys/modeling/base/modelingMaterialMain.do?method=saveMaterialAsync";
	            var attachmentObject_defaultUploadLi = new Swf_AttObject_Material("material_upload_li_default", "", "",size,attachmentConfig);
	            attachmentObject_defaultUploadLi.drawFrame()
            </script>
        <div class="lui_material_upload_dlg">
            <ul class="material_upload_ul" id="material_upload_li_default_renderId">
                <li id="material_upload_li_default"></li>
                <!-- temp material_upload_success -->
                <li id="formTemple" class="material_upload_li  " style="display: none">
                    <div class="material_upload_li_img"></div>
                    <input name="fdSize" class="material_input_hidden " placeholder="${ lfn:message('sys-modeling-base:modelingMaterialMain.fdName.help')}" type="text">
                    <input name="fdWidth" class="material_input_hidden " placeholder="${ lfn:message('sys-modeling-base:modelingMaterialMain.fdName.help')}" type="text">
                    <input name="fdLength" class="material_input_hidden " placeholder="${ lfn:message('sys-modeling-base:modelingMaterialMain.fdName.help')}" type="text">
                    <input class="material_upload_title " placeholder="${ lfn:message('sys-modeling-base:modelingMaterialMain.fdName.help')}" type="text">

                    <div class="material_upload_li_hr"></div>

                    <span class="material_upload_li_close">X</span>
                    <div class="material_upload_cover">
                        <div class="material_upload_li_progress ">
                            <div class="progress_wrapper progress_wrapper_r">
                                <div class="circle_progress"></div>
                            </div>
                            <div class="progress_wrapper progress_wrapper_l">
                                <div class="circle_progress"></div>
                            </div>
                            <div class="progress_text">
                                1%
                            </div>
                        </div>
                          <div class="material_upload_failed_circle ">
                          </div>
                        
                         <div class="material_upload_status">
                         <p>上传中</p>  
                         </div> 
                        <div class="material_upload_option">
                            <span class="material_upload_cancel" onclick="btnDelViewContainer(event)">${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.cancelUp')}</span>
                            <span class="material_upload_restart">${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.restartUp')}</span>
                            <span class="lui_material_mark_split material_upload_split"></span>
                            <span class="material_upload_del" onclick="btnDelViewContainer(event)">  ${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.del')}</span>
                        </div>
                    </div>
                </li>
                <!-- temp end-->
               
            </ul>
            <br />
        </div>
        <div class="material_dlg_btn_bar">
            <span class="material_dlg_btn gary" onclick="Com_CloseWindow();">${lfn:message('button.cancel') }</span>
            <span class="material_dlg_btn" onclick="uploadClickOK('${HtmlParam.type}');">${lfn:message('button.ok') }</span>
        </div>

    </template:replace>
</template:include>