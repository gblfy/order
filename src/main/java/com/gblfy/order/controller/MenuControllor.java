package com.gblfy.order.controller;

import com.gblfy.order.model.Menu;
import com.gblfy.order.service.MenuManager;
import com.gblfy.order.utils.MQSendUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class MenuControllor {

    @Autowired
    private MenuManager menuManager;
    @Autowired
    private MQSendUtils mqSendUtils;

    /**
     * 展示菜品
     *
     * @return
     */
    @RequestMapping(value = "/showMenuList", method = RequestMethod.GET)
    @ResponseBody
    public String showMenuList() {
        List<Menu> menuList = menuManager.getAllMenus();
        for (Menu menu : menuList) {
            log.info("mid = " + menu.getMid());
            //发送消息到MQ的交换机，通知其他系统
            mqSendUtils.sendMsg(menu.getMid(), "menu");
        }
        return "send MenuList msg success !!!";
    }

}
