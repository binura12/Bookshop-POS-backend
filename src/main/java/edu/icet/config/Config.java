package edu.icet.config;

import edu.icet.util.EncryptionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public EncryptionUtil encryptionUtil(){
        return new EncryptionUtil();
    }
}
