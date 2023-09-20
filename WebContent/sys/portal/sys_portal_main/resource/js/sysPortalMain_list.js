
$(document).ready(function() { 

	seajs.use(['lui/jquery','lui/topic','lui/toolbar'], function($, topic, toolbar){
		LUI.ready(function(){
		    // 读取Cookie信息判断是否要显示“导出为初始数据”按钮
			var mark=getCookie();
			if(mark=='open'){
				var dataInitBtn = toolbar.buildButton({id:'dataInit',order:'1',text:portal_operation_button_data["exportInitData"],click:'Datainit_Submit()'});
				LUI('toolbar').addButton(dataInitBtn);
			}   		 
	    }); 
		// 监听系统公共事件，保存成功后自动刷新列表
		topic.subscribe('successReloadPage', function(){
			loadPortalData();
		});
	});
	
	// 加载门户数据
	loadPortalData();

}); 

/**
* 读取Cookie信息用于判断是否有开启初始数据导出
* @return 返回Cookie匹配的相关数据
*/
function getCookie(){   
	var arr,reg=new RegExp("(^| )isopen=([^;]*)(;|$)");   
	if(arr=document.cookie.match(reg)) return unescape(arr[2]);   
	else return null;   
}


/**
* 加载门户数据
* @return
*/
function loadPortalData(evt){
	//校验筛选器数据
	var criteriaData = checkData(evt);
	seajs.use(['sys/ui/js/dialog'], function(dialog) {
	    // 显示加载中提示框
	    var loadingDialog = dialog.loading();
		// 发起请求后台获取门户列表数据
		var requestUrl = Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=getPortalList";
		$.ajaxSetup({ cache: false });  // 禁止jquery ajax缓存
		$.ajax({
			url:requestUrl,
			type:"get",
			dataType: "json", 
			cache: false,
			async: true,
			data:criteriaData,
			success:function(data){
			   var dataList = data;
			   // 构建门户配置表格内容
			   createPortalTable(dataList);
			   // 隐藏加载中提示框
			   loadingDialog.hide();
			},
			error: function(err) {
				loadingDialog.hide();
			}
		});		
	});
}

/**
 * 获取筛选器选择数据
 * @param evt
 */
function checkData(evt){
	var criteriaData = portal_main_list_criteria_data;
	if(evt) {
		//门户名称
		if(evt.key == "fdName"){
			if(evt.values[0]) {
				criteriaData.fdName= evt.values[0].value;
			}else{
				criteriaData.fdName= "";
			}
		//状态
		}else if(evt.key == "fdEnabled"){
			if(evt.values[0]) {
				criteriaData.fdEnabled = evt.values[0].value;
			}else{
				criteriaData.fdEnabled= "";
			}
		//是否匿名
		}else if(evt.key == "fdAnonymous"){
			if(evt.values[0]) {
				criteriaData.fdAnonymous = evt.values[0].value;
			}else{
				criteriaData.fdAnonymous= "";
			}
		//创建时间
		}else if(evt.key == "docCreateTime"){
			if(evt.values[0]) {
				criteriaData.createStartTime = evt.values[0].value;
			}else{
				criteriaData.createStartTime= "";
			}
			if(evt.values[1]) {
				criteriaData.createEndTime = evt.values[1].value;
			}else{
				criteriaData.createEndTime= "";
			}
		}
	}
	return criteriaData;
}
/**
* 构建门户配置表格
* @param dataList 门户数据集合
* @return
*/
function createPortalTable(dataList){
	$("#portal_info_container").empty(); // 清空之前的内容
	
	var titleInfo = portal_main_list_title_data; // 门户配置表格标题
	var html = "";
	html+="<table class='xpage' style='width: 100%;'>";
	
	// 标题行
	html+="<tr class='tr_listfirst' style='height:31px;font-weight:bold'>";   
	html+="<td width='10pt'><input type='checkbox' onclick='selectAllPortal(this)'></td>"; // 全选-复选框
	html+="<td nowrap style='text-align:left;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+titleInfo.fdName+"</td>"; // 门户名称
	html+="<td nowrap width='250px' style='text-align:left;'>"+titleInfo.fdType+"</td>"; // 门户类型
	html+="<td nowrap width='50px' style='text-align:left;'>"+titleInfo.fdAnonymous+"</td>"; // 门户匿名
	html+="<td nowrap width='100px'>"+titleInfo.fdEnabled+"</td>";
	html+="<td nowrap width='200px'>"+titleInfo.fdOpts+"</td>";
	html+="</tr>";
	
	// 构建门户数据行
	var childRow = {"html":""};
	createPortalRow( dataList, childRow );
    html+=childRow.html;
    
	html+="</table>";

	$("#portal_info_container").append(html);
	
	// 添加样式
	$("#portal_info_container .xpage tr[over='yes']").each(function(i){
		$(this).attr("class","tr_listrow1");
	});

	// 绑定鼠标悬停事件（光棒效果）
	$("#portal_info_container .xpage tr[over='yes']").hover(
		  function (evt) {
		    $(this).addClass("tr_listrowo");
		    evt.stopPropagation();
		  },
		  function (evt) {
		    $(this).removeClass("tr_listrowo");
		    evt.stopPropagation();
		  }
	);
}


