<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle">附件目录首次配置注意事项</p>
<div style="text-align: left; padding:0px 10px;">
<h3>首次配置附件目录场景</h3>
<ul>
	<li>1、系统规划了多目录存储，需要系统支持多目录的切换方式来存储附件</li>
	<li>2、系统未规划多目录存储，运行一段时间后，当默认的存放路径的磁盘已满时，需要考虑附件的多目录存储支持</li>
</ul>
<h3>解决方案</h3>
	<p>针对场景1，进入“后台配置-附件机制-附件目录配置”直接进行目录配置即可。</p><br/>
	<p>针对场景2，需要做以下操作：</p><br/>
	<ul>
		<li><b>步骤1</b>、在“后台配置-附件机制-附件目录”配置中新建目录配置，存储路径目录和原有的admin.do的附件目录保持一致，设置为非默认，如下图：<br/>
			<img src="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_catalog/img/1.png" width="800"/>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;&lt;图 1&gt;</div>
			<img src="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_catalog/img/2.png" width="800"/>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;&lt;图 2&gt;</div>
		</li>
		<li><br/><b>步骤2</b>、记录下上一步骤中目录文档的id，在数据库执行update sys_att_file set fd_cata_id='admin.do附件目录对应的目录文档Id' where fd_cata_id is null;，如下图：<br/>
			<img src="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_catalog/img/3.png" width="800"/>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;&lt;图 3&gt;</div>
			<img src="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_catalog/img/4.png" width="800"/>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;&lt;图 4&gt;</div>
		</li>
		<li><br/><b>步骤3</b>、新建其他目录，设定其为默认目录，后续的附件会往默认目录中存储<br/>
		</li>
	</ul>
</div>
<%@ include file="/resource/jsp/view_down.jsp"%>