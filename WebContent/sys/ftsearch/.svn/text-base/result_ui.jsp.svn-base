<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/sys/ftsearch/search_ui_js.jsp"%>
<title>${ lfn:message('sys-ftsearch-db:search.moduleName.all')}</title>
</head>
<body>
<%!
	//根据附件后缀名得到附件图标
	public String getIconNameByFileName(String fileName)
	{
		String imageName="documents.png";
		if(StringUtil.isNotNull(fileName))
		{
			String fileExt = fileName.substring(fileName.lastIndexOf("."));
			fileExt=fileExt.toLowerCase();
			if(".doc".equals(fileExt) || ".docx".equals(fileExt))
			{
				imageName = "word.png";
			}
			else if(".xls".equals(fileExt) || ".xlsx".equals(fileExt))
			{
				imageName = "excel.png";
			}
			else if(".ppt".equals(fileExt) || ".pptx".equals(fileExt))
			{
				imageName = "ppt.gif";
			}
			else if(".pdf".equals(fileExt))
			{
				imageName = "pdf.png";
			}
			else if(".txt".equals(fileExt))
			{
				imageName = "txt.gif";
			}
			else if(".jpg".equals(fileExt))
			{
				imageName = "image.png";
			}
			else if(".ico".equals(fileExt) || ".bmp".equals(fileExt) || ".gif".equals(fileExt) || ".png".equals(fileExt))
			{
				imageName = "image.png";
			}
			else if(".zip".equals(fileExt) || ".rar".equals(fileExt))
			{
				imageName = "zip.gif";
			}
			else if(".htm".equals(fileExt) || ".html".equals(fileExt))
			{
				imageName = "htm.gif";
			}
		}
		return imageName;
	}
%>
<% 
	Page queryPage = (Page)request.getAttribute("queryPage");
	List fieldList = (ArrayList)request.getAttribute("fieldList");
	String timeRange = request.getParameter("timeRange");
	request.setAttribute("timeRange",timeRange);
%>

<form id="sysFtsearchReadLogForm" name="sysFtsearchReadLogForm"  action="<c:url value="/sys/ftsearch/expand/sys_ftsearch_read_log/sysFtsearchReadLog.do?method=save" />" method="post" target="_blank">
<input id="fdDocSubject" name="fdDocSubject" type="hidden">
<input id="fdModelName" name="fdModelName" type="hidden">
<input id="fdCategory" name="fdCategory" type="hidden">
<input id="fdUrl" name="fdUrl" type="hidden">
<input id="fdSearchWord" name="fdSearchWord" type="hidden">
<input id="fdHitPosition" name="fdHitPosition" type="hidden">
<input id="fdModelId" name="fdModelId" type="hidden">
</form>
	
	<%--范围个数--%>
<div id="inputData_id">
	<input type='hidden'  name ='entriesDesignCount'  value='<c:out value="${entriesDesignCount}"/>' />
	<input type="hidden" name="modelName" value='<c:out value="${param.modelName}"/>' />
	<input type="hidden" name="srcModelName" value='<c:out value="${param.modelName}"/>' />
	<input type="hidden" name="searchFields" value='<c:out value="${param.searchFields}" />' />
	<input type='hidden'  name ='entriesDesignCount'  value='<c:out value="${entriesDesignCount}" />' />
	<input type="hidden" name="multisSysModel" value=''/>
	<input type="hidden" name="modelGroup" value='<c:out value="${modelGroup}" />' />
	<input type="hidden" name="modelGroupChecked" value='<c:out value="${modelGroupChecked}" />' />
	<input type="hidden" id='category' name="category" value='<c:out value="${category}"/>'/>
	<input type="hidden" id='srcCategory' name ="srcCategory" value ='<c:out value="${treeCategory}"/>'/>
	<input type="hidden" id='srcQueryString' name ="srcQueryString" value ='<c:out value="${queryString}"/>'/>
	<input type="hidden" id='categoryFlag' name ="categoryFlag" value ='<c:out value="${categoryFlag}"/>'/>
</div>
<!-- 最外层DIV  -->
<div id="search_wrapper">

