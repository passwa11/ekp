<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<script>
	Com_IncludeFile("dialog.js|doclist.js|optbar.js|data.js|jquery.js|json2.js");
</script>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js" type="text/javascript"></script>
<style type="text/css">
    
    	.lui_paragraph_title{
    		font-size: 15px;
    		color: #15a4fa;
        	padding: 15px 0px 5px 0px;
    	}
    	.lui_paragraph_title span{
    		display: inline-block;
    		margin: -2px 5px 0px 0px;
    	}
    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
      		border: 0px;
      		color: #868686
    	}
    
</style>
<script type="text/javascript">
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("dialog.js|doclist.js|optbar.js|data.js|jquery.js|json2.js");
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/tic/core/common/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/tic/core/common/tic_core_trans_sett/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    
    function selectFunction() {
    	
    		Dialog_TreeList(false,"fdFunctionId","fdFunctionName",";",
    			           "ticCoreFindFunctionService&selectId=!{value}&type=cate",
    			           "业务分类",
    			           "ticCoreFindFunctionService&selectId=!{value}&type=func",
    			           after_dialogSelect,
    			           "ticCoreFindFunctionService&type=search&keyword=!{keyword}",
    			           null,null,null,
    			           "选择函数");
   }
    
    var trs =new Array();
    
    function genParaInHtml( obj,  parentId){
    	var name = obj.name;
    	var title = obj.title;
    	var id = name;
    	if(parentId){
    		id = parentId+"-"+name;
    	}
    	var tr = {};
    	tr.id = id;
    	tr.name = name;
    	tr.title = title;
    	tr.parentId = parentId;
    	trs.push(tr);
    	var children = obj.children;
    	if(children){
    		$.each(children, function(idx2, obj2) {
    			genParaInHtml(obj2,id);
    		});
    	}
    }
    
    function after_dialogSelect(rtn, soapVersionValue) {
    	//debugger;
    	//alert(rtn);
    	if (!rtn) {
    		return;
    	}
/*     	if(true){
    		return;
    	} */
    	var data = rtn.GetHashMapArray();
    	if (data && data.length > 0) {
    		var funcType = data[0]["type"];
    		var info = data[0]["info"];
    		var infoObj = JSON.parse(info);
    		var paraIn = infoObj.paraIn;
    		trs = new Array();
    		$.each(paraIn, function(idx, obj) {
    			genParaInHtml(obj,"");
    		});
    		for(i=0;i<trs.length;i++){
    			var tr = trs[i];
				var tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td><input type='text'></input></td><td></td>"+"</tr>");
				if(tr.parentId){
    				tr_new.addClass("child-of-"+tr.parentId);
    			}
    			$("#table_paraIn").append(tr_new);
    			
    		}
    		$("#table_paraIn").treeTable({ initialState: true,indent:15 }).expandAll();
    		
    		
    		var paraOut = infoObj.paraOut;
    		trs = new Array();
    		$.each(paraOut, function(idx, obj) {
    			genParaInHtml(obj,"");
    		});
    		for(i=0;i<trs.length;i++){
    			var tr = trs[i];
				var tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td>"+"</tr>");
				if(tr.parentId){
    				tr_new.addClass("child-of-"+tr.parentId);
    			}
    			$("#table_paraOut").append(tr_new);
    			
    		}
    		$("#table_paraOut").treeTable({ initialState: true,indent:15 }).expandAll();
    		
    	}
    }
    
    
    function selectForm(){
    	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($,dialog,topic) {
    		dialog.categoryForNewFile(
				'com.landray.kmss.km.review.model.KmReviewTemplate',
				null,false,null,function(rtn) {
					// 门户无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
					alert(rtn);
				},'',null,null,true);
    	
    	}
    	
    	);
    }
    
</script>


