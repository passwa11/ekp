<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="com.landray.kmss.framework.util.PluginConfigLocationsUtil"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>
<%
    KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
    String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
    if ("true".equals(kmsCategoryEnabled)) {
%>
<c:set var="kmsCategoryEnabled" value="true"></c:set>
<%
    }
%>
<template:include ref="default.simple" sidebar="no" >
<template:replace name="body">
<script>
	seajs.use("kms/knowledge/kms_knowledge_portlet_ui/style/portlet.css");
</script>

<!-- 知识仓库 知识统计 Starts -->
<div class="lui_portlet_kn_statistics_wrap">
    <div class="lui_portlet_kn_statistics_head">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.totality')}</div>
    <div class="lui_portlet_kn_statistics_body">
        <!-- 数字区域 -->
        <div class="lui_portlet_kn_statistics_sum">
            <!-- <dl>
                <dd>5</dd>
                <dd>8</dd>
                <dd>0</dd>
                <dt>,</dt>
                <dd>0</dd>
                <dd>4</dd>
                <dd>0</dd>
                <dt>,</dt>
                <dd>9</dd>
                <dd>4</dd>
                <dd>2</dd>
            </dl> -->
        </div>
        <p class="lui_portlet_kn_statistics_tip">
            <!--
            本月更新<em>268</em>份文档
            -->
        </p>
        <!-- 列表区域  -->
        <div class="lui_portlet_kn_statistics_list">
            <!-- 
            <ul>
                <li><a href="javascript:;">
                    <em>256,083</em>
                    <p>本月浏览</p>
                </a></li>
                <li><a href="javascript:;"><em>6,882</em>
                    <p>本月评论</p>
                </a></li>
                <li><a href="javascript:;"><em>4,324</em>
                    <p>本月下载</p>
                </a></li>
            </ul>
            -->
        </div>
        <!-- 积分上传 -->
        <div class="lui_portlet_kn_statistics_btn">
            <p class="statistics_info">
               	 <!-- 我的积分：888  -->
            </p>
            <kmss:authShow roles="ROLE_KMSKNOWLEDGE_CREATE">
                 <span class="statistics_btn com_bgcolor_d" onclick="addDoc()">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.publishNew')}</span>
            </kmss:authShow>
        </div>
    </div>
</div>
<!-- 知识仓库 知识统计 Ends -->

<script type="text/javascript">
	domain.autoResize();
</script>

