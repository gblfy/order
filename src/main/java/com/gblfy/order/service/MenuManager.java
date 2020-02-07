package com.gblfy.order.service;

import com.gblfy.order.pojo.Menu;

import java.util.List;

public interface MenuManager {
     List<Menu> getAllMenus();

     List<Menu> getMenusByMidCid(String mid, String cid);

     Menu getMenuByMid(String mid);

     int addMenu(int cid, String mname, float price);

     int updateMenuByMid(int mid, int cid, String mname, float price);

     int deleteMenuByMid(int mid);
}
