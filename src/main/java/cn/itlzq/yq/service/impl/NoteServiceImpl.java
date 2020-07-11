package cn.itlzq.yq.service.impl;


import cn.itlzq.yq.db.NoteDao;
import cn.itlzq.yq.exception.NotFoundBlogException;
import cn.itlzq.yq.model.bj.Note;
import cn.itlzq.yq.model.bj.NoteQuery;
import cn.itlzq.yq.model.bj.Type;
import cn.itlzq.yq.model.bj.User;
import cn.itlzq.yq.service.NoteService;
import cn.itlzq.yq.util.MarkdownUtils;
import cn.itlzq.yq.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/8 11:41
 * @email 邮箱:905866484@qq.com
 * @description 描述：笔记管理
 */
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDao dao;
    /**
     * 查询单个Blog
     * @param id 根据id
     * @return blog
     */
    @Override
    public Note getNote(Long id) {
        return dao.getOne(id);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Note> listNote(Pageable pageable, NoteQuery note) {
        return  dao.findAll(new Specification<Note>() {
            @Override
            public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(note.getTitle() !=null && !"".equals(note.getTitle())){
                    list.add(cb.like(root.get("title"),"%"+note.getTitle()+"%"));
                }
                if(note.getTypeId() !=null ){
                    list.add(cb.equal(root.<Type>get("type").get("id"),note.getTypeId()));
                }
                if(note.getUserId() !=null ){
                    list.add(cb.equal(root.<User>get("user").get("id"),note.getUserId()));
                }
                cq.where(list.toArray(new Predicate[list.size()]));
                return null;
            }
        },pageable);

    }

    @Override
    public Page<Note> listNote(Pageable pageable) {
        return dao.findAll(pageable);
    }

    /**
     * 分页查询根据关键字
     * @return 搜索结果
     */
    @Override
    public Page<Note> listNote(Pageable pageable, String query) {
        return dao.findByQuery(pageable,query);
    }

    @Override
    public Page<Note> listNote(Long tagId, Pageable pageable,Long userId) {
        return dao.findAll(new Specification<Note>() {
            @Override
            public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(tagId !=null && !"".equals(tagId)){
                    list.add(cb.equal(root.join("tags").get("id"),tagId));
                }
                if(userId !=null ){
                    list.add(cb.equal(root.<User>get("user").get("id"),userId));
                }
                cq.where(list.toArray(new Predicate[list.size()]));
                return null;

            }
        },pageable);

    }
    /**
     * 添加博客
     * @param blog 博客
     */
    @Override
    public Note saveNote(Note blog) {
        System.out.println("service:"+blog);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        System.out.println(blog);
        return dao.save(blog);
    }

    /**
     * 更新博客
     */
    @Override
    @Transient
    public Note updateNote(Long id, Note blog) {
        Note b = dao.getOne(id);
        blog.setUser(b.getUser());
        if(b == null){
            throw new NotFoundBlogException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
         return dao.saveAndFlush(b);
       // return dao.save(b);
    }

    /**
     * 删除博客
     */
    @Override
    @Transient
    public void deleteNote(Long id) {
        dao.deleteById(id);
    }

    /**
     * 获取博客顶部
     */
    @Override
    public List<Note> listRecommendNoteTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
         Pageable pageable = PageRequest.of(0, size, sort);
        return dao.findTop(pageable);
    }

    /**
     * 转化html
     */
    @Override
    public Note getHtmlNote(Long id) {
        Note blog = dao.getOne(id);
        if(blog == null){
            throw new NotFoundBlogException("博客不存在");
        }
        dao.save(blog);
        Note b = new Note();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    /**
     * 年份map
     */
    @Override
    public Map<String, List<Note>> yearNoteMap(Long userId) {
        List<String> years = dao.findGroupYear();
        Map<String, List<Note>> map = new HashMap<>();
        for (String year : years) {
            List<Note> byYear = dao.findByYearAndUserId(year,userId);
            map.put(year,byYear);
        }
        return map;
    }

    /**
     * 数量
     *
     * @return 数量
     */
    @Override
    public int countNote(Long userId) {
        return dao.findAllByUserId(userId).size();
    }
}
