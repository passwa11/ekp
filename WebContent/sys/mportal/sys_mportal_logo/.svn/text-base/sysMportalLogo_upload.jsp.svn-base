<%@page import="com.landray.kmss.sys.ui.plugin.SysUiTools"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="title">上传logo</template:replace>

	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" style="float:right;margin-right: 30px;">
			<ui:button text="${lfn:message('sys-mportal:sysMportal.profile.logo.upload')}" onclick="submit()"></ui:button>
		</ui:toolbar>

		<style>
		.msg {
			color: #808080;
			position: absolute;
			top:40px;
		}
		
		.input_name {
				float: left;
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
			
			.input_name input {
			    position: absolute;
			    font-size: 100px;
			    right: 0;
			    top: 0;
			    opacity: 0;
			    filter: alpha(opacity=0);
			    cursor: pointer
			}
			
			.input_name:hover {
			    color: #fff;
			    background: #4285f4;
			    border-color: #ccc;
			    text-decoration: none;
			}
		</style>
		<script>
			function submit() {
				var file = $("input[name=file]").val();
				if(file && file.length > 0)
					document.getElementsByName('sysUiLogoForm')[0].submit();
				else
					alert("${lfn:message('sys-mportal:sysMportal.profile.logo.tip')}");
			}
		</script>
	</template:replace>
	<template:replace name="content">
		<div class="container">
			<div>
				<html:form enctype="multipart/form-data"
				action="/sys/mportal/sys_mportal_logo/sysMportalLogo.do"
				method="post">
				<a class="input_name">
					<input type="file" name="file" accept=".png,.jpg,.jpeg,.gif,.ico" onchange="uploadFileChange(this)"/> ${lfn:message('common.fileUpLoad.selectFile')} 
				</a>
				<input type="hidden" name="method" value="upload" />
				<div class="upload_file_name" ></div>
			</html:form>
			</div>
			<div class="msg">${lfn:message('sys-mportal:sysMportal.profile.logo.support') }</div>
		</div>
		<script>
			seajs.use(["lui/jquery"], function($) {
				var rExt = /\.\w+$/,
					accept = /\.gif$|\.jpg$|\.jpeg$|\.bmp$|\.png$|\.ico$/i;
				
	            var invalidName = function(name) {
	            	return rExt.exec( name ) && !accept.test(name );
	            };
	            
				
				$("[name='file']").on("change" , function(e) {
					var name = $("[name='file']").val();
					if(invalidName(name)) {
						//某些远古浏览器不能将 name=file 的value置为"",采用reset
						document.forms[0].reset();
						alert("${lfn:message('sys-mportal:sysMportal.profile.logo.error')}");
					}
				});
			})
			function uploadFileChange(fileDom){		
				var index = fileDom.value.lastIndexOf("\\");
				var fileName = fileDom.value.substring(index + 1);
				// 显示上传文件名称
				$(".upload_file_name").text(fileName); 
			}	
		</script>
	</template:replace>
</template:include>