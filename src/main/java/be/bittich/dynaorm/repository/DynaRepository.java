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
package be.bittich.dynaorm.repository;

import be.bittich.dynaorm.entity.PrimaryKey;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 *
 * @author Nordine
 * @param <T>
 */
public interface DynaRepository<T> extends Serializable {

    List<T> findAll();

    T findById(PrimaryKey... pk);

    T update(T t);

    T delete(T t);

    List<T> findBy(String value, String columnName);

    ResultSetHandler<List<T>> getListHandler();

    ResultSetHandler<T> getHandler();
    
}