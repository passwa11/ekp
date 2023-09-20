<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%-- Hibernate二级缓存管理页面，只管理声明了管理扩展的缓存 --%>

<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="title"><bean:message bundle="sys-cache" key="hibernate.cache" /></template:replace>
<template:replace name="content">
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
	<div style="margin-top:25px">
	<p class="configtitle"><bean:message bundle="sys-cache" key="hibernate.cache" /></p>
		<center>
			<table class="tb_normal" width="85%" >
                <tr>
                    <td class="td_normal_title" width=15%>
                        <%--缓存总开关--%>
                        ${lfn:message('sys-cache:hibernate.cache.clean.all')}
                    </td>
                    <td>
                        <ui:button text="${lfn:message('sys-cache:hibernate.cache.clean')}" height="25" width="85" onclick="cleanCache('all','all');"></ui:button>&nbsp;&nbsp;
                        <span style="outline: none;color: #F95A5A;">${lfn:message('sys-cache:hibernate.cache.clean.des')}</span>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width=15%>
                        <%--缓存总开关--%>
                        ${lfn:message('sys-cache:hibernate.cache.open')}
                    </td>
                    <td>
                        <ui:switch property="enable" onValueChange="config_chgEnabled(this);" enabledText="${lfn:message('sys-cache:btn.enable')}" disabledText="${lfn:message('sys-cache:btn.disable')}">
                        </ui:switch>
                    </td>
                </tr>
            </table>
            <br>
            <table class="tb_normal" width="85%" id="dataTableRow">
                <tr>
                    <td class="td_normal_title" style="text-align: center">${lfn:message('sys-cache:hibernate.cache.table.name')}</td>
                    <td class="td_normal_title" style="text-align: center">${lfn:message('sys-cache:hibernate.cache.table.des')}</td>
                    <td class="td_normal_title" style="text-align: center">${lfn:message('sys-cache:hibernate.cache.table.type')}</td>
                    <%--<td class="td_normal_title" style="text-align: center">所属模块</td>--%>
                    <td colspan="2" class="td_normal_title" style="text-align: center">${lfn:message('sys-cache:hibernate.cache.table.operation')}</td>
                </tr>
                <c:forEach var="cache" items="${cacheList}">
                    <tr class="tr_normal_title" style="text-align:left;cursor:pointer;" id="${cache.modelName}" onclick="showRow(this);">
                        <td colspan="5">
                            <img src="${KMSS_Parameter_StylePath}icons/plus.gif"/>&nbsp;&nbsp;
                            <c:out value="${cache.modelName}" />
                            <label onclick="selectOpt(this);event.cancelBubble=true;">
                                <input type="checkbox" style="top:2px"/>
                                <bean:message key="sysAuthRole.selectAll" bundle="sys-authorization"/>
                            </label>
                        </td>
                    </tr>
                    <%-- 缓存数据 --%>
                    <c:forEach var="cachevo" items="${cache.modelList}" varStatus="varStatus">
                        <tr style="text-align:left;cursor:pointer;" id="${cachevo.moduleMsg}_${varStatus.index }">
                            <td>${cachevo.nameMsg}</td>
                            <td>${cachevo.descMsg}</td>
                            <td>${cachevo.typeMsg}</td>
                            <%--<td>${cachevo.moduleMsg}</td>--%>
                            <td width="10%" style="text-align: center">
                                <ui:switch id="${cachevo.moduleMsg}_${varStatus.index }" property="value(${cachevo.regionName})" enabledText="${lfn:message('sys-cache:btn.enable')}" disabledText="${lfn:message('sys-cache:btn.disable')}"></ui:switch>
                            </td>
                            <td width="10%" style="text-align: center">
                                <ui:button text="${lfn:message('sys-cache:hibernate.cache.table.but')}" height="20" width="50" onclick="cleanCache('${cachevo.type}','${cachevo.regionName}');"></ui:button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
			<div style="margin-bottom: 10px;margin-top:25px">
				  <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="commitMethod();"></ui:button>
			</div>
		</center>
	</div>
    <html:hidden property="method_GET" />
    <html:hidden property="modelName"  value="com.landray.kmss.sys.cache.hibernate.HibernateRegionConfig"/>