<!-- 页面上部 -->
<div class="search_main_wrapper">
    <div class="search_main_box clrfix" style="padding-top: 5px">
        <div class="search_main_logo"></div>
        <div class="search_main_content open">
            <p class="hots">
                <!-- 近期热门搜索 -->
                <span class="titleR"><span class="titleL">${lfn:message('sys-ftsearch-db:search.ftsearch.current.hot.search')}</span></span>
                <!-- 近期热门搜索的关键字 -->
                <c:if test="${hotwordList!=null}">
                    <c:forEach items="${hotwordList}" var="hotword"  varStatus="status">
                        <span>
                            <a href="javascript:void(0);" onclick="relatedSearchWord()" id="${hotword}">${hotword}</a>
                        </span>
                    </c:forEach>
                </c:if>
                <!-- 高级搜索展开按钮 -->
                <%-- 
                <!-- <a class="btn_fiter" onclick="advanced_search();">${lfn:message('sys-ftsearch-db:ftsearch.advanced.button')}<i id="btn_fiter-id"></i></a> -->
                --%>
            </p>
            <div class="search_bar">
            	<!-- 搜索输入框 -->
                <input id="q5" class="input_txt" type="text" name="queryString" value='<c:out value="${queryString}"/>' onkeydown="if (event.keyCode == 13 && this.value !='') refreshBrowser(CommitSearch(0));"/>
                <!-- 搜索按钮 -->
                <a style="height:33px;cursor:pointer;" class="btn_search_top" onclick="refreshBrowser(CommitSearch(0));" title="${lfn:message('sys-ftsearch-db:search.ftsearch.button.search')}">
                    <span>${lfn:message('sys-ftsearch-db:search.ftsearch.button.search')}</span>
                </a>
            </div>
            <div id="fiterContent-id" class="fiterContent" style="margin-top: 10px">
                <!-- 关键词的搜索关系 -->
                <p>
                	<%-- <span class="title">${lfn:message('sys-ftsearch-db:search.ftsearch.bond')}</span> --%>
                    <span class="rd_item">
                        <label style="cursor: pointer;" >
                        	<!-- 匹配任意一个 -->
                            <input id="bond_or_id" type="radio" value="or" name="bond" style="width: 20px"  onclick="searchResult(CommitSearch(0));"
                                <c:if test="${requestScope.bond == 'or'}"> checked="checked" </c:if> /> ${lfn:message('sys-ftsearch-db:search.ftsearch.bond.or')}
                        </label>
                    </span>
                    <span class="rd_item">
                        <label style="cursor: pointer;" >
                        	<!-- 全部匹配  -->
                            <input id="bond_and_id" type="radio" value="and" name="bond" style="width: 20px" onclick="searchResult(CommitSearch(0));"
                                <c:if test="${requestScope.bond == 'and'}"> checked="checked" </c:if> />${lfn:message('sys-ftsearch-db:search.ftsearch.bond.and')}
                        </label>
                    </span>
                </p>
                <!-- 不包括以下关键词 -->
                <p>
                    <span class="title">${lfn:message('sys-ftsearch-db:search.ftsearch.outKeyword')}</span>
                    <!-- 关键词输入 -->
                    <input id="out_keyword" class="input_txt" type="text" style="width: 300px" name="out_keyword" value='<c:out value="${requestScope.outKeyword}"/>' onkeydown="if (event.keyCode == 13) refreshBrowser(CommitSearch(0));"/>
                </p>
            </div>
        </div>
        <div style=" position: relative; width: 100%;">
            <!-- 页面返回按钮 -->
            <input type="button" class="btn_return" onclick="window.location.href='<c:out value="${KMSS_Parameter_ContextPath}"/>'" value="${lfn:message('sys-ftsearch-db:search.ftsearch.search.return')}" />
        </div>
    </div>
</div>
<!-- 页面上部结束 -->

<!-- 页面下部 -->
<div id="search_main" class="c">
<!-- 设置下部页面背景 -->
<c:if test="${queryPage==null || param.queryString==null}">
	<%-- <div class="search_con" style="height: 500px; background:url(styles/images/bg_split.gif) repeat-y; border-bottom:1px solid #efefef;"></div> --%>
	<div id="search_list_id" class="search_con" style="visibility: hidden;">
		<div class="search_main" style="min-height:500px; overflow:visible">
			<div class="search_con" style="height: 500px">
			</div>
		</div>
	</div>
</c:if>

