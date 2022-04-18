## Cucumber PDF Report Plugin

Refer to this [article](https://ghchirp.tech/2224/) for more details. ![Sample Report](https://github.com/grasshopper7/cucumber-pdf-plugin-report/blob/master/cucumber-pdf-plugin-report/pdf-report/report.pdf)

The below configuration needs to be added to the project pom to setup the PDF report plugin.

```
<plugin>
	<groupId>tech.grasshopper</groupId>
	<artifactId>cucumber-pdf-plugin</artifactId>
	<version>1.6.0</version>
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
- cucumberJsonReportDirectory - Directory location of cucumber json report files
- cucumberPdfReportDirectory - Directory prefix of location of generated pdf report, default is 'report'
- cucumberPdfReportDirectoryTimeStamp - Directory suffix of location of generated pdf report, default is 'dd MM yyyy HH mm ss'
- strictCucumber6Behavior - Default is true

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
