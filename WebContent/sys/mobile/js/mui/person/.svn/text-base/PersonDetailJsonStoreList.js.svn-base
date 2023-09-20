define([ "dojo/_base/declare",  "mui/list/JsonStoreList",'dojo/_base/lang'
    ],function(declare,JsonStoreList,lang){
	
	return declare("mui.person.PersonDetailJsonStoreList", [JsonStoreList], {
		
		formatDatas : function(datas) {
			var dataed = [];
			if(datas.length>0){
				var item=datas[0];
				if(item instanceof Array)
					dataed=this.inherited(arguments);
				else
					dataed=datas;
			}
			return dataed;
		},
		
		// 构建
		buildQuery : function() {
			return lang.mixin({} , {
						pageno : this.pageno,
						rowsize : this.rowsize,
						personId:this.personId
			});
		}
		
	});
	
});