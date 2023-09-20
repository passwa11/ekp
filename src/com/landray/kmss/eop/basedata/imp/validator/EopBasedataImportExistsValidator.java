package com.landray.kmss.eop.basedata.imp.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.HQLBuilderImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportColumn;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportReference;
import com.landray.kmss.eop.basedata.imp.interfaces.IEopBasedataImportValidator;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class EopBasedataImportExistsValidator extends IEopBasedataImportValidator{

	@Override
	public Boolean validate(EopBasedataImportContext ctx,EopBasedataImportColumn col, List<Object> data) throws Exception {
		String type = col.getFdType();
		//如果该字段不是必填且值为空，则不校验
		if(StringUtil.isNull((String) data.get(col.getFdColumn()))){
			return true;
		}
		//如果是对象类型字段，校验对象是否已存在
		if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_OBJECT.equals(type)||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_LIST.equals(type)){
			int length = 0;
			String value = (String) data.get(col.getFdColumn());
			for(String label:value.split(";")){
				if(StringUtil.isNull(label)){
					continue;
				}
				length++;
			}
			if(length==0){
				return false;
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setModelName(ctx.getFdModelName());
			hqlInfo.setSelectBlock("model");
			hqlInfo.setFromBlock(col.getFdRel().getFdReference()+" model");
			getRelHqlInfo(ctx, col, hqlInfo,  data);
			String hql = new HQLBuilderImp().buildQueryHQL(hqlInfo).getHql();
			Query query = EopBasedataImportUtil.getBaseDao().getHibernateSession().createQuery(hql);
			for(HQLParameter param:hqlInfo.getParameterList()){
				if(param.getValue() instanceof Collection){
					query.setParameterList(param.getName(), (Collection) param.getValue());
				}else{
					query.setParameter(param.getName(), param.getValue());
				}
			}
			//如果查询到的已存在的对象数量小于导入时填写的数量，则说明导入的部分对象不存在，不允许导入
			return query.list().size()>=length;
		}
		//如果是枚举类型字段，校验对应枚举值是否存在
		else if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_ENUMS.equals(type)||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_RADIO.equals(type)){
			String labels = (String) data.get(col.getFdColumn());
			for(String label:labels.split(";")){
				if(StringUtil.isNull(label)){
					continue;
				}
				String value = "";
				try{
					value = EnumerationTypeUtil.getColumnValueByLabel(col.getFdProperty().getEnumType(), label.trim());
				}catch(Exception e){
					return false;
				}
				if(StringUtil.isNull(value)){
					return false;
				}
			}
		}
		return true;
	}
	
	private void getRelHqlInfo(EopBasedataImportContext context,EopBasedataImportColumn col,HQLInfo hqlInfo,List<Object> rowData) throws Exception{
		EopBasedataImportReference rel = col.getFdRel();
		StringBuffer where = new StringBuffer();
		if(StringUtil.isNotNull(hqlInfo.getWhereBlock())){
			where.append(hqlInfo.getWhereBlock());
		}else{
			where.append("1=1 ");
		}
		int paramIndex = 0;
		if(StringUtil.isNotNull((String)rowData.get(col.getFdColumn()))){
			List<String> data = new ArrayList<String>();
			for(String str:((String)rowData.get(col.getFdColumn())).split(";")){
				if(StringUtil.isNotNull(str)){
					data.add(str);
				}
			}
			if(ArrayUtil.isEmpty(data)){
				where.append("and 1=2 ");
			}else{
				String key=rel.getFdKey();
				if(key.indexOf(";")>-1){//多个匹配条件
					String[] keys=key.split(";");
					int pIndex=paramIndex++;
					for(int i=0,len=keys.length;i<len;i++){
						if(i==0){
							where.append(" and (");
						}else{
							where.append(" or ");
						}
						where.append(" model.").append(keys[i]).append(" in(:param").append(pIndex).append(") ");
						if(i==len-1){
							where.append(" )");
						}
					}
					hqlInfo.setParameter("param"+pIndex,data);
				}else{
					where.append("and model.").append(rel.getFdKey()).append(" in(:param").append(++paramIndex).append(") ");
					hqlInfo.setParameter("param"+paramIndex,data);
				}
			}
		}else{
			where.append("and 1=2 ");
		}
		//如果有额外条件
		if(rel.getFdFields()!=null){
			for(Map<String,Object> field:rel.getFdFields()){
				if(EopBasedataImportReference.FIELD_TYPE_STATIC.equals(field.get("type"))){
					where.append(" and model.").append(field.get("name")).append("=:param").append(++paramIndex);
					hqlInfo.setParameter("param"+paramIndex,field.get("value"));
				}else if(EopBasedataImportReference.FIELD_TYPE_REF.equals(field.get("type"))){
					String relField = (String) field.get("rel-column");
					EopBasedataImportContext ctx = EopBasedataImportUtil.getImportContext(rel.getFdReference());
					EopBasedataImportColumn col_ = ctx.getColumnByProperty(relField);//引用对象的字段
					if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_LIST.equals(col_.getFdType())) {//多选字段，需要设置join
						hqlInfo.setJoinBlock("left join model."+relField+" relField ");
						if("fdCompanyList".equals(col_.getFdName())) {//公司，需要查询公共的数据
							where.append(" and (relField.").append(field.get("name")).append(" in(:param").append(++paramIndex).append(") or relField.").append(field.get("name")).append(" is null)");
						}else {
							where.append(" and relField.").append(field.get("name")).append(" in(:param").append(++paramIndex).append(")");
						}
					}else {
						where.append(" and model.").append(field.get("rel-column")).append(".").append(field.get("name")).append(" in(:param").append(++paramIndex).append(")");
					}
					String val = (String) rowData.get(col_.getFdColumn());
					hqlInfo.setParameter("param"+paramIndex,Arrays.asList(val.split(";")));
				}
			}
		}
		hqlInfo.setWhereBlock(where.toString());
	}
	
}
