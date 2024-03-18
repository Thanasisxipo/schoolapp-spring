package gr.aueb.cf.schoolapppro.rest;

import gr.aueb.cf.schoolapppro.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapppro.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapppro.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Student;
import gr.aueb.cf.schoolapppro.service.IStudentService;
import gr.aueb.cf.schoolapppro.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.validator.StudentInsertValidator;
import gr.aueb.cf.schoolapppro.validator.StudentUpdateValidator;
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
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentRestController {
    private final IStudentService studentService;
    private final StudentInsertValidator insertValidator;
    private final StudentUpdateValidator updateValidator;

    @Operation(summary = "Add a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/")
    public ResponseEntity<StudentReadOnlyDTO> addStudent(@Valid @RequestBody StudentInsertDTO dto, BindingResult bindingResult) {
        insertValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Student student = studentService.insertStudent(dto);
            StudentReadOnlyDTO readOnlyDTO = Mapper.mapStudentToReadOnly(student);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(readOnlyDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(readOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Operation(summary = "Update a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<StudentReadOnlyDTO> updateStudent(@PathVariable("id") Long id, @Valid @RequestBody StudentUpdateDTO dto, BindingResult bindingResult) {
        if (!Objects.equals(id, dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        updateValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Student student = studentService.updateStudent(dto);
            StudentReadOnlyDTO readOnlyDTO = Mapper.mapStudentToReadOnly(student);
            return new ResponseEntity<>(readOnlyDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a student by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<StudentReadOnlyDTO> deleteStudent(@PathVariable("id") Long id) {
        try {
            Student student = studentService.deleteStudent(id);
            StudentReadOnlyDTO readOnlyDTO = Mapper.mapStudentToReadOnly(student);
            return ResponseEntity.ok(readOnlyDTO);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get students by their lastname starting with initials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid lastname supplied",
                    content = @Content)})
    @GetMapping("")
    public ResponseEntity<List<StudentReadOnlyDTO>> getStudentByLastname(@RequestParam("lastname") String lastname) {
        List<Student> students;
        try {
            students = studentService.getStudentByLastname(lastname);
            List<StudentReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
            for (Student student : students) {
                readOnlyDTOS.add(Mapper.mapStudentToReadOnly(student));
            }
            return new ResponseEntity<>(readOnlyDTOS, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get a student by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<StudentReadOnlyDTO> getTeacher(@PathVariable("id") Long id) {
        try {
            Student student = studentService.getById(id);
            StudentReadOnlyDTO dto = Mapper.mapStudentToReadOnly(student);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