<!-- 如果查询结果不为空显示查询结果 -->
<c:if test="${not empty queryPage && queryPage!=null}">
<% 
//如果查询结果数为0
if (queryPage.getTotalrows()==0)
{
%>
	<!-- 查询结果为空时显示的界面  -->
    <c:if test="${ not empty mapSet['queryString']}" >
        <div id="search_list_id" class="search_con" style="visibility: hidden;">
            <div class="search_main" style="min-height:500px; overflow:visible">
                <div class="search_con" style="height: 500px">
                    <c:if test="${checkWord!=null}">
                        <div style="color:#000; font:bold 14px Arial; line-height:15px;">${lfn:message('sys-ftsearch-db:search.ftsearch.mean')}
                            <a href="javascript:void(0);" onclick="relatedSearchWord()" id="${checkWord}" style="color: red; font:bold 14px Arial; line-height:15px;">
                                <c:out value="${checkWord}"/>
                            </a>
                        </div>
                    </c:if>
                    <ul class="search_none">
                        <li>
                            <h3>${lfn:message('sys-ftsearch-db:sysFtsearchDb.sorry')}
                                <span style="color: red"><c:out value="${param.queryString}"/></span>${lfn:message('sys-ftsearch-db:sysFtsearchDb.about')}
                            </h3>
                        </li>
                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.advice')}</li>
                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.checkWrong')}</li>
                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.deleteSome')}</li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>
    <!-- 查询结果为空时显示的界面 结束 -->
<% 
}
else  
{
//查询结果数大于0
%>
    <!-- 搜索返回结果 -->
    <div id="search_list_id" class="search_con" style="visibility: hidden;">
        <div class="search_main" style="min-height:500px; overflow:visible">
        	
        	<!-- 搜索结果总数、用时、以及排序规则 -->
            <div class="search_result c">
                <ul class="btn_box">
                    <li>
                        <a href="javascript:void(0);" onclick="searchResult(sortResult('time'));" 
                            <c:if test="${param.sortType==null || param.sortType!='time'}" >
                                class="btn_a" 
                            </c:if>
                            <c:if test="${param.sortType!=null && param.sortType=='time'}">
                                class="btn_a_selected"
                            </c:if>
                        title="">
                            <span><em>${lfn:message('sys-ftsearch-db:search.ftsearch.sort.by.time')}</em></span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="searchResult(sortResult('readCount'));"
                            <c:if test="${param.sortType==null || param.sortType!='readCount'}">
		                		class="btn_a"
		            		</c:if>
		                    <c:if test="${param.sortType!=null && param.sortType=='readCount'}">
		                        class="btn_a_selected"
		                    </c:if>
                        title="">
                            <span><em>${lfn:message('sys-ftsearch-db:search.ftsearch.sort.by.readCount')}</em></span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="searchResult(sortResult('score'));"
                            <c:if test="${param.sortType!=null && param.sortType!='score'}">
                        		class="btn_a"
                    		</c:if>
                            <c:if test="${param.sortType==null || param.sortType=='score'}">
                                class="btn_a_selected"
                            </c:if>
                        title="">
                            <span><em>${lfn:message('sys-ftsearch-db:search.ftsearch.sort.by.score')}</em></span>
                        </a>
                    </li>
                </ul>

                <p>
                    ${lfn:message('sys-ftsearch-db:search.ftsearch.probably')}
                    <span class="item_Result">&nbsp;<%=queryPage.getTotalrows()%>&nbsp;</span>
                    ${lfn:message('sys-ftsearch-db:search.ftsearch.itemResult')}
                </p>
                <p>
                    &nbsp;&nbsp;&nbsp;&nbsp;${lfn:message('sys-ftsearch-db:search.ftsearch.search.userTime')}
                    <span class="item_Result">&nbsp;${userTime}&nbsp;</span>
                    ${lfn:message('sys-ftsearch-db:search.ftsearch.search.minute')}
                </p>
            </div>
            <!-- 搜索结果总数、用时、以及排序规则  结束-->

            <!-- 搜索纠正：你是不是想找 -->
            <c:if test="${checkWord!=null}">
                <div style="color:#000; font:bold 14px Arial; line-height:15px;">${lfn:message('sys-ftsearch-db:search.ftsearch.mean')}
                    <a href="javascript:void(0);" onclick="searchWord('<c:out value="${checkWord}"/>')" style="color: red; font:bold 14px Arial; line-height:15px;">
                        <c:out value="${checkWord}"/>
                    </a>
                    <span>${lfn:message('sys-ftsearch-db:search.ftsearch.checkResult')}
                        <span style="color: red">${checkWord}</span>${lfn:message('sys-ftsearch-db:search.ftsearch.checkBeforeResult')}
                    </span>
                    <a href="javascript:void(0);" onclick="searchOldWord('<c:out value="${param.queryString}"/>')" style="color: red; font:bold 14px Arial; line-height:15px;">
                        <c:out value="${param.queryString}"/>
                    </a>
                </div>
            </c:if>
            <!-- 搜索纠正结束 -->

            <div class="search_list">
<%--如果查询有数据--%>
<%--标题 时间 创建者 所属模块--%>
<%
    //最外层for循环--循环输出搜索结果集
    for(int i=0;i<queryPage.getList().size();i++)
    {
        LksHit lksHit = (LksHit)queryPage.getList().get(i);
        Map lksFieldsMap = lksHit.getLksFieldsMap();
        LksField link = (LksField)lksFieldsMap.get("linkStr");
        LksField title = (LksField)lksFieldsMap.get("title");
        LksField subject = (LksField)lksFieldsMap.get("subject");
        LksField content = (LksField)lksFieldsMap.get("content");
        LksField fileName = (LksField)lksFieldsMap.get("fileName");
        LksField ekpDigest = (LksField)lksFieldsMap.get("ekpDigest");
        LksField juniorSummary = (LksField)lksFieldsMap.get("juniorSummary");
        LksField docKey = (LksField)lksFieldsMap.get("docKey");
        LksField mimeType = (LksField)lksFieldsMap.get("mimeType");
        //自定义表单内容
        LksField xmlContent = (LksField)lksFieldsMap.get("xmlcontent");
        LksField addField1 = (LksField)lksFieldsMap.get("addField1");//机构
        LksField addField2 = (LksField)lksFieldsMap.get("addField2");//电话
        LksField addField3 = (LksField)lksFieldsMap.get("addField3");//部门
        LksField addField4 = (LksField)lksFieldsMap.get("addField4");//手机
        LksField addField5 = (LksField)lksFieldsMap.get("addField5");//岗位
        LksField addField6 = (LksField)lksFieldsMap.get("addField6");//邮箱
        LksField addField7 = (LksField)lksFieldsMap.get("addField7");//个人资料
        LksField docReadCount = (LksField)lksFieldsMap.get("docReadCount");
        LksField kmsAskPostList = (LksField)lksFieldsMap.get("kmsAskPostList");
        LksField kmsAskPostListIDs = (LksField)lksFieldsMap.get("kmsAskPostListIDs");
        String existPersonName=lksHit.getExistPeronName();
        String docInfo="";
        if(docKey != null)
        {
            docInfo = docKey.getValue();
        }
        String linkArgu=null;
        String docId="";
        if(docInfo.lastIndexOf("_")>-1)
        {
            linkArgu=docInfo.substring(docInfo.lastIndexOf("_")+1);
            docId=docInfo.substring(docInfo.lastIndexOf("_")+1,docInfo.length());
        }
        LksField keyword = (LksField)lksFieldsMap.get("keyword");
        LksField modelName = (LksField)lksFieldsMap.get("modelName");
        LksField category= (LksField)lksFieldsMap.get("category");
        LksField creator = (LksField)lksFieldsMap.get("creator");
        LksField createTime = (LksField)lksFieldsMap.get("createTime");  
        LksField systemName = (LksField)lksFieldsMap.get("systemName");//系统名
        LksField fullText = (LksField)lksFieldsMap.get("fullText");
        String fdSystemName="";
        String linkUrl= "";
        String systemModelStr="";
        if(link != null)
        {
            linkUrl = link.getValue();
        }
        if(systemName!=null)
        {
            fdSystemName=systemName.getValue();
            systemModelStr=fdSystemName+"@";
        }
        String mainDocLink = linkUrl;
        if(fileName!=null && StringUtil.isNotNull(linkArgu))
        {
            linkUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + linkArgu;
        }
        request.setAttribute("mimeType",mimeType==null?"":mimeType.getValue()); 
        request.setAttribute("linkUrl",linkUrl); 
        request.setAttribute("mainDocLink",mainDocLink); 
        if(modelName != null)
        {
            Map<String,String> modelUrlMap = (Map<String,String>)request.getAttribute("modelUrlMap");
            String modelUrl = null;
            if(modelUrlMap !=null)
            {
                if(fdSystemName != null && modelUrlMap.containsKey(fdSystemName+"@"+modelName.getValue()))
                {
                    modelUrl =  modelUrlMap.get(fdSystemName+"@"+modelName.getValue());
                }
                else
                {
                    if(modelUrlMap.get(modelName.getValue())!=null)
                    {
                        if(modelUrlMap.get(modelName.getValue()).startsWith("/"))
                        {
                            modelUrl = request.getContextPath()+modelUrlMap.get(modelName.getValue());
                        }
                        else
                        {
                            modelUrl = request.getContextPath()+"/"+modelUrlMap.get(modelName.getValue());
                        }
                    }
                }
            }
            if(modelUrl!=null && !"".equals(modelUrl))
            {
                request.setAttribute("modelUrl",modelUrl);
            }
            else
            {
                request.setAttribute("modelUrl","#");
            }
            request.setAttribute("modelName",modelName.getValue());
            request.setAttribute("ResultModelName",ResultModelName.getModelName(modelName.getValue())); 
        }
        String fdDocSubject= "";
        String fdModelName= "";
        String fdCategory= "";
        String fdUrl= "";
        String fdSummary = "";
        String fdFileName = "";

        String addStrField1="";
        String addStrField2="";
        String addStrField3="";
        String addStrField4="";
        String addStrField5="";
        String addStrField6="";
        String addStrField7="";
        if(addField1!=null)
        {
            addStrField1=addField1.getValue();
        }
        if(addField2!=null)
        {
            addStrField2=addField2.getValue();
        }
            if(addField3!=null)
        {
            addStrField3=addField3.getValue();
        }
        if(addField4!=null)
        {
            addStrField4=addField4.getValue();
        }
        if(addField5!=null)
        {
            addStrField5=addField5.getValue();
        }
        if(addField6!=null)
        {
            addStrField6=addField6.getValue();
        }
        if(addField7!=null)
        {
            addStrField7=addField7.getValue();
        }
        if("com.landray.kmss.km.kmap.model.KmKmapMain".equals(modelName.getValue()))
        {
            //知识地图文本是乱码，使其不显示。
            content=null;
        }
        if(subject!=null) 
        {
            fdDocSubject = subject.getValue();
        } 
        else if(title!=null) 
        {
            fdDocSubject = title.getValue();
        }
        else if(fileName!=null) 
        {
            fdDocSubject = fileName.getValue();
        }
        if(modelName!=null) 
        {
            fdModelName=modelName.getValue();
            systemModelStr+=fdModelName;
        }
        request.setAttribute("systemModelStr",systemModelStr);
        if(category!=null) 
        {
            fdCategory=category.getValue();
        }
        if(link!=null) 
        {
            fdUrl=linkUrl;
        }

        if(fileName!=null) 
        {
            fdFileName=fileName.getValue();
        }

        if(content!=null) 
        {
            fdSummary +=content.getValue();
        }
        if(xmlContent!=null)
        {
            fdSummary += xmlContent.getValue();
        }
        if(ekpDigest!=null) 
        {
            fdSummary +=ekpDigest.getValue();
        }
        if(fullText!=null)
        {
            fdSummary +=fullText.getValue();
        }
        if(!"true".equals(existPersonName))
        {
            if(addStrField1.contains("<font"))
            {
                fdSummary+=addStrField1;
            }
            if(addStrField2.contains("<font"))
            {
                fdSummary+=addStrField2;
            }
            if(addStrField3.contains("<font"))
            {
                fdSummary+=addStrField3;
            }
            if(addStrField4.contains("<font"))
            {
                fdSummary+=addStrField4;
            }
            if(addStrField5.contains("<font"))
            {
                fdSummary+=addStrField5;
            }
            if(addStrField6.contains("<font"))
            {
                fdSummary+=addStrField6;
            }
            if(addStrField7.contains("<font"))
            {
                fdSummary+=addStrField7;
            }
        }
        //添加标签高亮
        StringBuilder stringBuilder = new StringBuilder();
        if(keyword!=null)
        {
            String keywordVlaue = keyword.getValue();
            if (keywordVlaue != null && keywordVlaue != "")
            {
                if (keywordVlaue.contains(" "))
                {
                    String[] keywords = keywordVlaue.split(" ");
                    for(String keywordStr : keywords)
                    {
                        if (keywordStr.contains("<font") || keywordStr.contains("<hn>"))
                        {
                            stringBuilder.append(keywordStr).append(" ");
                        }
                    }
                }else
                {
                    if (keywordVlaue.contains("<font") || keywordVlaue.contains("<hn>"))
                    {
                        stringBuilder.append(keywordVlaue);
                    }
                }
            }
        }
        request.setAttribute("keywordVlaue",stringBuilder.toString().trim());

        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(fdDocSubject); 
        fdDocSubject=m_html.replaceAll(""); //过滤html标签 
        m_html=p_html.matcher(fdCategory); 
        fdCategory=m_html.replaceAll("");

        m_html=p_html.matcher(fdModelName); 
        fdModelName=m_html.replaceAll("");

        m_html=p_html.matcher(fdUrl); 
        fdUrl=m_html.replaceAll("");

        m_html=p_html.matcher(mainDocLink); 
        mainDocLink=m_html.replaceAll("");
        m_html=p_html.matcher(fdSummary);
        String fdTempTitle = HtmlEscaper.escapeHTML2(fdDocSubject);
        String fdIconTitle = fdTempTitle;
%>
<%
        if("true".equals(existPersonName))
        {
%>
            <c:forEach items="${personSearchs}" var="personSearch" varStatus="vstatus">
                <c:if test="${(modelName == personSearch['module'])||(systemModelStr == personSearch['module'])}">
                    <div class="lui_search_personWrapper">
                        <div class="lui_search_personHeader">
                            <c:choose>
                                <c:when test="${personSearch['path']!=null&&fn:contains(personSearch['path'],'method')}"> 
                                    <a class="person_img"><img src="<c:out value="${ LUI_ContextPath }${personSearch['path']}"/>&fdId=<%=docId%>&size=b"></a>
                                </c:when>
                                <c:otherwise>
                                    <!--人头像扩展用 -->
                                    <a class="person_img"><img src=""></a>
                                </c:otherwise>
                            </c:choose>
                            <div class="person_infoWrapper">
                                <dl class="person_infoList">
                                    <dt>
                                        <a href="javascript:void(0)" id="'<%=fdTempTitle %>'" onclick="readDoc('<%=fdModelName %>','<%=fdCategory %>','<%=fdUrl %>','<%=fdSystemName %>','<%=i %>');" class="name"  >
                                            <%=subject!=null?subject.getValue():title!=null?title.getValue():fileName!=null?fileName.getValue():""%>
                                        </a>
                                    </dt>
                                    <c:if test="${personSearch['addFieldName1']!=null}"> 
                                        <dd>
                                            <em><c:out value="${personSearch['addFieldName1']}"/>：</em>
                                            <span><%=addStrField1 %></span>
                                        </dd>
                                    </c:if>
                                    <c:if test="${personSearch['addFieldName2']!=null}"> 
                                        <dd>
                                            <em><c:out value="${personSearch['addFieldName2']}"/>：</em>
                                            <span><%=addStrField2 %></span>
                                        </dd>
                                    </c:if>
                                    <c:if test="${personSearch['addFieldName3']!=null}"> 
                                        <dd>
                                            <em><c:out value="${personSearch['addFieldName3']}"/>：</em>
                                            <span><%=addStrField3 %></span>
                                        </dd>
                                    </c:if>
                                    <c:if test="${personSearch['addFieldName4']!=null}"> 
                                        <dd>
                                            <em><c:out value="${personSearch['addFieldName4']}"/>：</em>
                                            <span><%=addStrField4 %></span>
                                        </dd>
                                    </c:if>
                                    <c:if test="${personSearch['addFieldName5']!=null}"> 
                                        <dd>
                                            <em><c:out value="${personSearch['addFieldName5']}"/>：</em>
                                            <span><%=addStrField5 %></span>
                                        </dd>
                                    </c:if>
                                    <c:if test="${personSearch['addFieldName6']!=null}"> 
                                        <dd>
                                            <em><c:out value="${personSearch['addFieldName6']}"/>：</em>
                                            <span><%=addStrField6 %></span>
                                        </dd>
                                    </c:if>
                                    <c:if test="${personSearch['addFieldName7']!=null}"> 
                                        <dd class="personal_data">
                                            <em><c:out value="${personSearch['addFieldName7']}"/>：</em>
                                            <span><%=addStrField7 %></span>
                                        </dd>
                                    </c:if>
                                </dl>
                            </div>
                        </div>
                        <div class="lui_search_personInfo">
                            <em>${lfn:message('sys-ftsearch-db:search.search.modelNames')}
                                <a href='<c:out value="${modelUrl}"/>' onclick =
"<%
            if("#".equals(request.getAttribute("modelUrl")))
            {
                out.print("return false;");
            }
            else
            {
                out.print("return true;");
            }
%>"
                                    target="_blank" class="module">
                                       <c:out value="${ResultModelName}"/>
                                </a>
                            </em>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
<%
        }
        else
        { 
%>
            <dl class="dl_c">
                <dt>
<%
            if(fileName!=null)
            {
%>
                <img src="<%="styles/images/"+ getIconNameByFileName(fdIconTitle) %>" height="18" width="18" border="0" />
<%
            }
%>
                <a href="javascript:void(0)" id="'<%=fdTempTitle %>'" onclick="readDoc('<%=fdModelName %>','<%=fdCategory %>','<%=fdUrl %>','<%=fdSystemName %>','<%=i %>');" class="a_title"  >
                    <%=subject!=null?subject.getValue():title!=null?title.getValue():fileName!=null?fileName.getValue():""%>
                </a>
<%
            if(fileName!=null && link!=null)
            {
%>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0);" id="'<%=fdTempTitle %>'" onclick="readDoc('<%=fdModelName %>','<%=fdCategory %>','<%=mainDocLink %>','<%=fdSystemName %>','<%=i %>');" class="a_view">
                    ${lfn:message('sys-ftsearch-db:search.ftsearch.viewMainDoc')}
                </a>
<%
            }
%>
                </dt>
                <dd>
                    <div id="summary_<%=i%>" style="word-break:break-all;">
                        <%=fdSummary%>
                    </div>
                </dd>
<%
// 知识问答显示 答案
if("com.landray.kmss.kms.ask.model.KmsAskTopic".equals(modelName.getValue()) && kmsAskPostListIDs !=null && kmsAskPostList !=null)
{
	String[] idArray = kmsAskPostListIDs.getValue().split("\u001a");
	String[] askPosArray = kmsAskPostList.getValue().split("\u001a");
	
	%>
	<%-- <%=content.getValue() +"<br>" %> --%>
		<table border="0" style="margin-left:15px">
	<%
	for(int j = 0; j < askPosArray.length; j++)
	{
		if(j >= 3)
		{
			break;
		} 
		String id = "-1";
		String askPos = askPosArray[j];
		if(idArray.length > j)
		{
			id = idArray[j];
		}
		if(StringUtil.isNull(askPos.trim()))
		{
			continue;
		}
	%>
	<tr>
		<td width="40px" align="left" valign="top" style="color: #666;">${lfn:message('sys-ftsearch-db:search.search.Answer')} </td> 
		<td align="left" valign="top">
		<a class="askPos" href="javascript:void(0)"  onclick="readDoc('<%=fdModelName %>','<%=fdCategory %>','<%=fdUrl + "&answerId=" + id %>','<%=fdSystemName %>','<%=i %>');">
			<%=askPos%>  <%-- <%="----" + id%> --%>
		</a>
		</td>
	</tr>
	<%
	} 
	%>
		</table>
	<%
}
%>
                <dd class="dd2">
                    ${lfn:message('sys-ftsearch-db:search.search.modelNames')}
                    <a href='${modelUrl}' onclick = 
"<%
            if("#".equals(request.getAttribute("modelUrl")))
            {
                out.print("return false;");
            }
            else
            {
                out.print("return true;");
            }
%>"
                        target="_blank">
                            <c:out value="${ResultModelName}"/>
                    </a>
                    <span>|</span>${lfn:message('sys-ftsearch-db:search.search.creators')}
<%
            if(creator!=null && request.getAttribute("escaperStr").equals(creator.getValue()))
            {
                out.println("<strong class = 'lksHit'>"+creator.getValue()+"</strong>");
            }
            else if(creator!=null)
            { 
                out.println(creator.getValue());
            }
%>
                    <span>|</span>${lfn:message('sys-ftsearch-db:search.search.createDates')}
<%
            if(createTime!=null)
            {
                out.println(createTime.getValue());
            }

            if(docReadCount!=null)
            {
%>
				<span>|</span>${lfn:message('sys-ftsearch-db:search.search.docReadCount')}
<%
                out.println(docReadCount.getValue());
            }
%>
     
                    <c:if test="${keywordVlaue !='' && keywordVlaue != null}">
                        <span>|</span>${lfn:message('sys-ftsearch-db:search.search.tags')}${keywordVlaue}
                    </c:if>
                </dd>
            </dl>
<%
        }
    }
    //最外层for循环结束--循环输出搜索结果集
