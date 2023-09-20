package com.landray.kmss.sys.ui.taglib.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * 该类代表输出一个数据对象子项的一个字段下的拥有validators属性下的一个validator 。
 * 
 * 类型为pattern时 value值作为 "regexp"输出
 * 
 *
 */
@SuppressWarnings("serial")
public class PropertyValidatorTag extends BaseTag {
	private static final String KEY_NAME_MIN = "min";
	private static final String KEY_NAME_MAX = "max";
	private static final String KEY_NAME_INCLUSIVE = "inclusive";
	private static final String KEY_NAME_INTEGER = "integer";
	private static final String KEY_NAME_FRACTION = "fraction";
	private static final String KEY_NAME_FLAG = "flag";
	private static final String KEY_NAME_REGEXP = "regexp";

	private static final String TYPE_SIZE = "size";
	private static final String TYPE_DECIMAL_MIN = "decimalMin";
	private static final String TYPE_DECIMAL_MAX = "decimalMax";
	private static final String TYPE_DIGITS = "digits";
	private static final String TYPE_PATTERN = "pattern";

	private static final String[] COMPLEX_TYPES = { TYPE_SIZE, TYPE_DECIMAL_MIN,
			TYPE_DECIMAL_MAX,
			TYPE_DIGITS, TYPE_PATTERN
	};
	private static final int COMPLEX_TYPES_LENGTH = COMPLEX_TYPES.length;

	private String type = null;
	private Object value = null;

	// size 类型
	private Integer min = null;
	// size类型
	private Integer max = null;
	// decimalMax decimalMin类型
	private Boolean inclusive = null;
	// digits类型
	private Integer integer = null;
	// digits类型
	private Integer fraction = null;
	// pattern类型的忽略大小写
	private String flag = null;

	@Override
	public void release() {
		type = null;
		value = null;
		min = null;
		max = null;
		inclusive = null;
		integer = null;
		fraction = null;
		flag = null;

		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}


	@Override
	public int doEndTag() throws JspException {
		try {
			Tag parent = getParent();
			if (parent instanceof MapParentTagElement) {
				MapParentTagElement mapParentTagElement = (MapParentTagElement) parent;

				if (type != null && hasValue()) {
					if (isComplexType()) {
						// 复杂验证器类型
						Map<String, Object> valuesMap = new HashMap<String, Object>();

						if (value != null) {
							if (TYPE_PATTERN.equals(type)) {
								// 类型为pattern时 value值作为 "regexp"输出
								valuesMap.put(KEY_NAME_REGEXP, value);
							} else {
								valuesMap.put(ResponseConstant.KEY_NAME_VALUE,
										value);
							}

						}
						if (min != null) {
							valuesMap.put(KEY_NAME_MIN, min);
						}
						if (max != null) {
							valuesMap.put(KEY_NAME_MAX, max);
						}
						if (inclusive != null) {
							valuesMap.put(KEY_NAME_INCLUSIVE, inclusive);
						}
						if (integer != null) {
							valuesMap.put(KEY_NAME_INTEGER, integer);
						}
						if (fraction != null) {
							valuesMap.put(KEY_NAME_FRACTION, fraction);
						}
						if (flag != null) {
							valuesMap.put(KEY_NAME_FLAG, flag);
						}
						mapParentTagElement.addKeyValue(type, valuesMap);
					} else {
						// 简单验证器类型
						if (value != null) {
							mapParentTagElement.addKeyValue(type, value);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}


	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Boolean getInclusive() {
		return inclusive;
	}

	public void setInclusive(Boolean inclusive) {
		this.inclusive = inclusive;
	}

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public Integer getFraction() {
		return fraction;
	}

	public void setFraction(Integer fraction) {
		this.fraction = fraction;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	private boolean hasValue() {
		return value != null || min != null || max != null || inclusive != null
				|| integer != null || fraction != null || flag != null;
	}

	private boolean isComplexType() {
		for (int i = 0; i < COMPLEX_TYPES_LENGTH; i++) {
			if (COMPLEX_TYPES[i].equals(type)) {
				return true;
			}
		}

		return false;
	}

}
