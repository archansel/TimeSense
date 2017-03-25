package com.gwk.timesense;

import com.gwk.timesense.configuration.TSConfiguration;
import com.gwk.timesense.listener.TSListener;
import com.gwk.timesense.rule.TSRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/***
 * TimeSense singleton class.
 *
 * @author Anselmus KA Kurniawan
 * @version 0.1
 */
public class TimeSense {

    private TSConfiguration configuration;
    private ArrayList<TSListener> genericListeners;
    private HashMap<String, ArrayList<TSListener>> specificListeners;

    /***
     * Singleton.
     */
    private static TimeSense instance;
    private TimeSense() {
        this.configuration = new TSConfiguration();
        this.genericListeners = new ArrayList<TSListener>();
        this.specificListeners = new HashMap<String, ArrayList<TSListener>>();
    }
    public static TimeSense getInstance() {
        if (instance == null) instance = new TimeSense();
        return instance;
    }

    /***
     * @return TSConfiguration Getter for configuration property
     */
    private TSConfiguration getConfiguration() {
        return configuration;
    }

    /***
     * @param configuration Set configuration property with this value
     */
    public void setConfiguration(TSConfiguration configuration) {
        this.configuration = configuration;
    }

    /***
     * @param rules Rules to be added to existing rules
     */
    public void addRules(ArrayList<TSRule> rules) {
        for (TSRule rule: rules) {
            this.addRule(rule);
        }
    }

    /***
     * @param rule Rule to be added to existing rules
     */
    public void addRule(TSRule rule) {
        this.configuration.addRule(rule);
    }

    /***
     * Add new rule using each TSRule property
     *
     * @param ruleName Rule name to be added
     * @param startTime Start time for rule to be added
     * @param endTime End time for rule to be added
     */
    public void addRule(String ruleName, Date startTime, Date endTime) {
        TSRule rule = new TSRule(ruleName, startTime, endTime);
        this.addRule(rule);
    }

    /***
     * Update existing rule with new start time and end time
     *
     * @param ruleName Rule name to be updated
     * @param startTime Start time change with this value
     * @param endTime End time change with this value
     * @return Boolean whether update success or not
     */
    public Boolean updateRule(String ruleName, Date startTime, Date endTime) {
        return this.configuration.updateRule(ruleName, startTime, endTime);
    }

    /***
     * @param ruleName Remove rule with this name from configuration
     */
    public void removeRule(String ruleName) {
        this.configuration.removeRule(ruleName);
    }

    /***
     * @param ruleName Get array of Date (start time, end time) from rule name
     * @return ArrayList<Date>
     */
    public ArrayList<Date> getTimeRange(String ruleName) {
        TSRule rule = this.configuration.findRule(ruleName);
        ArrayList<Date> dates = new ArrayList<Date>();

        dates.add(rule.getStartTime());
        dates.add(rule.getEndTime());

        return dates;
    }

    /***
     * Add listener for all rules in TimeSense
     *
     * @param listener TSListener to be added
     */
    public void addListener(TSListener listener) {
        this.genericListeners.add(listener);
    }

    /***
     * Add listener to specific rule in TimeSense
     *
     * @param ruleName Rule name identifier
     * @param listener TSListener to be added
     */
    public void addListener(String ruleName, TSListener listener) {
        ArrayList<TSListener> listeners = this.specificListeners.get(ruleName);
        if (listeners == null) listeners = new ArrayList<TSListener>();
        listeners.add(listener);
    }

    /***
     * Remove all listeners
     */
    public void removeListeners() {
        this.genericListeners = new ArrayList<TSListener>();
        this.specificListeners = new HashMap<String, ArrayList<TSListener>>();
    }

    /***
     * Remove specific listener from TimeSense
     *
     * @param ruleName Rule name identifier
     */
    public void removeListener(String ruleName) {
        this.specificListeners.remove(ruleName);
    }

    /***
     * Remove specific listener for specific rule
     *
     * @param ruleName Rule name identifier
     * @param listener TSListener to be removed
     */
    public void removeListener(String ruleName, TSListener listener) {
        ArrayList<TSListener> listeners = this.specificListeners.get(ruleName);
        if (listeners == null) return;
        listeners.remove(listener);
    }

    /***
     * Detect whether rules meet detection criteria (inside start and end time)
     *
     * @return ArrayList<TSRule> of detected rules
     */
    public ArrayList<TSRule> detect() {
        return this.detect(new Date());
    }

