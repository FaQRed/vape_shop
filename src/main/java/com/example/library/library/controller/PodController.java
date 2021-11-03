package com.example.library.library.controller;

import com.example.library.library.model.goods.Pod;
import com.example.library.library.service.goods.pod.PodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/references/pods")
public class PodController {
    private static final String POD_TABLE = "references/pod/pod :: pod_list";
    private static final String ERROR_ALERT = "fragments/alert :: alert";
    private static final String EDIT_MODAL = "references/pod/modal/editPod";
    private static final String ADD_MODAL = "references/pod/modal/addPod";

    PodService podService;

    public PodController(PodService podService) {
        this.podService = podService;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<Pod> pods = podService.getAllPods();
        model.addAttribute("pods", pods);
        return "references/pod/pod";
    }

    @GetMapping("/addPod")
    public String addPod(Model model) {
        try {
            model.addAttribute("pod", new Pod());
            return ADD_MODAL;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ADD_MODAL;
        }
    }

    @RequestMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(Long pid) throws IOException {
        Pod pod = podService.getPodById(pid);
        byte[] imageContent = null;
        imageContent = pod.getCover();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/savePod",  consumes = {"multipart/form-data"})
    public String savePod(Pod pod, Model model,  @RequestParam("maintenanceFile") MultipartFile maintenanceFile) {
        try {
            podService.createNewPod(pod, maintenanceFile);
            model.addAttribute("pods", podService.getAllPods());
            model.addAttribute("message", "Под-система успешно добавлена");
            model.addAttribute("alertClass", "alert-success");
            return POD_TABLE;
        } catch (Exception e) {
            model.addAttribute("pods", podService.getAllPods());
            model.addAttribute("message", "Ошибка добавления Под-системы");
            model.addAttribute("alertClass", "alert-danger");
            return POD_TABLE;
        }
    }

    @GetMapping("/edit")
    public String editPod(Long pid, Model model) {
        try {
            Pod pod = podService.getPodById(pid);
            model.addAttribute("pod", pod);
            return EDIT_MODAL;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return EDIT_MODAL;
        }
    }

    @PostMapping("/updatePod")
    public String updatePod(Pod pod, Model model, @RequestParam("maintenanceFile") MultipartFile maintenanceFile) {
        try {
            podService.updatePod(pod, maintenanceFile);
            model.addAttribute("pods", podService.getAllPods());
            model.addAttribute("message", "Под-система успешно изменена");
            model.addAttribute("alertClass", "alert-success");
            return POD_TABLE;
        } catch (Exception e) {
            model.addAttribute("pods", podService.getAllPods());
            model.addAttribute("message", "Ошибка редактирования Под-системы");
            model.addAttribute("alertClass", "alert-danger");
            return POD_TABLE;
        }
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deletePod(Long pid, Model model) {
        try {
            podService.deletePodById(pid);
            model.addAttribute("pods", podService.getAllPods());
            model.addAttribute("message", "Под-система успешно удалена");
            model.addAttribute("alertClass", "alert-success");
            return POD_TABLE;
        } catch (Exception e) {
            model.addAttribute("pods", podService.getAllPods());
            model.addAttribute("message", "Ошибка удаления Под-системы");
            model.addAttribute("alertClass", "alert-danger");
            return POD_TABLE;
        }
    }

    @GetMapping("/filter")
    public String filterPods(String filterText, Model model) {
        List<Pod> filterPods;
        try {
            if (!StringUtils.isBlank(filterText)) {
                filterPods = podService.filterPod(filterText);
            } else {
                filterPods = podService.getAllPods();
            }
            model.addAttribute("pods", filterPods);
            return POD_TABLE;
        } catch (Exception e) {
            System.err.println(e);
            model.addAttribute("pods", podService.getAllPods());
            model.addAttribute("message", "При работе с под-системами произошла ошибка");
            model.addAttribute("alertClass", "alert-danger");
            return POD_TABLE;
        }
    }
}
