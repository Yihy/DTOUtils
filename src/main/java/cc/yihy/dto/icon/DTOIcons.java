package cc.yihy.dto.icon;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class DTOIcons {
    private static Icon load(String path) {
        return IconLoader.getIcon(path, DTOIcons.class);
    }

    /** 16x16 */
    public static final Icon DTO_UTILS_PLUGIN = load("/icon/candy_16.png");
}
