<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page	import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>
<%@page	import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.common.dao.IBaseDao"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="com.landray.kmss.util.NumberUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.kms.knowledge.borrow.util.KmsKnowledgeBorrowUtil"%>

<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page
		import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsKnowledgeBaseDocDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory') }"></c:set>
<c:set var="kmsKnowledgeBaseDocListDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory') }"></c:set>
<%
	String _categoryId = request.getParameter("categoryId");
	JSONObject jsonSetTop = new JSONObject();

	Page p = (Page) request.getAttribute("queryPage");
	List<KmsKnowledgeBaseDoc> list = p.getList();
	String fdIds = "";
	int i = 0;
	for (KmsKnowledgeBaseDoc kmsKnowledgeBaseDoc : list) {
		fdIds += i == 0 ? "'" + kmsKnowledgeBaseDoc.getFdId() + "'"
				: ",'" + kmsKnowledgeBaseDoc.getFdId() + "'";
		i++;
		String topCategoryId = kmsKnowledgeBaseDoc.getFdTopCategoryId();
		String docSubject = kmsKnowledgeBaseDoc.getDocSubject();
		String _setTopTitle = ResourceUtil.getString("kmsMultidoc.setTop", "kms-multidoc");
		String _setTopSign = "<span class='lui_icon_s lui_icon_s_icon_settop' title='"+_setTopTitle+"'></span>";
		Boolean docIsIndexTop = kmsKnowledgeBaseDoc.getDocIsIndexTop();
		if(docIsIndexTop!=null&&docIsIndexTop){
			jsonSetTop.element(kmsKnowledgeBaseDoc.getFdId(),_setTopSign);
		}else{
			jsonSetTop.element(kmsKnowledgeBaseDoc.getFdId(),"");
		}
	}
	request.setAttribute("jsonSetTop", jsonSetTop);

	boolean isBorrowOpen = KmsKnowledgeBorrowUtil.checkBorrowOpen(request);
	request.setAttribute("isBorrowOpen", isBorrowOpen);
