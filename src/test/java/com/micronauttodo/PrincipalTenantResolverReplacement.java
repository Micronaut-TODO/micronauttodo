package com.micronauttodo;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.multitenancy.exceptions.TenantNotFoundException;
import io.micronaut.multitenancy.tenantresolver.PrincipalTenantResolver;
import io.micronaut.multitenancy.tenantresolver.SystemPropertyTenantResolverConfigurationProperties;
import jakarta.inject.Singleton;

import java.io.Serializable;

@Singleton
@Replaces(PrincipalTenantResolver.class)
public class PrincipalTenantResolverReplacement extends PrincipalTenantResolver {

    public @NonNull Serializable resolveTenantIdentifier() {
        try {
            return super.resolveTenantIdentifier();
        } catch (TenantNotFoundException e) {
            String value = System.getProperty(SystemPropertyTenantResolverConfigurationProperties.DEFAULT_SYSTEM_PROPERTY_NAME);
            if (value == null) {
                throw new TenantNotFoundException("System property (" + SystemPropertyTenantResolverConfigurationProperties.DEFAULT_SYSTEM_PROPERTY_NAME + ") not found");
            }
            return value;
        }
    }
}
