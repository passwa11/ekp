<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
<template:replace name="head">
	<script>
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
	<p class="txttitle"><bean:message bundle="sys-help" key="sysHelpModule.module.select"/></p>
	<table class="tb_normal" width="80%">
		<tr>
			<c:forEach var = "moduleItem" items="${moduleList}" varStatus="vStatus">
				<td>
					<c:if test="${moduleItem['isSave'] != true}">
						<div style="float:left;margin-top:4px">
							<input type="checkbox" value="${moduleItem['fdModulePath']}"/>
						</div>
					</c:if>
					<div style="float:left;width:155px;" title="${lfn:message(moduleItem['fdModuleName'])}(${moduleItem['fdModulePath']})">
						${lfn:message(moduleItem['fdModuleName'])}(${moduleItem['fdModulePath']})
						<font style="color:#4285f4;">
							<c:if test="${moduleItem['isSave'] == true}">
								(<bean:message bundle="sys-help" key="sysHelpModule.module.isSave"/>)
							</c:if>
						</font>
					</div>
				</td>
				<c:if test="${vStatus.index % 4 ==3 }">
					</tr>
					<tr>
				</c:if>
			</c:forEach>
			<script>
				var leaveNum = '${leaveNum}';
				if(Number(leaveNum)<4){
				    for(var i=0;i<Number(leaveNum);i++ ){
				        document.write("<td>&nbsp;</td>")
				    }
				}
			</script>
		</tr>
	</table>
	<style>
	.lui_form_content{
		padding-bottom: 20px;
	}
	.txttitle{
		padding-top: 10px;
	}
	.tb_normal {
		margin-top: 20px;
	}
	.tb_normal > tbody > tr > td{
		width: 25%;
	}
	</style>
</template:replace>
</template:include>