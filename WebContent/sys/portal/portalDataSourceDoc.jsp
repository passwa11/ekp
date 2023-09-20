<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("optbar.js|list.js");
</script>
<script>
 function expandMethod_tr(domObj) {
	 var thisObj = $(domObj);
		var isExpand = thisObj.attr("isExpanded");
		if(isExpand == null)
			isExpand = "0";
		var trObj=thisObj.parents("tr");
		trObj = trObj.next("tr");
		var imgObj = thisObj.find("img");
		if(trObj.length > 0){
			if(isExpand=="0"){
				trObj.show();
				thisObj.attr("isExpanded","1");
				imgObj.attr("src","${KMSS_Parameter_StylePath}icons/collapse.gif");
			}else{
				trObj.hide();
				thisObj.attr("isExpanded","0");
				imgObj.attr("src","${KMSS_Parameter_StylePath}icons/expand.gif");
			}
		}
 }
 
 function expandMethod(imgSrc,divSrc) {
		var imgSrcObj = document.getElementById(imgSrc);
		var divSrcObj = document.getElementById(divSrc);
		if(divSrcObj.style.display!=null && divSrcObj.style.display!="") {
			divSrcObj.style.display = "";
			imgSrcObj.src = "${KMSS_Parameter_StylePath}icons/collapse.gif";
		}else{
			divSrcObj.style.display = "none";
			imgSrcObj.src = "${KMSS_Parameter_StylePath}icons/expand.gif";		
		}
	 }
 List_TBInfo = new Array(
			{TBID:"List_ViewTable1_1"},
			{TBID:"List_ViewTable2_1"},{TBID:"List_ViewTable2_2"}
		);
</script>


<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}服务接口说明</p>

<center>
<br/>
<table border="0" width="95%">
	<tr>
		<td><b>1.接口说明</td>
	</tr>
	<!-- 接口01 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;获取资源信息列表
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">findModule()</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">获取资源信息列表</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">资源信息list集合，List< SysPortalDataModuleVO></td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回参数</td>
					<td width="85%"><img id="viewSrc1_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc1_1','paramDiv1_1')" style="cursor: pointer"><br>
					<div id="paramDiv1_1" style="display:none">
					<table id="List_ViewTable1_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
								<td width="20%">缺省值</td>
							    <td width="10%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdCode</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>模块编码</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>fdName</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>模块名称（messageKey）</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>dynamicProps</td>
							<td>键值对Map< String, String></td>
							<td>无</td>
							<td>dynamicProps</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>fdMd5</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>模块信息md5值</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>fdSysCode</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>系统编码</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>fdSource</td>
							<td>整型（int）</td>
							<td>10</td>
							<td>来源，10：内部系统 20：第三方</td>
						</tr>
					</table></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口02 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.2&nbsp;&nbsp;获取模块下的portlet列表信息
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">findDataSource(SysPortalDataModuleVO vo)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">获取模块下的portlet列表信息</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">portlet列表集合List< SysPortalDataSourceVO></td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数vo</td>
					<td width="85%"><img id="viewSrc2_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc2_1','paramDiv2_1')" style="cursor: pointer"><br>
					<div id="paramDiv2_1" style="display:none">
					<table id="List_ViewTable2_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdCode</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>模块编码，不允许为空</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>fdName</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>模块名称（messageKey）</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>dynamicProps</td>
							<td>键值对（Map< String,String>）</td>
							<td>无</td>
							<td>dynamicProps</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>fdMd5</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>模块信息 md5值</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>fdSysCode</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>系统编码</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>fdSource</td>
							<td>整型（int）</td>
							<td>10</td>
							<td>来源，10：内部系统 20：第三方</td>
						</tr>
					</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回参数</td>
					<td width="85%"><img id="viewSrc2_2"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc2_2','paramDiv2_2')" style="cursor: pointer"><br>
					<div id="paramDiv2_2" style="display:none">
					<table id="List_ViewTable2_2" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="30%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="20%">缺省值</td>
							    <td width="30%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdCode</td>
							<td>字符串(String)</td>
							<td>无</td>
							<td>编码</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>fdName</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>数据源名称</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>dynamicProps</td>
							<td>键值对Map(Map< String,String>)</td>
							<td>无</td>
							<td>dynamicProps</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>fdSysCode</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>系统标识</td>
						</tr>
						<tr>
			              <td align="center">5</td>
			              <td>fdModuleCode</td>
			              <td>字符串（String）</td>
			              <td>无</td>
			              <td>模块标识</td>
			            </tr>
			            <tr>
			              <td align="center">6</td>
			              <td>fdFormatCode</td>
			              <td>字符串（String）</td>
			              <td>无</td>
			              <td>数据格式</td>
			            </tr>
						<tr>
							<td align="center">7</td>
							<td>fdDesc</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>描述</td>
						</tr>

						<tr>
							<td align="center">8</td>
							<td>fdAnonymous</td>
							<td>布尔值（Boolean）</td>
							<td>false</td>
							<td>是否匿名</td>
						</tr>
						<tr>
							<td align="center">9</td>
							<td>fdContent</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>数据源内容(json)</td>
						</tr>
						<tr>
							<td align="center">10</td>
							<td>fdSource</td>
							<td>整型（int）</td>
							<td>10</td>
							<td>系统类型 10：内部系统 20：第三方</td>
						</tr>
						<tr>
							<td align="center">11</td>
							<td>fdRenders</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>默认呈现集合,分隔</td>
						</tr>
						<tr>
							<td align="center">12</td>
							<td>fdThumbnail</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>数据源缩略图(使用iframe框架的EKP等公司系统的组件，在组件选择时，需展示原业务数据的缩略图； 组件拖至页面配置窗口时也应显示原业务数据的缩略图)</td>
						</tr>
						<tr>
							<td align="center">13</td>
							<td>resourceMap</td>
							<td>键值对Map（Map< String,Map< String,String>>）</td>
							<td>无</td>
							<td>数据源多语言 信息 key 为语言 value 为对应的 标识与值</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>