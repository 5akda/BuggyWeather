package com.example.buggyweather.helper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestRule

open class BaseTest {
	@get:Rule
	var rule: TestRule = InstantTaskExecutorRule()

	@ExperimentalCoroutinesApi
	protected val testCoroutineDispatcher = TestCoroutineDispatcher()

	@ExperimentalCoroutinesApi
	protected fun coroutineScopeTest(testBody: suspend TestCoroutineScope.() -> Unit) {
		testCoroutineDispatcher.runBlockingTest(testBody)
	}

	@ExperimentalCoroutinesApi
	protected fun viewModelScopeTest(testBody: suspend TestCoroutineScope.() -> Unit) {
		Dispatchers.setMain(testCoroutineDispatcher)
		testCoroutineDispatcher.runBlockingTest(testBody)
		Dispatchers.resetMain()
	}
}