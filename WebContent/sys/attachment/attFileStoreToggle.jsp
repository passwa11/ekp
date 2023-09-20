<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil"%>
<%@ page import="com.landray.kmss.sys.filestore.location.model.SysFileLocation"%>

<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-attachment" key="attachment.filestore.toggle" />
	</template:replace>
	<template:replace name="body">
<script type="text/javascript">
	seajs.use( [ 'lui/dialog','lui/jquery','theme!form' ], function(dialog,$) {
		window.dialog = dialog;
		window.$ = $;
		$(document).ready(refreshToggling);
	});
	
	//开始迁移附件
	function startToggleAtt(){
		var soruceChecked = $("#attToggleSource").val();
	    var targetChecked = $("#attToggleTarget").val();
	    if(!soruceChecked){
            dialog.alert("请选择源位置");
            return;
        }
	    if(!targetChecked){
            dialog.alert("请选择目标位置");
            return;
        }
	    if(soruceChecked == targetChecked){
            dialog.alert("源位置和目标位置一样，无须迁移");
            return;
        }
	    var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        var freeTimeChange = $("#freeTimeChange").is(":checked");
        var toggleBeginTime = 1;
        var toggleEndTime = 7;
	    if(!freeTimeChange){
            toggleBeginTime = $("#toggleBeginTime").val() || toggleBeginTime;
            toggleEndTime = $("#toggleEndTime").val() || toggleEndTime;
        }
	    dialog.confirm("您是否已做好备份？确认执行迁移附件操作？",function(confirm){
	    	if(!confirm){
	    		return;
	    	}
	    	var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=toggleAttchment&operate=start"; 
	        $.ajax({    
	            type:"POST",    
	            url:url,  
	            data:{"source":soruceChecked,"target":targetChecked,"toggleBeginTime":toggleBeginTime,
	            	"toggleEndTime":toggleEndTime,"beginDate":beginDate,"endDate":endDate},
	            dataType:"json",
	            success:function(data){
	                if (data.status == "1") {
	                	dialog.alert(data.toggleMsg);
	                	refreshToggling();
	                }else{
	                	dialog.alert(data.errrMsg);
	                }
	           }
	        });
	    });
	}
	
	
	//结束迁移附件
    function endToggleAtt(){
    	var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=toggleAttchment&operate=end"; 
        $.ajax({     
            type:"POST",    
            url:url,   
            data:{"toggleBeginTime":0,"toggleEndTime":0},
            dataType:"json",
            success:function(data){
                if (data.status == "1") {
                	dialog.alert("附件迁移定时任务已删除");
                	refreshToggling();
                }else{
                	dialog.alert(data.errrMsg);
                }
           }
        });
    }
	
    function displayToggling(toggling){
    	if(toggling){
    		$("#toggleMsg").text("附件迁移定时任务正在进行...");
    	}else{
    		$("#toggleMsg").text("附件迁移定时任务未运行");
    	}
    	if(window.togglingInterval){
            return;
        }
    	window.togglingInterval = setInterval(refreshToggling,1000 * 60);
    }
	
    //刷新附件迁移状态
    function refreshToggling(){
    	var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=toggleAttchment&operate=status"; 
        $.ajax({
            type:"POST",    
            url:url,   
            data:{"toggleBeginTime":0,"toggleEndTime":0},
            dataType:"json",
            success:function(data){
                if (data.status == "1") {
                    displayToggling(data.toggling);
                }
           }
        });
    }
    
    function freeTimeChange(checked){        
        $("#toggleTimeTr").css("display",(checked ? "none" : ""));
    };
