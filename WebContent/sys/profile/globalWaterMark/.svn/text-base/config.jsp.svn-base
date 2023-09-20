<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">

	<template:replace name="title">
		${lfn:message('sys-profile:sys.profile.globalWaterMark.config')}
	</template:replace>

	<template:replace name="content">
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do?modelName=com.landray.kmss.sys.profile.model.GlobalWaterMarkConfig&autoclose=true">

			<center>

				<table class="tb_normal" width=95% style="display: none">
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('sys-profile:sys.profile.globalWaterMark.config')}</td>
						<td>
							<ui:switch property="value(waterMarkEnabled)"
								onValueChange="autoChange();switchChange()"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"
								>

							</ui:switch>
						</td>
					</tr>

					<tr class="waterMark" style="display: none">
						<td class="td_normal_title" colspan=2>
							<b><label>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.display')}</label></b>
						</td>
					</tr>
<%--					<tr class="waterMark" style="display: none">--%>
<%--						<td class="td_normal_title" width=15%>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.app.port')}</td>--%>
<%--						<td>--%>
<%--							<xform:checkbox  property="value(viewType)" dataType="boolean" subject="PC"  showStatus="edit">--%>
<%--								<xform:simpleDataSource value="pcEnable"><bean:message key='sys.profile.globalWaterMark.config.app.port.pc' bundle='sys-profile'/></xform:simpleDataSource>--%>
<%--							</xform:checkbox>--%>
<%--							<xform:checkbox property="value(viewType)" subject="移动" dataType="boolean" showStatus="edit">--%>
<%--								<xform:simpleDataSource value="mobileEnable"><bean:message key='sys.profile.globalWaterMark.config.app.port.mobile' bundle='sys-profile'/></xform:simpleDataSource>--%>
<%--							</xform:checkbox>--%>
<%--						</td>--%>
<%--					</tr>--%>
					<tr class="waterMark" style="display: none">
						<td class="td_normal_title" colspan=2>
							<b><label>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css')}</label></b>
						</td>
					</tr>
					<tr class="waterMark" style="display: none" id="waterMarkJsonTd">
						<td class="td_normal_title" width=15%>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css')}</td>
						<td>
							<html:text property="value(watermarkParams)" style="width:90%"/>
						</td>
					</tr>
				</table>

			</center>
			<body class="watermark_config_body">
			<div class="watermark_config_main_container">
				<div class="watermark_config_container">
					<div class="watermark_config_content">
						<div class="watermark_config_content_item">
							<div class="watermark_config_switch_content">
								<div class="watermark_config_switch_image switch_on_content">
								</div>
								<div class="watermark_config_switch_explain">
									<div class="watermark_config_switch_bar">
										<p>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.enable')}</p>
										<div class="watermark_config_switch_button">
											<ui:switch property="value(waterMarkEnabled)"
													   onValueChange="autoChange();switchChange()"
													   checkVal="true" unCheckVal="false" checked="false"
											>
											</ui:switch>
										</div>
									</div>
									<ul class="switch_on_content">
										<li>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.enable.tips1')}</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="watermark_config_title watermark_config_display_title switch_off_content" >
								${lfn:message('sys-profile:sys.profile.globalWaterMark.config.display')}
						</div>
						<table><tr><td>
						<div class="watermark_config_display_content switch_off_content">
							<span>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.app.port')}：</span>
							<xform:checkbox property="value(viewType)" required="true" dataType="boolean" subject="${lfn:message('sys-profile:sys.profile.globalWaterMark.config.app.port')}"  showStatus="edit">
								<xform:simpleDataSource value="pcEnable" ><bean:message key='sys.profile.globalWaterMark.config.app.port.pc' bundle='sys-profile' /></xform:simpleDataSource>
								<xform:simpleDataSource value="mobileEnable"><bean:message key='sys.profile.globalWaterMark.config.app.port.mobile' bundle='sys-profile'/></xform:simpleDataSource>
							</xform:checkbox>
						</div>
						</td></tr></table>
						<div class="watermark_config_title switch_off_content">
								${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css')}
						</div>
						<div class="watermark_config_view_content switch_off_content">
							<div class="watermark_config_viewer_title">
								<div class="watermark_config_viewer_name">${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css')}</div>
								<div class="watermark_config_viewer_bar">
									<ul class="watermark_config_viewer_bar_list watermark_config_tab">
										<li class="computer_bar active">${lfn:message('sys-profile:sys.profile.globalWaterMark.config.app.port.pc')}</li>
										<li class="mobile_bar">${lfn:message('sys-profile:sys.profile.globalWaterMark.config.app.port.mobile')}</li>
									</ul>
								</div>
								<div class="watermark_config_viewer_bar_info"></div>
							</div>
							<div class="watermark_config_viewer_box">
								<div class="watermark_config_viewer_box_left">
									<div class="watermark_config_viewer_simulation computer">
										<div class="watermark_template">
											<div class="watermark_template_banner">
												<div></div>
											</div>
											<ul class="watermark_template_info">
												<li>
													<div></div>
													<span></span>
												</li>
												<li>
													<div></div>
													<span></span>
												</li>
												<li>
													<div></div>
													<span></span>
												</li>
												<li>
													<div></div>
													<span></span>
												</li>
												<li>
													<div></div>
													<span></span>
												</li>
												<li>
													<div></div>
													<span></span>
												</li>
												<li>
													<div></div>
													<span></span>
												</li>
											</ul>
											<ul class="watermark_template_list">
												<li>
													<div class="watermark_template_list_left"></div>
													<div class="watermark_template_list_right">
														<div></div>
														<div></div>
														<div></div>
													</div>
												</li>
												<li>
													<div class="watermark_template_list_left"></div>
													<div class="watermark_template_list_right">
														<div></div>
														<div></div>
														<div></div>
													</div>
												</li>
												<li>
													<div class="watermark_template_list_left"></div>
													<div class="watermark_template_list_right">
														<div></div>
														<div></div>
														<div></div>
													</div>
												</li>
												<li>
													<div class="watermark_template_list_left"></div>
													<div class="watermark_template_list_right">
														<div></div>
														<div></div>
														<div></div>
													</div>
												</li>
											</ul>
										</div>
									</div>
									<div class="watermark_config_viewer_simulation mobile">
										<div class="watermark_template_mobile_bar"></div>
										<div class="watermark_template_title">
												${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.title')}
										</div>
										<div class="watermark_template_banner">
											<div></div>
										</div>
										<ul class="watermark_template_mobile_info clearfix">
											<li><div></div></li>
											<li><div></div></li>
											<li><div></div></li>
											<li><div></div></li>
											<li><div></div></li>
											<li><div></div></li>
											<li><div></div></li>
											<li><div></div></li>
										</ul>
										<ul class="watermark_template_mobile_list">
											<li>
												<div class="watermark_template_mobile_left"></div>
												<div class="watermark_template_mobile_right">
													<div></div>
													<div></div>
													<div></div>
												</div>
											</li>
											<li>
												<div class="watermark_template_mobile_left"></div>
												<div class="watermark_template_mobile_right">
													<div></div>
													<div></div>
													<div></div>
												</div>
											</li>
										</ul>
									</div>
								</div>
								<div class="watermark_config_viewer_box_right">
									<ul class="watermark_configuration_box_tab watermark_config_tab">
										<li class="active">${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.words.watermark')}</li>
<%--										<li>图片水印</li>--%>
									</ul>
									<div class="watermark_configuration_box_content">
										<div class="watermark_configuration_box_title">
											<span>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.watermark.content')}</span>
											<div class="watermark_configuration_list_add"  onselectstart="return false">
												<ul class="watermark_configuration_list_pop">
												</ul>
											</div>
										</div>
										<ul class="watermark_configuration_box_list list-group clearfix" id="nested">
										</ul>
										<div class="watermark_configuration_box_title">
											<span>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.watermark.typesetting')}</span>
										</div>
										<ul class="watermark_config_tab watermark_configuration_box_composing watermark_cover">
											<li class="watermark_configuration_box_cover active">
												<i class="watermark_configuration_icon watermark_configuration_icon_cover"></i>
													${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.spread.over')}
											</li>
<%--											<li>--%>
<%--												<i class="watermark_configuration_icon watermark_configuration_icon_single"></i>--%>
<%--												单个--%>
<%--											</li>--%>
										</ul>
										<div class="muiPerformanceDropdownBox coords_content" onselectstart="return false">
											<div class="watermark_configuration_coords_box">
												<div class="watermark_configuration_coords_item watermark_configuration_coords_horizon">
													<i></i>
													<em>15</em>
												</div>
												<i class="watermark_configuration_coords_relation"></i>
												<div class="watermark_configuration_coords_item watermark_configuration_coords_vertical">
													<i></i>
													<em>20</em>
												</div>
											</div>
											<i class="muiPerformanceDropdownBoxIcon"></i>
											<div class="muiPerformanceDropdownBoxList">
												<ul>
													<li>
														<span class="watermark_coords_horizon_num">15</span>
														<i class="watermark_configuration_coords_relation"></i>
														<span class="watermark_coords_vertical_num">20</span>
													</li>
													<li>
														<span class="watermark_coords_horizon_num">20</span>
														<i class="watermark_configuration_coords_relation"></i>
														<span class="watermark_coords_vertical_num">25</span>
													</li>
													<li>
														<span class="watermark_coords_horizon_num">25</span>
														<i class="watermark_configuration_coords_relation"></i>
														<span class="watermark_coords_vertical_num">30</span>
													</li>
													<li>
														<span class="watermark_coords_horizon_num">30</span>
														<i class="watermark_configuration_coords_relation"></i>
														<span class="watermark_coords_vertical_num">35</span>
													</li>
												</ul>
												<div class="watermark_configuration_custom_btn">
													<div class="watermark_configuration_custom_unedited">
														<i class="watermark_configuration_icon_add"></i>
														<span>自定义</span>
													</div>
													<div class="watermark_configuration_custom_editing">
														<div class="watermar_editing_horizon_box">
															<div class="watermar_editing_horizon_box_sign">${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.transverse ')}</div>
															<input class="watermar_editing_horizon_box_input" type="text" placeholder="${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.please.enter')}" oninput = "value=value.replace(/[^\d]/g,'')">
														</div>
														<div class="watermar_editing_vertical_box">
															<div class="watermar_editing_vertical_box_sign">${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.longitudinal ')}</div>
															<input class="watermar_editing_vertical_box_input" placeholder="${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.please.enter')}" oninput = "value=value.replace(/[^\d]/g,'')">
														</div>
														<div class="watermar_editing_operation_box">
															<div class="watermar_editing_operation_confirm"><bean:message key='button.ok'/></div>
															<div class="watermar_editing_operation_cancel"><bean:message key='button.cancel'/></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="watermark_configuration_box_tips">
												${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.tips')}
										</div>
										<div class="watermark_configuration_box_title">
											<span>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.angle')}</span>
										</div>
										<div class="watermark_configuration_scale_box hide">
											<ul class="watermark_config_tab watermark_configuration_box_composing scale_list">
												<li data-scale="30">
													<i class="watermark_configuration_scale_icon watermark_configuration_icon_scale30"></i>
												</li>
												<li data-scale="45">
													<i class="watermark_configuration_scale_icon watermark_configuration_icon_scale45"></i>
												</li>
												<li data-scale="60">
													<i class="watermark_configuration_scale_icon watermark_configuration_icon_scale60"></i>
												</li>
												<li data-scale="90">
													<i class="watermark_configuration_scale_icon watermark_configuration_icon_scale90"></i>
												</li>
											</ul>
											<div class="watermark_configuration_alpha_btn" onselectstart="return false">
												<span>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.custom')}</span>
												<i class="watermark_configuration_icon_select"></i>
											</div>
											<div class="watermark_configuration_alpha_input_box scale">
												<p>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.angle')}</p>
												<div class="watermark_configuration_alpha_number">
													<span>0</span>
													<em>°</em>
													<div class="watermark_configuration_alpha_adjust">
                                                <span class="watermark_configuration_alpha_adjust_Btn watermark_configuration_alpha_adjust_upBtn">
                                                    <i class="watermark_configuration_alpha_adjust_up"></i>
                                                </span>
														<span class="watermark_configuration_alpha_adjust_Btn watermark_configuration_alpha_adjust_downBtn">
                                                    <i class="watermark_configuration_alpha_adjust_down"></i>
                                                </span>
													</div>
												</div>
											</div>
										</div>
										<div class="watermark_configuration_box_title">
											<span>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.words.style')}</span>
										</div>
										<div class="watermark_configuration_flex_box">
											<div class="watermark_configuration_font_box muiPerformanceDropdownBox">
												<span>微软雅黑</span>
												<i class="muiPerformanceDropdownBoxIcon"></i>
												<div class="muiPerformanceDropdownBoxList">
													<ul>
														<li>微软雅黑</li>
														<li>fangsong</li>
														<li>serif</li>
														<li>cursive</li>
														<li>monospace</li>
													</ul>
												</div>
											</div>
											<div class="watermark_configuration_fontSize_box muiPerformanceDropdownBox">
												<span>16</span>
												<i class="muiPerformanceDropdownBoxIcon"></i>
												<div class="muiPerformanceDropdownBoxList">
													<ul>
														<li>12</li>
														<li>13</li>
														<li>14</li>
														<li>15</li>
														<li>16</li>
														<li>17</li>
														<li>18</li>
														<li>19</li>
														<li>20</li>
													</ul>
												</div>
											</div>
											<div class="watermark_configuration_fontBold_box">
												<i></i>
											</div>
										</div>
										<div class="watermark_configuration_flex_box">
											<div class="watermark_configuration_pallet">
												<div name="font_color_item"  class="colorColorDiv">
													<div data-lui-mark="colorColor"></div>
												</div>
											</div>
											<div class="watermark_configuration_alpha_bar hide">
												<div class="watermark_configuration_alpha_box">
													<div class="watermark_configuration_scroll" id="watermark_configuration_scroll">
														<span class="watermark_configuration_bar" id="watermark_configuration_bar"></span>
														<span class="watermark_configuration_mask" id="watermark_configuration_mask"></span>
													</div>
												</div>
												<div class="watermark_configuration_alpha_btn drop" onselectstart="return false">
													<span>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.custom')}</span>
													<i class="watermark_configuration_icon_select"></i>
												</div>
												<div class="watermark_configuration_alpha_input_box drop">
													<p>${lfn:message('sys-profile:sys.profile.globalWaterMark.config.css.custom.opacity')}</p>
													<div class="watermark_configuration_alpha_number">
														<span>0</span>
														<em>%</em>
														<div class="watermark_configuration_alpha_adjust drop">
                                                    <span class="watermark_configuration_alpha_adjust_upBtn">
                                                        <i class="watermark_configuration_alpha_adjust_up"></i>
                                                    </span>
															<span class="watermark_configuration_alpha_adjust_downBtn">
                                                        <i class="watermark_configuration_alpha_adjust_down"></i>
                                                    </span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			</body>
			<html:hidden property="method_GET" />

			<input type="hidden" name="modelName"
				value="com.landray.kmss.sys.profile.model.GlobalWaterMarkConfig" />
			<input type="hidden" name="autoclose" value="true" />
				<kmss:authShow roles="SYS_PROFILE_GLOBAL_WATERMARK_SETTING">
					<div class="watermark_config_save_content">
						<div class="watermark_config_save_button" onclick="configChange();">
							${lfn:message('button.save')}
						</div>
					</div>
				</kmss:authShow>
		</html:form>

		<script type="text/javascript">
			// 调色板
			window.colorChooserHintInfo = {
				cancelText: '<bean:message key="button.cancel"/>',
				chooseText: '<bean:message key="button.ok"/>'
			};
			Com_IncludeFile("watermark.css", "${LUI_ContextPath}/sys/profile/resource/css/", 'css', true);
			Com_IncludeFile("jquery.js", null, "js");
			Com_IncludeFile("Sortable.js", "${LUI_ContextPath}/sys/profile/resource/js/", 'js', true);
			Com_IncludeFile("spectrum.js", Com_Parameter.ContextPath + 'resource/js/colorpicker/', 'js', true);
			Com_IncludeFile("spectrum.css", Com_Parameter.ContextPath + 'resource/js/colorpicker/css/', 'css', true);
			Com_IncludeFile("spectrumColorPicker.js");
			Com_IncludeFile("watermarkconfig.js", "${LUI_ContextPath}/sys/profile/resource/js/", 'js', true);
			Com_IncludeFile("watermark.js");


			function configChange() {
				waterMarkConfigUpdate();
				Com_Submit(document.sysAppConfigForm, 'update');
			}
		
		function autoChange() {
			var autoCategoryTag = "true" == $(
					"input[name='value\\\(waterMarkEnabled\\\)']")
					.val();

			if (autoCategoryTag) {
				//$('#advance').css('display', 'table-row');
				$('.waterMark').css('display', 'table-row');
			}else{
				//$('#advance').hide();
				$('.waterMark').hide();
			}
		}
		
		LUI.ready(function() {
			var validation = $KMSSValidation();//校验框架
			autoChange();
		});
		
		</script>
	</template:replace>
</template:include>
