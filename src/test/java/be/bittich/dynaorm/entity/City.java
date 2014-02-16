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
package be.bittich.dynaorm.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nordine
 */
public class City implements Entity {

    public City() {
        super();
    }

    private static final long serialVersionUID = 9084121880626161360L;
    private int id;
    private String zip;
    private String name;
    private final List<PrimaryKey> pkz = new ArrayList();

    public City(int id, String zip, String name) {
        super();
        this.id = id;
        PrimaryKey<Integer> pk = new BasicPrimaryKey(id, "id");
        pkz.add(pk);
        this.zip = zip;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTableName() {
        return "city";
    }

    @Override
    public List<PrimaryKey> getPrimaryKeys() {
        return pkz;
    }

}