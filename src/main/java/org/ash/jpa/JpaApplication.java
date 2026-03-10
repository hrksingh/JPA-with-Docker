package org.ash.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;


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

            Set.of("A", "B").forEach(name -> customerRepository.save(new Customer(null, name)));
            List<Customer> records= customerRepository.findAll();
            records.forEach(IO::println);

//
//            List<Long> ids = records.stream()
//                    .map(Customer::getId)
//                    .collect(Collectors.toList());
//            customerRepository.deleteAllById(ids);
        };
    }

}

interface CustomerRepository extends JpaRepository<Customer, Long>{
}

@Entity
class  Customer{
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId()) && Objects.equals(getName(), customer.getName());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getName());
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
