package a.gleb.publisherservice.configuration

import a.gleb.publisherservice.configuration.properties.PublisherSwaggerProperties
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(PublisherSwaggerProperties::class)
class PublisherSwaggerConfiguration {

    @Bean
    fun openApi(buildProperties: BuildProperties): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Publisher service on .kt")
                    .version(buildProperties.version)
            )
    }
}