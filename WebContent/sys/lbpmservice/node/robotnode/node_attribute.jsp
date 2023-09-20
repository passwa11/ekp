<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
FlowChartObject.Lang.Include('sys-lbpmservice');
FlowChartObject.Lang.Include('sys-lbpmservice-node-robotnode');
</script>
<script>
	document.getElementsByTagName("body")[0].style.paddingBottom = "0px";
</script>

<table width="650px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
		<table width="100%" class="tb_normal">
			<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
			<tr>
				<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.robotType" bundle="sys-lbpmservice-node-robotnode" /></td>
				<td>
					<select id="class" onchange="changeClass(this);">
						<option><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>
					</select>
					<select id="category" onchange="changeCategory(this);">
						<option><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>
					</select>
					<select id="type" onchange="changeType(this);">
						<option><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.robot_Description" bundle="sys-lbpmservice-node-robotnode" /></td>
				<td id="robot_Description" style="word-wrap:break-word;word-break:break-all;"></td>
			</tr>
			<tr>
				<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.robot_nodeErrorSkip" bundle="sys-lbpmservice-node-robotnode" /></td>
				<td><label>
							<input name="wf_nodeErrorSkip" type="checkbox" value="true">
							<span class="com_help"><kmss:message bundle="sys-lbpmservice-node-robotnode" key="FlowChartObject.Lang.Node.robot_nodeErrorSkip_help" /></span>
						</label>
				</td>
			</tr>
			<tr>
				<td colspan="2" >
					<iframe id="IF_Parameter" src="" frameborder="0" scrolling="yes" width="100%" height="265px"></iframe>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Event" bundle="sys-lbpm-engine" />">
		<td>
		<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr>
</table>

