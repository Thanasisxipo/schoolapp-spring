package gr.aueb.cf.schoolapppro.rest;

import gr.aueb.cf.schoolapppro.dto.MeetingInsertDTO;
import gr.aueb.cf.schoolapppro.dto.MeetingReadOnlyDTO;
import gr.aueb.cf.schoolapppro.dto.MeetingUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.service.IMeetingService;
import gr.aueb.cf.schoolapppro.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapppro.validator.MeetingInsertValidator;
import gr.aueb.cf.schoolapppro.validator.MeetingUpdateValidator;
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
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingRestController {

    private final IMeetingService meetingService;
    private final MeetingInsertValidator insertValidator;
    private final MeetingUpdateValidator updateValidator;

    @Operation(summary = "Add a meeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meeting created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeetingReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/")
    public ResponseEntity<MeetingReadOnlyDTO> addMeeting(@Valid @RequestBody MeetingInsertDTO dto, BindingResult bindingResult) {
        insertValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Meeting meeting = meetingService.insertMeeting(dto);
            MeetingReadOnlyDTO readOnlyDTO = Mapper.mapMeetingToReadOnly(meeting);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(readOnlyDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(readOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Operation(summary = "Update a meeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeetingReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Meeting not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<MeetingReadOnlyDTO> updateMeeting(@PathVariable("id") Long id, @Valid @RequestBody MeetingUpdateDTO dto, BindingResult bindingResult) {
        if (!Objects.equals(id, dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        updateValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Meeting meeting = meetingService.updateMeeting(dto);
            MeetingReadOnlyDTO readOnlyDTO = Mapper.mapMeetingToReadOnly(meeting);
            return new ResponseEntity<>(readOnlyDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a meeting by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeetingReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Meeting not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<MeetingReadOnlyDTO> deleteMeeting(@PathVariable("id") Long id) {
        try {
            Meeting meeting = meetingService.deleteMeeting(id);
            MeetingReadOnlyDTO readOnlyDTO = Mapper.mapMeetingToReadOnly(meeting);
            return ResponseEntity.ok(readOnlyDTO);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get meetings in room starting with initials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meetings Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeetingReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid room supplied",
                    content = @Content)})
    @GetMapping("")
    public ResponseEntity<List<MeetingReadOnlyDTO>> getMeetingByRoom(@RequestParam("room") String room) {
        List<Meeting> meetings;
        try {
            meetings = meetingService.getMeetingByRoom(room);
            List<MeetingReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
            for (Meeting meeting : meetings) {
                readOnlyDTOS.add(Mapper.mapMeetingToReadOnly(meeting));
            }
            return new ResponseEntity<>(readOnlyDTOS, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get a meeting by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeetingReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Meeting not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<MeetingReadOnlyDTO> getMeeting(@PathVariable("id") Long id) {
        try {
            Meeting meeting = meetingService.getMeetingById(id);
            MeetingReadOnlyDTO dto = Mapper.mapMeetingToReadOnly(meeting);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