%>
                <!-- 搜索结果分页栏 -->
                <div id="Q123456789">
                	<list:paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize}"  totalSize="${queryPage.totalrows}"></list:paging>
                </div>
            </div>
        </div>
        
        <!-- 底部搜索框 -->
        <div class="search_bottom" style="padding-left:105px; width:735px;height:33px">
        	<!-- 底部搜索框 输入关键字文本框 -->
            <input type="text" class="input_search" name="queryString" value='<c:out value="${queryString}"/>' id="q6" onkeydown="if (event.keyCode == 13 && this.value !='') { refreshBrowser(CommitSearch(1)); $('#q5').focus();}" />
            <!-- 底部搜索框 搜索按钮 -->
            <a style="height:33px;cursor:pointer;" class="btn_search" onclick="refreshBrowser(CommitSearch(1));$('#q5').focus();" title="<bean:message key="search.ftsearch.button.search" bundle="sys-ftsearch-db"/> ">
                <span>${lfn:message('sys-ftsearch-db:search.ftsearch.button.search')}</span>
            </a>
            <!-- 底部搜索框 在结果中搜索按钮 -->
            <a style="cursor:pointer;" href="javascript:void(0);" class="btn_b" title="${lfn:message('sys-ftsearch-db:search.ftsearch.search.on.result')}" onclick="search_on_result();">
                <span><em>${lfn:message('sys-ftsearch-db:search.ftsearch.search.on.result')}</em></span>
            </a>
        </div>
    </div>
    <!-- 搜索返回结果 结束 -->
