<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<% response.addHeader("X-UA-Compatible", "IE=8"); %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<style type="text/css">
	.grayColor
		{
			color:gray;
		}
	.noborder{
		border-collapse:collapse;
		border: 0px #FFF solid;
		background-color: #FFFFFF;
	}
	.noborder td{
		border-collapse:collapse;
		border: 0px #FFF solid;
		padding: 5px 5px 0 0;
	}
	#nav li {     
		 list-style-type: none;    
		 padding: 2px 5px;  
	 } 
	.flowNoLabel{
	 	width:150px;text-align: right;padding-right:5px;
	 }
	 
</style>


<!-- 编号机制自定义样式 -->
<link rel="stylesheet" href="../resource/css/custom.css">

<script type="text/javascript">
	Com_IncludeFile("doclist.js|dialog.js|calendar.js|optbar.js|jquery.js|json2.js|formula.js");
	Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");
</script>

<html:form action="/sys/number/sys_number_main/sysNumberMain.do">
	<c:if test="${JsParam['isCustom'] !='1'}">
		<div id="optBarDiv">
			<c:if test="${sysNumberMainForm.method_GET=='edit'}">
				<input type=button value="<bean:message key="button.update"/>"
					onclick="Com_Submit(document.sysNumberMainForm, 'update');">
			</c:if>
			<c:if test="${sysNumberMainForm.method_GET=='add' || sysNumberMainForm.method_GET=='clone'}">
				<input type=button value="<bean:message key="button.save"/>"
					onclick="Com_Submit(document.sysNumberMainForm, 'save');">
				<input type=button value="<bean:message key="button.saveadd"/>"
					onclick="Com_Submit(document.sysNumberMainForm, 'saveadd');">
			</c:if>
			<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
		</div>
	</c:if>
	<table align=center width="95%"><tr><td width="100%">
	<div id="showError" class="txtstrong" align="center" style="font-size:14px;">
	</div></td></tr></table>
	<c:if test="${JsParam['isCustom'] =='1'}">
		<div style="height: 10px"></div>
	</c:if>
	<c:if test="${JsParam['isCustom'] !='1'}">
		<p class="txttitle"><bean:message bundle="sys-number" key="table.sysNumberMain"/></p>
	</c:if>
