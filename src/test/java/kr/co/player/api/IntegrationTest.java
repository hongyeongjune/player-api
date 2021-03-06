package kr.co.player.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.player.api.domain.shared.ObjectMapperUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = ObjectMapperUtil.objectMapper;
}
