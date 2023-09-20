<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<template:include ref="default.dialog">
<template:replace name="head">
</template:replace>
<template:replace name="content">
<c:if test="${! empty HtmlParam.key }">
  <c:set scope="request" value="${HtmlParam.key }" var="key"></c:set>
  <%
    //获取key值
    String key= (String)request.getAttribute("key");
    String[] keys=key.split(",");
    request.setAttribute("keys",keys);
    request.setAttribute("keysLength", keys.length);
  %>
</c:if>
<table>
		<!-- 如果是两个状态 -->
		<c:if test="${requestScope.keysLength==2}">
			<tr>
				<c:forEach items="${requestScope.keys}" var="item" varStatus="id">
				    <td>
						<div style="padding:12px;">
							<div style="position: relative;border:2px solid #dcdcdc;border-radius:3px;">
								<img id="detail" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/${item}.png" height="170px" width="265px">
								<img onclick='showMagnifyImageDetail("${item}")' style="position: absolute;top: 10px;right: 10px;width:18px;height:18px;cursor: pointer;" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/btn_icon_magnifier.png">
							</div>
							<c:set var="itemSp" scope="request" value="${item}"/>
							<%
								//得到最后的状态位
								String itemSp=(String)request.getAttribute("itemSp");
								String endStatus=itemSp.substring(itemSp.length()-1);
								String key = MultiLangTextGroupTag.getUserLangKey();
								if("en-US".equals(key)){
									endStatus = itemSp.substring(itemSp.length()-7,itemSp.length()-6);
								}
								request.setAttribute("endStatus",endStatus);
							%>
							<div style="margin-top:10px;">
								<!-- 开启状态 -->
								<c:if test="${endStatus==1}">
									<span style="color:#fff;padding:6px;background: #4bd71e;border-radius:3px;"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.open"/></span>
								</c:if>
								<!-- 关闭状态 -->
								<c:if test="${endStatus==0}">
									<span style="color:#fff;padding:6px;background: #cdcdcd;border-radius:3px;"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.close"/></span>
								</c:if>
							</div>
						</div>
					</td>
				</c:forEach>
			</tr>
		</c:if>
		<!-- 如果是四个状态 -->
		<c:if test="${requestScope.keysLength ==4}">
			<tr>
				<c:forEach items="${requestScope.keys}" var="item" varStatus="id" end="1">
				    <td>
						<div style="padding:12px;">
							<div style="position: relative;border:2px solid #dcdcdc;border-radius:3px;">
								<img id="detail" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/${item}.png" height="170px" width="265px">
								<img onclick='showMagnifyImageDetail("${item}")' style="position: absolute;top: 10px;right: 10px;width:18px;height:18px;cursor: pointer;" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/btn_icon_magnifier.png">
							</div>
							<c:set var="itemSp" scope="request" value="${item}"/>
							<%
								//得到最后的状态位
								String itemSp=(String)request.getAttribute("itemSp");
								String endStatus=itemSp.substring(itemSp.length()-1);
								String key = MultiLangTextGroupTag.getUserLangKey();
								if("en-US".equals(key)){
									endStatus = itemSp.substring(itemSp.length()-7,itemSp.length()-6);
								}
								request.setAttribute("endStatus",endStatus);
							%>
							<div style="margin-top:10px;">
								<!-- 开启状态 -->
								<c:if test="${endStatus==1}">
									<span style="color:#fff;padding:6px;background: #4bd71e;border-radius:3px;"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.open"/></span>
								</c:if>
								<!-- 关闭状态 -->
								<c:if test="${endStatus==0}">
									<span style="color:#fff;padding:6px;background: #cdcdcd;border-radius:3px;"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.close"/></span>
								</c:if>
							</div>
						</div>
					</td>
				</c:forEach>
			</tr>
			<tr>
				<c:forEach items="${requestScope.keys}" var="item" varStatus="id" begin="2">
				    <td>
						<div style="padding:12px;">
							<div style="position: relative;border:2px solid #dcdcdc;border-radius:3px;">
								<img id="detail" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/${item}.png" height="170px" width="265px">
								<img onclick='showMagnifyImageDetail("${item}")' style="position: absolute;top: 10px;right: 10px;width:18px;height:18px;cursor: pointer;" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/btn_icon_magnifier.png">
							</div>
							<c:set var="itemSp" scope="request" value="${item}"/>
							<%
								//得到最后的状态位
								String itemSp=(String)request.getAttribute("itemSp");
								String endStatus=itemSp.substring(itemSp.length()-1);
								String key = MultiLangTextGroupTag.getUserLangKey();
								if("en-US".equals(key)){
									endStatus = itemSp.substring(itemSp.length()-7,itemSp.length()-6);
								}
								request.setAttribute("endStatus",endStatus);
							%>
							<div style="margin-top:10px;">
								<!-- 开启状态 -->
								<c:if test="${endStatus==1}">
									<span style="color:#fff;padding:6px;background: #4bd71e;border-radius:3px;"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.open"/></span>
								</c:if>
								<!-- 关闭状态 -->
								<c:if test="${endStatus==0}">
									<span style="color:#fff;padding:6px;background: #cdcdcd;border-radius:3px;"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.close"/></span>
								</c:if>
							</div>
						</div>
					</td>
				</c:forEach>
			</tr>
		</c:if>
	</table>
	
	<script>
		//#68650-开始
		//得到当前浏览器的类型是否为ie8
		var isIE = function(ver){
			ver = ver || '';
			var b = document.createElement('b')
			b.innerHTML = '<!--[if IE ' + ver + ']>1<![endif]-->'
			return b.innerHTML === '1'
		};
		// 图片弹窗重新绘制
 		window.showMagnifyImageDetail=function(key){
	 		//得到父级文档对象
	 		var documentParent=window.parent.document;
	 		//得到父窗口mask层
	 		var container=window.parent.document.getElementById('container');
	 		if(container!=null){
	 			container.style.display='block';
	 		}
	 	
	 		//隐藏原本弹窗
	 		var dialog=$(window.parent.document).find('.lui_dialog_main')[0];
	 		if(dialog!=null){
	 			dialog.style.display='none';
	 		}
	 		//创建图片
	 		var image=documentParent.createElement("img");
	 		image.setAttribute("style","width: 100%;height: 100%;");
	 		image.setAttribute("src","${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/"+key+".png");
	 		
	 		//创建图片父容器div
	 		var content=documentParent.createElement("div");
	 		//获取图片父容器的大小
	 		var imageHeight=image.height;
	 		var imagewidth=image.width;
	 		var imageRealHeight = documentParent.body.clientHeight * 0.6;
	 		var imageRealWidth = documentParent.body.clientWidth * 0.6;
	 		//通过创建的图片配置父容器的高度
	 		if(imageHeight!=null&&imageHeight!=0&&imagewidth!=null&&imagewidth!=0){
	 			content.setAttribute("style","width:"+imageRealWidth+"px;height:"+imageRealHeight+"px;position:absolute;left:50%; top:50%;margin-left:-"+imageRealWidth/2+"px;margin-top:-"+imageRealHeight/2+"px");
	 		}
	 		
	 		//创建退出按钮
	 		var btnClose=documentParent.createElement("img");
	 		btnClose.setAttribute("style","position: absolute;top: 0;right: -36px;cursor: pointer;");
	 		//ie8兼容性图片设置
	 		if(isIE(8)){
	 			btnClose.setAttribute("src","${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/btn_icon_close_ie8.png");
	 		}else{
	 			btnClose.setAttribute("src","${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/btn_icon_close.png");
	 		}
	 		
	 		btnClose.onclick = CloseBtn;
	 		
	 		//添加控件
	 		content.appendChild(image);
	 		content.appendChild(btnClose);
	 		container.appendChild(content);
	 		
	 		//btnClose按钮点击事件
	 		function CloseBtn(){
	 			var container=window.parent.document.getElementById('container');
	 			//隐藏父窗口mask层
	 			if(container!=null&&'none'!=container.style.display){
	 				container.style.display='none';
	 			}
	 			//从父窗口mask层移除图片图层
	 			if(container!=null&&content!=null){
	 				container.removeChild(content);
	 			}
	 			//显示弹窗
	 			var dialog=$(window.parent.document).find('.lui_dialog_main')[0];
	 			if(dialog!=null&&'none'==dialog.style.display){
	 				dialog.style.display='block';
	 			}
	 		}
		};
		//#68650-结束
 	</script>
</template:replace>
</template:include>