<%
}
//查询结果数大于0 结束
%>
</c:if>
<!-- 如果查询结果不为空显示查询结果 结束 -->


<!-- 页面左部 -->
<div class="search_left" style="visibility: hidden;" id="search_left_id">
    <div class="search_left_content">
    	<!-- 类别 -->
        <dl class="dl_b" id="ftsearch_facet">
            <dt>${lfn:message('sys-ftsearch-db:search.ftsearch.search.catorgy')}</dt>
            <ul id="categoryTree" class="ztree" style='overflow:auto;min-height:0%;max-height:500px;'></ul>
        </dl>
        <!-- 搜索域 -->
        <dl class="dl_b" id="search_by_field">
            <dt>${ lfn:message('sys-ftsearch-db:search.ftsearch.search.fields')}</dt>
            <!-- 标签  -->
            <dd>
                <label for="type_tag" style="cursor: pointer;">
                    <input id='type_tag' type="checkbox" name="search_field"  onclick="searchResult(CommitSearch(0));isSelectAllField();"
<% 
if(fieldList==null || fieldList.contains("tag")) 
{ 
%>
    checked
<%
} 
%>
                        >${lfn:message('sys-ftsearch-db:search.ftsearch.field.tag')}
                </label>
            </dd>
            <!-- 标题  -->
            <dd>
                <label for="type_title" style="cursor: pointer;">
                    <input id='type_title' type="checkbox" name="search_field" onclick="searchResult(CommitSearch(0));isSelectAllField();"
