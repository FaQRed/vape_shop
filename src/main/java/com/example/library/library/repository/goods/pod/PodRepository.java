package com.example.library.library.repository.goods.pod;

import com.example.library.library.model.goods.Pod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PodRepository extends JpaRepository<Pod, Long>, PodRepositoryCustom {

    @Query("select b from Pod b")
    List<Pod> findAll();


    Pod findPodByPid(Long pid);
}
