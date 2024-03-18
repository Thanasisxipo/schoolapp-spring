package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.MeetingInsertDTO;
import gr.aueb.cf.schoolapppro.dto.MeetingUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.repository.MeetingRepository;
import gr.aueb.cf.schoolapppro.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetingServiceImpl implements IMeetingService {

    private final MeetingRepository meetingRepository;

    @Transactional
    @Override
    public Meeting insertMeeting(MeetingInsertDTO dto) throws Exception {
        Meeting meeting = null;
        try {
            meeting = meetingRepository.save(Mapper.mapToMeeting(dto));
            if (meeting.getId() == null) {
                throw new Exception("Insert error");
            }
            log.info("Insert success for meeting with id " + meeting.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return meeting;
    }

    @Transactional
    @Override
    public Meeting updateMeeting(MeetingUpdateDTO dto) throws EntityNotFoundException {
        Meeting meeting = null;
        Meeting updatedMeeting = null;
        try {
            meeting = meetingRepository.findMeetingById(dto.getId());
            if (meeting == null ) {
                throw new EntityNotFoundException(Meeting.class, dto.getId());
            }
            updatedMeeting = meetingRepository.save(Mapper.mapToMeeting(dto));
            log.info("Meeting with id " + updatedMeeting.getId() + " was updated");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return updatedMeeting;
    }

    @Transactional
    @Override
    public Meeting deleteMeeting(Long id) throws EntityNotFoundException {
        Meeting meeting = null;
        try {
            meeting = meetingRepository.findMeetingById(id);
            if (meeting == null ) {
                throw new EntityNotFoundException(Meeting.class, id);
            }
            meetingRepository.deleteById(id);
            log.info("Meeting with id " + meeting.getId() + " was deleted");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return meeting;
    }

    @Transactional
    @Override
    public List<Meeting> getMeetingByRoom(String room) throws EntityNotFoundException {
        List<Meeting> meetings = new ArrayList<>();
        try {
            meetings = meetingRepository.findByRoomStartingWith(room);
            if (meetings.isEmpty()) {
                throw new EntityNotFoundException(Meeting.class, 0L);
            }
            log.info("Meetings in room starting with " + room + " were found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return meetings;
    }

    @Transactional
    @Override
    public Meeting getMeetingById(Long id) throws EntityNotFoundException {
        Meeting meeting = null;
        try {
            meeting = meetingRepository.findMeetingById(id);
            if (meeting == null ) {
                throw new EntityNotFoundException(Meeting.class, id);
            }
            log.info("Meeting with id  " + id + " was found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return meeting;
    }
}
