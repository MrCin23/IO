package pl.lodz.p.ias.io.zasoby.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ResourceAlertScheduler {

    private final ResourceAlertService resourceAlertService;

    public ResourceAlertScheduler(ResourceAlertService resourceAlertService) {
        this.resourceAlertService = resourceAlertService;
    }

    @Scheduled(fixedRate = 60000)
    public void monitorResources() {
        resourceAlertService.checkLowResourceLevels();
    }
}