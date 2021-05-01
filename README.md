## Cucumber PDF Report Plugin

Refer to this [article](https://grasshopper.tech/2224/) for more details. ![Sample Report](https://github.com/grasshopper7/cucumber-pdf-plugin-report/blob/master/cucumber-pdf-plugin-report/pdf-report/report.pdf)

The below configuration needs to be added to the project pom to setup the PDF report plugin.

```
<plugin>
	<groupId>tech.grasshopper</groupId>
	<artifactId>cucumber-pdf-plugin</artifactId>
	<version>1.4.2</version>
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
		<cucumberPdfReportDirectory>pdf-report</cucumberPdfReportDirectory>
		<strictCucumber6Behavior>true</strictCucumber6Behavior>
	</configuration>
</plugin>
```

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

**EXPANDED SECTION**

![sample](https://raw.githubusercontent.com/grasshopper7/cucumber-pdf-plugin/master/cucumber-pdf-plugin/expanded.png)
