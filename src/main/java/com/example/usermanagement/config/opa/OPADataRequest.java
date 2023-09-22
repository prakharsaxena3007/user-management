package com.example.usermanagement.config.opa;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@AllArgsConstructor
@Data
public class OPADataRequest {
    Map<String, Object> input;
}
