package com.thuong.controller;

import com.thuong.model.Blog;
import com.thuong.model.Category;
import com.thuong.service.blog.IBlogService;
import com.thuong.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/create-blog")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView=new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }
    @PostMapping("/create-blog")
    public ModelAndView saveBlog(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);
        ModelAndView modelAndView= new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("message", "New blog create successfully");
        return modelAndView;
    }
    @GetMapping("/blogs")
    public ModelAndView listBlogs(@RequestParam("search")Optional<String> search, Pageable pageable){
        Page<Blog> blogs;
        if(search.isPresent()){
            blogs=blogService.findAllByTitleContaining(search.get(),pageable);
        }else {
            blogs=blogService.findAll(pageable);
        }
//        Iterable<Blog> blogs =blogService.findAll();
        ModelAndView modelAndView= new ModelAndView("/blog/list");
        modelAndView.addObject("blogs",blogs);
        return modelAndView;
    }
    @GetMapping("/edit-blog/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Blog> blog =blogService.findById(id);
        if(blog.isPresent()){
            ModelAndView modelAndView=new ModelAndView("/blog/edit");
            modelAndView.addObject("blog",blog.get());
            return modelAndView;

        }else {
            ModelAndView modelAndView=new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-blog")
    public ModelAndView updateBlog(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);
        ModelAndView modelAndView=new ModelAndView("/blog/edit");
        modelAndView.addObject("blog",blog);
        modelAndView.addObject("message", "Blog update successfully");
        return modelAndView;

    }
    @GetMapping("/delete-blog/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Blog> blog= blogService.findById(id);
        if (blog.isPresent()){
            ModelAndView modelAndView= new ModelAndView("/blog/delete");
            modelAndView.addObject("blog", blog.get());
            return modelAndView;
        }else {
            ModelAndView modelAndView=new ModelAndView("error.404");
            return modelAndView;
        }
    }
    @PostMapping("/delete-blog")
    public String deleteBlog(@ModelAttribute("blog") Blog blog){
        blogService.remove(blog.getId());
        return "redirect:blogs";
    }

}
