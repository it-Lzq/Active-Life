package cn.itlzq.yq.service;


import cn.itlzq.yq.model.bj.Note;
import cn.itlzq.yq.model.bj.NoteQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/8 11:37
 * @email 邮箱:905866484@qq.com
 * @description 描述：博客
 */
public interface NoteService {

    /**
     * 查询单个Blog
     * @param id 根据id
     * @return blog
     */
    Note getNote(Long id);

    /**
     * 分页查询根据条件查询
     * @param note 根据条件查询
     */
    Page<Note> listNote(Pageable pageable, NoteQuery note);

    /**
     *分页查询所有博客
     */
    Page<Note> listNote(Pageable pageable);

    /**
     * 分页查询根据关键字
     * @return 搜索结果
     */
    Page<Note> listNote(Pageable pageable, String query);

    /**
     * 分页查询根据标签
     */
    Page<Note> listNote(Long tagId, Pageable pageable,Long userId);

    /**
     *  添加博客
     * @param note 博客
     */
    Note saveNote(Note note);

    /**
     * 更新博客
     */
    Note updateNote(Long id, Note note);

    /**
     * 删除博客
     */
    void deleteNote(Long id);

    /**
     * 获取博客顶部
     */
    List<Note> listRecommendNoteTop(Integer size);

    /**
     * 转化html
     */
    Note getHtmlNote(Long id);

    /**
     * 年份map
     */
    Map<String, List<Note>> yearNoteMap(Long userId);

    /**
     * 数量
     * @return 数量
     */
    int countNote(Long userId);




}
