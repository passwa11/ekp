<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" sidebar="no">
	<template:replace name="title">
		矩阵组织使用说明
	</template:replace>
	<template:replace name="head">
		<template:super/>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/table.css">
		<style type="text/css">
			
		</style>
	</template:replace>
	<template:replace name="body">
		<div class="main_data">
			<p class="txttitle" style="font-size: 20px;">矩阵组织使用说明</p>
			
			<p class="main_data_title">
				一、矩阵组织定义
			</p>
			<p class="main_data_body">
				按照职能划分的纵向领导系统和按项目任务或产品划分的横向领导系统相结合的组织形式，这种纵横交叉的领导系统构成了矩阵结构，称为矩阵组织，又称任务组织或项目组织。
			</p>
			
			<p class="main_data_title">
				二、矩阵组织特点
			</p>
			<p class="main_data_body">
				优势：应用面广，易用性高，适用于任何业务场景，可灵活构建审批环境。
			</p>
			<p class="main_data_body">
				不足：梳理的矩阵数据量较大，如可用通用岗位或角色线可解决场景避免使用矩阵组织。
			</p>
			
			<p class="main_data_title">
				三、矩阵组织应用场景
			</p>
			<p class="main_data_body">
				矩阵组织适用于任何业务场景，通过岗位或角色线无法解决的场景可考虑使用矩阵组织。例：总部售前小王，出差到南京参与项目一，接着出差到上海参与项目二，出差审批需要项目所在地领导审批，出差地点不固定，使用通过岗位或角色线无法计算出审批领导，需要使用矩阵组织，矩阵可以出差地作为条件，各出差地审批领导作为结果。
			</p>
			
			<p class="main_data_title">
				四、矩阵组织应用说明
			</p>
			<p class="main_data_body">
				一个矩阵组织由条件部分和结果部分组织一个二维表，通过条件匹配，查找结果部分。矩阵组织比较机动、灵活，条件可以是组织架构、常量、系统主数据，结果可以是人员和岗位。数据维护可以通过界面录入，也可以通过Excel批量导入，维护非常方便。
			</p>
			<p class="main_data_body">
				矩阵结构为一个二维表，比如项目矩阵组织，列为项目矩阵的属性，行为项目矩阵的数据，如下图示例：
			</p>
			<div class="lui_matrix_div_wrap">
				<table class="lui_matrix_tb_normal" style="width: 80%;text-align: center;margin: 20px auto;">
					<tr class="main_data_table_title">
						<td>序号</td>
						<td class="lui_matrix_td_normal_title lui_maxtrix_condition_th">项目名称</td>
						<td class="lui_matrix_td_normal_title lui_maxtrix_condition_th">项目人员</td>
						<td class="lui_matrix_td_normal_title lui_maxtrix_result_th" style="background: #ECF3FF;">项目经理</td>
						<td class="lui_matrix_td_normal_title lui_maxtrix_result_th" style="background: #ECF3FF;">项目总监</td>
					</tr>
					<tr>
						<td>1</td>
						<td class="lui_maxtrix_condition_td">项目一</td>
						<td class="lui_maxtrix_condition_td">A</td>
						<td class="lui_maxtrix_result_td">E</td>
						<td class="lui_maxtrix_result_td">F</td>
					</tr>
					<tr>
						<td>2</td>
						<td class="lui_maxtrix_condition_td">项目一</td>
						<td class="lui_maxtrix_condition_td">B</td>
						<td class="lui_maxtrix_result_td">G</td>
						<td class="lui_maxtrix_result_td">H</td>
					</tr>
					<tr>
						<td>3</td>
						<td class="lui_maxtrix_condition_td">项目二</td>
						<td class="lui_maxtrix_condition_td">C</td>
						<td class="lui_maxtrix_result_td">F</td>
						<td class="lui_maxtrix_result_td">H</td>
					</tr>
					<tr>
						<td>4</td>
						<td class="lui_maxtrix_condition_td">项目二</td>
						<td class="lui_maxtrix_condition_td">D</td>
						<td class="lui_maxtrix_result_td">F</td>
						<td class="lui_maxtrix_result_td">H</td>
					</tr>
				</table>
			</div>
			<p class="main_data_body">
				通常项目审批需要项目经理和项目总监进行审批，故项目名称和项目人员为条件，当项目为项目一，项目人员为A的时候，则会找到项目经理为E，项目总监为F，故结果为E，F。
			</p>
			<p class="main_data_body">
				在一个矩阵组织中有多个条件，在审批使用时条件可以是项目名称，也可以是项目人员，也可以是项目名称&项目人员一起匹配，查找结果可以返回第一条，也可以返回多条集合，或者当返回多条时抛出错误。
			</p>
			<p class="main_data_body">
				如仅用项目名称做为条件，当条件为项目一时，通过矩阵表可以得到序号1和序号2两条结果，当返回第一条，则项目经理为E，项目总监为F，当返回多条集合，则项目经理为E和G，项目总监为F和H，当返回多条抛出错误，此处则为错误，需要调整矩阵组织表的数据。
			</p>
			
			<p class="main_data_title">
				五、矩阵组织使用操作截图
			</p>
			<p class="main_data_body">
				以下是系统内的截图，灰色底色区域为条件字段，蓝色底色区域为结果字段
			</p>
			<img src="<c:url value="/sys/organization/resource/image/maxtrix_help_edit.png"/>"/>
			<p class="main_data_body">
				矩阵组织数据维护界面：
			</p>
			<img src="<c:url value="/sys/organization/resource/image/maxtrix_help_data.png"/>"/>
			<p class="main_data_body">
				流程审批节点配置：
			</p>
			<img src="<c:url value="/sys/organization/resource/image/maxtrix_help_node.png"/>"/>
			
		</div>
	</template:replace>
</template:include>
