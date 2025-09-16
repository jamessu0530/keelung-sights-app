package keelung.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import keelung.model.Sight;
import keelung.service.SightService;

@RestController
@RequestMapping("/api")
public class SightCrawlerController {
    private final SightService sightService;
    public SightCrawlerController(SightService sightService) {
        this.sightService = sightService;
    }
    @GetMapping("/sights")
    public ResponseEntity<?> getSights(@RequestParam("zone") String zone) {
        // 檢查空字串
        if (zone == null || zone.isBlank()) {
            return ResponseEntity.status(400).body("zone 不能為空");
        } 
        try {
            List<Sight> sights = sightService.getSightsByZone(zone);
            // 如果找不到該區域的景點，回傳 404
            if (sights.isEmpty()) {
                return ResponseEntity.status(404).body("404 Not Found"); 
            }
            
            return ResponseEntity.ok(sights);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("伺服器內部錯誤: " + e.getMessage());
        }
    }
}


