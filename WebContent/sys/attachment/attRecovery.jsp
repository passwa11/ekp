<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-attachment" key="sysAttRecovery.info.title" />
	</template:replace>
	<template:replace name="body">
	<script language="JavaScript">
	  seajs.use(['theme!form']);
	</script>
<script>
	seajs.use( [ 'lui/dialog' ], function(dialog,topic) {
		window.dialog = dialog;
	});
	
	var __docId = "";
	
	function checkPath(){
	   seajs.use(['lui/util/str'], function(strutil){
		   $("#successTb").hide();
		   $("#failTb").hide();
	     var fdDocIds = document.getElementsByName("fdDocIds")[0];
	     if(fdDocIds.value == ""){
	    	 dialog.alert('<bean:message bundle="sys-attachment" key="sysAttRecovery.info.input" />');
	     }else{
			 var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=findFilePath"; 
			 $.ajax({     
	    	     type:"post",    
	    	     url:url,   
	    	     data:{fdDocIds:fdDocIds.value},
	    	     async:false,    //用同步方式 
	    	     success:function(data){
	    	    	 var results = eval("(" + data + ")");
	    	    	 $("#resultTb tr:not(:first)").remove();
	    	    	 if (results.length > 0) {
	    	    		 $("#resultDiv").show();
						for(var i=0;i<results.length;i++){
							__docLink = '${LUI_ContextPath}'+results[i].fdLink;
							trHTML = '<tr>';
							trHTML += '<td>';
							if(results[i].fdExist == 'true'){
								trHTML += '<input type="checkbox" name="List_Selected" value="'+results[i].fdFilePath+'"/>';
							}
							trHTML += '</td>';
							trHTML += '<td>';
							trHTML += strutil.encodeHTML(results[i].fdFileName);
							trHTML += '</td>';
							trHTML += '<td>';
							trHTML += results[i].fdKey;
							trHTML += '</td>';
							trHTML += '<td>';
							trHTML += results[i].fdSize;
							trHTML += '</td>';
							trHTML += '<td>';
							trHTML += results[i].fdFilePath;
							trHTML += '</td>';
							/*
							trHTML += '<td>';
							trHTML += '<a class="com_btn_link" target="_blank" href="${LUI_ContextPath}'+results[i].fdLink+'"> 链接到文档';
							trHTML += '</a>';
							trHTML += '</td>';
							*/
							trHTML += '</tr>';
							$("#resultTb").append(trHTML);
						}
	    	    	 }else{
	    	    		 dialog.alert('<bean:message bundle="sys-attachment" key="sysAttRecovery.info.nodata" />');
	    	    	 }
	    		}  
			 });
	    }
	});
}
	
	function openDoc(){
		if(__docLink!=""){
			 Com_OpenWindow(__docLink,"_blank");
		}
	}
	
	function restoreFile(){
		var values = [];
		$("input[name='List_Selected']:checked").each(function(){
			values.push($(this).val());
		});
		if(values.length==0){
			dialog.alert('<bean:message bundle="sys-attachment" key="sysAttRecovery.info.select" />');
			return;
		}
		
		dialog.confirm('<bean:message bundle="sys-attachment" key="sysAttRecovery.info.confirm" />',function(flag){
			if(flag==true){
				var restoreUrl="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=restorefile"; 
				$.post(restoreUrl,$.param({"List_Selected":values},true),function(data){
					if(data['success'].length>0){
						$("#successTb").show();
						$("#successTb tr").remove();
						for(var i=0;i<data['success'].length;i++){
							trHTML = '<tr>';
							trHTML += '<td>';
							trHTML += data['success'][i]+ ' <bean:message bundle="sys-attachment" key="sysAttRecovery.info.sucess" />';
							trHTML += '</td>';
							trHTML += '</tr>';
							$("#successTb").append(trHTML);
						}
					}
					if(data['fail'].length>0){
						$("#failTb").show();
						$("#failTb tr").remove();
						for(var i=0;i<data['fail'].length;i++){
							trHTML = '<tr>';
							trHTML += '<td>';
							trHTML += data['fail'][i]+ ' <bean:message bundle="sys-attachment" key="sysAttRecovery.info.fail" />';
							trHTML += '</td>';
							trHTML += '</tr>';
							$("#failTb").append(trHTML);
						}
					}
					//alert(data['success'].length);
				},'json');
		    }
		},"warn");
		 
	}
</script>
<div style="padding-top:40px;">
<p class="txttitle"><bean:message bundle="sys-attachment" key="sysAttRecovery.info.title" /></p>
<center>
	<table class="tb_normal" width=90%>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-attachment" key="sysAttRecovery.fdId" />
			</td><td width="85%">
				<input type="text" name="fdDocIds" style="width:90%" class="inputsgl"/>
				<span class="txtstrong">*</span><br>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<bean:message bundle="sys-attachment" key="sysAttRecovery.info.desc" />
			</td>
		</tr>
	</table>
	<br/>
	<span>
	    <ui:button id="ok_id" text="${lfn:message('sys-attachment:sysAttRecovery.opt.submit')}"  order="2"  onclick="checkPath();">
		</ui:button>
	</span>
</center>	
<center>
<div id="resultDiv" style="display: none">
<table class="tb_normal" width=90% id="resultTb" style="margin-top:30px">
	<tr>
	    <td class="td_normal_title" width="3%"></td>
	    <td class="td_normal_title" width=25%>
			<bean:message bundle="sys-attachment" key="sysAttRecovery.fdName" />
		</td>
		<td class="td_normal_title" width=10%>
			<bean:message bundle="sys-attachment" key="sysAttRecovery.fdKey" />
		</td>
		<td class="td_normal_title" width=8%>
			<bean:message bundle="sys-attachment" key="sysAttRecovery.fdSize" />
		</td>
		<td class="td_normal_title" width="50%">
			<bean:message bundle="sys-attachment" key="sysAttRecovery.fdPath" />
		</td>
	</tr>
</table>
<br/>
<span>
    <ui:button  text="${lfn:message('sys-attachment:sysAttRecovery.opt.link')}"   order="2"  onclick="openDoc();">
	</ui:button>&nbsp;
    <ui:button  text="${lfn:message('sys-attachment:sysAttRecovery.opt.restore')}" order="2"  onclick="restoreFile();">
	</ui:button>
</span>
</div>
</center>	
<table class="tb_normal" width=90% id="successTb" style="margin-top:30px;display: none">
</table>
<table class="tb_normal" width=90% id="failTb" style="margin-top:30px;display: none">
</table>
</div>	
</template:replace>
</template:include>