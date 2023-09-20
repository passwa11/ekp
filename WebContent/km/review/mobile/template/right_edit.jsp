<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="mui/view/DocScrollableView" id="rightView" style="background-color: #F5F6FB">
	<div class="mui_kmReview_right">
		<div class="mui_kmReview_authReader">
			<span><bean:message bundle="km-review" key="kmReviewTemplate.authReaders" /></span><br><br>
			<xform:radio property="fdAuthReader" mobile="true" alignment="V" showStatus="edit" value="1" onValueChange="authReaderValueChange">
				<xform:simpleDataSource value="1"><bean:message bundle="km-review" key="kmReviewTemplate.right.all" /></xform:simpleDataSource>
				<xform:simpleDataSource value="2"><bean:message bundle="km-review" key="kmReviewTemplate.right.person" /></xform:simpleDataSource>
			</xform:radio>
			<div class="mui_kmReview_authReader_address">
				<xform:address mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" mobile="true" showStatus="edit" htmlElementProperties="id='templateAuthReader'"></xform:address>
			</div>
		</div>
		<div class="mui_kmReview_split"></div>
		<div class="mui_kmReview_authEditor">
			<span><bean:message bundle="km-review" key="kmReviewTemplate.authEditors" /></span><br><br>
			<xform:radio property="fdAuthEditor" mobile="true" alignment="V" showStatus="edit" value="1" onValueChange="authEditorValueChange">
				<xform:simpleDataSource value="1"><bean:message bundle="km-review" key="kmReviewTemplate.right.none" /></xform:simpleDataSource>
				<xform:simpleDataSource value="2"><bean:message bundle="km-review" key="kmReviewTemplate.right.person" /></xform:simpleDataSource>
			</xform:radio>
			<div class="mui_kmReview_authEditor_address">
				<xform:address mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" mobile="true" showStatus="edit" htmlElementProperties="id='templateAuthEditor'"></xform:address>
			</div>
		</div>
	</div>
	<%-- 操作按钮展示区域  --%>
	<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" <c:if test="${'false' ne kmReviewTemplateForm.fdIsMobileCreate}">data-dojo-props='fill:"grid"'</c:if>>
		<%-- 下一步  --%> 	
	  	<li data-dojo-type="mui/tabbar/TabBarButton" class="mainTabBarButton"
	  		data-dojo-props='colSize:2,transition:"slide"' onclick="submitForm();">
	  		<bean:message key="button.submit" /></li>
	</ul>
	<script>
		require(["dojo/query", "dojo/dom-style","dijit/registry"], function(query, domStyle, registry) {
			window.authReaderValueChange = function(value){
				var addressDom = query(".mui_kmReview_authReader_address")[0];
				if(value=='1'){
					domStyle.set(addressDom,{display:'none'});
					var myWgt = registry.byId("templateAuthReader");
                    myWgt.set("curIds", "");
                    myWgt.set("curNames", "");
				}else{
					domStyle.set(addressDom,{display:'block'});
				}
			}
			window.authEditorValueChange = function(value){
				var addressDom = query(".mui_kmReview_authEditor_address")[0];
				if(value=='1'){
					domStyle.set(addressDom,{display:'none'});
					var myWgt = registry.byId("templateAuthEditor");
                    myWgt.set("curIds", "");
                    myWgt.set("curNames", "");
				}else{
					domStyle.set(addressDom,{display:'block'});
				}
			}
			var ctime = new Date().getTime();
			window.submitForm = function(){
				var nowTime = new Date().getTime();
			    var clickTime = ctime;
			    if(nowTime - clickTime < 500){
			       return false;
			    }
			    ctime = nowTime;
			    ${param.onClickSubmitButton}
			}
		});
	</script>
</div>
