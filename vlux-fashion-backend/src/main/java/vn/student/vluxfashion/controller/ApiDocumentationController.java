package vn.student.vluxfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class ApiDocumentationController {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping("/endpoints")
    public Map<String, String> getAllEndpoints() {
        return requestMappingHandlerMapping.getHandlerMethods().entrySet().stream()
            .collect(Collectors.toMap(
                entry -> entry.getKey().toString(),  // Endpoint path
                entry -> entry.getValue().toString()  // HTTP method
            ));
    }
}
