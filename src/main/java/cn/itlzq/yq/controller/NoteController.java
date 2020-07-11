package cn.itlzq.yq.controller;

import cn.itlzq.yq.model.bj.Note;
import cn.itlzq.yq.model.bj.NoteQuery;
import cn.itlzq.yq.model.bj.User;
import cn.itlzq.yq.service.NoteService;
import cn.itlzq.yq.service.TagService;
import cn.itlzq.yq.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/28 19:39
 * @email 邮箱:905866484@qq.com
 * @description 描述：笔记controller
 */
@Controller
@RequestMapping("/bj")
public class NoteController {
    @Autowired
    private NoteService service;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 跳转到笔记列表
     */
    @GetMapping("/notes")
    public String blogManager(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                              NoteQuery blog,
                              Model model,
                              HttpSession session){
        System.out.println(blog);
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
        Page<Note> blogs = service.listNote(pageable, blog);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogs);
        return "study/NoteManager";
    }
    /**
     * 笔记发布页面
     * @return 发布
     */
    @GetMapping("/noteRelease")
    public String blogRelease(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("note",new Note());
        return "study/NoteRelease";
    }


    /**
     * 编辑笔记
     */
    @GetMapping("/note/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Note note = service.getNote(id);
        note.init();
        //System.out.println(blog);
        model.addAttribute("note",note);
        return "study/NoteRelease";
    }
    /**
     * 删除笔记
     * @param id 博客id
     * @return 页面
     */
    @GetMapping("/note/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes){
        service.deleteNote(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/bj/notes";
    }

    /**
     * 搜索笔记列表
     */
    @RequestMapping("/note/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         NoteQuery blog,
                         Model model){
        Page<Note> notes = service.listNote(pageable, blog);
        model.addAttribute("page",notes);
        return "study/NoteManager :: noteList";
    }

    /**
     * 发布笔记
     */
    @PostMapping("/note")
    public String pulish(Note note, HttpSession session, RedirectAttributes attributes){
        System.out.println(note);
        note.setUser((User)session.getAttribute("user"));
        // blog.setType(typeService.getType(blog.getType().getId()));
        // blog.setTags(tagService.listTag(blog.getTagIds()));
        System.out.println("note:"+note);
        Note b;
        if (note.getId() == null) {
            b =  service.saveNote(note);
            b.setTags(tagService.listTag(note.getTagIds()));
            b = service.updateNote(b.getId(), b);
        } else {
            note.setTags(tagService.listTag(note.getTagIds()));
            b = service.updateNote(note.getId(), note);
        }
        if(b != null){
            attributes.addFlashAttribute("message","发布成功");
        }else{
            attributes.addFlashAttribute("message","发布失败");
        }
        return "redirect:/bj/notes";
    }


}
