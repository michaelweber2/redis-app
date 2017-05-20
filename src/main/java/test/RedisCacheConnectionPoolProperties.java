package test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 *
 */
@ManagedResource
public class RedisCacheConnectionPoolProperties {

    private int maxTotal;
    private int minIdle;
    private int maxIdle;
    private long maxWaitMillis;
    private boolean testOnCreate;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;
    private long minEvictableIdleTimeMillis;
    private long timeBetweenEvictionRunsMillis;
    private String hostname;
    private int port;
    private long timeout;
    private String redisPassword;
    private boolean useRedisPassword;
    private boolean shareNativeConnection;
    private boolean validateConnection;
    private boolean useJedis;

    @ManagedAttribute
    public int getMaxTotal() {
        return maxTotal;
    }

    @Value("#{systemProperties['maxTotal'] ?: 50}")
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    @ManagedAttribute
    public int getMinIdle() {
        return minIdle;
    }

    @Value("#{systemProperties['minIdle'] ?: 1}")
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    @ManagedAttribute
    public int getMaxIdle() {
        return maxIdle;
    }

    @Value("#{systemProperties['maxIdle'] ?: 5}")
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    @ManagedAttribute
    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    @Value("#{systemProperties['maxWaitMillis'] ?: 100L}")
    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    @ManagedAttribute
    public boolean isTestOnCreate() {
        return testOnCreate;
    }

    @Value("#{systemProperties['testOnCreate'] ?: true}")
    public void setTestOnCreate(boolean testOnCreate) {
        this.testOnCreate = testOnCreate;
    }

    @ManagedAttribute
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    @Value("#{systemProperties['testOnBorrow'] ?: true}")
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    @ManagedAttribute
    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    @Value("#{systemProperties['testOnReturn'] ?: true}")
    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    @ManagedAttribute
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    @Value("#{systemProperties['testWhileIdle'] ?: true}")
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    @ManagedAttribute
    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    @Value("#{systemProperties['minEvictableIdleTimeMillis'] ?: 60000L}")
    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    @ManagedAttribute
    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    @Value("#{systemProperties['timeBetweenEvictionRunsMillis'] ?: 30000L}")
    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    @ManagedAttribute
    public String getHostname() {
        return hostname;
    }

    @Value("#{systemProperties['hostname'] ?: 'localhost'}")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @ManagedAttribute
    public int getPort() {
        return port;
    }

    @Value("#{systemProperties['port'] ?: 6379}")
    public void setPort(int port) {
        this.port = port;
    }

    @ManagedAttribute
    public long getTimeout() {
        return timeout;
    }

    @Value("#{systemProperties['connectionTimeout'] ?: 100L}")
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }


    public String getRedisPassword() {
        return redisPassword;
    }

    @Value("#{systemProperties['redisPassword'] ?: ''}")
    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    @ManagedAttribute
    public boolean isUseRedisPassword() {
        return useRedisPassword;
    }

    @Value("#{systemProperties['useRedisPassword'] ?: false}")
    public void setUseRedisPassword(boolean useRedisPassword) {
        this.useRedisPassword = useRedisPassword;
    }

    @ManagedAttribute
    public boolean isShareNativeConnection() {
        return shareNativeConnection;
    }

    @Value("#{systemProperties['shareNativeConnection'] ?: true}")
    public void setShareNativeConnection(boolean shareNativeConnection) {
        this.shareNativeConnection = shareNativeConnection;
    }

    @ManagedAttribute
    public boolean isValidateConnection() {
        return validateConnection;
    }

    @Value("#{systemProperties['validateConnection'] ?: false}")
    public void setValidateConnection(boolean validateConnection) {
        this.validateConnection = validateConnection;
    }

    @ManagedAttribute
    public boolean isUseJedis() {
        return useJedis;
    }

    @Value("#{systemProperties['useJedis'] ?: true}")
    public void setUseJedis(boolean useJedis) {
        this.useJedis = useJedis;
    }
}
