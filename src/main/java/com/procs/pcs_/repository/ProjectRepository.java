package com.procs.pcs_.repository;

import com.procs.pcs_.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    @Transactional
    Optional<ProjectEntity> findByIdAndName(Integer integer, String name);

//    @Transactional
//    @Query("select p.id,p.name from ProjectEntity p where p.user.id=:id")
//    Map<Integer, String> findAllByUser(@Param("id") int id);

    @Transactional
    @Query("select p from ProjectEntity p where p.user.id=:id and " +
            "p.user.email=:login and p.id=:pId and " +
            "p.name=:name")
    Optional<ProjectEntity> findByUserAndIdAndName(@Param("id") int id, @Param("login") String login,
                                                    @Param("pId") int pId, @Param("name") String name);


//    void deleteByIdAndName(int id, String name);

//    @Transactional
//    @Query("select p from ProjectEntity p where p.user.id=:id")
//    List<ProjectEntity> findAllByUserId(@Param("id") int id);
}