<%
if(fieldList==null || fieldList.contains("title")) 
{ 
%>
    checked
<%
} 
%>
                        >${lfn:message('sys-ftsearch-db:search.ftsearch.field.title')}
                </label>
            </dd>
            <!-- 正文  -->
            <dd>
                <label for="type_content" style="cursor: pointer;">
                    <input id='type_content' type="checkbox" name="search_field" onclick="searchResult(CommitSearch(0));isSelectAllField();"
<%
if(fieldList==null || fieldList.contains("content"))
{ 
%>
    checked
<%
} 
%>
                        >${lfn:message('sys-ftsearch-db:search.ftsearch.field.content')}
                </label>
            </dd>
            <!-- 附件  -->
            <dd>
                <label for="type_attachment" style="cursor: pointer;"> 
                    <input id='type_attachment' type="checkbox" name="search_field" onclick="selectAttachment();searchResult(CommitSearch(0));isSelectAllField();"
<% 
//if(fieldList!=null && fieldList.contains("attachment")) 
if(fieldList==null || fieldList.contains("attachment")) 
{ 
%>
    checked
<% 
} 
%>
                        >${lfn:message('sys-ftsearch-db:search.ftsearch.field.attachment')}
                </label>
                <script type="text/javascript" src="js/jquery.multiple.select.js"></script>
                <select id="doc_file_type" name="doc_file_type" style="width: 140px;height: 15px" multiple="multiple" >
                    <option value="">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.all')}</option>
                    <option value="pdf">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.pdf')}</option>
                    <option value="doc;docx">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.doc')}</option>
                    <option value="xls;xlsx">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.xls')}</option>
                    <option value="ppt;pptx">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.ppt')}</option>
                    <option value="txt">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.txt')}</option>
                    <option value="exe">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.exe')}</option>
                </select>
                <script>
                    $("#doc_file_type").multipleSelect({
                        placeholder: "${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType')}",
                        selectAll : false,
                        selectAllText : "${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.all')}",
                        allSelected : "${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.all')}",
                        keepOpen : false,
                        minimumCountSelected : 2,
                        countSelected : "已选择#项",
                        onClick: function(view) {
                            if( (view.value==null || view.value=="") && view.checked ){
                                //选择 所有格式 时清除其他选项
                                $("#doc_file_type").multipleSelect("setSelects", [""]);
                            } else if(view.checked) {
                                // 未点击 所有格式 ，但选择了其他类型 清除 所有格式
                                var selects=$("#doc_file_type").multipleSelect("getSelects");
                                selects.push(view.value);
                                selects.splice($.inArray("",selects),1);
                                $("#doc_file_type").multipleSelect("setSelects", selects);
                            }

                            if( (view.value==null || view.value=="") && !view.checked ){
                                //不允许取消 所有格式 
                                $("#doc_file_type").multipleSelect("setSelects", [""]);
                            }

                            if(view.value!="" && !view.checked){
                                var selects=$("#doc_file_type").multipleSelect("getSelects");
                                if(selects.length <= 0){
                                    $("#doc_file_type").multipleSelect("setSelects", [""]);
                                }
                            }

                            searchResult(CommitSearch(0));
                        }
                    });

                    var docFileType = '<c:out value="${requestScope.docFileType}"/>';
                    var allTypes=new Array("pdf","doc;docx","xls;xlsx","ppt;pptx","txt","exe");
                    var selectTypes=new Array();
                    var count=0;
                    $.each(allTypes, function(i,type){      
                        if(docFileType.indexOf(type) >= 0 )  
                        {
                            selectTypes[count] = type;
                            count++;
                        }
                    });
                    if(docFileType!=""){
                        $("#doc_file_type").multipleSelect("setSelects", selectTypes);
                    } else {
                        $("#doc_file_type").multipleSelect("setSelects", [""]);
                    }

                    selectAttachment();
                    // 隐藏文件类型下拉列表
                    var docFileTypeExists = false;
                    docFileTypeExists = <c:out value="${requestScope.docFileTypeExists}"/>;
                    if(docFileTypeExists == false){
                    	$(".ms-parent").hide();
                    }
                </script>
            </dd>
            <!-- 创建者  -->
            <dd>
                <label for="type_creator" style="cursor: pointer;">
                    <input id='type_creator' type="checkbox" name="search_field" onclick="searchResult(CommitSearch(0));isSelectAllField();"
