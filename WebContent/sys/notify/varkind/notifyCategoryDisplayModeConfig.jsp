<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.taglib.fn.LuiFunctions"%>
<%@page import="com.landray.kmss.util.*,com.landray.kmss.sys.notify.service.ISysNotifyCategoryService"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
JSONObject var = JSONObject.fromObject(request.getParameter("var"));
pageContext.setAttribute("luivar", var);
pageContext.setAttribute("luivarid","var_"+IDGenerator.generateID());
pageContext.setAttribute("luivarparam",StringUtil.isNotNull(var.getString("body")) ? JSONObject.fromObject(var.get("body")) : new JSONObject());
String dialogjsp = "/sys/notify/varkind/selectNotifyCategory.jsp";
String dialogrealurl = "openSelectNotifyCategoryDialog('!{nameField}','!{idField}','"+dialogjsp+"','"+ResourceUtil.getString("sys-ui:ui.vars.select")+"');";
dialogrealurl = dialogrealurl.replace("!{nameField}", pageContext.getAttribute("luivarid").toString()+"_categoryName");
dialogrealurl = dialogrealurl.replace("!{idField}", pageContext.getAttribute("luivarid").toString()+"_categoryId");
pageContext.setAttribute("luivardialogjs", dialogrealurl);
%>
<script>
var dialogWin = dialogWin || window;
${param['jsname']}.VarSet.push({
	"name":"${ luivar['key'] }",
	"getter":function(){
		var displayMode = $("input[type='radio'][name='${luivarid}_display_mode']:checked").val();
		return displayMode;
	},
	"setter":function(val){
		var displayMode = val;
		if(!displayMode){
			displayMode="widthData";
		}
		$("input[type='radio'][name='${luivarid}_display_mode']").each(function(){
			if(this.value == displayMode){
				this.checked = true; 
			}else{
				this.checked = false;
			}
		});
		setCategoryConfigDisplay();
	},
	"validation":function(){
		var displayMode = this['getter'].call();
		if(displayMode=="singleCategory"){
			if($("#${luivarid}_categoryId").val()==""){
				return "${ lfn:message('sys-notify:sysNotifyTodo.displayMode.singleCategory.null.tip') }";
			}
		}
	}
}); 

${param['jsname']}.VarSet.push({
	"name":"notifyCategory",
	"getter":function(){
		var categoryId = $("#${luivarid}_categoryId").val();
		var categoryName = $("#${luivarid}_categoryName").val();
		return {"notifyCategory":categoryId,"notifyCategoryName":categoryName};
	},
	"setter":function(val){
		if(val){
			$("#${luivarid}_categoryId").val(val.notifyCategory || '');
			$("#${luivarid}_categoryName").val(val.notifyCategoryName || '');			
		}
	}
});

	/**
	 * 打开消息分类列表选择框
	 * @return
	 */
	function openSelectNotifyCategoryDialog(nameField,idField,jsp,title){
		var _dialogWin = window.parent || dialogWin || window;
		seajs.use(['lui/dialog','lui/jquery'],function(dialog){
			dialog.iframe(jsp, title, function(val){
				if(!val){
					return;
				}
				$("#"+nameField).val(val.fdName);
				$("#"+idField).val(val.fdId);
			}, {width:750,height:550,"topWin":_dialogWin});
		});
	}
	
    /**
    * 清空已选择的消息分类
    * @return
    */
	function clearNotifyCategoryInfo(){
		$("#${luivarid}_categoryId").val('');
		$("#${luivarid}_categoryName").val('');
	}
    
    /**
    * 设置选择单个分类区域显示或隐藏(展现模式为“显示单个分类”才显示出来)
    * @return
    */
    function setCategoryConfigDisplay(){
    	var displayMode = $("input[type='radio'][name='${luivarid}_display_mode']:checked").val();
    	if (displayMode == "singleCategory") {
    		$("#${luivarid}_category_config_div").show();
    	}else{
    		$("#${luivarid}_category_config_div").hide();
    	}
    }
    
    /** 单选按钮onchange事件，如果选择的不是“显示单个分类”，则清空分类ID和名称 **/
	$("input[type='radio'][name='${luivarid}_display_mode']").change(function() {
		  if (this.value != 'singleCategory') {
			  clearNotifyCategoryInfo();
		  }
		  setCategoryConfigDisplay();
	});
</script>

<tr>
	<td>${ lfn:msg(luivar['name']) }</td>
	<td>
	    <div>
	        <!-- 显示分类与消息内容 -->
		    <label><input type="radio" name="${luivarid}_display_mode" value="widthData" ${luivar['default'] eq 'widthData'?'checked':''}>&nbsp;${lfn:message('sys-notify:sysNotifyTodo.displayMode.widthData')}</label>
		    &nbsp;&nbsp;
		    <!-- 只显示分类 -->
		    <label><input type="radio" name="${luivarid}_display_mode" value="noData" ${luivar['default'] eq 'noData'?'checked':''} >&nbsp;${lfn:message('sys-notify:sysNotifyTodo.displayMode.noData')}</label>	    
	    </div>
        <div style="margin-top:6px;" >
            <!-- 显示单个分类 -->
		    <label><input type="radio" name="${luivarid}_display_mode" value="singleCategory" ${luivar['default'] eq 'singleCategory'?'checked':''}>&nbsp;${lfn:message('sys-notify:sysNotifyTodo.displayMode.singleCategory')}</label>
		    <br/> 
		    <div id="${luivarid}_category_config_div" style="display:none;" >
			    <input type="hidden" id="${luivarid}_categoryId" name="${luivarid}_categoryId">
				<input class="inputsgl" readonly="readonly" id="${luivarid}_categoryName" name="${luivarid}_categoryName" type="text">
				<a href="javascript:void(0)"  class="com_btn_link" onclick="${luivardialogjs}" >${lfn:message('sys-ui:ui.vars.select')}</a>
			    <a href="javascript:void(0)"  class="com_btn_link" onclick="clearNotifyCategoryInfo()" >${lfn:message('sys-ui:ui.vars.clear')}</a>    
		    </div>   
        </div>
	</td>
</tr>
