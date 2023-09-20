<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysNotifyShortMessageSend" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 收件人 -->
		<list:data-column property="fdReceiver" title="${ lfn:message('sys-sms:sysSmsMain.fdReceiver') }">
		</list:data-column>
		<!-- 接收号码 -->
		<list:data-column property="fdReceiverNumber" title="${ lfn:message('sys-sms:sysSmsMain.fdReceiverNumber') }">
		</list:data-column>
		<!-- 发送人 -->
		<list:data-column property="fdSender" title="${ lfn:message('sys-sms:sysSmsMain.fdCreatorId') }">
		</list:data-column>
		<!-- 发送时间 -->
		<list:data-column col="fdCreateTime" title="${ lfn:message('sys-sms:sysSmsMain.docCreateTime') }">
			<kmss:showDate value="${sysNotifyShortMessageSend.fdCreateTime}" type="date"/>
		</list:data-column>
		<!-- 发送是否成功 -->
		<list:data-column col="fdFlag" title="${ lfn:message('sys-sms:sysSmsMain.docStatus') }">
			<sunbor:enumsShow value="${sysNotifyShortMessageSend.fdFlag}" enumsType="sysSmsMainDocStatus" />
		</list:data-column>
		<list:data-column property="fdModuleSource" title="${ lfn:message('sys-sms:sysSmsMain.fdModuleSource') }">
		</list:data-column>
		<!-- 使用场景 -->
		<list:data-column property="fdScene" title="${ lfn:message('sys-sms:sysSmsMain.fdScene') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysNotifyShortMessageSend.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>