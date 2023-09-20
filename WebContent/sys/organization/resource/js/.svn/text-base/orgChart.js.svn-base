var data_array;
var map;
var maxLevel = 1;
var initSize = 1;
var mapsW = 0;
var mapsH = 500;

/**
* ajax异步根据组织机构ID获取子级组织机构数据
* @param fdId 组织机构ID
* @param sceneId  场景标识，判断当前是在什么动作或状态下触发的查询组织机构
* 可选场景           page_init: 页面初始化      
*                 change_level: 修改展开层级    
*                 show_child_node: 点击节点展开图标显示子节点   
* @param load_callback 回调函数
* @return
*/
function GetOrgChartAjax( fdId, sceneId, load_callback ){
	seajs.use(['lui/dialog'], function(dialog){
		window.del_load = dialog.loading();
	});
	var expandLevel = $("#select_Arrow").val(); // 展开层级(默认展开前两级)
	expandLevel = expandLevel?expandLevel:"2";
	var isFirstTimeLoad = sceneId=="page_init"?true:false; // 是否页面初始化时首次加载机构数据（true:是，false：否）
	var url = Com_Parameter.ContextPath + "sys/organization/sys_org_chart/sysOrgChart.do?method=GetAllList";
	$.ajax({
    	type: "post",
        url: url,
        dataType: "json",
		async : true,
        data: { fdId: fdId, isFirstTimeLoad:isFirstTimeLoad, expandLevel: expandLevel },
        success: function (data) {
            if (data != null && data.d != null && data.d != "") {
            	if(sceneId=="page_init"){ // 页面初始化首次加载组织机构数据
            	   page_init_callback( data );
            	}else if(sceneId=="change_level"){ // 修改展开层级
            	   change_level_callback( data );
            	}else if(sceneId=="show_child_node"){ // 点击节点展开图标显示子节点 
            	   expandNodeLoad_callback( data, fdId, load_callback );
            	}
            	setOrgMapStyle();
            }
            seajs.use(['lui/dialog'], function(dialog){
                if(window.del_load!=null){
					window.del_load.hide(); 
				}
			});
        },
        error: function (err) {
            return;
        }
    });
}

/**
* 加载子节点的组织机构数据（点击组织机构展开图标时触发）
* @param fdId 组织机构ID
* @param load_child_callback 加载子节点回调函数
* @return
*/
function loadChildNode( fdId, load_child_callback ){
	var sceneId = "show_child_node";     // 场景标识 （点击节点展开图标显示子节点 ）
	GetOrgChartAjax( fdId, sceneId, load_child_callback );
}

/**
* 页面初始化加载组织机构数据回调函数
* @param data 组织机构数据
* @return
*/
function page_init_callback(data){
	    maxLevel = data.maxLevel; // 获取最大层级
	    //往下拉框中补充可选项
	    for (var level = 1; level <= maxLevel; level++) {
	        if (level <= 4) {
	            $("#select_Arrow").append('<option value="' + level + '">' + level + '</option>');
	        }
	    }
	    $("#select_Arrow").append('<option value="' + maxLevel + '">' + window.allExpandText + '</option>');
	    //默认展开2级
	    $("#select_Arrow").val( maxLevel>=2? "2":"1" );
	
	    var html = "";
	    data_array = data.d;
	    //定义第一级组织ID
	    var firstOrganisationID = "";
	    for (var i = 0; i < data_array.length; i++) {
	        if (data_array[i].ParentOrganizationalID == "" || data_array[i].ParentOrganizationalID == undefined || data_array[i].ParentOrganizationalID == null) {
	            firstOrganisationID = data_array[i].OrganizationalID;
	            break;
	        } else {
		        var headNode= FindHeadNode(data_array);
		        if( headNode!=null && typeof( headNode)!=undefined){
		           firstOrganisationID = headNode.OrganizationalID;
		           break;
		        }
	        }
	    }
	   
	    html = setDiv(data_array, firstOrganisationID);

	    var showLevel = maxLevel>=2?2:1;
	    
	    //往organisation中加实际内容
	    $("#organisation").append(html);
	    CallFunc_orgChart({ container: $("#main"), depth: maxLevel, interactive: true, fade: true, speed: 'fast', showLevels: showLevel });
	    $("#organisation").empty();
	    
	    var w = document.body.clientWidth;
	    var h = top.document.body.clientHeight - 200; 
	    if (h < 400) { h = 400; }
	    map = new SpryMap({
	        id: "worldMap",
	        height: h,
	        width: w,
	        startX: 0,
	        startY: 0,
	        cssClass: "mappy"
	    });
	
	    $("#main").css("transition", "all 0.5s ease-in-out");
	    $("#main").css("transform", "scale(" + initSize + ")");
	    $("#main").css("transform-origin", "0 0");	    
}


