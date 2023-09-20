<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="auto">
	<template:replace name="head">
		<template:super/>
		<script>
			seajs.use(['theme!zone','theme!module']);
		</script>
	</template:replace>
	<template:replace name="body">
				<c:set var="userId" value="<%=com.landray.kmss.util.UserUtil.getUser().getFdId() %>"></c:set>
				<div class="lui_personal_relation">
					<div class="lui_personal_relation_nav" style="margin: 0px auto;${empty param['pagewidth'] ? 'width:90%' : lfn:concat('width:',fn:escapeXml(param['pagewidth'])) };min-width:980px; max-width:${fdPageMaxWidth}">
						<ul>
							
								<li class="current" >
									<a href="javascript:void(0)" onclick="onSelectTabItemClick('1',this)"><bean:message bundle="sys-zone" key="sysZonePersonInfo.relation.team" /></a>
								</li>
								<li>
									<a href="javascript:void(0)" onclick="onSelectTabItemClick('2',this)"><bean:message bundle="sys-zone" key="sysZonePersonInfo.relation.follow" /></a>
								</li>
								<li>
									<a href="javascript:void(0)" onclick="onSelectTabItemClick('3',this)"><bean:message bundle="sys-zone" key="sysZonePersonInfo.relation.fan" /></a>
								</li>
						</ul>
					</div>	
				</div>
				<div class="lui_personal_relaction_content" style="margin: 0px auto;${empty param['pagewidth'] ? 'width:90%' : lfn:concat('width:',fn:escapeXml(param['pagewidth'])) };min-width:980px; max-width:${fdPageMaxWidth}">
					<div class="lui_personal_relation_item item_team" style="display:none;">
							<div class="lui_personal_relation_chain">
								<div class="title">
									<span><bean:message bundle="sys-zone" key="sysZonePersonInfo.relation.chain" /></span>
								</div>
								<div class="content">
									<ui:dataview>
									<ui:source type="AjaxJson">
										{url:"/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getTeam&orgId=${userId}&type=chain"}
									</ui:source>
									<ui:render type="Template">
										if(data && data.length > 0 ) {
											{$<ul>$}
											for(var i = 0 ; i < data.length ; i++ ) {
												{$
													<li>
														
														<div class="lui_personal_relation_chain_img">
															<a target="_blank" 
																href="{%env.fn.formatUrl(data[i].homePagePath)%}">
																<img
																	src="{%env.fn.formatUrl(data[i].homeImgPath)%}" />
															</a>
														</div>
														<div class="lui_personal_relation_chain_txt">
															<h4>
																<a target="_blank" class="com_author"
																	href="{%env.fn.formatUrl(data[i].homePagePath)%}">
																	{%data[i].leaderName%}
																</a>
															</h4>
															<h5>
																{%data[i].postName%}
															</h5>
														</div>
														
													</li>
												$}
											}
											{$</ul>$}
										}else {
										{$	<div></div>$}
										}
									</ui:render>
								</ui:dataview>
								</div>
							</div>
							<div class="lui_personal_relation_team">
								<div class="title">
									<span><bean:message bundle="sys-zone" key="sysZonePersonInfo.relation.myteam" /></span>
								</div>
								<div class="content">
									<ui:dataview id="id_personal_relation_team">
										<ui:source type="AjaxJson">
											{url:"/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=listMyTeam&orgId=${userId}"}
										</ui:source>
										<ui:render type="Template">
												if(data && data.status==1 && data.datas.length > 0) {
													var records = data.datas;
													var totalSize = data.totalSize;
													{$<ul>$}
													 for(var i = 0; i < records.length; i ++ ) {
														{$
														<li>
															<div class="lui_personal_relation_team_img">
																<a target="_blank"
																	href="${LUI_ContextPath }/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId={%records[i].fdId%}">
																<img
																	src="${ LUI_ContextPath }{%records[i].imgUrl%}" />
																</a>
															</div>
															<h4>{%records[i].fdName%}</h4>
															<h5>{%records[i].postName%}</h5>
														</li>									
														$}
													}
													{$</ul>$}
													if(!window.personalRelationTeamBtn && totalSize>data.datas.length){
														{$<div class="lui_personal_relation_team_btn">
															<a href="javascript:void(0)" onclick="onQueryMorePersons()">查看所有团队成员</a>
														</div>$}
													}
												}else{
													{$	<div></div>$}
												}
											
										</ui:render>
									</ui:dataview>
								</div>
							</div>
					</div>
					<div class="lui_personal_relation_item item_fans" style="display:none;">
						<ui:iframe id="item_iframe_content_fans"></ui:iframe>
					</div>
				</div>

		
	
<script>
		function formatUrl(url) {
			var context = "${LUI_ContextPath}";
			if(!url){
				return "";
			}
			if (url.substring(0, 1) == '/') {
				return context + url;
			} else {
				return url;
			}
		}
		function onQueryMorePersons(){
			var listview = LUI('id_personal_relation_team'),
			source = listview.source,
			url = Com_SetUrlParameter(source.url,'rowsize',1000);
			source.url = url;
			source._url = url;
			source.get();
			window.personalRelationTeamBtn = true;
		}

		function onSelectTabItemClick(value,evt){
			var parent = $(evt).parent();
			if(parent.hasClass('current')){
				return;
			}
			$('.lui_personal_relation_nav li.current').removeClass('current');
			parent.addClass('current');
			$('.lui_personal_relaction_content .lui_personal_relation_item').hide();
			if(value==1){
				$('.lui_personal_relation_item.item_team').show();
			}
			if(value==2 || value==3){
				$('.lui_personal_relation_item.item_fans').show();
				onFansClick(value);
			}
		}
		function onFansClick(value){
			var url = '${LUI_ContextPath}/sys/fans/sys_fans_main/sysFansMain_follow_list.jsp?LUIID=iframe_body&fdId=${userId}&attentModelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo&fansModelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo&fans_TA=&type=attention&showTabPanel=false';
			var frameNode = LUI('item_iframe_content_fans');
			url = Com_SetUrlParameter(url,'j_iframe',"true");
			var type = value==2 ? "attention":"fans";
			url = Com_SetUrlParameter(url,'type',type);
			frameNode.reload(url);
			frameNode.element.show();
		}
		LUI.ready(function(){
			var contentW = $('.lui_personal_relation_nav').width();
			$('.lui_personal_relation_item .lui_personal_relation_team').width(contentW-214);
			$('.lui_personal_relation_item.item_team').show();
		});

</script>
	</template:replace>
</template:include>
