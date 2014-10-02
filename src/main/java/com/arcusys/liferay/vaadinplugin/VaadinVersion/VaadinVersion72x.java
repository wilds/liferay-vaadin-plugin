package com.arcusys.liferay.vaadinplugin.vaadinVersion;

/*
 * #%L
 * Liferay Vaadin Plugin
 * %%
 * Copyright (C) 2013 - 2014 Arcusys Ltd.
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

import com.arcusys.liferay.vaadinplugin.util.VaadinFileInfo;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by igorborisov on 01.10.14.
 */
public class VaadinVersion72x extends VaadinVersion {
    private static final String VAADIN_SERVER_JAR = "vaadin-server.jar";
    private static final String VAADIN_CLIENT_JAR = "vaadin-client.jar";
    private static final String VAADIN_THEMES_JAR = "vaadin-themes.jar";
    private static final String VAADIN_SASS_COMPILER_JAR = "vaadin-sass-compiler.jar";
    private static final String VAADIN_PUSH_JAR = "vaadin-push.jar";
    private static final String VAADIN_SHARED_JAR = "vaadin-shared.jar";
    private static final String VAADIN_CLIENT_COMPILER_JAR = "vaadin-client-compiler.jar";
    private static final String VAADIN_CLIENT_COMPILER_DEPS_JAR = "vaadin-client-compiler-deps.jar";
    private static final String GUAVA_JAR = "guava.vaadin1.jar";
    private static final String STREAM_HTML_PARSER_JSILVER_JAR = "streamhtmlparser-jsilver.vaadin1.jar";
    private static final String JSOUP_JAR = "jsoup.jar";
    private static final String VALIDATON_API = "validation-api.GA.jar";
    private static final String VALIDATON_API_SOURCES = "validation-api.GA-sources.jar";

    private static final String SERIALIZER_JAR = "serializer.jar";

    //removed
    //private static final String VAADIN_THEME_COMPILER_JAR = "vaadin-theme-compiler.jar";
    //private static final String VAADIN_SHARED_DEPS_JAR = "vaadin-shared-deps.jar";

    @Override
    public Collection<VaadinFileInfo> getVaadinFilesInfo(){

        vaadinFileInfos = Arrays.asList(
                new VaadinFileInfo(VAADIN_SERVER_JAR, portalPath, 100),
                new VaadinFileInfo(VAADIN_CLIENT_JAR, vaadinClientJarsPath, 200),
                new VaadinFileInfo(VAADIN_THEMES_JAR, portalPath, 300),
                new VaadinFileInfo(VAADIN_SASS_COMPILER_JAR, portalPath, 400, libDir),
                new VaadinFileInfo(VAADIN_SHARED_JAR, portalPath, 500),
                new VaadinFileInfo(VAADIN_PUSH_JAR, portalPath, 550),
                new VaadinFileInfo(VAADIN_CLIENT_COMPILER_JAR, vaadinClientJarsPath, 700),
                new VaadinFileInfo(VAADIN_CLIENT_COMPILER_DEPS_JAR, vaadinClientJarsPath, 750, libDir),
                new VaadinFileInfo(GUAVA_JAR, vaadinClientJarsPath, 800, libDir),
                new VaadinFileInfo(STREAM_HTML_PARSER_JSILVER_JAR, vaadinClientJarsPath, 900, libDir),
                new VaadinFileInfo(JSOUP_JAR, portalPath, 1000, libDir),
                new VaadinFileInfo(VALIDATON_API, portalPath, 1100, libDir),
                new VaadinFileInfo(VALIDATON_API_SOURCES, portalPath, 1200, libDir),
                new VaadinFileInfo(SERIALIZER_JAR, vaadinClientJarsPath, 1300, libDir)
        );
        return vaadinFileInfos;
    }


}
