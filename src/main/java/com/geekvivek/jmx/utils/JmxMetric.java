package com.geekvivek.jmx.utils;

import com.geekvivek.jmx.utils.exceptions.MetricNotAvailableException;
import lombok.Getter;

import javax.management.*;
import java.util.Map;
import java.util.Objects;

public class JmxMetric {
    @Getter
    private final String domain;
    @Getter
    private final String type;
    @Getter
    private final String name;
    @Getter
    private final Map<String, String> tags;
    @Getter
    private final String meterName;

    private final ObjectName mBeanObjectName;
    private final MBeanServer mBeanServer;

    public JmxMetric(ObjectName mBeanObjectName, MBeanAttributeInfo attributeInfo, MBeanServer mBeanServer) {
        this.mBeanObjectName = mBeanObjectName;
        this.mBeanServer = mBeanServer;
        this.domain = mBeanObjectName.getDomain();
        this.tags = mBeanObjectName.getKeyPropertyList();
        this.type = this.tags.remove("type");
        this.name = this.tags.remove("name");
        this.meterName = attributeInfo.getName();
    }

    /**
     * returns the current value of this metric in MBeanServer
     *
     * @return returns the current value of this metric in MBeanServer
     * @throws MetricNotAvailableException
     */
    public Object getValue() throws MetricNotAvailableException {
        try {
            return mBeanServer.getAttribute(mBeanObjectName, meterName);
        } catch (JMException e) {
            throw new MetricNotAvailableException("metric : " + mBeanObjectName + " is not available now");
        }
    }

    /**
     * Register a value change listener for this metric.
     *
     * @param listener listener object to be called when any value change event occurs.
     * @param context  this context object will be passed to
     *                 {@link NotificationListener#handleNotification(Notification, Object) handleNotification}
     *                 function when a notification is emitted
     * @throws InstanceNotFoundException
     */
    public void addNotificationListener(NotificationListener listener, Object context) throws InstanceNotFoundException {
        mBeanServer.addNotificationListener(this.mBeanObjectName, listener, null, context);
    }

    /**
     * Remove the registered listener from this metric.
     *
     * @param listener listener object to be removed
     * @throws ListenerNotFoundException
     * @throws InstanceNotFoundException
     */
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException, InstanceNotFoundException {
        mBeanServer.removeNotificationListener(this.mBeanObjectName, listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mBeanObjectName, this.meterName);
    }

    /**
     * It returns JMX style metric representation in string.
     * e.g :     {domain}:type={type},name={metric_name},tag1={tag1_value}.{meterName}
     * <p>
     * Note: the order of type,name and tags are not guaranteed.
     *
     * @return returns JMX style metric representation in string.
     */
    @Override
    public String toString() {
        if ("Value".equals(meterName) || "Number".equals(meterName)) {
            return mBeanObjectName.toString();
        } else {
            return mBeanObjectName.toString() + "." + meterName;
        }
    }
}