<html:form action="/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${ticCoreTransSettForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.ticCoreTransSettForm, 'update');">
            </c:when>
            <c:when test="${ticCoreTransSettForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.ticCoreTransSettForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('tic-core-common:table.ticCoreTransSett') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
            	<tr>
                    <td class="td_normal_title" width="15%">
                        转换函数名称
                    </td>
                    <td width="35%">
                        <xform:text
						property="fdName" style="width:85%" />
                    </td>
                    <td class="td_normal_title" width="15%">
                        业务分类
                    </td>
                    <td width="35%">
					<html:hidden property="fdCategoryId" /> <xform:text
						property="fdCategoryName" required="true" style="width:34%" /> <a
						href="#" 
						onclick="Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 'ticCoreBusiCateTreeService&parentId=!{value}&type=${JsParam.fdAppType}', 
			'<bean:message key="table.ticCoreTransSett" bundle="tic-core-common"/>', null, null, 
			'${ticCoreBusiCateForm.fdId}', null, null, 
			'业务分类');">
					<bean:message key="dialog.selectOther" /> </a>
					</td>
                </tr>
                
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}
                    </td>
                    <td width="35%">
                       
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreTransSett.fdIsDefault')}
                    </td>
                    <td width="35%">
                        <%-- 是否默认--%>
                            <div id="_xform_fdIsDefault" _xform_type="radio">
                                <xform:radio property="fdIsDefault" htmlElementProperties="id='fdIsDefault'" showStatus="view">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreTransSett.fdTransType')}
                    </td>
                    
                   
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreTransSett.fdTransDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 转换描述--%>
                            <div id="_xform_fdTransDesc" _xform_type="textarea">
                                <xform:textarea property="fdTransDesc" showStatus="edit" style="width:95%;" />
                            </div>
                    </td>
                </tr>
            </table>
            
            <br/>
            <div align="left">转换配置</div>
            
            
            <table class="tb_normal" width="100%">
            	<tr>
            	<td class="td_normal_title">
            		第三方函数选择
            	</td>
            	<td class="td_normal_title">
            		转换函数构建
            	</th>
            	</td>
            	<tr>
                    <td width="50%">
                    	<div style="display: inline;;float:left">
                        第三方函数绑定：
                        </div>
                        <xform:dialog required="true" propertyId="fdFunctionId" propertyName="fdFunctionName"
						 	subject="关联函数" dialogJs="selectFunction()" style="width:35%;float:left">
						</xform:dialog>
						<br/>
						<br/>
						第三方函数交互方式：SOAP
						<br/>
						<br/>
						第三方函数入参（需配置参数值）：
                        
                        转换范围设置：    <div id="_xform_fdTransType" _xform_type="select">
                                <xform:select property="fdTransType" htmlElementProperties="id='fdTransType'" showStatus="edit">
                                    <xform:enumsDataSource enumsType="tic_core_trans_type" />
                                </xform:select>
                            </div>
						<br/>
						<div style="width:100%">
						<table class="tb_normal" style="width:100%" id="table_paraIn">
						<tr>
			            	<td class="td_normal_title">
									参数名称
									</td>
			            	<td class="td_normal_title">
									参数说明
									</td>
			            	<td class="td_normal_title">
									参数值
									</td>
			            	<td class="td_normal_title">
									
									</td>
						</tr>
						</table>
						
						<br/>
						<br/>
						第三方函数出参：
						<br/>
						<div style="width:100%">
						<table class="tb_normal" style="width:100%" id="table_paraOut">
						<tr>
			            	<td class="td_normal_title">
									参数名称
									</td>
			            	<td class="td_normal_title">
									参数说明
									</td>
						</tr>
						</table>
						</div>	
                    </td>
                    
                    <td width="50%">
                        选择表单</a></br>
                         表单所属模块：
					        <select>
					        <option value="com.landray.kmss.km.review.model.KmReviewMain">流程管理</option>
					        </select>
					        表单模板：
        					<a href="javascript:void(0);" onClick="selectForm();">选择表单模板</a>
        </br>
                        转换函数入参:<a href="javascript:void(0);" onClick="">入参批量操作</a></br>
                        <div style="width:100%">
						<table class="tb_normal" style="width:100%" id="table_paraIn">
						<tr>
			            	<td class="td_normal_title">
									参数名称
									</td>
			            	<td class="td_normal_title">
									参数说明
									</td>
			            	<td class="td_normal_title">
									参数类型
									</td>
			            	<td class="td_normal_title">
									<a href="javascript:void(0);" onclick="">新增</a> <a href="javascript:void(0);" onclick="">删除</a>
							</td>
						</tr>
						
						</table>
						
						<br/>
						<br/>
						第三方函数出参：
						<br/>
						<div style="width:100%">
						<table class="tb_normal" style="width:100%" id="table_paraOut">
						<tr>
			            	<td class="td_normal_title">
									参数名称
									</td>
			            	<td class="td_normal_title">
									参数说明
									</td>
			            	<td class="td_normal_title">
									参数值
									</td>
			            	<td class="td_normal_title">
									
									</td>
						</tr>
						</table>
						</div>	
                    </td>
                    
                </tr>
                
                
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
        $("#test").treeTable({ initialState: true });
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>