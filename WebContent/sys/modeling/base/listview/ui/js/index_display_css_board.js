//极简模式下鼠标移入预览
$('.cardClassifyContent.minimalism').find(".cardClassifyDetails").mouseenter(function(){
	var clientTop = $(this).offset().top;
	var clientleft = $(this).offset().left;
	var txt = $(this).children('.cardClassifyDetailsText').children('.cardClassifyDetailsTitle').text();
	var subTitle = $(this).children('.cardClassifyDetailsText').find('.cardClassifyAbstractTitle').text();
	var subContent = $(this).children('.cardClassifyDetailsText').find('.cardClassifyAbstractContent').text();
	$(this).parents('.boardContainer').append("<div class='cardClassifyDetailsPreview'></div>");
	let preview = $(this).parents('.boardContainer').find('.cardClassifyDetailsPreview');
	let content = `<div class="cardClassifyDetailsCover"></div>
									<div class="cardClassifyDetailsText">
										<div class="cardClassifyDetailsTitle panelShowText">
											${txt}
										</div>
										<div class="cardClassifyAbstract">
											<div class="cardClassifyAbstractTitle">
												${subTitle}
											</div>
											<div class="cardClassifyAbstractContent">
												${subContent}
											</div>
										</div>
									</div>`;
	preview.css({
		"top": clientTop + 16,
		"left": clientleft + 222
	});
	preview.append(content).show();

}).mouseleave(function(){
	$('.cardClassifyDetailsPreview').remove();
});