package edu.unicolombo.trustHotelAPI.service.staying;

import edu.unicolombo.trustHotelAPI.domain.model.Room;
import edu.unicolombo.trustHotelAPI.domain.model.Staying;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.StayingStatus;
import edu.unicolombo.trustHotelAPI.domain.repository.RoomRepository;
import edu.unicolombo.trustHotelAPI.domain.repository.StayingRepository;
import edu.unicolombo.trustHotelAPI.infrastructure.errors.exception.BusinessLogicValidationException;
import edu.unicolombo.trustHotelAPI.service.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
public class CheckOutService {

    // Cola para optimización de CheckOut masivos y su procesamiento
    private final Queue<CheckOutTask> checkOutQueue = new ConcurrentLinkedQueue<>(); // FIFO
    // Pila para deshacer checkouts realizando instantaneas
    private final Stack<StayingSnapshot> undoCheckOutStack = new Stack<>();// LIFO
    @Autowired
    private StayingRepository stayingRepository;
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private RoomRepository roomRepository;

    /** Encola un checkout */
    @Transactional
    public void enqueueCheckOut(Long stayingId) {
        Staying staying = stayingRepository.findById(stayingId).orElseThrow(
                () -> new EntityNotFoundException("La estadia no existen en la base de datos")
        );
        if(!staying.getStatus().equals(StayingStatus.ON_PROGRESS)){
            throw new BusinessLogicValidationException("No es posible realizar un checkout de la estadia: "
                    + staying.getStayingId() + " ya que no se encuentra en Progreso");
        }
        checkOutQueue.add(new CheckOutTask(stayingId));
        log.info("Check-Out encolado para la estadia: {}", stayingId);
    }

    /** Procesa la cola de check-outs (puede ejecutarse automáticamente con @Scheduled o manualmente) */
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void processCheckOutQueue() {
        log.info("Procesando cola de check-outs...");
        while (!checkOutQueue.isEmpty()) {
            CheckOutTask task = checkOutQueue.poll();
            try {
                pushSnapshotToStack(task.stayingId());
                processSingleCheckOut(task.stayingId());
                log.info("Check-out procesado para la estadia  {}", task.stayingId());
            } catch (Exception e) {
                log.error("Error procesando check-out: {}", e.getMessage(), e);
            }
        }
    }

    /** Guarda una instantánea previa antes del check-out */
    private void pushSnapshotToStack(Long stayingId) {
        Staying staying = stayingRepository.findById(stayingId)
                .orElseThrow(() -> new BusinessLogicValidationException("No existe la estadía con el ID: " + stayingId));

        undoCheckOutStack.push(new StayingSnapshot(
                staying.getStayingId(),
                null,
                staying.getStatus(),
                staying.getTotalAmount()
        ));
    }

    /** Deshacer el último check-out */
    @Transactional
    public void undoLastCheckOut() {
        if (undoCheckOutStack.isEmpty()) {
            throw new BusinessLogicValidationException("No hay operaciones a deshacer");
        }

        StayingSnapshot snapshot = undoCheckOutStack.pop();
        Staying stayingToReverse = stayingRepository.getReferenceById(snapshot.stayingId);
        if(LocalDate.now().isAfter(stayingToReverse.getBooking().getEndDate())){
            throw new BusinessLogicValidationException("No se puede deshacer el ultimo checkout debido debido a que se encuentra fuera del rango de fecha de la reserva");
        }
        Room stayingRoom = stayingToReverse.getBooking().getRoom();

        stayingToReverse.setCheckOutDate(snapshot.checkOutDate);
        stayingToReverse.setStatus(snapshot.status);
        stayingToReverse.setTotalAmount(snapshot.totalAmount);
        stayingRoom.setCurrentState(RoomStatus.OCCUPIED);

        stayingRepository.save(stayingToReverse);
        log.info("Check-out revertido para la habitación {}", stayingRoom.getRoomId());
    }

    /** Procesa un solo check-out (una habitación por estadía) */
    private void processSingleCheckOut(Long stayingId) {
        Staying staying = stayingRepository.getReferenceById(stayingId);
        if(staying.getFinalInvoice()==null){
            throw new BusinessLogicValidationException("La estadia no cuenta aún con una factura final por favor genere una para confirmar checkout");
        }
        // TODO: implements the payments for the final invoices
        var finalInvoice = staying.getFinalInvoice();
        finalInvoice.setStatus("PAGADO");
        Room stayingRoom = staying.getBooking().getRoom();
        staying.setCheckOutDate(LocalDate.now());
        staying.setStatus(StayingStatus.FINISHED);
        stayingRoom.setCurrentState(RoomStatus.FREE);
        roomRepository.save(stayingRoom);
        stayingRepository.save(staying);
    }

    /** Clases internas para encapsular las tareas */
    private record CheckOutTask(Long stayingId) {}
    private record StayingSnapshot(long stayingId, LocalDate checkOutDate, StayingStatus status, Double totalAmount) {}
}
