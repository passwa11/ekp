<%@ page language="java" pageEncoding="UTF-8"%>
	<ui:toolbar layout="sys.ui.toolbar.float" count="10" var-navwidth="100%">
		<ui:button onclick="onStep2()" text="${ lfn:message('sys-portal:sysPortalPage.desgin.prevstep') }" id="btnPrevStep"></ui:button>
		<ui:button onclick="onEnter()" text="${ lfn:message('sys-portal:sysPortalPage.msg.enter') }"></ui:button>
	</ui:toolbar>
	<script src="${LUI_ContextPath}/sys/ui/js/var.js"></script>
	<div id="step1" style="display:none;">
		<div class="lui_portal_select_tab_iframe" id="lui_portal_select_tab_iframe">
			<ul>
				<li id="panelType_panel_select" onclick="document.getElementsByName('panelType')[0].checked=true;onStep1();changeToPanel();">
					<img src="${ LUI_ContextPath }/sys/portal/designer/images/panel.png"/>
					<p>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.typepanel') }</p>
					<input name="panelType" type="radio" value="panel"/>
				</li>
				<li id="panelType_tabpanel_select" onclick="document.getElementsByName('panelType')[1].checked=true;onStep1();changeToTabPanel();">
					<img src="${ LUI_ContextPath }/sys/portal/designer/images/tab.png"/>
					<p>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.typetab') }</p>
					<input name="panelType" type="radio" value="tabpanel"/>

				</li>
			</ul>
		</div>
	</div>
	<div id="step2" style="display:none;">
		<div id="panel_box" style="width:100%;height:409px;margin:0px auto;margin-top: 40px;">
			<div id="panelType_panel" style="display:none;">
				<ui:tabpanel layout="sys.ui.tabpanel.lighVertical" >
					<ui:content title="${ lfn:message('sys-portal:sysPortalPage.desgin.msg.contentSetting') }">
						<div class='portlet_table' style="height: 403px;">
							<table id="portlet_setting_template" class="tb_simple" style="width:100%;">
								<tbody>
									<!-- 基本信息 Starts -->
									<tr>
										<td colspan="2" class="portlet_sub_title_bar portlet_baseContent_sub_title_bar">
											<label>
												<input type="checkbox" checked onclick="openPortletBaseSetting(this)"/>
												<i></i>${ lfn:message('sys-portal:sysPortalPage.msg.baseInfo') }
											</label>
										</td>
									</tr>
									<tr class="portlet_baseContent_setting_tr">
										<td colspan="2">
											<div class="portlet_baseContent_setting">
												<table class="tb_simple" style="width: 100%;">
													<tr>
														<td class="td_normal_title" width="15%"><i class="portlet_require">*</i>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.title') }</td>
														<td><input name="portlet_input_title" class="inputsgl portlet_input_title" oninput="changePortletTitle(this)" propertychange="changePortletTitle(this)" type="text" style="width:80%" /></td>
													</tr>
													<tr class="contentSubtitleTr">
														<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.subtitle') }</td>
														<td>
															<input name="portlet_input_subtitle" class="inputsgl portlet_input_subtitle"
																oninput="changePortletSubtitle()" propertychange="changePortletSubtitle()" type="text" style="width:80%" />
														</td>
													</tr>
													<tr class="contentTitleIconTr">
														<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.titleicon') }</td>
														<td class="contentTitleIcon">
															<div class="contentTitleIconShow">
																<div style="cursor: pointer;" id='contentTitleIconPreview'  onclick="selectContentTitleIcon()" class="lui_icon_l ">
																	<img class="lui_img_l" src="" width="100%">
																</div>
															</div>
															&nbsp;
															<a class="icon" href="javascript:void(0)" onclick="selectContentTitleIcon()">
																${ lfn:message('sys-portal:sysPortalPage.desgin.msg.select') }
															</a>
															&nbsp;
															<a href="javascript:void(0)" onclick="clearContentTitleIcon()">
																${ lfn:message('sys-portal:sysPortalPage.desgin.msg.clear') }
															</a>
															<input name="portlet_input_titleicon" class="inputsgl portlet_input_titleicon"
																oninput="changePortletTitleicon()" propertychange="changePortletTitleicon()"
																type="hidden" style="width:80%" />
															<input name="portlet_input_titleimg" class="inputsgl portlet_input_titleimg"
																oninput="changePortletTitleicon()" propertychange="changePortletTitleicon()"
																type="hidden" style="width:80%" />
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<!-- 基本信息 Ends -->
									<!-- 内容设置 Starts -->
									<tr>
										<td colspan="2" class="portlet_sub_title_bar">
											<label>
												<input type="checkbox" checked onclick="openPortletContentrSetting(this)"/>
												<i></i>${ lfn:message('sys-portal:sysPortalMapTplNavCustom.fdContent') }
											</label>
										</td>
									</tr>
									<tr class="portlet_content_setting_tr">
										<td colspan="2">
											<div class="portlet_content_setting">
												<table class="tb_simple" style="width: 100%;">
													<tr>
														<td class="td_normal_title" width="15%"><i class="portlet_require">*</i>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.content') }</td>
														<td><input type="hidden" name="portlet_input_source_format" class="portlet_input_source_format" />
															<input type="hidden" name="portlet_input_source_formats" class="portlet_input_source_formats" />
															<input type="hidden" name="portlet_input_source_id" class='portlet_input_source_id' />
															<input type="text" readonly="readonly" name="portlet_input_source_name" class="inputsgl portlet_input_source_name" style="width:80%" />
															<a href="javascript:void(0)" class="com_btn_link" onclick="openPortletSourceDialog(this)">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.select') }</a>
														</td>
													</tr>
													<tr>
														<td colspan="2">
															<div class="portlet_input_source_opt"></div>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<!-- 内容设置 Ends -->
									<!-- 高级选项 Starts -->
									<tr>
										<td colspan="2" class="portlet_sub_title_bar"><label><input type="checkbox" checked onclick="openPortletRenderSetting(this)" /><i></i>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.advOpt') }</label></td>
									</tr>
									<tr class="portlet_render_setting_tr">
										<td  colspan="2">
											<div class="portlet_render_setting">
												<table class="tb_simple" style="width: 100%;">
													<tr>
														<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:portlet.component') }${ lfn:message('sys-portal:sysPortalPage.desgin.msg.render') }</td>
														<td>
															<input type="hidden" name="portlet_input_render_id" class="portlet_input_render_id" />
															<input type="text" readonly="readonly" name="portlet_input_render_name" class="inputsgl portlet_input_render_name" style="width:80%" />
															<a href="javascript:void(0)" class="com_btn_link" onclick="openPortletRenderDialog(this)">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.select') }</a>
															<!-- <div class="portlet_input_render_opt" style="display:none;width:80%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.renderOpt') }</div> -->
														</td>
													</tr>
													<tr class="portlet_input_render_opt_tr">
														<td colspan="2">
															<div class="portlet_input_render_opt">
																${ lfn:message('sys-portal:sysPortalPage.desgin.msg.renderOpt') }
															</div>
														</td>
													</tr>
													<tr>
														<td class="td_normal_title">
															${ lfn:message('sys-portal:sysPortalPage.desgin.msg.refresh') }
														</td>
														<td>
															<input type="text" name="portlet_refresh_time" class="inputsgl portlet_refresh_time" onkeyup="this.value=this.value.replace(/\D/gi,'')" />&nbsp;
															min
															<!-- ${ lfn:message('sys-portal:sysPortalPage.desgin.msg.refresh1') }  -->
															<span class="com_help">(${ lfn:message('sys-portal:sysPortalPage.desgin.msg.refresh2') })</span>
														</td>
													</tr>
													<tr>
														<td class="td_normal_title">
															${ lfn:message('sys-portal:sysPortalPage.msg.extClass') }
														</td>
														<td>
															<label class=" portlet_extend_class varkind_radio"><input name="portlet_extend_class" checked type="radio" value="">${ lfn:message('sys-portal:sysPortalPage.desgin.background.color.Item') }</label>
															<label class=" portlet_extend_class varkind_radio"><input name="portlet_extend_class" type="radio" value="lui_panel_ext_padding">${ lfn:message('sys-portal:sysPortalPage.msg.extPadding') }</label>
															<!-- <select name="portlet_extend_class"  >
																<option value=""> == ${ lfn:message('sys-portal:sysPortalPage.msg.selectExt') } == </option>
																<option value="lui_panel_ext_padding">${ lfn:message('sys-portal:sysPortalPage.msg.extPadding') }</option>
															</select> -->
														</td>
													</tr>
													<tr id="_operationTr">
														<td class="td_normal_title">
															${ lfn:message('sys-portal:sysPortalPage.msg.optButton') }
														</td>
														<td>
															<div class="portlet_input_operations"></div>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<!-- 高级选项 Ends -->
								</tbody>
							</table>
						</div>
					</ui:content>
					<ui:content title="${ lfn:message('sys-portal:sysPortalPage.desgin.msg.view') }">
						<div class="porltlet_layout_tb_wrap" style="height:408px;">
							<table class="tb_simple porltlet_layout_tb" style="width:100%;">
								<tr>
									<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.title.style') }</td>
									<td>
										<label class="varkind_radio">
											<input onclick="portletChangePanelType();changePortletPanelLayout(document.getElementById('portlet_panel_layout'));" type="radio" name="portlet_panel_type" checked="checked" value="panel" />
											<span>${ lfn:message('sys-portal:sysPortalPage.desgin.title.style.yes') }</span>
										</label>
										<label class="varkind_radio">
											<input onclick="portletChangePanelType();changePortletPanelLayout(document.getElementById('portlet_panel_layout'));"
												   type="radio" name="portlet_panel_type" value="none"/>
											<span><span>${ lfn:message('sys-portal:sysPortalPage.desgin.title.style.no') }</span></span>
										</label>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.height') }</td>
									<td><input type="text" class='inputsgl' style="width: 50px" id="portlet_panel_height" onkeyup="DigitInput(this,event);" name="portlet_panel_height" value="240" />px</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.autoheight') }</td>
									<td>
										<label class="varkind_radio">
											<input type="radio" name="portlet_panel_height_ext" value="auto"><span>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.autoheight1') }</span>
										</label>
										<label class="varkind_radio">
											<input type="radio" name="portlet_panel_height_ext" value="scroll"><span>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.autoheight2') }</span>
										</label>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.layout.style') }</td>
									<td></td>
								</tr>
								<!-- 单标签外观切换 Starts -->
								<tr id="portlet_panel_layout_tr">
									<!-- <td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.layout') }</td> -->
									<td colspan="2">
										<div class="portlet_panel_layout_td_content portlet_panel_layout_td_switch_wrap">
											<div class="portlet_panel_layout_td_result_wrap">
												<div class="portlet_panel_layout_td_switch_thumb">
													<img id="portlet_panel_layout_img" alt="">
													<p class="portlet_panel_layout_td_switch_desc"></p>
												</div>
												<span class="portlet_layout_swtich_btn" onclick="showPortletPanelLayoutList(this)">${ lfn:message('sys-portal:sysPortalPage.replace') }</span>
											</div>
											<div class="portlet_layout_list">
												<ul>
												</ul>
											</div>
											<select class="portlet_panel_layout_hidden" onchange="changePortletPanelLayout(this)" id="portlet_panel_layout" name="portlet_panel_layout">
											</select>
											<div class="portlet_panel_layout_td_result_otherInfo">
												<p id="portlet_panel_layout_helps">${ lfn:message('sys-portal:sysPortalPage.msg.tips') }</p>
												<div id="portlet_panel_layout_opt">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.layoutOpt') }</p>
											</div>
										</div>
									</td>
								</tr>
								<!-- 单标签外观切换 Ends -->
							</table>
						</div>
					</ui:content>
					<!-- 增加匿名判断，不为匿名显示权限配置页签 @author 吴进 by 20191113 -->
					<c:if test="${param['scene'] != 'anonymous'}">
						<ui:content title="${ lfn:message('sys-portal:sysPortalPage.desgin.msg.rightSetting') }">
							<div class="portlet_readList_power">
								<h4>${ lfn:message('sys-right:right.read.authReaders') }</h4>
								<p class="com_help">${ lfn:message('sys-portal:sysPortalPage.msg.nreader') }</p>
								<!-- <p class="com_help">${ lfn:message('sys-portal:sysPortalPage.msg.nreader') }</p> -->
								<div id="_xform_tabAuthReaderIds" _xform_type="address">
									<xform:address  textarea="true" showStatus="edit" mulSelect="true" propertyId="panelAuthReaderIds" propertyName="panelAuthReaderNames" style="width:97%;height:190px;" ></xform:address>
								</div>
							</div>
						</ui:content>
					</c:if>
				</ui:tabpanel>
			</div>
			<!-- 多标签 -->
			<div id="panelType_tabpanel" style="display:none;">
				<ui:tabpanel layout="sys.ui.tabpanel.lighVertical">
					<ui:content title="${ lfn:message('sys-portal:sysPortalPage.desgin.msg.contentSetting') }">
						<table class="tb_noborder" style="width:100%">
							<tbody>
								<tr>
									<td style="height: 20px;padding: 3px;text-align: right;"> <input class='lui_form_button' type="button" title="${ lfn:message('sys-portal:sysPortalPage.desgin.msg.addportlet') }" value="${ lfn:message('sys-portal:sysPortalPage.desgin.msg.addportlet') }" onclick="addTabPanelPortlet()">  </td>
								</tr>
							</tbody>
						</table>
						<div style="height: 372px;overflow-y:auto;">
							<div id="tabpanel_content">
							</div>
						</div>
					</ui:content>
					<ui:content title="${ lfn:message('sys-portal:sysPortalPage.desgin.msg.view') }">
						<!-- 外观配置 -->
						<div class="portlet_layout_config_wrap">
							<table class="tb_simple" style="width:100%;table-layout: fixed;">
								<tr>
									<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.paiban') }</td>
									<td>
										<label class="varkind_radio">
											<input onclick="portletChangeTabPanelType();changePortletTabPanelLayout(document.getElementById('portlet_tabpanel_layout'));" type="radio" name="portlet_tabpanel_type" checked="checked" value="h" /><span>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.shuipin') }</span>
										</label>
										<label class="varkind_radio">
											<input onclick="portletChangeTabPanelType();changePortletTabPanelLayout(document.getElementById('portlet_tabpanel_layout'));" type="radio" name="portlet_tabpanel_type" value="v" /><span>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.cuizhi') }</span>
										</label>
									</td>
								</tr>
								<tbody id="portlet_tabpanel_height_tr">
									<tr>
										<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.height') }</td>
										<td><input type="text" class='inputsgl' style="width: 50px" id="portlet_tabpanel_height" name="portlet_tabpanel_height" onkeyup="DigitInput(this,event);" value="240" />px</td>
									</tr>
									<tr>
										<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.autoheight') }</td>
										<td>
										<label class="varkind_radio">
											<input type="radio" name="portlet_tabpanel_height_ext" value="auto"><span>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.autoheight1') }</span>
										</label>
										<label class="varkind_radio">
											<input type="radio" name="portlet_tabpanel_height_ext" value="scroll"><span>${ lfn:message('sys-portal:sysPortalPage.desgin.msg.autoheight2') }</span>
										</label>
										</td>
									</tr>
								</tbody>
								<tr>
									<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.view.style') }</td>
									<td></td>
								</tr>
								<!-- 多标签外观切换 Starts -->
								<tr id="portlet_tabpanel_layout_tr">
									<!-- <td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.layout') }</td> -->
									<td colspan="2">
										<div class="portlet_panel_layout_td_content portlet_panel_layout_td_switch_wrap">
											<div class="portlet_panel_layout_td_result_wrap">
												<div class="portlet_panel_layout_td_switch_thumb">
													<img id="portlet_tabpanel_layout_img" alt="">
													<p class="portlet_panel_layout_td_switch_desc"></p>
												</div>
												<span class="portlet_layout_swtich_btn" onclick="showPortletPanelLayoutList(this)">${lfn:message('sys-portal:sysPortalPage.replace')}</span>
											</div>
											<div class="portlet_layout_list portlet_layout_tabpanel_list">
												<ul>
												</ul>
											</div>
											<select class="portlet_panel_layout_hidden" onchange="changePortletTabPanelLayout(this)" id="portlet_tabpanel_layout" name="portlet_tabpanel_layout_0">
											</select>
											<div class="portlet_panel_layout_td_result_otherInfo">
												<div id="portlet_tabpanel_layout_opt"><p>${lfn:message('sys-portal:sysPortalPage.desgin.msg.layoutOpt')}</p>
											</div>
										</div>

										<!-- <div class="portlet_panel_layout_td_content portlet_panel_layout_td_switch_wrap">
											<div class="portlet_panel_layout_td_switch_thumb">
												<img id="portlet_tabpanel_layout_img" alt="">
												<p class="portlet_panel_layout_td_switch_desc"></p>
											</div>
											<select onchange="changePortletTabPanelLayout(this)" id="portlet_tabpanel_layout" name="portlet_tabpanel_layout_0">
											</select>
											<div id="portlet_tabpanel_layout_opt">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.layoutOpt') }</p>
										</div> -->

									</td>
									<!-- <td class="td_normal_title" width="15%">外观样式</td> -->
									<!-- <td>
										<table class="tb_layout" width="100%">
											<tr>
												<td class="tb_layout_td" style="width: 300px;" valign="top">
													<select onchange="changePortletPanelLayout(this)" id="portlet_panel_layout" name="portlet_panel_layout">
													</select>
													<p id="portlet_panel_layout_helps">垂直单标签不支持内容配置中的副标题/图标的显示</p>
													<div id="portlet_panel_layout_opt" style="display:none;width:280px;">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.layoutOpt') }</p>
												</td>
												<td class="tb_layout_td" style="background: #f6f6f6;"  align="center">
													<img id="portlet_panel_layout_img" src=""   style="  max-width:200px;max-height: 200px;" />
												</td>
											</tr>
										</table>
									</td> -->
								</tr>
								<!-- 多标签外观切换 -->
								<!-- <tr>
									<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.layout') }</td>
									<td>
										<table class="tb_layout" width="100%">
											<tr>
												<td class="tb_layout_td" style="width: 300px;" valign="top">
													<select style="width:280px;" onchange="changePortletTabPanelLayout(this)" id="portlet_tabpanel_layout" name="portlet_tabpanel_layout_0">
													</select>
													<div id="portlet_tabpanel_layout_opt" style="display:none;width:280px;">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.layoutOpt') }</p>
												</td>
												<td class="tb_layout_td" style="background: #f6f6f6;" align="center">
													<img id="portlet_tabpanel_layout_img" src="" style=" max-width:200px;max-height: 200px; " />
												</td>
											</tr>
										</table>
									</td>
								</tr> -->
							</table>
						</div>
					</ui:content>
					<!-- 增加匿名判断，不为匿名显示权限配置页签 @author 吴进 by 20191113 -->
					<c:if test="${param['scene'] != 'anonymous'}">
						<ui:content title="${ lfn:message('sys-portal:sysPortalPage.desgin.msg.rightSetting') }">
							<div class="portlet_readList_power">
								<h4>${ lfn:message('sys-right:right.read.authReaders') }</h4>
								<p class="com_help">${ lfn:message('sys-portal:sysPortalPage.msg.nreader') }</p>
								<!-- <p class="com_help">${ lfn:message('sys-portal:sysPortalPage.msg.nreader') }</p> -->
								<div id="_xform_tabAuthReaderIds" _xform_type="address">
									<xform:address  textarea="true" showStatus="edit" mulSelect="true" propertyId="tabAuthReaderIds" propertyName="tabAuthReaderNames" style="width:97%;height:190px;" ></xform:address>
								</div>
							</div>
						</ui:content>
					</c:if>
				</ui:tabpanel>
			</div>
		</div>
	</div>
	<script>
		function DigitInput(el,e) {
			el.value=el.value.replace(/\D/gi,"");
		}
		String.prototype.startWith=function(str){
			var reg=new RegExp("^"+str);
			return reg.test(this);
		}

		String.prototype.endWith=function(str){
			var reg=new RegExp(str+"$");
			return reg.test(this);
		}

	</script>
	<script>
		window.panelLayout = {};
		window.scene = "${param['scene']}";
		lodingImg = "<img src='${LUI_ContextPath}/sys/ui/js/ajax.gif'/>"
		function validation(value){
			if(value.panel == null){
				alert("${ lfn:message('sys-portal:sysPortalPage.desgin.err.select') }");
				return false;
			}else if(value.panel=="panel"){
				if($.trim(value.height) == ""){
					alert("${ lfn:message('sys-portal:sysPortalPage.desgin.err.height') }");
					return false;
				}
				if($.trim(value.portlet[0].title)==""){
					alert("${ lfn:message('sys-portal:sysPortalPage.desgin.err.title') }");
					return false;
				}
				if($.trim(value.portlet[0].sourceId)==""){
					alert("${ lfn:message('sys-portal:sysPortalPage.desgin.err.content') }");
					return false;
				}
				if($.trim(value.portlet[0].renderId)==""){
					alert("${ lfn:message('sys-portal:sysPortalPage.desgin.err.render') }");
					return false;
				}
				var jsname = value.portlet[0].sourceOpt['__sourcejsname'];
				if(window[jsname]!=null){
					if(window[jsname].validation("${ lfn:message('sys-portal:sysPortalPage.desgin.err.source') }")==false){
						return false;
					}
				}
				// #153491 增加呈现必填验证
				var render_jsname = value.portlet[0].renderOpt['__renderjsname'];
				if(window[render_jsname]!=null){
					return window[render_jsname].validation();
				}
			}else if(value.panel == "tabpanel"){
				if($.trim(value.height) == ""){
					alert("${ lfn:message('sys-portal:sysPortalPage.desgin.err.height') }");
					return false;
				}
				for(var i=0;i<value.portlet.length;i++){
					if($.trim(value.portlet[i].title)==""){
						alert("第"+(i+1)+"个"+"${ lfn:message('sys-portal:sysPortalPage.desgin.err.title') }");
						return false;
					}
					if($.trim(value.portlet[i].sourceId)==""){
						alert("第"+(i+1)+"个"+"${ lfn:message('sys-portal:sysPortalPage.desgin.err.content') }");
						return false;
					}
					if($.trim(value.portlet[i].renderId)==""){
						alert("第"+(i+1)+"个"+"${ lfn:message('sys-portal:sysPortalPage.desgin.err.render') }");
						return false;
					}
					var jsname = value.portlet[i].sourceOpt['__sourcejsname'];
					if(window[jsname]!=null){
						if(window[jsname].validation("第"+(i+1)+"个${ lfn:message('sys-portal:sysPortalPage.desgin.err.source') }")==false){
							return false;
						}
					}
					// #153491 增加呈现必填验证
					var render_jsname = value.portlet[i].renderOpt['__renderjsname'];
					if(window[render_jsname]!=null){
						if(window[render_jsname].validation("第"+(i+1)+"个${ lfn:message('sys-portal:sysPortalPage.desgin.err.source') }")==false){
							return false;
						}
					}
				}
			}
			return true;
		}

		function onConfigWidget(value){
			if(value.panel == "panel"){
				//单标签显示内容配置的副标题和图标
				var xtable = LUI.$("#portlet_setting_template");
				xtable.find(".contentSubtitleTr").show();
				xtable.find(".contentTitleIconTr").show();
				LUI.$("input[name='panelType']")[0].checked=true;
				LUI.$("#portlet_panel_height").val(value.height);
				LUI.$("input[name='portlet_panel_type']").each(function(i,ele){
					if($(ele).val()==value.panelType){
						ele.checked = true;
					}
				});
				$("[name='portlet_panel_height_ext']").each(function(i,ele){
					if($(ele).val()==value.heightExt){
						ele.checked = true;
					}
				});

				//填充值
				portletChangePanelType();
				//选择值
				LUI.$("#portlet_panel_layout").val(value.layoutId);
				//选中值后更换图片
				changePortletPanelLayout(document.getElementById("portlet_panel_layout"),value.layoutOpt);

				var xtable = LUI.$("#portlet_setting_template");
				setPortletConfigData(xtable,value.portlet[0]);

				$form(LUI.$("input[name='panelAuthReaderIds']")).val(value.authReaderIds);
				$form(LUI.$("textarea[name='panelAuthReaderNames']")).val(value.authReaderNames);
			}
			else if(value.panel == "tabpanel"){
				//多标签隐藏内容配置的副标题和图标
				var xtable = LUI.$("#portlet_setting_template");
				xtable.find(".contentSubtitleTr").hide();
				xtable.find(".contentTitleIconTr").hide();
				LUI.$("input[name='panelType']")[1].checked=true;
				LUI.$("#portlet_tabpanel_height").val(value.height);
				LUI.$("input[name='portlet_tabpanel_type']").each(function(i,ele){
					if($(ele).val()==value.panelType){
						ele.checked = true;
					}
				});

				$("[name='portlet_tabpanel_height_ext']").each(function(i,ele){
					if($(ele).val()==value.heightExt){
						ele.checked = true;
					}
				});

				portletChangeTabPanelType();
				LUI.$("#portlet_tabpanel_layout").val(value.layoutId);
				changePortletTabPanelLayout(document.getElementById("portlet_tabpanel_layout"),value.layoutOpt);


				$form(LUI.$("input[name='tabAuthReaderIds']")).val(value.authReaderIds);
				$form(LUI.$("textarea[name='tabAuthReaderNames']")).val(value.authReaderNames);

				seajs.use(['lui/util/env'],function(env){
					for(var i=0;i<value.portlet.length;i++){
						var xtable = createPortletConfigTbody(i,env.fn.formatText(value.portlet[i].title));
						LUI.$("#tabpanel_content").append(xtable);
						setPortletConfigData(xtable,value.portlet[i]);
					}
				});
			}
			onStep1(false);
		}

		// 返回值
		function getPortletReturnData(){
			var data = {};
			data.panel = LUI.$("input[name='panelType']:checked").val();
			if(data.panel == "panel"){
				//
				data.layoutId =LUI.$("#portlet_panel_layout").find("option:selected").val();
				data.layoutName = LUI.$("#portlet_panel_layout").find("option:selected").text();
				var jsname = LUI.$("#portlet_panel_layout_opt").attr("jsname");
				if(window[jsname]!=null){
					data.layoutOpt = window[jsname].getValue();
				}else{
					data.layoutOpt = {};
				}
				data.height = LUI.$("#portlet_panel_height").val();
				data.heightExt = $("input[name='portlet_panel_height_ext']:checked").val();
				data.panelType = LUI.$("input[name='portlet_panel_type']:checked").val();
				data.authReaderIds = LUI.$("input[name='panelAuthReaderIds']").val();
				data.authReaderNames = LUI.$("textarea[name='panelAuthReaderNames']").val();
				//
				data.portlet = [];
				var xtable = LUI.$("#portlet_setting_template");
				data.portlet.push(getPortletConfigData(xtable));
			}
			else if(data.panel == "tabpanel"){
				//
				data.layoutId = LUI.$("#portlet_tabpanel_layout").find("option:selected").val();
				data.layoutName = LUI.$("#portlet_tabpanel_layout").find("option:selected").text();
				var jsname = LUI.$("#portlet_tabpanel_layout_opt").attr("jsname");
				if(window[jsname]!=null){
					data.layoutOpt = window[jsname].getValue();
				}else{
					data.layoutOpt = {};
				}
				data.height = LUI.$("#portlet_tabpanel_height").val();
				data.heightExt = $("input[name='portlet_tabpanel_height_ext']:checked").val();
				data.panelType = LUI.$("input[name='portlet_tabpanel_type']:checked").val();
				data.authReaderIds = LUI.$("input[name='tabAuthReaderIds']").val();
				data.authReaderNames = LUI.$("textarea[name='tabAuthReaderNames']").val();
				//
				data.portlet = [];
				var xtables = LUI.$("#tabpanel_content > .portlet_table");
				for(var i=0;i<xtables.length;i++){
					var xtable = LUI.$(xtables[i]);
					data.portlet.push(getPortletConfigData(xtable));
				}
			}
			// console.table(data)
			// console.table(LUI.$("#portlet_panel_layout").find("option:selected"))
			return data;
	}

		function getPortletConfigData(xtable){
			var portlet = {};
			portlet.title = xtable.find(".portlet_input_title").val();
			portlet.subtitle= xtable.find(".portlet_input_subtitle").val();
			portlet.titleimg= xtable.find(".portlet_input_titleimg").val();//素材库图标
			portlet.titleicon= xtable.find(".portlet_input_titleicon").val();//系统图标
			portlet.refresh = xtable.find(".portlet_refresh_time").val();
			portlet.extendClass = xtable.find("input[name='portlet_extend_class']:checked").val();
			portlet.sourceId = xtable.find(".portlet_input_source_id").val();
			portlet.sourceName = xtable.find(".portlet_input_source_name").val();
			portlet.format = xtable.find(".portlet_input_source_format").val();
			portlet.formats = xtable.find(".portlet_input_source_formats").val();
			portlet.operations = [];
			var xchk = xtable.find(".portlet_input_operations").find("input:checked");
			for(var i=0;i<xchk.length;i++){
				var opt = {};
				opt.key = $(xchk[i]).val();
				portlet.operations.push(opt);
			}
			var jsname = xtable.find(".portlet_input_source_opt").attr("jsname");
			if(window[jsname]!=null){
				portlet.sourceOpt = window[jsname].getValue();
				portlet.sourceOpt.__sourcejsname = jsname;
			}else{
				portlet.sourceOpt = {};
			}
			portlet.renderId = xtable.find(".portlet_input_render_id").val();
			portlet.renderName = xtable.find(".portlet_input_render_name").val();
			var jsname = xtable.find(".portlet_input_render_opt").attr("jsname");
			if(window[jsname]!=null){
				portlet.renderOpt = window[jsname].getValue();
				portlet.renderOpt.__renderjsname = jsname;
			}else{
				portlet.renderOpt = {};
			}
			return portlet;
		}

		function setPortletConfigData(xtable,portlet){
			xtable.find(".portlet_refresh_time").val(portlet.refresh==null?"":portlet.refresh);
			// xtable.find(".portlet_extend_class").val(portlet.extendClass==null?"":portlet.extendClass);
			if(portlet.extendClass){
				xtable.find("input[name='portlet_extend_class'][value="+portlet.extendClass+"]").attr("checked",true);
			}
			xtable.find(".portlet_input_title").val(portlet.title);
			xtable.find(".portlet_input_subtitle").val(portlet.subtitle);
			if(portlet.titleicon){//图标
				xtable.find(".portlet_input_titleicon").val(portlet.titleicon);
				xtable.find("#contentTitleIconPreview").attr("class","lui_icon_l "+portlet.titleicon);
			}else if(portlet.titleimg){//素材库图标
				var imgUrl = portlet.titleimg;
				if(imgUrl.indexOf("/") == 0){
					imgUrl = imgUrl.substring(1);
				}
				xtable.find(".portlet_input_titleimg").val(portlet.titleimg);
				xtable.find("#contentTitleIconPreview").attr("class","lui_icon_l ");
				$(".lui_img_l").attr('src',Com_Parameter.ContextPath+imgUrl);
			}
			xtable.find(".portlet_input_source_id").val(portlet.sourceId);
			xtable.find(".portlet_input_source_name").val(portlet.sourceName);
			xtable.find(".portlet_input_source_format").val(portlet.format);
			xtable.find(".portlet_input_source_formats").val(portlet.formats);
			xtable.find(".portlet_input_render_id").val(portlet.renderId);
			xtable.find(".portlet_input_render_name").val(portlet.renderName);
			if(portlet.sourceOpt!=null&&portlet.sourceOpt.__sourcejsname!=null){
				xtable.find(".portlet_input_source_opt").html(lodingImg).show().attr("jsname",portlet.sourceOpt.__sourcejsname).load("${LUI_ContextPath}/sys/ui/jsp/vars/source.jsp?x="+(new Date().getTime()),{"fdId":portlet.sourceId,"jsname":portlet.sourceOpt.__sourcejsname},function(){
					if(window[portlet.sourceOpt.__sourcejsname])
						window[portlet.sourceOpt.__sourcejsname].setValue(portlet.sourceOpt);
				});
			}
			if(portlet.renderOpt!=null&&portlet.renderOpt.__renderjsname!=null){
				xtable.find(".portlet_input_render_opt").html(lodingImg).show().attr("jsname",portlet.renderOpt.__renderjsname).load("${LUI_ContextPath}/sys/ui/jsp/vars/render.jsp?x="+(new Date().getTime()),{"fdId":portlet.renderId,"jsname":portlet.renderOpt.__renderjsname},function(){
					if(window[portlet.renderOpt.__renderjsname])
						window[portlet.renderOpt.__renderjsname].setValue(portlet.renderOpt);
				});
			}
			xtable.find(".portlet_input_operations").html(lodingImg);
			$.getJSON("${LUI_ContextPath}/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=portletOperation&x="+(new Date().getTime()),{"sourceId":portlet.sourceId},function(val){
				xtable.find(".portlet_input_operations").empty();
				if (val.length === 0) {
					//#136464 解决id重复导致的问题
					$('#panelType_panel #_operationTr').hide();
					$('#panelType_tabpanel #_operationTr').hide();
					// $("#_operationTr").hide();
				} else {
					// $("#_operationTr").show();
					//#136464 解决id重复导致的问题
					$('#panelType_panel #_operationTr').show();
					$('#panelType_tabpanel #_operationTr').show();
				}
				for(var i=0;i<val.length;i++){
					xtable.find(".portlet_input_operations").append("<label><input class='"+val[i].key+"' value='"+val[i].key+"' type='checkbox' />"+val[i].name+"</label>");
				}
				if(portlet.operations!=null){
					for(var i=0;i<portlet.operations.length;i++){
						xtable.find(".portlet_input_operations").find("."+portlet.operations[i].key).attr("checked","checked");
					}
				}
			});
		}

		function onStep1(animate){
			LUI("btnPrevStep").setVisible(true);
			var t = LUI.$("input[name='panelType']:checked").val();
			// $("#lui_portal_select_tab_iframe")
			$("#panelType_"+t+"_select").addClass("select").siblings().removeClass("select");
			LUI.$("input[name='panelType']").each(function(i,ele){
				LUI.$("#panelType_"+LUI.$(ele).val()).hide();
			});
			LUI.$("#panelType_"+t).show();

			//
			if(t=="panel"){
				//单标签
				if(LUI.$("#portlet_panel_layout option").length<=0){
					portletChangePanelType();
					changePortletPanelLayout(document.getElementById("portlet_panel_layout"));
				}
				// #155355 页面编辑外观会重置为默认单标签、默认多标签
				// else{
				// 	portletChangePanelType()
				// }
			}
			else if(t=="tabpanel"){
				//多标签
				if(LUI.$("#portlet_tabpanel_layout option").length<=0){
					portletChangeTabPanelType();
					changePortletTabPanelLayout(document.getElementById('portlet_tabpanel_layout'));
				}
				// #155355 页面编辑外观会重置为默认单标签、默认多标签
				// else {
				// 	portletChangeTabPanelType()
				// }
			}
			//

			if(animate==null||animate==true){
				LUI.$("#step1").slideUp();
				LUI.$("#step2").slideDown();
			}else{
				LUI.$("#step1").hide();
				LUI.$("#step2").show();
			}
		}

		function onStep2(){
			LUI("btnPrevStep").setVisible(false);
			LUI.$("#step2").slideUp();
			LUI.$("#step1").slideDown();
		}

		// 展示外观列表
		function showPortletPanelLayoutList(ele){
			LUI.$(ele).parents(".portlet_panel_layout_td_result_wrap").siblings(".portlet_layout_list").slideDown();
			LUI.$(ele).parents(".portlet_panel_layout_td_result_wrap").siblings(".portlet_panel_layout_td_result_otherInfo").slideUp();
			LUI.$(ele).parents(".portlet_panel_layout_td_result_wrap").slideUp();
		}

		// 切换单标签外观
		function changePortletPanelLayout(ele,val){
			// 图文列表外观样式选择
			if(LUI.$(ele).hasClass("portlet_layout_list_item")){
				var src = LUI.$(ele).data("img");
				var layoutId = LUI.$(ele).data("id");
				var layoutName = LUI.$(ele).data("name");
				LUI.$(ele).addClass("portlet_layout_list_item_select").siblings().removeClass("portlet_layout_list_item_select");
				var helpsDom=LUI.$("#portlet_panel_layout_helps")
				helpsDom.hide();
				if(layoutId=="sys.ui.panel.vertical"){
					helpsDom.text("${ lfn:message('sys-ui:layout.panel.vertical.helps') }");
					helpsDom.show();
				}
				if($.trim(src)!=""){
					LUI.$("#portlet_panel_layout_img").show().attr("src","${LUI_ContextPath}"+src);
				}else{
					LUI.$("#portlet_panel_layout_img").hide();
				}
				if($.trim(layoutName)!=""){
					$(".portlet_panel_layout_td_switch_desc").text(layoutName).attr("title",layoutName).attr("data-id",layoutId);
				}
				if($.trim(layoutId)!=""){
					$("#portlet_panel_layout").val(layoutId);
					//#155355 修复 页面编辑外观会重置为默认单标签、默认多标签
					// $("#portlet_panel_layout").find("option").each(function(){
					// 	if($(this).val() === layoutId ){
					// 		$(this).attr("selected",true).siblings().attr("selected",false);
					// 	}
					// })
					LUI.$("#portlet_panel_layout_opt").html(lodingImg).show().attr("jsname","portlet_panel_layout_opt_var").load("${LUI_ContextPath}/sys/ui/jsp/vars/layout.jsp?x="+(new Date().getTime()),{"fdId":layoutId,"jsname":"portlet_panel_layout_opt_var"},function(){
						if(val!=null){
							window['portlet_panel_layout_opt_var'].setValue(val);
						}
					});
				}else{
					LUI.$("#portlet_panel_layout_opt").empty().hide();
					window['portlet_panel_layout_opt_var'] = null;
				}
				LUI.$(ele).parents(".portlet_layout_list").slideUp();
				LUI.$(ele).parents(".portlet_layout_list").siblings(".portlet_panel_layout_td_result_wrap").slideDown();
				LUI.$(ele).parents(".portlet_layout_list").siblings(".portlet_panel_layout_td_result_otherInfo").slideDown();
			}else{
				var src = LUI.$(LUI.$(ele).find("option")[ele.selectedIndex]).attr("img");
				var layoutName = LUI.$(LUI.$(ele).find("option")[ele.selectedIndex]).attr("name");
				var layoutId = LUI.$(LUI.$(ele).find("option")[ele.selectedIndex]).val();
				var src = LUI.$(LUI.$(ele).find("option")[ele.selectedIndex]).attr("img");
				var helpsDom=LUI.$("#portlet_panel_layout_helps")
				helpsDom.hide();
				if(layoutId=="sys.ui.panel.vertical"){
					helpsDom.text("${ lfn:message('sys-ui:layout.panel.vertical.helps') }");
					helpsDom.show();
				}
				if($.trim(src)!=""){
					LUI.$("#portlet_panel_layout_img").show().attr("src","${LUI_ContextPath}"+src);
				}else{
					LUI.$("#portlet_panel_layout_img").hide();
				}
				if($.trim(layoutName)!=""){
					$(".portlet_panel_layout_td_switch_desc").text(layoutName).attr("title",layoutName).attr("data-id",layoutId);
				}
				if($.trim(layoutId)!=""){
					// 单标签图文列表选中
					$("#portlet_panel_layout_tr .portlet_layout_list li").each(function(){
						if($(this).data("id") == layoutId){
							$(this).addClass("portlet_layout_list_item_select").siblings().removeClass("portlet_layout_list_item_select");
						}
					})
					// 单标签select选中
					$("#portlet_panel_layout").val(layoutId);
					//#155355 修复 页面编辑外观会重置为默认单标签、默认多标签
					//$("#portlet_panel_layout").find("option").each(function(){
						// if($(this).val() === layoutId ){
						// 	$(this).attr("selected",true).siblings().attr("selected",false);
						// }
					//})

					LUI.$("#portlet_panel_layout_opt").html(lodingImg).show().attr("jsname","portlet_panel_layout_opt_var").load("${LUI_ContextPath}/sys/ui/jsp/vars/layout.jsp?x="+(new Date().getTime()),{"fdId":layoutId,"jsname":"portlet_panel_layout_opt_var"},function(){
						if(val!=null){
							window['portlet_panel_layout_opt_var'].setValue(val);
						}
					});
				}else{
					LUI.$("#portlet_panel_layout_opt").empty().hide();
					window['portlet_panel_layout_opt_var'] = null;
				}
			}
		}

		// 切换多标签外观
		function changePortletTabPanelLayout(ele,val){
			if(LUI.$(ele).hasClass("portlet_layout_list_item")){
				var src = LUI.$(ele).data("img");
				var layoutId = LUI.$(ele).data("id");
				var layoutName = LUI.$(ele).data("name");
				LUI.$(ele).addClass("portlet_layout_list_item_select").siblings().removeClass("portlet_layout_list_item_select");
				if($.trim(src)!=""){
					LUI.$("#portlet_tabpanel_layout_img").show().attr("src","${LUI_ContextPath}"+src);
				}else{
					LUI.$("#portlet_tabpanel_layout_img").hide();
				}
				if($.trim(layoutName)!=""){
					$(".portlet_panel_layout_td_switch_desc").text(layoutName).attr("title",layoutName).attr("data-id",layoutId);
				}

				if($.trim(layoutId)!=""){
					//#155355 修复 页面编辑外观会重置为默认单标签、默认多标签
					$("#portlet_tabpanel_layout").val(layoutId);
					// $("#portlet_tabpanel_layout").find("option").each(function(){
					//
					// 	if($(this).val() === layoutId ){
					// 		$(this).attr("selected",true).siblings().attr("selected",false);
					// 	}
					// })
					LUI.$("#portlet_tabpanel_layout_opt").html(lodingImg).show().attr("jsname","portlet_tabpanel_layout_opt_var").load("${LUI_ContextPath}/sys/ui/jsp/vars/layout.jsp?x="+(new Date().getTime()),{"fdId":layoutId,"jsname":"portlet_tabpanel_layout_opt_var"},function(){
						if(val!=null){
							window['portlet_tabpanel_layout_opt_var'].setValue(val);
						}
					});
				}else{
					LUI.$("#portlet_tabpanel_layout_opt").empty().hide();
					window['portlet_tabpanel_layout_opt'] = null;
				}
				LUI.$(ele).parents(".portlet_layout_list").slideUp();
				LUI.$(ele).parents(".portlet_layout_list").siblings(".portlet_panel_layout_td_result_wrap").slideDown();
				LUI.$(ele).parents(".portlet_layout_list").siblings(".portlet_panel_layout_td_result_otherInfo").slideDown();
			}else{
				var src = LUI.$(LUI.$(ele).find("option")[ele.selectedIndex]).attr("img");
				var layoutId = LUI.$(LUI.$(ele).find("option")[ele.selectedIndex]).val();
				var layoutName = LUI.$(LUI.$(ele).find("option")[ele.selectedIndex]).attr("name");
				if($.trim(src)!=""){
					LUI.$("#portlet_tabpanel_layout_img").show().attr("src","${LUI_ContextPath}"+src);
				}else{
					LUI.$("#portlet_tabpanel_layout_img").hide();
				}
				if($.trim(layoutName)!=""){
					$(".portlet_panel_layout_td_switch_desc").text(layoutName).attr("title",layoutName).attr("data-id",layoutId);
				}
				if($.trim(layoutId)!=""){
					// 多标签图文列表选中
					$("#portlet_tabpanel_layout_tr .portlet_layout_list li").each(function(){
						if($(this).data("id") == layoutId){
							$(this).addClass("portlet_layout_list_item_select").siblings().removeClass("portlet_layout_list_item_select");
						}
					})
					// 多标签select选中
					//#155355 修复 页面编辑外观会重置为默认单标签、默认多标签
					$("#portlet_tabpanel_layout").val(layoutId);
					//$("#portlet_tabpanel_layout").find("option").each(function(){
						// if($(this).val() === layoutId ){
						// 	$(this).attr("selected",true).siblings().attr("selected",false);
						// }
					//})

					LUI.$("#portlet_tabpanel_layout_opt").html(lodingImg).show().attr("jsname","portlet_tabpanel_layout_opt_var").load("${LUI_ContextPath}/sys/ui/jsp/vars/layout.jsp?x="+(new Date().getTime()),{"fdId":layoutId,"jsname":"portlet_tabpanel_layout_opt_var"},function(){
						if(val!=null){
							window['portlet_tabpanel_layout_opt_var'].setValue(val);
						}
					});
				}else{
					LUI.$("#portlet_tabpanel_layout_opt").empty().hide();
					window['portlet_tabpanel_layout_opt_var'] = null;
				}
			}
		}

		// 部件内容选择
		function openPortletSourceDialog(ele){
			var xtable = LUI.$(ele).closest(".portlet_table");
			seajs.use(['lui/dialog','lui/jquery'],function(dialog){
				dialog.iframe("/sys/portal/designer/jsp/selectportletsource.jsp?scene="+scene+"&sourceId="+xtable.find(".portlet_input_source_id").val(), "${ lfn:message('sys-portal:sysPortalPage.desgin.selectsource') }", function(val){
					if(!val){
						return;
					}

					if(xtable.find(".portlet_input_title").val()==""){
						xtable.find(".portlet_input_title").val(val.sourceName);
						xtable.find(".portlet_title").html(xtable.find(".portlet_input_title").val());
					}
					xtable.find(".portlet_input_source_format").val(val.sourceFormat);
					xtable.find(".portlet_input_source_formats").val(val.sourceFormats);
					xtable.find(".portlet_input_source_id").val(val.sourceId);
					xtable.find(".portlet_input_source_name").val(val.sourceName);
					xtable.find(".portlet_input_render_id").val(val.renderId);
					xtable.find(".portlet_input_render_name").val(val.renderName);
					xtable.find(".portlet_input_source_opt").html(lodingImg).show().attr("jsname",val.uuid+"_source").load("${LUI_ContextPath}/sys/ui/jsp/vars/source.jsp?x="+(new Date().getTime()),{"fdId":val.sourceId,"jsname":val.uuid+"_source"});
					xtable.find(".portlet_input_render_opt").html(lodingImg).show().attr("jsname",val.uuid+"_render").load("${LUI_ContextPath}/sys/ui/jsp/vars/render.jsp?x="+(new Date().getTime()),{"fdId":val.renderId,"jsname":val.uuid+"_render","containerId":xtable[0].id});
					xtable.find(".portlet_input_operations").empty();
					if(val.operations!=null&&val.operations.length>0){
						// $("#_operationTr").show();
						//#136464 解决id重复导致的问题
						$('#panelType_panel #_operationTr').show();
						$('#panelType_tabpanel #_operationTr').show();
						for(var i=0;i<val.operations.length;i++){
							xtable.find(".portlet_input_operations").append("<label><input type='checkbox' value='"+val.operations[i].key+"' checked='checked' />"+val.operations[i].name+"</label>");
						}
					} else {
						// $("#_operationTr").hide();
						//#136464 解决id重复导致的问题
						$('#panelType_panel #_operationTr').hide();
						$('#panelType_tabpanel #_operationTr').hide();
					}
				}, {width:750,height:550,topWin:dialogWin});
			});
		}

		// 部件呈现选择
		function openPortletRenderDialog(ele){
			var xtable = LUI.$(ele).closest(".portlet_table");
			var formats = xtable.find(".portlet_input_source_formats").val();
			if(LUI.$.trim(formats)==""){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.alert("${ lfn:message('sys-portal:sysPortalPage.desgin.selectsource') }");
				},{topWin:dialogWin});
				return;
			}
			seajs.use(['lui/dialog','lui/jquery'],function(dialog){
				dialog.iframe("/sys/portal/designer/jsp/selectportletrender.jsp?format="+encodeURIComponent(formats)+"&scene="+encodeURIComponent(scene) + "&curRenderId=" + xtable.find(".portlet_input_render_id").val(), "${ lfn:message('sys-portal:sysPortalPage.desgin.selectrender') }", function(val){
					if(!val){
						return;
					}
					if(val.renderId != xtable.find(".portlet_input_render_id").val()){
						xtable.find(".portlet_input_render_id").val(val.renderId);
						xtable.find(".portlet_input_render_name").val(val.renderName);
						var xopt = xtable.find(".portlet_input_render_opt").html(lodingImg).show();
						window[xopt.attr("jsname")] = null;
						xopt.load("${LUI_ContextPath}/sys/ui/jsp/vars/render.jsp?x="+(new Date().getTime()),{"fdId":val.renderId,"jsname":xopt.attr("jsname")});

					}
				}, {width:750,height:550,topWin:dialogWin});
			});
		}

		// 添加多标签面板
		function addTabPanelPortlet(){
			var len = LUI.$("#tabpanel_content > .portlet_table").length;
			var xdiv = createPortletConfigTbody(len);
			LUI.$("#tabpanel_content").append(xdiv);
			xdiv.find(".portlet_config_setting").click();
		}

		// 切换
		function switchPorgletConfigTable(x){
			LUI.$("#tabpanel_content .portlet_table .portlet_setting_body").filter(function(index) {
				var xid = LUI.$(this).closest(".portlet_table").attr("id");
				var index = xid.substring(xid.lastIndexOf("_")+1,xid.length);
				if(parseInt(index) == x){
					return false;
				}else{
					return true;
				}
			}).hide();
			var currentTable = LUI.$("#portlet_table_"+x).find(".portlet_setting_body");
			if(currentTable.is(":visible")){
				currentTable.parents(".portlet_table").find(".portlet_tr_title").removeClass("portlet_tr_title_active");
				currentTable.slideUp();
			}else{
				currentTable.parents(".portlet_table").find(".portlet_tr_title").addClass("portlet_tr_title_active");
				currentTable.slideDown();
			}
		}

		// 多标签上移
		function moveUpPorgletConfigTable(x){
			if(x<=0){
				return;
			}
			var y = x-1;
			LUI.$("#portlet_table_"+y).before(LUI.$("#portlet_table_"+x));
			updatePorgletConfigTableIndex();
		}

		// 多标签下移
		function moveDownPorgletConfigTable(x){
			var len = LUI.$("#tabpanel_content > .portlet_table").length;
			if(x>=len){
				return;
			}
			var y = x+1;
			LUI.$("#portlet_table_"+y).after(LUI.$("#portlet_table_"+x));
			updatePorgletConfigTableIndex();
		}

		// 多标签删除
		function deletePorgletConfigTable(x){
			seajs.use(['lui/dialog','lui/jquery'],function(dialog){
				dialog.confirm("${ lfn:message('sys-portal:sysPortalPage.msg.delete') }",function(val){
					if(val==true){
						LUI.$("#portlet_table_"+x).remove();
						updatePorgletConfigTableIndex();
					}
				})
			});
		}

		// 多标签更新
		function updatePorgletConfigTableIndex(){
			LUI.$("#tabpanel_content > .portlet_table").each(function(i,ele){
				LUI.$(ele).attr("id","portlet_table_"+i);
			});
		}

		// 更新标题
		function changePortletTitle(ele){
			seajs.use(['lui/util/env'],function(env){
				var title = env.fn.formatText(LUI.$(ele).val());
				LUI.$(ele).closest(".portlet_table").find(".portlet_title").html(title);
			});

		}

		// 更新副标题
		function changePortletSubtitle(ele){
			seajs.use(['lui/util/env'],function(env){
				var subtitle = env.fn.formatText(LUI.$(ele).val());
				LUI.$(ele).closest(".portlet_table").find(".portlet_subtitle").html(subtitle);
			});
		}

		// 更新图标
		function changePortletTitleicon(ele){
			seajs.use(['lui/util/env'],function(env){
				var subtitle = env.fn.formatText(LUI.$(ele).val());
				LUI.$(ele).closest(".portlet_table").find(".portlet_titleicon").html(subtitle);
			});
		}

		//
		function createPortletConfigTbody(x,title){
			var xtobdy = LUI.$("<div class='portlet_table' id='portlet_table_"+x+"' ></div>");
			var xtr1 = LUI.$("<div class='portlet_tr_title'></div>");
			var xtd1 = LUI.$("<div class='portlet_td'></div>");
			var xTitle = LUI.$("<div class='portlet_title'>"+(title?title:"${ lfn:message('sys-portal:sysPortalPage.desgin.msg.nonetitle') }")+"</div>");
			xTitle.click(function (evt){
				var xid = LUI.$(this).closest(".portlet_table").attr("id");
				var index = xid.substring(xid.lastIndexOf("_")+1,xid.length);
				switchPorgletConfigTable(parseInt(index));
			});
			var xConfig = LUI.$("<div class='portlet_config'></div>");
			var xSetting = LUI.$("<span class='portlet_config_setting lui_icon_s lui_icon_s_icon_set_green' title=\"${ lfn:message('sys-portal:sysPortalPage.desgin.opt.setting') }\"></span>").click(function(evt){
				var xid = LUI.$(this).closest(".portlet_table").attr("id");
				var index = xid.substring(xid.lastIndexOf("_")+1,xid.length);
				switchPorgletConfigTable(parseInt(index));
			});
			var xMoveUp = LUI.$("<span class='lui_icon_s lui_icon_s_icon_arrow_up_blue' title='${ lfn:message('sys-portal:sysPortalPage.desgin.opt.moveup') }'></span>").click(function(evt){
				var xid = LUI.$(this).closest(".portlet_table").attr("id");
				var index = xid.substring(xid.lastIndexOf("_")+1,xid.length);
				moveUpPorgletConfigTable(parseInt(index));
			});
			var xMoveDown = LUI.$("<span class='lui_icon_s lui_icon_s_icon_arrow_down_blue' title='${ lfn:message('sys-portal:sysPortalPage.desgin.opt.movedown') }'></span>").click(function(evt){
				var xid = LUI.$(this).closest(".portlet_table").attr("id");
				var index = xid.substring(xid.lastIndexOf("_")+1,xid.length);
				moveDownPorgletConfigTable(parseInt(index));
			});
			var xMoveDel = LUI.$("<span  class='lui_icon_s lui_icon_s_icon_close_red' title='${ lfn:message('sys-portal:sysPortalPage.desgin.opt.delete') }'></span>").click(function(evt){
				var xid = LUI.$(this).closest(".portlet_table").attr("id");
				var index = xid.substring(xid.lastIndexOf("_")+1,xid.length);
				deletePorgletConfigTable(parseInt(index));
			});
			xtd1.append(xTitle).append(xConfig.append(xMoveUp).append("&nbsp;").append(xMoveDown).append("&nbsp;").append(xMoveDel).append("&nbsp;").append(xSetting)).append("<div style='clear: both;'></div>");
			xtobdy.append(xtr1.append(xtd1));

			var xtr2 = LUI.$("<div class='portlet_setting_body'></div>");
			var xtd2 = LUI.$("<div class='portlet_td'></div>").append(createPortletConfigTable());
			xtobdy.append(xtr2.append(xtd2).hide());
			return xtobdy;
		}

		//
		function createPortletConfigTable(){
			var xtable = LUI.$("#portlet_setting_template").clone();
			xtable.removeAttr("id");
			xtable.find(":input").val("");
			xtable.find("script").remove();
			xtable.find(".portlet_input_source_opt").empty();
			xtable.find(".portlet_input_render_opt").empty();
			return xtable;
		}

		// 多标签外观切换渲染
		var portletChangeTabPanelType = function (){
			LUI.$("#portlet_tabpanel_layout").empty();
			//.append("<option value=''>系统默认布局</option>");
			var panelType = LUI.$("input[name='portlet_tabpanel_type']:checked").val();
			var $layoutList  = LUI.$("#portlet_tabpanel_layout_tr .portlet_layout_list ul");
			$layoutList.empty();

			if(panelType=="h"){
				LUI.$("#portlet_tabpanel_height_tr").show();
				for(var i=0;i<panelLayout.tabpanel.length;i++){
					var _class = '';
					if(panelLayout.tabpanel[i].id.endWith(".default")){
						_class = 'portlet_layout_list_item_select';
						LUI.$("#portlet_tabpanel_layout_tr .portlet_panel_layout_td_switch_wrap").data('layoutId',panelLayout.tabpanel[i].id).data('layoutName',panelLayout.tabpanel[i].name);
					}
					var $layoutLi = $('<li class="portlet_layout_list_item '+ _class +'" data-id="' + panelLayout.tabpanel[i].id + '" data-img="'+ panelLayout.tabpanel[i].img +'" data-name="' + panelLayout.tabpanel[i].name + '" onclick="changePortletTabPanelLayout(this)">')
					var _html = '<div class="portlet_layout_list_thumb">'
									+'<img src="${LUI_ContextPath}'+ panelLayout.tabpanel[i].img +'" alt="">'
								+'</div>'
								+'<p class="portlet_layout_list_title" title="' + panelLayout.tabpanel[i].name + '">'+panelLayout.tabpanel[i].name+'</p>'
					$layoutLi.html(_html)
					$layoutList.append($layoutLi);

					var opt = $("<option></option>");
					opt.attr("value",panelLayout.tabpanel[i].id);
					opt.attr("img",panelLayout.tabpanel[i].img);
					opt.attr("name",panelLayout.tabpanel[i].name);
					opt.text(panelLayout.tabpanel[i].name);
					if(panelLayout.tabpanel[i].id.endWith(".default")){
						opt.attr("selected",true);
					}
					LUI.$("#portlet_tabpanel_layout").append(opt);
				}
			}else if(panelType=="v"){
				LUI.$("#portlet_tabpanel_height_tr").hide();
				for(var i=0;i<panelLayout.accordionpanel.length;i++){
					var _class = '';
					if(panelLayout.tabpanel[i].id.endWith(".default")){
						_class = 'portlet_layout_list_item_select';
						LUI.$("#portlet_tabpanel_layout_tr .portlet_panel_layout_td_switch_wrap").data('layoutId',panelLayout.accordionpanel[i].id).data('layoutName',panelLayout.accordionpanel[i].name);
					}
					var $layoutLi = $('<li class="portlet_layout_list_item '+ _class +'" data-id="' + panelLayout.accordionpanel[i].id + '" data-img="'+ panelLayout.accordionpanel[i].img +'" data-name="'+panelLayout.accordionpanel[i].name+'" onclick="changePortletTabPanelLayout(this)">')
					var _html = '<div class="portlet_layout_list_thumb">'
									+'<img src="${LUI_ContextPath}'+ panelLayout.accordionpanel[i].img +'" alt="">'
								+'</div>'
								+'<p class="portlet_layout_list_title" title="'+panelLayout.accordionpanel[i].name+'">'+panelLayout.accordionpanel[i].name+'</p>'
					$layoutLi.html(_html)
					$layoutList.append($layoutLi);

					var opt = $("<option></option>");
					opt.attr("value",panelLayout.accordionpanel[i].id);
					opt.attr("img",panelLayout.accordionpanel[i].img);
					opt.attr("name",panelLayout.accordionpanel[i].name);
					opt.text(panelLayout.accordionpanel[i].name);
					if(panelLayout.accordionpanel[i].id.endWith(".default")){
						opt.attr("selected",true);
					}
					LUI.$("#portlet_tabpanel_layout").append(opt);
				}
			}
		}

		// 单标签外观切换渲染
		var portletChangePanelType = function (){
			var panelType = LUI.$("input[name='portlet_panel_type']:checked").val();
			LUI.$("#portlet_panel_layout").empty();
			var $layoutList  = LUI.$("#portlet_panel_layout_tr .portlet_layout_list ul");
			$layoutList.empty();
			//.append("<option value=''>系统默认布局</option>");
			if(panelType=="panel"){
				//LUI.$("#portlet_panel_layout_tr").show();
				for(var i=0;i<panelLayout.panel.length;i++){
					var $layoutLi = $('<li class="portlet_layout_list_item" data-id="' + panelLayout.panel[i].id + '" data-img="'+ panelLayout.panel[i].img +'" data-name="'+panelLayout.panel[i].name+'" onclick="changePortletPanelLayout(this)">')
					var _html = '<div class="portlet_layout_list_thumb">'
									+'<img src="${LUI_ContextPath}'+ panelLayout.panel[i].img +'" alt="">'
								+'</div>'
								+'<p class="portlet_layout_list_title" title="' + panelLayout.panel[i].name + '">'+panelLayout.panel[i].name+'</p>'
					$layoutLi.html(_html)
					$layoutList.append($layoutLi);

					var opt = $("<option></option>");
					opt.attr("value",panelLayout.panel[i].id);
					opt.attr("img",panelLayout.panel[i].img);
					opt.attr("name",panelLayout.panel[i].name);
					opt.text(panelLayout.panel[i].name);
					if(panelLayout.panel[i].id.endWith(".default")){
						opt.attr("selected",true);
					}
					LUI.$("#portlet_panel_layout").append(opt);
				}
			}else if(panelType=="none"){
				//LUI.$("#portlet_panel_layout_tr").hide();
				for(var i=0;i<panelLayout.nonepanel.length;i++){
					var $layoutLi = $('<li class="portlet_layout_list_item" data-id="' + panelLayout.nonepanel[i].id + '" data-img="'+ panelLayout.nonepanel[i].img +'" data-name="'+panelLayout.nonepanel[i].name+'" onclick="changePortletPanelLayout(this)">')
					var _html = '<div class="portlet_layout_list_thumb">'
									+'<img src="${LUI_ContextPath}'+ panelLayout.nonepanel[i].img +'" alt="">'
								+'</div>'
								+'<p class="portlet_layout_list_title" title="' + panelLayout.nonepanel[i].name + '">'+panelLayout.nonepanel[i].name+'</p>'
					$layoutLi.html(_html)
					$layoutList.append($layoutLi);

					var opt = $("<option></option>");
					opt.attr("value",panelLayout.nonepanel[i].id);
					opt.attr("img",panelLayout.nonepanel[i].img);
					opt.attr("name",panelLayout.nonepanel[i].name);
					opt.text(panelLayout.nonepanel[i].name);
					if(panelLayout.nonepanel[i].id.endWith(".default")){
						opt.attr("selected",true);
					}
					LUI.$("#portlet_panel_layout").append(opt);
				}
			}
		}

		// 呈现展开收起
		function openPortletRenderSetting(obj){
			var xtable = LUI.$(obj).closest(".portlet_table");
			if(obj.checked)
				xtable.find(".portlet_render_setting").slideDown();
			else
				xtable.find(".portlet_render_setting").slideUp();
		}

		// 内容展开收起
		function openPortletContentrSetting(obj){
			var xtable = LUI.$(obj).closest(".portlet_table");
			if(obj.checked)
				xtable.find(".portlet_content_setting").slideDown();
			else
				xtable.find(".portlet_content_setting").slideUp();
		}

		// 基本信息展开收起
		function openPortletBaseSetting(obj){
			var xtable = LUI.$(obj).closest(".portlet_table");
			if(obj.checked)
				xtable.find(".portlet_baseContent_setting").slideDown();
			else
				xtable.find(".portlet_baseContent_setting").slideUp();
		}
		//
		function changeToPanel(){
			//单标签显示内容配置的副标题和图标
			var xtable = LUI.$("#portlet_setting_template");
			xtable.find(".contentSubtitleTr").show();
			xtable.find(".contentTitleIconTr").show();
			var data = {};
			data.portlet = [];
			var xtables = LUI.$("#tabpanel_content > .portlet_table");
			for(var i=0;i<xtables.length;i++){
				var xtable = LUI.$(xtables[i]);
				data.portlet.push(getPortletConfigData(xtable));
			}
			var xxx = LUI.$("#portlet_setting_template");
			if(LUI.$.trim(xxx.find(".portlet_input_source_name").val()) == "" && data.portlet.length > 0){
				setPortletConfigData(xxx,data.portlet[0]);
			}
		}
		//
		function changeToTabPanel(){
			var data = {};
			data.portlet = [];
			//多标签隐藏内容配置的副标题和图标
			var xtable = LUI.$("#portlet_setting_template");
			xtable.find(".contentSubtitleTr").hide();
			xtable.find(".contentTitleIconTr").hide();
			data.portlet.push(getPortletConfigData(xtable));
			var xtables = LUI.$("#tabpanel_content > .portlet_table");
			if(xtables.length <= 0 && LUI.$.trim(data.portlet[0].sourceId) != ""){
				addTabPanelPortlet();
				var xxx = LUI.$(LUI.$("#tabpanel_content > .portlet_table")[0]);
				setPortletConfigData(xxx,data.portlet[0]);
			}
		}

		//
		function selectContentTitleIcon(){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.build({
					config : {
							width : 750,
							height : 500,
							title : "${ lfn:message('sys-portal:sysPortalPage.msg.selectIcon') }",
							content : {
								type : "iframe",
								url : "/sys/ui/jsp/iconfont.jsp"
							}
					},
					callback : function(value, dia) {
						console.log("回调",value)
						if(value==null){
							return ;
						}
						if(value.url){//素材库
							var imgUrl = value.url;
							if(imgUrl.indexOf("/") == 0){
								imgUrl = imgUrl.substring(1);
							}
							$("#contentTitleIconPreview .lui_img_l").css("display","block");
							$("#contentTitleIconPreview .lui_img_l").attr('src',Com_Parameter.ContextPath+imgUrl);
							$("#contentTitleIconPreview").attr("class", "lui_icon_l ");
							$(".contentTitleIcon [name='portlet_input_titleicon']").val(""); //系统图标清空
							$(".contentTitleIcon [name='portlet_input_titleimg']").val(value.url); //添加素材库图标
							console.log("回调素材",value.url)
						}else {//系统
							$("#contentTitleIconPreview .lui_img_l").attr('src',"");
							$("#contentTitleIconPreview .lui_img_l").css("display","none")
							$("#contentTitleIconPreview").attr("class", "lui_icon_l " + value);
							$(".contentTitleIcon [name='portlet_input_titleimg']").val(""); //素材库清空
							$(".contentTitleIcon [name='portlet_input_titleicon']").val(value); //添加系统图标
							console.log("回调系统图标",value)
						}
					}
				}).show();
			});
		}

		//
		function clearContentTitleIcon(){
			$("#contentTitleIconPreview").attr("class","lui_icon_l ");
			$(".contentTitleIcon [name='portlet_input_titleicon']").val("");
			$(".contentTitleIcon [name='portlet_input_titleimg']").val("");
			$(".lui_img_l").attr('src',"");
		}
	</script>
