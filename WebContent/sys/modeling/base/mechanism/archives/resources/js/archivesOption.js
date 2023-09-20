function initxformData(fdModelId,type){
		//获取自定义表单的属性
		var url = Com_Parameter.ContextPath +"sys/modeling/main/modelingAppView.do?method=getSubjectOption&fdModelId="+fdModelId;
		$.ajax({
			type:"POST",
			url:url,
			success:function(result){
				doCreateOption(result,type);
			},
			error:function(){
			    if(window.console){
                    console.log("获取表单自定义属性错误");
                }
			}
		});
	}

function doCreateOption(result,type,selectedValue){
    if(!result){
        return;
    }
    var rtn = $.parseJSON(result);
    var StringHtml = '';
    var PersonHtml = '';
    var TimeHtml = '';
    for(var key in rtn){
        //过滤明细表字段
        if(rtn[key].name.indexOf(".") != -1){
            continue;
        }
        switch(rtn[key].type) {
            case "String":
                if(type == "extend" && (rtn[key].businessType == "mainModel" || rtn[key].businessType == "fdId") ){
                    continue;
                }else{
                    StringHtml += '<option value="'+key+'">'+rtn[key].label+'</option>';
                }
                break;
            case "com.landray.kmss.sys.organization.model.SysOrgPerson":
                PersonHtml += '<option value="'+key+'">'+rtn[key].label+'</option>';
                break;
            case "DateTime":
                TimeHtml += '<option value="'+key+'">'+rtn[key].label+'</option>';
                break;
            case "Date":
                TimeHtml += '<option value="'+key+'">'+rtn[key].label+'</option>';
                break;
        }
    }
    if(type == "extend"){
        $("#file_extendFieldTB").find("[fType='String']").append(StringHtml);
        $("#file_extendFieldTB").find("[fType='Date|DateTime']").append(TimeHtml);
        $("#file_extendFieldTB").find("[ftype='com.landray.kmss.sys.organization.model.SysOrgPerson']").append(PersonHtml);
    }else{
        $("[fType='String']").each(function () {
            if($(this).closest("table").attr("id") != "file_extendFieldTB"){
                $(this).append(StringHtml);
                var selected = $(this).closest("td").find("[name*='selectedValue']").val();
                if(selected){
                    $(this).find("option[value='"+selected+"']").attr("selected",true);
                }
            }
        });
        $("[fType='Date|DateTime']").each(function () {
            if($(this).closest("table").attr("id") != "file_extendFieldTB") {
                $(this).append(TimeHtml);
                var selected = $(this).closest("td").find("[name*='selectedValue']").val();
                if (selected) {
                    $(this).find("option[value='" + selected + "']").attr("selected", true);
                }
            }
        });
        $("[ftype='com.landray.kmss.sys.organization.model.SysOrgPerson']").each(function () {
            if($(this).closest("table").attr("id") != "file_extendFieldTB") {
                if($(this).find("option[value='docCreator']").length == 1  &&  PersonHtml.indexOf('value="docCreator"') > -1){
                    $(this).find("option[value='docCreator']").remove();
                }
                $(this).append(PersonHtml);
                var selected = $(this).closest("td").find("[name*='selectedValue']").val();
                if (selected) {
                    $(this).find("option[value='" + selected + "']").attr("selected", true);
                }
            }
        });
    }
}