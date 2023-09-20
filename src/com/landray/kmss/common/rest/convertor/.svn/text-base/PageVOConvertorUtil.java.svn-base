package com.landray.kmss.common.rest.convertor;

import com.landray.kmss.common.dto.PageVO;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: PageVOConvertorUtil
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-11-19 1:36
 * @Version: 1.0
 */
public class PageVOConvertorUtil {
    
    /**
     * @Method buildColumnInfos
     * @Version  1.0
     * @Description
     * @param props
     * @Exception
     * @Date 2020-11-19 1:45
     */
    public static Set<PageVO.ColumnInfo> buildColumnInfos(List<String> props) {
        Set<PageVO.ColumnInfo> columnInfoSet = new LinkedHashSet<>();
        for (String prop : props) {
            String[] propertyInfo = prop.split(":");
            PageVO.ColumnInfo columnInfo = new PageVO.ColumnInfo(propertyInfo[0]);
            if (propertyInfo.length > 1) {
                columnInfo.setTitle(propertyInfo[1]);
            }
            columnInfoSet.add(columnInfo);
        }
        columnInfoSet.add(new PageVO.ColumnInfo("fdId"));
        return columnInfoSet;
    }

}
