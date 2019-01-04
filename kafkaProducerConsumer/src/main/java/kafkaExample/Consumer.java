package kafkaExample;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.sql.*;  

public class Consumer {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	Class.forName("com.mysql.jdbc.Driver");  
    	Connection con=DriverManager.getConnection(  
    	"jdbc:mysql://localhost:3306/sonoo","root","root");  
    	Statement stmt = con.createStatement();
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        KafkaConsumer consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList("testtopic"));
        int counter = 0;
        while (counter <= 2) {
            ConsumerRecords<String, String> recs = consumer.poll(10);
            if (recs.count() == 0) {
            } else {
                for (ConsumerRecord<String, String> rec : recs) {
                    System.out.printf("Recieved %s: %s", rec.key(), rec.value());
                    String sql = String.format("INSERT INTO access_persist VALUES ('%s','%s')", rec.key(), rec.value());
                    stmt.executeUpdate(sql);
                }
            }
            counter++;
        }
        stmt.close();
    }
}
