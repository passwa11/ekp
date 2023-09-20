<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

var submitText = "${lfn:message('sys-ui:ui.step.submit')}";
if(layout.parent.submitText) {
	submitText = layout.parent.submitText;
}
// 设定当前页面步骤类型为fixed
window.lui_step_tabs_type = 'fixed';
// 步骤是否已经悬浮固定
window.lui_step_tabs_fixed_flag = false;

// 初始化悬浮固定步骤的备份
function initFixedHtml(){
    var pathFixed = $(".lui_form_path_frame_fixed_inner");
    var stepFixed = $(".lui_step_tabs_fixed_titleL");
	if (!pathFixed || pathFixed.length == 0 || !stepFixed || stepFixed.length == 0){
		// 页面加载速度不同，延迟操作
		setTimeout(function(){initFixedHtml();}, 300);
		return;
	}
	// 复制元素
	$(".lui_form_path_frame_fixed_inner").append($(".lui_step_tabs_fixed .lui_step_tabs_fixed_titleL").clone());
	var copy_fixed_html = $(".lui_form_path_frame_fixed_inner .lui_step_tabs_fixed_titleL");
	copy_fixed_html.hide();
	// 设置缩进
	if ($(".lui-fm-flexibleL-inner").length > 0){
		copy_fixed_html.css("padding-left", $(".lui-fm-flexibleL-inner").css("padding-left"));
	}
	// 查找绑定事件
	var newStepLi = $(".lui_form_path_frame_fixed_inner .lui_step_tabs_fixed_titleL li");
	var oldStepLi = $(".lui_step_tabs_fixed .lui_step_tabs_fixed_titleL li");
	if (newStepLi.length > 0){
		for(var i = 0; i < newStepLi.length; i++){
			$(newStepLi[i]).bind("click",function(){
				// 指向对应的真实的步骤上
				$(oldStepLi[$(this).attr("data-lui-index")]).click()
			});
		}
	}
	// 设置滚动效果
	if ($(".lui-fm-flexibleL-inner").length > 0){
		$(".lui-fm-flexibleL-inner").scroll(function() {
			window.checkStepScroll();
		});
	}
	// 有时候页面加载完成，滚动条在中间
	window.checkStepScroll();
}
// 检查滚动的效果
window.checkStepScroll = function(){
	if ($(".lui_step_tabs_fixed").length == 0){
		return;
	}
	var stepMainTop = $(".lui_step_tabs_fixed").offset().top;
	if (window.lui_step_tabs_fixed_flag == false && stepMainTop < 40){
		window.lui_step_tabs_fixed_flag = true;
		$(".lui_toolbar_frame_float_mark").height($(".lui_toolbar_frame_float_mark").height() + 26)
		$(".lui_form_path_frame_fixed_inner .lui_step_tabs_fixed_titleL").show();
		if ($("#spreadRight").length > 0){
			$("#spreadRight").css("margin-top", "15px");
		}
	} else if (window.lui_step_tabs_fixed_flag == true && stepMainTop > 39){
		window.lui_step_tabs_fixed_flag = false;
		$(".lui_toolbar_frame_float_mark").height($(".lui_toolbar_frame_float_mark").height() - 26)
		$(".lui_form_path_frame_fixed_inner .lui_step_tabs_fixed_titleL").hide();
		if ($("#spreadRight").length > 0){
			$("#spreadRight").css("margin-top", "0px");
		}
	}
}

initFixedHtml();


{$
	<div class="lui_step_tabs_fixed">
		<div class="lui_step_tabs_fixed_titleL">
			<div class="lui_step_tabs_fixed_titleR">
				<div class="lui_step_tabs_fixed_titleM">
					<ul data-lui-mark="step.title">
$}
					for(var i=0;i<layout.parent.contents.length;i++){
{$				
						<li data-lui-index="{% i %}" $} if(i==0){{$ class="current lui_text_primary" $}} {$>
							<div class="no $}if(i==0){{$ com_bgcolor_d $}}{$"><span>{% i+1 %}</span></div>
							<div class="title">{% layout.parent.contents[i].title %}</div>
$}							if(i < (layout.parent.contents.length - 1)){
{$							<div class="tri">&nbsp;</div>
$}							}
{$						</li>
$}
					}
{$
					</ul>
				</div>
			</div>
		</div>
		
		<div class="lui_step_tabs_fixed_contentL">
			<div class="lui_step_tabs_fixed_contentR">
				<div class="lui_step_tabs_fixed_contentM">
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
											<div class="lui_step_buttons_fixed_wrapper">
												<div class="lui_step_buttons_fixed_wrapperL">
													<div class="lui_step_buttons_fixed_wrapperR">
														<div class="lui_step_buttons_fixed_wrapperC">
															<div class="lui_step_buttons_fixed_btnList">
																<input type="button" class="btn_step_cur btn_step_white"$} if(!layout.parent.hasPre()){
																 {$ style="display: none;" $} } {$ value="${lfn:message('sys-ui:ui.step.pre')}" 
																 data-lui-mark="step.pre"/>
																
																<input type="button" class="btn_step_cur btn_step_white"$} if(!layout.parent.hasSubmit()){
																 {$ style="display: none;" $} } {$value="{% submitText %}" data-lui-mark="step.submit"/>
																
																<input type="button" class="btn_step_cur btn_step_color com_bgcolor_d"$} if(!layout.parent.hasNext()){ 
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