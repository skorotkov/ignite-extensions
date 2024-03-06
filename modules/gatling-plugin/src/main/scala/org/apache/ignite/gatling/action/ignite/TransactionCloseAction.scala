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
package org.apache.ignite.gatling.action.ignite

import org.apache.ignite.gatling.action.IgniteAction
import org.apache.ignite.gatling.protocol.IgniteProtocol.TransactionApiSessionKey
import io.gatling.core.action.Action
import io.gatling.core.session.Expression
import io.gatling.core.session.Session
import io.gatling.core.structure.ScenarioContext

/**
 * Action for the transaction close Ignite operation.
 *
 * @param requestName Name of the request.
 * @param next Next action from chain to invoke upon this one completion.
 * @param ctx Scenario context.
 */
class TransactionCloseAction(requestName: Expression[String], next: Action, ctx: ScenarioContext)
    extends IgniteAction("txClose", requestName, ctx, next) {

    override protected def execute(session: Session): Unit =
        for {
            IgniteActionParameters(resolvedRequestName, _, transactionApiOptional) <- resolveIgniteParameters(session)
        } yield {
            logger.debug(s"session user id: #${session.userId}, before $resolvedRequestName $actionType")

            transactionApiOptional.fold {
                next ! session
            } { transactionApi =>
                call(
                    transactionApi.close,
                    actionType,
                    session,
                    updateSession = (session, _: Option[Unit]) => session.remove(TransactionApiSessionKey)
                )
            }
        }
}
