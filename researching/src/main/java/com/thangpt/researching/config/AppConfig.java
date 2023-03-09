package com.thangpt.researching.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.thangpt.researching.common.FileWatcher;
import com.thangpt.researching.services.FileConverter;

@Configuration
@EnableScheduling
public class AppConfig {
    @Value("${app.file.watcher.stp}")
    private String pathToFolder;

    @Bean(name = "modelMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    @Bean(name = "executorService")
    public ExecutorService executorService() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        return executorService;
    }

    @Bean
    public CommandLineRunner schedulingRunner(ExecutorService executor) {
        return new CommandLineRunner() {
            public void run(String... args) throws Exception {
                executor.execute(new FileWatcher(pathToFolder));
                ResourceLoader resourceLoader = new DefaultResourceLoader();
                PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(resourceLoader);
                FileConverter.initTemplates(resolver.getResources("template/*.txt"));
            }
        };
    }

    // @EventListener(ApplicationReadyEvent.class)
    // public void init() throws IOException {
    //     ResourceLoader resourceLoader = new DefaultResourceLoader();
    //     PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(resourceLoader);
    //     FileConverter.initTemplates(resolver.getResources("template/*.txt"));
    // }
}
