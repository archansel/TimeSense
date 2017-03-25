package com.gwk.timesense.configuration;

import com.gwk.timesense.rule.TSRule;

import java.util.ArrayList;
import java.util.Date;

/***
 * Configuration object used in TimeSense detection.
 *
 * @author Anselmus KA Kurniawan
 * @version 0.1
 */
public class TSConfiguration {

    public static class Builder {

        private ArrayList<TSRule> rules;

        /***
         * Constructor.
         */
        public Builder() {
            this.rules = new ArrayList<TSRule>();
        }

        /***
         * Add rules to builder
         *
         * @param rules
         * @return Builder
         */
        public Builder addRules(ArrayList<TSRule> rules) {
            for (TSRule rule: rules) {
                this.addRule(rule);
            }
            return this;
        }

        /***
         * Add rule to builder
         *
         * @param rule
         * @return Builder
         */
        public Builder addRule(TSRule rule) {
            if (!this.rules.contains(rule)) this.rules.add(rule);
            return this;
        }

        /***
         * @return TSConfiguration that builder build
         */
        public TSConfiguration done() {
            TSConfiguration configuration = new TSConfiguration();
            configuration.addRules(this.rules);
            return configuration;
        }
    }

    /***
     * @return Builder for TSConfiguration
     */
    public static Builder create() {
        return new Builder();
    }

    private ArrayList<TSRule> rules;

    /***
     * Constructor with empty rules.
     */
    public TSConfiguration() {
        this.rules = new ArrayList<TSRule>();
    }

    /***
     * Constructor from another configuration.
     *
     * @param configuration Configuration to copied
     */
    public TSConfiguration(TSConfiguration configuration) {
        this.rules = new ArrayList<TSRule>(configuration.getRules());
    }

    /***
     * @return TSConfiguration Default configuration with four rules defined in it (TS_RULE_NAME_MORNING, TS_RULE_NAME_AFTERNOON, TS_RULE_NAME_EVENING, TS_RULE_NAME_NIGHT).
     */
    public static TSConfiguration defaultConfiguration() {
        TSConfiguration configuration = new TSConfiguration();
        configuration.addRule(TSRule.morning());
        configuration.addRule(TSRule.afternoon());
        configuration.addRule(TSRule.evening());
        configuration.addRule(TSRule.night());
        return configuration;
    }

    /***
     * @return ArrayList<TSRule> Getter for rules property
     */
    public ArrayList<TSRule> getRules() {
        return rules;
    }

    /***
     * @param rules Set rules property with this value
     */
    public void setRules(ArrayList<TSRule> rules) {
        this.rules = rules;
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
        if (!this.rules.contains(rule)) this.rules.add(rule);
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
        Boolean isUpdated = false;
        TSRule rule = this.findRule(ruleName);
        if (rule == null) return false;
        rule.setStartTime(startTime);
        rule.setEndTime(endTime);
        return true;
    }

    /***
     * @param ruleName Remove rule with this name from configuration
     */
    public void removeRule(String ruleName) {
        TSRule rule = this.findRule(ruleName);
        this.rules.remove(rule);
    }

    /***
     * Find any matching rule by its name
     *
     * @param ruleName Rule name to find
     * @return TSRule
     */
    private TSRule findRule(String ruleName) {
        for (TSRule rule: this.rules) {
            if (rule.getName().equals(ruleName)) return rule;
        }
        return null;
    }
}
