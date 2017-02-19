package org.wzstudio.spring.finance.data.domain;

import com.google.common.base.Objects;

/**
 * The source of the index info
 */
public class ETFIndexConfig {
    /**
     * The database primary key for the config
     */
    public final int id;

    /**
     * The url used to download the index info
     */
    public final String url;

    /**
     * The index config type
     */
    public final ETFIndexConfigType type;

    /**
     * The major country exposure of the index, e.g. China
     * The information cannot retrieved easily from every index info source, so
     * we set it manually here for now
     */
    public final String geographicExposure;

    public ETFIndexConfig(int id,
                          String url,
                          String geographicExposure) {
        this.id = id;
        this.url = url;
        this.geographicExposure = geographicExposure;

        if (url.startsWith("https://www.ishares.com/us")) {
            this.type = ETFIndexConfigType.ISHARE_US;
        } else if (url.startsWith("https://www.blackrock.com/au")) {
            this.type = ETFIndexConfigType.ISHARE_AU;
        } else {
            this.type = ETFIndexConfigType.UNKNOWN;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(url);
    }

    @Override
    public boolean equals(final Object obj){
        if(obj instanceof ETFIndexConfig){
            final ETFIndexConfig other = (ETFIndexConfig) obj;
            return Objects.equal(url, other.url);
        } else {
            return false;
        }
    }
}
