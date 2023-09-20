(function() {
	function addCombo(editor, comboName, styleType, lang, entries,
			defaultLabel, styleDefinition, order) {
		var config = editor.config, style = new CKEDITOR.style(styleDefinition);
		var names = entries.split(';'), values = [];
		var styles = {};
		for (var i = 0; i < names.length; i++) {
			var parts = names[i];
			if (parts) {
				parts = parts.split('/');
				var vars = {}, name = names[i] = parts[0];
				vars[styleType] = values[i] = parts[1] || name;
				styles[name] = new CKEDITOR.style(styleDefinition, vars);
				styles[name]._.definition.name = name;
			} else
				names.splice(i--, 1);
		}
		editor.ui
				.addRichCombo(
						comboName,
						{
							label : editor.lang.lineheight.title,
							title : editor.lang.lineheight.title,
							toolbar : 'styles,' + order,
							allowedContent : style,
							requiredContent : style,
							panel : {
								css : [ CKEDITOR.skin.getPath('editor') ]
										.concat(config.contentsCss),
								multiSelect : false,
								attributes : {
									'aria-label' : editor.lang.lineheight.title
								}
							},
							init : function() {
								this.startGroup(editor.lang.lineheight.title);
								for (var i = 0; i < names.length; i++) {
									var name = names[i];
									this.add(name, null, name);
								}
							},
							onClick : function(value) {
								editor.focus();
								editor.fire('saveSnapshot');
								var style = styles[value];
								editor[this.getValue() == value ? 'removeStyle'
										: 'applyStyle'](style);
								editor.fire('saveSnapshot');
							},
							onRender : function() {
								editor
										.on(
												'selectionChange',
												function(ev) {
													var currentValue = this
															.getValue();
													var elementPath = ev.data.path, elements = elementPath.elements;
													for (var i = 0, element; i < elements.length; i++) {
														element = elements[i];
														for ( var value in styles) {
															if (styles[value]
																	.checkElementMatch(
																			element,
																			true,
																			editor)) {
																if (value != currentValue)
																	this
																			.setValue(value);
																return;
															}
														}
													}
													this.setValue('',
															defaultLabel);
												}, this);
							},
							refresh : function() {
								if (!editor.activeFilter.check(style))
									this.setState(CKEDITOR.TRISTATE_DISABLED);
							}
						});
	}

	CKEDITOR.plugins.add('lineheight', {
		requires : 'richcombo',
		lang : 'zh,zh-cn,en',
		init : function(editor) {
			var config = editor.config;
			addCombo(editor, 'lineheight', 'size',
					editor.lang.lineheight.title, config.line_height,
					editor.lang.lineheight.title, config.lineHeight_style, 40);
		}
	});
})();
CKEDITOR.config.line_height = '1;1.5;2;2.5;3;3.5;4;4.5;5;5.5;6;6.5;7;7.5;8;8.5;9;9.5;10;10.5;11;11.5;12;12.5;13;13.5;14;14.5;15;15.5;16;16.5;17;17.5;18;18.5;19;19.5;20;20.5;21;21.5;22;22.5;23;23.5;24;24.5;25;25.5;26;26.5;27;27.5;28;28.5;29;29.5;30;30.5;31;31.5;32;32.5;33;33.5;34;34.5;35;35.5;36;36.5;37;37.5;38;38.5;39;39.5;40;40.5;41;41.5;42;42.5;43;43.5;44;44.5;45;45.5;46;46.5;47;47.5;48;48.5;49;49.5;50;50.5;51;51.5;52;52.5;53;53.5;54;54.5;55;55.5;56;56.5;57;57.5;58;58.5;59;59.5;60;60.5;61;61.5;62;62.5;63;63.5;64;64.5;65;65.5;66;66.5;67;67.5;68;68.5;69;69.5;70;70.5;71;71.5;72;72.5';
CKEDITOR.config.lineHeight_style = {
	element : 'span',
	styles : {
		'line-height' : '#(size)'
	},
	overrides : [ {
		element : 'line-height',
		attributes : {
			'size' : null
		}
	} ]
};