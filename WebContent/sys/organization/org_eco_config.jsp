<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<script type="text/javascript">
	function config_org_eco_chgEnabled() {
		var tbObj = document.getElementById("org_eco_tab");
		var field = document.getElementsByName("_value(kmss.org.eco.enabled)")[0];
		for (var i = 0; i < tbObj.rows.length; i++) {
			tbObj.rows[i].style.display = field.checked ? "" : "none";
			var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
			for (var j = 0; j < cfgFields.length; j++) {
				cfgFields[j].disabled = !field.checked;
			}
		}
		$(document).trigger("kmss.org.eco.enabled", field.checked);
	}

	config_addOnloadFuncList(function() {
		config_org_eco_chgEnabled();
	});
</script>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan="3">
			<b>
				<label>
					<xform:checkbox property="value(kmss.org.eco.enabled)" onValueChange="config_org_eco_chgEnabled()" showStatus="edit">
						<xform:simpleDataSource value="true">启用生态组织</xform:simpleDataSource>
					</xform:checkbox>
				</label>
			</b>
		</td>
	</tr>
	<tbody id="org_eco_tab"> 
		<tr>
			<td class="td_normal_title" width="15%">生态组织说明：</td>
			<td colspan="2">
				启用生态组织之后，组织架构自动分成“内部组织”和“生态组织”。<br>
				1、原有的组织架构属于“内部组织”，“生态组织”需要重新创建组织类型与外部组织，通过钉钉邀请的方式加入外部人员。<br>
				2、开启生态组织后，建议同时开启钉钉集成中的生态组织同步，保持生态组织与钉钉端的实时同步更新。<br>
				3、内外组织权限隔离，默认情况下，内部组织无法查看外部组织信息；外部组织仅允许查看本组织及子组织信息，无法跨组织查看。<br>
				4、系统启动成功后，需要做一下数据迁移：后台管理 -> 管理员工具箱 -> 兼容性检测 -> “【组织机构】外部组织数据迁移”。<br>
				5、如有定制模块，需要根据《生态组织部署文档》按相关要求重新定制权限部分代码。<br>
			</td>
		</tr>
	</tbody>
</table>
