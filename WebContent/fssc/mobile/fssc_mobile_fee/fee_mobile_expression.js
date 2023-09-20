   /**
    * 计算主表的控件的值，保存和取消，删除是调用
    * @returns
    */
	function reCaculateMain(){
		var forms = document.forms;
		//console.log("reCaculateMain==========");
		$('input[class="mainCaculate"]').each(function(){
			var expression=this.getAttribute("expression");
			var isRow=this.getAttribute("isRow")=='true';
			var value=XForm_CalculationExecuteExpression(expression, isRow, forms);
			if(value){
			this.value=value;
			}
			});
	}
	
	/**
	 * 获取与当前元素有关的控件元素
	 * @param dom
	 * @returns
	 */
	function XForm_CalculationGetAllContral(dom) {
		var forms = document.forms;
		var objs = [], executor;
		var varName = dom.name;
		if (varName == null) {return objs;}
		varName = varName.replace(".id","");
		varName = varName.replace("extendDataFormInfo.value(","");
		varName = varName.replace(")","");
		for (var i = 0, l = forms.length; i < l; i ++) {
			var elems = forms[i].elements;
			for (var j = 0, m = elems.length; j < m; j ++) {
				executor = elems[j];
				//console.log("varName:"+varName+"==="+executor.getAttribute("expression"));
				//console.log("----"+executor.name+"---"+executor.getAttribute("expression")+"----"+varName)
					if (executor.getAttribute("expression")&&executor.getAttribute("expression").indexOf(varName) > 0) {
						console.log("----"+executor.name+"---"+executor.getAttribute("expression")+"----"+varName)
						if ( executor.getAttribute("isRow") == 'true' && varName.indexOf('.') > 0) {
							objs.push(elems[j]);
							continue;
						}
						objs.push(elems[j]);
					
					continue;
				}
			}
		}
		//console.log("objs:"+JSON.stringify(objs));
		return objs;
	}

	
   /**
    * 当前元素改变，计算与当前元素相关的控件的值
    * @param currentDom
    * @returns
    */
	function caculate(currentDom){
	        var currentDom=currentDom;
	        var dom = document.forms;
		var executors = XForm_CalculationGetAllContral(currentDom);
		for (var i = 0; i < executors.length; i ++) {
		var expression=executors[i].getAttribute("expression");
		var isRow=executors[i].getAttribute("isRow")=='true';
		var value=XForm_CalculationExecuteExpression(expression, isRow, dom);
		if(value){
			executors[i].value=value;
		}
		

		}
	}
	
        