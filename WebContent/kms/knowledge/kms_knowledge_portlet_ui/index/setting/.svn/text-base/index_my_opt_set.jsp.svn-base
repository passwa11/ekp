<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.util.IDGenerator" %>

<%
    JSONObject var = JSONObject.fromObject(request.getParameter("var"));
    pageContext.setAttribute("luivar", var);
    pageContext.setAttribute("luivarid","var_"+ IDGenerator.generateID());
    pageContext.setAttribute("luivarparam", StringUtil.isNotNull(var.getString("body")) ? JSONObject.fromObject(var.get("body")) : new JSONObject());
%>

<script>
    ${param['jsname']}.VarSet.push({
        "name":"opttype",
        "getter":function(){
            var showType = "checkbox";
            if(showType==""||showType=="select"){
                return $("#${luivarid}").val();
            }else if(showType=="radio"){
                return $("input[name='${luivarid}']:checked").val();
            }else if(showType=="checkbox"){
                var tempVal = [];
                $("input[name='${luivarid}']:checked").each(function(){
                    tempVal.push($(this).val());
                });
                return tempVal.join(";");
            }
        },
        "setter":function(val){
            var showType = "checkbox";
            if(showType==""||showType=="select"){
                $("#${luivarid}").val(val);
            }else if(showType=="radio"){
                $("input[name='${luivarid}']").each(function(){
                    if($(this).val() == val){
                        $(this).attr("checked","checked");
                    }
                });
            }else if(showType=="checkbox"){
                $("input[name='${luivarid}']").each(function(){
                    this.checked = false;
                });
                $("input[name='${luivarid}']").each(function(){
                    var vals = $.trim(val).split(";");
                    for(var i=0;i<vals.length;i++){
                        if(vals[i] == $.trim($(this).val())){
                            //$(this).attr("checked","checked");
                            this.checked = true;
                        }
                    }
                });
            }
        },
        "validation":function(){
            // 不需要校验
            var val = this['getter'].call();

            // if($.trim(val).split(";").length>3){
            //     return "操作显示最多选择3项";
            // }
            var requ = true;
            if(requ){
                if($.trim(val)==""){
                    return "操作显示项不能为空";
                }
            }
        }
    });
</script>

<tr>
    <td>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.optitem')}</td>
    <td>
        <label><input type="checkbox" name="${luivarid}" value="fdUpload" checked>&nbsp;${lfn:message('kms-knowledge:kms.knowledge.mobile.type.upload')}</label><br/>
        <label><input type="checkbox" name="${luivarid}" value="fdRead" checked>&nbsp;${lfn:message('kms-knowledge:kms.knowledge.mobile.type.read')}</label><br/>
        <label><input type="checkbox" name="${luivarid}" value="fdIntroduce" checked>&nbsp;${lfn:message('kms-knowledge:kms.knowledge.mobile.type.introduce')}</label><br/>
        <label><input type="checkbox" name="${luivarid}" value="fdCollect" checked>&nbsp;${lfn:message('kms-knowledge:kms.knowledge.mobile.type.collect')}</label><br/>
        <label><input type="checkbox" name="${luivarid}" value="fdComment" checked>&nbsp;${lfn:message('kms-knowledge:kms.knowledge.mobile.type.comment')}</label><br/>
        <label><input type="checkbox" name="${luivarid}" value="fdCorrection" checked>&nbsp;${lfn:message('kms-knowledge:kms.knowledge.mobile.type.correction')}</label><br/>
        <span style="color:red;">*<span><span class="com_help"></span></span></span>
    </td>
</tr>
