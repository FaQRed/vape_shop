package com.example.library.library.service.goods.pod.impl;

import com.example.library.library.model.goods.Pod;
import com.example.library.library.repository.goods.pod.PodRepository;
import com.example.library.library.service.goods.pod.PodService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PodServiceImpl implements PodService {

    PodRepository podRepository;

    @Autowired
    public PodServiceImpl(PodRepository podRepository) {
        this.podRepository = podRepository;
    }

    @Override
    public List<Pod> getAllPods() {
        return podRepository.findAll();
    }

    @Override
    public Pod createNewPod(Pod pod, MultipartFile maintenanceFile) throws ServiceException {
        if (!maintenanceFile.isEmpty()) {
            try {
                byte[] fileBytes = maintenanceFile.getBytes();
                pod.setCover(fileBytes);
            } catch (IOException e) {
                throw new ServiceException("Ошибка добавления фото");
            }
        }
       return podRepository.save(pod);
    }

    @Override
    public Pod updatePod(Pod pod, MultipartFile maintenanceFile) {
        if (!maintenanceFile.isEmpty()) {
            try {
                byte[] fileBytes = maintenanceFile.getBytes();
                pod.setCover(fileBytes);
            } catch (IOException e) {
                pod.setCover(pod.getCover());
            }
        }
        return podRepository.save(pod);
    }

    @Override
    public Pod getPodById(Long pid) {
        return podRepository.findPodByPid(pid);
    }

    @Override
    public void deletePodById(Long pid) {
        podRepository.deleteById(pid);
    }

    @Override
    public List<Pod> filterPod(String filterText) {
        return podRepository.filterPod(filterText);
    }
}
