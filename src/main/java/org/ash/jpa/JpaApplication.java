package org.ash.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@SpringBootApplication
public class JpaApplication {

    static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(CustomerRepository customerRepository, JdbcConnectionDetails jdbc){
        return _ -> {

            var details = """
        class: %s
        JDBC: %s
        userName: %s
        password: %s
        """.formatted(
                    jdbc.getClass().getName(),
                    jdbc.getJdbcUrl(),
                    jdbc.getUsername(),
                    jdbc.getPassword()
            );

            IO.println(details);

            customerRepository.deleteAll();

            customerRepository.saveAll(List.of(
                    new Customer("A"),
                    new Customer("B")
            ));

            customerRepository.findAll().forEach(IO::println);

        };
    }

}

interface CustomerRepository extends JpaRepository<Customer, Long>{
}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
class  Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Customer(String name) {
        this.name = name;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return id != null && id.equals(customer.id);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
