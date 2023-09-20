<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>

<xform:editShow>
<!-- k3登录方式 -->
	<tr id="k3Login_1" <c:if test="${ticSoapSettingForm.fdCheck eq 'false' || ticSoapSettingForm.fdAuthMethod!='k3Type'}">style="display: none"</c:if> >
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdAuthInfo"/>
		</td>
		<c:if test="${ticSoapSettingForm.extendInfoList!=null}">
			<td width="35%" colspan="3">
			<c:forEach items="${ticSoapSettingForm.extendInfoList}" var="currentObject" varStatus="vstatus">		  
				     <c:if test="${currentObject.key=='k3UserName' }">
				           <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdUserName"/>:
					       <input type="text" name="k3UserName" value="${currentObject.value}" class="inputsgl"  style="width:15%" />
				     </c:if> 
				       <c:if test="${currentObject.key=='k3Password' }">    <br>
				           <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdPassword"/> :
				           <input type="password" name="k3Password" value="${currentObject.value}" class="inputsgl"  style="width:15%" />
				    </c:if>
				    <span id="k3Login_2" <c:if test="${ticSoapSettingForm.fdCheck eq 'false' || ticSoapSettingForm.fdAuthMethod!='k3Type'}">style="display: none"</c:if> >
				     <c:if test="${currentObject.key=='k3IAisID' }">    <br>
				           <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdIAisID"/>:
					       <input type="text" name="k3IAisID"  value="${currentObject.value}" class="inputsgl"  style="width:15%" />
				  </c:if>
				    </span>
			</c:forEach>
			</td>
		</c:if>
		
		<c:if test="${ticSoapSettingForm.extendInfoList==null}">
		       <td width="35%" colspan="3">
		             <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdUserName"/>:
					 <input type="text" name="k3UserName" value="" class="inputsgl"  style="width:15%" />
					  <br>
					   <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdPassword"/>:
					   <input type="password" name="k3Password" value="" class="inputsgl"  style="width:16%" />
					   <br>   
					   <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdIAisID"/>:
					   <input type="text" name="k3IAisID" value="" class="inputsgl"  style="width:15%" />
			   </td>
		</c:if>
	</tr>

</xform:editShow>

<xform:viewShow>
<c:if test="${ticSoapSettingForm.fdCheck eq 'true' }">
<c:if test="${ticSoapSettingForm.fdAuthMethod == 'k3Type' }">
<!-- k3登录方式 -->
	<tr id="k3Login_2" <c:if test="${ticSoapSettingForm.fdCheck eq 'false' || ticSoapSettingForm.fdAuthMethod!='k3Type'}">style="display: none"</c:if> >
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdUserName"/>
		</td>
		
		<c:if test="${ticSoapSettingForm.extendInfoList!=null}">
			<td width="35%" colspan="3">
			<c:forEach items="${ticSoapSettingForm.extendInfoList}" var="currentObject" varStatus="vstatus">		  
				     <c:if test="${currentObject.key=='k3UserName' }">
				           <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdUserName"/>:
					       <input type="text" name="k3UserName" value="${currentObject.value}" class="inputread"  readonly="readonly" style="width:15%" />
				     </c:if> 
				       <c:if test="${currentObject.key=='k3Password' }">    <br>
				           <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdPassword"/>:
				           <input type="password" name="k3Password" value="${currentObject.value}" class="inputread" readonly="readonly" style="width:15%" />
				    </c:if>
				    <span id="k3Login_2" <c:if test="${ticSoapSettingForm.fdCheck eq 'false' || ticSoapSettingForm.fdAuthMethod!='k3Type'}">style="display: none"</c:if> >
				     <c:if test="${currentObject.key=='k3IAisID' }">    <br>
				           <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdIAisID"/>:
					       <input type="text" name="k3IAisID" value="${currentObject.value}" class="inputread"  readonly="readonly" style="width:15%" />
				  </c:if>
				    </span>
			</c:forEach>
			 </td>
		</c:if>
		
		<c:if test="${ticSoapSettingForm.extendInfoList==null}">
		       <td width="35%" colspan="3">
		             <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdUserName"/>:
					 <input type="text" name="k3UserName" value="" class="inputread" readonly="readonly" style="width:15%" />
					  <br>
					   <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdPassword"/>:
					   <input type="password" name="k3Password" value="" class="inputread" readonly="readonly" style="width:15%" />
					   <br>   
					   <bean:message bundle="tic-soap-connector" key="ticSoapAuth.k3FdIAisID"/>:
					    <input type="text" name="k3IAisID" value="" class="inputread" readonly="readonly" style="width:15%" />
			   </td>
		</c:if>
	</tr>
</c:if>
</c:if>	
</xform:viewShow>
	