<center>
	<table width=95% class="tb_normal">
	<c:if test="${JsParam['isCustom'] !='1'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-number" key="sysNumberMain.fdName"/>
			</td><td width="35%">
				<xform:text property="fdName" style="width:45%" validators="maxLength(200)" required="true"/>
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-number" key="sysNumberMain.fdDefaultFlag"/>
			</td><td width="35%">
				<input type="checkbox" name="DefaultFlag" value="0" />
				<bean:message bundle="sys-number" key="sysNumberMain.templateDefault"/>
			</td>
		</tr>

		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-number" key="sysNumberMain.fdOrder"/>
			</td><td colspan="3">
				<xform:text property="fdOrder" validators="digits maxLength(10)" />
			</td>
		</tr>
		<!-- 可使用者 -->
		<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-number" key="sysNumberMain.authReaders" />
				</td><td colspan="3">
					<html:hidden property="authReaderIds" /> 
							<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:97%;height:90px;" ></xform:address>
							<br>
							<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
							 	<!-- 保存当前页面form名称，用于生态组织判断时从request中取值 -->
							    <c:set var="formName" value="sysNumberMainForm" scope="request"/>
							       <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
							        <!-- （为空则本组织人员可使用） -->
							        <bean:message key="sysNumberMain.authReaders.tip.outer" bundle="sys-number" arg0="${ecoName}"/>
							    <% } else { %>
							        <!-- （为空则所有内部人员可使用） -->
							          <bean:message key="sysNumberMain.authReaders.tip.inner" bundle="sys-number"/>
							    <% } %>
							 <% } else { %>
							 	 <!-- （为空则所有人员可使用） -->
						         <bean:message key="sysNumberMain.authReaders.tip" bundle="sys-number"/>
							<% } %>
				</td>
		</tr>
	</c:if>
	<tr>
		<td colspan="4">
			<div id="lui_number_setting_content" style="width:100%;margin:20px auto">
			    <!-- 编号机制 Starts -->
			    <div class="lui_number_setting_wrap">
			      <div class="lui_number_setting_head">
			        <em class="number_setting_td_title"><bean:message bundle="sys-number" key="sysNumberMain.fdTitle"/></em>
			        <ul id="number_select_list" class="number_setting_tab_list number_setting_tab_list_select">
			          <li data-type="date">
			            <i class="number_icon icon_date"></i>
			            <span><bean:message bundle="sys-number" key="sysNumberMain.fdDate"/></span>
			          </li>
			          <li data-type="time">
			            <i class="number_icon icon_time"></i>
			            <span><bean:message bundle="sys-number" key="sysNumberMain.fdTime"/></span>
			          </li>
			          <li data-type="flow">
			            <i class="number_icon icon_flow"></i>
			            <span><bean:message bundle="sys-number" key="sysNumberMain.flowNo"/></span>
			          </li>
			          <li data-type="const">
			            <i class="number_icon icon_const"></i>
			            <span><bean:message bundle="sys-number" key="sysNumberMain.fdConstant"/></span>
			          </li>
			          <li data-type="custom">
			            <i class="number_icon icon_custom"></i>
			            <span><bean:message bundle="sys-number" key="sysNumberMain.fdCustom"/></span>
			          </li>
			        </ul>
			        <!-- 清空 -->
			        <div id="number_btn_clear" class="number_btn_clear">
			        	  <bean:message bundle="sys-number" key="sysNumberMain.fdClear"/>
			        </div>
			      </div>
			      <div class="lui_number_setting_content">
			        <div class="number_setting_tab_list_wrap">
			          <!-- 动态选择列表区域 -->
			          <ul id="number_select_list_tab" class="number_setting_tab_list">
			          
			          </ul>
			        </div>
			        <div class="number_tab_content" id="number_tab_content">
			          <!-- 日期属性 Starts -->
			          <div class="number_tab_item" data-type="date" style="display:none;">
			            <div class="number_tab_tips">
			              <h3 class="number_tab_title"><bean:message bundle="sys-number" key="sysNumberMain.dateProperty"/></h3>
			              <div class="number_table">
			                <ul>
			                  <li><em><bean:message bundle="sys-number" key="sysNumberMain.areaProperty"/></em><select name="" id="selectDateArea">
			                      <option><bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/></option>
			                    </select></li>
			                  <li><em><bean:message bundle="sys-number" key="sysNumberMain.datetimeType"/></em><select name="" id="selectDate">
			                      <option><bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/></option>
			                    </select></li>
			                </ul>
			              </div>
			              <div class="number_btn_group">
			                <span data-type="date"  class="number_btn number_btn_primary"><bean:message bundle="sys-number" key="sysNumberMain.OK"/></span>
			              	<span data-type="date-cancel" class="number_btn number_btn_gray"><bean:message key="button.cancel"/></span>
			              </div>
			            </div>
			          </div>
			          <!-- 日期属性 Ends -->
			          <!-- 时间属性 Starts -->
			          <div class="number_tab_item" data-type="time" style="display:none;">
			            <div class="number_tab_tips">
			              <h3 class="number_tab_title"><bean:message bundle="sys-number" key="sysNumberMain.timeProperty"/></h3>
			              <div class="number_table">
			                <ul>
			                  <li><em><bean:message bundle="sys-number" key="sysNumberMain.areaProperty"/></em><select name="" id="selectTimeArea">
			                      <option><bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/></option>
			                    </select></li>
			                  <li><em><bean:message bundle="sys-number" key="sysNumberMain.datetimeType"/></em><select name="" id="selectTime">
			                      <option><bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/></option>
			                    </select></li>
			                </ul>
			              </div>
			              <div class="number_btn_group">
			                <span data-type="time" class="number_btn number_btn_primary"><bean:message bundle="sys-number" key="sysNumberMain.OK"/></span>
			              	<span data-type="time-cancel" class="number_btn number_btn_gray"><bean:message key="button.cancel"/></span>
			              </div>
			            </div>
			          </div>
			          <!-- 时间属性 Ends -->
			          <!-- 流水号属性 Starts -->
			          <div class="number_tab_item" data-type="flow" style="display:none;">
			            <div class="number_tab_tips">
			              <h3 class="number_tab_title"><bean:message bundle="sys-number" key="sysNumberMain.flowProperty"/></h3>
			              <div class="number_table">
			                <ul>
			                  <li><em><bean:message bundle="sys-number" key="sysNumberMain.flow.period"/></em>
			                  	 <label class="number_raido"><input type="radio" value="0" checked="checked" name="period" onclick="changeSelectPeriod(this);" ><bean:message bundle="sys-number" key="sysNumberMain.flow.period.t0"/></label>
			                  	 <label class="number_raido"><input type="radio" value="1" name="period" onclick="changeSelectPeriod(this);"><bean:message bundle="sys-number" key="sysNumberMain.flow.period.t1"/></label>
			                  	 <label class="number_raido"><input type="radio" value="2" name="period" onclick="changeSelectPeriod(this);"><bean:message bundle="sys-number" key="sysNumberMain.flow.period.t2"/></label>
			                  	 <label class="number_raido"><input type="radio" value="3" name="period" onclick="changeSelectPeriod(this);"><bean:message bundle="sys-number" key="sysNumberMain.flow.period.t3"/></label>
			                  </li>
			                  <li><em><bean:message bundle="sys-number" key="sysNumberMain.flow.length"/></em>
			                  	 <input type="text" id="len" size="20" value="3" >
			                  	 <span class="txtstrong">*</span>
			                  	 <label class="number_checkbox"><input checked="checked" id="isZeroFill" style="vertical-align: middle;" type="checkbox">
								 <span style="vertical-align: middle;"><bean:message bundle="sys-number" key="sysNumberMain.flow.isZeroFill"/></span>
								 </label>
								  <label class="number_checkbox"><input  id="isAutoElevation" style="vertical-align: middle;" type="checkbox">
								 <span style="vertical-align: middle;"><bean:message bundle="sys-number" key="sysNumberMain.flow.isAutoElevation"/></span>
								 </label>
			                  	 <div id="flowLenTip" style="width:300px;color:red;font-size:14px;"></div>
			                  </li>
			                  <li><em><bean:message bundle="sys-number" key="sysNumberMain.flow.start"/></em>
			                  	 <input type="text" id="start" value="1" onchange="onFlowStartChange(this.value)" size="20" >
			                  	 <span class="txtstrong">*</span>
			                  	 <span id="flowStartTip" style="color:red;padding-left:10px;font-size:14px;"></span>
			                  </li>
							  <li id="li_period_start">
								  <em><bean:message bundle="sys-number" key="sysNumber.start.no"/></em>
								  <div class="number_prompt_tooltip">
									  <label class="number_prompt_tooltip_drop">
										  <img src="${KMSS_Parameter_ContextPath}sys/number/resource/images/promptControl.png">
									  </label>
									  <div class="number_dropdown_tooltip_menu" name="useDefaultLayoutTip" style="display: none;">
										  <bean:message bundle="sys-number" key="sysNumber.useDefaultLayoutTip"/>
									  </div>
								  </div>
								  <input type="text" id="firstStart" value="1"  size="20" >
							  </li>
			                  <li id="flowscope" style="display:none"><em><bean:message bundle="sys-number" key="sysNumberMain.flow.limits"/></em>
							 	<span id="limits"></span>
				 			  </li>
			                </ul>
			              </div>
			              <div class="number_btn_group">
			                <span data-type="flow"  class="number_btn number_btn_primary"><bean:message bundle="sys-number" key="sysNumberMain.OK"/></span>
			              	<span data-type="flow-cancel" class="number_btn number_btn_gray"><bean:message key="button.cancel"/></span>
			              </div>
			            </div>
			          </div>
			          <!-- 流水号属性 Ends -->
			          <!-- 常量值 Starts -->
			          <div class="number_tab_item" data-type="const" style="display:none;">
			            <div class="number_tab_tips">
			              <h3 class="number_tab_title"><bean:message bundle="sys-number" key="sysNumberMain.constProperty"/></h3>
			              <div class="number_table">
			                <ul>
			                  <li><em><bean:message bundle="sys-number" key="sysNumberMain.constValue"/></em>
			                  <input type="text" id="textConst" size="40" ><span class="txtstrong">*</span>
			                  </li>
			                </ul>
			              </div>
			              <div class="number_btn_group">
			                <span data-type="const" class="number_btn number_btn_primary"><bean:message bundle="sys-number" key="sysNumberMain.OK"/></span>
			              	<span data-type="const-cancel" class="number_btn number_btn_gray"><bean:message key="button.cancel"/></span>
			              </div>
			            </div>
			          </div>
			          <!-- 常量值属性 Ends -->
			          <!-- 自定义 Starts -->
			          <div class="number_tab_item" data-type="custom" style="display:none">
			            <div class="number_tab_tips">
			              <h3 class="number_tab_title"><bean:message bundle="sys-number" key="sysNumberMain.customProperty"/></h3>
			              <div class="number_table">
			                <ul>
			                  <li>
			                  	<em><bean:message bundle="sys-number" key="sysNumberMain.formula"/></em>
								<input type="hidden" name="textCustomID" id="textCustomID"/>
				 				<input type="text" name="textCustomName" id="textCustomName" readonly size="40" />
								<a id="formula" onclick="formulaClick();"><bean:message bundle="sys-number" key="sysNumberMain.formulaDef"/></a><i class="number_warn">*</i>
							 </li>
			                 <li>
			                  	<em><bean:message bundle="sys-number" key="sysNumberMain.formulaShowName"/></em>
			                  	<input type="text" id="textCustomNameFromUser" value="<bean:message bundle="sys-number" key="sysNumberMain.fdCustom"/>"  size="20" />
			                  	<span class="txtstrong">*</span>
								 <label class="number_checkbox"><input  id="isHidden" style="vertical-align: middle;" type="checkbox">
									 <span style="vertical-align: middle;"><bean:message bundle="sys-number" key="sysNumber.hide.in.results"/></span>
								 </label>
			                 </li>
			                </ul>
			              </div>
			              <div class="number_btn_group">
			                <span data-type="custom"  class="number_btn number_btn_primary"><bean:message bundle="sys-number" key="sysNumberMain.OK"/></span>
			                <span data-type="custom-cancel" class="number_btn number_btn_gray"><bean:message key="button.cancel"/></span>
			              </div>
			            </div>
			          </div>
			          <!-- 自定义 Ends -->
			        </div>
			        <!-- 具体输入区域 -->
			        <div class="number_input_wrap">
			          <div class="number_input_list">
			          </div>
			        </div>
			      </div>
			    </div>
			  </div>
		</td>
	</tr>
	
	
	
	<c:if test="${JsParam['isCustom'] !='1' && JsParam['method']=='edit'}">
    <tr>
        <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
        <td class="td_normal_title" width=15%>
            <bean:message bundle="sys-number" key="sysNumberMain.docCreator"/>
        </td><td width="35%">
            <xform:text property="docCreatorName" style="width:45%" showStatus="view"/>
        </td>
        <td class="td_normal_title" width=15%>
            <bean:message bundle="sys-number" key="sysNumberMain.docCreateTime"/>
        </td><td width="35%">
            <xform:text property="docCreateTime" style="width:45%" showStatus="view"/>
        </td>
    </tr>
    <tr>
        <td class="td_normal_title" width=15%>
            <bean:message bundle="sys-number" key="sysNumberMain.docAlteror"/>
        </td><td width="35%">
            <xform:text property="docAlterorName" style="width:45%" showStatus="view"/>
        </td>
        <td class="td_normal_title" width=15%>
            <bean:message bundle="sys-number" key="sysNumberMain.docAlterTime"/>
        </td><td width="35%">
            <xform:text property="docAlterTime" style="width:45%" showStatus="view"/>
        </td>
    </tr>
	</c:if>
