package com.landray.kmss.common.rest.convertor;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dto.PageVO;

/**
 * @ClassName: PropertyConvertorContext
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-11-18 20:03
 * @Version: 1.0
 */
public class PropertyConvertorContext {

    private PageVO.ColumnInfo columnInfo;

    private Object value;

    private String convertorProps;

    private RequestContext requestContext;

    public PageVO.ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public void setColumnInfo(PageVO.ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getConvertorProps() {
        return convertorProps;
    }

    public void setConvertorProps(String convertorProps) {
        this.convertorProps = convertorProps;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }
}
