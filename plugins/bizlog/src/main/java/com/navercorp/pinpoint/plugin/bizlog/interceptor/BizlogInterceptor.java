/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.navercorp.pinpoint.plugin.bizlog.interceptor;

import com.navercorp.pinpoint.bootstrap.context.MethodDescriptor;
import com.navercorp.pinpoint.bootstrap.context.SpanEventRecorder;
import com.navercorp.pinpoint.bootstrap.context.Trace;
import com.navercorp.pinpoint.bootstrap.context.TraceContext;
import com.navercorp.pinpoint.bootstrap.interceptor.AroundInterceptor;
import com.navercorp.pinpoint.bootstrap.logging.PLogger;
import com.navercorp.pinpoint.bootstrap.logging.PLoggerFactory;
import com.navercorp.pinpoint.plugin.bizlog.BizlogPlugin;

/**
 * Gson method interceptor
 *
 * @author ChaYoung You
 */
public class BizlogInterceptor implements AroundInterceptor {
    private final TraceContext traceContext;
    private final MethodDescriptor descriptor;
    private final PLogger logger = PLoggerFactory.getLogger(getClass());

    public BizlogInterceptor(TraceContext traceContext, MethodDescriptor descriptor) {
        this.traceContext = traceContext;
        this.descriptor = descriptor;
    }

    @Override
    public void before(Object target, Object[] args) {
        if (logger.isDebugEnabled()) {
            logger.beforeInterceptor(target, args);
        }

        final Trace trace = traceContext.currentTraceObject();
        if (trace == null) {
            return;
        }

        trace.traceBlockBegin();
System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
/*
    @Override
    public void after(Object target, Object result, Throwable throwable) {
        if (logger.isDebugEnabled()) {
            logger.afterInterceptor(target, null, result, throwable);
        }

        final Trace trace = traceContext.currentTraceObject();
        if (trace == null) {
            return;
        }
System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbb");
        try {
            SpanEventRecorder recorder = trace.currentSpanEventRecorder();
            recorder.recordServiceType(BizlogPlugin.BIZLOG_SERVICE_TYPE);
            recorder.recordApi(descriptor);
            recorder.recordException(throwable);

            recorder.recordAttribute(BizlogPlugin.BIZLOG_ANNOTATION_KEY_INFO, "aabbcc");
        } finally {
            trace.traceBlockEnd();
        }
    }
*/


	@Override
    public void after(Object target, Object[] args, Object result, Throwable throwable) {
        if (logger.isDebugEnabled()) {
            logger.afterInterceptor(target, args);
        }

        Trace trace = traceContext.currentTraceObject();
        if (trace == null) {
            return;
        }

        try {
            SpanEventRecorder recorder = trace.currentSpanEventRecorder();
       	recorder.recordServiceType(BizlogPlugin.BIZLOG_SERVICE_TYPE);
            recorder.recordApi(descriptor);
            recorder.recordException(throwable);
recorder.recordAttribute(BizlogPlugin.BIZLOG_ANNOTATION_KEY_INFO, args[0]);
        } finally {
            trace.traceBlockEnd();
        }
    }






}
