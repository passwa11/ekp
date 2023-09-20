var isnext=false;  //全局变量，标识是否经过下一节点函数，经过selectOrg不执行
var callback_;  //全局变量，回调函数
function closeLdRighModal() {
    $('.ld-right-selector').removeClass('ld-right-selector-show')
    var timer = setTimeout(function() {
        $('.ld-right-mask').removeClass('ld-right-mask-show')
        clearInterval(timer)
    }, 500)
    removeTouchListener();
    $("[name='keyword']").val('');
    $("#yixuan").html('');//点击取消，清楚侧边栏已选择数据
    ableScroll();
}
$('.ld-right-mask').click(function(e) {
    if ((e.target || e.srcElement).id == "ld-right-mask") {
        closeLdRighModal()
    }
})
/**
 * 选择组织架构
 * @param id 对象id
 * @param name 对象 name
 * @param orgType  组织架构类型
 * @returns
 */
function selectOrgElement(id,name,parentId,multi,orgType,callback){
	callback_=callback;
	$('.ld-right-mask').addClass('ld-right-mask-show');
    var timer=setTimeout(function(){
        $('.ld-right-selector').addClass('ld-right-selector-show')
        clearInterval(timer)
    },50)
    addTouchListener();
    var current_id=$("[name='"+id+"']").val();
    if(current_id&&current_id.indexOf(';')>-1){
    	current_id=current_id.split(';')[0];  //多选
    }
    $.ajax({
        type: 'post',
        url:Org_JS_InitData["LUI_ContextPath"]  + '/fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=parentNode',
        data: {"parentId":current_id,"orgType":orgType},
    }).success(function (data) {
 	   var rtn = JSON.parse(data);
 	   parentId=rtn['parentId'];
 	   deptList(parentId,id,name,multi,orgType,'0');  //0 显示当前同级  1显示上一级
 	   $("[name='currentParentId']").val(parentId);
 	   forbiddenScroll();
    })
}

//选择完点击确定按钮
function confirmChecked(){
	var currentFeildId=$("[name='currentFeildId']").val();
	var currentFeildName=$("[name='currentFeildName']").val();
	var change_event=$("[name='"+currentFeildName+"']").attr("onchange");
	var params;
  	if(change_event){
  		params=change_event.replace("changeValue('","").replace("');","");
  	}
	var multi=$("[name='currentMulti']").val();
	if(multi=='true'){//多选
		var xuanzhongName =""; 
		var xuanzhongId =""; 
		$("#yixuan").find('li').each(function(){
			  var name=$(this).find("p").html();
			  var src=$(this).find('img').attr('src');
			  var id=Com_GetUrlParameter(src,"personId");
			  xuanzhongId += id+";";
 	 		  xuanzhongName += name+";";
		  });
		 $("[name='"+currentFeildId+"']").val(xuanzhongId.substr(0, xuanzhongId.length - 1));
		 $("[name='"+currentFeildName+"']").val(xuanzhongName.substr(0, xuanzhongName.length - 1));
	}else{//单选
		var xuanzhong=$("input[name='selector']:checked").val();
		if(xuanzhong){
			$("[name='"+currentFeildId+"']").val(xuanzhong.split("-")[0]);
			$("[name='"+currentFeildName+"']").val(xuanzhong.split("-")[1]);
		}
	}
	changeValue(params);//选择完组织架构触发对应的联动函数
	closeLdRighModal();  //隐藏侧边栏
	if(callback_){
		callback_($("[name="+currentFeildId+"]").val());
	}
	$("[name='keyword']").val('');
	ableScroll();
}
    
  //上一个节点
    function parentRighModal(){
 		var parentId = $("[name='currentParentId']").val();
 	   $.ajax({
           type: 'post',
           url:Org_JS_InitData["LUI_ContextPath"]  + '/fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=parentNode',
           data: {"parentId":parentId},
       }).success(function (data) {
    	   var rtn = JSON.parse(data);
    	   var name=$("[name='currentFeildName']").val();
    	   var id=$("[name='currentFeildId']").val();
    	   var multi=$("[name='currentMulti']").val();
    	   var orgType=$("[name='currentOrgType']").val();
    	   if("dept"==orgType){
    		   deptList(rtn.parentId,id,name,multi,orgType,'1'); 
    	   }else if("person"==orgType){
    		   deptList(rtn.parentId,id,name,multi,orgType,'1'); 
    	   }
    	   $("[name='currentParentId']").val(rtn.parentId);
    	   diaplayChecked();  //选中当前被选择的组织架构
       })
    }
    
  //选择下一个节点
  function nextRighModal(parentId,parentName,multi,orgType,id,name) {
	  if("dept"==orgType){
		  isnext=true;
	  }
	  deptList(parentId,id,name,multi,orgType,'2');  //下一级
	  $("[name='currentParentId']").val(parentId);
	  diaplayChecked();  //选中当前被选择的组织架构
  }
  
  //选中当前被选择的组织架构
  function diaplayChecked(){
	  $("#yixuan").find('li').each(function(){
		  var name=$(this).find("p").html();
		  var src=$(this).find('img').attr('src');
		  var id=Com_GetUrlParameter(src,"personId");
		  $("input[name='selector'][value='"+id+"-"+name+"']").prop("checked",true);
	  });
  }

