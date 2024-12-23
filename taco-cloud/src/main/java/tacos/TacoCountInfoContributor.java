package tacos;

import org.springframework.boot.actuate.info.InfoContributor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.stereotype.Component;

import tacos.data.TacoRepository;

@Component
public class TacoCountInfoContributor implements InfoContributor {
	
	private TacoRepository tacoRepo;
	
	public TacoCountInfoContributor(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}
	
	@Override
	public void contribute(Builder builder) {
		long tacoCount = tacoRepo.count();
		Map<String, Object> tacoMap = new HashMap<>();
		tacoMap.put("count", tacoCount);
		builder.withDetail("taco-stats", tacoMap);
	}
}
