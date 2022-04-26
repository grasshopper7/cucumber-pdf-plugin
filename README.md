## Cucumber PDF Report Plugin

Refer to this [article](https://ghchirp.tech/2224/) for more details. ![Sample Report](https://github.com/grasshopper7/cucumber-pdf-plugin-report/blob/master/cucumber-pdf-plugin-report/pdf-report/report.pdf)

The below configuration needs to be added to the project pom to setup the PDF report plugin.

```
<plugin>
	<groupId>tech.grasshopper</groupId>
	<artifactId>cucumber-pdf-plugin</artifactId>
	<version>1.8.0</version>
	<executions>
		<execution>
			<id>report</id>
			<phase>post-integration-test</phase>
			<goals>
				<goal>pdfreport</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<cucumberJsonReportDirectory>${project.build.directory}/json-report</cucumberJsonReportDirectory>
	</configuration>
</plugin>
```

**Plugin Configurations**

| Configuration      | Description | Default |
| :----------- | :----------- | :----------- |
| cucumberJsonReportDirectory | Directory location of cucumber json reports. Required. | |
| cucumberPdfReportDirectory | Directory prefix of location of generated pdf report | report |
| cucumberPdfReportDirectoryTimeStamp | Directory suffix of location of generated pdf report | dd MM yyyy HH mm ss |
| strictCucumber6Behavior | Cucumber 6 Strict behavior | true |
| title | Report title | Cucumber PDF Report |
| titleColor | Report title color in hex without leading # | Black |
| passColor | Pass status color in hex without leading # | Green |
| failColor | Fail status color in hex without leading # | Red |
| skipColor | Skip status color in hex without leading # | Yellow |
| displayFeature | Display feature report section | true |
| displayScenario | Display scenario report section  | true |
| displayDetailed | Display step detailed report section  | true |
| displayExpanded | Display media as zoomed report section  | false |
| displayAttached | Display media as pdf attachment | true |

**SUMMARY SECTION**

![sample](https://raw.githubusercontent.com/grasshopper7/cucumber-pdf-plugin/master/cucumber-pdf-plugin/summary.png)

**FEATURE SECTION**

![sample](https://raw.githubusercontent.com/grasshopper7/cucumber-pdf-plugin/master/cucumber-pdf-plugin/feature.png)

**SCENARIO SECTION**

![sample](https://raw.githubusercontent.com/grasshopper7/cucumber-pdf-plugin/master/cucumber-pdf-plugin/scenario.png)

**DETAILED SECTION**

![sample](https://raw.githubusercontent.com/grasshopper7/cucumber-pdf-plugin/master/cucumber-pdf-plugin/details.png)

**SCREENSHOT**

![sample](https://raw.githubusercontent.com/grasshopper7/cucumber-pdf-plugin/master/cucumber-pdf-plugin/screenshot.png)

**ZOOMED SCREENSHOT**

![sample](https://raw.githubusercontent.com/grasshopper7/cucumber-pdf-plugin/master/cucumber-pdf-plugin/expanded.png)
