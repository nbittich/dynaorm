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
package be.bittich.dynaorm.maping;

import be.bittich.dynaorm.exception.ColumnNotFoundException;
import be.bittich.dynaorm.exception.RequestInvalidException;
import be.bittich.dynaorm.repository.DynaRepository;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nordine
 */
public class ReflectionMapper implements Serializable {

    private static final long serialVersionUID = 3836286981898895355L;

    /**
     * Load lazily a relation between two entities. First step before concrete
     * mapping
     *
     * @param <E>
     * @param e
     * @param lazyRepo
     * @return
     */
    public static <E> E lazyLoadRelation(E e, DynaRepository<E> lazyRepo) {

        E v = lazyRepo.findById(e);

        return lazyRepo.findById(v);
    }

    /**
     * That method does a basic findBy to get the list of entities related to
     * the field
     *
     * @param <E>
     * @param mapping
     * @param lazyRepo
     * @return
     */
    public static <E> List<E> lazyLoadRelationList(final ForeignKeyMapping<E> mapping, DynaRepository<E> lazyRepo) {

        try {
            List<E> list = lazyRepo.findBy(mapping.getIdMappedBy(), mapping.getId());
            return list;
        } catch (ColumnNotFoundException | RequestInvalidException ex) {
            Logger.getLogger(ReflectionMapper.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

}
