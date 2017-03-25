package com.gwk.timesense.configuration;

import com.gwk.timesense.rule.TSRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;


/***
 * Unit test for TSConfiguration class.
 *
 * @author Anselmus KA Kurniawan
 * @version 0.1
 */
public class TSConfigurationUnitTest {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    private static void assertEqualDates(Date date1, Date date2) {
        String d1 = formatter.format(date1);
        String d2 = formatter.format(date2);
        assertTrue("Dates should be equal", d1.equals(d2));
    }
    private static void assertNotEqualDates(Date date1, Date date2) {
        String d1 = formatter.format(date1);
        String d2 = formatter.format(date2);
        assertFalse("Dates should not be equal", d1.equals(d2));
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void constructor() throws Exception {
        TSConfiguration configuration = new TSConfiguration();

        assertEquals("Rules size should be zero", 0, configuration.getRules().size());
    }

    @Test
    public void constructorFromConfiguration() throws Exception {
        TSRule rule = TSRule.morning();

        TSConfiguration configuration = new TSConfiguration();
        configuration.addRule(rule);

        TSConfiguration newConfiguration = new TSConfiguration(configuration);
        TSRule newConfigurationRule = newConfiguration.getRules().get(0);

        assertEquals("Configuration size should be equal", configuration.getRules().size(), newConfiguration.getRules().size());
        assertTrue("Rule inside each configuration should be equal", rule.equals(newConfigurationRule));
    }

    @Test
    public void defaultConfiguration() throws Exception {
        TSConfiguration configuration = TSConfiguration.defaultConfiguration();

        assertEquals("Rules size should be four", 4, configuration.getRules().size());
    }

    @Test
    public void getRules() throws Exception {
        TSConfiguration configuration1 = TSConfiguration.defaultConfiguration();
        TSConfiguration configuration2 = new TSConfiguration(configuration1);

        assertEquals("Rules in each configuration should be equal", configuration1.getRules(), configuration2.getRules());
    }

    @Test
    public void setRules() throws Exception {
        TSConfiguration configuration1 = TSConfiguration.defaultConfiguration();
        TSConfiguration configuration2 = new TSConfiguration();
        configuration2.setRules(configuration1.getRules());

        assertEquals("Rules in each configuration should be equal", configuration1.getRules(), configuration2.getRules());
    }

    @Test
    public void addRules() throws Exception {
        ArrayList<TSRule> set1 = new ArrayList<TSRule>();
        set1.add(TSRule.morning());
        set1.add(TSRule.afternoon());

        ArrayList<TSRule> set2 = new ArrayList<TSRule>();
        set2.add(TSRule.evening());
        set2.add(TSRule.night());

        TSConfiguration configuration = new TSConfiguration();
        configuration.addRules(set1);

        assertEquals("Rules size should be two", 2, configuration.getRules().size());

        configuration.addRules(set1);

        assertEquals("Rules size should be two", 2, configuration.getRules().size());

        configuration.addRules(set2);

        assertEquals("Rules size should be four", 4, configuration.getRules().size());
    }

    @Test
    public void addRule() throws Exception {
        TSConfiguration configuration = new TSConfiguration();
        configuration.addRule(TSRule.morning());

        assertEquals("Rules size should be one", 1, configuration.getRules().size());

        configuration.addRule(TSRule.morning());

        assertEquals("Rules size should be one", 1, configuration.getRules().size());

        configuration.addRule(TSRule.night());

        assertEquals("Rules size should be two", 2, configuration.getRules().size());
    }

    @Test
    public void addRuleWithProperty() throws Exception {
        Date now = new Date();

        TSConfiguration configuration = new TSConfiguration();
        configuration.addRule(TSRule.TS_RULE_NAME_MORNING, now, now);

        assertEquals("Rules size should be one", 1, configuration.getRules().size());

        configuration.addRule(TSRule.TS_RULE_NAME_MORNING, now, now);

        assertEquals("Rules size should be one", 1, configuration.getRules().size());
    }

    @Test
    public void updateRule() throws Exception {
        Date now = new Date();

        TSConfiguration configuration = new TSConfiguration();
        configuration.addRule(TSRule.morning());
        configuration.updateRule(TSRule.TS_RULE_NAME_MORNING, now, now);

        TSRule rule = configuration.getRules().get(0);

        assertEqualDates(now, rule.getStartTime());
        assertEqualDates(now, rule.getEndTime());
    }

    @Test
    public void removeRule() throws Exception {
        TSConfiguration configuration = TSConfiguration.defaultConfiguration();
        configuration.removeRule(TSRule.TS_RULE_NAME_MORNING);

        assertEquals("Rules size should be three", 3, configuration.getRules().size());
    }

}