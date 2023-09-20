<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>		
	<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
		<c:param name="fdKey" value="${param.fdKey}"/>
		<c:param name="fdModelId" value="" />
		<c:param name="fdModelName" value="${ param.fdModelName }" />
		<c:param name="fdAttType" value="pic"/>
		<c:param name="fdMulti" value="false" />
		<c:param name="enabledFileType" value="*.gif;*.jpg;*.jpeg;*.png" />
	</c:import>
	<c:if test="${JsParam.changeImgSize eq 'true'}">
	<br>
	<div>
		${lfn:message('sys-attachment:sysAttachmentCrop.size')}：
		<xform:text property="aa" htmlElementProperties="onblur='changeImgSize(this)'" showStatus="edit" value="${empty JsParam.bWidth ? '120' : JsParam.bWidth}"></xform:text>
	</div>
	<br>
	</c:if>
	<div data-crop-id="${HtmlParam.fdKey}"> 
		<div class="crop_image_types_title">
			${lfn:message('sys-attachment:sysAttImageCrop.types')}
		</div>
		<div class="crop_image_container">
			<div class="crop_image_src">
				<img id="crop_${HtmlParam.fdKey}" src="" style="display: none;">
			</div>
			<div class="crop_image_area">
				<div class="crop_image_view crop_image_b">
					<img id="${HtmlParam.fdKey}_b" src="">
				</div>
				<div class="crop_image_view crop_image_m">
					<img id="${HtmlParam.fdKey}_m" src="">
				</div>
				<div class="crop_image_view crop_image_s">
					<img id="${HtmlParam.fdKey}_s" src="">
				</div>
			</div>
			<div class="crop_cue_area">
				<div class="crop_image_box_view crop_image_box_b"></div>
				<div class="crop_image_box_view crop_image_box_m"></div>
				<div class="crop_image_box_view crop_image_box_s"></div>
			</div>		
	   </div>
	   	<div class="crop_image_btn">
	   	    <div class="crop_submit"></div>
	   	    <div class="crop_cancel"></div>
	   	</div>        
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_b.fdModelName" value="${HtmlParam.fdModelName }" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_b.fdKey"     value="${HtmlParam.fdKey}_b" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_b.fdAttType" value="pic" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_b.fdMulti"   value="false" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_b.attachmentIds" />
		
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_m.fdModelName" value="${HtmlParam.fdModelName }" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_m.fdKey"     value="${HtmlParam.fdKey}_m" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_m.fdAttType" value="pic" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_m.fdMulti"   value="false" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_m.attachmentIds" />
		
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_s.fdModelName" value="${HtmlParam.fdModelName }" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_s.fdKey"     value="${HtmlParam.fdKey}_s" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_s.fdAttType" value="pic" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_s.fdMulti"   value="false" />
		<input type="hidden" name="attachmentForms.${HtmlParam.fdKey}_s.attachmentIds" />				
	</div>
	<link rel="stylesheet" href="${ LUI_ContextPath }/sys/attachment/Jcrop/css/jquery.Jcrop.css" type="text/css" />
	
	<script>
		seajs.use(['sys/attachment/Jcrop/css/imageCrop.css']);
		
		seajs.use(["sys/attachment/Jcrop/js/imageCrop",
		   			"lui/jquery",
		   			"sys/attachment/Jcrop/js/jquery.Jcrop.min"], function(imageCrop, $){ 
			
			var _options = {
					fdKey : "${JsParam.fdKey}",
					fdModelName : "${JsParam.fdModelName}",
					fdModelId : "${JsParam.fdModelId}",
					_$ : $,
					attObj : attachmentObject_${JsParam.fdKey},
					cropNum : "${JsParam.fdCropNum}",
					editMode : "${JsParam.fdEditMode}",
					fdMaxWidthHeight : "${JsParam.fdMaxWidthHeight}",
					bWidth : "${JsParam.bWidth}",
					bHeight : "${JsParam.bHeight}",
					mWidth : "${JsParam.mWidth}",
					mHeight : "${JsParam.mHeight}",					
					sWidth : "${JsParam.sWidth}",
					sHeight : "${JsParam.sHeight}"
				};
			imageCrop.buildCropImage(_options);
		});
	</script>
	
	<script>
		function changeImgSize(target){
			seajs.use(['lui/topic', 'lui/dialog'],function(topic, dialog){
				var size = target.value;

				// 数据校验120-300之间，整型
				if( size % 1 != 0 || ( 120 > size || 300 < size ))
					dialog.alert("${lfn:message('sys-attachment:sysAttachmentCrop.size.tips')}");
				else
					topic.publish('/lui/sys/attachment/crop/changeImgSize', size);
			})
		}
	</script>
	