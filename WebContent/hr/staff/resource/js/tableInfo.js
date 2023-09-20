
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		window.addInfo = function (iframeUrl,config){
			dialog.iframe(iframeUrl, config.title, function(data) {
				if (null != data && undefined != data) {
					data['fdId']=config.id;
					$.post(config.url, data, function(result){
						if(result.status) {
							LUI(config.dataviewId).load();
						} else {
							dialog.alert(result.msg);
						}
					}, "json");
				}
			}, {
				width : 900,
				height : 400
			});
		}
		
		window.deleleInfo = function(config) {
			var values = [];
			if(config.id) {
 				values.push(config.id);
	 		}
			dialog.confirm(config.tip, function(value) {
				if(value == true) {
					window.del_load = dialog.loading();
					$.ajax({
						url : config.url,
						type : 'POST',
						data : $.param({"List_Selected" : values}, true),
						dataType : 'json',
						error : function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							var dataJson = data.responseJSON;
							dataJson['status']=false
							dialog.result();
						},
						success: function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							LUI(config.dataviewId).load();
							
						}
				   });
				}
			});
		};
	})
