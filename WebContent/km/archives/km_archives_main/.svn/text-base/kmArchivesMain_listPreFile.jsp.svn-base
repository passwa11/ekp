<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDictModel,com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@ page import="com.landray.kmss.km.archives.model.KmArchivesMain"%>
<%@ page import="com.landray.kmss.util.ResourceUtil,java.lang.String,com.landray.kmss.util.StringUtil"%>
<list:data>
    <list:data-columns var="kmArchivesMain" list="${queryPage.list}" varIndex="status">
    	<%
    	if(pageContext.getAttribute("kmArchivesMain")!=null){
    		KmArchivesMain kmArchivesMain = (KmArchivesMain)pageContext.getAttribute("kmArchivesMain");
    		String fdModelName = kmArchivesMain.getFdModelName();
    		String fdFromModule = "";
    		if(StringUtil.isNotNull(fdModelName)){
	    		SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
	    		fdFromModule = ResourceUtil.getString(dict.getMessageKey());
	    		request.setAttribute("fdFromModule", fdFromModule);
    		}
    	}
    	%>
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="docSubject" escape="false" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}">
        	<span class="com_subject"><c:out value="${kmArchivesMain.docSubject }"/></span>
        </list:data-column>
         <list:data-column property="docTemplate.fdName" title="${lfn:message('km-archives:kmArchivesMain.docTemplate')}" />
        <list:data-column property="docNumber" title="${lfn:message('km-archives:kmArchivesMain.docNumber')}" />
        <list:data-column col="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesMain.docCreator')}" escape="false">
            <c:out value="${kmArchivesMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.fdId" escape="false">
            <c:out value="${kmArchivesMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="fdFileDate" title="${lfn:message('km-archives:kmArchivesMain.fdFileDate')}">
            <kmss:showDate value="${kmArchivesMain.fdFileDate}" type="date"></kmss:showDate>
        </list:data-column>
          <list:data-column col="fdFromModule" title="${lfn:message('km-archives:kmArchivesMain.fdFromModule')}">
             <c:out value="${fdFromModule}" />
        </list:data-column>
        <!-- 创建时间 -->
        <list:data-column col="docCreateTime" title="${lfn:message('km-archives:kmArchivesMain.docCreateTime')}">
            <kmss:showDate value="${kmArchivesMain.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdValidityDate" title="${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}">
            <kmss:showDate value="${kmArchivesMain.fdValidityDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdLibrary" title="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}" escape="false">
            <c:out value="${kmArchivesMain.fdLibrary}" />
        </list:data-column>
        <list:data-column col="lbpm_main_listcolumn_node" title="${lfn:message('km-archives:lbpm.currentNode') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${kmArchivesMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('km-archives:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${kmArchivesMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
        <list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:confirmFile('${kmArchivesMain.fdId}')">${lfn:message('km-archives:button.decidedToArchive') }</a><br>
					<a class="btn_txt" href="javascript:changeCategory('${kmArchivesMain.fdId}')">${lfn:message('km-archives:button.changeCategory') }</a>
				</div>
			</div>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
