package utils;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jupiter.tools.spring.test.jpa.extension.TraceSqlExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.test.context.TestPropertySource;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(TraceSqlExtension.class)
@TestPropertySource(properties = {"logging.level.org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener=trace",
                                  "logging.level.org.springframework.jdbc.datasource.init.ScriptUtils=trace"})
public @interface TraceSql {

}
