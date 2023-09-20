/**
 * 
 */
(function(){
	function ChartMode(){
		this.mode = [];
		
		this.getMode = function(modeType){
			var self = this;
			if(self.mode.length == 0){
				$.ajax( {
					url : Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=getChartMode&modeType=" + modeType,
					type : 'get',
					async : false,//同步请求
					success : function(json) {
						for(var key in json){
							self.mode.push(json[key]);
						}
					},
					dataType : 'json',

					error : function(msg) {
						alert("图表控件请求图表模式时出错:"+msg);
					}
				});
			}
			return self.mode;
			
		},
		
		this.getItemByVal = function(val){
			var self = this;
			for(var i = 0;i < self.mode.length;i++){
				var m = self.mode[i];
				if(m.value == val){
					return m;
				}
			}
		},
		
		this.getItemByMainModelName = function(modelName){
			var self = this;
			for(var i = 0;i < self.mode.length;i++){
				var m = self.mode[i];
				if(m.mainModelName == modelName){
					return m;
				}
			}
		}
	}
	
	window.dbEchartChartMode = new ChartMode();
})()