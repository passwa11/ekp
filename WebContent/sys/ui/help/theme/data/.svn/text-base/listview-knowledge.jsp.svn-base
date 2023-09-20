<%@ page language="java" pageEncoding="UTF-8" contentType="application/json; charset=UTF-8"%>
<%
	int rowsize = request.getParameter("rowsize")==null?16:Integer.valueOf(request.getParameter("rowsize"));
	int pageno = request.getParameter("pageno")==null?1:Integer.valueOf(request.getParameter("pageno"));
	int total = request.getParameter("q.docStatus")==null?1783:0;
%>
{
    columns: [{
            property: "fdId"
        },
        {
            property: "fdKnowledgeType"
        },
        {
            property: "docAuthor.fdAuthorImageUrl"
        },
        {
            title: "作者",
            property: "docAuthorName"
        },
        {
            title: "作者",
            property: "docAuthor.fdName"
        },
        {
            property: "docAuthorId"
        },
        {
            title: "分类",
            property: "docCategory.fdName"
        },
        {
            title: "分类",
            property: "docCategoryName"
        },
        {
            title: "发布日期",
            property: "docPublishTime"
        },
        {
            title: "置顶时间",
            property: "fdSetTopTime"
        },
        {
            title: "精华文档",
            property: "icon"
        },
        {
            property: "docIsIntroduced"
        },
        {
            title: "文档标题",
            property: "docSubject"
        },
        {
            title: "内容摘要",
            property: "fdDescription"
        },
        {
            title: "内容摘要",
            property: "fdDescription1"
        },
        {
            title: "推荐",
            property: "docIntrCount"
        },
        {
            title: "点评",
            property: "docEvalCount"
        },
        {
            title: "浏览",
            property: "docReadCount"
        },
        {
            title: "浏览",
            property: "fdTotalCount"
        },
        {
            title: "imageLink",
            property: "fdImageUrl"
        },
        {
            title: "评分",
            property: "docScore"
        },
        {
            title: "状态",
            property: "docStatus"
        },
        {
            title: "所属模板",
            property: "docCategoryId"
        },
        {
            props: ""
        }
    ],
    "datas": [
<% if(total>0) 
		for(int i=0; i<rowsize/2; i++) {
			if(i>0) out.write(",");%>
        [
            {
                col: "fdId",
                value: "16cbcbab019d6f75788c5af41b880edb"
            }, {
                col: "fdKnowledgeType",
                value: "1"
            }, {
                col: "docAuthor.fdAuthorImageUrl",
                value: "/sys/person/image.jsp?personId=1708492377949409d66096d4db38c71b&amp;size=m"
            }, {
                col: "docAuthorName",
                value: "郑颖玉"
            }, {
                col: "docAuthor.fdName",
                value: '<a class="com_author" href="javascript: void(0)">冯友友</a>'
            }, {
                col: "docAuthorId",
                value: "1708492377949409d66096d4db38c71b"
            }, {
                col: "docCategory.fdName",
                value: "English Warehouse"
            }, {
                col: "docCategoryName",
                value: "English Warehouse"
            }, {
                col: "docPublishTime",
                value: "2019-08-23"
            }, {
                col: "fdSetTopTime",
                value: "2019-08-23"
            }, {
                col: "icon",
                value: ""
            }, {
                col: "docIsIntroduced"
            }, {
                col: "docSubject",
                value: "协同协作Word插件配置"
            }, {
                col: "fdDescription"
            }, {
                col: "fdDescription1",
                value: ""
            }, {
                col: "docIntrCount",
                value: '<span class="com_number " title="0">0</span>'
            }, {
                col: "docEvalCount",
                value: '<span class="com_number" title="0">0</span>'
            }, {
                col: "docReadCount",
                value: '<span class="com_number " title="22 ">22</span>'
            }, {
                col: "fdTotalCount",
                value: '<span class="com_number " title="22 ">22</span>'
            }, {
                col: "fdImageUrl",
                value: "/resource/style/default/attachment/default.png"
            }, {
                col: "docScore",
                value: '<span class="com_number ">0.0</span>'
            }, {
                col: "docStatus",
                value: "发布"
            }, {
                col: "docCategoryId",
                value: "16b9bb71038524ef376696447adad081"
            }
        ],
        [
            {
                col: "fdId",
                value: "16cbcbab019d6f75788c5af41b880edb"
            }, {
                col: "fdKnowledgeType",
                value: "1"
            }, {
                col: "docAuthor.fdAuthorImageUrl",
                value: "/sys/person/image.jsp?personId=1708492377949409d66096d4db38c71b&amp;size=m"
            }, {
                col: "docAuthorName",
                value: "郑颖玉"
            }, {
                col: "docAuthor.fdName",
                value: '<a class="com_author" href="javascript: void(0)">冯友友</a>'
            }, {
                col: "docAuthorId",
                value: "1708492377949409d66096d4db38c71b"
            }, {
                col: "docCategory.fdName",
                value: "English Warehouse"
            }, {
                col: "docCategoryName",
                value: "English Warehouse"
            }, {
                col: "docPublishTime",
                value: "2019-08-23"
            }, {
                col: "fdSetTopTime",
                value: "2019-08-23"
            }, {
                col: "icon",
                value: ""
            }, {
                col: "docIsIntroduced"
            }, {
                col: "docSubject",
                value: "年度调薪方案"
            }, {
                col: "fdDescription"
            }, {
                col: "fdDescription1",
                value: ""
            }, {
                col: "docIntrCount",
                value: '<span class="com_number " title="0">0</span>'
            }, {
                col: "docEvalCount",
                value: '<span class="com_number" title="0">0</span>'
            }, {
                col: "docReadCount",
                value: '<span class="com_number " title="22 ">22</span>'
            }, {
                col: "fdTotalCount",
                value: '<span class="com_number " title="22 ">22</span>'
            }, {
                col: "fdImageUrl",
                value: "/resource/style/default/attachment/default.png"
            }, {
                col: "docScore",
                value: '<span class="com_number ">0.0</span>'
            }, {
                col: "docStatus",
                value: "发布"
            }, {
                col: "docCategoryId",
                value: "16b9bb71038524ef376696447adad081"
            }
        ]
<% } %>
    ],
    "page": {
        "currentPage": <%= pageno %>,
        "pageSize": <%= rowsize %>,
        "totalSize": <%= total %>
    }
}