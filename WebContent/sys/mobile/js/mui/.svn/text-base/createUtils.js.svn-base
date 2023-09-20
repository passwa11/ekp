/**
 * 提供create函数构建dojo组件
 */
define(['dojo/_base/lang','dojo/_base/array', 'dojo/json'], function(lang, array, json){
	
	/**
	 * 从对象中移除指定属性
	 * @param props 要处理的对象
	 * @param exclude 要移除的属性列表(Array)
	 */
	function omit(props, exclude){
		var cloneProps =  lang.clone(props);
		if(exclude && exclude.length > 0){
			array.forEach(exclude, function(key){
				if(cloneProps[key]){
					delete cloneProps[key];
				}
			});
		}
		return cloneProps;
	}
	
	/**
	 * 将对象转为data-dojo-props可以使用的json字符串
	 */
	function stringify(obj){
		if(!lang.isObject(obj)){
			return;
		}
		var str = json.stringify(obj);
		str = str.replace(/\"/g,'\'');
		return str
	}
	
	/**
	 * 根据传递的属性构建dojo组件的html字符串
	 */
	function createTemplate(tag, props, children){
		tag = tag || 'div';
		props = props || {};
		var html = '<' + tag + ' ';
		// 处理data-dojo-type
		if(props.dojoType){
			html += ' data-dojo-type="' + props.dojoType + '" ';
		}
		// 处理data-dojo-mixins
		var mixins = [];
		if(props.dojoMixins){
			if(typeof(props.dojoMixins) === 'string'){
				mixins.push(props.dojoMixins);
			}else{
				mixins = props.dojoMixins;
			}
		}
		if(mixins.length > 0){
			html += ' data-dojo-mixins="' + mixins.join(',') + '" ';
		}
		// 处理data-dojo-props
		if(props.dojoProps){
			html += " data-dojo-props=\"" + (function(){
				var dojoProps = props.dojoProps;
				var propsArray = [];
				for(var prop in dojoProps){
					var value = typeof(dojoProps[prop]) === 'string' ? 
							( /^\$\$.*\$\$$/.test(dojoProps[prop]) ? dojoProps[prop].replace(/^\$\$/,'').replace(/\$\$$/,'') :  ("'" + dojoProps[prop] + "'") )
							: (lang.isObject(dojoProps[prop]) ? stringify(dojoProps[prop]) : dojoProps[prop]);
					propsArray.push(prop + ':' + value);
				}
				return propsArray.join(',');
			})() + "\" ";
		}
		// 处理className
		if(props.className){
			html += ' class="' + props.className + '" ';
		}
		// 处理其他props
		var otherProps = omit(props, ['dojoType','dojoMixins','dojoProps','className']);
		for(var p in otherProps){
			html += ' ' + p + '="' + otherProps[p] + '" ';
		}
		html += ' >';
		// 处理子组件
		if(children){
			if(typeof(children) === 'string'){
				html += children;
			}else{
				html += children.join(' ');
			}
		}
		html += ' </' + tag + '>';
//		console.log(html)
		
		return html
	}
	
	
	return {
		createTemplate: createTemplate
	};
});