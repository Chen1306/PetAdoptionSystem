package com.petadopt.controller.admin;

import com.petadopt.common.ApiResponse;
import com.petadopt.dto.PetReviewRequest;
import com.petadopt.entity.Pet;
import com.petadopt.service.PetService;
import com.petadopt.util.AuthContext;
import com.petadopt.vo.LoginUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}
