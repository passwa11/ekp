<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingAppMobile" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdOrder"  title="${lfn:message('sys-modeling-base:modelingAppMobile.fdOrder')}"/>
        <list:data-column col="docSubject"  title="${lfn:message('sys-modeling-base:modelingAppMobile.docSubject')}">
            <c:if test="${modelingAppMobile.docSubject == null}">
               默认首页
            </c:if>
            <c:if test="${modelingAppMobile.docSubject != null}">
                <c:out value="${modelingAppMobile.docSubject}" />
            </c:if>
        </list:data-column>
      <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:modelingAppMobile.docCreator')}" escape="false">
          <c:if test="${modelingAppMobile.docCreator == null}">
             管理员
          </c:if>
          <c:if test="${modelingAppMobile.docCreator != null}">
              <c:out value="${modelingAppMobile.docCreator.fdName}" />
          </c:if>
        </list:data-column>
        
        <list:data-column col="docCreator.id" escape="false">

            <c:out value="${modelingAppMobile.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:modelingAppMobile.docCreateTime')}">
            <kmss:showDate value="${modelingAppMobile.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIndex" title="${lfn:message('sys-modeling-base:modelingAppMobile.fdIndex.type')}">
            <c:if test="${modelingAppMobile.fdIndex eq 'custom'}">
                ${lfn:message('sys-modeling-base:modelingAppMobile.fdIndex.type.2')}
            </c:if>
            <c:if test="${modelingAppMobile.fdIndex ne 'custom'}">
                ${lfn:message('sys-modeling-base:modelingAppMobile.fdIndex.type.1')}
            </c:if>
        </list:data-column>
        <!-- 其它操作 -->
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
            <!--操作按钮 开始-->
            <div class="conf_show_more_w">
                <div class="conf_btn_edit">
                    <!-- 编辑 -->
                    <a class="btn_txt" href="javascript:toView('${modelingAppMobile.fdId}')">${lfn:message('button.edit')}</a>
                    <!-- 删除 -->
                    <a class="btn_txt" href="javascript:del('${modelingAppMobile.fdId}')">${lfn:message('button.delete')}</a>
                </div>
            </div>
            <!--操作按钮 结束-->
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