/**
 * 获取当前人员、部门列表
 * @param parentId 对象父级
 * @param id 对象id
 * @param orgType  组织架构类型
 * @returns
 */
function deptList(parentId,id,name,multi,orgType,disType){
	 //回车搜索
    $("[name='keyword']").keypress(function (e) {
        if (e.which == 13) {
        	var keyword=$("[name='keyword']").val();
        	if(keyword){
        		$.ajax({
        	           type: 'post',
        	           url:Org_JS_InitData["LUI_ContextPath"] + '/fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=getOrganizationList',
        	           data: {"keyword":keyword,"type":$("[name='currentOrgType']").val(),'queryType':'search'},
        	       }).success(function (data) {
        	    	   console.log('获取信息成功');
        	    	   var rtn = JSON.parse(data);
        	    	   if('dept'==orgType){
        	    		   buildDeptData(id,name,multi,rtn,orgType,disType);
        	        	}else{
        	        		buildPersonData(id,name,multi,rtn,disType,orgType);
        	        	}
        	       }).error(function (data) {
        	    	   console.log('获取信息失败');
        	       })
        	}
        }
	});
	$("[name='currentFeildId']").val(id);
	$("[name='currentFeildName']").val(name);
	$("[name='currentMulti']").val(multi);
	$("[name='currentOrgType']").val(orgType);
	if(parentId){
		$("[name='currentParentId']").val(parentId);
	}else{
		$("[name='currentParentId']").val('');
	}
	$("[name='currentOrgType']").val(orgType);
	if('dept'==orgType){
		var keyword = $("[name='keyword']").val();
		var currentDeptId;
		if("0"==disType){//显示同级
			currentDeptId=$("[name='"+id+"']").val();
			if(currentDeptId&&currentDeptId.indexOf(';')>-1){
				currentDeptId=currentDeptId.split(';')[0];  //多选
		    }
		}else if("1"==disType){//显示上一级
			currentDeptId=parentId;
		}else if("2"==disType){//显示下一级
			currentDeptId=parentId;
		}
		$.ajax({
	           type: 'post',
	           async:false,
	           url:Org_JS_InitData["LUI_ContextPath"] + '/fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=getOrganizationList',
	           data: {"currentDeptId":currentDeptId,"type":"dept","keyword":keyword,"exceptValue":"","disType":disType},
	       }).success(function (data) {
	      	 var rtn = JSON.parse(data);
	      	 buildDeptData(id,name,multi,rtn,orgType,disType);
	       }).error(function () {
	           console.log("获取部门失败");
	   	   })
	} else if('person'==orgType){
		var keyword = $("[name='keyword']").val();
		if("0"==disType){//显示同级
			currentDeptId=$("[name='"+id+"']").val();
			if(currentDeptId&&currentDeptId.indexOf(';')>-1){
				currentDeptId=currentDeptId.split(';')[0];  //多选
		    }
		}else if("1"==disType){//显示上一级
			currentDeptId=parentId;
		}else if("2"==disType){//显示下一级
			currentDeptId=parentId;
		}
	   	$.ajax({
			url: Org_JS_InitData["LUI_ContextPath"] + '/fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=getOrganizationList',
			type: 'post',
			async:false,
			data: {"currentDeptId":currentDeptId,"type":"person","keyword":keyword,"exceptValue":"","disType":disType},
	   	}).error(function(data){
				console.log("获取人员失败"+data);
		}).success(function(rtnData){
			console.log("获取人员成功");
	      	 var data = JSON.parse(rtnData);
	      	buildPersonData(id,name,multi,data,disType,orgType);
		});
	}
}

