<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
	<div class="field_label field_label_basic" style="cursor:pointer;">
		<div class="field_label_desc">
			<i class="label_icon_basic"></i>
			<p>${lfn:message('sys-modeling-base:kmReviewDocumentLableName.baseInfo')}</p>
		</div>
		<div class="item_icon item_expand"></div>
	</div>
	<div class="field_value base_info" style="display:none;">
		<div class="content_body">
			<div class="field_item_value shrink">
				<div class="field_item_content">
					<div class="content_wrap">
						<%--标题--%>
						<div class="content_item_form_element content_input">
							<p class="content_body_title">${lfn:message('sys-modeling-base:modelingAppMobile.docSubject')}</p>
							<div class="content_opt" style="display:inline-block;width:92%">
								<input name="docSubject" type="text" title="${lfn:message('sys-modeling-base:modelingAppMobile.docSubject')}" placeholder="${lfn:message('sys-modeling-base:modeling.enter.title')}"
									value="${modelingAppMobileForm.docSubject}" data-validate="required" />
							</div>
							<span class="txtstrong">*</span>
							<p class="content_opt_info"></p>
						</div>
						<%--排序号--%>
						<div class="content_item_form_element content_input">
							<p class="content_body_title">${lfn:message('sys-modeling-base:modelingAppNav.fdOrder')}</p>
							<div class="content_opt" style="width:92%">
								<input name="fdOrder" type="text" onblur="checkOrderValue(this)" placeholder="${lfn:message('sys-modeling-base:modeling.enter.sort')}"
									value="${modelingAppMobileForm.fdOrder}"/>
							</div>
							<p class="content_opt_info"></p>
						</div>
						<%--    可访问者--%>
						<div class="content_item_form_element content_input">
							<p class="content_body_title">${lfn:message('sys-modeling-base:modelingAppNav.fdAuthReaders')}</p>
							<div>
								<xform:address textarea="true" mulSelect="true"
									propertyId="authReaderIds" propertyName="authReaderNames"
									style="width: 96%;height:90px;"></xform:address>
								<div style="color: #999999;font-size: 12px;">${lfn:message('sys-modeling-base:modeling.empty.everyone.access')}</div>
							</div>
						</div>
						<%--    访问地址--%>
						<div class="content_item_form_element content_input auto_read_address">
							<p class="content_body_title">${lfn:message('sys-modeling-base:modeling.address')}</p>
							<div class="content_opt" style="display:inline-block;width:80%;">
								<input name="__fdUrl" type="text" readonly placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}">
							</div>
							<ui:button text="${lfn:message('sys-modeling-base:modeling.Go.to')}" styleClass="lui_toolbar_btn_def" onclick="openMobileIndex(this);"></ui:button>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
<script>
	// 基础数据的初始化
	function baseInfoInit(params){
		seajs.use(["lui/jquery", "lui/util/env"],function($, env){
			// 初始化访问地址
			var mobileIndexUrl = env.fn.formatUrl(params.tempUrl, true);
			$("[name='__fdUrl']").val(mobileIndexUrl);
			
			// 添加伸展事件
			$(".field_label_basic").on("click",function(){
				$('.base_info').slideToggle("normal");
	    		$('.field_label').toggleClass('shrink');
			});
			
			// 简单的一个必填校验框架
			$(".base_info").delegate("[data-validate]","change",function(event){
				var val = this.value || "";
				var isNull = val.trim().length === 0 ? true : false;
				if(isNull){
					$(this).closest(".content_item_form_element").find(".content_opt_info").html("${lfn:message('sys-modeling-base:modeling.content.cannot.blank')}");
					$(this).closest(".content_item_form_element").find(".content_opt").css("border-color","#FF4444");
				}else{
					$(this).closest(".content_item_form_element").find(".content_opt_info").html("");
					$(this).closest(".content_item_form_element").find(".content_opt").css("border-color","#DFE3E9");
				}
			});
			
			// 校验标题是否有值，如果有值，则收起基础信息
			var docSubject = $("[name='docSubject']").val() || "";
			if(docSubject.trim().length === 0){
				$('.base_info').show();
				$('.field_label').addClass('shrink');
			}
		})
	}
	
	function openMobileIndex(dom){
		var fdUrl = $(dom).closest(".content_item_form_element").find("[name='__fdUrl']").val();
		Com_OpenWindow(fdUrl, "_blank");
	}
	function checkOrderValue(obj){
		var fdOrderval=obj.value;
		if (fdOrderval){
			if(!/^-?\d+$/.test(fdOrderval)) {
				$(obj).closest(".content_item_form_element").find(".content_opt_info").html("${lfn:message('sys-modeling-base:modeling.sortumber.mustbe.integer')}");
				$(obj).closest(".content_item_form_element").find(".content_opt").css("border-color","#FF4444");
			}else{
				$(obj).closest(".content_item_form_element").find(".content_opt_info").html("");
				$(obj).closest(".content_item_form_element").find(".content_opt").css("border-color","#DFE3E9");
			}
		}
	}
	
	// 基础数据的校验
	function baseInfoValidate(){
		var pass = true;
		pass = doBaseInfoValidate("docSubject");
		return pass;
	}
	//排序号的校验
	function checkOrder(){
		var pass = true;
		var $dom = $("[name='fdOrder']");
		var fdOrderValue=$dom.val();
		if(fdOrderValue){
			//判断排序号是否是整数
			if(!/^-?\d+$/.test(fdOrderValue)) {
				$dom.closest(".content_item_form_element").find(".content_opt_info").html("${lfn:message('sys-modeling-base:modeling.sortumber.mustbe.integer')}");
				$dom.closest(".content_item_form_element").find(".content_opt").css("border-color","#FF4444");
				pass = false;
			}
		}else{
			pass = true;
		}
		return pass;
	}
	
	function doBaseInfoValidate(name){
		var pass = true;
		var $dom = $("[name='"+ name +"']");
		var value = $dom.val() || "";
		pass = value.trim().length === 0 ? false : true;
    	if(!pass){
    		seajs.use(["lui/jquery", "lui/util/str"],function($, strutil){
    			var msg = "${lfn:message('sys-modeling-base:modeling.title.cannot.null')}";
    			var title = $dom.attr("title") || "${lfn:message('sys-modeling-base:modeling.form.Content')}";
    			msg = strutil.variableResolver(msg,{
    				title : title
    			});
    			$dom.closest(".content_item_form_element").find(".content_opt_info").html(msg);
				$dom.closest(".content_item_form_element").find(".content_opt").css("border-color","#FF4444");
				// 展开
				if($(".base_info").css("display") == "none"){
					$(".field_label_basic").trigger($.Event("click"));
				}
    		});

    	}
    	return pass;
	}
</script>