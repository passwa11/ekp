<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
		seajs.use("theme!widget");
</script>
<ui:ajaxtext>

	<ui:dataview>
		<ui:source type="AjaxJson">
			{url:'/sys/praise/sys_praise_info_personal/sysPraiseInfoPersonal.do?method=getRankInfo&fdTimeType=${ranktype}&deptId=${deptId}&rowsize=${rowsize}'}
		</ui:source>
		<ui:render type="Template">
		{$
		<table width="100%" border="0" cellspacing="0" 
							cellpadding="0" class="lui_portlet_rank_tb lui_portlet_rank_tb_praise" >
			<tr>
				<th class="lui_portlet_rank_th_no"><bean:message bundle="sys-praise" key="sysPraiseInfo.portlet.rankInfo"/></th>
				<th class="lui_portlet_rank_th_name lui_portlet_rank_th_left"><bean:message bundle="sys-praise" key="sysPraiseInfo.calculate.person"/> </th>
				<th class="lui_portlet_rank_th_num"><bean:message bundle="sys-praise" key="sysPraiseInfo.portlet.count"/></th>
			</tr>	
		$}
			for(var i=0; i< data.rankInfo.length; i++){
				if(i<3){
				{$ 			<tr class="lui_portlet_rank_top3 lui_portlet_rank_top3_{%i+1%}">
								<td class="lui_portlet_rank_td_no lui_portlet_rank_pre_{%i+1%}">
				$}
							}else{
				{$ 				<tr class="lui_portlet_rank_top_common">
								<td class="lui_portlet_rank_td_no">
				$}
							}
				{$
									<span class="lui_portlet_rank_num_flag">{%i+1%}</span>
								</td>
								<td class="lui_portlet_rank_name">
									<div class="lui_portlet_rank_head_name">
										<span class="lui_portlet_rank_head">
											<img src="${LUI_ContextPath}{%data.rankInfo[i].imgUrl%}" alt="">
										</span>
										<a title="{%data.rankInfo[i].fdName%}" href="${LUI_ContextPath}/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId={%data.rankInfo[i].fdPersonId%}"
											target="_blank">
											{%data.rankInfo[i].fdPersonName%}
										</a>
									</div>
								</td>
								<td class="lui_portlet_rank_center lui_portlet_rank_score" >
									{%data.rankInfo[i].fdPraisedSum%}
								</td>
							</tr>	
				$}		
									
			}
		{$			
		</table>					
		$}
		</ui:render>
	</ui:dataview>
</ui:ajaxtext>