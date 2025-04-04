package com.lec.spring.domains.admin.controller;

import com.lec.spring.domains.admin.dto.HopePositionUsageDTO;
import com.lec.spring.domains.admin.dto.StackUsageDTO;
import com.lec.spring.domains.admin.service.AdminService;
import com.lec.spring.domains.banner.entity.Banner;
import com.lec.spring.domains.banner.service.BannerService;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.service.StackService;
import com.lec.spring.domains.user.dto.AdminUserDTO;
import com.lec.spring.domains.user.dto.UserDTO;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.domains.user.repository.UserStacksRepository;
import com.lec.spring.domains.user.service.UserService;
import com.lec.spring.domains.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final UserStacksRepository userStacksRepository;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final UserService userService;
    private final StackService stackService;
    private final BannerService bannerService;

    @GetMapping("/banners")
    public ResponseEntity<List<Banner>> getBanners() {
        return ResponseEntity.ok(bannerService.getAllBanners());
    }
    @GetMapping("/banners/{id}")
    public ResponseEntity<Banner> getBanner(@PathVariable Long id) {
        return ResponseEntity.ok(bannerService.getBannerById(id));
    }
    @PostMapping("/banners")
    public ResponseEntity<Banner> addBanner( @RequestPart("banner") Banner banner,
                                             @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(bannerService.createBanner(banner, file));
    }

    @PutMapping("/banners/{id}")
    public ResponseEntity<Banner> updateBanner(
            @PathVariable Long id,
            @RequestPart("banner") Banner banner,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(bannerService.updateBanner(id, banner, file));
    }

    @DeleteMapping("/banners/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/banners/{id}/activate")
    public ResponseEntity<Void> updateBannerActivation(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        boolean activate = request.get("activate");
        bannerService.updateBannerActivation(id, activate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDTO>> getUsers() {
        return ResponseEntity.ok(adminService.getUsers());
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects() {
        return ResponseEntity.ok(adminService.getProjects());
    }

    @GetMapping("/stacks")
    public ResponseEntity<List<Stack>> getStacks() {
        return ResponseEntity.ok(adminService.getStacks());
    }

    @PostMapping("/stacks")
    public ResponseEntity<Stack> addStack(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        Stack stack = adminService.addStack(name);
        return ResponseEntity.ok(stack);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts() {
        System.out.println("test");
        return ResponseEntity.ok(adminService.getPosts());
    }

    @GetMapping("/recruitmentposts")
    public ResponseEntity<?> getRecruitmentPosts() {
        return ResponseEntity.ok(adminService.getRecruitmentPosts());
    }

    // ✅ 삭제 API


    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        adminService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/stacks/{id}")
    public ResponseEntity<Void> deleteStack(@PathVariable Long id) {
        adminService.deleteStack(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        adminService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/recruitment-posts/{id}")
    public ResponseEntity<Void> deleteRecruitmentPost(@PathVariable Long id) {
        adminService.deleteRecruitmentPost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stacks/usage")
    public List<StackUsageDTO> getStackUsagePercentage() {
        return adminService.getStackUsagePercentage();
    }

    @GetMapping("/hopeposition/usage")
    public List<HopePositionUsageDTO> getHopePositionUsagePercentage() {
        return adminService.getHopePositionUsagePercentage();
    }
}
