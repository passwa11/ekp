<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Map"%>

<list:cri-ref key="docDescription" ref="criterion.sys.docSubject" title="${lfn:message('kms-common:kmsCommonDocErrorCorrection.docDescription') }">
</list:cri-ref>


<list:cri-auto
		modelName="com.landray.kmss.kms.common.model.KmsCommonDocErrorCorrection"
		property="docCreator" />


<list:cri-criterion
	title="${lfn:message('kms-common:kmsCommonDocErrorCorrection.docStatus') }"
	key="docStatus" expand="false">
	<list:box-select>
		<list:item-select cfg-if="!param.docStatus">
			<ui:source type="Static">
				[{text:'${ lfn:message('status.discard') }', value:'00'},
				{text:'${ lfn:message('status.refuse') }',value:'11'},
				{text:'${ lfn:message('status.examine') }',value:'20'},
				{text:'${ lfn:message('status.publish') }',value:'30'}
				]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>

 <list:cri-auto
		modelName="com.landray.kmss.kms.common.model.KmsCommonDocErrorCorrection"
		property="docCreateTime" />

