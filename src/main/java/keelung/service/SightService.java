package keelung.service;

import java.util.List;

import org.springframework.stereotype.Service;

import keelung.model.Sight;
import keelung.repository.SightRepository;

@Service
public class SightService {

    private final SightRepository sightRepository;
    private final KeelungSightService crawlerService;

    // 建構子注入（推薦）
    public SightService(SightRepository sightRepository, KeelungSightService crawlerService) {
        this.sightRepository = sightRepository;
        this.crawlerService = crawlerService;
    }

    /** 依區域查詢 */
    public List<Sight> getSightsByZone(String zone) {
        return sightRepository.findByZone(zone.trim()); 
    }

    /** 新增一筆 */
    public Sight saveSight(Sight sight) {
        return sightRepository.save(sight);
    }

    /** 新增多筆 */
    public List<Sight> saveSights(List<Sight> sights) {
        return sightRepository.saveAll(sights);
    }

    /** 是否為空 */
    public boolean isDatabaseEmpty() {
        return sightRepository.count() == 0;
    }

    /** 啟動時初始化（爬 → 存） */
    public void initializeDatabaseWithCrawledData() {
        String[] zones = {"中正", "七堵", "暖暖", "仁愛", "中山", "安樂", "信義"};
        for (String zone : zones) {
            for (int attempt = 0; attempt < 2; attempt++) {
                try {
                    List<Sight> sights = crawlerService.getSightsByZone(zone);
                    if (!sights.isEmpty()) {
                        sightRepository.saveAll(sights);
                    }
                    break; // 成功就跳出重試迴圈
                } catch (Exception e) {
                    System.err.println("爬取 " + zone + " 區失敗: " + e.getMessage());
                }
            }
        }
    }
}
