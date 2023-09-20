<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.edit">
	<template:replace name="title">
		附件
	</template:replace>
	<template:replace name="head">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/view-demo.css">
    </template:replace>
	<template:replace name="content">
		<br>
		<div style="border-bottom: 1px solid black;border-top: 1px solid black">
			<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=add">
				<div>
					上下结构
				</div>
				<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
					<c:param name="fdKey" value="attachment1" />
					<c:param name="fdRequired" value="true" />
					<c:param name="orient" value="vertical" />
					<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
				</c:import>
			</html:form> 
		</div>
		
		<br>

		
		<div style="border-bottom: 1px solid black;border-top: 1px solid black">
			<div>
				左右结构-左
			</div>
			<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=add">
				<table style="width:100%;">
					<tr>
						<td style="width:40%;">
							附件
						</td>
						<td style="width:60%;">
							<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
								<c:param name="fdKey" value="attachment2" />
								<c:param name="align" value="left" />
								<c:param name="fdRequired" value="true" />
								<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
							</c:import>
						</td>
					</tr>
				</table>
			</html:form>
		</div>
		
		<br>
		
		<div style="border-bottom: 1px solid black;border-top: 1px solid black">
			<div>
				左右结构-右
			</div>
			<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=add">
				<table style="width:100%;">
					<tr>
						<td style="width:40%;">
							附件
						</td>
						<td style="width:60%;">
							<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
								<c:param name="fdKey" value="attachment3" />
								<c:param name="align" value="right" />
								<c:param name="fdRequired" value="true" />
								<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
							</c:import>
						</td>
					</tr>
				</table>
			</html:form>
		</div>
	</template:replace>
</template:include>
