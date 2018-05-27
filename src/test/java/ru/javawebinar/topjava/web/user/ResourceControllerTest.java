package ru.javawebinar.topjava.web.user;

import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    public void testCssContentType() throws Exception {
       mockMvc.perform(get("/resources/css/style.css"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(status().is2xxSuccessful())
               .andExpect(content().contentType("text/css"));
    }
}
