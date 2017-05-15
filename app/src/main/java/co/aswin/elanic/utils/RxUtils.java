package co.aswin.elanic.utils;

import rx.Subscription;

/**
 * Utility class for RxJava
 */
public class RxUtils
{
    public static void unsubscribe(Subscription subscription)
    {
        if (subscription != null)
        {
            if (!subscription.isUnsubscribed())
            {
                subscription.unsubscribe();
            }
        }
    }
}

