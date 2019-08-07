package com.zl.wxgzh.menu;

public class MenuUtil {
    public Menu getMenu(){
        ClickButton clickButton = new ClickButton();
        clickButton.setName("功能图文");
        clickButton.setKey("function");
        clickButton.setType("click");
        Menu menu = new Menu();
        Button[] buttons = {clickButton};
        menu.setButtons(buttons);
        return menu;
    }
}
