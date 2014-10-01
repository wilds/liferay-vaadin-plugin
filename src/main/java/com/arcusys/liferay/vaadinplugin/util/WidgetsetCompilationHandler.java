package com.arcusys.liferay.vaadinplugin.util;

/*
 * #%L
 * Liferay Vaadin Plugin
 * %%
 * Copyright (C) 2010 - 2013 Vaadin Ltd.
 * Copyright (C) 2013 Arcusys Ltd.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WidgetsetCompilationHandler implements Runnable {
    private static final Log log = LogFactoryUtil.getLog(WidgetsetCompilationHandler.class);

    private WidgetsetCompiler compiler;

    private final String widgetset;
    private final List<VaadinAddonInfo> includeAddons;
    private final List<File> additionalDependencies;

    private ILog outputLog;

    public WidgetsetCompilationHandler(String widgetset, List<VaadinAddonInfo> includeAddons, List<File> additionalDependencies, ILog outputLog) {
        this.widgetset = widgetset;
        this.includeAddons = includeAddons;
        this.additionalDependencies = additionalDependencies;
        this.outputLog = outputLog;
    }

    public void run() {
        File tmpDir = null;
        try {
            tmpDir = WidgetsetUtil.createTempDir();

            WidgetsetUtil.createWidgetset(tmpDir, widgetset, getIncludeWidgetsets());

            compiler = new WidgetsetCompiler(outputLog, widgetset, tmpDir.getAbsolutePath(), getClasspathEntries(tmpDir));

            try {
                compiler.compileWidgetset();
            } catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
            }

            File compiledWidgetset = new File(tmpDir, widgetset);
            if (compiledWidgetset.exists() && compiledWidgetset.isDirectory()) {
                String ws = ControlPanelPortletUtil.getWidgetsetDir() + widgetset;
                WidgetsetUtil.rotateWidgetsetBackups(ws);
                WidgetsetUtil.backupOldWidgetset(ws);
                File destDir = new File(ws);

                outputLog.log("Copying widgetset from " + compiledWidgetset + " to " + destDir);

                System.out.println("Copying widgetset from " + compiledWidgetset + " to " + destDir);

                FileUtils.copyDirectory(compiledWidgetset, destDir);

                outputLog.log("Copying done");
                System.out.println("Copying done");
            }

        } catch (IOException e) {
            log.warn("Could not compile widgetsets.", e);
            System.out.println("Could not compile widgetsets. " + e.getMessage());
        } finally {
            try {
                System.out.println("Remove temp directory");
                FileUtils.deleteDirectory(tmpDir);
                System.out.println("Removing done...");
            } catch (IOException e) {
                log.warn("Could not delete temporary directory: " + tmpDir, e);
                System.out.println("Could not delete temporary directory: " + tmpDir + "  " + e);
            }

            compilationFinished();
        }
    }

    private List<File> getClasspathEntries(File entry) {
        Version version = ControlPanelPortletUtil.getPortalVaadinVersion();
        Version vaadin700 = new Version("7.0.0");
        Version vaadin710 = new Version("7.1.0");
        Version vaadin720 = new Version("7.2.0");
        Version vaadin730 = new Version("7.3.0");

        List<File> classpathEntries = new ArrayList<File>();
        classpathEntries.add(entry);

        // The vaadin-client-compiler JAR is located in the portal lib dir
        classpathEntries.add(ControlPanelPortletUtil.getVaadinClientCompilerJarLocation());

        // The vaadin-client JAR is located in the portal lib dir
        classpathEntries.add(ControlPanelPortletUtil.getVaadinClientJarLocation());

        // The vaadin-server JAR is located in the portal lib dir
        classpathEntries.add(ControlPanelPortletUtil.getVaadinServerJarLocation());

        // The vaadin-shared.jar is located in the portal lib dir
        classpathEntries.add(ControlPanelPortletUtil.getVaadinSharedJarLocation());

        if (version.compareTo(vaadin710) >= 0) {
            // The vaadin-client-compiler-deps JAR is located in the portal lib dir
            classpathEntries.add(ControlPanelPortletUtil.getVaadinClientCompilerDepsJarLocation());
        }

        if(version.compareTo(vaadin700) >= 0 && version.compareTo(vaadin720) < 0){
            // The vaadin-shared-deps.jar is located in the portal lib dir
            classpathEntries.add(ControlPanelPortletUtil.getVaadinSharedDepsJarLocation());

        } else if(version.compareTo(vaadin720) >= 0 && version.compareTo(vaadin730) < 0) {
            // guava-16.0.1.vaadin1.jar
            classpathEntries.add(ControlPanelPortletUtil.getGuavaJarLocation());
            // streamhtmlparser-jsilver-0.0.10.vaadin1.jar
            classpathEntries.add(ControlPanelPortletUtil.getStreamhtmlparserJsilverJarLocation());

            //serializer
            classpathEntries.add(ControlPanelPortletUtil.getSerializerJarLocation());

        } else if(version.compareTo(vaadin730) >= 0 ) {
            // guava-16.0.1.vaadin1.jar
            classpathEntries.add(ControlPanelPortletUtil.getGuavaJarLocation());
            // streamhtmlparser-jsilver-0.0.10.vaadin1.jar
            classpathEntries.add(ControlPanelPortletUtil.getStreamhtmlparserJsilverJarLocation());
            // flute-1.3.0.gg2.jar
            classpathEntries.add(ControlPanelPortletUtil.getFluteJarLocation());
            // json-0.0.20080701.jar
            classpathEntries.add(ControlPanelPortletUtil.getJsonJarLocation());
            // sac-1.3.jar
            classpathEntries.add(ControlPanelPortletUtil.getSacJarLocation());
            //serializer
            classpathEntries.add(ControlPanelPortletUtil.getSerializerJarLocation());
        }

        // The ant.jar is located in the portal lib dir
        classpathEntries.add(ControlPanelPortletUtil.getAntJarLocation());

        // The validation-api.GA.jar is located in the portal lib dir
        classpathEntries.add(ControlPanelPortletUtil.getValidationApi());

        // The validation-api.GA-sources.jar is located in the portal lib dir
        classpathEntries.add(ControlPanelPortletUtil.getValidationApiSources());

        for (VaadinAddonInfo addon : includeAddons) {
            classpathEntries.add(addon.getJarFile());
        }
        classpathEntries.addAll(additionalDependencies);

        return classpathEntries;
    }

    private Set<String> getIncludeWidgetsets() {
        Set<String> widgetsets = new HashSet<String>();
        if (includeAddons != null) {
            for (VaadinAddonInfo addon : includeAddons) {
                widgetsets.addAll(addon.getWidgetsets());
            }
        }
        widgetsets.add("com.vaadin.DefaultWidgetSet");
        return widgetsets;
    }

    public void terminate() {
        compiler.terminate();
        compilationFinished();
    }

    public void compilationFinished() {
    }
}
