package com.axmor;

import com.axmor.config.WebConfig;
import com.axmor.service.IssueService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import static spark.Spark.port;

/**
 * Application entry point
 */
@Configuration
@ComponentScan({ "com.axmor" })
public class Main {

    public static void main(String[] args) {
        port(80);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        new WebConfig(ctx.getBean(IssueService.class));
        ctx.registerShutdownHook();
        }
    }

