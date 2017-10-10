package org.jahia.modules.testESCluster.api;

import org.glassfish.jersey.server.ResourceConfig;

public class TestAPIApplication extends ResourceConfig {

    public TestAPIApplication() {
        super(
                TestAPI.class
        );
    }
}
