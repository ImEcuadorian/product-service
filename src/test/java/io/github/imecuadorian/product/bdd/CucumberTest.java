package io.github.imecuadorian.product.bdd;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "io.github.imecuadorian.product.bdd")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty,summary")
public class CucumberTest {
}
