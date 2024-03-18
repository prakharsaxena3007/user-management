package com.example.usermanagement.config.opa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class OPAAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private OpaClient opaClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {

            var httpServletRequest = requestAuthorizationContext.getRequest();

            String[] path = httpServletRequest.getRequestURI().replaceAll("^/|/$", "").split("/");
            String jwtToken = httpServletRequest.getHeader("Authorization");
            String token = jwtToken.split(" ")[1];
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));

            JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
            HashMap<String,Object> responseResourceHashMap = objectMapper.readValue(jsonObject.get("realm_access").toString(), new TypeReference<>() {
                    });

            org.json.JSONObject jsonObject1 = new org.json.JSONObject(responseResourceHashMap);
            JSONArray jsonArray = jsonObject1.getJSONArray("roles");
            String role = jsonArray.getString(0);

            String method =  httpServletRequest.getMethod();
            Map<String, Object> input = new HashMap<>();
            input.put("user",jsonObject.get("sub"));
            input.put("method",method);
            input.put("path",path);
            input.put("role",role);

            OPADataResponse opaDataResponse = opaClient.authorizedToAccessAPI(new OPADataRequest(input));
            return new AuthorizationDecision(opaDataResponse.getResult().getAllow());
        }
    }

