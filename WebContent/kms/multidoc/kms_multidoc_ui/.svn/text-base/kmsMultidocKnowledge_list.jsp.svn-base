<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page
	import="com.landray.kmss.kms.multidoc.service.IKmsMultidocKnowledgeService"%>
<%@page
	import="com.landray.kmss.kms.multidoc.util.KmsMultidocKnowledgeUtil"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page
	import="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.common.dao.IBaseDao"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<%@ page import="com.landray.kmss.util.*" %>
<%@ page import="com.landray.kmss.kms.knowledge.borrow.util.KmsKnowledgeBorrowUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDictModel" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<c:set var="kmsKnowledgeBaseDocListDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docCategory.categoryTrue') }"></c:set>
<c:set var="kmsMultidocKnowledgeTemplateName" value="${ lfn:message('kms-multidoc:kmsMultidocKnowledge.fdTemplateName.categoryTrue') }"></c:set>
<%
	String _categoryId = request.getParameter("categoryId");
	JSONObject jsonSetTop = new JSONObject();
	
	Page p = (Page) request.getAttribute("queryPage");
	List<KmsMultidocKnowledge> list = p.getList();
	String fdIds = "";
	int i = 0;
	for (KmsMultidocKnowledge kmsMultidocKnowledge : list) {
		fdIds += i == 0 ? "'" + kmsMultidocKnowledge.getFdId() + "'"
				: ",'" + kmsMultidocKnowledge.getFdId() + "'"; 
		i++;
		String topCategoryId = kmsMultidocKnowledge.getFdTopCategoryId();
		String _setTopTitle = ResourceUtil.getString("kmsMultidoc.setTop", "kms-multidoc");
		String _setTopSign = "<span class='lui_icon_s lui_icon_s_icon_settop' title='"+_setTopTitle+"'></span>";
		Boolean docIsIndexTop = kmsMultidocKnowledge.getDocIsIndexTop();
		if(docIsIndexTop!=null&&docIsIndexTop){
			jsonSetTop.element(kmsMultidocKnowledge.getFdId(),_setTopSign);
		}else{
			jsonSetTop.element(kmsMultidocKnowledge.getFdId(),"");
		}
	}
	request.setAttribute("jsonSetTop", jsonSetTop);
	
	JSONObject tagJson = new JSONObject();
	 
    boolean isBorrowOpen = KmsKnowledgeBorrowUtil.checkBorrowOpen(request);
    request.setAttribute("isBorrowOpen", isBorrowOpen);
%>

