package com.example.usermanagement.config.opa;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="opaAuthorization", url = "${app.opa.authz.url}")
public interface OpaClient {

    @PostMapping("/authz")
    OPADataResponse authorizedToAccessAPI(@RequestBody OPADataRequest opaDataRequest);
}
