package keelung.service;

import java.util.List;

import org.springframework.stereotype.Service;

import keelung.model.Sight;
import keelung.repository.SightRepository;

@Service
public class SightService {

    private final SightRepository sightRepository;

    // 建構子注入（推薦）
    public SightService(SightRepository sightRepository) {
        this.sightRepository = sightRepository;
    }

    /** 依區域查詢 */
    public List<Sight> getSightsByZone(String zone) {
        return sightRepository.findByZone(zone.trim()); 
    }

}
