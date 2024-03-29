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

import be.bittich.dynaorm.core.TableColumn;
import be.bittich.dynaorm.exception.ColumnNotFoundException;
import be.bittich.dynaorm.exception.EntityDoesNotExistException;
import be.bittich.dynaorm.exception.RequestInvalidException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nordine
 * @param <T>
 */
public interface DynaRepository<T> extends Serializable {

    List<T> findAll();

    T findById(T t);

    void update(T t);

    Boolean delete(T t) throws EntityDoesNotExistException;

    List<T> findBy(String columnName, String value) throws ColumnNotFoundException, RequestInvalidException;

    TableColumn getTableColumn();
    
    ResultSet executeQuerySQL(String sql,Object...params) throws SQLException;
    
    int executeQueryUpdateSQL(String sql,Object...params) throws SQLException;

}
