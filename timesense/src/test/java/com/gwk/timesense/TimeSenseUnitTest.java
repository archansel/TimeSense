package com.gwk.timesense;

import com.gwk.timesense.configuration.TSConfiguration;
import com.gwk.timesense.listener.TSListener;
import com.gwk.timesense.rule.TSRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/***
 * Unit test for TimeSense.
 *
 * @author Anselmus KA Kurniawan
 * @version 0.1
 */
public class TimeSenseUnitTest {

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

    @Mock
    private TSListener listener;

    @InjectMocks
    private TimeSense timeSense = TimeSense.getInstance();

    private Date morningDate;
    private Date afternoonDate;
    private Date eveningDate;
    private Date nightDate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        this.morningDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        this.afternoonDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 18);
        this.eveningDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 1);
        this.nightDate = calendar.getTime();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() throws Exception {
        TimeSense timeSense = TimeSense.getInstance();
        assertNotNull("TimeSense object should not be null", timeSense);
    }

    @Test
    public void setConfiguration() throws Exception {
        TSConfiguration configuration = TSConfiguration.defaultConfiguration();
        TimeSense.getInstance().setConfiguration(configuration);

        assertEquals("Rules should be equal", configuration.getRules(), TimeSense.getInstance().getConfiguration().getRules());
    }

    @Test
    public void addRules() throws Exception {
        TimeSense.getInstance().setConfiguration(new TSConfiguration());
        TimeSense.getInstance().addRules(TSConfiguration.defaultConfiguration().getRules());

        assertEquals("Rules size should be 4", 4, TimeSense.getInstance().getConfiguration().getRules().size());
    }

    @Test
    public void addRule() throws Exception {
        TimeSense.getInstance().setConfiguration(new TSConfiguration());
        TimeSense.getInstance().addRule(TSRule.morning());
        TimeSense.getInstance().addRule(TSRule.afternoon());

        assertEquals("Rules size should be 2", 2, TimeSense.getInstance().getConfiguration().getRules().size());
    }

    @Test
    public void addRuleProperty() throws Exception {
        TimeSense.getInstance().setConfiguration(new TSConfiguration());
        TimeSense.getInstance().addRule(TSRule.morning());
        TimeSense.getInstance().addRule(TSRule.TS_RULE_NAME_AFTERNOON, new Date(), new Date());

        assertEquals("Rules size should be 2", 2, TimeSense.getInstance().getConfiguration().getRules().size());
    }

    @Test
    public void updateRule() throws Exception {
        Date now = new Date();

        TimeSense.getInstance().addRule(TSRule.morning());
        TimeSense.getInstance().updateRule(TSRule.TS_RULE_NAME_MORNING, now, now);

        TSRule rule = TimeSense.getInstance().getConfiguration().getRules().get(0);

        assertEqualDates(now, rule.getStartTime());
        assertEqualDates(now, rule.getEndTime());
    }

    @Test
    public void removeRule() throws Exception {
        TSConfiguration configuration = TSConfiguration.defaultConfiguration();
        TimeSense.getInstance().setConfiguration(configuration);
        TimeSense.getInstance().removeRule(TSRule.TS_RULE_NAME_MORNING);

        assertEquals("Rules size should be 3", 3, TimeSense.getInstance().getConfiguration().getRules().size());
    }

    @Test
    public void getTimeRange() throws Exception {
        Date now = new Date();
        TSRule rule = new TSRule(TSRule.TS_RULE_NAME_MORNING, now, now);

        TimeSense.getInstance().setConfiguration(new TSConfiguration());
        TimeSense.getInstance().addRule(rule);
        ArrayList<Date> dates = TimeSense.getInstance().getTimeRange(TSRule.TS_RULE_NAME_MORNING);

        assertEqualDates(now, dates.get(0));
        assertEqualDates(now, dates.get(1));
    }

    @Test
    public void detectWithTime() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        ArrayList<TSRule> rules = TimeSense.getInstance().detect(this.morningDate);
        TSRule rule = rules.get(0);

        assertEquals("Detected rule should be morning rule", TSRule.TS_RULE_NAME_MORNING, rule.getName());
    }

    @Test
    public void isMorningWithTime() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());

        assertTrue("Time should be morning", TimeSense.getInstance().isMorning(this.morningDate));
        assertFalse("Time should not be morning", TimeSense.getInstance().isMorning(this.nightDate));
    }

    @Test
    public void isAfternoonWithTime() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());

        assertTrue("Time should be afternoon", TimeSense.getInstance().isAfternoon(this.afternoonDate));
        assertFalse("Time should not be afternoon", TimeSense.getInstance().isAfternoon(this.nightDate));
    }

    @Test
    public void isEveningWithTime() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());

        assertTrue("Time should be evening", TimeSense.getInstance().isEvening(this.eveningDate));
        assertFalse("Time should not be evening", TimeSense.getInstance().isEvening(this.nightDate));
    }

    @Test
    public void isNightWithTime() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());

        assertTrue("Time should be night", TimeSense.getInstance().isNight(this.nightDate));
        assertFalse("Time should not be night", TimeSense.getInstance().isNight(this.morningDate));
    }

    @Test
    public void isMatchWithTime() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());

        assertTrue("Time and rule should be matched", TimeSense.getInstance().isMatch(TSRule.morning(), this.morningDate));
        assertFalse("Time and rule should not be matched", TimeSense.getInstance().isMatch(TSRule.morning(), this.nightDate));
    }

    @Test
    public void addListener() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addListener(this.listener);

        TimeSense.getInstance().trigger(this.morningDate);
        verify(this.listener, times(1)).timeSenseTriggered();
    }

    @Test
    public void addListenerForRuleName() throws Exception {
        TSRule rule = TSRule.morning();

        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addListener(rule.getName(), this.listener);

        TimeSense.getInstance().trigger(this.morningDate);
        verify(this.listener, times(1)).timeSenseTriggered(rule.getName());
    }

    @Test
    public void removeListeners() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addListener(this.listener);
        TimeSense.getInstance().removeListeners();

        TimeSense.getInstance().trigger(this.morningDate);
        verify(this.listener, times(0)).timeSenseTriggered();
    }

    @Test
    public void removeListener() throws Exception {
        TSRule rule = TSRule.morning();

        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addListener(this.listener);
        TimeSense.getInstance().addListener(rule.getName(), this.listener);
        TimeSense.getInstance().removeListener(this.listener);

        TimeSense.getInstance().trigger(this.morningDate);
        verify(this.listener, times(0)).timeSenseTriggered();
        verify(this.listener, times(0)).timeSenseTriggered(rule.getName());
    }

    @Test
    public void removeListenerForRuleName() throws Exception {
        TSRule rule = TSRule.morning();

        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addListener(rule.getName(), this.listener);
        TimeSense.getInstance().addListener(TSRule.afternoon().getName(), this.listener);
        TimeSense.getInstance().removeListener(rule.getName());

        TimeSense.getInstance().trigger(this.morningDate);
        verify(this.listener, times(0)).timeSenseTriggered(rule.getName());
    }

    @Test
    public void removeListenerForRuleNameAndListener() throws Exception {
        TSRule rule = TSRule.morning();

        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addListener(rule.getName(), this.listener);
        TimeSense.getInstance().addListener(TSRule.afternoon().getName(), this.listener);
        TimeSense.getInstance().removeListener(TSRule.afternoon().getName(), this.listener);

        TimeSense.getInstance().trigger(this.morningDate);
        verify(this.listener, times(1)).timeSenseTriggered(rule.getName());
    }

    @Test
    public void triggerWithTime() throws Exception {
        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addListener(this.listener);

        TimeSense.getInstance().trigger(this.morningDate);
        verify(this.listener, times(1)).timeSenseTriggered();
    }

    @Test
    public void triggerMatchWithTime() throws Exception {
        TSRule rule = TSRule.morning();

        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addListener(this.listener);

        TimeSense.getInstance().triggerMatch(rule, this.nightDate);
        verify(this.listener, times(0)).timeSenseTriggered(rule.getName());
    }

}