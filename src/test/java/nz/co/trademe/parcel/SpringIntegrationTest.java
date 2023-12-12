package nz.co.trademe.parcel;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {nz.co.trademe.parcel.ParcelApplication.class, IntegrationConfiguration.class}
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@Tag("IntegrationTests")
public @interface SpringIntegrationTest {
}