/**
* 修改展开层级加载组织机构数据回调函数
* @param data 组织机构数据
* @return
*/
function change_level_callback(data){
    var html = "";
    data_array = data.d;
    //定义第一级组织ID
    var firstOrganisationID = "";
    for (var i = 0; i < data_array.length; i++) {
        if (data_array[i].ParentOrganizationalID == "" || data_array[i].ParentOrganizationalID == undefined || data_array[i].ParentOrganizationalID == null) {
            firstOrganisationID = data_array[i].OrganizationalID;
            break;
        } else {
	        var headNode= FindHeadNode(data_array);
	        if( headNode!=null && typeof( headNode)!=undefined){
	           firstOrganisationID = headNode.OrganizationalID;
	           break;
	        }
        }
    }
   
    html = setDiv(data_array, firstOrganisationID);

    var showLevel = parseInt($("#select_Arrow").val()); // 展开层级
    
    //往organisation中加实际内容
    $("#organisation").append(html);
    CallFunc_orgChart({ container: $("#main"), depth: maxLevel, interactive: true, fade: true, speed: 'fast', showLevels: showLevel });
    $("#organisation").empty();
}

/**
* 展开节点加载子级组织机构数据回调函数
* @param data 组织机构数据
* @param fdId 当前展开的组织机构ID
* @param load_callback 回调函数
* @return
*/
function expandNodeLoad_callback( data, fdId, load_callback ){
	var child_data_array = data.d;
	for(var i=0;i<child_data_array.length;i++){
		var childItem = child_data_array[i];
		var isExists = false;
		for(var k=0;k<data_array.length;k++){
			var item = data_array[k];
		    if(item.OrganizationalID == childItem.OrganizationalID){
		    	isExists = true;
		    	break;
		    }   
		}  
        if(isExists==false){
        	data_array.push(childItem);
        }
	}
	var html = setDiv(child_data_array, fdId);
	var $node = $(html);
	load_callback($node);

}

// 修复缺陷,组织架构图加载失败
function CallFunc_orgChart(options){
	 try{
	     $("#organisation").orgChart(options);
	 }catch(err){
		 window.console.debug('Load jQuery orgChart failed, Try reload !!');
		 $.getScript(Com_Parameter.ContextPath + "/sys/organization/resource/js/jquery.orgchart.js",function(){
			try{
				$("#organisation").orgChart(options);
			}catch(erro){
				window.console.debug('Load jQuery orgChart failed again !! ',erro);
			}
		});
	 }
}

/**
* 设置组织机构图的样式（宽度、高度）
* @return
*/
function setOrgMapStyle(){
	// 获取组织机构数据渲染出来的内容宽度
	var mapContentWidth = ($("#main").find("div.orgChart").width()+30)+"px";
	// 设置拖动区域的宽度
	document.getElementById("worldMap").style.width = mapContentWidth;
	document.getElementById("left").style.width = mapContentWidth;
	
	// 获取组织机构数据渲染出来的内容高度
	var mapContentHeight = ($("#main").find("div.orgChart").height()+30)+"px";
	// 设置拖动区域的高度
	document.getElementById("worldMap").style.height = mapContentHeight;
	document.getElementById("left").style.height = mapContentHeight;
}



//当worldMap的style属性的left和top的值为负数时，设置为0px
function setWMStyle(){
	var styleArr = $("#worldMap").attr("style").split(";");
	var wmStyle = "";
	for(var i = 0 ; i < styleArr.length;i++){
		var style = styleArr[i];
		if(style.indexOf("left") != -1 && style.indexOf("-") != -1){
				style = "left:0px";
		}
		if(style.indexOf("top") != -1 && style.indexOf("-") != -1){
			style = "top:0px";
		}
		wmStyle = wmStyle + style + ";";
	}
	$("#worldMap").attr("style",wmStyle);
}

//展示层级改变事件
function selectChange(obj) {
	var fdId = window.root_FdId;
	var sceneId = "change_level";     // 场景标识 （修改展开层级 ）
	GetOrgChartAjax( fdId, sceneId, null);
}

//获取当前数据中顶级节点信息
function FindHeadNode(dtArr){
    var headNode = true;
    var rtnArr;
    
    for (var i = 0; i < dtArr.length; i++) {
    	if (dtArr[i].depth == 1) {
            rtnArr = dtArr[i];
            break;
        }
    }
    
    return rtnArr;
}