%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdKnowledgeType"/>

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
								KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc) basedocObj;
								String outerAuthor="";
								if(basedoc.getOuterAuthor()!="" && basedoc.getOuterAuthor()!=null){
									outerAuthor=basedoc.getOuterAuthor();
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

		<list:data-column
				col="docAuthorName"
				title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }"
				escape="false">
			<c:if test="${not empty item.fdDocAuthorList}">
				<c:forEach var="obj"  items="${item.fdDocAuthorList}" varStatus="status">
					<c:choose>
						<c:when test="${status.count==item.fdDocAuthorList.size() }">
							<c:out value="${obj.fdSysOrgElement.fdName}"/>
						</c:when>
						<c:otherwise>
							<c:out value="${obj.fdSysOrgElement.fdName}"/>;
						</c:otherwise>
					</c:choose>

				</c:forEach>
			</c:if>
			<c:if test="${empty item.fdDocAuthorList  }">
				<c:out value="${item.outerAuthor }"/>
			</c:if>
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
							<ui:person personId="${obj.fdSysOrgElement.fdId}" personName="${obj.fdSysOrgElement.fdName}"></ui:person> /
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:if>
			<c:if test="${ empty item.fdDocAuthorList  }">
				<a class="com_outer_author" href="javascript:;" title="${item.outerAuthor}"><c:out value="${item.outerAuthor }"/></a>
			</c:if>
		</list:data-column>

		<list:data-column col="docAuthorId">
			<c:if test="${not empty item.fdDocAuthorList}">
				<c:forEach var="obj1"  items="${item.fdDocAuthorList}">
					<c:out value="${obj1.fdSysOrgElement.fdId}"/>
				</c:forEach>
			</c:if>
		</list:data-column>



		<%-- <list:data-column col="docAuthor.fdName"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }" escape="false">
			<c:if test="${not empty item.docAuthor.fdId  }">
				<ui:person personId="${item.docAuthor.fdId }" personName="${item.docAuthor.fdName }"></ui:person>
			</c:if>
			<c:if test="${empty item.docAuthor.fdId  }">
				<a class="com_outer_author" href="javascript:;"><c:out value="${item.outerAuthor }"/></a>
			</c:if>
		</list:data-column>
		<list:data-column col="docAuthorId">
			<c:out value="${item.docAuthor.fdId}"/>
		</list:data-column> --%>


		<list:data-column col="docCategory.fdName" title="${kmsKnowledgeBaseDocListDocCategory}">
			<%
				Object obj = pageContext.getAttribute("item");
				if (obj != null) {
					KmsKnowledgeBaseDoc baseModel = (KmsKnowledgeBaseDoc) obj;
					out.print(KmsKnowledgeUtil.getCategoryTreeString(baseModel));
				}
			%>
		</list:data-column>
		<list:data-column col="docCategoryName" title="${kmsKnowledgeBaseDocListDocCategory}">
			${item.docCategory.fdName}
		</list:data-column>

		<c:choose>
			<c:when test="${ param['q.mydoc'] == 'create' }">
				<list:data-column col="docPublishTime"
								  title="${lfn:message('kms-knowledge:kmsKnowledge.index.create.date') }">
					<%
						Object basedocObj = pageContext.getAttribute("item");
						if(basedocObj != null) {
							KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc) basedocObj;
							KmsKnowledgeBaseDoc model = KmsKnowledgeUtil.getKmsKnowledgeBaseDocById(basedoc.getFdId());
							out.print(DateUtil.convertDateToString(model.getDocCreateTime(), DateUtil.PATTERN_DATE));
						}
					%>
				</list:data-column>
			</c:when>
			<c:when test="${ param['q.mydoc'] == 'approval' }">
				<list:data-column col="docPublishTime"
								  title="${lfn:message('kms-knowledge:kmsKnowledge.index.upload.date') }">
					<%
						Object basedocObj = pageContext.getAttribute("item");
						if(basedocObj != null) {
							KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc) basedocObj;
							KmsKnowledgeBaseDoc model = KmsKnowledgeUtil.getKmsKnowledgeBaseDocById(basedoc.getFdId());
							out.print(DateUtil.convertDateToString(model.getDocCreateTime(), DateUtil.PATTERN_DATE));
						}
					%>
				</list:data-column>
			</c:when>
			<c:otherwise>
				<list:data-column col="docPublishTime"
								  title="${lfn:message('kms-knowledge:kmsKnowledge.index.upload.date') }">
					<kmss:showDate value="${ empty item.docEffectiveTime? item.docPublishTime : item.docEffectiveTime }" type="date" />
				</list:data-column>
			</c:otherwise>
		</c:choose>

		<list:data-column col="fdSetTopTime"
						  title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.fdSetTopTime') }">
			<kmss:showDate value="${item.fdSetTopTime}" type="date" />
		</list:data-column>
		<list:data-column col="icon" escape="false" title="${lfn:message('kms-knowledge:kmsKnowledge.introduced')}">
			<span>
				${jsonSetTop[item.fdId]}
				<c:if test="${item.docIsIntroduced==true}">
					<span class="lui_icon_s lui_icon_s_icon_essence" title="${lfn:message('kms-knowledge:kmsKnowledge.introduced')}"></span>
				</c:if>
				<c:if test="${item.docStatus=='20'}">
					<%--<span class="lui_icon_s lui_icon_s_new_know status_20_or_25" title="${lfn:message('status.examine')}">
						${lfn:message('status.examine')}
					</span>--%>
				</c:if>
				<c:if test="${item.docStatus=='10'}">
					<%--<span class="lui_icon_s lui_icon_s_new_know status_00_or_10_40" title="${lfn:message('status.draft')}">
						${lfn:message('status.draft')}
					</span>--%>
				</c:if>
				<c:if test="${item.docStatus=='00'}">
					<%--<span class="lui_icon_s lui_icon_s_new_know status_00_or_10_40" title="${lfn:message('status.discard')}">
						${lfn:message('status.discard')}
					</span>--%>
				</c:if>
				<c:if test="${item.docStatus=='11'}">
					<%--<span class="lui_icon_s lui_icon_s_new_know status_11" title="${lfn:message('status.refuse')}">
						${lfn:message('status.refuse')}
					</span>--%>
				</c:if>
				<c:if test="${item.docStatus=='40'}">
