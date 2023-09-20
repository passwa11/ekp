<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>

	<%
		int type = com.landray.kmss.sys.mobile.util.MobileUtil.getClientType(new com.landray.kmss.common.actions.RequestContext(request));
		if(type<=-1){
			request.setAttribute("__isPC",true);
		}
	%>
	<c:if test="${__isPC eq true }">
		<script>
			Com_IncludeFile("jquery.js");
			var httpUrls = []; // 预览时的图片路径数组
			$(document).ready(function(){
				var domNode = $('.muiAuditLog .auditHandlerImgBox');
				var auditImgGroup = "audit_img_${param.fdKey}";
				// 根据fdId查找图片对应的Dom对象，并绑定点击事件
				domNode.find("img[auditImgGroup='"+auditImgGroup+"']").each(function() {
					var src = $(this).attr('src');
					httpUrls.push({value:this.src});
					__resizeDom(this);
                    __resizeImg(this);
				});
			});
			
			//增加图片逻辑，防止图片模糊（若图片的大小小于100，就保持原图效果）
			var __resizeImg = function(item){
				var $imgObj = $(item);
				//获取原生图片宽度
				var img = new Image();
				img.src = $imgObj.attr("src");
				var orgWidth = img.width;
				var maxWidth = $imgObj.width();
				if(orgWidth < maxWidth && orgWidth != 0) {
					$imgObj.attr("width",orgWidth+"px");
				}
			}
			
			/** 绑定图片onclick点击预览 **/
			var __resizeDom = function(item){
				var $item = $(item);
				$item.click(function(){
					var previewUrl = item.src; // 图片预览URL
					var downloadUrl = previewUrl.replace("&open=1",""); // 图片下载URL（ open:1 表示查看、open:null 表示下载） 
					var datas = { data:httpUrls, value:previewUrl, valueType:'url', downloadUrl:downloadUrl, imageBgColor:'#fff' };
					var _previewImage = null;
					if(window.parent.previewImage){
						 _previewImage = window.parent.previewImage;
					}else if(window.previewImage){
						 _previewImage = window.previewImage;
					}
					_previewImage({
						data:datas
					});
				});
			}
		</script>
	</c:if>

