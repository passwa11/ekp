<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  file="/sys/zone/zoneSearchTemplate.jsp">
  <template:replace name="title">
  			${lfn:message('sys-zone:sysZonePerson.zoneSearchTitle') }
  </template:replace>
  <template:replace name="content">
    <!--主体区域 Starts-->
    <div class="lui_zone_mbody_index">
        <!--搜索栏 Starts-->
        <html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do">
        <div class="lui_zone_searchwrap">
            <div class="lui_zone_searchtitle"></div>
            <div class="lui_zone_searchbox">
                <div class="inputbar_l">
                    <div class="inputbar_r">
                        <div class="inputbar_c">
                        <input  id="searchValue" name="searchValue" type="text" value="${lfn:message('sys-zone:sysZonePerson.searchInputHelp') }"/>
                        <input  id="fdTags" name="fdTags" type="hidden" value=""/>
                        <input name="method" type="hidden" value="toSearch"/>
                        </div>
                    </div>
                </div>
                <a class="lui_zone_btn_search_l" href="javascript:zoneSearch();">
                    <span class="lui_zone_btn_search_r">
                        <span class="lui_zone_btn_search_c">${lfn:message('button.search')}</span>
                    </span>
                </a>
            </div>
        </div>
        </html:form>
        <!--搜索栏 Ends-->
        <!--左侧区域 Starts-->
        <div class="lui_zone_mbody_index_l">
            <!--人员查找搜索内容 Starts-->
            <div class="lui_zone_search_content">
                <!--已选条件 Starts-->
                <div class="lui_zone_search_conditon" id="staffYpage_searchConditon" style="display: none;">
                    <div class="lui_zone_search_conditon_r">
                        <div class="lui_zone_search_conditon_c">
                            <ul class="lui_zone_search_cdt" id="searchTagsDiv">
                                <li class="title">${lfn:message('sys-zone:sysZonePerson.hasSelected') }</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!--已选条件 Ends-->
                <!--人员内容 Starts-->
             <list:listview>
				<ui:source type="AjaxJson">
					{"url":"/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=searchPerson&tagNames=" + encodeURI('${lfn:escapeJs(tagNames)}') + "&searchValue=" + encodeURI('${lfn:escapeJs(searchValue)}')}
				</ui:source>
			  <list:gridTable name="gridtable" columnNum="1" >
				<list:row-template >
					<%@ include file="/sys/zone/sys_zone_personInfo/sysZonePersonInfo_grid_content.jsp"%>
				</list:row-template>

			</list:gridTable>
				<ui:event  event="load" args="data">
						seajs.use('sys/fans/sys_fans_main/style/listView.css');

						var personIds = [];
						$("[data-fans-sign='sys_fans']").each(function() {
							var $this = $(this), personId = $this.attr("data-action-id");
							personIds.push(personId);
						});

						seajs.use(['sys/fans/resource/sys_fans'], function(follow) {
							follow.changeFansStatus(personIds,".fans_follow_btn");
							follow.bindButton(".lui_zone_btn_p")
						});

						//seajs.use(['sys/zone/resource/zone_follow'], function(follow){
						//	follow.bindButton(".lui_zone_btn_p")
						//});
						var datas = [];
						$("[data-person-role='contact']").each(function() {
							var $this = $(this), personParam = $this.attr("data-person-param");
							datas.push({
								elementId : $this.attr("id"),
								personId: Com_GetUrlParameter(personParam, "fdId"),
								personName:Com_GetUrlParameter(personParam, "fdName"),
								loginName :Com_GetUrlParameter(personParam, "fdLoginName"),
								email:Com_GetUrlParameter(personParam, "fdEmail"),
								mobileNo:Com_GetUrlParameter(personParam, "fdMobileNo"),
								isSelf : ("${KMSS_Parameter_CurrentUserId}" == Com_GetUrlParameter(personParam, "fdId"))
							});
						});
						onRender(datas);


				</ui:event>
			</list:listview>
			<list:paging></list:paging>
                <!--人员内容 Ends-->
            </div>
            <!--人员查找搜索内容 Ends-->
        </div>
        <!--左侧区域 Ends-->
        <!--右侧区域 Starts-->
        <div class="lui_zone_mbody_index_r">
            <!--人员标签查找 Starts-->
            <div class="lui_zone_tag_search" id="staffYpage_tagSearch">
          	   <h2><span>${lfn:message('sys-zone:sysZonePerson.personTagSelect') }</span></h2>
          	   <ui:dataview>
				  <ui:source type="AjaxJson">
					{"url":"/sys/tag/sys_tag_group/sysTagGroup.do?method=getTagGroupJsonByModelName&modelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo"}
				  </ui:source>
				  <ui:render type="Template">
				  		{$<div>$}
				  			if(data && data.length > 0) {
				  				for(var i = 0; i < data.length; i++) {
				  					{$
				  						  <dl>
				  						  <dt><span>{%env.fn.formatText(data[i].categoryName)%}</span></dt>
				  					 $}
				  					 		if(data[i].tags && data[i].tags.length > 0) {
				  					 			for(var j = 0; j < data[i].tags.length; j++) {
				  					 				var obj = data[i].tags[j];
				  					 				{$<dd><a href="#" onclick="tagSearch('{%env.fn.formatText(obj)%}',true)" title="{%env.fn.formatText(obj)%}">{%env.fn.formatText(obj)%}</a></dd>$}
				  					 			}
				  					 		}
				  					 {$
				  						  </dl>
				  					$}
				  				}
				  			} else {
				  				{$<span></span>$}
				  			}
				  		{$</div>$}
				  </ui:render>
			   </ui:dataview>
            </div>
            <!--人员标签查找 Ends-->
        </div>
        <!--右侧区域 Ends-->
    </div>
    <%@ include file="/sys/zone/sys_zone_personInfo/sysZonePersonContact_include.jsp"%>

    <!--主体区域 Ends-->
	<%@ include file="/sys/zone/zoneSearch_js.jsp" %>
	<script>
		seajs.use(["sys/fans/resource/sys_fans_num.js"]);
	</script>
</template:replace>
</template:include>