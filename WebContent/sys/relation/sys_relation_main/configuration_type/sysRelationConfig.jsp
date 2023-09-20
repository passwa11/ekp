<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<center>
<table id="relationEntry" width=95% style="border: 0px;">
	<!-- 现在配置类的关联机制只有“关联文档”一个，所以先隐藏类型选择，以后有需要再开放
	<tr>
		<td width="16%" style="border: 0px;" nowrap="nowrap">
			<bean:message bundle="sys-relation" key="sysRelationEntry.select.type" />
		</td>
		<td style="border: 0px;">
			<nobr>
			<input type="hidden" name="fdOtherUrl" />
			<label>
			<input type="radio" name="fdType" value="5" onclick="changeRelationType(this.value);" />
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType5" />
			</label>
			</nobr>
		</td>
	</tr>
	 -->
	<tr>
		<td valign="top" colspan="2">
			<iframe id="sysRelationEntry" 
				frameborder="0" scrolling="no" width="100%"></iframe>
		</td>
	</tr>
</table>
</center>
<%@ include file="sysRelationConfig_script.jsp"%>
<%@ include file="/resource/jsp/view_down.jsp"%>