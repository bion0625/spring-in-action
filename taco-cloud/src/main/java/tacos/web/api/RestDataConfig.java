package tacos.web.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Configuration
public class RestDataConfig {

	private final RepositoryRestConfiguration configuration;

	public RestDataConfig(RepositoryRestConfiguration configuration) {
		this.configuration = configuration;
	}

	@Bean
	public RepresentationModelProcessor<CollectionModel<EntityModel<TacoResource>>> processor() {
		return new RepresentationModelProcessor<CollectionModel<EntityModel<TacoResource>>>() {
			@Override
			public CollectionModel<EntityModel<TacoResource>> process(CollectionModel<EntityModel<TacoResource>> model) {
				WebMvcLinkBuilder method = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(RecentTacoController.class).recentTacos());
				model.add(method
	                    .withRel("recents")
	                    .withHref(applyBasePath(method)));
				return model;
			}
		};
	}
	
	private String applyBasePath(WebMvcLinkBuilder method) {
		URI uri = method.toUri();
		URI newUri = null;
		  try {
		    newUri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), //
		        uri.getPort(), configuration.getBasePath() + uri.getPath(), uri.getQuery(), uri.getFragment());
		  } catch (URISyntaxException e) {
		    e.printStackTrace();
		  }
		  return newUri.toString();
	}
}
