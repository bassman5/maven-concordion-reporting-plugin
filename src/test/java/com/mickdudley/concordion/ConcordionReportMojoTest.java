package com.mickdudley.concordion;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.mockito.Mockito.mock;

/**
 * @author Mick Dudley
 */
public class ConcordionReportMojoTest extends AbstractMojoTestCase {

    public static final String PLUGIN_NAME = "concordion-reporting";

    /**
     * @see org.apache.maven.plugin.testing.AbstractMojoTestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see org.codehaus.plexus.PlexusTestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        // Do nothing
    }
    
    @Test
    public void testBasic() throws MavenReportException {
        File outputDirectory = new File(getBasedir(), "target/test/unit/target/site");
        File concordionDir = new File(outputDirectory, "concordion");
        File imageDir = new File(concordionDir, "image");
        File imageFile = new File(imageDir, "info16.png");

        MavenProject project = mock(MavenProject.class);
        SiteRenderer siteRenderer = mock(SiteRenderer.class);


        ConcordionReportMojo mojo = new ConcordionReportMojo(outputDirectory,
                        project,
                        siteRenderer,
                        "src/test/resources/unit/concordion",
                        "index.html");

        assertTrue(mojo.canGenerateReport());
        assertTrue(mojo.isExternalReport());
        mojo.executeReport(null);
        assertTrue(outputDirectory.exists());
        assertTrue(concordionDir.exists());
        assertTrue(imageDir.exists());
        assertTrue(imageFile.isFile());
        assertEquals("The Concordion acceptance reports for the project", mojo.getDescription(null));
        assertEquals("Concordion", mojo.getName(null));
        assertEquals("concordion/index", mojo.getOutputName());

        assertEquals(concordionDir.getAbsolutePath(), mojo.getOutputDirectory());

        assertEquals(project, mojo.getProject());
        assertEquals(siteRenderer, mojo.getSiteRenderer());
    }

    @Test
    public void testNoConcordionReportDir () {
        File outputDirectory = new File(getBasedir(), "target/test/unit/target/site");
        File concordionDir = new File(outputDirectory, "concordion");
        MavenProject project = mock(MavenProject.class);
        SiteRenderer siteRenderer = mock(SiteRenderer.class);


        ConcordionReportMojo mojo = new ConcordionReportMojo(outputDirectory,
                project,
                siteRenderer,
                "doesnotexist",
                "index.html");

        assertFalse(mojo.canGenerateReport());

    }

    @Test
    public void testNoConcordionIndex () {
        File outputDirectory = new File(getBasedir(), "target/test/unit/target/site");
        File concordionDir = new File(outputDirectory, "concordion");
        MavenProject project = mock(MavenProject.class);
        SiteRenderer siteRenderer = mock(SiteRenderer.class);


        ConcordionReportMojo mojo = new ConcordionReportMojo(outputDirectory,
                project,
                siteRenderer,
                "src/test/resources/unit/concordion",
                "indexnotfound.html");

        assertFalse(mojo.canGenerateReport());

    }

    @Test
    public void testSetOutputDir() throws MavenReportException {
        File outputDirectory = new File(getBasedir(), "target/test/unit/target/site");
        File concordionDir = new File(outputDirectory, "concordion2");

        MavenProject project = mock(MavenProject.class);
        SiteRenderer siteRenderer = mock(SiteRenderer.class);


        ConcordionReportMojo mojo = new ConcordionReportMojo(outputDirectory,
                project,
                siteRenderer,
                "src/test/resources/unit/concordion",
                "index.html");

        mojo.setReportOutputDirectory(concordionDir);
        assertTrue(mojo.canGenerateReport());
        assertTrue(mojo.isExternalReport());
        mojo.executeReport(null);
        assertTrue(outputDirectory.exists());
        assertTrue(concordionDir.exists());
    }
}
