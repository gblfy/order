package com.gblfy.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gblfy.order.model.Category;
import com.gblfy.order.model.Menu;
import com.gblfy.order.service.CategoryManager;
import com.gblfy.order.service.MenuManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class OrderControllor {

    @Autowired
    private CategoryManager categoryManager;
    @Autowired
    private MenuManager menuManager;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();

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
            sendMsg(menu.getMid(), "menu");
        }
        return "send MenuList msg success !!!";
    }

    /**
     * 发送MQ消息公用类
     *
     * @param mId
     * @param type
     */
    private void sendMsg(int mId, String type) {
        try {
            //发送消息到MQ的交换机，通知其他系统
            Map<String, Object> msg = new HashMap<String, Object>();
            msg.put("mId", mId);
            msg.put("type", type);
            msg.put("date", System.currentTimeMillis());
            rabbitTemplate.convertAndSend("user." + type, MAPPER.writeValueAsString(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送MQ消息公用类
     *
     * @param cId
     * @param type
     */
    private void sendMsg2(int cId, String type) {
        try {
            //发送消息到MQ的交换机，通知其他系统
            Map<String, Object> msg = new HashMap<String, Object>();
            msg.put("cId", cId);
            msg.put("type", type);
            msg.put("date", System.currentTimeMillis());
            rabbitTemplate.convertAndSend("user." + type, MAPPER.writeValueAsString(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示编辑菜品页
     *
     * @return
     */
    @RequestMapping(value = "/showCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public String showCategoryList() {
        List<Category> categoryList = categoryManager.getAllCategories();
        for (Category category : categoryList) {
            log.info("category = " + category.getCid());
            //发送消息到MQ的交换机，通知其他系统
            sendMsg2(category.getCid(), "category");
        }
        return "send Category msg success !!!";
    }
}