</html:form>
<script type="text/javascript">
 	function commitMethod(){
 			Com_Submit(document.sysAppConfigForm, 'update');
 	}

    function config_chgEnabled(enableObj){
        var isChecked = $($(enableObj).parents("td:first")).find("input[type='hidden'][name*='enable']").val();
 	    $("#dataTableRow").find("tr[id*='_']").each(function(){
            changeStatus(isChecked,this);
        });
    }

    function showRow(trObj){
 	    var _div_id = $(trObj).attr("id");
        var tbodyObj = $(trObj).parents("table:first");
        var obj = trObj.getElementsByTagName("IMG")[0];
        trObj = trObj.nextSibling;
        while(trObj!=null){
            if(trObj!=null && trObj.tagName=="TR"){
                break;
            }
            trObj = trObj.nextSibling;
        }
        var trObj$ = $(trObj);
        if(trObj$.is(":hidden")){
            $(tbodyObj).find("tr[id*="+_div_id+"_]").each(function(){
                $(this).show();
            });
            obj.setAttribute("src",Com_Parameter.StylePath+"icons/minus.gif");
        }else{
            $(tbodyObj).find("tr[id*="+_div_id+"_]").each(function(){
                $(this).hide();
            });
            obj.src = Com_Parameter.StylePath+"icons/plus.gif";
        }
    }

    function selectOpt(thisObj){
 	    //是否选中
        var isChecked = $(thisObj).find("input[type='checkbox']").prop("checked");
 	    var trObj = $(thisObj).parents("tr:first");
        var tbodyObj = $(thisObj).parents("table:first");
        var _row_name = trObj.attr("id");
        $(tbodyObj).find("tr[id*="+_row_name+"_]").each(function(){
            changeStatus(isChecked,this);
        });
    }

    /**
     * 修改switch控件
     * @param isChecked 是否选择标识
     * @param trObj 行dom
     */
    function changeStatus(isChecked,trObj){
        var enable='${lfn:message('sys-cache:btn.enable')}';
        var disable='${lfn:message('sys-cache:btn.disable')}';
        var _children = $(trObj).find("input[type='hidden'][name*='value(']").val();
        //手动出发switch控件
        if((isChecked+"")!=_children){
            $(trObj).find("span:first").click();
        }
        //手动修改文字显示
        var span = $(trObj).find("span[name='switchText']");
        if(isChecked&&(isChecked+"")=='true'){
            span.html(enable);
        }else{
            span.html(disable);
        }
        //手动修改实际值
        $(trObj).find("input[type='hidden'][name*='value(']").val(isChecked);
    }

    /**
     * 清理全部缓存
     */
    function  cleanCache(type,cachaName){
        seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog){
            var tipInfo = '';
            if(type=='all'){
                tipInfo='${lfn:message('sys-cache:hibernate.cache.clean.all.tip')}';
            }else{
                tipInfo='${lfn:message('sys-cache:hibernate.cache.clean.child.tip')}';
            }
            dialog.confirm(tipInfo, function(ok) {
                if(ok == true) {
                    $.ajax({
                        type : "POST",
                        dataType : "json",
                        url : "${LUI_ContextPath}/sys/cache/HibernateCache.do?method=removeHibernaterCache",
                        data : {
                            cacheType:type,
                            cachaName:cachaName
                        },
                        success : function(result) {
                            if(result.success){
                                dialog.alert('${lfn:message('sys-cache:hibernate.cache.clean.success')}');
                            }else{
                                dialog.alert(result.error);
                            }
                        },
                        error : function(e) {
                                console.log(e)
                        }
                    });
                }
            });
        });
    }
</script>
</template:replace>
</template:include>

