package hudson.tools;

import hudson.ExtensionList;
import hudson.model.JDK;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.casc.misc.JenkinsConfiguredWithCodeRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JDKInstallerCascTest {
    @Rule
    public JenkinsConfiguredWithCodeRule r = new JenkinsConfiguredWithCodeRule();

    @ConfiguredWithCode("casc.yaml")
    @Test
    public void configuredByCasC() {
        final JDK.DescriptorImpl descriptor = ExtensionList.lookupSingleton(JDK.DescriptorImpl.class);
        assertEquals(1, descriptor.getInstallations().length);

        JDK jdk = descriptor.getInstallations()[0];
        assertEquals("jdk8", jdk.getName());
        assertEquals("/jdk", jdk.getHome());

        InstallSourceProperty installSourceProperty = jdk.getProperties().get(InstallSourceProperty.class);
        assertEquals(1, installSourceProperty.installers.size());

        JDKInstaller installer = installSourceProperty.installers.get(JDKInstaller.class);
        assertEquals("jdk-8u181-oth-JPR", installer.id);
        assertTrue(installer.acceptLicense);
    }
}
