package com.petadopt.controller;

import com.petadopt.common.ApiResponse;
import com.petadopt.entity.Pet;
import com.petadopt.service.PetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ApiResponse<List<Pet>> list(@RequestParam(required = false) String type,
                                       @RequestParam(required = false) String keyword) {
        return ApiResponse.success(petService.listPublicPets(type, keyword));
    }

    @GetMapping("/{id}")
    public ApiResponse<Pet> detail(@PathVariable Long id) {
        return ApiResponse.success(petService.getPetDetail(id));
    }
}
