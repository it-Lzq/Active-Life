package cn.itlzq.yq.controller;

import cn.itlzq.yq.model.bj.Tag;
import cn.itlzq.yq.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/28 21:27
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
@RequestMapping("/bj")
public class TagController {

    @Autowired
    private TagService service;

    /**
     * 跳转到类别管理
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){
        model.addAttribute("page",service.listTag(pageable));
        return "study/tags";
    }

    /**
     * 跳转到增加类别
     */
    @GetMapping("/tags/input")
    public String insert(Model model){
        model.addAttribute("tag",new Tag());
        return "study/tag-input";
    }

    @GetMapping("/tag/{id}/input")
    public String updateTag(@PathVariable Long id, Model model){
        model.addAttribute("tag",service.getTag(id));
        return "study/tag-input";
    }

    /**
     * 删除类别
     * @param id 类别id
     * @return 页面
     */
    @GetMapping("/tag/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        service.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/bj/tags";
    }

    /**
     * 增加类别
     * @param tag 类别参数
     * @param result 错误结果
     * @param attributes 重定向参数
     * @return 页面
     */
    @PostMapping("/tags")
    @SuppressWarnings("all")
    public String insert(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        if(service.getTagByName(tag.getName()) != null){
            result.rejectValue("name","nameError","该分类名称已经存在");
            return "study/tag-input";
        }
        if(result.hasErrors()){
            return "study/tag-input";
        }

        Tag t = service.insertTag(tag);
        if(t != null){
            attributes.addFlashAttribute("message","添加成功");
        }else{
            attributes.addFlashAttribute("message","添加分类失败");
        }
        return "redirect:/bj/tags";
    }

    /**
     * 修改类别
     * @param tag 类别参数
     * @param result 错误结果
     * @param attributes 重定向参数
     * @return 页面
     */
    @PostMapping("/tags/{id}") @SuppressWarnings("all")
    public String update(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        if(service.getTagByName(tag.getName()) != null){
            result.rejectValue("name","nameError","该分类名称已经存在");
            return "study/tag-input";
        }
        if(result.hasErrors()){
            return "study/tag-input";
        }

        Tag t = service.updateTag(id,tag);
        if(t != null){
            attributes.addFlashAttribute("message","更新成功");
        }else{
            attributes.addFlashAttribute("message","更新分类失败");
        }
        return "redirect:/bj/tags";
    }

}
