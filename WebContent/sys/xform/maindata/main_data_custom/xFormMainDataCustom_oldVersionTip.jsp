<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="custom_tip_content">
		<div class="custom_tip_description" style="margin-top:20px;">
			<label>
				<span style="color:red;">警告：</span>系统检测到该模板的数据结构还是旧的结构，由于以前的数据结构，导致自定义的数据无法像内数据一样使用搜索和分页等功能，故新版系统在数据结构方面做了优化，建议管理员请按以下步骤执行数据迁移：
			</label>
		</div>
		<div class="custom_tip_step" style="margin-top:10px;color:blue;">
			<label>
				1、到【管理员工具箱】--【兼容性检测】--【表单主数据的自定义数据数据迁移 】执行数据迁移<br/>
				2、到所有使用了旧结构自定义数据的表单模板里面重新配置选择自定义数据
			</label>
		</div>
		<div class="custom_tip_ps" style="margin-top:10px;">
			<label>
				ps:不做数据迁移并不会影响原有数据，但是在使用的时候不能把旧结构的模板和新结构的模板混合用，例如：在表单模板里面，配置动态下拉框用了旧结构的【省】，而联动的时候用了新结构的【市】
			</label>
		</div>
	</div>
</body>
</html>