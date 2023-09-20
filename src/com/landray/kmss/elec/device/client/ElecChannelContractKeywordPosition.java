package com.landray.kmss.elec.device.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
*@author yucf
*@date  2020年1月7日
*@Description              合同关键字位置信息
*/

public class ElecChannelContractKeywordPosition implements IElecChannelRequestMessage, IElecChannelResponseMessage {

	private static final long serialVersionUID = 1L;
	
	//关键字
	private String word;
	private List<Position> positions;
	
	public ElecChannelContractKeywordPosition(String word) {
		super();
		this.word = word;
	}
	
	public ElecChannelContractKeywordPosition() {
		super();
	}

	public String getWord() {
		return word;
	}

	public ElecChannelContractKeywordPosition setWord(String word) {
		this.word = word;
		return this;
	}
	
	public ElecChannelContractKeywordPosition addPosition(Position positoin){
		if(CollectionUtils.isEmpty(positions)){
			this.positions = new ArrayList<>();
		}
		
		this.positions.add(positoin);
		return this;
	}

	public List<Position> getPositions() {
		return positions;
	}


	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
	//签章的方位
	static public enum DirectionEnum {
		TOP, BOTTOM, LEFT, RIGHT, CENTER;
	}

	static public class Position implements Serializable {

		private static final long serialVersionUID = 1L;

		//关键字所在文档页码
		private Integer pageNum;
		
		//标签x轴位置(偏移量)
		private Number xAxis;
		//标签y轴位置
		private Number yAxis;
		
		//签章方位
		private DirectionEnum direction;
		
		//对应上传的第几份文档
		private Integer docOrder;	
		
		//当前页码下第几个关键字(可能同1页中有多个一样的关键字)
		private Integer index;

		public Integer getPageNum() {
			return pageNum;
		}
		
		public Position setPageNum(Integer pageNum) {
			this.pageNum = pageNum;
			return this;
		}

		public Number getXAxis() {
			return xAxis;
		}

		public Position setXAxis(Number xAxis) {
			this.xAxis = xAxis;
			return this;
		}

		public Number getYAxis() {
			return yAxis;
		}

		public Position setYAxis(Number yAxis) {
			this.yAxis = yAxis;
			return this;
		}

		public Integer getDocOrder() {
			return docOrder;
		}

		public Position setDocOrder(Integer docOrder) {
			this.docOrder = docOrder;
			return this;
		}
		

		public DirectionEnum getDirection() {
			return direction;
		}

		public Position setDirection(DirectionEnum direction) {
			this.direction = direction;
			return this;
		}

		public Integer getIndex() {
			return index;
		}

		public Position setIndex(Integer index) {
			this.index = index;
			return this;
		}
		
		@Override
		public String toString() {
			return "Position [pageNum=" + pageNum + ", xAxis=" + xAxis
					+ ", yAxis=" + yAxis + ", direction=" + direction
					+ ", docOrder=" + docOrder + "]";
		}
	}

	@Override
	public String toString() {
		return "ElecChannelContractKeywordPosition [word=" + word
				+ ", positions=" + positions + "]";
	}
}
