<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<style>
body{
	height: 90% !important;
}
</style>
<template:include ref="config.edit">
    <template:replace name="content">
        <table class="tb_normal" width="80%" style="margin: 30px auto;">
       		<td class="td_normal_title" width="15%">
       			<label>
       				<input id="selectAll" type="checkbox" value="" checked="true">
       				<span>${ lfn:message('sys-help:sysHelpModule.selectAll') }</span>
       			</label>
       		</td>
       		<td class="td_normal_title" width="85%">
       			${ lfn:message('sys-help:sysHelpConfig.fdModuleName') }
       		</td>
        	<c:forEach var="data" items="${moduleList}" >
	        	<tr>
	        		<td class="td_normal_title select" width="15%">
	        			<input class="select" type="checkbox" value="${data.value}" checked="true" onchange="selectAll(this)">
	        		</td>
	        		<td class="td_normal_title" width="85%">
	        			${data.text}
	        		</td>
	        	</tr>
        	</c:forEach>
        </table>
        
        <script>
        	$("#selectAll").click(function () {
        		if(this.checked){
	        		$(".select input[type='checkbox']").prop('checked', true);
        		}else{
	        		$(".select input[type='checkbox']").prop('checked', false);
        		}
        	}); 
        	
        	function selectAll(dom){
        		if(!dom.checked){
        			$("#selectAll").prop('checked', false);
        		}else{
        			var list = $(".select input[type='checkbox']");
        			var isSelectAll = true;
        			for(var i=0;i<list.length;i++){
						if(dom.value == list[i].value)
							continue;
						
        				if(list[i].checked){
        					continue;
        				}else{
        					isSelectAll = false;
        					break;
        				}
        			}
        			if(isSelectAll)
        				$("#selectAll").prop('checked', true);
        		}
        	}
        </script>
        
    </template:replace>
</template:include>