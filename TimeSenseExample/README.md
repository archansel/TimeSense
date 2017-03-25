# Time Sense

Easy to use event detection based on time. Time Sense provide default set of rules that can trigger an event listeners. By default we have four time range; morning, afternoon, evening, and night but developer can add their own time range as a rules to trigger event listeners.

## Install

## Usage

## API

### TSRule

Rule object used in TimeSense detection.

#### Constant

`TSRule.Name`
By default there are four rules constants that already defined in the library:
- `TS_RULE_NAME_MORNING`, 04:00 <= T < 11:00
- `TS_RULE_NAME_AFTERNOON`, 11:00 <= T < 17:00
- `TS_RULE_NAME_EVENING`, 17:00 <= T < 21:00
- `TS_RULE_NAME_NIGHT`, 21:00 <= T < 04:00

TSRuleName object constant basically is a `String` so developer can define another string as a rule using `TSRule.Name` object. 

#### Construction

Quite easy to create TSRule object, just use its object constructor that accept three parameters: name (TSRule.Name), start time (Date), and end time (Date).
`TSRule rule = new TSRule(ruleName, startTime, endTime);`

Aside from its default constructor, we provide a fast way to generate each default rules.

```Java
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

- `TSRule.equal(TSRule)`, check whether current rule match another rule. Return `Boolean`
- `TSRule.getName()`, getter for name property. Return `TSRule.Name`
- `TSRule.setName(String)`, setter for name property
- `TSRule.getStartTime()`, getter for startTime property. Return `Date`
- `TSRule.setStartTime(Date)`, setter for startTime property
- `TSRule.getEndTime()`, getter for endTime property. Return `Date` 
- `TSRule.setEndTime(Date)`, setter for endTime property

### TSConfiguration

Configuration object for TimeSense class. TSConfiguration might contains some rules that will be used by TimeSense detection. 

#### Construction

There are three ways how you can create TSConfiguration object:

`TSConfiguration.default()`
The easiest way to generate TSConfiguration object. It will return new TSConfiguration object with four rules defined in it (`TS_RULE_NAME_MORNING`, `TS_RULE_NAME_AFTERNOON`, `TS_RULE_NAME_EVENING`, `TS_RULE_NAME_NIGHT`).

`new TSConfiguration()`
Empty configuration object. No rules are defined in it, you have to add it manualy.

`new TSConfiguration(configuration)`
Create TSConfiguration object based on another TSConfiguration object. The new configuration object will copy every rules defined in the source configuration object.

We also provide a builder class `TSConfiguration.Builder` that can be used to build configuration object.
`TSConfiguration.create().addRule(rule).addRule(rule).done()`

#### Method

- `TSConfiguration.getRules()`, get all rules in configuration. Return `List` of TSRule 
- `TSConfiguration.setRules(List<TSRule>)`, set rules in configuration replacing the old one 
- `TSConfiguration.addRules(List<TSRule>)`, add rules to existing rules in configuration 
- `TSConfiguration.addRule(TSRule)`, add rule object to the configuration
- `TSConfiguration.addRule(TSRule.Name, startTime, endTime)`, add rule object as separated rule property
- `TSConfiguration.updateRule(TSRule.Name, startTime, endTime)`, update rule based on its rule name with new start and end time. Return `Boolean` whether rule updated or not
- `TSConfiguration.removeRule(TSRule.Name)`, remove rule from configuration

### TimeSense

Singleton class that handle all the detection logic and handling. Can be accessed using `TimeSense.getInstance()`

#### Method

TimeSense will use empty configuration (no rules defined) if no TSConfiguration provided by developer. To change configuration use
`TimeSense.setConfiguration(TSConfiguration)`

Detection methods:
- `TimeSense.detect()`, find matching rule for current time. Return `List<TSRule>`
- `TimeSense.detect(time)`, find matching rule for time. Return `List<TSRule>`
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
- `TimeSense.addRule(TSRule)`, add rule for detection
- `TimeSense.addRule(TSRule.Name, startTime, endTime)`, add rule using name, start time, and end time
- `TimeSense.updateRule(TSRule.Name, startTime, endTime)`, update existing rule with start time and end time. Return `Boolean` whether rule updated or not
- `TimeSense.removeRule(TSRule.Name)`, remove rule by rule name
- `TimeSense.getTimeRange(TSRule.Name)`, get start time and end time for certain rule. Return `List<Date>`, start time and end time

Listener methods:
- `TimeSense.addListener(listener)`, add listener for all rules defined in TimeSense
- `TimeSense.addListener(TSRule.Name, listener)`, add listener for rule name
- `TimeSense.removeListeners()`, remove all listeners in TimeSense
- `TimeSense.removeListener(TSRule.Name)`, remove all listeners for rule name
- `TimeSense.removeListener(TSRule.Name, listener)`, remove specific listener for rule name

## Team

[![Michael Ingga](https://avatars1.githubusercontent.com/u/5793036?v=3&s=140)](https://github.com/Michinggun) | [![Bara Timur](https://avatars3.githubusercontent.com/u/3598958?v=3&s=140)](https://github.com/zavyra) | [![Anselmus KAK](https://avatars0.githubusercontent.com/u/816817?v=3&s=140)](https://github.com/archansel)
---|---|---
[Michael Ingga](https://github.com/Michinggun) | [Bara Timur](https://github.com/zavyra) | [Anselmus KAK](https://github.com/archansel)

## License

MIT License

Copyright (c) 2017 GARUDA WISNU KENCANA

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
