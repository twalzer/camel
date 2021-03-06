/**
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
package org.apache.camel.component.braintree;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.apache.camel.component.braintree.internal.BraintreeApiName;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.apache.camel.spi.UriPath;
import org.apache.camel.util.ObjectHelper;

/**
 * Component configuration for Braintree component.
 */
@UriParams
public class BraintreeConfiguration {
    private static final String ENVIRONMENT = "environment";
    private static final String MERCHANT_ID = "merchant_id";
    private static final String PUBLIC_KEY  = "public_key";
    private static final String PRIVATE_KEY = "private_key";

    @UriPath
    @Metadata(required = "true")
    private BraintreeApiName apiName;
    @UriPath
    private String methodName;

    @UriParam
    @Metadata(required = "true")
    private String environment;

    @UriParam
    @Metadata(required = "true")
    private String merchantId;

    @UriParam
    @Metadata(required = "true")
    private String publicKey;

    @UriParam
    @Metadata(required = "true")
    private String privateKey;

    @UriParam
    @Metadata(label = "proxy")
    private String proxyHost;

    @UriParam
    @Metadata(label = "proxy")
    private Integer proxyPort;

    public BraintreeApiName getApiName() {
        return apiName;
    }

    /**
     * What kind of operation to perform
     */
    public void setApiName(BraintreeApiName apiName) {
        this.apiName = apiName;
    }

    public String getMethodName() {
        return methodName;
    }

    /**
     * What sub operation to use for the selected operation
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getEnvironment() {
        return ObjectHelper.notNull(environment, ENVIRONMENT);
    }

    /**
     * The environment Either SANDBOX or PRODUCTION
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getMerchantId() {
        return ObjectHelper.notNull(merchantId, MERCHANT_ID);
    }

    /**
     * The merchant id provided by Braintree.
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPublicKey() {
        return ObjectHelper.notNull(publicKey, PUBLIC_KEY);
    }

    /**
     * The public key provided by Braintree.
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return ObjectHelper.notNull(privateKey, PRIVATE_KEY);
    }

    /**
     * The private key provided by Braintree.
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * The proxy host
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    /**
     * The proxy port
     */
    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * Helper method to get and Environment object from its name
     */
    private Environment getBraintreeEnvironment() {
        String name = getEnvironment();

        if (Environment.DEVELOPMENT.getEnvironmentName().equalsIgnoreCase(name)) {
            return Environment.DEVELOPMENT;
        }
        if (Environment.SANDBOX.getEnvironmentName().equalsIgnoreCase(name)) {
            return Environment.SANDBOX;
        }
        if (Environment.PRODUCTION.getEnvironmentName().equalsIgnoreCase(name)) {
            return Environment.PRODUCTION;
        }

        throw new IllegalArgumentException(String.format(
            "Environment should be development, sandbox or production, got %s", name));
    }

    /**
     * Construct a BraintreeGateway from configuration
     */
    BraintreeGateway newBraintreeGateway() {
        final BraintreeGateway gateway = new BraintreeGateway(
            getBraintreeEnvironment(),
            getMerchantId(),
            getPublicKey(),
            getPrivateKey());

        if (ObjectHelper.isNotEmpty(proxyHost) && ObjectHelper.isNotEmpty(proxyPort)) {
            gateway.setProxy(proxyHost, proxyPort);
        }

        return gateway;
    }
}
