<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

<%@page import="com.landray.kmss.kms.common.model.KmsShareMain"%>
<%@page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@page import="com.landray.kmss.sys.organization.service.ISysOrgElementService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.common.service.IBaseService" %>
<%@ page import="com.landray.kmss.common.model.IBaseModel" %>
<%@ page import="com.landray.kmss.util.ModelUtil" %>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@page import="com.landray.kmss.constant.SysOrgConstant"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="com.landray.kmss.kms.common.model.KmsShareGoal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" 
	prefix="person"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}">
		<list:data-column property="fdSharePerson.fdName" title="${ lfn:message('kms-common:kmsShareMain.sharePerson') }">
		</list:data-column>
		<list:data-column property="fdSharePerson.fdId">
		</list:data-column>
		<list:data-column col="fdSharePerson" title="${ lfn:message('kms-common:kmsShareMain.sharePerson') }" escape="false">
			<ui:person personId="${item.fdSharePerson.fdId}" personName="${item.fdSharePerson.fdName}"></ui:person>
		</list:data-column>
		<list:data-column property="fdShareTime" title="${ lfn:message('kms-common:kmsShareMain.shareTime') }">
		</list:data-column>
		<list:data-column col="fdShareReason" title="${ lfn:message('kms-common:kmsShareMain.shareReason')}" escape="false">
			<%
				KmsShareMain kmsShareMain = (KmsShareMain)pageContext.getAttribute("item");
				if(kmsShareMain!=null && 
						StringUtil.isNotNull(kmsShareMain.getFdShareReason())){
					String shareReason = kmsShareMain.getFdShareReason();
					shareReason = shareReason.replaceAll("\\[face","<img src='" + request.getContextPath() 
			                + "/kms/common/resource/img/bq/").replaceAll("]",".gif' type='face'></img>");
					out.print(shareReason);
				}else{
					out.print("");
				}
			%>
		</list:data-column>
		<list:data-column col="goalPersonNames" title="${ lfn:message('kms-common:kmsShareMain.shareTarget')}" escape="false">
				<%
					KmsShareMain kmsShareMain = (KmsShareMain)pageContext.getAttribute("item");
					if(kmsShareMain!=null){
						String shareType = kmsShareMain.getFdShareType();
						if("person".equals(shareType)){
							List<KmsShareGoal> shareGoalList = kmsShareMain.getFdShareGoalList();
							String[] orgArray = new String[shareGoalList.size()];
							for(int j=0;j<shareGoalList.size();j++){
								KmsShareGoal shareGoal = shareGoalList.get(j);
								if(shareGoal!=null){
									String goalId = shareGoal.getFdModelId();
									orgArray[j] = goalId;
								}
							}
							ISysOrgCoreService sysOrgCoreService = 
								(ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService");
							List orgList = new ArrayList();
							if(orgArray.length > 0){
								orgList = sysOrgCoreService.findByPrimaryKeys(orgArray);
							}else{//兼容旧数据
								String goalPersonIds = kmsShareMain.getGoalPersonIds();
								if(StringUtil.isNotNull(goalPersonIds)){
									orgList = sysOrgCoreService.findByPrimaryKeys(
											ArrayUtil.toStringArray(goalPersonIds.split(";")));
								}
							}
							
							if(orgList.size()>0){
								StringBuffer sb = new StringBuffer();
								int personType = SysOrgConstant.ORG_TYPE_PERSON;
								for(int i=0;i<orgList.size();i++){
									SysOrgElement p = (SysOrgElement)orgList.get(i);
									String authorString = "";
									if(personType == p.getFdOrgType()){
										authorString = "<a class='com_author' href='" + request.getContextPath()+
														"/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" + 
														p.getFdId() +"' target='_blank'>"+ p.getFdName() + "</a>";
									}else{
										authorString = p.getFdName();
									}
									
									sb.append(authorString+"，");
								}
								String goalPesonNames = sb.toString();
								out.print(goalPesonNames.substring(0,goalPesonNames.length()-1));
							}
						}else if("sns".equals(shareType)){
							out.print("SNS");
						}
					}
				 %>
		</list:data-column>
		<list:data-column property="fdGroupId">
        </list:data-column>
		<list:data-column col="fdGroupName">
            <%
	            try{
					KmsShareMain kmsShareMain = (KmsShareMain)pageContext.getAttribute("item");
	                IBaseService snsGroupMainService = (IBaseService) SpringBeanUtil.getBean("snsGroupMainService");
	                if(snsGroupMainService!=null){
	                     IBaseModel baseModel = snsGroupMainService.findByPrimaryKey(kmsShareMain.getFdGroupId());
	                     if(null!=baseModel){
	                         String groupName = ModelUtil.getModelPropertyString(baseModel,"docSubject",null,null);
	                         out.print(groupName);
	                     }else{
	                         out.print("");
	                     }
	                }
	            }catch(Exception e){
	                e.printStackTrace();
	                out.print("");
	            }
            %>
        </list:data-column>
		<list:data-column col="imgUrl">
			<person:headimageUrl personId="${item.fdSharePerson.fdId}"/>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"></list:data-paging>
	
</list:data>