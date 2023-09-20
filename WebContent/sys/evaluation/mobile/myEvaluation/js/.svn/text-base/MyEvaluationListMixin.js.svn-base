define(
		[
				"dojo/_base/declare",
				"dojo/_base/array",
				'dojo/topic',
				"./MyEvaluationListItem"
				],

		function(declare, array, topic, MyEvaluationListItem) {

			return declare("sys.evaluation.myEvaluationmixin",null,{
						
						lazy : false,
			
						rowsize: 10,	
						
						itemRenderer: MyEvaluationListItem,

						url : '/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=getMyEvaluations',						
						
						formatDatas: function(datas){							
							return datas;
						},
						
		});
	})
