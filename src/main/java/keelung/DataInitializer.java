package keelung;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final SightService sightService;

    public DataInitializer(SightService sightService) {
        this.sightService = sightService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (sightService.isDatabaseEmpty()) {
            sightService.initializeDatabaseWithCrawledData();
        }
    }
}
