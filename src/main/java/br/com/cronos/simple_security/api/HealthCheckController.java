package br.com.cronos.simple_security.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping
    public String healthCheck () {
        return  "Simple Security UP" ;
    }
}
