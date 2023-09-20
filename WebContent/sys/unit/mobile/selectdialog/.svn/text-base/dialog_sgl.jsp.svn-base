<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@page import="com.landray.kmss.sys.unit.util.SysUnitUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String fdUnitId = request.getParameter("fdUnitId");
	boolean decUnit = SysUnitUtil.checkDecUnit(fdUnitId);
	boolean exchangeEnable = SysUnitUtil.getExchangeEnable();
	boolean showDec = false;
	boolean allUnitByDec = "true".equals(request.getParameter("allUnitByDec"));
	if(decUnit || allUnitByDec && exchangeEnable){
		showDec = true;
	}
	request.setAttribute("showDec", showDec);
%>
	<div id='_address_mul_search_{categroy.key}'
		data-dojo-type="sys/unit/mobile/selectdialog/DialogSearchBar" 
		data-dojo-props="orgType:{categroy.type},searchUrl:'{categroy.searchDataUrl}',key:'{categroy.key}',exceptValue:'{categroy.exceptValue}',height:'4rem',channel:'all'">
	</div>
	<div id="defaultView_{categroy.key}" data-dojo-type="dojox/mobile/View" >
		<c:if test="${param.showGroup eq 'true'}">
			<div data-dojo-type="mui/header/Header" class="muiHeaderNav">
				<c:choose>
					<c:when test="${param.showCate eq 'true'}">
						<c:choose>
							<c:when test="${showDec}">
								<div data-dojo-type="mui/nav/StaticNavBar" data-dojo-mixins="sys/unit/mobile/selectdialog/UnitCateNavBarMixin" data-dojo-props="key:'{categroy.key}'">
								</div>
							</c:when>
							<c:otherwise>
								<div data-dojo-type="mui/nav/StaticNavBar" data-dojo-mixins="sys/unit/mobile/selectdialog/NoDecCateNavBarMixin" data-dojo-props="key:'{categroy.key}'">
								</div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${showDec}">
								<div data-dojo-type="mui/nav/StaticNavBar" data-dojo-mixins="sys/unit/mobile/selectdialog/DialogNavBarMixin" data-dojo-props="key:'{categroy.key}'">
								</div>
							</c:when>
							<c:otherwise>
								<div data-dojo-type="mui/nav/StaticNavBar" data-dojo-mixins="sys/unit/mobile/selectdialog/NoDecDialogNavBarMixin" data-dojo-props="key:'{categroy.key}'">
								</div>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
	        </div>
	      </c:if>
         <div id="allUnitView_{categroy.key}" data-dojo-type="dojox/mobile/View" >
         		<div 
         			data-dojo-type="sys/unit/mobile/selectdialog/UnitCategoryPath" 
         			data-dojo-mixins="sys/unit/mobile/selectdialog/UnitAllCategoryPathMixin" 
         			data-dojo-props="key:'{categroy.key}',height:'3rem',channel:'all',visible:true,titleNode:'单位管理'">
        		</div>
        		<div  
				data-dojo-type="dojox/mobile/ScrollableView"
				data-dojo-mixins="mui/category/AppBarsMixin"
				data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'all'">
	        		<ul data-dojo-type="sys/unit/mobile/selectdialog/DialogList"
					data-dojo-mixins="sys/unit/mobile/selectdialog/DialogItemListMixin"
					data-dojo-props="isMul:{categroy.isMul},key:'{categroy.key}',dataUrl:'{categroy.listDataUrl}',fieldParam:'{categroy.fieldParam}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:2,exceptValue:'{categroy.exceptValue}',channel:'all'">
				</ul>
	        </div>
        </div>
        <div id="unitGroupView_{categroy.key}" data-dojo-type="dojox/mobile/View" >
        		<div 
         			data-dojo-type="sys/unit/mobile/selectdialog/UnitGroupCategoryPath" 
         			data-dojo-mixins="sys/unit/mobile/selectdialog/UnitGroupCategoryPathMixin" 
         			data-dojo-props="key:'{categroy.key}',height:'3rem',channel:'group',visible:true,titleNode:'机构组'">
        		</div>
        		<div  
				data-dojo-type="dojox/mobile/ScrollableView"
				data-dojo-mixins="mui/category/AppBarsMixin"
				data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'group'">
	        		<ul data-dojo-type="sys/unit/mobile/selectdialog/DialogList"
					data-dojo-mixins="sys/unit/mobile/selectdialog/UnitGroupListMixin"
					data-dojo-props="isMul:{categroy.isMul},key:'{categroy.key}',fieldParam:'{categroy.fieldParam}',allDataUrl:'{categroy.listDataUrl}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:2,exceptValue:'{categroy.exceptValue}',channel:'group'">
				</ul>
	        </div>
       	</div>
		<c:if test="${showDec}">
       	<div id="unitDecView_{categroy.key}" data-dojo-type="dojox/mobile/View" >
       			<div 
         			data-dojo-type="sys/unit/mobile/selectdialog/UnitDecCategoryPath" 
         			data-dojo-mixins="sys/unit/mobile/selectdialog/UnitDecCategoryPathMixin" 
         			data-dojo-props="key:'{categroy.key}',height:'3rem',channel:'dec',visible:true,titleNode:'交换单位'">
        		</div>
        		<div  
				data-dojo-type="dojox/mobile/ScrollableView"
				data-dojo-mixins="mui/category/AppBarsMixin"
				data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'dec'">
	        		<ul data-dojo-type="sys/unit/mobile/selectdialog/DialogList"
					data-dojo-mixins="sys/unit/mobile/selectdialog/UnitDecListMixin"
					data-dojo-props="isMul:{categroy.isMul},key:'{categroy.key}',dataUrl:'{categroy.decDataUrl}',fieldParam:'{categroy.fieldParam}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:2,exceptValue:'{categroy.exceptValue}',channel:'dec'">
				</ul>
	        </div>
       	</div>
		</c:if>
       	<div id="unitCateView_{categroy.key}" data-dojo-type="dojox/mobile/View" >
       			<div 
         			data-dojo-type="sys/unit/mobile/selectdialog/UnitAllCatePath" 
         			data-dojo-mixins="sys/unit/mobile/selectdialog/UnitAllCatePathMixin" 
         			data-dojo-props="key:'{categroy.key}',height:'3rem',channel:'cate',visible:true,titleNode:'所有分类'">
        		</div>
        		<div  
				data-dojo-type="dojox/mobile/ScrollableView"
				data-dojo-mixins="mui/category/AppBarsMixin"
				data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'cate'">
	        		<ul data-dojo-type="sys/unit/mobile/selectdialog/DialogList"
					data-dojo-mixins="sys/unit/mobile/selectdialog/UnitCateListMixin"
					data-dojo-props="isMul:{categroy.isMul},key:'{categroy.key}',allDataUrl:'{categroy.listDataUrl}',fieldParam:'{categroy.fieldParam}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:3,exceptValue:'{categroy.exceptValue}',channel:'cate'">
				</ul>
	        </div>
       	</div>
	</div>
<div  id="searchView_{categroy.key}"
				data-dojo-type="dojox/mobile/ScrollableView"
				data-dojo-mixins="mui/address/AddressViewScrollResizeMixin"
				data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'search'">
		       		<ul data-dojo-type="sys/unit/mobile/selectdialog/DialogList"
					data-dojo-mixins="sys/unit/mobile/selectdialog/DialogItemListMixin,sys/unit/mobile/selectdialog/DialogSearchListMixin"
					data-dojo-props="key:'{categroy.key}',searchUrl:'{categroy.searchDataUrl}',dataUrl:'{categroy.searchDataUrl}',fieldParam:'{categroy.fieldParam}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:3,exceptValue:'{categroy.exceptValue}',channel:'search',isMul:{categroy.isMul}">
				</ul>
       </div>