/**
 * 构建人员显示
 */
function buildPersonData(id,name,multi,data,disType,orgType){
	var rtn = data.data[0].list;
	if(rtn.length==0){
		return;
	}
 	if(data.currentDeptName){
 		$('.department').html(data.currentDeptName);
	}else{
		$('.department').html('');
	}
 	 $('.ld-right-selector-list .ld-right-selector-list-ul li').remove();
 	 var html ="";
 	 var icon ="";
 	 var input_type = multi=='true'?'checkbox':'radio';
 	 for(var i=0;i<rtn.length;i++){
 		$("[name='currentParentId']").val(rtn[i]["parentId"]);
 		 var type=rtn[i]["type"];
			 if (type=='person'){//人员
				icon = Org_JS_InitData["LUI_ContextPath"]+"/sys/person/image.jsp?personId="+rtn[i]["id"];
			 }else{//部门、机构
				icon = Org_JS_InitData["LUI_ContextPath"] +"/resource/style/default/tree/dept.gif";
			 } 
			 if(type=='person'){
				html += ("<li><label onclick=\"selectedOrg('"+rtn[i]["id"]+"','"+rtn[i]["name"]+"',"+multi+");\">" +
     				"<div class='ld-right-selector-name'><img src='"+icon+"' alt=''>" +
     				"<span>"+ rtn[i]["name"]+ "</span></div>" +
     				"<div class=\"ld-modal-checkbox\">" +
     				"<input type=\""+input_type+"\" name=\"selector\" value="+rtn[i]["id"]+"-"+rtn[i]["name"]+">" +
     				"<span class=\"modal-checkbox-label\"></span></div>" +
     				"</label></li>");
			 }else{//不显示勾选
				html += ("<li><label onclick=\"nextRighModal('"+rtn[i]["id"]+"','"+rtn[i]["name"]+"','"+multi+"','"+orgType+"','"+id+"','"+name+"');\">" +
     				"<div class='ld-right-selector-name'><img src='"+icon+"' alt=''>" +
     				"<span>"+ rtn[i]["name"]+ "</span></div>" +
     				"<div class=\"ld-modal-checkbox\">" +
     				"<input type=\"hidden\" name=\"selector\" value="+rtn[i]["id"]+"-"+rtn[i]["name"]+">" +
     				"<span class=\"modal-checkbox-label\"></span></div>" +
     				"</label></li>");
			 }
     }
 	//拼接获取人员、部门的列表
 	$('#daixuan').html(html);
 	if("0"==disType){  //点击上一级不改变已选
 		var idValue=$("[name='"+id+"']").val();
     	var nameValue=$("[name='"+name+"']").val();
     	if(idValue){
     		if(multi=="true"){
     			html='';
     			var ids=idValue.split(';');
     			var names=nameValue.split(';');
     			for(var i=0;i<ids.length;i++){
     				$("input[name='selector'][value='"+ids[i]+"-"+names[i]+"']").prop("checked",true);
    	      		var src=Org_JS_InitData["LUI_ContextPath"] +"/sys/person/image.jsp?personId="+ids[i];
    	      		if(src){
    	      			html+="<li><img src=\""+src+"\" alt=\"\"><p>"+names[i]+"</p></li>";
    	      		}
     			}
	      		$('#yixuan').html(html);  //已选择
     		}else{//单选
     			$("input[name='selector'][value='"+idValue+"-"+nameValue+"']").prop("checked",true);
	      		var src=$("input[name='selector'][value='"+idValue+"-"+nameValue+"']").parent().parent().find("img").attr("src");
	      		if(src){
	      			html="<li><img src=\""+src+"\" alt=\"\"><p>"+nameValue+"</p></li>";
		      		$('#yixuan').html(html);  //已选择
	      		}
     		}
     	}
 	}
}
/**
 * 构建部门显示
 */
