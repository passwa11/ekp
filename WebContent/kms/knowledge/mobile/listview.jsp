<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<mui:cache-file name="mui-nav.js" cacheType="md5" />

<script>
    require(["dojo/store/Memory"],function(Memory) {
        window.nav = new Memory({
            data : [
                {
                    url : '/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=viewData',
                    text :  "${lfn:message('kms-knowledge:kms.knowledge.4m.index')}",
                    headerTemplate : '/kms/knowledge/mobile/js/headTemplate/index/headerTemplate.js',
                    // 设置了默认之后这里可以省略
                    listTemplate : '/kms/knowledge/mobile/js/headTemplate/index/listTemplate.js'
                },
                {
                    url : '/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&dataType=pic&orderby=docPublishTime&ordertype=down',
                    text : "${lfn:message('kms-knowledge:kms.knowledge.4m.all')}",
                    headerTemplate : '/kms/knowledge/mobile/js/headTemplate/all/headerTemplate.js',
                    listTemplate : '/kms/knowledge/mobile/js/headTemplate/all/listTemplate.js'
                },
                {
                    url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&dataType=pic&orderby=docPublishTime&ordertype=down&q.docIsIntroduced=1',
                    text: "${lfn:message('kms-knowledge:kms.knowledge.4m.introduce')}",
                    headerTemplate : '/kms/knowledge/mobile/js/headTemplate/introduce/headerTemplate.js',
                    listTemplate : '/kms/knowledge/mobile/js/headTemplate/introduce/listTemplate.js'
                },
                {
                    url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=readLogdata&orderby=fdReadTime&ordertype=down',
                    text:"${lfn:message('kms-knowledge:list.readlog')}",
                    headerTemplate: '/kms/knowledge/mobile/js/headTemplate/readLog/headerTemplate.js',
                    listTemplate : '/kms/knowledge/mobile/js/headTemplate/readLog/listTemplate.js'
                }
            ]
        });
    });
</script>

<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
    <div data-dojo-type="mui/nav/MobileCfgNavBar"
         data-dojo-props="store:nav">
    </div>
    <%--
    <div data-dojo-type="mui/nav/MobileCfgNavBar"
		data-dojo-props="modelName:'com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc'">
	</div>
    --%>

	<div data-dojo-type="mui/search/SearchButtonBar"
        data-dojo-props="modelName:'com.landray.kmss.kms.wiki.model.KmsWikiMain;com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge'" >
    </div>
</div>

<div data-dojo-type="mui/header/NavHeader">
</div>

<div data-dojo-type="mui/list/NavView" id="kms-knowledge-view-container">
       <ul data-dojo-type="mui/list/HashJsonStoreList"
           data-dojo-mixins="kms/knowledge/mobile/js/headTemplate/index/KmsKnowledgeIndexListMixin,mui/list/ComplexLItemListMixin">
       </ul>
</div>