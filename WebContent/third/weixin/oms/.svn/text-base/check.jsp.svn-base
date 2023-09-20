<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#dl").attr("style","display: none;position: absolute;left:"+(document.body.clientWidth)/2);
		$("#dl").attr("style",$("#dl").attr("style")+";top:"+(document.body.clientHeight-$("#dl")[0].clientHeight)/2);
	});
	function check(){
        $(".erweima").show();
		var url = '<c:url value="/third/wx/weixinSynchroOrgCheck.do?method=checkData" />';
        var ehp = "<tr><td>";
        var ehe = "</td></tr>";
		$.post(url,function(data){
            if(data.suc=="1"){
                $(".erweima").hide();
            	$("#ed").empty();
                var ed = $.parseJSON(data.errorDept);
                if(ed.length>0){
	                for(var i=0;i<ed.length;i++){
						$("#ed").append(ehp+ed[i].info+ehe);
	                }
                    $("#Label_Tabel_Label_Btn_1").attr("value",'<bean:message key="third.wx.oms.errordept" bundle="third-weixin"/>('+ed.length+')');
                }else{
                	$("#Label_Tabel_Label_Btn_1").attr("value",'<bean:message key="third.wx.oms.errordept" bundle="third-weixin"/>');
                	$("#ed").append(ehp+'<bean:message key="third.wx.oms.dept" bundle="third-weixin"/>'+ehe);
                }
                
                $("#ep").empty();
                var ep = $.parseJSON(data.errorPerson);
                if(ep.length>0){
	                for(var i=0;i<ep.length;i++){
	                    $("#ep").append(ehp+ep[i].info+ehe);
	                }
                	 $("#Label_Tabel_Label_Btn_2").attr("value",'<bean:message key="third.wx.oms.errorperson" bundle="third-weixin"/>('+ep.length+')');
                }else{
                	 $("#Label_Tabel_Label_Btn_2").attr("value",'<bean:message key="third.wx.oms.errorperson" bundle="third-weixin"/>');
                	$("#ep").append(ehp+'<bean:message key="third.wx.oms.person" bundle="third-weixin"/>'+ehe);
                }
            }else{
            	$(".erweima").hide();
				alert(data.msg);
            }
        },"json");
	}
	
</script>

<div id="optBarDiv">
    <input  type="button" value="<bean:message key="third.wx.oms.check.main" bundle="third-weixin"/>" onclick="check();">
    <input  type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<center>
<div id="dl" class="erweima">
	<img alt="" src='<c:url value="/sys/portal/template/default/loading.gif" />'>
</div>
<table id="Label_Tabel" width=95%>
    <tr LKS_LabelName="<bean:message key="third.wx.oms.errordept" bundle="third-weixin"/>">
      <td>
        <table class="tb_normal" width=100% id="ed">
        </table>
      </td>
    </tr>
    <tr LKS_LabelName="<bean:message key="third.wx.oms.errorperson" bundle="third-weixin"/>">
      <td>
        <table class="tb_normal" width=100% id="ep">
        </table>
      </td>
    </tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
