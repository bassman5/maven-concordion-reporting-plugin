package com.mickdudley.concordion;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * @goal report
 * @phase site
 * @author Mick Dudley
 * @description add a concordion report to the site project reports.
 */
public class ConcordionReportMojo extends AbstractMavenReport {


    public static final String PLUGIN_NAME = "Concordion-Report ";
    public static final String DEFAULT_REPORT_DIR = "concordion";
    /**
     * Output folder where the main page of the report will be generated. Note that this parameter is only relevant if
     * the goal is run directly from the command line or from the default lifecycle. If the goal is run indirectly as
     * part of a site generation, the output directory configured in the Maven Site Plugin will be used instead.

     * @parameter default-value="${project.reporting.outputDirectory}"
     * @required
     */
    protected File outputDirectory;


    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @component
     * @required
     * @readonly
     */
    private SiteRenderer siteRenderer;

    // The location of the concordion tests
    /**
     * @parameter default-value="${basedir}/target/concordion"
     * @required
     */
    private String concordionDir;


    // The location of the concordion index file within concordionDir
    /**
     * @parameter default-value="index.html"
     * @required
     */
    private String concordionIndexFile;


    public ConcordionReportMojo() {
        super();
    }

    /*
     * Only used for testing
     */
    protected ConcordionReportMojo(File outputDirectory,
                                MavenProject project,
                                SiteRenderer siteRenderer,
                                String concordionDir,
                                String concordionIndexFile) {
        this.outputDirectory = outputDirectory;
        this.project = project;
        this.siteRenderer = siteRenderer;
        this.concordionDir = concordionDir;
        this.concordionIndexFile = concordionIndexFile;
    }



    @Override
    public boolean isExternalReport()
    {
        return true;
    }

    @Override
    public boolean canGenerateReport()
    {
        /* Here we can decide if there are no concordion report files don't add to site */

        File sourceDir = new File(this.concordionDir);
        if (! sourceDir.isDirectory()) {
            getLog().info("Skipped \"" + PLUGIN_NAME + "\" report, Concordion report files not found in " + sourceDir.getAbsolutePath());
            return false;
        }
        File index = new File(sourceDir, concordionIndexFile);
        if (! index.exists()) {
            getLog().info("Skipped \"" + PLUGIN_NAME + "\" report, Concordion index file not found " + index.getAbsolutePath());
            return false;
        }

        return true;
    }

    protected void executeReport(Locale locale) throws MavenReportException {


        File directory = new File (getOutputDirectory());
        if ( !directory.exists() ) {
            directory.mkdirs();
        }

        try {
            FileUtils.copyDirectoryStructure(new File(concordionDir), directory);
        } catch (IOException e) {
            throw new MavenReportException("Error copying concordion reports", e);
        }
    }


    protected String getOutputDirectory() {
        return outputDirectory.getAbsolutePath() + "/" + DEFAULT_REPORT_DIR;
    }

    protected MavenProject getProject() {
        return project;
    }

    protected SiteRenderer getSiteRenderer() {
        return siteRenderer;
    }

    public String getDescription(Locale locale) {
        return "The Concordion acceptance reports for the project";
    }

    public String getName(Locale locale) {
        return "Concordion";
    }

    public String getOutputName() {
        return DEFAULT_REPORT_DIR + "/" + concordionIndexFile.replace(".html", "");
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#setReportOutputDirectory(java.io.File)
     */
    public void setReportOutputDirectory( File reportOutputDirectory )
    {
        this.outputDirectory = reportOutputDirectory;
    }

}
