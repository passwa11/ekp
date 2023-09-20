<%@ page import="com.landray.kmss.common.model.IBaseModel" %>
<%@ page import="org.apache.commons.beanutils.PropertyUtils" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDictModel" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.common.service.IBaseService" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">		
		<list:data-column col="fdId"  escape="false">
			${item[0].fdId}
		</list:data-column>
        <list:data-column col="fdDocStatus"  escape="false">
            ${item[0].docStatus}
        </list:data-column>
		<list:data-column  col="docSubject" title="标题" escape="false">
			${item[0].docSubject}
		</list:data-column>
		<list:data-column col="from" escape="false" title="来自">
			${fromJson[item[2]]}
		</list:data-column>
		<list:data-column col="status" escape="false" title="状态">
			<c:if test='${item[1] == "1" }'>
				${lfn:message('sys-follow:sysFollowRelatedDoc.fdStatus.yes')}
			</c:if>
			<c:if test='${item[1] == "0" }'>
				${lfn:message('sys-follow:sysFollowRelatedDoc.fdStatus.no')}
			</c:if>
		</list:data-column>
		<list:data-column col="docCreateTime" escape="false" title="订阅时间">
			<kmss:showDate value="${item[0].docCreateTime }" type="date"></kmss:showDate>
		</list:data-column>
		<list:data-column col="href" escape="false">
			/sys/follow/sys_follow_doc/sysFollowDoc.do?method=view&fdId=${item[0].fdId}
		</list:data-column>
		<list:data-column col="fdModelId"  escape="false">
			${item[0].fdModelId}
		</list:data-column>
		<list:data-column col="fdModelName"  escape="false">
			${item[0].fdModelName}
		</list:data-column>
		<list:data-column col="docAuthor">
			<%
				Object[] basedocObj = (Object[]) pageContext.getAttribute("item");
				if (basedocObj != null) {
					IBaseModel item = (IBaseModel) basedocObj[0];
					Object fdModelId = PropertyUtils.getProperty(item, "fdModelId");
					Object fdModelName = PropertyUtils.getProperty(item, "fdModelName");

					// 文档和维基需要输出作者
					if ("com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge".equals(fdModelName)
							|| "com.landray.kmss.kms.multidoc.model.KmsWikiMain".equals(fdModelName)) {
						// 找到文档作者
						SysDictModel dictModel = SysDataDict.getInstance().getModel("com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc");
						String serviceBean = dictModel.getServiceBean();
						IBaseService service = (IBaseService) SpringBeanUtil.getBean(serviceBean);
						IBaseModel basedoc = (IBaseModel) service.findByPrimaryKey(fdModelId.toString());

						List fdDocAuthorList = (List) PropertyUtils.getProperty(basedoc, "fdDocAuthorList");
						if (null != fdDocAuthorList && fdDocAuthorList.size() > 0) {
							Object author = fdDocAuthorList.get(fdDocAuthorList.size() - 1);
							Object org = PropertyUtils.getProperty(author, "fdSysOrgElement");
							out.print(PropertyUtils.getProperty(org, "fdName"));
						} else {
							out.print(PropertyUtils.getProperty(basedoc, "outerAuthor"));
						}
					}else{
						out.print("none");
					}
				}
			%>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>

