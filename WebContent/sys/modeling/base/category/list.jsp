<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingAppCategory" list="${queryPage.list }">
        <list:data-column property="fdId"/>
        <list:data-column property="fdOrder" title="${ lfn:message('model.fdOrder') }"> </list:data-column>
        <list:data-column property="fdName" title="${ lfn:message('sys-modeling-base:modeling.category.name') }"
                          style="text-align:left;min-width:180px">
        </list:data-column>
        <list:data-column headerClass="width80" property="docCreator.fdName"
                          title="${ lfn:message('model.fdCreator') }">
        </list:data-column>
        <list:data-column headerClass="width140" property="docCreateTime"
                          title="${ lfn:message('model.fdCreateTime') }">
        </list:data-column>
        <!-- 其它操作 -->
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }"
                          escape="false">
            <!--操作按钮 开始-->
            <div class="conf_show_more_w">
                <div class="conf_btn_edit">
                    <!-- 编辑 -->
					<kmss:auth requestURL="/sys/modeling/base/modelingAppCategory.do?method=edit&fdId=${modelingAppCategory.fdId}" requestMethod="GET">
                    <a class="btn_txt"
                       href="javascript:edit('${modelingAppCategory.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth> 
					<kmss:auth requestURL="/sys/modeling/base/modelingAppCategory.do?method=delete&fdId=${modelingAppCategory.fdId}" requestMethod="GET">
                    <a class="btn_txt"
                       href="javascript:deleteDoc('${modelingAppCategory.fdId}')">${lfn:message('button.delete')}</a>
				  	</kmss:auth> 
              </div>
            </div>
            <!--操作按钮 结束-->
        </list:data-column>
    </list:data-columns>
    <!-- 分页 -->
    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }"
                      totalSize="${queryPage.totalrows }"/>
</list:data>