/**
* 构建门户配置数据行
* @param dataList 门户数据集合
* @param html     待追加拼接的HTML字符串   
* @return
*/
function createPortalRow( dataList, childRow ){
	for (var i=0; i<dataList.length; i++) {
		var rowData = dataList[i]; // 行数据
		var childrenList = rowData.children || []; // 子数据
		var hasChildren = childrenList.length>0 ? true : false; // 是否有子数据
		
		childRow.html+="<tr over='yes'>";  // ----------- TR Start -------------
		
		
		// 1、《复选框》列             当前行数据类型为“门户”时，才显示复选框
		var checkBoxHtml = (rowData.fdType=="portal") ? "<input type='checkbox' name='List_Selected' value='"+rowData.fdId+"'>" : "";
		childRow.html+="<td style='width:20px;'>"+checkBoxHtml+"</td>";
		
		
		// 2、《门户名称》列         
		childRow.html+="<td width='*'>";
		childRow.html+="<div style='text-align:left;'>";
	    // 当前行有子数据的情况下，显示“展开”图标，默认不展开
		if(hasChildren){
			childRow.html+="<div id='t_"+rowData.fdId+"' class='expand' onclick='toggle(this)' style='float:left;height:20px;width:20px;' ></div>";
		}else{
			childRow.html+="<div id='t_"+rowData.fdId+"' style='float:left;height:20px;width:20px;' ></div>";
		}
        // 门户名称
	    childRow.html+="<div style='float:left;'><a href='javascript:void(0)' onclick=\"titleClick('"+(rowData.fdType=="portal"?rowData.fdId:rowData.fdPageId)+"','"+rowData.fdType+"')\">"+rowData.fdName+"</a></div>";
		childRow.html+="</div>";
		childRow.html+="<div style='clear: both;'></div>";
		childRow.html+="</td>";
		
		
		// 3、《门户类型》列    
		childRow.html+="<td style='width:250px'>";
		childRow.html+="<div class='inl' style='text-align:left;'>";
		childRow.html+=portal_type_data[rowData.fdType];
		if(rowData.fdType!="portal"){
			childRow.html+="&nbsp;&nbsp;:&nbsp;&nbsp;["+rowData.fdPageName+"]";
		}
		childRow.html+="</div>";
		childRow.html+="</td>";
		
		// 4、《匿名》列 @author 吴进 by 20191113
		childRow.html+="<td style='width:50px'>";
		childRow.html+="<div class='inl' style='text-align:left;'>";
		childRow.html+=portal_anonymous_data[rowData.fdAnonymous];
		childRow.html+="</div>";
		childRow.html+="</td>";

		// 4、《是否启用》列   
		childRow.html+="<td style='width:100px'>";
		childRow.html+="<div class='inl'>";
		childRow.html+=portal_enabled_status_data[rowData.fdEnabled];
		childRow.html+="</div>";
		childRow.html+="</td>";

		// 5、《操作》列
		childRow.html+="<td style='width:200px'>";
		if(rowData.isCanEdit){ // 判断是否允许“编辑”
			childRow.html+="<div style='float:left;width: 50px;'>";
			childRow.html+="<div class='inl conf_btn_edit'>";
			childRow.html+="<a href='javascript:void(0);' class='btn_txt' onclick=\"configClick('"+rowData.fdId+"','"+rowData.fdType+"','"+rowData.fdAnonymous+"')\">"+portal_operation_button_data["edit"]+"</a>";
			childRow.html+="</div>";
			childRow.html+="</div>";
		}
		
		if(rowData.isCanDel){  // 判断是否允许“删除”
			childRow.html+="<div style='float:left;width: 50px;'>";
			childRow.html+="<div class='inl conf_btn_edit'>";
			childRow.html+="<a href='javascript:void(0);' class='btn_txt' onclick=\"deleteClick('"+rowData.fdId+"','"+rowData.fdType+"')\">"+portal_operation_button_data["delete"]+"</a>";
			childRow.html+="</div>";
			childRow.html+="</div>";
		}else{
			childRow.html+="<div style='float:left;width: 50px;'><span>&nbsp;</span></div>";
		}
		
		if(rowData.isCanEnableOrDisable){ // 判断是否要显示“启用”或“禁用”按钮
			childRow.html+="<div style='float:left;width: 50px;'>";
			childRow.html+="<div class='inl conf_btn_edit'>";
			if(rowData.fdEnabled==false){ // 显示“启 用”
			   childRow.html+="<a href='javascript:void(0);' class='btn_txt' onclick=\"enableClick('"+rowData.fdId+"')\">"+portal_operation_button_data["enable"]+"</a>";
			}else{ // 显示“禁用”
			   childRow.html+="<a href='javascript:void(0);' class='btn_txt' onclick=\"disableClick('"+rowData.fdId+"')\">"+portal_operation_button_data["disable"]+"</a>";
			}
			childRow.html+="</div>";
			childRow.html+="</div>";				
		}else{
			childRow.html+="<div style='float:left;width: 50px;'><span>&nbsp;</span></div>";
		}
		// 导出操作列
		if(rowData.fdType=="portal") {
			//导出
			childRow.html += "<div style='float:left;width: 50px;'>";
			childRow.html += "<div class='inl conf_btn_edit'>";
			childRow.html += "<a href='javascript:void(0);' class='btn_txt' onclick=\"exportPortal('" + rowData.fdId + "')\">" + portal_operation_button_data["export"] + "</a>";
			childRow.html += "</div>";
			childRow.html += "</div>";
		}
		childRow.html+="<div style='clear: both;'></div>";
		childRow.html+="</td>";
		
		
		if(hasChildren){
			childRow.html+="<tr id='d_"+rowData.fdId+"' style='display: none'>";
			childRow.html+="<td colspan='6'>";
			   childRow.html+="<table width='100%' style='border-spacing:0px;'>";
			     childRow.html+="<tr>";
			       childRow.html+="<td width='20'></td>";
			       childRow.html+="<td style='border:0px;border-top:dotted 1px #333;border-bottom: dotted 1px #333;'>";
						childRow.html+="<table width='100%'>";
						createPortalRow( childrenList, childRow ); // 递归构建子数据行
						childRow.html+="</table>";
			       childRow.html+="<td>";
			     childRow.html+="</tr>";
			   childRow.html+="</table>";
			childRow.html+="</td>";
			childRow.html+="</tr>";
		}
		
		childRow.html+="</tr>";  // ----------- TR End -------------
	}
}


