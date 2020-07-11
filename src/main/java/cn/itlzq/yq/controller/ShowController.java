package cn.itlzq.yq.controller;

import cn.itlzq.yq.model.bj.*;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/29 0:27
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
@RequestMapping("/bj")
public class ShowController {

    @Resource
    private NoteService service;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;

    /**
     * 首页
     * @return 首页
     */
    @GetMapping("/note-index")
    public String index(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model,
                        HttpSession session){
        User user = (User) session.getAttribute("user");
        NoteQuery query = new NoteQuery();
        query.setUserId(user.getId());
        List<Type> types = typeService.listType(6);
        List<Tag> tags = tagService.listTag(10);
        model.addAttribute("page",service.listNote(pageable,query));
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        return "study/note_index";
    }

    /**
     * 分类展示
     * @return 分类
     */
    @GetMapping("/type/{id}")
    public String blogClass(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                            @PathVariable Long id,
                            Model model,
                            HttpSession session){
        List<Type> types = typeService.listType(30);
        if(id == -1){
            id = types.get(0).getId();
        }
        User user = (User) session.getAttribute("user");
        NoteQuery bq = new NoteQuery();
        bq.setTypeId(id);
        bq.setUserId(user.getId());
        Page<Note> page = service.listNote(pageable, bq);
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        model.addAttribute("activeId",id);
        return "study/NoteClass";
    }


    /**
     * 标签展示
     * @return 标签
     */
    @GetMapping("/tag/{id}")
    public String blogTag(@PageableDefault(size = 8 ,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                          @PathVariable Long id,
                          Model model,
                          HttpSession session){
        List<Tag> tags = tagService.listTag(100);
        if(id == -1){
            id = tags.get(0).getId();
        }
        User user = (User) session.getAttribute("user");
        Page<Note> blogs = service.listNote(id, pageable,user.getId());
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogs);
        model.addAttribute("activeId",id);
        return "study/NoteTag";
    }

    /**
     * 归档展示
     * @return 归档
     */
    @GetMapping("/home")
    public String blogHome(Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        Map<String, List<Note>> yearNoteMap = service.yearNoteMap(user.getId());
        int noteCount = service.countNote(user.getId());
        model.addAttribute("yearNoteMap",yearNoteMap);
        model.addAttribute("noteCount",noteCount);
        return "study/NoteHome";
    }

    /**
     * 搜索
     * @param pageable 分页
     * @param query 关键字
     * @param model 返回模型
     * @return 搜索结果
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",service.listNote(pageable,"%"+query+"%"));
        model.addAttribute("qy",query);
        return "study/search";
    }

    /**
     * 搜索翻页
     */
    @GetMapping("/search")
    public String searchPage(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam String query,
                             Model model){
        model.addAttribute("page",service.listNote(pageable,"%"+query+"%"));
        model.addAttribute("qy",query);
        return "study/search";
    }

    /**
     * 笔记详情
     * @param id 笔记id
     * @param model model
     * @return 详情
     */
    @GetMapping("/note/{id}")
    public String blog(@PathVariable Long id,Model model){
        Note note = service.getHtmlNote(id);
        model.addAttribute("note",note);
        return "study/note";
    }


}
