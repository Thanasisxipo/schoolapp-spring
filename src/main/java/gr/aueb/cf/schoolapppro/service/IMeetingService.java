package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.MeetingInsertDTO;
import gr.aueb.cf.schoolapppro.dto.MeetingUpdateDTO;
import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IMeetingService {
    Meeting insertMeeting(MeetingInsertDTO dto) throws Exception;
    Meeting updateMeeting(MeetingUpdateDTO dto) throws EntityNotFoundException;
    Meeting deleteMeeting(Long id) throws EntityNotFoundException;
    List<Meeting> getMeetingByRoom(String room) throws EntityNotFoundException;
    Meeting getMeetingById(Long id) throws EntityNotFoundException;
}
