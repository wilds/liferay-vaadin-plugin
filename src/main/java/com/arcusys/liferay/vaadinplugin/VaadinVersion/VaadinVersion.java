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

import com.arcusys.liferay.vaadinplugin.util.ControlPanelPortletUtil;
import com.arcusys.liferay.vaadinplugin.util.VaadinFileInfo;
import com.arcusys.liferay.vaadinplugin.util.Version;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by igorborisov on 01.10.14.
 */
public class VaadinVersion {

    protected Collection<VaadinFileInfo> vaadinFileInfos;

    String portalPath = ControlPanelPortletUtil.getPortalLibLocationPath();
    String vaadinClientJarsPath = ControlPanelPortletUtil.getVaadinClientJarsLocationPath();
    String libDir = ControlPanelPortletUtil.FileSeparator + "lib" + ControlPanelPortletUtil.FileSeparator;

    //TODO change method

    public Collection<VaadinFileInfo> getVaadinFilesInfo(){

        return new ArrayList<VaadinFileInfo>();
    }

    public static VaadinVersion getVaadinVersion(String vaadinVersion){
        Version currentVersion = new Version(vaadinVersion);
        return getVaadinVersion(currentVersion );
    }

    public static VaadinVersion getVaadinVersion(Version vaadinVersion){

        Version version600 = new Version("6.0.0");
        Version version700 = new Version("7.0.0");
        Version version710 = new Version("7.1.0");
        Version version720 = new Version("7.2.0");
        Version version730 = new Version("7.3.0");

        if (vaadinVersion.compareTo(version600) >= 0 && vaadinVersion.compareTo(version700) < 0) {
            return new VaadinVersion6();
        }else if (vaadinVersion.compareTo(version700) >= 0 && vaadinVersion.compareTo(version710) < 0)  {
            return new VaadinVersion70x();
        }else if (vaadinVersion.compareTo(version710) >= 0 && vaadinVersion.compareTo(version720) < 0)  {
            return new VaadinVersion71x();
        }else if (vaadinVersion.compareTo(version720) >= 0 && vaadinVersion.compareTo(version730) < 0)  {
            return new VaadinVersion72x();
        }else if (vaadinVersion.compareTo(version730) >= 0)  {
            return new VaadinVersion73x();
        }else{
            throw new IllegalArgumentException("Unsupported vaadin version");
        }
    }
}
