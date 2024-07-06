package org.cgm.config;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Defines an integration test profile. Tests run under this test profile
 * will have different configuration options to other tests.
 */
public class CgmTestProfile implements QuarkusTestProfile {

    /**
     * Returns additional config to be applied to the test. This
     * will override any existing config (including in application.properties),
     * however existing config will be merged with this (i.e. application.properties
     * config will still take effect, unless a specific config key has been
     * overridden).
     */
    @Override
    public Map<String, String> getConfigOverrides() {
        return Collections.emptyMap();
    }

    /**
     * Returns enabled alternatives.
     * <p>
     * This has the same effect as setting the 'quarkus.arc.selected-alternatives'
     * config key,
     * however it may be more convenient.
     */
    @Override
    public Set<Class<?>> getEnabledAlternatives() {
        return Collections.emptySet();
    }

    /**
     * Allows the default config profile to be overridden. This basically just sets
     * the quarkus.test.profile system
     * property before the test is run.
     */
    @Override
    public String getConfigProfile() {
        return "test-profile";
    }
}
