package org.maats.eloserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = 'org.maats.eloserver.data')
class Application {

	static void main(String[] args) {
		SpringApplication.run(Application, args)
	}

}
