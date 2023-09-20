/**
 * 业务建模三级页面的通用js文件
 */

seajs.use(["lui/jquery", "lui/dialog","lang!sys-modeling-main"], function ($, dialog,modelingLang) {
	
	/*
	 *	数据唯一性校验 
	 * 
	 */
	window.Modeling_DataUniqueValidate = function(){
		var isUnique = false;
		var url = Com_Parameter.ContextPath + "sys/modeling/main/dataValidate.do?method=dataValidate";
	    $.ajax({
	        url: url,
	        type: "post",
	        data: $('form').serialize(),
	        async : false,
	        success: function (rtn) {
	            if (rtn.status === '00') {
					isUnique = true;
	            }else{
					dialog.alert(rtn.errmsg || modelingLang['modeling.data.uniqueness.verification.tips']);
					isUnique = false;
				}
	        },
	        error : function(rtn){
	        	dialog.alert(rtn.errmsg || modelingLang['modeling.data.uniqueness.verification.tips']);
	        	isUnique = false;
	        }
	    });
		
		return isUnique;
	}
    
	Com_Parameter.event["submit"].push(Modeling_DataUniqueValidate);
});

