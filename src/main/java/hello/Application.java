package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    CustomerRepository repository;

    @Autowired
    CompanyRepository companyRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        // save a couple of companies and customers
        Company ibm = companyRepository.save(new Company("IBM"));
        Company oracle = companyRepository.save(new Company("Oracle"));
        Company apple = companyRepository.save(new Company("Apple"));
        Company vaadin = companyRepository.save(new Company("Vaadin"));
        repository.save(new Customer("Jack", "Bauer", ibm));
        repository.save(new Customer("Chloe", "O'Brian", oracle));
        repository.save(new Customer("Kim", "Bauer", apple));
        repository.save(new Customer("David", "Palmer", vaadin));
        repository.save(new Customer("Michelle", "Dessler", ibm));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer by ID
        Customer customer = repository.findOne(1L);
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();

        // fetch customers by last name
        System.out.println("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
        System.out.println("--------------------------------------------");
        for (Customer bauer : repository.findByLastNameStartsWithIgnoreCase("Bauer")) {
            System.out.println(bauer);
        }
    }

}
