package gr.aueb.cf.schoolapppro.repository;

import gr.aueb.cf.schoolapppro.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByRoomStartingWith(String room);
    Meeting findMeetingById(Long id);
}