<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="icon" escape="false" title="${lfn:message('kms-knowledge:kmsKnowledge.introduced')}">
		  	 <c:if test="${item.docIsIntroduced==true}">
		  	 	<span class="lui_icon_s lui_icon_s_icon_essence" title="${lfn:message('kms-knowledge:kmsKnowledge.introduced')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='20'}">
			 	<span class="lui_icon_s lui_icon_s_icon_examine" title="${lfn:message('status.examine')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='10'}">
			 	<span class="lui_icon_s lui_icon_s_icon_draft" title="${lfn:message('status.draft')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='00'}">
			 	<span class="lui_icon_s lui_icon_s_icon_discard" title="${lfn:message('status.discard')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='11'}">
			 	<span class="lui_icon_s lui_icon_s_icon_refuse" title="${lfn:message('status.refuse')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='40'}">
			 	<span class="lui_icon_s lui_icon_s_icon_expire" title="${lfn:message('status.expire')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='25'}">
			 	<span class="lui_icon_s lui_icon_s_icon_waitpublish" title="${ lfn:message('kms-common:kmsDocStatus.waitpublish') }"></span>
			 </c:if>
			 ${jsonSetTop[item.fdId]}
		</list:data-column>
		<list:data-column property="docIsIntroduced">
		</list:data-column>
		<list:data-column property="docSubject"
			title="${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docSubject')}">
		</list:data-column>
		<list:data-column property="fdDescription"
			title="${lfn:message('kms-multidoc:kmsMultidocKnowledge.fdDescription')}">
		</list:data-column>
		<list:data-column property="docCreator.fdName"
			title="${ lfn:message('kms-multidoc:kmsMultidoc.creator') }">
		</list:data-column>	
		<list:data-column property="docDept.fdName"
			title="${ lfn:message('kms-multidoc:kmsMultidoc.form.main.docDeptId') }">
		</list:data-column>			
		<list:data-column col="docStatus"
			title="${ lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docStatus') }">
			<sunbor:enumsShow value="${item.docStatus}"
				enumsType="common_status" />
		</list:data-column>
		<list:data-column property="docCreateTime"
			title="${ lfn:message('kms-multidoc:kmsMultidocSubside.docCreateTime') }">
		</list:data-column>				
		<%--多作者头像--%>
		<list:data-column col="docAuthor.fdAuthorImageUrl">
		     <c:choose>
		        <c:when test="${not empty item.fdDocAuthorList}">
			         <c:if test="${not empty item.fdDocAuthorList}">
			            <c:if test="${item.fdDocAuthorList.size()>1}">
			                 /sys/person/resource/images/head_image.png
			            </c:if>
			             <c:if test="${item.fdDocAuthorList.size() == 1}">
			            	<person:headimageUrl personId="${item.fdDocAuthorList[0].fdSysOrgElement.fdId}" size="m" />
			            </c:if>
			         </c:if>
		        </c:when>
		        <c:otherwise>
		           <c:if test="${empty item.fdDocAuthorList}">
						<%
							Object basedocObj = pageContext.getAttribute("item");
							if (basedocObj != null) {
								KmsMultidocKnowledge kmsMultidocKnowledge = (KmsMultidocKnowledge) basedocObj;
								String outerAuthor="";
								if(kmsMultidocKnowledge.getOuterAuthor()!="" && kmsMultidocKnowledge.getOuterAuthor()!=null){
									outerAuthor=kmsMultidocKnowledge.getOuterAuthor().trim();
									if(outerAuthor.indexOf(";")>-1){
										String[] outerAuthors=outerAuthor.split(";");
										if(outerAuthors.length>1){
											out.print("/sys/person/resource/images/head_image.png");
										} 
									}else{
										String[] outerAuthors=outerAuthor.split(";");
										if(outerAuthors.length>1){
											out.print("/sys/person/resource/images/head_image.png");
										} 
									}
								}
							}
						%>
			        </c:if>
		        </c:otherwise>
		     </c:choose>
		</list:data-column>
		
		<list:data-column col="docAuthor.fdName"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }" escape="false">
			<c:if test="${not empty item.fdDocAuthorList}">
			   <c:forEach var="obj"  items="${item.fdDocAuthorList}" varStatus="status">
			        <c:choose>
			           <c:when test="${status.count==item.fdDocAuthorList.size() }">
			              <ui:person personId="${obj.fdSysOrgElement.fdId}" personName="${obj.fdSysOrgElement.fdName}"></ui:person> 
			           </c:when>
			           <c:otherwise>
			             <ui:person personId="${obj.fdSysOrgElement.fdId}" personName="${obj.fdSysOrgElement.fdName}"></ui:person>;
			           </c:otherwise>
			        </c:choose>
			   </c:forEach> 
			</c:if>
			<c:if test="${empty item.fdDocAuthorList  }">
				 <a class="com_outer_author" href="javascript:;"><c:out value="${item.outerAuthor }"/></a>
			</c:if>
	     </list:data-column> 
	     
	     <list:data-column col="_docAuthor.fdName"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }" escape="false">
			<c:if test="${not empty item.fdDocAuthorList}">
			   <c:forEach var="obj"  items="${item.fdDocAuthorList}" varStatus="status">
			        <c:choose>
			           <c:when test="${status.count==item.fdDocAuthorList.size() }">
			                <ui:person personId="${obj.fdSysOrgElement.fdId}" personName="${obj.fdSysOrgElement.fdName}"></ui:person> 
			           </c:when>
			           <c:otherwise>
			               <ui:person personId="${obj.fdSysOrgElement.fdId}" personName="${obj.fdSysOrgElement.fdName}"></ui:person> ;
			           </c:otherwise>
			        </c:choose>
			       
			   </c:forEach> 
			</c:if>
			<c:if test="${empty item.fdDocAuthorList  }">
				 <a class="com_outer_author" href="javascript:;"><c:out value="${item.outerAuthor }"/></a>
			</c:if>
	     </list:data-column> 
		
		  <list:data-column col="docAuthorId">
		   <c:if test="${not empty item.fdDocAuthorList}">
		      <c:forEach var="obj1"  items="${item.fdDocAuthorList}">
		        <c:out value="${obj1.fdSysOrgElement.fdId}"/>
		      </c:forEach>
		  </c:if>
		</list:data-column>
		
	<%-- 	<list:data-column col="docAuthorId">
			<c:out value="${kmsMultidocKnowledge.docAuthor.fdId}"/>
		</list:data-column> --%>
		
		<%-- <list:data-column col="docAuthor.fdName"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }" escape="false">
			<c:if test="${not empty item.docAuthor.fdId  }">
				<ui:person personId="${item.docAuthor.fdId }" personName="${item.docAuthor.fdName }"></ui:person>
			</c:if>
			<c:if test="${empty item.docAuthor.fdId  }">
				<a class="com_author" href="javascript:void(0);"><c:out value="${item.outerAuthor}"/></a> 
			</c:if>
		</list:data-column>
		<list:data-column col="_docAuthor.fdName"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }" escape="false">
			<c:if test="${not empty item.docAuthor.fdId  }">
				<ui:person personId="${item.docAuthor.fdId }" personName="${item.docAuthor.fdName }"></ui:person>
			</c:if>
			<c:if test="${empty item.docAuthor.fdId  }">
				<c:out value="${item.outerAuthor}"/>
			</c:if>
		</list:data-column> --%>

		<list:data-column col="docPublishTime"
			title="${lfn:message('kms-multidoc:kmsMultidocKnowledge.docPublishTime') }">
			 <kmss:showDate value="${ empty item.docEffectiveTime? item.docPublishTime : item.docEffectiveTime }" type="date" />
		</list:data-column>
		<list:data-column title="${lfn:message('sys-tag:table.sysTagTags') }"
			col="sysTagMain">
			${tagJson[item.fdId]}
		</list:data-column>
		<list:data-column col="docIntrCount"
			title="${lfn:message('kms-knowledge:kmsKnowledge.intrCount') }" escape="false">
			<span class="com_number" title="${not empty item.docIntrCount ? item.docIntrCount : 0}">${not empty item.docIntrCount ? item.docIntrCount : 0}</span>
		</list:data-column>
		<list:data-column col="docEvalCount"
			title="${lfn:message('kms-knowledge:kmsKnowledge.evalCount') }" escape="false">
			<span class="com_number" title="${not empty item.docEvalCount ? item.docEvalCount : 0}">${not empty item.docEvalCount ? item.docEvalCount : 0}</span>
		</list:data-column>
		<list:data-column col="docReadCount"
			title="${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.read') }" escape="false">
			<span class="com_number" title="${item.docReadCount}">${item.docReadCount}</span>
		</list:data-column>
		<list:data-column col="fdTotalCount"
			title="${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.read') }" escape="false">
			<span class="com_number" title="${item.fdTotalCount}">${item.fdTotalCount}</span>
		</list:data-column>
		<list:data-column
			title="${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.score') }"
			col="docScore" escape="false">
			<c:choose>
				<c:when test="${not empty item.docScore}">
					<span class="com_number"><c:out value="${item.docScore}"></c:out></span>
				</c:when>
				<c:otherwise>
					<span class="com_number"><c:out value="${0.0}"></c:out></span>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdImageUrl" title="imageLink">
				<c:if test="${loadImg == true}">
				<%
					Object basedocObj = pageContext.getAttribute("item");
					if(basedocObj != null) {
						KmsMultidocKnowledge kmsMultidocKnowledge 
										= (KmsMultidocKnowledge)basedocObj;
						out.print(KmsMultidocKnowledgeUtil.getImgUrl(kmsMultidocKnowledge,request));
					}
				%>
				</c:if>
		</list:data-column>
		<list:data-column col="docCategory.fdName" title="${kmsKnowledgeBaseDocListDocCategory}">
			${item.docCategory.fdName}
		</list:data-column>
		<list:data-column col="docAttName" title="${lfn:message('kms-multidoc:kmsMultidoc.attName')}">
			${__s[item.fdId]}
		</list:data-column>
		<list:data-column col="docCategoryId" title="${kmsMultidocKnowledgeTemplateName}">
			${item.docCategory.fdId}
		</list:data-column>
        <c:if test="${isBorrowOpen}">
			<list:data-column col="docBorrowFlag" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
				<c:out value="${item.docBorrowFlag }" />
			</list:data-column>
			<c:choose>
				<c:when test="${0 == item.docBorrowFlag}">
					<list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
							<span class="com_number">
							<c:out value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.canRead') }" />
						</span>
					</list:data-column>
				</c:when>
				<c:when test="${1 == item.docBorrowFlag}">
					<list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
							<span class="com_number">
							<c:out value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.canBorrow') }" />
						</span>
					</list:data-column>
				</c:when>
				<c:otherwise>
				    <list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
	                </list:data-column>
				</c:otherwise>
			</c:choose>
		</c:if>
        <list:data-column col="borrowOpenFlag">
            ${borrowOpenFlag}
        </list:data-column>
		<list:data-column col="subModuleName" title="${lfn:message('kms-knowledge:kms.knowledge.sub.criterion')}">
			<%
				Object basedocObj = pageContext.getAttribute("item");
				SysDictModel sysDictModel = null;
				if(basedocObj != null) {
					KmsMultidocKnowledge kmsMultidocKnowledge
							= (KmsMultidocKnowledge)basedocObj;
					String subModelName = kmsMultidocKnowledge.getSubModelName();
					if(StringUtils.isNotBlank(subModelName)){
						sysDictModel = SysDataDict.getInstance().getModel(subModelName);
					}
				}
				if(null != sysDictModel){
					out.print(ResourceUtil.getString(sysDictModel.getMessageKey()));
				}else{
					out.print("未知");
				}
			%>
		</list:data-column>
	</list:data-columns>


	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
