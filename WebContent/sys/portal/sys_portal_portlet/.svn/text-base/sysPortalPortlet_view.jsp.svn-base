<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiRender"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="org.apache.commons.beanutils.BeanUtils"%>
<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiSource"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view" sidebar="no">
	<template:replace name="title"><bean:message  bundle="sys-portal" key="sys.portal.preview"/></template:replace>
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/portal/sys_portal_portlet/css/preview.css?s_cache=${LUI_Cache}202001132">
	</template:replace>
	<template:replace name="content">
		<script>
			Com_IncludeFile("dialog.js");
		</script>
		<style>
			.OverChecked {
				overflow-y:auto;
			}
		</style>
		<p class="txttitle">Portlet<bean:message  bundle="sys-portal" key="sys.portal.syswidget"/></p>
		<table class="tb_normal preview-baseInfo" width=95%>
			<tr>
				<td class="td_normal_title preview-title-info" colspan="2">
					<span><em><bean:message  bundle="sys-portal" key="sysPortalPage.fdName"/>：</em><xform:text property="fdName" style="width:85%" /></span>
					<span><em><bean:message  bundle="sys-portal" key="sysPortalPortlet.fdModule"/>：</em>${ sysPortalPortletForm.fdModule }</span>
					<span><em>ID：</em><xform:text property="fdId" style="width:85%" /></span>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="114px">
						${ lfn:message('sys-portal:sysPortal.portal.width') }
				</td>
				<td class="preview-size-td">
					<label class="preview-radio-size"><input type="radio" id="width1" name="width" checked="checked"  onclick="chwidth(600)"/><span>600</span></label>
					<label class="preview-radio-size"><input type="radio" id="width2" name="width" onclick="chwidth(300)"/><span>300</span></label>
					<label class="preview-radio-size"><input type="radio" id="width3" name="width" onclick="chwidth(180)"/><span>180</span></label>
					<span class="preview-size-content preview-size-content-width">
						<input class="preview-size-input" type="text" id="width4" placeholder="${ lfn:message('sys-portal:sysPortal.portal.diy') }" name="width" />
						<label title="${ lfn:message('sys-portal:sysPortal.portal.diy') }">
							<input type="radio" id="width4_radio"  name="width" onclick="changeWidth()"/><span></span>
						</label>
						<!-- <input class="preview-size-btn" type="button" name="widthButton" value="" onclick="changeWidth()"/> -->
					</span>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="114px">
						${ lfn:message('sys-portal:sysPortal.portal.height') }
				</td>
				<td class="preview-size-td">
					<label class="preview-radio-size"><input type="radio" id="height1" name="height" onclick="chheight(600)"/><span>600</span></label>
					<label class="preview-radio-size"><input type="radio" id="height2" name="height"  checked="checked"  onclick="chheight(400)"/><span>400</span></label>
					<label class="preview-radio-size"><input type="radio" id="height3" name="height" onclick="chheight(200)"/><span>200</span>
					</label>
					<!-- <span class="preview-size-content">
						<input class="preview-size-input" type="text" id="height4" name="height"/>
					    <input class="preview-size-btn" type="button" name="heightButton" value="${ lfn:message('sys-portal:sysPortal.portal.diy') }" onclick="changeHeight()"/>
					</span> -->
					<span class="preview-size-content preview-size-content-height">
						<input class="preview-size-input" type="text" id="height4" placeholder="${ lfn:message('sys-portal:sysPortal.portal.diy') }" name="height" />
						<label title="${ lfn:message('sys-portal:sysPortal.portal.diy') }">
							<input type="radio" id="height4_radio"  name="height" onclick="changeHeight()"/>
							<span></span>
						</label>
						<!-- <input class="preview-size-btn" type="button" name="widthButton" value="" onclick="changeWidth()"/> -->
					</span>
					<input type="checkbox" id="overBox" onchange="overChange()">${ lfn:message('sys-portal:sysPortal.portal.auto.increase')}
				</td>
			</tr>
			</table>
			<table class="tb_normal preview-param-tb" width=95% style="margin-top:-2px;">
			<tr>
				<td width="100%" colspan="2">
					<table width="100%">
						<tr>
							<td valign="top" width="40%">
								<div style="" id="previewBox" >
									<span class="preview_refresh" onclick="onRefresh()"><i></i>刷新</span>
									<iframe id="preview_portlet" frameborder="0" width="100%" height="100%"></iframe>
								</div>
							</td>
							<td width="1%"></td>
							<td valign="top" width="40%">
								<%
									String jsname = "v_"+IDGenerator.generateID();
									String sourceId = BeanUtils.getProperty(request.getAttribute("sysPortalPortletForm"),"fdSource");
									SysUiSource source = SysUiPluginUtil.getSourceById(sourceId);
									String renderId = SysUiPluginUtil.getFormatById(source.getFdFormat()).getFdDefaultRender();
									pageContext.setAttribute("sourceId",sourceId);
									pageContext.setAttribute("renderId",renderId);
									pageContext.setAttribute("formats",ArrayUtil.concat(source.getFdFormats(),';'));
								%>
								<script src="${ LUI_ContextPath }/sys/ui/js/var.js"></script>
								<table class="tb_normal" width="100%" id="porlet">
									<tbody>
									<tr>
										<input type="hidden" name="portlet_input_source_format" class="portlet_input_source_format" value="${ sysPortalPortletForm.fdFormat }"/>
										<input type="hidden" name="portlet_input_source_formats" class="portlet_input_source_formats"  value="${ sysPortalPortletForm.fdFormat }"/>
										<input type="hidden" name="portlet_input_source_id" class='portlet_input_source_id'  value="${ sysPortalPortletForm.fdSource }"/>
										<td class="td_normal_title"><bean:message  bundle="sys-portal" key="sysHomeNav.display.data"/></td>
										<td style="padding:0;"><div class="portlet_input_source_opt" jsname='_source_opt'></div></td>
									</tr>
										<%-- 	<tr>
                                                            <td><bean:message  bundle="sys-portal" key="sysPortalPage.desgin.msg.render"/>11<br>
                                                                <a href="javascript:selectRender()"><bean:message  bundle="sys-portal" key="sysPortalPortlet.External.access"/></a>
                                                            </td>
                                                            <td><div class="portlet_input_render_opt" jsname='_render_opt'></div></td>
                                                        </tr> --%>
									<!-- <tr>
										<td colspan="2" style="background: #f6f6f6;"><label><input type="checkbox" checked onclick="openPortletRenderSetting(this)" />${ lfn:message('sys-portal:sysPortalPage.desgin.msg.advOpt') }</label></td>
									</tr> -->
									</tbody>
									<tbody class="portlet_render_setting">
									<tr>
										<td class="td_normal_title" width="114px">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.render') }</td>
										<td style="padding:0">
											<table class="" style="width:100%">
												<tr>
													<td style="padding:5px;">当前呈现：<input type="hidden" name="portlet_input_render_id" class="portlet_input_render_id" />
														<input type="text" readonly="readonly" name="portlet_input_render_name" class="inputsgl portlet_input_render_name" style="width:340px" />
														<a href="javascript:void(0)" class="com_btn_link" onclick="openPortletRenderDialog(this)">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.select') }</a></td>
												</tr>
											</table>
											<div class="portlet_input_render_opt" jsname='_render_opt' style="display:none;">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.renderOpt') }</div>
										</td>
									</tr>
									<tr>
										<td class="td_normal_title">
												${ lfn:message('sys-portal:sysPortalPage.desgin.msg.refresh') }
										</td>
										<td>
											<input type="text" name="portlet_refresh_time" class="inputsgl portlet_refresh_time" onkeyup="this.value=this.value.replace(/\D/gi,'')" /><i></i>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.refresh1') } <span class="com_help">(${ lfn:message('sys-portal:sysPortalPage.desgin.msg.refresh2') })</span>
										</td>
									</tr>
									<tr>
										<td class="td_normal_title" style="white-space:nowrap;">
												${ lfn:message('sys-portal:sysPortalPage.msg.extClass') }
										</td>
										<td>
											<select name="portlet_extend_class" class="inputsgl portlet_extend_class" >
												<option value=""> == ${ lfn:message('sys-portal:sysPortalPage.msg.selectExt') } == </option>
												<option value="lui_panel_ext_padding">${ lfn:message('sys-portal:sysPortalPage.msg.extPadding') }</option>
											</select>
										</td>
									</tr>
									</tbody>
								</table>
								<script>
									var lodingImg = "<img src='${LUI_ContextPath}/sys/ui/js/ajax.gif'/>";
									var dialogWin = window;
									window.scene = "${param['scene']}";
									function ondocready(cb){
										var _url = window.location.href || '';
										// 获取参数
										var _renderId = Com_GetUrlParameter(_url,"renderId") || "${renderId}";
										var _renderName = Com_GetUrlParameter(_url,"renderName") || "";
										$(".portlet_input_source_opt").html(lodingImg).load("${LUI_ContextPath}/sys/ui/jsp/vars/source.jsp?x="+(new Date().getTime()),{"fdId":"${sourceId}","renderId":_renderId,"jsname":"_source_opt"},function(){
											$(".portlet_input_render_opt").attr("renderId",_renderId);
											$(".portlet_input_render_opt").show()
											$(".portlet_input_render_name").val(_renderName)
											$(".portlet_input_render_opt").html(lodingImg).load("${LUI_ContextPath}/sys/ui/jsp/vars/render.jsp?x="+(new Date().getTime()),{"fdId":_renderId,"jsname":"_render_opt"},function(){
												if(cb)
													cb();
											});
										});
										chwidth(600);
										chheight(400);

										$(".preview-size-input").on("focus",function(){
											$(this).parent().addClass("preview-size-content-focus")
										})
										$(".preview-size-input").on("blur",function(){
											$(this).parent().removeClass("preview-size-content-focus")
										})
									}
									function selectRender(){
										seajs.use(['lui/dialog'],function(dialog){
											dialog.iframe("/sys/portal/designer/jsp/selectportletrender.jsp?format="+encodeURIComponent("${formats}")+"&scene=portal"+"&curRenderId="+val.renderId, "${ lfn:message('sys-portal:sysPortalPage.desgin.selectrender') }", function(val){
												if(!val){
													return;
												}
												$(".portlet_input_render_opt").attr("renderId",val.renderId);
												$(".portlet_input_render_opt").html(lodingImg).load("${LUI_ContextPath}/sys/ui/jsp/vars/render.jsp?x="+(new Date().getTime()),{"fdId":val.renderId,"jsname":"_render_opt"},function(){
													onRefresh();
												});
											}, {width:750,height:550});
										});
									}
									function getConfigValues(){
										var config = {};
										if(window['_source_opt']!=null){
											config.sourceOpt = window['_source_opt'].getValue();
										}else{
											config.sourceOpt = {};
										}
										if(window['_render_opt']!=null){
											config.renderOpt = window['_render_opt'].getValue();
										}else{
											config.renderOpt = {};
										}
										return config;
									}
									function generateURL(vars){
										var urlPrefix = "<%= ResourceUtil.getKmssConfigString("kmss.urlPrefix")%>";
										var base = "/resource/jsp/widget.jsp?portletId=${sysPortalPortletForm.fdId}";
										var url = base;
										if($.trim(vars.renderId)!=""){
											url+="&renderId="+$.trim(vars.renderId);
										}
										url+="&sourceOpt="+encodeURIComponent(domain.stringify(vars.sourceOpt));
										url+="&renderOpt="+encodeURIComponent(domain.stringify(vars.renderOpt));
										LUI.$("#portlet_url").html(urlPrefix+url).attr("href",urlPrefix+url);

										LUI.$("#preview_portlet").attr("src","${LUI_ContextPath}"+url);
									}
									function onRefresh(){
										var tempData = getConfigValues();
										for (var key in tempData.sourceOpt)   {
											if(typeof(tempData.sourceOpt[key]) == "object"){
												tempData.sourceOpt[key] = tempData.sourceOpt[key][key];
											}
										}
										for (var key in tempData.renderOpt)   {
											if(typeof(tempData.renderOpt[key]) == "object"){
												tempData.renderOpt[key] = tempData.renderOpt[key][key];
											}
										}
										if($.trim($(".portlet_input_render_opt").attr("renderId"))!=""){
											tempData.renderId = $.trim($(".portlet_input_render_opt").attr("renderId"));
										}
										var _url = window.location.href || '';
										var _renderId = $(".portlet_input_render_id").val() || Com_GetUrlParameter(_url,"renderId");
										var _renderName =  $(".portlet_input_render_name").val() || Com_GetUrlParameter(_url,"renderName");
										var url = Com_SetUrlParameter(window.location.href,'renderId',_renderId)
										url = Com_SetUrlParameter(url,'renderName',_renderName)
										var _browserVersion = Com_IEVersion()
										if(_browserVersion==='ie9' || _browserVersion==='ie8' ){

										}else{
											history.pushState({url: url, title: document.title}, document.title, url)
										}
										/* if(tempData.renderId != xtable.find(".portlet_input_render_id").val()){
													xtable.find(".portlet_input_render_id").val(val.renderId);
													xtable.find(".portlet_input_render_name").val(val.renderName);
													var xopt = xtable.find(".portlet_input_render_opt").html(lodingImg).show();
													window[xopt.attr("jsname")] = null;
													xopt.load("${LUI_ContextPath}/sys/ui/jsp/vars/render.jsp?x="+(new Date().getTime()),{"fdId":val.renderId,"jsname":xopt.attr("jsname")});

									} */
										generateURL(tempData);
									}
									LUI.ready(function(){
										window.$ = LUI.$;
										ondocready(onRefresh);
									});

									function openPortletRenderDialog(ele){
										var xtable = LUI.$(ele).closest("#porlet");
										var formats = xtable.find(".portlet_input_source_formats").val();
										if(LUI.$.trim(formats)==""){
											seajs.use(['lui/dialog'],function(dialog){
												dialog.alert("${ lfn:message('sys-portal:sysPortalPage.desgin.selectsource') }");
											},{topWin:dialogWin});
											return;
										}
										var _renderId = $(".portlet_input_render_id").val() || '';
										seajs.use(['lui/dialog','lui/jquery'],function(dialog){
											dialog.iframe("/sys/portal/designer/jsp/selectportletrender.jsp?format="+encodeURIComponent(formats)+"&scene="+encodeURIComponent(scene)+"&curRenderId="+_renderId, "${ lfn:message('sys-portal:sysPortalPage.desgin.selectrender') }", function(val){
												if(!val){
													return;
												}
												$(".portlet_input_render_opt").attr("renderId",val.renderId);
												if(val.renderId != xtable.find(".portlet_input_render_id").val()){
													xtable.find(".portlet_input_render_id").val(val.renderId);
													xtable.find(".portlet_input_render_name").val(val.renderName);
													var xopt = xtable.find(".portlet_input_render_opt").html(lodingImg).show();
													window[xopt.attr("jsname")] = null;
													xopt.load("${LUI_ContextPath}/sys/ui/jsp/vars/render.jsp?x="+(new Date().getTime()),{"fdId":val.renderId,"jsname":xopt.attr("jsname")});
													onRefresh()
												}
											}, {width:750,height:550,topWin:dialogWin});
										});
									}
									function chwidth(width,flag){
										// $('#width4').val(width);
										if(width!=null&&!isNaN(width)){
											$('#previewBox').width(width);
											!flag && $(".preview-size-content-width").removeClass("preview-size-content-active")
											onRefresh();
										}
									}
									function changeWidth(){
										var width = $('#width4').val();
										$(".width4_radio").attr("checked",'true')
										$(".preview-size-content-width").addClass("preview-size-content-active")
										chwidth(width,true);
									}
									function chheight(height,flag){
										// $('#height4').val(height);
										if(height!=null&&!isNaN(height)){
											$('#preview_portlet').css("min-height",height);
											!flag && $(".preview-size-content-height").removeClass("preview-size-content-active")
											onRefresh();
										}
									}
									function changeHeight(){
										var height=$('#height4').val();
										if(height!=null&&!isNaN(height)){
											$('#preview_portlet').css("min-height", parseInt(height)+"px");
											$(".preview-size-content-height").addClass("preview-size-content-active")
											onRefresh();
										}
									}
									function overChange(){
										if(document.getElementById("overBox").checked){
											$("#previewBox").addClass("OverChecked");
										}else{
											$("#previewBox").removeClass("OverChecked");
										}
										onRefresh();
									}
									function openPortletRenderSetting(obj){
										var xtable = LUI.$(obj).closest("table");
										if(obj.checked)
											xtable.find(".portlet_render_setting").show();
										else
											xtable.find(".portlet_render_setting").hide();
									}
								</script>
								<span  class="preview_refresh" type="button" onclick="onRefresh()">
									<i></i>
									<bean:message  bundle="sys-portal" key="sysPortalPortlet.Refresh"/>
								</span>
								<p class="preview-tips">
									点击刷新, 预览配置效果
								</p>
							</td>
						</tr>
					</table>

				</td>

			</tr>
			<tr>
				<td class="td_normal_title" style="white-space:nowrap" >
					<bean:message  bundle="sys-portal" key="sysPortalPortlet.External.access"/>URL
				</td>
				<td >
					<a id="portlet_url"  style="word-break: break-word;" href="" target="_blank">
					</a>
				</td>
			</tr>
		</table>
	</template:replace>
</template:include>