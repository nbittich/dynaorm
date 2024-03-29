/*
 * Copyright 2014 Nordine.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.bittich.dynaorm.ioc;

import be.bittich.dynaorm.exception.BeanAlreadyExistException;
import be.bittich.dynaorm.exception.BeanNotFoundException;
import be.bittich.dynaorm.exception.IOCContainerException;
import static be.bittich.dynaorm.facad.IOCFacadGet.getContainer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nordine
 */
public class BasicContainer implements Container {

    private static final long serialVersionUID = -7812658198232446401L;

    private final Map<String, Bean> containerBeans = new HashMap();

    public BasicContainer() {

    }

    /**
     * Add a bean to the container
     *
     * @param <T>
     * @param bean
     * @throws BeanAlreadyExistException
     * @throws IOCContainerException
     */
    @Override
    public <T> void addBean(Bean<T> bean) throws BeanAlreadyExistException, IOCContainerException {
        if (containerBeans.containsValue(bean)) {
            throw new BeanAlreadyExistException("IOC exception: bean already exists");
        }
        if (containerBeans.containsKey(bean.getId())) {
            throw new IOCContainerException("IOC exception: the id should be unique!");
        }
        this.containerBeans.put(bean.getId(), bean);
    }

    /**
     * Remove a bean from the container
     *
     * @param <T>
     * @param id
     * @return
     */
    @Override
    public <T> Bean<T> releaseBean(String id) {
        return containerBeans.remove(id);

    }

    /**
     * The bean is injected by the container
     *
     * @param <T>
     * @param id
     * @return
     * @throws BeanNotFoundException
     */
    @Override
    public <T> T inject(String id) throws BeanNotFoundException {
        if (containerBeans.get(id) == null) {
            throw new BeanNotFoundException("Bean not found");
        }
        Bean<T> bean = containerBeans.get(id);
        Class<T> clazz = bean.getClazz();
        return clazz.cast(containerBeans.get(id).getBean());

    }

    /**
     * Safely inject a bean from the container. If the bean doesn't exist, it
     * returns null
     *
     * @param <T>
     * @param id
     * @return bean
     */
    @Override
    public <T> T injectSafely(String id) {
        T bean = null;
        try {
            bean = this.inject(id);
        } catch (BeanNotFoundException ex) {
            Logger.getLogger(BasicContainer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return bean;
    }

    /**
     * Create a new instance of t. the bean is never saved in the container
     *
     * @param <T>
     * @param tClazz
     * @return
     */
    @Override
    public <T> T newInstance(Class<T> tClazz) {
        try {
            return tClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BasicContainer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return null;
    }

    @Override
    public <T> void registerBean(String label, T t) {
        Bean<T> bean = new Bean(label, t);
        try {
            getContainer().addBean(bean);
        } catch (BeanAlreadyExistException | IOCContainerException ex) {
            Logger.getLogger(BasicContainer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
    }

}
