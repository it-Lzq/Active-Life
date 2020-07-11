package cn.itlzq.yq.db;


import cn.itlzq.yq.model.bj.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/8 11:43
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
public interface NoteDao extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {

    @Query("select b from Note b ")
    List<Note> findTop(Pageable p);

    @Query("select b from Note b where b.title like ?1 or b.content like ?1")
    Page<Note> findByQuery(Pageable pageable, String query);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Note b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Note b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Note> findByYear(String year);

    @Query("select b from Note b where function('date_format',b.updateTime,'%Y') = ?1 and b.user.id = ?2")
    List<Note> findByYearAndUserId(String year,Long id);

    @Query("select b from Note b where b.user.id = ?1")
    List<Note> findAllByUserId(Long userId);
}