/**
* 全选按钮点击响应事件
* @param obj  HTML DOM Element对象
* @return
*/
function selectAllPortal(obj){
	var checkAll = document.getElementsByName('List_Selected');
	for (var i=0; i<checkAll.length; i++)
		 checkAll[i].checked = obj.checked;
}


/**
* 展开、收缩图标点击响应事件
* @param obj  HTML DOM Element对象
* @return
*/
function toggle(obj){
	var id = obj.id.replace("t_","d_");
	if($("#"+id).is(":visible")){
		$(obj).removeClass("collapse").addClass("expand");
		$("#"+id).hide();
	}else{
		$(obj).removeClass("expand").addClass("collapse");
		$("#"+id).show();
	}
}


/**
* “ 新建门户 ” 按钮响应事件
* @return
*/
function addPortal(){
	var checkAll = $("[name='List_Selected']:checked");
	var addUrl = Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=add&fdAnonymous=0";
	if(checkAll.length>0){
		if(checkAll.length==1){
			addUrl+="&parentportal="+checkAll[0].value;
			Com_OpenWindow(addUrl);
		}else{
			alert("只能选择一个门户进行新建，请重新选择");
			return false;
		}
	}else{
		Com_OpenWindow(addUrl);
	}
}

/**
 * 导出门户 按钮响应事件
 * @return
 */
