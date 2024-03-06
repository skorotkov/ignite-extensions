/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ignite.gatling.builder.cache

import scala.collection.SortedSet

import org.apache.ignite.gatling.action.cache.CacheGetAllAction
import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioContext

/**
 * Cache getAll action builder.
 *
 * @tparam K Type of the cache key.
 * @tparam V Type of the cache value.
 * @param cacheName Cache name.
 * @param keys The collection of the cache entry keys.
 */
class CacheGetAllActionBuilder[K, V](
    cacheName: Expression[String],
    keys: Expression[SortedSet[K]]
) extends ActionBuilder
    with CacheActionCommonParameters
    with CheckParameters[K, V] {

    /**
     * Builds an action.
     *
     * @param ctx The scenario context.
     * @param next The action that will be chained with the Action build by this builder.
     * @return The resulting action.
     */
    override def build(ctx: ScenarioContext, next: Action): Action =
        new CacheGetAllAction(requestName, cacheName, keys, withKeepBinary, withAsync, checks, next, ctx)
}