</table>
</center>

<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<html:hidden property="fdContent" styleId="fdContent"/>
<html:hidden property="fdModelName" />
<html:hidden property="fdDefaultFlag"/>
<%--<html:hidden property="fdUniqueInQuote"/>--%>
<html:hidden property="fdFlowContent" styleId="fdFlowContent"/>
	
<c:if test="${JsParam['isCustom'] !='1'}">
		<xform:text property="fdTemplateFlag" value="0" showStatus="noShow"/>
</c:if>
<c:if test="${JsParam['isCustom'] =='1'}">
		<xform:text property="fdTemplateFlag" value="1" showStatus="noShow"/>
		<xform:text property="fdName" value='自定义编号规则' showStatus="noShow"/>
</c:if>

<!-- 脚本 Starts -->
<script src="../resource/js/lib/jquery.dragsort.js"></script>
<script type="text/javascript">
Com_IncludeFile("custom.js",Com_Parameter.ContextPath+"sys/number/resource/js/","js",true);
</script>
<!-- 脚本 Ends -->

<jsp:include page="/sys/number/include/commonScript.jsp"></jsp:include>

<script type="text/javascript">
	$KMSSValidation();
	
	//全局fdContent元素
	var fdContent = $("input[name='fdContent']").val();
	var fdContentJson = fdContent!=""?JSON.parse(fdContent):[];
	
	//入口函数
	$(function(){
		init();
		selectPeriod();
	});

	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
		var name=$("input[name='fdName']").val();
		if(isNull(name)){
			addError('<bean:message bundle="sys-number" key="sysNumber.error.numberNameIsNotEmpty"/>');
			return false;
		}
		var fdOrder = $("input[name='fdOrder']").val();
		if(!isNull(fdOrder) && !Validate_Integer(fdOrder)){
			addError('<bean:message bundle="sys-number" key="sysNumberMain.fdOrder.format" />');	
			return false;
		}
		var str=$("input[name='fdContent']").val();
		if(isNull(str) || str=='[]'){
			addError('<bean:message bundle="sys-number" key="sysNumber.error.numberIsNotEmpty"/>');	
			return false;
		}
		var _fdContentJson = new Array();
		Page_CanCommit = false;
		//过滤name和ruleData为空的元素
		$.each(fdContentJson,function(idx,item){
			if(!isNull(item.name)&&!$.isEmptyObject(item.ruleData)){
				var newsItem = {'id':item.id,'name':item.name,'type':item.type,'ruleData':item.ruleData};
				_fdContentJson.push(newsItem);
			}else{
				Page_CanCommit = true;
			}
		});
		if(Page_CanCommit){
			addError('<bean:message bundle="sys-number" key="sysNumber.error.OneIsNotEmpty"/>');
			return false;
		}
		
		
		$("input[name='fdContent']").val(JSON.stringify(_fdContentJson));
		
		//设置是否默认值
		var evalue=document.getElementsByName("DefaultFlag")[0];
		var fvalue=document.getElementsByName("fdDefaultFlag")[0];
		if (evalue.checked == true) {
			fvalue.value = "0";
		} else {
			fvalue.value = "1";
		}

		isExists(JSON.stringify(_fdContentJson));
		if(temp_control_commit==1){
			return true;
		}else{
			return false;
		}
	}
	// 变更操作选择项
	function changeSelectPeriod(obj){
		if(obj.value == "0") {
			document.getElementById('li_period_start').style.display="none";
		} else {
			document.getElementById('li_period_start').style.display="block";
		}
	};

	// 初始化编辑显示
	function selectPeriod() {
		$.each(fdContentJson,function(idx,item){
			if(!isNull(item.name)&&!$.isEmptyObject(item.ruleData)){
				var period = item.ruleData.period;
				if(period) {
					if(period == "0") {
						document.getElementById('li_period_start').style.display="none";
					} else {
						document.getElementById('li_period_start').style.display="block";
					}
				}

			}
		});

		if(fdContentJson.length == 0) {
			document.getElementById('li_period_start').style.display="none";
		}
	}
</script>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>