<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
    Com_IncludeFile("iconfont.css",Com_Parameter.ContextPath +"sys/ui/extend/theme/default/style/","css",true);
    Com_IncludeFile("dataview.css",Com_Parameter.ContextPath +"sys/ui/extend/theme/default/style/","css",true);
</script>
<div class="lui_all_process">
    <div class="lui_all_process_header expand" id="allProcessHeader">
        <div class="lui_title_text">${lfn:message("sys-lbpmperson:ui.all.processes")}</div>
        <div class="lui_search_box">
            <i class="lui_search_btn"></i>
            <input class="lui_search_keyword" placeholder="${lfn:message("sys-lbpmperson:ui.search.placeholder.process")}" id="searchInput" type="text"/>
            <i class="lui_keyword_reset_btn hidden" id="searchRestBtn"></i>
        </div>
    </div>
    <div class="lui_all_process_content" id="allProcessContent">
        <div class="liu_content_treeview">
            <div id="treeView" data-lui-type="lui/treeview/treeView!TreeView">
                <script type="text/config">
                {
                    searchPlaceholder:'${lfn:message("sys-lbpmperson:ui.search.placeholder")}',
                    titleText:'${lfn:message("sys-lbpmperson:ui.all.processes")}',
                    initAllModules:true,
                    initAllModulesAjaxParam:{
                        url:"${LUI_ContextPath}/sys/lbpmperson/SysLbpmPersonCreate.do?method=listModulesOrCates",
                        data:{
                            authAdd:true,
                            ignoreOtherCate:true,
                            filter:true
                        }
                    },
                    ajaxParam:{
                        url:"${LUI_ContextPath}/sys/lbpmperson/SysLbpmPersonCreate.do?method=listModulesOrCates&filter=true"
                    }
                }
                </script>
            </div>
        </div>
        <div class="liu_content_list expand">
            <div>
                <div id="treeViewContent" data-lui-type="lui/treeview/content!Content">
                    <script type="text/config">
                    {
                        linkUrl :'${addUrl}',
                        titleText:'${lfn:message("sys-lbpmperson:ui.all.processes")}',
                        initAllModules:true,
                        expand:true,
                        showSummary:true,
                        loadLevel:2,
                        button:{
                            className:"lui_summary_item_btn_star",
                            click:window.usualCateAddOrDelToFavorite
                        },
                        ajaxParam:{
                            url:"${LUI_ContextPath}/sys/lbpmperson/SysLbpmPersonCreate.do?method=listTemps"
                        }
                    }
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    Com_IncludeFile("jquery.js");
    Com_IncludeFile("allProcess.js", "${LUI_ContextPath}/sys/lbpmperson/resource/js/", "js", true);
    Com_IncludeFile("allProcess.css", "${LUI_ContextPath}/sys/lbpmperson/resource/css/", "css", true);
</script>
