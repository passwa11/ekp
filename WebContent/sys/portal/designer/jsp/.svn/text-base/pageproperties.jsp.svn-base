<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.simple">
	<template:replace name="title">页面属性</template:replace>
	<template:replace name="body">
	<ui:toolbar layout="sys.ui.toolbar.float" count="10" var-navwidth="100%">
		<ui:button onclick="onEnter()" text="${lfn:message('button.ok')}"></ui:button>
	</ui:toolbar>
	<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
	<script type="text/javascript" src="<c:url value="/resource/js/jquery-plugin/jquery.form.js"/>?s_cache=${LUI_Cache}"></script>
	    <style type="text/css" >
        .image_opt_button_area{
            float: left;
        }  
        
        #remove_image_button_area{
            display: none;
        }

		.a_upload_image {
		    padding: 4px 10px;
		    height: 20px;
		    line-height: 20px;
		    position: relative;
		    cursor: pointer;
		    color: #4285f4;
		    background: #fff;
		    border: 1px solid #ddd;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1
		}
		
		.a_remove_image{
		    margin-left: 20px;
		    padding: 4px 10px;
		    height: 20px;
		    line-height: 20px;
		    position: relative;
		    cursor: pointer;
		    color: #4285f4;
		    background: #fff;
		    border: 1px solid #ddd;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1
		}
		
		.a_upload_image input {
		    position: absolute;
		    font-size: 100px;
		    right: 0;
		    top: 0;
		    opacity: 0;
		    filter: alpha(opacity=0);
		    cursor: pointer
		}
		
		.a_upload_image:hover,.a_remove_image:hover {
		    color: #fff;
		    background: #4285f4;
		    border-color: #ccc;
		    text-decoration: none;
		}
		
		.imageUpload{
		    float: left;
		    padding-right: 10px;
		}
		.imagePreview{
		    float: right;
		    width: 200px;
		    height: 180px;
			border: 1px #daeaff solid;
			text-align: center;
			cursor: pointer;
		}
		.imageRecommendedSizeDesc{
		  	margin-top: 46px;
		    width: 260px;
		}
		.imageNoteDesc{
		  	margin-top: 10px;
		    width: 260px;
		}
		#backgroundImagePreview{
			width: 200px;
			height: 180px;
			display:none;
		}
    </style>
    
	<script>
		seajs.use(['theme!form']);
		
		String.prototype.startsWith = function (substring) {
		    var reg = new RegExp("^" + substring);
		    return reg.test(this);
		};
		 
		// 给字符串对象添加一个endsWith()方法
		String.prototype.endsWith = function (substring) {
		    var reg = new RegExp(substring + "$");
		    return reg.test(this);
		};
		
		$(document).ready(function(){  
			
			    window.onReady = function(){
					if(window.$dialog == null){
						window.setTimeout(onReady, 100);
						return;
					}
					var dp = window.$dialog.dialogParameter;
					if(dp.pageWidth){
						$("#pageWidth").val(dp.pageWidth);
					}	
					if(dp.backgroundImagePath){
						 $("#backgroundImagePath").val(dp.backgroundImagePath);
						 var previewImagePath = "${LUI_ContextPath}"+dp.backgroundImagePath;
						 $("#backgroundImagePreview").attr("src",previewImagePath).show();
						 $("#remove_image_button_area").show();
					}			    	
			    };
			
				onReady();
				
			    /**
				*  点击确定按钮响应事件
				* @return
				*/	
				window.onEnter = function(){
					var data = {};
					data["pageWidth"] = $("#pageWidth").val();
					if(!data.pageWidth.endsWith("%")&&!data.pageWidth.endsWith("px")){
						alert('${lfn:message("sys-portal:sysPortalPage.desgin.property.validate.tip") }');
						return;
					}
					if(data.pageWidth.endsWith("%")){
						if(parseInt(data.pageWidth)>=98){
							if(!confirm('${lfn:message("sys-portal:sysPortalPage.desgin.property.validate.confirm") }')){
								return false;
							}
						}
					}
					var backgroundImagePath = $("#backgroundImagePath").val();
					if(backgroundImagePath){
						data["backgroundImagePath"] = backgroundImagePath; 
					}
					window.$dialog.hide(data);
				};
				
			    /**
				*  点击上传背景图片 处理事件
				* @param fileDom  HTML DOM Element对象
				* @return
				*/	
				window.uploadFileChange = function(fileDom){
					var index = fileDom.value.lastIndexOf("\\");
					var fileName = fileDom.value.substring(index + 1);
					var ext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase(); // 文件扩展名
	
					// 校验文件类型
			        if(ext!="png"&&ext!="jpg"&&ext!="jpeg"&&ext!="gif"){
			           return;
			        }
					
					// jQuery ajax 异步上传文件
					var uploadUrl = Com_Parameter.ContextPath+"sys/portal/sys_portal_page/sysPortalPage.do?method=uploadBackgroundImage";
	
			        seajs.use([ 'lui/dialog' ], function( dialog ) {
			        	
						window.import_load = dialog.loading(); // 显示导入loading遮罩层
					    var uploadForm = $("<form enctype=\"multipart/form-data\"></form>");  // 定义一个上传文件form表单
					    uploadForm.append($("#upload_image_link_button").find("input[name='imageFile']")); // 移动文件上传input DOM到上传表单中
					    $("body").append(uploadForm);  // 将表单放置在页面body中
	
						$.ajaxSetup({ cache: false });         // 禁止jquery ajax缓存
						uploadForm.ajaxSubmit({
							type: "post", 
							url: uploadUrl,
							dataType: "json", 
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							success: function(data) { 
								window.import_load.hide();
			                	var result = data;
			                	if(result && result.imagePath){
			                		var previewImagePath = "${LUI_ContextPath}"+result.imagePath;
			                		$("#remove_image_button_area").show();
			                		$("#backgroundImagePreview").attr("src",previewImagePath).show();
			                		$("#backgroundImagePath").val(result.imagePath);
			                	}else{
			                		$("#backgroundImagePreview").hide();
			                	}
			                	
								// 重新创建input file文件上传元素（避免file在某些浏览器的onChange事件对同一文件不会二次触发，导致第二次选择相同文件时导入功能失效）
			                	$("#upload_image_link_button").append("<input type=\"file\" name=\"imageFile\" accept=\".png,.jpg,.jpeg,.gif\" onchange=\"uploadFileChange(this);\">");
								// 删除作为临时上传文件的表单
								uploadForm.remove();
							}
						});	
	
			        });
			        
			        return false; // 阻止表单自动提交事件				
				};
			
			    /**
				*  点击移除背景图片 处理事件
				* @return
				*/	
			    window.removeBackgroundImage = function(){
			    	$("#backgroundImagePath").val("");
			    	$("#backgroundImagePreview").hide();
			    	$("#remove_image_button_area").hide();
			    };	
			    
			    /**
				*  点击背景图片 处理事件(打开新窗口查看原图)
			    * @param imageDom  HTML DOM Element对象
				* @return
				*/	
			    window.viewImage = function(imageDom){
			    	window.open($(imageDom).attr("src"));
			    };
		});

	</script>

