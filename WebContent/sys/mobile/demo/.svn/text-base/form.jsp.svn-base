<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<c:set var="tiny" value="true" scope="request" />
<c:set var="showStatus" value="${empty param.showStatus? 'edit' : param.showStatus}" ></c:set>
<template:include ref="mobile.simple" tiny="true" isNative="true">
  <template:replace name="title">移动端表单控件demo</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-view.js" cacheType="md5" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/view-tiny.css">
	    <style>
	      .headerContainer{
	        display: none;
			position: fixed;
			width: 100%;
			z-index: 8;
			background-color: #FFFFFF;	        
	      }
	      .tabHeader{      
		    position: relative;
		    padding-left: 10rem;
	      }
		  .tabHeader:after {
		    content: " ";
		    position: absolute;
		    z-index: 10;
		    bottom: 0;
		    left: 0;
		    right: 0;
		    border-bottom: .1rem solid #eee;
		    -webkit-transform-origin: 0 0;
		    transform-origin: 0 0;
		    -webkit-transform: scaleY(.5);
		    transform: scaleY(.5);
	      }	 
	      .tabHeader>.tabsContainer{
		    height: 3rem;
		    width: 100%;
		    display: table;
	      }
	      .tabHeader>.tabsContainer>div{
	         display: table-cell;
	         color: #2a304a; 
	         font-size: 14px;
	         text-align: center;
             vertical-align: middle;
	      }    
	      .tabHeader>.currentView{
             position: absolute;
             color: #FFBF86;
             font-size: 14px;
		     top: 0;
		     left: 1rem;
		     height: 3rem;
		     line-height: 3rem;
		     font-weight: bold;
	      }
	      h2.categoryTitle:first-child{
	        margin-top: 4rem;
	      }
	      h2.categoryTitle{ 
	        position: relative;
	        margin-top: 1.6rem;
            color: #2a304a;
            background-color: #F0F8FF;
		    padding-top: .6rem;
		    padding-bottom: .6rem;
		    padding-left: 1.2rem;
		    letter-spacing: .4rem;
	      }
	      h3.categoryTitle{
	        position: relative;
	        margin-top: 1.6rem;
            color: #2a304a;
            background-color: #FFF5EE;
		    padding-top: .6rem;
		    padding-bottom: .6rem;
		    padding-left: 1.2rem;
		    letter-spacing: .4rem;	      
	      }
	      h2.categoryTitle>i.fontmuis,
	      h3.categoryTitle>i.fontmuis{
	        position: absolute;
		    right: 1.2rem;
		    top: 1rem;
		    font-size: 1rem;
	      }
	      footer{
	         margin-top: 1rem;
             height: 3rem;
             line-height: 3rem;
             color: #FFFFFF;
			 background-color: #C1C1C1;
			 font-size: 14px;
			 text-align: center;
	      }
	      .muiFormContent{
	     	 padding: 0 1.2rem;
	      }
		  input[type='button'] {
		    border: 0;
		    padding: .4rem 1rem;
		    border-radius: 1.7rem;
		    color: var(--muiFontColor);
		    background-color: rgba(153, 159, 183, 0.08);
		  }	      
	    </style>
		<script type="text/javascript">
		window.onload = function(){
			require(['dojo/query','dojo/on','dojo/dom-attr','dojo/dom','dojo/dom-class','dojo/topic','mui/dialog/Tip',"mui/dialog/BarTip"],function(query, on, domAttr, dom, domClass, topic, tip, BarTip){				  
				 // 延迟显示顶部 header Tab 切换栏，避免DocScrollableView计算高度时将该内容计算进去
				 setTimeout(function(){
					query(".headerContainer")[0].style.display = "block";
				 },600);
				 
			    var _localStorageKey = "_sys_mobile_from_demo_data";
				var showStatus = "${pageScope.showStatus}";
				var categoryTitleDoms = query(".categoryTitle");
				
				/**
				* 获取表单Demo页面存储在 localStorage 中的数据（该数据记录着各个分类的展开或收缩显示状态）
				* @return 返回表单Demo存储localStorage数据
				*/ 
				var _getFormDemoLocalStorageData = function(){
					var _dataStr = window.localStorage.getItem(_localStorageKey);
					var _data = {};
					if(_dataStr){
						_data = JSON.parse(_dataStr);
					}
                    for(var i=0;i<categoryTitleDoms.length;i++){
                    	var categoryTitleDom = categoryTitleDoms[i];
                    	var panelId = domAttr.get(categoryTitleDom,"category_type");
                    	if(!_data[panelId]){
                    		_data[panelId] = {"${pageScope.showStatus}":"expand"};
                    	}else{
                    		if(!_data[panelId][showStatus]){
                    			_data[panelId][showStatus] = "expand";
                    		}
                    	}
                    }
                    window.localStorage.setItem(_localStorageKey,JSON.stringify(_data));
                    return _data;
				}
				 
				/**
				* 设置分类表单内容展开或收缩显示状态
				* @param categoryTitleDom 分类标题DOM
				* @param status 状态（expand：展开、  collapse：收缩）				
				*/	
				 var _setCategoryDisplayState = function(categoryTitleDom,status){
					 var panelId = domAttr.get(categoryTitleDom,"category_type");
					 var panelDom = dom.byId(panelId);
					 var iconDom = query("i.fontmuis",categoryTitleDom)[0];
					 if(status=="collapse"){ // 收起
						 panelDom.style.display = "none";
						 domClass.remove(iconDom,"muis-drop-up");
						 domClass.add(iconDom,"muis-drop-down");                             
				     }else if(status=="expand"){ // 展开
						 panelDom.style.display = "block";
						 domClass.remove(iconDom,"muis-drop-down");
						 domClass.add(iconDom,"muis-drop-up");
					 }
				 }
				
				 /**
				 * 设置Demo页面所有分类表单内容展开或收缩显示状态				
				 */				
				 var _initDemoCategoryDisplayState = function(){
					 var _data = _getFormDemoLocalStorageData();
                     for(var i=0;i<categoryTitleDoms.length;i++){
                    	var categoryTitleDom = categoryTitleDoms[i];
                    	var panelId = domAttr.get(categoryTitleDom,"category_type");
                    	var status = _data[panelId][showStatus];
                    	_setCategoryDisplayState(categoryTitleDom,status)
                     }
				 }
				 
				 // 绑定分类标题点击事件（触发分类表单内容展开或收缩）
				 on(categoryTitleDoms,"click",function(){
					 var categoryTitleDom = this;
					 var panelId = domAttr.get(categoryTitleDom,"category_type");
					 var panelDom = dom.byId(panelId);
					 var _data = _getFormDemoLocalStorageData();
					 var status = "";
					 if(_data[panelId][showStatus]=="expand"){ // 展开状态转收缩状态
						 status = "collapse";
					 }else if (_data[panelId][showStatus]=="collapse"){ // 收缩状态转展开状态
						 status = "expand";
					 }
					 _data[panelId][showStatus] = status;
					 window.localStorage.setItem(_localStorageKey,JSON.stringify(_data));
					 _setCategoryDisplayState(categoryTitleDom,status);
				 });
				 
				 // 初始化Demo页面所有分类表单内容展开或收缩显示状态
				 _initDemoCategoryDisplayState();

			});			
		}
		</script>          
	</template:replace>  
  <template:replace name="content">
    <c:import url="/sys/mobile/template/com_head.jsp"></c:import>
    
    <%----------------------------------------------------- 切换视图Tab  Start-----------------------------------------------------%> 
    <div class="headerContainer">
	    <div class="tabHeader">
	       <div class="tabsContainer">
			<c:choose>
			    <c:when test="${pageScope.showStatus==null||pageScope.showStatus eq 'edit'}">
			         <c:set var="currentSubject" value="编辑" ></c:set>
			       	 <div onclick="window.location.href='<%=request.getContextPath()%>/sys/mobile/demo/form.jsp?showStatus=view'">切换查看视图</div>
		             <div onclick="window.location.href='<%=request.getContextPath()%>/sys/mobile/demo/form.jsp?showStatus=readOnly'">切换只读视图</div> 
			    </c:when>
			    <c:when test="${pageScope.showStatus!=null&&pageScope.showStatus eq 'view'}">
			         <c:set var="currentSubject" value="查看" ></c:set>
			       	 <div onclick="window.location.href='<%=request.getContextPath()%>/sys/mobile/demo/form.jsp?showStatus=edit'">切换编辑视图</div>
		             <div onclick="window.location.href='<%=request.getContextPath()%>/sys/mobile/demo/form.jsp?showStatus=readOnly'">切换只读视图</div> 		    
			    </c:when>
			    <c:when test="${pageScope.showStatus!=null&&pageScope.showStatus eq 'readOnly'}">
			         <c:set var="currentSubject" value="只读" ></c:set>
			       	 <div onclick="window.location.href='<%=request.getContextPath()%>/sys/mobile/demo/form.jsp?showStatus=edit'">切换编辑视图</div>
		             <div onclick="window.location.href='<%=request.getContextPath()%>/sys/mobile/demo/form.jsp?showStatus=view'">切换查看视图</div> 		    
			    </c:when>		    
			</c:choose>	       
	       </div>
			<div class="currentView">${pageScope.currentSubject}视图</div>
	    </div>
    </div>
    <%----------------------------------------------------- 切换视图Tab  End-----------------------------------------------------%>      
         
    <div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" >
    
      <%----------------------------★★★★★★★★★★★★------------------------- 上下结构   Start-------------------------★★★★★★★★★★★★----------------------------%> 
      <h2 class="categoryTitle" category_type="layout_vertical">上下结构（${pageScope.currentSubject}）<i class="fontmuis muis-drop-up"></i></h2>   
      <div id="layout_vertical" class="muiFlowInfoW muiFormContent" >
        <xform:config showStatus="${pageScope.showStatus}">
          <xform:config orient="vertical" >
            
            <%----------------------------------------------------- 上下结构（非必填）   Start-----------------------------------------------------%>
            <h3 class="categoryTitle" category_type="layout_vertical_required_false">上下结构（非必填）<i class="fontmuis muis-drop-up"></i></h3> 
            <div id="layout_vertical_required_false">
            <table class="muiSimple" cellpadding="0" cellspacing="0">                  
              <tr>
                <td>
                	<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=add">
						<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
							<c:param name="fdKey" value="attachment1" />
							<c:param name="orient" value="vertical" />
							<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
						</c:import>
					</html:form>
				</td>
              </tr>
              <tr>
                <td><xform:text property="demo_property" mobile="true" subject="单行文本" title="单行文本"></xform:text></td>
              </tr>
              <tr>
                <td><xform:textarea property="demo_property" mobile="true" subject="多行文本" title="多行文本"></xform:textarea></td>
              </tr>
              <tr>
                <td><xform:datetime property="demo_property" mobile="true" dateTimeType="time" subject="时间" title="时间"></xform:datetime></td>
              </tr>
              <tr>
                <td><xform:datetime property="demo_property" mobile="true" dateTimeType="date" subject="日期" title="日期"></xform:datetime></td>
              </tr>
              <tr>
                <td><xform:datetime property="demo_property" mobile="true" subject="日期时间" title="日期时间"></xform:datetime></td>
              </tr>
              <tr>
              	<td>
              		<div data-dojo-type="mui/form/Composite">
              			<xform:datetime property="demo_property" mobile="true" subject="开始时间" title="开始时间"></xform:datetime>
              			<xform:datetime property="demo_property" mobile="true" subject="结束时间" title="结束时间"></xform:datetime>
					</div>
              	</td>
              </tr>
              <tr>
             	 <td>
              		<div data-dojo-type="mui/form/Composite">
              			<xform:datetime property="demo_property" mobile="true" subject="开始时间" title="开始时间"></xform:datetime>
              			<xform:datetime property="demo_property" mobile="true" subject="过渡时间" title="过渡时间"></xform:datetime>
              			<xform:datetime property="demo_property" mobile="true" subject="结束时间" title="结束时间"></xform:datetime>
					</div>
              	</td>
              </tr>
              <tr>
                <td><xform:rtf property="demo_rtf" mobile="true"></xform:rtf></td>
              </tr>
              <tr>
              	<td>
					<div data-dojo-type="sys/xform/mobile/controls/Relevance"
						 data-dojo-props="showStatus:'edit',onValueChange:'__xformDispatch',value:'',
						 name:'extendDataFormInfo.value(fd_38497f470b8a00)',fdControlId:'fd_38497f470b8a00',
						 orient:'vertical',subject:'关联文档',
						 fdMainModelName:'com.landray.kmss.km.review.model.KmReviewMain',btnName:'添加'">
					</div>
              	</td>
              </tr>    
              <tr>
              	<td>
              		<div data-dojo-type="sys/xform/mobile/controls/QrCode" 
              			data-dojo-props="showStatus:'edit',orient:'vertical',
              			subject:'二维码',
              			mold:'11',valType:'12',
              			content:'http://www.baidu.com/'">
              		</div>
              	</td>
              </tr>
			  <tr>
	            <td>
	            	<div class="newMui muiFormEleWrap showTitle muiFormStatusEdit muiFormLeft">
		            	<div class="muiFormEleTip">
		            		<span class="muiFormEleTitle">二维码</span>
		            	</div>
		            	<div class="muiFormRequired">*</div>
		            	<div style="color: #C4C6CF;">提示：二维码将在提交文档后展示</div>
	            	</div>
	            </td>
              </tr>
              <tr>
              	<td>
					<div data-dojo-type="sys/xform/mobile/controls/Hyperlink"
						 data-dojo-props="orient:'vertical',subject:'最常访问的网站',linkUrl:'http://www.baidu.com',linkStyle:'word-wrap:break-word;word-break:break-all;font-size:inherit;font-weight:normal;font-style:normal;'">
					</div>  
              	</td>
              </tr>
              <tr>
              	<td>
						<div data-dojo-type="sys/xform/mobile/controls/ChinaValue" 
							  data-dojo-props='orient:"vertical","dataType":"String","name":"c_demo_property_number","subject":"结算金额","value":"壹仟捌佰元整","onValueChange":"__xformDispatch","showStatus":"${pageScope.showStatus}"' 
							  relatedid="demo_property_number" 
							  isrow="false">
						</div>
              	</td>
              </tr>                                                    
              <tr>
                <td>
					<div data-dojo-type="mui/form/Switch"
						 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'vertical',subject:'开关（默认开启）',leftLabel:'',rightLabel:'',value:'on',property:'demo_property_switch'">
					</div>              
                </td>
              </tr>
              <tr>
                <td>
					<div data-dojo-type="mui/form/Switch"
						 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'vertical',subject:'开关（默认关闭）',leftLabel:'',rightLabel:'',value:'off',property:'demo_property_switch'">
					</div>              
                </td>
              </tr>             
              <tr>
                <td>
					<div data-dojo-type="mui/form/Switch"
						 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'vertical',subject:'开关（中文提示文字）',leftLabel:'开',rightLabel:'关',value:'on',property:'demo_property_switch'">
					</div>              
                </td>
              </tr>              
              <tr>
                <td>
					<div data-dojo-type="mui/form/Switch"
						 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'vertical',subject:'开关（英文提示文字）',leftLabel:'ON',rightLabel:'OFF',value:'on',property:'demo_property_switch'">
					</div>              
                </td>
              </tr>
              <tr>
                <td>
					<div data-dojo-type="mui/form/Switch"
						 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'vertical',subject:'开关(长、短提示文字)',leftLabel:'长段文字',rightLabel:'短文',value:'on',property:'demo_property_switch'">
					</div>              
                </td>
              </tr>              
              <tr>
                <td>
                    <xform:select property="demo_property" mobile="true" subject="下拉弹窗选择框" title="下拉弹窗选择框" showPleaseSelect="true" mul="true">
						<xform:simpleDataSource value="1">弹窗选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">弹窗选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">弹窗选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">弹窗选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>                
                    </xform:select>
                </td>
              </tr>
              <tr>
                 <td>
                      <%-- 注：当单独使用mui/form/CheckBox构建复选框时，不会创建表单hidden元素或input checkbox, 如需监听组件的change选中状态，可通过订阅事件实现：“mui/form/checkbox/change” --%>
                      <div data-dojo-type="mui/form/CheckBox" data-dojo-props="name:'demo_property_checkbox', renderType: 'normal', checked:true, value:'true', text:'单个复选框的使用'"></div>
                 </td>
              </tr>                            			  
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【标准】复选框" title="【标准】复选框" mobileRenderType="normal" value="2">
						<xform:simpleDataSource value="1">标准复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">标准复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">标准复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">标准复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>               
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【标准】复选框" title="【标准】复选框" mobileRenderType="normal" alignment="V" value="2">
						<xform:simpleDataSource value="1">标准复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">标准复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">标准复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">标准复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>              
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【块状】复选框" title="【块状】复选框" mobileRenderType="block" value="2">
						<xform:simpleDataSource value="1">块状复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>              
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【块状】复选框" title="【块状】复选框" mobileRenderType="block" alignment="V" value="2">
						<xform:simpleDataSource value="1">块状复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【表格】复选框" title="【表格】复选框" mobileRenderType="table" value="2">
						<xform:simpleDataSource value="1">表格复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">表格复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">表格复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">表格复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>              
              <tr>
                <td>
					<xform:radio property="demo_property_radio_1" mobile="true" subject="【标准】单选按钮" title="【标准】单选按钮" mobileRenderType="normal" value="2">
						<xform:simpleDataSource value="1">标准单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">标准单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">标准单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">标准单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>              
              <tr>
                <td>
					<xform:radio property="demo_property_radio_2" mobile="true" subject="【标准】单选按钮" title="【标准】单选按钮" mobileRenderType="normal" alignment="V" value="2">
						<xform:simpleDataSource value="1">标准单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">标准单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">标准单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">标准单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>              
              <tr>
                <td>
					<xform:radio property="demo_property_radio_3" mobile="true" subject="【块状】单选按钮" title="【块状】单选按钮" mobileRenderType="block" value="2">
						<xform:simpleDataSource value="1">块状单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>              
              <tr>
                <td>
					<xform:radio property="demo_property_radio_4" mobile="true" subject="【块状】单选按钮" title="【块状】单选按钮" mobileRenderType="block" alignment="V" value="2">
						<xform:simpleDataSource value="1">块状单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>              
              <tr>
                <td>
					<xform:radio property="demo_property_radio_5" mobile="true" subject="【表格】单选按钮" title="【表格】单选按钮" mobileRenderType="table" value="2">
						<xform:simpleDataSource value="1">块状单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>
             <tr>
              	<td>
					<xform:address 
							required="false"
							propertyId="demo_property_address" 
							propertyName="demo_property_address_name" 
							orgType='ORG_TYPE_ALL'  
							mobile="true"
							subject="地址本"
							mulSelect="true"
							title="地址本" />
              	</td>
              </tr>                                                                                            				              
            </table>
            </div>
            <%----------------------------------------------------- 上下结构（非必填）   End-----------------------------------------------------%>
            
            <%----------------------------------------------------- 上下结构（必填）   Start-----------------------------------------------------%>
            <h3 class="categoryTitle" category_type="layout_vertical_required_true">上下结构（必填）<i class="fontmuis muis-drop-up"></i></h3> 
            <div id="layout_vertical_required_true">
            <table class="muiSimple" cellpadding="0" cellspacing="0">
              <tr>
                <td>
                	<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=add">
						<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
							<c:param name="fdKey" value="attachment2" />
							<c:param name="fdRequired" value="true" />
							<c:param name="orient" value="vertical" />
							<c:param name="fdRequired" value="true" />
							<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
						</c:import>
					</html:form> 
                </td>
              </tr>
              <tr>
                <td><xform:text property="demo_property" mobile="true" subject="单行文本  (必填)" title="单行文本" required="true" value="单行文本内容"></xform:text></td>
              </tr>
              <tr>
                <td><xform:textarea property="demo_property" mobile="true" subject="多行文本  (必填)" title="多行文本" value="多行文本内容" required="true"></xform:textarea></td>
              </tr>                          
              <tr>
                <td><xform:datetime property="demo_property" mobile="true" subject="日期时间  (必填)" title="日期时间" value="2020-09-01 19:20" required="true"></xform:datetime></td>
              </tr>
               <tr>
              	<td>
              		<div data-dojo-type="mui/form/Composite">
              			<xform:datetime property="demo_property" mobile="true" subject="开始时间" title="开始时间" required="true"></xform:datetime>
              			<xform:datetime property="demo_property" mobile="true" subject="结束时间" title="结束时间" required="true"></xform:datetime>
					</div>
              	</td>
              </tr>
              <tr>
                <td>
                    <xform:select property="demo_property" mobile="true" subject="下拉弹窗选择框 (必填)" title="下拉弹窗选择框 (必填)" showPleaseSelect="true" required="true">
						<xform:simpleDataSource value="1">弹窗选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">弹窗选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">弹窗选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">弹窗选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>                
                    </xform:select>
                </td>
              </tr> 
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【标准】复选框 (必填)" title="【标准】复选框 (必填)" mobileRenderType="normal" value="2" required="true">
						<xform:simpleDataSource value="1">标准复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">标准复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">标准复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">标准复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>   
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【标准】复选框 (必填)" title="【标准】复选框 (必填)" mobileRenderType="normal" alignment="V" value="2" required="true">
						<xform:simpleDataSource value="1">标准复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">标准复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">标准复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">标准复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【块状】复选框 (必填)" title="【块状】复选框 (必填)" mobileRenderType="block" value="2" required="true">
						<xform:simpleDataSource value="1">块状复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr> 
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【块状】复选框 (必填)" title="【块状】复选框 (必填)" mobileRenderType="block" alignment="V" value="2" required="true">
						<xform:simpleDataSource value="1">块状复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>
              <tr>
                <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【表格】复选框 (必填)" title="【表格】复选框 (必填)" mobileRenderType="table" value="2" required="true">
						<xform:simpleDataSource value="1">表格复选框选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">表格复选框选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">表格复选框选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">表格复选框选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>                
                </td>
              </tr>
              <tr>
                <td>
					<xform:radio property="demo_property_radio_r_1" mobile="true" subject="【标准】单选按钮 (必填)" title="【标准】单选按钮 (必填)" mobileRenderType="normal" value="2" required="true">
						<xform:simpleDataSource value="1">标准单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">标准单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">标准单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">标准单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>
              <tr>
                <td>
					<xform:radio property="demo_property_radio_r_2" mobile="true" subject="【标准】单选按钮 (必填)" title="【标准】单选按钮 (必填)" mobileRenderType="normal" alignment="V" value="2"  required="true">
						<xform:simpleDataSource value="1">标准单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">标准单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">标准单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">标准单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>
              <tr>
                <td>
					<xform:radio property="demo_property_radio_r_3" mobile="true" subject="【块状】单选按钮 (必填)" title="【块状】单选按钮 (必填)" mobileRenderType="block" value="2" required="true">
						<xform:simpleDataSource value="1">块状单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>
              <tr>
                <td>
					<xform:radio property="demo_property_radio_r_4" mobile="true" subject="【块状】单选按钮 (必填)" title="【块状】单选按钮 (必填)" mobileRenderType="block" alignment="V" value="2" required="true">
						<xform:simpleDataSource value="1">块状单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>
              <tr>
                <td>
					<xform:radio property="demo_property_radio_r_5" mobile="true" subject="【表格】单选按钮 (必填)" title="【表格】单选按钮 (必填)" mobileRenderType="table" value="2" required="true">
						<xform:simpleDataSource value="1">块状单选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">块状单选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">块状单选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">块状单选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
                </td>
              </tr>
              <tr>
              	<td>
					<xform:address 
							required="true"
							propertyId="demo_property_address" 
							propertyName="demo_property_address_name" 
							orgType='ORG_TYPE_ALL'  
							mobile="true"
							subject="地址本"
							showStatus="readOnly"
							mulSelect="true"
							title="地址本" />

              	</td>
              </tr>                                                                                                                                                                  
            </table>
            </div>
            <%----------------------------------------------------- 上下结构（必填）   End-----------------------------------------------------%>
                                   
          </xform:config>
        </xform:config>
      </div>
      <%----------------------------★★★★★★★★★★★★------------------------- 上下结构   End-------------------------★★★★★★★★★★★★----------------------------%>
      
      
      <%----------------------------★★★★★★★★★★★★------------------------- 左右结构   Start-------------------------★★★★★★★★★★★★----------------------------%>
      <h2 class="categoryTitle" category_type="layout_horizontal">左右结构 （${pageScope.currentSubject}）<i class="fontmuis muis-drop-up"></i></h2>
      <div id="layout_horizontal" class="muiFlowInfoW muiFormContent">
        <xform:config showStatus="${pageScope.showStatus}" >
          <%----------------------------------------------------- 左右结构（非必填）   Start-----------------------------------------------------%>
          <h3 class="categoryTitle" category_type="layout_horizontal_required_false">左右结构（非必填）<i class="fontmuis muis-drop-up"></i></h3> 
          <div id="layout_horizontal_required_false">                
          <table class="muiSimple" cellpadding="0" cellspacing="0">            
            <tr>
              <td class="muiTitle">
              	附件（左对齐）
              </td>
              <td>
				<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
					<c:param name="fdKey" value="attachment3" />
					<c:param name="align" value="left" />
					<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
				</c:import>
              </td>
            </tr>
            <tr>
              <td class="muiTitle">
              	附件（右对齐）
              </td>
              <td>
				<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
					<c:param name="fdKey" value="attachment4" />
					<c:param name="align" value="right" />
					<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
				</c:import>
              </td>
            </tr>
            <tr>
              <td class="muiTitle">单行文本（左对齐）</td>
              <td><xform:text property="demo_property" mobile="true" subject="单行文本" title="单行文本" align="right"></xform:text></td>
            </tr>
            <tr>
              <td class="muiTitle">单行文本（右对齐）</td>
              <td><xform:text property="demo_property" mobile="true" subject="单行文本" title="单行文本" align="right"></xform:text></td>
            </tr>
            <tr>
              <td class="muiTitle">多行文本</td>
              <td><xform:textarea property="demo_property" mobile="true" subject="多行文本" title="多行文本" align="right"></xform:textarea></td>
            </tr>
            <tr>
              <td class="muiTitle">时间</td>
              <td><xform:datetime property="demo_property" mobile="true" dateTimeType="time" subject="时间" title="时间" align="right"></xform:datetime></td>
            </tr>
            <tr>
              <td class="muiTitle">日期</td>
              <td><xform:datetime property="demo_property" mobile="true" dateTimeType="date" subject="日期" title="日期" align="right"></xform:datetime></td>
            </tr>
            <tr>
              <td class="muiTitle">日期时间</td>
              <td><xform:datetime property="demo_property" mobile="true" subject="日期时间" title="日期时间" align="right"></xform:datetime></td>
            </tr>
              <tr>
	            <td class="muiTitle">二维码（左右结构）</td>   
              	<td>
              		<div data-dojo-type="sys/xform/mobile/controls/QrCode" 
              			data-dojo-props="showStatus:'edit',orient:'horizontal',
              			subject:'二维码',
              			mold:'11',valType:'12',
              			content:'http://www.baidu.com/'">
              		</div>
              	</td>
              </tr>
              <tr>
	            <td class="muiTitle">二维码（提示）</td>   
              	<td><div style="text-align: right;color: #C4C6CF;line-height: 3rem;">提示：二维码将在提交文档后展示</div></td>
              </tr>
            <tr>
	            <td class="muiTitle">
	            	最喜欢的视频网站（右对齐）
	            </td>              
            	<td>
					<div data-dojo-type="sys/xform/mobile/controls/Hyperlink"
						 data-dojo-props="orient:'horizontal',align:'right',subject:'最常访问的网站',linkUrl:'http://www.baidu.com',linkStyle:'word-wrap:break-word;word-break:break-all;font-size:inherit;font-weight:normal;font-style:normal;'">
					</div>      
            	</td>
            </tr>
            <tr>
              <td class="muiTitle">结算金额</td>              
              <td>
				    <div data-dojo-type="sys/xform/mobile/controls/ChinaValue" 
					  data-dojo-props='orient:"horizontal",align:"right","dataType":"String","name":"c_demo_property_number","subject":"结算金额","value":"壹仟捌佰元整","onValueChange":"__xformDispatch","showStatus":"${pageScope.showStatus}"' 
					  relatedid="demo_property_number" 
					  isrow="false">
				    </div>
              </td>
            </tr>                        
            <tr>
              <td class="muiTitle">开关（默认开启）</td>
              <td>
				<div data-dojo-type="mui/form/Switch"
					 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'horizontal',align:'right',leftLabel:'',rightLabel:'',value:'on',property:'demo_property_switch'">
				</div>              
              </td>
            </tr>
            <tr>
              <td class="muiTitle">开关（默认关闭）</td>
              <td>
				<div data-dojo-type="mui/form/Switch"
					 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'horizontal',align:'right',leftLabel:'',rightLabel:'',value:'off',property:'demo_property_switch'">
				</div>              
              </td>
            </tr>             
            <tr>
              <td class="muiTitle">开关（中文提示文字）</td>
              <td>
				<div data-dojo-type="mui/form/Switch"
					 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'horizontal',align:'right',leftLabel:'开',rightLabel:'关',value:'on',property:'demo_property_switch'">
				</div>              
              </td>
            </tr>              
            <tr>
              <td class="muiTitle">开关（英文提示文字）</td>
              <td>
				<div data-dojo-type="mui/form/Switch"
					 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'horizontal',align:'right',leftLabel:'ON',rightLabel:'OFF',value:'on',property:'demo_property_switch'">
				</div>              
              </td>
            </tr>
            <tr>
              <td class="muiTitle">开关(长、短提示文字)</td>
              <td>
				<div data-dojo-type="mui/form/Switch"
					 data-dojo-props="showStatus:'${pageScope.showStatus}',orient:'horizontal',align:'right',leftLabel:'长段文字',rightLabel:'短文',value:'on',property:'demo_property_switch'">
				</div>              
              </td>
            </tr>            
            <tr>
              <td class="muiTitle">下拉弹窗选择框</td>
              <td>
                    <xform:select property="demo_property" mobile="true" subject="下拉弹窗选择框" title="下拉弹窗选择框" showPleaseSelect="true">
						<xform:simpleDataSource value="1">弹窗选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">弹窗选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">弹窗选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">弹窗选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>                
                    </xform:select>              
              </td>
            </tr>  
            <tr>
              <td class="muiTitle">【标准】复选框</td>
              <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【标准】复选框" title="【标准】复选框" mobileRenderType="normal" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>               
              </td>
            </tr>             
            <tr>
              <td class="muiTitle">【标准】复选框</td>
              <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【标准】复选框" title="【标准】复选框" mobileRenderType="normal" alignment="V" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>               
              </td>
            </tr>                 
            <tr>
              <td class="muiTitle">【块状】复选框</td>
              <td>   
            		<xform:checkbox property="demo_property" mobile="true" subject="【块状】复选框" title="【块状】复选框" mobileRenderType="block" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox> 					          
              </td>
            </tr>             
            <tr>
              <td class="muiTitle">【块状】复选框</td>
              <td>   
            		<xform:checkbox property="demo_property" mobile="true" subject="【块状】复选框" title="【块状】复选框" mobileRenderType="block" alignment="V" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox> 					          
              </td>
            </tr>             
            <tr>
              <td class="muiTitle">【表格】复选框</td>
              <td>   
					<xform:checkbox property="demo_property" mobile="true" subject="【表格】复选框" title="【表格】复选框" mobileRenderType="table" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>  				          
              </td>
            </tr>             
            <tr>
              <td class="muiTitle">【标准】单选按钮</td>
              <td>   
					<xform:radio property="demo_property_radio_h1" mobile="true" subject="【标准】单选按钮" title="【标准】单选按钮" mobileRenderType="normal" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>  				          
              </td>
            </tr>            
            <tr>
              <td class="muiTitle">【标准】单选按钮</td>
              <td>   
					<xform:radio property="demo_property_radio_h2" mobile="true" subject="【标准】单选按钮" title="【标准】单选按钮" mobileRenderType="normal" alignment="V" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>  				          
              </td>
            </tr>            
            <tr>
              <td class="muiTitle">【块状】单选按钮</td>
              <td>   
					<xform:radio property="demo_property_radio_h3" mobile="true" subject="【块状】单选按钮" title="【块状】单选按钮" mobileRenderType="block" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>  				          
              </td>
            </tr>            
            <tr>
              <td class="muiTitle">【块状】单选按钮</td>
              <td>   
					<xform:radio property="demo_property_radio_h4" mobile="true" subject="【块状】单选按钮" title="【块状】单选按钮" mobileRenderType="block" alignment="V" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>  				          
              </td>
            </tr>            
            <tr>
              <td class="muiTitle">【表格】单选按钮</td>
              <td>
					<xform:radio property="demo_property_radio_h5" mobile="true" subject="【表格】单选按钮" title="【表格】单选按钮" mobileRenderType="table" value="2">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
              </td>
            </tr>
           <tr>
             	<td class="muiTitil">地址本</td>
              	<td>
					<xform:address 
							required="false"
							propertyId="demo_property_address" 
							propertyName="demo_property_address_name" 
							orgType='ORG_TYPE_ALL'  
							mobile="true"
							subject="地址本"
							mulSelect="true"
							align="right"
							title="地址本" />

              	</td>
              </tr>                                                                                                
          </table>
          </div>
          <%----------------------------------------------------- 左右结构（非必填）   End-----------------------------------------------------%>    
          
          <%----------------------------------------------------- 左右结构（必填）   Start-----------------------------------------------------%>
          <h3 class="categoryTitle" category_type="layout_horizontal_required_true">左右结构（必填）<i class="fontmuis muis-drop-up"></i></h3> 
          <div id="layout_horizontal_required_true">                
          <table class="muiSimple" cellpadding="0" cellspacing="0">
            <tr>
				<td class="muiTitle">
              		附件（左对齐）
                </td>
              	<td>
					<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
						<c:param name="fdKey" value="attachment5" />
						<c:param name="align" value="left" />
						<c:param name="fdRequired" value="true" />
						<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
					</c:import>
              	</td>
            </tr>
            <tr>
				<td class="muiTitle">
              		附件（右对齐）
                </td>
              	<td>
					<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysZonePersonInfoForm"></c:param>
						<c:param name="fdKey" value="attachment6" />
						<c:param name="align" value="right" />
						<c:param name="fdRequired" value="true" />
						<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
					</c:import>
              	</td>
            </tr>
            <tr>
              <td class="muiTitle">单行文本（左对齐）(必填)</td>
              <td><xform:text property="demo_property" mobile="true" subject="单行文本" title="单行文本" value="单行文本内容" required="true"></xform:text></td>
            </tr>
            <tr>
              <td class="muiTitle">单行文本（右对齐） (必填)</td>
              <td><xform:text property="demo_property" align="right" mobile="true" subject="单行文本" title="单行文本" value="单行文本内容" required="true"></xform:text></td>
            </tr>
            <tr>
              <td class="muiTitle">多行文本（左对齐） (必填)</td>
              <td><xform:textarea property="demo_property" mobile="true" subject="多行文本" title="多行文本" value="多行文本内容" required="true"></xform:textarea></td>
            </tr>
            <tr>
              <td class="muiTitle">多行文本（右对齐）(必填)</td>
              <td><xform:textarea property="demo_property" align="right" mobile="true" subject="多行文本" title="多行文本" required="true" value="多行文本内容"></xform:textarea></td>
            </tr>
            <tr>
              <td class="muiTitle">日期时间  (必填)</td>
              <td><xform:datetime property="demo_property" mobile="true" subject="日期时间 " title="日期时间" value="2020-09-01 19:20" required="true" align="right"></xform:datetime></td>
            </tr> 
            <tr>
              <td class="muiTitle">下拉弹窗选择框 (必填)</td>
              <td>
                    <xform:select property="demo_property" mobile="true" subject="下拉弹窗选择框 (必填)" title="下拉弹窗选择框 (必填)" showPleaseSelect="true" required="true">
						<xform:simpleDataSource value="1">弹窗选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">弹窗选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">弹窗选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">弹窗选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>                
                    </xform:select>             
              </td>
            </tr>
            <tr>
              <td class="muiTitle">【标准】复选框  (必填)</td>
              <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【标准】复选框  (必填)" title="【标准】复选框  (必填)" mobileRenderType="normal" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>               
              </td>
            </tr>
            <tr>
              <td class="muiTitle">【标准】复选框  (必填)</td>
              <td>
					<xform:checkbox property="demo_property" mobile="true" subject="【标准】复选框  (必填)" title="【标准】复选框  (必填)" mobileRenderType="normal" alignment="V" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>               
              </td>
            </tr>  
            <tr>
              <td class="muiTitle">【块状】复选框 (必填)</td>
              <td>   
            		<xform:checkbox property="demo_property" mobile="true" subject="【块状】复选框 (必填)" title="【块状】复选框 (必填)" mobileRenderType="block" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox> 					          
              </td>
            </tr>
            <tr>
              <td class="muiTitle">【块状】复选框 (必填)</td>
              <td>   
            		<xform:checkbox property="demo_property" mobile="true" subject="【块状】复选框 (必填)" title="【块状】复选框 (必填)" mobileRenderType="block" alignment="V" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox> 					          
              </td>
            </tr>
            <tr>
              <td class="muiTitle">【表格】复选框 (必填)</td>
              <td>   
					<xform:checkbox property="demo_property" mobile="true" subject="【表格】复选框 (必填)" title="【表格】复选框 (必填)" mobileRenderType="table" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:checkbox>  				          
              </td>
            </tr> 
            <tr>
              <td class="muiTitle">【标准】单选按钮 (必填)</td>
              <td>   
					<xform:radio property="demo_property_radio_r_h1" mobile="true" subject="【标准】单选按钮 (必填)" title="【标准】单选按钮 (必填)" mobileRenderType="normal" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>  				          
              </td>
            </tr>
            <tr>
              <td class="muiTitle">【标准】单选按钮 (必填)</td>
              <td>   
					<xform:radio property="demo_property_radio_r_h2" mobile="true" subject="【标准】单选按钮 (必填)" title="【标准】单选按钮 (必填)" mobileRenderType="normal" alignment="V" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>  				          
              </td>
            </tr>            
            <tr>
              <td class="muiTitle">【块状】单选按钮 (必填)</td>
              <td>   
					<xform:radio property="demo_property_radio_r_h3" mobile="true" subject="【块状】单选按钮 (必填)" title="【块状】单选按钮 (必填)" mobileRenderType="block" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>  				          
              </td>
            </tr>            
            <tr>
              <td class="muiTitle">【块状】单选按钮 (必填)</td>
              <td>   
					<xform:radio property="demo_property_radio_r_h4" mobile="true" subject="【块状】单选按钮 (必填)" title="【块状】单选按钮 (必填)" mobileRenderType="block" alignment="V" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>  				          
              </td>
            </tr>            
            <tr>
              <td class="muiTitle">【表格】单选按钮 (必填)</td>
              <td>
					<xform:radio property="demo_property_radio_r_h5" mobile="true" subject="【表格】单选按钮 (必填)" title="【表格】单选按钮 (必填)" mobileRenderType="table" value="2" required="true">
						<xform:simpleDataSource value="1">选项A</xform:simpleDataSource>
						<xform:simpleDataSource value="2">选项B</xform:simpleDataSource>
						<xform:simpleDataSource value="3">选项C</xform:simpleDataSource>
						<xform:simpleDataSource value="4">选项D</xform:simpleDataSource>
						<xform:simpleDataSource value="5">这是一段很长很长很长非常长的文字，测试文字超过一行时选项内容的显示效果，样式的控制需要考虑长度极限场景</xform:simpleDataSource>
					</xform:radio>                
              </td>
            </tr> 
            <tr>
             	<td class="muiTitil">地址本</td>
              	<td>
					<xform:address 
							required="true"
							propertyId="demo_property_address" 
							propertyName="demo_property_address_name" 
							orgType='ORG_TYPE_ALL'  
							mobile="true"
							subject="地址本"
							mulSelect="true"
							align="right"
							title="地址本" />

              	</td>
              </tr>                                                                                                                                              
          </table>
          </div>
          <%----------------------------------------------------- 左右结构（非必填）   End-----------------------------------------------------%>                         
        </xform:config>
      </div>
      <%----------------------------★★★★★★★★★★★★------------------------- 左右结构   End-------------------------★★★★★★★★★★★★----------------------------%>

      
      <footer>已经滑到最底部了</footer>
      
      
    </div>
  </template:replace>
</template:include>
