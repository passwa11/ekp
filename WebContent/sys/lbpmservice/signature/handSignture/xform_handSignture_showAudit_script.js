/**
 * 表单手写签章
 */

Com_AddEventListener(window,'load',function(){
	// 隐藏的不用加载
	try{
		var revisionObjList = $("[name='iWebRevisionObject']:visible");
		for(var i=0;i<revisionObjList.length;i++){
			// 不可编辑
			revisionObjList[i].Enabled = '0';
			// 隐藏不必要的菜单
			revisionObjList[i].InvisibleMenus("-2,-3,-4,-5,-6,-7,");
			revisionObjList[i].LoadSignature();
		}		
	}catch(e){
		
	}
	
});