package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find room by room number
    Optional<Room> findByRoomNumber(String roomNumber);

    // Check if room number already exists
    boolean existsByRoomNumber(String roomNumber);

    // Find rooms by status
    List<Room> findByStatus(Room.RoomStatus status);

    // Find rooms by type
    List<Room> findByRoomType(Room.RoomType roomType);

    // Find rooms by status and type
    List<Room> findByStatusAndRoomType(Room.RoomStatus status, Room.RoomType roomType);
}