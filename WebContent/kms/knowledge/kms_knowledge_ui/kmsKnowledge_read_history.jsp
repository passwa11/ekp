<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
<!--
.lui_list_nav_frame_history{}
-->
</style>
<div class="lui_list_nav_frame_history">
			<div class="lui_accordionpanel_content_frame">
				<div class="lui_accordionpanel_header_l">
					<div class="lui_accordionpanel_header_r">
						<div class="lui_accordionpanel_header_c">
							<div class="lui_accordionpanel_nav_l">
								<div class="lui_accordionpanel_nav_r">
									<div class="lui_accordionpanel_nav_c">
										<span class="lui_accordionpanel_nav_text">
											<span class="lui_tabpanel_navs_item_title">
													<ui:combin ref="menu.nav.simple">
														<ui:varParam name="source">
															<ui:source type="Static">
											  					[
													  				{
													  					"text" : "${lfn:message('kms-knowledge:kmsKnowledge.my.readHistory') }",
																		"href" :  "/readInfo",
																		"router" : true
													  				}
												  				]
								  							</ui:source>
														</ui:varParam>
													</ui:combin>
											</span>
										</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
