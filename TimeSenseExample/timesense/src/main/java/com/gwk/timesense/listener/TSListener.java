package com.gwk.timesense.listener;


import com.gwk.timesense.rule.TSRule;

import java.util.ArrayList;

/***
 * Listener object for TimeSense.
 *
 * @author Anselmus KA Kurniawan
 * @version 0.1
 */
public interface TSListener {
    public void timeSenseTriggered(ArrayList<TSRule> rules);
}
