define(function(require, exports, module) {

	var dialog = require("lui/dialog");
	var $ = require("lui/jquery");
	var lang = require('lang!kms-common');
	
	function addressImport(ele_id,ele_name,_ids,_names,flag) {

		 	//用于批量更改页面地址本,ele_id为元素id,ele_name为元素name,ids为传入id字符串，names为name字符串。flag为是否重置该地址本框
		
			
			var new_ids = _ids; //新地址ids
			var new_names = _names;//新地址names
			
			if(!flag){ //地址本拼接
				var ori_ids = $("input[name='"+ele_id+"']").val(); //原地址ids
				var ori_names = $("textarea[name='"+ele_name+"']").val();//原地址names
				if(ori_ids){//原地址本不为空
					new_ids = ori_ids+";"+new_ids;
					new_names = ori_names+";"+new_names;
				}
		
			}
				
			
			//用于过滤重复字符串
			var opera_ids = ""; //操作后ids
			var opera_names = "";//操作后inames	
			
			if(new_ids){
				var names = new_names.split(";");
				var ids = new_ids.split(";");

				for(var i=0;i<names.length;i++){
					if(opera_ids.indexOf(ids[i])==-1){ //重置地址本或者不重置但id未重复时进入
						var obj = {	
								id:ids[i],
								img:"",
								info:"",
								isAvailable:"true",
								name:names[i],
								orgType:"",
								parentName:"",
								length:0
							};

						if(i==0){
							var values = [];
							values.push(obj);
							var address = Address_GetAddressObj(ele_name);
							address.reset(";","ORG_TYPE_ALL",true,values);
						}else{
							
							var address = Address_GetAddressObj(ele_name);
							address.addData(obj); //插入数据
						
						}
						opera_ids = opera_ids+ids[i]+";";
						opera_names = opera_names + names[i] +";";
					
					
					}
					
				}	
		
				if(opera_ids){
					opera_ids = opera_ids.substring(0,opera_ids.length-1);
					opera_names = opera_names.substring(0,opera_names.length-1);
				}
			
				$("input[name='"+ele_id+"']").val(opera_ids);
				$("textarea[name='"+ele_name+"']").val(opera_names);
			}
			
			
		
			
		};
		
		//清空地址本操作
		function reset(ele_id,ele_name) {
			var address = Address_GetAddressObj(ele_name);
			address.reset(";","ORG_TYPE_ALL",true,null);
			$("input[name='"+ele_id+"']").val("");
			$("textarea[name='"+ele_name+"']").val("");
		}

		
		exports.addressImport = addressImport;
		exports.reset = reset;


});