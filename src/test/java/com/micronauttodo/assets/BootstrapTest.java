// Copyright 2024 Object Computing, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

//     http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.micronauttodo.assets;

import com.micronauttodo.MicronautTestNoPersistence;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@MicronautTestNoPersistence
class BootstrapTest {
    @Test
    void testBootstrap(@Client("/") HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        assertDoesNotThrow(() -> client.exchange(HttpRequest.GET("/assets/stylesheets/bootstrap-5.3.3-dist/bootstrap.css")));
        assertDoesNotThrow(() -> client.exchange(HttpRequest.GET("/assets/stylesheets/bootstrap-5.3.3-dist/bootstrap.css.map")));
        assertDoesNotThrow(() -> client.exchange(HttpRequest.GET("/assets/stylesheets/bootstrap-5.3.3-dist/bootstrap.min.css")));
        assertDoesNotThrow(() -> client.exchange(HttpRequest.GET("/assets/stylesheets/bootstrap-5.3.3-dist/bootstrap.min.css.map")));
    }
}
