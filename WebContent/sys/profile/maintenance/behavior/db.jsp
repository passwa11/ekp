<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.database" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			function deleteRow(dom){
				for(var tr = dom; tr.tagName!='TR'; tr = tr.parentNode){}
				var cell = tr.cells[0];
				var text = cell.innerText;
				if(confirm('<bean:message key="sys.profile.behavior.delete.confirm" bundle="sys-profile-behavior" />\r\n'+cell.innerText)){
					$('input[name="delete"]').val(text);
					document.forms[0].submit();
				}
			}
	
			$(function(){
				menu_focus("1__db");
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.database" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <div style="margin:10px auto; width:900px;">
					<table class="tb_normal" style="width:900px;">
						<tr>
							<td class="td_normal_title">
								${message}
							</td>
							<td class="td_normal_title" style="width:100px; text-align: center;">
								<c:if test="${not empty preHref}">
									<a href="${preHref}">[<bean:message key="sys.profile.behavior.return" bundle="sys-profile-behavior" />]</a>
								</c:if>
							</td>
						</tr>
						<c:forEach items="${entries}" var="entry">
							<tr>
								<td>
									<c:if test="${empty href}">
										<c:out value="${entry}" />
									</c:if>
									<c:if test="${not empty href}">
										<a href="${href}${entry}">${entry}</a>
									</c:if>
								</td>
								<td style="text-align: center;">
									<a href="#" onclick="deleteRow(this);return false;">[<bean:message key="sys.profile.behavior.delete" bundle="sys-profile-behavior" />]</a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<br><br>
					<form action="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=db<%= request.getQueryString()==null ? "" : "&"+request.getQueryString() %>" method="post">
						<input type="hidden" name="delete">
						<c:if test="${not empty param.coll}">
						<table class="tb_normal" style="width:900px;">
							<tr>
								<td>
									<div style="padding-bottom:5px;"><bean:message key="sys.profile.behavior.database.option" bundle="sys-profile-behavior" />query,sort,rowsize,pageNo,insert,save</div>
									<textarea name="option" style="width:100%; height:120px;">${param.option}</textarea>
									<center><input type="submit" value="提交" style="padding:3px 10px;cursor:pointer;"></center>
								</td>
							</tr>
						</table>
						</c:if>
					</form>
				</div>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>