<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
{$
	<ul class="lui_category_frame clearfloat" >
		<a href="javascript:;" class="lui_category_aleft" title="${lfn:message('sys-ui:ui.category.left')}" data-lui-mark="scroll.left"></a>
		<a href="javascript:;" class="lui_category_aright" title="${lfn:message('sys-ui:ui.category.right')}" data-lui-mark="scroll.right"></a>
		<div data-lui-mark="select.area.content" class="lui_category_area">
		</div>
	</ul>
$}