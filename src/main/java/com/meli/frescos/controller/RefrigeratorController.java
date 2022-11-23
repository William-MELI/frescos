package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.RefrigeratorRequest;
import com.meli.frescos.controller.dto.RefrigeratorResponse;
import com.meli.frescos.exception.RefrigeratorNotFoundException;
import com.meli.frescos.service.IRefrigeratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/refrigerator")
public class RefrigeratorController {

    private final IRefrigeratorService iRefrigeratorService;

    public RefrigeratorController(IRefrigeratorService iRefrigeratorService) {
        this.iRefrigeratorService = iRefrigeratorService;
    }

    @GetMapping
    public ResponseEntity<List<RefrigeratorResponse>> getAll() throws RefrigeratorNotFoundException {
        return new ResponseEntity<>(iRefrigeratorService.getAll().stream().map(RefrigeratorResponse::toResponse).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefrigeratorResponse> getById(@PathVariable Long id) throws RefrigeratorNotFoundException {
        return new ResponseEntity<>(RefrigeratorResponse.toResponse(iRefrigeratorService.getById(id)), HttpStatus.OK);
    }

    @GetMapping("/section/{id}")
    public ResponseEntity<RefrigeratorResponse> getBySectionId(@PathVariable Long id) throws RefrigeratorNotFoundException {
        return new ResponseEntity<>(RefrigeratorResponse.toResponse(iRefrigeratorService.getBySectionId(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RefrigeratorResponse> save(@RequestBody @Valid RefrigeratorRequest refrigeratorRequest) throws RefrigeratorNotFoundException {
        return new ResponseEntity<>(RefrigeratorResponse.toResponse(iRefrigeratorService.save(refrigeratorRequest.toModel())), HttpStatus.CREATED);
    }

    @PatchMapping("/section")
    public ResponseEntity<RefrigeratorResponse> setSection(@RequestParam Long refrigeratorId, @RequestParam Long sectionId) throws RefrigeratorNotFoundException {
        return new ResponseEntity<>(RefrigeratorResponse.toResponse(iRefrigeratorService.setSection(refrigeratorId, sectionId)), HttpStatus.OK);
    }

    @PatchMapping("/turnedon")
    public ResponseEntity<RefrigeratorResponse> setTurnedOn(@RequestParam Long refrigeratorId, @RequestParam boolean turnedOn) throws RefrigeratorNotFoundException {
        return new ResponseEntity<>(RefrigeratorResponse.toResponse(iRefrigeratorService.setOn(refrigeratorId, turnedOn)), HttpStatus.OK);
    }

    @PatchMapping("/temperature")
    public ResponseEntity<RefrigeratorResponse> setSection(@RequestParam Long refrigeratorId, @RequestParam Double temperature) throws RefrigeratorNotFoundException {
        return new ResponseEntity<>(RefrigeratorResponse.toResponse(iRefrigeratorService.setTemperature(refrigeratorId, temperature)), HttpStatus.OK);
    }

    @PatchMapping("/revision")
    public ResponseEntity<RefrigeratorResponse> executeRevision(@RequestParam Long refrigeratorId) throws RefrigeratorNotFoundException {
        return new ResponseEntity<>(RefrigeratorResponse.toResponse(iRefrigeratorService.executeRevision(refrigeratorId)), HttpStatus.OK);
    }

}
