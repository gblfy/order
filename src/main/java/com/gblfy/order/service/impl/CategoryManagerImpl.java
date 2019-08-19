package com.gblfy.order.service.impl;

import com.gblfy.order.dao.CategoryDAO;
import com.gblfy.order.model.Category;
import com.gblfy.order.service.CategoryManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryManagerImpl implements CategoryManager {


    @Autowired
    CategoryDAO categoryDAO;

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public Category getCategoryById(int cid) {
        List<Category> categories = categoryDAO.getCategoriesById(cid);
        if (categories == null || categories.size() == 0 || categories.size() > 1) {
            return null;
        }

        return categories.get(0);
    }

    public int addCategory(String cname) {
        return categoryDAO.addCategory(cname);
    }

    public int updateCategoryById(int cid, String cname) {
        return categoryDAO.updateCategoryById(cid, cname);
    }

    public int deleteCategoryById(int cid) {
        return categoryDAO.deleteCategoryById(cid);
    }
}
