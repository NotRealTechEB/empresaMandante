package cl.dgac.empresaMandante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class WebClientConfig {
    @Bean
    public OpenAPI custonApi(){
        return new OpenAPI()
        .info(new Info()
        .title("Api EmpresaMandante")
        .version("1.5")
        .description("Descripcion del sistema")
        );
    }

}