    /***
     * Detect whether rules meet detection criteria (inside start and end time)
     *
     * @param time to be checked with rules
     * @return ArrayList<TSRule> of detected rules
     */
    public ArrayList<TSRule> detect(Date time) {
        ArrayList<TSRule> array = new ArrayList<TSRule>();
        for (TSRule rule: this.configuration.getRules()) {
            if (this.isMatch(rule, time)) array.add(rule);
        }
        return array;
    }

    /***
     * @return Whether current time is morning or not
     */
    public Boolean isMorning() {
        return this.isMorning(new Date());
    }

    /***
     * @param time to be checked with rule
     * @return Whether time is morning or not
     */
    public Boolean isMorning(Date time) {
        TSRule rule = TSRule.morning();
        return this.isMatch(rule, time);
    }

    /***
     * @return Whether current time is afternoon or not
     */
    public Boolean isAfternoon() {
        return this.isAfternoon(new Date());
    }

    /***
     * @param time to be checked with rule
     * @return Whether time is afternoon or not
     */
    public Boolean isAfternoon(Date time) {
        TSRule rule = TSRule.afternoon();
        return this.isMatch(rule, time);
    }

    /***
     * @return Whether current time is evening or not
     */
    public Boolean isEvening() {
        return this.isEvening(new Date());
    }

    /***
     * @param time to be checked with rule
     * @return Whether time is evening or not
     */
    public Boolean isEvening(Date time) {
        TSRule rule = TSRule.evening();
        return this.isMatch(rule, time);
    }

    /***
     * @return Whether current time is night or not
     */
    public Boolean isNight() {
        return this.isNight(new Date());
    }

    /***
     * @param time to be checked with rule
     * @return Whether time is night or not
     */
    public Boolean isNight(Date time) {
        TSRule rule = TSRule.night();
        return this.isMatch(rule, time);
    }

    /***
     * @param rule to be checked
     * @return Whether current time match rule criteria
     */
    public Boolean isMatch(TSRule rule) {
        return this.isMatch(rule, new Date());
    }

    /***
     * @param rule to be checked
     * @param time to be checked with rule
     * @return Whether time match rule criteria
     */
    public Boolean isMatch(TSRule rule, Date time) {
        return this.insideDate(time, rule.getStartTime(), rule.getEndTime());
    }

    /***
     * Trigger matched rule's listeners
     */
    public void trigger() {
        this.trigger(new Date());
    }

    /***
     * Trigger matched rule's listeners based on time
     *
     * @param time to be checked with rules
     */
    public void trigger(Date time) {
        ArrayList<TSListener> triggeredListeners = new ArrayList<TSListener>();
        ArrayList<TSRule> matchRules = this.detect(time);
        for (TSRule rule: matchRules) {
            ArrayList<TSListener> listeners = this.specificListeners.get(rule.getName());
            if (listeners != null && listeners.size() > 0) {
                for (TSListener listener: listeners) {
                    if (!triggeredListeners.contains(listener)) triggeredListeners.add(listener);
                }
            }
        }
        for (TSListener listener: this.genericListeners) {
            if (!triggeredListeners.contains(listener)) triggeredListeners.add(listener);
        }
        for (TSListener listener: triggeredListeners) {
            listener.timeSenseTriggered(matchRules);
        }
    }

    /***
     * Trigger all listeners for rule in current time if match
     *
     * @param rule to be checked
     */
    public void triggerMatch(TSRule rule) {
        this.triggerMatch(rule, new Date());
    }

    /***
     * Trigger all listeners for rule in time if match
     *
     * @param rule to be checked
     * @param time to be checked with rule
     */
    public void triggerMatch(TSRule rule, Date time) {
        if (this.isMatch(rule, time)) {
            ArrayList<TSListener> triggeredListeners = new ArrayList<TSListener>();
            ArrayList<TSListener> listeners = this.specificListeners.get(rule.getName());
            if (listeners != null && listeners.size() > 0) {
                for (TSListener listener: listeners) {
                    if (!triggeredListeners.contains(listener)) triggeredListeners.add(listener);
                }
            }
            for (TSListener listener: this.genericListeners) {
                if (!triggeredListeners.contains(listener)) triggeredListeners.add(listener);
            }
            ArrayList<TSRule> matchRules =  new ArrayList<TSRule>();
            matchRules.add(rule);
            for (TSListener listener: triggeredListeners) {
                listener.timeSenseTriggered(matchRules);
            }
        }
    }

    /***
     * Helper method to check whether date inside start and end date
     * @param date to be checked
     * @param start lower date bound
     * @param end upper date bound
     * @return whether inside or not
     */
    private Boolean insideDate(Date date, Date start, Date end) {
        return date.getTime() >= start.getTime() && date.getTime() < end.getTime();
    }

}
