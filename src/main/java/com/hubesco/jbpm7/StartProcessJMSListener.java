package com.hubesco.jbpm7;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StartProcessJMSListener {

    private static final Logger logger = LoggerFactory.getLogger(StartProcessJMSListener.class);

    @Autowired
    private RuntimeManager runtimeManager;

    @JmsListener(destination = "startProcessQueue")
    public void processMessage(String content) {
        logger.info("Received message : " + content);
        RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
        KieSession kieSession = runtimeEngine.getKieSession();
        ProcessInstance processInstance = kieSession.startProcess("com.sample");
        logger.info("Process started with id " + processInstance.getId());
    }

}
