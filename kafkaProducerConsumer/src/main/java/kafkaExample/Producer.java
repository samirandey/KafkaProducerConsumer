package kafkaExample;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        String a = "77.179.66.156 - - [25/Oct/2016:14:49:33 +0200] \"GET / HTTP/1.1\" 200 612 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36";
        String b = "77.179.66.157 - - [07/Dec/2016:10:43:23 +0100] \"GET / HTTP/1.1\" 404 571 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36";
        ProducerRecord<String, String> data;
        data = new ProducerRecord<String, String>("testtopic", 0 ,"msg1",a); 
        producer.send(data);
        Thread.sleep(1L);

        data = new ProducerRecord<String, String>("testtopic", 1 ,"msg2",b); 
        producer.send(data);
        Thread.sleep(1L);
        producer.close();
    }
}
