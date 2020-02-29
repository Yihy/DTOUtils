package cc.yihy.dto.action;

import cc.yihy.dto.util.ActionUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ActionRight extends AnAction {

    public ActionRight() {
        // Set the menu item name.
        super("Add To Converter Model");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        ActionUtil.right(event);
    }
}