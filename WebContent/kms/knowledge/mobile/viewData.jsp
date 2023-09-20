<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<list:data>
    <list:data-columns var="item" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>

        <list:data-column col="allCount" escape="false">
            <div class="muiPortalCard muiPortalContent muiPortalClickItem" title="${lfn:message('kms-knowledge:kmsKnowledge.portlet.allCount.title')}">
                <div class="mui_ekp_portal_branch_title muiFontSizeXL clearfloat">
                    <span class="muiFontColorInfo">${lfn:message('kms-knowledge:kmsKnowledge.portlet.allCount.title')}</span>
                </div>

                <div class="mui_ekp_portal_tab_content">
                    <div data-dojo-type="kms/knowledge/mobile/mportal/index/js/AllCountItem"
                         data-dojo-props="type:''">
                    </div>
                </div>
            </div>
        </list:data-column>

        <list:data-column col="myCount" escape="false">
            <div class="muiPortalCard muiPortalContent muiPortalClickItem" title="${lfn:message('kms-knowledge:kmsKnowledge.portlet.myCount.title')}">
                <div class="mui_ekp_portal_branch_title muiFontSizeXL clearfloat">
                    <span class="muiFontColorInfo">${lfn:message('kms-knowledge:kmsKnowledge.portlet.myCount.title')}</span>
                </div>

                <div class="mui_ekp_portal_tab_content">
                    <div data-dojo-type="kms/knowledge/mobile/mportal/index/js/MyCountItem"
                         data-dojo-props="type:''">
                    </div>
                </div>
            </div>
        </list:data-column>

        <list:data-column col="rank" escape="false">
            <div class="muiPortalCard muiPortalContent muiPortalClickItem" title="${lfn:message('kms-knowledge:kmsKnowledge.portlet.rank.title')}">
                <div class="mui_ekp_portal_branch_title muiFontSizeXL clearfloat">
                    <span class="muiFontColorInfo">${lfn:message('kms-knowledge:kmsKnowledge.portlet.rank.title')}</span>
                    <%--
                    <a href="javascript:;" class="muiFontSizeS muiFontColorMuted">${lfn:message('kms-knowledge:operation.more')}
                        <i class="fontmuis muis-to-right muiFontSizeXS mui_ekp_portal_more_arrow"></i>
                    </a>
                    --%>
                </div>

                <div class="mui_ekp_portal_tab_content">
                    <div data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList"
                         data-dojo-mixins="sys/mportal/mobile/card/ComplexLImgTextListMixin"
                         data-dojo-props="url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&orderby=kmsKnowledgeBaseDoc.fdTotalCount&ordertype=down&rowsize=6&dataType=pic&q.docStatus=30',lazy:false,pic:true">
                    </div>
                </div>
            </div>
        </list:data-column>

    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
