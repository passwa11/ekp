<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil,java.lang.String" %>

<template:include ref="config.profile.edit" >
	<template:replace name="head">
	<script>
	
	seajs.use(['lui/jquery'],function($){
		$(function() {
			var autoSaveExcept = top.window.autoSaveExcept;
			if(autoSaveExcept){
				if(autoSaveExcept.charAt(autoSaveExcept.length-1)==";"){
					autoSaveExcept = autoSaveExcept.substring(0,autoSaveExcept.length-1);
		 		}
			 		
		 		var name = autoSaveExcept.split(/[;；]/);
		 		for(var i=0;i<name.length;i++){
		 			$("#wpsCloudAutoSaveExcept").find("input[type='checkbox'][value='"+name[i]+"']").prop('checked',true);
		 		}
			}
		});
	});
		function getSelectModule(){
		    var selectVal="";
		    seajs.use(['lui/jquery'],function($){
		        $("input[type='checkbox']:checked").each(function(){
		            if(selectVal==""){
		                selectVal = this.value
		            }else{
		                selectVal += ";" + this.value;
		            }
		        })
		    })
		    return selectVal;
		}
	</script>
</template:replace>
	<template:replace name="content">
		<center>
			<table class="tb_normal" width='100%' style="margin-top: 10px;">
					<tr>
						<td>
							<div id='wpsCloudAutoSaveExcept'>
								<xform:checkbox  property="value(wpsCloudAutoSaveExcept)" showStatus="edit">
									<xform:customizeDataSource className="com.landray.kmss.sys.attachment.service.spring.WpsCloudAutoSaveNoDataSource"></xform:customizeDataSource>
								</xform:checkbox >
						    </div>
						</td>
					</tr>
			</table>
			
		</center>
	</template:replace>
</template:include>