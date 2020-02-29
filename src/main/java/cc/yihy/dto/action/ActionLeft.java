package cc.yihy.dto.action;

import cc.yihy.dto.util.ActionUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ActionLeft extends AnAction {
    public ActionLeft() {
        // Set the menu item name.
        super("Add ?");
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        ActionUtil.left(event);
    }
}