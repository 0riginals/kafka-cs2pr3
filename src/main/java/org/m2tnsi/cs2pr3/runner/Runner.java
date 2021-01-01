package org.m2tnsi.cs2pr3.runner;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.m2tnsi.cs2pr3.classes.Country;
import org.m2tnsi.cs2pr3.classes.Global;
import org.m2tnsi.cs2pr3.service.CountryService;
import org.m2tnsi.cs2pr3.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Component
public class Runner implements CommandLineRunner {

    private static final String KAFKA_BROKER = "localhost:9092";
    private static final String TOPIC_2 = "Topic2";
    private static final String TOPIC_3 = "Topic3";
    private static final String CONSUMER_ID_CONFIG = "covid19";
    private static final int MAX_POLL_RECORDS = 1;

    @Autowired
    private GlobalService globalService;

    @Autowired
    private CountryService countryService;

    @Override
    public void run(String... args) throws Exception {
        consumer();
    }

    public Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "Pr3");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<>(props);
    }

    public Consumer<Long, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_ID_CONFIG);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, MAX_POLL_RECORDS);

        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC_2));
        return consumer;
    }

    public void consumer() {
        Consumer<Long, String> consumer = createConsumer();
        Producer<String, String> pr3 = createProducer();
        Optional<Global> globalOpt = globalService.findFirstByOrderByDateMajDesc();

        while(true) {
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));

            consumerRecords.forEach(record -> {
                String value = record.value();
                switch (value) {
                    case "get_global_values":
                        globalOpt.ifPresent(
                                (global) -> {
                                    System.out.print("Envoi sur Topic 3 -- Global values --: ");
                                    System.out.println(global.toString());
                                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_3,
                                            "get_global_values", global.toString());
                                    pr3.send(producerRecord);
                                }
                        );
                        break;
                    case "get_confirmed_avg":
                        globalOpt.ifPresent(
                                (global) -> {
                                    Timestamp date = global.getDateMaj();
                                    List<Country> countries = countryService.findAllByDateMaj(date);
                                    int nbCountries = countries.size();
                                    int totalConfirmed = 0;
                                    for(Country c : countries) {
                                        totalConfirmed += c.getTotalConfirmed();
                                    }
                                    double result = (double) totalConfirmed / (double) nbCountries;
                                    System.out.print("Envoi sur Topic 3 -- Confirmed avg --: ");
                                    System.out.println(result);
                                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_3,
                                            "get_confirmed_avg", Double.toString(result));
                                    pr3.send(producerRecord);
                                }
                        );
                        break;
                    case "get_deaths_avg":
                        globalOpt.ifPresent(
                                (global) -> {
                                    Timestamp date = global.getDateMaj();
                                    List<Country> countries = countryService.findAllByDateMaj(date);
                                    int nbCountries = countries.size();
                                    int totalDeaths = 0;
                                    for(Country c : countries) {
                                        totalDeaths += c.getTotalDeaths();
                                    }
                                    double result = (double) totalDeaths / (double) nbCountries;
                                    System.out.print("Envoi sur Topic 3 -- Deaths avg --: ");
                                    System.out.println(result);
                                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_3,
                                            "get_deaths_avg", Double.toString(result));
                                    pr3.send(producerRecord);
                                }
                        );
                        break;
                    case "get_countries_deaths_percent":
                        globalOpt.ifPresent(
                                (global) -> {
                                    Timestamp date = global.getDateMaj();
                                    List<Country> countries = countryService.findAllByDateMaj(date);
                                    int totalDeaths = 0;
                                    int totalConfirmed = 0;
                                    for(Country c : countries) {
                                        totalDeaths += c.getTotalDeaths();
                                        totalConfirmed += c.getTotalConfirmed();
                                    }
                                    double result = (double) totalDeaths / (double) totalConfirmed * 100;
                                    System.out.print("Envoi sur Topic 3 -- Countries Deaths Percent --: ");
                                    System.out.println(result);
                                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_3,
                                            "get_countries_deaths_percent", Double.toString(result));
                                    pr3.send(producerRecord);
                                }
                        );
                        break;
                    default:
                        String[] param = value.split(" ");
                        try {
                            if(param[0].equals("get_country_values") && param.length == 2) {
                                String countryCode = param[1];
                                // -- Dans la théorie il est censé n'est retourné qu'un --
                                Optional<Country> countryOpt = countryService.findFirstByCountryCodeOrderByDateMajDesc(countryCode);

                                countryOpt.ifPresent(
                                        (country) -> {
                                            System.out.println("Envoi sur Topic 3 -- Country values " + countryCode + " --: ");
                                            System.out.println(country.toString());
                                            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_3,
                                                    "get_country_values", country.toString());
                                            pr3.send(producerRecord);
                                        }
                                );
                            } else {
                                System.out.println("Commande inconnue");
                            }
                        } catch(ArrayIndexOutOfBoundsException e) {
                            System.out.println("Il manque le paramètre v_pays");
                        }
                        break;
                }
            });
            consumer.commitAsync();
        }
    }
}
