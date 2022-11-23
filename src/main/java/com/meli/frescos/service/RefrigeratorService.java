package com.meli.frescos.service;

import com.meli.frescos.exception.RefrigeratorNotFoundException;
import com.meli.frescos.model.Refrigerator;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.repository.RefrigeratorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RefrigeratorService implements IRefrigeratorService {

    private final RefrigeratorRepository refrigeratorRepository;

    private final ISectionService iSectionService;

    public RefrigeratorService(RefrigeratorRepository refrigeratorRepository, ISectionService iSectionService) {
        this.refrigeratorRepository = refrigeratorRepository;
        this.iSectionService = iSectionService;
    }

    @Override
    public List<Refrigerator> getAll() throws RefrigeratorNotFoundException {
        List<Refrigerator> refrigeratorList = refrigeratorRepository.findAll();
        if (refrigeratorList.isEmpty()) throw new RefrigeratorNotFoundException("Refrigerator not found!");
        return refrigeratorList;
    }

    @Override
    public Refrigerator getById(Long id) throws RefrigeratorNotFoundException {
        return refrigeratorRepository.findById(id).orElseThrow(() -> new RefrigeratorNotFoundException("Refrigerator not found!"));
    }

    @Override
    public Refrigerator getBySectionId(Long sectionId) throws RefrigeratorNotFoundException {
        SectionModel section = iSectionService.getById(sectionId);
        return refrigeratorRepository.findBySection(section).orElseThrow(() -> new RefrigeratorNotFoundException("Refrigerator not found!"));
    }

    @Override
    public Refrigerator save(Refrigerator refrigerator) {
        refrigerator.setAcquired(LocalDateTime.now());
        refrigerator.setTemperature(25D);
        refrigerator.setTurnedOn(false);
        return refrigeratorRepository.save(refrigerator);
    }

    @Override
    public Refrigerator setSection(Long refrigeratorId, Long sectionId) throws RefrigeratorNotFoundException {
        Refrigerator refrigerator = getById(refrigeratorId);
        refrigerator.setSection(iSectionService.getById(sectionId));
        return refrigeratorRepository.save(refrigerator);
    }

    @Override
    public Refrigerator setTemperature(Long refrigeratorId, Double temperature) throws RefrigeratorNotFoundException {
        Refrigerator refrigerator = getById(refrigeratorId);
        refrigerator.setTemperature(temperature);
        refrigerator = refrigeratorRepository.save(refrigerator);
        updateSectionTemperature(refrigerator, temperature);
        return refrigerator;
    }

    @Override
    public Refrigerator setOn(Long refrigeratorId, Boolean turnedOn) throws RefrigeratorNotFoundException {
        Refrigerator refrigerator = getById(refrigeratorId);
        refrigerator.setTurnedOn(turnedOn);
        refrigerator = refrigeratorRepository.save(refrigerator);
        updateSectionTemperature(refrigerator, refrigerator.getTemperature());
        return refrigerator;
    }

    private void updateSectionTemperature(Refrigerator refrigerator, Double temperature) {
        if (refrigerator.getSection()!=null && refrigerator.getTurnedOn()) {
            iSectionService.setTemperature(refrigerator.getSection().getId(), temperature);
        }
    }

    @Override
    public Refrigerator executeRevision(Long refrigeratorId) throws RefrigeratorNotFoundException {
        Refrigerator refrigerator = getById(refrigeratorId);
        refrigerator.setLastRevision(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return refrigeratorRepository.save(refrigerator);
    }
}
