/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.conf.activiti.runtime.api;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.runtime.api.connector.DefaultServiceTaskBehavior;
import org.activiti.runtime.api.connector.IntegrationContextBuilder;
import org.activiti.runtime.api.connector.IntegrationContextImpl;
import org.activiti.runtime.api.model.IntegrationContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorsAutoConfiguration {

    @Bean
    public Module customizeConnectorObjectMapper(){
        SimpleModule module = new SimpleModule("mapConnectorInterfaces",
                                               Version.unknownVersion());

        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver() {
            //this is a workaround for https://github.com/FasterXML/jackson-databind/issues/2019
            //once version 2.9.6 is related we can remove this @override method
            @Override
            public JavaType resolveAbstractType(DeserializationConfig config,
                                                BeanDescription typeDesc) {
                return findTypeMapping(config,
                                       typeDesc.getType());
            }
        };

        resolver.addMapping(IntegrationContext.class, IntegrationContextImpl.class);
        module.setAbstractTypes(resolver);

        return module;
    }

    @Bean
    public IntegrationContextBuilder integrationContextBuilder() {
        return new IntegrationContextBuilder();
    }

    @Bean(name = DefaultActivityBehaviorFactory.DEFAULT_SERVICE_TASK_BEAN_NAME)
    @ConditionalOnMissingBean(name = DefaultActivityBehaviorFactory.DEFAULT_SERVICE_TASK_BEAN_NAME)
    public DefaultServiceTaskBehavior defaultServiceTaskBehavior(ApplicationContext applicationContext,
                                                                 IntegrationContextBuilder integrationContextBuilder) {
        return new DefaultServiceTaskBehavior(applicationContext,
                                              integrationContextBuilder);
    }


}
