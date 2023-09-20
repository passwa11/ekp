$(function() {

	init();
	bind();

	// 绑定事件
	function bind() {

		$('.type').on('click', function(evt) {

			$('.type').removeClass('selected');
			var target = $(evt.target).parents('.type');
			setType(target.data('type'))

		});
	}

	// 设置类型
	function setType(type) {

		$('[data-type="' + type + '"]').addClass('selected');
		$('input[name="value(fdType)"]').val(type);
		var url = window.location.protocol + "//" + window.location.hostname
				+ (window.location.port ? ':' + window.location.port : '');
		url = url + Com_Parameter.ContextPath + "sys/mportal/mobile/?nav="
				+ type;
		$('#url').html(url);
	}

	// 初始化
	function init() {

		var type = 'top';
		if ($('input[name="value(fdType)"]').val()) {
			type = $('input[name="value(fdType)"]').val();
		}

		setType(type);

	}

})