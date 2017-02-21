package io.behindthemath.testwithlogstub;

import android.util.Log;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class stubs out {@link android.util.Log} for unit testing, using PowerMock and Mockito.
 * All Log.* methods are redirected to System.out.println(), so the output shows in the JUnit console.
 * This allows easy unit testing of code with calls to Log.*, without requiring extensive modification
 * to implement dependency injection, while still showing the output in the console.
 * <p>
 * To implement, subclass the TestWithLogStub class, and add your own methods. Call Log.* as usual,
 * and the output will be redirected to the console.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class TestWithLogStub {
    @Before
    public void setup() {
        PowerMockito.mockStatic(Log.class);

        try {
            // Unstub Log.getStackTraceString() so we can use it to prettify the stack traces
            PowerMockito.doCallRealMethod().when(Log.class, "getStackTraceString", Mockito.any(Throwable.class));

            // Stub for all the methods with the signature (String, String)
            PowerMockitoStubber stubber = PowerMockito.doAnswer(new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {
                    Pattern pattern = Pattern.compile("Log\\.([vdiwe])");
                    Matcher matcher = pattern.matcher(invocation.getMethod().toString());
                    matcher.find();
                    String output = matcher.group(1).toUpperCase() + "/" +
                            invocation.getArguments()[0] + ": " + invocation.getArguments()[1];
                    System.out.println(output);
                    return null;
                }
            });
            stubber.when(Log.class, "v", Mockito.any(String.class), Mockito.any(String.class));
            stubber.when(Log.class, "d", Mockito.any(String.class), Mockito.any(String.class));
            stubber.when(Log.class, "i", Mockito.any(String.class), Mockito.any(String.class));
            stubber.when(Log.class, "w", Mockito.any(String.class), Mockito.any(String.class));
            stubber.when(Log.class, "e", Mockito.any(String.class), Mockito.any(String.class));

            // Stub for all the methods with the signature (String, String, Throwable)
            PowerMockitoStubber stubberWithThrowable = PowerMockito.doAnswer(new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {
                    Pattern pattern = Pattern.compile("log\\.([vdiwe])");
                    Matcher matcher = pattern.matcher(invocation.getMethod().toString());
                    matcher.find();
                    String output = matcher.group(1).toUpperCase() + "/" +
                            invocation.getArguments()[0] + ": " + invocation.getArguments()[1] + "\n" +
                            Log.getStackTraceString((Throwable) invocation.getArguments()[2]);
                    System.out.println(output);
                    return null;
                }
            });
            stubberWithThrowable.when(Log.class, "v", Mockito.any(String.class), Mockito.any(String.class), Mockito.any(Throwable.class));
            stubberWithThrowable.when(Log.class, "d", Mockito.any(String.class), Mockito.any(String.class), Mockito.any(Throwable.class));
            stubberWithThrowable.when(Log.class, "i", Mockito.any(String.class), Mockito.any(String.class), Mockito.any(Throwable.class));
            stubberWithThrowable.when(Log.class, "w", Mockito.any(String.class), Mockito.any(String.class), Mockito.any(Throwable.class));
            stubberWithThrowable.when(Log.class, "e", Mockito.any(String.class), Mockito.any(String.class), Mockito.any(Throwable.class));

            // Stub for Log.w(String, Throwable)
            PowerMockito.doAnswer(new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {
                    String output = invocation.getMethod().toString().toUpperCase() + "/" +
                            invocation.getArguments()[0] + ": " +
                            Log.getStackTraceString((Throwable) invocation.getArguments()[1]);
                    System.out.println(output);
                    return null;
                }
            }).when(Log.class, "w", Mockito.any(String.class), Mockito.any(Throwable.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}