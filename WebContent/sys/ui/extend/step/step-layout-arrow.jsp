<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

var submitText = "${lfn:message('sys-ui:ui.step.submit')}";
if(layout.parent.submitText) {
	submitText = layout.parent.submitText;
}
{$
	<div class="lui_step_tabs_arrow">
		<div class="lui_step_tabs_arrow_titleL">
			<div class="lui_step_tabs_arrow_titleR">
				<div class="lui_step_tabs_arrow_titleM">
					<ul data-lui-mark="step.title">
$}
					for(var i=0;i<layout.parent.contents.length;i++){
{$				
						<li data-lui-index="{% i %}" $} if(i==0){{$ class="current" $}} {$>
							<span class="no">{% i+1 %}</span>
							<span class="title">{% layout.parent.contents[i].title %}</span>
							<i class="after"></i>
							<i class="before"></i>
							<i class="border"></i>
						</li>
$}
					}
{$
					</ul>
				</div>
			</div>
		</div>
		
		<div class="lui_step_tabs_arrow_contentL">
			<div class="lui_step_tabs_arrow_contentR">
				<div class="lui_step_tabs_arrow_contentM">
					<div>
						<div class="lui_step_sheet_wrapper mb20">
							<div class="lui_step_sheet_topL">
								<div class="lui_step_sheet_topR">
									<div class="lui_step_sheet_topC"></div>
								</div>
							</div>
							<div class="lui_step_sheet_centreL">
								<div class="lui_step_sheet_centreR">
									<div class="lui_step_sheet_centreC">
										<div class="lui_step_sheet_content">
											<div class="lui_step_sheet_c_table" data-lui-mark='step.contents'>
											</div>
											<div class="lui_step_buttons_arrow_wrapper">
												<div class="lui_step_buttons_arrow_wrapperL">
													<div class="lui_step_buttons_arrow_wrapperR">
														<div class="lui_step_buttons_arrow_wrapperC">
															<div class="lui_step_buttons_arrow_btnList">
																<input type="button" class="btn_step_cur btn_step_white"$} if(!layout.parent.hasPre()){
																 {$ style="display: none;" $} } {$ value="${lfn:message('sys-ui:ui.step.pre')}" 
																 data-lui-mark="step.pre"/>
																
																<input type="button" class="btn_step_cur btn_step_color"$} if(!layout.parent.hasSubmit()){
																 {$ style="display: none;" $} } {$value="{% submitText %}" data-lui-mark="step.submit"/>
																
																<input type="button" class="btn_step_cur btn_step_white"$} if(!layout.parent.hasNext()){ 
																{$ style="display: none;" $} } {$value="${lfn:message('sys-ui:ui.step.next')}" 
																	data-lui-mark="step.next"/>
																
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
			</div>
		</div>
	</div>
$}