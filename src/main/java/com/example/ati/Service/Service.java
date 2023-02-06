package com.example.ati.Service;

import com.example.ati.domain.Pacient;
import com.example.ati.domain.Pat;
import com.example.ati.domain.Type;
import com.example.ati.repository.BedDBRepository;
import com.example.ati.repository.PacientDBRepository;
import com.example.ati.repository.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Service {

    BedDBRepository bedDBRepository;
    PacientDBRepository pacientDBRepository;

    public Service(BedDBRepository bedDBRepository, PacientDBRepository pacientDBRepository) {
        this.bedDBRepository = bedDBRepository;
        this.pacientDBRepository = pacientDBRepository;
    }

    public List<Integer> getOccupied_TIC_TIIP_TIM_Beds() {
        List<Integer> numbers = new ArrayList<>();
        AtomicInteger occupied = new AtomicInteger();
        AtomicInteger tic = new AtomicInteger();
        AtomicInteger tiip = new AtomicInteger();
        AtomicInteger tim = new AtomicInteger();
        bedDBRepository.getAll().values().forEach(bed -> {
            if (bed.getPacient() != null) {
                occupied.getAndIncrement();
            }
            else {
                if (bed.getTip().equals(Type.TIC)) {
                    tic.getAndIncrement();
                }
                if (bed.getTip().equals(Type.TIIP)) {
                    tiip.getAndIncrement();
                }
                if (bed.getTip().equals(Type.TIM)) {
                    tim.getAndIncrement();
                }
            }

        });
        numbers.add(occupied.get());
        numbers.add(tic.get());
        numbers.add(tiip.get());
        numbers.add(tim.get());
        return numbers;
    }

    public List<Pacient> getAllPacients() {
        return pacientDBRepository.getAll().values().stream().toList();
    }

    public List<Pacient> getAllPacientsSortedByGravityAndNotInBed() {
           return pacientDBRepository.getAll().values().stream().filter(pacient -> !pacient.getInBed()).sorted((p1, p2) -> p2.getGravity().compareTo(p1.getGravity())).toList();
        }

    public void updateBed(Pat bed) throws RepositoryException {
        bedDBRepository.update(bed);
    }

    public Pat getOneUnoccupiedBedByType(Type type) {
        return bedDBRepository.getAll().values().stream().filter(bed -> bed.getTip().equals(type) && bed.getPacient() == null).findFirst().orElse(null);
    }

    public Pat getOneUnoccupiedBedByTypeAndVentilation(Type type) {
        return bedDBRepository.getAll().values().stream().filter(bed -> bed.getTip().equals(type) && bed.getPacient() == null && bed.isVentilation()).findFirst().orElse(null);
    }

    public void updatePacient(Pacient pacient) throws RepositoryException {
        pacientDBRepository.update(pacient);
    }

    public Pat getBedByCNP(Long cnp) {
       return bedDBRepository.getAll().values().stream().filter(bed -> bed.getPacient() != null && bed.getPacient().getId().equals(cnp)).findFirst().orElse(null);

    }
}