<%
if(fieldList==null || fieldList.contains("creator")) 
{ 
%>
    checked
<% 
} 
%>
                        >${lfn:message('sys-ftsearch-db:search.ftsearch.field.creator')}
                </label>
            </dd>
            <!-- 全选  -->
            <dd>
				<label for="type_allselect" style="cursor: pointer;">
				    <input id='type_allselect' type="checkbox" name="all_field_select" onclick="selectSearchField();selectAttachment();searchResult(CommitSearch(0));" 
<% 
if(fieldList==null || fieldList.size() ==5) 
{ 
%>
    checked
<% 
} 
%> 
				        >${lfn:message('sys-ftsearch-db:search.ftsearch.field.allSelect')}
				</label>
            </dd>
        </dl>
        <!-- 按时间 -->
        <dl class="dl_b" id="timeRangeSelect_id">
            <dt><bean:message key="search.ftsearch.time.range" bundle="sys-ftsearch-db" /></dt>
            <dd><a href="javascript:void(0);" onclick="searchByTime('day')" id="day" 
            	<c:if test="${timeRange=='day'}">style="color: #FF0000"</c:if> >
            	${lfn:message('sys-ftsearch-db:search.ftsearch.time.day')}</a></dd>
            	
            <dd><a href="javascript:void(0);" onclick="searchByTime('week')" id="week"
            	<c:if test="${timeRange=='week'}">style="color: #FF0000"</c:if> >
            	${lfn:message('sys-ftsearch-db:search.ftsearch.time.week')}</a></dd>
            	
            <dd><a href="javascript:void(0);" onclick="searchByTime('month')" id="month"
            	<c:if test="${timeRange=='month'}">style="color: #FF0000"</c:if> >
            	${lfn:message('sys-ftsearch-db:search.ftsearch.time.month')}</a></dd>
            
            <dd><a href="javascript:void(0);" onclick="searchByTime('year')" id="year"
            	<c:if test="${timeRange=='year'}">style="color: #FF0000"</c:if> >
           		${lfn:message('sys-ftsearch-db:search.ftsearch.time.year')}</a></dd>
            
            <dd><a href="javascript:void(0);" onclick="searchByTime('')" id="anyTime"
            	<c:if test="${timeRange=='' || timeRange==null}">style="color: #FF0000"</c:if> >
            	${lfn:message('sys-ftsearch-db:search.ftsearch.time.nolimit')}</a></dd>
        </dl>

        <!-- 相关搜索 -->
        <dl id="related_searches_id" class="dl_b">
            <dt>${lfn:message('sys-ftsearch-db:search.ftsearch.relate.word')}</dt>
            <c:if test="${relevantwordList!=null}">
                <c:forEach items="${relevantwordList}" var="relevantWord"  varStatus="status">
                    <dd style="word-break:break-all;">
                        <a  href="javascript:void(0);" onclick="relatedSearchWord()" id='<c:out value="${relevantWord}"/>'>
                            <c:out value="${relevantWord}"/>
                        </a>
                    </dd>	 				
                </c:forEach>
            </c:if>
        </dl>
    </div>
