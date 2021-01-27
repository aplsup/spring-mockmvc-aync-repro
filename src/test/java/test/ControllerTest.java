package test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@ContextConfiguration(classes = Controller.class)
public class ControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeAll() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new ReadFilter())
            .build();
    }

    /**
     * The higher the number of test run, the higher the chances to get
     * a {@link java.util.ConcurrentModificationException}
     */
    @RepeatedTest(100)
    public void test() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/test"))
            .andExpect(request().asyncStarted());
        MvcResult result = mockMvc.perform(asyncDispatch(resultActions.andReturn()))
            .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
