/*!
 * KMS Template Javascript Library v1.0
 * Copyright 2010, Landray(http://www.landray.com.cn)
 * 
 * Requires:
 * [kms.js, jquery.js]
 * 
 * Author: 
 * yangf@landray.com.cn
 * Date: 2011/9/1 9:28
 * Version: v1.0
 */

( function(S) {

	var defaultConfig = {},

	// Template Cache
	templateCache = {},

	// start/end tag mark
	tagStartEnd = {
		'#' : 'start',
		'/' : 'end'
	},

	// Regexp Cache
	regexpCache = {}, getRegexp = function(regexp) {
		if (!(regexp in regexpCache)) {
			regexpCache[regexp] = new RegExp(regexp, 'ig');
		}
		return regexpCache[regexp];
	},

	// static string
	KS_TEMPL_STAT_PARAM = 'KS_TEMPL_STAT_PARAM', KS_TEMPL = 'KS_TEMPL', KS_DATA = 'KS_DATA_', KS_EMPTY = '', KS_AS = 'as',

	PREFIX = '");', SUFFIX = KS_TEMPL + '.push("',

	PARSER_SYNTAX_ERROR = 'KMS.Template: Syntax Error. ', PARSER_RENDER_ERROR = 'KMS.Template: Render Error. ',

	PARSER_PREFIX = 'var ' + KS_TEMPL + '=[],' + KS_TEMPL_STAT_PARAM
			+ '=false;with(', PARSER_MIDDLE = '||{}){try{' + KS_TEMPL + '.push("', PARSER_SUFFIX = '");}catch(e){'
			+ KS_TEMPL
			+ '=["'
			+ PARSER_RENDER_ERROR
			+ '" + e.message]}};return ' + KS_TEMPL + '.join("");',

	// build a static parser
	buildParser = function(templ) {
		var _parser, _empty_index;
		return S
				.trim(templ)
				.replace(getRegexp('[\r\t\n]'), ' ')
				.replace(getRegexp('(["\'])'), '\\$1')
				.replace(getRegexp('\{\{([#/]?)(?!\}\})([^}]*)\}\}'),
						function(all, expr, oper) {
							_parser = KS_EMPTY;
							// is an expression
						if (expr) {
							oper = S.trim(oper);
							_empty_index = oper.indexOf(' ');
							oper = _empty_index === -1 ? [ oper, '' ] : [
									oper.substring(0, oper.indexOf(' ')),
									oper.substring(oper.indexOf(' ')) ];
							for ( var i in Statements) {
								if (oper[0] !== i)
									continue;
								oper.shift();
								if (expr in tagStartEnd) {
									// get expression definition function/string
									var fn = Statements[i][tagStartEnd[expr]];
									_parser = S.isFunction(fn) ? String(fn
											.apply(
													this,
													S
															.trim(
																	oper
																			.join(
																					KS_EMPTY)
																			.replace(
																					getRegexp('\\\\([\'"])'),
																					'$1'))
															.split(/\s+/)))
											: String(fn
													.replace(
															getRegexp(KS_TEMPL_STAT_PARAM),
															oper
																	.join(
																			KS_EMPTY)
																	.replace(
																			getRegexp('\\\\([\'"])'),
																			'$1')));
								}
							}
						}

						// return array directly
						else {
							_parser = KS_TEMPL
									+ '.push('
									+ oper.replace(getRegexp('\\\\([\'"])'),
											'$1') + ');';
						}
						return PREFIX + _parser + SUFFIX;

					});
	},

	// convert any object to array
	toArray = function(args) {
		return [].slice.call(args);
	},

	// join any array to string by empty
	join = function(args) {
		return toArray(args).join(KS_EMPTY);
	},

	// expression
	Statements = {
		'if' : {
			start : 'if(' + KS_TEMPL_STAT_PARAM + '){',
			end : '}'
		},

		'else' : {
			start : '}else{'
		},

		'elseif' : {
			start : '}else if(' + KS_TEMPL_STAT_PARAM + '){'
		},

		// KISSY.each function wrap
		'each' : {
			start : function() {
				var args = toArray(arguments), _ks_value = '_ks_value', _ks_index = '_ks_index';
				if (args[1] === KS_AS && args[2]) {
					_ks_value = args[2] || _ks_value, _ks_index = args[3]
							|| _ks_index;
				}
				return 'jQuery.each(' + args[0] + ', function(' + _ks_index
						+ ', ' + _ks_value + '){';
			},
			end : '});'
		},

		// comments
		'!' : {
			start : '/*' + KS_TEMPL_STAT_PARAM + '*/'
		}
	},

	/**
	 * Template
	 * @param {String} templ template to be rendered.
	 * @param {Object} config configuration.
	 * @return {Object} return this for chain.
	 */
	Template = function(templ, config) {
		// S.mix(defaultConfig, config);
		if (!(templ in templateCache)) {
			// 引入core里面低版本jquery，手动实现获取当前时间方法
			var _ks_data = KS_DATA + (new Date()).getTime(), func, _parser = [ PARSER_PREFIX,
					_ks_data, PARSER_MIDDLE, buildParser(templ), PARSER_SUFFIX ];

			try {
				func = new Function(_ks_data, _parser.join(KS_EMPTY));
			} catch (e) {
				_parser[3] = PREFIX + SUFFIX + PARSER_SYNTAX_ERROR + ','
						+ e.message + PREFIX + SUFFIX;
				func = new Function(_ks_data, _parser.join(KS_EMPTY));
			}

			templateCache[templ] = {
				name : _ks_data,
				parser : _parser.join(KS_EMPTY),
				render : func
			};
		}
		return templateCache[templ];
	};

	Object.extend(Template, {
		/**
		 * Logging Compiled Template Codes
		 * @param {String} templ template string.
		 */
		log : function(templ) {
			if (templ in templateCache) {
				if ('js_beautify' in window) {
					S.log(js_beautify(templateCache[templ].parser, {
						indent_size : 4,
						indent_char : ' ',
						preserve_newlines : true,
						braces_on_own_line : false,
						keep_array_indentation : false,
						space_after_anon_function : true
					}), 'info');
				} else {
					S.log(templateCache[templ].parser, 'info');
				}
			} else {
				Template(templ, undefined);
				this.log(templ);
			}
		},

		/**
		 * add statement for extending template tags
		 * @param {String} statement tag name.
		 * @param {String} o extent tag object.
		 */
		addStatement : function(statement, o) {
			if (S.isString(statement) && S.isObject(o)) {
				Statements[statement] = o;
			}
		}

	});

	S.getTemplate = Template;
}(jQuery))