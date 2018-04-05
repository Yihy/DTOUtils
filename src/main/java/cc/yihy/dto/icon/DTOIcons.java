package cc.yihy.dto.icon;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class DTOIcons {
    private static Icon load(String path) {
        return IconLoader.getIcon(path, DTOIcons.class);
    }

    public static final Icon DTOUtilsPlugin = load("/icon/candy_16.png"); // 16x16
}