<script>
	function formatNum(num) {
        var result = [],counter = 0;
        num = (num || 0).toString().split('');
        for (var i = num.length - 1; i >= 0; i--) {
            counter++;
            result.unshift(num[i]);
            if (!(counter % 3) && i != 0) {
                result.unshift(',');
            }
        }
        return result.join('');
    }
	
	function formatCountList(data){
		var countList = [];
		
		var dayView = '${data.dayPublicView}';
		var weekView = '${data.weekPublicView}';
		var monthView = '${data.monthPublicView}';
		
		var monthReader = '${data.monthReaderView}';
		var monthComment = '${data.monthCommentView}';
		var monthDownload = '${data.monthDownloadView}';
		
		if(dayView){
			var dayData = {item:"fdDay",title:"${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.statistical.fdDay')}",value:'${data.dayPublicCount}'};
			countList.push(dayData);
		}
		if(weekView){
			var weekData = {item:"fdWeek",title:"${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.statistical.fdWeek')}",value:'${data.weekPublicCount}'};
			countList.push(weekData);
		}
		if(monthView){
			var monthData = {item:"fdMonth",title:"${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.statistical.fdMonth')}",value:'${data.monthPublicCount}'};
			countList.push(monthData);
		} 
		
		if(monthReader){
			var monthReaderData = {item:"fdMonthReader",title:"${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.statistical.reader.fdMonth')}",value:'${data.monthReaderCount}'};
			countList.push(monthReaderData);
		}
		
		if(monthComment){
			var monthReaderData = {item:"fdMonthComment",title:"${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.statistical.comment.fdMonth')}",value:'${data.monthCommentCount}'};
			countList.push(monthReaderData);
		}
		
		if(monthDownload){
			var monthReaderData = {item:"fdMonthDownload",title:"${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.statistical.download.fdMonth')}",value:'${data.monthDownloadCount}'};
			countList.push(monthReaderData);
		}
		
		return countList;
	}

    function renderSumHtml(num) {
    	seajs.use(['lui/jquery'], function($) {
	        num = formatNum(num).split('');
	        var html = '';
	        html += '<dl>';
	        for (var i = 0; i < num.length; i++) {
	            if (i != ',') {
	                html += '<dd>' + num[i] + '</dd>';
	            } else {
	                html += '<dt>' + num[i] + '<dt>';
	            }
	        }
	        html += '</dl>';
	        $('.lui_portlet_kn_statistics_sum').html(html);
    	});
    }

    function renderCountListHtml(data){
    	seajs.use(['lui/jquery'], function($) {
        	var count = formatCountList(data);
        	var html = '';
        	html += '<ul>';
        	for(var i=0;i<count.length;i++){
        		var item = count[i];
        		html += '<li><a href="javascript:;"><em>'+item.value+'</em><p>'+item.title+'</p></a></li>';
        	}
        	html += '</ul>';
        	$(".lui_portlet_kn_statistics_list").html(html);
    	});
    }
    
    function renderMonthPublishHtml(num){
    	if(num){
            seajs.use(['lui/jquery'], function($) {
                var html = '本月新增了<em>'+num+'</em>份知识';
                $('.lui_portlet_kn_statistics_tip').html(html);
            });
    	}
    }

    /**
     * 我的积分或者我的财富，可能不存在积分模块
     */
    function renderRichHtml() {
		var scoreView = '${data.scoreView}';
		var richView = '${data.richView}';
    	
        // 存在积分模块显示
        seajs.use(['lui/jquery','lui/dialog'], function($,dialog) {
        	var numtype=null;
			var scoreHtml="";
        	if(scoreView){
       			numtype="score";
				scoreHtml="我的积分：";
            }else if(richView){
        		numtype="rick";
				scoreHtml="我的财富：";
            }
            // 异步获取积分或者财富数据
            $.ajax({
				url:"${LUI_ContextPath}/kms/integral/kms_integral_portlet/kmsIntegralPortlet.do",
				type: 'get',
				data:{'numtype':numtype,'method':'getRichAndExperienceData'},
				dataType: 'json',
				success: function(data) {
					if(data.flag){
						var data=data.data;
						if(scoreView){
							scoreHtml+=""+(data.score || 0);
						}else if(richView){
							scoreHtml+=""+(data.rick || 0);
						}
						$(".lui_portlet_kn_statistics_btn .statistics_info").html(scoreHtml);
					    domain.autoResize();
					}else{
						// dialog.failure("${lfn:message('return.optFailure')}" );
					}
				},
				error:function(){
					// dialog.failure("${lfn:message('return.optFailure')}" );
				}
			});    
        });
    }

    function addDoc(){
        window.moduleAPI.kmsKnowledge.addDoc(null,<%=kmsCategoryEnabled%>);
    }

    seajs.use('kms/knowledge/kms_knowledge_ui/js/button');
    seajs.use('kms/knowledge/kms_knowledge_ui/js/subscribe');

    renderSumHtml(${data.docCount});
    renderMonthPublishHtml(${data.monthPublicCount});
    renderCountListHtml(${data});
    <kmss:ifModuleExist path="/kms/integral">
	    <kmss:authShow roles="ROLE_KMSINTEGRAL_DEFAULT">
			renderRichHtml();
	    </kmss:authShow>
	</kmss:ifModuleExist>
</script>

</template:replace>
</template:include>