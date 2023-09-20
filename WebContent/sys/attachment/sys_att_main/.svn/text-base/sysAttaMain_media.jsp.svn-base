<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
@IMPORT
	url("${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/view.css")
	;
</style>


<div class="viewDiv">
	<!-- 图片浏览器展现 -->
	<table class="lui_atta_showPic" id='imgBrowser'>
		<tr>
			<td colspan="3">
				<div class="lui_atta_showPic_container">
					<div class="lui_atta_showPic_content">
						<div id='bigImg1' style="float: left;"></div>
						<div id='bigImg2' style="float: left;"></div>
						<div id='bigImg3' style="float: left;"></div>
					</div>
					<!-- 左箭头 -->
					<div>
						<a class="insignia_left left"></a>
					</div>
					<!-- 右剪头 -->
					<div>
						<a class="insignia_right right"></a>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td class="img_small_btnl">
				<div class="img_small_btnbar">
					&nbsp;<a href="javascript:void(0)"><i
						class="d_i_maintrig d_i_trigL"></i><i
						class="d_i_maintrig d_i_trig_coverL"></i></a>
				</div>
			</td>
			<td class="small_pic_list">
				<div id='smallImg' class='picList'>
					<ul id='picListItem'>
					</ul>
				</div>
			</td>
			<td class="img_small_btnr">
				<div class="img_small_btnbar" id="play_next">
					&nbsp; <a href="javascript:void(0)"> <i
						class="d_i_maintrig d_i_trigR"></i><i
						class="d_i_maintrig d_i_trig_coverR"></i></a>

				</div>
			</td>
		</tr>
	</table>

</div>
<script>
/**
 * 图片按比例缩放&垂直居中
 * 
 * @param {}
 *            obj
 * @param {}
 *            outerObj
 */
function drawImage(obj, outerObj) {
	var image = new Image(), iheight, iwidth;
	if (outerObj.currentStyle) {
		iheight = outerObj.currentStyle['height'];
		iwidth = outerObj.currentStyle['width'];
	} else {
		var style = document.defaultView.getComputedStyle(outerObj, null);
		iheight = style['height'];
		iwidth = style['width'];
	}

	iheight = parseInt(iheight);
	iwidth = parseInt(iwidth);
	image.src = obj.src;
	// 兼容chrome浏览器
	image.height = image.height == 0 ? obj.height : image.height;
	image.width = image.width == 0 ? obj.width : image.width;
	if (image.width > 0 && image.height > 0) {
		if (image.width / image.height >= iwidth / iheight) {
			if (image.width > iwidth) {
				obj.width = iwidth;
				obj.height = (image.height * iwidth) / image.width;
				// display is none ~~ obj.height = 0;
				obj.style.cssText = ['margin-top: ',
						(iheight - (image.height * iwidth) / image.width) / 2,
						'px'].join('');
			} else {
				obj.width = image.width;
				obj.height = image.height;
				obj.style.cssText = ['margin-top: ',
						(iheight - image.height) / 2, 'px;margin-left: ',
						(iwidth - image.width) / 2, 'px'].join('');
			}
		} else {
			if (image.height > iheight) {
				obj.height = iheight;
				obj.width = (image.width * iheight) / image.height;
				obj.style.cssText = ['margin-left: ',
						(iwidth - (image.width * iheight) / image.height) / 2,
						'px'].join('');
			} else {
				obj.width = image.width;
				obj.height = image.height;
				obj.style.cssText = ['margin-top: ',
						(iheight - image.height) / 2, 'px;margin-left: ',
						(iwidth - image.width) / 2, 'px'].join('');
			}
		}
	}
	obj.style.visibility = 'visible';
}
 </script>
<script>
	seajs.use(["lui/jquery", "sys/attachment/js/player"], function($, player) {
			$(function() {
				if(typeof(window.attachmentObject_${JsParam.fdKey}) !== "undefined") {
					player.Attachment_display(attachmentObject_${JsParam.fdKey},
							"${JsParam.modelId}", "${JsParam.modelName}");
				}
			});
	});
</script>