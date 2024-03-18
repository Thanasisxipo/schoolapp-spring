package gr.aueb.cf.schoolapppro.rest;


import gr.aueb.cf.schoolapppro.dto.SpecialityInsertDTO;
import gr.aueb.cf.schoolapppro.dto.SpecialityReadOnlyDTO;
import gr.aueb.cf.schoolapppro.dto.SpecialityUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Speciality;
import gr.aueb.cf.schoolapppro.service.ISpecialityService;
import gr.aueb.cf.schoolapppro.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.validator.SpecialityInsertValidator;
import gr.aueb.cf.schoolapppro.validator.SpecialityUpdateValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/specialities")
@RequiredArgsConstructor
public class SpecialityRestController {

    private final ISpecialityService specialityService;
    private final SpecialityInsertValidator insertValidator;
    private final SpecialityUpdateValidator updateValidator;

    @Operation(summary = "Add a speciality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Speciality created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecialityReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/")
    public ResponseEntity<SpecialityReadOnlyDTO> addSpeciality(@Valid @RequestBody SpecialityInsertDTO dto, BindingResult bindingResult) {
        insertValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Speciality speciality = specialityService.insertSpeciality(dto);
            SpecialityReadOnlyDTO readOnlyDTO = Mapper.mapSpecialityToReadOnly(speciality);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(readOnlyDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(readOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Operation(summary = "Update a speciality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Speciality updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecialityReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Speciality not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<SpecialityReadOnlyDTO> updateSpeciality(@PathVariable("id") Long id, @Valid @RequestBody SpecialityUpdateDTO dto, BindingResult bindingResult) {
        if (!Objects.equals(id, dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        updateValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Speciality speciality = specialityService.updateSpeciality(dto);
            SpecialityReadOnlyDTO readOnlyDTO = Mapper.mapSpecialityToReadOnly(speciality);
            return new ResponseEntity<>(readOnlyDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a speciality by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Speciality Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecialityReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Speciality not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<SpecialityReadOnlyDTO> deleteSpeciality(@PathVariable("id") Long id) {
        try {
            Speciality speciality = specialityService.deleteSpeciality(id);
            SpecialityReadOnlyDTO readOnlyDTO = Mapper.mapSpecialityToReadOnly(speciality);
            return ResponseEntity.ok(readOnlyDTO);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get specialitie by their lastname starting with initials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specialities Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecialityReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid speciality supplied",
                    content = @Content)})
    @GetMapping("")
    public ResponseEntity<List<SpecialityReadOnlyDTO>> getSpecialityBySpecialityName(@RequestParam("speciality") String speciality) {
        List<Speciality> specialities;
        try {
            specialities = specialityService.getSpecialityBySpeciality(speciality);
            List<SpecialityReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
            for (Speciality specialityName : specialities) {
                readOnlyDTOS.add(Mapper.mapSpecialityToReadOnly(specialityName));
            }
            return new ResponseEntity<>(readOnlyDTOS, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get a speciality by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Speciality Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecialityReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Speciality not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<SpecialityReadOnlyDTO> getSpeciality(@PathVariable("id") Long id) {
        try {
            Speciality speciality = specialityService.getSpecialityById(id);
            SpecialityReadOnlyDTO dto = Mapper.mapSpecialityToReadOnly(speciality);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
