package com.example.library.library.repository;

import com.example.library.library.model.Pod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PodRepository extends JpaRepository<Pod, Long>, PodRepositoryCustom {

    @Query("select b from Pod b")
    List<Pod> findAll();


    Pod findPodByPid(Long pid);
}
