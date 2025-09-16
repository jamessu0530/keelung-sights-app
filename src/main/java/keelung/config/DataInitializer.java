package keelung.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import keelung.model.Sight;
import keelung.repository.SightRepository;
import keelung.service.KeelungSightService;

@Component
public class DataInitializer implements ApplicationRunner {

    private final SightRepository sightRepository;
    private final KeelungSightService crawlerService;

    public DataInitializer(SightRepository sightRepository, KeelungSightService crawlerService) {
        this.sightRepository = sightRepository;
        this.crawlerService = crawlerService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (sightRepository.count() == 0) {
            loadData();
        }
    }

    /** 啟動時初始化（爬 → 存） */
    private void loadData() {
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