<div style="margin-top: 70px;">
		<table class="tb_normal" style="width: 600px;">
			<tbody>
			    <!-- 页面宽度 -->
				<tr>
					<td width="80px;" valign="top">${lfn:message('sys-portal:sysPortalPage.desgin.property.pagewidth') }：<br> <span class="com_help">&nbsp; </span></td>
					<td valign="top"><input type="text" id="pageWidth" name="pageWidth" value="<%=com.landray.kmss.sys.ui.util.SysUiConfigUtil.getFdWidth()%>" style="width:50px;"  />
						<div style="margin-top: 20px;">
						   <span class="com_help">${lfn:message('sys-portal:sysPortalPage.desgin.property.eg') }</span>
						</div>
						<div style="margin-top: 10px;">
						   <span class="com_help">${lfn:message('sys-portal:sysPortalPage.desgin.property.note') }</span>
						</div>
					</td>
				</tr> 
				
				<c:if test="${not empty param['allowUploadBackgroundImage'] && param['allowUploadBackgroundImage'] eq 'true' }">
					<!-- 内容区背景图片 -->
					<tr>
						<td width="80px;" valign="top">${lfn:message('sys-portal:sysPortalPage.desgin.background.contentArea')}<br/>${lfn:message('sys-portal:sysPortalPage.desgin.background.image')}：</td>
						<td valign="top">
			
						        <div class="imageUpload">
			                            <!-- 上传背景图片 按钮 -->
			                            <div id="upload_image_button_area" class="image_opt_button_area" >
											<a id="upload_image_link_button" class="a_upload_image" href="javascript:void(0);">
											     <input type="file" name="imageFile" accept=".png,.jpg,.jpeg,.gif" onchange="uploadFileChange(this);">${lfn:message('sys-portal:sysPortalPage.desgin.background.upload.button.text')}
											</a>
										</div>
					                    <!-- 移除背景图片 按钮 -->
					                    <div id="remove_image_button_area" class="image_opt_button_area">
										    <a id="remove_image_link_button" class="a_remove_image" href="javascript:void(0);" onclick="removeBackgroundImage();" >${lfn:message('sys-portal:sysPortalPage.desgin.background.remove.button.text')}</a>
										</div>
						            <div class="imageRecommendedSizeDesc"><span class="com_help">${lfn:message('sys-portal:sysPortalPage.desgin.background.recommendedSize') }</span></div>
						            <div class="imageNoteDesc"><span class="com_help">${lfn:message('sys-portal:sysPortalPage.desgin.background.note') }</span></div>
						        </div>
						        
						        <!-- 图片预览区域 -->
								<div class="imagePreview">
								   <!-- 图片标签 -->
								   <img id="backgroundImagePreview" onclick="viewImage(this);" />
								   <!-- 图片存放路径隐藏hidden -->
								   <input type="hidden" id="backgroundImagePath" />
								</div>		
						</td>
					</tr> 
				</c:if>
				
			</tbody>
		</table>    
</div>


	</template:replace>
</template:include>