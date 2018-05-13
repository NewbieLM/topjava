package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
  /*  @Autowired
    private Environment env;

    private static Environment environment;

    @PostConstruct
    public void init(){
        environment = env;
    }

    @BeforeClass
    public static void initJpaUtil() {
        Assume.assumeTrue(!isActiveProfilJdbc());
    }

    public static boolean isActiveProfilJdbc() {
        for (String s : environment.getActiveProfiles()) {
            if ("JDBC".equalsIgnoreCase(s))
                return true;
        }
        return false;
    }*/
}