define(
		[
				"dojo/_base/declare",
				"dojo/_base/array",
				'dojo/topic',
				"./item/MyNoteCourseItem"
				],

		function(declare, array, topic, MyNoteCourseItem) {

			return declare("kms.common.myNoteCourse.list.mixin",null,{
						
						lazy : false,
			
						rowsize: 10,	
						
						itemRenderer: MyNoteCourseItem,

						url : '/kms/common/kms_notes/kmsCourseNotes.do?method=getNoteCourses&mode=myNotes',						
						
						
		});
	})
