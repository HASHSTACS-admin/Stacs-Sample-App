package io.stacs.dapp.helloworld.callback;

import com.google.common.collect.ImmutableMap;

import javax.security.auth.callback.CallbackHandler;
import java.util.Map;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
public class SmtCallbackFactory {

    public static Map<String, Class<? extends SmtCallbackHandler>> callbackHandlerMap;

    static {
        ImmutableMap.Builder<String, Class<? extends SmtCallbackHandler>> builder = ImmutableMap.builder();
        builder.put("smtpm-fix-permission-set-1-v1", PermissionCallbackHandler.class);
        callbackHandlerMap = builder.build();
    }

    public static CallbackHandler getInstance(String smtCode) {

        return null;
    }

}
