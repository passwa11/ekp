<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
		<list:criteria multi="false" expand="false" channel="pstudy">
			<list:cri-criterion
				title="${ lfn:message('kms-lservice:kmsLservice.student.task.type') }"
				key="type" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[
							<kmss:ifModuleExist path="/kms/learn">
							{text:'${ lfn:message('kms-lservice:kmsLservice.student.learn.task') }',value:'learn'},
							</kmss:ifModuleExist>
							
							<kmss:ifModuleExist path="/kms/lmap">
							{text:'${ lfn:message('kms-lservice:kmsLservice.student.learn.map') }', value:'lmap'},
							</kmss:ifModuleExist>
							
							<kmss:ifModuleExist path="/kms/exam">
							{text:'${ lfn:message('kms-lservice:kmsLservice.student.myexam') }', value:'exam'}
							</kmss:ifModuleExist>
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>

		</list:criteria>
		<list:listview id="listview" channel="pstudy">
		    <ui:source type="AjaxJson" > 
				{url:"/kms/lservice/Lservice.do?method=PStudyCenterInfo"}
			</ui:source>

			<%-- 图文视图--%>
			<list:gridTable name="gridtable" columnNum="1" gridHref="!{href}">
				<list:row-template ref="sys.ui.lservice.listview.new">
				</list:row-template>
			</list:gridTable>

		</list:listview>
		<list:paging channel="pstudy"></list:paging>