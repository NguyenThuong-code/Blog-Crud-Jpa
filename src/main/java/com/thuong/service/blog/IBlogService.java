package com.thuong.service.blog;

import com.thuong.model.Blog;
import com.thuong.model.Category;
import com.thuong.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBlogService extends IGeneralService<Blog> {
Iterable<Blog> findAllByCategory(Category category);
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
}
