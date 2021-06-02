package com.boot.example.configuration;

import com.boot.example.annotation.EnableCache;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

/**
 * com.boot.example.configuration.CacheManagementConfigurationSelector
 *
 * @author lipeng
 * @date 2021/6/2 3:37 PM
 */
public class CacheManagementConfigurationSelector extends AdviceModeImportSelector<EnableCache> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return new String[] {AutoProxyRegistrar.class.getName(),
                        CacheConfiguration.class.getName()};
            default:
                return null;
        }
    }
}