<%--					<span class="lui_icon_s lui_icon_s_new_know status_00_or_10_40" title="${lfn:message('status.expire')}">--%>
<%--						${lfn:message('status.expire')}--%>
<%--					</span>--%>
				</c:if>
			</span>
		</list:data-column>
		<list:data-column property="docIsIntroduced">
		</list:data-column>
		<list:data-column property="docSubject" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}">
		</list:data-column>
		<list:data-column property="fdDescription"
						  title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.fdDescription')}">
		</list:data-column>
		<%--供知识专辑使用--%>
		<list:data-column col="fdDescription1" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.fdDescription')}">
			<%
				Object basedocObj = pageContext.getAttribute("item");
				if(basedocObj != null) {
					KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc)basedocObj;
					if(basedoc.getFdDescription()!=null){
						String str = basedoc.getFdDescription();
						str = str.replace("\r\n"," ");
						out.print(str);
					}else{
						out.print("");
					}
				}
			%>
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
						  title="${lfn:message('kms-knowledge:kmsKnowledge.read') }" escape="false">
			<c:choose>
				<c:when test="${'fdTotalCount' == showRead}">
						<span class="com_number" title="${not empty item.fdTotalCount ? item.fdTotalCount : 0}">${not empty item.fdTotalCount ? item.fdTotalCount : 0}</span>
				</c:when>
				<c:otherwise>
						<span class="com_number" title="${not empty item.docReadCount ? item.docReadCount : 0}">${not empty item.docReadCount ? item.docReadCount : 0}</span>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdTotalCount"
						  title="${lfn:message('kms-knowledge:kmsKnowledge.read') }" escape="false">
				<span class="com_number" title="${not empty item.fdTotalCount ? item.fdTotalCount : 0}">${not empty item.fdTotalCount ? item.fdTotalCount : 0}</span>
		</list:data-column>
		<list:data-column col="fdImageUrl" title="imageLink" escape="false">
			<c:if test="${loadImg == true}">
				<%
					Object basedocObj = pageContext.getAttribute("item");
					if(basedocObj != null) {
						KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc) basedocObj;
						out.print(KmsKnowledgeUtil.getImgUrl(basedoc));
					}
				%>
			</c:if>
		</list:data-column>
		<list:data-column
				title="${lfn:message('kms-knowledge:kmsKnowledge.score') }"
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
		<c:if test="${isBorrowOpen}">
			<list:data-column col="docBorrowFlag" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
				<c:out value="${item.docBorrowFlag }" />
			</list:data-column>
			<c:choose>
				<c:when test="${0 == item.docBorrowFlag}">
					<list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
							<span class="com_number">
								<%--                       <c:out value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.canRead') }" /> --%>
						</span>
					</list:data-column>
				</c:when>
				<c:when test="${1 == item.docBorrowFlag}">
					<list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
							<span data-lui-mark-id="0" class="com_number docBorrow_span" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.borrow.pmpt')}" style="color: #A30014; cursor:pointer" onclick="window.moduleAPI.kmsKnowledge.docBorrow('${item.fdId}')">
							<c:import url="/kms/knowledge/kms_knowledge_ui/import/borrowSvg.jsp">
								<c:param name="fillText" value="${ lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.borrow.need')}"></c:param>
								<c:param name="svgStyle" value="${ param.dataType=='pic'? 'width: 58px;' : param.dataType=='des'? 'width: 60px;position: relative;top: 6px;' : 'width: 60px;position: relative;top: 2px;'}"></c:param>
							</c:import>
						</span>
					</list:data-column>
				</c:when>
				<c:when test="${3 == item.docBorrowFlag}">
					<list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
							<span class="com_number docBorrow_span" style="color: #F59A23" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.borrow.date')}${ borrowMap[item.fdId] }">
							<c:import url="/kms/knowledge/kms_knowledge_ui/import/borrowSvg.jsp">
								<c:param name="fillText" value="${ lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.borrow.ing')}"></c:param>
								<c:param name="svgStyle" value="${ param.dataType=='pic'? 'width: 58px;' : param.dataType=='des'? 'width: 60px;position: relative;top: 6px;' : 'width: 60px;position: relative;top: 2px;'}"></c:param>
								<c:param name="colorType" value="2"></c:param>
							</c:import>
						</span>
					</list:data-column>
				</c:when>
				<c:otherwise>
					<list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">

					</list:data-column>
				</c:otherwise>
			</c:choose>
			<list:data-column col="borrowEndTime" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.borrow.date')}" escape="false">
				<c:out value="${ borrowMap[item.fdId] }" />
			</list:data-column>
		</c:if>

		<list:data-column escape="false" col="docStatus" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docStatus') }">
			<sunbor:enumsShow enumsType="common_status" value="${item.docStatus}"></sunbor:enumsShow>
		</list:data-column>
		<list:data-column col="docCategoryId" title="${kmsKnowledgeBaseDocDocCategory}">
			${item.docCategory.fdId}
		</list:data-column>
		<list:data-column col="borrowOpenFlag">
			${borrowOpenFlag}
		</list:data-column>
		<c:if test="${ param['q.type'] == 'myBookmark' }">
			<list:data-column col="markTime" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.mark')}">
				<%
					Object basedocObj = pageContext.getAttribute("item");
					if(basedocObj != null) {
						KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc) basedocObj;
						out.print(KmsKnowledgeUtil.getMyDocMarkTime(basedoc.getFdId()));
					}
				%>
			</list:data-column>
		</c:if>
		<c:if test="${ param['q.mydoc'] == 'approved' }">
			<list:data-column col="lbpmTime" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.approved')}">
				<%
					Object basedocObj = pageContext.getAttribute("item");
					if(basedocObj != null) {
						KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc) basedocObj;
						out.print(KmsKnowledgeUtil.getMyDocLbpmTime(basedoc.getFdId()));
					}
				%>
			</list:data-column>
		</c:if>
		<list:data-column col="docAlterTime"
						  title="${ item.docStatus == '10'? lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.save') : lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.upload') }">

		<c:if test="${ item.docStatus == '10' ||  item.docStatus == '20' }">
				<%
					Object basedocObj = pageContext.getAttribute("item");
					if(basedocObj != null) {
						KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc) basedocObj;
						KmsKnowledgeBaseDoc model = KmsKnowledgeUtil.getKmsKnowledgeBaseDocById(basedoc.getFdId());
						out.print(DateUtil.convertDateToString(model.getDocCreateTime(), DateUtil.PATTERN_DATE));
					}
				%>
		</c:if>
		</list:data-column>
		<c:if test="${ param['q.type'] == 'myEval' }">
			<list:data-column col="evalTime" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.tmpl.eval')}">
				<%
					Object basedocObj = pageContext.getAttribute("item");
					if(basedocObj != null) {
						KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc) basedocObj;
						out.print(KmsKnowledgeUtil.getMyDocEvalTime(basedoc.getFdId()));
					}
				%>
			</list:data-column>
			<list:data-column col="docScoreStar" escape="false">
				${item.docScore}
			</list:data-column>
			<list:data-column col="docScoreStarHtml" escape="false">
				<c:import url="/kms/knowledge/import/kms_knowledge_star_eval.jsp">
					<c:param name="starScore"  value="${item.docScore}" ></c:param>
					<c:param name="starBoxStyle"  value="width: 160px;top: 7px;" ></c:param>
					<c:param name="starTextSize"  value="12" ></c:param>
					<c:param name="starNumSize"  value="12" ></c:param>
					<c:param name="starTextColor"  value="#373737" ></c:param>
					<c:param name="starNumColor"  value="#373737" ></c:param>
				</c:import>
			</list:data-column>
		</c:if>
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
