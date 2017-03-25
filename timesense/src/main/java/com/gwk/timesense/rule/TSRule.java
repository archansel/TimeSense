package com.gwk.timesense.rule;

import java.util.Calendar;
import java.util.Date;

/***
 * Rule object used in TimeSense detection.
 *
 * @author Anselmus KA Kurniawan
 * @version 0.1
 */
public class TSRule {

    public static final String TS_RULE_NAME_MORNING = "TS_RULE_NAME_MORNING";
    public static final String TS_RULE_NAME_AFTERNOON = "TS_RULE_NAME_AFTERNOON";
    public static final String TS_RULE_NAME_EVENING = "TS_RULE_NAME_EVENING";
    public static final String TS_RULE_NAME_NIGHT = "TS_RULE_NAME_NIGHT";

    private String name;
    private Date startTime;
    private Date endTime;

    /***
     * Constructor.
     *
     * @param name used as rule identifier
     * @param startTime start time of the rule (included in calculation)
     * @param endTime end time of the rule (excluded in calculation)
     */
    public TSRule(String name, Date startTime, Date endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /***
     * @return TSRule Generated rule for TS_RULE_NAME_MORNING
     */
    public static TSRule morning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date start = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 11);
        Date end = cal.getTime();

        return new TSRule(TS_RULE_NAME_MORNING, start, end);
    }

    /***
     * @return TSRule Generated rule for TS_RULE_NAME_AFTERNOON
     */
    public static TSRule afternoon() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date start = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 17);
        Date end = cal.getTime();

        return new TSRule(TS_RULE_NAME_AFTERNOON, start, end);
    }

    /***
     * @return TSRule Generated rule for TS_RULE_NAME_EVENING
     */
    public static TSRule evening() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date start = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 21);
        Date end = cal.getTime();

        return new TSRule(TS_RULE_NAME_EVENING, start, end);
    }

    /***
     * @return TSRule Generated rule for TS_RULE_NAME_NIGHT
     */
    public static TSRule night() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 21);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date start = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 4);
        Date end = cal.getTime();

        return new TSRule(TS_RULE_NAME_NIGHT, start, end);
    }

    /***
     * @return String Getter for name property
     */
    public String getName() {
        return name;
    }

    /***
     * @param name Set name property with this value
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * @return Date Getter for start time property
     */
    public Date getStartTime() {
        return startTime;
    }

    /***
     * @param startTime Set start time property with this value
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /***
     * @return Date Getter for end time property
     */
    public Date getEndTime() {
        return endTime;
    }

    /***
     * @param endTime Set end time property with this value
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TSRule) {
            TSRule param = (TSRule)obj;

            Boolean isNameEqual = this.name.equals(param.name);

            // Hour compare based on http://stackoverflow.com/a/7676307
            int hour1, hour2;

            hour1 = (int) (this.startTime.getTime() % (24*60*60*1000L));
            hour2 = (int) (param.startTime.getTime() % (24*60*60*1000L));
            Boolean isStartTimeEqual = (hour1 - hour2) == 0;

            hour1 = (int) (this.endTime.getTime() % (24*60*60*1000L));
            hour2 = (int) (param.endTime.getTime() % (24*60*60*1000L));
            Boolean isEndTimeEqual = (hour1 - hour2) == 0;

            return isNameEqual && isStartTimeEqual && isEndTimeEqual;
        }
        return false;
    }
}
