<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
    <template:replace name="title">${lfn:message('kms-knowledge:module.kms.knowledge') }</template:replace>
    <template:replace name="head">
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/loading.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_layout.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_operation.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_my.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_new_read.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_my_collect.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_my_sub.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_new.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_intro.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_rank.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_contribute.css?s_cache=${LUI_Cache}"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/style/index_tag.css?s_cache=${LUI_Cache}"/>
    </template:replace>
    <%-- 右边栏 --%>
    <template:replace name="body">
        <div id="kms_knowledge_index_body">
            <!-- 工作台、我的统计 -->
            <div class="index_top">
                <div class="index_operation">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_operation.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
                <div class="index_my">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_my.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
            </div>
            <!-- 最近阅读 -->
            <div class="index_new_read">
                <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_new_read.jsp" charEncoding="UTF-8">
                </c:import>
            </div>
            <!-- 我的收藏、我的订阅 -->
            <div class="index_feed">
                <div class="index_my_collect">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_my_collect.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
                <div class="index_my_sub">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_my_sub.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
            </div>
            <!-- 最新知识、精华知识 -->
            <div class="index_middle">
                <div class="index_new">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_new.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
                <div class="index_intro">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_intro.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
            </div>
            <!-- 知识排行、知识贡献排行、热门标签 -->
            <div class="index_bottom">
                <div class="index_rank">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_rank.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
                <div class="index_contribute">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_contribute.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
                <div class="index_tag">
                    <c:import url="/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_tag.jsp" charEncoding="UTF-8">
                    </c:import>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            Com_IncludeFile("util.js", "${LUI_ContextPath}/kms/knowledge/resource/js/", 'js', true);

            seajs.use(['kms/knowledge/kms_knowledge_ui/js/goToMoreView.js'], function (goToMoreView) {
                window.more = function (categoryId, type) {
                    goToMoreView.goToView(categoryId, 'kms/knowledge/', type, 'rowtable');
                }

                window.openDoc = function (url) {
                    Com_OpenWindow('${LUI_ContextPath}' + url);
                }

                window.openBaseDoc = function (fdId) {
                    Com_OpenWindow('${LUI_ContextPath}/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=' + fdId);
                }
            });

            window.getColVal = function (item, key) {
                var result = "";
                for (var i = 0; i < item.length; i++) {
                    if (item[i].col == key) {
                        result = item[i].value;
                        break;
                    }
                }
                return result;
            }

            window.getPersonLink = function (personId, personName, isOuter, outerName) {
                if (isOuter) {
                    return '<a class="com_outer_author" href="javascript:;" title="' + outerName + '">' + outerName + '</a>';
                }
                var personStr = '<ui:person personId="personId" personName="personName"></ui:person>';
                personStr = personStr.replace("personId", personId);
                personStr = personStr.replace("personName", personName);
                // console.log("personStr=>", personStr);
                return personStr;
            }
        </script>
    </template:replace>
</template:include>