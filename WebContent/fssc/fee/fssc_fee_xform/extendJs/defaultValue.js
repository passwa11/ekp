seajs.use(['lui/jquery'],function($){
	window.setDefaultValue = function(id,name,type,params,output){
		if(Com_GetUrlParameter(window.location.href,"method")=='view'){
			return;
		}
		
		var data = new KMSSData();
		var bean = "fsscFeeDataService&type="+type;
		if(params){
			for(var i in params){
				bean+='&'+i+"="+$("[name='extendDataFormInfo.value("+params[i].replace("!{index}",0)+")']").val();
			}
		}
		data.AddBeanData(bean);
		data = data.GetHashMapArray();
		if(!data||data.length==0){
			data = [{id:"",name:""}]
		}
		if(Com_GetUrlParameter(window.location.href,"method")=='edit'){
			if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
				//绑定事件
				$(".opt_add_style").parent().click(function(){
					var tb = DocListFunc_GetParentByTagName("TABLE",this);
					$("[name='extendDataFormInfo.value("+id.replace("!{index}",tb.rows.length-4)+")']").val(data[0].id);
					$("[name='extendDataFormInfo.value("+name.replace("!{index}",tb.rows.length-4)+")']").val(data[0].name);
					if(output){
						for(var i=0;i<output.length;i++){
							$("[name='extendDataFormInfo.value("+id.replace("!{index}",tb.rows.length-4)+output[i]+")']").val(data[0][output[i]]);
						}
					}
				});
			}
			return;
		}
		//明细表
		if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
			setTimeout(function(){
				$("[name='extendDataFormInfo.value("+id.replace("!{index}",0)+")']").val(data[0].id);
				$("[name='extendDataFormInfo.value("+name.replace("!{index}",0)+")']").val(data[0].name);
				if(output){
					for(var i=0;i<output.length;i++){
						$("[name='extendDataFormInfo.value("+id.replace("!{index}",0)+output[i]+")']").val(data[0][output[i]]);
					}
				}
			},500);
			//绑定事件
			$(".opt_add_style").parent().click(function(){
				var tb = DocListFunc_GetParentByTagName("TABLE",this);
				$("[name='extendDataFormInfo.value("+id.replace("!{index}",tb.rows.length-4)+")']").val(data[0].id);
				$("[name='extendDataFormInfo.value("+name.replace("!{index}",tb.rows.length-4)+")']").val(data[0].name);
				if(output){
					for(var i=0;i<output.length;i++){
						$("[name='extendDataFormInfo.value("+id.replace("!{index}",tb.rows.length-4)+output[i]+")']").val(data[0][output[i]]);
					}
				}
			});
		}else{
			$("[name='extendDataFormInfo.value("+id+")']").val(data[0].id);
			$("[name='extendDataFormInfo.value("+name+")']").val(data[0].name);
			if(output){
				for(var i=0;i<output.length;i++){
					$("[name='extendDataFormInfo.value("+id+output[i]+")']").val(data[0][output[i]]);
				}
			}
		}
	}
})