</div>
<!-- 页面左部 结束 -->

<div class="clear"></div>

<!-- 页面底部 -->
<div class="search_range" style="margin-left:180px">
	<!-- 搜索范围展开按钮 -->
    <p style=" width:120px;">
        <a style="cursor:pointer;" onclick="view_select_model();isSelectAllModel();" id="_strHref" title="">${lfn:message('sys-ftsearch-db:search.ftsearch.search.range')}</a>
    </p>
    <!-- 搜索范围 -->
    <ul id="hidden_div" class="ul1">
        <li id="selectLi" style="display:none" class="li_opt">
            <label for="selectAll_id" style="cursor: pointer;margin-left: 80px">
                <c:if test="${empty sysNameList || sysNameList==null}">
                    <input id="selectAll_id" type="checkbox" onclick="selectAllModel(this,'checkbox_model');refreshBrowser(CommitSearch(2),true);" name="" />
                    ${lfn:message('sys-ftsearch-db:search.ftsearch.select.all')}
                </c:if>
            </label>
            <%-- 
	            <a style="cursor:pointer;" class="btn_c" onclick="refreshBrowser(CommitSearch(2));">
	                <span><em>${lfn:message('sys-ftsearch-db:search.ftsearch.confirm')}</em></span>
	            </a>
             --%>
        </li>
        <!-- 黄伟强  2013-4-25 多浏览器兼容 -->
        <li class="li_range">
            <h3>
                <c:if test="${empty sysNameList || sysNameList==null}">
                    ${lfn:message('sys-ftsearch-db:search.ftsearch.search.range2')}
                </c:if>
                <c:if test="${not empty sysNameList && sysNameList!=null}">
                    <input type="checkbox" onclick="selectAllModel(this,'checkbox_model');" name="" /> EKP：
                </c:if>		
            </h3>
            <ul id="model_view" name="model_view" class="ul2">
                <c:forEach items="${entriesDesign}" var="element" varStatus="status">
                    <c:if test="${element['flag']==true}">
                        <li>
                            <label for="">
                                <c:out value="${element['title']}"/>
                            </label>
                        </li>
                    </c:if> 
                </c:forEach>
            </ul>
            <ul id="model_select" name="model_select" class="ul2" style="display:none">
                <c:forEach items="${entriesDesign}" var="element" varStatus="status">
                    <li>
                        <label for='element<c:out value="${status.index}"/>' style="cursor: pointer;">
                            <input id='element<c:out value="${status.index}"/>' type="checkbox" name="checkbox_model"
                                <c:if test="${element['flag']==true}">
                                    checked
                                </c:if> 
                                onclick="selectOutSystemModel('checkbox_model');refreshBrowser(CommitSearch(2),true);isSelectAllModel();"  value='<c:out value="${element['modelName']}"/>' ><c:out value="${element['title']}"/>
                        </label>
                    </li>
                </c:forEach>		
            </ul>
            <div class="clear"></div>
        </li>
        
        <c:forEach items="${otherSysDesign}" var="sysDesigns" varStatus="status">
            <li class="li_range c">
                <h3>
                    <c:forEach items="${sysNameList}" var="sysNames" varStatus="status2">
                        <c:if test="${status.index==status2.index}">
                            <input type="checkbox" onclick="selectAllModel(this,'<c:out value="${sysNames }"/>');" name="sysModel" />
                            ${sysNames }：
                            <input type="hidden" id="<c:out value="${sysNames}"/>_systemName" value="" name = 'systemName'>		
                        </c:if>
                    </c:forEach>
                </h3>
                <ul name="multiSysmodel_view" class="ul2">
                    <c:forEach items="${sysDesigns}" var="sysDesign" varStatus="status">
                        <c:if test="${sysDesign['flag']==true}">
                            <li>
                                <label for="">
                                    <c:out value="${sysDesign['title']}"/>
                                </label>
                            </li>
                        </c:if> 
                    </c:forEach>
                </ul>
                <ul name="multiSysmodel_select"  style="display:none" class="ul2" id="multiSysmodel_select_id">
                    <c:forEach items="${sysDesigns}" var="sysDesign" varStatus="status">
                        <li>
                            <label for="">
                                <input id="<c:out value="${sysDesign['system']}"/><c:out value="${status.index}"/>" onclick="selectOutSystemModel('<c:out value="${sysDesign['system']}"/>')" type="checkbox" name="<c:out value="${sysDesign['system'] }"/>"
                                    <c:if test="${sysDesign['flag']==true}">
                                        checked
                                    </c:if> 
                                    value='<c:out value="${sysDesign['modelName']}"/>' ><c:out value="${sysDesign['title']}"/>
                            </label>
                        </li>
                    </c:forEach>		
                </ul>
                <div class="clear"></div>
            </li>
        </c:forEach>
    </ul>
</div>
<!-- 页面底部 结束 -->

</div>
<!-- 页面下部结束 -->

</div>
<!-- 最外层DIV结束  -->

<div id="search_bottom">
	<%@ include file="/sys/portal/template/default/footer.jsp"%>
</div>
</body>
</html>







