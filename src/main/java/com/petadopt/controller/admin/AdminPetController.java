package com.petadopt.controller.admin;

import com.petadopt.common.ApiResponse;
import com.petadopt.dto.PetReviewRequest;
import com.petadopt.entity.Pet;
import com.petadopt.service.PetService;
import com.petadopt.util.AuthContext;
import com.petadopt.vo.LoginUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/pets")
public class AdminPetController {

    private final PetService petService;

    public AdminPetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ApiResponse<List<Pet>> list(HttpServletRequest request) {
        AuthContext.getAdmin(request);
        return ApiResponse.success(petService.listAllPets());
    }

    @PostMapping
    public ApiResponse<Long> create(@Validated @RequestBody Pet pet, HttpServletRequest request) {
        AuthContext.getAdmin(request);
        return ApiResponse.success("create success", petService.createPet(pet));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id,
                                    @Validated @RequestBody Pet pet,
                                    HttpServletRequest request) {
        AuthContext.getAdmin(request);
        pet.setId(id);
        petService.updatePet(pet);
        return ApiResponse.success("update success", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthContext.getAdmin(request);
        petService.deletePet(id);
        return ApiResponse.success("delete success", null);
    }

    @PutMapping("/{id}/review")
    public ApiResponse<Void> review(@PathVariable Long id,
                                    @Validated @RequestBody PetReviewRequest request,
                                    HttpServletRequest servletRequest) {
        LoginUser loginUser = AuthContext.getAdmin(servletRequest);
        petService.reviewPet(loginUser, id, request);
        return ApiResponse.success("review success", null);
    }

    @PostMapping("/upload")
    public ApiResponse<String> uploadImage(@RequestParam("file") MultipartFile file,
                                           HttpServletRequest request) {
        AuthContext.getAdmin(request);
        if (file.isEmpty()) {
            return ApiResponse.fail(400, "请选择文件");
        }
        String originalName = file.getOriginalFilename();
        String ext = "";
        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf("."));
        }
        String newName = UUID.randomUUID().toString() + ext;
        Path uploadDir = Paths.get("uploads", "pets");
        try {
            Files.createDirectories(uploadDir);
            file.transferTo(uploadDir.resolve(newName));
        } catch (IOException e) {
            return ApiResponse.fail(500, "文件上传失败");
        }
        String url = "/uploads/pets/" + newName;
        return ApiResponse.success("上传成功", url);
    }
}