function exportPortal(id){
	var exportUrl = Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalPackage.do?method=exportPortal&portalId="+id;
	Com_OpenWindow(exportUrl);
}
/**
 * 导入门户 按钮响应事件
 * @return
 */
function importPortal(){
	//var importUrl = Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=importPortal";
	//Com_OpenWindow(importUrl);
	seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
		var url = '/sys/portal/portal/uploadPortal.jsp';
		dialog.iframe(url,portal_msg_info_data["importTitle"], function(value) {
			window.location.reload(true);
		}, {
			"width" : 700,
			"height" : 400
		});
	});
}


/**
 * “ 新建匿名门户 ” 按钮响应事件
 * @author 吴进 by 20191113
 * @returns
 */
function addPortalAnonymous() {
	var checkAll = $("[name='List_Selected']:checked");
	var addUrl = Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=addAnonymous&fdAnonymous=1";
	if (checkAll.length > 0) {
		if (checkAll.length == 1) {
			addUrl+="&parentportal="+checkAll[0].value;
			Com_OpenWindow(addUrl);
		} else {
			alert("只能选择一个门户进行新建，请重新选择");
			return false;
		}
	} else {
		Com_OpenWindow(addUrl);
	}
}

/**
 * 批量删除
 */
function deleteAllPortal(id){
	seajs.use(['sys/ui/js/dialog','lui/jquery'], function(dialog,$) {
		var values = [];
		if(id){
			values.push(id);
		}else{
			$("input[name='List_Selected']:checked").each(function(){
				values.push($(this).val());
			});
		}
		if(values.length==0){
			dialog.alert(portal_msg_info_data["page.noSelect"]);
			return;
		}
		var url = Com_Parameter.ContextPath + 'sys/portal/sys_portal_main/sysPortalMain.do?method=deleteall';

		dialog.confirm(portal_msg_info_data["deleteAllTitle"],function(value){
			if(value){
				window.del_load = dialog.loading();
				$.ajax({
					url : url,
					type : 'POST',
					data : $.param({"List_Selected" : values}, true),
					dataType : 'json',
					error : function(data) {
						if(window.del_load != null) {
							window.del_load.hide();
						}
					},
					success: function(data) {
						if(window.del_load != null) {
							window.del_load.hide();
						}
						dialog.result(data);
						//刷新
						loadPortalData();
					}
				});
			}
		});

	});
}


