package net.bolbat.utils.reflect.proxy;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.bolbat.utils.lang.CastUtils;

/**
 * {@link Proxy} utilities.
 *
 * @author Alexandr Bolbat
 */
public final class ProxyUtils {

	/**
	 * {@link ProxySupport}'s holder.
	 */
	private static final Map<Class<?>, ProxySupport<?>> SUPPORTS = new ConcurrentHashMap<>();

	/**
	 * {@link ProxyHandlerSupport}'s holder.
	 */
	private static final Map<Class<?>, ProxyHandlerSupport> SUPPORTS_BY_HANDLERS = new ConcurrentHashMap<>();

	/**
	 * Static initialization.
	 */
	static {
		registerProxyHandlerSupport(new AdvisedHandlerSupport());
	}

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ProxyUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Register {@link ProxySupport}.
	 * 
	 * @param support
	 *            {@link ProxySupport}
	 */
	public static void registerProxyHandlerSupport(final ProxySupport<?> support) {
		checkArgument(support != null, "support argument is null");

		if (support instanceof ProxyHandlerSupport) {
			SUPPORTS_BY_HANDLERS.put(support.getClass(), (ProxyHandlerSupport) support);
		} else {
			SUPPORTS.put(support.getClass(), support);
		}
	}

	/**
	 * Unwrap proxied target from supported proxy invocation handlers.<br>
	 * {@link ClassCastException} would be thrown if result instance can't be casted to expected type.<br>
	 * {@link ProxyHandlerUnsupportedException} would be thrown if proxy invocation handler is unsupported.
	 *
	 * @param proxy
	 *            proxy instance, can be <code>null</code>
	 * @return expected instance
	 */
	public static <T> T unwrapProxy(final Object proxy) {
		if (proxy == null || !Proxy.isProxyClass(proxy.getClass()))
			return CastUtils.cast(proxy);

		Object result = proxy;
		while (Proxy.isProxyClass(result.getClass())) {
			boolean unwrapped = false;
			for (final ProxySupport<?> support : SUPPORTS.values())
				if (support.getHandlerClass().isAssignableFrom(result.getClass())) {
					final ProxySupport<Object> oSupport = CastUtils.cast(support);
					result = oSupport.getTarget(result);
					unwrapped = true;
					break;
				}

			final InvocationHandler handler = Proxy.getInvocationHandler(result);
			for (final ProxyHandlerSupport support : SUPPORTS_BY_HANDLERS.values())
				if (support.getHandlerClass().isAssignableFrom(handler.getClass())) {
					result = support.getTarget(handler);
					unwrapped = true;
					break;
				}

			if (!unwrapped)
				throw new ProxyHandlerUnsupportedException(handler.getClass());
		}

		return CastUtils.cast(result);
	}

}