/**
* 遍历绘制页面内容区域
* @param dt 组织机构数据
* @param NodeID 节点ID（组织ID）
* @return
*/
function setDiv(dt, NodeID) {

    //添加本级节点
    var html = "";
    html += "<li>";
    //添加本级节点的展示信息
    html += DrawDiv(dt, NodeID);

    //判断本级节点的下一级，如果有内容，也要添加上去
    var isHaveChild = false;
    for (var i = 0; i < dt.length; i++) {
        if (dt[i].ParentOrganizationalID == NodeID) {
            isHaveChild = true;
            break;
        }
    }

    if (isHaveChild == true) {
        html += "<ul>";
    }

    for (var i = 0; i < dt.length; i++) {
        if (dt[i].ParentOrganizationalID == NodeID) {
            html += setDiv(dt, dt[i].OrganizationalID, dt[i].OrganizationalName);
        }
    }

    if (isHaveChild == true) {
        html += "</ul>";
    }

    //本级节点结束
    html += "</li>";
    return html;
}

//绘制单一的DIV
function DrawDiv(dt, NodeID) {

    for (var i = 0; i < dt.length; i++) {
        if (dt[i].OrganizationalID == NodeID) {
            var h = "<div id='div_" + dt[i].OrganizationalID + "' class='divShowD " + dt[i].OrganizationalID + "' style='width: 150px;'>                                                                           "
                  + "     <table width='100%'>                                                                                                             "
                  + "         <tr>                                                                                                                         ";
            if(dt[i].OrganizationalType == 1){
            	h += "             <td class='headrow-bradius' style='height: 24px; background-color: #2596E4; width: 100%;'>                                                       ";
            }else{
            	h += "             <td class='headrow-bradius' style='height: 24px; background-color: #FBC02D; width: 100%;'>                                                       ";
            }
            h += "                 <table width='100%'>                                                                                                 "
                  + "                     <tr>                                                                                                             "
                  + "                         <td align='left' style='white-space: nowrap;width: 125px;height: 24px; vertical-align: middle;  padding-left: 2px; font-family: 微软雅黑; color: #fff;             "
                  + "                             font-size: 12px; text-align:left;'>                                                                                        ";
            
            var maxWidth = 70 - (dt[i].EmpCount.toString().length - 2) * 6;	//数字每10位width减/加6
            maxWidth = 80;
            h += '                                 <div title="' + dt[i].OrganizationalNameAll + '" style="font-size: 12px;font-weight:normal;max-width:' + maxWidth + 'px;float:left;cursor: pointer;overflow: hidden;text-overflow: ellipsis;word-break: keep-all; white-space: nowrap;" onclick="openDept(\''+dt[i].OrganizationalID+'\',\''+dt[i].OrganizationalType+'\');">' + dt[i].OrganizationalName + ' </div>';
            h += "         <div style='font-size: 12px;font-weight:normal'>(" + dt[i].EmpCount + dt[i].EmpCountUnit + ")</div>               ";
            h += "                         </td>                                                                                                        "
                  + "                         <td style='padding-right: 2px;width: 18px;  vertical-align: middle;'>                                                                                    "

            if (dt[i].ChildCount > 0) {
                h += "<img id='arrowImg_" + dt[i].OrganizationalID + "' src='../../../sys/organization/resource/image/arrowOpen.png' style=' cursor:pointer; vertical-align:middle;'/>   ";
            }
            h += "                         </td>                                                                                                        "
                  + "                     </tr>                                                                                                            "
                  + "                 </table>                                                                                                             "
                  + "             </td>                                                                                                                    "
                  + "         </tr>                                                                                                                        ";
            	  + "         <tr>                                                                                                                         ";
            h+=	 "             <td style='height: 60px; width: 100%; vertical-align: middle;'>                                                                  "
                  + "                 <table width='100%'>                                                                                                 "
                  + "                     <tr>                                                                                                             "
                  + "                         <td rowspan='2'>                                         "
                  + "                             <img src='../../.." + dt[i].empImgUrl + "' height='32px' width='32px' style='border-radius:50%;margin:2px 10px 0px 15px' />     "
                  + "                         </td>                                                                                                        "
                  + "                         <td align='left'rowspan='2' >                            "
            if(dt[i].DirectorEmpID == null || dt[i].DirectorEmpID == ""){
            	h += "                            <div style='text-align:left; width: 80px; height:20px; font-family:微软雅黑; font-size:12px; color:#999;overflow: hidden;text-overflow: ellipsis;word-break: keep-all; white-space: nowrap;' title='"+dt[i].DirectorName+"'> " + dt[i].DirectorName +"</div>";
            }else{
            	h += '                            <div style="text-align:left; width: 80px; height:20px; font-family:微软雅黑; font-size:14px; color:#333;overflow: hidden;text-overflow: ellipsis;word-break: keep-all; white-space: nowrap;cursor: pointer;" title="'+dt[i].DirectorName+'" onclick="openPerson(\''+dt[i].DirectorEmpID+'\');" > ' + dt[i].DirectorName +'</div>';
            	h += '							  <div style="text-align:left; width: 80px; height:21px; font-family:微软雅黑; font-size:12px; color:#999;overflow: hidden;text-overflow: ellipsis;word-break: keep-all; white-space: nowrap;cursor: pointer;" title="'+dt[i].PositionName +'" onclick="openPost(\''+dt[i].PositionId+'\');" >' + dt[i].PositionName + '</div>';
            }
                  + "                         </td>"
                  + "                     </tr>                                                                                                            "
                  + "                     <tr>                                                                                                             "
                  + "                         <td align='left' style='text-align:middle; padding-left: 0px; padding-top: 13px; border-left-color: Black; font-family:微软雅黑; font-size:12px; color:#7885a4;'>             "
            h += "                         </td>                                                                                                        "
                  + "                     </tr>                                                                                                            "
                  + "                 </table>                                                                                                             "
                  + "             </td>                                                                                                                    "
                  + "         </tr>                                                                                                                        ";


            /* 大于一个人员时的展示内容 */
            if(dt[i].DirectorNumber > 1){
            	if(dt[i].SiblingDirectors != null && dt[i].SiblingDirectors != 'undefind' && dt[i].SiblingDirectors != ""){
	            	for(var j=0; j<dt[i].SiblingDirectors.length; j++){
		            	h += "         <tr class='sibling-director'>                                                                                                                         "
		                  + "             <td style='height: 60px; width: 100%; vertical-align: middle;'>                                                                  "
		                  + "                 <table width='100%'>                                                                                                 "
		                  + "                     <tr>                                                                                                             "
		                  + "                         <td rowspan='2'>                                         "
		                  + "                             <img src='../../.." + dt[i].SiblingDirectors[j].empImgUrl + "' height='32px' width='32px' style='border-radius:50%;margin:0px 10px 0px 15px' />     "
		                  + "                         </td>                                                                                                        "
		                  + "                         <td align='left'rowspan='2' >                            "
			            if(dt[i].SiblingDirectors[j].DirectorEmpID == null || dt[i].SiblingDirectors[j].DirectorEmpID == ""){
			            	h += "                            <div style='text-align:left; width: 80px; height:20px; font-family:微软雅黑; font-size:12px; color:#999;overflow: hidden;text-overflow: ellipsis;word-break: keep-all; white-space: nowrap;' title='"+dt[i].SiblingDirectors[j].DirectorName+"'> " + dt[i].SiblingDirectors[j].DirectorName +"</div>";
			            }else{
			            	h += '                            <div style="text-align:left; width: 80px; height:20px; font-family:微软雅黑; font-size:14px; color:#333;overflow: hidden;text-overflow: ellipsis;word-break: keep-all; white-space: nowrap;cursor: pointer;" title="'+dt[i].SiblingDirectors[j].DirectorName+'" onclick="openPerson(\''+dt[i].SiblingDirectors[j].DirectorEmpID+'\');" > ' + dt[i].SiblingDirectors[j].DirectorName +'</div>';
			            	h += '							  <div style="text-align:left; width: 80px; height:21px; font-family:微软雅黑; font-size:12px; color:#999;overflow: hidden;text-overflow: ellipsis;word-break: keep-all; white-space: nowrap;cursor: pointer;" title="'+dt[i].SiblingDirectors[j].PositionName +'" onclick="openPost(\''+dt[i].SiblingDirectors[j].PositionId+'\');" >' + dt[i].SiblingDirectors[j].PositionName + '</div>';
			            }
			                  + "                         </td>"
			                  + "                     </tr>                                                                                                            "
			                  + "                     <tr>                                                                                                             "
			                  + "                         <td align='left' style='text-align:middle; padding-left: 0px; padding-top: 13px; border-left-color: Black; font-family:微软雅黑; font-size:12px; color:#7885a4;'>             "
			            h += "                         </td>                                                                                                        "
			                  + "                     </tr>                                                                                                            "
			                  + "                 </table>                                                                                                             "
			                  + "             </td>                                                                                                                    "
			                  + "         </tr>                                                                                                                        "
		            }
            	}
        	}

            /*显示更多人员按钮*/
            if(dt[i].DirectorNumber > 1){
            h += 	"		<tr class='fatherNode-footer' id='fatherNode-footer'>		"
                  + '			<td class="fatherNode-footer-icon" onclick="changeOrganization(\''+ dt[i].OrganizationalID +'\',\''+ dt[i].DirectorNumber + '\');">						'
                  + "				<div class='fatherNode-footer-icon-arr fatherNode-footer-icon-arrClose'></div>		"
                  + "			</td>													"
                  + "		</tr>														";
            }
            
                  
            h += "     </table>                                                                                                                         "
                  + " </div>";
            return h;
        }
    }
    return "";
}

