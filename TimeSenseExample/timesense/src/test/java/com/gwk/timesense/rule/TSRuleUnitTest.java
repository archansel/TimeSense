package com.gwk.timesense.rule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/***
 * Unit test for TSRule class.
 *
 * @author Anselmus KA Kurniawan
 * @version 0.1
 */
public class TSRuleUnitTest {

    private Date now;
    private Calendar calendar;

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
        this.now = new Date();
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(this.now);
        this.calendar.set(Calendar.HOUR_OF_DAY, 0);
        this.calendar.set(Calendar.MINUTE, 0);
        this.calendar.set(Calendar.SECOND, 0);
    }

    @After
    public void tearDown() throws Exception {
        this.now = null;
        this.calendar = null;

        assertNull(this.now);
        assertNull(this.calendar);
    }

    @Test
    public void constructor() throws Exception {
        String name = "RULE_NAME";
        Date start = new Date();

        this.calendar.setTime(start);
        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date end = this.calendar.getTime();

        TSRule rule = new TSRule(name, start, end);

        assertEquals("Name should be equal", name, rule.getName());
        assertEqualDates(start, rule.getStartTime());
        assertEqualDates(end, rule.getEndTime());
    }

    @Test
    public void morning() throws Exception {
        this.calendar.set(Calendar.HOUR_OF_DAY, 4);
        Date start = this.calendar.getTime();

        this.calendar.set(Calendar.HOUR_OF_DAY, 11);
        Date end = this.calendar.getTime();

        TSRule rule = TSRule.morning();

        assertEquals("Name should be equal", TSRule.TS_RULE_NAME_MORNING, rule.getName());
        assertEqualDates(start, rule.getStartTime());
        assertEqualDates(end, rule.getEndTime());
    }

    @Test
    public void afternoon() throws Exception {
        this.calendar.set(Calendar.HOUR_OF_DAY, 11);
        Date start = this.calendar.getTime();

        this.calendar.set(Calendar.HOUR_OF_DAY, 17);
        Date end = this.calendar.getTime();

        TSRule rule = TSRule.afternoon();

        assertEquals("Name should be equal", TSRule.TS_RULE_NAME_AFTERNOON, rule.getName());
        assertEqualDates(start, rule.getStartTime());
        assertEqualDates(end, rule.getEndTime());
    }

    @Test
    public void evening() throws Exception {
        this.calendar.set(Calendar.HOUR_OF_DAY, 17);
        Date start = this.calendar.getTime();

        this.calendar.set(Calendar.HOUR_OF_DAY, 21);
        Date end = this.calendar.getTime();

        TSRule rule = TSRule.evening();

        assertEquals("Name should be equal", TSRule.TS_RULE_NAME_EVENING, rule.getName());
        assertEqualDates(start, rule.getStartTime());
        assertEqualDates(end, rule.getEndTime());
    }

    @Test
    public void night() throws Exception {
        this.calendar.set(Calendar.HOUR_OF_DAY, 21);
        Date start = this.calendar.getTime();

        this.calendar.set(Calendar.HOUR_OF_DAY, 4);
        Date end = this.calendar.getTime();

        TSRule rule = TSRule.night();

        assertEquals("Name should be equal", TSRule.TS_RULE_NAME_NIGHT, rule.getName());
        assertEqualDates(start, rule.getStartTime());
        assertEqualDates(end, rule.getEndTime());
    }

    @Test
    public void getName() throws Exception {
        String name = "RULE_NAME";
        Date start = new Date();

        this.calendar.setTime(start);
        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date end = this.calendar.getTime();

        TSRule rule = new TSRule(name, start, end);

        assertEquals("Name should be equal", name, rule.getName());
    }

    @Test
    public void setName() throws Exception {
        String name = "RULE_NAME";
        Date start = new Date();

        this.calendar.setTime(start);
        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date end = this.calendar.getTime();

        TSRule rule = new TSRule(name, start, end);

        String newName = "NAME";
        rule.setName(newName);

        assertEquals("Name should be equal", newName, rule.getName());
    }

    @Test
    public void getStartTime() throws Exception {
        String name = "RULE_NAME";
        Date start = new Date();

        this.calendar.setTime(start);
        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date end = this.calendar.getTime();

        TSRule rule = new TSRule(name, start, end);

        assertEqualDates(start, rule.getStartTime());
    }

    @Test
    public void setStartTime() throws Exception {
        String name = "RULE_NAME";
        Date start = new Date();

        this.calendar.setTime(start);
        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date end = this.calendar.getTime();

        TSRule rule = new TSRule(name, start, end);

        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date newDate = this.calendar.getTime();

        rule.setStartTime(newDate);

        assertEqualDates(newDate, rule.getStartTime());
    }

    @Test
    public void getEndTime() throws Exception {
        String name = "RULE_NAME";
        Date start = new Date();

        this.calendar.setTime(start);
        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date end = this.calendar.getTime();

        TSRule rule = new TSRule(name, start, end);

        assertEqualDates(end, rule.getEndTime());
    }

    @Test
    public void setEndTime() throws Exception {
        String name = "RULE_NAME";
        Date start = new Date();

        this.calendar.setTime(start);
        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date end = this.calendar.getTime();

        TSRule rule = new TSRule(name, start, end);

        this.calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date newDate = this.calendar.getTime();

        rule.setEndTime(newDate);

        assertEqualDates(newDate, rule.getEndTime());
    }

    @Test
    public void equals() throws Exception {
        TSRule morning = TSRule.morning();
        TSRule night = TSRule.night();

        assertTrue("Rules should be equal", morning.equals(morning));
        assertFalse("Rules should not be equal", morning.equals(night));
    }

}