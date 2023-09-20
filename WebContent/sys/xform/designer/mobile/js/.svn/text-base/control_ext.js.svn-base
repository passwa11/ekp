/**
 * 
 */
function Designer_Control_JSP_FillMobileValue(){
	var pcOptions = this.__pcOptions;
	if (pcOptions == null) return;
	var $pcDomElement = $(pcOptions.domElement);
	var $mobileDomElement = $(this.options.domElement);
	var attrName = "mobileView";
	var inputSelector = "input[type='hidden']";
	$mobileDomElement.attr(attrName,$pcDomElement.attr(attrName));
	$mobileDomElement.find(inputSelector).val($pcDomElement.find(inputSelector).val());
}

function Designer_Control_Validator_FillMobileValue(){
	var pcOptions = this.__pcOptions;
	if (pcOptions == null) return;
	var $pcDomElement = $(pcOptions.domElement);
	var $mobileDomElement = $(this.options.domElement);
	$mobileDomElement.attr("param",$pcDomElement.attr("param"));
	$mobileDomElement.attr("varIds",$pcDomElement.attr("param"));
	$mobileDomElement.attr("fd_values",$pcDomElement.attr("fd_values"));
	_Designer_Control_Default_FillMobileValue();
}