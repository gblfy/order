package com.gblfy.order.service.impl;

import com.gblfy.order.dao.MenuDAO;
import com.gblfy.order.model.Menu;
import com.gblfy.order.service.MenuManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MenuManagerImpl implements MenuManager {

    @Autowired
    MenuDAO menuDAO;

    public List<Menu> getAllMenus() {
        return menuDAO.getAllMenus();
    }

    public List<Menu> getMenusByMidCid(String mid, String cid) {
        return menuDAO.getMenuByMidCid(mid, cid);
    }

    public Menu getMenuByMid(String mid) {
        List<Menu> menus = menuDAO.getMenuByMidCid(mid, "%");

        if (menus == null) { //如果是空直接返回null
            log.info("查询menu无返回接口，请检查后台是否出错！");
            return null;
        }

        int size = menus.size();

        if (size == 0) {
            log.info("菜品查询返回结果为空 mid = " + mid);
            return null;
        } else if (size > 1) {
            log.error("DB mid 重复 mid = " + mid);
            return null;
        }

        return menus.get(0);
    }

    public int addMenu(int cid, String mname, float price) {
        log.info("添加菜品 cid = " + cid + ", mname = " + mname + ", price = " + price);
        return menuDAO.addMenu(cid, mname, price);
    }

    public int updateMenuByMid(int mid, int cid, String mname, float price) {
        log.info("更新菜品详情 mid = " + mid + ", cid = " + cid + ", mname = " + mname + ", price = " + price);
        return menuDAO.updateMenuByMid(mid, cid, mname, price);
    }

    public int deleteMenuByMid(int mid) {
        int inpactRowNum = menuDAO.deleteMenuByMid(mid);
        if (inpactRowNum == 1) {
            log.info("对应菜品已被删除，mid = " + mid);
        } else {
            log.info("对应菜品删除失败, mid = " + mid);
        }
        return inpactRowNum;
    }
}
