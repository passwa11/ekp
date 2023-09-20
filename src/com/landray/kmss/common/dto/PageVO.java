package com.landray.kmss.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.landray.kmss.common.rest.convertor.IPropertyConvertor;
import com.landray.kmss.sys.profile.model.SysListShow;
import com.landray.kmss.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 返回给前端的列表数据
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PageVO {
	/**
	 * 所有列名及列宽度的信息
	 */
	private List<ColumnInfo> columns = new ArrayList<>();
	/**
	 * 需要在列表视图显示的列，以;分割
	 */
	private String props;
	/**
	 * 每一行数据的信息
	 */
	private List<List<SigleFild>> datas = new ArrayList<>();

	/**
	 * 分页信息
	 */
	private PageInfo page = new PageInfo();

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public List<List<SigleFild>> getDatas() {
		return datas;
	}

	public void setDatas(List<List<SigleFild>> datas) {
		this.datas = datas;
	}

	public PageInfo getPage() {
		return page;
	}

	public void setPaging(int currentPage, int pageSize, int totalSize) {
		this.page.setCurrentPage(currentPage);
		this.page.setPageSize(pageSize);
		this.page.setTotalSize(totalSize);
	}

	/**
	 * 列信息
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class ColumnInfo {
		/**
		 * 显示文字
		 */
		private String title;
		/**
		 * 字段名
		 */
		private String property;

        /**
         * 转换器
         */
        @JsonIgnore
		private IPropertyConvertor propertyConvertor;

        @JsonIgnore
		private String convertorProps;

		/**
		 * 标题宽度
		 */
		private String width;
		/**
		 * 类型
		 */
		private String type;
		/**
		 * 枚举键值对定义
		 */
		private List<EnumValueLabel> enumValues;

		/**
		 * 用于获取非后台配置的显示列信息
		 */
		@JsonIgnore
		private PageBO extraFildsBO;

		public ColumnInfo(String property) {
			this.property = property;
		}

        public ColumnInfo(String title, String property, IPropertyConvertor propertyConvertor) {
            this.title = title;
            this.property = property;
            this.propertyConvertor = propertyConvertor;
        }

        public ColumnInfo(String title, String property, List<EnumValueLabel> enumValues) {
			this.title = title;
			this.property = property;
			this.enumValues = enumValues;
		}

		public ColumnInfo(SysListShow listShow, List<EnumValueLabel> enumValues) {
			this.title = ResourceUtil.getString(listShow.getFdMessagekey());
			this.property = listShow.getFdField();
			this.width = listShow.getFdWidth();
			this.enumValues = enumValues;
		}

        public IPropertyConvertor getPropertyConvertor() {
            return propertyConvertor;
        }

        public void setPropertyConvertor(IPropertyConvertor propertyConvertor) {
            this.propertyConvertor = propertyConvertor;
        }

        public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<EnumValueLabel> getEnumValues() {
			return enumValues;
		}

		public void setEnumValues(List<EnumValueLabel> enumValues) {
			this.enumValues = enumValues;
		}

		public PageBO getExtraFildsBO() {
			return extraFildsBO;
		}

		public void setExtraFildsBO(PageBO extraFildsBO) {
			this.extraFildsBO = extraFildsBO;
		}

        public String getConvertorProps() {
            return convertorProps;
        }

        public void setConvertorProps(String convertorProps) {
            this.convertorProps = convertorProps;
        }

        @Override
		public boolean equals(Object o) {
			if (this == o) {
                return true;
            }
			if (o == null || getClass() != o.getClass()) {
                return false;
            }
			ColumnInfo that = (ColumnInfo) o;
			return Objects.equals(property, that.property);
		}

		@Override
		public int hashCode() {
			return Objects.hash(property);
		}

		public static class EnumValueLabel {
			private String value;
			private String label;

			public EnumValueLabel(String value, String label) {
				this.value = value;
				this.label = label;
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}

			public String getLabel() {
				return label;
			}

			public void setLabel(String label) {
				this.label = label;
			}
		}
	}

	/**
	 * 一行数据中的单个字段信息
	 */
	public static class SigleFild {
		/**
		 * 字段名
		 */
		private String property;
		/**
		 * 字段值
		 */
		private Object value;

		public SigleFild(String property, Object value) {
			this.property = property;
			this.value = value;
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}

	public static class PageInfo {
		/**
		 * 当前页
		 */
		private int currentPage;
		/**
		 * 分页大小
		 */
		private int pageSize;
		/**
		 * 数据总条数
		 */
		private int totalSize;

		public int getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public int getTotalSize() {
			return totalSize;
		}

		public void setTotalSize(int totalSize) {
			this.totalSize = totalSize;
		}
	}
}
