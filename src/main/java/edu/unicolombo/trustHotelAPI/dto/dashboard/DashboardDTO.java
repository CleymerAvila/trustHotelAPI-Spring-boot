package edu.unicolombo.trustHotelAPI.dto.dashboard;

public record DashboardDTO(

        // Rooms
        long totalRooms,
        long freeRooms,
        long bookedRooms,
        long occupiedRooms,
        long maintenanceRooms,

        // Bookings
        long totalBookings,
        long pendingBookings,
        long confirmedBookings,
        long canceledBookings,
        long completedBookings,

        // Stayings
        long activeStayings,
        long finishedStayings,

        // Clients
        long totalClients,

        // Revenue
        double monthlyRevenue,

        // Employees
        long activeEmployees
) {}
