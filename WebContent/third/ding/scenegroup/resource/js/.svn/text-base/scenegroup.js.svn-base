
/**
 * 创建场景群
 * fdId：主文档ID，必填
 * modelName：主文档modelName，必填
 * fdKey：选填
 * moduleKey：群模块标识，必填
 * title：群名称，必填
 * ownerPersonId：群主的fdId，必填
 * initPersonIds：初始群成员，选填
 */
window.createScenegroup = function(fdId,modelName,fdKey,moduleKey,title,ownerPersonId,initPersonIds){
		if(!initPersonIds){
			initPersonIds = "";
		}
		var el = $("<input type='hidden' id='scenegroup_person_id' name='scenegroup_person_id' value='"+initPersonIds+"'></input>");
		var e2 = $("<input type='hidden' id='scenegroup_person_name' name='scenegroup_person_name' value=''></input>");
		$('body').append(el);
		$('body').append(e2);
		seajs.use(['lui/topic'],function(topic){
			Dialog_Address(true,"scenegroup_person_id","scenegroup_person_name",';',ORG_TYPE_PERSON,function(data){
				var datas = data.data;
				var personIds = "";
				for(var i=0;i<datas.length;i++){
					personIds += datas[i].id +";";
				}
				var data = {"title":title, "fdId" : fdId, "modelName" : modelName, "fdKey" : fdKey, "moduleKey" : moduleKey, "ownerPersonId" : ownerPersonId, "personIds" : personIds };
				
				$.ajax({
	                url: Com_Parameter.ContextPath+'third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=createScenegroup',
	                type: 'POST',
	                data: data,
	                dataType: "json",
	                success: function (data, status, xhr) {
	                	if(data.result==true){
	                		alert("新建场景群成功")
	                	}else{
	                		alert(data.errorMsg);
	                	}
	                	$("#scenegroup_person_id").remove();
	            		$("#scenegroup_person_name").remove();
	                },
	                error: function (xhr, errorType, error) {
	                	 alert("新建场景群失败，"+JSON.stringify(error));
	                	 $("#scenegroup_person_id").remove();
	             		$("#scenegroup_person_name").remove();
	                }
	            });
			},null,null,null,null,null,null,null);
		});
	};




/**
 * 场景群加人
 * fdId：主文档ID，必填
 * modelName：主文档modelName，必填
 * fdKey：选填
 * moduleKey：群模块标识，必填
 * title：群名称，必填
 * ownerPersonId：群主的fdId，必填
 * initPersonIds：初始群成员，选填
 */
window.addScenegroupMember = function(fdId,modelName,fdKey,initPersonIds){
		if(!initPersonIds){
			initPersonIds = "";
		}
		var el = $("<input type='hidden' id='scenegroup_person_id' name='scenegroup_person_id' value='"+initPersonIds+"'></input>");
		var e2 = $("<input type='hidden' id='scenegroup_person_name' name='scenegroup_person_name' value=''></input>");
		$('body').append(el);
		$('body').append(e2);
		seajs.use(['lui/topic'],function(topic){
			Dialog_Address(true,"scenegroup_person_id","scenegroup_person_name",';',ORG_TYPE_PERSON,function(data){
				var datas = data.data;
				var personIds = "";
				for(var i=0;i<datas.length;i++){
					personIds += datas[i].id +";";
				}
				var data = {"fdId" : fdId, "modelName" : modelName, "fdKey" : fdKey, "personIds" : personIds };
				
				$.ajax({
	                url: Com_Parameter.ContextPath+'third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=addScenegroupMember',
	                type: 'POST',
	                data: data,
	                dataType: "json",
	                success: function (data, status, xhr) {
	                	if(data.result==true){
	                		alert("场景群加人成功")
	                	}else{
	                		alert(data.errorMsg);
	                	}
	                	$("#scenegroup_person_id").remove();
	            		$("#scenegroup_person_name").remove();
	                },
	                error: function (xhr, errorType, error) {
	                	 alert("场景群加人失败，"+JSON.stringify(error));
	                	 $("#scenegroup_person_id").remove();
	             		$("#scenegroup_person_name").remove();
	                }
	            });
			},null,null,null,null,null,null,null);
		});
	};



/**
 * 场景群删人
 * fdId：主文档ID，必填
 * modelName：主文档modelName，必填
 * fdKey：选填
 * moduleKey：群模块标识，必填
 * title：群名称，必填
 * ownerPersonId：群主的fdId，必填
 * initPersonIds：初始群成员，选填
 */
window.addScenegroupMember = function(fdId,modelName,fdKey,initPersonIds){
		if(!initPersonIds){
			initPersonIds = "";
		}
		var el = $("<input type='hidden' id='scenegroup_person_id' name='scenegroup_person_id' value='"+initPersonIds+"'></input>");
		var e2 = $("<input type='hidden' id='scenegroup_person_name' name='scenegroup_person_name' value=''></input>");
		$('body').append(el);
		$('body').append(e2);
		seajs.use(['lui/topic'],function(topic){
			Dialog_Address(true,"scenegroup_person_id","scenegroup_person_name",';',ORG_TYPE_PERSON,function(data){
				var datas = data.data;
				var personIds = "";
				for(var i=0;i<datas.length;i++){
					personIds += datas[i].id +";";
				}
				var data = {"fdId" : fdId, "modelName" : modelName, "fdKey" : fdKey, "personIds" : personIds };
				
				$.ajax({
	                url: Com_Parameter.ContextPath+'third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=delScenegroupMember',
	                type: 'POST',
	                data: data,
	                dataType: "json",
	                success: function (data, status, xhr) {
	                	if(data.result==true){
	                		alert("场景群删人成功")
	                	}else{
	                		alert(data.errorMsg);
	                	}
	                	$("#scenegroup_person_id").remove();
	            		$("#scenegroup_person_name").remove();
	                },
	                error: function (xhr, errorType, error) {
	                	 alert("场景群删人失败，"+JSON.stringify(error));
	                	 $("#scenegroup_person_id").remove();
	             		$("#scenegroup_person_name").remove();
	                }
	            });
			},null,null,null,null,null,null,null);
		});
	};