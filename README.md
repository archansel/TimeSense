# Time Sense

Easy to use event detection based on time. Time Sense provide default set of rules that can trigger an event listeners. By default we have four rules; morning, afternoon, evening, and night but developer can add their own rules to trigger event listeners.

## Install

Add jitpack repository
```gradle
repositories {
    // another repositories
    maven { url 'https://jitpack.io' }
}
```

Add dependencies in build.gradle file
```gradle
dependencies {
    // another dependencies
    compile 'com.github.archansel:TimeSense:0.1'
}
```

## Usage

See example project.

## API

### TSRule

Rule object used in TimeSense detection.

#### Constant

By default there are four rules constants that already defined in the library:
- `TS_RULE_NAME_MORNING`, 04:00 <= T < 11:00
- `TS_RULE_NAME_AFTERNOON`, 11:00 <= T < 17:00
- `TS_RULE_NAME_EVENING`, 17:00 <= T < 21:00
- `TS_RULE_NAME_NIGHT`, 21:00 <= T < 04:00

#### Construction

Quite easy to create TSRule object, just use its object constructor that accept three parameters: name (String), start time (Date), and end time (Date).
`TSRule rule = new TSRule(ruleName, startTime, endTime);`

Aside from its default constructor, we provide a fast way to generate each default rules.
```
    TSRule.morning();
    TSRule.afternoon();
    TSRule.evening();
    TSRule.night();
```

#### Property

- `name`, used as rule identifier
- `startTime`, start time of the rule (included in calculation)
- `endTime`, end time of the rule (excluded in calculation)

#### Method

- `TSRule.equals(TSRule)`, check whether current rule match another rule. Return `Boolean`
- `TSRule.getName()`, getter for name property. Return `String`
- `TSRule.setName(String)`, setter for name property
- `TSRule.getStartTime()`, getter for startTime property. Return `Date`
- `TSRule.setStartTime(Date)`, setter for startTime property
- `TSRule.getEndTime()`, getter for endTime property. Return `Date` 
- `TSRule.setEndTime(Date)`, setter for endTime property

### TSConfiguration

Configuration object for TimeSense class. TSConfiguration might contains some rules that will be used by TimeSense detection. 

#### Construction

There are three ways how you can create TSConfiguration object:

```
    TSConfiguration.defaultConfiguration()
```
The easiest way to generate TSConfiguration object. It will return new TSConfiguration object with four rules defined in it (`TS_RULE_NAME_MORNING`, `TS_RULE_NAME_AFTERNOON`, `TS_RULE_NAME_EVENING`, `TS_RULE_NAME_NIGHT`).

```
    new TSConfiguration()
```
Empty configuration object. No rules are defined in it, you have to add it manualy.

```
    new TSConfiguration(configuration)
```
Create TSConfiguration object based on another TSConfiguration object. The new configuration object will copy every rules defined in the source configuration object.

We also provide a builder class `TSConfiguration.Builder` that can be used to build configuration object.
`TSConfiguration.create().addRule(TSRule).addRule(TSRule).done()`

#### Method

- `TSConfiguration.getRules()`, get all rules in configuration. Return `ArrayList` of TSRule 
- `TSConfiguration.setRules(ArrayList<TSRule>)`, set rules in configuration replacing the old one 
- `TSConfiguration.addRules(ArrayList<TSRule>)`, add rules to existing rules in configuration 
- `TSConfiguration.addRule(TSRule)`, add rule object to the configuration
- `TSConfiguration.addRule(ruleName, startTime, endTime)`, add rule object as separated rule property
- `TSConfiguration.updateRule(ruleName, startTime, endTime)`, update rule based on its rule name with new start and end time. Return `Boolean` whether rule updated or not
- `TSConfiguration.removeRule(ruleName)`, remove rule name from configuration
- `TSConfiguration.findRule(ruleName)`, find rule with name from configuration

### TSListener

Interface class that can listen to TimeSense event.

#### Protocol

- `TSListener.timeSenseTriggered()`, triggered only if listen to all rules (not using specific rule name)
- `TSListener.timeSenseTriggered(ruleName)`, triggered if certain rule match

### TimeSense

Singleton class that handle all the detection logic and handling. Can be accessed using `TimeSense.getInstance()`

#### Method

TimeSense will use empty configuration (no rules defined) if no TSConfiguration provided by developer. To change configuration use
`TimeSense.setConfiguration(TSConfiguration)`

Detection methods:
- `TimeSense.detect()`, find matching rule for current time. Return `ArrayList<TSRule>`
- `TimeSense.detect(time)`, find matching rule for time. Return `ArrayList<TSRule>`
- `TimeSense.isMorning()`, check whether current time is morning (based on TS_RULE_NAME_MORNING) or not. Return `Boolean`
- `TimeSense.isMorning(time)`, check whether time is morning (based on TS_RULE_NAME_MORNING) or not. Return `Boolean`
- `TimeSense.isAfternoon()`, check whether current time is afternoon (based on TS_RULE_NAME_AFTERNOON) or not. Return `Boolean`
- `TimeSense.isAfternoon(time)`, check whether time is afternoon (based on TS_RULE_NAME_AFTERNOON) or not. Return `Boolean`
- `TimeSense.isEvening()`, check whether current time is evening (based on TS_RULE_NAME_EVENING) or not. Return `Boolean`
- `TimeSense.isEvening(time)`, check whether time is evening (based on TS_RULE_NAME_EVENING) or not. Return `Boolean`
- `TimeSense.isNight()`, check whether current time is night (based on TS_RULE_NAME_NIGHT) or not. Return `Boolean`
- `TimeSense.isNight(time)`, check whether time is night (based on TS_RULE_NAME_NIGHT) or not. Return `Boolean`
- `TimeSense.isMatch(TSRule)`, check whether current time is inside rule time interval or not. Return `Boolean`
- `TimeSense.iSMatch(TSRule, time)`, check whether time is inside rule time interval or not. Return `Boolean`
- `TimeSense.trigger()`, trigger all listeners for current time that match the rule detected in `TimeSense.detect()`
- `TimeSense.trigger(time)`, trigger all listeners for time that match the rule detected in `TimeSense.detect(time)`
- `TimeSense.triggerMatch(TSRule)`, trigger all listeners for rule in current time if match
- `TimeSense.triggerMatch(TSRule, time)`, trigger all listeners for rule in time if match

Rule methods:
- `TimeSense.addRules(ArrayList<TSRule>)`, add rules for detection 
- `TimeSense.addRule(TSRule)`, add rule for detection
- `TimeSense.addRule(ruleName, startTime, endTime)`, add rule using name, start time, and end time
- `TimeSense.updateRule(ruleName, startTime, endTime)`, update existing rule with start time and end time. Return `Boolean` whether rule updated or not
- `TimeSense.removeRule(ruleName)`, remove rule by rule name
- `TimeSense.getTimeRange(ruleName)`, get start time and end time for certain rule. Return `ArrayList<Date>`, start time and end time

Listener methods:
- `TimeSense.addListener(listener)`, add listener for all rules defined in TimeSense
- `TimeSense.addListener(ruleName, listener)`, add listener for rule name
- `TimeSense.removeListeners()`, remove all listeners in TimeSense
- `TimeSense.removeListener(listener)`, remove listener from TimeSense
- `TimeSense.removeListener(ruleName)`, remove all listeners for rule name
- `TimeSense.removeListener(ruleName, listener)`, remove specific listener for rule name

## License

MIT License

Copyright (c) 2017 Anselmus KA Kurniawan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
