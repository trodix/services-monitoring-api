package com.trodix.monitoring.servicesmonitoringapi.domain.adapters.http;

import com.trodix.monitoring.servicesmonitoringapi.domain.models.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.ServiceStatusAdapter;
import com.trodix.monitoring.servicesmonitoringapi.domain.models.ServiceStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class HttpAdapter implements ServiceStatusAdapter  {

    private final RestTemplate restTemplate;

    private final HttpAdapterProperties properties;

    public HttpAdapter(HttpAdapterProperties properties) {
        this.restTemplate = new RestTemplateBuilder().build();
        this.properties = properties;
    }

    @Override
    public List<HealthStatus> getServicesStatus() {
        List<String> services = properties.httpEndpoints();
        List<HealthStatus> statusList = new ArrayList<>();

        for (String service : services) {
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(service + "/health", String.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    statusList.add(new HealthStatus(service, ServiceStatus.UP, "Status code: " + response.getStatusCode()));
                } else {
                    statusList.add(new HealthStatus(service, ServiceStatus.DOWN, "Status code: " + response.getStatusCode()));
                }
            } catch (Exception e) {
                statusList.add(new HealthStatus(service, ServiceStatus.DOWN, "Error: " + e.getMessage()));
            }
        }

        return statusList;
    }

}
