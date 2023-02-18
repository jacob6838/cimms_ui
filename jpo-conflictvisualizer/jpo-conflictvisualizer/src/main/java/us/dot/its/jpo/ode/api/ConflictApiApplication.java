package us.dot.its.jpo.ode.api;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import us.dot.its.jpo.conflictmonitor.monitor.serialization.JsonSerdes;
import us.dot.its.jpo.geojsonconverter.DateJsonMapper;
import us.dot.its.jpo.ode.api.accessors.map.ProcessedMapMongo;
import us.dot.its.jpo.ode.api.accessors.map.ProcessedMapRepo;
import us.dot.its.jpo.ode.api.controllers.MapController;
import us.dot.its.jpo.ode.api.converters.DateConverter;
import us.dot.its.jpo.ode.api.converters.StringToZonedDateTimeConverter;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableMongoRepositories
public class ConflictApiApplication extends SpringBootServletInitializer {

	@Autowired
	ProcessedMapRepo processedMapRepo;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ConflictApiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ConflictApiApplication.class, args);
		System.out.println("Started Conflict Monitor API");
		System.out.println("Conflict Monitor API docs page found here: http://localhost:8081/swagger-ui/index.html");
	}

	// public MongoCustomConversions mongoCustomConversions() {
	// return new MongoCustomConversions(Arrays.asList(new
	// StringToZonedDateTimeConverter()));
	// }

	// public MappingMongoConverter mappingMongoConverter() {
	// MappingMongoConverter converter = new MappingMongoConverter(null, new
	// MongoMappingContext());
	// converter.setCustomConversions(mongoCustomConversions());
	// return converter;
	// }

	// @Bean
	// public ObjectMapper defaultMapper() {
	// 	ObjectMapper objectMapper = DateJsonMapper.getInstance();
	// 	objectMapper.registerModule(new JavaTimeModule());
	// 	return objectMapper;
	// }

	@Bean
	public void test() {
		// String originIP = "{$exists: true}";
		// String startTimeString = "1970-01-00T00:00:00.000000Z";
		// String endTimeString = ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE);
		// System.out.println(startTimeString);
		// System.out.println(endTimeString);
		// System.out.println(originIP);
		// List<ProcessedMapMongo> processedMapMongo = processedMapRepo.getProcessedMap("12109", startTimeString,
		// 		endTimeString);
		// System.out.println(processedMapMongo);
	}

	

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				Properties props = new Properties();
				registry.addMapping("/**").allowedOrigins(props.getCors());
			}

			// public MappingJackson2HttpMessageConverter
			// customJackson2HttpMessageConverter() {
			// System.out.println("Setting Custom Jackson Annotations");
			// MappingJackson2HttpMessageConverter converter = new
			// MappingJackson2HttpMessageConverter();
			// // set your custom object mapper
			// converter.setObjectMapper(customObjectMapper());
			// return converter;
			// }

			// // configure the custom object mapper
			// public ObjectMapper customObjectMapper() {
			// System.out.println("Getting custom Object Mapper");
			// // create and configure your custom object mapper
			// ObjectMapper mapper = DateJsonMapper.getInstance();
			// mapper.registerModule(new JavaTimeModule());
			// return mapper;
			// }

			// // configure the message converters used by Spring
			// public void configureMessageConverters(List<HttpMessageConverter<?>>
			// converters) {
			// // add your custom message converter
			// System.out.println("Adding Configure Message Converter");
			// converters.add(customJackson2HttpMessageConverter());
			// }
		};
	}

	// @Bean
	// public MongoCustomConversions mongoCustomConversions() {

	// return new MongoCustomConversions(
	// Arrays.asList(
	// new JsonSerdes.ProcessedMap().seriale,
	// new BytesToMyClassConverter()));
	// }

	// @Configuration
	// public class WebConfig implements WebMvcConfigurer {

	// 	@Override
	// 	public void addFormatters(FormatterRegistry registry) {
	// 		System.out.println("Added Converter");
	// 		registry.addConverter(String.class, ZonedDateTime.class, new DateConverter());
	// 	}
	// }

}
