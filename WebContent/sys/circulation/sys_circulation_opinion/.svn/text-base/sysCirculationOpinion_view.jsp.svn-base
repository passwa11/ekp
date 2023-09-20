<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<script type="text/javascript">
	seajs.use(['theme!form']);
	</script>	
		 <table class="tb_normal" width=95% style="margin-top:30px">
		 	<tr>
		 		<td class="td_normal_title" width=15%>
					<!-- 传阅时间 --> 
					${lfn:message('sys-circulation:sysCirculationMain.fdCirculationTime')}
				</td>
				<td width=35% >
					<c:out value="${fdCirculationTime}"></c:out>
				</td>
				<td class="td_normal_title" width=15%>
					<!-- 填写时间 -->
					${lfn:message('sys-circulation:sysCirculationMain.fillTime')}
				</td>
				<td width=35% >
					<c:out value="${fdWriteTime}"></c:out>
				</td>
		 	</tr>
		 	<tr>
		 		<td class="td_normal_title" width=15%>
					<!-- 接收人 -->
					${lfn:message('sys-circulation:sysCirculationMain.receiver')}
				</td>
				<td width=35% >
					<c:out value="${sysCirculationOpinionForm.fdBelongPersonName}"></c:out>
				</td>
				<td class="td_normal_title" width=15%>
					<!-- 接收人部门 -->
					${lfn:message('sys-circulation:sysCirculationMain.receiverDept')}
				</td>
				<td width=35% >
					<c:out value="${fdDeptName}"></c:out>
				</td>
		 	</tr>
		 	<tr>
		 		<td class="td_normal_title" width=15%>
					<!-- 意见 -->
					${lfn:message('sys-circulation:sysCirculationMain.opinion')}
				</td>
		 		<td width=85% colspan="3">
					<xform:textarea property="docContent" style="height:100px;width:96%" ></xform:textarea>
		 		</td>
		 	</tr>
		 	<tr>
		 		<td class="td_normal_title" width=15%>
					<!-- 附件 -->
					${lfn:message('sys-circulation:sysCirculationMain.attachment')}
				</td>
		 		<td width=85% colspan="3">
		 			<div>
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="attachment" />
							<c:param name="formBeanName" value="sysCirculationOpinionForm" />
							<c:param name="fdMulti" value="true" />
							<c:param name="fdModelId" value="${sysCirculationOpinionForm.fdId }" />
							<c:param name="fdForceDisabledOpt" value="edit;print;copy" />
							<c:param name="fdModelName" value="com.landray.kmss.sys.circulation.model.SysCirculationOpinion" />
						</c:import>
					</div>
		 		</td>
		 	</tr>
		 </table>
	</template:replace>
</template:include>