<script>
// 格式化特殊字符
function formatJson(value) {
	return value.replace(/"/ig,'\\"').replace(/\r\n/ig,'\\r\\n');
}
// 顶级分类选项
RobotClasses = new Array();
// 类型选项
RobotCategories = new Array();
// 顶级分类对象
function RobotClass(name) {
	this.name = name;
	if(name=="lbpm"){
		this.className = '<kmss:message key="lbpmRobotNode.class.lbpm" bundle="sys-lbpmservice-node-robotnode" />';
	} else if (name=="app") {
		this.className = '<kmss:message key="lbpmRobotNode.class.app" bundle="sys-lbpmservice-node-robotnode" />';
	} else if (name=="integrate") {
		this.className = '<kmss:message key="lbpmRobotNode.class.integrate" bundle="sys-lbpmservice-node-robotnode" />';
	} else {
		this.className = '<kmss:message key="lbpmRobotNode.class.others" bundle="sys-lbpmservice-node-robotnode" />';
	}
	this.subClassCategories = new Array();
}
// 分类对象
function RobotCategory(name) {
	this.name = name || '';
	this.subCategories = new Array(); 
};
// 子分类对象
function RobotSubCategory(name, url, unid, description, className) {
	this.name = name || '';
	this.url = url || '';
	this.unid = unid || '';
	this.description = description || '';
	this.className = className || '';
};
// 获得分类对象，根据name
function getCategoryByName(name) {
	if (name) {
		for (var i = 0, length = RobotCategories.length; i < length; i++)
			if (RobotCategories[i].name == name)
				return RobotCategories[i];
	}
	return null;
};

function getClassByName(name) {
	if (name) {
		for (var i = 0, length = RobotClasses.length; i < length; i++)
			if (RobotClasses[i].name == name)
				return RobotClasses[i];
	}
	return null;
};

AttributeObject.Init.AllModeFuns.push(function() {
	initType();
	//window.dialogHeight = "450px";
	//window.dialogWidth = "800px";
});

var NodeData = AttributeObject.NodeData;
var NodeContent = NodeData.content;

function initType() {
	var nodesValue = new KMSSData().AddBeanData("robotNodeConfigService&modelName=" + FlowChartObject.ModelName).GetHashMapArray();
	var objCategory = document.getElementById("category");
	var objClass = document.getElementById("class");
	// 由于更改为扩展模式，为了向下兼容...
	var _nUnid = NodeData.unid || null, delimiters = "@Robot@";
	if (_nUnid != null && _nUnid.lastIndexOf(delimiters) == -1) {
		_nUnid = "*" + delimiters + _nUnid;
	}
	// 输出主分类和子分类的选项
	var selectClass = null, selectCategory = null, categorySelectIndex = -1, typeSelectIndex = -1;
	for (var i = 0; i < nodesValue.length; i++) {
		var _node = nodesValue[i];
		var _category = _node["category"];
		if (_category) {
			var type = _node["type"] || "";
			var url = _node["url"] || "";
			var unid = _node["unid"] || "";
			var description = _node["description"] || type;
			var className = _node["class"] || "others";
			// 获得分类对象
			var oCategory = getCategoryByName(_category);
			if (!oCategory) {
				oCategory = new RobotCategory(_category);
				RobotCategories.push(oCategory);
				// 添加选项
				//objCategory.options[objCategory.options.length] = new Option(_category);
				// 获得顶级分类对象
				var oClass = getClassByName(className);
				if(!oClass){
					oClass = new RobotClass(className);
					RobotClasses.push(oClass);
					objClass.options[objClass.options.length] = new Option(oClass.className);
				}
				oClass.subClassCategories.push(oCategory);
			}
			// 记录子分类对象
			oCategory.subCategories.push(new RobotSubCategory(type, Com_Parameter.ContextPath + cleanPath(url), unid, description, className));
			//若没有初始化配置，则_nUnid为null，此时默认选中流程引擎类的第一个选项。
			if (_nUnid == null && className == "lbpm") _nUnid = unid;
			// 记录当前选中
			if (unid == _nUnid) {
				selectClass = oClass;
				classSelectIndex = oClass.subClassCategories.length;
				selectCategory = oCategory;
				typeSelectIndex = oCategory.subCategories.length;
			}
		}
	}
	if (selectClass == null) return;
	// 初始化选中状态
	for (var i = 0, length = RobotClasses.length; i < length; i++) {
		if (RobotClasses[i] == selectClass) {
			objClass.selectedIndex = (i + 1);
			var currSubClassCategory = selectClass.subClassCategories;
			for (var j = 0; j < currSubClassCategory.length; j++) {
				// 添加分类选项
				objCategory.options[objCategory.options.length] = new Option(currSubClassCategory[j].name);
				if(currSubClassCategory[j] == selectCategory){
					categorySelectIndex = j;
				}
			}
			objCategory.selectedIndex = categorySelectIndex + 1;
			break;
		}
	}
	// 当前分类对象的子分类集
	var objType = document.getElementById("type");
	var currSubRobotCategory = selectCategory.subCategories;
	for (var i = 0, length = currSubRobotCategory.length; i < length; i++) {
		objType.options[objType.options.length] = new Option(currSubRobotCategory[i].name, currSubRobotCategory[i].unid);
	}
	objType.selectedIndex = typeSelectIndex;
	// 调用相应机器人
	var iframe = document.getElementById("IF_Parameter");
	Com_AddEventListener(iframe, "load", iframeOnload);
	// 调用相应机器人的配置页面
	iframe.src = currSubRobotCategory[typeSelectIndex - 1].url;
	// 显示相应机器人的功能说明
	document.getElementById("robot_Description").innerHTML = currSubRobotCategory[typeSelectIndex - 1].description;
};

function cleanPath(url) {
	return (url.indexOf("/") == 0) ? url.substr(1) : url;
}

function iframeOnload() {
	if (!AttributeObject.isNodeCanBeEdit) {
		if (window.frames["IF_Parameter"].document) {
			AttributeObject.Utils.disabledOperation(window.frames["IF_Parameter"].document);
		} else {
			AttributeObject.Utils.disabledOperation(window.frames["IF_Parameter"].contentWindow.document);
		}
	}
};

function changeClass(owner) {
	var objCategory = document.getElementById("category");
	objCategory.options.length = 0;
	objCategory.options[objCategory.options.length] = new Option(FlowChartObject.Lang.pleaseSelect);
	var objType = document.getElementById("type");
	objType.options.length = 0;
	objType.options[objType.options.length] = new Option(FlowChartObject.Lang.pleaseSelect);
	// 请选择时...
	var index = owner.selectedIndex;
	if (owner.options[index].text == FlowChartObject.Lang.pleaseSelect) return;
	// 当前分类对象的子分类集
	var currSubRobotCategory = RobotClasses[index - 1].subClassCategories;
	for (var i = 0, length = currSubRobotCategory.length; i < length; i++) {
		objCategory.options[objCategory.options.length] = new Option(currSubRobotCategory[i].name);
	}
};

function changeCategory(owner) {
	var objType = document.getElementById("type");
	objType.options.length = 0;
	objType.options[objType.options.length] = new Option(FlowChartObject.Lang.pleaseSelect);
	// 请选择时...
	var index = owner.selectedIndex;
	if (owner.options[index].text == FlowChartObject.Lang.pleaseSelect) return;
	var objClass = document.getElementById("class");
	var classIndex = objClass.selectedIndex;
	// 当前分类对象的子分类集
	var currSubRobotCategory = RobotClasses[classIndex-1].subClassCategories[index - 1].subCategories;
	for (var i = 0, length = currSubRobotCategory.length; i < length; i++) {
		objType.options[objType.options.length] = new Option(currSubRobotCategory[i].name, currSubRobotCategory[i].unid);
	}
};

function changeType(owner) {
	// 请选择时...
	var index = owner.selectedIndex;
	if (owner.options[index].text == FlowChartObject.Lang.pleaseSelect) return;
	// 当前子分类
	var objCategory = document.getElementById("category");
	var objClass = document.getElementById("class");
	var classIndex = objClass.selectedIndex;
	var categoryIndex = objCategory.selectedIndex;
	var currSubRobotCategory = RobotClasses[classIndex-1].subClassCategories[categoryIndex - 1].subCategories;
	document.getElementById("IF_Parameter").src = currSubRobotCategory[index - 1].url;
	document.getElementById("robot_Description").innerHTML = currSubRobotCategory[index - 1].description;
};

AttributeObject.CheckDataFuns.push(function(data) {
	var robotType = document.getElementById("type");
	var robotTypeOption = robotType.options[robotType.selectedIndex];
	if (robotTypeOption.text == FlowChartObject.Lang.pleaseSelect) {
		alert(FlowChartObject.Lang.Node.robot_CheckType);
		return false;
	}

	var contentDoc = document.getElementById("IF_Parameter");
	var json = contentDoc.contentWindow.returnValue();
	if (json == null) return false;

	data.unid = robotTypeOption.value;
	data.content = json;
	
	return true;
});
</script>