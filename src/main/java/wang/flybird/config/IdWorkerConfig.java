package wang.flybird.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import wang.flybird.utils.idwoker.IdWorker;

@Configuration
public class IdWorkerConfig {

	@Bean(name = "idWorker")
    public IdWorker idWorker(
    		@Value(value = "${idworker.datacenterId}") long datacenterId,
    		@Value(value = "${idworker.workerId}") long workerId,
    		@Value(value = "${idworker.idepoch}") long idepoch){
		IdWorker idWorker = new IdWorker(workerId,datacenterId,0,idepoch);
		return idWorker;
    }
}
