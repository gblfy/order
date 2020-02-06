package com.gblfy.order.controller;

import com.gblfy.order.model.Category;
import com.gblfy.order.service.CategoryManager;
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
public class CategoryControllor {

    @Autowired
    private CategoryManager categoryManager;
    @Autowired
    private MQSendUtils mqSendUtils;

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
            mqSendUtils.sendMsg2(category.getCid(), "category");
        }
        return "send Category msg success !!!";
    }
}
