# Test With Log Stub
This is a class that stubs out `android.util.Log` for unit testing, using [PowerMock](<https://github.com/powermock/powermock>) and [Mockito](<http://site.mockito.org/>). All `Log.*` methods are redirected to `System.out.println()`, so the output shows in the JUnit console. This allows easy unit testing of code with calls to `Log.*`, without requiring extensive modification to implement dependency injection, while still showing the output in the console.

## Usage
1. Copy `TestWithLogStub.java` to your project, and rename the package name to match your package.
2. Make sure you have the following lines in the `dependencies` block in your module-level `build.gradle`:
```java
    testCompile 'junit:junit:4.12'
    testCompile 'org.mockito:mockito-core:1.10.19'
    testCompile 'org.powermock:powermock-api-mockito:1.6.6'
    testCompile 'org.powermock:powermock-module-junit4:1.6.6'
```
3. Subclass the TestWithLogStub class, and add your own methods. Call `Log.*` as usual, and the output will be redirected to the console.

**Example:**
```java
package io.behindthemath.test;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestClass extends TestWithLogStub {
    @Test
    public void test() {
        Log.i("TestClass", "This is a test");

        assertEquals(true, true);
    }
}

```
**Output:**
`I/TestClass: This is a test`

## License
Copyright 2017 Behind The Math

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.