function buildDeptData(id,name,multi,rtn,orgType,disType){
	if(rtn.currentDeptName){
		$('.department').html(rtn.currentDeptName);
	}else{
		$('.department').html('');
	}
  	 if(rtn.result=='failure'){
  		jqalert({
              title:'',
              content:rtn.message,
              yestext:message['button.ok']
          })
  		return ;
  	 }
    $('.ld-right-selector-list .ld-right-selector-list-ul li').remove();
  	 var html ="";
  	 var icon =Org_JS_InitData["LUI_ContextPath"] +"/resource/style/default/tree/dept.gif";
  	 var type = 2;
 	 var list= rtn.data[0]['list'];
 	 var input_type = multi?'checkbox':'radio';
  	 for(var i=0;i< list.length;i++){
  			html += "<li><label><div class='ld-right-selector-name' " ;
  			if(list[i]["childFlag"]=='1'){
  				html +="onclick=\"nextRighModal('"+list[i]["id"]+"','"+list[i]["name"]+"','"+multi+"','"+orgType+"','"+id+"','"+name+"');\"" ;
  			}
  			html +="><img src='"+icon+"' alt=''><span>"+ list[i]["name"]+ "</span></div>" +
  				"<div class=\"ld-modal-checkbox\" onclick=\"selectedOrg('"+list[i]["id"]+"','"+list[i]["name"]+"',"+multi+");\">" +
  				"<input type=\""+input_type+"\" name=\"selector\" value="+list[i]["id"]+"-"+list[i]["name"]+">" +
  				"<span class=\"modal-checkbox-label\"></span></div>" +
  				"</label></li>";
  	 }
  	//拼接获取人员、部门的列表
  	$('#daixuan').html(html);
  	if("0"==disType||"2"==disType){  //点击上一级或下一级不改变已选
      	var idValue=$("[name='"+id+"']").val();
      	var nameValue=$("[name='"+name+"']").val();
      	if(idValue){
      		if(multi=="true"){
      			html='';
      			var ids=idValue.split(';');
     			var names=nameValue.split(';');
     			for(var i=0;i<ids.length;i++){
     				$("input[name='selector'][value='"+ids[i]+"-"+names[i]+"']").prop("checked",true);
    	      		html+="<li><img src=\""+icon+"\" alt=\"\"><p>"+names[i]+"</p></li>";
     			}
	      		$('#yixuan').html(html);  //已选择
      		}else{//单选
      			$("input[name='selector'][value='"+idValue+"-"+nameValue+"']").prop("checked",true);
	      		var src=$("input[name='selector'][value='"+idValue+"-"+nameValue+"']").parent().parent().find("img").attr("src");
	      		if(src){
	      			html="<li><img src=\""+src+"\" alt=\"\"><p>"+nameValue+"</p></li>";
		      		$('#yixuan').html(html);  //已选择
	      		}
      		}
      	}
  	}
}
/**
 * 选中后切换效果
 */
function selectedOrg(id,name,multi){
	if(isnext){
		isnext=false;
		return;
	}
	if(multi){
		var event = event ? event : window.event;
		var obj = event.srcElement ? event.srcElement : event.target;
		if(obj.tagName=='label'||obj.tagName=='LABEL'){
			return ; //阻止冒泡
		}
		var html='';
		//需判断是否重复点击
		var val=obj.value;
		if(val){
			val=val.trim();
		}
		var id=val.split('-')[0];
		var name=val.split('-')[1];
		if(obj.checked){//选中
			var src=$("input[name='selector'][value='"+id+"-"+name.trim()+"']").parent().parent().find("img").attr("src");
	  		html="<li><img src=\""+src+"\" alt=\"\"><p>"+name+"</p></li>";
		}else{//取消选中
			$('#yixuan').find('li').each(function(){
				var src=$(this).find('img').attr('src');
				if(src.indexOf(id)>-1){
					$(this).remove();
				}
			});
		}
		$('#yixuan').append(html);    //已选择
	}else{
		//若是单选，选择了当前值需要去除其他值的选中效果
		$("input[name='selector']").prop("checked",false); //全部清除选中
		$("input[name='selector'][value='"+id+"-"+name.trim()+"']").prop("checked",true);  //选中选择的
  		var src=$("input[name='selector'][value='"+id+"-"+name.trim()+"']").parent().parent().find("img").attr("src");
  		html="<li><img src=\""+src+"\" alt=\"\"><p>"+name.trim()+"</p></li>";
  		$('#yixuan').html(html);  //已选择
	}
}

function changeValue(json){
	  if(json){
		  var jsonObj=JSON.parse(json);
		  for(var n=0;n<jsonObj.length;n++){
			  var obj=jsonObj[n];
			  var func=obj.func;
			  if(func){
				  eval(func+"('"+obj.params+"','"+obj.target+"')");
			  }
		  }
	  }
}
