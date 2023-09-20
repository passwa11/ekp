package com.landray.kmss.sys.attend.taglib.pc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.landray.kmss.sys.attend.taglib.LocationTag;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.AbstractFieldElementTag;
import com.landray.kmss.web.taglib.xform.pc.AbstractFieldElementPcRender;

import net.sf.json.JSONObject;

public class LocationPcRender extends AbstractFieldElementPcRender {

	private LocationTag locationTag = null;

	public LocationPcRender(PageContext pageContext, AbstractFieldElementTag fieldTag) {
		super(pageContext, fieldTag);
		this.locationTag = (LocationTag) fieldTag;
	}

	@Override
	protected void prepareOnValueChange(StringBuffer results) throws JspException {
		return;
	}

	@Override
	protected void renderElementInEditingMode(StringBuffer results) throws JspException {
		results.append(generateEditHTML());
	}

	@Override
	protected void renderElementInViewMode(StringBuffer results) throws JspException {
		results.append(generateViewHTML());
	}

	@Override
	protected void renderElementInHiddenMode(StringBuffer results) throws JspException {
		results.append(generateViewHTML());
	}

	@Override
	protected void renderElementInReadOnlyMode(StringBuffer results) throws JspException {
		results.append(generateViewHTML());
	}

	private StringBuffer generateEditHTML() throws JspException {
		StringBuffer results = new StringBuffer();
		if (StringUtil.isNull(locationTag.getStyle())) {
			locationTag.setStyle("width:100%");
		}
		results.append("<div data-location-container=\""
				+ locationTag.getPropertyName() + "\" ");
		results.append("class=\"lui_location_container\" ");
		results.append("style=\"" + acquireStyle() + "\" ");
		results.append("></div>");
		if (locationTag.getRequired()) {
			results.append("<span class='txtstrong'>*</span>");
		}
		results.append(
				"<script>seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){");
		results.append("init( " + acquireProps() + " );");
		results.append("})</script>");
		return results;
	}

	private StringBuffer generateViewHTML() throws JspException {
		StringBuffer results = new StringBuffer();
		results.append("<div data-location-container=\""
				+ locationTag.getPropertyName() + "\" ");
		results.append(
				"style=\"" + acquireStyle() + ";display: inline-block;\" ");
		results.append("></div>");
		results.append("<script>seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){");
		results.append("init( " + acquireProps() + " );");
		results.append("})</script>");
		return results;
	}

	private JSONObject acquireProps() throws JspException {
		JSONObject props = new JSONObject();
		props.accumulate("id", locationTag.getId());
		props.accumulate("propertyName", locationTag.getPropertyName());
		props.accumulate("propertyCoordinate", locationTag.getPropertyCoordinate());
		props.accumulate("nameValue", acquireValue(locationTag.getPropertyName(), locationTag.getNameValue()));
		props.accumulate("coordinateValue",
				acquireValue(locationTag.getPropertyCoordinate(), locationTag.getCoordinateValue()));
		props.accumulate("showStatus", locationTag.getShowStatus());
		props.accumulate("subject", acquireSubject());
		props.accumulate("required", locationTag.getRequired());
		props.accumulate("validators", acquireValidate());
		props.accumulate("propertyDetail", locationTag.getPropertyDetail());
		props.accumulate("detailValue", acquireValue(
				locationTag.getPropertyDetail(), locationTag.getDetailValue()));
		props.accumulate("placeholder", locationTag.getPlaceholder());
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
		return props;
	}

}
