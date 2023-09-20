/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.util.DateUtil;

/**
 * 时间范围
 * 
 * @author 龚健
 * @see
 */
public class TimeRange implements Comparable<TimeRange> {
	private long startTime = 0;
	private long endTime = DateUtil.DAY - 1;
	private Date date=null;

	public TimeRange(long startTime, long endTime) {
		this.startTime = startTime < 0 ? 0 : startTime;
		this.endTime = endTime < 0 ? DateUtil.DAY : endTime;
	}
	
	public TimeRange(long startTime, long endTime,Date date) {
		this.startTime = startTime < 0 ? 0 : startTime;
		this.endTime = endTime < 0 ? DateUtil.DAY : endTime;
		this.date=date;
	}

	public TimeRange(Long start, Long end) {
		this(start == null ? 0 : start.longValue(), end == null ? -1 : end
				.longValue());
	}
	
	public TimeRange(Long start, Long end,Date date) {
		this(start == null ? 0 : start.longValue(), end == null ? -1 : end
				.longValue(),date);
	}

	/**
	 * 获取当前范围与指定范围之间的交集范围，若没有则返回空
	 * 
	 * @param other
	 * @return 当前范围与指定范围之间的交集范围
	 */
	public TimeRange intersect(TimeRange other) {
		if (isIntersect(other)) {
			long startToUse = Math.max(startTime, other.startTime);
			long endToUse = Math.min(endTime, other.endTime);

			return new TimeRange(startToUse, endToUse);
		}
		return null;
	}

	/**
	 * 从当前范围中剔除掉指定范围，返回剩下的时间范围
	 * 
	 * @param other
	 * @return 剔除后的时间范围
	 */
	public List<TimeRange> exclude(TimeRange other) {
		List<TimeRange> ranges = new ArrayList<TimeRange>();
		if (!isIntersect(other)) {
			ranges.add(this);
		} else if (contains(other.startTime)) {
			ranges.add(new TimeRange(startTime, other.startTime));

			if (contains(other.endTime)) {
				ranges.add(new TimeRange(other.endTime, endTime));
			}
		} else if (contains(other.endTime)) {
			ranges.add(new TimeRange(other.endTime, endTime));
		} else if (other.startTime < this.startTime
				&& other.endTime > this.endTime) {
			ranges.add(new TimeRange(0, 0));
		} else {
			ranges.add(this);
		}
		return ranges;
	}

	/**
	 * 以当前范围为基准，合并指定时间范围，若指定时间范围与当前范围没有交集，则不合并，并返回空。
	 * 
	 * @param other
	 * @return
	 */
	public TimeRange merger(TimeRange other) {
		if (isIntersect(other)) {
			long startToUse = Math.min(startTime, other.startTime);
			long endToUse = Math.max(endTime, other.endTime);

			return new TimeRange(startToUse, endToUse);
		}
		return null;
	}

	/**
	 * 与指定时间范围是否有交集
	 * 
	 * @param other
	 * @return
	 */
	private boolean isIntersect(TimeRange other) {
		return !(other.startTime > endTime || other.endTime < startTime);
	}

	public boolean contains(Date timeToCheck) {
		return contains(DateUtil.getTimeNubmer(timeToCheck));
	}

	public boolean contains(long timeToCheck) {
		return startTime <= timeToCheck && timeToCheck <= endTime;
	}

	public long getCapacity() {
		return endTime - startTime;
	}

	@Override
	public int compareTo(TimeRange other) {
		if (startTime == other.startTime) {
			return endTime <= other.endTime ? -1 : 1;
		}
		return startTime < other.startTime ? -1 : 1;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (endTime ^ (endTime >>> 32));
		result = prime * result + (int) (startTime ^ (startTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TimeRange other = (TimeRange) obj;
		if (endTime != other.endTime) {
			return false;
		}
		if (startTime != other.startTime) {
			return false;
		}
		return true;
	}

}
