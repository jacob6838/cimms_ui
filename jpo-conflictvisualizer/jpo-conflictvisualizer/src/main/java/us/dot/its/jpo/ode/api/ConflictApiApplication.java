package us.dot.its.jpo.ode.api;

import java.util.Arrays;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import us.dot.its.jpo.ode.api.accessors.events.LaneDirectionOfTravelEventRepo;
import us.dot.its.jpo.ode.mockdata.MockMapGenerator;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableMongoRepositories
public class ConflictApiApplication extends SpringBootServletInitializer {

	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ConflictApiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ConflictApiApplication.class, args);
		System.out.println("Started Conflict Monitor API");
	}



	


	// @Bean
	// public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	// 	return args -> {

	// 		System.out.println("Let's inspect the beans provided by Spring Boot:");

	// 		String[] beanNames = ctx.getBeanDefinitionNames();
	// 		Arrays.sort(beanNames);
	// 		for (String beanName : beanNames) {
	// 			System.out.println(beanName);
	// 		}

	// 	};
	// }

	// @Bean
	// public NewTopic topic() {
	// 	return TopicBuilder.name("topic1")
	// 		.partitions(1)
	// 		.replicas(1)
	// 		.build();
	// }

	
	

}
