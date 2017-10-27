package com.hubesco.jbpm7;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.Configuration;
import bitronix.tm.TransactionManagerServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;

@org.springframework.context.annotation.Configuration
@ImportResource("classpath:jbpm/config.xml")
public class JbpmConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean jbpmEMF() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName("org.jbpm.domain");
        return emf;
    }

    @Bean
    public Configuration btmConfig() {
        return TransactionManagerServices.getConfiguration();
    }

    @Bean(destroyMethod = "shutdown")
    @DependsOn("btmConfig")
    public BitronixTransactionManager BitronixTransactionManager() {
        return TransactionManagerServices.getTransactionManager();
    }

    @Bean
    public JtaTransactionManager jbpmTxManager(BitronixTransactionManager transactionManager, BitronixTransactionManager userTransaction) {
        return new JtaTransactionManager(userTransaction, transactionManager);
    }

}
