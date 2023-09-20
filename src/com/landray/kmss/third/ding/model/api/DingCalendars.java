package com.landray.kmss.third.ding.model.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;
import java.util.Map;

public class DingCalendars {

    private String id;
    private String summary;
    private String description;
    private DateAssist start;
    private DateAssist end;

    @JSONField(name = "isAllDay")
    private Boolean isAllDay;

    private RecurrenceAssist recurrence;
    private List<UserAssist> attendees;
    private LocationAssist location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<RemindersAssist> reminders;
    private OnlineMeetingInfoAssist onlineMeetingInfo;

    public DingCalendars() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getAllDay() {
        return isAllDay;
    }

    public void setAllDay(Boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    public String getDescription() {
        return description;
    }

    public void setIsAllDay(Boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    public Boolean getIsAllDay() {
        return isAllDay;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateAssist getStart() {
        return start;
    }

    public void setStart(DateAssist start) {
        this.start = start;
    }

    public DateAssist getEnd() {
        return end;
    }

    public void setEnd(DateAssist end) {
        this.end = end;
    }

    public RecurrenceAssist getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(RecurrenceAssist recurrence) {
        this.recurrence = recurrence;
    }

    public List<UserAssist> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<UserAssist> attendees) {
        this.attendees = attendees;
    }

    public LocationAssist getLocation() {
        return location;
    }

    public void setLocation(LocationAssist location) {
        this.location = location;
    }

    public List<RemindersAssist> getReminders() {
        return reminders;
    }

    public void setReminders(List<RemindersAssist> reminders) {
        this.reminders = reminders;
    }

    public OnlineMeetingInfoAssist getOnlineMeetingInfo() {
        return onlineMeetingInfo;
    }

    public void setOnlineMeetingInfo(OnlineMeetingInfoAssist onlineMeetingInfo) {
        this.onlineMeetingInfo = onlineMeetingInfo;
    }

    public static class OnlineMeetingInfoAssist{
       private String type;
        private String conferenceId;
        private String url;
        private Map extraInfo;

        public OnlineMeetingInfoAssist() {
        }

        public OnlineMeetingInfoAssist(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getConferenceId() {
            return conferenceId;
        }

        public void setConferenceId(String conferenceId) {
            this.conferenceId = conferenceId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map getExtraInfo() {
            return extraInfo;
        }

        public void setExtraInfo(Map extraInfo) {
            this.extraInfo = extraInfo;
        }
    }
    public static class RemindersAssist{
        private String method;
        private Integer minutes;

        public RemindersAssist() {
        }

        public RemindersAssist(String method, Integer minutes) {
            this.method = method;
            this.minutes = minutes;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Integer getMinutes() {
            return minutes;
        }

        public void setMinutes(Integer minutes) {
            this.minutes = minutes;
        }
    }
    public static class LocationAssist {

        private String displayName;

        public LocationAssist() {
        }

        public LocationAssist(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    public static class UserAssist {
        private String id;
        private String name;
        private String responseStatus;
        private boolean self;

        public UserAssist() {
        }

        public UserAssist(String id) {
            this.id = id;
        }

        public UserAssist(String id, String name, String responseStatus, boolean self) {
            this.id = id;
            this.name = name;
            this.responseStatus = responseStatus;
            this.self = self;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public boolean isSelf() {
            return self;
        }

        public void setSelf(boolean self) {
            this.self = self;
        }
    }

    public static class RecurrenceAssist{
        private PatternAssist pattern;
        private RangeAssist range;

        public RecurrenceAssist(PatternAssist pattern, RangeAssist range) {
            this.pattern = pattern;
            this.range = range;
        }

        public RecurrenceAssist() {
        }

        public PatternAssist getPattern() {
            return pattern;
        }

        public void setPattern(PatternAssist pattern) {
            this.pattern = pattern;
        }

        public RangeAssist getRange() {
            return range;
        }

        public void setRange(RangeAssist range) {
            this.range = range;
        }
    }

    public static class PatternAssist{
        private String type;
        private Integer dayOfMonth;
        private String daysOfWeek;
        private String index;
        private Integer interval;

        public PatternAssist() {
        }

        public PatternAssist(String type, Integer dayOfMonth, String daysOfWeek, String index, Integer interval) {
            this.type = type;
            this.dayOfMonth = dayOfMonth;
            this.daysOfWeek = daysOfWeek;
            this.index = index;
            this.interval = interval;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getDayOfMonth() {
            return dayOfMonth;
        }

        public void setDayOfMonth(Integer dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
        }

        public String getDaysOfWeek() {
            return daysOfWeek;
        }

        public void setDaysOfWeek(String daysOfWeek) {
            this.daysOfWeek = daysOfWeek;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public Integer getInterval() {
            return interval;
        }

        public void setInterval(Integer interval) {
            this.interval = interval;
        }
    }
    public static class RangeAssist{
        private String type;
        private String endDate;
        private Integer numberOfOccurrences;

        public RangeAssist() {
        }

        public RangeAssist(String type, String endDate, Integer numberOfOccurrences) {
            this.type = type;
            this.endDate = endDate;
            this.numberOfOccurrences = numberOfOccurrences;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public Integer getNumberOfOccurrences() {
            return numberOfOccurrences;
        }

        public void setNumberOfOccurrences(Integer numberOfOccurrences) {
            this.numberOfOccurrences = numberOfOccurrences;
        }
    }


    public static class DateAssist {
        private String date;
        private String dateTime;
        private String timeZone;

        public DateAssist() {
        }

        public DateAssist(String date) {
            this.date = date;
        }

        public DateAssist(String date, String dateTime) {
            this.date = date;
            this.dateTime = dateTime;
        }

        public DateAssist(String date, String dateTime, String timeZone) {
            this.date = date;
            this.dateTime = dateTime;
            this.timeZone = timeZone;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }
    }

}
