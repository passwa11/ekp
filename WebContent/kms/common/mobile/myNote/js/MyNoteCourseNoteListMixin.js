define(
		[
				"dojo/_base/declare",
				"dojo/_base/array",
				'dojo/topic',
				"./item/MyNoteCourseNoteItem"
				],

		function(declare, array, topic, MyNoteCourseNoteItem) {

			return declare("kms.common.myNoteCourseNote.list.mixin",null,{
						
						lazy : false,
			
						//todo，删除数据后加载更多有一定问题，目前暂且加载我的所有笔记信息
						rowsize: 100000,
						
						itemRenderer: MyNoteCourseNoteItem,						
						
						
		});
	})
