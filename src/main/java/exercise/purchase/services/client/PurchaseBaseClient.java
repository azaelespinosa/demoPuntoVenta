package exercise.purchase.services.client;

import exercise.purchase.model.ConfigEntity;
import exercise.purchase.repositories.RestConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.Optional;

public abstract class PurchaseBaseClient {

    @Autowired
    private RestConfigRepository restConfig;

    protected HttpEntity createHttpEntity(Object body,Long configId) {
        Optional<ConfigEntity> opt = restConfig.findById(configId);
        if (!opt.isPresent()) {
            throw new InvalidParameterException("The configuration does not exist.");
        }
        ConfigEntity conf = opt.get();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        if (conf.getActive()){
            httpHeaders.add("Authorization", conf.getApiKey());
        }

        return new HttpEntity(body,httpHeaders);
    }


}
