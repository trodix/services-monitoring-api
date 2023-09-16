package com.trodix.monitoring.servicesmonitoringapi.domain.adapters.springactuator;

import com.trodix.monitoring.servicesmonitoringapi.domain.models.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.ServiceStatusAdapter;
import com.trodix.monitoring.servicesmonitoringapi.domain.models.ServiceStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpringActuatorAdapter implements ServiceStatusAdapter {

    private final RestTemplate restTemplate;

    private final SpringActuatorAdapterProperties properties;

    public SpringActuatorAdapter(SpringActuatorAdapterProperties properties) {
        this.restTemplate = new RestTemplateBuilder().build();
        this.properties = properties;
    }

    @Override
    public List<HealthStatus> getServicesStatus() {
        List<String> services = properties.actuatorEndpoints();
        List<HealthStatus> statusList = new ArrayList<>();

        for (String service : services) {
            try {
                ActuatorBody body = restTemplate.getForObject(service + "/health", ActuatorBody.class);
                statusList.add(new HealthStatus(service, body.status().equals("UP") ? ServiceStatus.UP : ServiceStatus.DOWN, ""));
            } catch (Exception e) {
                statusList.add(new HealthStatus(service, ServiceStatus.DOWN, e.getMessage()));
            }
        }

        return statusList;
    }

}
