package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.dto.GuestDto;
import vn.student.vluxfashion.model.Guest;
import vn.student.vluxfashion.repository.GuestRepository;
import vn.student.vluxfashion.response.GuestResponse;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    // Create a new guest
    public GuestResponse createGuest(GuestDto guestDto) {
        Guest guest = new Guest();
        guest.setFullName(guestDto.getFullName());
        guest.setEmail(guestDto.getEmail());
        guest.setPhone(guestDto.getPhone());
        guest.setAddress(guestDto.getAddress());
        guest.setAddress2(guestDto.getAddress2());
        guest.setCity(guestDto.getCity());
        guest.setCreatedAt(new Date());
        guest.setUpdatedAt(new Date());

        Guest savedGuest = guestRepository.save(guest);
        return mapToResponse(savedGuest);
    }

    // Retrieve a guest by ID
    public GuestResponse getGuestById(Integer guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + guestId));
        return mapToResponse(guest);
    }

    // Update a guest by ID
    public GuestResponse updateGuest(Integer guestId, GuestDto guestDto) {
        Guest existingGuest = guestRepository.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + guestId));
        
        existingGuest.setFullName(guestDto.getFullName());
        existingGuest.setEmail(guestDto.getEmail());
        existingGuest.setPhone(guestDto.getPhone());
        existingGuest.setAddress(guestDto.getAddress());
        existingGuest.setAddress2(guestDto.getAddress2());
        existingGuest.setCity(guestDto.getCity());
        existingGuest.setUpdatedAt(new Date()); // Update timestamp

        Guest updatedGuest = guestRepository.save(existingGuest);
        return mapToResponse(updatedGuest);
    }

    // Delete a guest by ID
    public void deleteGuest(Integer guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + guestId));
        guestRepository.delete(guest);
    }

    // Get all guests
    public List<GuestResponse> getAllGuests() {
        List<Guest> guests = guestRepository.findAll();
        return guests.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Convert Guest entity to GuestResponse DTO
    private GuestResponse mapToResponse(Guest guest) {
        return new GuestResponse(
                guest.getGuestId(),
                guest.getFullName(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getAddress(),
                guest.getAddress2(),
                guest.getCity(),
                guest.getCreatedAt(),
                guest.getUpdatedAt()
        );
    }
}
