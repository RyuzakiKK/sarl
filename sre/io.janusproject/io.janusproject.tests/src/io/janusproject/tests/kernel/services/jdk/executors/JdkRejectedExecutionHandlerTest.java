/*
 * $Id$
 * 
 * Janus platform is an open-source multiagent platform.
 * More details on http://www.janusproject.io
 * 
 * Copyright (C) 2014-2015 Sebastian RODRIGUEZ, Nicolas GAUD, Stéphane GALLAND.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.janusproject.tests.kernel.services.jdk.executors;

import static org.junit.Assert.assertSame;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import io.janusproject.kernel.services.jdk.executors.JdkRejectedExecutionHandler;
import io.janusproject.services.logging.LogService;
import io.janusproject.tests.testutils.AbstractJanusTest;

import io.sarl.tests.api.Nullable;

/**
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class JdkRejectedExecutionHandlerTest extends AbstractJanusTest {

	@Nullable
	private LogService logger;

	@Nullable
	private JdkRejectedExecutionHandler handler;

	@Before
	public void setUp() {
		this.logger = Mockito.mock(LogService.class);
		Mockito.when(this.logger.isLoggeable(ArgumentMatchers.any(Level.class))).thenReturn(true);

		this.handler = new JdkRejectedExecutionHandler(this.logger);
	}

	@Test
	public void rejectedExecution() {
		Runnable runnable = Mockito.mock(Runnable.class);
		this.handler.rejectedExecution(runnable, null);

		ArgumentCaptor<LogRecord> argument = ArgumentCaptor.forClass(LogRecord.class);
		Mockito.verify(this.logger).log(argument.capture());
		assertSame(Level.SEVERE, argument.getValue().getLevel());
	}

}