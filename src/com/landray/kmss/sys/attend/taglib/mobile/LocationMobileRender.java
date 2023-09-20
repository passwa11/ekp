package com.landray.kmss.sys.attend.taglib.mobile;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.landray.kmss.sys.attend.taglib.LocationTag;
import com.landray.kmss.web.taglib.xform.AbstractFieldElementTag;
import com.landray.kmss.web.taglib.xform.mobile.AbstractFieldElementMobileRender;

import net.sf.json.JSONObject;

public class LocationMobileRender extends AbstractFieldElementMobileRender {

	private LocationTag locationTag;

	public LocationMobileRender(PageContext pageContext, AbstractFieldElementTag fieldTag) {
		super(pageContext, fieldTag);
		this.locationTag = (LocationTag) fieldTag;
	}

	@Override
	protected void renderElementInEditingMode(StringBuffer results) throws JspException {
		results.append(generateElementHTML());
	}

	@Override
	protected void renderElementInViewMode(StringBuffer results) throws JspException {
		results.append(generateElementHTML());
	}

	@Override
	protected void renderElementInHiddenMode(StringBuffer results) throws JspException {
		results.append(generateElementHTML());
	}

	@Override
	protected void renderElementInReadOnlyMode(StringBuffer results) throws JspException {
		results.append(generateElementHTML());
	}

	private StringBuffer generateElementHTML() throws JspException {
		StringBuffer results = new StringBuffer();
		results.append("<div");
		prepareDojoType(results, "sys/attend/map/mobile/resource/js/Location");
		JSONObject props = acquireMappingDojoProps();
		prepareValidateProps(props);
		prepareDojoProperties(results, props);
		prepareStyle(results);
		prepareOtherAttributes(results);
		results.append("></div>");
		return results;
	}

	private JSONObject acquireMappingDojoProps() throws JspException {
		JSONObject props = new JSONObject();
		props.accumulate("id", locationTag.getId());
		props.accumulate("name", locationTag.getPropertyName());
		props.accumulate("propertyName", locationTag.getPropertyName());
		props.accumulate("propertyCoordinate", locationTag.getPropertyCoordinate());
		props.accumulate("value", acquireValue(locationTag.getPropertyName(), locationTag.getNameValue()));
		props.accumulate("coordinateValue",
				acquireValue(locationTag.getPropertyCoordinate(), locationTag.getCoordinateValue()));
		props.accumulate("propertyDetail", locationTag.getPropertyDetail());
		props.accumulate("detailValue",
				acquireValue(locationTag.getPropertyDetail(),
						locationTag.getDetailValue()));
		props.accumulate("radius", locationTag.getRadius());
		props.accumulate("propertyProvince", locationTag.getPropertyProvince());
		props.accumulate("provinceValue",
				acquireValue(locationTag.getPropertyProvince(),
						locationTag.getProvinceValue()));
		props.accumulate("propertyDistrict", locationTag.getPropertyDistrict());
		props.accumulate("districtValue",
				acquireValue(locationTag.getPropertyDistrict(),
						locationTag.getDistrictValue()));
		props.accumulate("propertyCity", locationTag.getPropertyCity());
		props.accumulate("cityValue",
				acquireValue(locationTag.getPropertyCity(),
						locationTag.getCityValue()));
		props.accumulate("isModify", locationTag.getIsModify());
		props.accumulate("defaultValue", locationTag.getDefaultValue());
		prepareAdditionDojoProps(props);
		return props;
	}

}
