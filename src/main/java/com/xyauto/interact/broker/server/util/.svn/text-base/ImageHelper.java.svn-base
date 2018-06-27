package com.xyauto.interact.broker.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shiqm on 2017/3/20.
 */
@Component
public class ImageHelper {

    @Autowired
    private Constants constants;

    public String getImageUrl(String pathUrl) {
        if (pathUrl == null || pathUrl.isEmpty()) {
            return pathUrl;
        }
        if (pathUrl.toLowerCase().startsWith("http://")
                || pathUrl.toLowerCase().startsWith("https://")) {
            return pathUrl;
        }
        if (pathUrl.startsWith("/group1") || pathUrl.startsWith("/group2")) {
            pathUrl = pathUrl.substring(1, pathUrl.length());
        }
        if (pathUrl.startsWith("group1")) {
            pathUrl = constants.getAvatarGroup1Root() + pathUrl;
        }
        if (pathUrl.startsWith("group2")) {
            pathUrl = constants.getAvatarGroup2Root() + pathUrl;
        }
        return pathUrl;
    }

}