//展开/收缩组织架构
var changeOrganization = function(id,num){
	var cssTr = $(this);
	id= ".divShowD." + id + " tr:nth-child(2)";
	$(id).nextUntil("#fatherNode-footer").fadeToggle(50, "swing", function(){
		var topDiv = $(this).parent();
		if(topDiv.find(" tr:nth-child(3)").css("display") == 'none'){
			topDiv.find(".fatherNode-footer-icon-arr").addClass("fatherNode-footer-icon-arrClose");
			topDiv.closest("td.node").height("");
			//setWHnone();
		}else{
			topDiv.find(".fatherNode-footer-icon-arr").removeClass("fatherNode-footer-icon-arrClose");
			var node_height = 20 + num*60 + 10;	//20：标题高度	60:每行高度	10:展开按钮高度
			if(topDiv.closest("td.node").height()!=node_height){
				topDiv.closest("td.node").height(node_height);
				//setWHnone();
			}
		}
	});
};

//重置组织架构
var initAllSibling = function(){
	//缩起
    $("tr.sibling-director").hide();
	$("tr.sibling-director").closest("td.node").height("");
    $("div.fatherNode-footer-icon-arr").addClass("fatherNode-footer-icon-arrClose");
}


//放大
function ShowBig() {
    initSize = initSize + 0.1;
    if (initSize > 1.2) {
        initSize = 1.2;
    }
    else if (initSize <= 1.2 && initSize > 1) { initSize = 1.2; }
    else if (initSize <= 1 && initSize > 0.8) { initSize = 1; }
    else if (initSize <= 0.8) { initSize = 0.8; }
    $("#main").css("transition", "all 0.5s ease-in-out");
    //$("#main").css("transform", "scale(" + initSize + ")");
    //$("#main").css("transform-origin", "0 0");
    $("#main").css("zoom",initSize)
    if (initSize >= 1.2) {
        worldMap.style.width = (mapsW * initSize * 1.05).toString() + 'px';
        left.style.width = (mapsW * initSize).toString() + 'px';
        worldMap.style.height = (mapsH * initSize).toString() + 'px';
    }
    //setWHnone();
}
//缩小
function ShowSmall() {
    initSize = initSize - 0.1;
    if (initSize >= 1.2) {
        initSize = 1.2;
    }
    else if (initSize < 1.2 && initSize >= 1) { initSize = 1; }
    else if (initSize < 1 && initSize >= 0.8) { initSize = 0.8; }
    else if (initSize < 0.8) { initSize = 0.8; }
    $("#main").css("transition", "all 0.5s ease-in-out");
    //$("#main").css("transform", "scale(" + initSize + ")");
    //$("#main").css("transform-origin", "0 0");
    $("#main").css("zoom",initSize)
    if (initSize >= 1.2) {
        worldMap.style.width = (mapsW * initSize * 1.05).toString() + 'px';
        left.style.width = (mapsW * initSize).toString() + 'px';
        worldMap.style.height = (mapsH * initSize).toString() + 'px';
    }
    //setWHnone();
}

function openDept(fdId, orgType){
	if(orgType == 2){
		Com_OpenWindow('../../../sys/organization/sys_org_dept/sysOrgDept.do?method=view&fdId='+fdId);
	}else if(orgType == 1){
		Com_OpenWindow('../../../sys/organization/sys_org_org/sysOrgOrg.do?method=view&fdId='+fdId);
	}
}
function openPerson(fdId){
	if (fdId == "" || fdId == undefined || fdId == null){
		// do nothing
	}else{
		Com_OpenWindow('../../../sys/organization/sys_org_person/sysOrgPerson.do?method=view&fdId='+fdId);
	}
}
function openPost(fdId){
	if (fdId == "" || fdId == undefined || fdId == null){
		// do nothing
	}else{
		Com_OpenWindow('../../../sys/organization/sys_org_post/sysOrgPost.do?method=view&fdId='+fdId);
	}
}


window.onresize = function () {
    var w = document.body.clientWidth + 'px';
    if (map) {
        map.viewingBox.style.width = w;
    }
}