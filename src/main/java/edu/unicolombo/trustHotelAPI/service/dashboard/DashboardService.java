package edu.unicolombo.trustHotelAPI.service.dashboard;

import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.BookingStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.StayingStatus;
import edu.unicolombo.trustHotelAPI.domain.repository.*;
import edu.unicolombo.trustHotelAPI.dto.dashboard.DashboardDTO;
import edu.unicolombo.trustHotelAPI.service.ClientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final StayingRepository stayingRepository;
    private final ClientService clientService;
    private final PaymentRepository paymentRepository;
    private final EmployeeRepository employeeRepository;

    public DashboardService(RoomRepository roomRepository,
                            BookingRepository BookingRepository,
                            StayingRepository stayingRepository,
                            ClientService clientService,
                            PaymentRepository paymentRepository,
                            EmployeeRepository employeeRepository){
        this.roomRepository = roomRepository;
        this.bookingRepository = BookingRepository;
        this.stayingRepository = stayingRepository;
        this.clientService = clientService;
        this.paymentRepository = paymentRepository;
        this.employeeRepository = employeeRepository;
    }

    public DashboardDTO getDashboardData() {

        var rooms = roomRepository.findAll(); // ENTIDADES reales
        var bookings = bookingRepository.findAll();
        var stayings = stayingRepository.findAll();
        var clients = clientService.getAllClients();

        long totalRooms = rooms.size();
        long freeRooms = rooms.stream().filter(r -> r.getCurrentState() == RoomStatus.FREE).count();
        long bookedRooms = rooms.stream().filter(r -> r.getCurrentState() == RoomStatus.BOOKED).count();
        long occupiedRooms = rooms.stream().filter(r -> r.getCurrentState() == RoomStatus.OCCUPIED).count();
        long maintenanceRooms = rooms.stream().filter(r -> r.getCurrentState() == RoomStatus.MAINTENANCE).count();

        long totalBookings = bookings.size();
        long pendingBookings = bookings.stream().filter(b -> b.getStatus() == BookingStatus.PENDING).count();
        long confirmedBookings = bookings.stream().filter(b -> b.getStatus() == BookingStatus.CONFIRMED).count();
        long canceledBookings = bookings.stream().filter(b -> b.getStatus() == BookingStatus.CANCELED).count();
        long completedBookings = bookings.stream().filter(b -> b.getStatus() == BookingStatus.COMPLETED).count();

        long activeStayings = stayings.stream().filter(s -> s.getStatus() == StayingStatus.ON_PROGRESS).count();
        long finishedStayings = stayings.stream().filter(s -> s.getStatus() == StayingStatus.FINISHED).count();

        long totalClients = clients.size();

        double monthlyRevenue = paymentRepository.sumByMonth(
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear()
        );

        long activeEmployees = employeeRepository.countByActive(true);

//        long monthlyRevenue = paymentRepository.findAll().stream()
//                .filter(p -> p.getIssueDate().getMonth().equals(LocalDate.now().getMonth()))
//                .mapToDouble(Payment::getTotalAmount)
//                .sum();

        return new DashboardDTO(
                totalRooms,
                freeRooms,
                bookedRooms,
                occupiedRooms,
                maintenanceRooms,

                totalBookings,
                pendingBookings,
                confirmedBookings,
                canceledBookings,
                completedBookings,

                activeStayings,
                finishedStayings,

                totalClients,

                monthlyRevenue,

                activeEmployees
        );
    }
}