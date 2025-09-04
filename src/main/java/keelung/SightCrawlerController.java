package keelung;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SightCrawlerController {

    private final SightService sightService;

    public SightCrawlerController(SightService sightService) {
        this.sightService = sightService;
    }

    @GetMapping("/sights")
    public ResponseEntity<?> getSights(@RequestParam("zone") String zone) {
        try {
            List<Sight> sights = sightService.getSightsByZone(zone);
            return ResponseEntity.ok(sights);
        } catch (ZoneNotFoundException e) {
            return ResponseEntity.status(404).body("錯誤: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("伺服器內部錯誤: " + e.getMessage());
        }
    }
}


