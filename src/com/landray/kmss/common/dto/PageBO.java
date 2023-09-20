package com.landray.kmss.common.dto;

import com.landray.kmss.common.rest.convertor.IPropertyConvertor;

/**
 * @Author 严明镜
 * @create 2020/10/26 10:46
 */
public class PageBO {
	private String property;
	private String title;

	private IPropertyConvertor convertor;
	/**
	 * 用于查询结果为非Model，而是Object[]时，指定对应的顺序
	 */
	private Integer queryListObjectArrayIndex;

	public PageBO(String property) {
		this.property = property;
	}

	public PageBO(String property, String title) {
		this.property = property;
		this.title = title;
	}

	public PageBO(String property, String title, Integer queryListObjectArrayIndex) {
		this.property = property;
		this.title = title;
		this.queryListObjectArrayIndex = queryListObjectArrayIndex;
	}

	public PageBO(String property,IPropertyConvertor convertor) {
	    this.property = property;
	    this.convertor = convertor;
    }

    public PageBO(String property,String title, IPropertyConvertor convertor) {
        this.property = property;
        this.title = title;
        this.convertor = convertor;
    }

    public PageBO(String property, String title, IPropertyConvertor convertor, Integer queryListObjectArrayIndex) {
	    this.property = property;
	    this.title = title;
	    this.convertor = convertor;
	    this.queryListObjectArrayIndex = queryListObjectArrayIndex;
    }


	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getQueryListObjectArrayIndex() {
		return queryListObjectArrayIndex;
	}

	public void setQueryListObjectArrayIndex(Integer queryListObjectArrayIndex) {
		this.queryListObjectArrayIndex = queryListObjectArrayIndex;
	}

    public IPropertyConvertor getConvertor() {
        return convertor;
    }

    public void setConvertor(IPropertyConvertor convertor) {
        this.convertor = convertor;
    }
}
