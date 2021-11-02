package com.example.library.library.service;

import com.example.library.library.model.Pod;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PodService {
    List<Pod> getAllPods();

    Pod createNewPod(Pod pod, MultipartFile maintenanceFile);

    Pod updatePod(Pod pod, MultipartFile maintenanceFile);

    Pod getPodById(Long pid);

    void deletePodById(Long pid);

    List<Pod> filterPod(String filterText);
}