</script>
<div style="padding-top:40px;">
<p class="txttitle"><bean:message bundle="sys-attachment" key="attachment.filestore.toggle" /></p>
<br/>
<center>
	<table class="tb_normal" width=90%>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-attachment" key="attachment.filestore.toggle.source" />
			</td><td width="35%">
			<xform:select property="attToggleSource" htmlElementProperties="id='attToggleSource'" 
			         showStatus="edit" subject="源位置" showPleaseSelect="false">
                <%
                    int index = 0;
                    String soruceChecked = "";
	                for (SysFileLocation location : SysFileLocationUtil.getLocations()) {
	                	soruceChecked = index == 0 ? location.getKey() : soruceChecked;
	            %> 
                       <xform:simpleDataSource value="<%=location.getKey()%>"><%=location.getTitle()%></xform:simpleDataSource>
	            <%
	                index++;
	                }
	                request.setAttribute("soruceChecked", soruceChecked);
	            %>
            </xform:select>
			</td>
			<td class="td_normal_title" width=15%>
                <bean:message bundle="sys-attachment" key="attachment.filestore.toggle.targer" />
            </td><td width="35%">
            <xform:select property="attToggleTarget"  htmlElementProperties="id='attToggleTarget'"
                    showStatus="edit" subject="目标位置" showPleaseSelect="false">
	            <%
	                String key = SysFileLocationUtil.getKey(null);
	                String targetChecked = "";
	                for (SysFileLocation location : SysFileLocationUtil.getLocations()) {
	                	targetChecked = key.equals(location.getKey()) ? location.getKey() : targetChecked;
	            %> 
	                   <xform:simpleDataSource value="<%=location.getKey()%>"><%=location.getTitle()%></xform:simpleDataSource>
	            <%
	                }
	                request.setAttribute("targetChecked", targetChecked);
	            %>
            </xform:select>
            </td>
		</tr>
		<tr>
            <td class="td_normal_title" width=15%>
                <bean:message bundle="sys-attachment" key="attachment.filestore.toggle.beginDate" />
            </td><td width="35%">
                <xform:datetime property="beginDate" dateTimeType="date" htmlElementProperties="id='beginDate'"
                    showStatus="edit"/>
            </td>
            <td class="td_normal_title" width=15%>
                <bean:message bundle="sys-attachment" key="attachment.filestore.toggle.endDate" />
            </td><td width="35%">
                <xform:datetime property="endDate" htmlElementProperties="id='endDate'"
                                subject="uuuuu"
                                dateTimeType="date"  
                                validators="compareBegin " showStatus="edit"/>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width=15%>
                <bean:message bundle="sys-attachment" key="attachment.filestore.toggle.freeTime" />
            </td><td width="35%" colspan="3">
			    <label style="display: inline-block;height:20px;line-height:20px;">
			        <input style="display: inline-block;vertical-align: middle;" type="checkbox" id="freeTimeChange" 
			             onclick="freeTimeChange(this.checked);" checked>
			        <span><bean:message bundle="sys-attachment" key="attachment.filestore.toggle.freeTimeDesc" /></span>
			    </label>
            </td>
        </tr>
        <tr id="toggleTimeTr" style="display:none;">
            <td class="td_normal_title" width=15%>
                <bean:message bundle="sys-attachment" key="attachment.filestore.toggle.beginTime" />
            </td><td width="35%">
	            <xform:select property="toggleBeginTime"  htmlElementProperties="id='toggleBeginTime'"
	                    showStatus="edit" subject="" showPleaseSelect="false">
	                <%
	                    String hour = null;
	                    for (int i=0; i<24; i++) {
	                    	hour = i + ":00";
	                    	String value = String.valueOf(i);
	                %> 
	                       <xform:simpleDataSource value="<%=value%>"><%=hour%></xform:simpleDataSource>
	                <%
	                    }
	                %>
	            </xform:select>
            </td>
            <td class="td_normal_title" width=15%>
                <bean:message bundle="sys-attachment" key="attachment.filestore.toggle.endTime" />
            </td><td width="35%">
                <xform:select property="toggleEndTime"  htmlElementProperties="id='toggleEndTime'"
                        showStatus="edit" subject="" showPleaseSelect="false">
                    <%
                        String hour = null;
                        for (int i=0; i<24; i++) {
                            hour = i + ":00";
                            String value = String.valueOf(i);
                    %> 
                           <xform:simpleDataSource value="<%=value%>"><%=hour%></xform:simpleDataSource>
                    <%
                        }
                    %>
                </xform:select>
            </td>
        </tr>
                                
		<tr>
			<td colspan="4">
				<bean:message bundle="sys-attachment" key="attachment.filestore.toggle.desc" />
			</td>
		</tr>
	</table>
	<br/><span id="toggleMsg" style="color:red;"></span><br/><br/>
	<span>
	    <table width="90%">
        <tr>
            <td  align="right">
                <ui:button text="${lfn:message('sys-attachment:attachment.filestore.toggle.start')}" 
                    onclick="startToggleAtt();" />
            </td>
            <td width="90px"></td>
            <td align="left" >
                <ui:button text="${lfn:message('sys-attachment:attachment.filestore.toggle.end')}" 
                    onclick="endToggleAtt();" />
            </td>
        </tr>
        </table>    
	</span>
</center>	
</div>	
<script type="text/javascript">
    var soruceChecked = "<%=request.getAttribute("soruceChecked")%>";
    var targetChecked = "<%=request.getAttribute("targetChecked")%>";
    document.getElementById('attToggleSource').value=soruceChecked;
    document.getElementById('attToggleTarget').value=targetChecked;
    document.getElementById('toggleBeginTime').value="1";
    document.getElementById('toggleEndTime').value="7";
    
</script>
</template:replace>
</template:include>