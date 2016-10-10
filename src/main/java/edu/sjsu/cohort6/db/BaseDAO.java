/*
 * Copyright (c) 2015 San Jose State University.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package edu.sjsu.cohort6.db;

import java.util.List;

/**
 * @author rwatsh on 11/5/15.
 */
public interface BaseDAO<T> {
    List<String> add(List<T> entityList) throws DBException;
    long remove(List<String> entityIdsList) throws DBException;
    void update(List<T> entityList) throws DBException;

    List<T> fetchById(List<String> entityIdsList, Integer limit) throws DBException;

    List<T> fetch(String jsonQueryString) throws DBException;
}