/**
* “ 删除 ” 按钮响应事件
* @param id     数据唯一标识ID
* @param type   类型（portal、page、url）
* @return
*/
function deleteClick(id,type){
	seajs.use(['sys/ui/js/dialog'], function(dialog){
		if(type=='portal'){ 
			dialog.confirm(portal_msg_info_data["sysPortalMain.msg.delete"],function(value){
				if(value==true){
					window.open(Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=delete&fdId="+id+"&s_path="+portal_request_param["s_path"], '_self');
				}
			});
		}
		if(type=='page'||type=='url'){
			dialog.confirm(portal_msg_info_data["sysPortalPage.msg.delete"],function(value){
				if(value==true){
					window.open(Com_Parameter.ContextPath+"sys/portal/sys_portal_page/sysPortalPage.do?method=delete&fdId="+id, '_self');
				}
			});
		}
	});
}

/**
* “ 启用 ” 按钮响应事件
* @param id  数据唯一标识ID
* @return
*/
function enableClick(id){
	seajs.use(['sys/ui/js/dialog'], function(dialog){
		dialog.confirm(portal_msg_info_data["sysPortalMain.msg.enable"],function(value){
			if(value==true){
				window.open(Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=setEnable&fdId="+id+"&s_path="+portal_request_param["s_path"], '_self');	
			}
		});
	});
}


/**
* “ 禁用 ” 按钮响应事件
* @param id  数据唯一标识ID
* @return
*/
function disableClick(id){
	seajs.use(['sys/ui/js/dialog'], function(dialog){
		dialog.confirm(portal_msg_info_data["sysPortalMain.msg.disable"],function(value){
			if(value==true){
				window.open(Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=setDisable&fdId="+id+"&s_path="+portal_request_param["s_path"], '_self');
			}
		});
	});
}


/**
* 门户或页面名称点击响应事件
* @param id     数据唯一标识ID
* @param type   类型（portal、page、url）
* @return
*/
function titleClick(id,type){
	if(type=='portal')
		window.open(Com_Parameter.ContextPath+"sys/portal/page.jsp?portalId="+id);
	if(type=='page'||type=='url')
		window.open(Com_Parameter.ContextPath+"sys/portal/page.jsp?pageId="+id);
}

/**
* “ 编辑 ” 按钮响应事件
* @author 吴进 by 20191115
* @param id     数据唯一标识ID
* @param type   类型（portal、page、url）
* @param anonymous 匿名（0普通 1匿名）
* @return
*/
function configClick(id, type, anonymous) {
	if (type=='portal') {
		if ('false' == anonymous) {
			window.open(Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=edit&fdId="+id+"&fdAnonymous=0");
		} else if ('true' == anonymous) {
			window.open(Com_Parameter.ContextPath+"sys/portal/sys_portal_main/sysPortalMain.do?method=editAnonymous&fdId="+id+"&fdAnonymous=1");
		}
	}
	if (type=='page'||type=='url') {
		if ('false' == anonymous) {
			window.open(Com_Parameter.ContextPath+"sys/portal/sys_portal_page/sysPortalPage.do?method=edit&fdId="+id+"&fdAnonymous=0");
		} else if ('true' == anonymous) {
			window.open(Com_Parameter.ContextPath+"sys/portal/sys_portal_page/sysPortalPage.do?method=editAnonymous&fdId="+id+"&fdAnonymous=1");
		}
	}
}

/**
* “ 导出为初始数据 ” 按钮响应事件
* @return
*/
function Datainit_Submit(){
	seajs.use(['lui/jquery','lui/util/env','sys/ui/js/dialog'], function($,env,dialog){
			var select = document.getElementsByName("List_Selected");
			var values = [];
			var selected;
			for (var i = 0; i < select.length; i++) {
				if (select[i].checked) {
					values.push(select[i].value);
					selected = true;
				}
			}
			if (selected) {
				var loading = dialog.loading();
				$.post(env.fn.formatUrl('/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=export&formName=sysPortalMainForm'),
						$.param({ "List_Selected" : values}, true), 
						function(data, textStatus, xhr) {
							loading.hide();
							if (data.status) {
								dialog.success(portal_msg_info_data["return.optSuccess"]);
							} else {
								dialog.failure(portal_msg_info_data["return.optFailure"]);
							}
						}, 'json').error(function(){
							loading.hide();
							dialog.failure(portal_msg_info_data["return.optFailure"]);
						});
			}else{
				dialog.alert(portal_msg_info_data["page.noSelect"]);
			}
	});
}