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
package be.bittich.dynaorm.dialect;

import be.bittich.dynaorm.exception.RequestInvalidException;
import java.util.List;

/**
 *
 * @author Nordine
 */
public interface Dialect {

    static final String REPLACEMENT_VALUE = "? ";
     static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    String selectAll(String tableName);

    String from(String tableName);

    String equalTo(String request, String label) throws RequestInvalidException;

    String andWhere(String request);

    String where(String request);

    String insert(String tableName, List<String> columns, List<String> values);

    String update(String tableName, List<String> columns, List<String> values, String condition);

    String delete(String tableName);

    String type();

    String requestForTableColumns(String tableName);

    String doFilterValue(String fieldVal);
}
